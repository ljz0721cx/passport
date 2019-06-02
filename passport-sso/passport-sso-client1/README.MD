#对应的拦截
@EnableOAuth2Sso


##项目结构
passport-sso
- passport-sso-server  //认证服务
- passport-sso-ui  //需要认证的资源服务
- passport-sso-ui1 //需要认证的资源服务


### 认证服务
- passport-sso-server


### 客户端应用1
开发过程中需要配置的操作手册

- passport-sso-client1
   
  接入SSO代码  
  ```$java  
  客户端应用使用在安全控制模块添加注解@EnableOAuth2Sso
  @Configuration
  @EnableOAuth2Sso
  public class SsoResourceServerConfig extends WebSecurityConfigurerAdapter {
  
  
      @Override
      public void configure(HttpSecurity http) throws Exception {
          http.authorizeRequests()
                  .anyRequest().authenticated()
                  .and()
                  .csrf().disable();
      }
   }
  ```
 SSO配置文件application.yml文件配置如下
  ```$java
    server:
      port: 8080
    
    security:
      oauth2:
        client:
          client-id: janle
          client-secret: janleSecret
          user-authorization-uri: http://www.clouds1000.com/oauth/authorize
          access-token-uri: http://www.clouds1000.com/oauth/token
          scope: all
        resource:
          jwt:
            #需要携带client
            key-uri: http://www.clouds1000.com/oauth/token_key
            key-value: janle
  
  
  
  ```
  
  启动服务
  
  访问用户信息，启动请求访问地址 http://www.clouds1000.com:8080/user


### 客户端应用2
- passport-sso-client2



## 源码跟踪
WebAsyncManagerIntegrationFilter 
SecurityContextPersistenceFilter 
HeaderWriterFilter 
CsrfFilter 
CSRFHeaderFilter（自定义的过滤器，添加在CsrfFilter后） 
LogoutFilter 
OAuth2ClientAuthenticationProcessingFilter 
RequestCacheAwareFilter 
SecurityContextHolderAwareRequestFilter 
AnonymousAuthenticationFilter 
SessionManagementFilter 
ExceptionTranslationFilter 
FilterSecurityInterceptor 

WebAsyncManagerIntegrationFilter 