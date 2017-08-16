package com.mjoys.zjh.entity;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

public class WaitThread extends Thread {

	private CountDownLatch countDownLatch = null;

	private long endSecond;

	private boolean invalid = false;

	private boolean isTimeout = false;

	private Map<String, Object> parameters;

	public WaitThread() {
		this.invalid = false;
		this.parameters = new HashMap<>();
	}

	/**
	 * 线程等待
	 */
	public void await(long endSecond) {
		try {
			this.endSecond = endSecond;
			this.countDownLatch = new CountDownLatch(1);
			this.invalid = true;
			this.isTimeout = false;
			this.start();
			this.countDownLatch.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 结束等待
	 */
	public void ewait() {
		if (!this.invalid)// 如果已经激活了就不用激活了
			return;
		this.invalid = false;
		this.countDownLatch.countDown();
	}

	public void set(String key, Object value) {
		this.parameters.put(key, value);
	}

	public Object get(String key) {
		return this.parameters.get(key);
	}

	public boolean isTimeout() {
		return isTimeout;
	}

	public void setTimeout(boolean isTimeout) {
		this.isTimeout = isTimeout;
	}

	@Override
	public void run() {
		super.run();
		try {
			Thread.sleep(this.endSecond - System.currentTimeMillis());
			this.isTimeout = true;
			this.ewait();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		System.out.println("start");
		WaitThread wt = new WaitThread();
		wt.await(System.currentTimeMillis() + 1000 * 5);
		System.out.println("end");
	}

}
