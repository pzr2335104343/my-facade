package com.rong.myfacade.controller;

import cn.hutool.core.bean.BeanUtil;
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
import com.rong.myfacade.model.dto.article.ArticleAddRequest;
import com.rong.myfacade.model.dto.article.ArticleQueryRequest;
import com.rong.myfacade.model.dto.article.ArticleUpdateRequest;
import com.rong.myfacade.model.entity.Article;
import com.rong.myfacade.model.entity.User;
import com.rong.myfacade.model.vo.ArticleVO;
import com.rong.myfacade.service.ArticleService;
import com.rong.myfacade.service.UserService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

/**
 * 文章 控制层。
 *
 * @author rong
 */
@RestController
@RequestMapping("/article")
public class ArticleController {

    @Resource
    private ArticleService articleService;

    @Resource
    private UserService userService;

    /**
     * 创建文章
     */
    @PostMapping("/add")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Long> addArticle(@RequestBody ArticleAddRequest articleAddRequest, HttpServletRequest request) {
        ThrowUtils.throwIf(articleAddRequest == null, ErrorCode.PARAMS_ERROR);
        User loginUser = userService.getLoginUser(request);

        Article article = new Article();
        BeanUtil.copyProperties(articleAddRequest, article);
        article.setUserId(loginUser.getId());
        article.setArticleStatus(StatusConstant.NORMAL);
        boolean result = articleService.save(article);
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
        return ResultUtils.success(article.getId());
    }

    /**
     * 更新文章
     */
    @PostMapping("/update")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Boolean> updateArticle(@RequestBody ArticleUpdateRequest articleUpdateRequest, HttpServletRequest request) {
        ThrowUtils.throwIf(articleUpdateRequest == null || articleUpdateRequest.getId() == null, ErrorCode.PARAMS_ERROR);
        Article oldArticle = articleService.getById(articleUpdateRequest.getId());
        ThrowUtils.throwIf(oldArticle == null, ErrorCode.NOT_FOUND_ERROR);
        User loginUser = userService.getLoginUser(request);
        // 只有作者本人或管理员可以修改
        if (!UserConstant.ADMIN_ROLE.equals(loginUser.getUserRole())) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }
        Article article = new Article();
        BeanUtil.copyProperties(articleUpdateRequest, article);
        boolean result = articleService.updateById(article);
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
        return ResultUtils.success(true);
    }

    /**
     * 根据 id 获取文章
     */
    @GetMapping("/get")
    public BaseResponse<ArticleVO> getArticleById(@RequestParam long id) {
        ThrowUtils.throwIf(id <= 0, ErrorCode.PARAMS_ERROR);
        Article article = articleService.getById(id);
        ThrowUtils.throwIf(article == null, ErrorCode.NOT_FOUND_ERROR);
        // 只返回已发布的文章
        if (article.getArticleStatus() != StatusConstant.NORMAL) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR, "文章未发布");
        }
        // 增加阅读量
        articleService.incrementViewCount(id);
        return ResultUtils.success(articleService.getArticleVO(article));
    }

    /**
     * 根据 id 获取文章（管理员）
     */
    @GetMapping("/get/detail")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<ArticleVO> getArticleDetail(@RequestParam long id, HttpServletRequest request) {
        ThrowUtils.throwIf(id <= 0, ErrorCode.PARAMS_ERROR);
        Article article = articleService.getById(id);
        ThrowUtils.throwIf(article == null, ErrorCode.NOT_FOUND_ERROR);
        User loginUser = userService.getLoginUser(request);
        // 只有作者本人或管理员可以查看未发布的文章
        if (article.getArticleStatus() != 1 && !article.getUserId().equals(loginUser.getId())
                && !UserConstant.ADMIN_ROLE.equals(loginUser.getUserRole())) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR, "无权查看");
        }
        return ResultUtils.success(articleService.getArticleVO(article));
    }

    /**
     * 删除文章（管理员）
     */
    @PostMapping("/delete")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Boolean> deleteArticle(@RequestBody DeleteRequest deleteRequest) {
        ThrowUtils.throwIf(deleteRequest == null || deleteRequest.getId() <= 0, ErrorCode.PARAMS_ERROR);
        boolean result = articleService.removeById(deleteRequest.getId());
        return ResultUtils.success(result);
    }

    /**
     * 分页获取文章列表（管理员）
     */
    @PostMapping("/list/page")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Page<Article>> listArticleByPage(@RequestBody ArticleQueryRequest articleQueryRequest) {
        ThrowUtils.throwIf(articleQueryRequest == null, ErrorCode.PARAMS_ERROR);
        long pageNum = articleQueryRequest.getPageNum();
        long pageSize = articleQueryRequest.getPageSize();
        Page<Article> articlePage = articleService.page(Page.of(pageNum, pageSize),
                articleService.getQueryWrapper(articleQueryRequest));
        return ResultUtils.success(articlePage);
    }

}
