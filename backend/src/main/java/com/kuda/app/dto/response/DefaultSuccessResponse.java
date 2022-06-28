package com.kuda.app.dto.response;


import org.springframework.web.bind.annotation.ResponseBody;


@ResponseBody
public class DefaultSuccessResponse {

	private Boolean success;

	public Boolean getSuccess() {

		return success;
	}

	public void setSuccess(Boolean success) {

		this.success = success;
	}

	public static DefaultSuccessResponse defaultResponse() {

		DefaultSuccessResponse response = new DefaultSuccessResponse();

		response.setSuccess(true);

		return response;
	}

	@Override
	public String toString() {

		return "DefaultSuccessResponse [success=" + success + "]";
	}
}
