package com.rong.myfacade.controller;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rong.myfacade.annotation.AuthCheck;
import com.rong.myfacade.common.BaseResponse;
import com.rong.myfacade.common.DeleteRequest;
import com.rong.myfacade.common.ResultUtils;
import com.rong.myfacade.constant.UserConstant;
import com.rong.myfacade.exception.ErrorCode;
import com.rong.myfacade.exception.ThrowUtils;
import com.rong.myfacade.model.dto.tag.TagAddRequest;
import com.rong.myfacade.model.dto.tag.TagQueryRequest;
import com.rong.myfacade.model.dto.tag.TagUpdateRequest;
import com.rong.myfacade.model.entity.Tag;
import com.rong.myfacade.service.TagService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 标签 控制层。
 *
 * @author rong
 */
@RestController
@RequestMapping("/tag")
public class TagController {

    @Resource
    private TagService tagService;

    /**
     * 创建标签
     */
    @PostMapping("/add")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Long> addTag(@RequestBody TagAddRequest tagAddRequest) {
        ThrowUtils.throwIf(tagAddRequest == null, ErrorCode.PARAMS_ERROR);
        Tag tag = new Tag();
        BeanUtil.copyProperties(tagAddRequest, tag);
        boolean result = tagService.save(tag);
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
        return ResultUtils.success(tag.getId());
    }

    /**
     * 更新标签
     */
    @PostMapping("/update")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Boolean> updateTag(@RequestBody TagUpdateRequest tagUpdateRequest) {
        ThrowUtils.throwIf(tagUpdateRequest == null || tagUpdateRequest.getId() == null, ErrorCode.PARAMS_ERROR);
        Tag oldTag = tagService.getById(tagUpdateRequest.getId());
        ThrowUtils.throwIf(oldTag == null, ErrorCode.NOT_FOUND_ERROR);
        Tag tag = new Tag();
        BeanUtil.copyProperties(tagUpdateRequest, tag);
        boolean result = tagService.updateById(tag);
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
        return ResultUtils.success(true);
    }

    /**
     * 删除标签（管理员）
     */
    @PostMapping("/delete")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Boolean> deleteTag(@RequestBody DeleteRequest deleteRequest) {
        ThrowUtils.throwIf(deleteRequest == null || deleteRequest.getId() <= 0, ErrorCode.PARAMS_ERROR);
        boolean result = tagService.removeById(deleteRequest.getId());
        return ResultUtils.success(result);
    }

    /**
     * 分页获取标签列表（管理员）
     */
    @PostMapping("/list/page")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Page<Tag>> listTagByPage(@RequestBody TagQueryRequest tagQueryRequest) {
        ThrowUtils.throwIf(tagQueryRequest == null, ErrorCode.PARAMS_ERROR);
        long pageNum = tagQueryRequest.getPageNum();
        long pageSize = tagQueryRequest.getPageSize();
        Page<Tag> tagPage = tagService.page(Page.of(pageNum, pageSize),
                tagService.getQueryWrapper(tagQueryRequest));
        return ResultUtils.success(tagPage);
    }
}