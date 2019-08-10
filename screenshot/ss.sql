-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               8.0.16 - MySQL Community Server - GPL
-- Server OS:                    Win64
-- HeidiSQL Version:             10.1.0.5464
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


-- Dumping database structure for screenshot
CREATE DATABASE IF NOT EXISTS `screenshot` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_bin */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `screenshot`;

-- Dumping structure for table screenshot.screen
CREATE TABLE IF NOT EXISTS `screen` (
  `id` varchar(50) COLLATE utf8mb4_bin NOT NULL,
  `customer_id` varchar(50) COLLATE utf8mb4_bin NOT NULL,
  `request_url` varchar(512) COLLATE utf8mb4_bin NOT NULL,
  `request_url_domain` varchar(100) COLLATE utf8mb4_bin NOT NULL,
  `created` date NOT NULL,
  `status` enum('PENDING','FAILED','COMPLETED') COLLATE utf8mb4_bin NOT NULL,
  `screenshot` varbinary(60000) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- Data exporting was unselected.
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
