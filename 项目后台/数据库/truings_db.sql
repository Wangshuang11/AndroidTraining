/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50701
Source Host           : localhost:3306
Source Database       : truings_db

Target Server Type    : MYSQL
Target Server Version : 50701
File Encoding         : 65001

Date: 2019-11-26 09:01:34
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `tbl_mistaken`
-- ----------------------------
DROP TABLE IF EXISTS `tbl_mistaken`;
CREATE TABLE `tbl_mistaken` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `subject` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `tag` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `type` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `time` varchar(255) DEFAULT NULL,
  `titleImg` varchar(255) DEFAULT NULL,
  `optionA` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `optionB` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `optionC` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `optionD` varchar(255) DEFAULT NULL,
  `answer` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of tbl_mistaken
-- ----------------------------

-- ----------------------------
-- Table structure for `tbl_self_courses`
-- ----------------------------
DROP TABLE IF EXISTS `tbl_self_courses`;
CREATE TABLE `tbl_self_courses` (
  `cInfo` varchar(255) DEFAULT NULL,
  `cId` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`cId`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tbl_self_courses
-- ----------------------------

-- ----------------------------
-- Table structure for `tbl_self_courses_my`
-- ----------------------------
DROP TABLE IF EXISTS `tbl_self_courses_my`;
CREATE TABLE `tbl_self_courses_my` (
  `cId` int(11) NOT NULL DEFAULT '0',
  `uId` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`cId`,`uId`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tbl_self_courses_my
-- ----------------------------

-- ----------------------------
-- Table structure for `tbl_self_fans`
-- ----------------------------
DROP TABLE IF EXISTS `tbl_self_fans`;
CREATE TABLE `tbl_self_fans` (
  `fId` int(11) NOT NULL DEFAULT '0',
  `uId` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`uId`,`fId`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tbl_self_fans
-- ----------------------------

-- ----------------------------
-- Table structure for `tbl_self_schools`
-- ----------------------------
DROP TABLE IF EXISTS `tbl_self_schools`;
CREATE TABLE `tbl_self_schools` (
  `sWebaddress` varchar(100) DEFAULT NULL,
  `sInfo` varchar(255) DEFAULT NULL,
  `sId` int(11) NOT NULL,
  PRIMARY KEY (`sId`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tbl_self_schools
-- ----------------------------

-- ----------------------------
-- Table structure for `tbl_self_schools_favorite`
-- ----------------------------
DROP TABLE IF EXISTS `tbl_self_schools_favorite`;
CREATE TABLE `tbl_self_schools_favorite` (
  `sId` int(11) NOT NULL,
  `uId` int(11) NOT NULL,
  PRIMARY KEY (`sId`,`uId`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tbl_self_schools_favorite
-- ----------------------------

-- ----------------------------
-- Table structure for `tbl_self_user`
-- ----------------------------
DROP TABLE IF EXISTS `tbl_self_user`;
CREATE TABLE `tbl_self_user` (
  `uId` int(11) NOT NULL DEFAULT '0',
  `uTel` char(11) DEFAULT NULL,
  `uName` varchar(20) DEFAULT NULL,
  `uPwd` varchar(20) DEFAULT NULL,
  `uMotto` varchar(60) DEFAULT NULL,
  `uAvatar` varchar(100) DEFAULT NULL,
  `uTime` int(11) DEFAULT NULL,
  `uScore` int(11) DEFAULT NULL,
  PRIMARY KEY (`uId`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tbl_self_user
-- ----------------------------
INSERT INTO `tbl_self_user` VALUES ('0', '13010001000', '假的用户', 'mima00', '这是一条假的座右铭', null, '0', '0');
