package com.mjoys.zjh.controller;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.DataListener;

@SuppressWarnings("rawtypes")
public class MessageController extends IController implements DataListener {

	public MessageController(SocketIOServer server) {
		super(server);
	}

	@Override
	public void onData(SocketIOClient arg0, Object arg1, AckRequest arg2)
			throws Exception {

	}

}
