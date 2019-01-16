/*
Navicat MySQL Data Transfer

Source Server         : 本地
Source Server Version : 50528
Source Host           : localhost:3306
Source Database       : apple

Target Server Type    : MYSQL
Target Server Version : 50528
File Encoding         : 65001

Date: 2019-01-10 14:37:07
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for diseases
-- ----------------------------
DROP TABLE IF EXISTS `diseases`;
CREATE TABLE `diseases` (
  `disease_id` int(255) unsigned zerofill NOT NULL AUTO_INCREMENT,
  `disease_name` varchar(255) NOT NULL,
  PRIMARY KEY (`disease_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for disease_information
-- ----------------------------
DROP TABLE IF EXISTS `disease_information`;
CREATE TABLE `disease_information` (
  `disease_id` int(255) NOT NULL,
  `part_id` int(80) NOT NULL,
  `harm_symptoms` varchar(255) NOT NULL,
  `causes` varchar(255) NOT NULL,
  PRIMARY KEY (`disease_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for part
-- ----------------------------
DROP TABLE IF EXISTS `part`;
CREATE TABLE `part` (
  `part_id` int(255) NOT NULL,
  `part_name` varchar(255) NOT NULL,
  PRIMARY KEY (`part_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for pesticides
-- ----------------------------
DROP TABLE IF EXISTS `pesticides`;
CREATE TABLE `pesticides` (
  `pesticide_id` int(222) NOT NULL,
  `disease_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`pesticide_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for pesticide_information
-- ----------------------------
DROP TABLE IF EXISTS `pesticide_information`;
CREATE TABLE `pesticide_information` (
  `pesticide_id` int(255) NOT NULL,
  `category` varchar(255) DEFAULT NULL COMMENT '类别',
  `scientific_name` varchar(255) DEFAULT NULL COMMENT '学名',
  `popular_name` varchar(255) DEFAULT NULL COMMENT '俗名',
  `dosage_form` varchar(255) DEFAULT NULL COMMENT '剂型',
  `feature` varchar(255) DEFAULT NULL COMMENT '特点',
  `useage` varchar(255) DEFAULT NULL COMMENT '用法',
  PRIMARY KEY (`pesticide_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(13) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(33) DEFAULT NULL COMMENT '姓名',
  `password` varchar(255) DEFAULT NULL,
  `age` int(3) DEFAULT NULL COMMENT '年龄',
  `money` double DEFAULT NULL COMMENT '账户余额',
  `create_date` datetime DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=utf8;
