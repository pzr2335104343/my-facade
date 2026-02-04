package com.rong.myfacade.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rong.myfacade.exception.BusinessException;
import com.rong.myfacade.exception.ErrorCode;
import com.rong.myfacade.mapper.TagMapper;
import com.rong.myfacade.model.dto.tag.TagQueryRequest;
import com.rong.myfacade.model.entity.Tag;
import com.rong.myfacade.model.vo.TagVO;
import com.rong.myfacade.service.TagService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 标签 服务层实现。
 *
 * @author rong
 */
@Service
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements TagService {

    @Override
    public TagVO getTagVO(Tag tag) {
        if (tag == null) {
            return null;
        }
        TagVO tagVO = new TagVO();
        BeanUtil.copyProperties(tag, tagVO);
        return tagVO;
    }

    @Override
    public List<TagVO> getTagVOList(List<Tag> tagList) {
        if (CollUtil.isEmpty(tagList)) {
            return new ArrayList<>();
        }
        return tagList.stream().map(this::getTagVO).toList();
    }

    @Override
    public QueryWrapper<Tag> getQueryWrapper(TagQueryRequest tagQueryRequest) {
        if (tagQueryRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "请求参数为空");
        }
        Long id = tagQueryRequest.getId();
        String tagName = tagQueryRequest.getTagName();
        String sortField = tagQueryRequest.getSortField();
        String sortOrder = tagQueryRequest.getSortOrder();

        QueryWrapper<Tag> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", id)
                .like(StrUtil.isNotBlank(tagName), "tagName", tagName)
                .orderBy(StrUtil.isNotBlank(sortField), "ascend".equals(sortOrder), sortField);
        return queryWrapper;
    }

}
