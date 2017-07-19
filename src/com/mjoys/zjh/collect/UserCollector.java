package com.mjoys.zjh.collect;

import java.util.ArrayList;
import java.util.List;

import com.mjoys.zjh.domain.User;

/**
 * 在线用户集合
 * 
 * @author t_Ber
 *
 */
public class UserCollector {

	public static UserCollector instance = null;

	private List<User> users;

	private UserCollector() {
		this.users = new ArrayList<>();
	}

	public synchronized static UserCollector getInstance() {
		if (instance == null) {
			instance = new UserCollector();
		}
		return instance;
	}

	public List<User> getTables() {
		return users;
	}

	public void setTables(List<User> users) {
		this.users = users;
	}

	public synchronized void addUser(User u) {
		users.add(u);
	}

	public synchronized void removeUser(User u) {
		for (int i = 0; i < users.size(); i++) {
			User user = users.get(i);
			if (user.getObjectId().equals(u.getObjectId())) {
				users.remove(i);
				return;
			}
		}
	}

}
