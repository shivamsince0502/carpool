-- MySQL dump 10.13  Distrib 8.0.19, for Win64 (x86_64)
--
-- Host: localhost    Database: carpool
-- ------------------------------------------------------
-- Server version	8.0.32

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `admin`
--

DROP TABLE IF EXISTS `admin`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `admin` (
  `admin_id` int NOT NULL AUTO_INCREMENT,
  `admin_name` varchar(655) DEFAULT NULL,
  `admin_email` varchar(655) NOT NULL,
  `admin_mob` varchar(10) NOT NULL,
  `admin_username` varchar(655) NOT NULL,
  `admin_password` varchar(655) NOT NULL,
  PRIMARY KEY (`admin_id`),
  UNIQUE KEY `admin_email` (`admin_email`),
  UNIQUE KEY `admin_mob` (`admin_mob`),
  UNIQUE KEY `admin_username` (`admin_username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `admin`
--

LOCK TABLES `admin` WRITE;
/*!40000 ALTER TABLE `admin` DISABLE KEYS */;
/*!40000 ALTER TABLE `admin` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `car`
--

DROP TABLE IF EXISTS `car`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `car` (
  `car_id` int NOT NULL AUTO_INCREMENT,
  `car_name` varchar(655) NOT NULL,
  `car_color` varchar(655) NOT NULL,
  `car_number` varchar(655) NOT NULL,
  PRIMARY KEY (`car_id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `car`
--

LOCK TABLES `car` WRITE;
/*!40000 ALTER TABLE `car` DISABLE KEYS */;
INSERT INTO `car` VALUES (1,'BMW-SF','red','TS09LADF'),(2,'LAMBO-NSF','black','TS04JADF'),(3,'MG-HECTRE','grey','TS09LTFF'),(4,'TATA SUMO','Black','TS08YUIO'),(5,'MG NEXON','White','TS06KJHG'),(6,'Mercedes-Benz','Black','TS07YUI'),(7,'Volvo - Elite','Grey','TS03GHJK'),(8,'Pajero','Blue','TS09H434'),(9,'MG-HECTRE','Black','TS09THDF'),(10,'Jaguar','Black','TS09SDFG'),(11,'Mercedes','Black','TS06HJKL');
/*!40000 ALTER TABLE `car` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `city`
--

DROP TABLE IF EXISTS `city`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `city` (
  `city_id` int NOT NULL AUTO_INCREMENT,
  `city_name` varchar(655) DEFAULT NULL,
  PRIMARY KEY (`city_id`)
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `city`
--

LOCK TABLES `city` WRITE;
/*!40000 ALTER TABLE `city` DISABLE KEYS */;
INSERT INTO `city` VALUES (1,'Gachibowli'),(2,'Shamshabad'),(3,'Mallapur'),(4,'Kukatpally'),(5,'Hi-Tech City'),(6,'Habsiguda'),(7,'Jubilee Hills'),(8,'Banjara Hills'),(9,'Manikonda'),(10,'Uppal Kalan'),(11,'Quthbullapur'),(12,'Shamirpet'),(13,'Hyderabad'),(14,'Secunderabad'),(15,'Saroornagar'),(16,'Sindhi Colony'),(17,'Miyapur'),(18,'Ameerpet'),(19,'Lalaguda'),(20,'Uppuguda'),(21,'Yousufguda'),(22,'Toli Chowki'),(23,'Balangar'),(24,'Malakjgiri'),(25,'Hyderabad'),(26,'Chilkalguda'),(27,'Basheer BAgh'),(28,'Bagh Lingampally'),(29,'Laad Bazar'),(30,'Edi Bazaar');
/*!40000 ALTER TABLE `city` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `deletedride`
--

DROP TABLE IF EXISTS `deletedride`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `deletedride` (
  `deleteride_id` int NOT NULL AUTO_INCREMENT,
  `ride_id` int NOT NULL,
  PRIMARY KEY (`deleteride_id`),
  KEY `ride_id` (`ride_id`),
  CONSTRAINT `deletedride_ibfk_1` FOREIGN KEY (`ride_id`) REFERENCES `ride` (`ride_id`)
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `deletedride`
--

LOCK TABLES `deletedride` WRITE;
/*!40000 ALTER TABLE `deletedride` DISABLE KEYS */;
INSERT INTO `deletedride` VALUES (4,3),(3,6),(28,7),(23,8),(2,9),(22,9),(1,10),(21,10),(20,11),(19,20),(18,21),(17,22),(16,23),(15,24),(14,25),(13,26),(12,27),(11,28),(27,28),(10,29),(9,30),(25,30),(26,30),(8,31),(24,31),(7,32),(6,33),(5,34);
/*!40000 ALTER TABLE `deletedride` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `deletepoolerride`
--

DROP TABLE IF EXISTS `deletepoolerride`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `deletepoolerride` (
  `deletepoolerride_id` int NOT NULL AUTO_INCREMENT,
  `ride_pooler_id` int NOT NULL,
  PRIMARY KEY (`deletepoolerride_id`),
  UNIQUE KEY `ride_pooler_id` (`ride_pooler_id`),
  CONSTRAINT `deletepoolerride_ibfk_1` FOREIGN KEY (`ride_pooler_id`) REFERENCES `ride_pooler` (`ride_pooler_id`)
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `deletepoolerride`
--

LOCK TABLES `deletepoolerride` WRITE;
/*!40000 ALTER TABLE `deletepoolerride` DISABLE KEYS */;
INSERT INTO `deletepoolerride` VALUES (10,1),(1,2),(9,5),(6,8),(8,12),(18,14),(22,17),(23,18),(26,19),(11,20),(32,23),(19,28),(27,29),(28,30),(29,31),(12,32),(16,33),(30,48),(31,49);
/*!40000 ALTER TABLE `deletepoolerride` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `owner`
--

DROP TABLE IF EXISTS `owner`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `owner` (
  `owner_id` int NOT NULL AUTO_INCREMENT,
  `owner_name` varchar(655) DEFAULT NULL,
  `owner_email` varchar(655) NOT NULL,
  `owner_mob` varchar(10) NOT NULL,
  `owner_username` varchar(655) NOT NULL,
  `owner_password` varchar(655) NOT NULL,
  PRIMARY KEY (`owner_id`),
  UNIQUE KEY `owner_email` (`owner_email`),
  UNIQUE KEY `owner_mob` (`owner_mob`),
  UNIQUE KEY `owner_username` (`owner_username`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `owner`
--

LOCK TABLES `owner` WRITE;
/*!40000 ALTER TABLE `owner` DISABLE KEYS */;
INSERT INTO `owner` VALUES (1,'Aftab','Af@gmail.com','53423245','aftabo','123'),(2,'Karan','Karan@gmail.com','945678912','karansingh','123'),(3,' ','jagan@gmail.com','7654323467','adf','132'),(4,'Shubham ','shubham@gmail.com','9931729367','shubham','123'),(5,'Jagan  Rao','jagan@pramati.com','Jagan  Rao','jaganrao','123'),(6,'Shivam ','shiva@gmail.com','9876754321','shivam','123'),(7,'Ranjan','ran@gmail.com','5432178906','ranjan','123'),(8,'Piyush','p@gmail.com','876543219','piyush','123'),(9,'Praneeth Raj','Pra@gmail.com','9871234512','praneet','123'),(11,'Jagan Rao','jaganchowhaan@gmail.com','7989570994','jaganc','123');
/*!40000 ALTER TABLE `owner` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `owner_cars`
--

DROP TABLE IF EXISTS `owner_cars`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `owner_cars` (
  `owner_cars_id` int NOT NULL AUTO_INCREMENT,
  `owner_id` int NOT NULL,
  `car_id` int NOT NULL,
  PRIMARY KEY (`owner_cars_id`),
  KEY `owner_id` (`owner_id`),
  KEY `car_id` (`car_id`),
  CONSTRAINT `owner_cars_ibfk_1` FOREIGN KEY (`owner_id`) REFERENCES `owner` (`owner_id`),
  CONSTRAINT `owner_cars_ibfk_2` FOREIGN KEY (`car_id`) REFERENCES `car` (`car_id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `owner_cars`
--

LOCK TABLES `owner_cars` WRITE;
/*!40000 ALTER TABLE `owner_cars` DISABLE KEYS */;
INSERT INTO `owner_cars` VALUES (1,1,1),(2,2,2),(3,2,3),(4,5,4),(5,5,5),(6,6,6),(7,6,7),(8,8,8),(9,9,9),(10,2,10),(11,11,11);
/*!40000 ALTER TABLE `owner_cars` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ownernotification`
--

DROP TABLE IF EXISTS `ownernotification`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ownernotification` (
  `notification_id` int NOT NULL AUTO_INCREMENT,
  `owner_id` int NOT NULL,
  `message` text,
  `is_read` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`notification_id`),
  KEY `owner_id` (`owner_id`),
  CONSTRAINT `ownernotification_ibfk_1` FOREIGN KEY (`owner_id`) REFERENCES `owner` (`owner_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ownernotification`
--

LOCK TABLES `ownernotification` WRITE;
/*!40000 ALTER TABLE `ownernotification` DISABLE KEYS */;
INSERT INTO `ownernotification` VALUES (1,2,'Hey Owner Jagan  has unbooked the ride ride no 33 from Shamshabad to Kukatpally on 2023-03-19',1);
/*!40000 ALTER TABLE `ownernotification` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pool_request`
--

DROP TABLE IF EXISTS `pool_request`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `pool_request` (
  `poolrequest_id` int NOT NULL AUTO_INCREMENT,
  `ride_pooler_id` int NOT NULL,
  `is_approved` tinyint(1) NOT NULL,
  `is_seen` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`poolrequest_id`),
  KEY `ride_pooler_id` (`ride_pooler_id`),
  CONSTRAINT `pool_request_ibfk_1` FOREIGN KEY (`ride_pooler_id`) REFERENCES `ride_pooler` (`ride_pooler_id`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pool_request`
--

LOCK TABLES `pool_request` WRITE;
/*!40000 ALTER TABLE `pool_request` DISABLE KEYS */;
INSERT INTO `pool_request` VALUES (1,38,1,1),(2,39,1,1),(3,40,1,1),(4,41,1,1),(5,42,1,1),(6,43,1,1),(7,44,1,1),(8,45,1,1),(9,46,1,1),(10,47,1,1),(11,48,0,1),(12,49,1,1),(13,50,1,1),(14,51,1,1),(15,52,1,1),(16,53,1,1),(17,54,0,1),(18,55,0,1),(19,56,0,1),(20,57,0,1),(21,58,0,1),(22,59,0,1),(23,60,0,1),(24,61,1,1),(25,62,1,1);
/*!40000 ALTER TABLE `pool_request` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pooler`
--

DROP TABLE IF EXISTS `pooler`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `pooler` (
  `pooler_id` int NOT NULL AUTO_INCREMENT,
  `pooler_name` varchar(655) NOT NULL,
  `pooler_email` varchar(655) NOT NULL,
  `pooler_mob` varchar(10) NOT NULL,
  `pooler_username` varchar(655) NOT NULL,
  `pooler_password` varchar(655) NOT NULL,
  PRIMARY KEY (`pooler_id`),
  UNIQUE KEY `pooler_email` (`pooler_email`),
  UNIQUE KEY `pooler_mob` (`pooler_mob`),
  UNIQUE KEY `pooler_username` (`pooler_username`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pooler`
--

LOCK TABLES `pooler` WRITE;
/*!40000 ALTER TABLE `pooler` DISABLE KEYS */;
INSERT INTO `pooler` VALUES (1,'Shivam Kumar','kumar.shivam.cse@gmail.com','8789595980','shivamsince0502','shivam123'),(2,'Keshav','Keshav@gmail.com','4354678765','keshavDeo','keshav123'),(3,'Sachin','sachin@gmail.com','9875645342','sachinkeral','sachin123'),(4,' asd','Shreya@gmail.com','8789595981','jagan',' '),(5,'Jagan ','jags@gmail.com','8789595954','jaganrao','123'),(6,'Aryan Goud','aryangoud9848@gmail.com','8187027475','aryangoud','123'),(7,'Praveen Raj','shivam.kumar@wavemaker.com','9876754212','praveen','123');
/*!40000 ALTER TABLE `pooler` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `poolernotification`
--

DROP TABLE IF EXISTS `poolernotification`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `poolernotification` (
  `notification_id` int NOT NULL AUTO_INCREMENT,
  `pooler_id` int NOT NULL,
  `message` text,
  `is_read` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`notification_id`),
  KEY `pooler_id` (`pooler_id`),
  CONSTRAINT `poolernotification_ibfk_1` FOREIGN KEY (`pooler_id`) REFERENCES `pooler` (`pooler_id`)
) ENGINE=InnoDB AUTO_INCREMENT=39 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `poolernotification`
--

LOCK TABLES `poolernotification` WRITE;
/*!40000 ALTER TABLE `poolernotification` DISABLE KEYS */;
INSERT INTO `poolernotification` VALUES (1,1,'Your Request for ride no 34 from Shamshabad to Mallapur has been approved by Karan having mobile no 945678912',1),(2,5,'Your Request for ride no 34 from Kukatpally to Habsiguda has been approved by Karan having mobile no 945678912',1),(3,2,'Hey Karan has declined the ride with ride no 3 with you from Shamshabad to Hi-Tech City on 2023-03-15',0),(4,1,'Hey Karan has declined the ride with ride no 3 with you from Shamshabad to Hi-Tech City on 2023-03-15',1),(5,1,'Hey Karan has declined the ride with ride no 20 with you from Shamshabad to Hi-Tech City on 2023-03-15',1),(6,1,'Hey Karan has declined the ride with ride no 20 with you from Shamshabad to Hi-Tech City on 2023-03-15',1),(7,1,'Hey Karan has declined the ride with ride no 21 with you from Shamshabad to Hi-Tech City on 2023-03-15',1),(8,1,'Hey Karan has declined the ride with ride no 21 with you from Shamshabad to Kukatpally on 2023-03-15',1),(9,1,'Hey Karan has declined the ride with ride no 22 with you from Shamshabad to Kukatpally on 2023-03-15',1),(10,1,'Hey Karan has declined the ride with ride no 22 with you from Shamshabad to Kukatpally on 2023-03-15',1),(11,2,'Hey Karan has declined the ride with ride no 2 with you from Shamshabad to Kukatpally on 2023-03-12',0),(12,3,'Hey Karan has declined the ride with ride no 2 with you from Kukatpally to Mallapur on 2023-03-12',0),(13,1,'Hey Karan has declined the ride with ride no 28 with you from Shamshabad to Hi-Tech City on 2023-03-17',1),(14,1,'Hey Karan has declined the ride with ride no 28 with you from Shamshabad to Hi-Tech City on 2023-03-17',1),(15,1,'Hey Karan has declined the ride with ride no 28 with you from Shamshabad to Hi-Tech City on 2023-03-17',1),(16,1,'Hey Karan has declined the ride with ride no 28 with you from Shamshabad to Hi-Tech City on 2023-03-17',1),(17,1,'Hey Karan has declined the ride with ride no 28 with you from Shamshabad to Kukatpally on 2023-03-17',1),(18,1,'Hey Karan has declined the ride with ride no 30 with you from Shamshabad to Kukatpally on 2023-03-18',1),(19,5,'Hey Karan has declined the ride with ride no 32 with you from Shamshabad to Hi-Tech City on 2023-03-19',1),(20,1,'Hey Karan has declined the ride with ride no 32 with you from Shamshabad to Kukatpally on 2023-03-19',1),(21,1,'Hey Karan has declined the ride with ride no 33 with you from Shamshabad to Kukatpally on 2023-03-19',1),(22,1,'Hey Karan has declined the ride with ride no 34 with you from Shamshabad to Mallapur on 2023-03-19',1),(23,5,'Your Request for ride no 23 from Shamshabad to Hi-Tech City has been approved by Karan having mobile no 945678912',1),(24,1,'Your Request for ride no 4 from Shamshabad to Hi-Tech City has been approved by Karan having mobile no 945678912',1),(25,1,'Your Request for ride no 24 from Shamshabad to Hi-Tech City has been approved by Karan having mobile no 945678912',1),(26,6,'Your Request for ride no 35 from Banjara Hills to Manikonda has been rejected by Jagan Rao having mobile no 7989570994',1),(27,6,'Your Request for ride no 35 from Banjara Hills to Uppal Kalan has been rejected by Jagan Rao having mobile no 7989570994',1),(28,6,'Your Request for ride no 35 from Banjara Hills to Uppal Kalan has been rejected by Jagan Rao having mobile no 7989570994',1),(29,6,'Your Request for ride no 35 from Banjara Hills to Manikonda has been rejected by Jagan Rao having mobile no 7989570994',1),(30,6,'Your Request for ride no 35 from Banjara Hills to Manikonda has been rejected by Jagan Rao having mobile no 7989570994',1),(31,6,'Hey Jagan Rao has declined the ride with ride no 35 with you from Banjara Hills to Manikonda on 2023-03-20',1),(32,6,'Hey Jagan Rao has declined the ride with ride no 35 with you from Banjara Hills to Uppal Kalan on 2023-03-20',1),(33,1,'Your Request for ride no 35 from Banjara Hills to Manikonda has been rejected by Jagan Rao having mobile no 7989570994',1),(34,6,'Your Request for ride no 35 from Banjara Hills to Uppal Kalan has been rejected by Jagan Rao having mobile no 7989570994',1),(35,6,'Your Request for ride no 35 from Banjara Hills to Uppal Kalan has been approved by Jagan Rao having mobile no 7989570994',1),(36,6,'Hey Jagan Rao has declined the ride with ride no 35 with you from Banjara Hills to Manikonda on 2023-03-20',1),(37,6,'Hey Jagan Rao has declined the ride with ride no 35 with you from Banjara Hills to Uppal Kalan on 2023-03-20',1),(38,1,'Your Request for ride no 35 from Banjara Hills to Uppal Kalan has been approved by Jagan Rao having mobile no 7989570994',0);
/*!40000 ALTER TABLE `poolernotification` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ride`
--

DROP TABLE IF EXISTS `ride`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ride` (
  `ride_id` int NOT NULL AUTO_INCREMENT,
  `owner_id` int NOT NULL,
  `car_id` int NOT NULL,
  `no_of_seats` int DEFAULT NULL,
  `is_active` tinyint(1) DEFAULT NULL,
  `ride_datetime` datetime DEFAULT NULL,
  PRIMARY KEY (`ride_id`),
  KEY `owner_id` (`owner_id`),
  KEY `car_id` (`car_id`),
  CONSTRAINT `ride_ibfk_1` FOREIGN KEY (`owner_id`) REFERENCES `owner` (`owner_id`),
  CONSTRAINT `ride_ibfk_2` FOREIGN KEY (`car_id`) REFERENCES `car` (`car_id`)
) ENGINE=InnoDB AUTO_INCREMENT=37 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ride`
--

LOCK TABLES `ride` WRITE;
/*!40000 ALTER TABLE `ride` DISABLE KEYS */;
INSERT INTO `ride` VALUES (1,1,1,0,1,'2023-03-12 00:00:00'),(2,2,2,0,1,'2023-03-12 00:00:00'),(3,2,2,5,0,'2023-03-15 00:00:00'),(4,2,3,2,1,'2023-03-20 00:00:00'),(5,2,3,2,1,'2023-03-14 00:00:00'),(6,2,2,2,0,'2023-03-14 00:00:00'),(7,2,2,3,1,'2023-03-14 00:00:00'),(8,2,2,3,0,'2023-03-22 00:00:00'),(9,2,3,2,0,'2023-03-16 00:00:00'),(10,2,3,5,0,'2023-03-17 00:00:00'),(11,2,3,4,1,'2023-03-16 00:00:00'),(12,5,4,3,1,'2023-03-13 00:00:00'),(13,5,4,2,1,'2023-03-14 00:00:00'),(14,5,5,3,1,'2023-03-14 00:00:00'),(15,6,7,3,1,'2023-03-14 00:00:00'),(16,6,6,2,1,'2023-03-14 00:00:00'),(17,6,6,2,1,'2023-03-14 00:00:00'),(18,8,8,1,1,'2023-03-15 00:00:00'),(19,9,3,3,0,'2023-03-15 00:00:00'),(20,2,3,1,1,'2023-03-15 00:00:00'),(21,2,3,1,1,'2023-03-15 00:00:00'),(22,2,3,1,1,'2023-03-15 00:00:00'),(23,2,2,1,1,'2023-03-20 00:00:00'),(24,2,3,1,1,'2023-03-23 00:00:00'),(25,2,3,4,0,'2023-03-19 00:00:00'),(26,2,2,3,1,'2023-03-16 00:00:00'),(27,2,2,3,1,'2023-03-16 00:00:00'),(28,2,2,3,1,'2023-03-17 00:00:00'),(29,2,10,2,1,'2023-03-18 00:00:00'),(30,2,2,1,1,'2023-03-18 00:00:00'),(31,2,3,4,0,'2023-03-19 00:00:00'),(32,2,2,4,1,'2023-03-19 00:00:00'),(33,2,2,5,1,'2023-03-19 00:00:00'),(34,2,2,3,1,'2023-03-19 00:00:00'),(35,11,11,1,1,'2023-03-20 00:00:00'),(36,11,11,3,1,'2023-03-21 20:39:00');
/*!40000 ALTER TABLE `ride` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ride_cities`
--

DROP TABLE IF EXISTS `ride_cities`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ride_cities` (
  `ride_cities_id` int NOT NULL AUTO_INCREMENT,
  `ride_id` int NOT NULL,
  `city_id` int NOT NULL,
  PRIMARY KEY (`ride_cities_id`),
  KEY `ride_id` (`ride_id`),
  KEY `city_id` (`city_id`),
  CONSTRAINT `ride_cities_ibfk_1` FOREIGN KEY (`ride_id`) REFERENCES `ride` (`ride_id`),
  CONSTRAINT `ride_cities_ibfk_2` FOREIGN KEY (`city_id`) REFERENCES `city` (`city_id`)
) ENGINE=InnoDB AUTO_INCREMENT=177 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ride_cities`
--

LOCK TABLES `ride_cities` WRITE;
/*!40000 ALTER TABLE `ride_cities` DISABLE KEYS */;
INSERT INTO `ride_cities` VALUES (1,1,1),(2,1,2),(3,1,3),(4,1,4),(5,1,5),(6,1,6),(8,2,1),(9,2,2),(10,2,4),(11,2,3),(12,2,5),(13,3,2),(14,3,3),(15,3,5),(16,3,7),(17,3,8),(18,3,9),(19,4,2),(20,4,3),(21,4,4),(22,4,5),(23,4,6),(24,5,9),(25,5,10),(26,5,12),(27,5,14),(28,6,5),(29,6,12),(30,6,15),(31,6,17),(32,6,20),(33,7,8),(34,7,10),(35,7,25),(36,7,14),(37,8,3),(38,8,10),(39,8,16),(40,8,18),(41,9,7),(42,9,25),(43,9,20),(44,9,22),(45,10,2),(46,10,4),(47,10,6),(48,10,19),(49,11,15),(50,11,22),(51,12,3),(52,12,6),(53,12,8),(54,12,9),(55,12,8),(56,13,3),(57,13,6),(58,13,9),(59,13,12),(60,14,4),(61,14,5),(62,14,8),(63,14,10),(64,15,5),(65,15,6),(66,15,14),(67,15,15),(68,15,17),(69,16,2),(70,17,2),(71,16,4),(72,17,4),(73,17,6),(74,16,6),(75,17,10),(76,16,10),(77,18,5),(78,18,7),(79,18,8),(80,18,9),(81,19,2),(82,19,3),(83,19,6),(84,19,8),(85,20,2),(86,20,3),(87,20,4),(88,20,5),(89,20,6),(90,20,7),(91,22,2),(92,21,2),(93,22,3),(94,22,4),(95,21,3),(96,21,4),(97,22,5),(98,21,5),(99,22,6),(100,21,6),(101,23,2),(102,23,3),(103,23,5),(104,23,6),(105,23,7),(106,24,2),(107,24,3),(108,24,5),(109,24,9),(110,24,11),(111,24,25),(112,25,6),(113,25,9),(114,25,10),(115,25,12),(116,25,14),(117,25,15),(118,25,17),(119,26,3),(120,27,3),(121,27,4),(122,26,4),(123,26,5),(124,27,5),(125,26,6),(126,27,6),(127,28,2),(128,28,3),(129,28,4),(130,28,5),(131,29,2),(132,29,3),(133,29,8),(134,29,9),(135,29,10),(136,29,25),(137,30,2),(138,30,3),(139,30,4),(140,30,5),(141,30,6),(142,30,7),(143,31,2),(144,31,3),(145,32,2),(146,31,4),(147,32,3),(148,32,4),(149,31,5),(150,32,5),(151,31,7),(152,32,7),(153,31,9),(154,32,9),(155,34,2),(156,33,2),(157,33,3),(158,34,3),(159,34,4),(160,33,4),(161,34,5),(162,33,5),(163,34,6),(164,33,6),(165,34,7),(166,33,7),(167,35,1),(168,35,8),(169,35,9),(170,35,10),(171,35,11),(172,35,12),(173,36,1),(174,36,2),(175,36,3),(176,36,4);
/*!40000 ALTER TABLE `ride_cities` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ride_pooler`
--

DROP TABLE IF EXISTS `ride_pooler`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ride_pooler` (
  `ride_pooler_id` int NOT NULL AUTO_INCREMENT,
  `ride_id` int NOT NULL,
  `pooler_id` int NOT NULL,
  `start_id` int NOT NULL,
  `end_id` int NOT NULL,
  `seat_no` int DEFAULT NULL,
  `is_active` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`ride_pooler_id`),
  KEY `ride_id` (`ride_id`),
  KEY `pooler_id` (`pooler_id`),
  KEY `start_id` (`start_id`),
  KEY `end_id` (`end_id`),
  CONSTRAINT `ride_pooler_ibfk_1` FOREIGN KEY (`ride_id`) REFERENCES `ride` (`ride_id`),
  CONSTRAINT `ride_pooler_ibfk_2` FOREIGN KEY (`pooler_id`) REFERENCES `pooler` (`pooler_id`),
  CONSTRAINT `ride_pooler_ibfk_3` FOREIGN KEY (`start_id`) REFERENCES `city` (`city_id`),
  CONSTRAINT `ride_pooler_ibfk_4` FOREIGN KEY (`end_id`) REFERENCES `city` (`city_id`)
) ENGINE=InnoDB AUTO_INCREMENT=63 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ride_pooler`
--

LOCK TABLES `ride_pooler` WRITE;
/*!40000 ALTER TABLE `ride_pooler` DISABLE KEYS */;
INSERT INTO `ride_pooler` VALUES (1,1,1,2,4,2,1),(2,1,2,3,5,3,1),(3,2,3,4,3,4,0),(5,2,1,2,4,1,0),(6,1,2,2,5,1,1),(7,1,2,2,4,0,1),(8,2,2,2,4,0,0),(11,3,2,2,5,1,0),(12,3,1,2,5,4,0),(14,18,1,5,8,1,1),(17,20,1,2,5,1,0),(18,21,1,2,5,2,0),(19,21,1,2,4,1,0),(20,22,1,2,4,2,0),(23,22,1,2,4,1,0),(24,20,1,2,5,1,0),(25,25,1,14,15,2,0),(27,23,1,2,7,2,0),(28,28,1,2,5,2,0),(29,28,1,2,5,1,0),(30,28,1,2,5,1,0),(31,28,1,2,5,5,0),(32,28,1,2,4,4,0),(33,4,1,2,6,3,0),(34,4,1,2,5,-1,0),(35,30,1,2,5,-1,0),(36,4,2,3,5,-1,0),(37,23,1,2,5,-1,0),(38,4,1,2,5,4,0),(39,24,5,2,5,4,0),(40,32,5,2,5,4,0),(41,24,1,2,5,3,1),(42,23,1,2,5,4,1),(43,30,1,2,4,3,0),(44,32,1,2,4,3,0),(45,33,1,2,4,3,0),(46,33,5,2,4,2,0),(47,34,5,2,4,3,0),(48,34,1,2,4,5,0),(49,34,1,2,3,6,0),(50,34,5,4,6,5,1),(51,23,5,2,5,3,1),(52,4,1,2,5,4,1),(53,24,1,2,5,3,1),(54,35,6,8,9,4,0),(55,35,6,8,10,4,0),(56,35,6,8,10,4,0),(57,35,6,8,9,4,0),(58,35,6,8,9,4,0),(59,35,1,8,9,4,0),(60,35,6,8,10,-1,0),(61,35,6,8,10,4,1),(62,35,1,8,10,3,1);
/*!40000 ALTER TABLE `ride_pooler` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping routines for database 'carpool'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-03-21 15:40:03
