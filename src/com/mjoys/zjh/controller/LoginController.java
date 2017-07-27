package com.mjoys.zjh.controller;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.DataListener;
import com.mjoys.zjh.common.CSMapping;
import com.mjoys.zjh.domain.User;
import com.mjoys.zjh.service.UserService;
import com.mjoys.zjh.utility.ProtobufUtility;

public class LoginController extends IController implements
		DataListener<String> {

	public LoginController(SocketIOServer server) {
		super(server);
	}

	@Override
	public void onData(SocketIOClient arg0, String arg1, AckRequest arg2)
			throws Exception {
		byte bytes[] = ProtobufUtility.toBytes(arg1);
		User user = new User(bytes);
		UserService userService = new UserService();
		user = userService.queryByDeviceID(user.getDeviceId());
		if (user != null) { // 登录成功，返回用户数据
			// 添加到在线用户中
			arg0.set(G.CACHE_USER, user);
			String msg = ProtobufUtility.stringify(user.toByteArray());
			arg0.sendEvent(CSMapping.S2C.LOGIN_SUCCESS, msg);
		} else { // 登录失败
			arg0.sendEvent(CSMapping.S2C.LOGIN_FAILED, "");
		}
	}

}
