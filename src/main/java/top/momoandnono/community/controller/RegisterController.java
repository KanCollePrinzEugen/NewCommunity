package top.momoandnono.community.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import top.momoandnono.community.pojo.request.UserRequest;
import top.momoandnono.community.service.UserService;

import javax.annotation.Resource;

/**
 * 注册控制器
 * @author l
 */
@RestController
public class RegisterController {

    @Resource
    private UserService userService;

    @PostMapping("/register")
    public void register(@RequestBody UserRequest userRequest) {
        userService.register(userRequest);
    }

}
