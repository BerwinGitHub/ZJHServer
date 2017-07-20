package com.mjoys.zjh.controller;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.DataListener;

public class RoomController extends IController {

	public RoomController(SocketIOServer server) {
		super(server);
		// TODO Auto-generated constructor stub
	}

	public class QuickStartController extends IController implements DataListener<String> {

		public QuickStartController(SocketIOServer server) {
			super(server);
		}

		@Override
		public void onData(SocketIOClient arg0, String arg1, AckRequest arg2) throws Exception {
			TableController tc = arg0.get(G.CACHE_TABLE_CONTROLLER);
			if (tc != null) { // 从前面一个房间中移除
				arg0.del(G.CACHE_TABLE_CONTROLLER);
			}
		}
	}

	public class JoinRoomController extends IController implements DataListener<String> {

		public JoinRoomController(SocketIOServer server) {
			super(server);
		}

		@Override
		public void onData(SocketIOClient arg0, String arg1, AckRequest arg2) throws Exception {
			// TODO Auto-generated method stub

		}
	}

	public class CreateController extends IController implements DataListener<String> {

		public CreateController(SocketIOServer server) {
			super(server);
		}

		@Override
		public void onData(SocketIOClient arg0, String arg1, AckRequest arg2) throws Exception {

		}

	}

}
