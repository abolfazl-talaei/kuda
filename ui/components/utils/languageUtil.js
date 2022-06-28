import '../../i18n/i18n';

export const getLanguage = (i18n) => {
  return i18n.language;
};
export const detectComponentPosition = (i18n) => {
  return i18n.language == 'fa' ? 'right' : 'left';
};

export const detectOppositeComponentPosition = (i18n) => {
  return i18n.language == 'fa' ? 'left' : 'right';
};

export const detectNavbarPosition = (i18n) => {
  return i18n.language == 'fa' ? 'ml-auto' : 'mr-auto';
};

export const detectTagDirection = (i18n) => {
  return i18n.language == 'fa' ? 'rtl' : 'ltr';
};

export const detectFlexDirection = (i18n) => {
  return i18n.language == 'fa' ? 'row-reverse' : 'row';
};

export const getAvailableLanguages = () => {
  return [
    { name: 'english', value: 'en' },
    { name: 'persian', value: 'fa' },
  ];
};

export const getIconMarginClass = (i18n, size) => {
  return getLanguage(i18n) == 'fa' ? 'ml-' + size : 'mr-' + size;
};
