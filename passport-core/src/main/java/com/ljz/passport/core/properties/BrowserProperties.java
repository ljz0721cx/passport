package com.ljz.passport.core.properties;

/**
 * @author 李建珍
 * @date 2019/3/20
 */
public class BrowserProperties {
    //默认的登录页面
    private String loginPage = "/login.html";
    //登录类型
    private LoginType loginType = LoginType.JSON;

    public String getLoginPage() {
        return loginPage;
    }

    public void setLoginPage(String loginPage) {
        this.loginPage = loginPage;
    }

    public LoginType getLoginType() {
        return loginType;
    }

    public void setLoginType(LoginType loginType) {
        this.loginType = loginType;
    }
}
