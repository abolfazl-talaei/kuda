import React from 'react';

const Footer = () => {
  const text = `@abolfazl-talaei | 2022 open source project. Contributions are welcomed.
  `;
  return (
    <div className="footer">
      <div onClick={() => alert(text)}>{text}</div>
    </div>
  );
};

export default Footer;
