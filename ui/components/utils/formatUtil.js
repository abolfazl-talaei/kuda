import { getLanguage } from './languageUtil';

export const formatDate = (i18n, dateString) => {
  if (dateString) {
    let date = new Date(dateString);
    return (
      date.toLocaleDateString(getLanguage(i18n)) +
      ' - ' +
      date.toLocaleTimeString(getLanguage(i18n))
    );
  }
  return '';
};

export const toPersianNumber = (str) => {
  return str.replace(/[0-9]/g, (chr) => {
    var persian = ['۰', '۱', '۲', '۳', '۴', '۵', '۶', '۷', '۸', '۹'];
    return persian[chr];
  });
};
