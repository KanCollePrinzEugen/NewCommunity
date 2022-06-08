package top.momoandnono.community.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.momoandnono.community.constant.RoleStateConstant;
import top.momoandnono.community.mapper.UserMapper;
import top.momoandnono.community.mapper.UserRoleMapper;
import top.momoandnono.community.pojo.enty.UserEntity;
import top.momoandnono.community.pojo.enty.UserRoleEntity;
import top.momoandnono.community.pojo.query.UserQuery;
import top.momoandnono.community.pojo.request.UserRequest;
import top.momoandnono.community.service.UserService;
import top.momoandnono.community.util.ResultUtil;

import javax.annotation.Resource;

/**
 * 用户管理业务逻辑
 * @author l
 */
@Service
public class UserServiceImpl implements UserService {

    @Resource
    private PasswordEncoder passwordEncoder;
    
    @Resource
    private UserMapper userMapper;

    @Resource
    private UserRoleMapper userRoleMapper;
    
    /**
     * 添加用户
     * @param request 用户请求信息
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insertUser(UserRequest request) {
        // 初始化用户信息
        final UserEntity userEntity = request.generateInsert();
        
        // 添加校验
        flagUserInsert(userEntity);

        // 密码加密
        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
        
        // 添加用户
        userMapper.insert(userEntity);

        // 设置对应的角色
        final UserRoleEntity userRoleEntity = new UserRoleEntity();
        userRoleEntity.setUId(userEntity.getId());
        userRoleEntity.setRId(request.getRole());
        this.userRoleMapper.insert(userRoleEntity);

        ResultUtil.success("添加成功");
    }

    /**
     * 修改用户
     * @param request 用户请求信息
     */
    @Override
    public void updateUser(UserRequest request) {
        // 初始化用户信息
        final UserEntity userEntity = request.generateUpdate();

        // 修改校验
        flagUserUpdate(userEntity);

        // 修改用户
        userMapper.updateById(userEntity);

        // 设置对应的角色
        final UserRoleEntity userRoleEntity = new UserRoleEntity();
        userRoleEntity.setRId(request.getRole());
        userRoleMapper.update(userRoleEntity, Wrappers.lambdaQuery(UserRoleEntity.class)
                .eq(UserRoleEntity::getUId, userEntity.getId()));

        ResultUtil.success("修改成功");
    }

    /**
     * 删除用户
     * @param id 用户id
     */
    @Override
    public void deleteUser(Integer id) {
        ResultUtil.notNull(userMapper.selectById(id), "用户不存在");

        userMapper.deleteById(id);

        ResultUtil.success("删除成功");
    }

    /**
     * 查询用户
     * @param query 查询条件
     */
    @Override
    public void pageUser(UserQuery query) {
        ResultUtil.success(map -> map.put("userPage", userMapper.selectPage(query.page(), query.generate())));
    }

    /**
     * 注册
     * @param request 用户请求信息
     */
    @Override
    public void register(UserRequest request) {
        // 初始化注册信息
        final UserEntity userEntity = request.generateRegister();

        // 添加校验
        flagUserInsert(userEntity);

        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));

        // 添加用户
        userMapper.insert(userEntity);

        // 设置对应的角色
        final UserRoleEntity userRoleEntity = new UserRoleEntity();
        userRoleEntity.setUId(userEntity.getId());
        userRoleEntity.setRId(RoleStateConstant.USER);
        this.userRoleMapper.insert(userRoleEntity);

        ResultUtil.success("添加成功");
    }


    /**
     * 添加校验
     * @param userEntity 添加用户信息
     */
    private void flagUserInsert(UserEntity userEntity) {
        final UserEntity queryUser = userMapper.selectOne(Wrappers.lambdaQuery(UserEntity.class)
                .eq(UserEntity::getUsername, userEntity.getUsername()));

        ResultUtil.isTrue(queryUser != null, "用户名已存在");
    }

    /**
     * 修改校验
     * @param userEntity 修改用户信息
     */
    private void flagUserUpdate(UserEntity userEntity) {
        final UserEntity queryUser = userMapper.selectOne(Wrappers.lambdaQuery(UserEntity.class)
                .eq(UserEntity::getId, userEntity.getId()));

        ResultUtil.notNull(queryUser, "用户为空");

        ResultUtil.isTrue(userMapper.selectCount(Wrappers.lambdaQuery(UserEntity.class)
                .ne(UserEntity::getId, userEntity.getId()).
                eq(UserEntity::getUsername, userEntity.getUsername())) > 0, "用户名已存在");

        ResultUtil.isTrue( passwordEncoder
                .matches(queryUser.getPassword(), userEntity.getPassword()), "新密码不能和旧密码相同");

    }
}
