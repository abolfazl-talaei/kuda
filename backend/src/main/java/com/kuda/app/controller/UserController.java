package com.kuda.app.controller;

import java.util.List;

import javax.annotation.security.RolesAllowed;

import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kuda.app.constants.Keys;
import com.kuda.app.dto.response.UserInfo;
import com.kuda.app.enums.KudaRole;
import com.kuda.app.exception.KudaException;
import com.kuda.app.security.JWTUtil;
import com.kuda.app.service.user.UserService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

	private final UserService userService;
	private final JWTUtil jwtUtil;

	@GetMapping
	public @ResponseBody List<UserInfo> getUsers(@RequestHeader(value = HttpHeaders.AUTHORIZATION) String token)
			throws KudaException {

		return userService.getUsers(jwtUtil.getUsername(token));
	}

	@PostMapping
	@RolesAllowed({ KudaRole.ADMIN })
	public @ResponseBody UserInfo createUser(@RequestHeader(value = HttpHeaders.AUTHORIZATION) String token,
			@RequestParam(Keys.USERNAME) final String username, @RequestParam(Keys.NICKNAME) final String nickname)
			throws Exception {

		return userService.createUser(jwtUtil.getUsername(token), username, nickname);
	}
}
