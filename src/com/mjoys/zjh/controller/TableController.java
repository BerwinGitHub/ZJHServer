package com.mjoys.zjh.controller;

import java.util.List;

import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.mjoys.zjh.collect.TableCollector;
import com.mjoys.zjh.common.CSMapping;
import com.mjoys.zjh.domain.User;
import com.mjoys.zjh.entity.Seat;
import com.mjoys.zjh.entity.Table;
import com.mjoys.zjh.utility.ProtobufUtility;

public class TableController extends IController {

	private Table table;

	public TableController(SocketIOServer server, Table table) {
		super(server);
		this.table = table;
	}

	public Table getTable() {
		return table;
	}

	public void setTable(Table table) {
		this.table = table;
	}

	public void removeSeat(User u) {
		List<Seat> seats = table.getSeats();
		for (int i = 0; i < seats.size(); i++) {
			if (seats.get(i).getUser().getId() == u.getId()) {
				// 移除
				seats.get(i).getSocketIOClient()
						.leaveRoom(this.table.getTableID() + "");
				seats.remove(i);
				// 如果Table里面没有人了就移除这个Table
				if (seats.size() <= 0) {
					TableCollector.getInstance().removeTableControlleByID(
							this.table.getTableID());
				} else {// 需要向其他用户广播
					this.broadcast(CSMapping.S2C.USER_EXIT_TABLE);
				}
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
		// 广播添加了一个用户
		this.broadcast(CSMapping.S2C.USER_ENTER_TABLE);
		return true;
	}

	public void broadcast(String name) {
		byte[] bytes = this.table.toByteArray();
		this.server.getRoomOperations(this.getTable().getTableID() + "")
				.sendEvent(name, ProtobufUtility.stringify(bytes));
	}

}
