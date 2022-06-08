package top.momoandnono.community.util;

import org.springframework.security.core.context.SecurityContextHolder;
import top.momoandnono.community.pojo.enty.UserEntity;

import java.util.Objects;

/**
 * 权限工具类
 * @author l
 */
public class SecurityUtil {

    /**
     * 获取用户信息
     * @return 用户信息
     */
    public static UserEntity context() {
        final Object principalObject = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Objects.requireNonNull(principalObject);
        return (UserEntity)principalObject;
    }
}
