package com.rong.myfacade.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rong.myfacade.model.dto.user.UserQueryRequest;
import com.rong.myfacade.model.entity.User;
import com.rong.myfacade.model.vo.LoginUserVO;
import com.rong.myfacade.model.vo.UserVO;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

/**
 * 用户 服务层。
 *
 * @author rong
 */
public interface UserService extends IService<User> {

    /**
     * 用户注册
     */
    long userRegister(String userAccount, String userPassword, String checkPassword);

    /**
     * 用户登录
     */
    LoginUserVO userLogin(String userAccount, String userPassword, HttpServletRequest request);

    /**
     * 用户注销
     */
    Boolean userLogout(HttpServletRequest request);

    /**
     * 获取加密后的密码
     */
    String getEncryptPassword(String userPassword);

    /**
     * 获取当前登录用户信息(脱敏)
     */
    LoginUserVO getLoginUserVO(User user);

    /**
     * 获取当前登录用户信息
     */
    User getLoginUser(HttpServletRequest request);

    /**
     * 根据用户信息获取用户VO
     */
    UserVO getUserVO(User user);

    /**
     * 根据用户列表获取用户VO列表
     */
    List<UserVO> getUserVOList(List<User> userList);

    /**
     * 根据用户查询条件获取QueryWrapper
     */
    QueryWrapper<User> getQueryWrapper(UserQueryRequest userQueryRequest);

}
