package com.kuda.app.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import com.kuda.app.constants.Keys;
import com.kuda.app.socket.SocketRequest;
import com.kuda.app.socket.SocketResponse;

@Controller
public class WebsocketController {

	@MessageMapping("/socketendpoint")
	@SendTo("/kudabroker/commands")
	public SocketResponse greeting(SocketRequest message) {

		if (message != null && Keys.REFRESH.equals(message.getName())) {
			return new SocketResponse(message.getRequestId(), Keys.REFRESH, message.getDelay());
		}
		return new SocketResponse(message != null ? message.getRequestId() : "-1", Keys.NOPE,
				message != null ? message.getDelay() : 0);
	}

}
