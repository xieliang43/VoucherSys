/*
MySQL Data Transfer
Source Host: localhost
Source Database: voucherdb
Target Host: localhost
Target Database: voucherdb
Date: 2012/7/30 23:01:07
*/

SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for t_advice
-- ----------------------------
DROP TABLE IF EXISTS `t_advice`;
CREATE TABLE `t_advice` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `MSG` varchar(2048) DEFAULT NULL,
  `CREATE_DATE` date DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `IDX_ADVICE` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_distance
-- ----------------------------
DROP TABLE IF EXISTS `t_distance`;
CREATE TABLE `t_distance` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `NAME` varchar(32) DEFAULT NULL,
  `ENABLED` smallint(6) DEFAULT NULL,
  `CREATE_DATE` date DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `IDX_DISTANCE` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_merchant
-- ----------------------------
DROP TABLE IF EXISTS `t_merchant`;
CREATE TABLE `t_merchant` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `PHONE_NO` varchar(20) DEFAULT NULL,
  `PASSWORD` varchar(100) DEFAULT NULL,
  `NAME` varchar(50) DEFAULT NULL,
  `EMAIL` varchar(50) DEFAULT NULL,
  `SEX` varchar(2) DEFAULT NULL,
  `TEL_NO` varchar(50) DEFAULT NULL,
  `QQ_NO` varchar(50) DEFAULT NULL,
  `CITY_ID` int(11) DEFAULT NULL,
  `CREATE_DATE` date DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_MERCHANT_CITY` (`CITY_ID`),
  KEY `IDX_MERCHANT_ID` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_position
-- ----------------------------
DROP TABLE IF EXISTS `t_position`;
CREATE TABLE `t_position` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `SHOP_ID` int(11) DEFAULT NULL,
  `LONGITUDE` double DEFAULT NULL,
  `LATITUDE` double DEFAULT NULL,
  `CREATE_DATE` date DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_SHOP_POSITION` (`SHOP_ID`),
  KEY `IDX_POSITION_ID` (`ID`),
  CONSTRAINT `FK_SHOP_POSITION` FOREIGN KEY (`SHOP_ID`) REFERENCES `t_shop` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=74 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_publish
-- ----------------------------
DROP TABLE IF EXISTS `t_publish`;
CREATE TABLE `t_publish` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `PHONE_NO` varchar(20) DEFAULT NULL,
  `MSG` varchar(2048) DEFAULT NULL,
  `CREATE_DATE` date DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `IDX_PUBLISH` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_region
-- ----------------------------
DROP TABLE IF EXISTS `t_region`;
CREATE TABLE `t_region` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `AGENCYID` int(11) NOT NULL,
  `NAME` varchar(50) DEFAULT NULL,
  `REGION_PREFIX` varchar(8) DEFAULT NULL,
  `TYPE` int(11) NOT NULL,
  `PARENTID` int(11) DEFAULT NULL,
  `CREATE_DATE` date DEFAULT NULL,
  `ENABLED` smallint(6) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_PARENT` (`PARENTID`),
  KEY `IDX_REGION_ID` (`ID`),
  CONSTRAINT `FK_PARENT` FOREIGN KEY (`PARENTID`) REFERENCES `t_region` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=89 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_shop
-- ----------------------------
DROP TABLE IF EXISTS `t_shop`;
CREATE TABLE `t_shop` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `SHOP_TYPE_ID` int(11) DEFAULT NULL,
  `SHOP_NAME` varchar(255) DEFAULT NULL,
  `SHOP_ADDRESS` varchar(255) DEFAULT NULL,
  `DESCRIPTION` varchar(255) DEFAULT NULL,
  `IMAGE` varchar(100) DEFAULT NULL,
  `CREATE_DATE` date DEFAULT NULL,
  `MERCHANT_ID` int(11) DEFAULT NULL,
  `CITY_ID` int(11) DEFAULT NULL,
  `AREA_ID` int(11) DEFAULT NULL,
  `TEL_NO` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_SHOP_TYPE_ID` (`SHOP_TYPE_ID`),
  KEY `IDX_SHOP_ID` (`ID`),
  KEY `FK_SHOP_CITY` (`CITY_ID`),
  KEY `FK_SHOP_AREA` (`AREA_ID`),
  KEY `FK_SHOP_MERCHANT` (`MERCHANT_ID`),
  CONSTRAINT `FK_SHOP_AREA` FOREIGN KEY (`AREA_ID`) REFERENCES `t_region` (`ID`),
  CONSTRAINT `FK_SHOP_CITY` FOREIGN KEY (`CITY_ID`) REFERENCES `t_region` (`ID`),
  CONSTRAINT `FK_SHOP_MERCHANT` FOREIGN KEY (`MERCHANT_ID`) REFERENCES `t_sys_user` (`ID`),
  CONSTRAINT `FK_SHOP_TYPE_ID` FOREIGN KEY (`SHOP_TYPE_ID`) REFERENCES `t_shop_type` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=61 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_shop_type
-- ----------------------------
DROP TABLE IF EXISTS `t_shop_type`;
CREATE TABLE `t_shop_type` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `NAME` varchar(100) DEFAULT NULL,
  `DESCRIPTION` varchar(255) DEFAULT NULL,
  `CREATE_DATE` date DEFAULT NULL,
  `ENABLED` smallint(6) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `IDX_SHOP_TYPE_ID` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_sys_field
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_field`;
CREATE TABLE `t_sys_field` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `FIELD` varchar(64) DEFAULT NULL,
  `FIELD_NAME` varchar(128) DEFAULT NULL,
  `VALUE_FIELD` varchar(128) DEFAULT NULL,
  `DISPLAY_FIELD` varchar(128) DEFAULT NULL,
  `ENABLED` smallint(6) DEFAULT NULL,
  `SORT_ORDER` smallint(6) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_sys_module
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_module`;
CREATE TABLE `t_sys_module` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `MODULE_NAME` varchar(64) NOT NULL,
  `MODULE_URL` varchar(64) DEFAULT NULL,
  `PARENT_ID` int(11) DEFAULT NULL,
  `LEAF` smallint(6) DEFAULT NULL,
  `EXPANDED` smallint(6) DEFAULT NULL,
  `DISPLAY_INDEX` smallint(6) DEFAULT NULL,
  `IS_DISPLAY` smallint(6) DEFAULT NULL,
  `EN_MODULE_NAME` varchar(64) DEFAULT NULL,
  `ICON_CSS` varchar(128) DEFAULT NULL,
  `INFORMATION` varchar(128) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_sys_role
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_role`;
CREATE TABLE `t_sys_role` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `ROLE_NAME` varchar(64) DEFAULT NULL,
  `ROLE_DESC` varchar(128) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_sys_role_module
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_role_module`;
CREATE TABLE `t_sys_role_module` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `ROLE_ID` int(11) NOT NULL,
  `MODULE_ID` int(11) NOT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_RM_ROLE` (`ROLE_ID`),
  KEY `FK_RM_MODULE` (`MODULE_ID`),
  CONSTRAINT `FK_RM_MODULE` FOREIGN KEY (`MODULE_ID`) REFERENCES `t_sys_module` (`ID`),
  CONSTRAINT `FK_RM_ROLE` FOREIGN KEY (`ROLE_ID`) REFERENCES `t_sys_role` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=75 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_sys_user
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_user`;
CREATE TABLE `t_sys_user` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `ACCOUNT` varchar(64) NOT NULL,
  `PASSWORD` varchar(128) NOT NULL,
  `REAL_NAME` varchar(64) DEFAULT NULL,
  `SEX` smallint(6) DEFAULT NULL,
  `EMAIL` varchar(64) DEFAULT NULL,
  `MOBILE` varchar(32) DEFAULT NULL,
  `OFFICE_PHONE` varchar(32) DEFAULT NULL,
  `ERROR_COUNT` smallint(6) DEFAULT '0',
  `LAST_LOGIN_TIME` date DEFAULT NULL,
  `LAST_LOGIN_IP` varchar(32) DEFAULT NULL,
  `REMARK` varchar(128) DEFAULT NULL,
  `CREATE_DATE` date DEFAULT NULL,
  `QQ_NO` varchar(32) DEFAULT NULL,
  `CITY_ID` int(11) DEFAULT NULL,
  `EXPENSE_PASSWORD` varchar(128) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_user_role`;
CREATE TABLE `t_sys_user_role` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `USER_ID` int(11) NOT NULL,
  `ROLE_ID` int(11) NOT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_UR_USER` (`USER_ID`),
  KEY `FK_UM_ROLE` (`ROLE_ID`),
  CONSTRAINT `FK_UM_ROLE` FOREIGN KEY (`ROLE_ID`) REFERENCES `t_sys_role` (`ID`),
  CONSTRAINT `FK_UR_USER` FOREIGN KEY (`USER_ID`) REFERENCES `t_sys_user` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_user
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `PHONE_NO` varchar(20) DEFAULT NULL,
  `PASSWORD` varchar(100) DEFAULT NULL,
  `NAME` varchar(50) DEFAULT NULL,
  `EMAIL` varchar(50) DEFAULT NULL,
  `SEX` varchar(2) DEFAULT NULL,
  `CITY_ID` int(11) DEFAULT NULL,
  `CREATE_DATE` date DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_CITY` (`CITY_ID`),
  KEY `IDX_USER_ID` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_user_voucher
-- ----------------------------
DROP TABLE IF EXISTS `t_user_voucher`;
CREATE TABLE `t_user_voucher` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `USER_ID` int(11) DEFAULT NULL,
  `VCH_INST_ID` int(11) DEFAULT NULL,
  `CREATE_DATE` date DEFAULT NULL,
  `IS_USED` smallint(6) DEFAULT NULL,
  `IS_ACTIVE` smallint(6) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_USER_VOUCHER` (`USER_ID`),
  KEY `FK_VCH_INST` (`VCH_INST_ID`),
  KEY `IDX_USER_VOUCHER` (`ID`),
  CONSTRAINT `FK_USER_VOUCHER` FOREIGN KEY (`USER_ID`) REFERENCES `t_user` (`ID`),
  CONSTRAINT `FK_VCH_INST` FOREIGN KEY (`VCH_INST_ID`) REFERENCES `t_voucher_instance` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=38 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_voucher
-- ----------------------------
DROP TABLE IF EXISTS `t_voucher`;
CREATE TABLE `t_voucher` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `NAME` varchar(255) DEFAULT NULL,
  `PRICE` double DEFAULT NULL,
  `QUANTITY` int(11) DEFAULT NULL,
  `START_DATE` date DEFAULT NULL,
  `END_DATE` date DEFAULT NULL,
  `USE_RULE` varchar(255) DEFAULT NULL,
  `IMAGE` varchar(255) DEFAULT NULL,
  `DESCRIPTION` varchar(255) DEFAULT NULL,
  `CREATE_DATE` date DEFAULT NULL,
  `ENABLED` smallint(6) DEFAULT NULL,
  `SHOP_ID` int(11) DEFAULT NULL,
  `VCH_KEY` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `IDX_VOUCHER_ID` (`ID`),
  KEY `FK_SHOP_VOUCHER` (`SHOP_ID`),
  CONSTRAINT `FK_SHOP_VOUCHER` FOREIGN KEY (`SHOP_ID`) REFERENCES `t_shop` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_voucher_instance
-- ----------------------------
DROP TABLE IF EXISTS `t_voucher_instance`;
CREATE TABLE `t_voucher_instance` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `VOUCHER_ID` int(11) DEFAULT NULL,
  `IS_BOUGHT` smallint(6) DEFAULT NULL,
  `V_KEY` varchar(100) DEFAULT NULL,
  `VERSION` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_VOUCHER` (`VOUCHER_ID`),
  KEY `IDX_VOUCHER_INSTANCE_ID` (`ID`),
  CONSTRAINT `FK_VOUCHER` FOREIGN KEY (`VOUCHER_ID`) REFERENCES `t_voucher` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=72 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records 
-- ----------------------------
INSERT INTO `t_distance` VALUES ('1', '500', '1', '2012-07-21');
INSERT INTO `t_distance` VALUES ('2', '1000', '1', '2012-07-21');
INSERT INTO `t_distance` VALUES ('3', '2000', '1', '2012-07-21');
INSERT INTO `t_distance` VALUES ('4', '3000', '1', '2012-07-21');
INSERT INTO `t_position` VALUES ('1', '4', '104.071966', '30.5504596', '2012-07-22');
INSERT INTO `t_position` VALUES ('6', '3', '104.1259439', '30.6005214', '2012-07-22');
INSERT INTO `t_position` VALUES ('7', '1', '104.0658517', '30.6575136', '2012-07-22');
INSERT INTO `t_position` VALUES ('9', '6', '104.071966', '30.5504596', null);
INSERT INTO `t_position` VALUES ('10', '7', '104.071966', '30.5504596', null);
INSERT INTO `t_position` VALUES ('11', '8', '104.071966', '30.5504596', null);
INSERT INTO `t_position` VALUES ('12', '9', '104.071966', '30.5504596', null);
INSERT INTO `t_position` VALUES ('13', '10', '104.071966', '30.5504596', null);
INSERT INTO `t_position` VALUES ('14', '11', '104.071966', '30.5504596', null);
INSERT INTO `t_position` VALUES ('15', '12', '104.071966', '30.5504596', null);
INSERT INTO `t_position` VALUES ('16', '13', '104.071966', '30.5504596', null);
INSERT INTO `t_position` VALUES ('17', '15', '104.071966', '30.5504596', null);
INSERT INTO `t_position` VALUES ('18', '16', '104.071966', '30.5504596', null);
INSERT INTO `t_position` VALUES ('19', '17', '104.071966', '30.5504596', null);
INSERT INTO `t_position` VALUES ('20', '18', '104.071966', '30.5504596', null);
INSERT INTO `t_position` VALUES ('21', '19', '104.071966', '30.5504596', null);
INSERT INTO `t_position` VALUES ('22', '20', '104.071966', '30.5504596', null);
INSERT INTO `t_position` VALUES ('23', '21', '104.071966', '30.5504596', null);
INSERT INTO `t_position` VALUES ('24', '22', '104.071966', '30.5504596', null);
INSERT INTO `t_position` VALUES ('25', '23', '104.071966', '30.5504596', null);
INSERT INTO `t_position` VALUES ('26', '24', '104.071966', '30.5504596', null);
INSERT INTO `t_position` VALUES ('27', '25', '104.071966', '30.5504596', null);
INSERT INTO `t_position` VALUES ('28', '26', '104.071966', '30.5504596', null);
INSERT INTO `t_position` VALUES ('29', '30', '104.071966', '30.5504596', null);
INSERT INTO `t_position` VALUES ('30', '31', '104.071966', '30.5504596', null);
INSERT INTO `t_position` VALUES ('31', '32', '104.071966', '30.5504596', null);
INSERT INTO `t_position` VALUES ('32', '33', '104.071966', '30.5504596', null);
INSERT INTO `t_position` VALUES ('33', '34', '104.071966', '30.5504596', null);
INSERT INTO `t_position` VALUES ('34', '35', '104.071966', '30.5504596', null);
INSERT INTO `t_position` VALUES ('35', '36', '104.071966', '30.5504596', null);
INSERT INTO `t_position` VALUES ('36', '37', '104.071966', '30.5504596', null);
INSERT INTO `t_position` VALUES ('37', '38', '104.071966', '30.5504596', null);
INSERT INTO `t_position` VALUES ('38', '39', '104.071966', '30.5504596', null);
INSERT INTO `t_position` VALUES ('39', '40', '104.071966', '30.5504596', null);
INSERT INTO `t_position` VALUES ('40', '41', '104.071966', '30.5504596', null);
INSERT INTO `t_position` VALUES ('41', '42', '104.071966', '30.5504596', null);
INSERT INTO `t_position` VALUES ('42', '43', '104.071966', '30.5504596', null);
INSERT INTO `t_position` VALUES ('43', '44', '104.071966', '30.5504596', null);
INSERT INTO `t_position` VALUES ('44', '45', '104.071966', '30.5504596', null);
INSERT INTO `t_position` VALUES ('45', '46', '104.071966', '30.5504596', null);
INSERT INTO `t_position` VALUES ('46', '47', '104.071966', '30.5504596', null);
INSERT INTO `t_position` VALUES ('47', '48', '104.071966', '30.5504596', null);
INSERT INTO `t_position` VALUES ('48', '49', '104.071966', '30.5504596', null);
INSERT INTO `t_position` VALUES ('49', '50', '104.071966', '30.5504596', null);
INSERT INTO `t_position` VALUES ('50', '51', '104.071966', '30.5504596', null);
INSERT INTO `t_position` VALUES ('51', '52', '104.071966', '30.5504596', null);
INSERT INTO `t_position` VALUES ('52', '53', '104.071966', '30.5504596', null);
INSERT INTO `t_position` VALUES ('73', '5', '104.0658517', '30.6575136', '2012-07-30');
INSERT INTO `t_publish` VALUES ('2', '23456754', 'illl', '2012-07-20');
INSERT INTO `t_region` VALUES ('1', '0', '中国', 'CHN', '0', null, '2012-07-15', '1');
INSERT INTO `t_region` VALUES ('2', '0', '北京', 'BJ', '1', '1', '2012-07-15', '1');
INSERT INTO `t_region` VALUES ('3', '0', '安徽', 'AH', '1', '1', '2012-07-15', '1');
INSERT INTO `t_region` VALUES ('4', '0', '福建', 'FJ', '1', '1', '2012-07-15', '1');
INSERT INTO `t_region` VALUES ('5', '0', '甘肃', 'GS', '1', '1', '2012-07-15', '1');
INSERT INTO `t_region` VALUES ('6', '0', '广东', 'GD', '1', '1', '2012-07-15', '1');
INSERT INTO `t_region` VALUES ('7', '0', '广西', 'GX', '1', '1', '2012-07-15', '1');
INSERT INTO `t_region` VALUES ('8', '0', '贵州', 'GZ', '1', '1', '2012-07-15', '1');
INSERT INTO `t_region` VALUES ('9', '0', '海南', 'HN', '1', '1', '2012-07-15', '1');
INSERT INTO `t_region` VALUES ('10', '0', '河北', 'HB', '1', '1', '2012-07-15', '1');
INSERT INTO `t_region` VALUES ('11', '0', '河南', 'HN', '1', '1', '2012-07-15', '1');
INSERT INTO `t_region` VALUES ('12', '0', '黑龙江', 'HLJ', '1', '1', '2012-07-15', '1');
INSERT INTO `t_region` VALUES ('13', '0', '湖北', 'HB', '1', '1', '2012-07-15', '1');
INSERT INTO `t_region` VALUES ('14', '0', '湖南', 'HN', '1', '1', '2012-07-15', '1');
INSERT INTO `t_region` VALUES ('15', '0', '吉林', 'JL', '1', '1', '2012-07-15', '1');
INSERT INTO `t_region` VALUES ('16', '0', '江苏', 'JS', '1', '1', '2012-07-15', '1');
INSERT INTO `t_region` VALUES ('17', '0', '江西', 'JX', '1', '1', '2012-07-15', '1');
INSERT INTO `t_region` VALUES ('18', '0', '辽宁', 'LL', '1', '1', '2012-07-15', '1');
INSERT INTO `t_region` VALUES ('19', '0', '内蒙古', 'NMG', '1', '1', '2012-07-15', '1');
INSERT INTO `t_region` VALUES ('20', '0', '宁夏', 'NX', '1', '1', '2012-07-15', '1');
INSERT INTO `t_region` VALUES ('21', '0', '青海', 'QH', '1', '1', '2012-07-15', '1');
INSERT INTO `t_region` VALUES ('22', '0', '山东', 'SD', '1', '1', '2012-07-15', '1');
INSERT INTO `t_region` VALUES ('23', '0', '山西', 'SX', '1', '1', '2012-07-15', '1');
INSERT INTO `t_region` VALUES ('24', '0', '陕西', 'SX', '1', '1', '2012-07-15', '1');
INSERT INTO `t_region` VALUES ('25', '0', '上海', 'SH', '1', '1', '2012-07-15', '1');
INSERT INTO `t_region` VALUES ('26', '0', '四川', 'SC', '1', '1', '2012-07-15', '1');
INSERT INTO `t_region` VALUES ('27', '0', '天津', 'TJ', '1', '1', '2012-07-15', '1');
INSERT INTO `t_region` VALUES ('28', '0', '西藏', 'XZ', '1', '1', '2012-07-15', '1');
INSERT INTO `t_region` VALUES ('29', '0', '新疆', 'XJ', '1', '1', '2012-07-15', '1');
INSERT INTO `t_region` VALUES ('30', '0', '云南', 'YN', '1', '1', '2012-07-15', '1');
INSERT INTO `t_region` VALUES ('31', '0', '浙江', 'ZJ', '1', '1', '2012-07-15', '1');
INSERT INTO `t_region` VALUES ('32', '0', '重庆', 'CQ', '1', '1', '2012-07-15', '1');
INSERT INTO `t_region` VALUES ('33', '0', '香港', 'HK', '1', '1', '2012-07-15', '1');
INSERT INTO `t_region` VALUES ('34', '0', '澳门', 'AM', '1', '1', '2012-07-15', '1');
INSERT INTO `t_region` VALUES ('35', '0', '台湾', 'TW', '1', '1', '2012-07-15', '1');
INSERT INTO `t_region` VALUES ('52', '0', '北京', 'B', '2', '2', '2012-07-15', '1');
INSERT INTO `t_region` VALUES ('53', '0', '合肥', 'H', '2', '3', '2012-07-15', '1');
INSERT INTO `t_region` VALUES ('54', '0', '福州', 'F', '2', '4', '2012-07-15', '1');
INSERT INTO `t_region` VALUES ('55', '0', '兰州', 'L', '2', '5', '2012-07-15', '1');
INSERT INTO `t_region` VALUES ('56', '0', '广州', 'G', '2', '6', '2012-07-15', '1');
INSERT INTO `t_region` VALUES ('57', '0', '南宁', 'N', '2', '7', '2012-07-15', '1');
INSERT INTO `t_region` VALUES ('58', '0', '贵阳', 'G', '2', '8', '2012-07-15', '1');
INSERT INTO `t_region` VALUES ('59', '0', '海口', 'H', '2', '9', '2012-07-15', '1');
INSERT INTO `t_region` VALUES ('60', '0', '石家庄', 'S', '2', '10', '2012-07-15', '1');
INSERT INTO `t_region` VALUES ('61', '0', '郑州', 'Z', '2', '11', '2012-07-15', '1');
INSERT INTO `t_region` VALUES ('62', '0', '哈尔滨', 'H', '2', '12', '2012-07-15', '1');
INSERT INTO `t_region` VALUES ('63', '0', '武汉', 'W', '2', '13', '2012-07-15', '1');
INSERT INTO `t_region` VALUES ('64', '0', '长沙', 'C', '2', '14', '2012-07-15', '1');
INSERT INTO `t_region` VALUES ('65', '0', '长春', 'C', '2', '15', '2012-07-15', '1');
INSERT INTO `t_region` VALUES ('66', '0', '南京', 'N', '2', '16', '2012-07-15', '1');
INSERT INTO `t_region` VALUES ('67', '0', '南昌', 'N', '2', '17', '2012-07-15', '1');
INSERT INTO `t_region` VALUES ('68', '0', '沈阳', 'S', '2', '18', '2012-07-15', '1');
INSERT INTO `t_region` VALUES ('69', '0', '呼和浩特', 'H', '2', '19', '2012-07-15', '1');
INSERT INTO `t_region` VALUES ('70', '0', '银川', 'Y', '2', '20', '2012-07-15', '1');
INSERT INTO `t_region` VALUES ('71', '0', '西宁', 'X', '2', '21', '2012-07-15', '1');
INSERT INTO `t_region` VALUES ('72', '0', '济南', 'J', '2', '22', '2012-07-15', '1');
INSERT INTO `t_region` VALUES ('73', '0', '太原', 'T', '2', '23', '2012-07-15', '1');
INSERT INTO `t_region` VALUES ('74', '0', '西安', 'X', '2', '24', '2012-07-15', '1');
INSERT INTO `t_region` VALUES ('75', '0', '上海', 'S', '2', '25', '2012-07-15', '1');
INSERT INTO `t_region` VALUES ('76', '0', '成都', 'C', '2', '26', '2012-07-15', '1');
INSERT INTO `t_region` VALUES ('77', '0', '天津', 'T', '2', '27', '2012-07-15', '1');
INSERT INTO `t_region` VALUES ('78', '0', '拉萨', 'L', '2', '28', '2012-07-15', '1');
INSERT INTO `t_region` VALUES ('79', '0', '乌鲁木齐', 'W', '2', '29', '2012-07-15', '1');
INSERT INTO `t_region` VALUES ('80', '0', '昆明', 'K', '2', '30', '2012-07-15', '1');
INSERT INTO `t_region` VALUES ('81', '0', '杭州', 'H', '2', '31', '2012-07-15', '1');
INSERT INTO `t_region` VALUES ('82', '0', '重庆', 'C', '2', '32', '2012-07-15', '1');
INSERT INTO `t_region` VALUES ('83', '0', '香港', 'X', '2', '33', '2012-07-15', '1');
INSERT INTO `t_region` VALUES ('84', '0', '澳门', 'A', '2', '34', '2012-07-15', '1');
INSERT INTO `t_region` VALUES ('85', '0', '台北', 'T', '2', '35', '2012-07-15', '1');
INSERT INTO `t_region` VALUES ('86', '0', '高新区', 'GX', '3', '76', '2012-07-21', '1');
INSERT INTO `t_region` VALUES ('87', '0', '武侯区', 'WH', '3', '76', '2012-07-21', '1');
INSERT INTO `t_region` VALUES ('88', '0', '浦东', 'PD', '3', '75', '2012-07-27', '1');
INSERT INTO `t_shop` VALUES ('1', '2', 'AAA', '成都市天府广场', 'AAAbbbb', 'new_iphone.jpg', '2012-07-13', '3', '76', '87', '7654321');
INSERT INTO `t_shop` VALUES ('3', '1', 'bbb', '成都市锦江区静宁路12号', 'BBB', 'new_iphone.jpg', '2012-07-21', '3', '76', '87', '7654321');
INSERT INTO `t_shop` VALUES ('4', '1', 'ccccc', '成都市高新区天华路', 'CCCC', 'new_iphone.jpg', '2012-07-22', '3', '76', '86', '7654321');
INSERT INTO `t_shop` VALUES ('5', '2', 'AAA', '天府广场', 'AAAbbbb', 'new_iphone.jpg', '2012-07-13', '3', '76', '87', '23456789');
INSERT INTO `t_shop` VALUES ('6', '1', 'bbb', '成都市锦江区静宁路12号', 'BBB', 'new_iphone.jpg', '2012-07-21', '3', '76', '87', '7654321');
INSERT INTO `t_shop` VALUES ('7', '1', 'ccccc', '成都市高新区天华路', 'CCCC', 'new_iphone.jpg', '2012-07-22', '3', '76', '86', '7654321');
INSERT INTO `t_shop` VALUES ('8', '2', 'AAA', '成都市天府广场', 'AAAbbbb', 'new_iphone.jpg', '2012-07-13', '3', '76', '87', '7654321');
INSERT INTO `t_shop` VALUES ('9', '1', 'bbb', '成都市锦江区静宁路12号', 'BBB', 'new_iphone.jpg', '2012-07-21', '3', '76', '87', '7654321');
INSERT INTO `t_shop` VALUES ('10', '1', 'ccccc', '成都市高新区天华路', 'CCCC', 'new_iphone.jpg', '2012-07-22', '3', '76', '86', '7654321');
INSERT INTO `t_shop` VALUES ('11', '2', 'AAA', '成都市天府广场', 'AAAbbbb', 'new_iphone.jpg', '2012-07-13', '3', '76', '87', '7654321');
INSERT INTO `t_shop` VALUES ('12', '1', 'bbb', '成都市锦江区静宁路12号', 'BBB', 'new_iphone.jpg', '2012-07-21', '3', '76', '87', '7654321');
INSERT INTO `t_shop` VALUES ('13', '1', 'ccccc', '成都市高新区天华路', 'CCCC', 'new_iphone.jpg', '2012-07-22', '3', '76', '86', '7654321');
INSERT INTO `t_shop` VALUES ('15', '2', 'AAA', '成都市天府广场', 'AAAbbbb', 'new_iphone.jpg', '2012-07-13', '3', '76', '87', '7654321');
INSERT INTO `t_shop` VALUES ('16', '1', 'bbb', '成都市锦江区静宁路12号', 'BBB', 'new_iphone.jpg', '2012-07-21', '3', '76', '87', '7654321');
INSERT INTO `t_shop` VALUES ('17', '1', 'ccccc', '成都市高新区天华路', 'CCCC', 'new_iphone.jpg', '2012-07-22', '3', '76', '86', '7654321');
INSERT INTO `t_shop` VALUES ('18', '2', 'AAA', '成都市天府广场', 'AAAbbbb', 'new_iphone.jpg', '2012-07-13', '3', '76', '87', '7654321');
INSERT INTO `t_shop` VALUES ('19', '1', 'bbb', '成都市锦江区静宁路12号', 'BBB', 'new_iphone.jpg', '2012-07-21', '3', '76', '87', '7654321');
INSERT INTO `t_shop` VALUES ('20', '1', 'ccccc', '成都市高新区天华路', 'CCCC', 'new_iphone.jpg', '2012-07-22', '3', '76', '86', '7654321');
INSERT INTO `t_shop` VALUES ('21', '2', 'AAA', '成都市天府广场', 'AAAbbbb', 'new_iphone.jpg', '2012-07-13', '3', '76', '87', '7654321');
INSERT INTO `t_shop` VALUES ('22', '1', 'bbb', '成都市锦江区静宁路12号', 'BBB', 'new_iphone.jpg', '2012-07-21', '3', '76', '87', '7654321');
INSERT INTO `t_shop` VALUES ('23', '1', 'ccccc', '成都市高新区天华路', 'CCCC', 'new_iphone.jpg', '2012-07-22', '3', '76', '86', '7654321');
INSERT INTO `t_shop` VALUES ('24', '2', 'AAA', '成都市天府广场', 'AAAbbbb', 'new_iphone.jpg', '2012-07-13', '3', '76', '87', '7654321');
INSERT INTO `t_shop` VALUES ('25', '1', 'bbb', '成都市锦江区静宁路12号', 'BBB', 'new_iphone.jpg', '2012-07-21', '3', '76', '87', '7654321');
INSERT INTO `t_shop` VALUES ('26', '1', 'ccccc', '成都市高新区天华路', 'CCCC', 'new_iphone.jpg', '2012-07-22', '3', '76', '86', '7654321');
INSERT INTO `t_shop` VALUES ('30', '2', 'AAA', '成都市天府广场', 'AAAbbbb', 'new_iphone.jpg', '2012-07-13', '3', '76', '87', '7654321');
INSERT INTO `t_shop` VALUES ('31', '1', 'bbb', '成都市锦江区静宁路12号', 'BBB', 'new_iphone.jpg', '2012-07-21', '3', '76', '87', '7654321');
INSERT INTO `t_shop` VALUES ('32', '1', 'ccccc', '成都市高新区天华路', 'CCCC', 'new_iphone.jpg', '2012-07-22', '3', '76', '86', '7654321');
INSERT INTO `t_shop` VALUES ('33', '2', 'AAA', '成都市天府广场', 'AAAbbbb', 'new_iphone.jpg', '2012-07-13', '3', '76', '87', '7654321');
INSERT INTO `t_shop` VALUES ('34', '1', 'bbb', '成都市锦江区静宁路12号', 'BBB', 'new_iphone.jpg', '2012-07-21', '3', '76', '87', '7654321');
INSERT INTO `t_shop` VALUES ('35', '1', 'ccccc', '成都市高新区天华路', 'CCCC', 'new_iphone.jpg', '2012-07-22', '3', '76', '86', '7654321');
INSERT INTO `t_shop` VALUES ('36', '2', 'AAA', '成都市天府广场', 'AAAbbbb', 'new_iphone.jpg', '2012-07-13', '3', '76', '87', '7654321');
INSERT INTO `t_shop` VALUES ('37', '1', 'bbb', '成都市锦江区静宁路12号', 'BBB', 'new_iphone.jpg', '2012-07-21', '3', '76', '87', '7654321');
INSERT INTO `t_shop` VALUES ('38', '1', 'ccccc', '成都市高新区天华路', 'CCCC', 'new_iphone.jpg', '2012-07-22', '3', '76', '86', '7654321');
INSERT INTO `t_shop` VALUES ('39', '2', 'AAA', '成都市天府广场', 'AAAbbbb', 'new_iphone.jpg', '2012-07-13', '3', '76', '87', '7654321');
INSERT INTO `t_shop` VALUES ('40', '1', 'bbb', '成都市锦江区静宁路12号', 'BBB', 'new_iphone.jpg', '2012-07-21', '3', '76', '87', '7654321');
INSERT INTO `t_shop` VALUES ('41', '1', 'ccccc', '成都市高新区天华路', 'CCCC', 'new_iphone.jpg', '2012-07-22', '3', '76', '86', '7654321');
INSERT INTO `t_shop` VALUES ('42', '2', 'AAA', '成都市天府广场', 'AAAbbbb', 'new_iphone.jpg', '2012-07-13', '3', '76', '87', '7654321');
INSERT INTO `t_shop` VALUES ('43', '1', 'bbb', '成都市锦江区静宁路12号', 'BBB', 'new_iphone.jpg', '2012-07-21', '3', '76', '87', '7654321');
INSERT INTO `t_shop` VALUES ('44', '1', 'ccccc', '成都市高新区天华路', 'CCCC', 'new_iphone.jpg', '2012-07-22', '3', '76', '86', '7654321');
INSERT INTO `t_shop` VALUES ('45', '2', 'AAA', '成都市天府广场', 'AAAbbbb', 'new_iphone.jpg', '2012-07-13', '3', '76', '87', '7654321');
INSERT INTO `t_shop` VALUES ('46', '1', 'bbb', '成都市锦江区静宁路12号', 'BBB', 'new_iphone.jpg', '2012-07-21', '3', '76', '87', '7654321');
INSERT INTO `t_shop` VALUES ('47', '1', 'ccccc', '成都市高新区天华路', 'CCCC', 'new_iphone.jpg', '2012-07-22', '3', '76', '86', '7654321');
INSERT INTO `t_shop` VALUES ('48', '2', 'AAA', '成都市天府广场', 'AAAbbbb', 'new_iphone.jpg', '2012-07-13', '3', '76', '87', '7654321');
INSERT INTO `t_shop` VALUES ('49', '1', 'bbb', '成都市锦江区静宁路12号', 'BBB', 'new_iphone.jpg', '2012-07-21', '3', '76', '87', '7654321');
INSERT INTO `t_shop` VALUES ('50', '1', 'ccccc', '成都市高新区天华路', 'CCCC', 'new_iphone.jpg', '2012-07-22', '3', '76', '86', '7654321');
INSERT INTO `t_shop` VALUES ('51', '2', 'AAA', '成都市天府广场', 'AAAbbbb', 'new_iphone.jpg', '2012-07-13', '3', '76', '87', '7654321');
INSERT INTO `t_shop` VALUES ('52', '1', 'bbb', '成都市锦江区静宁路12号', 'BBB', 'new_iphone.jpg', '2012-07-21', '3', '76', '87', '7654321');
INSERT INTO `t_shop` VALUES ('53', '1', 'ccccc', '成都市高新区天华路', 'CCCC', 'new_iphone.jpg', '2012-07-22', '3', '76', '86', '7654321');
INSERT INTO `t_shop_type` VALUES ('1', '休闲娱乐', '娱乐，玩耍high翻天', '2012-07-15', '1');
INSERT INTO `t_shop_type` VALUES ('2', '电影', '抢购开始啦，电影票', '2012-07-15', '1');
INSERT INTO `t_shop_type` VALUES ('3', '美食', '吃货天下', '2012-07-26', '1');
INSERT INTO `t_shop_type` VALUES ('4', '生活服务', '生活服务', '2012-07-26', '1');
INSERT INTO `t_shop_type` VALUES ('5', '购物', '购物者好去处', '2012-07-26', '1');
INSERT INTO `t_shop_type` VALUES ('6', '酒店旅游', '酒店旅游', '2012-07-26', '1');
INSERT INTO `t_sys_field` VALUES ('1', 'sex', '性别', '0', '男', '1', '1');
INSERT INTO `t_sys_field` VALUES ('2', 'sex', '性别', '1', '女', '1', '2');
INSERT INTO `t_sys_field` VALUES ('3', 'sex', '性别', '2', '其他', '1', '3');
INSERT INTO `t_sys_field` VALUES ('4', 'sex', '性别', '3', '保密', '0', '4');
INSERT INTO `t_sys_field` VALUES ('5', 'pagesize', '每页显示条数', '10', '10条/页', '1', '1');
INSERT INTO `t_sys_field` VALUES ('6', 'pagesize', '每页显示条数', '20', '20条/页', '1', '2');
INSERT INTO `t_sys_field` VALUES ('7', 'pagesize', '每页显示条数', '30', '30条/页', '1', '3');
INSERT INTO `t_sys_field` VALUES ('8', 'pagesize', '每页显示条数', '50', '50条/页', '1', '4');
INSERT INTO `t_sys_field` VALUES ('9', 'theme', '风格', 'xtheme-blue.css', '经典蓝色', '1', '1');
INSERT INTO `t_sys_field` VALUES ('10', 'theme', '风格', 'xtheme-gray.css', '简约灰色', '1', '2');
INSERT INTO `t_sys_field` VALUES ('11', 'leaf', '父模块', '0', '父节点', '1', '1');
INSERT INTO `t_sys_field` VALUES ('12', 'leaf', '父模块', '1', '子节点', '1', '2');
INSERT INTO `t_sys_field` VALUES ('13', 'expanded', '展开状态', '0', '收缩', '1', '1');
INSERT INTO `t_sys_field` VALUES ('14', 'expanded', '展开状态', '1', '展开', '1', '2');
INSERT INTO `t_sys_field` VALUES ('15', 'isdisplay', '是否显示', '0', '否', '1', '1');
INSERT INTO `t_sys_field` VALUES ('16', 'isdisplay', '是否显示', '1', '是', '1', '2');
INSERT INTO `t_sys_field` VALUES ('17', 'pagesize', '每页显示条数', '100', '100条/页', '1', '5');
INSERT INTO `t_sys_field` VALUES ('18', 'pagesize', '每页显示条数', '200', '200条/页', '1', '6');
INSERT INTO `t_sys_field` VALUES ('19', 'pagesize', '每页显示条数', '500', '500条/页', '0', '7');
INSERT INTO `t_sys_field` VALUES ('20', 'enabled', '是否启用', '0', '禁用', '1', '2');
INSERT INTO `t_sys_field` VALUES ('21', 'enabled', '是否启用', '1', '启用', '1', '1');
INSERT INTO `t_sys_field` VALUES ('22', 'areatype', '区域类型', '0', '国家', '1', '1');
INSERT INTO `t_sys_field` VALUES ('23', 'areatype', '区域类型', '1', '省份', '1', '2');
INSERT INTO `t_sys_field` VALUES ('24', 'areatype', '区域类型', '2', '市', '1', '3');
INSERT INTO `t_sys_field` VALUES ('25', 'areatype', '区域类型', '3', '区（县、镇）', '1', '4');
INSERT INTO `t_sys_module` VALUES ('1', '系统设置', null, '0', '0', '1', '1', '1', 'System Settings', 'system_settings', null);
INSERT INTO `t_sys_module` VALUES ('2', '供应商管理', '', '0', '0', '1', '2', '1', null, 'system_settings', '');
INSERT INTO `t_sys_module` VALUES ('11', '角色管理', '/role.action', '1', '1', '0', '3', '1', 'Role Management', 'role', null);
INSERT INTO `t_sys_module` VALUES ('12', '系统用户管理', '/sysUserInit.action', '1', '1', '0', '2', '1', null, 'user', '');
INSERT INTO `t_sys_module` VALUES ('13', '模块管理', '/sysModuleInitAction.action', '1', '1', '0', '1', '1', 'Module Management', 'module', null);
INSERT INTO `t_sys_module` VALUES ('14', '系统字段管理', '/field.action', '1', '1', '1', '4', '1', 'field', 'field', null);
INSERT INTO `t_sys_module` VALUES ('21', '供应商信息', '/provider.action', '2', '1', '0', '1', '1', null, 'user', '');
INSERT INTO `t_sys_module` VALUES ('22', '类型管理', '/shopTypeInit.action', '1', '1', '1', '5', '1', null, 'field', '类型管理');
INSERT INTO `t_sys_module` VALUES ('23', '地区管理', '/areaInit.action', '1', '1', '1', '6', '1', null, 'module', '地区管理');
INSERT INTO `t_sys_module` VALUES ('24', '我的商店', '/myshopinfo.action', '2', '1', '1', '2', '1', null, 'field', '商店信息');
INSERT INTO `t_sys_module` VALUES ('25', '代金券信息', '/myvoucherinfo.action', '2', '1', '1', '3', '1', null, 'field', '代金券信息');
INSERT INTO `t_sys_module` VALUES ('26', '普通用户管理', '/cmnUserInit.action', '1', '1', '1', '7', '1', null, 'user', '普通用户管理');
INSERT INTO `t_sys_module` VALUES ('27', '建议管理', '/myAdviceInit.action', '1', '1', '1', '8', '1', null, 'field', '用户建议管理');
INSERT INTO `t_sys_module` VALUES ('28', '消息管理', '/myPublishInit.action', '1', '1', '1', '9', '1', null, 'field', '用户消息管理');
INSERT INTO `t_sys_module` VALUES ('29', '距离管理', '/mydistance.action', '1', '1', '1', '10', '1', null, 'module', '距离管理');
INSERT INTO `t_sys_role` VALUES ('1', 'admin', 'system administrator');
INSERT INTO `t_sys_role` VALUES ('2', 'provider', '商家角色');
INSERT INTO `t_sys_role_module` VALUES ('35', '2', '2');
INSERT INTO `t_sys_role_module` VALUES ('36', '2', '21');
INSERT INTO `t_sys_role_module` VALUES ('37', '2', '24');
INSERT INTO `t_sys_role_module` VALUES ('38', '2', '25');
INSERT INTO `t_sys_role_module` VALUES ('64', '1', '1');
INSERT INTO `t_sys_role_module` VALUES ('65', '1', '13');
INSERT INTO `t_sys_role_module` VALUES ('66', '1', '12');
INSERT INTO `t_sys_role_module` VALUES ('67', '1', '11');
INSERT INTO `t_sys_role_module` VALUES ('68', '1', '14');
INSERT INTO `t_sys_role_module` VALUES ('69', '1', '22');
INSERT INTO `t_sys_role_module` VALUES ('70', '1', '23');
INSERT INTO `t_sys_role_module` VALUES ('71', '1', '26');
INSERT INTO `t_sys_role_module` VALUES ('72', '1', '27');
INSERT INTO `t_sys_role_module` VALUES ('73', '1', '28');
INSERT INTO `t_sys_role_module` VALUES ('74', '1', '29');
INSERT INTO `t_sys_user` VALUES ('1', 'admin', 'e10adc3949ba59abbe56e057f20f883e', 'Adams', '0', 'illl23@sina.com', '11934565432', '110', '0', '2012-07-30', '127.0.0.1', 'Super Admin', '2012-07-01', '22241212', '77', 'e10adc3949ba59abbe56e057f20f883e');
INSERT INTO `t_sys_user` VALUES ('3', 'll', 'e10adc3949ba59abbe56e057f20f883e', 'xxxx', '1', '678@qq.com', '1234567890', '3456789', '0', '2012-07-30', '127.0.0.1', '商家', '2012-07-19', '123456789', '76', 'e10adc3949ba59abbe56e057f20f883e');
INSERT INTO `t_sys_user_role` VALUES ('3', '1', '1');
INSERT INTO `t_sys_user_role` VALUES ('5', '3', '2');
INSERT INTO `t_user` VALUES ('4', '15123340263', 'e10adc3949ba59abbe56e057f20f883e', null, null, null, '0', '2012-07-11');
INSERT INTO `t_user` VALUES ('7', '13637989956', 'e10adc3949ba59abbe56e057f20f883e', null, null, null, '0', '2012-07-12');
INSERT INTO `t_user` VALUES ('8', '15881133958', '96e79218965eb72c92a549dd5a330112', null, null, null, '0', '2012-07-15');
INSERT INTO `t_user` VALUES ('10', '18621230823', 'e10adc3949ba59abbe56e057f20f883e', null, null, null, '0', '2012-07-30');
INSERT INTO `t_user_voucher` VALUES ('35', '8', '34', '2012-07-29', '0', '1');
INSERT INTO `t_user_voucher` VALUES ('36', '8', '71', '2012-07-29', '0', '1');
INSERT INTO `t_user_voucher` VALUES ('37', '10', '28', '2012-07-30', '0', '1');
INSERT INTO `t_voucher` VALUES ('1', 'AAAAA', '5', '20', '2012-07-21', '2012-08-04', 'AAABBBSSSSSS', 'new_iphone.jpg', 'AAAAAAAA', '2012-07-21', '1', '3', 'KFC');
INSERT INTO `t_voucher` VALUES ('3', 'CCCC', '25', '25', '2012-07-24', '2012-07-31', 'CCCC', 'new_iphone.jpg', 'CCCC', '2012-07-25', '1', '4', 'KFC');
INSERT INTO `t_voucher` VALUES ('4', 'MCDANALD', '12', '20', '2012-07-20', '2012-08-10', 'MCDANALD', 'new_iphone.jpg', 'MCDANALD', '2012-07-27', '1', '4', 'MCDLD');
INSERT INTO `t_voucher_instance` VALUES ('27', '3', '1', 'KFC1343146616', '2');
INSERT INTO `t_voucher_instance` VALUES ('28', '3', '1', 'KFC1343146616', '2');
INSERT INTO `t_voucher_instance` VALUES ('29', '3', '0', 'KFC1343146616', '1');
INSERT INTO `t_voucher_instance` VALUES ('30', '3', '0', 'KFC1343146616', '1');
INSERT INTO `t_voucher_instance` VALUES ('31', '3', '0', 'KFC1343146616', '1');
INSERT INTO `t_voucher_instance` VALUES ('32', '3', '0', 'KFC1343146616', '1');
INSERT INTO `t_voucher_instance` VALUES ('33', '3', '0', 'KFC1343146616', '1');
INSERT INTO `t_voucher_instance` VALUES ('34', '3', '1', 'KFC1343146616', '2');
INSERT INTO `t_voucher_instance` VALUES ('35', '3', '0', 'KFC1343146616', '1');
INSERT INTO `t_voucher_instance` VALUES ('36', '3', '0', 'KFC1343146616', '1');
INSERT INTO `t_voucher_instance` VALUES ('37', '3', '0', 'KFC1343146616', '1');
INSERT INTO `t_voucher_instance` VALUES ('38', '3', '0', 'KFC1343146616', '1');
INSERT INTO `t_voucher_instance` VALUES ('39', '3', '0', 'KFC1343146616', '1');
INSERT INTO `t_voucher_instance` VALUES ('40', '3', '0', 'KFC1343146616', '1');
INSERT INTO `t_voucher_instance` VALUES ('41', '3', '0', 'KFC1343146616', '1');
INSERT INTO `t_voucher_instance` VALUES ('42', '3', '0', 'KFC1343146617', '1');
INSERT INTO `t_voucher_instance` VALUES ('43', '3', '0', 'KFC1343146617', '1');
INSERT INTO `t_voucher_instance` VALUES ('44', '3', '0', 'KFC1343146617', '1');
INSERT INTO `t_voucher_instance` VALUES ('45', '3', '0', 'KFC1343146617', '1');
INSERT INTO `t_voucher_instance` VALUES ('46', '3', '0', 'KFC1343146617', '1');
INSERT INTO `t_voucher_instance` VALUES ('47', '3', '0', 'KFC1343146617', '1');
INSERT INTO `t_voucher_instance` VALUES ('48', '3', '0', 'KFC1343146617', '1');
INSERT INTO `t_voucher_instance` VALUES ('49', '3', '0', 'KFC1343146617', '1');
INSERT INTO `t_voucher_instance` VALUES ('50', '3', '0', 'KFC1343146617', '1');
INSERT INTO `t_voucher_instance` VALUES ('51', '3', '0', 'KFC1343146617', '1');
INSERT INTO `t_voucher_instance` VALUES ('52', '4', '0', 'MCDLD1343395318', '1');
INSERT INTO `t_voucher_instance` VALUES ('53', '4', '0', 'MCDLD1343395318', '1');
INSERT INTO `t_voucher_instance` VALUES ('54', '4', '0', 'MCDLD1343395318', '1');
INSERT INTO `t_voucher_instance` VALUES ('55', '4', '0', 'MCDLD1343395318', '1');
INSERT INTO `t_voucher_instance` VALUES ('56', '4', '0', 'MCDLD1343395318', '1');
INSERT INTO `t_voucher_instance` VALUES ('57', '4', '1', 'MCDLD1343395318', '2');
INSERT INTO `t_voucher_instance` VALUES ('58', '4', '0', 'MCDLD1343395318', '1');
INSERT INTO `t_voucher_instance` VALUES ('59', '4', '0', 'MCDLD1343395318', '1');
INSERT INTO `t_voucher_instance` VALUES ('60', '4', '0', 'MCDLD1343395318', '1');
INSERT INTO `t_voucher_instance` VALUES ('61', '4', '0', 'MCDLD1343395318', '1');
INSERT INTO `t_voucher_instance` VALUES ('62', '4', '0', 'MCDLD1343395318', '1');
INSERT INTO `t_voucher_instance` VALUES ('63', '4', '0', 'MCDLD1343395318', '1');
INSERT INTO `t_voucher_instance` VALUES ('64', '4', '0', 'MCDLD1343395318', '1');
INSERT INTO `t_voucher_instance` VALUES ('65', '4', '0', 'MCDLD1343395318', '1');
INSERT INTO `t_voucher_instance` VALUES ('66', '4', '0', 'MCDLD1343395318', '1');
INSERT INTO `t_voucher_instance` VALUES ('67', '4', '0', 'MCDLD1343395318', '1');
INSERT INTO `t_voucher_instance` VALUES ('68', '4', '0', 'MCDLD1343395318', '1');
INSERT INTO `t_voucher_instance` VALUES ('69', '4', '0', 'MCDLD1343395318', '1');
INSERT INTO `t_voucher_instance` VALUES ('70', '4', '0', 'MCDLD1343395319', '1');
INSERT INTO `t_voucher_instance` VALUES ('71', '4', '1', 'MCDLD1343395319', '2');
