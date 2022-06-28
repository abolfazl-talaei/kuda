import React from 'react';
import Head from 'next/head';
import Footer from '../components/Footer';
import TopNavbar from '../components/navbar/TopNavbar';
import UserCreateForm from '../components/user/UserCreateForm';
import styles from '../styles/Home.module.css';

const NewUser = (props) => {
  const { permissions, loginInfo } = props;

  return (
    <div className={styles.container}>
      <Head>
        <title>Kuda</title>
        <link rel="icon" href="/favicon.ico" />
      </Head>

      <main className={styles.main}>
        <TopNavbar permissions={permissions} loginInfo={loginInfo}></TopNavbar>

        <UserCreateForm></UserCreateForm>
      </main>

      <Footer></Footer>
    </div>
  );
};

export default NewUser;
