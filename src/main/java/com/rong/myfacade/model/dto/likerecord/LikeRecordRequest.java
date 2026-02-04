package com.rong.myfacade.model.dto.likerecord;

import lombok.Data;

import java.io.Serializable;

/**
 * 标签添加请求
 *
 * @author rong
 */
@Data
public class LikeRecordRequest implements Serializable {

    /**
     * 目标类型：0-文章，1-评论
     */
    private Integer targetType;

    /**
     * 目标ID
     */
    private Long targetId;

}
