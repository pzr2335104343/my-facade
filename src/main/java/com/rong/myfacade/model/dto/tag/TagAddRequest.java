package com.rong.myfacade.model.dto.tag;

import lombok.Data;

import java.io.Serializable;

/**
 * 标签添加请求
 *
 * @author rong
 */
@Data
public class TagAddRequest implements Serializable {

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
