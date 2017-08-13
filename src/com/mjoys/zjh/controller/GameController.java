package com.mjoys.zjh.controller;

import java.util.logging.Logger;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.DataListener;
import com.mjoys.zjh.common.CSMapping;
import com.mjoys.zjh.domain.User;
import com.mjoys.zjh.entity.WaitThread;
import com.mjoys.zjh.proto.Protobufs.GameAction;
import com.mjoys.zjh.proto.Protobufs.GameOperate;
import com.mjoys.zjh.utility.ProtobufUtility;

public class GameController extends IController implements DataListener<String> {

	public GameController(SocketIOServer server) {
		super(server);
	}

	@Override
	public void onData(SocketIOClient arg0, String arg1, AckRequest arg2) throws Exception {
		byte bs[] = ProtobufUtility.toBytes(arg1);
		GameOperate go = GameOperate.parseFrom(bs);
		int action = go.getActionValue();
		if (action == GameAction.PREPARE_VALUE) {
			User user = arg0.get(G.CACHE_USER); // 用户
			TableController tc = arg0.get(G.CACHE_TABLE_CONTROLLER);
			if (tc != null) // 从前面一个房间中移除
				tc.prepare(user);
		} else if (action == GameAction.PREPARE_VALUE || action == GameAction.ADDBET_VALUE
				|| action == GameAction.FOLLOW_VALUE || action == GameAction.GIVEUP_VALUE
				|| action == GameAction.COMPARE_VALUE || action == GameAction.WATCH_VALUE) {
			WaitThread wt = arg0.get(G.CACHE_WAIT_THREAD);
			if (wt != null) {
				// 赋值
				wt.set(CSMapping.S.GAME_OPT_ACTION, go.getActionValue() + "");
				wt.set(CSMapping.S.GAME_SEAT_ID, go.getSeatID());
				wt.set(CSMapping.S.GAME_PLC_SEAT_ID, go.getPlacementSeatID());
				wt.set(CSMapping.S.GAME_COIN, go.getCoin());
				wt.set(CSMapping.S.GAME_CARDS, go.getCardsList());
				wt.ewait();// 结束等待，继续执行TableController里面的代码
			} else {
				Logger.getLogger(CSMapping.S.LOG_NAME).warning("用户超时，或者代码有bug，导致用户操作没有等待线程");
			}
		}
	}

}
