create schema wetech_admin collate utf8mb4_unicode_ci;

create table sys_log
(
    id          bigint auto_increment comment '主键'
        primary key,
    username    varchar(100)                       null comment '用户名',
    ip          varchar(45)                        null comment '用户ip',
    req_method  varchar(200)                       null comment '请求方法',
    req_uri     text                               null comment '请求URL',
    exec_method varchar(200)                       null comment '执行方法',
    exec_time   bigint                             null comment '响应时间',
    args        text                               null comment '参数',
    return_val  text                               null comment '返回值',
    exec_desc   varchar(200)                       null comment '描述',
    status      varchar(45)                        null comment '状态',
    create_time datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP comment '创建时间'
) comment '系统日志表';

create table sys_permission
(
    id         bigint auto_increment comment '编号'
        primary key,
    name       varchar(100)         null comment '名称',
    type       tinyint(1)           not null comment '资源类型(1.菜单 2.按钮或文本块)',
    parent_id  bigint               null comment '父编号',
    parent_ids varchar(100)         null comment '父编号列表',
    permission varchar(100)         null comment '权限字符串',
    icon       varchar(100)         null comment '图标',
    sort       int        default 0 not null comment '排序',
    status     tinyint(1) default 1 null comment '是否有效',
    config     json                 null comment '权限配置'
) comment '资源表';

create index idx_sys_resource_parent_id
    on sys_permission (parent_id);

create index idx_sys_resource_parent_ids
    on sys_permission (parent_ids);

create table sys_role
(
    id             bigint auto_increment comment '编号'
        primary key,
    role           varchar(100)         not null comment '唯一标识',
    name           varchar(100)         null comment '角色名称',
    description    varchar(100)         null comment '描述',
    status         tinyint(1) default 1 null comment '状态 1.正常 0.禁用',
    permission_ids varchar(500)         null comment '资源编号列表'
) comment '角色表';

create index idx_sys_role_resource_ids
    on sys_role (permission_ids);

create table sys_user
(
    id       bigint auto_increment comment '编号'
        primary key,
    username varchar(100)         null comment '用户名',
    password varchar(100)         null comment '密码',
    salt     varchar(100)         null comment '盐值',
    role_ids varchar(100)         null comment '角色列表',
    locked   tinyint(1) default 0 null comment '是否锁定',
    constraint idx_sys_user_username
        unique (username)
) comment '用户表';

