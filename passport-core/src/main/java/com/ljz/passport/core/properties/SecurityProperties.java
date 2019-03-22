package com.ljz.passport.core.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author 李建珍
 * @date 2019/3/20
 */
@ConfigurationProperties(prefix = "passport.security")
public class SecurityProperties {
    /**
     * 访问相关的配置
     */
    private BrowserProperties browser = new BrowserProperties();
    /**
     * 验证码相关的配置
     */
    private ValidateCodeProperties code = new ValidateCodeProperties();

    public BrowserProperties getBrowser() {
        return browser;
    }

    public void setBrowser(BrowserProperties browser) {
        this.browser = browser;
    }

    public ValidateCodeProperties getCode() {
        return code;
    }

    public void setCode(ValidateCodeProperties code) {
        this.code = code;
    }
}
