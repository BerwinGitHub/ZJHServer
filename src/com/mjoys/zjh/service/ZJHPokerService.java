package com.mjoys.zjh.service;

import java.util.Random;

import com.mjoys.zjh.entity.Poker;

public class ZJHPokerService {

	public static final int POKER_A = 12; // A
	public static final int POKER_2 = 0; // 2
	public static final int POKER_3 = 1;// 3

	public enum CardType {
		SINGLE, // 单牌
		PAIR, // 对子
		QUEUE_A23, // 最小的顺子，奇葩
		QUEUE, // 顺子
		SAME_COLOR, // 金花
		QUEUE_SAME_COLOR_A23, // 最小的顺金，奇葩
		QUEUE_SAME_COLOR, // 顺金
		SSS; // 三同

		public String toString() {
			String[] names = new String[] { "单牌", "对子", "顺子(A23)", "顺子", "金花", "顺金(A23)", "顺金", "三同" };
			return names[this.ordinal()];
		}
	}

	private Poker poker = null;

	public ZJHPokerService() {
		this.poker = new Poker(false);
	}

	/**
	 * 洗牌
	 */
	public void shufflePoker() {
		this.poker.shufflePoker();
	}

	/**
	 * 切牌端牌
	 * 
	 * @param num
	 */
	public void randomCutPoker() {
		Random random = new Random();
		this.poker.cutPoker(random.nextInt(this.poker.getCardNumer()));
	}

	/**
	 * 下一张牌
	 * 
	 * @return
	 */
	public byte nextCard() {
		return this.poker.nextCard();
	}

	/**
	 * 
	 * @param bs1
	 * @param bs2
	 * @return >0 前者大 <0后者大 =0一样大
	 */
	public int compareCards(byte[] bs1, byte[] bs2) {
		int[] c1 = new int[] { poker.getLitter(bs1[0]), poker.getLitter(bs1[1]), poker.getLitter(bs1[2]) };
		int[] c2 = new int[] { poker.getLitter(bs2[0]), poker.getLitter(bs2[1]), poker.getLitter(bs2[2]) };
		int[] pc1 = new int[] { poker.getColor(bs1[0]), poker.getColor(bs1[1]), poker.getColor(bs1[2]) };
		int[] pc2 = new int[] { poker.getColor(bs2[0]), poker.getColor(bs2[1]), poker.getColor(bs2[2]) };
		CardType cs1 = this.geCardType(c1, pc1);
		CardType cs2 = this.geCardType(c2, pc2);
		int result = cs1.ordinal() - cs2.ordinal();
		if (result == 0) { // 牌型相同，比大小
			return this.compareSameType(c1, c2);
		}
		return result;
	}

	private int compareSameType(int[] c1, int[] c2) {
		int result = 0;
		for (int i = 2; i >= 0; i--) {
			if (c1[i] != c2[i]) { // 不相等才进行判断
				result = c1[i] - c2[i];
				return result;
			}
		}
		return result;
	}

	/**
	 * 得到牌型
	 * 
	 * @param bs
	 * @return
	 */
	private CardType geCardType(int[] is, int[] pc) {
		this.bubbleSort(is);
		if (is[0] == is[1] && is[1] == is[2]) { // 三个一样
			return CardType.SSS;
		} else if (pc[0] == pc[1] && pc[1] == pc[2]) { // 三个同色
			// 再看是不是顺
			if (is[2] - is[1] == 1 && is[1] - is[0] == 1) { // 连续的三张牌/A23
				return CardType.QUEUE_SAME_COLOR; // 顺金
			} else if (is[2] == POKER_A && is[0] == POKER_2 && is[1] == POKER_3) {
				return CardType.QUEUE_SAME_COLOR_A23;// 特殊顺金A23
			}
			return CardType.SAME_COLOR; // 金花
		} else if (is[2] - is[1] == 1 && is[1] - is[0] == 1) { // 连续的三张牌/A23
			return CardType.QUEUE;// 顺子
		} else if (is[2] == POKER_A && is[0] == POKER_2 && is[1] == POKER_3) {// 特殊顺子A23
			return CardType.QUEUE_A23;
		} else if (is[0] == is[1] || is[1] == is[2] || is[2] == is[0]) {
			return CardType.PAIR;// 对子
		}
		return CardType.SINGLE;// 单牌
	}

	public void bubbleSort(int a[]) {
		int n = a.length;
		for (int i = 0; i < n - 1; i++) {
			for (int j = 0; j < n - 1; j++) {
				if (a[j] > a[j + 1]) {
					a[j] = a[j] + a[j + 1];
					a[j + 1] = a[j] - a[j + 1];
					a[j] = a[j] - a[j + 1];
				}
			}
		}
	}

	public String toCardString(byte[] bs) {
		String str = "";
		String colors[] = new String[] { "方块", "樱花", "红心", "黑桃" };
		String numbers[] = new String[] { "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "A" };
		for (byte b : bs) {
			int c = poker.getColor(b);
			str += colors[c] + ":" + numbers[poker.getLitter(b)] + "\t";
		}
		int[] c1 = new int[] { poker.getLitter(bs[0]), poker.getLitter(bs[1]), poker.getLitter(bs[2]) };
		int[] pc1 = new int[] { poker.getColor(bs[0]), poker.getColor(bs[1]), poker.getColor(bs[2]) };
		str += "-" + this.geCardType(c1, pc1).toString();
		return str;
	}

	public static void main(String[] args) {
		// Byte[] CARDS_WITHOUT_JOKER = new Byte[] { // 52张牌
		// 0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08, 0x09, 0x0A,
		// 0x0B, 0x0C, // 方块
		// 0x10, 0x11, 0x12, 0x13, 0x14, 0x15, 0x16, 0x17, 0x18, 0x19, 0x1A,
		// 0x1B, 0x1C, // 樱花
		// 0x20, 0x21, 0x22, 0x23, 0x24, 0x25, 0x26, 0x27, 0x28, 0x29, 0x2A,
		// 0x2B, 0x2C, // 红心
		// 0x30, 0x31, 0x32, 0x33, 0x34, 0x35, 0x36, 0x37, 0x38, 0x39, 0x3A,
		// 0x3B, 0x3C }; // 黑桃
		// String keys[] = new String[] { //
		// "FK-A", "FK-2", "FK-3", "FK-4", "FK-5", "FK-6", "FK-7", "FK-8",
		// "FK-9", "FK-10", "FK-J", "FK-Q", "FK-K", //
		// "YH-A", "YH-2", "YH-3", "YH-4", "YH-5", "YH-6", "YH-7", "YH-8",
		// "YH-9", "YH-10", "YH-J", "YH-Q", "YH-K", //
		// "HX-A", "HX-2", "HX-3", "HX-4", "HX-5", "HX-6", "HX-7", "HX-8",
		// "HX-9", "HX-10", "HX-J", "HX-Q", "HX-K", //
		// "HT-A", "HT-2", "HT-3", "HT-4", "HT-5", "HT-6", "HT-7", "HT-8",
		// "HT-9", "HT-10", "HT-J", "HT-Q", "HT-K"//
		// };
		// Map<String, Byte> card = new HashMap<>();
		// for (int j = 0; j < keys.length; j++) {
		// card.put(keys[j], CARDS_WITHOUT_JOKER[j]);
		// }

		ZJHPokerService zjh = new ZJHPokerService();
		// for (int i = 0; i < 50; i++) {
		// zjh.poker.shufflePoker();
		// zjh.poker.cutPoker(20);
		// byte[] bs1 = new byte[] { zjh.poker.nextCard(),
		// zjh.poker.nextCard(), zjh.poker.nextCard() };
		// byte[] bs2 = new byte[] { zjh.poker.nextCard(),
		// zjh.poker.nextCard(), zjh.poker.nextCard() };
		// int res = zjh.compareCards(bs1, bs2);
		// System.out.println(zjh.toCardString(bs1) + (res > 0 ? "\t-大" : ""));
		// System.out.println(zjh.toCardString(bs2) + (res < 0 ? "\t-大" : ""));
		// System.out.println();
		// }

		byte[] bs1 = new byte[] { 0x01, 0x02, 0x03 };
		byte[] bs2 = new byte[] { 0x1C, 0x10, 0x11 };
		int res = zjh.compareCards(bs1, bs2);
		System.out.println(zjh.toCardString(bs1) + (res > 0 ? "\t-大" : ""));
		System.out.println(zjh.toCardString(bs2) + (res < 0 ? "\t-大" : ""));

		// sim();
		// sim();
		// sim();
	}

	public static void sim() {
		long total = 1000000;
		int players = 3;
		float parts[] = new float[] { 0, 0, 0, 0, 0, 0 };
		String[] names = new String[] { "单牌", "对子", "顺子", "金花", "顺金", "三同" };
		ZJHPokerService zjh = new ZJHPokerService();
		for (int i = 0; i < total; i++) {
			zjh.poker.shufflePoker();
			zjh.poker.cutPoker(20);
			byte[][] bs = new byte[players][3];
			for (int j = 0; j < 3; j++) {
				for (int k = 0; k < bs.length; k++) {
					bs[k][j] = zjh.poker.nextCard();
				}
			}
			for (int j = 0; j < bs.length; j++) {
				int[] c1 = new int[] { zjh.poker.getLitter(bs[j][0]), zjh.poker.getLitter(bs[j][1]),
						zjh.poker.getLitter(bs[j][2]) };
				int[] pc1 = new int[] { zjh.poker.getColor(bs[j][0]), zjh.poker.getColor(bs[j][1]),
						zjh.poker.getColor(bs[j][2]) };
				int t = zjh.geCardType(c1, pc1).ordinal();
				parts[t]++;
			}
		}
		total = total * players;
		for (int i = 0; i < parts.length; i++) {
			if (parts[i] != 0)
				System.out.println(names[i] + "-次数:" + parts[i] + "\t\t概率:" + (parts[i] / total));
		}
		System.out.println();
	}

}
