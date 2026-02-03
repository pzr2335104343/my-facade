package com.rong.myfacade.model.dto.article;

import lombok.Data;

import java.io.Serializable;

/**
 * 文章添加请求
 *
 * @author rong
 */
@Data
public class ArticleAddRequest implements Serializable {

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

    private static final long serialVersionUID = 1L;
}
