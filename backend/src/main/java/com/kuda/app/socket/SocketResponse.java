package com.kuda.app.socket;


public class SocketResponse {

	private String requestId;
	private String content;
	private Long delay;

	public SocketResponse(String requestId, String content, Long delay) {

		super();
		this.requestId = requestId;
		this.content = content;
		this.delay = delay;
	}

	public Long getDelay() {

		return delay;
	}

	public void setDelay(Long delay) {

		this.delay = delay;
	}

	public SocketResponse() {}

	public String getRequestId() {

		return requestId;
	}

	public void setRequestId(String requestId) {

		this.requestId = requestId;
	}

	public String getContent() {

		return content;
	}

	public void setContent(String content) {

		this.content = content;
	}
}
