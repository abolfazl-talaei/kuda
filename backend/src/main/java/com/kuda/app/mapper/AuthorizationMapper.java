package com.kuda.app.mapper;

import org.springframework.http.HttpStatus;

import com.kuda.app.dto.response.AuthorizationInfo;
import com.kuda.app.exception.Errors;
import com.kuda.app.exception.KudaException;
import com.kuda.app.exception.Resources;
import com.kuda.app.model.Authorization;

public class AuthorizationMapper {

    private AuthorizationMapper() throws KudaException {
        throw new KudaException(Errors.INTERNAL_ERROR.getName(), HttpStatus.INTERNAL_SERVER_ERROR,
                Resources.UTIL.getName(), "104");
    }

    public static AuthorizationInfo map(Authorization authorization) {

        return new AuthorizationInfo(authorization.getId(), authorization.getUsername(), authorization.getPermission());
    }

}
