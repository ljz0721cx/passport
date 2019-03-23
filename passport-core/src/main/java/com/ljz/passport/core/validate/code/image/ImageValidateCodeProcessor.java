package com.ljz.passport.core.validate.code.image;

import com.ljz.passport.core.validate.code.AbsctractValidateCodeProcessor;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

import javax.imageio.ImageIO;

/**
 * @author 李建珍
 * @date 2019/3/23
 */
@Component("imageValidateCodeProcessor")
public class ImageValidateCodeProcessor extends AbsctractValidateCodeProcessor<ImageCode> {

    @Override
    protected void send(ServletWebRequest request, ImageCode validateCode) throws Exception {
        ImageIO.write(validateCode.getImage(), "JPEG", request.getResponse().getOutputStream());
    }
}
