package top.momoandnono.community.pojo.request;

import lombok.Data;
import top.momoandnono.community.constant.RoleStateConstant;
import top.momoandnono.community.constant.UserStateConstant;
import top.momoandnono.community.pojo.enty.UserEntity;
import top.momoandnono.community.util.ModelMapperUtil;
import top.momoandnono.community.util.ResultUtil;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;
import java.util.stream.Stream;

/**
 * 用户请求信息
 * @author l
 */
@Data
public class UserRequest implements Serializable {

    /**
     * 用户id
     */
    private Long id;

    /**
     * 登录名
     */
    @NotBlank(message = "登录名不能为空")
    private String username;

    /**
     * 登录密码
     */
    @NotBlank(message = "登录密码不能为空")
    private String password;


    /**
     * 姓名
     */
    @NotBlank(message = "姓名不能为空")
    private String nickname;

    /**
     * 手机号
     */
    @NotBlank(message = "手机号不能为空")
    private String mobile;

    /**
     * 用户类型：'-1：注销删除，0 禁用，1：启用
     */
    private Integer state;

    /**
     * 用户角色: 1.管理员；2.用户
     */
    private Integer role;

    /**
     * 初始化添加信息
     * @return 用户表
     */
    public UserEntity generateInsert() {
        ResultUtil.isTrue(Stream.of(RoleStateConstant.ADMIN, RoleStateConstant.USER).noneMatch(number -> number.equals(role)), "用户角色1.管理员；2.用户");

        final UserEntity userEntity = ModelMapperUtil.map(this, UserEntity.class);
        userEntity.setState(UserStateConstant.NORMAL);
        final Date date = new Date();
        userEntity.setCtime(date);
        userEntity.setUptime(date);
        return userEntity;
    }

    /**
     * 初始化修改信息
     * @return 用户表
     */
    public UserEntity generateUpdate() {

        ResultUtil.isTrue(Stream.of(UserStateConstant.DELETE, UserStateConstant.DISABLED, UserStateConstant.NORMAL).noneMatch(number -> number.equals(state)), "用户类型：1.正常；2.禁用");
        ResultUtil.isTrue(Stream.of(RoleStateConstant.ADMIN, RoleStateConstant.USER).noneMatch(number -> number.equals(role)), "用户类型：'-1：注销删除，0 禁用，1：启用");

        ResultUtil.notNull(id, "用户id不能为空");
        ResultUtil.notNull(state, "用户类型不能为空");

        final UserEntity userEntity = ModelMapperUtil.map(this, UserEntity.class);
        userEntity.setUptime(new Date());
        return userEntity;
    }

    /**
     * 初始化注册信息
     * @return 用户表
     */
    public UserEntity generateRegister() {
        final UserEntity userEntity = ModelMapperUtil.map(this, UserEntity.class);
        userEntity.setState(UserStateConstant.NORMAL);
        final Date date = new Date();
        userEntity.setCtime(date);
        userEntity.setUptime(date);
        return userEntity;
    }
}
