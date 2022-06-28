import React from 'react';
import Head from 'next/head';
import Footer from '../components/Footer';
import TopNavbar from '../components/navbar/TopNavbar';
import SettingsForm from '../components/settings/SettingsForm';
import styles from '../styles/Home.module.css';

const Users = (props) => {
  const { permissions, loginInfo } = props;

  return (
    <div className={styles.container}>
      <Head>
        <title>Kuda</title>
        <link rel="icon" href="/favicon.ico" />
      </Head>

      <main className={styles.main}>
        <TopNavbar permissions={permissions} loginInfo={loginInfo}></TopNavbar>

        <SettingsForm loginInfo={loginInfo}></SettingsForm>
      </main>

      <Footer></Footer>
    </div>
  );
};

export default Users;
