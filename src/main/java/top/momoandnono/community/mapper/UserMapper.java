package top.momoandnono.community.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import top.momoandnono.community.pojo.enty.UserEntity;
import top.momoandnono.community.pojo.response.UserResponse;

/**
 * 用户表
 * @author l
 */
@Mapper
public interface UserMapper extends BaseMapper<UserEntity> {

    /**
     * 查询用户分页
     * @param page 分页条件
     * @param queryWrapper 分页条件
     * @return 用户分页
     */
    Page<UserResponse> selectPage(Page<UserEntity> page, @Param("ew") Wrapper<UserEntity> queryWrapper);
}
