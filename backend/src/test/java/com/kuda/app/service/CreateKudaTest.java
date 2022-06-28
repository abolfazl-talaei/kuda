package com.kuda.app.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

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

import com.kuda.app.constants.Keys;
import com.kuda.app.dto.NewKudaRequest;
import com.kuda.app.dto.response.KudaInfo;
import com.kuda.app.enums.KudaType;
import com.kuda.app.enums.SettingKey;
import com.kuda.app.exception.KudaException;
import com.kuda.app.helper.TestInstanceGenerator;
import com.kuda.app.model.Organization;
import com.kuda.app.model.OrganizationCredential;
import com.kuda.app.model.Settings;
import com.kuda.app.model.User;
import com.kuda.app.repository.KudaRepository;
import com.kuda.app.security.SecurityUtil;
import com.kuda.app.service.kuda.KudaServiceImpl;
import com.kuda.app.service.organization.credential.OrganizationCredentialService;
import com.kuda.app.service.setting.SettingService;
import com.kuda.app.service.user.UserService;

@Profile("test")
@ExtendWith(MockitoExtension.class)
@TestPropertySource("classpath:application-test.properties")
public class CreateKudaTest {

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

	private NewKudaRequest request;
	private User user;

	@InjectMocks
	@Qualifier("KudaServiceImpl")
	private KudaServiceImpl kudaService;

	private final Integer DURATION = 6;

	@BeforeEach
	public void setup() throws KudaException {

		Organization organization = new Organization();
		organization.setId(33l);
		Optional<OrganizationCredential> sampleOrganization = Optional
				.of(new OrganizationCredential(organization, Keys.USERNAME, Keys.PASSWORD, true));
		Mockito.when(organizationCredentialService.findByUsername(Mockito.anyString())).thenReturn(sampleOrganization);

		user = new User();
		user.setId(1);
		user.setName("user name");
		user.setUsername("username");

		Mockito.when(settingService.get(SAMPLE_USERNAME, SettingKey.DEFAULT_KUDA_SHOW))
				.thenReturn(Optional
						.of(Settings.builder().key(SettingKey.DEFAULT_KUDA_SHOW.getName()).value("false").build()));

		Mockito.when(settingService.get(SAMPLE_USERNAME, SettingKey.DURATION))
				.thenReturn(Optional
						.of(Settings.builder().key(SettingKey.DURATION.getName()).value(DURATION + "").build()));

		OrganizationCredential organizationCredential = new OrganizationCredential();
		organizationCredential.setOrganization(organization);
		organizationCredential.setIsActive(true);
		organizationCredential.setUsername(SAMPLE_USERNAME);

		Mockito.when(organizationCredentialService
				.findByUsername(SecurityUtil.getOrganizationUsername(SAMPLE_USERNAME)))
				.thenReturn(Optional.of(organizationCredential));

		request = new NewKudaRequest();
		request.setDescription("description");
		request.setFromUser(user.getId());
		request.setKudaType(KudaType.THANKS.getName());

		Mockito.when(userService.getUserById(request.getFromUser()))
				.thenReturn(Optional.of(user));

		Mockito.when(kudaRepository.save(Mockito.any()))
				.thenReturn(TestInstanceGenerator.getKudaSample(organization, user, request, DURATION));
	}

	@Test
	public void ok() throws KudaException {

		KudaInfo createdKuda = kudaService.createKuda(SAMPLE_USERNAME, request);
		assertThat(createdKuda.getDescription()).isEqualTo(request.getDescription());
		assertThat(createdKuda.getDuration()).isEqualTo(DURATION);
		assertThat(createdKuda.getFromUser()).isEqualTo(user.getName());
		assertThat(createdKuda.getFromUsername()).isEqualTo(user.getUsername());
		assertThat(createdKuda.getKudaType()).isEqualTo(KudaType.THANKS);
		assertThat(createdKuda.getReadStatus()).isFalse();
	}
}
