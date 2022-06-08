package top.momoandnono.community.security;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import top.momoandnono.community.constant.SysConstant;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Collections;

/**
 * SpringBoot Security 权限控制类
 * @author l
 */
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter{

    @Resource
    AuthenticationSuccessHandler myAuthenticationSuccessHandler;

    @Resource
    AuthenticationFailureHandler myAuthenticationFailureHandler;

    @Resource
    LogoutSuccessHandler myLogoutSuccessHandler;

    @Resource
    AccessDeniedHandler myAccessDeniedHandler;

    /**
     * 权限控制认证
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin()
                .loginPage(SysConstant.SYS_FRONT + "/#/login")
                .loginProcessingUrl("/login").usernameParameter("userName").passwordParameter("userPassword")
                .successHandler(myAuthenticationSuccessHandler)
                .failureHandler(myAuthenticationFailureHandler);
        //允许跨域
        http.csrf().disable().authorizeRequests();
        http.cors();
        //授权 所有请求都要被认证
        http.authorizeRequests()
                // 注册
                .antMatchers("/register").permitAll()
                // 验证码
                .antMatchers("/code/**").permitAll()
                .anyRequest().authenticated();
        //退出登录
        http.logout()
                .logoutUrl("/logout")
                .logoutSuccessHandler(myLogoutSuccessHandler);
        //唯一的Jwt拦截器
        http.addFilter(new JwtAuthenticationTokenFilter(authenticationManager()));
        //自定义异常
        http.exceptionHandling()
                .accessDeniedHandler(myAccessDeniedHandler);
    }


    /**
     * 密码加密类
     */
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }


    /**
     * 跨域 拦截
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource(){
        CorsConfiguration configuration=new CorsConfiguration();
        configuration.setAllowedOrigins(Collections.singletonList(SysConstant.SYS_FRONT));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "DELETE", "PUT"));
        configuration.applyPermitDefaultValues();
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**",configuration);
        return source;
    }



}
