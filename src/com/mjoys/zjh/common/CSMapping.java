package com.mjoys.zjh.common;

/**
 * 客户端和服务器socket的动作对应表<br/>
 * 客户端和服务器保持一致
 * 
 * @author t_Ber
 * 
 */
public class CSMapping {

	// server to client
	public interface C2S {
		public static final String LOGIN = "login"; // 客户端请求登录
		public static final String MESSAGE = "message"; // 客户端发送消息
		public static final String QUICK_START = "quickStart";
		public static final String CREATE_ROOM = "createRoom";
		public static final String JOIN_ROOM = "joinRoom";
		public static final String EXIT_ROOM = "exitRoom";
		public static final String GAME = "game";
	}

	// client to server
	public interface S2C {
		public static final String LOGIN_SUCCESS = "loginSuccess";
		public static final String LOGIN_FAILED = "loginFailed";

		public static final String USER_EXIT_TABLE = "userExitTable"; // 玩家退出桌子  Prrotobuf -> Seat
		public static final String USER_ENTER_TABLE = "userEnterTable"; // 玩家进入桌子  Prrotobuf -> Seat
		public static final String ENTER_TABLE_SUCCESS = "enterTableSuccess"; // 匹配/加入桌子成功 Prrotobuf -> Table
		public static final String ENTER_TABLE_FAILED = "enterTableFailed"; // 匹配/加入桌子失败 null

		public static final String USER_PREPARE = "userPrepare"; // 玩家准备 Prrotobuf -> Seat

	}

}
