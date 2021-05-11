package com.chen.imagemanage.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.chen.imagemanage.model.entity.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMapper extends BaseMapper<User> {
    //修改用户头像信息
    boolean updateAvatar(String userName,String avatar);

    //修改用户信息
    boolean updateUserInformation(String userName,String bio,String email,String mobile,String role);
}
