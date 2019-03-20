package com.ljz.passport.core.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author 李建珍
 * @date 2019/3/20
 */
@ConfigurationProperties(prefix = "passport.security")
public class SecurityProperties {
    private BrowserProperties browser = new BrowserProperties();

    public BrowserProperties getBrowserProperties() {
        return browser;
    }

    public void setBrowserProperties(BrowserProperties browser) {
        this.browser = browser;
    }
}
