package com.ljz.passport.core.validate.code;

/**
 * @author 李建珍
 * @date 2019/3/24
 */
public enum ValidateCodeType {
    SMS("sms"),
    IMAGE("image");

    private String lowName;


    ValidateCodeType(String lowName) {
        this.lowName = lowName;
    }

    public String getLowName() {
        return lowName;
    }
}
