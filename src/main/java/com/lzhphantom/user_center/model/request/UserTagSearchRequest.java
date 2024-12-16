package com.lzhphantom.user_center.model.request;

import com.lzhphantom.user_center.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class UserTagSearchRequest extends PageRequest implements Serializable {
    private List<String> tagList;
}
