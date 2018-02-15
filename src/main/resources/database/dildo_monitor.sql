/*
 Navicat MySQL Data Transfer

 Source Server         : LOCALHOST
 Source Server Type    : MySQL
 Source Server Version : 50617
 Source Host           : localhost:3306
 Source Schema         : dildo_monitor

 Target Server Type    : MySQL
 Target Server Version : 50617
 File Encoding         : 65001

 Date: 14/02/2018 19:51:16
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for processed_eth_tx
-- ----------------------------
DROP TABLE IF EXISTS `processed_eth_tx`;
CREATE TABLE `processed_eth_tx`  (
  `processed_eth_tx_id` int(11) NOT NULL AUTO_INCREMENT,
  `date_created` datetime(0) NULL DEFAULT NULL,
  `date_updated` datetime(0) NULL DEFAULT NULL,
  `eth_amount` double NULL DEFAULT NULL,
  `from_address` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  `processed` bit(1) NULL DEFAULT NULL,
  `succeeded` bit(1) NULL DEFAULT NULL,
  `to_address` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  `token_amount` int(11) NULL DEFAULT NULL,
  `tx_id` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  PRIMARY KEY (`processed_eth_tx_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 59 CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = Compact;

SET FOREIGN_KEY_CHECKS = 1;
