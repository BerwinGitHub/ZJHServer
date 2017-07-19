package com.mjoys.zjh.entity;

import com.mjoys.zjh.domain.User;

public class Seat {

	private int seatID;

	private int roundCoin;

	private User user;

	public int getSeatID() {
		return seatID;
	}

	public void setSeatID(int seatID) {
		this.seatID = seatID;
	}

	public int getRoundCoin() {
		return roundCoin;
	}

	public void setRoundCoin(int roundCoin) {
		this.roundCoin = roundCoin;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
