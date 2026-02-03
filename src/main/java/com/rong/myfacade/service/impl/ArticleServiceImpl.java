package com.rong.myfacade.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rong.myfacade.exception.BusinessException;
import com.rong.myfacade.exception.ErrorCode;
import com.rong.myfacade.mapper.ArticleMapper;
import com.rong.myfacade.mapper.UserMapper;
import com.rong.myfacade.model.dto.article.ArticleQueryRequest;
import com.rong.myfacade.model.entity.Article;
import com.rong.myfacade.model.entity.User;
import com.rong.myfacade.model.vo.ArticleVO;
import com.rong.myfacade.service.ArticleService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 文章 服务层实现。
 *
 * @author rong
 */
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {

    @Resource
    private UserMapper userMapper;

    @Override
    public ArticleVO getArticleVO(Article article) {
        if (article == null) {
            return null;
        }
        ArticleVO articleVO = new ArticleVO();
        BeanUtil.copyProperties(article, articleVO);

        // 填充用户信息
        if (article.getUserId() != null) {
            User user = userMapper.selectById(article.getUserId());
            if (user != null) {
                articleVO.setUserName(user.getUserName());
                articleVO.setUserAvatar(user.getUserAvatar());
            }
        }

        return articleVO;
    }

    @Override
    public List<ArticleVO> getArticleVOList(List<Article> articleList) {
        if (CollUtil.isEmpty(articleList)) {
            return new ArrayList<>();
        }

        // 批量获取用户信息
        List<Long> userIds = articleList.stream()
                .map(Article::getUserId)
                .distinct()
                .collect(Collectors.toList());

        Map<Long, User> userMap = userMapper.selectBatchIds(userIds).stream()
                .collect(Collectors.toMap(User::getId, user -> user));

        // 填充用户信息
        return articleList.stream().map(article -> {
            ArticleVO articleVO = new ArticleVO();
            BeanUtil.copyProperties(article, articleVO);
            User user = userMap.get(article.getUserId());
            if (user != null) {
                articleVO.setUserName(user.getUserName());
                articleVO.setUserAvatar(user.getUserAvatar());
            }
            return articleVO;
        }).collect(Collectors.toList());
    }

    @Override
    public QueryWrapper<Article> getQueryWrapper(ArticleQueryRequest articleQueryRequest) {
        if (articleQueryRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "请求参数为空");
        }
        Long id = articleQueryRequest.getId();
        Long userId = articleQueryRequest.getUserId();
        String articleTitle = articleQueryRequest.getArticleTitle();
        Integer articleStatus = articleQueryRequest.getArticleStatus();
        Integer isTop = articleQueryRequest.getIsTop();
        String sortField = articleQueryRequest.getSortField();
        String sortOrder = articleQueryRequest.getSortOrder();

        QueryWrapper<Article> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", id)
                .eq("userId", userId)
                .like(StrUtil.isNotBlank(articleTitle), "articleTitle", articleTitle)
                .eq("articleStatus", articleStatus)
                .eq("isTop", isTop)
                .orderBy(StrUtil.isNotBlank(sortField), "ascend".equals(sortOrder), sortField);
        return queryWrapper;
    }

    @Override
    public void incrementViewCount(Long articleId) {
        Article article = this.getById(articleId);
        if (article != null) {
            article.setArticleView(article.getArticleView() + 1);
            this.updateById(article);
        }
    }
}
