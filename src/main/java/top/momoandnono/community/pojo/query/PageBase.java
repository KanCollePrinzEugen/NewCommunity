package top.momoandnono.community.pojo.query;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;

import java.io.Serializable;

/**
 * 查询父类
 * @author l
 */
@Data
public abstract class PageBase implements Serializable {

    /**
     * 当前页
     */
    private Long pageNum;

    /**
     * 一页总数
     */
    private Long pageSize;

    /**
     * 请求筛选
     * @return lambda
     */
    public abstract LambdaQueryWrapper<?> generate();

    /**
     * 分页
     * @return 分页
     */
    public <T> Page<T> page() {
        return new Page<>(pageNum, pageSize);
    }
}
