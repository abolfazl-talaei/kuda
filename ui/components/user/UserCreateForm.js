import React, { useState } from 'react';
import { Button, Form, Row } from 'react-bootstrap';
import { useTranslation } from 'react-i18next';
import { useToasts } from 'react-toast-notifications';
import Input from '../input/Input';
import { createUser, REST_CALL_SUCCESS } from '../utils/apiCallUtil';
import { detectTagDirection } from '../utils/languageUtil';
import styles from './UserCreateForm.module.css';

const UserCreateForm = (props) => {
  const { t, i18n } = useTranslation();
  const direction = detectTagDirection(i18n);
  const [validated, setValidated] = useState(false);
  const [username, setUsername] = useState('');
  const [nickname, setNickname] = useState('');
  const { addToast } = useToasts();

  const handleNicknameChange = (event) => {
    setNickname(event.target.value);
  };
  const handleUsernameCHange = (event) => {
    setUsername(event.target.value);
  };

  const handleSubmit = (event) => {
    const form = event.currentTarget.form;
    setValidated(true);
    if (form && form.checkValidity() === false) {
      event.preventDefault();
      event.stopPropagation();
    } else {
      createNewUser();
    }
  };

  const createNewUser = () => {
    createUser(username, nickname).then((response) => {
      if (response.status != REST_CALL_SUCCESS) {
        addToast(t(response.data.message), {
          appearance: 'error',
          autoDismiss: true,
        });
        return;
      }
      addToast(t('success'), {
        appearance: 'success',
        autoDismiss: true,
      });
      setValidated(false);
      setUsername('');
      setNickname('');
    });
  };

  return (
    <Row className="m-3" style={{ direction: direction }}>
      <Form className="rtl" noValidate validated={validated}>
        <Input
          title={t('user.name')}
          value={nickname}
          placeholder={t('user.name.placeholder')}
          changeHandler={handleNicknameChange}
          hint={t('user.name.hint')}
        ></Input>

        <Input
          title={t('user.username')}
          value={username}
          placeholder={t('user.username.placeholder')}
          changeHandler={handleUsernameCHange}
        ></Input>

        <div className={styles.buttonWrapper}>
          <Button
            className="mt-3"
            variant="success"
            type="button"
            onClick={handleSubmit}
          >
            {t('user.register')}
          </Button>
        </div>
      </Form>
    </Row>
  );
};

export default UserCreateForm;
