package top.momoandnono.community.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import top.momoandnono.community.pojo.enty.RoleEntity;

/**
 * 角色表
 * @author l
 */
@Mapper
public interface RoleMapper extends BaseMapper<RoleEntity> {

    /**
     * 查询角色
     * @param userId 用户id
     * @return 角色信息
     */
    RoleEntity selectOneByUserId(@Param("userId") Integer userId);
}
