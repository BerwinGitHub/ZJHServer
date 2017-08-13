package com.test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.mjoys.zjh.entity.Seat;
import com.mjoys.zjh.utility.ProtobufUtility;

public class Test {

	// J$BF35095B-4003-4AF2-BF2E-5B2EBA6BA748
	public static void main(String[] args) {
		List<Seat> preparedSeats = new ArrayList<>();
		preparedSeats.add(new Seat());
		preparedSeats.add(new Seat());
		preparedSeats.add(new Seat());
		preparedSeats.add(new Seat());
		int seatIDs[] = { 1, 3, 4, 6 };
		int k = 0;
		for (Seat seat : preparedSeats) {
			seat.setSeatID(seatIDs[k]);
			k++;
		}

		Collections.sort(preparedSeats);
		int lastIdx = 1;
		for (int i = 0; i < 7; i++) {
			int a = (i + lastIdx + 7) % 7;
			System.out.println("a:" + a);
			for (Seat seat : preparedSeats) {
				if (seat.getSeatID() == a) {
					System.out.println(seat.getSeatID());
					return;
				}
			}
		}
	}

}
