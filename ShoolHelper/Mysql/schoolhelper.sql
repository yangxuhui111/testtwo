/*
Navicat MySQL Data Transfer

Source Server         : work
Source Server Version : 50506
Source Host           : localhost:3306
Source Database       : schoolhelper

Target Server Type    : MYSQL
Target Server Version : 50506
File Encoding         : 65001

Date: 2019-06-04 12:00:11
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `connection`
-- ----------------------------
DROP TABLE IF EXISTS `connection`;
CREATE TABLE `connection` (
  `connection_id` int(10) NOT NULL AUTO_INCREMENT,
  `poster_id` int(20) NOT NULL,
  `receiver_id` int(20) NOT NULL,
  `reward_id` int(10) NOT NULL,
  PRIMARY KEY (`connection_id`) USING BTREE,
  KEY `reward_id` (`reward_id`) USING BTREE,
  KEY `conn_poster` (`poster_id`) USING BTREE,
  KEY `conn_receiver` (`receiver_id`) USING BTREE,
  CONSTRAINT `conn_poster` FOREIGN KEY (`poster_id`) REFERENCES `user` (`user_id`),
  CONSTRAINT `conn_receiver` FOREIGN KEY (`receiver_id`) REFERENCES `user` (`user_id`),
  CONSTRAINT `conn_reward` FOREIGN KEY (`reward_id`) REFERENCES `reward` (`reward_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of connection
-- ----------------------------

-- ----------------------------
-- Table structure for `reward`
-- ----------------------------
DROP TABLE IF EXISTS `reward`;
CREATE TABLE `reward` (
  `reward_id` int(10) NOT NULL AUTO_INCREMENT,
  `poster_id` int(20) NOT NULL,
  `reward_content` varchar(255) NOT NULL,
  `reward_title` varchar(20) NOT NULL,
  `reward_money` double(10,2) NOT NULL,
  `reward_time` varchar(255) NOT NULL,
  `reward_deadline` varchar(255) NOT NULL,
  `reward_state` varchar(10) NOT NULL,
  `reward_image` varchar(50) DEFAULT NULL,
  `receiver_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`reward_id`) USING BTREE,
  UNIQUE KEY `reward_id` (`reward_id`) USING BTREE,
  KEY `reward_poster` (`poster_id`) USING BTREE,
  CONSTRAINT `reward_poster` FOREIGN KEY (`poster_id`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=58 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of reward
-- ----------------------------
INSERT INTO `reward` VALUES ('52', '1323', '1月2号，正定机场3点50分的飞机', '机场接人', '10.00', '2019年01月02日 02:18:12', '2019-01-04 02:18:01', '1', null, null);
INSERT INTO `reward` VALUES ('53', '1323', '西门圆通快递', '代取快递', '10.00', '2019年01月02日 02:18:47', '2019-01-03 02:18:41', '1', null, null);
INSERT INTO `reward` VALUES ('54', '1323', '东门京东快递', '代取快递', '5.00', '2019年01月02日 02:21:59', '2019-01-03 02:21:53', '1', null, null);
INSERT INTO `reward` VALUES ('55', '1324', '1月2号，软件学院404帮忙占一个前排座位', '帮忙占座', '5.00', '2019年01月02日 02:27:41', '2019-01-04 02:27:30', '1', null, null);
INSERT INTO `reward` VALUES ('56', '1324', '1月4号下午2点30分，石家庄站', '火车站接人', '5.00', '2019年01月02日 02:31:04', '2019-01-05 02:30:56', '1', null, null);
INSERT INTO `reward` VALUES ('57', '1325', '求收购英语四级材料', '收购英语四级材料', '10.00', '2019年01月02日 02:37:52', '2019-01-06 02:37:45', '1', null, null);

-- ----------------------------
-- Table structure for `school`
-- ----------------------------
DROP TABLE IF EXISTS `school`;
CREATE TABLE `school` (
  `school_id` int(10) NOT NULL,
  `school_name` varchar(20) NOT NULL,
  PRIMARY KEY (`school_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of school
-- ----------------------------
INSERT INTO `school` VALUES ('1', '河北师范大学');

-- ----------------------------
-- Table structure for `user`
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `user_id` int(20) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(25) NOT NULL,
  `user_password` varchar(25) NOT NULL,
  `school_id` int(20) NOT NULL,
  `user_student_num` varchar(20) NOT NULL,
  `user_phone` varchar(20) NOT NULL,
  `user_image` varchar(50) NOT NULL,
  `user_money` double(30,2) NOT NULL,
  `user_reputation_value` int(10) NOT NULL,
  `user_took_count` int(10) NOT NULL,
  `user_publish_count` int(10) NOT NULL,
  `user_identification` varchar(10) NOT NULL,
  `user_signature` varchar(50) DEFAULT '',
  `user_realname` varchar(25) DEFAULT NULL,
  `user_sex` varchar(25) DEFAULT NULL,
  PRIMARY KEY (`user_id`) USING BTREE,
  UNIQUE KEY `user_id` (`user_id`) USING BTREE,
  KEY `school_id` (`school_id`) USING BTREE,
  CONSTRAINT `user_ibfk_1` FOREIGN KEY (`school_id`) REFERENCES `school` (`school_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1326 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1323', '我是官方号', '123123', '1', '2016011111', '15232811399', 'images/geren.png', '975.00', '60', '0', '0', '未认证', '我是官方号哈哈哈哈哈', '尚一飞', '男');
INSERT INTO `user` VALUES ('1324', '软件吴彦祖', '123123', '1', '2016011112', '110', 'images/geren.png', '990.00', '60', '0', '0', '未认证', '软件学院吴彦祖哈哈哈', '张海波', '男');
INSERT INTO `user` VALUES ('1325', '小魔鬼', '123123', '1', '2016011113', '120', 'images/geren.png', '990.00', '60', '0', '0', '未认证', '我是小魔鬼哈哈哈', '小红', '男');
