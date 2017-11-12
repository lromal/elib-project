CREATE DATABASE  IF NOT EXISTS `elib` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `elib`;
-- MySQL dump 10.13  Distrib 5.7.17, for Linux (x86_64)
--
-- Host: localhost    Database: elib
-- ------------------------------------------------------
-- Server version	5.7.18-0ubuntu0.16.04.1

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
-- Table structure for table `myLibAuthors`
--

DROP TABLE IF EXISTS `myLibAuthors`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `myLibAuthors` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `description` varchar(255) DEFAULT NULL,
  `full_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `myLibAuthors`
--

LOCK TABLES `myLibAuthors` WRITE;
/*!40000 ALTER TABLE `myLibAuthors` DISABLE KEYS */;
INSERT INTO `myLibAuthors` VALUES (1,NULL,'a1'),(2,NULL,'a2');
/*!40000 ALTER TABLE `myLibAuthors` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `myLibBooks`
--

DROP TABLE IF EXISTS `myLibBooks`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `myLibBooks` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `description` varchar(255) DEFAULT NULL,
  `id_author` bigint(20) DEFAULT NULL,
  `title` varchar(255) NOT NULL,
  `year` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_id_author` (`id_author`),
  CONSTRAINT `fk_id_author` FOREIGN KEY (`id_author`) REFERENCES `myLibAuthors` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `myLibBooks`
--

LOCK TABLES `myLibBooks` WRITE;
/*!40000 ALTER TABLE `myLibBooks` DISABLE KEYS */;
INSERT INTO `myLibBooks` VALUES (1,NULL,1,'b1',12),(2,NULL,2,'b2',12);
/*!40000 ALTER TABLE `myLibBooks` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `myLibFiles`
--

DROP TABLE IF EXISTS `myLibFiles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `myLibFiles` (
  `name` varchar(255) NOT NULL,
  `id_book` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `myLibFiles`
--

LOCK TABLES `myLibFiles` WRITE;
/*!40000 ALTER TABLE `myLibFiles` DISABLE KEYS */;
INSERT INTO `myLibFiles` VALUES ('mylib/HellowWorld1.txt',1),('mylib/HellowWorld2.txt',2);
/*!40000 ALTER TABLE `myLibFiles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `users` (
  `userId` bigint(20) NOT NULL AUTO_INCREMENT,
  `description` varchar(255) DEFAULT NULL,
  `loginId` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `role` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`userId`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,NULL,'sa','c12e01f2a13ff5587e1e9e4aedb8242d','admin'),(2,'descr','qwe','76d80224611fc919a5d54f0ff9fba446','user');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-11-05 20:22:24
