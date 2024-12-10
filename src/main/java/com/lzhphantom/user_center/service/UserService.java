package com.lzhphantom.user_center.service;

import com.lzhphantom.user_center.model.domain.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lzhphantom.user_center.model.request.UserRegisterRequest;

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
     * @param tags 标签列
     * @return 用户数量
     */
    List<User> searchUsersByTags(List<String> tags);
}
