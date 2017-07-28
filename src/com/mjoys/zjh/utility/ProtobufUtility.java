package com.mjoys.zjh.utility;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

import com.google.protobuf.GeneratedMessageV3;
import com.mjoys.zjh.domain.User;
import com.mjoys.zjh.entity.Seat;
import com.mjoys.zjh.entity.Table;
import com.mjoys.zjh.proto.Protobufs;

import net.sf.json.JSONObject;

public class ProtobufUtility {

	@SuppressWarnings("rawtypes")
	public static byte[] toBytes(String msg) {
		JSONObject jo = JSONObject.fromObject(msg);
		LinkedHashMap map = (LinkedHashMap) JSONObject.toBean(jo, LinkedHashMap.class);
		byte[] bytes = new byte[map.size()];
		int i = 0;
		for (Iterator iterator = map.values().iterator(); iterator.hasNext(); i++) {
			Integer v = (Integer) iterator.next();
			bytes[i] = v.byteValue();
		}
		return bytes;
	}

	public static String stringify(byte[] bs) {
		String result = "";
		if (bs == null)
			return result;
		for (int i = 0; i < bs.length; i++)
			result += (bs[i] + (i != bs.length - 1 ? "," : ""));
		return result;
	}

	@SuppressWarnings("unchecked")
	public static <P> P toPBEntity(Object e, Class<P> cls) throws Exception {
		Method method = cls.getMethod("newBuilder", null);
		com.google.protobuf.GeneratedMessageV3.Builder<?> b = (com.google.protobuf.GeneratedMessageV3.Builder<?>) method
				.invoke(null, null);
		// 开始进行赋值操作
		Field[] fields = e.getClass().getDeclaredFields();
		Field[] pFields = b.getClass().getDeclaredFields();
		for (int i = 0; i < fields.length; i++) {
			Field f = fields[i];
			for (int j = 0; j < pFields.length; j++) {
				Field pf = pFields[j];
				if ((f.getName() + "_").equals(pf.getName())) { // 可以赋值，PROTO后面加了个下划线
					boolean accessible = f.isAccessible();
					boolean pAccessible = pf.isAccessible();
					f.setAccessible(true);
					pf.setAccessible(true);
					Object value = null;
					Object pv = null;
					try {
						value = f.get(e);
						pv = pf.get(b);
						if (value != null && value instanceof Date) { // 日期类型处理
							pf.set(b, ((Date) value).getTime());
						} else if (pv instanceof List<?>) { // 如果是List,放在下面判断的前面
							List<Object> eos = (List<Object>) value;
							List<Object> pos = new ArrayList<>();
							ParameterizedType pt = (ParameterizedType) pf.getGenericType();// 获取当前new对象的泛型的父类类型
							String clsName = pt.getActualTypeArguments()[0].getTypeName();
							for (Object object : eos) {
								pos.add(toPBEntity(object, Class.forName(clsName)));
							}
							pf.set(b, pos);
						} else if (pf.getGenericType().toString().indexOf("com.mjoys.zjh.proto.Protobufs") != -1) { // 如果是包含的另外一个Message
							pf.set(b, toPBEntity(value, pf.getType()));
						} else if (value != null) {
							pf.set(b, value);
						}
					} catch (Exception ex) {
						ex.printStackTrace();
					}

					f.setAccessible(accessible);
					pf.setAccessible(pAccessible);
					break;
				}
			}
		}
		return (P) b.build();
	}

	public static <E> E toEntity(Class<?> pcls, Class<E> ecls, byte[] bs) throws Exception {
		Object o = ecls.newInstance();
		Method method = pcls.getMethod("parseFrom", byte[].class);
		GeneratedMessageV3 pe = (GeneratedMessageV3) method.invoke(null, bs);
		// 赋值
		Field[] fields = o.getClass().getDeclaredFields();
		Field[] pFields = pe.getClass().getDeclaredFields();
		for (int i = 0; i < fields.length; i++) {
			Field f = fields[i];
			for (int j = 0; j < pFields.length; j++) {
				Field pf = pFields[j];
				if ((f.getName() + "_").equals(pf.getName())) { // 可以赋值，PROTO后面加了个下划线
					boolean accessible = f.isAccessible();
					boolean pAccessible = pf.isAccessible();
					f.setAccessible(true);
					pf.setAccessible(true);
					Object value = null;
					try {
						value = pf.get(pe); // Protobuf的value
						if (value != null && f.getGenericType().toString().equals("class java.util.Date")) {
							f.set(o, new Date((long) value));
						} else if (value instanceof List<?>) { // 如果是List,放在下面判断的前面
							List<Object> pos = (List<Object>) value; // proto
							List<Object> eos = new ArrayList<>();
							ParameterizedType pt = (ParameterizedType) f.getGenericType();// 获取当前new对象的泛型的父类类型
							String clsName = pt.getActualTypeArguments()[0].getTypeName();
							for (Object po : pos) {
								eos.add(toEntity(po.getClass(), Class.forName(clsName),
										((GeneratedMessageV3) po).toByteArray()));
							}
							f.set(o, eos);
						} else if (pf.getGenericType().toString().indexOf("com.mjoys.zjh.proto.Protobufs") != -1) { // 如果是包含的另外一个Message
							// 如果有Message对象
							f.set(o, toEntity(pf.getType(), f.getType(), ((GeneratedMessageV3) value).toByteArray()));
						} else if (value != null) {
							f.set(o, value);
						}
					} catch (Exception e) {
						e.printStackTrace();
					}

					f.setAccessible(accessible);
					pf.setAccessible(pAccessible);
					break;
				}
			}
		}
		return (E) o;
	}

	public static void main(String[] args) {
		// Table table = new Table(00001);
		// table.setMinBet(100);
		// table.setMaxBet(1000);
		// table.setRound(1);
		// for (int i = 0; i < 3; i++) {
		// User user = new User();
		// user.setObjectId("oei9381qf" + i);
		// user.setId(10000 + i);
		// user.setUsername("Berwin" + i);
		// user.setUpdatedAt(new Date(System.currentTimeMillis()));
		// user.setCreatedAt(new Date(System.currentTimeMillis()));
		// Seat seat = new Seat(1000 + i, user, null);
		// table.getSeats().add(seat);
		// }
		// try {
		// byte[] bs = table.toByteArray();
		// for (byte b : bs) {
		// System.out.print(b + ",");
		// }
		// System.out.println();
		// Table table2 = new Table(bs);
		// System.out.println();
		// } catch (Exception e) {
		// e.printStackTrace();
		// }
		Protobufs.Seat.Builder builder = Protobufs.Seat.newBuilder();
		builder.setSeatID(1);
		builder.setUser(Protobufs.User.newBuilder().build());
		byte[] bs = builder.build().toByteArray();
		System.out.println();
	}

}
