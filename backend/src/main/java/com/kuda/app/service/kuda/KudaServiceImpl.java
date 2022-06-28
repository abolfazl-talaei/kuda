package com.kuda.app.service.kuda;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kuda.app.dto.NewKudaRequest;
import com.kuda.app.dto.response.KudaInfo;
import com.kuda.app.enums.KudaType;
import com.kuda.app.enums.SettingKey;
import com.kuda.app.exception.Errors;
import com.kuda.app.exception.KudaException;
import com.kuda.app.exception.Resources;
import com.kuda.app.mapper.KudaMapper;
import com.kuda.app.model.Kuda;
import com.kuda.app.model.OrganizationCredential;
import com.kuda.app.model.Settings;
import com.kuda.app.model.User;
import com.kuda.app.repository.KudaRepository;
import com.kuda.app.repository.dto.IterationUserRank;
import com.kuda.app.repository.dto.KudaDefaultFeedback;
import com.kuda.app.security.SecurityUtil;
import com.kuda.app.service.organization.credential.OrganizationCredentialService;
import com.kuda.app.service.setting.SettingService;
import com.kuda.app.service.user.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class KudaServiceImpl implements KudaService {

	private final KudaRepository kudaRepository;
	private final UserService userService;
	private final SettingService settingService;
	private final OrganizationCredentialService organizationCredentialService;

	public List<KudaDefaultFeedback> getFeedbackTypes(String username) throws KudaException {

		List<KudaDefaultFeedback> feedbackTypes = new ArrayList<>();
		List<Settings> definedFeedbacks = settingService.getByTag(username, SettingKey.FEEDBACK);
		if (definedFeedbacks != null) {
			for (Settings item : definedFeedbacks) {
				feedbackTypes.add(KudaDefaultFeedback.of(item.getValue(), item.getValue()));
			}
		}
		return feedbackTypes;
	}

	private Long getOrganizationId(String username) {

		Optional<OrganizationCredential> foundOrganizationCredential = organizationCredentialService
				.findByUsername(username);
		if (foundOrganizationCredential.isPresent()) {
			return foundOrganizationCredential.get().getOrganization().getId();
		} else {
			return -1l;
		}
	}

	@Override
	public List<KudaInfo> getKudas(String username) {

		List<Kuda> kudas = kudaRepository.findByOrganizationAndShowStatus(getOrganizationId(username), true);
		return kudas.stream().map(KudaMapper::map).collect(Collectors.toList());
	}

	@Override
	public List<Kuda> getCurrentDurationKudas(String username) throws KudaException {

		Optional<Settings> iterationSetting = settingService.get(username, SettingKey.FEEDBACK_EFFECT);
		boolean feedbackEffect = iterationSetting.isPresent()
				&& Boolean.parseBoolean(iterationSetting.get().getValue());
		return kudaRepository.findAllDurationKudas(getOrganizationId(username), feedbackEffect);
	}

	@Override
	public List<IterationUserRank> getReadStatistics(String username) {

		return kudaRepository.findReadKudas(getOrganizationId(username), true);
	}

	@Override
	public Optional<Kuda> getKudaById(long id) {

		return kudaRepository.findById(id);
	}

	public KudaInfo chnageKudaReadStatus(Long id) throws KudaException {

		Optional<Kuda> found = getKudaById(id);
		if (!found.isPresent()) {
			throw new KudaException(Errors.NOT_FOUND.getName(),
					HttpStatus.NOT_FOUND, Resources.KUDA.getName(), "5000");
		}
		found.get().setReadStatus(found.get().getReadStatus() != null ? !found.get().getReadStatus() : true);
		kudaRepository.save(found.get());
		return KudaMapper.map(found.get());
	}

	@Override
	public void deleteKuda(long id) {

		Optional<Kuda> kuda = kudaRepository.findById(id);
		if (kuda.isPresent()) {
			Kuda foundKuda = kuda.get();
			foundKuda.setReadStatus(foundKuda.getReadStatus() == null || !foundKuda.getReadStatus());
			kudaRepository.save(foundKuda);
		}
	}

	@Override
	public KudaInfo createKuda(String username, NewKudaRequest request) throws KudaException {

		Optional<Settings> defaultShowStatusSetting = settingService.get(username, SettingKey.DEFAULT_KUDA_SHOW);
		Boolean defaultShowStatus = defaultShowStatusSetting.isPresent()
				&& Boolean.TRUE.toString().equalsIgnoreCase(defaultShowStatusSetting.get().getValue());

		Kuda kuda = new Kuda();
		kuda.setShowStatus(defaultShowStatus);
		kuda.setReadStatus(false);
		kuda.setDuration(getDuration(username));
		kuda.setDescription(request.getDescription());
		kuda.setRequestId(UUID.randomUUID().toString() + UUID.randomUUID().toString());
		kuda.setKudaType(KudaType.fromName(request.getKudaType()));

		Optional<OrganizationCredential> foundOrganizationCredential = organizationCredentialService
				.findByUsername(SecurityUtil.getOrganizationUsername(username));
		if (foundOrganizationCredential.isPresent()) {

			kuda.setOrganization(foundOrganizationCredential.get().getOrganization());
		}
		if (request.getFromUser() == null) {
			throw new KudaException(Errors.INVALID.getName(),
					HttpStatus.BAD_REQUEST, Resources.FROM_USER.getName(), "1000");
		}
		Optional<User> user = userService.getUserById(request.getFromUser());
		if (user.isPresent()) {
			kuda.setFromUser(user.get());
		} else {
			throw new KudaException(Errors.INVALID.getName(),
					HttpStatus.NOT_FOUND, Resources.FROM_USER.getName(), "1001");
		}

		if (kuda.getDescription() == null || kuda.getDescription().length() < 1) {
			throw new KudaException(Errors.INVALID.getName(),
					HttpStatus.BAD_REQUEST, Resources.DESCRIPTION.getName(), "1300");
		}

		kudaRepository.save(kuda);
		return KudaMapper.map(kuda);
	}

	@Override
	@Transactional
	public void changeShowStatus(String username, Boolean status) throws KudaException {

		List<Kuda> allDurationKudos = getCurrentDurationKudas(SecurityUtil.getOrganizationUsername(username));
		for (Kuda item : allDurationKudos) {
			item.setShowStatus(status);
			kudaRepository.save(item);
		}
	}

	public List<IterationUserRank> getIterationKudaRanks(String username, Integer duration) {

		if (duration == null) {
			return Collections.emptyList();
		}

		return kudaRepository.findDurationKudas(getOrganizationId(username), duration);
	}

	@Override
	public Map<String, Object> getKudaDurationMap(String username) throws KudaException {

		Map<String, Object> durationMap = new HashMap<>();
		durationMap.put("duration", getDuration(username));
		return durationMap;
	}

	public Integer getDuration(String username) throws KudaException {

		Optional<Settings> iterationSetting = settingService.get(username, SettingKey.DURATION);
		return iterationSetting.isPresent() ? Integer.valueOf(iterationSetting.get().getValue()) : 1;
	}
}
