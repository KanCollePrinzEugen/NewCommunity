package top.momoandnono.community.security;


import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import top.momoandnono.community.constant.UserStateConstant;
import top.momoandnono.community.mapper.RoleMapper;
import top.momoandnono.community.mapper.UserMapper;
import top.momoandnono.community.pojo.enty.RoleEntity;
import top.momoandnono.community.pojo.enty.UserEntity;
import top.momoandnono.community.util.ResultUtil;

import javax.annotation.Resource;

/**
 * 自定义安全权限 对登录名的验证
 * @author l
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Resource
    private UserMapper userMapper;

    @Resource
    private RoleMapper roleMapper;

    @Override
    public UserDetails loadUserByUsername(String loginName) {
        final UserEntity userEntity = userMapper.selectOne(Wrappers.lambdaQuery(UserEntity.class)
                .eq(UserEntity::getUsername, loginName));

        ResultUtil.notNull(userEntity, "用户不存在");

        ResultUtil.isTrue(!UserStateConstant.toNormal(userEntity.getState()), "账号已禁用，请联系管理员");


        final RoleEntity roleEntity = roleMapper.selectOneByUserId(userEntity.getId());

        ResultUtil.notNull(roleEntity, "角色不能为空");

        return new User(userEntity.getUsername(), userEntity.getPassword(),
                AuthorityUtils.commaSeparatedStringToAuthorityList(roleEntity.getName()));
    }
}
