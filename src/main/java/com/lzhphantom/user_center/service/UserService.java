package com.lzhphantom.user_center.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lzhphantom.user_center.model.domain.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lzhphantom.user_center.model.request.UserRegisterRequest;
import com.lzhphantom.user_center.model.request.UserTagSearchRequest;
import com.lzhphantom.user_center.model.vo.UserVo;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author lzhphantom
 * @description 针对表【user(用户)】的数据库操作Service
 * @createDate 2024-12-06 17:14:55
 */
public interface UserService extends IService<User> {

    /**
     * 用户注册
     *
     * @param dto 用户注册类
     * @return 用户ID
     */
    long userRegister(UserRegisterRequest dto);

    /**
     * 用户登录
     *
     * @param userAccount  用户账号
     * @param userPassword 用户密码
     * @param request      请求
     * @return 用户信息
     */
    User doLogin(String userAccount, String userPassword, HttpServletRequest request);

    /**
     * 用户脱敏
     * @param one 原用户
     * @return 脱敏用户
     */
    User getSafetyUser(User one);

    /**
     * 用户注销
     * @param request 请求
     */
    int userLogout(HttpServletRequest request);

    /**
     * 根据标签搜索用户
     *
     * @param request 标签列
     * @return 用户数量
     */
    Page<User> searchUsersByTags(UserTagSearchRequest request);

    /**
     * 更新用户
     * @param user
     * @param request
     * @return
     */
    Integer updateUser(User user,HttpServletRequest request);

    /**
     * 获取当前登录用户
     * @param request
     * @return
     */
    User getLoginUser(HttpServletRequest request);

    boolean isAdmin(HttpServletRequest request);

    boolean isAdmin(User user);


    List<UserVo> matchUser(long num, User loginUser);
}
