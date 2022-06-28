import React from 'react';
import { Button, Card, Jumbotron } from 'react-bootstrap';
import styles from './ActionArea.module.css';

const ActionArea = (props) => {
  const { title, description, actionText, action, url, direction } = props;

  return (
    <Card style={{ textAlign: direction }} className={styles.container}>
      <Card.Body className={styles.cardBody}>
        <Jumbotron style={{ textAlign: direction }}>
          <h1>{title}</h1>
          <p>{description}</p>
          <p>
            {action && (
              <Button variant="primary" onClick={() => action()}>
                {actionText}
              </Button>
            )}
            <Button variant="primary" href={url} className={styles.button}>
              {actionText}
            </Button>
          </p>
        </Jumbotron>
      </Card.Body>
    </Card>
  );
};

export default ActionArea;
