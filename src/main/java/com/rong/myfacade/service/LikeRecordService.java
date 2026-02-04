package com.rong.myfacade.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.rong.myfacade.model.entity.LikeRecord;

/**
 * 点赞记录 服务层。
 *
 * @author rong
 */
public interface LikeRecordService extends IService<LikeRecord> {

    /**
     * 点赞
     *
     * @param targetType
     * @param targetId
     * @param userId
     * @return
     */
    boolean doLike(Integer targetType, Long targetId, Long userId);

}
