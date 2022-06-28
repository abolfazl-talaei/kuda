package com.kuda.app.repository.dto;


public class KudaDefaultFeedback {

	private String name;
	private String value;

	public KudaDefaultFeedback() {

		super();
	}

	public KudaDefaultFeedback(String name, String value) {

		super();
		this.value = value;
		this.name = name;
	}

	public static KudaDefaultFeedback of(String name, String value) {

		return new KudaDefaultFeedback(name, value);
	}

	public String getName() {

		return name;
	}

	public void setName(String name) {

		this.name = name;
	}

	public String getValue() {

		return value;
	}

	public void setValue(String value) {

		this.value = value;
	}

	@Override
	public String toString() {

		return "KudaDefaultFeedback [value=" + value + "]";
	}
}
