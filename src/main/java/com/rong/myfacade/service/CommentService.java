package com.rong.myfacade.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rong.myfacade.model.dto.comment.CommentQueryRequest;
import com.rong.myfacade.model.entity.Comment;
import com.rong.myfacade.model.entity.User;
import com.rong.myfacade.model.vo.CommentVO;

import java.util.List;

/**
 * 评论 服务层。
 *
 * @author rong
 */
public interface CommentService extends IService<Comment> {

    /**
     * 根据评论信息获取评论VO
     */
    CommentVO getCommentVO(Comment comment, User currentUser);

    /**
     * 根据评论列表获取评论VO列表
     */
    List<CommentVO> getCommentVOList(List<Comment> commentList);

    /**
     * 根据评论查询条件获取QueryWrapper
     */
    QueryWrapper<Comment> getQueryWrapper(CommentQueryRequest commentQueryRequest);

    /**
     * 增加文章评论数
     */
    void incrementArticleCommentCount(Long articleId);

    /**
     * 减少文章评论数
     */
    void decrementArticleCommentCount(Long articleId);
}
