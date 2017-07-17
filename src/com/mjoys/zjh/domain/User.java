package com.mjoys.zjh.domain;

import java.util.Date;

/**
 * 请保证属性名和数据库字段名字一致
 * 
 * @author t_Ber
 *
 */
public class User extends IBmobObject {

	private String objectId = null;

	private String username = null;

	private boolean mobilePhoneNumberVerified;

	private String mobilePhoneNumber = null;

	private int totalInning;

	private int winInning;

	private int diamond;

	private int coin;

	private String deviceId = null;

	private int id;

	private String authData = null;

	private Date createdAt = null;

	private Date updatedAt = null;

	private boolean specialMode;

	public User() {
	}

	public String getObjectId() {
		return objectId;
	}

	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public boolean isMobilePhoneNumberVerified() {
		return mobilePhoneNumberVerified;
	}

	public void setMobilePhoneNumberVerified(boolean mobilePhoneNumberVerified) {
		this.mobilePhoneNumberVerified = mobilePhoneNumberVerified;
	}

	public String getMobilePhoneNumber() {
		return mobilePhoneNumber;
	}

	public void setMobilePhoneNumber(String mobilePhoneNumber) {
		this.mobilePhoneNumber = mobilePhoneNumber;
	}

	public int getTotalInning() {
		return totalInning;
	}

	public void setTotalInning(int totalInning) {
		this.totalInning = totalInning;
	}

	public int getWinInning() {
		return winInning;
	}

	public void setWinInning(int winInning) {
		this.winInning = winInning;
	}

	public int getDiamond() {
		return diamond;
	}

	public void setDiamond(int diamond) {
		this.diamond = diamond;
	}

	public int getCoin() {
		return coin;
	}

	public void setCoin(int coin) {
		this.coin = coin;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAuthData() {
		return authData;
	}

	public void setAuthData(String authData) {
		this.authData = authData;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	public boolean isSpecialMode() {
		return specialMode;
	}

	public void setSpecialMode(boolean specialMode) {
		this.specialMode = specialMode;
	}

	@Override
	public String getTableName() {
		return "_User";
	}

}
