package top.momoandnono.community.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import top.momoandnono.community.pojo.enty.UserRoleEntity;

/**
 * 用户角色表
 * @author l
 */
@Mapper
public interface UserRoleMapper extends BaseMapper<UserRoleEntity> {
}
