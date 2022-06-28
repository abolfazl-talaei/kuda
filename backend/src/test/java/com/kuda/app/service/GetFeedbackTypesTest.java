package com.kuda.app.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.TestPropertySource;

import com.kuda.app.enums.SettingKey;
import com.kuda.app.exception.KudaException;
import com.kuda.app.model.Settings;
import com.kuda.app.repository.KudaRepository;
import com.kuda.app.repository.dto.KudaDefaultFeedback;
import com.kuda.app.security.SecurityUtil;
import com.kuda.app.service.kuda.KudaServiceImpl;
import com.kuda.app.service.organization.credential.OrganizationCredentialService;
import com.kuda.app.service.setting.SettingService;
import com.kuda.app.service.user.UserService;

@Profile("test")
@ExtendWith(MockitoExtension.class)
@TestPropertySource("classpath:application-test.properties")
public class GetFeedbackTypesTest {

	private static final String SAMPLE_USERNAME = "admin";

	@Mock
	private OrganizationCredentialService organizationCredentialService;

	@Mock
	private KudaRepository kudaRepository;

	@Mock
	private UserService userService;

	@Mock
	private SettingService settingService;

	@Mock
	private SecurityUtil securityUtil;

	@InjectMocks
	@Qualifier("KudaServiceImpl")
	private KudaServiceImpl kudaService;

	@BeforeEach
	public void setup() throws KudaException {

		Mockito.when(settingService.getByTag(SAMPLE_USERNAME, SettingKey.FEEDBACK))
				.thenReturn(Arrays.asList(
						Settings.builder().key("test1").value("test1").build(),
						Settings.builder().key("test2").value("test2").build(),
						Settings.builder().key("test3").value("test3").build()));

	}

	@Test
	public void ok() throws KudaException {

		List<KudaDefaultFeedback> list = kudaService.getFeedbackTypes(SAMPLE_USERNAME);
		assertThat(list).hasSize(3);
		for (KudaDefaultFeedback item : list) {
			assertThat(item.getValue()).isNotNull();
		}
	}
}
