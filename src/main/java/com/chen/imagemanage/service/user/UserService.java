package com.chen.imagemanage.service.user;

import com.baomidou.mybatisplus.extension.service.IService;
import com.chen.imagemanage.model.dto.RegisterDTO;
import com.chen.imagemanage.model.entity.User;

public interface UserService extends IService<User> {

    /**
     * 注册功能
     * @return 注册对象
     */
    User executeRegister(RegisterDTO dto);


}
