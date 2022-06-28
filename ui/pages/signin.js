import React from 'react';
import Head from 'next/head';
import Footer from '../components/Footer';
import Signin from '../components/signin/Signin';
import styles from '../styles/Home.module.css';

const NewUser = () => {
  return (
    <div className={styles.container}>
      <Head>
        <title>Kuda</title>
        <link rel="icon" href="/favicon.ico" />
      </Head>

      <main className={styles.main}>
        <Signin></Signin>
      </main>

      <Footer></Footer>
    </div>
  );
};

export default NewUser;
