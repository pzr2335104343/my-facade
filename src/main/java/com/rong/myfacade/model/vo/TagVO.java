package com.rong.myfacade.model.vo;

import lombok.Data;

import java.util.Date;

/**
 * 标签视图对象
 *
 * @author rong
 */
@Data
public class TagVO {

    /**
     * 标签id
     */
    private Long id;

    /**
     * 标签名称
     */
    private String tagName;

    /**
     * 标签颜色
     */
    private String tagColor;

    /**
     * 创建时间
     */
    private Date createTime;
}
