/*
 Navicat Premium Data Transfer

 Source Server         : localhost_3306
 Source Server Type    : MySQL
 Source Server Version : 80030 (8.0.30)
 Source Host           : localhost:3306
 Source Schema         : library

 Target Server Type    : MySQL
 Target Server Version : 80030 (8.0.30)
 File Encoding         : 65001

 Date: 03/11/2022 16:52:08
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for admin
-- ----------------------------
DROP TABLE IF EXISTS `admin`;
CREATE TABLE `admin`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户名',
  `phone` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '联系方式',
  `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '邮箱',
  `createtime` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updatetime` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `password` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '密码',
  `status` tinyint(1) NULL DEFAULT 1 COMMENT '状态',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `username_key`(`username` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of admin
-- ----------------------------
INSERT INTO `admin` VALUES (1, '方文健', '17607122946', '2813162575@qq.com', '2022-10-13 15:14:26', '2022-11-03 11:48:48', 'af60582ba351f0482282531a9c44c645', 1);
INSERT INTO `admin` VALUES (2, 'admin1', '13456545678', '28@qq.com', '2022-10-13 15:16:55', '2022-10-28 11:46:08', 'af60582ba351f0482282531a9c44c645', 1);
INSERT INTO `admin` VALUES (3, 'admin2', '13454324567', '2813162575@qq.com', '2022-10-17 13:29:40', '2022-10-28 11:45:55', 'af60582ba351f0482282531a9c44c645', 1);

-- ----------------------------
-- Table structure for book
-- ----------------------------
DROP TABLE IF EXISTS `book`;
CREATE TABLE `book`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '名称',
  `description` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '描述',
  `publish_date` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '出版日期',
  `author` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '作者',
  `publisher` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '出版社',
  `category` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '分类',
  `book_no` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '标准码',
  `cover` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '封面',
  `createtime` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updatetime` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `score` int NULL DEFAULT NULL COMMENT '积分',
  `nums` int NULL DEFAULT 0 COMMENT '数量',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `book_no_index`(`book_no` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 13 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of book
-- ----------------------------
INSERT INTO `book` VALUES (3, 'python123', '《程序员的数学2：概率统计》沿袭《程序员的数学》平易近人的风格，用通俗的语言和具体的图表深入讲解程序员必须掌握的各类概率统计知识，例证丰富，讲解明晰，且提供了大量扩展内容，引导读者进一步深入学习。\n《程序员的数学2：概率统计》涉及随机变量、贝叶斯公式、离散值和连续值的概率分布、协方差矩阵、多元正态分布、估计与检验理论、伪随机数以及概率论的各类应用，适合程序设计人员与数学爱好者阅读，也可作为高中或大学非数学专业学生的概率论入门读物。', '2022-10-07', 'python', '人民邮电出版社', '现代文化', 'ISBN：9787115473059', 'https://img13.360buyimg.com/n1/jfs/t1/145659/19/19742/96420/60ae2430E98e8712a/8230dc4c5e2d385c.jpg', '2022-10-28 18:19:38', '2022-11-01 14:04:15', 15, 9);
INSERT INTO `book` VALUES (7, '孙悟空', '孙悟空', '2022-10-07', '孙悟空', '孙悟空', '历史文化 > 唐朝文化', '123123', 'https://img2.baidu.com/it/u=150699643,525530844&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=500', '2022-10-29 12:31:59', '2022-11-01 17:32:49', 10, 2);
INSERT INTO `book` VALUES (12, '1', NULL, '2022-11-07', NULL, NULL, '历史文化 > 明朝文化', '111', 'http://localhost:8080/api/book/file/download/1667387126553?play=1&token=eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJhdWQiOiIxIiwiZXhwIjoxNjY4NjgzMTI2fQ.FwtxQGutsdnYCHKJljIY4mJbMa0jReprED4NlMcpqEo', '2022-11-02 19:17:42', NULL, 10, 11);

-- ----------------------------
-- Table structure for borrow
-- ----------------------------
DROP TABLE IF EXISTS `borrow`;
CREATE TABLE `borrow`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `book_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '图书名称',
  `book_no` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '图书标准码',
  `user_no` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户会员码',
  `user_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户名称',
  `user_phone` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户联系方式',
  `createtime` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updatetime` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `score` int NULL DEFAULT NULL COMMENT '借书积分',
  `status` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '已借出' COMMENT '借书状态',
  `days` int NULL DEFAULT 1 COMMENT '借书天数',
  `return_date` datetime NULL DEFAULT NULL COMMENT '归还日期',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of borrow
-- ----------------------------
INSERT INTO `borrow` VALUES (1, 'python123', 'ISBN：9787115473059', '20224528-1586366254', '小强', '11111', '2022-11-01 14:49:05', NULL, 15, '已借出', 1, '2022-11-02 14:49:05');
INSERT INTO `borrow` VALUES (2, 'python123', 'ISBN：9787115473059', '20224828151124410', '小鸟', '22222', '2022-11-01 17:27:11', NULL, 15, '已归还', 2, '2022-11-03 14:49:05');
INSERT INTO `borrow` VALUES (4, 'python123', 'ISBN：9787115473059', '20222717470638931', '战神', '13453245678', '2022-11-02 11:48:01', NULL, 15, '已归还', 3, '2022-11-05 00:00:00');
INSERT INTO `borrow` VALUES (5, '孙悟空', '123123', '20222228802660720', '海吉拉', '14598652369', '2022-11-02 13:33:07', NULL, 10, '已借出', 1, '2022-11-03 00:00:00');
INSERT INTO `borrow` VALUES (7, '孙悟空', '123123', '202228172107307040', '游戏', '13456789876', '2022-11-02 16:26:58', NULL, 10, '已归还', 3, '2022-11-05 00:00:00');
INSERT INTO `borrow` VALUES (8, '孙悟空', '123123', '202228172107307040', '游戏', '13456789876', '2022-11-02 16:35:39', NULL, 40, '已归还', 4, '2022-11-06 00:00:00');
INSERT INTO `borrow` VALUES (9, '孙悟空', '123123', '202228172107307040', '游戏', '13456789876', '2022-11-02 16:44:06', NULL, 50, '已归还', 5, '2022-10-31 00:00:00');

-- ----------------------------
-- Table structure for category
-- ----------------------------
DROP TABLE IF EXISTS `category`;
CREATE TABLE `category`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '名称',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `pid` int NULL DEFAULT NULL COMMENT '父级id',
  `createtime` datetime NULL DEFAULT CURRENT_TIMESTAMP,
  `updatetime` datetime NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 13 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of category
-- ----------------------------
INSERT INTO `category` VALUES (1, '历史文化', '历史文化', NULL, '2022-10-28 12:42:32', '2022-10-28 13:09:42');
INSERT INTO `category` VALUES (3, '唐朝文化', '唐朝文化', 1, '2022-10-28 13:50:20', '2022-10-28 13:51:29');
INSERT INTO `category` VALUES (4, '宋朝文化', '宋朝文化', 1, '2022-10-28 13:51:14', NULL);
INSERT INTO `category` VALUES (5, '现代文化', '现代文化', NULL, '2022-10-28 14:26:41', NULL);
INSERT INTO `category` VALUES (12, '明朝文化', '明朝文化', 1, '2022-10-28 14:53:30', NULL);

-- ----------------------------
-- Table structure for returnbook
-- ----------------------------
DROP TABLE IF EXISTS `returnbook`;
CREATE TABLE `returnbook`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `book_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '图书名称',
  `book_no` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '图书标准码',
  `user_no` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户会员码',
  `user_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户名称',
  `user_phone` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户联系方式',
  `createtime` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updatetime` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `score` int NULL DEFAULT NULL COMMENT '借书积分',
  `status` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '已借出' COMMENT '借书状态',
  `days` int NULL DEFAULT 1 COMMENT '借书天数',
  `return_date` datetime NULL DEFAULT NULL COMMENT '归还日期',
  `real_date` datetime NULL DEFAULT NULL COMMENT '实际归还日期',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 15 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of returnbook
-- ----------------------------
INSERT INTO `returnbook` VALUES (14, '孙悟空', '123123', '202228172107307040', '游戏', '13456789876', '2022-11-02 16:45:52', NULL, 50, '已归还', 5, '2022-10-31 00:00:00', '2022-11-02 00:00:00');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '姓名',
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户会员码',
  `age` int NULL DEFAULT NULL COMMENT '年龄',
  `sex` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '性别',
  `phone` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '联系方式',
  `address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '地址',
  `createtime` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updatetime` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `account` int NULL DEFAULT 0 COMMENT '账户余额',
  `status` tinyint(1) NULL DEFAULT 1 COMMENT '禁用状态 1表示不禁用',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `username_index`(`username` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 46 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (25, '小鸟', '20224828151124410', 20, '女', '22222', '天津', '2022-09-28 09:48:09', NULL, 5, 1);
INSERT INTO `user` VALUES (37, '战神', '20222717470638931', 23, '男', '13453245678', '深圳', '2022-10-17 13:27:15', '2022-11-03 11:50:02', 45, 1);
INSERT INTO `user` VALUES (38, '游戏', '202228172107307040', 15, '男', '13456789876', '北京', '2022-10-17 13:28:16', '2022-11-03 14:31:00', 0, 0);
INSERT INTO `user` VALUES (44, '黑帮老大', '20223703294200226', 43, '男', '13234543234', '13123', '2022-11-03 14:37:39', '2022-11-03 14:37:56', 20, 1);

SET FOREIGN_KEY_CHECKS = 1;
