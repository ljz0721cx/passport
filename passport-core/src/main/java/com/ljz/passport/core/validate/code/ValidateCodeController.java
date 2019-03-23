package com.ljz.passport.core.validate.code;

import com.ljz.passport.core.validate.ValidateCode;
import com.ljz.passport.core.validate.sms.SmsCodeSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author 李建珍
 * @date 2019/3/21
 */
@RestController
@RequestMapping("/validate")
public class ValidateCodeController {
    private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();
    public static final String SESSION_CODE_KEY = "SESSION_KEY__CODE";

    @Autowired
    private ValidateCodeGenerator imageCodeGenerator;

    @Autowired
    private SmsCodeSender smsCodeSender;

    /**
     * 请求验证码
     *
     * @param request
     * @param response
     * @throws IOException
     */
    @GetMapping("/imageCode")
    public void createCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ImageCode imageCode = (ImageCode) imageCodeGenerator.generate(new ServletWebRequest(request));
        sessionStrategy.setAttribute(new ServletWebRequest(request), SESSION_CODE_KEY, imageCode);
        ImageIO.write(imageCode.getImage(), "JPEG", response.getOutputStream());
    }

    /**
     * 请求验证码
     *
     * @param request
     * @throws IOException
     */
    @GetMapping("/smsCode")
    public void createSmsCode(HttpServletRequest request) throws ServletRequestBindingException {
        ValidateCode validateCode = imageCodeGenerator.generate(new ServletWebRequest(request));
        sessionStrategy.setAttribute(new ServletWebRequest(request), SESSION_CODE_KEY, validateCode);
        String mobile = ServletRequestUtils.getRequiredStringParameter(request, "mobile");
        smsCodeSender.send(mobile, validateCode.getCode());
    }
}
