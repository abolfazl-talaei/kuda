package com.kuda.app.dto.response;

import java.util.Date;

import com.kuda.app.enums.KudaType;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class KudaInfo {

	private Long id;
	private String description;
	private String fromUser;
	private String fromUsername;
	private Date creationTime;
	private Integer duration;
	private KudaType kudaType;
	private Boolean readStatus;
	private String requestId;
}
