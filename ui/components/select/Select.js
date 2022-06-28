import React, { useEffect, useState } from 'react';
import { useTranslation } from 'react-i18next';
import styles from './Select.module.css';

const Select = (props) => {
  const { title, options, placeholder, onChange, required, defaultValue } =
    props;
  const [value, setValue] = useState('');
  const { t } = useTranslation();
  const valueChanged = (event) => {
    setValue(event.target.value);
    if (onChange) {
      onChange(event, event.target.value);
    }
  };

  useEffect(() => {
    setValue(defaultValue);
  }, [defaultValue]);
  return (
    <div {...props}>
      <label className={styles.title} htmlFor="selectItem">
        {title}
      </label>
      <select
        required={required}
        id="selectItem"
        className={styles.select}
        onChange={valueChanged}
        value={value}
      >
        {placeholder && <option value=""> {placeholder}</option>}
        {options &&
          options.map((item, index) => (
            <option key={index} value={item.value} className={styles.option}>
              {t(item.name)}
            </option>
          ))}
      </select>
    </div>
  );
};

export default Select;
