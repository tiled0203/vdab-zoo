CREATE SCHEMA  IF NOT EXISTS `companyDb` ;
USE `companyDb`;

DROP TABLE IF EXISTS `employees`;
CREATE TABLE `employees` (
                             `id` int NOT NULL AUTO_INCREMENT,
                             `firstname` varchar(45) DEFAULT NULL,
                             `lastname` varchar(45) DEFAULT NULL,
                             `profession` varchar(45) DEFAULT NULL,
                             PRIMARY KEY (`id`)
)  ;

--
-- Dumping data for table `employees`
--

INSERT INTO `employees` VALUES (1,'Timothy',NULL,NULL),(2,'Elias',NULL,'DEVELOPER'),(3,'Maarten','Thijs',null);

-- Dump completed on 2020-09-09 13:55:07
