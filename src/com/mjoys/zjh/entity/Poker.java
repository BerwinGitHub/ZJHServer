package com.mjoys.zjh.entity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Poker {

	public static final Byte[] CARDS_WITH_JOKER = new Byte[] { // 54张牌
			0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08, 0x09, 0x0A, 0x0B, 0x0C, // 方块
			0x10, 0x11, 0x12, 0x13, 0x14, 0x15, 0x16, 0x17, 0x18, 0x19, 0x1A, 0x1B, 0x1C, // 樱花
			0x20, 0x21, 0x22, 0x23, 0x24, 0x25, 0x26, 0x27, 0x28, 0x29, 0x2A, 0x2B, 0x2C, // 红桃
			0x30, 0x31, 0x32, 0x33, 0x34, 0x35, 0x36, 0x37, 0x38, 0x39, 0x3A, 0x3B, 0x3C, // 黑桃
			0x40, 0x41 }; // 大小王

	public static final Byte[] CARDS_WITHOUT_JOKER = new Byte[] { // 52张牌
			0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08, 0x09, 0x0A, 0x0B, 0x0C, // 方块
			0x10, 0x11, 0x12, 0x13, 0x14, 0x15, 0x16, 0x17, 0x18, 0x19, 0x1A, 0x1B, 0x1C, // 樱花
			0x20, 0x21, 0x22, 0x23, 0x24, 0x25, 0x26, 0x27, 0x28, 0x29, 0x2A, 0x2B, 0x2C, // 红桃
			0x30, 0x31, 0x32, 0x33, 0x34, 0x35, 0x36, 0x37, 0x38, 0x39, 0x3A, 0x3B, 0x3C }; // 黑桃

	/**
	 * 用16进制表示牌张，方便计算
	 */
	private List<Byte> cards = null;

	/**
	 * 是否带有大小王
	 */
	private boolean isJoker = true;

	public Poker(boolean joker) {
		this.isJoker = joker;
		this.cards = new ArrayList<>();
		this.resetPoker();
	}

	/**
	 * 重置牌
	 */
	public void resetPoker() {
		this.cards.clear(); // 先移除所有的牌
		this.cards = new ArrayList<Byte>(Arrays.asList((this.isJoker ? CARDS_WITH_JOKER : CARDS_WITHOUT_JOKER)));
	}

	/**
	 * 打乱牌(洗牌)
	 */
	public void shufflePoker() {
		this.resetPoker(); // 先重置一下牌
		Collections.shuffle(this.cards);
	}

	/**
	 * 切牌,把切下来的牌放到下面
	 * 
	 * @param num
	 *            切牌的数量
	 * @throws Exception
	 */
	public void cutPoker(int num) {
		if (num > this.cards.size()) {
			return;
		}
		while (num-- >= 0) {
			Byte b = this.cards.remove(this.cards.size() - 1);
			this.cards.add(0, b);
		}
	}

	/**
	 * 下张牌
	 * 
	 * @return
	 */
	public byte nextCard() {
		Byte b = this.cards.remove(this.cards.size() - 1);
		return b.byteValue();
	}

	/**
	 * 得到牌的花色
	 * 
	 * @param b
	 * @return
	 */
	public int getColor(byte b) {
		return b >> 0x04;
	}

	public int getLitter(byte b) {
		return b % 16;
	}

	@Override
	public String toString() {
		String result = "";
		for (Byte b : cards)
			result += (b + ",");
		return result;
	}

	public List<Byte> getCards() {
		return cards;
	}

	public void setCards(List<Byte> cards) {
		this.cards = cards;
	}

	public boolean isJoker() {
		return isJoker;
	}

	public void setJoker(boolean isJoker) {
		this.isJoker = isJoker;
	}

	public static void main(String[] args) {
		// Poker poker = new Poker(false);
		// System.out.println("新牌:" + poker.toString());
		// poker.shufflePoker();
		// System.out.println("洗牌:" + poker.toString());
		// poker.cutCard(20);
		// System.out.println("切牌:" + poker.toString());
		System.out.println(0x02 >> 0x04);
		System.out.println(0x23 >> 0x04);
		System.out.println(0xa2 % 16);

	}
}
