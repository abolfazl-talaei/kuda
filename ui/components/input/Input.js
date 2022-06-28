import React, { useEffect, useRef } from 'react';
import { render } from 'react-dom';
import { Form } from 'react-bootstrap';
import styles from './Input.module.css';
import data from '@emoji-mart/data';
import { Picker } from 'emoji-mart';

const Input = ({
  title,
  onTitle,
  value,
  changeHandler,
  keyUpHandler,
  blurHandler,
  required,
  placeholder,
  hint,
  type,
  disabled,
}) => {
  return (
    <Form.Group
      controlId="formBasicNickname"
      className={styles.container}
    >
      <Form.Label className={styles.title}>
        {title ? title : onTitle()}
      </Form.Label>
      {type === 'textarea' && (
        <Form.Control
          className={styles.input}
          as="textarea"
          rows={3}
          value={value}
          onChange={changeHandler}
          onBlur={blurHandler}
          onKeyUp={keyUpHandler}
          placeholder={placeholder}
          required={required}
        />
      )}

      {type !== 'textarea' && (
        <Form.Control
          className={styles.input}
          type="input"
          value={value}
          onChange={changeHandler}
          onBlur={blurHandler}
          onKeyUp={keyUpHandler}
          placeholder={placeholder}
          disabled={disabled}
          required={required}
        />
      )}
      <Form.Text className={styles.hint}>{hint}</Form.Text>
    </Form.Group>
  );
};

export default Input;
