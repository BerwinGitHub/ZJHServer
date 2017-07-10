package com.mjoys.zjh.controller;

import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DisconnectListener;

public class LifeCircleController extends IController implements
		ConnectListener, DisconnectListener {

	public LifeCircleController(SocketIOServer server) {
		super(server);
	}

	@Override
	public void onDisconnect(SocketIOClient arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onConnect(SocketIOClient arg0) {
		// TODO Auto-generated method stub

	}

}
