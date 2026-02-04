package com.rong.myfacade.model.dto.tag;

import lombok.Data;

import java.io.Serializable;

/**
 * 标签更新请求
 *
 * @author rong
 */
@Data
public class TagUpdateRequest implements Serializable {

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

    private static final long serialVersionUID = 1L;
}
