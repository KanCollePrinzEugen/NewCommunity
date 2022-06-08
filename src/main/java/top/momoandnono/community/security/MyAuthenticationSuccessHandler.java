package top.momoandnono.community.security;


import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import top.momoandnono.community.mapper.RoleMapper;
import top.momoandnono.community.mapper.UserMapper;
import top.momoandnono.community.pojo.enty.UserEntity;
import top.momoandnono.community.util.JwtUtl;
import top.momoandnono.community.util.ResultUtil;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * 安全权限 登录 成功类
 * @author l
 */
@Component
public class MyAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Resource
    private UserMapper userMapper;

    @Resource
    private RoleMapper roleMapper;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        // 修改登录时间
        final UserEntity userEntity = userMapper.selectOne(Wrappers.lambdaQuery(UserEntity.class)
                .eq(UserEntity::getUsername, authentication.getName()));
        userEntity.setLoginTime(new Date());
        userMapper.insert(userEntity);

        ResultUtil.success(map -> {
            // 生成 jwt
            map.put("token", JwtUtl.setJwt(jwt -> {
                jwt.claim("id", userEntity.getId());
                jwt.claim("username", userEntity.getUsername());
                jwt.claim("password", userEntity.getPassword());
                jwt.claim("nickname", userEntity.getNickname());
                jwt.claim("mobile", userEntity.getMobile());
                jwt.claim("state", userEntity.getState());
                jwt.claim("role", roleMapper.selectOneByUserId(userEntity.getId()).getName());
            }));
            // 返回用户信息
            map.put("user", userEntity);

            // 返回用户菜单
//            map.put("")
        });
    }
}
