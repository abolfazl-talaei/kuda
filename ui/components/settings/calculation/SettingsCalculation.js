import React, { useEffect, useState } from 'react';
import { Card, Col } from 'react-bootstrap';
import { useTranslation } from 'react-i18next';
import { useToasts } from 'react-toast-notifications';
import Switch from '../../switch/Switch';
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

const SettingsCalculation = (props) => {
  const { defaultFeedbackApply, defaultSettingList } = props;
  const { t, i18n } = useTranslation();
  const { addToast } = useToasts();
  const direction = detectComponentPosition(i18n);
  const tagDirection = detectTagDirection(i18n);
  const [feedbackApply, setFeedbackApply] = useState(false);
  const [settingList, setSettingList] = useState([]);

  const handleFeedbackApplyChange = (value) => {
    setFeedbackApply(value);
    let id = getSettingId('default.feedback.apply');
    if (id >= 0) {
      setNewSetting(id, value);
    } else {
      createSetting('default.feedback.apply', 'general.settings', true).then(
        (response) => {
          if (response.status != REST_CALL_SUCCESS) {
            return;
          }
          setSettingList(settingList.concat([response.data]));
        }
      );
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
    if (defaultFeedbackApply) {
      setFeedbackApply(defaultFeedbackApply);
    }
    if (defaultSettingList) {
      setSettingList(defaultSettingList);
    }
  }, [defaultFeedbackApply, defaultSettingList]);

  return (
    <Card
      className="m-2"
      style={{ textAlign: direction, direction: tagDirection }}
    >
      <Card.Header className="flex-space-between">
        {t('settings.kuda.calculation.header')}
      </Card.Header>
      <Card.Body style={{ textAlign: direction }}>
        <Col className="m-3">
          <Switch
            onChange={handleFeedbackApplyChange}
            value={feedbackApply}
            title={t('settings.kuda.calculation.feedbacks')}
          ></Switch>
        </Col>
      </Card.Body>
    </Card>
  );
}

export default SettingsCalculation;