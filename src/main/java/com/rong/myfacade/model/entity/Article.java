package com.rong.myfacade.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 文章
 * article
 */
@TableName(value = "article")
@Data
public class Article implements Serializable {
    /**
     * 文章主键id
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 发布人id，关联user表id
     */
    private Long userId;

    /**
     * 文章标题
     */
    private String articleTitle;

    /**
     * 文章封面图
     */
    private String articleCover;

    /**
     * 文章简介/摘要
     */
    private String articleIntro;

    /**
     * 文章正文内容
     */
    private String articleContent;

    /**
     * 标签列表(json 数组)
     */
    private String tags;

    /**
     * 点赞数
     */
    private Integer likeCount;

    /**
     * 评论数
     */
    private Integer commentCount;

    /**
     * 文章阅读量
     */
    private Long articleView;

    /**
     * 是否置顶(0-否 1-是)
     */
    private Integer isTop;

    /**
     * 文章状态(0-草稿 1-已发布 2-审核中)
     */
    private Integer articleStatus;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 是否删除(0-未删 1-已删)
     */
    @TableLogic
    private Integer isDelete;

}
