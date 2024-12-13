package com.lzhphantom.user_center.model.request;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class UserTagSearchRequest implements Serializable {
    private List<String> tagList;
}
