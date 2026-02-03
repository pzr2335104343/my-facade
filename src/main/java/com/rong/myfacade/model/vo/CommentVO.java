package com.rong.myfacade.model.vo;

import lombok.Data;

import java.util.Date;

/**
 * 评论视图对象
 *
 * @author rong
 */
@Data
public class CommentVO {

    /**
     * 评论id
     */
    private Long id;

    /**
     * 评论用户id
     */
    private Long userId;

    /**
     * 评论用户昵称
     */
    private String userName;

    /**
     * 评论用户头像
     */
    private String userAvatar;

    /**
     * 文章id
     */
    private Long articleId;

    /**
     * 父评论id
     */
    private Long parentId;


    /**
     * 评论内容
     */
    private String content;

    /**
     * 点赞数
     */
    private Integer likeCount;

    /**
     * 创建时间
     */
    private Date createTime;
}
