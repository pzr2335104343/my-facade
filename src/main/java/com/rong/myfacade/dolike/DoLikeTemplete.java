package com.rong.myfacade.dolike;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rong.myfacade.constant.StatusConstant;
import com.rong.myfacade.exception.ErrorCode;
import com.rong.myfacade.exception.ThrowUtils;
import com.rong.myfacade.model.entity.LikeRecord;
import com.rong.myfacade.service.LikeRecordService;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Lazy;

public abstract class DoLikeTemplete {

    @Lazy
    @Resource
    private LikeRecordService likeRecordService;

    public boolean doLike(Integer targetType, Long targetId, Long userId) {
        // 1.判断是否存在点赞记录
        LikeRecord likeRecord = getLike(targetType, targetId, userId);
        boolean result;
        boolean isLiked;
        if (likeRecord == null) {
            result = addLike(targetType, targetId, userId);
            isLiked = false;
        } else {
            result = modifyLike(likeRecord);
            isLiked = likeRecord.getStatus().equals(StatusConstant.NORMAL);
        }
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR, "点赞操作失败");
        // 2.修改对应的点赞数量
        boolean modifyCountResult = modifyLikeCount(targetId, isLiked);
        ThrowUtils.throwIf(!modifyCountResult, ErrorCode.OPERATION_ERROR, "点赞数量更新失败");
        return true;
    }

    /**
     * 点赞数修改
     *
     * @param targetId 目标Id
     * @param isLiked  点赞状态
     * @return 执行结果
     */
    protected abstract boolean modifyLikeCount(Long targetId, boolean isLiked);


    /**
     * 获取点赞状态
     *
     * @param targetType 目标类型
     * @param targetId   目标Id
     * @param userId     用户Id
     * @return 点赞状态
     */
    private LikeRecord getLike(Integer targetType, Long targetId, Long userId) {
        QueryWrapper<LikeRecord> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("targetType", targetType)
                .eq("targetId", targetId)
                .eq("userId", userId);
        return likeRecordService.getOne(queryWrapper);
    }

    /**
     * 点赞/取消点赞
     *
     * @param likeRecord 目标记录
     * @return 执行结果
     */
    private boolean modifyLike(LikeRecord likeRecord) {
        boolean isLiked = likeRecord.getStatus().equals(StatusConstant.NORMAL);
        likeRecord.setStatus(isLiked ? StatusConstant.INVALID : StatusConstant.NORMAL);
        return likeRecordService.updateById(likeRecord);
    }

    /**
     * 新增点赞
     *
     * @param targetType 目标类型
     * @param targetId   目标Id
     * @param userId     用户Id
     * @return 执行结果
     */
    private boolean addLike(Integer targetType, Long targetId, Long userId) {
        LikeRecord likeRecord = new LikeRecord();
        likeRecord.setTargetType(targetType);
        likeRecord.setTargetId(targetId);
        likeRecord.setUserId(userId);
        likeRecord.setStatus(StatusConstant.NORMAL);
        return likeRecordService.save(likeRecord);
    }

}
