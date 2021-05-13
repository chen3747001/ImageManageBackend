package com.chen.imagemanage.service.user;

import com.baomidou.mybatisplus.extension.service.IService;
import com.chen.imagemanage.model.dto.LoginDTO;
import com.chen.imagemanage.model.dto.RegisterDTO;
import com.chen.imagemanage.model.entity.User;

public interface UserService extends IService<User> {

    //检验该用户名或者邮箱是否已被使用
    boolean isUsed(String name,String email);
    /**
     * 注册功能
     * @return 注册对象
     */
    User executeRegister(RegisterDTO dto);

    //获取用户信息
    User getUserByUsername(String username);

    //用户登录
    String  executeLogin(LoginDTO dto);

    //修改头像信息
    Boolean setAvatar(String userName,String avatar);

    //修改用户信息
    Boolean updateUserInformation(String userName,String bio,String email,String mobile,String role);
}
