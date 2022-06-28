import React, { useEffect, useState, useRef } from 'react';
import { Card } from 'react-bootstrap';
import { useTranslation } from 'react-i18next';
import {
  detectComponentPosition,
  detectTagDirection,
} from '../../utils/languageUtil';
import styles from './GeneralList.module.css';

const GeneralList = (props) => {
  const { items, onHeaderRender, onBodyRender, border, highlight } = props;
  // const ref = useRef(items);
  const [itemsState, setItemsState] = useState([]);
  const { i18n } = useTranslation();
  const direction = detectComponentPosition(i18n);
  const tagDirection = detectTagDirection(i18n);

  useEffect(() => {
    setItemsState(items);
  }, [items]);

  const changeToCurrent = (index) => {
    let newArray = itemsState.slice();
    for (let item of newArray) {
      if (item.selected) {
        item.selected = false;
      }
    }
    newArray[index].selected = true;
    setItemsState(newArray);
  };

  const getCardClassName = (item) => {
    if (highlight) {
      return item.selected ? styles.selected : styles.nonSelected;
    }
    return styles.normal;
  };
  return (
    <div className="m-5">
      {itemsState &&
        itemsState.map((item, idx) => (
          <Card
            border={border ? border : 'primary'}
            key={idx}
            style={{ textAlign: direction, direction: tagDirection }}
            className={getCardClassName(item)}
            onClick={() => changeToCurrent(idx)}
          >
            <Card.Header className={styles.listItemHeader}>
              {onHeaderRender(item)}
            </Card.Header>
            <Card.Body style={{ textAlign: direction, fontFamily: 'Sahel' }}>
              {onBodyRender(item)}
            </Card.Body>
          </Card>
        ))}
    </div>
  );
};

export default GeneralList;
