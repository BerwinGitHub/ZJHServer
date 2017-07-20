package com.mjoys.zjh.controller;

import com.corundumstudio.socketio.SocketIOServer;
import com.mjoys.zjh.entity.Table;

public class TableController extends IController {

	private Table table;

	public TableController(SocketIOServer server) {
		super(server);
	}

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

}
