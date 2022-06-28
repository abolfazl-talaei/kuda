package com.kuda.app.exception;

public enum Errors {

	INVALID("invalid"),
	INTERNAL_ERROR("internal.error"),
	EXISTED_USERNAME("existed.username"),
	ACCESS_DENIED("access.denied"),
	NOT_FOUND("not.found"),
	DUPLICATE("duplicate"),

	;

	private Errors(String name) {

		this.name = name;
	}

	private String name;

	public String getName() {

		return name;
	}
}
