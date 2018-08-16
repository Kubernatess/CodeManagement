/*
Navicat MySQL Data Transfer

Source Server         : 172.16.188.86
Source Server Version : 50720
Source Host           : localhost:3306
Source Database       : lumlum

Target Server Type    : MYSQL
Target Server Version : 50720
File Encoding         : 65001

Date: 2018-07-01 22:47:48
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for userlist
-- ----------------------------
DROP TABLE IF EXISTS `userlist`;
CREATE TABLE `userlist` (
  `username` varchar(10) NOT NULL,
  `password` varchar(10) NOT NULL,
  PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of userlist
-- ----------------------------
INSERT INTO `userlist` VALUES ('CentOS', 'sise');
INSERT INTO `userlist` VALUES ('Docker', '123456');
INSERT INTO `userlist` VALUES ('hadoop', '502717');
INSERT INTO `userlist` VALUES ('OpenStack', '502717');
