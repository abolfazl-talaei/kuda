package com.kuda.app.service.setting;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kuda.app.dto.response.SettingInfo;
import com.kuda.app.enums.SettingKey;
import com.kuda.app.exception.Errors;
import com.kuda.app.exception.KudaException;
import com.kuda.app.exception.Resources;
import com.kuda.app.mapper.SettingsMapper;
import com.kuda.app.model.Organization;
import com.kuda.app.model.OrganizationCredential;
import com.kuda.app.model.Settings;
import com.kuda.app.repository.SettingsRepository;
import com.kuda.app.security.SecurityUtil;
import com.kuda.app.service.organization.credential.OrganizationCredentialService;

@Service
public class SettingServiceImpl implements SettingService {

	private SettingsRepository settingsRepository;
	private OrganizationCredentialService organizationCredentialService;
	private SecurityUtil securityUtil;

	public SettingServiceImpl(SettingsRepository settingsRepository,
			OrganizationCredentialService organizationCredentialService, SecurityUtil securityUtil) {

		super();
		this.settingsRepository = settingsRepository;
		this.organizationCredentialService = organizationCredentialService;
		this.securityUtil = securityUtil;
	}

	@Override
	public List<Settings> getByTag(String username, SettingKey settingKey) throws KudaException {

		return settingsRepository.findByTagAndOrganizationId(settingKey.getName(),
				organizationCredentialService.getOrganizationId(username));
	}

	@Override
	public Optional<Settings> get(String username, SettingKey settingKey) throws KudaException {

		return settingsRepository.findOneByKeyAndOrganizationId(settingKey.getName(),
				organizationCredentialService.getOrganizationId(username));
	}

	@Override
	public List<SettingInfo> get(String username) throws KudaException {

		List<SettingInfo> settingsInfo = new ArrayList<>();
		List<Settings> settings = settingsRepository
				.findByOrganizationId(organizationCredentialService.getOrganizationId(username));
		for (Settings setting : settings) {
			settingsInfo.add(SettingsMapper.map(setting));
		}
		return settingsInfo;
	}

	@Override
	@Transactional
	public SettingInfo set(Long id, String value) throws KudaException {

		Optional<Settings> found = settingsRepository.findById(id);
		if (!found.isPresent()) {
			throw new KudaException(Errors.INVALID.getName(),
					HttpStatus.NOT_FOUND, Resources.SETTINGS.getName(), "1600");
		}

		found.get().setValue(value);
		settingsRepository.save(found.get());

		return SettingsMapper.map(found.get());
	}

	@Override
	@Transactional
	public SettingInfo create(String username, String key, String tag, String value) throws KudaException {

		Optional<OrganizationCredential> organization = organizationCredentialService
				.findByUsername(securityUtil.getDefaultUsername(username));
		if (!organization.isPresent()) {
			throw new KudaException(Errors.NOT_FOUND.getName(),
					HttpStatus.NOT_FOUND, Resources.ORGANIZATION.getName(), "1904");
		}

		Settings settings = Settings.builder()
				.key(key)
				.tag(tag)
				.value(value)
				.organization(organization.get().getOrganization())
				.build();

		settings = settingsRepository.save(settings);

		return SettingsMapper.map(settings);
	}

	@Override
	@Transactional
	public SettingInfo delete(Long id) throws KudaException {

		Optional<Settings> found = settingsRepository.findById(id);
		if (!found.isPresent()) {
			throw new KudaException(Errors.NOT_FOUND.getName(),
					HttpStatus.NOT_FOUND, Resources.SETTINGS.getName(), "1601");
		}
		settingsRepository.delete(found.get());

		return SettingsMapper.map(found.get());
	}

	@Override
	@Transactional
	public void initOrganizationSettings(Organization organization) {

		settingsRepository.save(Settings.builder().key(SettingKey.DURATION.getName()).tag(SettingKey.GENERAL.getName())
				.value("1").organization(organization).build());
		settingsRepository
				.save(Settings.builder().key(SettingKey.DEFAULT_KUDA_SHOW.getName()).tag(SettingKey.GENERAL.getName())
						.value("false").organization(organization).build());

		settingsRepository
				.save(Settings.builder().key(SettingKey.FEEDBACK_EFFECT.getName()).tag(SettingKey.GENERAL.getName())
						.value("false").organization(organization).build());
	}

}
