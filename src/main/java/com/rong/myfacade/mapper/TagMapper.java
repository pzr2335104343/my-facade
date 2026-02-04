package com.rong.myfacade.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rong.myfacade.model.entity.Tag;
import org.apache.ibatis.annotations.Mapper;

/**
 * 标签 Mapper 接口
 *
 * @author rong
 */
@Mapper
public interface TagMapper extends BaseMapper<Tag> {

}
