import React, { useEffect, useState } from 'react';
import { Col, Nav, Navbar, NavDropdown } from 'react-bootstrap';
import { useCookies } from 'react-cookie';
import { useTranslation } from 'react-i18next';
import { VisibleContainer } from '../authorization/VisibleContainer';
import { hasPermission, userInfoFromToken } from '../utils/authorizationUtil';
import { detectTagDirection } from '../utils/languageUtil';
import { getCurrentRouteConfig, ROUTES, setNewRoute } from '../utils/routeUtil';
import styles from './TopNavbar.module.css';

const TopNavbar = (props) => {
  const { t, i18n } = useTranslation();
  const direction = detectTagDirection(i18n);
  const [cookies, setCookie, removeCookie] = useCookies(['token', 'language']);
  const [userInfo, setUserInfo] = useState({});

  useEffect(() => {
    setUserInfo(userInfoFromToken());
  }, []);

  const changeLanguage = (language) => {
    i18n.changeLanguage(language);
    setCookie('language', language);
  };

  const { permissions, loginInfo } = props;

  const logout = () => {
    removeCookie('token');
    setNewRoute('/signin');
  };

  return (
    <div>
      <Navbar
        bg="light"
        expand="lg"
        style={{ direction: direction, padding: 0, fontFamily: 'Sahel' }}
      >
        <Navbar.Brand href="/">
          <img width="55px" height="50px" src="/logo.png"></img>
        </Navbar.Brand>
        {t('environment')}

        <span className={styles.environment}>
          {loginInfo && loginInfo.name}
        </span>

        <Navbar.Toggle aria-controls="basic-navbar-nav" />
        <Navbar.Collapse id="basic-navbar-nav" style={{ direction: direction }}>
          <Nav>
            <VisibleContainer
              condition={hasPermission(permissions, ROUTES.home.name)}
            >
              <Nav.Link href={ROUTES.home.route} className={styles.navSelected}>
                {t('home')}{' '}
              </Nav.Link>
            </VisibleContainer>
            <VisibleContainer
              condition={hasPermission(permissions, ROUTES.newkuda.name)}
            >
              <Nav.Link
                className={styles.navSelected}
                href={ROUTES.newkuda.route}
              >
                {t('kuda.new')}{' '}
              </Nav.Link>
            </VisibleContainer>
            <VisibleContainer
              condition={hasPermission(permissions, ROUTES.listkuda.name)}
            >
              <Nav.Link
                className={styles.navSelected}
                href={ROUTES.listkuda.route}
              >
                {t('kuda.list')}{' '}
              </Nav.Link>
            </VisibleContainer>
            <VisibleContainer
              condition={hasPermission(permissions, ROUTES.listwinner.name)}
            >
              <Nav.Link
                className={styles.navSelected}
                href={ROUTES.listwinner.route}
              >
                {t('winner.list')}{' '}
              </Nav.Link>
            </VisibleContainer>
            <NavDropdown
              className={styles.navSelected}
              title={t('language')}
              id="basic-nav-dropdown"
            >
              <NavDropdown.Item onClick={() => changeLanguage('fa')}>
                {t('persian')}
              </NavDropdown.Item>
              <NavDropdown.Item onClick={() => changeLanguage('en')}>
                {t('english')}
              </NavDropdown.Item>
            </NavDropdown>
            <NavDropdown
              className={styles.navSelected}
              title={t('user')}
              id="basic-nav-dropdown"
            >
              <VisibleContainer
                condition={hasPermission(permissions, ROUTES.listuser.name)}
              >
                <NavDropdown.Item href={ROUTES.listuser.route}>
                  {t('user.list')}{' '}
                </NavDropdown.Item>
              </VisibleContainer>
              <VisibleContainer
                condition={hasPermission(permissions, ROUTES.newuser.name)}
              >
                <NavDropdown.Item href={ROUTES.newuser.route}>
                  {t('user.new')}{' '}
                </NavDropdown.Item>
              </VisibleContainer>
            </NavDropdown>
            <VisibleContainer
              condition={hasPermission(permissions, ROUTES.settings.name)}
            >
              <Nav.Link
                className={styles.navSelected}
                href={ROUTES.settings.route}
              >
                {t('settings')}
              </Nav.Link>
            </VisibleContainer>
            <Nav.Link className={styles.navSelected} onClick={logout}>
              {t('logout')}
            </Nav.Link>
          </Nav>
        </Navbar.Collapse>
      </Navbar>

      {getCurrentRouteConfig().route != '/' && (
        <Col
          className="m-2"
          style={{
            display: 'flex',
            justifyContent: 'center',
            direction: detectTagDirection(i18n),
          }}
        >
          <span className={styles.breadcrumb}>
            <span className="m-2">
              <a href="/">{t('home')}</a>
            </span>
            <span className="m-2">/</span>
            <span className="m-2">{t(getCurrentRouteConfig().name)}</span>
          </span>
        </Col>
      )}
    </div>
  );
};

export default TopNavbar;
