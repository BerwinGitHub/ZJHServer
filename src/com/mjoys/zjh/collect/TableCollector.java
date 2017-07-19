package com.mjoys.zjh.collect;

import java.util.ArrayList;
import java.util.List;

import com.mjoys.zjh.entity.Table;

public class TableCollector {

	public static TableCollector instance = null;

	private List<Table> tables;

	private TableCollector() {
		this.tables = new ArrayList<>();
	}

	public synchronized static TableCollector getInstance() {
		if (instance == null) {
			instance = new TableCollector();
		}
		return instance;
	}

	public List<Table> getTables() {
		return tables;
	}

	public void setTables(List<Table> tables) {
		this.tables = tables;
	}

}
