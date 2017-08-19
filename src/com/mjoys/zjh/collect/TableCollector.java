package com.mjoys.zjh.collect;

import java.util.ArrayList;
import java.util.List;

import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.mjoys.zjh.controller.TableController;
import com.mjoys.zjh.domain.User;
import com.mjoys.zjh.entity.Table;

public class TableCollector {

	public static final int BET_ITEMS[] = { 1, 3, 5, 8, 10 };
	public static final int DEFAULT_BET = 10;

	private static TableCollector instance = null;

	private List<TableController> tableCollector;

	private TableCollector() {
		this.tableCollector = new ArrayList<>();
	}

	public synchronized static TableCollector getInstance() {
		if (instance == null) {
			instance = new TableCollector();
		}
		return instance;
	}

	/**
	 * 加入或者创建一个房间/ 有可能是
	 * 
	 * @return
	 */
	public synchronized TableController quickStart(SocketIOServer server, User u, SocketIOClient sIoClient) {
		for (int i = 0; i < tableCollector.size(); i++) {
			if (this.tableCollector.get(i).addSeat(u, sIoClient)) { // 向位置上添加用户，成功返回true
				return tableCollector.get(i);
			}
		}
		// 没有找到就创建一个TableController
		TableController tableController = new TableController(server, this.buildTable());
		// 向table中添加一个Seat
		tableController.addSeat(u, sIoClient);
		// 添加到缓存中
		this.tableCollector.add(tableController);
		return tableController;
	}

	public synchronized TableController createTable(SocketIOServer server, User u, Table table,
			SocketIOClient sIoClient) {
		// 没有找到就创建一个TableController
		TableController tableController = new TableController(server, this.buildTable());
		tableController.getTable().setMinBet(table.getMinBet());
		tableController.getTable().setMaxBet(table.getMaxBet());
		// 向table中添加一个Seat
		tableController.addSeat(u, sIoClient);
		// 添加到缓存中
		this.tableCollector.add(tableController);
		return tableController;
	}

	public synchronized TableController joinTable(SocketIOServer server, User u, int tableID,
			SocketIOClient sIoClient) {
		TableController tc = this.getTableControllerByID(tableID);
		if (tc != null) {
			tc.addSeat(u, sIoClient);
		}
		return tc;
	}

	public List<TableController> getTables() {
		return tableCollector;
	}

	public void setTables(List<TableController> tables) {
		this.tableCollector = tables;
	}

	/**
	 * 生成桌子
	 * 
	 * @return
	 */
	private Table buildTable() {
		Table table = new Table(this.tableCollector.size() + 1, DEFAULT_BET);
		return table;
	}

	private TableController getTableControllerByID(int tableID) {
		for (int i = 0; i < tableCollector.size(); i++) {
			if (this.tableCollector.get(i).getTable().getTableID() == tableID) { // 向位置上添加用户，成功返回true
				return tableCollector.get(i);
			}
		}
		return null;
	}

	public void removeTableControlleByID(int tableID) {
		for (int i = 0; i < tableCollector.size(); i++) {
			if (this.tableCollector.get(i).getTable().getTableID() == tableID) { // 向位置上添加用户，成功返回true
				this.tableCollector.remove(i);
			}
		}

	}

}
