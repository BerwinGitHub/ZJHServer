package com.ericsoft.test;

import com.ericsoft.bmob.restapi.Bmob;
import com.mjoys.zjh.domain.User;
import com.mjoys.zjh.service.UserService;

public class UnitTest {

	public static final String APPLICATION_ID = "1760eafcc521aa8814d87623e7e04a77";
	public static final String REST_API_KEY = "ef622f1a6c10c3d711aa9d0c12dd5c7a";
	public static final String MASKTER_KEY = "064bf73ed9f00b0c9274572fee71981f";

	public static void main(String[] args) {
		Bmob.setTimeout(10 * 1000);
		Bmob.initBmob(APPLICATION_ID, REST_API_KEY);
		Bmob.initMaster(MASKTER_KEY); // 更新需要这个权限
		testUserService();
	}

	public static void testUserService() {
		UserService userService = new UserService();
		User user = userService.queryByDeviceID("BF35095B-4003-4AF2-BF2E-5B2EBA6BA732");
		// user.setDiamond(1001);
		// userService.update(user);
		// delete
		userService.delete(user);
	}

}
