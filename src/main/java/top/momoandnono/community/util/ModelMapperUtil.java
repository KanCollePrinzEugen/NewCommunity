package top.momoandnono.community.util;

import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 实体类转换工具类
 * @author l
 */
public class ModelMapperUtil {

    public static final ModelMapper MAPPER = new ModelMapper();

    static {
        // 自定义转换 Date 转 Long
        MAPPER.addConverter((Converter<Date, Long>) context -> context.getSource().getTime());
        // 自定义转换 Long 转 Date
        MAPPER.addConverter((Converter<Long, Date>) context -> new Date(context.getSource()));
        Configuration configuration = MAPPER.getConfiguration();
        configuration.setAmbiguityIgnored(true);
        // 匹配模式设置为严格模式，避免属性名称相似也能被覆盖
        configuration.setMatchingStrategy(MatchingStrategies.STRICT);
    }

    /**
     * list 转换
     * @param collection 转换源
     * @param targetClass 实体类
     */
    public static <T> List<T> map(Collection<?> collection, Class<T> targetClass) {
        if (CollectionUtils.isEmpty(collection)) {
            return Collections.emptyList();
        }
        return collection.stream().map(obj -> MAPPER.map(obj, targetClass)).collect(Collectors.toList());
    }

    /**
     * 实体类 转换
     * @param source 转换源
     * @param targetClass 实体类
     */
    public static <T> T map(Object source, Class<T> targetClass) {
        Objects.requireNonNull(source);
        return MAPPER.map(source, targetClass);
    }


}
