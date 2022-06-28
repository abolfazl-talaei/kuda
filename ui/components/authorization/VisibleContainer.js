export const VisibleContainer = ({ condition, children }) =>
  Boolean(condition) && children;
