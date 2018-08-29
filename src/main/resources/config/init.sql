
-- 用户
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `type` int(2) NOT NULL DEFAULT 1,
  `state` bit NOT NULL DEFAULT 1,
  `expired` bit NOT NULL DEFAULT 0,
  `locked` bit NOT NULL DEFAULT 0,
  `name` varchar(255) DEFAULT NULL,
  `sex` int(2) DEFAULT NULL,
  `mobile` varchar(20) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `wechat` varchar(50) DEFAULT NULL,
  `qq` varchar(20) DEFAULT NULL,
  `create_time` DATETIME,
  `creator_id` int(11) DEFAULT NULL,
  `alter_time` DATETIME,
  `alteror_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_user_username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;
alter table sys_user add index IK_user_username (username) ;

insert into sys_user (username, password, name) values ('panyh', '$2a$10$LbvpOkzBGzWy/LrSkTMlVuIOcJuTO/tR/7W.GPAlJGzCIr9JTmcsm', '潘永辉');

-- 角色（权限）
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `alias` varchar(255) NOT NULL,
  `desc` varchar(255) DEFAULT NULL,
  `model` varchar(255) DEFAULT NULL,
  `url` varchar(255) DEFAULT NULL,
  `create_time` DATETIME,
  `creator_id` int(11) DEFAULT NULL,
  `alter_time` DATETIME,
  `alteror_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

-- 用户与角色
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL,
  `role_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=100 DEFAULT CHARSET=utf8;
alter table sys_user_role add index IK_ur_user_id (user_id) ;
alter table sys_user_role add index IK_ur_role_id (role_id) ;

