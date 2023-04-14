-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: localhost
-- Generation Time: Apr 14, 2023 at 12:50 PM
-- Server version: 10.3.38-MariaDB-0ubuntu0.20.04.1
-- PHP Version: 7.4.3-4ubuntu2.18

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `charter`
--

DELIMITER $$
--
-- Procedures
--
CREATE DEFINER=`charter`@`localhost` PROCEDURE `add_transaction` (IN `customer_firstname` VARCHAR(50), IN `customer_surname` VARCHAR(50), IN `customer_email` VARCHAR(200), IN `transaction_points_earned` INT(11))  SQL SECURITY INVOKER BEGIN

INSERT INTO customers(firstname, surname, email) 
SELECT customer_firstname, customer_surname, customer_email FROM DUAL
WHERE NOT EXISTS(SELECT 1 FROM customers WHERE firstname=customer_firstname AND surname=customer_surname AND email=customer_email LIMIT 1);


# Obtain the id_customer
SELECT @id_customer:=id_customer FROM customers WHERE firstname=customer_firstname AND surname=customer_surname AND email=customer_email LIMIT 1;

# Insert
INSERT INTO transactions(id_customer, date, points_earned) VALUES (@id_customer, NOW(), transaction_points_earned);
END$$

DELIMITER ;

-- --------------------------------------------------------

--
-- Table structure for table `customers`
--

CREATE TABLE `customers` (
  `id_customer` int(11) NOT NULL,
  `firstname` varchar(50) NOT NULL,
  `surname` varchar(50) NOT NULL,
  `email` varchar(200) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `customers`
--

INSERT INTO `customers` (`id_customer`, `firstname`, `surname`, `email`) VALUES
(4, 'Bartosz', 'Zaleski', 'bartosz.zaleski@gmail.com'),
(5, 'Bartosz', 'Zaleski', 'bartosz.zaleski@avenga.com'),
(6, 'Bartosz', 'Zaleski', 'bartosz.zaleski@igglobal.com');

-- --------------------------------------------------------

--
-- Table structure for table `transactions`
--

CREATE TABLE `transactions` (
  `id_transaction` int(11) NOT NULL,
  `id_customer` int(11) NOT NULL,
  `date` date NOT NULL,
  `points_earned` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `transactions`
--

INSERT INTO `transactions` (`id_transaction`, `id_customer`, `date`, `points_earned`) VALUES
(1, 4, '2023-04-08', 1),
(2, 4, '2023-04-08', 2),
(3, 5, '2023-04-08', 3),
(4, 6, '2023-04-08', 4),
(5, 4, '2023-04-08', 10),
(6, 4, '2023-04-08', 90),
(7, 4, '2023-04-12', 90),
(8, 4, '2023-04-12', 90),
(9, 4, '2023-04-12', 90),
(10, 4, '2023-04-12', 90),
(11, 4, '2023-04-12', 90),
(12, 4, '2023-04-12', 90),
(13, 4, '2023-04-12', 20),
(14, 4, '2023-04-12', 90),
(15, 4, '2023-04-12', 90),
(16, 4, '2023-04-12', 90),
(17, 4, '2023-04-12', 90),
(18, 4, '2023-04-12', 90),
(19, 4, '2023-04-12', 90),
(20, 4, '2023-04-12', 250),
(21, 4, '2023-01-01', 2000);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `customers`
--
ALTER TABLE `customers`
  ADD PRIMARY KEY (`id_customer`);

--
-- Indexes for table `transactions`
--
ALTER TABLE `transactions`
  ADD PRIMARY KEY (`id_transaction`),
  ADD KEY `transaction_to_customers` (`id_customer`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `customers`
--
ALTER TABLE `customers`
  MODIFY `id_customer` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT for table `transactions`
--
ALTER TABLE `transactions`
  MODIFY `id_transaction` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=22;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `transactions`
--
ALTER TABLE `transactions`
  ADD CONSTRAINT `transaction_to_customers` FOREIGN KEY (`id_customer`) REFERENCES `customers` (`id_customer`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
