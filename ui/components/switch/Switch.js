import { faCog } from '@fortawesome/free-solid-svg-icons';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import React, { useEffect, useState } from 'react';
import styles from './Switch.module.css';

const Switch = (props) => {
  const { title, value, onChange } = props;
  const [savedValue, setSavedValue] = useState(false);

  const handleSwitchChange = (event) => {
    let newValue = event.target.value === 'false' ? 'true' : 'false';
    if (onChange) {
      onChange(newValue);
    }
    setSavedValue(newValue);
  };
  useEffect(() => {
    setSavedValue(value);
  }, [value]);
  return (
    <span>
      <FontAwesomeIcon size="sm" icon={faCog}></FontAwesomeIcon>
      <span className="mr-1 ml-1">{title}</span>
      <label className={styles.switch}>
        <input
          className={styles.switchInput}
          checked={savedValue === 'true'}
          value={savedValue}
          type="checkbox"
          onChange={handleSwitchChange}
        />
        <span className={styles.slider}></span>
      </label>
    </span>
  );
};

export default Switch;
