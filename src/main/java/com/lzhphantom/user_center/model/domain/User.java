package com.lzhphantom.user_center.model.domain;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import lombok.Data;

/**
 * 用户
 * @TableName user
 */
@Data
@TableName("user")
public class User implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 昵称
     */
    private String username;

    /**
     * 登录账号
     */
    private String loginAccount;

    /**
     * 头像
     */
    private String avatarUrl;

    /**
     * 性别
     */
    private Integer gender;

    /**
     * 密码
     */
    private String password;

    /**
     * 电话
     */
    private String phone;

    /**
     * 邮件
     */
    private String email;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 是否删除
     */
    @TableLogic
    private Integer isDelete;

    private Integer role;

    private String tags;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;


}