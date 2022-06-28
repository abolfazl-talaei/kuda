import React, { useEffect, useRef, useState } from 'react';
import { Col } from 'react-bootstrap';
import { useTranslation } from 'react-i18next';
import { getKudas, REST_CALL_SUCCESS } from '../utils/apiCallUtil';
import {
  detectComponentPosition,
  detectTagDirection,
} from '../utils/languageUtil';
import GeneralList from './general/GeneralList';
import styles from './KudaList.module.css';

import KudaItemHeader from '../kuda/item/KudaItemHeader';

const KudaList = (props) => {
  const [items, setItems] = useState([]);
  const ref = useRef(items);
  const { t, i18n } = useTranslation();
  const direction = detectComponentPosition(i18n);
  const tagDirection = detectTagDirection(i18n);

  useEffect(() => {}, [items]);

  useEffect(() => {
    setItems([]);
    getKudas().then((response) => {
      if (response.status == REST_CALL_SUCCESS && response.data.length > 0) {
        response.data[0].selected = true;
        setItems(response.data);
      }
    });

    ref.current = items;
    return () => {};
  }, []);

  const onBodyRender = (item) => {
    return <div className={styles.itemBody}>{item.description}</div>;
  };

  const onHeaderRender = (item) => {
    return <KudaItemHeader item={item}></KudaItemHeader>;
  };
  return (
    <Col className="center">
      <GeneralList
        border="info"
        items={items}
        onHeaderRender={onHeaderRender}
        onBodyRender={onBodyRender}
        highlight={true}
      ></GeneralList>
      {!items.length && (
        <span style={{ textAlign: direction, direction: tagDirection }}>
          {t('notfound.kuda')}
        </span>
      )}
    </Col>
  );
};

export default KudaList;
