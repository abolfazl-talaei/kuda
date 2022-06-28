package com.kuda.app.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.security.RolesAllowed;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.kuda.app.constants.Keys;
import com.kuda.app.dto.NewKudaRequest;
import com.kuda.app.dto.response.DefaultSuccessResponse;
import com.kuda.app.dto.response.KudaInfo;
import com.kuda.app.enums.KudaRole;
import com.kuda.app.exception.KudaException;
import com.kuda.app.repository.dto.IterationUserRank;
import com.kuda.app.repository.dto.KudaDefaultFeedback;
import com.kuda.app.security.JWTUtil;
import com.kuda.app.service.kuda.KudaService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/api/kuda")
@RequiredArgsConstructor
public class KudaController {

	private final KudaService kudaService;
	private final JWTUtil jwtUtil;

	@GetMapping(value = "/feedback/type")
	@RolesAllowed({ KudaRole.ADMIN, KudaRole.DEFAULT, KudaRole.READER })
	public @ResponseBody List<KudaDefaultFeedback> getKudaFeedbackTypes(
			@RequestHeader(value = HttpHeaders.AUTHORIZATION, required = true) String token) throws KudaException {

		return kudaService.getFeedbackTypes(jwtUtil.getUsername(token));
	}

	@GetMapping(value = "/duration")
	@RolesAllowed({ KudaRole.ADMIN, KudaRole.DEFAULT, KudaRole.READER })
	public @ResponseBody Map<String, Object> getKudaDuration(
			@RequestHeader(value = HttpHeaders.AUTHORIZATION, required = true) String token) throws KudaException {

		return kudaService.getKudaDurationMap(jwtUtil.getUsername(token));
	}

	@PostMapping(value = "/show/change")
	@RolesAllowed({ KudaRole.ADMIN, KudaRole.READER })
	public @ResponseBody DefaultSuccessResponse changeShowKudas(
			@RequestHeader(value = HttpHeaders.AUTHORIZATION, required = true) String token,
			@RequestParam(Keys.STATUS) Boolean status)
			throws KudaException {

		kudaService.changeShowStatus(jwtUtil.getUsername(token), status);
		return DefaultSuccessResponse.defaultResponse();
	}

	@GetMapping(value = "/winner")
	@RolesAllowed({ KudaRole.ADMIN, KudaRole.DEFAULT, KudaRole.READER })
	public @ResponseBody List<IterationUserRank> getWinnersInfo(
			@RequestHeader(value = HttpHeaders.AUTHORIZATION, required = true) String token,
			@RequestParam(Keys.DURATION) Integer duration) throws KudaException {

		return kudaService.getIterationKudaRanks(jwtUtil.getUsername(token), duration);
	}

	@PostMapping(value = "/read/change")
	@RolesAllowed({ KudaRole.ADMIN, KudaRole.READER })
	@ResponseStatus(value = HttpStatus.OK)
	public @ResponseBody KudaInfo chnageKudaReadStatus(@RequestParam(Keys.ID) Long id) throws KudaException {

		return kudaService.chnageKudaReadStatus(id);
	}

	@GetMapping
	@RolesAllowed({ KudaRole.ADMIN, KudaRole.DEFAULT, KudaRole.READER })
	public @ResponseBody List<KudaInfo> getKudas(
			@RequestHeader(value = HttpHeaders.AUTHORIZATION, required = true) String token) throws KudaException {

		return kudaService.getKudas(jwtUtil.getUsername(token));
	}

	@PostMapping
	@RolesAllowed({ KudaRole.ADMIN, KudaRole.DEFAULT, KudaRole.READER })
	public @ResponseBody KudaInfo createKuda(
			@RequestHeader(value = HttpHeaders.AUTHORIZATION, required = true) String token,
			@RequestBody NewKudaRequest request) throws KudaException {

		return kudaService.createKuda(jwtUtil.getUsername(token), request);
	}
}
