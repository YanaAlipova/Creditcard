-- phpMyAdmin SQL Dump
-- version 4.0.10deb1
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Sep 29, 2016 at 04:36 PM
-- Server version: 5.5.49-0ubuntu0.14.04.1
-- PHP Version: 5.5.9-1ubuntu4.17

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `payment_card`
--

-- --------------------------------------------------------

--
-- Table structure for table `account`
--

CREATE TABLE IF NOT EXISTS `account` (
  `account_number` int(10) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `ballance` int(10) unsigned NOT NULL,
  PRIMARY KEY (`account_number`),
  KEY `account_fk0` (`user_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=422 ;

--
-- Dumping data for table `account`
--

INSERT INTO `account` (`account_number`, `user_id`, `ballance`) VALUES
(123, 2, 991422),
(244, 2, 178765),
(321, 2, 7591),
(421, 3, 2323400);

-- --------------------------------------------------------

--
-- Table structure for table `cards`
--

CREATE TABLE IF NOT EXISTS `cards` (
  `card_number` int(12) NOT NULL AUTO_INCREMENT,
  `account_number` int(10) NOT NULL,
  `status` tinyint(1) NOT NULL,
  PRIMARY KEY (`card_number`),
  KEY `cards_fk0` (`account_number`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=888867 ;

--
-- Dumping data for table `cards`
--

INSERT INTO `cards` (`card_number`, `account_number`, `status`) VALUES
(123990, 123, 0),
(125489, 244, 1),
(265467, 244, 1),
(441663, 244, 1),
(482211, 421, 1),
(833866, 321, 1),
(844811, 421, 1),
(888866, 321, 1);

-- --------------------------------------------------------

--
-- Table structure for table `payments`
--

CREATE TABLE IF NOT EXISTS `payments` (
  `payment_id` int(11) NOT NULL AUTO_INCREMENT,
  `payment_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `card_number` int(12) NOT NULL,
  `amount` float NOT NULL,
  PRIMARY KEY (`payment_id`),
  KEY `payments_fk0` (`card_number`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=62 ;

--
-- Dumping data for table `payments`
--

INSERT INTO `payments` (`payment_id`, `payment_date`, `card_number`, `amount`) VALUES
(33, '2016-09-18 08:52:43', 482211, 2400),
(34, '2016-09-18 08:52:46', 482211, 12),
(35, '2016-09-18 08:53:57', 482211, 1),
(36, '2016-09-18 09:06:21', 482211, 3),
(37, '2016-09-18 09:06:34', 482211, 2),
(38, '2016-09-18 09:10:00', 482211, 8),
(39, '2016-09-18 09:10:09', 482211, 0.4),
(40, '2016-09-18 09:14:42', 833866, 1035.3),
(41, '2016-09-18 09:28:45', 125489, 17),
(42, '2016-09-18 09:29:00', 125489, 13.99),
(43, '2016-09-18 10:34:32', 833866, 15),
(44, '2016-09-18 10:36:13', 833866, 100),
(45, '2016-09-18 10:38:15', 482211, 1100),
(46, '2016-09-18 11:46:58', 123990, 3400),
(47, '2016-09-18 11:47:02', 123990, 33300),
(48, '2016-09-18 14:06:25', 123990, 343400),
(49, '2016-09-18 23:04:32', 123990, 34300),
(50, '2016-09-18 23:04:38', 123990, 34300),
(51, '2016-09-29 13:03:26', 125489, 3440),
(52, '2016-09-29 13:03:36', 265467, 32334),
(53, '2016-09-29 13:03:47', 833866, 1),
(54, '2016-09-29 13:03:59', 833866, 9),
(55, '2016-09-29 13:04:28', 833866, 400),
(56, '2016-09-29 13:04:39', 833866, 400),
(57, '2016-09-29 13:04:55', 833866, 400),
(58, '2016-09-29 13:05:12', 833866, 100),
(59, '2016-09-29 13:08:33', 125489, 4400),
(60, '2016-09-29 13:09:26', 125489, 300),
(61, '2016-09-29 13:10:55', 125489, 220);

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE IF NOT EXISTS `users` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `password` char(32) NOT NULL,
  `role` tinyint(1) DEFAULT NULL,
  `name` varchar(60) DEFAULT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=5 ;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`user_id`, `password`, `role`, `name`) VALUES
(1, '5c493066e6f3b824c44227718d23a09e', 1, 'Валентин'),
(2, '5c493066e6f3b824c44227718d23a09e', 0, 'Виталий'),
(3, '5c493066e6f3b824c44227718d23a09e', 0, 'Антон'),
(4, '5c493066e6f3b824c44227718d23a09e', 1, 'Евгений');

--
-- Constraints for dumped tables
--

--
-- Constraints for table `account`
--
ALTER TABLE `account`
  ADD CONSTRAINT `account_fk0` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`);

--
-- Constraints for table `cards`
--
ALTER TABLE `cards`
  ADD CONSTRAINT `cards_fk0` FOREIGN KEY (`account_number`) REFERENCES `account` (`account_number`);

--
-- Constraints for table `payments`
--
ALTER TABLE `payments`
  ADD CONSTRAINT `payments_fk0` FOREIGN KEY (`card_number`) REFERENCES `cards` (`card_number`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
