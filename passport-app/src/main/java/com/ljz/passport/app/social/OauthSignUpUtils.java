package com.ljz.passport.app.social;

import com.ljz.passport.app.exceptions.OauthSecretException;
import com.ljz.passport.core.auth.SecurityConstants;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionData;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.WebRequest;

import java.util.concurrent.TimeUnit;


/**
 * app环境下替换providerSignInUtils，避免由于没有session导致读不到社交用户信息的问题
 *
 * @author 李建珍
 * @date 2019/5/17
 */
@Component
public class OauthSignUpUtils {
    @Autowired
    private RedisTemplate redisCodeTemplate;
    @Autowired
    private UsersConnectionRepository usersConnectionRepository;
    @Autowired
    private ConnectionFactoryLocator connectionFactoryLocator;


    /**
     * 缓存社交网站用户信息到redis,设置缓存时间，如果用户不进行授权，后期删除
     *
     * @param request
     * @param connectionData
     */
    public void saveConnectionData(WebRequest request, ConnectionData connectionData) {
        //设置10分钟自动清除数据
        redisCodeTemplate.opsForValue().set(getKey(request), connectionData, 10, TimeUnit.MINUTES);
    }


    /**
     * 将缓存的社交网站用户信息与系统注册用户信息绑定
     *
     * @param request
     * @param userId
     */
    public void doPostSignUp(WebRequest request, String userId) {
        //把之前放在session中的数据拿出来
        String key = getKey(request);
        if (!redisCodeTemplate.hasKey(key)) {
            throw new OauthSecretException("无法找到缓存的第三方用户信息");
        }
        ConnectionData connectionData = (ConnectionData) redisCodeTemplate.opsForValue().get(key);
        //根据连接数据中的providerId拿到连接工厂去创建连接
        Connection<?> connection =
                connectionFactoryLocator.getConnectionFactory(connectionData.getProviderId())
                        .createConnection(connectionData);
        //FIXME 切换sevice或者切换服务 绑定用户id
        usersConnectionRepository.createConnectionRepository(userId)
                .addConnection(connection);
        //注册完成,在redis中把这个key删掉
        redisCodeTemplate.delete(key);
    }

    /**
     * 获取redis key
     *
     * @param request
     * @return
     */
    private String getKey(WebRequest request) {
        String deviceId = request.getHeader(SecurityConstants.DEFAULT_PARAMETER_DEVICEID);
        if (StringUtils.isBlank(deviceId)) {
            throw new OauthSecretException("设备id参数 不能为空");
        }
        return "oauth:security:social.connect." + deviceId;
    }
}
