package com.ljz.passport.app.exceptions;

/**
 * @author 李建珍
 * @date 2019/5/17
 */
public class OauthSecretException extends RuntimeException {
    /**
     * @param msg
     */
    public OauthSecretException(String msg) {
        super(msg);
    }
}
