-- 关闭外键约束检查
set foreign_key_checks = 0;

INSERT INTO user (id, avatar, created_time, full_name, gender, state, username) VALUES (1, 'https://picsum.photos/id/237/100', '2023-01-05 17:16:11', '系统管理员', 0, 0, 'admin');
INSERT INTO user (id, avatar, created_time, full_name, gender, state, username) VALUES (2, 'https://picsum.photos/id/237/100', '2023-01-05 17:16:11', '及时雨', 0, 0, '宋江');
INSERT INTO user (id, avatar, created_time, full_name, gender, state, username) VALUES (3, 'https://picsum.photos/id/237/100', '2023-01-05 17:16:11', '玉麒麟', 0, 0, '卢俊义');
INSERT INTO user (id, avatar, created_time, full_name, gender, state, username) VALUES (4, 'https://picsum.photos/id/237/100', '2023-01-05 17:16:11', '智多星', 0, 0, '吴用');
INSERT INTO user (id, avatar, created_time, full_name, gender, state, username) VALUES (5, 'https://picsum.photos/id/237/100', '2023-01-05 17:16:11', '入云龙', 0, 0, '公孙胜');
INSERT INTO user (id, avatar, created_time, full_name, gender, state, username) VALUES (6, 'https://picsum.photos/id/237/100', '2023-01-05 17:16:11', '大刀', 0, 0, '关胜');
INSERT INTO user (id, avatar, created_time, full_name, gender, state, username) VALUES (7, 'https://picsum.photos/id/237/100', '2023-01-05 17:16:11', '豹子头', 0, 0, '林冲');
INSERT INTO user (id, avatar, created_time, full_name, gender, state, username) VALUES (8, 'https://picsum.photos/id/237/100', '2023-01-05 17:16:11', '霹雳火', 0, 0, '秦明');
INSERT INTO user (id, avatar, created_time, full_name, gender, state, username) VALUES (9, 'https://picsum.photos/id/237/100', '2023-01-05 17:16:11', '双鞭', 0, 1, '呼延灼');
INSERT INTO user (id, avatar, created_time, full_name, gender, state, username) VALUES (10, 'https://picsum.photos/id/237/100', '2023-01-05 17:16:11', '小李广', 0, 1, '花荣');
INSERT INTO user (id, avatar, created_time, full_name, gender, state, username) VALUES (11, 'https://picsum.photos/id/237/100', '2023-01-05 17:16:11', '小旋风', 0, 1, '柴进');
INSERT INTO user (id, avatar, created_time, full_name, gender, state, username) VALUES (12, 'https://picsum.photos/id/237/100', '2023-01-05 17:16:11', '母夜叉', 1, 1, '孙二娘');

INSERT INTO user_credential (id, credential, identifier, identity_type, user_id) VALUES (1, 'a66abb5684c45962d887564f08346e8d', 'admin', 0, 1);

INSERT INTO role (id, available, description, name) VALUES (1, true, '超级管理员拥有所有资源', '超级管理员');
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

INSERT INTO resource (type, id, available, name, parent_ids, permission, url, parent_id) VALUES (null, 1, true, '根节点', null, '*', null, null);
INSERT INTO resource (type, id, available, name, parent_ids, permission, url, parent_id) VALUES (0, 2, true, '仪表盘', null, 'dashboard', '/dashboard', 1);
INSERT INTO resource (type, id, available, name, parent_ids, permission, url, parent_id) VALUES (0, 3, true, '系统管理', null, 'sys', '/sys', 1);
INSERT INTO resource (type, id, available, name, parent_ids, permission, url, parent_id) VALUES (0, 4, true, '用户管理', null, 'sys:user:view', '/users', 3);
INSERT INTO resource (type, id, available, name, parent_ids, permission, url, parent_id) VALUES (0, 5, true, '角色管理', null, 'sys:role:view', '/roles', 3);
INSERT INTO resource (type, id, available, name, parent_ids, permission, url, parent_id) VALUES (0, 6, true, '权限资源', null, 'sys:resource:view', '/resources', 3);
INSERT INTO resource (type, id, available, name, parent_ids, permission, url, parent_id) VALUES (1, 7, true, '查看用户', null, 'sys:user:view', null, 4);
INSERT INTO resource (type, id, available, name, parent_ids, permission, url, parent_id) VALUES (1, 8, true, '新增用户', null, 'sys:user:create', null, 4);
INSERT INTO resource (type, id, available, name, parent_ids, permission, url, parent_id) VALUES (1, 9, true, '修改用户', null, 'sys:user:update', null, 4);
INSERT INTO resource (type, id, available, name, parent_ids, permission, url, parent_id) VALUES (1, 10, true, '删除用户', null, 'sys:user:delete', null, 4);
INSERT INTO resource (type, id, available, name, parent_ids, permission, url, parent_id) VALUES (1, 11, true, '查看角色', null, 'sys:role:view', null, 5);
INSERT INTO resource (type, id, available, name, parent_ids, permission, url, parent_id) VALUES (1, 12, true, '新增角色', null, 'sys:role:create', null, 5);
INSERT INTO resource (type, id, available, name, parent_ids, permission, url, parent_id) VALUES (1, 13, true, '修改角色', null, 'sys:role:update', null, 5);
INSERT INTO resource (type, id, available, name, parent_ids, permission, url, parent_id) VALUES (1, 14, true, '删除角色', null, 'sys:role:delete', null, 5);
INSERT INTO resource (type, id, available, name, parent_ids, permission, url, parent_id) VALUES (1, 15, true, '查看资源', null, 'sys:resource:view', null, 6);
INSERT INTO resource (type, id, available, name, parent_ids, permission, url, parent_id) VALUES (1, 16, true, '新增资源', null, 'sys:resource:create', null, 6);
INSERT INTO resource (type, id, available, name, parent_ids, permission, url, parent_id) VALUES (1, 17, true, '修改资源', null, 'sys:resource:update', null, 6);
INSERT INTO resource (type, id, available, name, parent_ids, permission, url, parent_id) VALUES (1, 18, true, '删除资源', null, 'sys:resource:delete', null, 6);


INSERT INTO role_resource (role_id, resource_id) VALUES (1, 1);
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
