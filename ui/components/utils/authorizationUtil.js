import jwt_decode from 'jwt-decode';
import Cookies from 'js-cookie';

export const hasPermission = (permissions, acceptedPermission) => {
  return permissions.indexOf(acceptedPermission) != -1;
};

export const hasMultiplePermission = (permissions, acceptedPermissions) => {
  for (let acceptedPermission of acceptedPermissions) {
    if (permissions.indexOf(acceptedPermission) != -1) {
      return true;
    }
  }
  return false;
};

export const userInfoFromToken = () => {
  return jwt_decode(Cookies.get('token'));
};
