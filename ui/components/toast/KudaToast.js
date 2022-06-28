import React from 'react';
import { useTranslation } from 'react-i18next';
import { DefaultToast } from 'react-toast-notifications';
import { detectComponentPosition } from '../utils/languageUtil';
import styles from './KudaToast.module.css';

export const KudaToast = ({ children, ...props }) => {
  const { i18n } = useTranslation();
  const direction = detectComponentPosition(i18n);
  return (
    <DefaultToast
      {...props}
      style={{ textAlign: direction, fontFamily: 'Sahel'}}
    >
      {children}
    </DefaultToast>
  );
};
