import React, { useEffect, useState } from 'react';
import { Button, ButtonGroup, Card, Col, Form, Row } from 'react-bootstrap';
import { useTranslation } from 'react-i18next';
import {
  REST_CALL_SUCCESS,
  signIn,
  signUp,
  getOrganizationInfo,
} from '../utils/apiCallUtil';
import {
  detectTagDirection,
  getAvailableLanguages,
} from '../utils/languageUtil';
import styles from './Signin.module.css';
import { useToasts } from 'react-toast-notifications';
import { useCookies } from 'react-cookie';
import Input from '../input/Input';

const Signin = (props) => {
  const { t, i18n } = useTranslation();
  const direction = detectTagDirection(i18n);
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [name, setName] = useState('');
  const { addToast } = useToasts();
  const [cookies, setCookie] = useCookies(['token', 'language']);
  const [haveAccount, setHaveAccount] = useState(true);
  const [loadedOrganization, setLoadedOrganization] = useState('');

  useEffect(() => {
    if (!cookies.language) {
      changeLanguage('en');
    }
  }, []);
  const changeLanguage = (language) => {
    i18n.changeLanguage(language);
    setCookie('language', language);
  };

  const submitOrganization = () => {
    if (haveAccount) {
      handleSignIn();
    } else {
      handleSignUp();
    }
  };

  const handleLoadOrganization = (event) => {
    if (haveAccount && event.target.value) {
      getOrganizationInfo(event.target.value).then((response) => {
        if (response.status != REST_CALL_SUCCESS) {
          setLoadedOrganization('');
          return;
        }
        setLoadedOrganization(response.data.name);
      });
    }
  };

  const handleSignIn = () => {
    signIn(username, password).then((response) => {
      if (
        response.status === REST_CALL_SUCCESS &&
        response.data.token &&
        response.data.token.length > 10
      ) {
        setCookie('token', response.data.token, { path: '/' });
        window.location.href = '/';
      } else {
        addToast(t(response.data.message + '.' + response.data.resource), {
          appearance: 'error',
          autoDismiss: true,
        });
      }
    });
  };

  const handleSignUp = () => {
    signUp(name, username, password).then((result) => {
      if (
        result.status === REST_CALL_SUCCESS &&
        result.data.token &&
        result.data.token.length > 10
      ) {
        setCookie('token', result.data.token, { path: '/' });
        window.location.href = '/';
      } else {
        addToast(t(result.data.message), {
          appearance: 'error',
          autoDismiss: true,
        });
      }
    });
  };

  const renderUsernameTitle = () => {
    return (
      <span>
        <span>{t('organization.username')}</span>
        {loadedOrganization && (
          <span className={styles.environmentWrapper}>
            {' ['}
            <span> {t('login.environment')}</span>
            <span>
              (
              {haveAccount && loadedOrganization && (
                <a href="/signin">{loadedOrganization}</a>
              )}
              )
            </span>
            {' ]'}
          </span>
        )}
      </span>
    );
  };

  return (
    <Col>
      <Card className={styles.container} style={{ direction: direction }}>
        <Card.Header className={styles.header}>
          <img width="50px" height="50px" src="/logo.png"></img>
          <div className={styles.kudaWelcomeStatus}>{t('welcome')}</div>
          <div>
            <select
              className={styles.languageButtonWrapper}
              onChange={(e) => changeLanguage(e.target.value)}
            >
              {getAvailableLanguages().map((item, index) => (
                <option
                  className={styles.languageButton}
                  variant="outline-primary"
                  key={index}
                  type="button"
                  size="sm"
                  value={item.value}
                >
                  {t(item.name)}
                </option>
              ))}
            </select>
          </div>
        </Card.Header>
        <Card.Body>
          <Form>
            {!haveAccount && (
              <Input
                title={t('organization.name')}
                value={name}
                placeholder={t('organization.name.placeholder')}
                changeHandler={(e) => setName(e.target.value)}
                keyUpHandler={(event) => {
                  if (event.code == 'Enter') {
                    submitOrganization();
                  }
                }}
                required
              ></Input>
            )}

            <Input
              onTitle={renderUsernameTitle}
              value={username}
              placeholder={t('organization.username.placeholder')}
              changeHandler={(event) => {
                let value = '';
                if (event.target.value) {
                  value = event.target.value;
                  if (!haveAccount) {
                    value = value.replace('.', '');
                  }
                }
                setUsername(value);
              }}
              keyUpHandler={(event) => {
                if (event.code == 'Enter') {
                  submitOrganization();
                }
              }}
              required
              blurHandler={handleLoadOrganization}
            ></Input>

            <Input
              title={t('organization.password')}
              value={password}
              placeholder={t('organization.password.placeholder')}
              changeHandler={(e) => setPassword(event.target.value)}
              keyUpHandler={(event) => {
                if (event.code == 'Enter') {
                  submitOrganization();
                }
              }}
              required
            ></Input>

            <Col className={styles.loginSubmit}>
              <Button
                variant="primary"
                type="button"
                className={styles.submit}
                onClick={submitOrganization}
              >
                {haveAccount
                  ? t('organization.login')
                  : t('organization.signup')}
              </Button>
            </Col>
          </Form>
        </Card.Body>
      </Card>

      <Col className="center m-1">
        <Button
          type="button"
          variant={!haveAccount ? 'outline-success' : 'outline-primary'}
          className={styles.signInUpSwitch}
          href="#"
          onClick={() => {
            setHaveAccount(!haveAccount);
            setUsername('');
            setPassword('');
          }}
        >
          {!haveAccount ? t('signin.haveaccount') : t('signin.newaccount')}
        </Button>
      </Col>
    </Col>
  );
};

export default Signin;
