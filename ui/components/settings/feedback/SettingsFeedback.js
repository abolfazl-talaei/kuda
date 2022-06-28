import { faEdit, faPlus, faTrash } from '@fortawesome/free-solid-svg-icons';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import React, { useEffect, useState } from 'react';
import { Button, Card, Col, FormControl, InputGroup } from 'react-bootstrap';
import { useTranslation } from 'react-i18next';
import { useToasts } from 'react-toast-notifications';
import { createSetting, REST_CALL_SUCCESS, setSetting } from '../../utils/apiCallUtil';
import { cloneJson } from '../../utils/commonUtil';
import {
  detectComponentPosition,
  detectTagDirection,
  getIconMarginClass,
} from '../../utils/languageUtil';
import { showSuccessfulMessage } from '../../utils/messageUtil';

const SettingsFeedback = (props) => {
  const { defaultFeedbackList } = props;
  const { t, i18n } = useTranslation();
  const { addToast } = useToasts();
  const direction = detectComponentPosition(i18n);
  const tagDirection = detectTagDirection(i18n);
  const [kudaDefaultFeedbacks, setKudaDefaultFeedbacks] = useState([]);

  const handleSaveDefaultFeedbacks = (event, index, id) => {
    setSetting(id, kudaDefaultFeedbacks[index].value).then((response) => {
      if (response.status != REST_CALL_SUCCESS) {
        return;
      }

      showSuccessfulMessage(addToast, t);
    });
  };

  const handleCreateDefaultFeedbacks = (event, index) => {
    createSetting(
      'feedback',
      'feedback',
      kudaDefaultFeedbacks[index].value
    ).then((response) => {
      if (response.status != REST_CALL_SUCCESS) {
        return;
      }
      setKudaDefaultFeedbacks([
        ...kudaDefaultFeedbacks.filter((item) => {
          return item.id;
        }),
        response.data,
        {},
      ]);
      showSuccessfulMessage(addToast, t);
    });
  };

  const handleDeleteDefaultFeedbacks = (event, index, id) => {
    deleteSetting(id).then((response) => {
      if (response.status != REST_CALL_SUCCESS) {
        return;
      }
      setKudaDefaultFeedbacks(
        kudaDefaultFeedbacks.filter((item) => {
          return item.id != id;
        })
      );
      showSuccessfulMessage(addToast, t);
    });
  };

  const handleKudaDefaultsFeedbackChange = (event, index) => {
    kudaDefaultFeedbacks[index].value = event.target.value;
    setKudaDefaultFeedbacks(cloneJson(kudaDefaultFeedbacks));
  };

  useEffect(() => {
    if (defaultFeedbackList) {
      setKudaDefaultFeedbacks(defaultFeedbackList);
    }
  }, [defaultFeedbackList]);

  return (
    <Card
      className="m-2"
      style={{ textAlign: direction, direction: tagDirection }}
    >
      <Card.Header className="flex-space-between">
        {t('settings.kuda.feedbacks.default.header')}
      </Card.Header>
      <Card.Body style={{ textAlign: direction }}>
        <Col className="m-3">
          {kudaDefaultFeedbacks.map((item, index) => (
            <InputGroup className="mb-3">
              <FormControl
                aria-describedby="feedback"
                key={item.id}
                onChange={() => handleKudaDefaultsFeedbackChange(event, index)}
                value={kudaDefaultFeedbacks[index].value}
              />
              {item.id && (
                <InputGroup.Append>
                  <Button
                    variant="outline-success"
                    className="m-1"
                    onClick={() =>
                      handleSaveDefaultFeedbacks(event, index, item.id)
                    }
                  >
                    <FontAwesomeIcon
                      className={getIconMarginClass(i18n, 1)}
                      icon={faEdit}
                      size="sm"
                    ></FontAwesomeIcon>
                    {t('save')}
                  </Button>
                  <Button
                    variant="outline-danger"
                    className="m-1"
                    onClick={() =>
                      handleDeleteDefaultFeedbacks(event, index, item.id)
                    }
                  >
                    <FontAwesomeIcon
                      className={getIconMarginClass(i18n, 1)}
                      icon={faTrash}
                      size="sm"
                    ></FontAwesomeIcon>
                    {t('delete')}
                  </Button>
                </InputGroup.Append>
              )}

              {!item.id && (
                <InputGroup.Append>
                  <Button
                    variant="outline-primary"
                    className="m-1"
                    onClick={() => handleCreateDefaultFeedbacks(event, index)}
                  >
                    <FontAwesomeIcon
                      className={getIconMarginClass(i18n, 1)}
                      icon={faPlus}
                      size="sm"
                    ></FontAwesomeIcon>
                    {t('new')}
                  </Button>
                </InputGroup.Append>
              )}
            </InputGroup>
          ))}
        </Col>
      </Card.Body>
    </Card>
  );
};

export default SettingsFeedback;
