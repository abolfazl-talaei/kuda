import React, { useEffect, useState } from 'react';
import { getSettings, REST_CALL_SUCCESS } from '../utils/apiCallUtil';
import SettingsAAA from './aaa/SettingsAAA';
import SettingsCalculation from './calculation/SettingsCalculation';
import SettingsDuration from './duration/SettingsDuration';
import SettingsFeedback from './feedback/SettingsFeedback';
import styles from './SettingsForm.module.css';
import SettingsShowKuda from './show/SettingsShowKuda';

const SettingsForm = (props) => {
  const { loginInfo } = props;
  const [kudaDuration, setKudaDuration] = useState(0);
  const [feedbackApply, setFeedbackApply] = useState(false);
  const [kudaDefaultFeedbacks, setKudaDefaultFeedbacks] = useState([]);
  const [kudaShowStatusDefault, setKudaShowStatusDefault] = useState(false);
  const [settingList, setSettingList] = useState([]);

  const loadSettings = () => {
    getSettings().then((response) => {
      if (response.status != REST_CALL_SUCCESS) {
        return;
      }
      let feedbackList = [];
      for (let setting of response.data) {
        if (setting.key === 'duration') {
          setKudaDuration(setting.value);
        } else if (setting.tag === 'feedback') {
          feedbackList.push(setting);
        } else if (setting.key === 'default.kuda.show') {
          setKudaShowStatusDefault(setting.value);
        } else if (setting.key === 'default.feedback.apply') {
          setFeedbackApply(setting.value);
        }
      }
      setKudaDefaultFeedbacks([...feedbackList, {}]);
      setSettingList(response.data);
    });
  };

  useEffect(() => {
    loadSettings();
  }, []);

  return (
    <span className={styles.settingsWrapper}>
      <SettingsShowKuda
        defaultSettingList={settingList}
        defaultShowStatus={kudaShowStatusDefault}
      ></SettingsShowKuda>

      <SettingsDuration
        defaultDuration={kudaDuration}
        defaultSettingList={settingList}
      ></SettingsDuration>

      <SettingsCalculation
        defaultSettingList={settingList}
        defaultFeedbackApply={feedbackApply}
      ></SettingsCalculation>

      <SettingsFeedback
        defaultFeedbackList={kudaDefaultFeedbacks}
      ></SettingsFeedback>

      <SettingsAAA loginInfo={loginInfo}></SettingsAAA>
    </span>
  );
};

export default SettingsForm;
