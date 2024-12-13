package com.lzhphantom.user_center.service.impl;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import cn.hutool.core.collection.CollUtil;
import com.lzhphantom.user_center.model.domain.User;
import com.lzhphantom.user_center.model.request.UserRegisterRequest;
import com.lzhphantom.user_center.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;
import org.springframework.util.DigestUtils;
import org.springframework.util.StopWatch;

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

    @Test
    void insertUserByMybatisBatch(){
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        final int batchNum = 100000;
        List<User> userList = CollUtil.newArrayList();
        for (int i = 0; i < batchNum; i++) {
            User user = new User();
            user.setUsername("test"+i);
            user.setLoginAccount("test"+i);
            user.setAvatarUrl("https://fastly.jsdelivr.net/npm/@vant/assets/cat.jpeg");
            user.setGender(1);
            String encryptPassword = DigestUtils.md5DigestAsHex((SALT + "test@1234").getBytes());
            user.setPassword(encryptPassword);
            user.setPhone("12345678901");
            user.setEmail("test@test.com");
            user.setTags("[]");
            user.setProfile("个人简介：久啊塑料袋客服经理萨达会计法立卡");
            userList.add(user);
        }
        userService.saveBatch(userList,10000);
        stopWatch.stop();
        System.out.println(stopWatch.getTotalTimeMillis());
    }

    @Test
    void insertUserByConcurrency(){
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        final int batchNum = 100000;
        List<User> userList = CollUtil.newArrayList();
        for (int i = 0; i < batchNum; i++) {
            User user = new User();
            user.setUsername("test"+i);
            user.setLoginAccount("test"+i);
            user.setAvatarUrl("https://fastly.jsdelivr.net/npm/@vant/assets/cat.jpeg");
            user.setGender(1);
            String encryptPassword = DigestUtils.md5DigestAsHex((SALT + "test@1234").getBytes());
            user.setPassword(encryptPassword);
            user.setPhone("12345678901");
            user.setEmail("test@test.com");
            user.setTags("[]");
            user.setProfile("个人简介：久啊塑料袋客服经理萨达会计法立卡");
            userList.add(user);
        }
        // 将数据分成10组
        int numberOfGroups = 10;
        int groupSize = userList.size() / numberOfGroups;
        List<List<User>> groups = CollUtil.newArrayList();
        for (int i = 0; i < numberOfGroups; i++) {
            int fromIndex = i * groupSize;
            int toIndex = (i == numberOfGroups - 1) ? userList.size() : fromIndex + groupSize;
            groups.add(userList.subList(fromIndex, toIndex));
        }
        // 提交任务到线程池
        // 创建一个固定大小的线程池
        ExecutorService executorService = Executors.newFixedThreadPool(numberOfGroups);
        List<CompletableFuture<Void>> futures = CollUtil.newArrayList();
        for (List<User> group : groups) {
            CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
                System.out.println("test"+Thread.currentThread().getName());
                userService.saveBatch(group, 1000);
            }, executorService);
            futures.add(future);
        }
        // 等待所有任务完成
        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
        // 关闭线程池
        executorService.shutdown();
        stopWatch.stop();
        System.out.println(stopWatch.getTotalTimeMillis());
    }
}