import React, { useEffect, useState } from 'react';
import { Button, Card, Col } from 'react-bootstrap';
import { useTranslation } from 'react-i18next';
import { useToasts } from 'react-toast-notifications';
import Switch from '../../switch/Switch';
import {
  changeKudaShowStatus,
  createSetting,
  REST_CALL_SUCCESS,
  setSetting,
} from '../../utils/apiCallUtil';
import { showSuccessfulMessage } from '../../utils/messageUtil';
import {
  detectComponentPosition,
  detectTagDirection,
} from '../../utils/languageUtil';

const SettingsShowKuda = (props) => {
  const { defaultShowStatus, defaultSettingList } = props;
  const { t, i18n } = useTranslation();
  const { addToast } = useToasts();
  const direction = detectComponentPosition(i18n);
  const tagDirection = detectTagDirection(i18n);
  const [kudaShowStatusDefault, setKudaShowStatusDefault] = useState(false);
  const [settingList, setSettingList] = useState([]);

  const handleKudaShowStatusDefault = (value) => {
    setKudaShowStatusDefault(value);
    let id = getSettingId('default.kuda.show');
    if (id >= 0) {
      setNewSetting(id, value);
    } else {
      createSetting('default.kuda.show', 'general.settings', true).then(
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
  const handleShowKudasClicked = () => {
    changeKudaShowStatus(true).then((resposne) => {
      if (resposne.status != REST_CALL_SUCCESS) {
        return;
      }
      showSuccessfulMessage(addToast, t);
    });
  };

  const handleHideKudasClicked = () => {
    changeKudaShowStatus(false).then((resposne) => {
      if (resposne.status != REST_CALL_SUCCESS) {
        return;
      }
      showSuccessfulMessage(addToast, t);
    });
  };

  useEffect(() => {
    if (defaultShowStatus) {
      setKudaShowStatusDefault(defaultShowStatus);
    }
    if (defaultSettingList) {
      setSettingList(defaultSettingList);
    }
  }, [defaultShowStatus, defaultSettingList]);
  return (
    <Card
      className="m-2"
      style={{ textAlign: direction, direction: tagDirection }}
    >
      <Card.Header className="flex-space-between">
        {t('settings.kuda.change.show.status.header')}
      </Card.Header>
      <Card.Body style={{ textAlign: direction }}>
        <span className=" text-center">
          <Button
            size="sm"
            variant="outline-success"
            type="button"
            className="m-1"
            onClick={handleShowKudasClicked}
          >
            {t('settings.kuda.show')}
          </Button>
          <Button
            size="sm"
            variant="outline-danger"
            type="button"
            className="m-1"
            onClick={handleHideKudasClicked}
          >
            {t('settings.kuda.hide')}
          </Button>
        </span>
        <hr />
        <Col className="m-3">
          <Switch
            onChange={handleKudaShowStatusDefault}
            value={kudaShowStatusDefault}
            title={t('settings.kuda.show.default')}
          ></Switch>
        </Col>
      </Card.Body>
    </Card>
  );
};

export default SettingsShowKuda;
