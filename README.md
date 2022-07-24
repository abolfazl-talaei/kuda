# Kuda Application

![Logo](https://images2.imgbox.com/e2/fa/CfbD0Ods_o.png)

A simple application for your organization to better explain your feelings about your colleagues. In this application you could anonymously create thanks or feedbacks for others to provide a healthy circle in the organization. This action is somehow an implementation of Kudos (praise and honour received for an achievement).

## Requirements

1. [Docker and Docker Compose](https://www.google.com/url?sa=t&rct=j&q=&esrc=s&source=web&cd=&cad=rja&uact=8&ved=2ahUKEwjHjIHEs8_4AhVQuqQKHS94D94QFnoECBEQAQ&url=https%3A%2F%2Fwww.docker.com%2F&usg=AOvVaw3p9e1qPvdfjCrUwPYAhUlS)
2. [MySQL database](https://www.mysql.com/) (+8.0 has been tested)
3. [Maven build tool](https://maven.apache.org/)
4. Open ports: 80 (Frontend) and 8080 (Backend)

## Features

### Organization:
 - Create organization for your department to have a box for using for creating kudas

### Kuda
 - Create kudas for users anonymously (Thanks and Feedbacks)
 - List of kudas
 - List of winners in each duration
 - Mark kudas as read kuda for considering as a point for that user

### User
 - Create new user
 - List of users

### Settings and management
 - Change password for Admin, Reader and Default user
 - Multiple language is supported (current languages are English and Persian)
 - Change status of kudas to show/hide
 - Settings for default show status for new kudas (Moderation)
 - Settings for changing current duration

## Installation

### 1. Initialize database

First create a database called `kuda_db` with a user with all required access to this database named `kuda_user`. Please consider defining a strong password for the user and fill in the below query.

```sql
CREATE DATABASE kuda_db;
  CREATE USER 'kuda_user'@'%' IDENTIFIED BY '<your password>';
  GRANT CREATE, ALTER, INSERT, UPDATE, DELETE, SELECT, REFERENCES on kuda_db.* TO 'kuda_user'@'%' WITH GRANT OPTION;
```

Then use below queries to create required tables for running the application.

```sql
CREATE TABLE `hibernate_sequence` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4_unicode_ci;

CREATE TABLE `kuda_db`.`kuda_organization` (
  `id` bigint NOT NULL,
  `is_active` bit(1) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE utf8mb4_unicode_ci;

CREATE TABLE `kuda_db`.`kuda_authorization` (
  `id` bigint NOT NULL,
  `permission` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  `organization_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKrs0uh1klue6bnuhdr6tn1mvt9` (`organization_id`),
  CONSTRAINT `FKrs0uh1klue6bnuhdr6tn1mvt9` FOREIGN KEY (`organization_id`) REFERENCES `kuda_organization` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE utf8mb4_unicode_ci;

CREATE TABLE `kuda_db`.`kuda_organization_credential` (
  `id` bigint NOT NULL,
  `is_active` bit(1) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  `organization_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKjiwmkylelmjcwxyuon15uj00l` (`organization_id`),
  CONSTRAINT `FKjiwmkylelmjcwxyuon15uj00l` FOREIGN KEY (`organization_id`) REFERENCES `kuda_organization` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE utf8mb4_unicode_ci;

CREATE TABLE `kuda_db`.`kuda_settings` (
  `id` bigint NOT NULL,
  `setting_key` varchar(255) DEFAULT NULL,
  `tag` varchar(255) DEFAULT NULL,
  `setting_value` varchar(255) DEFAULT NULL,
  `organization_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKldwir6xt1ie1mtc3w480mn2af` (`organization_id`),
  CONSTRAINT `FKldwir6xt1ie1mtc3w480mn2af` FOREIGN KEY (`organization_id`) REFERENCES `kuda_organization` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE utf8mb4_unicode_ci;

CREATE TABLE `kuda_db`.`kuda_users` (
  `id` bigint NOT NULL,
  `is_active` bit(1) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  `organization_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKkwfikk9e79digygegpb5uexv3` (`organization_id`),
  CONSTRAINT `FKkwfikk9e79digygegpb5uexv3` FOREIGN KEY (`organization_id`) REFERENCES `kuda_organization` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE utf8mb4_unicode_ci;

CREATE TABLE `kuda_db`.`kuda_kudas` (
  `id` bigint NOT NULL,
  `creation_time` datetime DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `duration` int DEFAULT NULL,
  `kuda_type` varchar(255) DEFAULT NULL,
  `read_status` bit(1) DEFAULT NULL,
  `request_id` varchar(255) DEFAULT NULL,
  `show_status` bit(1) DEFAULT NULL,
  `from_user` bigint DEFAULT NULL,
  `organization_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKbqrmu989gu8gu9ww54ool6xer` (`from_user`),
  KEY `FKq93pbo0pmx888326hexoi6hiv` (`organization_id`),
  CONSTRAINT `FKbqrmu989gu8gu9ww54ool6xer` FOREIGN KEY (`from_user`) REFERENCES `kuda_users` (`id`),
  CONSTRAINT `FKq93pbo0pmx888326hexoi6hiv` FOREIGN KEY (`organization_id`) REFERENCES `kuda_organization` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE utf8mb4_unicode_ci;
```

### 2. Fill the database connection into the docker-compose.yml file

Please fill the environment variables in the docker-compose.yml file with your database connection info

```bash
DB_URL: The url for connecting to the database server.
DB_PORT: The port of database instance that serves the connection requests. If you have default installation options it is probably the 3306.
DB_USERNAME: The username that has access to the created database.
DB_PASSWORD: The password for authentcation with the database server.
```

### 3. Change secret key in the application.properties

Please fill the environment variables in the docker-compose.yml file with your database connection info

```bash
jwt.secret=<some random generated string>
```

### 4. Run the application by Docker Compose platform

For running the application please make sure you are in the root of the project and you see the `install.sh` file. Then open a terminal and run below command:

```bash
sh install.sh
```

If everything is alright, you should see logs of the application and after a while you would have access to the applcation in the browser through the `localhost:80` url.

## Screenshots

![Main page](https://images2.imgbox.com/77/bf/zal7gpFk_o.png)

## Contributing

Please feel free to do any contrbution on this project and any improvement that is aligned to the project path would be welcomed.

See `contributing.md` for ways to get started.

## Authors

- [@abolfazl-talaei](https://github.com/abolfazl-talaei)
