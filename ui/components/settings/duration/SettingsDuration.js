import React, { useEffect, useState } from 'react';
import { Card, Col, Form } from 'react-bootstrap';
import { useTranslation } from 'react-i18next';
import { useToasts } from 'react-toast-notifications';
import {
  createSetting,
  REST_CALL_SUCCESS,
  setSetting,
} from '../../utils/apiCallUtil';
import {
  detectComponentPosition,
  detectTagDirection,
} from '../../utils/languageUtil';
import { showSuccessfulMessage } from '../../utils/messageUtil';

const SettingsDuration = (props) => {
  const { defaultDuration, defaultSettingList } = props;
  const { t, i18n } = useTranslation();
  const { addToast } = useToasts();
  const direction = detectComponentPosition(i18n);
  const tagDirection = detectTagDirection(i18n);
  const [kudaDuration, setKudaDuration] = useState(1);
  const [settingList, setSettingList] = useState([]);

  const handleKudaDurationChange = (event) => {
    const newValue = event.target.value;
    if (newValue <= 0) {
      return;
    }

    setKudaDuration(newValue);
    let id = getSettingId('duration');
    if (id >= 0) {
      setNewSetting(id, newValue);
    } else {
      createSetting('duration', 'general.settings', true).then((response) => {
        if (response.status != REST_CALL_SUCCESS) {
          return;
        }
        setSettingList(settingList.concat([response.data]));
      });
    }
  };

  const getSettingId = (key) => {
    for (let setting of settingList) {
      if (setting.key === key) {
        return setting.id;
      }
    }
    return -1;
  };
  const setNewSetting = (id, value) => {
    setSetting(id, value).then((response) => {
      if (response.status != REST_CALL_SUCCESS) {
        addToast(t(response.data.message), {
          appearance: 'error',
          autoDismiss: true,
        });
        return;
      }
      showSuccessfulMessage(addToast, t);
    });
  };
  useEffect(() => {
    if (defaultDuration) {
      setKudaDuration(defaultDuration);
    }
    if (defaultSettingList) {
      setSettingList(defaultSettingList);
    }
  }, [defaultDuration, defaultSettingList]);
  return (
    <Card
      className="m-2"
      style={{ textAlign: direction, direction: tagDirection }}
    >
      <Card.Header className="flex-space-between">
        {t('settings.kuda.duration.header')}
      </Card.Header>
      <Card.Body style={{ textAlign: direction }}>
        <Col className="m-3">
          <Form.Group controlId="formBasicDuration">
            <Form.Label>{t('kuda.duration')}</Form.Label>
            <Form.Control
              type="number"
              placeholder={t('kuda.duration.placeholder')}
              onChange={handleKudaDurationChange}
              value={kudaDuration}
            />
          </Form.Group>
        </Col>
      </Card.Body>
    </Card>
  );
};

export default SettingsDuration;
