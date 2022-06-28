package com.kuda.app.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;
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
import com.kuda.app.helper.TestInstanceGenerator;
import com.kuda.app.model.Kuda;
import com.kuda.app.model.Organization;
import com.kuda.app.model.OrganizationCredential;
import com.kuda.app.model.User;
import com.kuda.app.repository.KudaRepository;
import com.kuda.app.security.SecurityUtil;
import com.kuda.app.service.kuda.KudaServiceImpl;
import com.kuda.app.service.organization.credential.OrganizationCredentialService;
import com.kuda.app.service.setting.SettingService;
import com.kuda.app.service.user.UserService;

@Profile("test")
@TestPropertySource("classpath:application-test.properties")
@ExtendWith(MockitoExtension.class)
public class GetKudasTest {

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

	private NewKudaRequest request;

	@BeforeEach
	public void setup() {

		Organization organization = new Organization();
		organization.setId(33l);
		Optional<OrganizationCredential> sampleOrganization = Optional
				.of(new OrganizationCredential(organization, Keys.USERNAME, Keys.PASSWORD, true));
		Mockito.when(organizationCredentialService.findByUsername(Mockito.anyString())).thenReturn(sampleOrganization);

		User user = new User();
		user.setId(1);
		user.setName("user name");
		user.setUsername("username");
		List<Kuda> kudaList = new ArrayList<>();

		request = new NewKudaRequest();
		request.setDescription("description");
		request.setFromUser(user.getId());
		request.setKudaType(KudaType.THANKS.getName());

		kudaList.add(TestInstanceGenerator.getKudaSample(organization, user, request, 1));
		kudaList.add(TestInstanceGenerator.getKudaSample(organization, user, request, 1));
		kudaList.add(TestInstanceGenerator.getKudaSample(organization, user, request, 1));
		Mockito.when(kudaRepository.findByOrganizationAndShowStatus(organization.getId(), true)).thenReturn(kudaList);
	}

	@Test
	public void ok() {

		List<KudaInfo> kudas = kudaService.getKudas(SAMPLE_USERNAME);
		assertThat(kudas).hasSize(3);
		for (KudaInfo kuda : kudas) {
			assertThat(kuda.getFromUser()).isNotNull();
			assertThat(kuda.getDescription()).isNotNull();
			assertThat(kuda.getKudaType()).isNotNull();
			assertThat(kuda.getReadStatus()).isNotNull();
		}
	}
}
