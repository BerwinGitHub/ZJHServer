package com.mjoys.zjh.controller;

import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DisconnectListener;
import com.mjoys.zjh.domain.User;

public class LifeCircleController extends IController implements ConnectListener, DisconnectListener {

	public LifeCircleController(SocketIOServer server) {
		super(server);
	}

	@Override
	public void onDisconnect(SocketIOClient arg0) {
		User u = arg0.get(G.CACHE_USER);
		TableController tc = arg0.get(G.CACHE_TABLE_CONTROLLER);
		if (tc != null) {
			tc.removeSeat(u);
			arg0.del(G.CACHE_TABLE_CONTROLLER);
		}
		System.out.println("onDisconnect SessionId:" + arg0.getSessionId());
		arg0.disconnect();
	}

	@Override
	public void onConnect(SocketIOClient arg0) {
		System.out.println("onConnect SessionId:" + arg0.getSessionId());
	}

}
