/*
 Navicat MySQL Data Transfer

 Source Server         : My SQL local
 Source Server Version : 50533
 Source Host           : localhost
 Source Database       : simpleprp

 Target Server Version : 50533
 File Encoding         : utf-8

 Date: 11/18/2013 20:39:01 PM
*/

SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `ROLE`
-- ----------------------------
DROP TABLE IF EXISTS `ROLE`;
CREATE TABLE `ROLE` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `CHANGEDAT` datetime DEFAULT NULL,
  `CHANGEDBY` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `CREATEDAT` datetime DEFAULT NULL,
  `CREATEDBY` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `VERSION` bigint(20) DEFAULT NULL,
  `NAME` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `UK_f69b08d975504d399e9cb5d4d69` (`NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
--  Table structure for `USER`
-- ----------------------------
DROP TABLE IF EXISTS `USER`;
CREATE TABLE `USER` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `CHANGEDAT` datetime DEFAULT NULL,
  `CHANGEDBY` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `CREATEDAT` datetime DEFAULT NULL,
  `CREATEDBY` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `VERSION` bigint(20) DEFAULT NULL,
  `ACCOUNT_NON_EXPIRED` tinyint(1) NOT NULL,
  `ACCOUNT_NON_LOCKED` tinyint(1) NOT NULL,
  `CREDENTIALS_NON_EXPIRED` tinyint(1) NOT NULL,
  `EMAIL` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `ENABLED` tinyint(1) NOT NULL,
  `FAILED_LOGIN_ATTEMPTS` int(11) DEFAULT NULL,
  `FIRSTNAME` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `LASTNAME` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `LOGIN` varchar(20) COLLATE utf8_unicode_ci NOT NULL,
  `PASSWORD` varchar(20) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `UK_651214f580684f5dbb6181ab6e4` (`LOGIN`),
  UNIQUE KEY `UK_e302d9d45c2b4c1fb91cf1dbf46` (`EMAIL`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
--  Records of `USER`
-- ----------------------------
BEGIN;
INSERT INTO `USER` VALUES ('1', '2013-11-18 18:22:10', 'anonymous', '2013-11-18 18:22:10', 'anonymous', '0', '1', '1', '1', 'bernfried.howe1@me.com', '1', '0', 'Bernfried1', 'Howe', 'bernfried1', 'password');
INSERT INTO `USER` VALUES ('2', '2013-11-18 18:22:10', 'anonymous', '2013-11-18 18:22:10', 'anonymous', '0', '1', '1', '1', 'bernfried.howe2@me.com', '1', '0', 'Bernfried2', 'Howe', 'bernfried2', 'password');
INSERT INTO `USER` VALUES ('3', '2013-11-18 18:22:10', 'anonymous', '2013-11-18 18:22:10', 'anonymous', '0', '1', '1', '1', 'bernfried.howe3@me.com', '1', '0', 'Bernfried3', 'Howe', 'bernfried3', 'password');
INSERT INTO `USER` VALUES ('4', '2013-11-18 18:22:10', 'anonymous', '2013-11-18 18:22:10', 'anonymous', '0', '1', '1', '1', 'bernfried.howe4@me.com', '1', '0', 'Bernfried4', 'Howe', 'bernfried4', 'password');
INSERT INTO `USER` VALUES ('5', '2013-11-18 18:22:10', 'anonymous', '2013-11-18 18:22:10', 'anonymous', '0', '1', '1', '1', 'bernfried.howe5@me.com', '1', '0', 'Bernfried5', 'Howe', 'bernfried5', 'password');
COMMIT;

-- ----------------------------
--  Table structure for `USER_ROLE`
-- ----------------------------
DROP TABLE IF EXISTS `USER_ROLE`;
CREATE TABLE `USER_ROLE` (
  `USER_ID` bigint(20) NOT NULL,
  `ROLE_ID` bigint(20) NOT NULL,
  KEY `FK_722bd8bc83c0453f8a319cdbbb6` (`ROLE_ID`),
  KEY `FK_b321561a88104386bc15a4ca3a5` (`USER_ID`),
  CONSTRAINT `FK_b321561a88104386bc15a4ca3a5` FOREIGN KEY (`USER_ID`) REFERENCES `USER` (`ID`),
  CONSTRAINT `FK_722bd8bc83c0453f8a319cdbbb6` FOREIGN KEY (`ROLE_ID`) REFERENCES `ROLE` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

SET FOREIGN_KEY_CHECKS = 1;
