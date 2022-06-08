package top.momoandnono.community.pojo.response;

import lombok.Data;
import lombok.EqualsAndHashCode;
import top.momoandnono.community.pojo.enty.UserEntity;

import java.io.Serializable;

/**
 * 用户响应信息
 * @author l
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class UserResponse extends UserEntity implements Serializable {

    /**
     * 角色id
     */
    private Long roleId;

    /**
     * 角色名
     */
    private String roleName;
}
