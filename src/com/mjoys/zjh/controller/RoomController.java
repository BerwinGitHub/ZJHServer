package com.mjoys.zjh.controller;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.DataListener;
import com.mjoys.zjh.collect.TableCollector;
import com.mjoys.zjh.domain.User;
import com.mjoys.zjh.entity.Table;
import com.mjoys.zjh.utility.ProtobufUtility;

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
			User user = arg0.get(G.CACHE_USER); // 用户
			TableController tc = arg0.get(G.CACHE_TABLE_CONTROLLER);
			if (tc != null) { // 从前面一个房间中移除
				tc.removeSeat(user);
				arg0.del(G.CACHE_TABLE_CONTROLLER);
			}
			// 快速查找空房间 / 创建一个空房间
			TableController newTc = TableCollector.getInstance().quickStart(this.server, user);
			arg0.set(G.CACHE_TABLE_CONTROLLER, newTc);
			// TODO 向客户端发送房间数据
		}
	}

	public class JoinRoomController extends IController implements DataListener<String> {

		public JoinRoomController(SocketIOServer server) {
			super(server);
		}

		@Override
		public void onData(SocketIOClient arg0, String arg1, AckRequest arg2) throws Exception {
			User user = arg0.get(G.CACHE_USER); // 用户
			TableController tc = arg0.get(G.CACHE_TABLE_CONTROLLER);
			if (tc != null) { // 从前面一个房间中移除
				tc.removeSeat(user);
				arg0.del(G.CACHE_TABLE_CONTROLLER);
			}
			byte bytes[] = ProtobufUtility.toBytes(arg1);
			Table table = new Table(bytes);
			TableController newTc = TableCollector.getInstance().joinTable(this.server, user, table.getTableID());
			if (tc != null) {
				arg0.set(G.CACHE_TABLE_CONTROLLER, newTc);
				// TODO 回执加入成功

			} else {
				// TODO 回执加入失败

			}
		}
	}

	public class CreateController extends IController implements DataListener<String> {

		public CreateController(SocketIOServer server) {
			super(server);
		}

		@Override
		public void onData(SocketIOClient arg0, String arg1, AckRequest arg2) throws Exception {
			User user = arg0.get(G.CACHE_USER); // 用户
			TableController tc = arg0.get(G.CACHE_TABLE_CONTROLLER);
			if (tc != null) { // 从前面一个房间中移除
				tc.removeSeat(user);
				arg0.del(G.CACHE_TABLE_CONTROLLER);
			}
			byte bytes[] = ProtobufUtility.toBytes(arg1);
			Table table = new Table(bytes);
			TableController newTc = TableCollector.getInstance().createTable(this.server, user, table);
			if (tc != null) {
				arg0.set(G.CACHE_TABLE_CONTROLLER, newTc);
				// TODO 回执成功创建

			} else {
				// TODO 回执创建失败

			}
		}

	}

}
