package com.ljz.passport.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author 李建珍
 * @date 2019/5/31
 */
@Component
@ConfigurationProperties(prefix = "security.oauth2.client")
public class OauthSecurityConfig {
    @Value("${security.oauth2.client.client-id}")
    private String clientId;
    @Value("${security.oauth2.client.client-secret}")
    private String clientSecret;
    @Value("${security.oauth2.client.access-token-uri}")
    private String accessTokenUri;
    @Value("${security.oauth2.client.scope}")
    private String scope;
    @Value("${security.oauth2.client.registered-redirect-uri}")
    private String registRedirectUri;


    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public String getAccessTokenUri() {
        return accessTokenUri;
    }

    public void setAccessTokenUri(String accessTokenUri) {
        this.accessTokenUri = accessTokenUri;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getRegistRedirectUri() {
        return registRedirectUri;
    }

    public void setRegistRedirectUri(String registRedirectUri) {
        this.registRedirectUri = registRedirectUri;
    }

}
