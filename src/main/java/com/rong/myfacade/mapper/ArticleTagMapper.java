package com.rong.myfacade.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rong.myfacade.model.entity.ArticleTag;
import org.apache.ibatis.annotations.Mapper;

/**
 * 文章标签关联 映射层
 *
 * @author rong
 */
@Mapper
public interface ArticleTagMapper extends BaseMapper<ArticleTag> {

}
