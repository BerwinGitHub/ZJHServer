package com.mjoys.zjh.domain;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.ericsoft.bmob.bson.BSONObject;
import com.google.protobuf.GeneratedMessageV3;

public abstract class IBPEntity<PE extends GeneratedMessageV3> {

	public IBPEntity() {
	}

	public IBPEntity(byte[] bs) {
		try {
			this.toEntity(bs);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public byte[] toByteArray() {
		ParameterizedType parameterizedType = (ParameterizedType) this.getClass().getGenericSuperclass();
		@SuppressWarnings("unchecked")
		Class<PE> cls = (Class<PE>) (parameterizedType.getActualTypeArguments()[0]);
		PE pe = null;
		try {
			pe = this.toPBEntity(this, cls);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return pe.toByteArray();
	}

	@SuppressWarnings("unchecked")
	protected PE toPBEntity(Object e, Class<?> cls) throws Exception {
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
								pos.add(this.toPBEntity(object, Class.forName(clsName)));
							}
							pf.set(b, pos);
						} else if (pf.getGenericType().toString().indexOf("com.mjoys.zjh.proto.Protobufs") != -1) { // 如果是包含的另外一个Message
							pf.set(b, this.toPBEntity(value, pf.getType()));
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
		return (PE) b.build();
	}

	public void toEntity(byte[] bs) {
		ParameterizedType parameterizedType = (ParameterizedType) this.getClass().getGenericSuperclass();
		@SuppressWarnings("unchecked")
		Class<PE> cls = (Class<PE>) (parameterizedType.getActualTypeArguments()[0]);
		try {
			this.toEntity(cls, this, bs);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected Object toEntity(Class<?> pcls, Object o, byte[] bs) throws Exception {
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
							@SuppressWarnings("unchecked")
							List<Object> pos = (List<Object>) value; // proto
							List<Object> eos = new ArrayList<>();
							ParameterizedType pt = (ParameterizedType) f.getGenericType();// 获取当前new对象的泛型的父类类型
							String clsName = pt.getActualTypeArguments()[0].getTypeName();
							for (Object po : pos) {
								eos.add(toEntity(po.getClass(), Class.forName(clsName).newInstance(),
										((GeneratedMessageV3) po).toByteArray()));
							}
							f.set(o, eos);
						} else if (pf.getGenericType().toString().indexOf("com.mjoys.zjh.proto.Protobufs") != -1) { // 如果是包含的另外一个Message
							// 如果有Message对象
							f.set(o, toEntity(pf.getType(), f.getType().newInstance(),
									((GeneratedMessageV3) value).toByteArray()));
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
		return o;
	}

	/**
	 * 这几个字段是不能够被修改的
	 */
	public static final String[] DEFAULT_COLUMNS_EXCLUDE = { "objectId", "mobilePhoneNumber", "createdAt",
			"updatedAt" };

	public abstract String getTableName();

	public BSONObject toBSONObject() {
		return this.toBSONObjectWithoutDefaultColumns(new String[] {});
	};

	public BSONObject toUpdateBSONObject() {
		return this.toBSONObjectWithoutDefaultColumns(DEFAULT_COLUMNS_EXCLUDE);
	};

	private BSONObject toBSONObjectWithoutDefaultColumns(String[] exclude) {
		BSONObject bson = new BSONObject();
		Field[] fields = this.getClass().getDeclaredFields();
		for (int i = 0; i < fields.length; i++) {
			Field f = fields[i];
			boolean isExclude = false;
			for (int j = 0; j < exclude.length; j++) {
				if (f.getName().equals(exclude[j])) {
					isExclude = true;
					break;
				}
			}
			if (isExclude)
				continue;

			boolean accessible = f.isAccessible();
			f.setAccessible(true);
			Object value = null;
			try {
				value = f.get(this);
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (value != null)
				bson.put(f.getName(), value);
			f.setAccessible(accessible);
		}
		return bson;
	};

}
