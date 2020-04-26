/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50506
Source Host           : localhost:3306
Source Database       : turings_db

Target Server Type    : MYSQL
Target Server Version : 50506
File Encoding         : 65001

Date: 2020-04-26 10:44:03
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `tbl_childvideo`
-- ----------------------------
DROP TABLE IF EXISTS `tbl_childvideo`;
CREATE TABLE `tbl_childvideo` (
  `childid` varchar(11) NOT NULL,
  `childurl` varchar(200) DEFAULT NULL,
  `childbelong` varchar(11) DEFAULT NULL,
  `childtitle` varchar(20) DEFAULT NULL,
  `childrank` int(11) DEFAULT NULL,
  PRIMARY KEY (`childid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tbl_childvideo
-- ----------------------------
INSERT INTO `tbl_childvideo` VALUES ('c1', 'http://vfx.mtime.cn/Video/2019/02/04/mp4/190204084208765161.mp4', 'guo1', '我是第一个的测试子视频', '1');
INSERT INTO `tbl_childvideo` VALUES ('c2', 'http://vfx.mtime.cn/Video/2019/02/04/mp4/190204084208765161.mp4', 'guo1', '我是第一个的第二个测试', '2');

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
-- Table structure for `tbl_coursevideo`
-- ----------------------------
DROP TABLE IF EXISTS `tbl_coursevideo`;
CREATE TABLE `tbl_coursevideo` (
  `id` int(11) NOT NULL DEFAULT '0',
  `course` varchar(20) DEFAULT NULL,
  `branch` varchar(20) DEFAULT NULL,
  `viewcounts` int(11) DEFAULT NULL,
  `videourl` varchar(200) DEFAULT NULL,
  `storedcounts` int(11) DEFAULT NULL,
  `sharedcounts` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tbl_coursevideo
-- ----------------------------
INSERT INTO `tbl_coursevideo` VALUES ('1', 'chinese', '文言文阅读', '10', 'http://vd2.bdstatic.com/mda-jiu6b112k593b2if/hd/mda-jiu6b112k593b2if.mp4', '8', '9');
INSERT INTO `tbl_coursevideo` VALUES ('2', 'chinese', '诗词鉴赏', '10', 'http://vd4.bdstatic.com/mda-jjfxt303f7b679z0/sc/mda-jjfxt303f7b679z0.mp4', '88', '99');
INSERT INTO `tbl_coursevideo` VALUES ('3', 'chinese', '如何写作文', '88', 'http://vd3.bdstatic.com/mda-ji7h290adghhms8p/mda-ji7h290adghhms8p.mp4', '89', '6');
INSERT INTO `tbl_coursevideo` VALUES ('4', 'chinese', '起个名真难', '99', 'http://vd4.bdstatic.com/mda-jk1jjj496b1je5ud/sc/mda-jk1jjj496b1je5ud.mp4', '88', '44');
INSERT INTO `tbl_coursevideo` VALUES ('5', 'chinese', '成语大会', '85', 'http://vfx.mtime.cn/Video/2019/03/09/mp4/190309153658147087.mp4', '525', '252');
INSERT INTO `tbl_coursevideo` VALUES ('6', 'chinese', '三字经', '258', 'http://vfx.mtime.cn/Video/2019/03/09/mp4/190309153658147087.mp4', '65', '49');
INSERT INTO `tbl_coursevideo` VALUES ('7', 'chinese', '朗读者', '59', 'http://vfx.mtime.cn/Video/2019/03/09/mp4/190309153658147087.mp4', '82', '28');

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
-- Table structure for `tbl_farm_punish`
-- ----------------------------
DROP TABLE IF EXISTS `tbl_farm_punish`;
CREATE TABLE `tbl_farm_punish` (
  `uid` int(11) NOT NULL AUTO_INCREMENT,
  `not_come` int(11) DEFAULT NULL,
  PRIMARY KEY (`uid`)
) ENGINE=MyISAM AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tbl_farm_punish
-- ----------------------------
INSERT INTO `tbl_farm_punish` VALUES ('1', '3');
INSERT INTO `tbl_farm_punish` VALUES ('2', '1');
INSERT INTO `tbl_farm_punish` VALUES ('3', '10');
INSERT INTO `tbl_farm_punish` VALUES ('4', '7');
INSERT INTO `tbl_farm_punish` VALUES ('5', '6');
INSERT INTO `tbl_farm_punish` VALUES ('6', '20');
INSERT INTO `tbl_farm_punish` VALUES ('7', '23');
INSERT INTO `tbl_farm_punish` VALUES ('8', '32');

-- ----------------------------
-- Table structure for `tbl_farm_score`
-- ----------------------------
DROP TABLE IF EXISTS `tbl_farm_score`;
CREATE TABLE `tbl_farm_score` (
  `uid` int(11) NOT NULL DEFAULT '0',
  `uwater` int(11) DEFAULT NULL,
  `uprocess` int(11) DEFAULT NULL,
  `status` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`uid`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tbl_farm_score
-- ----------------------------
INSERT INTO `tbl_farm_score` VALUES ('1', '13', '10', '0');
INSERT INTO `tbl_farm_score` VALUES ('3', '5', '56', '0');
INSERT INTO `tbl_farm_score` VALUES ('2', '2', '140', '1');
INSERT INTO `tbl_farm_score` VALUES ('4', '11', '100', '0');
INSERT INTO `tbl_farm_score` VALUES ('5', '7', '240', '1');
INSERT INTO `tbl_farm_score` VALUES ('6', '6', '266', '0');
INSERT INTO `tbl_farm_score` VALUES ('7', '15', '80', '2');
INSERT INTO `tbl_farm_score` VALUES ('8', '5', '32', '0');

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
-- Table structure for `tbl_indexcourse`
-- ----------------------------
DROP TABLE IF EXISTS `tbl_indexcourse`;
CREATE TABLE `tbl_indexcourse` (
  `parentid` varchar(11) NOT NULL DEFAULT '0',
  `courseimage` varchar(200) DEFAULT NULL,
  `coursetitle` varchar(20) DEFAULT NULL,
  `courseperson` varchar(20) DEFAULT NULL,
  `coursedata` varchar(20) DEFAULT NULL,
  `courseViews` int(11) DEFAULT NULL,
  PRIMARY KEY (`parentid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tbl_indexcourse
-- ----------------------------
INSERT INTO `tbl_indexcourse` VALUES ('10', 'https://tse3-mm.cn.bing.net/th?id=OIP.Vrytnh4rS_EIJwsSSqNRbwHaEi&w=295&h=180&c=7&o=5&dpr=1.25&pid=1.7', '恭喜你答对了一加一等于二', '45253人已加入', '直播时间9:02', '11');
INSERT INTO `tbl_indexcourse` VALUES ('11', 'https://tse4-mm.cn.bing.net/th?id=OIP.fXl2YUxSYQTxhJlGeIPYxAHaE5&w=237&h=161&c=7&o=5&dpr=1.25&pid=1.7', '一加一等于二请问对吗', '4252人已加入', '直播时间9:03', '10');
INSERT INTO `tbl_indexcourse` VALUES ('12', 'https://tse4-mm.cn.bing.net/th?id=OIP.P8BlzbWGSZTlYEzVxasncgHaD_&w=237&h=160&c=7&o=5&dpr=1.25&pid=1.7', '大家好才是真的好', '888人已加入', '直播时间10:04', '9');
INSERT INTO `tbl_indexcourse` VALUES ('2', 'https://tse1-mm.cn.bing.net/th?id=OIP.RiFOFk_duvfV9EeJ6TdB4gHaE9&w=296&h=197&c=7&o=5&dpr=1.25&pid=1.7', '真正的好课', '889人已加入', '直播时间6:05', '7');
INSERT INTO `tbl_indexcourse` VALUES ('3', 'https://tse4-mm.cn.bing.net/th?id=OIP.1f6Anm9ADiAMS-YznEenNgHaFP&w=260&h=184&c=7&o=5&dpr=1.25&pid=1.7', 'turings名师精选', '898人已加入', '直播时间9:06', '6');
INSERT INTO `tbl_indexcourse` VALUES ('4', 'https://tse4-mm.cn.bing.net/th?id=OIP.YtQ-g79IrwioagmUGVUQUAHaE8&w=294&h=192&c=7&o=5&dpr=1.25&pid=1.7', '学霸笔记分享给你请享用', '225人已加入', '直播时间17:07', '5');
INSERT INTO `tbl_indexcourse` VALUES ('5', 'https://tse4-mm.cn.bing.net/th?id=OIP.j67FmjbZkQG94KuHZ-Wm1wHaEJ&w=300&h=168&c=7&o=5&dpr=1.25&pid=1.7', '请给我看完这个视频谢谢', '252人已加入', '直播时间12:08', '4');
INSERT INTO `tbl_indexcourse` VALUES ('6', 'https://tse2-mm.cn.bing.net/th?id=OIP.ceetIOyHv1iKMUpBdNPp2AHaFj&w=274&h=196&c=7&o=5&dpr=1.25&pid=1.7', '起个名字真的是太难了', '528人已加入', '直播时间8:09', '3');
INSERT INTO `tbl_indexcourse` VALUES ('7', 'https://tse3-mm.cn.bing.net/th?id=OIP.nF08gZFf4lNjOwxjTgO5uQHaDM&w=300&h=129&c=7&o=5&dpr=1.25&pid=1.7', '我也不知道这个是个啥名字', '787人已加入', '直播时间7:10', '2');
INSERT INTO `tbl_indexcourse` VALUES ('8', 'https://tse3-mm.cn.bing.net/th?id=OIP.tVSeyoF2MlrZMzlBdG1e-QHaFD&w=213&h=160&c=7&o=5&dpr=1.25&pid=1.7', '我是一个测试用的标题', '4524人已加入', '直播时间9:11', '1');
INSERT INTO `tbl_indexcourse` VALUES ('9', 'https://tse1-mm.cn.bing.net/th?id=OIP.0rzA4sN0_GoaqZ1qy99IKQHaEK&w=300&h=168&c=7&o=5&dpr=1.25&pid=1.7', '请问一加一等于几', '245人已加入', '直播时间10:12', '0');
INSERT INTO `tbl_indexcourse` VALUES ('guo1', 'https://tse4-mm.cn.bing.net/th?id=OIP.P8BlzbWGSZTlYEzVxasncgHaD_&w=237&h=160&c=7&o=5&dpr=1.25&pid=1.7', '三天作文速成攻略', '99999人已加入', '直播时间9:01', '12');

-- ----------------------------
-- Table structure for `tbl_indexname`
-- ----------------------------
DROP TABLE IF EXISTS `tbl_indexname`;
CREATE TABLE `tbl_indexname` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL,
  `img` varchar(20) DEFAULT NULL,
  `content` text,
  `src` varchar(200) DEFAULT NULL,
  `num` varchar(20) DEFAULT NULL,
  `author` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tbl_indexname
-- ----------------------------
INSERT INTO `tbl_indexname` VALUES ('1', '近现代大学的起源', 'co', '     近现代大学直接起源于十二、十三世纪的欧洲中世纪大学，古代埃及、印度、中国等都是高等教育的发源地，古希腊、罗马、拜占廷及阿拉伯国家都建立了较完善和发达的高等教育体制。虽然许多教育史家把上述地方的高等学府也称之为大学，但严格地说，它们不是真正意义的大学。\r\n     1088年，意大利建立了第一所正规大学--博罗尼亚大学，它是欧洲最著名的罗马法研究中心(也被称为\\\"母大学\\\"，是一所学生大学)。\\r\\n      随后，欧洲各地相继出现了大学。巴黎大学是由巴黎圣母院的附属学校演变而来，1200年法国国王承认巴黎大学的学者具有合法的牧师资格，有司法豁免权(巴黎大学是第二所大学，是一所先生大学)。\r\n      现代大学开始于19世纪初，是指启蒙运动以后、经过理性主义改造、特别是指以德国洪堡创办的柏林大学为代表的新型大学，一般认为，1809年德国柏林大学的创立标志着现代意义上的大学的诞生\r\n     现代大学与中世纪大学的根本区别在于大学职能的转变。中世纪大学是传授已有知识的场所，将研究和发现知识排斥在大学之外，而现代大学则将科学研究作为自己的主要职能，将增扩人类的知识和培养科学工作者作为自己的主要任务，推崇\\\"学术自由\\\"和\\\"教学与研究的统一\\\"。柏林大学精神推动了德国的科学事业发达昌盛，19世纪初到20世纪初德国成为世界科学的中心。这一思想对世界高等教育也产生了深远影响，为近代大学形成奠定了基础。\r\n      而中国大学的起源是北洋大学堂，当年中国在甲午海战中惨败日本后，变法之声顿起，天津中西学堂改办为北洋大学堂，标志着中国近代第一所大学诞生。也有不少学者认为位于上海的由圣约翰书院变而来的圣约翰大学(诞生于1879年)是中国近代第一所大学。随着一些学者对中国近代高等教育史研究的不断深入，大家逐渐发现， 中国近代最早的教会大学是位于山东的由登州高等学堂演变而来的齐鲁大学(诞生于1864年)。\r\n    1898年戊戌变法，京师大学堂成立，这是中国近代第一所国立大学和综合大学。    大学，学名为普通高等学校，是一种功能独特的文化机构，是与社会的经济和政治机构既相互关联又鼎足而立的传承、研究、融合和创新高深学术的高等学府。它不仅是人类文化发展到一定阶段的产物，它还在长期办学实践的基础上，经过历史的积淀、自身的努力和外部环境的影响，逐步形成了一种独特的大学文化 。\r\n      大学从它产生到现在已有上千年的历史，上溯到它的产生，它主要是从德国、英国等国家最早发展起来的。中国现代大学源起于西方，现代西方大学又是从欧洲中世纪大学、英国大学、德国大学而到美国大学这样逐渐演化过来的，无论哪一个时代的大学都是以前大学的创造性继承而不是否定 。	', 'https://p1.ssl.qhimg.com/dr/270_500_/t017ebafe9b1ab5620c.jpg?size=1000x666', '3764', '启航狗');
INSERT INTO `tbl_indexname` VALUES ('2', '近现代大学的使命', 'co', '      在大学理念支配下的大学使命要求大学培养的学生首先是有高尚品格的，有教养的人，这是符合大学的本质。这个使命是指培养学生完整的人格、净化学生的心灵、修养学生的品行、锻炼学生对事物进行批判的能力，而不是仅仅对学生进行专业教育。\r\n      正如英国著名教育理论家纽曼所认识到的:\"从功利派的论点中看到了真正教育的死敌。新大学在功利派理论的指导下，更看重的是专业培训而不是文化要求，是考试及结果而不是心理过程，是对事实的被动获取而不是心智的一般活动。\"纽曼所指的\"新大学\"，是违背了大学的逻辑而按市场经济的逻辑和政治的逻辑来当作它的使命而运行的大学。', 'https://p1.ssl.qhimg.com/dr/270_500_/t017893e9d8d34cce14.jpg?size=268x384', '2054', '龙猫');
INSERT INTO `tbl_indexname` VALUES ('3', '985和211大学', 'co', '       211大学：第一点是面向21世纪，第二点是一百所大学，第三点是国家重点建设大学,这就是211大学的具体含义分析。\r\n      985大学：名称主要来源于日期，1998年5月4日，也就是北大校庆100周年，江泽民主席提出建设世界一流大学，985大学共有39所。', 'http://www.sinaimg.cn/dy/slidenews/11_img/2011_16/18420_48427_311108.jpg', '3685', '无名');
INSERT INTO `tbl_indexname` VALUES ('4', '世界一流大学和一流学科', 'co', '      世界一流大学和一流学科，简称“双一流”；建设世界一流大学和一流学科，是中国共产党中央、国务院作出的重大战略决策，有利于提升中国高等教育综合实力和国际竞争力，为实现“两个一百年”奋斗目标和中华民族伟大复兴的中国梦提供有力支撑。\r\n      2017年9月21日，教育部、财政部、国家发展改革委印发《关于公布世界一流大学和一流学科建设高校及建设学科名单的通知》，公布42所世界一流大学和95所一流学科建设高校及建设学科名单。', 'https://p1.ssl.qhimg.com/dr/270_500_/t01d5cace27d7e1f216.jpg?size=600x396', '7741', '天真的我');

-- ----------------------------
-- Table structure for `tbl_information`
-- ----------------------------
DROP TABLE IF EXISTS `tbl_information`;
CREATE TABLE `tbl_information` (
  `id` int(11) NOT NULL,
  `username` varchar(255) CHARACTER SET utf8 NOT NULL,
  `portrait` varchar(255) DEFAULT NULL,
  `totaltime` double DEFAULT NULL,
  `currenttime` double DEFAULT NULL,
  `university` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `motto` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of tbl_information
-- ----------------------------
INSERT INTO `tbl_information` VALUES ('1', '王小爽', 'a3', '2056', '125', '清华大学', '韦编屡绝铁砚穿，口诵手钞那计年');
INSERT INTO `tbl_information` VALUES ('2', '单小楠', 'a3', '1893', '150', '天津工业大学', '啦啦啦啦');
INSERT INTO `tbl_information` VALUES ('3', '金小媛', 'a3', '1775', '58', '燕山大学', '哈哈哈哈哈');
INSERT INTO `tbl_information` VALUES ('5', '吕小浩', 'a3', '1682', '68', '河北师范大学', '嘿嘿嘿嘿嘿');

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
  `uId` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of tbl_mistaken
-- ----------------------------
INSERT INTO `tbl_mistaken` VALUES ('1', '已知向量a，b满足|a|=1，a·b=-1，则a·（2a-b）=？', '数学', '选择', '20191126', 'img', '4', '3', '2', '0', 'D', '1');
INSERT INTO `tbl_mistaken` VALUES ('2', '曲线y=2ln(x+1)在点（0,0）处的切线方程为', '数学', '填空', '20191126', 'img', null, null, null, null, 'y=2x', null);
INSERT INTO `tbl_mistaken` VALUES ('3', '数学', '集合', '填空题', '2019-12-09', '1575893457455.jpg', '', '', '', '', '', '1');
INSERT INTO `tbl_mistaken` VALUES ('4', '数学', '集合', '填空题', '2019-12-10', '1575936251811.jpg', '', '', '', '', '哈哈哈', '4');
INSERT INTO `tbl_mistaken` VALUES ('5', '数学', '集合', '填空题', '2019-12-10', '1575937052405.jpg', '', '', '', '', '斤斤计较', '4');
INSERT INTO `tbl_mistaken` VALUES ('6', '数学', '集合', '填空题', '2019-12-10', '1575937471020.jpg', '', '', '', '', '', '4');
INSERT INTO `tbl_mistaken` VALUES ('7', '数学', '集合', '填空题', '2019-12-10', '1575960060606.jpg', '', '', '', '', '', '4');

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
-- Table structure for `tbl_position`
-- ----------------------------
DROP TABLE IF EXISTS `tbl_position`;
CREATE TABLE `tbl_position` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(20) CHARACTER SET utf8 NOT NULL,
  `portrait` varchar(20) DEFAULT NULL,
  `lat` double DEFAULT NULL,
  `lng` double DEFAULT NULL,
  `type` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of tbl_position
-- ----------------------------
INSERT INTO `tbl_position` VALUES ('1', '王大爽', 'i1', '0', '0', '0');
INSERT INTO `tbl_position` VALUES ('2', '单小楠', 'i2', '37.99', '114.5228', '0');
INSERT INTO `tbl_position` VALUES ('3', '金小媛', 'i3', '46.192', '124.141', '1');
INSERT INTO `tbl_position` VALUES ('4', '杨小鑫', 'i4', '38.001', '114.5238', '0');
INSERT INTO `tbl_position` VALUES ('5', '吕小浩', 'i5', '30.926', '95.883', '1');
INSERT INTO `tbl_position` VALUES ('6', '郭小伟', 'i6', '39.2', '114.7', '0');
INSERT INTO `tbl_position` VALUES ('7', '刘小辉', 'i7', '39.56', '114.93', '0');
INSERT INTO `tbl_position` VALUES ('8', '西瓜', 'i8', '34.533', '94.853', '1');
INSERT INTO `tbl_position` VALUES ('9', '芒果', 'i9', '30.127', '101.107', '1');
INSERT INTO `tbl_position` VALUES ('10', '草莓', 'i10', '26.48', '106.546', '1');
INSERT INTO `tbl_position` VALUES ('11', '西红柿', 'i11', '30.764', '107.436', '1');
INSERT INTO `tbl_position` VALUES ('12', '香蕉', 'i12', '34.01', '117.443', '1');
INSERT INTO `tbl_position` VALUES ('13', '丑橘', 'i13', '37.017', '105.375', '1');
INSERT INTO `tbl_position` VALUES ('14', '山竹', 'i14', '37.84', '115.972', '1');
INSERT INTO `tbl_position` VALUES ('15', '火龙果', 'i15', '26.745', '113.028', '1');
INSERT INTO `tbl_position` VALUES ('16', '猕猴桃', 'i16', '41.257', '107.141', '1');

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
INSERT INTO `tbl_self_courses_my` VALUES ('2', '1');
INSERT INTO `tbl_self_courses_my` VALUES ('5', '3');

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
  `uId` int(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`sId`,`uId`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tbl_self_schools_favorite
-- ----------------------------
INSERT INTO `tbl_self_schools_favorite` VALUES ('1', '1');
INSERT INTO `tbl_self_schools_favorite` VALUES ('1', '12');
INSERT INTO `tbl_self_schools_favorite` VALUES ('2', '2');
INSERT INTO `tbl_self_schools_favorite` VALUES ('2', '13');
INSERT INTO `tbl_self_schools_favorite` VALUES ('3', '3');
INSERT INTO `tbl_self_schools_favorite` VALUES ('3', '14');
INSERT INTO `tbl_self_schools_favorite` VALUES ('4', '4');
INSERT INTO `tbl_self_schools_favorite` VALUES ('4', '15');
INSERT INTO `tbl_self_schools_favorite` VALUES ('5', '5');
INSERT INTO `tbl_self_schools_favorite` VALUES ('5', '16');
INSERT INTO `tbl_self_schools_favorite` VALUES ('6', '6');
INSERT INTO `tbl_self_schools_favorite` VALUES ('6', '17');
INSERT INTO `tbl_self_schools_favorite` VALUES ('7', '7');
INSERT INTO `tbl_self_schools_favorite` VALUES ('7', '18');
INSERT INTO `tbl_self_schools_favorite` VALUES ('8', '8');
INSERT INTO `tbl_self_schools_favorite` VALUES ('8', '20');
INSERT INTO `tbl_self_schools_favorite` VALUES ('9', '9');
INSERT INTO `tbl_self_schools_favorite` VALUES ('9', '19');
INSERT INTO `tbl_self_schools_favorite` VALUES ('10', '10');
INSERT INTO `tbl_self_schools_favorite` VALUES ('11', '11');

-- ----------------------------
-- Table structure for `tbl_self_user`
-- ----------------------------
DROP TABLE IF EXISTS `tbl_self_user`;
CREATE TABLE `tbl_self_user` (
  `uId` int(11) NOT NULL AUTO_INCREMENT,
  `uTel` char(11) DEFAULT NULL,
  `uName` varchar(20) DEFAULT NULL,
  `uPwd` varchar(20) DEFAULT NULL,
  `uMotto` varchar(60) DEFAULT NULL,
  `uAvatar` varchar(255) DEFAULT NULL,
  `uTime` int(11) DEFAULT NULL,
  `uScore` int(11) DEFAULT NULL,
  `uFanscount` int(11) DEFAULT '0',
  `uAttentioncount` int(11) DEFAULT '0',
  `uAchievecount` int(11) DEFAULT '0',
  PRIMARY KEY (`uId`)
) ENGINE=MyISAM AUTO_INCREMENT=22 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tbl_self_user
-- ----------------------------
INSERT INTO `tbl_self_user` VALUES ('1', '15227859968', '王大爽', 'wangshuang', '自信，我是命运的主宰者', 'http://jxy2019.oss-cn-beijing.aliyuncs.com/avatars/i1t1576118560811.png?Expires=1891478561&OSSAccessKeyId=LTAI4FoQ82rmSV5EzaE1KtPU&Signature=YvC0ceYiUXcfAAi2bd7aZX2Buvo%3D', '1586', '36', '6', '4', '0');
INSERT INTO `tbl_self_user` VALUES ('2', '13000001002', '单小楠', 'shanxinnan', '丰富地过每一天，快乐地看每一天', 'https://jxy2019.oss-cn-beijing.aliyuncs.com/avatars/nv.png', '1256', '86', '3', '3', '0');
INSERT INTO `tbl_self_user` VALUES ('3', '13000001003', '金小媛', 'jinxinyuan', '人类因梦想而变的伟大', 'http://jxy2019.oss-cn-beijing.aliyuncs.com/avatars/i3t1576119212075.png?Expires=1891479212&OSSAccessKeyId=LTAI4FoQ82rmSV5EzaE1KtPU&Signature=G0jxbEBkJe4aSyAVc21B2i8mA%2F8%3D', '1259', '52', '5', '4', '0');
INSERT INTO `tbl_self_user` VALUES ('4', '13000001004', '杨小鑫', 'yangliuxin', '人生如茶，粗品是苦的，细品是香的', 'https://jxy2019.oss-cn-beijing.aliyuncs.com/avatars/nv.png', '1842', '86', '3', '2', '0');
INSERT INTO `tbl_self_user` VALUES ('5', '13000001005', '吕小浩', 'lvyihao', '失败的是事，绝不应是人', 'https://jxy2019.oss-cn-beijing.aliyuncs.com/avatars/nv.png', '1263', '75', '3', '3', '0');
INSERT INTO `tbl_self_user` VALUES ('6', '13000001006', '郭小伟', 'guowei', '从来不让认识我的朋友后悔', 'https://jxy2019.oss-cn-beijing.aliyuncs.com/avatars/nv.png', '1862', '86', '2', '4', '0');
INSERT INTO `tbl_self_user` VALUES ('7', '13000001007', '刘小辉', 'liupenghui', '努力改变，不为别人，只为脱胎换骨', 'https://jxy2019.oss-cn-beijing.aliyuncs.com/avatars/nv.png', '1230', '95', '3', '3', '0');
INSERT INTO `tbl_self_user` VALUES ('8', '13256899853', '西瓜', '111111', '态度决定高度，习惯主宰人生', 'https://jxy2019.oss-cn-beijing.aliyuncs.com/avatars/nv.png', '1542', '56', '0', '0', '0');
INSERT INTO `tbl_self_user` VALUES ('9', '15987532685', '芒果', '111111', '人生就是距离，距离就是人生', 'https://jxy2019.oss-cn-beijing.aliyuncs.com/avatars/nv.png', '1542', '85', '0', '0', '0');
INSERT INTO `tbl_self_user` VALUES ('10', '12355852626', '草莓', '111111', '天生我才必有用，千金散去还复来', 'https://jxy2019.oss-cn-beijing.aliyuncs.com/avatars/nv.png', '1542', '85', '0', '0', '0');
INSERT INTO `tbl_self_user` VALUES ('11', '13265894258', '西红柿', '111111', '天生我才必有用，千金散去还复来', 'https://jxy2019.oss-cn-beijing.aliyuncs.com/avatars/nv.png', '1542', '85', '0', '0', '0');
INSERT INTO `tbl_self_user` VALUES ('12', '15687953159', '香蕉', '111111', '天生我才必有用，千金散去还复来', 'https://jxy2019.oss-cn-beijing.aliyuncs.com/avatars/nv.png', '1542', '85', '0', '0', '0');
INSERT INTO `tbl_self_user` VALUES ('13', '15631648623', '丑橘', '111111', '天生我才必有用，千金散去还复来', 'https://jxy2019.oss-cn-beijing.aliyuncs.com/avatars/nv.png', '1542', '85', '0', '0', '0');
INSERT INTO `tbl_self_user` VALUES ('14', '15326489532', '山竹', '111111', '天生我才必有用，千金散去还复来', 'https://jxy2019.oss-cn-beijing.aliyuncs.com/avatars/nv.png', '1542', '85', '0', '0', '0');
INSERT INTO `tbl_self_user` VALUES ('15', '19852346286', '火龙果', '111111', '天生我才必有用，千金散去还复来', 'https://jxy2019.oss-cn-beijing.aliyuncs.com/avatars/nv.png', '1542', '85', '0', '0', '0');
INSERT INTO `tbl_self_user` VALUES ('16', '12346286428', '猕猴桃', '111111', '天生我才必有用，千金散去还复来', 'https://jxy2019.oss-cn-beijing.aliyuncs.com/avatars/nv.png', '1542', '85', '0', '0', '0');
INSERT INTO `tbl_self_user` VALUES ('17', '15987531688', '哈密瓜', '111111', '天生我才必有用，千金散去还复来', 'https://jxy2019.oss-cn-beijing.aliyuncs.com/avatars/nv.png', '1542', '85', '0', '0', '0');
INSERT INTO `tbl_self_user` VALUES ('18', '16879425634', '荔枝', '111111', '天生我才必有用，千金散去还复来', 'https://jxy2019.oss-cn-beijing.aliyuncs.com/avatars/nv.png', '1542', '85', '0', '0', '0');
INSERT INTO `tbl_self_user` VALUES ('19', '18531264866', '葡萄', '111111', '天生我才必有用，千金散去还复来', 'https://jxy2019.oss-cn-beijing.aliyuncs.com/avatars/nv.png', '1542', '85', '0', '0', '0');
INSERT INTO `tbl_self_user` VALUES ('20', '15326448952', '蛋挞', '111111', '天生我才必有用，千金散去还复来', 'https://jxy2019.oss-cn-beijing.aliyuncs.com/avatars/nv.png', '1542', '85', '0', '0', '0');

-- ----------------------------
-- Table structure for `tbl_share`
-- ----------------------------
DROP TABLE IF EXISTS `tbl_share`;
CREATE TABLE `tbl_share` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `sharetitle` varchar(255) CHARACTER SET utf8 NOT NULL,
  `sharecontent` varchar(2550) CHARACTER SET utf8 DEFAULT NULL,
  `background` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`,`sharetitle`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of tbl_share
-- ----------------------------
INSERT INTO `tbl_share` VALUES ('1', '王大爽', '心流状态', '当你打开游戏界面，你根本不需要调整状态，几乎可以一秒进入心流，很快注意力就高度集中了，甚至在玩的过程中，别人叫你，都听不到，抗干扰能力超强。而且，当你打的正带劲的时候，根本不会想着吃饭时间到啦，要吃饭啦；太累了太耗费脑力啦，要休息啦。要是谁有事叫你，你还特烦。当你在学习或者工作的时候呢？可能要磨蹭半天才开始干活儿，面对复杂的问题，好久都没办法进入状态。稍微有点动静，就受干扰了。有个信息来，你就不自觉玩手机去了...\n\n', 'bj1');
INSERT INTO `tbl_share` VALUES ('2', '单小楠', '特别注意', '不要妄想能兼顾，在各科之间保持平衡\r\n\r\n保持平衡是很难的，别人能做到你不一定能做到\r\n\r\n很多人会有误区，觉得每一科的时间必须平均分配，不然就难受\r\n\r\n这就没有坚持两点论和重点论的统一\r\n\r\n你各科时间平均分配，搞不好一科都学不好', 'bj1');
INSERT INTO `tbl_share` VALUES ('3', '金小媛', '敲黑板啦', '少玩手机。\r\n\r\n少刷朋友圈。\r\n\r\n少玩知乎。\r\n\r\n少混圈子。\r\n\r\n不要看小说，玩王者。', 'bj1');
INSERT INTO `tbl_share` VALUES ('4', '杨小鑫', '敲黑板啦2', '高一开始跟着学高三会轻松一点，也只是一点，但是你的心态会很稳。\r\n\r\n老师好，跟着老师学。\r\n\r\n老师不好，自己搞。\r\n\r\n时间很长，慢慢打基础，学到高三你就懂了。\r\n\r\n不要想着，还有很长，就三天打鱼两天晒网，这样相当于没学。\r\n\r\n抱着考985的心，考成什么样与你无关，你只要坚持就完事了。', 'bj1');
INSERT INTO `tbl_share` VALUES ('5', '吕小浩', '敲黑板啦3', '人脑并不发达，记忆和理解力都具有滞后性。同样是两小时学习两小时游戏，如果每五分钟转换一次，最终很可能没有任何成果；如果连续两小时学习然后再两小时游戏，效果就会好很多。', 'bj1');
INSERT INTO `tbl_share` VALUES ('6', '郭小伟', '敲黑板啦4', '高中，可能是人生中最后的连续专心学习的机会。努力抓住这个机会，培养自己深度学习的能力，就会受益一生。否则到了我这个岁数突然发现手机打乱了我一切学习机会，哭都找不到调门。', 'bj1');
INSERT INTO `tbl_share` VALUES ('7', '刘小辉', '敲黑板啦5', '关于积累我想说明一点，就是培养随手记录的习惯，更要学会记录适合自己风格和内容的句子。最好不要照搬某某地方看到的作文素材，还是自己记录的最好用。其次，要找好记的、好用的、朗朗上口的、角度多变的句子，方便使用。', 'bj1');
INSERT INTO `tbl_share` VALUES ('8', '西瓜', '心灵鸡汤', '关于积累我想说明一点，就是培养随手记录的习惯，更要学会记录适合自己风格和内容的句子。最好不要照搬某某地方看到的作文素材，还是自己记录的最好用。其次，要找好记的、好用的、朗朗上口的、角度多变的句子，方便使用。', 'bj1');
INSERT INTO `tbl_share` VALUES ('9', '芒果', '心灵鸡汤1', '关于积累我想说明一点，就是培养随手记录的习惯，更要学会记录适合自己风格和内容的句子。最好不要照搬某某地方看到的作文素材，还是自己记录的最好用。其次，要找好记的、好用的、朗朗上口的、角度多变的句子，方便使用。', 'bj1');
INSERT INTO `tbl_share` VALUES ('10', '草莓', '心灵鸡汤2', '关于积累我想说明一点，就是培养随手记录的习惯，更要学会记录适合自己风格和内容的句子。最好不要照搬某某地方看到的作文素材，还是自己记录的最好用。其次，要找好记的、好用的、朗朗上口的、角度多变的句子，方便使用。', 'bj1');
INSERT INTO `tbl_share` VALUES ('11', '西红柿', '心灵鸡汤3', '关于积累我想说明一点，就是培养随手记录的习惯，更要学会记录适合自己风格和内容的句子。最好不要照搬某某地方看到的作文素材，还是自己记录的最好用。其次，要找好记的、好用的、朗朗上口的、角度多变的句子，方便使用。', 'bj1');
INSERT INTO `tbl_share` VALUES ('12', '香蕉', '心灵鸡汤4', '关于积累我想说明一点，就是培养随手记录的习惯，更要学会记录适合自己风格和内容的句子。最好不要照搬某某地方看到的作文素材，还是自己记录的最好用。其次，要找好记的、好用的、朗朗上口的、角度多变的句子，方便使用。', 'bj1');
INSERT INTO `tbl_share` VALUES ('13', '丑橘', '心灵鸡汤5', '关于积累我想说明一点，就是培养随手记录的习惯，更要学会记录适合自己风格和内容的句子。最好不要照搬某某地方看到的作文素材，还是自己记录的最好用。其次，要找好记的、好用的、朗朗上口的、角度多变的句子，方便使用。', 'bj1');
INSERT INTO `tbl_share` VALUES ('14', '山竹', '心灵鸡汤6', '关于积累我想说明一点，就是培养随手记录的习惯，更要学会记录适合自己风格和内容的句子。最好不要照搬某某地方看到的作文素材，还是自己记录的最好用。其次，要找好记的、好用的、朗朗上口的、角度多变的句子，方便使用。', 'bj1');
INSERT INTO `tbl_share` VALUES ('15', '火龙果', '心灵鸡汤7', '关于积累我想说明一点，就是培养随手记录的习惯，更要学会记录适合自己风格和内容的句子。最好不要照搬某某地方看到的作文素材，还是自己记录的最好用。其次，要找好记的、好用的、朗朗上口的、角度多变的句子，方便使用。', 'bj1');
INSERT INTO `tbl_share` VALUES ('16', '猕猴桃', '心灵鸡汤8', '关于积累我想说明一点，就是培养随手记录的习惯，更要学会记录适合自己风格和内容的句子。最好不要照搬某某地方看到的作文素材，还是自己记录的最好用。其次，要找好记的、好用的、朗朗上口的、角度多变的句子，方便使用。', 'bj1');
INSERT INTO `tbl_share` VALUES ('17', '哈密瓜', '心灵鸡汤9', '关于积累我想说明一点，就是培养随手记录的习惯，更要学会记录适合自己风格和内容的句子。最好不要照搬某某地方看到的作文素材，还是自己记录的最好用。其次，要找好记的、好用的、朗朗上口的、角度多变的句子，方便使用。', 'bj1');
INSERT INTO `tbl_share` VALUES ('18', '荔枝', '心灵鸡汤10', '关于积累我想说明一点，就是培养随手记录的习惯，更要学会记录适合自己风格和内容的句子。最好不要照搬某某地方看到的作文素材，还是自己记录的最好用。其次，要找好记的、好用的、朗朗上口的、角度多变的句子，方便使用。', 'bj1');
INSERT INTO `tbl_share` VALUES ('19', '葡萄', '心灵鸡汤11', '关于积累我想说明一点，就是培养随手记录的习惯，更要学会记录适合自己风格和内容的句子。最好不要照搬某某地方看到的作文素材，还是自己记录的最好用。其次，要找好记的、好用的、朗朗上口的、角度多变的句子，方便使用。', 'bj1');
INSERT INTO `tbl_share` VALUES ('20', '蛋挞', '心灵鸡汤12', '关于积累我想说明一点，就是培养随手记录的习惯，更要学会记录适合自己风格和内容的句子。最好不要照搬某某地方看到的作文素材，还是自己记录的最好用。其次，要找好记的、好用的、朗朗上口的、角度多变的句子，方便使用。', 'bj1');
INSERT INTO `tbl_share` VALUES ('21', '金小媛', '啊赛风', '防身从不', '2131230821');

-- ----------------------------
-- Table structure for `tbl_story`
-- ----------------------------
DROP TABLE IF EXISTS `tbl_story`;
CREATE TABLE `tbl_story` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(50) DEFAULT NULL,
  `bigimg` varchar(500) DEFAULT NULL,
  `smallimg` varchar(500) DEFAULT NULL,
  `name` varchar(500) DEFAULT NULL,
  `starnum` varchar(20) DEFAULT NULL,
  `content` text,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tbl_story
-- ----------------------------
INSERT INTO `tbl_story` VALUES ('1', '没动力？No,加油少年！', 'http://uploads.5068.com/allimg/1805/147-1P51Q03208.png', 'tf3', '昊先生123', '100', '      在高考中有位考生写过这样一段话:我曾经埋怨过，脚底发黄的布鞋，踏不出青春的活力，直到我发觉霍金转动的轮椅，嵌出深深的历史痕迹;我曾咒骂过，脸边黝黑的胎记，映不出美丽的脸庞，直到我看见全身黝黑的黑人，露出甜甜的美丽微笑……\r\n      也许你曾抱怨过上帝是捉弄人的，他对你吝啬幸福，却让痛苦折磨着你，日日夜夜，疲惫不堪，换来的只是滚烫的泪珠。但苦尽甘来后，当你揭开那沉重的幕纱，你会看清，上帝是公平的，磨难也是一种财富。\r\n      曾经有人做了一个试验。他点清了一座山林中的红羊后，把山中的狼全部捕捉起来，让红羊没有敌人。照理说，红羊应该是悠闲自得、快乐地生活了，但事与愿违，红羊为了争夺一小片土地、甚至一棵青草而搏斗，由原来一支整齐的队伍变为一盘散沙。\r\n      经过搏斗的红羊一只只疲惫不堪，过去那种矫健、奔跑如飞的红羊在这座山林中再未曾出现过，而且，红羊的数量也在逐渐减少。是什么原因呢?因为人们捕获了狼，红羊没有了给它们造成生命威胁的动物，而自相残杀直至消亡，失去了危机。同时也失去了生机。可见，在生态环境中，没有磨难就不会有生态平衡的系统。\r\n      张海迪胸部以下失去知觉，但这些困难反而使她有了更为远大的目标，学习掌握了德语、日语、英语。她为什么能取得常人都不容易取得的成就呢?因为她在承受巨大挫折的同时，也将挫折化为了动力，让挫折成为自己的奴隶，再一次战胜了磨难。\r\n      人生如一块璞玉，这是上帝赐予你最大的财富，这块璞玉中有着酸甜苦辣，也许痛苦过后便是幸福，这是上帝给予你最大的机遇。只有自己努力来雕琢这块璞玉，才能使它成为完美无瑕的艺术品。同学们，当你遇到挫折、困难时，不妨想想，上帝是公平的，有时磨难也是一种财富。');
INSERT INTO `tbl_story` VALUES ('2', '努力从现在开始', 'https://p0.ssl.qhimgs1.com/sdr/400__/t0168f2443cee7a5bbe.jpg', 'tf2', '学霸狗', '160', '      在高考中有位考生写过这样一段话:我曾经埋怨过，脚底发黄的布鞋，踏不出青春的活力，直到我发觉霍金转动的轮椅，嵌出深深的历史痕迹;我曾咒骂过，脸边黝黑的胎记，映不出美丽的脸庞，直到我看见全身黝黑的黑人，露出甜甜的美丽微笑……\r\n      也许你曾抱怨过上帝是捉弄人的，他对你吝啬幸福，却让痛苦折磨着你，日日夜夜，疲惫不堪，换来的只是滚烫的泪珠。但苦尽甘来后，当你揭开那沉重的幕纱，你会看清，上帝是公平的，磨难也是一种财富。\r\n      曾经有人做了一个试验。他点清了一座山林中的红羊后，把山中的狼全部捕捉起来，让红羊没有敌人。照理说，红羊应该是悠闲自得、快乐地生活了，但事与愿违，红羊为了争夺一小片土地、甚至一棵青草而搏斗，由原来一支整齐的队伍变为一盘散沙。\r\n      经过搏斗的红羊一只只疲惫不堪，过去那种矫健、奔跑如飞的红羊在这座山林中再未曾出现过，而且，红羊的数量也在逐渐减少。是什么原因呢?因为人们捕获了狼，红羊没有了给它们造成生命威胁的动物，而自相残杀直至消亡，失去了危机。同时也失去了生机。可见，在生态环境中，没有磨难就不会有生态平衡的系统。\r\n      张海迪胸部以下失去知觉，但这些困难反而使她有了更为远大的目标，学习掌握了德语、日语、英语。她为什么能取得常人都不容易取得的成就呢?因为她在承受巨大挫折的同时，也将挫折化为了动力，让挫折成为自己的奴隶，再一次战胜了磨难。\r\n      人生如一块璞玉，这是上帝赐予你最大的财富，这块璞玉中有着酸甜苦辣，也许痛苦过后便是幸福，这是上帝给予你最大的机遇。只有自己努力来雕琢这块璞玉，才能使它成为完美无瑕的艺术品。同学们，当你遇到挫折、困难时，不妨想想，上帝是公平的，有时磨难也是一种财富。时光飞逝，一年时光就这样悄悄从指尖溜走, 或许除了自己没人能知道这一年我是怎么过来的，但现在的我所向披靡，战无不胜。 因为我有勇气和信念。高二时是班里的末等生，高三毕业时在班中名列前茅，不是天才，没有过人天赋，就是努力和坚持，还有那星星点点微光，那个叫做信念的东西支撑着， 一直向前，度过最难过的时段，走过最泥泞的路。谁都有过不堪，都有过过去');
INSERT INTO `tbl_story` VALUES ('3', '每天迎接一个崭新的自己', 'https://p0.ssl.qhimgs1.com/sdr/400__/t019fc63acd104c5490.jpg', 'tf1', '迷心兔', '226', '      在高考中有位考生写过这样一段话:我曾经埋怨过，脚底发黄的布鞋，踏不出青春的活力，直到我发觉霍金转动的轮椅，嵌出深深的历史痕迹;我曾咒骂过，脸边黝黑的胎记，映不出美丽的脸庞，直到我看见全身黝黑的黑人，露出甜甜的美丽微笑……\r\n      也许你曾抱怨过上帝是捉弄人的，他对你吝啬幸福，却让痛苦折磨着你，日日夜夜，疲惫不堪，换来的只是滚烫的泪珠。但苦尽甘来后，当你揭开那沉重的幕纱，你会看清，上帝是公平的，磨难也是一种财富。\r\n      曾经有人做了一个试验。他点清了一座山林中的红羊后，把山中的狼全部捕捉起来，让红羊没有敌人。照理说，红羊应该是悠闲自得、快乐地生活了，但事与愿违，红羊为了争夺一小片土地、甚至一棵青草而搏斗，由原来一支整齐的队伍变为一盘散沙。\r\n      经过搏斗的红羊一只只疲惫不堪，过去那种矫健、奔跑如飞的红羊在这座山林中再未曾出现过，而且，红羊的数量也在逐渐减少。是什么原因呢?因为人们捕获了狼，红羊没有了给它们造成生命威胁的动物，而自相残杀直至消亡，失去了危机。同时也失去了生机。可见，在生态环境中，没有磨难就不会有生态平衡的系统。\r\n      张海迪胸部以下失去知觉，但这些困难反而使她有了更为远大的目标，学习掌握了德语、日语、英语。她为什么能取得常人都不容易取得的成就呢?因为她在承受巨大挫折的同时，也将挫折化为了动力，让挫折成为自己的奴隶，再一次战胜了磨难。\r\n      人生如一块璞玉，这是上帝赐予你最大的财富，这块璞玉中有着酸甜苦辣，也许痛苦过后便是幸福，这是上帝给予你最大的机遇。只有自己努力来雕琢这块璞玉，才能使它成为完美无瑕的艺术品。同学们，当你遇到挫折、困难时，不妨想想，上帝是公平的，有时磨难也是一种财富。时光飞逝，一年时光就这样悄悄从指尖溜走, 或许除了自己没人能知道这一年我是怎么过来的，但现在的我所向披靡，战无不胜。 因为我有勇气和信念。高二时是班里的末等生，高三毕业时在班中名列前茅，不是天才，没有过人天赋，就是努力和坚持，还有那星星点点微光，那个叫做信念的东西支撑着，一直向前，度过最难过的时段，走过最泥泞的路。谁都有过不堪，都有过过去');
INSERT INTO `tbl_story` VALUES ('4', '99%的汗水+1%的智力=成功', 'http://img.smzy.com/Soft/UploadPic/2016-3/201631214351846179.jpg', 'tanimal4', 'HelloWorld!', '62', '      在高考中有位考生写过这样一段话:我曾经埋怨过，脚底发黄的布鞋，踏不出青春的活力，直到我发觉霍金转动的轮椅，嵌出深深的历史痕迹;我曾咒骂过，脸边黝黑的胎记，映不出美丽的脸庞，直到我看见全身黝黑的黑人，露出甜甜的美丽微笑……\r\n      也许你曾抱怨过上帝是捉弄人的，他对你吝啬幸福，却让痛苦折磨着你，日日夜夜，疲惫不堪，换来的只是滚烫的泪珠。但苦尽甘来后，当你揭开那沉重的幕纱，你会看清，上帝是公平的，磨难也是一种财富。\r\n      曾经有人做了一个试验。他点清了一座山林中的红羊后，把山中的狼全部捕捉起来，让红羊没有敌人。照理说，红羊应该是悠闲自得、快乐地生活了，但事与愿违，红羊为了争夺一小片土地、甚至一棵青草而搏斗，由原来一支整齐的队伍变为一盘散沙。\r\n      经过搏斗的红羊一只只疲惫不堪，过去那种矫健、奔跑如飞的红羊在这座山林中再未曾出现过，而且，红羊的数量也在逐渐减少。是什么原因呢?因为人们捕获了狼，红羊没有了给它们造成生命威胁的动物，而自相残杀直至消亡，失去了危机。同时也失去了生机。可见，在生态环境中，没有磨难就不会有生态平衡的系统。\r\n      张海迪胸部以下失去知觉，但这些困难反而使她有了更为远大的目标，学习掌握了德语、日语、英语。她为什么能取得常人都不容易取得的成就呢?因为她在承受巨大挫折的同时，也将挫折化为了动力，让挫折成为自己的奴隶，再一次战胜了磨难。\r\n      人生如一块璞玉，这是上帝赐予你最大的财富，这块璞玉中有着酸甜苦辣，也许痛苦过后便是幸福，这是上帝给予你最大的机遇。只有自己努力来雕琢这块璞玉，才能使它成为完美无瑕的艺术品。同学们，当你遇到挫折、困难时，不妨想想，上帝是公平的，有时磨难也是一种财富。时光飞逝，一年时光就这样悄悄从指尖溜走, 或许除了自己没人能知道这一年我是怎么过来的，但现在的我所向披靡，战无不胜。因为我有勇气和信念。高二时是班里的末等生，高三毕业时在班中名列前茅，不是天才，没有过人天赋，就是努力和坚持，还有那星星点点微光，那个叫做信念的东西支撑着， 一直向前，度过最难过的时段，走过最泥泞的路。谁都有过不堪，都有过过去');
