package com.ljz.passport.app.validate;

import com.ljz.passport.core.auth.SecurityConstants;
import com.ljz.passport.core.validate.code.ValidateCode;
import com.ljz.passport.core.validate.code.ValidateCodeException;
import com.ljz.passport.core.validate.code.repository.ValidateCodeRepository;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

import java.util.concurrent.TimeUnit;

/**
 * 基于redis的验证码存取器，避免由于没有session导致无法存取验证码的问题
 *
 * @author 李建珍
 * @date 2019/5/15
 */
@Component
public class RedisValidateCodeRepository implements ValidateCodeRepository {
    @Autowired
    private RedisTemplate redisCodeTemplate;

    @Override
    public void setValidateCode(ServletWebRequest request, String validateKey, ValidateCode validateCode) {
        //TODO  设置验证码时间和登录验证码时间
        //设置30分钟的超时时间
        redisCodeTemplate
                .opsForValue()
                .set(buildKey(request, validateKey), validateCode, 10, TimeUnit.MINUTES);
    }

    @Override
    public ValidateCode getValidateCode(ServletWebRequest request, String codekey) {
        Object value = redisCodeTemplate.opsForValue().get(buildKey(request, codekey));
        if (value == null) {
            return null;
        }
        return (ValidateCode) value;
    }

    @Override
    public void removeValidateCode(ServletWebRequest request, String codeKey) {
        redisCodeTemplate.delete(buildKey(request, codeKey));
    }

    private String buildKey(ServletWebRequest request, String type) {
        String deviceId = request.getHeader(SecurityConstants.DEFAULT_PARAMETER_DEVICEID);
        if (StringUtils.isBlank(deviceId)) {
            throw new ValidateCodeException("请在请求头中携带deviceId参数");
        }
        return "code:" + type.toLowerCase() + ":" + SecurityConstants.DEFAULT_PARAMETER_DEVICEID;
    }
}
