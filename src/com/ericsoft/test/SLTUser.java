package com.ericsoft.test;

public class SLTUser implements Comparable<SLTUser> {

	private int id;

	private String name;

	public SLTUser() {
	}

	public SLTUser(int id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public int compareTo(SLTUser o) {
		return id - o.getId();
	}
}
