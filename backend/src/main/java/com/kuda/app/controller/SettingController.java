package com.kuda.app.controller;

import java.util.List;

import javax.annotation.security.RolesAllowed;

import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kuda.app.constants.Keys;
import com.kuda.app.dto.response.SettingInfo;
import com.kuda.app.enums.KudaRole;
import com.kuda.app.exception.KudaException;
import com.kuda.app.security.JWTUtil;
import com.kuda.app.service.setting.SettingService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/api/setting")
@RequiredArgsConstructor
public class SettingController {

	private final SettingService settingService;
	private final JWTUtil jwtUtil;

	@GetMapping
	@RolesAllowed({ KudaRole.ADMIN })
	public @ResponseBody List<SettingInfo> getSettings(@RequestHeader(value = HttpHeaders.AUTHORIZATION) String token)
			throws KudaException {

		return settingService.get(jwtUtil.getUsername(token));
	}

	@PutMapping
	@RolesAllowed({ KudaRole.ADMIN })
	public @ResponseBody SettingInfo updateSetting(@RequestParam(Keys.ID) final Long id,
			@RequestParam(Keys.VALUE) final String value) throws KudaException {

		return settingService.set(id, value);
	}

	@PostMapping
	@RolesAllowed({ KudaRole.ADMIN })
	public @ResponseBody SettingInfo createSetting(@RequestHeader(value = HttpHeaders.AUTHORIZATION) String token,
			@RequestParam(Keys.KEY) final String key,
			@RequestParam(Keys.TAG) final String tag, @RequestParam(Keys.VALUE) final String value)
			throws KudaException {

		return settingService.create(jwtUtil.getUsername(token), key, tag, value);
	}

	@DeleteMapping
	@RolesAllowed({ KudaRole.ADMIN })
	public @ResponseBody SettingInfo deleteSetting(@RequestParam(Keys.ID) final Long id) throws KudaException {

		return settingService.delete(id);
	}
}
