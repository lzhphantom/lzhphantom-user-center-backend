package com.lzhphantom.user_center.exception;

import com.lzhphantom.user_center.common.ErrorCode;
import lombok.Getter;
import lombok.Setter;

/**
 * 自定义业务异常
 *
 * @author lzhphantom
 */
@Getter
@Setter
public class BusinessException extends RuntimeException {
    private final int code;

    public BusinessException(int code, String message) {
        super(message);
        this.code = code;
    }

    public BusinessException(ErrorCode errorCode) {
        this(errorCode.getCode(), errorCode.getMessage());
    }

    public BusinessException(ErrorCode errorCode, String message) {
        this(errorCode.getCode(), message);
    }
}
