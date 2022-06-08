package top.momoandnono.community.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import top.momoandnono.community.pojo.query.UserQuery;
import top.momoandnono.community.pojo.request.UserRequest;
import top.momoandnono.community.service.UserService;

import javax.annotation.Resource;

/**
 * 用户管理控制器
 * @author l
 */
@RestController
@RequestMapping("/users")
@PreAuthorize("hasAnyRole('admin')")
public class UserController {

    @Resource
    private UserService userService;

    @PostMapping("/user")
    public void insertUser(@RequestBody UserRequest userRequest) {
        userService.insertUser(userRequest);
    }

    @PutMapping("/user")
    public void updateUser(@RequestBody UserRequest userRequest) {
        userService.updateUser(userRequest);
    }

    @DeleteMapping("/user/{id}")
    public void deleteUser(@PathVariable("id") Integer id) {
        userService.deleteUser(id);
    }

    @PostMapping("/page")
    public void pageUser(@RequestBody UserQuery query) {
        userService.pageUser(query);
    }
}
