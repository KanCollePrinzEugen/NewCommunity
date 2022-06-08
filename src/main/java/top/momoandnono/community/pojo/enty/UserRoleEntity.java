package top.momoandnono.community.pojo.enty;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 用户角色关联表
 * @author l
 */
@Data
@TableName("mall_user_role")
public class UserRoleEntity implements Serializable {

  /**
   * 用户角色关联id
   */
  @TableId(value = "id", type = IdType.AUTO)
  private Integer id;

  /**
   * 用户id
   */
  @TableField("u_id")
  private Integer uId;

  /**
   * 角色id
   */
  @TableField("r_id")
  private Integer rId;

}
