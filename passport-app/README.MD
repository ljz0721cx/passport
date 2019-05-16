#APP和前后端分离登录

安全性和客户体验性差  
前端技术不支持cookie


access_token
reflush_token

1.请求认证服务器生成令牌token  
2.客户端带着token令牌请求数据  



1.访问app
2.将用户导向授权认证服务
3.用户同意授权
4.返回app携带的openid和accessToken
5.openid换取令牌
6.通过openid获得用户数据换取返回令牌
7.



1.访问app
2.将用户导向授权认证服务
3.用户同意授权
4.返回client携带授权code码
5.把授权码发送给服务器，
6.服务器申请令牌
7.发放令牌



SocialUserDetailsService
UsersConnectionRepository

- 认证服务器  
    - 授权模式
    - 生成token并存储  
    - 支持自定义认证

- 资源服务器
    - 过滤器链OAuth2AuthenticationProcessingFilter 