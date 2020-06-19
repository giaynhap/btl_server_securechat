/*
SQLyog Ultimate v11.3 (64 bit)
MySQL - 10.4.10-MariaDB : Database - chatsecurity
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`chatsecurity` /*!40100 DEFAULT CHARACTER SET utf8 COLLATE utf8_unicode_ci */;

USE `chatsecurity`;

/*Table structure for table `admins` */

DROP TABLE IF EXISTS `admins`;

CREATE TABLE `admins` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `email` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `username` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `email_verified_at` timestamp NULL DEFAULT NULL,
  `password` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `avatar` text COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `remember_token` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `admins_email_unique` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

/*Data for the table `admins` */

/*Table structure for table `categories` */

DROP TABLE IF EXISTS `categories`;

CREATE TABLE `categories` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `uuid` char(36) COLLATE utf8mb4_unicode_ci NOT NULL,
  `slug` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `name` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `description` text COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `meta_title` text COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `meta_description` text COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `thumbnail` text COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL,
  `deleted_at` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `categories_uuid_unique` (`uuid`),
  UNIQUE KEY `categories_slug_unique` (`slug`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

/*Data for the table `categories` */

/*Table structure for table `contact` */

DROP TABLE IF EXISTS `contact`;

CREATE TABLE `contact` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `user_uuid` varchar(64) COLLATE utf8_unicode_ci DEFAULT NULL,
  `contact_uuid` varchar(64) COLLATE utf8_unicode_ci DEFAULT NULL,
  `level` int(11) DEFAULT NULL,
  `custom_name` varchar(512) COLLATE utf8_unicode_ci DEFAULT NULL,
  `create_at` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Data for the table `contact` */

insert  into `contact`(`id`,`user_uuid`,`contact_uuid`,`level`,`custom_name`,`create_at`) values (18,'000-000-0001','000-000-0000',0,NULL,'2019-12-11 08:38:46'),(19,'000-000-0001','000-000-0001',0,NULL,'2019-12-11 08:50:02'),(23,'000-000-0000','e758cb50-3d30-498a-a0a9-5bc3a876b8c8',0,NULL,'2019-12-27 06:45:03'),(24,'000-000-0000','000-000-0001',0,NULL,'2019-12-30 20:52:54');

/*Table structure for table `conversation` */

DROP TABLE IF EXISTS `conversation`;

CREATE TABLE `conversation` (
  `uuid` varchar(64) COLLATE utf8_unicode_ci NOT NULL,
  `user_uuid` varchar(64) COLLATE utf8_unicode_ci NOT NULL,
  `thread_name` varchar(64) COLLATE utf8_unicode_ci DEFAULT NULL,
  `create_at` datetime DEFAULT NULL,
  `last_msg_at` datetime DEFAULT NULL,
  `unread` int(11) DEFAULT NULL,
  PRIMARY KEY (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Data for the table `conversation` */

insert  into `conversation`(`uuid`,`user_uuid`,`thread_name`,`create_at`,`last_msg_at`,`unread`) values ('2a063f6e-63ed-4858-9a0c-8ecb916a0d93','000-000-0000',NULL,'2019-12-27 06:45:00','2019-12-27 07:57:41',NULL),('5dd8c6cf-1353-49ba-8702-c0ba851f8a73','000-000-0001',NULL,'2019-12-16 05:42:28','2019-12-28 08:06:01',NULL),('a35835eb-71af-4a3a-bcc2-573e62a89ef7','000-000-0001',NULL,'2019-12-15 12:15:15','2020-01-09 00:32:55',NULL);

/*Table structure for table `device` */

DROP TABLE IF EXISTS `device`;

CREATE TABLE `device` (
  `uuid` varchar(64) COLLATE utf8_unicode_ci NOT NULL,
  `user_uuid` varchar(64) COLLATE utf8_unicode_ci DEFAULT NULL,
  `device_code` varchar(64) COLLATE utf8_unicode_ci DEFAULT NULL,
  `device_os` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Data for the table `device` */

insert  into `device`(`uuid`,`user_uuid`,`device_code`,`device_os`) values ('37e8aed4-eed1-411d-bb4a-6838ef334e73','a2d1be20-7ad2-4bfe-bd3d-c9173b3ee824',NULL,'android'),('531b2edb-173a-4891-a79e-ac3ad27af96f','000-000-0000','1d5a0498dbbc45b5','android'),('70249def-6106-44e5-94a8-0468bef387c4','30ddd48f-e9c6-4d17-b23e-d2ddb5532241','589946a19ab38127','android'),('74215dac-9755-4e1e-bbb0-1f2eb1e8854d','000-000-0001','1d5a0498dbbc45b5','android'),('95b2e881-87e1-4e18-b62d-52e919d997a4','a2d1be20-7ad2-4bfe-bd3d-c9173b3ee824','589946a19ab38127','android'),('af424bf6-f3de-48c8-8267-a348dd4aa71e','000-000-0000','589946a19ab38127','android'),('b60dfef4-2a98-44b5-b4d0-49b6185cd623','000-000-0001','dee29c700fd970ce','android'),('c4f1b675-89d4-4d14-9114-701358c0f9dc','000-000-0001','589946a19ab38127','android');

/*Table structure for table `failed_jobs` */

DROP TABLE IF EXISTS `failed_jobs`;

CREATE TABLE `failed_jobs` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `connection` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `queue` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `payload` longtext COLLATE utf8mb4_unicode_ci NOT NULL,
  `exception` longtext COLLATE utf8mb4_unicode_ci NOT NULL,
  `failed_at` timestamp NOT NULL DEFAULT current_timestamp(),
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

/*Data for the table `failed_jobs` */

/*Table structure for table `logs` */

DROP TABLE IF EXISTS `logs`;

CREATE TABLE `logs` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `level` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `tag` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `message` varchar(4096) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Data for the table `logs` */

/*Table structure for table `messages` */

DROP TABLE IF EXISTS `messages`;

CREATE TABLE `messages` (
  `uuid` varchar(192) COLLATE utf8_unicode_ci DEFAULT NULL,
  `type` int(11) DEFAULT NULL,
  `device_code` varchar(192) COLLATE utf8_unicode_ci DEFAULT NULL,
  `user_uuid` varchar(192) COLLATE utf8_unicode_ci DEFAULT NULL,
  `thread_uuid` varchar(192) COLLATE utf8_unicode_ci DEFAULT NULL,
  `time` datetime DEFAULT NULL,
  `encrypt` tinyint(4) DEFAULT NULL,
  `payload` text COLLATE utf8_unicode_ci DEFAULT NULL,
  `sender_uuid` varchar(192) COLLATE utf8_unicode_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Data for the table `messages` */

insert  into `messages`(`uuid`,`type`,`device_code`,`user_uuid`,`thread_uuid`,`time`,`encrypt`,`payload`,`sender_uuid`) values ('217d60f8-924a-48e0-81d8-cb56f64d0c06',3,'dee29c700fd970ce','000-000-0000','a35835eb-71af-4a3a-bcc2-573e62a89ef7','2019-12-19 03:11:33',1,'5mwLfAQdGZjYJKhDfaqbcA==\n','000-000-0001'),('f43dd828-2bd2-4af2-963a-5b1d52fd1a77',3,'dee29c700fd970ce','000-000-0001','a35835eb-71af-4a3a-bcc2-573e62a89ef7','2019-12-19 03:11:33',1,'5mwLfAQdGZjYJKhDfaqbcA==\n','000-000-0001'),('2d5283f0-d6e1-41df-9858-a5a5fd728d30',0,'dee29c700fd970ce','000-000-0000','a35835eb-71af-4a3a-bcc2-573e62a89ef7','2019-12-19 03:12:00',1,'HgGAMOJcdRuMhH7QDZMuzgeUfTnd3T3gv60yrBWrTx4=\n','000-000-0001'),('f02105b9-6d8c-41fe-963a-8a815f877356',0,'dee29c700fd970ce','000-000-0001','a35835eb-71af-4a3a-bcc2-573e62a89ef7','2019-12-19 03:12:00',1,'HgGAMOJcdRuMhH7QDZMuzgeUfTnd3T3gv60yrBWrTx4=\n','000-000-0001'),('6de8c298-3576-45c2-9bc8-3229c15244fe',3,'dee29c700fd970ce','000-000-0000','a35835eb-71af-4a3a-bcc2-573e62a89ef7','2019-12-19 03:13:55',1,'zhOCwhk9/FJRL4fOfqukzg==\n','000-000-0000'),('128016ce-6551-47ef-893d-4948ece2d328',3,'dee29c700fd970ce','000-000-0001','a35835eb-71af-4a3a-bcc2-573e62a89ef7','2019-12-19 03:13:55',1,'zhOCwhk9/FJRL4fOfqukzg==\n','000-000-0000'),('10c1b248-efd8-42c2-bbc2-106b614436ee',0,'dee29c700fd970ce','000-000-0000','a35835eb-71af-4a3a-bcc2-573e62a89ef7','2019-12-19 03:14:04',1,'kkItVmIm82GXvHQoyuohJQ==\n','000-000-0000'),('1ba6a0bc-131a-464c-93db-e818a90ded06',0,'dee29c700fd970ce','000-000-0001','a35835eb-71af-4a3a-bcc2-573e62a89ef7','2019-12-19 03:14:04',1,'kkItVmIm82GXvHQoyuohJQ==\n','000-000-0000'),('3050d94c-b0f7-4487-a898-0f6d81ea18e1',0,'dee29c700fd970ce','000-000-0000','a35835eb-71af-4a3a-bcc2-573e62a89ef7','2019-12-19 13:12:56',1,'gIVsVCt7PW5p3E/yoW0cEKufBTrLTddINH025NIAJ0w=\n','000-000-0001'),('8e7b852e-633d-4865-8d24-b3e092690bc5',0,'dee29c700fd970ce','000-000-0001','a35835eb-71af-4a3a-bcc2-573e62a89ef7','2019-12-19 13:12:56',1,'gIVsVCt7PW5p3E/yoW0cEKufBTrLTddINH025NIAJ0w=\n','000-000-0001'),('09367255-a7a4-44a1-a02e-e351da3f222c',3,'dee29c700fd970ce','000-000-0000','a35835eb-71af-4a3a-bcc2-573e62a89ef7','2019-12-19 13:13:04',1,'ND/2nC5goXvwjNd4RWgAbQ==\n','000-000-0001'),('b01112c8-6099-43ce-b6c7-2626ae508897',3,'dee29c700fd970ce','000-000-0001','a35835eb-71af-4a3a-bcc2-573e62a89ef7','2019-12-19 13:13:04',1,'ND/2nC5goXvwjNd4RWgAbQ==\n','000-000-0001'),('11cd1e1c-6126-4a4a-8e56-ac5171a77a3d',3,'dee29c700fd970ce','000-000-0000','a35835eb-71af-4a3a-bcc2-573e62a89ef7','2019-12-19 13:13:37',1,'COgAkIM7d6df+zHGmietZg==\n','000-000-0001'),('3cdf1899-bdb7-4383-9bf3-239636dc4f8b',3,'dee29c700fd970ce','000-000-0001','a35835eb-71af-4a3a-bcc2-573e62a89ef7','2019-12-19 13:13:37',1,'COgAkIM7d6df+zHGmietZg==\n','000-000-0001'),('27c26721-a708-486b-a9cb-8fc70c95df5a',0,'589946a19ab38127','000-000-0000','a35835eb-71af-4a3a-bcc2-573e62a89ef7','2019-12-20 14:16:15',1,'molqhyDJnwxg5ykX+fMW0g==\n','000-000-0001'),('44c5c820-d7eb-4e24-ae0d-e74a52d4e7bd',0,'589946a19ab38127','000-000-0001','a35835eb-71af-4a3a-bcc2-573e62a89ef7','2019-12-20 14:16:15',1,'molqhyDJnwxg5ykX+fMW0g==\n','000-000-0001'),('97dbb203-d4e3-4033-aafa-76584fa16fa2',0,'d57e5c22487fa091','000-000-0000','a35835eb-71af-4a3a-bcc2-573e62a89ef7','2019-12-20 14:16:21',1,'molqhyDJnwxg5ykX+fMW0g==\n','000-000-0000'),('ac266435-45d4-44c7-925a-bdc96ccaadb9',0,'d57e5c22487fa091','000-000-0001','a35835eb-71af-4a3a-bcc2-573e62a89ef7','2019-12-20 14:16:21',1,'molqhyDJnwxg5ykX+fMW0g==\n','000-000-0000'),('62173782-6902-4cf2-9954-351f33191e91',0,'589946a19ab38127','000-000-0000','a35835eb-71af-4a3a-bcc2-573e62a89ef7','2019-12-20 14:17:20',1,'molqhyDJnwxg5ykX+fMW0g==\n','000-000-0001'),('c0442c24-9223-478b-8574-a89ba123dcb7',0,'589946a19ab38127','000-000-0001','a35835eb-71af-4a3a-bcc2-573e62a89ef7','2019-12-20 14:17:20',1,'molqhyDJnwxg5ykX+fMW0g==\n','000-000-0001'),('358498ca-4f74-42e1-8bbc-335fa9246129',0,'d57e5c22487fa091','000-000-0000','a35835eb-71af-4a3a-bcc2-573e62a89ef7','2019-12-20 15:04:26',1,'AScN6/ckoY+am6kQuAAxP40zJIFjWBevPWWD7/zusHU=\n','000-000-0000'),('2b8d0d2b-5148-4d6f-b3f2-05c71bca31de',0,'d57e5c22487fa091','000-000-0001','a35835eb-71af-4a3a-bcc2-573e62a89ef7','2019-12-20 15:04:26',1,'AScN6/ckoY+am6kQuAAxP40zJIFjWBevPWWD7/zusHU=\n','000-000-0000'),('68362e36-bb7c-428d-8a0e-266e422c7705',0,'589946a19ab38127','000-000-0000','a35835eb-71af-4a3a-bcc2-573e62a89ef7','2019-12-20 15:04:38',1,'jMuVp16yzGms4kkjaOjw/g==\n','000-000-0001'),('2327dd34-2f2d-4046-b32b-7d59f7680a4e',0,'589946a19ab38127','000-000-0001','a35835eb-71af-4a3a-bcc2-573e62a89ef7','2019-12-20 15:04:38',1,'jMuVp16yzGms4kkjaOjw/g==\n','000-000-0001'),('c8cf5bac-fd6a-41de-82a7-8a22704d04e4',0,'d57e5c22487fa091','000-000-0000','a35835eb-71af-4a3a-bcc2-573e62a89ef7','2019-12-20 15:04:59',1,'pgNU2BnKjB7YiF/ffzppWuTJf66cu25IziVNH3YTuH6B8SSS7cM8wvZjyKTmcTcD\n','000-000-0000'),('ec33844f-6f94-4537-95b8-56bbb1ffe35f',0,'d57e5c22487fa091','000-000-0001','a35835eb-71af-4a3a-bcc2-573e62a89ef7','2019-12-20 15:04:59',1,'pgNU2BnKjB7YiF/ffzppWuTJf66cu25IziVNH3YTuH6B8SSS7cM8wvZjyKTmcTcD\n','000-000-0000'),('e871f57f-6080-4334-be37-9c9955d59805',0,'589946a19ab38127','000-000-0000','a35835eb-71af-4a3a-bcc2-573e62a89ef7','2019-12-20 15:05:06',1,'kkItVmIm82GXvHQoyuohJQ==\n','000-000-0001'),('b2364c1d-a3ad-4251-ac0a-a6e7d2511bac',0,'589946a19ab38127','000-000-0001','a35835eb-71af-4a3a-bcc2-573e62a89ef7','2019-12-20 15:05:06',1,'kkItVmIm82GXvHQoyuohJQ==\n','000-000-0001'),('0f25db96-56cf-4b80-9ba8-a04e09116787',3,'589946a19ab38127','000-000-0000','a35835eb-71af-4a3a-bcc2-573e62a89ef7','2019-12-20 15:41:39',1,'XF7mBEfxADDsHyrKOt4BJg==\n','000-000-0000'),('eead5c81-db64-455d-9a8f-a2df874e965a',3,'589946a19ab38127','000-000-0001','a35835eb-71af-4a3a-bcc2-573e62a89ef7','2019-12-20 15:41:39',1,'XF7mBEfxADDsHyrKOt4BJg==\n','000-000-0000'),('cb6da811-7585-492e-9c3c-e378231b847f',0,'589946a19ab38127','000-000-0001','5dd8c6cf-1353-49ba-8702-c0ba851f8a73','2019-12-27 06:44:38',1,'CiJhKqz7hlT3h9+3cov7MQ==\n','000-000-0001'),('446f8741-de1d-418d-b172-391399a01baf',0,'589946a19ab38127','e758cb50-3d30-498a-a0a9-5bc3a876b8c8','5dd8c6cf-1353-49ba-8702-c0ba851f8a73','2019-12-27 06:44:38',1,'CiJhKqz7hlT3h9+3cov7MQ==\n','000-000-0001'),('bdeda52e-8253-487a-a185-d0a81665988d',0,'589946a19ab38127','000-000-0000','2a063f6e-63ed-4858-9a0c-8ecb916a0d93','2019-12-27 07:57:35',1,'Rk9gj2tJbiYykqe2DlrHhw==\n','000-000-0000'),('d362b66a-d3c7-44c9-a9bb-150b2fc1516d',0,'589946a19ab38127','e758cb50-3d30-498a-a0a9-5bc3a876b8c8','2a063f6e-63ed-4858-9a0c-8ecb916a0d93','2019-12-27 07:57:35',1,'Rk9gj2tJbiYykqe2DlrHhw==\n','000-000-0000'),('1796be94-43f2-4dc0-ae3c-09f75ac538de',3,'589946a19ab38127','000-000-0000','2a063f6e-63ed-4858-9a0c-8ecb916a0d93','2019-12-27 07:57:41',1,'bwENKCWRMvPUuwTHvsicAg==\n','000-000-0000'),('b511fc23-5739-4e99-be18-f63138c60fc1',3,'589946a19ab38127','e758cb50-3d30-498a-a0a9-5bc3a876b8c8','2a063f6e-63ed-4858-9a0c-8ecb916a0d93','2019-12-27 07:57:41',1,'bwENKCWRMvPUuwTHvsicAg==\n','000-000-0000'),('d30ebb8f-2559-4a37-bdac-8ee3ef9fd1b4',1,'589946a19ab38127','000-000-0001','a35835eb-71af-4a3a-bcc2-573e62a89ef7','2019-12-27 23:31:12',1,'B5R9Od3dPeC/rTKsFatPHg==\n','000-000-0000'),('c14de120-a0d0-468a-90f4-da524d4b804e',1,'589946a19ab38127','000-000-0000','a35835eb-71af-4a3a-bcc2-573e62a89ef7','2019-12-27 23:31:12',1,'B5R9Od3dPeC/rTKsFatPHg==\n','000-000-0000'),('5712f5a0-2686-4778-9828-f0283e996a6f',1,'589946a19ab38127','000-000-0001','a35835eb-71af-4a3a-bcc2-573e62a89ef7','2019-12-27 23:32:25',1,'B5R9Od3dPeC/rTKsFatPHg==\n','000-000-0000'),('c399042c-38c5-4ec9-9a90-b41b48d5bcf4',1,'589946a19ab38127','000-000-0000','a35835eb-71af-4a3a-bcc2-573e62a89ef7','2019-12-27 23:32:25',1,'B5R9Od3dPeC/rTKsFatPHg==\n','000-000-0000'),('858915c3-f355-4917-bd4d-7036e3982a2c',1,'589946a19ab38127','000-000-0001','a35835eb-71af-4a3a-bcc2-573e62a89ef7','2019-12-27 23:33:54',1,'B5R9Od3dPeC/rTKsFatPHg==\n','000-000-0000'),('32885684-9665-4c17-9d45-069794e190a3',1,'589946a19ab38127','000-000-0000','a35835eb-71af-4a3a-bcc2-573e62a89ef7','2019-12-27 23:33:54',1,'B5R9Od3dPeC/rTKsFatPHg==\n','000-000-0000'),('c4a2d388-d3a0-425f-b66c-837068eb7ab3',3,'589946a19ab38127','000-000-0001','a35835eb-71af-4a3a-bcc2-573e62a89ef7','2019-12-28 00:11:34',1,'7Ch3MHl1SZzc+XGiETr9tg==\n','000-000-0001'),('852c2554-f048-4135-899b-9ee8f7b5b368',3,'589946a19ab38127','000-000-0000','a35835eb-71af-4a3a-bcc2-573e62a89ef7','2019-12-28 00:11:34',1,'7Ch3MHl1SZzc+XGiETr9tg==\n','000-000-0001'),('41d779ce-986e-4b6a-ba1f-a349da863f84',3,'589946a19ab38127','000-000-0001','5dd8c6cf-1353-49ba-8702-c0ba851f8a73','2019-12-28 00:11:44',1,'8N3zTstJzH4JvrQB8gmGXQ==\n','000-000-0001'),('4c4d58a7-b07d-4e89-919c-06599286f12d',3,'589946a19ab38127','e758cb50-3d30-498a-a0a9-5bc3a876b8c8','5dd8c6cf-1353-49ba-8702-c0ba851f8a73','2019-12-28 00:11:44',1,'8N3zTstJzH4JvrQB8gmGXQ==\n','000-000-0001'),('2745e88e-8f4d-4435-b57a-a49db7c374f1',3,'dee29c700fd970ce','000-000-0001','5dd8c6cf-1353-49ba-8702-c0ba851f8a73','2019-12-28 07:30:13',1,'EYciFEmrf1RmcKL/A5CocQ==\n','000-000-0001'),('7269d1ca-db63-47c3-813c-18832a3c4c7e',3,'dee29c700fd970ce','e758cb50-3d30-498a-a0a9-5bc3a876b8c8','5dd8c6cf-1353-49ba-8702-c0ba851f8a73','2019-12-28 07:30:13',1,'EYciFEmrf1RmcKL/A5CocQ==\n','000-000-0001'),('7d7482fd-1616-49ed-8e95-f1379077f26f',1,'dee29c700fd970ce','000-000-0001','5dd8c6cf-1353-49ba-8702-c0ba851f8a73','2019-12-28 07:30:36',1,'GgQ+wWQ9ddm2gjFqAqudsumi4DsJxqyYoiFyNxaSF/u024snLGdbloMIOx+jf2OKfgq6DA2alVxn\nm7FigC/5ow==\n','000-000-0001'),('797018c5-7919-40b1-86c9-fa7ef3d4d8cf',1,'dee29c700fd970ce','e758cb50-3d30-498a-a0a9-5bc3a876b8c8','5dd8c6cf-1353-49ba-8702-c0ba851f8a73','2019-12-28 07:30:36',1,'GgQ+wWQ9ddm2gjFqAqudsumi4DsJxqyYoiFyNxaSF/u024snLGdbloMIOx+jf2OKfgq6DA2alVxn\nm7FigC/5ow==\n','000-000-0001'),('0e6e7a39-e74f-4e53-b092-51d7bbb54976',1,'dee29c700fd970ce','000-000-0001','5dd8c6cf-1353-49ba-8702-c0ba851f8a73','2019-12-28 07:34:51',1,'GgQ+wWQ9ddm2gjFqAqudshimn/QbDWEDE5uZvREXqs3W4OyzE72eIgjCwRwTy2Y3R9izsAFiShQE\n5dGJFaD09w==\n','000-000-0001'),('62d78723-e6a9-4970-8d98-fcda7a20752b',1,'dee29c700fd970ce','e758cb50-3d30-498a-a0a9-5bc3a876b8c8','5dd8c6cf-1353-49ba-8702-c0ba851f8a73','2019-12-28 07:34:51',1,'GgQ+wWQ9ddm2gjFqAqudshimn/QbDWEDE5uZvREXqs3W4OyzE72eIgjCwRwTy2Y3R9izsAFiShQE\n5dGJFaD09w==\n','000-000-0001'),('db99d150-1b55-4fd7-89bd-37625d8d0f87',1,'dee29c700fd970ce','000-000-0001','5dd8c6cf-1353-49ba-8702-c0ba851f8a73','2019-12-28 07:56:59',1,'GgQ+wWQ9ddm2gjFqAqudshDUeKTqJOuBDiEqGqeeXW0nNUBKbX3QePXRFxJKXwgDg3p6AJpRjTp1\nHH1CwrGrrQ==\n','000-000-0001'),('c7ee443f-5f5e-46b7-941f-bc25329423c9',1,'dee29c700fd970ce','e758cb50-3d30-498a-a0a9-5bc3a876b8c8','5dd8c6cf-1353-49ba-8702-c0ba851f8a73','2019-12-28 07:56:59',1,'GgQ+wWQ9ddm2gjFqAqudshDUeKTqJOuBDiEqGqeeXW0nNUBKbX3QePXRFxJKXwgDg3p6AJpRjTp1\nHH1CwrGrrQ==\n','000-000-0001'),('4ee38f9f-f834-417b-b9b7-5b27f9b2fc56',3,'dee29c700fd970ce','000-000-0001','5dd8c6cf-1353-49ba-8702-c0ba851f8a73','2019-12-28 08:05:53',1,'gx6+08XsjOFWimW3bGtAGQ==\n','000-000-0001'),('370d06ec-436a-4b3a-acf5-79f1cf2587bd',3,'dee29c700fd970ce','e758cb50-3d30-498a-a0a9-5bc3a876b8c8','5dd8c6cf-1353-49ba-8702-c0ba851f8a73','2019-12-28 08:05:53',1,'gx6+08XsjOFWimW3bGtAGQ==\n','000-000-0001'),('d08b7d70-3d03-4c80-9d73-b12426efc6aa',3,'dee29c700fd970ce','000-000-0001','5dd8c6cf-1353-49ba-8702-c0ba851f8a73','2019-12-28 08:05:56',1,'QbU9nUkM2stBa8GLN5XvwA==\n','000-000-0001'),('f1690d4d-a064-4270-90e6-312aefa65ff8',3,'dee29c700fd970ce','e758cb50-3d30-498a-a0a9-5bc3a876b8c8','5dd8c6cf-1353-49ba-8702-c0ba851f8a73','2019-12-28 08:05:56',1,'QbU9nUkM2stBa8GLN5XvwA==\n','000-000-0001'),('72b71e70-1566-42b6-afa4-ae8ef8819501',3,'dee29c700fd970ce','000-000-0001','5dd8c6cf-1353-49ba-8702-c0ba851f8a73','2019-12-28 08:05:58',1,'EsOHjrivso9PUj9XH6ftCg==\n','000-000-0001'),('7f2a719a-024c-4efc-956a-5fcecb8263d9',3,'dee29c700fd970ce','e758cb50-3d30-498a-a0a9-5bc3a876b8c8','5dd8c6cf-1353-49ba-8702-c0ba851f8a73','2019-12-28 08:05:58',1,'EsOHjrivso9PUj9XH6ftCg==\n','000-000-0001'),('50ed6a01-0746-4d02-80d1-01af59c1b52b',3,'dee29c700fd970ce','000-000-0001','5dd8c6cf-1353-49ba-8702-c0ba851f8a73','2019-12-28 08:06:01',1,'M39qk7v6m9oN2XhXx6kn7Q==\n','000-000-0001'),('92016ec2-5667-442b-8a18-6dabab2dc033',3,'dee29c700fd970ce','e758cb50-3d30-498a-a0a9-5bc3a876b8c8','5dd8c6cf-1353-49ba-8702-c0ba851f8a73','2019-12-28 08:06:01',1,'M39qk7v6m9oN2XhXx6kn7Q==\n','000-000-0001'),('0db09e26-297a-4a1f-98de-1fd27f2bd97c',3,'d57e5c22487fa091','000-000-0001','a35835eb-71af-4a3a-bcc2-573e62a89ef7','2019-12-30 20:58:33',1,'dMRM3e0NPwS06UvQjCnUvw==\n','000-000-0001'),('a80a28b2-468b-4b5e-95fa-44512c686671',3,'d57e5c22487fa091','000-000-0000','a35835eb-71af-4a3a-bcc2-573e62a89ef7','2019-12-30 20:58:33',1,'dMRM3e0NPwS06UvQjCnUvw==\n','000-000-0001'),('c4951f02-1b45-484c-85b6-097e135cea55',3,'589946a19ab38127','000-000-0001','a35835eb-71af-4a3a-bcc2-573e62a89ef7','2019-12-30 20:58:43',1,'rUh1BPrBgYw7nWn10ZRb4Q==\n','000-000-0000'),('a8f19843-6ac6-4a89-904f-8f9b8c30e9e8',3,'589946a19ab38127','000-000-0000','a35835eb-71af-4a3a-bcc2-573e62a89ef7','2019-12-30 20:58:43',1,'rUh1BPrBgYw7nWn10ZRb4Q==\n','000-000-0000'),('7c1b250c-31b6-4c43-8326-3f50d3b23099',0,'d57e5c22487fa091','000-000-0001','a35835eb-71af-4a3a-bcc2-573e62a89ef7','2019-12-30 21:00:28',1,'VbdY90rllptTKXPUoaFD/g==\n','000-000-0001'),('4a44192b-a38e-4cb7-9e1b-0ba8389104f2',0,'d57e5c22487fa091','000-000-0000','a35835eb-71af-4a3a-bcc2-573e62a89ef7','2019-12-30 21:00:28',1,'VbdY90rllptTKXPUoaFD/g==\n','000-000-0001'),('85ce4fa8-a2c3-4a3a-97ff-6ffd00b27f4a',0,'589946a19ab38127','000-000-0001','a35835eb-71af-4a3a-bcc2-573e62a89ef7','2019-12-30 21:00:33',1,'VbdY90rllptTKXPUoaFD/g==\n','000-000-0000'),('46bcbd26-7771-4098-b349-fd063e9fdde2',0,'589946a19ab38127','000-000-0000','a35835eb-71af-4a3a-bcc2-573e62a89ef7','2019-12-30 21:00:33',1,'VbdY90rllptTKXPUoaFD/g==\n','000-000-0000'),('e3b28360-cdf5-45b8-a03f-f00965602c9f',3,'d57e5c22487fa091','000-000-0001','a35835eb-71af-4a3a-bcc2-573e62a89ef7','2019-12-30 21:00:47',1,'dMRM3e0NPwS06UvQjCnUvw==\n','000-000-0001'),('3a517227-4841-419d-ba50-aa90a47bda69',3,'d57e5c22487fa091','000-000-0000','a35835eb-71af-4a3a-bcc2-573e62a89ef7','2019-12-30 21:00:47',1,'dMRM3e0NPwS06UvQjCnUvw==\n','000-000-0001'),('0b3bd09a-0301-4fe3-abb0-07a05abf2088',3,'589946a19ab38127','000-000-0001','a35835eb-71af-4a3a-bcc2-573e62a89ef7','2019-12-30 21:00:51',1,'H8Llhx6vKSOsltsBvxf2pQ==\n','000-000-0000'),('0dce0a87-e321-4d04-b68a-fad2caecf472',3,'589946a19ab38127','000-000-0000','a35835eb-71af-4a3a-bcc2-573e62a89ef7','2019-12-30 21:00:51',1,'H8Llhx6vKSOsltsBvxf2pQ==\n','000-000-0000'),('2d35ce39-6915-4a53-b8cc-20698a616713',0,'589946a19ab38127','000-000-0001','a35835eb-71af-4a3a-bcc2-573e62a89ef7','2020-01-08 12:53:02',1,'G1kvT7yK+VsBr26d9Lo0Cg==\n','000-000-0001'),('aecad3f9-cbd9-4e3a-a5cf-048d9261adf2',0,'589946a19ab38127','000-000-0000','a35835eb-71af-4a3a-bcc2-573e62a89ef7','2020-01-08 12:53:02',1,'G1kvT7yK+VsBr26d9Lo0Cg==\n','000-000-0001'),('89250c19-4e9c-424a-a68a-5f3056861848',2,'589946a19ab38127','000-000-0001','a35835eb-71af-4a3a-bcc2-573e62a89ef7','2020-01-08 12:53:14',1,'70fuV9TLC51DLX0/ZsXphbzKu4vI286svlOlkrn5Vn75oxlGodq3Cd3PcElHPj+yDI04yMRPYhEQ\nXHPcqGI9VQ==\n','000-000-0001'),('2b9b56c9-13cf-4f7d-bb94-39e1809291b2',2,'589946a19ab38127','000-000-0000','a35835eb-71af-4a3a-bcc2-573e62a89ef7','2020-01-08 12:53:14',1,'70fuV9TLC51DLX0/ZsXphbzKu4vI286svlOlkrn5Vn75oxlGodq3Cd3PcElHPj+yDI04yMRPYhEQ\nXHPcqGI9VQ==\n','000-000-0001'),('63afea4d-f5d1-4547-b7bb-fc8eb6b952b2',1,'dee29c700fd970ce','000-000-0001','a35835eb-71af-4a3a-bcc2-573e62a89ef7','2020-01-08 23:15:40',1,'Xtob/RNfQjE8z5nDSrdspXpnVOiLSzV6V0xd4mNQ6RTA+XuBsh1yzGDoGBVpq6lMXCOgSLYspwJO\n0f0xu7n+Ww==\n','000-000-0001'),('6137c635-9758-4cbc-86e4-58c8cc2eff65',1,'dee29c700fd970ce','000-000-0000','a35835eb-71af-4a3a-bcc2-573e62a89ef7','2020-01-08 23:15:40',1,'Xtob/RNfQjE8z5nDSrdspXpnVOiLSzV6V0xd4mNQ6RTA+XuBsh1yzGDoGBVpq6lMXCOgSLYspwJO\n0f0xu7n+Ww==\n','000-000-0001'),('a776467d-7715-415e-9df0-476cbe69f8a1',1,'dee29c700fd970ce','000-000-0001','a35835eb-71af-4a3a-bcc2-573e62a89ef7','2020-01-08 23:42:27',1,'Xtob/RNfQjE8z5nDSrdspT2G9YuaTYyXFf/CfeIg4OAV6IPzWz0HABdDYuPrHVCBOb8XE/5cNISB\niNQOTQWioQ==\n','000-000-0001'),('db621664-093f-4700-b1c3-3b64e6440722',1,'dee29c700fd970ce','000-000-0000','a35835eb-71af-4a3a-bcc2-573e62a89ef7','2020-01-08 23:42:27',1,'Xtob/RNfQjE8z5nDSrdspT2G9YuaTYyXFf/CfeIg4OAV6IPzWz0HABdDYuPrHVCBOb8XE/5cNISB\niNQOTQWioQ==\n','000-000-0001'),('9d478368-a6ee-409a-85f9-a84bc2972c8a',1,'dee29c700fd970ce','000-000-0001','a35835eb-71af-4a3a-bcc2-573e62a89ef7','2020-01-09 00:04:11',1,'Xtob/RNfQjE8z5nDSrdspUI785VAfZ8FjRvNNPqa13T8HGRQRiunsxCWJ9AKkZDvZqEwUanevcaT\nk8TvOxcLhg==\n','000-000-0001'),('d0f5133a-2aa6-490a-92dd-b8a9b07a59db',1,'dee29c700fd970ce','000-000-0000','a35835eb-71af-4a3a-bcc2-573e62a89ef7','2020-01-09 00:04:11',1,'Xtob/RNfQjE8z5nDSrdspUI785VAfZ8FjRvNNPqa13T8HGRQRiunsxCWJ9AKkZDvZqEwUanevcaT\nk8TvOxcLhg==\n','000-000-0001'),('31c0715e-12a6-4c35-962c-3b0da9baebe5',1,'dee29c700fd970ce','000-000-0001','a35835eb-71af-4a3a-bcc2-573e62a89ef7','2020-01-09 00:07:53',1,'Xtob/RNfQjE8z5nDSrdspSS3rUC7CpPgV45iUVJNO9wWY+kWX18XJ5hGqJT2bCD+z50krGCUuF6f\n9oDqW9Fv2Q==\n','000-000-0001'),('1e970ea0-3cde-4641-aa13-4d09d002d2bf',1,'dee29c700fd970ce','000-000-0000','a35835eb-71af-4a3a-bcc2-573e62a89ef7','2020-01-09 00:07:53',1,'Xtob/RNfQjE8z5nDSrdspSS3rUC7CpPgV45iUVJNO9wWY+kWX18XJ5hGqJT2bCD+z50krGCUuF6f\n9oDqW9Fv2Q==\n','000-000-0001'),('ee145101-fe97-421c-8124-32cb88ede535',1,'dee29c700fd970ce','000-000-0001','a35835eb-71af-4a3a-bcc2-573e62a89ef7','2020-01-09 00:08:52',1,'Xtob/RNfQjE8z5nDSrdspZppzAmroq9pg+VVEe0uqg9s8sfiRuT8ffssfVy7o2ulrIAJxbR3xS+S\nTHDDtMej8Q==\n','000-000-0001'),('4204282e-0ab3-44b4-8310-4534a6e1ce71',1,'dee29c700fd970ce','000-000-0000','a35835eb-71af-4a3a-bcc2-573e62a89ef7','2020-01-09 00:08:52',1,'Xtob/RNfQjE8z5nDSrdspZppzAmroq9pg+VVEe0uqg9s8sfiRuT8ffssfVy7o2ulrIAJxbR3xS+S\nTHDDtMej8Q==\n','000-000-0001'),('a9e1aa64-c1aa-429f-a45a-770c4727c5e1',2,'589946a19ab38127','000-000-0001','a35835eb-71af-4a3a-bcc2-573e62a89ef7','2020-01-09 00:29:36',1,'70fuV9TLC51DLX0/ZsXphSEfXjOKU70SbmvXNxHjMXv6uRqvpx1BkRl+ed6wOUs52nrNDaVGqSuN\nxcvzqH2w0Q==\n','000-000-0001'),('5c8cb860-7b33-4485-95cc-20956ed6d2fa',2,'589946a19ab38127','000-000-0000','a35835eb-71af-4a3a-bcc2-573e62a89ef7','2020-01-09 00:29:36',1,'70fuV9TLC51DLX0/ZsXphSEfXjOKU70SbmvXNxHjMXv6uRqvpx1BkRl+ed6wOUs52nrNDaVGqSuN\nxcvzqH2w0Q==\n','000-000-0001'),('4deb76a6-c995-43bc-b7a9-28dd6e906894',2,'1d5a0498dbbc45b5','000-000-0001','a35835eb-71af-4a3a-bcc2-573e62a89ef7','2020-01-09 00:32:55',1,'70fuV9TLC51DLX0/ZsXphVDGJgH2qdLf0vfMVIjGunV5/jKM7LhXHh4b5wXDJqbbxJ107q3v+UOw\nPLoON+aQCg==\n','000-000-0001'),('0c992cd5-f9e9-4f8c-b7e1-965a5d219baa',2,'1d5a0498dbbc45b5','000-000-0000','a35835eb-71af-4a3a-bcc2-573e62a89ef7','2020-01-09 00:32:55',1,'70fuV9TLC51DLX0/ZsXphVDGJgH2qdLf0vfMVIjGunV5/jKM7LhXHh4b5wXDJqbbxJ107q3v+UOw\nPLoON+aQCg==\n','000-000-0001');

/*Table structure for table `migrations` */

DROP TABLE IF EXISTS `migrations`;

CREATE TABLE `migrations` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `migration` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `batch` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

/*Data for the table `migrations` */

/*Table structure for table `model_has_permissions` */

DROP TABLE IF EXISTS `model_has_permissions`;

CREATE TABLE `model_has_permissions` (
  `permission_id` bigint(20) unsigned NOT NULL,
  `model_type` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `model_id` bigint(20) unsigned NOT NULL,
  PRIMARY KEY (`permission_id`,`model_id`,`model_type`),
  KEY `model_has_permissions_model_id_model_type_index` (`model_id`,`model_type`),
  CONSTRAINT `model_has_permissions_permission_id_foreign` FOREIGN KEY (`permission_id`) REFERENCES `permissions` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

/*Data for the table `model_has_permissions` */

/*Table structure for table `model_has_roles` */

DROP TABLE IF EXISTS `model_has_roles`;

CREATE TABLE `model_has_roles` (
  `role_id` bigint(20) unsigned NOT NULL,
  `model_type` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `model_id` bigint(20) unsigned NOT NULL,
  PRIMARY KEY (`role_id`,`model_id`,`model_type`),
  KEY `model_has_roles_model_id_model_type_index` (`model_id`,`model_type`),
  CONSTRAINT `model_has_roles_role_id_foreign` FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

/*Data for the table `model_has_roles` */

/*Table structure for table `options` */

DROP TABLE IF EXISTS `options`;

CREATE TABLE `options` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `value` longtext COLLATE utf8mb4_unicode_ci NOT NULL,
  `status` tinyint(4) NOT NULL DEFAULT 1 COMMENT '0.hidden 1.show',
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

/*Data for the table `options` */

/*Table structure for table `password_resets` */

DROP TABLE IF EXISTS `password_resets`;

CREATE TABLE `password_resets` (
  `email` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `token` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  KEY `password_resets_email_index` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

/*Data for the table `password_resets` */

/*Table structure for table `permissions` */

DROP TABLE IF EXISTS `permissions`;

CREATE TABLE `permissions` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `guard_name` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

/*Data for the table `permissions` */

/*Table structure for table `role_has_permissions` */

DROP TABLE IF EXISTS `role_has_permissions`;

CREATE TABLE `role_has_permissions` (
  `permission_id` bigint(20) unsigned NOT NULL,
  `role_id` bigint(20) unsigned NOT NULL,
  PRIMARY KEY (`permission_id`,`role_id`),
  KEY `role_has_permissions_role_id_foreign` (`role_id`),
  CONSTRAINT `role_has_permissions_permission_id_foreign` FOREIGN KEY (`permission_id`) REFERENCES `permissions` (`id`) ON DELETE CASCADE,
  CONSTRAINT `role_has_permissions_role_id_foreign` FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

/*Data for the table `role_has_permissions` */

/*Table structure for table `roles` */

DROP TABLE IF EXISTS `roles`;

CREATE TABLE `roles` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `guard_name` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

/*Data for the table `roles` */

/*Table structure for table `user_conversation` */

DROP TABLE IF EXISTS `user_conversation`;

CREATE TABLE `user_conversation` (
  `thread_uuid` varchar(64) COLLATE utf8_unicode_ci NOT NULL,
  `user_uuid` varchar(64) COLLATE utf8_unicode_ci NOT NULL,
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `enkey` text COLLATE utf8_unicode_ci DEFAULT NULL,
  `last_seen` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=64 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Data for the table `user_conversation` */

insert  into `user_conversation`(`thread_uuid`,`user_uuid`,`id`,`enkey`,`last_seen`) values ('a35835eb-71af-4a3a-bcc2-573e62a89ef7','000-000-0001',58,'Qo8kssnvtApkXHpSwhrWdM7yOC7sR/Tpipl6AxA87qa9nZqLxnSqvBceJDB1OYpId2uR2irgeha7\nIs8kWyD5bjHxDIpq+UT63h0xZsKAedupOJLEtQJ/Zw2rc6u906kj8ddRxHpQRsa2IsY7Hnbop3+w\n4wKycg1kzlXBMpT/AbqamSCH5P+o6OTHBFs/xz4UppcI2iOOQcjF0oAagkGqD7GIZNnbcLySCGWd\nxglDqpigr3VZQKAZ6dIn+8A89SeY8Hw59H8g1QsfekQs74HpcyKT4V4xrhnVepsZnZqSVSYe7OUG\nacnkwHg40B/N/i4oMvHmmGVTvwJhMqIR3LLG2w==\n','2020-01-09 00:39:33'),('a35835eb-71af-4a3a-bcc2-573e62a89ef7','000-000-0000',59,'PZSeLq4a3sCyLovX0un0hZTDEPLNaKG3oBgg5HY+shAbTg+iVIq3XwftxoAxesq6wEwpoMnmKaNj\nSfiqaR3vvbAUlmFbe3FZ7Rx8Vk8j0cHv1aE1gYraIgTyf4teH1zBlBRynsnqCLL9IdiFG5FmcRO1\npAZW0TRkyIVQgZ1Gh8c+dSdO7yLm3Qh/6X0YemVFYAD0jw5vIKc3ndPZdgEYyFuAB6F83nTD2qnZ\nW0kS6BQEExnDT9pR437oei0qJwHSHKnTnnjqMsNeYB2yvC410lfv2TVwxS81+mOi5uaZmNB90eRS\nuHhG7TIgsNXC3buzju0n3b33TV7aNgMXYxcR3g==\n','2020-01-09 00:56:44'),('5dd8c6cf-1353-49ba-8702-c0ba851f8a73','000-000-0001',60,'LdGgc3X8utbQAA9GcRbcLUVjcyar/PMPh5/W6G1afxG28UQZQ/liTEsowgxBEH/xl+sqmktCptSi\n4nG+EWikUnahptXQPIByWJQd64EDRBre/mWqnsGZWERlIVP16ag6iTCXz472WjMZO7KKUoNZtibR\nkE9OtkJWCDgkUzhnsX0XfvxUJZ+7K7SVX5uEdIcoABM2h2/N8t8r6Pqot92G2kQSJLu9s5hI51se\nbY9goUpnBIxT1rGAVpgKFgwGXx+VCtO+LsEMvWwJwIdzUdfqAKd3ARCOIKPVmWtHlTCrfEgn1Gvv\ncURNlUkU0WG6POibHtJp/bRmgPoUZoZdZYeMQQ==\n','2020-01-09 00:18:53'),('5dd8c6cf-1353-49ba-8702-c0ba851f8a73','e758cb50-3d30-498a-a0a9-5bc3a876b8c8',61,'pumT+8gS4A7eJt8n6b5YOwoeRXVC8jsscsyAY5eDBy7gMq1V0P3v5GYwB8dqAujzQmF1x2fpaQKK\n9TsN36pJz1cjeZwu5bSktUvwIl0djUzgs2R8NiAv5q/EMYa9lX8Ht1Vr2R9G93TugW0AAc2DWiEA\nB0S1UVdIxV8/bnv0Ba9B/YC05fZWK1dUUmFtx2aTGbPyxy2Clhi635WZ4y1Pvcu/NVTDOu1uHxh3\nQk6Ss1/8UsjcPWiG7B4oSRNKB7xwNmt6aY9QGOeiNYRhOCz9af7uoB1D6H/vUF6VTptS6y+bqBdL\nKIpkR/ctCKwU5+bgOQmim+2tcknGH5JzwDQm7g==\n',NULL),('2a063f6e-63ed-4858-9a0c-8ecb916a0d93','000-000-0000',62,'Eg+HDZ+s/O6yyNyJQEVYo81zWRJ+Vyl3D+e83BDRuAFKhdKcjn0ieH0B4x2GY1LNSVWILjH2Ga5v\nUXg/kAR/tQrQyraMRIfBCOH/p51PmzzPa64DzbcSFHYpYWJ1uyuBdMD6gM1+oqsQhT+kjajUtCOS\nf29OM8Kjx+1N+05rAdqePWjG6+4eS8LxBqA9kbxFrbV49/N76bh7HkHqV5CPxNSOXfiLsNc5VQQb\nbFATdh2O+s5ztTYfjqkvm77WPHl102ypUDde2euMaAFffYKs9P83xHTgFDTyCwSsUXTBvkBtei+E\nwh74FV+hWswl7pe7Lf0g33K2hyOopZ5wfxTkEQ==\n','2020-01-09 00:54:12'),('2a063f6e-63ed-4858-9a0c-8ecb916a0d93','e758cb50-3d30-498a-a0a9-5bc3a876b8c8',63,'AN5+i6J6BEbp9uhHDOwueVmml8DR8eidzk3+QdJBijTvRqjIePFffogigtIA60g4NGBo5wME82rK\nosFT5ahdoO9Y7rJnn00fJA6sjbI2iknl7X3LZc4hdY38pDDYUVjtGMXDY6NX22QC8aqxNGuKYSOz\nOvv4mC5p/R5QPLIW20VSyT0HBlnCw+eno58ADBUrLy1nbkQ7AMySjP+XvX0wt5P7469gJFBT6aB7\n280aYVBDw4DNMNDpvGYxbPJsxbS0QKQJBh7fsl2LnEVr51OC3fF8MOoh2goYjfXGBMdD8CcDPLTZ\nlsH/n+VCEY9SwVCTAGuNnEAZrTnseIEq2K3lQg==\n',NULL);

/*Table structure for table `user_info` */

DROP TABLE IF EXISTS `user_info`;

CREATE TABLE `user_info` (
  `uuid` varchar(64) COLLATE utf8_unicode_ci NOT NULL,
  `name` varchar(512) COLLATE utf8_unicode_ci DEFAULT NULL,
  `dob` datetime DEFAULT NULL,
  `address` varchar(1024) COLLATE utf8_unicode_ci DEFAULT NULL,
  `phone` varchar(64) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Data for the table `user_info` */

insert  into `user_info`(`uuid`,`name`,`dob`,`address`,`phone`) values ('000-000-0000','Giấy Nháp','2019-11-26 07:51:48','Thanh Hóa','84852120219'),('000-000-0001','Đỗ Văn Thực','2019-12-07 01:24:55','Thanh Hóa','84852120219'),('30ddd48f-e9c6-4d17-b23e-d2ddb5532241','dovanthuc','2020-01-24 11:18:38','askdaskdaskdk','12310313123'),('44475cc7-bc65-43a7-a612-45f21aaf7a63','dovanthuc','2020-01-08 10:47:01','skincstt@gmail.com','84852120219'),('46553eb2-510b-4aab-864c-6c4e6c51eb6f','asdasdasdasdasd','2020-01-08 12:17:05','asdasdasdasd','asdadad123123123'),('57dff8a3-b847-4273-a02b-8d5b9a5769db','thuc123456','2020-01-25 12:13:42','giaynhap','84852120219'),('624f4e8d-539d-4354-bdd9-87ca5738a3ba','dovanthuc987','2020-01-08 10:46:34','skincstt@gmail.com','84852120219'),('68f0414e-a146-42a2-9762-6148cba4699a','asdadasdasd','2020-01-08 11:02:59','sdadasda','84852120219'),('6aed2589-c7ed-4d35-af92-478da4109c64','dovanthuc987','2020-01-08 12:15:54','1232414','123123123123'),('986cc8a6-660b-4d98-8341-46c42c626ae4','dovanthuc987','2020-01-08 11:04:41','skincstt@gmail.com','84852120219'),('a2d1be20-7ad2-4bfe-bd3d-c9173b3ee824','thuc','2020-01-08 12:27:38','thuc','84852120219'),('d02dd4f6-66ab-4d24-9c26-b298d07fed62','KimJongun','1970-01-19 12:49:12','Triều tiên','Private'),('d02dd4f6-66ab-4d24-9c26-b298d07fed63','Deadpool','1970-01-19 12:49:12','USA','deadpoolphone'),('d02dd4f6-66ab-4d24-9c26-b298d07fed64','Giay Nhap2','1970-01-19 12:49:11','ThanhHoa',''),('e758cb50-3d30-498a-a0a9-5bc3a876b8c3','Trần Dần','2019-12-27 06:51:36','California','Private'),('e758cb50-3d30-498a-a0a9-5bc3a876b8c8','Yasuo','1970-01-19 12:49:12','Ionia','yasuophone'),('f679a196-f5e2-4039-bbae-2fa6fd0e90b8','dovanthuc','2020-01-08 10:53:44','skincstt@gmail.com','84852120219');

/*Table structure for table `user_key` */

DROP TABLE IF EXISTS `user_key`;

CREATE TABLE `user_key` (
  `uuid` varchar(64) COLLATE utf8_unicode_ci NOT NULL,
  `publickey` text COLLATE utf8_unicode_ci NOT NULL,
  `privatekey` text COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Data for the table `user_key` */

insert  into `user_key`(`uuid`,`publickey`,`privatekey`) values ('000-000-0000','MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAwDt5fAcDsnkWa/+JUme/h+2teX9kLfve\njPqZXgaBwsTVgIsIHp+nq7RSiI16Ke8trgfT8j/qLX5b5KptPmDcNmirBDX2AFWwT5P+3hAodi0k\nnUSnOlHf1YS4YlH+4agRY1vLjewTt4+fNTh0MtNL+kqnsJd7ASjVCRTD+bGJLMy5jGgRheeg5WU3\nXcO5T2SE1tR2y6rJI1sHbNaIfwv3LonANyKBFnBkbJNvTILT6cSS+7yMPRyKSxZmk/EPAMSkQy8o\nfT+cLiADILOVX8lp0zDpkypQjaD68trXwxlAGna/sKcBphXBC0TXWcrBmXkGIJtSyjge0GeY+d3b\n2GTmfQIDAQAB\n','bNAgPha/E8yniSketi0f9/J8J9momuY92i3zoAP6QZibDMII9M8wypURIrGtjfDapiNnA3DnITRj\n2rMxoDMPH7Mz+j0qlNiHKUIbwKdf7iwlsZlxZgQlDMAOgMQY1VVwD8hZPVL+nKNDZTYwsPihR1De\nAyslVR2E78+0uQLG48sdguAq2KTWMencV/DZ/coE9Bo1ug6UnzEIqRO1e5WFcSLhgMWU0WDWdPj6\nzEwdxaPUYYsd4Rv+MVtY1pX5DClpCNY9usUtT9hf9oF/C9aZD9t9NpS8luVBUKpHWcleKlV8ko4/\nZ9M02zj+O2+Aw2h7Wl/qzyGtv82XZzgA8vamkU9gHRTqWkYq0zePBai6w1P9YD+kWFFY33SHnCrv\nR9Gc49ByrzOgBhjXKHkkiyC+/yDctzoanB9gtsdtTcPy2sTFdrTtu3p4+BjQcmjKejmXT+68VeAg\ntrk8lXltXXQdhT/FPXPivsIHnFrRkZZHpljJW8W0GtAaOsSJnkVmf9D653/E6O1iry+To02k78Rd\nCb7m6UuknbhFfQfQz7l/A4kcB1fQ66s2JyEpLI2lAvB5AwaGyJRRm8lHdfvB5eg8Lka63D6hSlzv\n+XhHbYMyAIBKJQCzx5FSZeo+59zwbeAS+IDky4wld6PX/n/iANY22jE/hortXZtKFkkXY46w/CKi\neh6EKx2/bviPbCqmtTPWUTDh5nIjTnFsaU4NlCGFWNXZshYngud1u/s0Y5tdjdBgxtBMbYLhHCqG\n6Xnrx40ItXVTg8uRcdv91ZuziNJQEyEixe3hnBFpxfvruPlhYu5Kja3/+x6HTfelMZI8X9UWMI1R\nN80uK+Sru7DKg6qfSpIe4Vjfqx+iMEKluxFreIuCyoo5+efsgIeGZj4zL6LQV3oNI+wWUlJj0ego\na7GB5KhvhAae+I0VTxAu9FQJK2ak7FRHkfcFGKzSzFeUgR3Y5cY5LI8hq61oKbOY0RzU88rb1LZN\nZTBMOLQO4ZWUwJ1QwqPl0uhxrbZ567jizuasO0RVrehL7JZ1ARXwisUA6RfevGSJBw/8A2Ts8PzJ\nSriEo3taDuC0ep/wuoLYIauawik7G9f2mS3DavnXAb9vPeJKcOY40fePDaHSCOsfLUr4ysLOlP3U\nwCtmMXek1lRgL5UuxdO+d6w5Bc78cxItW84FamL38zZMfpd3kfC6eaXZyE1w1cFFB+r5MgQhOeoR\nuCcTR9a3Spn4kATwy+QBTQT3mEBONF7KZ+o8ZzZpCHqMtOBuvEFlwMc+E9OqqlJT6F+LNZbtVpU3\ntYQifVjLcB5NioWAC4BXpul8YfjevnX+26Apu5i8h2Mc25wyozHGrIjUMr8z/w1QQ83ddJxvhOHR\n39BhRasLdqJ0Gfzfc+t5iHUvF5gqc27eiZsd5JSf2suG4R2/pceDEo6SgLg9Wi5Awtfl2qfslWm5\n05L4fUNWDserwksvIR1JpxkaSGPRk/7u2VyVZVFJHa8/SoSNEd9glilPihA83z1IXhXJntnavVrH\nTBXMdb+SQEibSWFQf5FZDcg1cTdQjP4+W6LahlYVlp4Gnp35SoODPCCZk7kcDvzVvDrctv/EcHSY\n5rppacuz/5YwXJo+3SCazryHlWlHMt0D1mBWUMgXZe5VyGc=\n'),('000-000-0001','MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAqj7DT5EYs+ndRpOm2WSdFqHwdRK7ptld\niSJZcSr+RtvxWHraShTB5Wbo9YKHrDLvTMjj/Hb1hx/GsPXKH0Q8Qv1P41FddbnpiX8blG77/5vo\nOmwGkN380TA8KXtQEH3YYMIQ0T2LC4+xFHBtfM68FwO5B7lIyuCsYHk3a9W9gvT/cC8ZRXEzkHSe\nTuHv5gMe2KGWxcWinM+D034ggbiTuMumdIT0APJ5lAW0mcYgKBZTz/DRp+qQmH/b0OT8nWyMCjJ4\n1hBIN5wighOa5NEczqsRgnHGHEjNVXv7Lgu3YQ1rGGSy82s9HKazu0oWWnBbV3wpND74haslyl1s\nYQun0QIDAQAB\n','KpHbNvOKOR7KOlJTEUvUcgjeRuJzkFjGa/lpgCiAQ4byP672zpz5ZVTkfRChDVmrreY8XFyshdCY\nBsnLIXbvC/1Al6nycaRR1ngdOiJrznyX1ryPSzyoV2bQsVz1k775qg4vc9uGdSH6B5DTODTFItRq\nsHcncs5UnOwL4ToIAUZV8NCHEhKgFhi3HKN5RWQZV/6o8k/70eJgyrBKR+/aOGldACSHPpCfv/2q\nPmoUjyF262rO06FicyO7E/fZt1aEqDDG65VeVDagxK7SNxlXOipGUORoXG7nNyLpyeTAXg0duavN\nEKXt4MupFMQWryg7OOXS1GOPDgIpXfwU4XjQfYkS7mkKCL+61oxID+BHmXjX108e0FhSw6DHq4Iz\nfE7CSOXgBupxYdg7lvnJwAgtyCcAcSgUXzoHGwY3NdgV3J40cd/3APfY8/VRXARo9136/eG2GyJm\nvX7n7n0ZM/oG5OA1Vj/uIzTUEce3iIPECjwN1i0SE4c7EZYeOqvtzBXdAQk4Ho82sWHwe8pjp60M\n0Qrnd+a5whYZcWfV1748O25gThoBPBEmv/qw0PatereX8FeHXC/4BjD7LzkXcEtdifSP2CvdpSnP\nHV4kbNnb1i54kHAhBFt7S7x72KBw9lh/EXdKCRVFhR92gG2un2lgd/0E0UxCFJK8q6DoicZHnv69\nZnHvIPLqsBNqKkeAq7mG5+FQWxxDn2v3J7UXsaL0akW2myRlXdzJ7BwHBN3L+5zXlOAbB7RIYcwR\nB/1IsVP77jimR31XmR+wnF1I2kEtJAlmM2Ls1Td1URydePXRx3gSV4KezzgxCGVZLcMl9iuhLapX\nFavScyEg4H5FAqu/cl0QV3SVPBvdklW3TGxYHe7qNlLfGWaABq+wiaXuoCRMAbjm+TUdB7Bkbr45\nFwIhZNOz0BPulj0ITirPdXhI5W7rzj6+JjlWF8jyqfSs35vs2plRs4QmngxJTqWM7hmD00o2AiXG\n4eMuD0tdQf2yJfgt6UyWzPkCOgBuvUf+/ljYkuyzrBYx1LGPZitXcKTRy/yYGgkOycG1A2WJ/BZj\nc1NMFhgsLOeN5wsmKN4QWfJHE5KaLS2FI28b68vlDsnpMEqm4A3HqPGWNsc5hyezfoT6vZM+uQrB\nHvH7k/Ri+QHyvTo5TxJW3fo5+pNMHYC9ux+9yafk+u59srV0OcZ7MFXvZuRUL6oQUbd4Xy9kccDY\nB9yWn7oBjBr+pB+2IfcU6SeC3DI4tj9RMwhWYSyTNO/ERzib1wA2pPi0uGbLWi9ZYHJcbpGD3ZZz\nu7n6PbtBEO0hJNExZea8MkoDaCbRbm2frCdhf7c/+6+5T0h+l+6uUrFo0j6ooR0JKBGn1Caisxc7\nlxkLS033xgWmfw1ymaIwVRALIgSco4MZCMlFwSA+ETcavHG6MFlJ0mHMrTmfdWfvY/HpPo/bEnsD\ngJjRozuiC7eZqQuI4CHT+UP/RgrLVAGtGubMIdXUKQPNx5P2SxPuXoDE2WDyDpvhxjdTAbISIE+G\nleiAV/J6A16E4cadc6Z23mv2f+krWYPGIyC+tE+BEp44XaRh0zWTWPB1S72gV9g/2UU9u3HnsM0F\nuCozB7aP9xxYHvDqHusmXER0oXe3LDCHW8Uz2y7Nb45bQ8A=\n'),('30ddd48f-e9c6-4d17-b23e-d2ddb5532241','MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAu80Z0EYv+jTyW7ctDfUCdiU+n4eOQo+N\nOesNxxFJ4vJ/S6aGZAcvDWjzc5L1TeiiMkEmUjQqqV+BuksOGWdRM7ETiWsyEVUXMcw3mOZt2N3Z\nB2IEuLXSySWwuScCFc85YET+B4zgbKhqtfZQ5Nl4CyUcvxcLcX8w+K0lcPlUooksgqcu2FNygFBC\nfmItaQyiNPZT+Y/1hoyJryquZeJpi92/QClQZxm7a22jChb3aq5L358POLkkjpaCOuC7N/0sxZGQ\nypU3AXdR7MX1DVTp8i3AL03PyGBzvQpKSvyJhEAta6ZAkaget4H0a/o4hszieXlUeu6B4UahgZtj\nNyDFLQIDAQAB\n','KpHbNvOKOR7KOlJTEUvUcgjeRuJzkFjGa/lpgCiAQ4b64RjdsZ/q9nhJyTUCLaiUgN4NULdUsBJG\nfkMlmApPBxEAyJIu2rkuDP6SsW71C7BLopged9d7RMWsGgDommrghcq7T8HkXx7dHET43G2s2qD9\n1JMG37tY4fchEt5GS54bbVSzS8o/AHQmcvwciv+GpSa+UMZcyRNdskaZBw32/FfFBmOqJtsaHZkX\nW6t2/JFWELypCBdbgCFllemh+kZMe9O417HGo/tT5FTXIFClOAqR34KWjEP6708LI25dLGBMUBGc\n5OVvmy4DCb4sCLPnPxxP0YstRr/gwdujxvKVhW5UnX/7+oNIo4C1plYSpMd57497ADtNd5n34k3z\n1eb1lrFpEzZWQnkYeQiNiugXpUPFp8O9giEuUchTPszhAejCUD6xLzT0l7Ypc6zXxo0c+4bdf9xz\nbTngRFlrukeKPGwGhVc9pOfep5Sgr7gkpyFBAUXkDz1CzkPUDSQocyB1dg/LcJVzgs3Vvt9fYdqG\nOwrbvRXqOPgQ/FYPujiA7LYWNFC1HEvMl2Uijl5XPSjaeuET4qjCl9s7xSvJ8KDzA3uNvJd28lsn\n3HADfrIpI6UGHbXSHwpvJVhB0vl48Ej/Y7zcNHiN2LRKiVg2q5kKjXOMDnzCegBs0QtXbwCqOHK7\no9uw9PU55O9tkOqnP6zZ3aYoRfUZCbs18HdsQt/iur1+YP3Ry84ot5W8eLxO5WexqSUaIHbz6YXc\nLwbQJZsMfGLMDMqrJlSRx2DgKb7inepRSGB+LCoYgDj19NhfVy6Zuuo8aSIG2ojc0QcwwR4ziQrW\ndZataMla1Y1ES31cQkrBJ8D54VEf4VjvzfFLCcdF2A3NbEpRIalncRKOQbB88SL9JGe2UjCgNnVw\nDNqbvuu77xysYnCjHeJwQvI6Ay92QQ4iab14oROlXVFBDo/hs7ByzkuNSWK/YmWTWIbx7dfZ9I47\n7JOhyd+6Wzh9b4+zvFiRXRjTopFTZ7hHadCwzYxcmJaQK34j46Hw3wYiTHN/D20URcBcq5Dm2d4Z\nX8a6LrYUFwNwApYXz91ONhlhEl0mrfzyKnUlgpXtJayG+MioMVlIqEunL5s4vQIeyxGtlOJGHf0L\n1iEcM03cXSIXn8ocN4R9eXApTQy/fr+zVlZzazzyAgoayKNZ232yYPk0fXntGtqMM+aBAhLw5om6\n562oc76p5IkdeRXOhsKXQTBJUTLw819MKRw45VDRud9WruXCkm4ZAMXjR9M0QZ1OkDxeU2cqrayn\n1IuaGYzvpGgnZA28rIF+L89Ajge2rJ7piSQMKmnoyBLPGN6bZKVXrgDMILr8YegC9d4zfbhLWF/c\ncJ84B8pEPrujsxV45NuEvK4geuzK20y3xuMxFKdbHBMkKcD0ot8oZLU11ooLXlTm2RxGGoaKDQET\n2bZLFuVNuUFKBwJ0aBzjYipS6bIBTPw9f9tkP+2OHoAjEiFwifyiVosV41KNOAgPnqRuGpqVFzcX\ngoK7GA8EHqI19+Q3p9gVU4Q9Ll5tnEj2kV0MuzMVrF8Dn56k+bCYKom4EWRGtOWDK3XDDhpFRstd\nIOEglb8+LABzMkZvRALNnN91avLV8typauXylse1iqxL0wY=\n'),('44475cc7-bc65-43a7-a612-45f21aaf7a63','',''),('46553eb2-510b-4aab-864c-6c4e6c51eb6f','',''),('57dff8a3-b847-4273-a02b-8d5b9a5769db','',''),('624f4e8d-539d-4354-bdd9-87ca5738a3ba','',''),('68f0414e-a146-42a2-9762-6148cba4699a','',''),('6aed2589-c7ed-4d35-af92-478da4109c64','',''),('986cc8a6-660b-4d98-8341-46c42c626ae4','',''),('a2d1be20-7ad2-4bfe-bd3d-c9173b3ee824','MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAzEqUuA4HjNd9UpluKx12vyYwubn1atPR\n9pavOStgICbx9RQ/2Nn07VZrwC94osD+NyRfWYtzXSZTWprne1J+7vg7w+i0yPwtMLYCYZv/aGpB\nN0nFerJE9sOvolc+gBFWIZf8Mz8RIhaitZ9dAeeki4UGgO32OiT/l9cGt3YORRPc0g9mVFyMThPg\nAcNQ83TWrSkN4BJaAocq8rq0KgaHJfYT4gJ3UCry54OSTYH0eQ9XO5+Dj7hus652GyBAebvWUUsg\nV4ogW6sI88WU6UD+XeYHMSJXFZVdRp4m/9D2W3git7GFoqRGLiSC5MDQr+6+X+3+/ZMGuYfKHIdy\n5jaW1wIDAQAB\n','bNAgPha/E8yniSketi0f9/J8J9momuY92i3zoAP6QZj7fIyQhNWbyf/iubAQkYpABehNKs90yeuG\nOR0JQdxJR5NGxH9Va68xXYrnNf1eXR9lKtk76z6RR1b8+0Elc60FQLTVQOfPAS1Aja54RH+rowQc\nSdYstwOm72Fyjm5K9Kd2pQQt3jti1ktWMyzvlofBqvNx0W2AV3ttd6gC8VGlqJYQpHyX4bNnBnkz\nu7gtKGeFHCcBym956azQGlz2GHa2sunyOXgKDSWTAb2RZs0fNeYAZ5cWcvZWp2I1FJxLn/2WOFuG\nc03zjaXP6RESCCPhI44AwOG7UQ36oalZCxIKLIyvZQ34vb2pRx6tu4BHycH52yQ5LX6Shy2DMiVY\nokLOR7rYDmppI8yLMKCNC6kIZCGUgJDjYAw2FQFVyG/TuEi5IZZm6kIZ/n0czEckIRd/7op5HR5B\n2/VnNoJ2wWnXfAAnpw3Mxt5jq6vPUWYUDH5VwJEdn4Mvwhnn9SJekjHHv/XiYmaKvBTqezgXrve0\nkl0HHhPWv1tInukhKh5Y6taNXwuo2BqDK/55eAAfmIrrvphz9v+oey8uAfeOmfE5JyilsOrGz+Et\n1JffMSNhyJ3I+TI2vvYK7svs0l8ft+l6rIbxtmbdWpHN3iKEnfObrlYmuUCuhK3wlyFJE+NBIJpz\n7tFOAjBcAN1XleqKx2a2AxXe3pR+efIPXfpJBxaJT9QcFR7beBW+IOH0W+qzwbotCgogfr1fiO5c\nZoQaH8Nr4yvkzvTSkhHeXYCJLXTg6NxZzQLIIQ5CywNkzXm9g2SlA3kHozvp7sKgBhdf9Qwwv0d3\ng34Q6I364KLshLeSkPfYYs83EAEsQIRq//mBoA/RLvv82Ccv/9CckA+Z0WMj4mj2m6luSWeh8x/k\nqWF2D0LE+N7gefQpb5GK7+SUu5Xfb9mJlO/xvzLmdRfmiEM2woZFxv6sjxIx7k/2q9SIBn9Swd3G\nihUzrmPB3CDplmbjgq3gs+2PzqO9yiGm/KUtXT7BUHwlyOn87X6JlHY7Q5PbckUjFDEEG0IVdegZ\nS4h9Zi3ekxnZ7i0EVy149+bb0e845aCkw1/9mlFrvAQs9rbi/DceoWoo6H+u6MAGdn4my9C3MFLh\nDq6XicX4dBpCjD44e/dmjKCOUqp1iVJTWUzZCrcywSTmdp+QNLhwOPlRRuIwgNgaaCunXaoKh8IF\nBlCXy+PTjI+oP+yObAhWh7CWoQWYBKXoPk0X6sFdKFYx7Rf0LYP8W8O/5hsBNQaR/cE/MbrZY2Qm\nQ+0TqI2KMdeg1Lkypufc/riAHYJuxMKxhP8G7IsyeN0S7fKetWfGLZ0LzEUNhZVAYVT0e+6uihOk\nE4/puFSseEy9IQHAiGjz0kai1xRgwIZHmyOahA52m3dca7OnnB36fIu6Wo1PC0UWqIgXnkEWr2Z7\nGLs4zjWugpRKXKSdLToauHcNQr+QvBVXG1GF9HJkUIKhnyfr9QHgn2tC7YfDDLuz3ijTZ1J2WLZ/\nSIPB19QMVYp3aCyXfR6ICL/F2ndEStV59Fr8fguCziXt9yGWjmt/GEQX+3wFmi7x/X49qpha6xzM\nxtqoLoANTBIiB2DXjq3Q3JmMxg8MeSzxo8aHD6Nn3OifEIY=\n'),('d02dd4f6-66ab-4d24-9c26-b298d07fed64','',''),('e758cb50-3d30-498a-a0a9-5bc3a876b8c8','MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA2Vc4PJDoA/sFaQSR+l9WZpkmhN0silvE\nDVxjNwY07IeOSg9qaxq2w4xPn6M+MMqdsXdoG7Qrx4I9QKAiO0ootJQVqyd0WoZwWKdqqjHkG0GK\n5Kv02DD5WqeDrRCr6Ol+VxqAn8fNOjdJlNogK3xJwNMU8Z95CJMr6UcKJRE0XDvdtHFxciAYdghL\noF6hN3bmxhq8tzJNORCyVw43OSfbQFy7w87SU0zOxuF5KxcOutGaM5Kgi/qqQ0AnAoB6r3NGtSvX\nigqhCO+A+w9zZns9EU0kizu/h5f1PBuUfkvOvviYpO+NaUwbP+vC1kvfSuiMn6/SuuI2Pu3rBdvh\nYejxfQIDAQAB\n','q1xzfo6P/lu7Q1nngZTTesnAuWT9XvK09YPZ1kGSn40t+qBQVDrcJ692lE+0PS2Ii/tpwNb701LH\nhdcixBvGaJj219G4l2DFRrf0ikrCj+LWHAgCuhumnm58c1NmQJqmB5YHn7wbWyNT6cDD07VtqdqQ\n2l/7iUxw+DxwMIfPOMYIze75jH2iH8yKrYKzR8OnCpppyaFHKX8/W2KlFa3iClahBT4lvr7BJDZU\nNXEar7ViOQLhNbBySE8ReTsYDd7VnCVKd6CKlBeidPOiZQi+ZU0w9zzR2pu8Azc7q2sB0Bu2MEyM\nHX79mwZbZA595zvRVIwR0GIvNQJjWP0ZwqiwspMP4pkO4gSYUHbj1u0CmWCn+dWBS1TgP2BspLaH\nujxo0WWEbyWYm+8t4eRy/GKGNOs5HxRObGoEYuhatk2047hNCbuOGfEvwSeQRXwsqGG2mPkc3urx\nJxB7CbZ8zDLMXHmrmW+E37Ev1nz2JS3XvC+ekzC8NgmdDafSrZTK/kzi3dwd8KlfbuEndxc6k1ed\nP87mYMzlWZ7DJ5BV87I8/YMSkPNWBNGTskSjJ+i8YhLxOlFjhsAtrkI27kIxHCX2d7HxVX8QWMug\niy8WpxbZjq+/WEer47jGaPLeRrfdcfuY7lgCCA9wQkPKmRXZYqlr7FfDqc2sQ481mJGxmEDOnEhM\n6iaj6kPkL2l84NK7aW/E/7jIrBjzVpvyYsesd/wUHEP0N/4nvIUZ5kaTpgjz8TQrEDETiethF2hU\n2NgZn8jLD5D6DJOVjbHvIh1F76xKeKWW7xbCteRldZMjCzPRs0i/dY6fG+NOJgfYP0jAlcS52EQD\n37lxM4fuhU3vR2/crK7KvF0BDFOpKDEepermtv5aAwBXlAI3r+M2XMCPVSQ/s1E9Fl0dYf31jSnY\n+Ka4EXDPycio6AJzdS4/WA+50rcEpxCZ1ZFskeXfZHlaOzFYTa3E5MkKTbYrn6pxJ6T2/BGuchN9\nDHaR0UQAvHeiuUr2j3kmOEcO/5e1KV7BmVj5/0nina+u0PxR21MVHcRVXgdZ7IXac8jPgRAG5bzm\nXV0hTZWqhERhpyBIzXUvNySWJ2B69UMfEUYW1ZCYO+cWQrpwYCzUPPu5nWzTCl/i9z0estyQb1w4\n0rsshSZN/36ejUK0q/hO5I52mD82BdEWz2AMBsryUmM/YKrzD1DEXNhaRSstsyULbnMbYsbFCs0U\nO/q77B3VYFt1BxoWWsu4R1K02j3BOZbVQg2XFZIAyDeYN3Dn1kOaI2r+ZUE1ZoD8O9HyKc0/oHbM\nEGCJOR4iRSK/8KYT8GLsSUKuHAPbcY2K9rvofQwuId7wBMLCv+GZseyDJ2UdJPeGRXVRLuId46CM\naSmOvJoKiet1VbcBUhgpvF28CadEWe25ph2j2YPlOq7S90Pyz+td4EsXOUJu7hZk6tJ4ZfmWUiyG\nGdX1hWyc6LilmjXxBbT2SI8nLK5ZbqbR7L/RHZYxaypMScbhdgyJIgPWN0mI190q1QDHJt/Ned3B\n3IEKhjT5AXOtZC4CyUePY7x205kOL8ffOJznV/clSAtvBinEipFJrag+FWZqfyi+O1BjvamGMTye\nipwm4WXzTJVIyP6zbrxjBak3KeOh9SDmEnAJBn9O0FJrdmk=\n'),('f679a196-f5e2-4039-bbae-2fa6fd0e90b8','','');

/*Table structure for table `users` */

DROP TABLE IF EXISTS `users`;

CREATE TABLE `users` (
  `uuid` varchar(64) COLLATE utf8_unicode_ci NOT NULL,
  `account` varchar(64) COLLATE utf8_unicode_ci DEFAULT NULL,
  `password` varchar(128) COLLATE utf8_unicode_ci DEFAULT NULL,
  `create_at` datetime DEFAULT NULL,
  `name` varchar(128) COLLATE utf8_unicode_ci DEFAULT NULL,
  `token` varchar(128) COLLATE utf8_unicode_ci DEFAULT NULL,
  `enable` tinyint(1) NOT NULL DEFAULT 0,
  `token_time` datetime DEFAULT NULL,
  PRIMARY KEY (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Data for the table `users` */

insert  into `users`(`uuid`,`account`,`password`,`create_at`,`name`,`token`,`enable`,`token_time`) values ('000-000-0000','giaynhap','$2a$12$xiAKipqM07vxwbtc/FwaOO0zAFbaYbHTAh5nlLLbZTo.yb/gpmxB2','2019-11-18 00:00:00','test',NULL,1,'2020-01-09 00:40:23'),('000-000-0001','admin','$2a$12$xiAKipqM07vxwbtc/FwaOO0zAFbaYbHTAh5nlLLbZTo.yb/gpmxB2','2019-12-07 00:00:00','test2',NULL,1,'2020-01-09 00:31:53'),('30ddd48f-e9c6-4d17-b23e-d2ddb5532241','admin1111','123456','2020-01-08 11:19:00',NULL,NULL,1,'2020-01-08 11:29:40'),('44475cc7-bc65-43a7-a612-45f21aaf7a63','admin1234','123456','2020-01-08 10:49:05',NULL,'0771985',0,'2020-01-08 10:49:05'),('46553eb2-510b-4aab-864c-6c4e6c51eb6f','asdasdas','$2a$12$Q7H4wwEoD5GWQ7T6Ntq.UOBdyqni/aY9y8nEO58noEe6r9KuAQ0Gi','2020-01-08 12:17:41',NULL,'1',0,'2020-01-08 12:17:41'),('57dff8a3-b847-4273-a02b-8d5b9a5769db','dovanthuc','$2a$12$MBQQsfPnSr2AT8OzV.ZTEOU9ygarLcNbr0pwmMh8Zc3b3RVOm/Qn2','2020-01-08 12:14:07',NULL,'1',0,'2020-01-08 12:14:07'),('624f4e8d-539d-4354-bdd9-87ca5738a3ba','admin123','123456','2020-01-08 10:47:00',NULL,'0670361',0,'2020-01-08 10:47:00'),('68f0414e-a146-42a2-9762-6148cba4699a','admin111','123456','2020-01-08 11:03:21',NULL,'6323467',0,'2020-01-08 11:03:21'),('6aed2589-c7ed-4d35-af92-478da4109c64','administratpr','$2a$12$t44uQIPr2ez71aOwYijrGeg9pFsnYuaijSD01GZadlmKRFJJUzGF.','2020-01-08 12:16:17',NULL,'9197935',0,'2020-01-08 12:38:12'),('986cc8a6-660b-4d98-8341-46c42c626ae4','admin2222','123456','2020-01-08 11:05:08',NULL,'4623248',0,'2020-01-08 12:37:44'),('a2d1be20-7ad2-4bfe-bd3d-c9173b3ee824','admin123456','$2a$12$xiAKipqM07vxwbtc/FwaOO0zAFbaYbHTAh5nlLLbZTo.yb/gpmxB2','2020-01-08 12:28:08',NULL,NULL,1,'2020-01-08 12:38:41'),('d02dd4f6-66ab-4d24-9c26-b298d07fed64','giaynhap1234','dovanthuc987','2019-12-12 18:58:29',NULL,NULL,1,'2019-12-12 18:58:29'),('e758cb50-3d30-498a-a0a9-5bc3a876b8c8','giaynhap123','dovanthuc987','2019-12-12 19:02:28',NULL,NULL,1,'2019-12-12 19:02:57'),('f679a196-f5e2-4039-bbae-2fa6fd0e90b8','adminabc','12345678','2020-01-08 11:01:00',NULL,'8941009',0,'2020-01-08 11:01:00');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
