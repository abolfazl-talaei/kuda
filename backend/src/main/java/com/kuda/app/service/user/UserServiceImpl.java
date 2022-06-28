package com.kuda.app.service.user;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kuda.app.dto.response.UserInfo;
import com.kuda.app.exception.Errors;
import com.kuda.app.exception.KudaException;
import com.kuda.app.exception.Resources;
import com.kuda.app.mapper.UserMapper;
import com.kuda.app.model.OrganizationCredential;
import com.kuda.app.model.User;
import com.kuda.app.repository.UserRepository;
import com.kuda.app.security.SecurityUtil;
import com.kuda.app.service.organization.credential.OrganizationCredentialService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;
	private final OrganizationCredentialService organizationCredentialService;

	@Override
	public List<UserInfo> getUsers(String username) throws KudaException {

		List<UserInfo> userInfo = new ArrayList<>();
		List<User> users = userRepository
				.findAllActiveUsersByOrganization(organizationCredentialService.getOrganizationId(username));

		for (User user : users) {
			userInfo.add(UserMapper.map(user));
		}

		return userInfo;
	}

	@Override
	public Optional<User> getUserById(Long id) {

		return userRepository.findById(id);
	}

	@Override
	@Transactional
	public UserInfo createUser(String loggedInUser, String username, String nickname) throws KudaException {

		Optional<OrganizationCredential> organization = organizationCredentialService
				.findByUsername(SecurityUtil.getOrganizationUsername(loggedInUser));
		if (!organization.isPresent()) {
			throw new KudaException(Errors.NOT_FOUND.getName(),
					HttpStatus.NOT_FOUND, Resources.ORGANIZATION.getName(), "1905");
		}

		Optional<User> existedUser = userRepository
				.findOneByUsername(organizationCredentialService.getOrganizationId(loggedInUser), username);
		if (existedUser.isPresent()) {
			throw new KudaException(Errors.DUPLICATE.getName(),
					HttpStatus.BAD_REQUEST, Resources.USER.getName(), "603");
		}

		User user = new User();
		user.setName(nickname);
		user.setUsername(username);
		user.setTotalPoints(0);
		user.setIsActive(true);
		user.setOrganization(organization.get().getOrganization());

		userRepository.save(user);

		return UserMapper.map(user);
	}

}
