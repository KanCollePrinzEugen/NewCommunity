package top.momoandnono.community.security;


import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import top.momoandnono.community.pojo.enty.UserEntity;
import top.momoandnono.community.util.JwtUtl;
import top.momoandnono.community.util.ResultUtil;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Jwt 拦截器
 * @author 18477
 */
public class JwtAuthenticationTokenFilter extends BasicAuthenticationFilter {

    public JwtAuthenticationTokenFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 获取请求头中JWT的Token
        String token = request.getHeader("Authorization");

        if (StringUtils.isBlank(token)) {
            filterChain.doFilter(request, response);
            return;
        }

        //获取token
        final UserEntity userEntity = new UserEntity();
        final StringBuilder roleBuilder = new StringBuilder();

        try {
            JwtUtl.getJwt(token, claims -> {
                userEntity.setId(claims.get("id", Integer.class));
                userEntity.setUsername(claims.get("username", String.class));
                userEntity.setPassword(claims.get("password", String.class));
                userEntity.setNickname(claims.get("nickname", String.class));
                userEntity.setMobile(claims.get("mobile", String.class));
                userEntity.setState(claims.get("state", Integer.class));
                roleBuilder.append(claims.get("role", String.class));
            });
        } catch (Exception e) {
            e.printStackTrace();
            ResultUtil.err("会话已过期，请从新登录");
            throw new RuntimeException();
        }

        //security 用户
        final User user = new User(userEntity.getUsername(), userEntity.getPassword(),
                AuthorityUtils.commaSeparatedStringToAuthorityList(roleBuilder.toString()));

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user, userEntity,
                AuthorityUtils.commaSeparatedStringToAuthorityList(roleBuilder.toString()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        filterChain.doFilter(request, response);
    }
}