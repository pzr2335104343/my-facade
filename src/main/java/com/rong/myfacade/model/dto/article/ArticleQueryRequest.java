package com.rong.myfacade.model.dto.article;

import com.rong.myfacade.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 文章查询请求
 *
 * @author rong
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ArticleQueryRequest extends PageRequest implements Serializable {

    /**
     * 文章id
     */
    private Long id;

    /**
     * 发布人id
     */
    private Long userId;

    /**
     * 文章标题
     */
    private String articleTitle;

    /**
     * 文章状态(0-草稿 1-已发布 2-审核中)
     */
    private Integer articleStatus;

    /**
     * 是否置顶
     */
    private Integer isTop;

    private static final long serialVersionUID = 1L;
}
