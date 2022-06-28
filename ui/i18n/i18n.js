import i18n from 'i18next';
import { initReactI18next } from 'react-i18next';
const en = require('./locales/en/translation.json');
const fa = require('./locales/fa/translation.json');

const resources = {
  fa: {
    translation: fa,
  },
  en: {
    translation: en,
  },
};

i18n.use(initReactI18next).init({
  resources,
  lng: 'fa',
  initImmediate: false,
  keySeparator: false,
  interpolation: {
    escapeValue: false,
  },
});

export default i18n;
