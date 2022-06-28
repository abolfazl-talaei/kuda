package com.kuda.app.security;


import java.util.Arrays;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import com.kuda.app.model.OrganizationCredential;


@TestConfiguration
public class SpringSecurityWebAuxTestConfig {

	@Bean
	@Primary
	public UserDetailsService userDetailsService() {

		OrganizationCredential user = new OrganizationCredential();
		user.setUsername("username");
		user.setIsActive(true);

		return new InMemoryUserDetailsManager(Arrays.asList(user));
	}
}
