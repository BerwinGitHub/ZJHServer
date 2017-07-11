package com.mjoys.zjh.controller;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.DataListener;
import com.mjoys.zjh.utility.ProtobufUtility;

public class LoginController extends IController implements
		DataListener<String> {

	public LoginController(SocketIOServer server) {
		super(server);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onData(SocketIOClient arg0, String arg1, AckRequest arg2)
			throws Exception {
		byte bytes[] = ProtobufUtility.toBytes(arg1);
		
		for (int i = 0; i < bytes.length; i++) {
			System.out.print(bytes[i] + ",");
		}
	}

}
