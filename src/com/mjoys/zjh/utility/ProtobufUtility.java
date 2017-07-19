package com.mjoys.zjh.utility;

import java.util.Iterator;
import java.util.LinkedHashMap;

import com.google.protobuf.ByteString;
import com.google.protobuf.InvalidProtocolBufferException;
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

	public static byte[] unpackData(byte[] bytes) throws InvalidProtocolBufferException {
		Protobufs.TransferData td = Protobufs.TransferData.parseFrom(bytes);
		byte unpackBytes[] = td.getData().toByteArray();
		return unpackBytes;
	}

	public static byte[] unpackData(String msg) throws InvalidProtocolBufferException {
		byte[] bs = ProtobufUtility.toBytes(msg);
		return ProtobufUtility.unpackData(bs);
	}

	public static byte[] packDataToBytes(int action, byte[] bytes) {
		Protobufs.TransferData.Builder builder = Protobufs.TransferData.newBuilder();
		builder.setActionValue(action);
		builder.setData(ByteString.copyFrom(bytes));
		Protobufs.TransferData td = builder.build();
		return td.toByteArray();
	}

	public static String packDataToString(int action, byte[] bytes) {
		byte[] bs = ProtobufUtility.packDataToBytes(action, bytes);
		return new String(bs);
	}

}
