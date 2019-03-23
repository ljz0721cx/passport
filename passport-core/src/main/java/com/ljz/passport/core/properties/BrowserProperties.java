package com.ljz.passport.core.properties;

/**
 * @author 李建珍
 * @date 2019/3/20
 */
public class BrowserProperties {
    /**
     * 默认的登录页面
     */
    private String loginPage = "/login.html";
    /**
     * 登录类型
     */
    private LoginType loginType = LoginType.JSON;
    /**
     * 设置记住时间为3600s
     */
    private int remeberMeSeconds = 3600;

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

    public int getRemeberMeSeconds() {
        return remeberMeSeconds;
    }

    public void setRemeberMeSeconds(int remeberMeSeconds) {
        this.remeberMeSeconds = remeberMeSeconds;
    }
}
