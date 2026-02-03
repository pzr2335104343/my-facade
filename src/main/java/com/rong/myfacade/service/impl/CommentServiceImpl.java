package com.rong.myfacade.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rong.myfacade.exception.BusinessException;
import com.rong.myfacade.exception.ErrorCode;
import com.rong.myfacade.mapper.ArticleMapper;
import com.rong.myfacade.mapper.CommentMapper;
import com.rong.myfacade.mapper.UserMapper;
import com.rong.myfacade.model.dto.comment.CommentQueryRequest;
import com.rong.myfacade.model.entity.Article;
import com.rong.myfacade.model.entity.Comment;
import com.rong.myfacade.model.entity.User;
import com.rong.myfacade.model.vo.CommentVO;
import com.rong.myfacade.service.CommentService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 评论 服务层实现。
 *
 * @author rong
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

    @Resource
    private UserMapper userMapper;
    @Resource
    private ArticleMapper articleMapper;

    @Override
    public CommentVO getCommentVO(Comment comment, User currentUser) {
        if (comment == null) {
            return null;
        }
        CommentVO commentVO = new CommentVO();
        BeanUtil.copyProperties(comment, commentVO);

        // 填充用户信息
        if (comment.getUserId() != null) {
            User user = userMapper.selectById(comment.getUserId());
            if (user != null) {
                commentVO.setUserName(user.getUserName());
                commentVO.setUserAvatar(user.getUserAvatar());
            }
        }


        return commentVO;
    }

    @Override
    public List<CommentVO> getCommentVOList(List<Comment> commentList) {
        if (CollUtil.isEmpty(commentList)) {
            return new ArrayList<>();
        }
        // 批量获取用户信息
        List<Long> userIds = commentList.stream()
                .map(Comment::getUserId)
                .distinct()
                .collect(Collectors.toList());
        Map<Long, User> userMap = userMapper.selectByIds(userIds).stream()
                .collect(Collectors.toMap(User::getId, user -> user));
        return commentList.stream().map(comment -> {
            CommentVO commentVO = new CommentVO();
            BeanUtil.copyProperties(comment, commentVO);
            User user = userMap.get(comment.getUserId());
            if (user != null) {
                commentVO.setUserName(user.getUserName());
                commentVO.setUserAvatar(user.getUserAvatar());
            }
            return commentVO;
        }).collect(Collectors.toList());
    }

    @Override
    public QueryWrapper<Comment> getQueryWrapper(CommentQueryRequest commentQueryRequest) {
        if (commentQueryRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "请求参数为空");
        }
        Long id = commentQueryRequest.getId();
        Long articleId = commentQueryRequest.getArticleId();
        Long userId = commentQueryRequest.getUserId();
        Long parentId = commentQueryRequest.getParentId();
        String sortField = commentQueryRequest.getSortField();
        String sortOrder = commentQueryRequest.getSortOrder();

        QueryWrapper<Comment> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", id)
                .eq("articleId", articleId)
                .eq("userId", userId)
                .eq("parentId", parentId)
                .orderBy(StrUtil.isNotBlank(sortField), "ascend".equals(sortOrder), sortField);
        return queryWrapper;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void incrementArticleCommentCount(Long articleId) {
        Article article = articleMapper.selectById(articleId);
        if (article != null) {
            article.setCommentCount(article.getCommentCount() + 1);
            articleMapper.updateById(article);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void decrementArticleCommentCount(Long articleId) {
        Article article = articleMapper.selectById(articleId);
        if (article != null && article.getCommentCount() > 0) {
            article.setCommentCount(article.getCommentCount() - 1);
            articleMapper.updateById(article);
        }
    }
}
