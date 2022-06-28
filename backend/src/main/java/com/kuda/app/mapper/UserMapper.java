package com.kuda.app.mapper;

import org.springframework.http.HttpStatus;

import com.kuda.app.dto.response.UserInfo;
import com.kuda.app.exception.Errors;
import com.kuda.app.exception.KudaException;
import com.kuda.app.exception.Resources;
import com.kuda.app.model.User;

public class UserMapper {

    private UserMapper() throws KudaException {
        throw new KudaException(Errors.INTERNAL_ERROR.getName(), HttpStatus.INTERNAL_SERVER_ERROR,
                Resources.UTIL.getName(), "105");
    }

    public static UserInfo map(User user) {

        UserInfo userInfo = new UserInfo();

        userInfo.setId(user.getId());
        userInfo.setName(user.getName());
        userInfo.setUsername(user.getUsername());
        userInfo.setTotalPoints(user.getTotalPoints());

        return userInfo;
    }
}
