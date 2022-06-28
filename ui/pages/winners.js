import Head from 'next/head';
import React, { useEffect, useState } from 'react';
import { Button, Col } from 'react-bootstrap';
import { useTranslation } from 'react-i18next';
import Footer from '../components/Footer';
import TopNavbar from '../components/navbar/TopNavbar';
import {
  getKudaDuration,
  REST_CALL_SUCCESS,
} from '../components/utils/apiCallUtil';
import { toPersianNumber } from '../components/utils/formatUtil';
import { detectTagDirection } from '../components/utils/languageUtil';
import WinnerList from '../components/winner/WinnerList';
import styles from '../styles/Home.module.css';

const Winners = (props) => {
  const { permissions, loginInfo } = props;
  const [durationList, setDurationList] = useState([]);
  const [duration, setDuration] = useState(0);
  const { t, i18n } = useTranslation();
  const direction = detectTagDirection(i18n);

  useEffect(() => {
    getKudaDuration().then((response) => {
      if (response.status != REST_CALL_SUCCESS) {
        return;
      }
      let list = [];
      for (let i = 0; i < response.data.duration - 1; i++) {
        list.push(i);
      }
      setDurationList(list);
    });
  }, []);

  const changeDuration = (newDuration) => {
    setDuration(newDuration);
  };

  return (
    <div className={styles.container}>
      <Head>
        <title>Kuda</title>
        <link rel="icon" href="/favicon.ico" />
      </Head>

      <main className={styles.main}>
        <TopNavbar permissions={permissions} loginInfo={loginInfo}></TopNavbar>

        <Col
          className="m-3"
          style={{ direction: direction, fontFamily: 'Sahel' }}
        >
          <span style={{ overflowX: 'auto', width: '100%' }}>
            <span className={styles.durationTitle}>{t('kuda.duration')}</span>

            {durationList.map((item) => (
              <Button
                variant="success"
                type="button"
                key={item}
                className="m-1"
                onClick={() => changeDuration(item + 1)}
              >
                <b>
                  {direction === 'rtl'
                    ? toPersianNumber(item + 1 + '')
                    : item + 1}
                </b>
              </Button>
            ))}
          </span>
        </Col>
        <WinnerList duration={duration}></WinnerList>
      </main>

      <Footer></Footer>
    </div>
  );
};

export default Winners;
