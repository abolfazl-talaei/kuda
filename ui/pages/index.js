import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import 'bootstrap/dist/css/bootstrap.css';
import Head from 'next/head';
import React from 'react';
import { Col, Row } from 'react-bootstrap';
import { useTranslation } from 'react-i18next';
import ActionArea from '../components/action/ActionArea';
import { VisibleContainer } from '../components/authorization/VisibleContainer';
import Footer from '../components/Footer';
import TopNavbar from '../components/navbar/TopNavbar';
import Panel from '../components/panel/Panel';
import {
  hasPermission,
  userInfoFromToken,
} from '../components/utils/authorizationUtil';
import { detectComponentPosition } from '../components/utils/languageUtil';
import { ROUTES } from '../components/utils/routeUtil';
import '../i18n/i18n';
import styles from '../styles/Home.module.css';
import { faExclamationCircle } from '@fortawesome/free-solid-svg-icons';

const Index = (props) => {
  const { t, i18n } = useTranslation();
  const direction = detectComponentPosition(i18n);

  const { permissions, loginInfo } = props;

  const onHeaderRender = () => {
    return (
      <span>
        {t('welcome')}
        <FontAwesomeIcon
          className={styles.panelIcon + ' ' + styles.panelIconWarning}
          icon={faExclamationCircle}
        />
      </span>
    );
  };

  const onBodyRender = () => {
    return <span>{t('welcome.body')}</span>;
  };

  const onHeaderAdminHelpRender = () => {
    return (
      <span>
        {t('admin.help.header')}
        <FontAwesomeIcon
          className={styles.panelIcon + ' ' + styles.panelIconInfo}
          icon={faExclamationCircle}
        />
      </span>
    );
  };

  const onBodyAdminHelpRender = () => {
    return (
      <span>
        {t('admin.help.body1')}
        <br />
        <span className={styles.usernames}>
          {t('username.admin')}: {userInfoFromToken()['username']}
        </span>
        <span className={styles.usernames}>
          {t('username.reader')}:{' '}
          {userInfoFromToken()['username'].split('.')[0] + '.reader'}
        </span>
        <span className={styles.usernames}>
          {t('username.default')}:{' '}
          {userInfoFromToken()['username'].split('.')[0]}
        </span>
      </span>
    );
  };

  return (
    <div className={styles.container}>
      <Head>
        <title>{t('kuda')}</title>
        <link rel="icon" href="/favicon.ico" />
      </Head>

      <main className={styles.main}>
        <TopNavbar permissions={permissions} loginInfo={loginInfo}></TopNavbar>

        <div className={styles.panelContainer}>
          <Panel
            type="warning"
            onHeaderRender={onHeaderRender}
            onBodyRender={onBodyRender}
            headerType="dynamic"
          ></Panel>
        </div>

        <VisibleContainer
          condition={hasPermission(permissions, ROUTES.settings.name)}
        >
          <div className={styles.panelContainer}>
            <Panel
              type="info"
              onHeaderRender={onHeaderAdminHelpRender}
              onBodyRender={onBodyAdminHelpRender}
              headerType="dynamic"
            ></Panel>
          </div>
        </VisibleContainer>

        <div className={styles.mainPageItems}>
          <Col md="4" xs="12" className={styles.box}>
            <ActionArea
              title={t('home.kuda.new.title')}
              description={t('home.kuda.new.description')}
              actionText={t('home.kuda.new.action')}
              url="newkuda"
              direction={direction}
            ></ActionArea>
          </Col>
          <Col md="4" xs="12" className={styles.box}>
            <ActionArea
              title={t('home.kuda.list.title')}
              description={t('home.kuda.list.description')}
              actionText={t('home.kuda.list.action')}
              url="kudas"
              direction={direction}
            ></ActionArea>
          </Col>
          <Col md="4" xs="12" className={styles.box}>
            <ActionArea
              title={t('home.kuda.winner.title')}
              description={t('home.kuda.winner.description')}
              actionText={t('home.kuda.winner.action')}
              url="winners"
              direction={direction}
            ></ActionArea>
          </Col>
        </div>
      </main>

      <Footer></Footer>
    </div>
  );
};

export default Index;
