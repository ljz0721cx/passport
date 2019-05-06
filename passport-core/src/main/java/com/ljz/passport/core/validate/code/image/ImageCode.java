package com.ljz.passport.core.validate.code.image;

import com.ljz.passport.core.validate.code.ValidateCode;

import java.awt.image.BufferedImage;
import java.time.LocalDateTime;

/**
 * @author 李建珍
 * @date 2019/3/21
 */
public class ImageCode extends ValidateCode {
    /**
     * 验证码图片
     */
    private BufferedImage image;

    /**
     * 多长时间后过期
     *
     * @param image
     * @param code
     * @param expireIn 过期时间单位为秒
     */
    public ImageCode(BufferedImage image, String code, int expireIn) {
        super(code, expireIn);
        this.image = image;
    }

    public ImageCode(BufferedImage image, String code, LocalDateTime expireTime) {
        super(code, expireTime);
        this.image = image;
    }

    public BufferedImage getImage() {
        return image;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }
}
