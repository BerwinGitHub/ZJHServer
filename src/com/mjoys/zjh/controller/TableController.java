package com.mjoys.zjh.controller;

import java.util.List;

import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.mjoys.zjh.collect.TableCollector;
import com.mjoys.zjh.common.CSMapping;
import com.mjoys.zjh.confgs.Configs;
import com.mjoys.zjh.domain.User;
import com.mjoys.zjh.entity.Seat;
import com.mjoys.zjh.entity.Table;
import com.mjoys.zjh.service.ZJHPokerService;
import com.mjoys.zjh.utility.ProtobufUtility;

public class TableController extends IController implements Runnable {

	/**
	 * 最少多少人准备才可以开始游戏，支持配置
	 */
	public static final int MIN_PLAYER_START = Configs.intValue("min_player_start");

	private Table table;

	private ZJHPokerService zjhPokerService = null;

	private boolean isRunning = false;

	public TableController(SocketIOServer server, Table table) {
		super(server);
		this.table = table;
		this.zjhPokerService = new ZJHPokerService();
	}

	public Table getTable() {
		return table;
	}

	public void setTable(Table table) {
		this.table = table;
	}

	public void prepare(User u) {
		Seat seat = this.table.getSeatByUser(u);
		if (seat == null)
			return;
		seat.setPrepared(true);
		// 广播用户准备的消息
		this.broadcast(CSMapping.S2C.USER_PREPARE, seat.toByteArray());
		// 如果>=两个人，就可以开始倒计时开始游戏
		if (this.table.getPrepareCount() >= MIN_PLAYER_START) {

		}
	}

	public void removeSeat(User u) {
		List<Seat> seats = table.getSeats();
		for (int i = 0; i < seats.size(); i++) {
			if (seats.get(i).getUser().getId() == u.getId()) {
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
				System.out.println("移除房间:" + this.getTable().getTableID() + "\tsize:" + seats.size());
				return;
			}
		}
	}

	public boolean addSeat(User u, SocketIOClient socketIOClient) {
		if (this.table.getSeats().size() >= Table.MAX_SEAT_PLAYER) {
			System.out.println("该房间人数已经达到最大:" + this.table.getTableID());
			return false;
		}
		Seat seat = new Seat(this.table.getEmptySeatID(), u, socketIOClient);
		socketIOClient.joinRoom(this.table.getTableID() + "");
		this.table.getSeats().add(seat);
		System.out.println("加入房间:" + this.getTable().getTableID() + "\tsize:" + this.table.getSeats().size());
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
		// TODO 游戏中依次出牌
		while (isRunning) {

		}
	}

}
