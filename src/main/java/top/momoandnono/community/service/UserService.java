package top.momoandnono.community.service;

import org.springframework.web.bind.annotation.RequestBody;
import top.momoandnono.community.pojo.query.UserQuery;
import top.momoandnono.community.pojo.request.UserRequest;

/**
 * 用户管理业务逻辑
 * @author l
 */
public interface UserService {

    /**
     * 添加用户
     * @param userRequest 用户请求信息
     */
    void insertUser(UserRequest userRequest);

    /**
     * 修改用户
     * @param userRequest 用户请求信息
     */
    void updateUser(UserRequest userRequest);

    /**
     * 删除用户
     * @param id 用户id
     */
    void deleteUser(Integer id);

    /**
     * 查询用户
     * @param query 查询条件
     */
    void pageUser(@RequestBody UserQuery query);

    /**
     * 注册
     * @param userRequest 用户请求信息
     */
    void register(UserRequest userRequest);
}
