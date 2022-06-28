import React from 'react';
import { Card } from 'react-bootstrap';
import { useTranslation } from 'react-i18next';
import { detectComponentPosition } from '../utils/languageUtil';
import styles from './Panel.module.css';

const Panel = (props) => {
  const { i18n } = useTranslation();
  const direction = detectComponentPosition(i18n);
  const { header, title, onBodyRender, headerType, onHeaderRender, type } =
    props;

  const getClassName = (panelType) => {
    switch (panelType) {
      case 'warning':
        return styles.warning;
      case 'danger':
        return styles.danger;
      case 'info':
        return styles.info;
      default:
        return styles.default;
    }
  };
  return (
    <div className={styles.container}>
      <Card className={getClassName(type)} style={{ textAlign: direction }}>
        {(!headerType || headerType === 'static') && (
          <Card.Header>{header}</Card.Header>
        )}
        {headerType && headerType === 'dynamic' && (
          <Card.Header>{onHeaderRender()}</Card.Header>
        )}
        <Card.Body style={{ textAlign: direction }}>
          <Card.Title> {title} </Card.Title>
          <Card.Text>{onBodyRender()}</Card.Text>
        </Card.Body>
      </Card>
    </div>
  );
};

export default Panel;
