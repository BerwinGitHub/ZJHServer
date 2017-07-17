package com.mjoys.zjh.service;

import com.mjoys.zjh.domain.IBmobObject;

public abstract class IService<E extends IBmobObject> {

	public abstract void insert(E e);

	public abstract void delete(E e);

	public abstract E queryByDeviceID(String deviceID);

	public abstract void update(E e);

	public abstract E toBean(String result);

}
