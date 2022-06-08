package top.momoandnono.community.pojo.enty;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 角色表
 * @author l
 */
@Data
@TableName("sys_role")
public class RoleEntity implements Serializable {

  /**
   * 角色id
   */
  @TableId(value = "r_id", type = IdType.AUTO)
  private Integer id;

  /**
   * 角色名
   */
  @TableField("name")
  private String name;


}
