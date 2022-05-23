/*
 Navicat Premium Data Transfer

 Source Server         : MySQL
 Source Server Type    : MySQL
 Source Server Version : 80024
 Source Host           : localhost:3306
 Source Schema         : attend_mgt

 Target Server Type    : MySQL
 Target Server Version : 80024
 File Encoding         : 65001

 Date: 19/05/2022 09:09:52
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_check
-- ----------------------------
DROP TABLE IF EXISTS `t_check`;
CREATE TABLE `t_check`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `user_id` int NULL DEFAULT NULL COMMENT '请假学生ID',
  `group_id` int NULL DEFAULT NULL COMMENT '请假的班级ID',
  `yorn` int NULL DEFAULT 0 COMMENT '是否批假0待审核，1批准，-1拒绝',
  `status` tinyint NULL DEFAULT 0 COMMENT '请假0 \r\n缺课1 \r\n旷课2',
  `start_date` datetime(0) NULL DEFAULT NULL COMMENT '开始请假时间',
  `end_date` datetime(0) NULL DEFAULT NULL COMMENT '结束请假时间',
  `type` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '类型',
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '描述',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 83 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_check
-- ----------------------------
INSERT INTO `t_check` VALUES (85, 5, 1, -1, 0, '2022-05-17 22:51:54', '2022-05-19 22:51:55', '事假', '111');
INSERT INTO `t_check` VALUES (86, 6, 2, -1, 0, '2022-05-18 22:52:33', '2022-05-21 22:52:34', '事假', '222');
INSERT INTO `t_check` VALUES (87, 5, 1, 1, 0, '2022-05-18 22:53:38', '2022-05-21 22:53:40', '病假', '1111111111111111111111111');

-- ----------------------------
-- Table structure for t_course
-- ----------------------------
DROP TABLE IF EXISTS `t_course`;
CREATE TABLE `t_course`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `name` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '课程名',
  `user_id` int NOT NULL COMMENT '任课教师',
  `course_date` datetime(0) NULL DEFAULT NULL COMMENT '课程时间',
  `selected_num` int NOT NULL COMMENT '选课人数',
  `max_num` int NOT NULL COMMENT '最大选课人数',
  `info` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '课程信息',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `fk_course_teacher`(`user_id`) USING BTREE,
  CONSTRAINT `fk_course_user_id` FOREIGN KEY (`user_id`) REFERENCES `t_user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_course
-- ----------------------------
INSERT INTO `t_course` VALUES (1, '数据结构', 1, '2022-04-11 23:24:01', 50, 50, NULL);

-- ----------------------------
-- Table structure for t_group
-- ----------------------------
DROP TABLE IF EXISTS `t_group`;
CREATE TABLE `t_group`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '班级ID',
  `name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '班级名称',
  `num` int NULL DEFAULT NULL COMMENT '班级人数',
  `last_operator` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '最后一次操作者',
  `last_operator_time` datetime(0) NULL DEFAULT NULL COMMENT '最后一次操作时间',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_group
-- ----------------------------
INSERT INTO `t_group` VALUES (0, '暂未分配班级', 99999, NULL, NULL, NULL);
INSERT INTO `t_group` VALUES (1, '测试1班', 50, NULL, NULL, NULL);
INSERT INTO `t_group` VALUES (2, '测试2班', 50, NULL, NULL, NULL);

-- ----------------------------
-- Table structure for t_leave
-- ----------------------------
DROP TABLE IF EXISTS `t_leave`;
CREATE TABLE `t_leave`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `user_id` int NOT NULL COMMENT '请假的学生',
  `leave_date` datetime(0) NULL DEFAULT NULL COMMENT '需要请假的时间',
  `reason` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '请假的理由',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '教师的回复',
  `state` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否批假',
  `create_date` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '申请的时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `fk_leave_user_id_bind`(`user_id`) USING BTREE,
  CONSTRAINT `fk_leave_user_id_bind` FOREIGN KEY (`user_id`) REFERENCES `t_user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_leave
-- ----------------------------

-- ----------------------------
-- Table structure for t_right
-- ----------------------------
DROP TABLE IF EXISTS `t_right`;
CREATE TABLE `t_right`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '权限ID',
  `name` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '权限名',
  `url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '可以请求的url',
  `icon` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '图标',
  `page_path` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '路由地址名',
  `type` int NULL DEFAULT NULL COMMENT '权限类型。1菜单，2按钮，3其他',
  `status` int NULL DEFAULT NULL COMMENT '权限状态。1正常，0冻结',
  `create_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '权限创建时间',
  `pid` int NULL DEFAULT NULL COMMENT '父级ID',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '权限备注',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1017 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_right
-- ----------------------------
INSERT INTO `t_right` VALUES (1, '主页', '/home', 'el-icon-house', 'Home', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `t_right` VALUES (2, '用户管理', '/user', 'el-icon-user', 'User', NULL, NULL, NULL, 2, NULL);
INSERT INTO `t_right` VALUES (3, '角色管理', '/role', 'el-icon-link', 'Role', NULL, NULL, '2022-04-14 22:24:04', NULL, NULL);
INSERT INTO `t_right` VALUES (4, '权限管理', '/right', 'el-icon-key', 'Right', NULL, NULL, '2022-04-14 22:30:02', NULL, NULL);
INSERT INTO `t_right` VALUES (1007, '我要请假', '/checkUser', 'el-icon-edit', 'CheckUser', NULL, NULL, '2022-04-16 14:09:14', NULL, NULL);
INSERT INTO `t_right` VALUES (1008, '请假名单', '/checkAdmin', 'el-icon-tickets', 'CheckAdmin', NULL, NULL, '2022-04-16 14:09:44', NULL, NULL);
INSERT INTO `t_right` VALUES (1012, '批假记录', '/checkAdminHistory', 'el-icon-edit', NULL, NULL, NULL, '2022-04-16 18:44:49', NULL, NULL);
INSERT INTO `t_right` VALUES (1013, '请假记录', '/checkUserHistory', 'el-icon-document', NULL, NULL, NULL, '2022-04-16 18:45:07', NULL, NULL);
INSERT INTO `t_right` VALUES (1018, 'test', '/test', NULL, NULL, NULL, NULL, '2022-05-19 09:05:20', NULL, NULL);

-- ----------------------------
-- Table structure for t_role
-- ----------------------------
DROP TABLE IF EXISTS `t_role`;
CREATE TABLE `t_role`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '角色ID',
  `name` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '角色名',
  `type` tinyint NULL DEFAULT 1 COMMENT '角色类型。0管理员，1普通用户，2其他用户',
  `status` tinyint NULL DEFAULT 1 COMMENT '角色状态。1正常，0冻结，2删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 20 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_role
-- ----------------------------
INSERT INTO `t_role` VALUES (1, 'admin', 0, 1);
INSERT INTO `t_role` VALUES (2, 'teacher', 0, 1);
INSERT INTO `t_role` VALUES (3, 'student', 1, 1);
INSERT INTO `t_role` VALUES (4, 'instructor', 0, 1);
INSERT INTO `t_role` VALUES (11, '宿管', 1, 0);
INSERT INTO `t_role` VALUES (12, '门卫', 1, 1);

-- ----------------------------
-- Table structure for t_role_right
-- ----------------------------
DROP TABLE IF EXISTS `t_role_right`;
CREATE TABLE `t_role_right`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '角色权限ID',
  `role_id` int NULL DEFAULT NULL COMMENT '角色ID',
  `right_id` int NULL DEFAULT NULL COMMENT '权限ID',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `fk_role_right_role_id_bind`(`role_id`) USING BTREE,
  INDEX `fk_role_right_right_id_bind`(`right_id`) USING BTREE,
  CONSTRAINT `fk_role_right_right_id_bind` FOREIGN KEY (`right_id`) REFERENCES `t_right` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_role_right_role_id_bind` FOREIGN KEY (`role_id`) REFERENCES `t_role` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 2101432326 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_role_right
-- ----------------------------
INSERT INTO `t_role_right` VALUES (-1459613694, 12, 1);
INSERT INTO `t_role_right` VALUES (-1459613693, 12, 3);
INSERT INTO `t_role_right` VALUES (-1459613692, 12, 1012);
INSERT INTO `t_role_right` VALUES (-1459613691, 12, 1018);
INSERT INTO `t_role_right` VALUES (-1224724479, 1, 1);
INSERT INTO `t_role_right` VALUES (-1224724478, 1, 2);
INSERT INTO `t_role_right` VALUES (-1174368255, 2, 1);
INSERT INTO `t_role_right` VALUES (-1174368254, 2, 1008);
INSERT INTO `t_role_right` VALUES (-1174368253, 2, 1012);
INSERT INTO `t_role_right` VALUES (-1061076991, 4, 1);
INSERT INTO `t_role_right` VALUES (-1061076990, 4, 2);
INSERT INTO `t_role_right` VALUES (-1061076989, 4, 1008);
INSERT INTO `t_role_right` VALUES (-989773823, 4, 1012);
INSERT INTO `t_role_right` VALUES (-989773822, 4, 1013);
INSERT INTO `t_role_right` VALUES (2000789505, 3, 1);
INSERT INTO `t_role_right` VALUES (2000789506, 3, 1007);
INSERT INTO `t_role_right` VALUES (2000789507, 3, 1013);
INSERT INTO `t_role_right` VALUES (2101432323, 1, 3);
INSERT INTO `t_role_right` VALUES (2101432325, 1, 4);

-- ----------------------------
-- Table structure for t_selected_course
-- ----------------------------
DROP TABLE IF EXISTS `t_selected_course`;
CREATE TABLE `t_selected_course`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `user_id` int NULL DEFAULT NULL COMMENT '选课学生ID',
  `course_id` int NULL DEFAULT NULL COMMENT '课程ID',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `fk_select_user_id_bind`(`user_id`) USING BTREE,
  INDEX `fk_select_couse_id_bind`(`course_id`) USING BTREE,
  CONSTRAINT `fk_select_couse_id_bind` FOREIGN KEY (`course_id`) REFERENCES `t_course` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_select_user_id_bind` FOREIGN KEY (`user_id`) REFERENCES `t_user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_selected_course
-- ----------------------------

-- ----------------------------
-- Table structure for t_user
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `name` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户名',
  `password` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '123456' COMMENT '用户密码',
  `telephone` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '用户手机号',
  `mail` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '用户邮箱',
  `group_id` int NULL DEFAULT 0 COMMENT '所在班级ID',
  `status` tinyint NULL DEFAULT 1 COMMENT '用户状态。1正常，0冻结，2删除',
  `last_time` datetime(0) NULL DEFAULT NULL COMMENT '上次登录的时间',
  `create_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '用户创建的时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `fk_user_group_id`(`group_id`) USING BTREE,
  CONSTRAINT `fk_user_group_id` FOREIGN KEY (`group_id`) REFERENCES `t_group` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 136 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_user
-- ----------------------------
INSERT INTO `t_user` VALUES (1, 'wang', '123456', '15555144633', 'wang@qq.com', NULL, 1, NULL, '2022-04-04 15:25:29');
INSERT INTO `t_user` VALUES (2, 'wan', '123456', '13699998888', 'wan@qq.com', NULL, 1, NULL, '2022-04-04 15:31:58');
INSERT INTO `t_user` VALUES (3, 'ls1', '123456', '13699998888', 'ls1@163.com', 1, 1, NULL, '2022-04-16 23:34:26');
INSERT INTO `t_user` VALUES (4, 'ls2', '123456', '13699998888', 'ls2@163.com', 2, 1, NULL, '2022-04-16 23:35:08');
INSERT INTO `t_user` VALUES (5, 'xs1', '123456', '13699998888', 'xs1@163.com', 1, 1, NULL, '2022-04-16 23:36:07');
INSERT INTO `t_user` VALUES (6, 'xs2', '123456', '13699998888', 'xs2@163.com', 2, 1, NULL, '2022-04-16 23:36:29');
INSERT INTO `t_user` VALUES (7, 'xs3', '123456', '13699998888', NULL, 1, 1, NULL, '2022-04-17 11:35:32');
INSERT INTO `t_user` VALUES (8, 'test1', '123456', '13699998888', NULL, 0, 1, NULL, '2022-04-18 10:21:32');
INSERT INTO `t_user` VALUES (9, 'test2', '123456', '13699998888', '1', 1, 1, NULL, '2022-04-24 14:59:27');
INSERT INTO `t_user` VALUES (10, 'test3', '123456', '13699998888', '2', 1, 1, NULL, '2022-04-24 14:59:44');
INSERT INTO `t_user` VALUES (11, 'test4', '123456', '13699998888', NULL, 0, 1, NULL, '2022-04-25 15:55:24');
INSERT INTO `t_user` VALUES (12, 'test5', '123456', '13699998888', NULL, 0, 1, NULL, '2022-04-25 15:55:29');
INSERT INTO `t_user` VALUES (13, 'test6', '123456', '13699998888', NULL, 1, 1, NULL, '2022-04-26 15:57:36');
INSERT INTO `t_user` VALUES (14, 'test7', '123456', '13699998888', NULL, 0, 0, NULL, '2022-04-26 16:04:04');
INSERT INTO `t_user` VALUES (15, 'test8', '123456', '13699998888', NULL, 0, 1, NULL, '2022-05-18 19:58:42');
INSERT INTO `t_user` VALUES (16, 'test9', '123456', '13699998888', NULL, 0, 1, NULL, '2022-05-18 19:58:54');
INSERT INTO `t_user` VALUES (17, 'test10', '123456', '13699998888', NULL, 0, 1, NULL, '2022-05-18 19:59:11');
INSERT INTO `t_user` VALUES (18, 'test11', '123456', '13699998888', NULL, 0, 1, NULL, '2022-05-18 19:59:29');
INSERT INTO `t_user` VALUES (19, 'test12', '123456', '13699998888', NULL, 0, 1, NULL, '2022-05-18 19:59:54');
INSERT INTO `t_user` VALUES (20, 'test13', '123456', '13699998888', NULL, 0, 1, NULL, '2022-05-18 20:00:29');
INSERT INTO `t_user` VALUES (21, 'test15', '123456', NULL, NULL, 0, 1, NULL, '2022-05-18 20:03:55');
INSERT INTO `t_user` VALUES (159, 'zhaoyun', '123456', '111111', '111', 1, 1, '1970-01-01 08:00:00', '2022-04-04 15:25:00');
INSERT INTO `t_user` VALUES (160, 'zhangfei', '123456', '', '', 0, 1, NULL, '2022-05-18 22:57:08');
INSERT INTO `t_user` VALUES (161, 'liubei', '123456', '', '', 0, 1, NULL, '2022-05-18 22:57:08');
INSERT INTO `t_user` VALUES (162, 'guanyu', '123456', '', '', 0, 1, NULL, '2022-05-18 22:57:08');
INSERT INTO `t_user` VALUES (163, 'sunshangxiang', '123456', '', '', 0, 1, NULL, '2022-05-18 22:57:08');
INSERT INTO `t_user` VALUES (164, 'caocao', '123456', '', '', 0, 1, NULL, '2022-05-18 22:57:08');
INSERT INTO `t_user` VALUES (165, 'zhugeliang', '123456', '', '', 0, 1, NULL, '2022-05-18 22:57:08');
INSERT INTO `t_user` VALUES (166, '123', '123', '', '', 0, 1, NULL, '2022-05-18 22:57:08');

-- ----------------------------
-- Table structure for t_user_role
-- ----------------------------
DROP TABLE IF EXISTS `t_user_role`;
CREATE TABLE `t_user_role`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '用户角色ID',
  `user_id` int NULL DEFAULT NULL COMMENT '用户ID',
  `role_id` int NULL DEFAULT NULL COMMENT '角色ID',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `fk_user_role_role_id_bind`(`role_id`) USING BTREE,
  INDEX `fk_user_role_user_id_bind`(`user_id`) USING BTREE,
  CONSTRAINT `fk_user_role_role_id_bind` FOREIGN KEY (`role_id`) REFERENCES `t_role` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_user_role_user_id_bind` FOREIGN KEY (`user_id`) REFERENCES `t_user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 1996517378 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_user_role
-- ----------------------------
INSERT INTO `t_user_role` VALUES (-2130694143, 3, 2);
INSERT INTO `t_user_role` VALUES (-775933951, 4, 2);
INSERT INTO `t_user_role` VALUES (-297775103, 5, 3);
INSERT INTO `t_user_role` VALUES (-134103038, 2, 3);
INSERT INTO `t_user_role` VALUES (-130019327, 8, 12);
INSERT INTO `t_user_role` VALUES (-117334015, 7, 3);
INSERT INTO `t_user_role` VALUES (1627402241, 6, 3);
INSERT INTO `t_user_role` VALUES (1820352514, 1, 1);
INSERT INTO `t_user_role` VALUES (1983905793, 13, 3);

-- ----------------------------
-- View structure for view_user_info
-- ----------------------------
DROP VIEW IF EXISTS `view_user_info`;
CREATE ALGORITHM = UNDEFINED SQL SECURITY DEFINER VIEW `view_user_info` AS select `t_user`.`id` AS `user_id`,`t_user`.`name` AS `user_name`,`t_user`.`telephone` AS `user_telephone`,`t_user`.`mail` AS `user_mail`,`t_user`.`group_id` AS `user_group`,`t_user`.`status` AS `user_status`,`t_user`.`create_time` AS `user_create_time`,`t_user`.`last_time` AS `user_last_time`,`t_role`.`id` AS `role_id`,`t_role`.`name` AS `role_name`,`t_right`.`id` AS `right_id`,`t_right`.`name` AS `right_name`,`t_right`.`url` AS `right_url`,`t_right`.`pid` AS `right_pid`,`t_right`.`icon` AS `right_icon`,`t_right`.`id` AS `rightId`,`t_right`.`page_path` AS `rightPagePath` from ((((`t_user` left join `t_user_role` on((`t_user`.`id` = `t_user_role`.`user_id`))) left join `t_role` on((`t_user_role`.`role_id` = `t_role`.`id`))) left join `t_role_right` on((`t_role`.`id` = `t_role_right`.`role_id`))) left join `t_right` on((`t_role_right`.`right_id` = `t_right`.`id`)));

SET FOREIGN_KEY_CHECKS = 1;
