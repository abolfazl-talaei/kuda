import { faGrinAlt } from '@fortawesome/free-solid-svg-icons';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import 'emoji-mart/css/emoji-mart.css';
import React, { useEffect, useRef, useState } from 'react';
import { Alert, Button, Form, Row } from 'react-bootstrap';
import { useTranslation } from 'react-i18next';
import { useToasts } from 'react-toast-notifications';
import KudaEmoji from '../emoji/KudaEmoji';
import Input from '../input/Input';
import Select from '../select/Select';
import {
  createNewKuda,
  getKudaDuration,
  getKudaFeedbackTypes,
  getUsers,
  REST_CALL_SUCCESS,
} from '../utils/apiCallUtil';
import { toPersianNumber } from '../utils/formatUtil';
import { detectTagDirection } from '../utils/languageUtil';
import styles from './KudaCreateForm.module.css';

const UserCreateForm = () => {
  const { t, i18n } = useTranslation();
  const direction = detectTagDirection(i18n);
  const [description, setDescription] = useState('');
  const [kudaDuration, setKudaDuration] = useState('1');
  const [fromUser, setFromUser] = useState('');
  const [fromUsers, setFromUsers] = useState([]);
  const [kudaType, setKudaType] = useState('');
  const [kudaFeedbackType, setKudaFeedbackType] = useState('');
  const [kudaFeedbackTypes, setKudaFeedbackTypes] = useState([]);
  const [validated, setValidated] = useState(false);
  const [showEmoji, setShowEmoji] = useState(false);
  const { addToast } = useToasts();
  const wrapperRef = useRef(null);
  const [requestId, setRequestId] = useState('');
  useOutsideAlerter(wrapperRef);

  function useOutsideAlerter(ref) {
    useEffect(() => {
      /**
       * Alert if clicked on outside of element
       */
      function handleClickOutside(event) {
        if (ref.current && !ref.current.contains(event.target)) {
          setShowEmoji(false);
        }
      }
      // Bind the event listener
      // document.addEventListener('mousedown', handleClickOutside);
      return () => {
        // Unbind the event listener on clean up
        // document.removeEventListener('mousedown', handleClickOutside);
      };
    }, [ref]);
  }

  const handleSubmit = (event) => {
    const form = event.currentTarget.form;
    setValidated(true);
    if (form && form.checkValidity() === false) {
      event.preventDefault();
      event.stopPropagation();
    } else {
      createKuda();
    }
  };

  const kudaTypes = [
    {
      name: 'thanks',
      value: 'thanks',
    },
    {
      name: 'feedbacks',
      value: 'feedbacks',
    },
  ];

  const handleFromUser = (e) => {
    setFromUser(e.target.value);
  };
  const handleKudaType = (e) => {
    setKudaType(e.target.value);
  };
  const handleKudaFeedbackType = (e) => {
    let selectedValue = e.target.value;
    setKudaFeedbackType(selectedValue);
    setDescription(
      kudaFeedbackTypes.filter((item) => {
        return item.value === selectedValue;
      })[0].name
    );
  };

  const createKuda = () => {
    createNewKuda(fromUser, description, kudaType).then((response) => {
      if (response.status != REST_CALL_SUCCESS) {
        addToast(t(response.data.message + '.' + response.data.resource), {
          appearance: 'error',
          autoDismiss: true,
        });
      } else {
        setRequestId(response.data.requestId);
        setValidated(false);
        setDescription('');
        setFromUser('');
        setKudaType('');
        setKudaFeedbackType('');
      }
    });
  };

  useEffect(() => {
    getKudaFeedbackTypes().then((response) => {
      if (response.status != REST_CALL_SUCCESS) {
        return;
      }
      setKudaFeedbackTypes(response.data);
    });

    getKudaDuration().then((response) => {
      if (response.status != REST_CALL_SUCCESS) {
        return;
      }
      setKudaDuration(response.data.duration);
    });

    getUsers().then((response) => {
      if (response.status != REST_CALL_SUCCESS) {
        return;
      }
      let list = [];
      for (let item of response.data) {
        list.push({
          name: item.name,
          value: item.id,
        });
      }
      setFromUsers(list);
    });
  }, []);

  const selectHandler = (emoji) => {
    setDescription(description + ' ' + emoji.native);
  };

  return (
    <Row className="m-3" style={{ direction: direction }}>
      {requestId && (
        <Alert variant="success" className={styles.latestKuda}>
          {t('latest.kuda.request.id.text')} {requestId}
        </Alert>
      )}

      <Form noValidate validated={validated}>
        <Input
          title={t('kuda.duration')}
          value={
            direction === 'rtl'
              ? toPersianNumber(kudaDuration + '')
              : kudaDuration
          }
          placeholder={t('kuda.duration.placeholder')}
          disabled={true}
        ></Input>

        <Select
          required={true}
          className="mt-3"
          title={t('kuda.new.from')}
          options={fromUsers}
          value={fromUser}
          placeholder={t('kuda.new.from.placeholder')}
          defaultValue={fromUser}
          onChange={handleFromUser}
        ></Select>

        <Select
          required={true}
          className="mt-3"
          title={t('kuda.new.type')}
          options={kudaTypes}
          value={kudaType}
          onChange={handleKudaType}
          defaultValue={kudaType}
          placeholder={t('kuda.new.type.placeholder')}
        ></Select>

        {kudaType === 'feedbacks' && (
          <Select
            className="mt-3"
            title={t('kuda.new.feedback.type')}
            options={kudaFeedbackTypes}
            value={kudaFeedbackType}
            onChange={handleKudaFeedbackType}
            defaultValue={kudaFeedbackType}
            placeholder={t('kuda.new.feedback.type.placeholder')}
          ></Select>
        )}

        <KudaEmoji i18n={i18n} selectHandler={selectHandler} show={showEmoji} />
        <span ref={wrapperRef}>
          <Input
            onTitle={() => {
              return (
                <span>
                  {t('kuda.new.description')}
                  <FontAwesomeIcon
                    className={styles.emoji}
                    icon={faGrinAlt}
                    onClick={() => setShowEmoji(!showEmoji)}
                  />
                </span>
              );
            }}
            value={description}
            changeHandler={(e) => setDescription(e.target.value)}
            placeholder={t('kuda.description.placeholder')}
            type="textarea"
          ></Input>
        </span>

        <div className={styles.buttonWrapper}>
          <Button
            className={styles.button}
            variant="primary"
            type="button"
            onClick={handleSubmit}
          >
            {t('kuda.create')}
          </Button>
        </div>
      </Form>
    </Row>
  );
};

export default UserCreateForm;
