#APP和前后端分离登录

安全性和客户体验性差  
前端技术不支持cookie


access_token
reflush_token

1.请求认证服务器生成令牌token  
2.客户端带着token令牌请求数据  


- 认证服务器  
    - 授权模式
    - 生成token并存储  
    - 支持自定义认证

- 资源服务器
    - 过滤器链OAuth2AuthenticationProcessingFilter 