package com.rong.myfacade.dolike;


import com.rong.myfacade.model.entity.Comment;
import com.rong.myfacade.service.CommentService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

@Component
public class CommentDoLikeTemplete extends DoLikeTemplete {

    @Resource
    private CommentService commentService;

    /**
     * 点赞数修改
     *
     * @param targetId 目标Id
     */
    @Override
    protected boolean modifyLikeCount(Long targetId, boolean isLiked) {
        Comment comment = commentService.getById(targetId);
        Integer objCount = comment.getLikeCount() + (isLiked ? -1 : 1);
        comment.setLikeCount(objCount);
        return commentService.updateById(comment);
    }

}
