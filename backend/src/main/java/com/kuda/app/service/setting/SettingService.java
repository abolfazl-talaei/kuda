package com.kuda.app.service.setting;

import java.util.List;
import java.util.Optional;

import com.kuda.app.dto.response.SettingInfo;
import com.kuda.app.enums.SettingKey;
import com.kuda.app.exception.KudaException;
import com.kuda.app.model.Organization;
import com.kuda.app.model.Settings;

public interface SettingService {

	List<Settings> getByTag(String username, SettingKey settingKey) throws KudaException;

	List<SettingInfo> get(String username) throws KudaException;

	Optional<Settings> get(String username, SettingKey settingKey) throws KudaException;

	SettingInfo set(Long id, String value) throws KudaException;

	SettingInfo create(String username, String key, String tag, String value) throws KudaException;

	SettingInfo delete(Long id) throws KudaException;

    void initOrganizationSettings(Organization organization);
}
