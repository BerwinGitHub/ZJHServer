package com.mjoys.zjh.entity;

import com.mjoys.zjh.domain.User;

public class Seat {

	private int seatID;

	private int callCoin;

	private User user;

	public Seat(int seatID, User u) {
		this.seatID = seatID;
		this.user = u;
		this.callCoin = 0;
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

}
