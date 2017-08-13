package com.mjoys.zjh.entity;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

public class WaitThread extends Thread {

	private CountDownLatch countDownLatch = null;

	private int waitSecond;

	private boolean invalid = false;

	private long finishTime = 0L;

	private Map<String, Object> parameters;

	public WaitThread() {
		this.invalid = false;
		this.parameters = new HashMap<>();
	}

	/**
	 * 线程等待
	 */
	public void await(int waitSecond) {
		try {
			this.waitSecond = waitSecond;
			this.finishTime = System.currentTimeMillis() + this.waitSecond * 1000;
			this.countDownLatch = new CountDownLatch(2);
			this.start();
			this.countDownLatch.await();
			this.invalid = true;
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

	public long getFinishTime() {
		return finishTime;
	}

	public void setFinishTime(long finishTime) {
		this.finishTime = finishTime;
	}

	@Override
	public void run() {
		super.run();
		try {
			Thread.sleep(this.finishTime - System.currentTimeMillis());
			this.ewait();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
