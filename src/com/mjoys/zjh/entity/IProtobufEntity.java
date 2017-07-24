/**
 * 
 */
package com.mjoys.zjh.entity;

import java.lang.reflect.Field;
import java.util.Date;

import com.google.protobuf.InvalidProtocolBufferException;

/**
 * @author t_Ber
 * 
 */
public abstract class IProtobufEntity<PE> {

	/**
	 * proto bytes -> entity
	 * 
	 * @param bytes
	 * @throws InvalidProtocolBufferException
	 */
	public void toEntity(byte[] bytes) {
		PE pe = this.parseEntityFromProtoBytes(bytes);
		// 赋值
		Field[] fields = this.getClass().getDeclaredFields();
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
						value = pf.get(pe);
						if (value != null
								&& f.getGenericType().toString()
										.equals("class java.util.Date")) {
							f.set(this, new Date((long) value));
						} else if (value != null) {
							f.set(this, value);
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
	}

	protected abstract PE parseEntityFromProtoBytes(byte[] bytes);

	protected abstract com.google.protobuf.GeneratedMessageV3.Builder<?> getEntityProtoBuilder();

	/**
	 * entity -> bytes
	 * 
	 * @return
	 */
	public byte[] toByteArray() {
		com.google.protobuf.GeneratedMessageV3.Builder<?> builder = this
				.getEntityProtoBuilder();
		Field[] fields = this.getClass().getDeclaredFields();
		Field[] pFields = builder.getClass().getDeclaredFields();
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
						value = f.get(this);
						if (value != null && value instanceof Date) { // 日期类型处理
							pf.set(builder, ((Date) value).getTime());
						} else if (value != null) {
							pf.set(builder, value);
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
		return builder.build().toByteArray();
	}
}
