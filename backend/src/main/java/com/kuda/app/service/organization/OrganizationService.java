package com.kuda.app.service.organization;


import com.kuda.app.dto.response.LoginInfo;
import com.kuda.app.model.Organization;


public interface OrganizationService {

	Organization signUp(String name, String username, String password) throws Exception;

	LoginInfo getInfo(String username) throws Exception;

}
