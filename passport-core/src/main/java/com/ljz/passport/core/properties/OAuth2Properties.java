package com.ljz.passport.core.properties;

/**
 * @author 李建珍
 * @date 2019/5/19
 */
public class OAuth2Properties {
    /**
     * 使用jwt时为token签名的秘钥,只做验签
     */
    private String jwtSigningKey = "janle";

    public String getJwtSigningKey() {
        return jwtSigningKey;
    }

    public void setJwtSigningKey(String jwtSigningKey) {
        this.jwtSigningKey = jwtSigningKey;
    }
}
