import { faUser } from '@fortawesome/free-solid-svg-icons';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import React, { useEffect, useState } from 'react';
import { Col } from 'react-bootstrap';
import { useTranslation } from 'react-i18next';
import i18n from '../../i18n/i18n';
import GeneralList from '../list/general/GeneralList';
import { getDurationWinners, REST_CALL_SUCCESS } from '../utils/apiCallUtil';
import { toPersianNumber } from '../utils/formatUtil';
import {
  detectComponentPosition,
  detectTagDirection,
} from '../utils/languageUtil';
import styles from './WinnerList.module.css';

const WinnerList = (props) => {
  const { duration } = props;
  const { t } = useTranslation();
  const [items, setItems] = useState([]);
  const direction = detectComponentPosition(i18n);
  const tagDirection = detectTagDirection(i18n);

  useEffect(() => {
    if (duration != 0) {
      getDurationWinners(duration).then((response) => {
        if (response.status != REST_CALL_SUCCESS) {
          return;
        }
        setItems(response.data);
      });
    }
  }, [duration]);

  const onHeaderRender = (item) => {
    return (
      <span className={styles.winnerHeader} name={item.username}>
        <FontAwesomeIcon
          icon={faUser}
          className={styles.user}
        ></FontAwesomeIcon>
        <b>{item.name}</b>
      </span>
    );
  };
  const onBodyRender = (item) => {
    return (
      <div className={styles.winnerBody}>
        <span>
          {t('thanks')} :{' '}
          {tagDirection === 'rtl'
            ? toPersianNumber(item.thanks + '')
            : item.thanks}
        </span>
        <span>
          {t('feedbacks')} :{' '}
          {tagDirection === 'rtl'
            ? toPersianNumber(item.feedbacks + '')
            : item.feedbacks}
        </span>
        <span>
          {t('points')} :{' '}
          {tagDirection === 'rtl'
            ? toPersianNumber(item.total + '')
            : item.total}
        </span>
      </div>
    );
  };
  return (
    <Col className="center">
      <GeneralList
        border="info"
        items={items}
        onBodyRender={onBodyRender}
        onHeaderRender={onHeaderRender}
      ></GeneralList>
      {!items.length && duration != 0 && (
        <span
          style={{
            textAlign: direction,
            direction: tagDirection,
            fontFamily: 'Sahel',
          }}
        >
          {t('notfound.winner')}
        </span>
      )}
      {duration == 0 && (
        <span
          style={{
            textAlign: direction,
            direction: tagDirection,
            fontFamily: 'Sahel',
          }}
        >
          {t('notselected.winner')}
        </span>
      )}
    </Col>
  );
};

export default WinnerList;
