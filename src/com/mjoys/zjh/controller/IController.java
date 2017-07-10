package com.mjoys.zjh.controller;

import com.corundumstudio.socketio.SocketIOServer;

public abstract class IController {

	protected SocketIOServer server;

	public IController(SocketIOServer server) {
		this.server = server;
	}

}
