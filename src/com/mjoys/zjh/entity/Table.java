package com.mjoys.zjh.entity;

import java.util.ArrayList;
import java.util.List;

import com.mjoys.zjh.confgs.Configs;
import com.mjoys.zjh.domain.IBPEntity;
import com.mjoys.zjh.domain.User;
import com.mjoys.zjh.proto.Protobufs;

/**
 * 牌桌
 * 
 * @author t_Ber
 * 
 */
public class Table extends IBPEntity<Protobufs.Table> {

	public static final int MAX_SEAT_PLAYER = Configs.intValue("max_seat_player");

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

	public Table() {
	}

	public Table(byte[] bs) {
		this.toEntity(bs);
	}

	public Table(int tableID) {
		this.tableID = tableID;
		this.seats = new ArrayList<>();
		this.round = 0;
	}

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

	public Seat getSeatByUser(User u) {
		for (int i = 0; i < seats.size(); i++) {
			if (seats.get(i).getUser().getId() == u.getId()) { // 找到了
				return seats.get(i);
			}
		}
		return null;
	}

	public Seat getSeatByID(int seatID) {
		for (int i = 0; i < seats.size(); i++) {
			if (seats.get(i).getSeatID() == seatID) { // 找到了
				return seats.get(i);
			}
		}
		return null;
	}

	public int getEmptySeatID() {
		for (int i = 0; i < MAX_SEAT_PLAYER; i++) {
			Seat seat = this.getSeatByID(i);
			if (seat == null) {
				return i;
			}
		}
		return -1;
	}

	public int getPrepareCount() {
		int total = 0;
		for (int i = 0; i < seats.size(); i++) {
			if (seats.get(i).isPrepared())
				total++;
		}
		return total;
	}

	public void clearTable() {
		this.round = 0;
		for (int i = 0; i < seats.size(); i++) {
			this.seats.get(i).clearSeat();
		}
	}

	@Override
	public String getTableName() {
		return "";
	}

}
