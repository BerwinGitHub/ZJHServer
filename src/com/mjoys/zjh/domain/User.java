package com.mjoys.zjh.domain;

import java.util.Date;

import com.google.protobuf.GeneratedMessageV3.Builder;
import com.mjoys.zjh.proto.Protobufs;

/**
 * 请保证属性名和数据库字段名字一致
 * 
 * @author t_Ber
 * 
 */
public class User extends IBmobObject<Protobufs.User> {

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

	private boolean placement;

	private String headerUrl = null;

	public User() {
	}

	public User(byte[] bytes) {
		this.toEntity(bytes);
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

	public boolean isPlacement() {
		return placement;
	}

	public void setPlacement(boolean placement) {
		this.placement = placement;
	}

	public String getHeaderUrl() {
		return headerUrl;
	}

	public void setHeaderUrl(String headerUrl) {
		this.headerUrl = headerUrl;
	}

	@Override
	public String getTableName() {
		return "_User";
	}

	/**
	 * 转成ProtoBufBytes
	 * 
	 * @return
	 */
	// public byte[] toProtoBufBytes() {
	// Protobufs.User.Builder builder = Protobufs.User.newBuilder();
	// Field[] fields = this.getClass().getDeclaredFields();
	// Field[] pFields = builder.getClass().getDeclaredFields();
	// for (int i = 0; i < fields.length; i++) {
	// Field f = fields[i];
	// for (int j = 0; j < pFields.length; j++) {
	// Field pf = pFields[j];
	// if ((f.getName() + "_").equals(pf.getName())) { // 可以赋值，PROTO后面加了个下划线
	// boolean accessible = f.isAccessible();
	// boolean pAccessible = pf.isAccessible();
	// f.setAccessible(true);
	// pf.setAccessible(true);
	// Object value = null;
	// try {
	// value = f.get(this);
	// if (value != null && value instanceof Date) {
	// pf.set(builder, ((Date) value).getTime());
	// } else if (value != null) {
	// pf.set(builder, value);
	// }
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	//
	// f.setAccessible(accessible);
	// pf.setAccessible(pAccessible);
	// break;
	// }
	// }
	// }
	// Protobufs.User pUser = builder.build();
	// return pUser.toByteArray();
	// }

	// private void toUser(byte[] pBytes) {
	// try {
	// Protobufs.User u = Protobufs.User.parseFrom(pBytes);
	// this.setObjectId(u.getObjectId());
	// this.setUsername(u.getUsername());
	// this.setMobilePhoneNumberVerified(u.getMobilePhoneNumberVerified());
	// this.setMobilePhoneNumber(u.getMobilePhoneNumber());
	// this.setTotalInning(u.getTotalInning());
	// this.setWinInning(u.getWinInning());
	// this.setDiamond(u.getDiamond());
	// this.setCoin(u.getCoin());
	// this.setDeviceId(u.getDeviceId());
	// this.setId(u.getId());
	// this.setAuthData(u.getAuthData());
	// this.setCreatedAt(new Date(u.getCreatedAt()));
	// this.setUpdatedAt(new Date(u.getUpdatedAt()));
	// this.setSpecialMode(u.getSpecialMode());
	// this.setHeaderUrl(u.getHeaderUrl());
	// } catch (InvalidProtocolBufferException e) {
	// System.out.println("byte -> user error");
	// e.printStackTrace();
	// }
	// }

	@Override
	protected com.mjoys.zjh.proto.Protobufs.User parseEntityFromProtoBytes(
			byte[] bytes) {
		com.mjoys.zjh.proto.Protobufs.User user = null;
		try {
			user = com.mjoys.zjh.proto.Protobufs.User.parseFrom(bytes);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return user;
	}

	@Override
	protected Builder<?> getEntityProtoBuilder() {
		return Protobufs.User.newBuilder();
	}

}
