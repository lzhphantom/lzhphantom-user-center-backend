DROP TABLE IF EXISTS `user`;

create table user_center.user
(
    id           bigint auto_increment
        primary key,
    username     varchar(100)      not null comment '昵称',
    loginAccount varchar(100)      not null comment '登录账号',
    avatarUrl    varchar(300)      null comment '头像',
    gender       tinyint           null comment '性别',
    password     varchar(100)      null comment '密码',
    phone        varchar(20)       null comment '电话',
    email        varchar(200)      null comment '邮件',
    status       tinyint default 0 not null comment '状态',
    createTime   datetime          null comment '创建时间',
    updateTime   datetime          null comment '更新时间',
    isDelete     tinyint default 0 null comment '是否删除',
    role         tinyint default 0 not null comment '角色'
)
    comment '用户';


