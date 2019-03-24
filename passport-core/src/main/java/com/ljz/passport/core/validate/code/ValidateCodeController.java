package com.ljz.passport.core.validate.code;

import com.ljz.passport.core.validate.code.sms.SmsCodeSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * @author 李建珍
 * @date 2019/3/21
 */
@RestController
@RequestMapping("/validate")
public class ValidateCodeController {
    private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();

    @Autowired
    private ValidateCodeGenerator imageCodeGenerator;

    @Autowired
    private SmsCodeSender smsCodeSender;


    @Autowired
    private Map<String, ValidateCodeProcessor> validateCodeProcessors;

    /**
     * 请求验证码
     *
     * @param request
     * @param response
     * @throws IOException
     */
    @GetMapping("/{vType}")
    public void createCode(HttpServletRequest request,
                           HttpServletResponse response,
                           @PathVariable String vType) throws Exception {
        validateCodeProcessors.get(vType + "ValidateCodeProcessor")
                .create(new ServletWebRequest(request, response));
    }
}
