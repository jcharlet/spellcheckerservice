CREATE DATABASE  IF NOT EXISTS `spell_checker` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `SPELL_CHECKER`;
-- MySQL dump 10.13  Distrib 5.6.13, for Win32 (x86)
--
-- Host: 127.0.0.1    Database: SPELL_CHECKER
-- ------------------------------------------------------
-- Server version	5.5.20-log

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
-- Table structure for table `Language`
--

DROP TABLE IF EXISTS `Language`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Language` (
  `id` smallint(6) NOT NULL AUTO_INCREMENT,
  `short_code` varchar(8) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `short_code_UNIQUE` (`short_code`)
) ENGINE=InnoDB AUTO_INCREMENT=238 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Language`
--

LOCK TABLES `Language` WRITE;
/*!40000 ALTER TABLE `Language` DISABLE KEYS */;
INSERT INTO `Language` VALUES (124,'EN'),(160,'FR');
/*!40000 ALTER TABLE `Language` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Word`
--

DROP TABLE IF EXISTS `Word`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Word` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  `language_id` smallint(6) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  UNIQUE KEY `id_languageId_UNIQUE` (`language_id`,`name`),
  KEY `FK_languageId_idx` (`language_id`),
  CONSTRAINT `FK_languageId` FOREIGN KEY (`language_id`) REFERENCES `Language` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=538 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Word`
--

LOCK TABLES `Word` WRITE;
/*!40000 ALTER TABLE `Word` DISABLE KEYS */;
INSERT INTO `Word` VALUES (295,'a',124),(460,'about',124),(289,'all',124),(512,'am',124),(403,'and',124),(498,'are',124),(395,'as',124),(519,'assertion',124),(427,'at',124),(435,'attire',124),(535,'baggy',124),(473,'ballet',124),(509,'band',124),(478,'barbarian',124),(412,'be',124),(304,'beauty',124),(468,'belts',124),(467,'big',124),(495,'black',124),(492,'boots',124),(537,'boy',124),(488,'brass',124),(510,'brigands',124),(453,'but',124),(401,'by',124),(388,'castles',124),(426,'clear',124),(459,'clumsy',124),(443,'coming',124),(296,'country',124),(474,'course',124),(534,'cow',124),(433,'crowds',124),(303,'dawdle',124),(290,'day',124),(479,'dirty',124),(499,'do',124),(506,'down',124),(472,'dresses',124),(409,'each',124),(424,'edge',124),(481,'enormous',124),(300,'every',124),(521,'except',124),(528,'figures',124),(415,'floods',124),(471,'fluttering',124),(485,'foot',124),(444,'france',124),(405,'from',124),(298,'full',124),(445,'germany',124),(524,'got',124),(414,'great',124),(431,'groups',124),(462,'had',124),(496,'hair',124),(514,'harmless',124),(450,'hats',124),(482,'heavy',124),(491,'high',124),(393,'hills',124),(440,'home',124),(511,'however',124),(442,'i',124),(397,'in',124),(494,'into',124),(416,'it',124),(448,'jackets',124),(437,'just',124),(301,'kind',124),(483,'leather',124),(438,'like',124),(536,'linen',124),(385,'little',124),(291,'long',124),(501,'look',124),(458,'looked',124),(418,'lot',124),(451,'made',124),(408,'margin',124),(399,'missals',124),(477,'more',124),(466,'most',124),(497,'moustaches',124),(489,'nails',124),(517,'natural',124),(525,'near',124),(484,'nearly',124),(500,'not',124),(299,'of',124),(398,'old',124),(389,'on',124),(507,'once',124),(387,'or',124),(508,'oriental',124),(465,'other',124),(454,'others',124),(423,'outside',124),(487,'over',124),(439,'peasants',124),(432,'people',124),(475,'petticoats',124),(456,'picturesque',124),(502,'prepossessing',124),(520,'pretty',124),(400,'ran',124),(515,'rather',124),(532,'rest',124),(425,'river',124),(402,'rivers',124),(449,'round',124),(420,'running',124),(384,'saw',124),(396,'see',124),(293,'seemed',124),(518,'self',124),(505,'set',124),(480,'shirts',124),(447,'short',124),(410,'side',124),(464,'sleeves',124),(529,'slovaks',124),(436,'some',124),(470,'something',124),(312,'sometimes',124),(434,'sorts',124),(503,'stage',124),(428,'station',124),(392,'steep',124),(407,'stony',124),(527,'strangest',124),(404,'streams',124),(469,'strips',124),(421,'strong',124),(486,'studded',124),(413,'subject',124),(394,'such',124),(422,'sweep',124),(417,'takes',124),(97,'test',124),(98,'test2',124),(531,'than',124),(390,'the',124),(533,'their',124),(411,'them',124),(429,'there',124),(526,'they',124),(441,'those',124),(294,'through',124),(302,'to',124),(513,'told',124),(391,'top',124),(386,'towns',124),(452,'trousers',124),(493,'tucked',124),(476,'under',124),(455,'very',124),(461,'waist',124),(516,'wanting',124),(383,'was',124),(419,'water',124),(292,'we',124),(430,'were',124),(522,'when',124),(297,'which',124),(463,'white',124),(530,'who',124),(406,'wide',124),(446,'with',124),(457,'women',124),(490,'wore',124),(504,'would',124),(523,'you',124),(134,'test',160);
/*!40000 ALTER TABLE `Word` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2014-08-27 21:18:27
