package com.mjoys.zjh.controller;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.DataListener;

public class GameController extends IController implements DataListener<String> {

	public GameController(SocketIOServer server) {
		super(server);
	}

	@Override
	public void onData(SocketIOClient arg0, String arg1, AckRequest arg2) throws Exception {

	}

}
