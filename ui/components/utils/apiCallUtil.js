import Cookies from 'js-cookie';
import uiConfiguration from '../../configuration/ui.config.json';
import { getCurrentRoute } from './routeUtil';

export const REST_CALL_SUCCESS = 200;

export const getGetRequestOptions = () => {
  return {
    method: 'GET',
    headers: {
      'Content-Type': 'application/json',
      Authorization: 'Bearer ' + Cookies.get('token'),
    },
  };
};

export const getPostRequestOptions = (body = {}) => {
  return {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
      Authorization: 'Bearer ' + Cookies.get('token'),
    },
    body: JSON.stringify(body),
  };
};

export const getPutRequestOptions = (body = {}) => {
  return {
    method: 'PUT',
    headers: {
      'Content-Type': 'application/json',
      Authorization: 'Bearer ' + Cookies.get('token'),
    },
    body: JSON.stringify(body),
  };
};

export const getDeleteRequestOptions = () => {
  return {
    method: 'DELETE',
    headers: {
      'Content-Type': 'application/json',
      Authorization: 'Bearer ' + Cookies.get('token'),
    },
  };
};

export const callRest = async (url, requestOptions) => {
  let appName = '';
  if (uiConfiguration.baseName) {
    appName = '/' + uiConfiguration.baseName;
  }
  const result = await fetch(
    uiConfiguration.server + ':' + uiConfiguration.port + appName + '/' + url,
    requestOptions
  );
  if (result.status === 403 && getCurrentRoute() != '/signin') {
    window.location.href = '/signin';
  }
  let response = await result.json();
  return {
    data: response,
    status: result.status,
  };
};

export const callPost = async (url, body = {}) => {
  return callRest(url, getPostRequestOptions(body));
};

export const callGet = async (url) => {
  return callRest(url, getGetRequestOptions());
};

export const callPut = async (url) => {
  return callRest(url, getPutRequestOptions());
};

export const callDelete = async (url) => {
  return callRest(url, getDeleteRequestOptions());
};

export const getKudas = () => {
  return callGet('api/kuda');
};

export const createNewKuda = (fromUser, description, kudaType) => {
  return callPost('api/kuda', {
    fromUser: fromUser,
    description: description,
    kudaType: kudaType,
  });
};

export const createUser = (username, nickname) => {
  return callPost(
    'api/user?username=' + username + '&nickname=' + nickname,
    {}
  );
};

export const getKudaFeedbackTypes = () => {
  return callGet('api/kuda/feedback/type');
};

export const getAvailablePermissions = () => {
  return callGet('api/authorization/permission/available');
};

export const getMyPermissions = () => {
  return callGet('api/authorization/permission/me');
};

export const getOrganizationPermissions = () => {
  return callGet('api/authorization/organization');
};

export const getKudaDuration = () => {
  return callGet('api/kuda/duration');
};

export const getDurationWinners = (duration) => {
  return callGet('api/kuda/winner?duration=' + duration);
};

export const getUsers = () => {
  return callGet('api/user');
};

export const getSettings = () => {
  return callGet('api/setting');
};

export const signIn = (username, password) => {
  return callPost(
    'public/auth/signin?username=' + username + '&password=' + password,
    {}
  );
};

export const changeReadStatus = (id) => {
  return callPost('api/kuda/read/change?id=' + id, {});
};

export const getOrganizationInfo = (username) => {
  return callGet('public/auth?username=' + username);
};

export const signUp = (name, username, password) => {
  return callPost(
    'public/auth/signup?name=' +
      name +
      '&username=' +
      username +
      '&password=' +
      password,
    {}
  );
};

export const setSetting = (id, value) => {
  return callPut('api/setting?id=' + id + '&value=' + value);
};

export const updatePassword = (username, password) => {
  return callPut(
    'api/credential?username=' + username + '&password=' + password
  );
};

export const deleteSetting = (id) => {
  return callDelete('api/setting?id=' + id);
};

export const createSetting = (key, tag, value) => {
  return callPost(
    'api/setting?key=' + key + '&tag=' + tag + '&value=' + value,
    {}
  );
};

export const changeKudaShowStatus = (showStatus) => {
  return callPost('api/kuda/show/change?status=' + showStatus, {});
};

export const validateLoginStatus = () => {
  return callPost('public/auth/status/validate', {});
};
