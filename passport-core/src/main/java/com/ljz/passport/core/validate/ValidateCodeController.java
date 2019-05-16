package com.ljz.passport.core.validate;

import com.ljz.passport.core.validate.code.ValidateCodeProcessor;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
    private Map<String, ValidateCodeProcessor> validateCodeProcessors;

    /**
     * 请求验证码
     *
     * @param request
     * @param response
     * @throws IOException
     */
    @GetMapping("/{createType}")
    public void createValidateCode(HttpServletRequest request,
                                   HttpServletResponse response,
                                   @PathVariable(name = "createType", required = true) String createType) throws Exception {
        validateCodeProcessors.get(createType + "ValidateCodeProcessor")
                .create(new ServletWebRequest(request, response));
    }
}
