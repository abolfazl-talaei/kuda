import 'bootstrap/dist/css/bootstrap.css';
import '../i18n/i18n';
import '../styles/globals.css';

import { config, dom } from '@fortawesome/fontawesome-svg-core';
import Head from 'next/head';

import 'bootstrap/dist/css/bootstrap.min.css';
import React, { useEffect, useState } from 'react';
import { CookiesProvider, useCookies } from 'react-cookie';
import { useTranslation } from 'react-i18next';
import { ToastProvider } from 'react-toast-notifications';
import { KudaToast } from '../components/toast/KudaToast';
import {
  getMyPermissions,
  REST_CALL_SUCCESS,
  validateLoginStatus,
} from '../components/utils/apiCallUtil';
import { getCurrentRoute, setNewRoute } from '../components/utils/routeUtil';

const MyApp = ({ Component, pageProps }) => {
  const [cookies] = useCookies(['token']);
  const [permissions, setPermissions] = useState([]);
  const { t, i18n } = useTranslation();
  const [loginInfo, setLoginInfo] = useState({});

  config.autoAddCss = false;

  useEffect(() => {
    if (cookies.language) {
      i18n.changeLanguage(cookies.language);
    } else {
      i18n.changeLanguage('en');
    }
    validateLoginStatus().then((response) => {
      if (
        (!response ||
          response.status != REST_CALL_SUCCESS ||
          !response.data.signedIn) &&
        getCurrentRoute() != '/signin'
      ) {
        setNewRoute('/signin');
      }
      setLoginInfo(response.data);
      getMyPermissions().then((permissionResponse) => {
        if (permissionResponse.status != REST_CALL_SUCCESS) {
          return;
        }
        let userPermissions = permissionResponse.data.map((item) => {
          return item.permission;
        });
        setPermissions(userPermissions);
        // if (
        //   !hasPermission(userPermissions, getCurrentRouteConfig().name) &&
        //   getCurrentRoute() != '/signin'
        // ) {
        //   setNewRoute('/signin');
        // }
      });
    });
    return {};
  }, []);
  return (
    <CookiesProvider>
      <ToastProvider components={{ Toast: KudaToast }}>
        <Head>
          <style>{dom.css()}</style>
        </Head>
        <Component
          permissions={permissions}
          loginInfo={loginInfo}
          {...pageProps}
          // loginInfo={loginInfo}
        />
      </ToastProvider>
    </CookiesProvider>
  );
};

export default MyApp;
