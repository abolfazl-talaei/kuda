package com.kuda.app.socket;


public class SocketRequest {

	private String requestId;
	private String name;
	private String data;
	private Long delay;

	public SocketRequest() {}

	public SocketRequest(String requestId, String name, String data, Long delay) {

		super();
		this.requestId = requestId;
		this.name = name;
		this.data = data;
		this.delay = delay;
	}

	public Long getDelay() {

		return delay;
	}

	public void setDelay(Long delay) {

		this.delay = delay;
	}

	public String getRequestId() {

		return requestId;
	}

	public void setRequestId(String requestId) {

		this.requestId = requestId;
	}

	public String getName() {

		return name;
	}

	public void setName(String name) {

		this.name = name;
	}

	public String getData() {

		return data;
	}

	public void setData(String data) {

		this.data = data;
	}
}
