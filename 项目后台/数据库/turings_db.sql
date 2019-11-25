/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50701
Source Host           : localhost:3306
Source Database       : turings_db

Target Server Type    : MYSQL
Target Server Version : 50701
File Encoding         : 65001

Date: 2019-11-25 15:31:37
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `tbl_courses`
-- ----------------------------
DROP TABLE IF EXISTS `tbl_courses`;
CREATE TABLE `tbl_courses` (
  `cInfo` varchar(255) DEFAULT NULL,
  `cId` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`cId`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tbl_courses
-- ----------------------------

-- ----------------------------
-- Table structure for `tbl_courses_my`
-- ----------------------------
DROP TABLE IF EXISTS `tbl_courses_my`;
CREATE TABLE `tbl_courses_my` (
  `cId` int(11) NOT NULL DEFAULT '0',
  `uId` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`cId`,`uId`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tbl_courses_my
-- ----------------------------

-- ----------------------------
-- Table structure for `tbl_fans`
-- ----------------------------
DROP TABLE IF EXISTS `tbl_fans`;
CREATE TABLE `tbl_fans` (
  `fId` int(11) NOT NULL DEFAULT '0',
  `uId` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`uId`,`fId`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tbl_fans
-- ----------------------------

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
-- Table structure for `tbl_myself_user`
-- ----------------------------
DROP TABLE IF EXISTS `tbl_myself_user`;
CREATE TABLE `tbl_myself_user` (
  `uPwd` varchar(20) DEFAULT NULL,
  `uTel` char(11) DEFAULT NULL,
  `uAvatar` varchar(100) DEFAULT NULL,
  `uMotto` varchar(60) DEFAULT NULL,
  `uId` int(11) NOT NULL DEFAULT '0',
  `uScore` int(11) DEFAULT NULL,
  `uTime` int(11) DEFAULT NULL,
  `uName` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`uId`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tbl_myself_user
-- ----------------------------

-- ----------------------------
-- Table structure for `tbl_schools`
-- ----------------------------
DROP TABLE IF EXISTS `tbl_schools`;
CREATE TABLE `tbl_schools` (
  `sWebaddress` varchar(100) DEFAULT NULL,
  `sInfo` varchar(255) DEFAULT NULL,
  `sId` int(11) NOT NULL,
  PRIMARY KEY (`sId`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tbl_schools
-- ----------------------------

-- ----------------------------
-- Table structure for `tbl_schools_favorite`
-- ----------------------------
DROP TABLE IF EXISTS `tbl_schools_favorite`;
CREATE TABLE `tbl_schools_favorite` (
  `sId` int(11) NOT NULL,
  `uId` int(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`sId`,`uId`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tbl_schools_favorite
-- ----------------------------
