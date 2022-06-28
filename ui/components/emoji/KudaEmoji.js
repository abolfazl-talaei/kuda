import React, { useEffect, useState } from 'react';

import { Picker } from 'emoji-mart';
import faLanguage from '../../i18n/emoji/fa.json';
import enLanguage from '../../i18n/emoji/en.json';

const KudaEmoji = ({ selectHandler, show, i18n }) => {
  const [i18nLanguage, setI18nLanguage] = useState(enLanguage);

  useEffect(() => {
    setI18nLanguage(i18n.language === 'fa' ? faLanguage : enLanguage);
  }, [i18n]);

  return (
    <Picker
      style={{
        position: 'absolute',
        right: '8rem',
        marginTop: '1rem',
        display: show ? 'block' : 'none',
      }}
      i18n={i18nLanguage}
      onSelect={selectHandler}
    />
  );
};

export default KudaEmoji;
