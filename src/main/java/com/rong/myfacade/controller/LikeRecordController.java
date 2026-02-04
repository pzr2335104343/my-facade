package com.rong.myfacade.controller;

import com.rong.myfacade.common.BaseResponse;
import com.rong.myfacade.common.ResultUtils;
import com.rong.myfacade.exception.ErrorCode;
import com.rong.myfacade.exception.ThrowUtils;
import com.rong.myfacade.model.dto.likerecord.LikeRecordRequest;
import com.rong.myfacade.model.entity.User;
import com.rong.myfacade.service.LikeRecordService;
import com.rong.myfacade.service.UserService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 评论 控制层。
 *
 * @author rong
 */
@RestController
@RequestMapping("/like")
public class LikeRecordController {

    @Resource
    private LikeRecordService likeRecordService;

    @Resource
    private UserService userService;

    /**
     * 点赞/取消点赞
     */
    @PostMapping("/do-like")
    public BaseResponse<Boolean> doLike(@RequestBody LikeRecordRequest likeRecordRequest, HttpServletRequest request) {
        ThrowUtils.throwIf(likeRecordRequest == null, ErrorCode.PARAMS_ERROR);
        User loginUser = userService.getLoginUser(request);
        Integer targetType = likeRecordRequest.getTargetType();
        Long targetId = likeRecordRequest.getTargetId();
        Long userId = loginUser.getId();
        boolean result = likeRecordService.doLike(targetType, targetId, userId);
        return ResultUtils.success(result);
    }
}
