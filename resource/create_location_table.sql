-- phpMyAdmin SQL Dump
-- version 2.9.0.3
-- http://www.phpmyadmin.net
-- 
-- Host: localhost
-- Generation Time: Aug 16, 2007 at 01:55 PM
-- Server version: 5.0.27
-- PHP Version: 5.2.0
-- 
-- Database: `location`
-- 

-- --------------------------------------------------------

-- 
-- Table structure for table `location`
-- 

CREATE TABLE `location` (
  `longitude` double NOT NULL default '0',
  `latitude` double NOT NULL default '0',
  `altitude` double NOT NULL default '0',
  `id` int(11) NOT NULL auto_increment,
  `tag` varchar(255) default NULL,
  `description` varchar(1000) default NULL,
  `user` varchar(255) NOT NULL,
  `time` timestamp NOT NULL default '0000-00-00 00:00:00' on update CURRENT_TIMESTAMP,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=2 ;

-- 
-- Dumping data for table `location`
-- 

INSERT INTO `location` (`longitude`, `latitude`, `altitude`, `id`, `tag`, `description`, `user`, `time`) VALUES 
(0, 0, 0, 1, '0', NULL, 'frank', '0000-00-00 00:00:00');
