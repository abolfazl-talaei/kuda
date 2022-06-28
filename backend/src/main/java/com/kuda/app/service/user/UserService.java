package com.kuda.app.service.user;


import java.util.List;
import java.util.Optional;

import com.kuda.app.dto.response.UserInfo;
import com.kuda.app.exception.KudaException;
import com.kuda.app.model.User;


public interface UserService {

	public List<UserInfo> getUsers(String username) throws KudaException;

	public UserInfo createUser(String loggedInUser, String username, String nickname) throws Exception;

	public Optional<User> getUserById(Long id);
}
