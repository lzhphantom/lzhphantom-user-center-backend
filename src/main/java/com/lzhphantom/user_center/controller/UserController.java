package com.lzhphantom.user_center.controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjUtil;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lzhphantom.user_center.common.BaseResponse;
import com.lzhphantom.user_center.common.ErrorCode;
import com.lzhphantom.user_center.common.ResultUtils;
import com.lzhphantom.user_center.exception.BusinessException;
import com.lzhphantom.user_center.model.domain.User;
import com.lzhphantom.user_center.model.request.UserLoginRequest;
import com.lzhphantom.user_center.model.request.UserRegisterRequest;
import com.lzhphantom.user_center.model.request.UserTagSearchRequest;
import com.lzhphantom.user_center.service.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static com.lzhphantom.user_center.constants.UserConstant.USER_LOGIN_STATE;

/**
 * 用户接口
 *
 * @author lzhphantom
 */
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor(onConstructor_ = {@Lazy})
public class UserController {
    private final UserService userService;

    private final RedisTemplate<String, Object> redisTemplate;

    @PostMapping("/login")
    public BaseResponse<User> doLogin(@RequestBody UserLoginRequest userLoginRequest, HttpServletRequest request) {
        if (Objects.isNull(userLoginRequest)) {
            throw new BusinessException(ErrorCode.PARAMS_NULL_ERROR);
        }
        if (StringUtils.isAnyBlank(userLoginRequest.getUsername(), userLoginRequest.getPassword())) {
            throw new BusinessException(ErrorCode.PARAMS_NULL_ERROR);
        }
        return ResultUtils.success(userService.doLogin(userLoginRequest.getUsername(), userLoginRequest.getPassword(), request));
    }

    @PostMapping("/logout")
    public BaseResponse<Integer> userLogout(HttpServletRequest request) {
        if (Objects.isNull(request)) {
            throw new BusinessException(ErrorCode.PARAMS_NULL_ERROR);
        }
        return ResultUtils.success(userService.userLogout(request));
    }

    @GetMapping("/currentUser")
    public BaseResponse<User> getCurrentUser(HttpServletRequest request) {
        Object userObj = request.getSession().getAttribute(USER_LOGIN_STATE);
        if (Objects.isNull(userObj)) {
            throw new BusinessException(ErrorCode.PARAMS_NULL_ERROR);
        }
        User currentUser = (User) userObj;
        User user = userService.getById(currentUser.getId());

        return ResultUtils.success(userService.getSafetyUser(user));
    }

    @PostMapping("/register")
    public BaseResponse<Long> userRegister(@RequestBody UserRegisterRequest dto) {
        if (Objects.isNull(dto)) {
            throw new BusinessException(ErrorCode.PARAMS_NULL_ERROR);
        }
        if (StringUtils.isAnyBlank(dto.getUserAccount(), dto.getUserPassword(), dto.getCheckPassword(), dto.getUsername())) {
            throw new BusinessException(ErrorCode.PARAMS_NULL_ERROR);
        }
        return ResultUtils.success(userService.userRegister(dto));
    }

    @GetMapping("/search")
    public BaseResponse<List<User>> searchUser(String username, HttpServletRequest request) {
        if (!userService.isAdmin(request)) {
            throw new BusinessException(ErrorCode.NO_AUTH);
        }
        LambdaQueryChainWrapper<User> lambdaQuery = userService.lambdaQuery();
        if (StringUtils.isNotEmpty(username)) {
            lambdaQuery.like(User::getUsername, username);
        }
        return ResultUtils.success(lambdaQuery.list()
                .stream().map(userService::getSafetyUser).collect(Collectors.toList()));
    }

    @PostMapping("/search/tags")
    public BaseResponse<List<User>> searchUsersByTags(@RequestBody UserTagSearchRequest request) {
        return ResultUtils.success(userService.searchUsersByTags(request.getTagList()));
    }

    @GetMapping("/recommend")
    public BaseResponse<Page<User>> recommendUsers(long pageSize, long pageNum, HttpServletRequest request) {
        User loginUser = userService.getLoginUser(request);
        String recommendUserKey = String.format("lzhphantom:user:recommend:%s", loginUser.getId());
        Page<User> result = (Page<User>) redisTemplate.opsForValue().get(recommendUserKey);
        if (ObjUtil.isNotEmpty(result)) {
            return ResultUtils.success(result);
        }
        Page<User> page = new Page<>(pageNum, pageSize);
        result = userService.page(page);
        result.setRecords(result.getRecords().stream().map(userService::getSafetyUser).collect(Collectors.toList()));
        redisTemplate.opsForValue().set(recommendUserKey, result,60, TimeUnit.SECONDS);
        return ResultUtils.success(result);
    }

    @GetMapping("/delete/{id}")
    public BaseResponse<Boolean> deleteUser(@PathVariable("id") Long id, HttpServletRequest request) {

        if (!userService.isAdmin(request)) {
            throw new BusinessException(ErrorCode.NO_AUTH);
        }
        if (Objects.isNull(id)) {
            throw new BusinessException(ErrorCode.PARAMS_NULL_ERROR);
        }
        return ResultUtils.success(userService.removeById(id));
    }

    @PostMapping("/update")
    public BaseResponse<Integer> updateUser(@RequestBody User user, HttpServletRequest request) {
        if (Objects.isNull(user)) {
            throw new BusinessException(ErrorCode.PARAMS_NULL_ERROR);
        }
        userService.getLoginUser(request);
        return ResultUtils.success(userService.updateUser(user, request));
    }


}
