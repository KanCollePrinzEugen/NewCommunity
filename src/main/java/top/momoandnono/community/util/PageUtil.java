package top.momoandnono.community.util;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

/**
 * 分页工具类
 * @author l
 */
public class PageUtil {

    /**
     * 类型转换
     * @param sourcePage 查询父类
     * @param clazz 转换实体类
     * @return 分页信息
     */
    public static <T, E> Page<T> page(Page<E> sourcePage, Class<T> clazz) {
        final Page<T> page = new Page<>();
        page.setTotal(sourcePage.getTotal());
        page.setSize(sourcePage.getSize());
        page.setCurrent(sourcePage.getCurrent());
        page.setOrders(sourcePage.getOrders());
        page.setRecords(ModelMapperUtil.map(sourcePage.getRecords(), clazz));
        return page;
    }

}
