import React, { useEffect, useState } from 'react';
import { Col, Row } from 'react-bootstrap';
import styles from './KudaItemHeader.module.css';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import {
  faEnvelope,
  faEnvelopeOpen,
  faHeart,
  faCheck,
  faWrench,
} from '@fortawesome/free-solid-svg-icons';
import { formatDate } from '../../utils/formatUtil';
import { useToasts } from 'react-toast-notifications';
import { useTranslation } from 'react-i18next';
import { detectOppositeComponentPosition } from '../../utils/languageUtil';
import { changeReadStatus, REST_CALL_SUCCESS } from '../../utils/apiCallUtil';

const KudaItemHeader = ({ item }) => {
  const { addToast } = useToasts();
  const { t, i18n } = useTranslation();
  const [itemData, setItemData] = useState();

  useEffect(() => {
    setItemData(item);
  }, []);

  const changeReadStatusHandler = (item) => {
    changeReadStatus(item.id).then((response) => {
      if (response.status != REST_CALL_SUCCESS) {
        addToast(t(response.data.message + '.' + response.data.resource), {
          appearance: 'error',
          autoDismiss: true,
        });
      } else {
        setItemData({ ...item, readStatus: !item.readStatus });
      }
    });
  };

  return (
    <>
      {itemData && (
        <Row className={styles.header}>
          <Col>
            {t('from')} <strong>{itemData.fromUser}</strong>
          </Col>
          <Col
            style={{ textAlign: detectOppositeComponentPosition(i18n) }}
            className={styles.time}
          >
            {formatDate(i18n, itemData.creationTime)}
            <FontAwesomeIcon
              color={itemData.readStatus ? 'gray' : 'orange'}
              className="ml-1 mr-1"
              icon={itemData.readStatus ? faEnvelopeOpen : faEnvelope}
            ></FontAwesomeIcon>
            <span>
              {itemData.kudaType && (
                <FontAwesomeIcon
                  color={itemData.kudaType === 'THANKS' ? 'green' : 'red'}
                  className="ml-1 mr-1"
                  icon={itemData.kudaType === 'THANKS' ? faHeart : faWrench}
                ></FontAwesomeIcon>
              )}
            </span>
            <span className={styles.updateReadStatusWrapper}>
              <FontAwesomeIcon
                className={
                  styles.updateReadStatus +
                  ' ' +
                  (itemData.readStatus ? styles.read : styles.unRead)
                }
                icon={faCheck}
                onClick={() => changeReadStatusHandler(itemData)}
              ></FontAwesomeIcon>
            </span>
          </Col>
        </Row>
      )}
    </>
  );
};

export default KudaItemHeader;
