package com.mjoys.zjh.collect;

import java.util.ArrayList;
import java.util.List;

import com.mjoys.zjh.controller.TableController;

public class TableCollector {

	private static TableCollector instance = null;

	private List<TableController> tables;

	private TableCollector() {
		this.tables = new ArrayList<>();
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
	public TableController quickStart() {

		return null;
	}

	public List<TableController> getTables() {
		return tables;
	}

	public void setTables(List<TableController> tables) {
		this.tables = tables;
	}

}
