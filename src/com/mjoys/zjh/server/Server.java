package com.mjoys.zjh.server;

import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOServer;
import com.mjoys.zjh.common.CSMapping;
import com.mjoys.zjh.controller.GameController;
import com.mjoys.zjh.controller.LifeCircleController;
import com.mjoys.zjh.controller.LoginController;
import com.mjoys.zjh.controller.MessageController;
import com.mjoys.zjh.controller.RoomController;

public class Server {

	private String host;

	private int port;

	private boolean isRunning;

	private SocketIOServer server;

	public Server(String host, int port) {
		this.host = host;
		this.port = port;
		this.isRunning = false;
		Configuration config = new Configuration();
		config.setHostname(this.host);
		config.setPort(this.port);

		this.server = new SocketIOServer(config);
		LifeCircleController lifeCircle = new LifeCircleController(this.server);
		this.server.addConnectListener(lifeCircle);
		this.server.addDisconnectListener(lifeCircle);

		// Message
		this.server.addEventListener(CSMapping.C2S_MESSAGE, String.class, new MessageController(this.server));

		// Login
		this.server.addEventListener(CSMapping.C2S_LOGIN, String.class, new LoginController(this.server));

		// createRoom
		RoomController room = new RoomController(server);
		this.server.addEventListener(CSMapping.C2S_CREATE_ROOM, String.class, room);
		this.server.addEventListener(CSMapping.C2S_JOIN_ROOM, String.class, room);

		// game
		this.server.addEventListener(CSMapping.C2S_GAME, String.class, new GameController(server));
	}

	public void start() {
		server.start();
		System.out.println("Server start at - " + this.server.getConfiguration().getHostname() + ":"
				+ this.server.getConfiguration().getPort());
		this.isRunning = true;
	}

	public void stop() {
		if (this.server != null) {
			this.server.stop();
		}
		this.isRunning = false;
	}

	public boolean isRunning() {
		return isRunning;
	}

	public void setRunning(boolean isRunning) {
		this.isRunning = isRunning;
	}

	public SocketIOServer getServer() {
		return server;
	}

	public void setServer(SocketIOServer server) {
		this.server = server;
	}

}