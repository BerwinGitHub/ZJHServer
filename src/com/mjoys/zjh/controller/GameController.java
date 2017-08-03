package com.mjoys.zjh.controller;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.DataListener;
import com.mjoys.zjh.domain.User;

public class GameController extends IController {

	public GameController(SocketIOServer server) {
		super(server);
	}

	public class PrepareController extends IController implements DataListener<String> {

		public PrepareController(SocketIOServer server) {
			super(server);
		}

		@Override
		public void onData(SocketIOClient arg0, String arg1, AckRequest arg2) throws Exception {
			User user = arg0.get(G.CACHE_USER); // 用户
			TableController tc = arg0.get(G.CACHE_TABLE_CONTROLLER);
			if (tc != null) { // 从前面一个房间中移除
				tc.prepare(user);
			}

		}
	}

}
