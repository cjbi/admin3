-- MySQL dump 10.13  Distrib 5.7.17, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: wetech_admin
-- ------------------------------------------------------
-- Server version	5.7.19

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `sys_log`
--

DROP TABLE IF EXISTS `sys_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
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
) ENGINE=InnoDB AUTO_INCREMENT=7602 DEFAULT CHARSET=utf8 COMMENT='系统日志表';
/*!40101 SET character_set_client = @saved_cs_client */;


--
-- Table structure for table `sys_organization`
--

DROP TABLE IF EXISTS `sys_organization`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_organization` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `name` varchar(100) DEFAULT NULL COMMENT '名称',
  `parent_id` bigint(20) DEFAULT NULL COMMENT '父编号',
  `parent_ids` varchar(100) DEFAULT NULL COMMENT '父编号列表',
  `available` tinyint(1) DEFAULT '0' COMMENT '是否可用',
  `priority` int(11) DEFAULT NULL COMMENT '优先级',
  `leaf` tinyint(1) DEFAULT '0' COMMENT '叶子节点',
  PRIMARY KEY (`id`),
  KEY `idx_sys_organization_parent_id` (`parent_id`),
  KEY `idx_sys_organization_parent_ids` (`parent_ids`)
) ENGINE=InnoDB AUTO_INCREMENT=41 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_organization`
--

LOCK TABLES `sys_organization` WRITE;
/*!40000 ALTER TABLE `sys_organization` DISABLE KEYS */;
INSERT INTO `sys_organization` VALUES (1,'总公司',0,'0/',1,0,0),(2,'分公司',1,'0/1/',1,1,0),(3,'分公司33',1,'0/1/',1,23,0),(4,'分公司10',2,'0/1/2/',1,221,0),(17,'一级节点',1,'0/1/',1,122,0),(18,'二级节点',17,'0/1/17/',1,NULL,0),(19,'三级节点',18,'0/1/17/18/',0,NULL,0),(24,'133',2,'0/1/2/',1,121212,0),(25,'222',3,'0/1/3/',1,3333,0),(29,'222',24,'0/1/2/24/',1,333,0),(32,'232',3,'0/1/3/',1,2323,0),(37,'333',32,'0/1/3/32/',1,444,0),(38,'222',2,'0/1/2/',1,3333,0),(40,'333',25,'0/1/3/25/',1,232332,0);
/*!40000 ALTER TABLE `sys_organization` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_resource`
--

DROP TABLE IF EXISTS `sys_resource`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_resource` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `name` varchar(100) DEFAULT NULL COMMENT '名称',
  `type` varchar(50) DEFAULT NULL COMMENT '资源类型',
  `url` varchar(200) DEFAULT NULL COMMENT '链接地址',
  `parent_id` bigint(20) DEFAULT NULL COMMENT '父编号',
  `parent_ids` varchar(100) DEFAULT NULL COMMENT '父编号列表',
  `permission` varchar(100) DEFAULT NULL COMMENT '权限字符串',
  `available` tinyint(1) DEFAULT '0' COMMENT '是否有效',
  `icon` varchar(100) DEFAULT NULL COMMENT '图标',
  `priority` int(11) DEFAULT NULL COMMENT '优先级',
  `leaf` tinyint(1) DEFAULT '0' COMMENT '叶子节点',
  PRIMARY KEY (`id`),
  KEY `idx_sys_resource_parent_id` (`parent_id`),
  KEY `idx_sys_resource_parent_ids` (`parent_ids`)
) ENGINE=InnoDB AUTO_INCREMENT=83 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_resource`
--

LOCK TABLES `sys_resource` WRITE;
/*!40000 ALTER TABLE `sys_resource` DISABLE KEYS */;
INSERT INTO `sys_resource` VALUES (1,'资源','menu','#',0,'0/','',1,'',0,0),(11,'组织机构管理','menu','#',1,'0/1/','organization:*',1,'fa fa-sitemap',3,0),(12,'组织机构新增','button','',11,'0/1/11/','organization:create',1,NULL,NULL,0),(13,'组织机构修改','button','',11,'0/1/11/','organization:update',1,NULL,NULL,0),(14,'组织机构删除','button','',11,'0/1/11/','organization:delete',1,NULL,NULL,0),(15,'组织机构查看','button','',11,'0/1/11/','organization:view',1,NULL,NULL,0),(21,'用户管理','menu','#',1,'0/1/','user:*',1,'fa fa-user',1,0),(22,'用户新增','button','',46,'0/1/21/','user:create',1,NULL,NULL,0),(23,'用户修改','button','',46,'0/1/21/','user:update',1,'',NULL,0),(24,'用户删除','button','',46,'0/1/21/','user:delete',1,NULL,NULL,0),(25,'用户查看','button','',46,'0/1/21/','user:view',1,NULL,NULL,0),(31,'资源管理','menu','#',1,'0/1/','resource:*',1,'fa fa-desktop',2,0),(32,'资源新增','button','',31,'0/1/31/','resource:create',1,NULL,NULL,0),(33,'资源修改','button','',31,'0/1/31/','resource:update',1,'',NULL,0),(34,'资源删除','button','',31,'0/1/31/','resource:delete',1,NULL,NULL,0),(35,'资源查看','button','',31,'0/1/31/','resource:view',1,NULL,NULL,0),(41,'角色管理','menu','#role',21,'0/1/','role:*',1,'fa fa-child',2,1),(42,'角色新增','button','',41,'0/1/41/','role:create',1,NULL,NULL,0),(43,'角色修改','button','',41,'0/1/41/','role:update',1,NULL,NULL,0),(44,'角色删除','button','',41,'0/1/41/','role:delete',1,NULL,NULL,0),(45,'角色查看','button','',41,'0/1/41/','role:view',1,NULL,NULL,0),(46,'系统用户','menu','#user',21,'0/1/11/','user:*',1,'fa fa-wrench',1,1),(47,'组织机构','menu','#organization',11,'0/1/11/','user:*',1,'fa fa-suitcase',NULL,1),(50,'资源管理','menu','#resource',31,'0/1/','resource:*',1,'fa fa-cubes',NULL,1),(69,'系统管理','menu','#',1,'0/1/','system:*',1,'fa fa-wrench',4,0),(70,'系统日志','menu','#log',69,'0/1/69/','log:*',1,'fa fa-history',NULL,1),(71,'代码生成器','menu','#generator',69,'0/1/69/','',1,'fa fa-code',NULL,1),(73,'一级菜单','menu','#',1,'0/1/','',1,'',999,0),(74,'二级菜单','menu','#',73,'0/1/73/','',1,'',NULL,1);
/*!40000 ALTER TABLE `sys_resource` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_role`
--

DROP TABLE IF EXISTS `sys_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `role` varchar(100) DEFAULT NULL COMMENT '角色',
  `description` varchar(100) DEFAULT NULL COMMENT '描述',
  `resource_ids` varchar(100) DEFAULT NULL COMMENT '资源编号列表',
  `available` tinyint(1) DEFAULT '0' COMMENT '是否有效',
  PRIMARY KEY (`id`),
  KEY `idx_sys_role_resource_ids` (`resource_ids`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_role`
--

LOCK TABLES `sys_role` WRITE;
/*!40000 ALTER TABLE `sys_role` DISABLE KEYS */;
INSERT INTO `sys_role` VALUES (1,'admin','超级管理员','11,21,31,41,69,70',0),(2,'guest','访客','15,25,35,45',0),(4,'ceshi3','测试的角色','12,14,34,35',0),(5,'test','测试角色','12,13,14,43,47',0),(6,'测试1','222','73',0);
/*!40000 ALTER TABLE `sys_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_group`
--

DROP TABLE IF EXISTS `sys_group`;
CREATE TABLE `sys_group` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `name` varchar(100) DEFAULT NULL COMMENT '组名称',
  `type` varchar(50) DEFAULT NULL COMMENT '组类型',
  `description` varchar(100) DEFAULT NULL COMMENT '描述',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COMMENT '用户组表';

--
-- Table structure for table `sys_user`
--

DROP TABLE IF EXISTS `sys_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `organization_id` bigint(20) DEFAULT NULL COMMENT '组织机构编号',
  `username` varchar(100) DEFAULT NULL COMMENT '用户名',
  `password` varchar(100) DEFAULT NULL COMMENT '密码',
  `salt` varchar(100) DEFAULT NULL COMMENT '盐值',
  `role_ids` varchar(100) DEFAULT NULL COMMENT '角色列表',
  `group_id` bigint(20) DEFAULT NULL COMMENT '用户组',
  `locked` tinyint(1) DEFAULT '0' COMMENT '是否锁定',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_sys_user_username` (`username`),
  KEY `idx_sys_user_organization_id` (`organization_id`),
  KEY `idx_sys_user_group_id` (`organization_id`)
) ENGINE=InnoDB AUTO_INCREMENT=48 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_user`
--

LOCK TABLES `sys_user` WRITE;
/*!40000 ALTER TABLE `sys_user` DISABLE KEYS */;
INSERT INTO `sys_user` VALUES (1,1,'admin','d3c59d25033dbf980d29554025c23a75','8d78869f470951332959580424d4bf4f','1',0),(2,2,'ceshi','c067bee5cf7f216f352011cb9064241e','2632e0a577dba732bbfb4b298ff0b6e5','4',0),(4,4,'ceshi22323','f0c6227912db35e27eaa82308be055cd','a2425da3d41a6ef79a1ec69a7d845767','1',0),(9,3,'guest22223','6622b95a30cf730253550985718102bc','7de124e9822c092d56750969eebcf260','1,2',0),(10,3,'guest','fdd97263888fb3767d55ff084751c125','9f1bdc22981ae8730e89f94d241a73cc','2',0),(16,17,'12434','f17bf5e211de8a5cb611c4b6d83b728b','ffb57814a69d37ec1898282d4090cbfc','2',0),(25,3,'admin212','f51764c4e21a7226f92ce79bef273a33','b184db3641404a412c28d0863f46054e','2,4',0),(27,4,'1208','0aff55b46e9c7f91f622c0f579f6b43f','3f83092c188d3de0695d260aec6ac4c1','1,2,4,5',0),(28,17,'12234343','3e5657ac9987290841d99ed6f575f4e0','b48f560c40f4a53a0793184e45992f3d','1,2,4,5',0),(33,3,'ada','150f2974610a97f26f9f295329e24afc','ec55c34cc25616aff30c0cc08f43f9eb','2,4,5',0),(36,3,'232323','4ffa547335faf5b18ccff6e9a227af88','8e3272e3ac9aac76370af12692f993ce','1,2',0),(37,3,'ceshcie','cca5985abc9a89f4d29e2e02aa6afca2','b789c54f40d592cdeaa17c452cee27b2','4',0),(38,3,'1233445','6283a19b895a571a2637a3bec3215cf3','34245e29965cfcd25da1fa671f5047d3','2',0),(39,3,'12334','677a3e446421395315aeb52844959acd','4e8f403e236e5910a2d13532796963bd','4',0),(40,3,'1221','3a7b7a1d99a5e23fb9f9e1e88da499b0','f91e00aadbdea44b8d3a18f490173eb6','1',0),(41,3,'123456','2aa2c7bf65db34867eb4fd7a49b8b18f','685e4e4a2a9d57ccb2a3bd24ee01691a','5',0),(42,2,'123434','355ff1f1a7c60a82b9d0685ec6406987','821b7c15dcf108502a1a024a5746e7b7','2',0),(44,3,'admin2','6e327ade642adb0d344b9909f7d9f990','ccf50557508af772c13bedfcc320c4f1','1,2,4,5',0),(45,3,'cee','ca9708541f98523b5c0b88661b2bb559','3ea093a5cb06042f825fcdb51b76cb5c','1,2,4,5',0),(46,3,'admin222','13503530117f28b48fc32a1eea299bf0','7292e549e492358c0233fde9e1c46ac3','1,2,4',0),(47,37,'admin333','387a1e939344d1dee392a7c02debb34b','28e95c4e30c53414e5b63cca2427e173','1,2,4',0);
/*!40000 ALTER TABLE `sys_user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-04-19 20:45:58
