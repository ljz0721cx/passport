package com.ljz.passport.config;

import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpCookie;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.web.cors.CorsUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author 李建珍
 * @date 2019/5/20
 */
@Configuration
@EnableOAuth2Sso
public class SsoResourceServerConfig extends WebSecurityConfigurerAdapter {

    @Override
    public void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests()
                .antMatchers("/auth/token").permitAll()
                .anyRequest().authenticated()
                .and().csrf()
                .csrfTokenRepository(csrfTokenRepository())
                .and().authorizeRequests().antMatchers("/**").authenticated()
                .requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
                .and()
                .addFilterAfter(csrfHeaderFilter(), CsrfFilter.class)
                .logout()
                .logoutUrl("/logout")
                .permitAll()
                .logoutSuccessUrl("/");
    }


    private Filter csrfHeaderFilter() {
        return new OncePerRequestFilter() {
            @Override
            protected void doFilterInternal(HttpServletRequest request,
                                            HttpServletResponse response, FilterChain filterChain)
                    throws ServletException, IOException {
                CsrfToken csrf = (CsrfToken) request
                        .getAttribute(CsrfToken.class.getName());
                if (csrf != null) {
                    Cookie cookie = new Cookie("XSRF-TOKEN",
                            csrf.getToken());
                    new HttpCookie("name", "");
                    cookie.setDomain("clouds1000.com");
                    cookie.setHttpOnly(false);
                    cookie.setPath("/");
                    response.addCookie(cookie);
                }
                // 指定允许其他域名访问
                response.setHeader("Access-Control-Allow-Origin", "clouds1000.com");
                // 响应类型
                response.setHeader("Access-Control-Allow-Methods", "POST, GET, DELETE, OPTIONS, DELETE");
                // 响应头设置
                response.setHeader("Access-Control-Allow-Headers", "Content-Type, x-requested-with, X-Custom-Header, HaiYi-Access-Token");

                if (HttpMethod.OPTIONS.equals(request.getMethod())) {
                    response.setStatus(HttpStatus.OK.value());
                }
                filterChain.doFilter(request, response);
            }
        };
    }

    private CsrfTokenRepository csrfTokenRepository() {
        HttpSessionCsrfTokenRepository repository = new HttpSessionCsrfTokenRepository();
        repository.setHeaderName("X-XSRF-TOKEN");
        return repository;
    }

    @Bean
    public OAuth2RestTemplate oAuth2RestTemplate(
            OAuth2ProtectedResourceDetails resource) {
        OAuth2RestTemplate oAuth2RestTemplate = new OAuth2RestTemplate(resource);
        return oAuth2RestTemplate;
    }
}
