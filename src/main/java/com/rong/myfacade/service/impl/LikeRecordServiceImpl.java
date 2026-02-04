package com.rong.myfacade.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rong.myfacade.constant.LikeTypeConstant;
import com.rong.myfacade.dolike.ArticleDoLikeTemplete;
import com.rong.myfacade.dolike.CommentDoLikeTemplete;
import com.rong.myfacade.exception.ErrorCode;
import com.rong.myfacade.exception.ThrowUtils;
import com.rong.myfacade.mapper.LikeRecordMapper;
import com.rong.myfacade.model.entity.LikeRecord;
import com.rong.myfacade.service.LikeRecordService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * 点赞记录 服务层。
 *
 * @author rong
 */
@Service
public class LikeRecordServiceImpl extends ServiceImpl<LikeRecordMapper, LikeRecord>
        implements LikeRecordService {


    @Resource
    private ArticleDoLikeTemplete articleDoLikeTemplete;

    @Resource
    private CommentDoLikeTemplete commentDoLikeTemplete;

    @Override
    public boolean doLike(Integer targetType, Long targetId, Long userId) {
        ThrowUtils.throwIf(targetType == null, ErrorCode.PARAMS_ERROR);
        ThrowUtils.throwIf(targetId == null, ErrorCode.PARAMS_ERROR);
        ThrowUtils.throwIf(userId == null, ErrorCode.PARAMS_ERROR);

        if (LikeTypeConstant.LIKE_ARTICLE_TYPE.equals(targetType)) {
            return articleDoLikeTemplete.doLike(targetType, targetId, userId);
        } else if (LikeTypeConstant.LIKE_COMMENT_TYPE.equals(targetType)) {
            return commentDoLikeTemplete.doLike(targetType, targetId, userId);
        }
        return false;
    }

}




