package com.mjoys.zjh.entity;

import java.util.ArrayList;
import java.util.List;

import com.corundumstudio.socketio.SocketIOClient;
import com.mjoys.zjh.domain.IBPEntity;
import com.mjoys.zjh.domain.User;
import com.mjoys.zjh.proto.Protobufs;

public class Seat extends IBPEntity<Protobufs.Seat> implements Comparable<Seat> {

	private int seatID = 0;

	private int callCoin = 0;

	private User user;

	private boolean isPrepared = false;

	private List<Byte> cards = new ArrayList<>();

	private SocketIOClient socketIOClient;

	public Seat() {
	}

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

	public void clearSeat() {
		this.callCoin = 0;
		this.isPrepared = false;
	}

	@Override
	public String getTableName() {
		return null;
	}

	@Override
	public int compareTo(Seat o) {
		return this.seatID - o.seatID;
	}

	public List<Byte> getCards() {
		return cards;
	}

	public void setCards(List<Byte> cards) {
		this.cards = cards;
	}

}
