package com.rong.myfacade.model.dto.article;

import lombok.Data;

import java.io.Serializable;

/**
 * 文章更新请求
 *
 * @author rong
 */
@Data
public class ArticleUpdateRequest implements Serializable {

    /**
     * 文章id
     */
    private Long id;

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
     * 文章状态(0-草稿 1-已发布 2-审核中)
     */
    private Integer articleStatus;

    private static final long serialVersionUID = 1L;
}
