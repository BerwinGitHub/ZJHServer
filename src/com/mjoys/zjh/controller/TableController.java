package com.mjoys.zjh.controller;

import java.util.List;

import com.corundumstudio.socketio.SocketIOServer;
import com.mjoys.zjh.domain.User;
import com.mjoys.zjh.entity.Seat;
import com.mjoys.zjh.entity.Table;

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
				seats.remove(i);
				// TODO 如果Table里面没有人了就移除这个Table
				// TODO 需要向其他用户广播
				return;
			}
		}
	}

	public boolean addSeat(User u) {
		if (this.table.getSeats().size() >= Table.MAX_SEAT_PLAYER) {
			System.out.println("该房间人数已经达到最大:" + this.table.getTableID());
			return false;
		}
		Seat seat = new Seat(this.table.getEmptySeatID(), u);
		this.table.getSeats().add(seat);
		// TODO 广播添加了一个用户
		return true;
	}

}
