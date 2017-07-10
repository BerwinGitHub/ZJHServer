package com.mjoys.zjh.controller;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.DataListener;

@SuppressWarnings("rawtypes")
public class RoomController extends IController implements DataListener {

	public RoomController(SocketIOServer server) {
		super(server);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onData(SocketIOClient arg0, Object arg1, AckRequest arg2)
			throws Exception {
		// TODO Auto-generated method stub

	}

}
