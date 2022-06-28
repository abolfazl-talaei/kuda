const config = {
  verbose: true,
  moduleNameMapper: {
    'react-i18next': '<rootDir>/__mocks__/reacti18nextMock.js',
    '\\.(jpg|jpeg|png|gif|eot|otf|webp|svg|ttf|woff|woff2|mp4|webm|wav|mp3|m4a|aac|oga)$':
      '<rootDir>/__mocks__/fileMock.js',
    '\\.(scss|sass|css)$': '<rootDir>/.jest/identity-obj-proxy-esm.js',
  },
  modulePathIgnorePatterns: ['<rootDir>/i18n/'],
};

module.exports = config;
