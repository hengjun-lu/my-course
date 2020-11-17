-- insert  into `test` (`id`,`name`) values (3,'测试')
-- 如果数据库中有这张表就删掉
drop table if exists `chapter`;
CREATE TABLE `chapter` (
                            `id`  char(32) NOT NULL COMMENT 'id' ,
                            `course_id`  char(32) NULL COMMENT '课程ID' ,
                            `name`  varchar(50) NULL COMMENT '名称' ,
                            PRIMARY KEY (`id`)
)engine=innodb default  charset=utf8mb4 comment ='大章';
-- engine=innodb 表示将数据库的引擎设置为InnoDB,从MySQL 5.6开始默认使用该引擎。
insert  into `chapter` (id,course_id,name) values ('00000001','00000000','测试大章01');
insert  into `chapter` (id,course_id,name) values ('00000002','00000001','测试大章02');
insert  into `chapter` (id,course_id,name) values ('00000003','00000000','测试大章03');
insert  into `chapter` (id,course_id,name) values ('00000004','00000001','测试大章04');
insert  into `chapter` (id,course_id,name) values ('00000005','00000000','测试大章05');
insert  into `chapter` (id,course_id,name) values ('00000006','00000001','测试大章06');
insert  into `chapter` (id,course_id,name) values ('00000007','00000000','测试大章07');
insert  into `chapter` (id,course_id,name) values ('00000008','00000001','测试大章08');
insert  into `chapter` (id,course_id,name) values ('00000009','00000000','测试大章09');
insert  into `chapter` (id,course_id,name) values ('00000010','00000001','测试大章10');
insert  into `chapter` (id,course_id,name) values ('00000011','00000000','测试大章11');
insert  into `chapter` (id,course_id,name) values ('00000012','00000001','测试大章12');
insert  into `chapter` (id,course_id,name) values ('00000013','00000000','测试大章13');
insert  into `chapter` (id,course_id,name) values ('00000014','00000001','测试大章14');
insert  into `chapter` (id,course_id,name) values ('00000015','00000000','测试大章15');
insert  into `chapter` (id,course_id,name) values ('00000016','00000001','测试大章16');

-- 小节
drop table if exists `section`;
CREATE TABLE `section` (
    `id`  char(32) NOT NULL default '' COMMENT 'ID' ,
    `title`  varchar(50) NOT NULL COMMENT '标题' ,
    `course_id`  char(32) NULL COMMENT '课程ID' ,
    `chapter_id`  char(32) NULL COMMENT '大章ID' ,
    `video`  varchar(200) NULL COMMENT '视频' ,
    `time`  int NULL COMMENT '时长|单位秒' ,
    `charge`  char(1) NULL COMMENT '收费|C收费；F免费' ,
    `sort`  int NULL COMMENT '顺序' ,
    `created_at`  datetime(3) NULL COMMENT '创建时间' ,
    `updated_at`  datetime(3) NULL COMMENT '修改时间' ,
    PRIMARY KEY (`id`)
)engine=innodb default  charset=utf8mb4 comment ='小节';
insert  into `section` (id,title,course_id,chapter_id,video,time,charge,sort,created_at,updated_at)
values ('00000001','测试小节','00000001','00000000','',500,'F',1,now(),now());

alter table `section` add column (`vod` CHAR(32) comment 'VOD|阿里云VOD');

-- 课程
drop table if exists `course`;
CREATE TABLE `course` (
    `id`  char(32) NOT NULL DEFAULT '' COMMENT 'ID' ,
    `name`  varchar(50) NOT NULL COMMENT '名称' ,
    `summary`  varchar(2000) NULL COMMENT '概述' ,
    `time`  int default 0 COMMENT '时间|时分秒' ,
    `price`  decimal(8,2)  default 0.00 COMMENT '价格(元)' ,
    `image`  varchar(100) NULL COMMENT '封面' ,
    `level`  char(1)  COMMENT '级别|ONE("1","初级"),TWO("2","中级"),THREE("3","高级")' ,
    `charge`  char(1) NULL COMMENT '收费|CHARGE("C","收费"),FREE("F","免费")' ,
    `status`  char(1) NULL COMMENT '状态|PUBLISH("P","发布"),DRAFT("D","草稿")' ,
    `enroll`  integer DEFAULT 0 NULL COMMENT '报名数' ,
    `sort`  int  COMMENT '顺序' ,
    `created_at`  datetime(3) NULL COMMENT '创建时间' ,
    `updated_at`  datetime(3) NULL COMMENT '修改时间' ,
    PRIMARY KEY (`id`)
)engine=innodb default  charset=utf8mb4 comment ='课程';
insert  into `course` (id,name,summary,time,price,image,level,charge,status,enroll,sort,created_at,updated_at)
values ('00000001','测试课程01','这是一门测试课程','7200','19.9','',1,'C','P','100','0',now(),now());

alter table `course` add column (`teacher_id` char(32) comment '讲师|teacher.id');


-- 创建分类表
drop table if exists `category`;
CREATE TABLE `category` (
    `id`  char(32) NOT NULL COMMENT 'id' ,
    `parent`  char(32) NOT NULL COMMENT '父id' ,
    `name`  varchar(50) NOT NULL COMMENT '名称' ,
    `sort`  int  COMMENT '排序' ,
    PRIMARY KEY (`id`)
)engine=innodb default  charset=utf8mb4 comment ='分类';
insert into `category` (id,parent,name,sort)values('00000100','00000000','前端技术',100);
insert into `category` (id,parent,name,sort)values('00000101','00000100','html/css',101);
insert into `category` (id,parent,name,sort)values('00000102','00000100','javascript',102);
insert into `category` (id,parent,name,sort)values('00000103','00000100','vue.js',103);
insert into `category` (id,parent,name,sort)values('00000104','00000100','react.js',104);
insert into `category` (id,parent,name,sort)values('00000105','00000100','angular.js',105);
insert into `category` (id,parent,name,sort)values('00000106','00000100','node.js',106);
insert into `category` (id,parent,name,sort)values('00000107','00000100','jquery.js',107);
insert into `category` (id,parent,name,sort)values('00000108','00000100','小程序',108);

insert into `category` (id,parent,name,sort)values('00000200','00000000','后端技术',200);
insert into `category` (id,parent,name,sort)values('00000201','00000200','java',201);
insert into `category` (id,parent,name,sort)values('00000202','00000200','spring boot',202);
insert into `category` (id,parent,name,sort)values('00000203','00000200','spring cloud',203);
insert into `category` (id,parent,name,sort)values('00000204','00000200','ssm',204);
insert into `category` (id,parent,name,sort)values('00000205','00000200','python',205);
insert into `category` (id,parent,name,sort)values('00000206','00000200','爬虫',206);

insert into `category` (id,parent,name,sort)values('00000300','00000000','移动开发',300);
insert into `category` (id,parent,name,sort)values('00000301','00000300','android',301);
insert into `category` (id,parent,name,sort)values('00000302','00000300','ios',302);
insert into `category` (id,parent,name,sort)values('00000303','00000300','react native',303);
insert into `category` (id,parent,name,sort)values('00000304','00000300','inoic',304);

insert into `category` (id,parent,name,sort)values('00000400','00000000','前沿技术',400);
insert into `category` (id,parent,name,sort)values('00000401','00000400','微服务',401);
insert into `category` (id,parent,name,sort)values('00000402','00000400','区块链',402);
insert into `category` (id,parent,name,sort)values('00000403','00000400','机器学习',403);
insert into `category` (id,parent,name,sort)values('00000404','00000400','深度学习',404);
insert into `category` (id,parent,name,sort)values('00000405','00000400','数据分析&挖掘',405);

insert into `category` (id,parent,name,sort)values('00000500','00000000','云计算&大数据',500);
insert into `category` (id,parent,name,sort)values('00000501','00000500','大数据',501);
insert into `category` (id,parent,name,sort)values('00000502','00000500','Hadoop',502);
insert into `category` (id,parent,name,sort)values('00000503','00000500','spark',503);
insert into `category` (id,parent,name,sort)values('00000504','00000500','Hbase',504);
insert into `category` (id,parent,name,sort)values('00000505','00000500','Storm',505);
insert into `category` (id,parent,name,sort)values('00000506','00000500','云计算',506);
insert into `category` (id,parent,name,sort)values('00000507','00000500','AMS',507);
insert into `category` (id,parent,name,sort)values('00000508','00000500','Docker',508);
insert into `category` (id,parent,name,sort)values('00000509','00000500','kubernetes',509);

-- 创建课程与分类的关系表
drop table if exists `course_category`;
CREATE TABLE `course_category` (
    `id`  char(32) NOT NULL default '' COMMENT 'id' ,
    `course_id`  char(32)  COMMENT '课程ID' ,
    `category_id`  char(32)  COMMENT '分类ID' ,
    PRIMARY KEY (`id`)
)engine=innodb default  charset=utf8mb4 comment ='课程分类关系';


-- 创建课程内容表
drop table if exists `course_content`;
CREATE TABLE `course_content` (
       `id`  char(32) NOT NULL default '' COMMENT '课程id' ,
       `content`  mediumtext not null  COMMENT '课程内容' ,
       PRIMARY KEY (`id`)
)engine=innodb default  charset=utf8mb4 comment ='课程内容表';

-- 课程内容文件
drop table if exists `course_content_file`;
CREATE TABLE `course_content_file` (
        `id`  char(32) NOT NULL default '' COMMENT 'id' ,
        `course_id` char(32) not null comment '课程ID',
        `url` varchar(100) comment '地址',
        `name` varchar(10) comment '文件名',
        `size` int comment '大小|字节B',
        PRIMARY KEY (`id`)
)engine=innodb default  charset=utf8mb4 comment ='课程文件内容';



-- 讲师
drop table if exists `teacher`;
CREATE TABLE `teacher` (
      `id`  char(32) NOT NULL default '' COMMENT 'id' ,
      `name` varchar(50) not null comment '姓名',
      `nickname` varchar(50) comment '昵称',
      `image` varchar(200) comment '头像',
      `position` varchar(50) comment '职位',
      `motto` varchar(50) comment '座右铭',
      `intro` varchar(50) comment '简介',
      PRIMARY KEY (`id`)
)engine=innodb default  charset=utf8mb4 comment ='讲师';

-- 文件
drop table if exists `file`;
CREATE TABLE `file` (
       `id`  char(32) NOT NULL default '' COMMENT 'id' ,
       `path` varchar(100) not null comment '相对路径',
       `name` varchar(100) comment '文件名',
       `suffix` varchar(10) comment '后缀',
       `size` int comment '大小|字节B',
       `use` char(1) comment '用途|枚举[FileUseEnum]:COURSE("C","讲师"),TEACHER("T","课程")',
       `created_at`  datetime(3) NULL COMMENT '创建时间' ,
       `updated_at`  datetime(3) NULL COMMENT '修改时间' ,
       PRIMARY KEY (`id`),
       unique key `paht_unique` (`path`)
)engine=innodb default  charset=utf8mb4 comment ='文件';

alter table `file` add column (`shard_index` int comment '已上传分片');
alter table `file` add column (`shard_size` int comment '分片大小|B');
alter table `file` add column (`shard_total` int comment '分片总数');
alter table `file` add column (`key` varchar(32) comment '文件标识');
alter table `file` add unique key key_unique(`key`);

alter table `file` add column (`vod` varchar(32) comment 'vod|阿里云vod');

#用户表
drop table if exists `user`;
CREATE TABLE `user` (
        `id`  char(32) NOT NULL default '' COMMENT 'id' ,
        `login_name` varchar(50) not null comment '登陆名',
        `name` varchar(50) comment '昵称',
        `password` char(32) NOT NULL comment '密码',
        PRIMARY KEY (`id`),
        unique key `lgoin_name_unique` (`login_name`)
)engine=innodb default  charset=utf8mb4 comment ='用户';

insert into `user` (id,login_name,name,password) values
('100000000','test','测试','1de4f233a3e9460ca4adce002a43dfc6');



#资源
drop table if exists `resource`;
CREATE TABLE `resource` (
        `id`  char(32) NOT NULL default '' COMMENT 'id' ,
        `name` varchar(100) not null comment '名称|菜单或按钮',
        `page` varchar(100) NULL comment '页面|路由',
        `request` char(200)  NULL comment '请求|接口',
        `parent` char(32)  comment '父ID',
        PRIMARY KEY (`id`)
)engine=innodb default  charset=utf8mb4 comment ='资源';

insert into `resource`  values ('01','系统管理','null','null','null');
insert into `resource`  values ('0101','用户管理','/system/user','null','01');
insert into `resource`  values ('010101','保存','null','["/system/admin/user/list","/system/admin/user/save"]','0101');
insert into `resource`  values ('010102','删除','null','["/system/admin/user/delete"]','0101');
insert into `resource`  values ('010103','重置秘密','null','["/system/admin/user/save-password"]','0101');
insert into `resource`  values ('0102','资源管理','/system/resource','null','01');
insert into `resource`  values ('010201','保存/显示','null','["/system/admin/resource"]','0102');
insert into `resource`  values ('0103','角色管理','/system/role','null','01');
insert into `resource`  values ('010301','角色管理/权限','null','["/system/admin/role"]','0103');

#角色表
drop table if exists `role`;
CREATE TABLE `role` (
            `id`  char(32) NOT NULL default '' COMMENT 'id' ,
            `name` varchar(50) not null comment '角色',
            `desc` varchar(100) not null comment '描述',
            PRIMARY KEY (`id`)
)engine=innodb default  charset=utf8mb4 comment ='角色';

insert into `role`  values ('00000000','系统管理员','用户管理、角色权限');
insert into `role`  values ('00000001','开发','维护资源');
insert into `role`  values ('00000002','业务管理员','负责业务管理');

#角色资源关联表
drop table if exists `role_resource`;
CREATE TABLE `role_resource`(
            `id`  char(32) NOT NULL default '' COMMENT 'id' ,
            `role_id` char(32) not null comment '角色|id',
            `resource_id` char(32) not null comment '资源|id',
            PRIMARY KEY (`id`)
)engine=innodb default  charset=utf8mb4 comment ='角色资源关联';

insert into `role_resource`  values ('00000000','00000000','01');
insert into `role_resource`  values ('00000001','00000000','0101');
insert into `role_resource`  values ('00000002','00000000','010101');
insert into `role_resource`  values ('00000003','00000000','010102');
insert into `role_resource`  values ('00000004','00000000','010103');
insert into `role_resource`  values ('00000005','00000000','0102');
insert into `role_resource`  values ('00000006','00000000','010201');
insert into `role_resource`  values ('00000007','00000000','0103');
insert into `role_resource`  values ('00000008','00000000','010301');


#角色资源关联表
drop table if exists `role_user`;
CREATE TABLE `role_user`(
        `id`  char(32) NOT NULL default '' COMMENT 'id' ,
        `role_id` char(32) not null comment '角色|id',
        `user_id` char(32) not null comment '用户|id',
        PRIMARY KEY (`id`)
)engine=innodb default  charset=utf8mb4 comment ='角色用户关联表';

insert into `role_user`  values ('00000000','00000000','10000000');

-- 会员
drop table if exists `member`;
CREATE TABLE `member`(
        `id`  char(32) NOT NULL default '' COMMENT 'id' ,
        `mobile` varchar(11) NOT NULL comment '手机号',
        `password` char(32) not null comment '密码',
        `name` varchar(100) comment '昵称',
        `photo` varchar(100) comment '头像url',
        `register_time` datetime(3) comment '注册时间',
        PRIMARY KEY (`id`),
        unique KEY `mobile_unique`(`mobile`)
)engine=innodb default  charset=utf8mb4 comment ='会员';

insert into `member` (id,mobile,password,name,photo,register_time)
 values ('00000000','13785295287','f3085aa4ef61c3ef5678dc0e1ba3ac9d','测试web账户',null,now());


-- 验证码
drop table if exists  `sms`;
CREATE TABLE `sms`(
         `id`  char(32) NOT NULL default '' COMMENT 'id' ,
         `mobile` varchar(50) NOT NULL comment '手机号',
         `code` char(6) not null comment '验证码',
         `use` char(2) not null comment '用途|枚举[SmsUseEnum]:REGISTER("R","注册"),FORGET("F","忘记密码")',
         `at` datetime not null comment '生成时间',
         `status` char(2) not null comment '状态|枚举[SmsStatusEnum]:USED("U","已使用",NOT_USED("N","未使用"))',
         PRIMARY KEY (`id`)
)engine=innodb default  charset=utf8mb4 comment ='验证码';

insert into `sms` (id,mobile,code,`use`,at,status) values ('00000000','13131331313','1234','R',now(),'N');

-- 会员报名
drop table if exists  `member_course`;
CREATE TABLE `member_course`(
          `id`  char(32) NOT NULL default '' COMMENT 'id' ,
          `member_id` char(32) NOT NULL comment '会员ID',
          `course_id` char(32) not null comment '课程ID',
          `at` datetime not null comment '报名时间',
          PRIMARY KEY (`id`),
          unique key `member_course_unique` (`member_id`,`course_id`)
)engine=innodb default  charset=utf8mb4 comment ='会员报名';






