package com.lzhphantom.user_center.service.impl;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import com.lzhphantom.user_center.model.domain.User;
import com.lzhphantom.user_center.model.request.UserRegisterRequest;
import com.lzhphantom.user_center.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;

import static com.lzhphantom.user_center.constants.UserConstant.SALT;

@SpringBootTest
class UserServiceImplTest {
    @Resource
    private UserService userService;

    @Test
    void userRegister() {
        User user = new User();
        user.setUsername("lzhphantom");
        user.setLoginAccount("admin");
        user.setAvatarUrl("");
        user.setGender(1);
        String encryptPassword = DigestUtils.md5DigestAsHex((SALT + "test@1234").getBytes());
        user.setPassword(encryptPassword);
        user.setPhone("12345678901");
        user.setEmail("test@test.com");
        user.setCreateTime(LocalDateTime.now());
        user.setUpdateTime(LocalDateTime.now());
        user.setRole(1);


        boolean save = userService.save(user);
        assert save;


    }

    void doLogin() {
    }

    void getSafetyUser() {
    }

    @Test
    void searchUsersByTags() {

        List<String> tags = Arrays.asList("123","456");
        List<User> users = userService.searchUsersByTags(tags);
        Assert.notNull(users,"123");
    }
}