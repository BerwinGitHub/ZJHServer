package com.mjoys.zjh.service;

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
		BSONObject condition = new BSONObject(Bmob.whereAll(deviceID));
		BSONObject where = new BSONObject();
		where.put("deviceId", condition);
		String result = Bmob.find("_User", where.toString(), "order");
		return toBean(result);
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
			System.out.println(result);
			e.printStackTrace();
		}
		return user;
	}

}
