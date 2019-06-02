#单点登录系统

##项目结构
passport-sso
- passport-sso-server  //认证服务
- passport-sso-ui  //需要认证的资源服务
- passport-sso-ui1 //需要认证的资源服务


### 前后端分离使用

> 服务启动入口，如果需要扫描指定的路径可以添加@ComponentScan的范围，如果不需要就
直接使用@SpringBootApplication就好
```$xslt
@ComponentScan
@SpringBootApplication
public class UiApplication {
    public static void main(String[] args) {
        SpringApplication.run(UiApplication.class, args);
    }
}
```

> 配置资源服务配置
````$xslt
@EnableResourceServer
@Configuration
public class Oauth2ClientConfig extends AbstractSecurityConfig {
}
````

>增加资源服务配置 和协调认证服务配置
```$xslt
security:
  oauth2:
    client:
      client-id: janle
      client-secret: janleSecret
      user-authorization-uri: http://www.clouds1000.com/oauth/authorize
      access-token-uri: http://www.clouds1000.com/oauth/token
      scope: all
      registered-redirect-uri: http://client.clouds1000.com:8080/passport
    resource:
      token-info-uri: http://www.clouds1000.com/oauth/check_token
      user-info-uri: http://www.clouds1000.com/user
      jwt:
        #需要携带client
        key-uri: http://www.clouds1000.com/oauth/token_key
        key-value: janle

```