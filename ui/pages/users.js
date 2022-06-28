import React from 'react';
import Head from 'next/head';
import Footer from '../components/Footer';
import KudaList from '../components/list/KudaList';
import TopNavbar from '../components/navbar/TopNavbar';
import UserListForm from '../components/user/UserListForm';
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

        <UserListForm></UserListForm>
      </main>

      <Footer></Footer>
    </div>
  );
};

export default Users;
