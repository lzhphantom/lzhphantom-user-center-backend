# lzhphantom 用户中心
[TOC]

## 项目简介
lzhphantom-user-center是一个用户中心服务，提供了用户注册、登录、搜索、推荐等功能。该项目使用了Spring Boot框架，并集成了MyBatis-Plus、Redis等技术。使用redis+session来缓存用户信息，使用knife4j给前端提供openapi格式

## 环境依赖
- Java 8
- MySQL
- Redis
- Maven
- knife4j

## 技术点

- 使用AOP实现**幂等性**：参考Idempotent的AOP
- 定时任务：计算出用户推荐匹配用户
- 算法：用户相似度计算（**编辑距离**，Jaccard相似度）

## 参考链接
- [MyBatis-Plus](https://mybatis.plus/)
- [Redis](https://redis.io/)
- [Spring Boot](https://spring.io/projects/spring-boot)
