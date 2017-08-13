package com.mjoys.zjh.common;

/**
 * 客户端和服务器socket的动作对应表<br/>
 * 客户端和服务器保持一致
 * 
 * @author t_Ber
 * 
 */
public class CSMapping {

	// client to server
	public interface C2S {
		public static final String LOGIN = "login"; // 客户端请求登录
		public static final String MESSAGE = "message"; // 客户端发送消息
		public static final String QUICK_START = "quickStart";
		public static final String CREATE_ROOM = "createRoom";
		public static final String JOIN_ROOM = "joinRoom";
		public static final String EXIT_ROOM = "exitRoom";
		public static final String GAMEING = "gameing";
		
	}

	// server to client
	public interface S2C {
		public static final String LOGIN_SUCCESS = "loginSuccess";
		public static final String LOGIN_FAILED = "loginFailed";

		public static final String USER_EXIT_TABLE = "userExitTable"; // 玩家退出桌子  Prrotobuf -> Seat
		public static final String USER_ENTER_TABLE = "userEnterTable"; // 玩家进入桌子  Prrotobuf -> Seat
		public static final String ENTER_TABLE_SUCCESS = "enterTableSuccess"; // 匹配/加入桌子成功 Prrotobuf -> Table
		public static final String ENTER_TABLE_FAILED = "enterTableFailed";
		
		public static final String GAMEING = "gameing"; // 匹配/加入桌子失败 null

	}
	
	public interface S {
		public static final String LOG_NAME = "logName";
		public static final String GAME_OPT_ACTION = "game_opt_action"; // 加注
		public static final String GAME_SEAT_ID = "game_seat_id"; // 加注
		public static final String GAME_PLC_SEAT_ID = "placementSeatID"; // 加注
		public static final String GAME_COIN = "game_coins"; // 加注
		public static final String GAME_CARDS = "gameCards"; // 加注
		
	}

}
