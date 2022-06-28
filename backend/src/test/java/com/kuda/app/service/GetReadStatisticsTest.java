package com.kuda.app.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
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
import com.kuda.app.exception.KudaException;
import com.kuda.app.helper.TestInstanceGenerator;
import com.kuda.app.model.Organization;
import com.kuda.app.model.OrganizationCredential;
import com.kuda.app.repository.KudaRepository;
import com.kuda.app.repository.dto.IterationUserRank;
import com.kuda.app.security.SecurityUtil;
import com.kuda.app.service.kuda.KudaServiceImpl;
import com.kuda.app.service.organization.credential.OrganizationCredentialService;
import com.kuda.app.service.setting.SettingService;
import com.kuda.app.service.user.UserService;

@Profile("test")
@ExtendWith(MockitoExtension.class)
@TestPropertySource("classpath:application-test.properties")
public class GetReadStatisticsTest {

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

		Organization organization = new Organization();
		organization.setId(33l);
		Optional<OrganizationCredential> sampleOrganization = Optional
				.of(new OrganizationCredential(organization, Keys.USERNAME, Keys.PASSWORD, true));
		Mockito.when(organizationCredentialService.findByUsername(Mockito.anyString())).thenReturn(sampleOrganization);

		Mockito.when(kudaRepository.findReadKudas(organization.getId(), true))
				.thenReturn(Arrays.asList(
						TestInstanceGenerator.getIterationKudaRanksSample(),
						TestInstanceGenerator.getIterationKudaRanksSample(),
						TestInstanceGenerator.getIterationKudaRanksSample()));
	}

	@Test
	public void ok() throws KudaException {

		List<IterationUserRank> list = kudaService.getReadStatistics(SAMPLE_USERNAME);
		assertThat(list).hasSize(3);
		for (IterationUserRank item : list) {
			assertThat(item.getTotal()).isEqualTo(item.getThanks() - item.getFeedbacks());
			assertThat(item.getUsername()).isNotNull();
			assertThat(item.getName()).isNotNull();
		}
	}
}
