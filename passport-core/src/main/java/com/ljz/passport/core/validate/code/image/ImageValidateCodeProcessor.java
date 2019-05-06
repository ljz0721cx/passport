package com.ljz.passport.core.validate.code.image;

import com.ljz.passport.core.auth.SecurityConstants;
import com.ljz.passport.core.validate.code.AbsctractValidateCodeProcessor;
import com.ljz.passport.core.validate.code.ValidateCodeType;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

import javax.imageio.ImageIO;

/**
 * @author 李建珍
 * @date 2019/3/23
 */
@Component("imageValidateCodeProcessor")
public class ImageValidateCodeProcessor
        extends AbsctractValidateCodeProcessor<ImageCode> {
    /**
     * 设置session的key
     */
    private final String IMAGE_SESSION_VALIDATE_CODE_KEY =
            SESSION_VALIDATE_CODE_KEY_PREFIX.concat(ValidateCodeType.IMAGE.name());

    /**
     * 实现生成验证码发送到用户浏览器中
     *
     * @param request
     * @param validateCode
     * @throws Exception
     */
    @Override
    protected void send(ServletWebRequest request, ImageCode validateCode) throws Exception {
        ImageIO.write(validateCode.getImage(), "JPEG", request.getResponse().getOutputStream());
    }

    @Override
    protected String getValidateSessionKey() {
        return IMAGE_SESSION_VALIDATE_CODE_KEY;
    }

    @Override
    protected String getValidateParameterName() {
        return SecurityConstants.DEFAULT_IMAGE_CODE_PARAMETER_NAME;
    }
}
