/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50553
Source Host           : localhost:3306
Source Database       : microservicecloud

Target Server Type    : MYSQL
Target Server Version : 50553
File Encoding         : 65001

Date: 2018-05-14 23:56:48
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userName` varchar(64) DEFAULT NULL COMMENT '用户名',
  `password` varchar(128) DEFAULT NULL COMMENT '密码',
  `gender` varchar(10) DEFAULT NULL COMMENT '性别',
  `tel` varchar(32) DEFAULT NULL COMMENT '联系电话',
  `dbSource` varchar(32) DEFAULT '' COMMENT '数据来源',
  `removed` tinyint(4) DEFAULT '0' COMMENT '是否删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', 'ddf', '123456', '男', '18300001111', 'dbSource-01', '0');
INSERT INTO `user` VALUES ('2', 'chen', '00000000', '男', '15688889999', 'dbSource-01', '0');
INSERT INTO `user` VALUES ('3', 'hong', '99999999', '女', '021-55559999', 'dbSource-01', '0');
