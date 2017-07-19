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
	public static final String C2S_LOGIN = "login";
	public static final String C2S_MESSAGE = "message";
	public static final String C2S_CREATE_ROOM = "createRoom";
	public static final String C2S_JOIN_ROOM = "joinRoom";
	public static final String C2S_GAME = "game";

	// server to client
	public static final String S2C_LOGIN_SUCCESS = "loginSuccess";
	public static final String S2C_LOGIN_FAILED = "loginFailed";
}
