package top.momoandnono.community.pojo.query;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.StringUtils;
import top.momoandnono.community.pojo.enty.UserEntity;
import top.momoandnono.community.util.SecurityUtil;

import java.io.Serializable;

/**
 * 用户分页查询请求
 * @author l
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class UserQuery extends PageBase implements Serializable {

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
     * 请求筛选
     * @return lambda
     */
    @Override
    public LambdaQueryWrapper<UserEntity> generate() {
        final LambdaQueryWrapper<UserEntity> query = Wrappers.lambdaQuery(UserEntity.class);
        query.ne(UserEntity::getId, SecurityUtil.context().getId());
        if (StringUtils.isNotBlank(nickname)) {
            query.likeRight(UserEntity::getNickname, nickname);
        }
        if (StringUtils.isNotBlank(mobile)) {
            query.likeRight(UserEntity::getMobile, mobile);
        }


        return query;
    }
}
