package com.ljz.passport.core.validate.code;

import org.springframework.security.core.AuthenticationException;

/**
 * @author 李建珍
 * @date 2019/3/21
 */
public class ValidateCodeException extends AuthenticationException {
    public ValidateCodeException(String msg, Throwable t) {
        super(msg, t);
    }

    public ValidateCodeException(String msg) {
        super(msg);
    }
}
