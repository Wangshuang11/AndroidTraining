/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50701
Source Host           : localhost:3306
Source Database       : turings_db

Target Server Type    : MYSQL
Target Server Version : 50701
File Encoding         : 65001

Date: 2019-11-27 16:26:59
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `tbl_indexcollege`
-- ----------------------------
DROP TABLE IF EXISTS `tbl_indexcollege`;
CREATE TABLE `tbl_indexcollege` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL,
  `flag` varchar(20) DEFAULT NULL,
  `img` varchar(50) DEFAULT NULL,
  `url` varchar(1024) DEFAULT NULL,
  `src` varchar(1024) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tbl_indexcollege
-- ----------------------------
INSERT INTO `tbl_indexcollege` VALUES ('1', '清华大学', '985', 'qinghua', 'https://www.tsinghua.edu.cn/publish/thu2018/index.html', 'https://p.ssl.qhimg.com/dr/298_299_/t01409ee9dd99712d8c.jpg');
INSERT INTO `tbl_indexcollege` VALUES ('2', '北京大学', '985', 'beida', 'https://www.pku.edu.cn/yytx/features_2019/index.htm', 'https://p.ssl.qhimg.com/dr/300_300_/t0159534bcb5ee3f8f3.jpg');
INSERT INTO `tbl_indexcollege` VALUES ('3', '浙江大学', '985', 'zheda', 'https://hao.360.com/', 'https://p.ssl.qhimg.com/dr/500_500_/t0130c7ce5179120b26.jpg');
INSERT INTO `tbl_indexcollege` VALUES ('4', '厦门大学', '985', 'xiamen', 'https://www.xmu.edu.cn/', 'https://p.ssl.qhimg.com/dr/268_264_/t01016a641f037d2bea.jpg');
INSERT INTO `tbl_indexcollege` VALUES ('5', '中山大学', '985', 'zhongshan', 'https://hao.360.com/', 'https://p.ssl.qhimg.com/dr/334_322_/t0114a51d14a6baffe1.jpg');
INSERT INTO `tbl_indexcollege` VALUES ('6', '中南大学', '985', 'zhongnan', 'https://hao.360.com/', 'https://p.ssl.qhimg.com/dr/250_250_/t01eb9694cdcd3b13bd.jpg');
INSERT INTO `tbl_indexcollege` VALUES ('7', '四川大学', '211', 'sichuan', 'https://www.tsinghua.edu.cn/publish/thu2018/index.html', 'https://p.ssl.qhimg.com/dr/365_365_/t012f60c25bbcccf25c.png');
INSERT INTO `tbl_indexcollege` VALUES ('8', '同济大学', '211', 'tongji', 'https://www.tongji.edu.cn/', 'https://p.ssl.qhimg.com/dr/400_397_/t013b32ff27e3933e0e.jpg');
INSERT INTO `tbl_indexcollege` VALUES ('9', '南开大学', '211', 'nankai', 'https://www.tongji.edu.cn/', 'https://p.ssl.qhimg.com/dr/425_425_/t010f549c3eaf3c000e.jpg');
INSERT INTO `tbl_indexcollege` VALUES ('10', '山东大学', '211', 'shandong', 'https://www.xmu.edu.cn/', 'https://p.ssl.qhimg.com/dr/300_300_/t01117a9f2d8939912f.jpg');
INSERT INTO `tbl_indexcollege` VALUES ('11', '中国人民大学', '211', 'renda', 'https://hao.360.com/', 'https://p.ssl.qhimg.com/dr/220_220_/t013bc8a707429be96a.jpg');

-- ----------------------------
-- Table structure for `tbl_indexcontent`
-- ----------------------------
DROP TABLE IF EXISTS `tbl_indexcontent`;
CREATE TABLE `tbl_indexcontent` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `idt` int(11) DEFAULT NULL,
  `content` varchar(1024) DEFAULT NULL,
  `flag` int(11) DEFAULT NULL,
  `flagt` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tbl_indexcontent
-- ----------------------------
INSERT INTO `tbl_indexcontent` VALUES ('1', '11', '大学，学名为普通高等学校，是一种功能独特的文化机构，是与社会的经济和政治机构既相互关联又鼎足', '1', '1');
INSERT INTO `tbl_indexcontent` VALUES ('2', '12', '而立的传承、研究、融合和创新高深学术的高等学府。它不仅是人类文化发展到一定阶段的产物，它还在长期办学实践的基础上，', '1', '1');
INSERT INTO `tbl_indexcontent` VALUES ('3', '13', '经过历史的积淀、自身的努力和外部环境的影响，逐步形成了一种独特的大学文化 。', '1', '1');
INSERT INTO `tbl_indexcontent` VALUES ('4', '21', '大学从它产生到现在已有上千年的历史，上溯到它的产生，它主要是从德国、英国等国家最早发展起来的。中国现代大学源起', '1', '2');
INSERT INTO `tbl_indexcontent` VALUES ('5', '22', '于西方，现代西方大学又是从欧洲中世纪大学、英国大学、德国大学而到美国大学这样逐渐演化过来的，无论哪一个时代的大学都', '1', '2');
INSERT INTO `tbl_indexcontent` VALUES ('6', '23', '是以前大学的创造性继承而不是否定 。', '1', '2');
INSERT INTO `tbl_indexcontent` VALUES ('7', '11', '在大学理念支配下的大学使命要求大学培养的学生首先是有高尚品格的，有教养的人，这是符合大学的本质。这个使命是指培', '2', '1');
INSERT INTO `tbl_indexcontent` VALUES ('8', '12', '养学生完整的人格、净化学生的心灵、修养学生的品行、锻炼学生对事物进行批判的能力，而不是仅仅对学生进行专业教育。', '2', '1');
INSERT INTO `tbl_indexcontent` VALUES ('9', '21', '正如英国著名教育理论家纽曼所认识到的:\"从功利派的论点中看到了真正教育的死敌。新大学在功利派理论的指导下，更看重的', '2', '2');
INSERT INTO `tbl_indexcontent` VALUES ('10', '22', '是专业培训而不是文化要求，是考试及结果而不是心理过程，是对事实的被动获取而不是心智的一般活动。\"纽曼所指的\"新大学\"，', '2', '2');
INSERT INTO `tbl_indexcontent` VALUES ('11', '23', '是违背了大学的逻辑而按市场经济的逻辑和政治的逻辑来当作它的使命而运行的大学。', '2', '2');
INSERT INTO `tbl_indexcontent` VALUES ('12', '11', '211大学：第一点是面向21世纪，第二点是一百所大学，第三点是国家重点建设大', '3', '1');
INSERT INTO `tbl_indexcontent` VALUES ('13', '12', '学,这就是211大学的具体含义分析。', '3', '1');
INSERT INTO `tbl_indexcontent` VALUES ('14', '21', '985大学：名称主要来源于日期，1998年5月4日，也就是北大校庆100周年，江泽民主席', '3', '2');
INSERT INTO `tbl_indexcontent` VALUES ('15', '22', '提出建设世界一流大学，985大学共有39所。', '3', '2');
INSERT INTO `tbl_indexcontent` VALUES ('16', '11', '世界一流大学和一流学科，简称“双一流”；建设世界一流大学和一流学科，是中国共产党中央、国务院作出的重大战略决策，', '4', '1');
INSERT INTO `tbl_indexcontent` VALUES ('17', '12', '有利于提升中国高等教育综合实力和国际竞争力，为实现“两个一百年”奋斗目标和中华民族伟大复兴的中国梦提供有力支撑。', '4', '1');
INSERT INTO `tbl_indexcontent` VALUES ('18', '21', '2017年9月21日，教育部、财政部、国家发展改革委印发《关于公布世界一流大学和一流学科建设高校及建设学科名单的通', '4', '2');
INSERT INTO `tbl_indexcontent` VALUES ('19', '22', '知》，公布42所世界一流大学和95所一流学科建设高校及建设学科名单。', '4', '2');

-- ----------------------------
-- Table structure for `tbl_indexname`
-- ----------------------------
DROP TABLE IF EXISTS `tbl_indexname`;
CREATE TABLE `tbl_indexname` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL,
  `img` varchar(20) DEFAULT NULL,
  `paracount` int(11) DEFAULT NULL,
  `src` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tbl_indexname
-- ----------------------------
INSERT INTO `tbl_indexname` VALUES ('1', '大学起源', 'co', '2', 'https://p1.ssl.qhimg.com/dr/270_500_/t017ebafe9b1ab5620c.jpg?size=1000x666');
INSERT INTO `tbl_indexname` VALUES ('2', '大学使命', 'co', '2', 'https://p1.ssl.qhimg.com/dr/270_500_/t017893e9d8d34cce14.jpg?size=268x384');
INSERT INTO `tbl_indexname` VALUES ('3', '985和211大学', 'co', '2', 'http://www.sinaimg.cn/dy/slidenews/11_img/2011_16/18420_48427_311108.jpg');
INSERT INTO `tbl_indexname` VALUES ('4', '世界一流大学和一流学科', 'co', '2', 'https://p1.ssl.qhimg.com/dr/270_500_/t01d5cace27d7e1f216.jpg?size=600x396');

-- ----------------------------
-- Table structure for `tbl_information`
-- ----------------------------
DROP TABLE IF EXISTS `tbl_information`;
CREATE TABLE `tbl_information` (
  `id` int(11) NOT NULL,
  `username` varchar(255) CHARACTER SET utf8 NOT NULL,
  `totaltime` double DEFAULT NULL,
  `currenttime` double DEFAULT NULL,
  `university` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `motto` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of tbl_information
-- ----------------------------
INSERT INTO `tbl_information` VALUES ('1', '王小爽', '2056', '125', '清华大学', '韦编屡绝铁砚穿，口诵手钞那计年');
INSERT INTO `tbl_information` VALUES ('2', '单小楠', '1893', '150', '天津工业大学', '啦啦啦啦');
INSERT INTO `tbl_information` VALUES ('3', '金小媛', '1775', '58', '燕山大学', '哈哈哈哈哈');
INSERT INTO `tbl_information` VALUES ('5', '吕小浩', '1682', '68', '河北师范大学', '嘿嘿嘿嘿嘿');

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
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of tbl_mistaken
-- ----------------------------
INSERT INTO `tbl_mistaken` VALUES ('1', '已知向量a，b满足|a|=1，a·b=-1，则a·（2a-b）=？', '数学', '选择', '20191126', 'img', '4', '3', '2', '0', 'D');
INSERT INTO `tbl_mistaken` VALUES ('2', '曲线y=2ln(x+1)在点（0,0）处的切线方程为', '数学', '填空', '20191126', 'img', null, null, null, null, 'y=2x');

-- ----------------------------
-- Table structure for `tbl_position`
-- ----------------------------
DROP TABLE IF EXISTS `tbl_position`;
CREATE TABLE `tbl_position` (
  `id` int(11) NOT NULL,
  `username` varchar(20) CHARACTER SET utf8 NOT NULL,
  `portrait` varchar(20) NOT NULL,
  `lat` double NOT NULL,
  `lng` double NOT NULL,
  `type` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of tbl_position
-- ----------------------------
INSERT INTO `tbl_position` VALUES ('1', '王大爽', 'tx1.png', '37.9976', '114.5222', '0');
INSERT INTO `tbl_position` VALUES ('2', '单小楠', 'tx2.png', '37.99', '114.5228', '0');
INSERT INTO `tbl_position` VALUES ('3', '金小媛', 'tx3.png', '37.9', '114.523', '1');
INSERT INTO `tbl_position` VALUES ('4', '杨晓鑫', 'tx4.png', '38.001', '114.5238', '0');
INSERT INTO `tbl_position` VALUES ('5', '吕小浩', 'tx5.png', '38.103', '114.53', '1');
INSERT INTO `tbl_position` VALUES ('6', '郭小伟', 'tx6.png', '39.2', '114.7', '0');
INSERT INTO `tbl_position` VALUES ('7', '刘小辉', 'tx7.png', '39.56', '114.93', '0');

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
  `uId` int(11) NOT NULL DEFAULT '0',
  `fId` int(11) unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`uId`,`fId`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tbl_self_fans
-- ----------------------------
INSERT INTO `tbl_self_fans` VALUES ('1', '2');
INSERT INTO `tbl_self_fans` VALUES ('1', '3');
INSERT INTO `tbl_self_fans` VALUES ('1', '4');
INSERT INTO `tbl_self_fans` VALUES ('1', '5');
INSERT INTO `tbl_self_fans` VALUES ('1', '6');
INSERT INTO `tbl_self_fans` VALUES ('1', '7');
INSERT INTO `tbl_self_fans` VALUES ('2', '1');
INSERT INTO `tbl_self_fans` VALUES ('2', '3');
INSERT INTO `tbl_self_fans` VALUES ('2', '5');
INSERT INTO `tbl_self_fans` VALUES ('3', '2');
INSERT INTO `tbl_self_fans` VALUES ('4', '1');
INSERT INTO `tbl_self_fans` VALUES ('4', '6');
INSERT INTO `tbl_self_fans` VALUES ('4', '7');
INSERT INTO `tbl_self_fans` VALUES ('5', '1');
INSERT INTO `tbl_self_fans` VALUES ('5', '3');
INSERT INTO `tbl_self_fans` VALUES ('5', '6');
INSERT INTO `tbl_self_fans` VALUES ('6', '1');
INSERT INTO `tbl_self_fans` VALUES ('6', '2');
INSERT INTO `tbl_self_fans` VALUES ('7', '3');
INSERT INTO `tbl_self_fans` VALUES ('7', '5');
INSERT INTO `tbl_self_fans` VALUES ('7', '6');

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
INSERT INTO `tbl_self_schools_favorite` VALUES ('1', '1');
INSERT INTO `tbl_self_schools_favorite` VALUES ('1', '2');
INSERT INTO `tbl_self_schools_favorite` VALUES ('2', '8');
INSERT INTO `tbl_self_schools_favorite` VALUES ('3', '7');
INSERT INTO `tbl_self_schools_favorite` VALUES ('4', '0');
INSERT INTO `tbl_self_schools_favorite` VALUES ('5', '5');
INSERT INTO `tbl_self_schools_favorite` VALUES ('6', '1');
INSERT INTO `tbl_self_schools_favorite` VALUES ('6', '2');
INSERT INTO `tbl_self_schools_favorite` VALUES ('7', '1');
INSERT INTO `tbl_self_schools_favorite` VALUES ('7', '2');
INSERT INTO `tbl_self_schools_favorite` VALUES ('7', '3');

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
INSERT INTO `tbl_self_user` VALUES ('0', '13010001000', '第一个用户', '000000', '这是第一条座右铭', 'img', '0', '0');
INSERT INTO `tbl_self_user` VALUES ('1', '13010001001', '王大爽', 'wangshuang', '王大爽的座右铭', 'img', '0', '0');
INSERT INTO `tbl_self_user` VALUES ('2', '13000001002', '单小楠', 'shanxinnan', '单鑫楠的座右铭', 'img', '0', '0');
INSERT INTO `tbl_self_user` VALUES ('3', '13000001003', '金小媛', 'jinxinyuan', '金小媛的座右铭', 'img', '0', '0');
INSERT INTO `tbl_self_user` VALUES ('4', '13000001004', '杨小鑫', 'yangliuxin', '杨小鑫的座右铭', 'img', '0', '0');
INSERT INTO `tbl_self_user` VALUES ('5', '13000001005', '吕小浩', 'lvyihao', '吕小浩的座右铭', 'img', '0', '0');
INSERT INTO `tbl_self_user` VALUES ('6', '13000001006', '郭小伟', 'guowei', '郭小伟的座右铭', 'img', '0', '0');
INSERT INTO `tbl_self_user` VALUES ('7', '13000001007', '刘小辉', 'liupenghui', '刘小辉的座右铭', 'img', '0', '0');

-- ----------------------------
-- Table structure for `tbl_share`
-- ----------------------------
DROP TABLE IF EXISTS `tbl_share`;
CREATE TABLE `tbl_share` (
  `id` int(11) NOT NULL,
  `sharetitle` varchar(11) CHARACTER SET utf8 NOT NULL,
  `sharecontent` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `background` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`,`sharetitle`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of tbl_share
-- ----------------------------
INSERT INTO `tbl_share` VALUES ('1', '周四开会', '我们要在周四老地方开会，没事的话就都来吧', 'img');
INSERT INTO `tbl_share` VALUES ('1', '我们的小组成立了', '2019年11月我们的小组正式成立。祝我们的工程成功', 'img');
INSERT INTO `tbl_share` VALUES ('2', '开始写代码了', '又报错了，无奈……', 'img');
INSERT INTO `tbl_share` VALUES ('3', '今天在写数据库', '代码写不出，先写点假数据吧', 'img');
INSERT INTO `tbl_share` VALUES ('3', '分享一个高中数学的模块', 'https://wenku.baidu.com/view/4cf90f12591b6bd97f192279168884868762b87e.html', 'img');
