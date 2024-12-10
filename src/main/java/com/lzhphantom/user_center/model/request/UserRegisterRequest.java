package com.lzhphantom.user_center.model.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserRegisterRequest implements Serializable {
    private static final long serialVersionUID = 1L;
    private String username;
    private String userAccount;
    private String userPassword;
    private String checkPassword;
    private Integer gender;
    private String phone;
    private String email;
}
