package com.rong.myfacade.dolike;


import com.rong.myfacade.model.entity.Article;
import com.rong.myfacade.service.ArticleService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

@Component
public class ArticleDoLikeTemplete extends DoLikeTemplete {

    @Resource
    private ArticleService articleService;

    /**
     * 点赞数修改
     *
     * @param targetId 目标Id
     */
    @Override
    protected boolean modifyLikeCount(Long targetId, boolean isLiked) {
        Article article = articleService.getById(targetId);
        Integer objCount = article.getLikeCount() + (isLiked ? -1 : 1);
        article.setLikeCount(objCount);
        return articleService.updateById(article);
    }

}
