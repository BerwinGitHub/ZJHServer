package com.mjoys.zjh.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.google.protobuf.ByteString;
import com.mjoys.zjh.collect.TableCollector;
import com.mjoys.zjh.common.CSMapping;
import com.mjoys.zjh.confgs.Configs;
import com.mjoys.zjh.domain.User;
import com.mjoys.zjh.entity.Seat;
import com.mjoys.zjh.entity.Table;
import com.mjoys.zjh.entity.WaitThread;
import com.mjoys.zjh.proto.Protobufs.GameAction;
import com.mjoys.zjh.proto.Protobufs.GameOperate;
import com.mjoys.zjh.service.ZJHPokerService;
import com.mjoys.zjh.utility.ProtobufUtility;

public class TableController extends IController implements Runnable {

	/**
	 * 一个桌子最多好多人
	 */
	public static final int TABLE_MAX_PLAYERS = Configs.intValue("table_max_players");
	/**
	 * 最少多少人准备才可以开始游戏，支持配置
	 */
	public static final int MIN_PLAYER_START = Configs.intValue("min_player_start");
	/**
	 * MIN_PLAYER_START个用户准备好后，等待开始的时间
	 */
	public static final long PREPARED_WAIT_SECOND = Configs.intValue("prepared_wait_second");
	/**
	 * 游戏操作的等待时间
	 */
	public static final long GAME_OPT_SECOND = Configs.intValue("game_opt_second");

	private Table table;

	private ZJHPokerService zjhPokerService = null;

	private boolean isWaitStarted = false;
	private boolean isGameStarted = false;
	private Seat lastWinSeat = null;
	private WaitThread currentWaitThread = null;
	/**
	 * 准备了的用户
	 */
	private List<Seat> preparedSeats = null;

	public TableController(SocketIOServer server, Table table) {
		super(server);
		this.table = table;
		preparedSeats = new ArrayList<Seat>();
		this.zjhPokerService = new ZJHPokerService();
	}

	public Table getTable() {
		return table;
	}

	public void setTable(Table table) {
		this.table = table;
	}

	public void prepare(User u) {
		if (isGameStarted) {
			// TODO 游戏已经开始了，不能准备了
			return;
		}
		Seat seat = this.table.getSeatByUser(u);
		if (seat == null)
			return;
		seat.setPrepared(true);
		this.preparedSeats.add(seat);
		// 广播用户准备的消息
		this.broadcast(CSMapping.S2C.GAMEING, buildGameOperate(GameAction.PREPARE, seat.getSeatID(), -1, 0, null, 0L));
		// 如果>=两个人，就可以开始倒计时开始游戏
		if (!this.isWaitStarted && table.getPrepareCount() >= MIN_PLAYER_START) {
			new Thread(this).start();// 开启游戏线程
		}
	}

	public void removeSeat(User u) {
		List<Seat> seats = table.getSeats();
		for (int i = 0; i < seats.size(); i++) {
			if (seats.get(i).getUser().getId() == u.getId()) {
				if (seats.get(i).isPrepared()) { // 用户已经准备了
					this.giveup(seats.get(i)); // 如果准备了，离开就放弃
				}
				// 移除
				byte[] bs = seats.get(i).toByteArray();
				seats.get(i).getSocketIOClient().leaveRoom(this.table.getTableID() + "");
				seats.remove(i);
				// 如果Table里面没有人了就移除这个Table
				if (seats.size() <= 0) {
					TableCollector.getInstance().removeTableControlleByID(this.table.getTableID());
				} else {// 需要向其他用户广播
					this.broadcast(CSMapping.S2C.USER_EXIT_TABLE, bs);
				}
				System.out.println(
						"移除\t用户:" + u.getId() + " 房间:" + this.getTable().getTableID() + "\t剩余:" + seats.size());
				return;
			}
		}
	}

	public boolean addSeat(User u, SocketIOClient socketIOClient) {
		if (this.table.getSeats().size() >= TableController.TABLE_MAX_PLAYERS) {
			System.out.println("该房间人数已经达到最大:" + this.table.getTableID());
			return false;
		}
		Seat seat = new Seat(this.table.getEmptySeatID(TableController.TABLE_MAX_PLAYERS), u, socketIOClient);
		socketIOClient.joinRoom(this.table.getTableID() + "");
		this.table.getSeats().add(seat);
		System.out.println(
				"加入\t用户:" + u.getId() + " 房间:" + this.getTable().getTableID() + "\t剩余:" + this.table.getSeats().size());
		// 广播添加了一个用户
		this.broadcast(CSMapping.S2C.USER_ENTER_TABLE, seat.toByteArray());
		return true;
	}

	public void broadcast(String name, byte[] bytes) {
		this.server.getRoomOperations(this.getTable().getTableID() + "").sendEvent(name,
				ProtobufUtility.stringify(bytes));
	}

	@Override
	public void run() {
		this.isWaitStarted = true;
		long startMillis = System.currentTimeMillis() + PREPARED_WAIT_SECOND * 1000;
		// 广播在startMillis的时候开始游戏
		this.broadcast(CSMapping.S2C.GAMEING,
				buildGameOperate(GameAction.COUNTDOWN_START, -1, -1, -1, null, startMillis));
		try {
			Thread.sleep(startMillis - System.currentTimeMillis()); // 等待时间准备开始
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 游戏正式开始，广播游戏开始
		this.isGameStarted = true;
		// 根据出牌顺序排序
		this.makeTurnQueue();
		// 广播发牌
		this.broadcast(CSMapping.S2C.GAMEING,
				buildGameOperate(GameAction.SEND_CARD, this.preparedSeats.get(0).getSeatID(), -1, -1, null, 0L));
		// 洗牌/切牌
		this.zjhPokerService.shufflePoker();
		this.zjhPokerService.randomCutPoker();
		// 发牌
		for (int i = 0; i < 3; i++) {
			for (Seat seat : preparedSeats) {
				seat.getCards().add(this.zjhPokerService.nextCard());
			}
		}
		// 开始操作流程
		while (isGameStarted) {
			for (Seat seat : preparedSeats) {
				this.currentWaitThread = new WaitThread();
				seat.getSocketIOClient().set(G.CACHE_WAIT_THREAD, this.currentWaitThread);
				System.out.println("轮到" + seat.getSeatID() + "操作");
				long endMillis = System.currentTimeMillis() + GAME_OPT_SECOND * 1000;
				// 广播该谁操作了
				this.broadcast(CSMapping.S2C.GAMEING,
						buildGameOperate(GameAction.TURN, seat.getSeatID(), -1, -1, null, endMillis));
				this.currentWaitThread.await(endMillis); // 给用户15秒钟的时间操作
				// 用户操作了，或者超时 1.先删除等待操作线程
				seat.getSocketIOClient().del(G.CACHE_WAIT_THREAD);
				this.currentWaitThread = null;
				// 取值前 先看看还有几个用户在
				if (this.preparedSeats.size() <= 1) { // 小于等于1个人
					this.isGameStarted = false; // 游戏结束
					break;
				}
				// 2 取值
				if (this.currentWaitThread.isTimeout()) { // 超时就放弃
					this.giveup(seat);
				}
				int action = (int) this.currentWaitThread.get(CSMapping.S.GAME_OPT_ACTION);
				// 3.TODO 根据操作做不同的处理

			}
		}
		// 结束游戏
		this.lastWinSeat = this.preparedSeats.get(0);
		this.isGameStarted = false;
		this.isWaitStarted = false;
		this.preparedSeats.clear();
		// TODO 广播游戏结束
		this.broadcast(CSMapping.S2C.GAMEING, buildGameOperate(GameAction.END, -1, -1, -1, null, 0L));
	}

	/**
	 * 用户弃牌
	 * 
	 * @param seat
	 */
	private void giveup(Seat seat) {
		preparedSeats.remove(seat);
		seat.setPrepared(false);
		// 广播弃牌
		this.broadcast(CSMapping.S2C.GAMEING, buildGameOperate(GameAction.GIVEUP, seat.getSeatID(), -1, 0, null, 0L));
		if (preparedSeats.size() <= 1 && this.currentWaitThread != null) { // 当小于2个人的时候通知继续执行
			this.currentWaitThread.ewait();
		}
	}

	private byte[] buildGameOperate(GameAction ga, int seatID, int plcSeatID, int coin, List<Byte> cards, long millis) {
		GameOperate.Builder builder = GameOperate.newBuilder();
		builder.setAction(ga);
		builder.setSeatID(seatID);
		builder.setPlacementSeatID(plcSeatID);
		builder.setCoin(coin);
		builder.setMillis(millis);
		if (cards != null) {
			for (int i = 0; i < cards.size(); i++) {
				byte[] bs = { cards.get(i).byteValue() };
				builder.setCards(i, ByteString.copyFrom(bs));
			}
		}
		return builder.build().toByteArray();
	}

	/**
	 * 生成这轮的顺序
	 */
	public void makeTurnQueue() {
		// 1.先从小到大排序
		Collections.sort(this.preparedSeats);
		int startIdx = this.getFirstSeat().getSeatID();
		// 不是开始的就往后移，找到为止
		while (this.preparedSeats.get(0).getSeatID() != startIdx) {
			Seat seat = this.preparedSeats.remove(0);
			this.preparedSeats.add(seat);
		}

	}

	private Seat getFirstSeat() {
		// 1.先确定第一个发牌的是谁(上次赢的下家)
		if (this.lastWinSeat == null) {// 上次没有赢家
			// 随机在准备的玩家中选取一个
			int idx = new Random().nextInt(this.preparedSeats.size());
			return this.preparedSeats.get(idx);
		} else { // 上次有赢家
			// 在准备的里面没有找到上把的赢家
			int lastIdx = this.lastWinSeat.getSeatID();
			for (int i = 0; i < TABLE_MAX_PLAYERS; i++) {
				int idx = (i + lastIdx + TABLE_MAX_PLAYERS) % TABLE_MAX_PLAYERS;
				for (Seat seat : preparedSeats) {
					if (seat.getSeatID() == idx)
						return seat;
				}
			}
		}
		return null;
	}

}
