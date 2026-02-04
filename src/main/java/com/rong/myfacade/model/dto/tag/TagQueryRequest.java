package com.rong.myfacade.model.dto.tag;

import com.rong.myfacade.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 标签查询请求
 *
 * @author rong
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class TagQueryRequest extends PageRequest implements Serializable {

    /**
     * 标签id
     */
    private Long id;

    /**
     * 标签名称
     */
    private String tagName;

    private static final long serialVersionUID = 1L;
}
