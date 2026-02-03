package com.rong.myfacade.model.vo;

import lombok.Data;

import java.util.Date;

/**
 * 文章视图对象
 *
 * @author rong
 */
@Data
public class ArticleVO {

    /**
     * 文章id
     */
    private Long id;

    /**
     * 发布人id
     */
    private Long userId;

    /**
     * 发布人昵称
     */
    private String userName;

    /**
     * 发布人头像
     */
    private String userAvatar;

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
     * 是否置顶
     */
    private Integer isTop;

    /**
     * 文章状态
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
}
