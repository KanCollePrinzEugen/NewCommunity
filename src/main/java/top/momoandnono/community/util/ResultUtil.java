package top.momoandnono.community.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;

/**
 * 异步返回工具包
 * @author l
 */
public class ResultUtil {


    /**
     * 成功请求
     * @param consumer lambda
     */
    public static void success(Consumer<Map<String, Object>> consumer) {
        final Map<String, Object> map = new HashMap<>(1);
        map.put("state", 200);
        consumer.accept(map);
        out(map);
    }

    /**
     * 成功请求
     * @param message 消息
     */
    public static void success(String message) {
        final Map<String, Object> map = new HashMap<>(1);
        map.put("state", 200);
        map.put("message", message);
        out(map);
    }

    /**
     * 失败请求
     * @param obj 任何类型
     * @param message 错误消息
     */
    public static void notNull(Object obj, String message) {
        Objects.requireNonNull(obj, () -> {
            err(message);
            return message;
        });
    }

    public static void isTrue(boolean flag, String message) {
        if (!flag) {
            return;
        }
        err(message);
        throw new RuntimeException(message);
    }


    /**
     * 失败请求
     * @param message 消息
     */
    public static void err(String message) {
        final Map<String, Object> map = new HashMap<>(1);
        map.put("state", 200);
        map.put("message", message);
        out(map);
    }

    /**
     * 异步返回
     * @param map 返回消息
     */
    private static void out(Map<String, Object> map){
        ServletOutputStream outputStream = null;
        try {
            ServletRequestAttributes servletRequestAttributes =  (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            assert servletRequestAttributes != null;
            HttpServletResponse response = servletRequestAttributes.getResponse();
            assert response != null;
            outputStream =  response.getOutputStream();
            outputStream.write(new ObjectMapper().writeValueAsString(map).getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
