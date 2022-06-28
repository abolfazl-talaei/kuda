import { faSquare, faUser } from '@fortawesome/free-solid-svg-icons';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import React, { useEffect, useState } from 'react';
import { useTranslation } from 'react-i18next';
import GeneralList from '../list/general/GeneralList';
import { getUsers, REST_CALL_SUCCESS } from '../utils/apiCallUtil';
import { toPersianNumber } from '../utils/formatUtil';
import { detectTagDirection } from '../utils/languageUtil';

const UserListForm = (props) => {
  const [users, setUsers] = useState();
  const { t, i18n } = useTranslation();
  const direction = detectTagDirection(i18n);

  useEffect(() => {
    getUsers().then((response) => {
      if (response.status != REST_CALL_SUCCESS) {
        return;
      }
      if (response.data && response.data.length > 0) {
        response.data[0].selected = true;
      }
      setUsers(response.data);
    });
  }, []);
  const onHeaderRender = (item) => {
    return (
      <span>
        <FontAwesomeIcon
          color="teal"
          size="sm"
          icon={faSquare}
        ></FontAwesomeIcon>
        <span className="mr-1 ml-1">{item.name}</span>
        <FontAwesomeIcon color="teal" size="sm" icon={faUser}></FontAwesomeIcon>
        <span className="mr-1 ml-1">{item.username}</span>
      </span>
    );
  };
  const onBodyRender = (item) => {
    return (
      <span>
        {t('points')} :{' '}
        {direction === 'rtl'
          ? toPersianNumber(item.totalPoints + '')
          : item.totalPoints}
      </span>
    );
  };
  return (
    <GeneralList
      items={users}
      border="warning"
      onHeaderRender={onHeaderRender}
      onBodyRender={onBodyRender}
    ></GeneralList>
  );
};

export default UserListForm;
