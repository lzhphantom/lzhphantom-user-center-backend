package com.lzhphantom.user_center.service.impl;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.*;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.javafaker.Faker;
import com.lzhphantom.user_center.mapper.TeamMapper;
import com.lzhphantom.user_center.model.domain.Team;
import com.lzhphantom.user_center.model.domain.User;
import com.lzhphantom.user_center.model.dto.TeamQuery;
import com.lzhphantom.user_center.model.request.UserRegisterRequest;
import com.lzhphantom.user_center.model.vo.TeamUserVo;
import com.lzhphantom.user_center.service.UserService;
import com.lzhphantom.user_center.utls.AlgorithmUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;
import org.springframework.util.DigestUtils;
import org.springframework.util.StopWatch;

import javax.annotation.Resource;

import static com.lzhphantom.user_center.constants.UserConstant.SALT;

@SpringBootTest
@Slf4j
class UserServiceImplTest {
    @Resource
    private UserService userService;

    @Resource
    private TeamMapper teamMapper;

    private Faker faker;

    @BeforeEach
    void setUp() {
        this.faker = new Faker(new Locale("zh_CN"));
    }

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

    @Test
    void doLogin() {

        System.out.println(faker.programmingLanguage().name());
        System.out.println(faker.options().option("男", "女"));
        System.out.println(faker.options().option("大一", "大二", "大三", "大四", "研究生", "博士"));
    }

    void getSafetyUser() {
    }

    @Test
    void testAlogorithmUils() {
        String str1 = "tabc";
        String str2 = "abcdf";
        String str3 = "abcdfg";
        System.out.println(AlgorithmUtils.computeEditDistance(str1, str2));
        System.out.println(AlgorithmUtils.computeEditDistance(str1, str3));
        HashSet<String> p1 = CollUtil.newHashSet("Java", "大一", "男");
        HashSet<String> p2 = CollUtil.newHashSet("Java", "大二", "男");
        HashSet<String> p3 = CollUtil.newHashSet("C++", "大一", "女");
        System.out.println(AlgorithmUtils.computeJaccardSimilarity(p1, p2));
        System.out.println(AlgorithmUtils.computeJaccardSimilarity(p1, p3));

    }

    @Test
    void userTeamList() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        // 获取拼接的 where 条件
        TeamQuery query = new TeamQuery();
        query.setName("test");
        List<TeamUserVo> teamUserVos = teamMapper.selectTeamsWithDynamicQuery(query);
        stopWatch.stop();
        System.out.println(stopWatch.getTotalTimeMillis());
        System.out.println(teamUserVos.size());

    }

    @Test
    void searchUsersByTags() {

    }

    @Test
    void insertUserByMybatisBatch() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        final int batchNum = 100000;
        List<User> userList = CollUtil.newArrayList();
        for (int i = 0; i < batchNum; i++) {
            User user = new User();
            user.setUsername(faker.name().fullName());
            user.setLoginAccount("test" + i);
            user.setAvatarUrl("https://fastly.jsdelivr.net/npm/@vant/assets/cat.jpeg");
            user.setGender(i % 2 == 0 ? 1 : 0);
            String encryptPassword = DigestUtils.md5DigestAsHex((SALT + "test@1234").getBytes());
            user.setPassword(encryptPassword);
            user.setPhone(faker.phoneNumber().phoneNumber());
            user.setEmail(faker.internet().emailAddress());
            user.setTags("[]");
            user.setProfile(faker.lorem().paragraph(10));
            userList.add(user);
        }
        userService.saveBatch(userList, 10000);
        stopWatch.stop();
        System.out.println(stopWatch.getTotalTimeMillis());
    }

    private static String generatePersonalProfile(Faker faker) {
        return "姓名: " + faker.name().fullName() + "\n" +
                "性别: " + (faker.bool().bool() ? "男" : "女") + "\n" +
                "出生日期: " + faker.date().birthday().toString() + "\n" +
                "联系方式: " + faker.phoneNumber().cellPhone() + "\n" +
                "电子邮件: " + faker.internet().emailAddress() + "\n" +
                "现居地址: " + faker.address().fullAddress() + "\n" +
                "职业: " + faker.company().profession() + "\n" +
                "公司: " + faker.company().name() + "\n" +
                "职位: " + faker.company().bs() + "\n" +
                "个人简介: " + faker.lorem().paragraph(3) + "\n" +
                "技能: " + String.join(", ", faker.options().option("Java", "Python", "C++", "JavaScript", "Go")) + "\n" +
                "兴趣爱好: " + String.join(", ", faker.options().option("阅读", "旅行", "编程", "电影", "音乐")) + "\n";
    }

    @RepeatedTest(10)
    void insertUserByConcurrency() {
        int totalRecords = 1_000_000; // 总记录数
        int batchSize = 12_000; // 每批次生成1,000,000条数据
        // 自定义线程池参数
        int corePoolSize = Runtime.getRuntime().availableProcessors() * 2; // 核心线程数
        int maximumPoolSize = corePoolSize; // 最大线程数
        long keepAliveTime = 60L; // 空闲线程存活时间
        TimeUnit unit = TimeUnit.SECONDS; // 时间单位
        int queueSize = 180; // 有界队列大小
        BlockingQueue<Runnable> workQueue = new ArrayBlockingQueue<>(queueSize); // 有界队列
            RejectedExecutionHandler rejectedHandler = new ThreadPoolExecutor.CallerRunsPolicy(); // 拒绝策略

        // 创建自定义线程池
        ThreadPoolExecutor executor = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime,
                unit, workQueue, rejectedHandler);

        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        List<CompletableFuture<Void>> futures = new ArrayList<>();

        // 分批次生成数据并提交任务
        for (int i = 0; i < totalRecords; i += batchSize) {

            int currentBatchSize = Math.min(batchSize, totalRecords - i);
            int finalI = i;
            CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
                List<User> userList = generateUsers(currentBatchSize);
                userService.saveBatch(userList, 4000); // 批量插入数据库
                log.info("completed task:{}", finalI);
            }, executor);
            futures.add(future);
        }

        // 等待所有任务完成
        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
        executor.shutdown();
        stopWatch.stop();

        System.out.println("总耗时: " + stopWatch.getTotalTimeMillis() + "ms");
    }
    private  static final String encryptPassword = DigestUtils.md5DigestAsHex((SALT + "test@1234").getBytes());
    // 生成用户数据的方法
    private List<User> generateUsers(int count) {
        List<User> userList = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            User user = new User();
            user.setUsername(faker.name().fullName());
            user.setLoginAccount(faker.name().username());
            user.setAvatarUrl("https://fastly.jsdelivr.net/npm/@vant/assets/cat.jpeg");
            user.setGender(faker.bool().bool() ? 1 : 0);
            user.setPassword(encryptPassword);
            user.setPhone(faker.phoneNumber().phoneNumber());
            user.setEmail(faker.internet().emailAddress());
            ArrayList<String> tags = new ArrayList<>();
            tags.add(faker.programmingLanguage().name());
            tags.add(faker.options().option("男", "女"));
            tags.add(faker.options().option("大一", "大二", "大三", "大四", "研究生", "博士"));
            user.setTags(JSONUtil.toJsonStr(tags));
            user.setProfile(faker.lorem().paragraph(3));
            userList.add(user);
        }
        return userList;
    }
}