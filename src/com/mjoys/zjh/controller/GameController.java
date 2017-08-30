package com.mjoys.zjh.controller;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.DataListener;
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
		User user = arg0.get(G.CACHE_USER); // 用户
		TableController tc = arg0.get(G.CACHE_TABLE_CONTROLLER);
		WaitThread wt = arg0.get(G.CACHE_WAIT_THREAD);
		wt.set("Action", go.getActionValue());
		int action = go.getActionValue();
		if (action == GameAction.PREPARE_VALUE) { // 准备
			if (tc != null) // 从前面一个房间中移除
				tc.prepare(user);
		} else if (action == GameAction.ADDBET_VALUE) { // 加注
			if (tc != null) {
				tc.addBet(go.getSeatID(), go.getCoin());
				if (wt != null)
					wt.ewait();
			}
		} else if (action == GameAction.FOLLOW_VALUE) { // 跟注
			if (tc != null) {
				tc.followBet(go.getSeatID(), go.getCoin());
				if (wt != null)
					wt.ewait();
			}
		} else if (action == GameAction.COMPARE_VALUE) { // 比较
			if (tc != null) {
				tc.compare(go.getSeatID(), go.getPlacementSeatID());
				if (wt != null)
					wt.ewait();
			}
		} else if (action == GameAction.WATCH_VALUE) { // 看牌
			if (tc != null) {
				tc.watch(go.getSeatID());
				if (wt != null)
					wt.ewait();
			}
		} else if (action == GameAction.GIVEUP_VALUE) { // 弃牌
			if (tc != null) {
				tc.giveup(go.getSeatID());
				if (wt != null)
					wt.ewait();
			}
		}
	}

}
