package com.ljz.passport.core.properties;

/**
 * @author 李建珍
 * @date 2019/3/22
 */
public class ValidateCodeProperties {
    private ImageCodeProperties image=new ImageCodeProperties();

    public ImageCodeProperties getImage() {
        return image;
    }

    public void setImage(ImageCodeProperties image) {
        this.image = image;
    }
}
