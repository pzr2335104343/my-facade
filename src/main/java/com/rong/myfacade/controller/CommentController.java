package com.rong.myfacade.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rong.myfacade.annotation.AuthCheck;
import com.rong.myfacade.common.BaseResponse;
import com.rong.myfacade.common.DeleteRequest;
import com.rong.myfacade.common.ResultUtils;
import com.rong.myfacade.constant.StatusConstant;
import com.rong.myfacade.constant.UserConstant;
import com.rong.myfacade.exception.BusinessException;
import com.rong.myfacade.exception.ErrorCode;
import com.rong.myfacade.exception.ThrowUtils;
import com.rong.myfacade.model.dto.comment.CommentAddRequest;
import com.rong.myfacade.model.dto.comment.CommentQueryRequest;
import com.rong.myfacade.model.entity.Article;
import com.rong.myfacade.model.entity.Comment;
import com.rong.myfacade.model.entity.User;
import com.rong.myfacade.model.vo.CommentVO;
import com.rong.myfacade.service.ArticleService;
import com.rong.myfacade.service.CommentService;
import com.rong.myfacade.service.UserService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 评论 控制层。
 *
 * @author rong
 */
@RestController
@RequestMapping("/comment")
public class CommentController {

    @Resource
    private CommentService commentService;

    @Resource
    private UserService userService;

    @Resource
    private ArticleService articleService;

    /**
     * 发布评论
     */
    @PostMapping("/add")
    public BaseResponse<Long> addComment(@RequestBody CommentAddRequest commentAddRequest, HttpServletRequest request) {
        ThrowUtils.throwIf(commentAddRequest == null, ErrorCode.PARAMS_ERROR);
        // 验证文章是否存在
        Article article = articleService.getById(commentAddRequest.getArticleId());
        ThrowUtils.throwIf(article == null, ErrorCode.NOT_FOUND_ERROR, "文章不存在");
        // 验证内容非空
        ThrowUtils.throwIf(commentAddRequest.getContent() == null || commentAddRequest.getContent().trim().isEmpty(),
                ErrorCode.PARAMS_ERROR, "评论内容不能为空");
        User loginUser = userService.getLoginUser(request);

        Comment comment = new Comment();
        comment.setUserId(loginUser.getId());
        comment.setArticleId(commentAddRequest.getArticleId());
        comment.setParentId(commentAddRequest.getParentId() != null ? commentAddRequest.getParentId() : 0L);
        comment.setContent(commentAddRequest.getContent().trim());
        boolean result = commentService.save(comment);
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
        // 增加文章评论数
        commentService.incrementArticleCommentCount(commentAddRequest.getArticleId());
        return ResultUtils.success(comment.getId());
    }

    /**
     * 删除评论（用户本身或管理员）
     */
    @PostMapping("/delete")
    public BaseResponse<Boolean> deleteComment(@RequestBody DeleteRequest deleteRequest, HttpServletRequest request) {
        ThrowUtils.throwIf(deleteRequest == null || deleteRequest.getId() <= 0, ErrorCode.PARAMS_ERROR);
        Comment comment = commentService.getById(deleteRequest.getId());
        ThrowUtils.throwIf(comment == null, ErrorCode.NOT_FOUND_ERROR);
        User loginUser = userService.getLoginUser(request);
        // 只有评论作者本人或管理员可以删除
        if (!comment.getUserId().equals(loginUser.getId()) && !UserConstant.ADMIN_ROLE.equals(loginUser.getUserRole())) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR, "无权删除该评论");
        }
        boolean result = commentService.removeById(deleteRequest.getId());
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
        // 减少文章评论数
        commentService.decrementArticleCommentCount(comment.getArticleId());
        return ResultUtils.success(true);
    }

    /**
     * 根据文章ID获取评论列表
     */
    @PostMapping("/list/byArticle")
    public BaseResponse<List<CommentVO>> listCommentsByArticle(@RequestBody CommentQueryRequest commentQueryRequest) {
        ThrowUtils.throwIf(commentQueryRequest == null || commentQueryRequest.getArticleId() == null, ErrorCode.PARAMS_ERROR);
        QueryWrapper<Comment> queryWrapper = commentService.getQueryWrapper(commentQueryRequest);
        List<Comment> commentList = commentService.list(queryWrapper);
        List<CommentVO> commentVOList = commentService.getCommentVOList(commentList);
        return ResultUtils.success(commentVOList);
    }

    /**
     * 分页获取评论列表
     */
    @PostMapping("/list/page")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Page<Comment>> listCommentByPage(@RequestBody CommentQueryRequest commentQueryRequest) {
        ThrowUtils.throwIf(commentQueryRequest == null, ErrorCode.PARAMS_ERROR);
        long pageNum = commentQueryRequest.getPageNum();
        long pageSize = commentQueryRequest.getPageSize();
        Page<Comment> commentPage = commentService.page(Page.of(pageNum, pageSize),
                commentService.getQueryWrapper(commentQueryRequest));
        return ResultUtils.success(commentPage);
    }
}
