package com.ljz.passport.core.properties;

/**
 * 默认的图形验证码的拦截需要的配置
 *
 * @author 李建珍
 * @date 2019/3/22
 */
public class ImageCodeProperties extends SmsCodeProperties {
    /**
     * 验证码宽度
     */
    private int width = 70;
    /**
     * 验证码图片高度
     */
    private int height = 28;

    public ImageCodeProperties() {
        super(4);
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

}
