-- 用户表
create table if not exists user
(
    id            bigint auto_increment comment 'id' primary key,
    userAccount   varchar(256)                           not null comment '账号',
    userPhone     varchar(256) default ''                not null comment '手机号',
    wxOpenid     VARCHAR(128) default ''                not null comment '微信登录标识',
    userPassword  varchar(512)                           not null comment '密码',
    userName      varchar(256)                           null comment '用户昵称',
    userAvatar    varchar(1024)                          null comment '用户头像',
    userProfile   varchar(512)                           null comment '用户简介',
    userRole      varchar(256) default 'user'            not null comment '用户角色：user/admin',
    editTime      datetime     default CURRENT_TIMESTAMP not null comment '编辑时间',
    createTime    datetime     default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime    datetime     default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    isDelete      tinyint      default 0                 not null comment '是否删除',
    UNIQUE KEY uk_userAccount (userAccount),
    UNIQUE KEY uk_userPhone (userPhone),
    UNIQUE KEY uk_wxOpenid (wxOpenid),
    INDEX idx_userName (userName)
) comment '用户' collate = utf8mb4_unicode_ci;

-- 文章表（技术文章核心表）
create table if not exists article
(
    id            bigint auto_increment comment '文章主键id' primary key,
    userId        bigint                                 not null comment '发布人id，关联user表id',
    articleTitle  varchar(512)                           not null comment '文章标题',
    articleCover  varchar(1024)                          null comment '文章封面图',
    articleIntro  varchar(1024)                          null comment '文章简介/摘要',
    articleContent longtext                              not null comment '文章正文内容',
    likeCount     int        default 0  				 null comment '点赞数',
    commentCount  int        default 0  				 null comment '评论数',
    articleView   bigint      default 0                 not null comment '文章阅读量',
    isTop         tinyint     default 0                 not null comment '是否置顶(0-否 1-是)',
    articleStatus tinyint     default 0         not null comment '文章状态(0-未发布 1-已发布)',
    createTime    datetime    default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime    datetime    default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    isDelete     tinyint     default 0                 not null comment '是否删除(0-未删 1-已删)',
    INDEX idx_userId (userId),
    INDEX idx_articleTitle (articleTitle)
) comment '技术文章表' collate = utf8mb4_unicode_ci;

-- 标签表
CREATE TABLE IF NOT EXISTS tag
(
    id         BIGINT AUTO_INCREMENT COMMENT '标签ID' PRIMARY KEY,
    tagName    VARCHAR(50)                           NOT NULL COMMENT '标签名称',
    tagColor   VARCHAR(20)                           NULL COMMENT '标签颜色（十六进制颜色码）',
    userId     BIGINT                                 NULL COMMENT '用户ID（NULL表示系统标签）',
    useCount   INT          DEFAULT 0                 NOT NULL COMMENT '使用次数',
    createTime DATETIME     DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT '创建时间',
    updateTime DATETIME     DEFAULT CURRENT_TIMESTAMP NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    isDelete   TINYINT      DEFAULT 0                 NOT NULL COMMENT '是否删除',
    UNIQUE KEY uk_tagName_userId (tagName, userId),
    INDEX idx_userId (userId)
) COMMENT '标签表' COLLATE = utf8mb4_unicode_ci;

-- 文章-标签关联表
create table if not exists article_tag_relation
(
    id            bigint auto_increment comment '关联主键id' primary key,
    articleId     bigint                                 not null comment '文章id，关联article表id',
    tagId         bigint                                 not null comment '标签id，关联article_tag表id',
    createTime    datetime    default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime    datetime    default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间(自动刷新)',
    isDelete     tinyint     default 0                  not null comment '是否删除 0-未删 1-已删',
    UNIQUE KEY uk_article_tag (articleId, tagId), -- 避免同一篇文章重复绑定同一个标签
    INDEX idx_articleId (articleId),
    INDEX idx_tagId (tagId)
) comment '文章标签关联表' collate = utf8mb4_unicode_ci;

-- 评论表
CREATE TABLE IF NOT EXISTS comment
(
    id          BIGINT AUTO_INCREMENT COMMENT '评论ID' PRIMARY KEY,
    userId      BIGINT                                 NOT NULL COMMENT '评论用户ID',
    articleId   BIGINT                                 NOT NULL COMMENT '文章ID',
    parentId    BIGINT       DEFAULT 0                 NOT NULL COMMENT '父评论ID（0表示顶级评论）',
    content     TEXT                                   NOT NULL COMMENT '评论内容',
    likeCount   INT          DEFAULT 0                 NOT NULL COMMENT '点赞数',
    status      TINYINT      DEFAULT 1                 NOT NULL COMMENT '状态：0-隐藏，1-正常',
    createTime  DATETIME     DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT '创建时间',
    updateTime  DATETIME     DEFAULT CURRENT_TIMESTAMP NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    isDelete    TINYINT      DEFAULT 0                 NOT NULL COMMENT '是否删除',
    INDEX idx_userId (userId),
    INDEX idx_parentId (parentId),
    INDEX idx_createTime (createTime)
) COMMENT '评论表' COLLATE = utf8mb4_unicode_ci;

-- 点赞表
CREATE TABLE IF NOT EXISTS like_collect
(
    id         BIGINT AUTO_INCREMENT COMMENT 'ID' PRIMARY KEY,
    userId     BIGINT                                 NOT NULL COMMENT '用户ID',
    targetType TINYINT      DEFAULT 0                 NOT NULL COMMENT '目标类型：0-文章，1-评论',
    targetId   BIGINT                                 NOT NULL COMMENT '目标ID',
    createTime DATETIME     DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT '创建时间',
    updateTime DATETIME     DEFAULT CURRENT_TIMESTAMP NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    isDelete   TINYINT      DEFAULT 0                 NOT NULL COMMENT '是否删除',
    UNIQUE KEY uk_user_target (userId, targetType, targetId),
    INDEX idx_target (targetType, targetId)
) COMMENT '点赞表' COLLATE = utf8mb4_unicode_ci;