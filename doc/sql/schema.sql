create schema wetech_admin collate utf8mb4_unicode_ci;

DROP TABLE IF EXISTS `sys_log`;
CREATE TABLE `sys_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `username` varchar(100) DEFAULT NULL COMMENT '用户名',
  `ip` varchar(45) DEFAULT NULL COMMENT '用户ip',
  `req_method` varchar(200) DEFAULT NULL COMMENT '请求方法',
  `req_uri` text COMMENT '请求URL',
  `exec_method` varchar(200) DEFAULT NULL COMMENT '执行方法',
  `exec_time` bigint(20) DEFAULT NULL COMMENT '响应时间',
  `args` text COMMENT '参数',
  `return_val` text COMMENT '返回值',
  `exec_desc` varchar(200) DEFAULT NULL COMMENT '描述',
  `status` varchar(45) DEFAULT NULL COMMENT '状态',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`)
) COMMENT='系统日志表';

DROP TABLE IF EXISTS `sys_permission`;
CREATE TABLE `sys_permission` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `name` varchar(100) DEFAULT NULL COMMENT '名称',
  `type` tinyint(1) NOT NULL COMMENT '资源类型(1.菜单 2.按钮或文本块)',
  `parent_id` bigint(20) DEFAULT NULL COMMENT '父编号',
  `parent_ids` varchar(100) DEFAULT NULL COMMENT '父编号列表',
  `permission` varchar(100) DEFAULT NULL COMMENT '权限字符串',
  `icon` varchar(100) DEFAULT NULL COMMENT '图标',
  `sort` int(11) NOT NULL DEFAULT '0' COMMENT '排序',
  `status` tinyint(1) DEFAULT '1' COMMENT '是否有效',
  `config` json DEFAULT NULL COMMENT '权限配置',
  PRIMARY KEY (`id`),
  KEY `idx_sys_resource_parent_id` (`parent_id`),
  KEY `idx_sys_resource_parent_ids` (`parent_ids`)
) COMMENT='资源表';

create index idx_sys_resource_parent_id
    on sys_permission (parent_id);

create index idx_sys_resource_parent_ids
    on sys_permission (parent_ids);

DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `role` varchar(100) NOT NULL COMMENT '唯一标识',
  `name` varchar(100) DEFAULT NULL COMMENT '角色名称',
  `description` varchar(100) DEFAULT NULL COMMENT '描述',
  `status` tinyint(1) DEFAULT '1' COMMENT '状态 1.正常 0.禁用',
  `permission_ids` varchar(500) DEFAULT NULL COMMENT '资源编号列表',
  PRIMARY KEY (`id`),
  KEY `idx_sys_role_resource_ids` (`permission_ids`)
) COMMENT='角色表';

DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `username` varchar(100) DEFAULT NULL COMMENT '用户名',
  `password` varchar(100) DEFAULT NULL COMMENT '密码',
  `salt` varchar(100) DEFAULT NULL COMMENT '盐值',
  `role_ids` varchar(100) DEFAULT NULL COMMENT '角色列表',
  `locked` tinyint(1) DEFAULT '0' COMMENT '是否锁定',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_sys_user_username` (`username`)
) COMMENT='用户表';

