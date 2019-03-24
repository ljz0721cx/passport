package com.ljz.passport.core.validate.code;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 获取对应的验证码生成和验证处理器
 *
 * @author 李建珍
 * @date 2019/3/24
 */
@Component
public class ValidateCodeProcessorHolder {

    @Autowired
    private Map<String, ValidateCodeProcessor> validateCodeProcessors;

    /**
     * 通过校验类型获取对应的
     *
     * @param type
     * @return
     */
    public ValidateCodeProcessor findValidateCodeProcessor(ValidateCodeType type) {
        return validateCodeProcessors.get(type.getLowName() + "ValidateCodeProcessor");
    }
}
