package com.rong.myfacade.model.dto.comment;

import lombok.Data;

import java.io.Serializable;

/**
 * 评论添加请求
 *
 * @author rong
 */
@Data
public class CommentAddRequest implements Serializable {

    /**
     * 文章ID
     */
    private Long articleId;

    /**
     * 父评论ID（0表示顶级评论）
     */
    private Long parentId;

    /**
     * 评论内容
     */
    private String content;

    private static final long serialVersionUID = 1L;
}
