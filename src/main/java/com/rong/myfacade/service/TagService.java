package com.rong.myfacade.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rong.myfacade.model.dto.tag.TagQueryRequest;
import com.rong.myfacade.model.entity.Tag;
import com.rong.myfacade.model.vo.TagVO;

import java.util.List;

/**
 * 标签 服务层。
 *
 * @author rong
 */
public interface TagService extends IService<Tag> {

    /**
     * 根据标签信息获取标签VO
     */
    TagVO getTagVO(Tag tag);

    /**
     * 根据标签列表获取标签VO列表
     */
    List<TagVO> getTagVOList(List<Tag> tagList);

    /**
     * 根据标签查询条件获取QueryWrapper
     */
    QueryWrapper<Tag> getQueryWrapper(TagQueryRequest tagQueryRequest);
}
