-- 关闭外键约束检查
set foreign_key_checks = 0;

INSERT INTO user (id, avatar, created_time, full_name, gender, state, username, organization_id) VALUES (1, 'https://picsum.photos/id/237/100', '2023-01-05 17:16:11', '系统管理员', 0, 0, 'admin', 1);
INSERT INTO user (id, avatar, created_time, full_name, gender, state, username, organization_id) VALUES (2, 'https://picsum.photos/id/237/100', '2023-01-05 17:16:11', '及时雨', 0, 0, '宋江', 2);
INSERT INTO user (id, avatar, created_time, full_name, gender, state, username, organization_id) VALUES (3, 'https://picsum.photos/id/237/100', '2023-01-05 17:16:11', '玉麒麟', 0, 0, '卢俊义', 2);
INSERT INTO user (id, avatar, created_time, full_name, gender, state, username, organization_id) VALUES (4, 'https://picsum.photos/id/237/100', '2023-01-05 17:16:11', '智多星', 0, 0, '吴用', 2);
INSERT INTO user (id, avatar, created_time, full_name, gender, state, username, organization_id) VALUES (5, 'https://picsum.photos/id/237/100', '2023-01-05 17:16:11', '入云龙', 0, 0, '公孙胜', 2);
INSERT INTO user (id, avatar, created_time, full_name, gender, state, username, organization_id) VALUES (6, 'https://picsum.photos/id/237/100', '2023-01-05 17:16:11', '大刀', 0, 0, '关胜', 2);
INSERT INTO user (id, avatar, created_time, full_name, gender, state, username, organization_id) VALUES (7, 'https://picsum.photos/id/237/100', '2023-01-05 17:16:11', '豹子头', 0, 0, '林冲', 2);
INSERT INTO user (id, avatar, created_time, full_name, gender, state, username, organization_id) VALUES (8, 'https://picsum.photos/id/237/100', '2023-01-05 17:16:11', '霹雳火', 0, 0, '秦明', 2);
INSERT INTO user (id, avatar, created_time, full_name, gender, state, username, organization_id) VALUES (9, 'https://picsum.photos/id/237/100', '2023-01-05 17:16:11', '双鞭', 0, 1, '呼延灼', 2);
INSERT INTO user (id, avatar, created_time, full_name, gender, state, username, organization_id) VALUES (10, 'https://picsum.photos/id/237/100', '2023-01-05 17:16:11', '小李广', 0, 1, '花荣', 2);
INSERT INTO user (id, avatar, created_time, full_name, gender, state, username, organization_id) VALUES (11, 'https://picsum.photos/id/237/100', '2023-01-05 17:16:11', '小旋风', 0, 1, '柴进', 2);
INSERT INTO user (id, avatar, created_time, full_name, gender, state, username, organization_id) VALUES (12, 'https://picsum.photos/id/237/100', '2023-01-05 17:16:11', '母夜叉', 1, 1, '孙二娘', 2);

INSERT INTO user_credential (id, credential, identifier, identity_type, user_id) VALUES (1, 'a66abb5684c45962d887564f08346e8d', 'admin', 0, 1);

INSERT INTO organization (id, name, parent_ids, type, parent_id) VALUES (1, '根节点', '/', 0, null);
INSERT INTO organization (id, name, parent_ids, type, parent_id) VALUES (2, '水浒传', '/1/', 0, 1);

INSERT INTO role (id, available, description, name) VALUES (1, true, '超级管理员可以对企业内的所有用户进行管理，请谨慎修改超管权限', '超级管理员');
INSERT INTO role (id, available, description, name) VALUES (2, true, '项目开发人员', '开发者');
INSERT INTO role (id, available, description, name) VALUES (3, true, '普通的用户', '普通用户');

INSERT INTO user_role (user_id, role_id) VALUES (1, 1);
INSERT INTO user_role (user_id, role_id) VALUES (2, 2);
INSERT INTO user_role (user_id, role_id) VALUES (3, 3);
INSERT INTO user_role (user_id, role_id) VALUES (4, 3);
INSERT INTO user_role (user_id, role_id) VALUES (5, 3);
INSERT INTO user_role (user_id, role_id) VALUES (6, 3);
INSERT INTO user_role (user_id, role_id) VALUES (7, 3);
INSERT INTO user_role (user_id, role_id) VALUES (8, 3);
INSERT INTO user_role (user_id, role_id) VALUES (9, 3);
INSERT INTO user_role (user_id, role_id) VALUES (10, 3);
INSERT INTO user_role (user_id, role_id) VALUES (11, 3);
INSERT INTO user_role (user_id, role_id) VALUES (12, 3);

INSERT INTO resource (id, icon, name, parent_ids, permission, type, url, parent_id) VALUES (1, null, '根节点', null, '*', null, null, null);
INSERT INTO resource (id, icon, name, parent_ids, permission, type, url, parent_id) VALUES (2, 'Odometer', '仪表盘', null, 'dashboard', 0, '/dashboard', 1);
INSERT INTO resource (id, icon, name, parent_ids, permission, type, url, parent_id) VALUES (3, 'SetUp', '系统管理', null, 'sys', 0, '/sys', 1);
INSERT INTO resource (id, icon, name, parent_ids, permission, type, url, parent_id) VALUES (4, 'User', '用户管理', null, 'user:view', 0, '/users', 3);
INSERT INTO resource (id, icon, name, parent_ids, permission, type, url, parent_id) VALUES (5, 'Tickets', '角色管理', null, 'role:view', 0, '/roles', 3);
INSERT INTO resource (id, icon, name, parent_ids, permission, type, url, parent_id) VALUES (6, 'Collection', '权限资源', null, 'resource:view', 0, '/resources', 3);
INSERT INTO resource (id, icon, name, parent_ids, permission, type, url, parent_id) VALUES (7, null, '查看用户', null, 'user:view', 1, null, 4);
INSERT INTO resource (id, icon, name, parent_ids, permission, type, url, parent_id) VALUES (8, null, '新增用户', null, 'user:create', 1, null, 4);
INSERT INTO resource (id, icon, name, parent_ids, permission, type, url, parent_id) VALUES (9, null, '修改用户', null, 'user:update', 1, null, 4);
INSERT INTO resource (id, icon, name, parent_ids, permission, type, url, parent_id) VALUES (10, null, '删除用户', null, 'user:delete', 1, null, 4);
INSERT INTO resource (id, icon, name, parent_ids, permission, type, url, parent_id) VALUES (11, null, '查看角色', null, 'role:view', 1, null, 5);
INSERT INTO resource (id, icon, name, parent_ids, permission, type, url, parent_id) VALUES (12, null, '新增角色', null, 'role:create', 1, null, 5);
INSERT INTO resource (id, icon, name, parent_ids, permission, type, url, parent_id) VALUES (13, null, '修改角色', null, 'role:update', 1, null, 5);
INSERT INTO resource (id, icon, name, parent_ids, permission, type, url, parent_id) VALUES (14, null, '删除角色', null, 'role:delete', 1, null, 5);
INSERT INTO resource (id, icon, name, parent_ids, permission, type, url, parent_id) VALUES (15, null, '查看资源', null, 'resource:view', 1, null, 6);
INSERT INTO resource (id, icon, name, parent_ids, permission, type, url, parent_id) VALUES (16, null, '新增资源', null, 'resource:create', 1, null, 6);
INSERT INTO resource (id, icon, name, parent_ids, permission, type, url, parent_id) VALUES (17, null, '修改资源', null, 'resource:update', 1, null, 6);
INSERT INTO resource (id, icon, name, parent_ids, permission, type, url, parent_id) VALUES (18, null, '删除资源', null, 'resource:delete', 1, null, 6);



INSERT INTO role_resource (role_id, resource_id) VALUES (1, 2);
INSERT INTO role_resource (role_id, resource_id) VALUES (1, 3);
INSERT INTO role_resource (role_id, resource_id) VALUES (1, 4);
INSERT INTO role_resource (role_id, resource_id) VALUES (1, 5);
INSERT INTO role_resource (role_id, resource_id) VALUES (1, 6);
INSERT INTO role_resource (role_id, resource_id) VALUES (1, 7);
INSERT INTO role_resource (role_id, resource_id) VALUES (1, 8);
INSERT INTO role_resource (role_id, resource_id) VALUES (1, 9);
INSERT INTO role_resource (role_id, resource_id) VALUES (1, 10);
INSERT INTO role_resource (role_id, resource_id) VALUES (1, 11);
INSERT INTO role_resource (role_id, resource_id) VALUES (1, 12);
INSERT INTO role_resource (role_id, resource_id) VALUES (1, 13);
INSERT INTO role_resource (role_id, resource_id) VALUES (1, 14);
INSERT INTO role_resource (role_id, resource_id) VALUES (1, 15);
INSERT INTO role_resource (role_id, resource_id) VALUES (1, 16);
INSERT INTO role_resource (role_id, resource_id) VALUES (1, 17);
INSERT INTO role_resource (role_id, resource_id) VALUES (1, 18);

INSERT INTO role_resource (role_id, resource_id) VALUES (2, 2);
INSERT INTO role_resource (role_id, resource_id) VALUES (2, 3);
INSERT INTO role_resource (role_id, resource_id) VALUES (2, 6);
INSERT INTO role_resource (role_id, resource_id) VALUES (2, 15);
INSERT INTO role_resource (role_id, resource_id) VALUES (2, 16);
INSERT INTO role_resource (role_id, resource_id) VALUES (2, 17);
INSERT INTO role_resource (role_id, resource_id) VALUES (2, 18);
INSERT INTO role_resource (role_id, resource_id) VALUES (3, 2);


-- 开启外键约束检查
set foreign_key_checks = 1;
