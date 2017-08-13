package com.mjoys.zjh.controller;

import com.corundumstudio.socketio.SocketIOServer;

public abstract class IController {

	public interface G {
		public static final String CACHE_USER = "cache_user";
		public static final String CACHE_TABLE_CONTROLLER = "cache_table_controller";
		public static final String CACHE_WAIT_THREAD = "wait_thread";
	}

	protected SocketIOServer server;

	public IController(SocketIOServer server) {
		this.server = server;
	}

}
