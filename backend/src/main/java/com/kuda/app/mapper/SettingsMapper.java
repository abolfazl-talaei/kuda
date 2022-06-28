package com.kuda.app.mapper;

import org.springframework.http.HttpStatus;

import com.kuda.app.dto.response.SettingInfo;
import com.kuda.app.exception.Errors;
import com.kuda.app.exception.KudaException;
import com.kuda.app.exception.Resources;
import com.kuda.app.model.Settings;

public class SettingsMapper {

    private SettingsMapper() throws KudaException {
        throw new KudaException(Errors.INTERNAL_ERROR.getName(), HttpStatus.INTERNAL_SERVER_ERROR,
                Resources.UTIL.getName(), "106");
    }

    public static SettingInfo map(Settings settings) {

        SettingInfo settingInfo = new SettingInfo();

        settingInfo.setId(settings.getId());
        settingInfo.setTag(settings.getTag());
        settingInfo.setKey(settings.getKey());
        settingInfo.setValue(settings.getValue());

        return settingInfo;
    }
}
