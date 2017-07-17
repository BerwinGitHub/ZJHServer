package com.mjoys.zjh.domain;

import java.lang.reflect.Field;

import com.ericsoft.bmob.bson.BSONObject;

public abstract class IBmobObject {

	public abstract String getTableName();

	/**
	 * 这几个字段是不能够被修改的
	 */
	public static final String[] DEFAULT_COLUMNS_EXCLUDE = { "objectId", "mobilePhoneNumber", "createdAt",
			"updatedAt" };

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
