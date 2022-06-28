import React from 'react';
import Head from 'next/head';
import Footer from '../components/Footer';
import TopNavbar from '../components/navbar/TopNavbar';
import KudaCreateForm from '../components/kuda/KudaCreateForm';
import styles from '../styles/Home.module.css';

const NewKuda = (props) => {
  const { permissions, loginInfo } = props;

  return (
    <div className={styles.container}>
      <Head>
        <title>Kuda</title>
        <link rel="icon" href="/favicon.ico" />
      </Head>

      <main className={styles.main}>
        <TopNavbar permissions={permissions} loginInfo={loginInfo}></TopNavbar>
        <KudaCreateForm></KudaCreateForm>
      </main>

      <Footer></Footer>
    </div>
  );
};

export default NewKuda;
