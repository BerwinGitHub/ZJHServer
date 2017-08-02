package com.mjoys.zjh.service;

import java.util.Date;

import com.ericsoft.bmob.bson.BSONObject;
import com.ericsoft.bmob.restapi.Bmob;
import com.mjoys.zjh.domain.User;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 
 * @author t_Ber
 * 
 */
public class UserService extends IService<User> {

	public UserService() {
	}

	@Override
	public void insert(User e) {
		BSONObject bson = e.toBSONObject();
		Bmob.insert(e.getTableName(), bson.toString());
	}

	@Override
	public void delete(User e) {
		Bmob.delete(e.getTableName(), e.getObjectId());
	}

	@Override
	public User queryByDeviceID(String deviceID) {
		// 连不上服务器就写死
		boolean localData = true;
		if (localData) {
			User user = new User();
			if (deviceID.equals("BF35095B-4003-4AF2-BF2E-5B2EBA6BA748")) { // 手机
				user.setObjectId("e7e86374f3");
				user.setUsername("Berwin");
				user.setMobilePhoneNumberVerified(false);
				user.setMobilePhoneNumber("18200387036");
				user.setWinInning(0);
				user.setPlacement(false);
				user.setId(1000000);
				user.setHeaderUrl("https://ss0.baidu.com/6ONWsjip0QIZ8tyhnq/it/u=930562350,2384138428&fm=58");
				user.setDiamond(1001);
				user.setDeviceId("BF35095B-4003-4AF2-BF2E-5B2EBA6BA748");
				user.setCoin(2047);
				user.setUpdatedAt(new Date());
				user.setCreatedAt(new Date());
			} else if (deviceID.equals("AFR5045B-4003-2AF2-BF2E-5B2EBA6BA748")) { // 网页
				user.setObjectId("jY6fFFFS");
				user.setUsername("Sammi");
				user.setMobilePhoneNumberVerified(false);
				user.setMobilePhoneNumber("18202815720");
				user.setWinInning(0);
				user.setPlacement(false);
				user.setId(1000001);
				user.setHeaderUrl("https://ss2.baidu.com/6ONYsjip0QIZ8tyhnq/it/u=1500729002,1758147343&fm=58");
				user.setDiamond(10);
				user.setDeviceId("AFR5045B-4003-2AF2-BF2E-5B2EBA6BA748");
				user.setCoin(1000);
				user.setUpdatedAt(new Date());
				user.setCreatedAt(new Date());
			}
			return user;
		} else {
			BSONObject condition = new BSONObject(Bmob.whereAll(deviceID));
			BSONObject where = new BSONObject();
			where.put("deviceId", condition);
			String result = Bmob.find("_User", where.toString(), "order");
			return toBean(result);
		}
	}

	@Override
	public void update(User e) {
		BSONObject bson = e.toUpdateBSONObject();
		String result = Bmob.update(e.getTableName(), e.getObjectId(), bson.toString());
		System.out.println(result);
	}

	@Override
	public User toBean(String result) {
		User user = null;
		try {
			JSONObject o1 = JSONObject.fromObject(result);
			JSONArray jsonArray = JSONArray.fromObject(o1.get("results"));
			JSONObject o2 = JSONObject.fromObject(jsonArray.get(0));
			user = (User) JSONObject.toBean(o2, User.class);
		} catch (Exception e) {
			System.err.println(result);
			return null;
		}
		return user;
	}

}
