drop table if exists `t_ebook`;
// 电子书表
create table  `t_ebook` (
`id` bigint NOT null comment 'id',
`name` VARCHAR(50) comment '名称',
`category1_id` bigint comment '分类1',
`category2_id` bigint comment '分类2',
`description` VARCHAR(200) comment '描述',
`cover` VARCHAR(200) comment '封面',
`doc_count` int comment '文档数',
`view_count` int comment '阅读数',
`vote_count` int comment '点赞数',
primary key (`id`)
) engine=innodb default charset=utf8mb4 comment='电子书'

insert into `t_ebook` (id, name, description) VALUES (1, 'SpringBoot 入门教材', '零基础Java开发最佳首选教材');

insert into `t_ebook` (id, name, description) VALUES (2, 'Vue 入门教材', '零基础Vue开发最佳首选教材');

insert into `t_ebook` (id, name, description) VALUES (3, 'Java 入门教材', '零基础Java开发最佳首选教材');

insert into `t_ebook` (id, name, description) VALUES (4, 'React 入门教材', '零基础React开发最佳首选教材');

// 分类表
drop table if exists `t_category`;

create table  `t_category` (
`id` bigint NOT null comment 'id',
`parent_id` bigint not null default 0 comment '父id',
`name` VARCHAR(200) comment '封面',
`sort` int comment '顺序',
primary key (`id`)
) engine=innodb default charset=utf8mb4 comment='分类'

insert into `t_category` (id, parent_id, name, sort) VALUES (100, 000, '前端开发', 100);
insert into `t_category` (id, parent_id, name, sort) VALUES (101, 100, 'Vue', 101);
insert into `t_category` (id, parent_id, name, sort) VALUES (102, 100, 'Html', 102);
insert into `t_category` (id, parent_id, name, sort) VALUES (200, 000, 'Java', 200);
insert into `t_category` (id, parent_id, name, sort) VALUES (201, 200, '基础应用', 201);
insert into `t_category` (id, parent_id, name, sort) VALUES (202, 200, '框架应用', 202);
insert into `t_category` (id, parent_id, name, sort) VALUES (203, 200, '性能应用', 203);
insert into `t_category` (id, parent_id, name, sort) VALUES (300, 000, 'Python', 300);
insert into `t_category` (id, parent_id, name, sort) VALUES (301, 300, '基础应用', 301);
insert into `t_category` (id, parent_id, name, sort) VALUES (302, 300, '进阶方向应用', 302);
insert into `t_category` (id, parent_id, name, sort) VALUES (400, 000, '数据库', 400);
insert into `t_category` (id, parent_id, name, sort) VALUES (401, 400, 'Mysql', 401);
insert into `t_category` (id, parent_id, name, sort) VALUES (402, 400, 'Oracle', 402);
insert into `t_category` (id, parent_id, name, sort) VALUES (500, 000, '其他', 500);
insert into `t_category` (id, parent_id, name, sort) VALUES (501, 500, '服务器', 501);
insert into `t_category` (id, parent_id, name, sort) VALUES (502, 500, '开发工具', 502);
insert into `t_category` (id, parent_id, name, sort) VALUES (503, 500, '热门服务器语言', 503);

-- 用户表
drop table if exists `t_user`;

create table `t_user` (
`id` bigint not null comment 'ID',
`login_name` varchar(50) not null comment '登录名',
`name` varchar(50) comment '封面',
`password` char(32) not null comment '密码',
primary key (`id`),
unique key `login_name_unique` (`login_name`)
) engine=innodb default charset=utf8mb4 comment='用户'


-- 文档表
drop table if exists `t_doc`;

create table `t_doc` (
`id` bigint NOT null comment 'id',
`ebook_id` bigint not null default 0 comment '电子书id',
`parent_id` bigint not null default 0 comment '父id',
`name` VARCHAR(50) not null comment '名称',
`sort` int comment '顺序',
`view_count` int default 0 comment '阅读数',
`vote_count` int default 0 comment '点赞数',
primary key (`id`)
) engine=innodb default charset=utf8mb4 comment='文档'

insert into `t_doc` (id, ebook_id, parent_id, name, sort, view_count, vote_count) VALUES (1, 1, 0, '文档1', 1, 0, 0);

insert into `t_doc` (id, ebook_id, parent_id, name, sort, view_count, vote_count) VALUES (2, 1, 1, '文档1.0', 1, 0, 0);
insert into `t_doc` (id, ebook_id, parent_id, name, sort, view_count, vote_count) VALUES (3, 1, 0, '文档2', 1, 0, 0);
insert into `t_doc` (id, ebook_id, parent_id, name, sort, view_count, vote_count) VALUES (4, 1, 3, '文档2.1', 1, 0, 0);
insert into `t_doc` (id, ebook_id, parent_id, name, sort, view_count, vote_count) VALUES (5, 1, 3, '文档2.2', 1, 0, 0);





select * from t_user where id = 55819333145858048;

select * from t_role_menu where role_id = (select * from t_user_role where user_id = 56251244699848704) as user_role ;


select rolemenu.role_id, rolemenu.menu_id from t_role_menu rolemenu, (select * from t_user_role where user_id = 56251244699848704) as userrole  where rolemenu.role_id = userrole.role_id

select rolemenu.id as id, rolemenu.role_id as roleId, rolemenu.menu_id as menuId, t_menu.pid as pId
from
t_role_menu rolemenu,
t_menu,
(select * from t_user_role where user_id = 56251244699848704) as userrole

where rolemenu.role_id = userrole.role_id and rolemenu.menu_id = t_menu.id;


select t2.role_id as roleId, t2.name, t2.description
from t_user_role t1, t_role t2, t_user t3
where t1.role_id = t2.role_id and t3.id = 56251244699848704



