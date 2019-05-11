#SpringSecurity 原理

## 基本原理
表单的过滤器：UsernamePasswordAuthenticationFilter    
检查过滤basic：BasicAuthenticationFilter   
自定义过滤器: ...  
异常传输：ExceptionTranstationFilter     
最后一道过滤拦截：FilterSecurityInterceptor  

访问RESTFull


## 软件设计

## 需要交互的用户接口

- 处理用户查询逻辑 

用户主体的字段值  
private String password; //密码  
private final String username; //账户名称  
private final Set<GrantedAuthority> authorities; //授权信息  
private final boolean accountNonExpired; //账户是否过期  
private final boolean accountNonLocked; //账户是否被锁定  
private final boolean credentialsNonExpired; //凭证是否过期  
private final boolean enabled; //是否生效

- 处理用户校验逻辑  

使用动态秘钥  
BCryptPasswordEncoder 密码比较短  
SCryptPasswordEncoder 密码比较长  

- 处理用户加密解密
注册时候输入



- 第三方登录注意
    微信授权回调域不能配置/之类的
    www.clouds1000.com
- qq本地调试
    qq本地调试不能加https://




