import { faEdit } from '@fortawesome/free-solid-svg-icons';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import React, { useEffect, useRef, useState } from 'react';
import {
  Button,
  Card,
  Col,
  Form,
  FormCheck,
  FormControl,
  InputGroup,
} from 'react-bootstrap';
import { useTranslation } from 'react-i18next';
import { useToasts } from 'react-toast-notifications';
import {
  getAvailablePermissions,
  getOrganizationPermissions,
  REST_CALL_SUCCESS,
  updatePassword,
} from '../../utils/apiCallUtil';
import {
  detectComponentPosition,
  detectTagDirection,
  getIconMarginClass,
} from '../../utils/languageUtil';
import { showSuccessfulMessage } from '../../utils/messageUtil';

const SettingsAAA = (props) => {
  const { loginInfo } = props;
  const { t, i18n } = useTranslation();
  const { addToast } = useToasts();
  const direction = detectComponentPosition(i18n);
  const tagDirection = detectTagDirection(i18n);
  const defaultCredential = useRef(null);
  const readerCredential = useRef(null);
  const adminCredential = useRef(null);
  const [userconfigs, setUserConfigs] = useState([
    {
      id: 'default',
      username: '[organization]',
      title: 'user.default',
      pattern: '',
      role: 'defaultAuthorization',
      permissions: [],
      credential: defaultCredential,
    },
    {
      id: 'reader',
      username: '[organization].reader',
      title: 'user.reader',
      pattern: '.reader',
      role: 'readerAuthorization',
      permissions: [],
      credential: readerCredential,
    },
    {
      id: 'admin',
      username: '[organization].admin',
      title: 'user.admin',
      pattern: '.admin',
      role: 'adminAuthorization',
      permissions: [],
      credential: adminCredential,
    },
  ]);

  const savePassword = (userconfig) => {
    updatePassword(
      loginInfo.username + userconfig.pattern,
      userconfig.credential.current.value
    ).then((response) => {
      if (response.status != REST_CALL_SUCCESS) {
        return;
      }
      showSuccessfulMessage(addToast, t);
    });
  };
  const createPermissions = (allPermissions, relatedPermissions) => {
    let instance = createArrayCopy(allPermissions);
    for (let permission of instance) {
      let found = relatedPermissions.filter(
        (item) => item.permission === permission.permission
      );
      if (found.length) {
        permission.id = found[0].id;
      }
    }
    return instance;
  };

  const createArrayCopy = (array) => {
    let newCopy = [];

    for (let item of array) {
      newCopy.push(Object.assign([], item));
    }
    return newCopy;
  };

  useEffect(() => {
    getAvailablePermissions().then((allPermissions) => {
      if (allPermissions.status != REST_CALL_SUCCESS) {
        return;
      }
      getOrganizationPermissions().then((relatedPermissions) => {
        let list = [];
        for (let userconfig of userconfigs) {
          list.push({
            id: userconfig.id,
            title: userconfig.title,
            username: userconfig.username,
            pattern: userconfig.pattern,
            role: userconfig.role,
            credential: userconfig.credential,
            permissions: createPermissions(
              allPermissions.data,
              relatedPermissions.data[userconfig['role']]
            ),
          });
        }
        setUserConfigs(list);
      });
    });
  }, []);

  const getUserSettingTemplate = (userconfig) => {
    return (
      <Col>
        <InputGroup className="mb-3">
          <InputGroup.Prepend>
            <Form.Label className="m-2 w-6" tooltip="test">
              {t(userconfig.title)}
            </Form.Label>
          </InputGroup.Prepend>
          <FormControl
            aria-describedby="userinfo"
            key={userconfig.id}
            ref={userconfig.credential}
            placeholder={t('change.password.placeholder')}
          />

          <InputGroup.Append>
            <Button
              variant="outline-success"
              className="m-1"
              onClick={() => savePassword(userconfig)}
            >
              <FontAwesomeIcon
                className={getIconMarginClass(i18n, 1)}
                icon={faEdit}
                size="sm"
              ></FontAwesomeIcon>
              {t('save')}
            </Button>
          </InputGroup.Append>
          {userconfig.permissions && (
            <InputGroup.Append className="m-2">
              <Col>
                {userconfig.permissions.map((permission) => (
                  <FormCheck
                    inline
                    checked={permission.id}
                    type="checkbox"
                    disabled
                    label={t(permission.permission)}
                  ></FormCheck>
                ))}
              </Col>
            </InputGroup.Append>
          )}
        </InputGroup>
      </Col>
    );
  };
  useEffect(() => {}, []);
  return (
    <Card
      className="m-2"
      style={{ textAlign: direction, direction: tagDirection }}
    >
      <Card.Header className="flex-space-between">
        {t('settings.kuda.aaa.header')}
      </Card.Header>
      <Card.Body style={{ textAlign: direction }}>
        <Col className="m-3">
          {userconfigs &&
            userconfigs.map((userconfig) => getUserSettingTemplate(userconfig))}
        </Col>
      </Card.Body>
    </Card>
  );
}

export default SettingsAAA;