package com.mjoys.zjh.domain;

public class User {

	private int id;

	private String deviceId;

	private String userName;

	private String passWord;

	private int coin;

	private int diamond;

	private boolean mobilePhoneNumberVer;

	private boolean mobilePhoneNumber;

	public User() {
	}

	public User(int id, String deviceId, String userName, String passWord,
			int coin, int diamond, boolean mobilePhoneNumberVer,
			boolean mobilePhoneNumber) {
		super();
		this.id = id;
		this.deviceId = deviceId;
		this.userName = userName;
		this.passWord = passWord;
		this.coin = coin;
		this.diamond = diamond;
		this.mobilePhoneNumberVer = mobilePhoneNumberVer;
		this.mobilePhoneNumber = mobilePhoneNumber;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassWord() {
		return passWord;
	}

	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}

	public boolean isMobilePhoneNumberVer() {
		return mobilePhoneNumberVer;
	}

	public void setMobilePhoneNumberVer(boolean mobilePhoneNumberVer) {
		this.mobilePhoneNumberVer = mobilePhoneNumberVer;
	}

	public boolean isMobilePhoneNumber() {
		return mobilePhoneNumber;
	}

	public void setMobilePhoneNumber(boolean mobilePhoneNumber) {
		this.mobilePhoneNumber = mobilePhoneNumber;
	}

	public int getCoin() {
		return coin;
	}

	public void setCoin(int coin) {
		this.coin = coin;
	}

	public int getDiamond() {
		return diamond;
	}

	public void setDiamond(int diamond) {
		this.diamond = diamond;
	}

}
