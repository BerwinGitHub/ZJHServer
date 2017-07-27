package com.mjoys.zjh.entity;

import com.corundumstudio.socketio.SocketIOClient;
import com.google.protobuf.GeneratedMessageV3.Builder;
import com.google.protobuf.InvalidProtocolBufferException;
import com.mjoys.zjh.domain.User;
import com.mjoys.zjh.proto.Protobufs;

public class Seat extends IProtobufEntity<Protobufs.Seat> {

	private int seatID;

	private int callCoin;

	private User user;

	private boolean isPrepared;

	private SocketIOClient socketIOClient;

	public Seat(byte[] bs) {
		this.toEntity(bs);
	}

	public Seat(int seatID, User u, SocketIOClient socketIOClient) {
		this.socketIOClient = socketIOClient;
		this.seatID = seatID;
		this.user = u;
		this.callCoin = 0;
	}

	public boolean isPrepared() {
		return isPrepared;
	}

	public void setPrepared(boolean isPrepared) {
		this.isPrepared = isPrepared;
	}

	public SocketIOClient getSocketIOClient() {
		return socketIOClient;
	}

	public void setSocketIOClient(SocketIOClient socketIOClient) {
		this.socketIOClient = socketIOClient;
	}

	public int getSeatID() {
		return seatID;
	}

	public void setSeatID(int seatID) {
		this.seatID = seatID;
	}

	public int getCallCoin() {
		return callCoin;
	}

	public void setCallCoin(int callCoin) {
		this.callCoin = callCoin;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	protected com.mjoys.zjh.proto.Protobufs.Seat parseEntityFromProtoBytes(byte[] bytes) {
		Protobufs.Seat seat = null;
		try {
			seat = Protobufs.Seat.parseFrom(bytes);
		} catch (InvalidProtocolBufferException e) {
			e.printStackTrace();
		}
		return seat;
	}

	@Override
	protected Builder<?> getEntityProtoBuilder() {
		return Protobufs.Seat.newBuilder();
	}

	public void clearSeat() {
		this.callCoin = 0;
		this.isPrepared = false;
	}

}
