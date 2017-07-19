package com.mjoys.zjh.entity;

import java.util.List;

/**
 * 牌桌
 * 
 * @author t_Ber
 *
 */
public class Table {

	/**
	 * 牌桌编号
	 */
	private int tableID;

	/**
	 * 用户
	 */
	private List<Seat> seats;

	/**
	 * 低注
	 */
	private int minBet;

	/**
	 * 封顶
	 */
	private int maxBet;

	/**
	 * 第几轮
	 */
	private int round;

	public int getTableID() {
		return tableID;
	}

	public void setTableID(int tableID) {
		this.tableID = tableID;
	}

	public List<Seat> getSeats() {
		return seats;
	}

	public void setSeats(List<Seat> seats) {
		this.seats = seats;
	}

	public int getMinBet() {
		return minBet;
	}

	public void setMinBet(int minBet) {
		this.minBet = minBet;
	}

	public int getMaxBet() {
		return maxBet;
	}

	public void setMaxBet(int maxBet) {
		this.maxBet = maxBet;
	}

	public int getRound() {
		return round;
	}

	public void setRound(int round) {
		this.round = round;
	}

}
