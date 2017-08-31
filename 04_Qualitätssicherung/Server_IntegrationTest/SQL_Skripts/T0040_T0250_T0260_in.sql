-- MySQL dump 10.13  Distrib 5.7.19, for Linux (x86_64)
--
-- Host: localhost    Database: pse_development
-- ------------------------------------------------------
-- Server version	5.7.19-0ubuntu0.16.04.1

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `ADMINS`
--
SET autocommit=0;
DROP TABLE IF EXISTS `ADMINS`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ADMINS` (
  `group_id` bigint(20) NOT NULL,
  `uid` varchar(255) NOT NULL,
  PRIMARY KEY (`group_id`,`uid`),
  KEY `FK72CD5B0450EB524C` (`uid`),
  KEY `FK72CD5B047060D96F` (`group_id`),
  CONSTRAINT `FK72CD5B0450EB524C` FOREIGN KEY (`uid`) REFERENCES `USERS` (`uid`),
  CONSTRAINT `FK72CD5B047060D96F` FOREIGN KEY (`group_id`) REFERENCES `GROUPS` (`group_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ADMINS`
--

LOCK TABLES `ADMINS` WRITE;
/*!40000 ALTER TABLE `ADMINS` DISABLE KEYS */;
INSERT INTO `ADMINS` VALUES (1,'1');
/*!40000 ALTER TABLE `ADMINS` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `GOING_USERS`
--

DROP TABLE IF EXISTS `GOING_USERS`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `GOING_USERS` (
  `go_id` bigint(20) NOT NULL,
  `uid` varchar(255) NOT NULL,
  PRIMARY KEY (`go_id`,`uid`),
  KEY `FKEF02374350EB524C` (`uid`),
  KEY `FKEF0237437298C4AB` (`go_id`),
  CONSTRAINT `FKEF02374350EB524C` FOREIGN KEY (`uid`) REFERENCES `USERS` (`uid`),
  CONSTRAINT `FKEF0237437298C4AB` FOREIGN KEY (`go_id`) REFERENCES `GOS` (`go_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `GOING_USERS`
--

LOCK TABLES `GOING_USERS` WRITE;
/*!40000 ALTER TABLE `GOING_USERS` DISABLE KEYS */;
INSERT INTO `GOING_USERS` VALUES (1,'2');
/*!40000 ALTER TABLE `GOING_USERS` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `GONE_USERS`
--

DROP TABLE IF EXISTS `GONE_USERS`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `GONE_USERS` (
  `go_id` bigint(20) NOT NULL,
  `uid` varchar(255) NOT NULL,
  PRIMARY KEY (`go_id`,`uid`),
  KEY `FK11173E4850EB524C` (`uid`),
  KEY `FK11173E487298C4AB` (`go_id`),
  CONSTRAINT `FK11173E4850EB524C` FOREIGN KEY (`uid`) REFERENCES `USERS` (`uid`),
  CONSTRAINT `FK11173E487298C4AB` FOREIGN KEY (`go_id`) REFERENCES `GOS` (`go_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `GONE_USERS`
--

LOCK TABLES `GONE_USERS` WRITE;
/*!40000 ALTER TABLE `GONE_USERS` DISABLE KEYS */;
INSERT INTO `GONE_USERS` VALUES (1,'1');
/*!40000 ALTER TABLE `GONE_USERS` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `GOS`
--

DROP TABLE IF EXISTS `GOS`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `GOS` (
  `go_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `description` varchar(255) DEFAULT NULL,
  `end` datetime DEFAULT NULL,
  `lat` bigint(20) DEFAULT NULL,
  `lon` bigint(20) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `start` datetime DEFAULT NULL,
  `group_id` bigint(20) NOT NULL,
  `owner` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`go_id`),
  KEY `FK1146B573D80AF` (`owner`),
  KEY `FK1146B7060D96F` (`group_id`),
  CONSTRAINT `FK1146B573D80AF` FOREIGN KEY (`owner`) REFERENCES `USERS` (`uid`),
  CONSTRAINT `FK1146B7060D96F` FOREIGN KEY (`group_id`) REFERENCES `GROUPS` (`group_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `GOS`
--

LOCK TABLES `GOS` WRITE;
/*!40000 ALTER TABLE `GOS` DISABLE KEYS */;
INSERT INTO `GOS` VALUES (1,'xxx','2025-08-17 19:41:00',49,8,'xg','2020-08-17 19:41:00',1,'2');
/*!40000 ALTER TABLE `GOS` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `GROUPS`
--

DROP TABLE IF EXISTS `GROUPS`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `GROUPS` (
  `group_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `description` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`group_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `GROUPS`
--

LOCK TABLES `GROUPS` WRITE;
/*!40000 ALTER TABLE `GROUPS` DISABLE KEYS */;
INSERT INTO `GROUPS` VALUES (1,'Default Descriptionn','Foo');
/*!40000 ALTER TABLE `GROUPS` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `MEMBERS`
--

DROP TABLE IF EXISTS `MEMBERS`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `MEMBERS` (
  `group_id` bigint(20) NOT NULL,
  `uid` varchar(255) NOT NULL,
  PRIMARY KEY (`group_id`,`uid`),
  KEY `FK635A54F950EB524C` (`uid`),
  KEY `FK635A54F97060D96F` (`group_id`),
  CONSTRAINT `FK635A54F950EB524C` FOREIGN KEY (`uid`) REFERENCES `USERS` (`uid`),
  CONSTRAINT `FK635A54F97060D96F` FOREIGN KEY (`group_id`) REFERENCES `GROUPS` (`group_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `MEMBERS`
--

LOCK TABLES `MEMBERS` WRITE;
/*!40000 ALTER TABLE `MEMBERS` DISABLE KEYS */;
INSERT INTO `MEMBERS` VALUES (1,'1'),(1,'2');
/*!40000 ALTER TABLE `MEMBERS` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `NOT_GOING_USERS`
--

DROP TABLE IF EXISTS `NOT_GOING_USERS`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `NOT_GOING_USERS` (
  `go_id` bigint(20) NOT NULL,
  `uid` varchar(255) NOT NULL,
  PRIMARY KEY (`go_id`,`uid`),
  KEY `FK21384FB750EB524C` (`uid`),
  KEY `FK21384FB77298C4AB` (`go_id`),
  CONSTRAINT `FK21384FB750EB524C` FOREIGN KEY (`uid`) REFERENCES `USERS` (`uid`),
  CONSTRAINT `FK21384FB77298C4AB` FOREIGN KEY (`go_id`) REFERENCES `GOS` (`go_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `NOT_GOING_USERS`
--

LOCK TABLES `NOT_GOING_USERS` WRITE;
/*!40000 ALTER TABLE `NOT_GOING_USERS` DISABLE KEYS */;
/*!40000 ALTER TABLE `NOT_GOING_USERS` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `REQUESTS`
--

DROP TABLE IF EXISTS `REQUESTS`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `REQUESTS` (
  `group_id` bigint(20) NOT NULL,
  `uid` varchar(255) NOT NULL,
  PRIMARY KEY (`group_id`,`uid`),
  KEY `FK17354FC450EB524C` (`uid`),
  KEY `FK17354FC47060D96F` (`group_id`),
  CONSTRAINT `FK17354FC450EB524C` FOREIGN KEY (`uid`) REFERENCES `USERS` (`uid`),
  CONSTRAINT `FK17354FC47060D96F` FOREIGN KEY (`group_id`) REFERENCES `GROUPS` (`group_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `REQUESTS`
--

LOCK TABLES `REQUESTS` WRITE;
/*!40000 ALTER TABLE `REQUESTS` DISABLE KEYS */;
INSERT INTO `REQUESTS` VALUES (1,'3');
/*!40000 ALTER TABLE `REQUESTS` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `USERS`
--

DROP TABLE IF EXISTS `USERS`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `USERS` (
  `uid` varchar(255) NOT NULL,
  `email` varchar(255) DEFAULT NULL,
  `instance_id` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `USERS`
--

LOCK TABLES `USERS` WRITE;
/*!40000 ALTER TABLE `USERS` DISABLE KEYS */;
INSERT INTO `USERS` VALUES ('1','bob@gmail.com','instanceId_bob','bob'),('2','alice@gmail.com','instanceId_alice','alice'),('3','peter@gmail.com','instanceId_peter','peter');
/*!40000 ALTER TABLE `USERS` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hibernate_sequence`
--

DROP TABLE IF EXISTS `hibernate_sequence`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `hibernate_sequence` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hibernate_sequence`
--

LOCK TABLES `hibernate_sequence` WRITE;
/*!40000 ALTER TABLE `hibernate_sequence` DISABLE KEYS */;
INSERT INTO `hibernate_sequence` VALUES (1),(1);
/*!40000 ALTER TABLE `hibernate_sequence` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;
COMMIT;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-08-27 14:01:17
