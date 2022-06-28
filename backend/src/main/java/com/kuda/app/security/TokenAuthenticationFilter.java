package com.kuda.app.security;

import static java.util.Optional.ofNullable;

import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kuda.app.exception.Errors;
import com.kuda.app.exception.KudaException;
import com.kuda.app.exception.Resources;
import com.kuda.app.security.impl.AuthenticatedUser;
import com.kuda.app.security.impl.NotAuthenticatedUser;

@Component
public class TokenAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

	public static final String BEARER = "Bearer";

	@Autowired
	private JWTUtil jwtUtil;

	public TokenAuthenticationFilter(final RequestMatcher requiresAuth) {

		super(requiresAuth);
	}

	@Override
	@Autowired
	public void setAuthenticationManager(AuthenticationManager authenticationManager) {
		super.setAuthenticationManager(authenticationManager);
	}

	@Override
	public Authentication attemptAuthentication(final HttpServletRequest request, final HttpServletResponse response) {

		final String param = ofNullable(request.getHeader(HttpHeaders.AUTHORIZATION))
				.orElse(request.getParameter("t"));

		try {
			final String token = ofNullable(param).map(value -> value.replace(BEARER, "")).map(String::trim)
					.orElseThrow(() -> new KudaException(Errors.ACCESS_DENIED.getName(),
							HttpStatus.FORBIDDEN, Resources.USER.getName(), "601"));

			if (jwtUtil.verify(token)) {
				AuthenticatedUser baseAuthentication = new AuthenticatedUser(jwtUtil.getUsername(token));

				return new UsernamePasswordAuthenticationToken(baseAuthentication.getPrincipal(),
						baseAuthentication.getCredentials(), getAuthorities(jwtUtil.getRoles(token, "roles")));
			} else {
				return new NotAuthenticatedUser();
			}

		} catch (Exception ex) {
			return new NotAuthenticatedUser();
		}
	}

	private List<GrantedAuthority> getAuthorities(List<String> roles) {
		List<GrantedAuthority> auths = new ArrayList<>();
		if (!roles.isEmpty()) {
			for (String r : roles) {
				auths.add(new SimpleGrantedAuthority(r));
			}
		}
		return auths;
	}

	@Override
	protected void successfulAuthentication(final HttpServletRequest request, final HttpServletResponse response,
			final FilterChain chain, final Authentication authResult) throws IOException,
			ServletException {

		if (authResult instanceof NotAuthenticatedUser) {
			response.setStatus(HttpStatus.FORBIDDEN.value());
			response.setContentType(MediaType.APPLICATION_JSON_VALUE);
			response.getWriter().print(new ObjectMapper().writeValueAsString(getFailedAuthenticationMap()));
			response.getWriter().flush();
			response.flushBuffer();
		} else {

			SecurityContextHolder.getContext().setAuthentication(authResult);
			chain.doFilter(request, response);
		}
	}

	private Map<String, Object> getFailedAuthenticationMap() {

		Map<String, Object> map = new HashMap<>();

		map.put("message", "access.denied");
		map.put("timestamp", Instant.now().toEpochMilli());
		map.put("resource", "Auth");

		return map;
	}
}
