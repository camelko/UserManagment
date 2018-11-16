-- phpMyAdmin SQL Dump
-- version 4.8.1
-- https://www.phpmyadmin.net/
--
-- Hostiteľ: 127.0.0.1
-- Čas generovania: Št 15.Nov 2018, 16:01
-- Verzia serveru: 10.1.33-MariaDB
-- Verzia PHP: 7.2.6

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Databáza: `user_management`
--
CREATE DATABASE IF NOT EXISTS `user_management` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;
USE `user_management`;

-- --------------------------------------------------------

--
-- Štruktúra tabuľky pre tabuľku `group`
--

CREATE TABLE `group` (
  `id` int(11) NOT NULL,
  `name` varchar(45) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Sťahujem dáta pre tabuľku `group`
--

INSERT INTO `group` (`id`, `name`) VALUES
(1, 'admin'),
(2, 'secretary'),
(3, 'toilet cleaner');

-- --------------------------------------------------------

--
-- Štruktúra tabuľky pre tabuľku `permission`
--

CREATE TABLE `permission` (
  `group_id` int(11) NOT NULL,
  `value` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Sťahujem dáta pre tabuľku `permission`
--

INSERT INTO `permission` (`group_id`, `value`) VALUES
(1, 'add users'),
(1, 'delete users'),
(2, 'add users'),
(2, 'pay sallary');

-- --------------------------------------------------------

--
-- Štruktúra tabuľky pre tabuľku `user`
--

CREATE TABLE `user` (
  `id` int(11) NOT NULL,
  `name` varchar(30) NOT NULL,
  `password` varchar(30) DEFAULT NULL,
  `email` varchar(50) NOT NULL,
  `last_login` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Sťahujem dáta pre tabuľku `user`
--

INSERT INTO `user` (`id`, `name`, `password`, `email`, `last_login`) VALUES
(1, 'Anka', 'mojeheslo', 'anka@estenovsieelct.sk', '2018-11-14 10:09:00'),
(2, 'Lenka', NULL, 'lenka@games.com', NULL),
(5, 'Jožko', NULL, 'jozko@jozko.sk', NULL),
(7, 'Fero Pekný', 'heslo', 'pekny@pekny.sk', NULL);

-- --------------------------------------------------------

--
-- Štruktúra tabuľky pre tabuľku `user_group`
--

CREATE TABLE `user_group` (
  `user_id` int(11) NOT NULL,
  `group_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Sťahujem dáta pre tabuľku `user_group`
--

INSERT INTO `user_group` (`user_id`, `group_id`) VALUES
(1, 1),
(1, 2),
(2, 2),
(5, 3);

--
-- Kľúče pre exportované tabuľky
--

--
-- Indexy pre tabuľku `group`
--
ALTER TABLE `group`
  ADD PRIMARY KEY (`id`);

--
-- Indexy pre tabuľku `permission`
--
ALTER TABLE `permission`
  ADD PRIMARY KEY (`group_id`,`value`),
  ADD KEY `fk_permission_group1_idx` (`group_id`);

--
-- Indexy pre tabuľku `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id`);

--
-- Indexy pre tabuľku `user_group`
--
ALTER TABLE `user_group`
  ADD PRIMARY KEY (`user_id`,`group_id`),
  ADD KEY `fk_user_has_group_group1_idx` (`group_id`),
  ADD KEY `fk_user_has_group_user_idx` (`user_id`);

--
-- AUTO_INCREMENT pre exportované tabuľky
--

--
-- AUTO_INCREMENT pre tabuľku `group`
--
ALTER TABLE `group`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT pre tabuľku `user`
--
ALTER TABLE `user`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- Obmedzenie pre exportované tabuľky
--

--
-- Obmedzenie pre tabuľku `permission`
--
ALTER TABLE `permission`
  ADD CONSTRAINT `fk_permission_group1` FOREIGN KEY (`group_id`) REFERENCES `group` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Obmedzenie pre tabuľku `user_group`
--
ALTER TABLE `user_group`
  ADD CONSTRAINT `fk_user_has_group_group1` FOREIGN KEY (`group_id`) REFERENCES `group` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_user_has_group_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
