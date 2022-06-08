package top.momoandnono.community.pojo.enty;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户表
 * @author l
 */
@Data
@TableName("sys_user")
public class UserEntity implements Serializable {

  /**
   * 用户id
   */
  @TableId(value = "id", type = IdType.AUTO)
  private Integer id;

  /**
   * 登录名
   */
  @TableField("username")
  private String username;

  /**
   * 密码
   */
  @TableField("password")
  private String password;

  /**
   * 登录时间
   */
  @TableField("last_login_time")
  private Date loginTime;


  /**
   * 昵称
   */
  @TableField("nickname")
  private String nickname;

  /**
   * 移动电话
   */
  @TableField("mobile")
  private String mobile;

  /**
   * 用户类型：'-1：注销删除，0 禁用，1：启用
   */
  @TableField("state")
  private Integer state;

  /**
   * 创建时间
   */
  @TableField("add_time")
  private Date ctime;

  /**
   * 修改时间
   */
  @TableField("update_time")
  private Date uptime;



}
