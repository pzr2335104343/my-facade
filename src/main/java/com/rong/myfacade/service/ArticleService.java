package com.rong.myfacade.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rong.myfacade.model.dto.article.ArticleQueryRequest;
import com.rong.myfacade.model.entity.Article;
import com.rong.myfacade.model.vo.ArticleVO;

import java.util.List;

/**
 * 文章 服务层。
 *
 * @author rong
 */
public interface ArticleService extends IService<Article> {

    /**
     * 根据文章信息获取文章VO
     */
    ArticleVO getArticleVO(Article article);

    /**
     * 根据文章列表获取文章VO列表
     */
    List<ArticleVO> getArticleVOList(List<Article> articleList);

    /**
     * 根据文章查询条件获取QueryWrapper
     */
    QueryWrapper<Article> getQueryWrapper(ArticleQueryRequest articleQueryRequest);

    /**
     * 增加文章阅读量
     */
    void incrementViewCount(Long articleId);

    /**
     * 根据标签ID列表搜索文章
     */
    List<Article> searchByTagIds(List<Long> tagIds);
}
