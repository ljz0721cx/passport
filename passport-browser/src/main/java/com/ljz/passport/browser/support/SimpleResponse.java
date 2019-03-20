package com.ljz.passport.browser.support;

/**
 * @author 李建珍
 * @date 2019/3/20
 */
public class SimpleResponse {

    private Object content;

    public SimpleResponse(Object content) {
        this.content = content;
    }

    public Object getContent() {
        return content;
    }

    public void setContent(Object content) {
        this.content = content;
    }
}
