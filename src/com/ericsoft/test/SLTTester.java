package com.ericsoft.test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.sf.json.JSONObject;

public class SLTTester {

	/**
	 * 1、写一个单例模式例子：要求根据传入参数的不同返回不同的对象及处理结果；
	 * 例如：输入“张三”输出“你好”；输入“0-9”输出计算方式为：从0开始到输入数字的总和；输入大于1位的数字输出计算方式为：（第一位+...+最后一位）*输入数字；
	 * 2、解析JSon数据，并将数据解析为对象（解析的目标对象参考：JsonObject.java）；
	 * 3、创建1个Web项目，向web项目发送打包加密后的请求，web项目将数据接收并打印信息（打包加密参考：DataPack.java；数据发送参考：Request.java）；
	 * 3、初始化10个对象并转入集合中，对集合中的数据进行正序和倒序的排序输出（初始化的目标对象参考：OrderObject.java）；
	 */

	public SLTTester() {
		// test for 1
		String res = SLTHandleData.getInstance().hanlde("张三", "张三", "你好");
		System.out.println(res);
		int n1 = SLTHandleData.getInstance().handle(5);
		System.out.println(n1);
		int n2 = SLTHandleData.getInstance().handle(12345);
		System.out.println(n2);
		// 2
		JSONObject o = JSONObject.fromObject("{'name':'Berwin'}");
		SLTUser user = (SLTUser) JSONObject.toBean(o, SLTUser.class);
		System.out.println(user.getName());
		// 3
		// 4
		List<SLTUser> users = new ArrayList<>();
		for (int i = 0; i < 10; i++) {
			users.add(new SLTUser(i, "Name" + i));
		}
		System.out.println("-----------我是分割线----------逆序");
		Collections.reverse(users);
		for (int i = 0; i < users.size(); i++) {
			System.out.println(users.get(i).getId());
		}
		System.out.println("-----------我是分割线----------顺序");
		Collections.sort(users);
		for (int i = 0; i < users.size(); i++) {
			System.out.println(users.get(i).getId());
		}

	}

	public static void main(String[] args) {

		new SLTTester();
	}

}
