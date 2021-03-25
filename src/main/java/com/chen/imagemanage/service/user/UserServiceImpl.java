package com.chen.imagemanage.service.user;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chen.imagemanage.mapper.UserMapper;
import com.chen.imagemanage.model.dto.LoginDTO;
import com.chen.imagemanage.model.dto.RegisterDTO;
import com.chen.imagemanage.model.entity.User;
import com.chen.imagemanage.utils.JwtUtil;
import com.chen.imagemanage.utils.MD5Util;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {


    @Override
    public User executeRegister(RegisterDTO dto) {
        //查询是否有相同用户名的用户
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUsername, dto.getName()).or().eq(User::getEmail, dto.getEmail());
        User umsUser = baseMapper.selectOne(wrapper);

        User addUser = User.builder()
                .username(dto.getName())
                .alias(dto.getName())
                .password(MD5Util.getPwd(dto.getPass()))
                .email(dto.getEmail())
                .createTime(new Date())
                .score(0)
                .build();

        if (!ObjectUtils.isEmpty(umsUser)) {
            log.info("账号或邮箱已存在！");
            User tempWrongUser=null;
            return  tempWrongUser;
        }
        else{
            baseMapper.insert(addUser);
            return addUser;
        }
    }

    @Override
    public User getUserByUsername(String username) {
        return baseMapper.selectOne(new LambdaQueryWrapper<User>().eq(User::getUsername, username));
    }
    @Override
    public String executeLogin(LoginDTO dto) {
        String token = null;
        try {
            User user = getUserByUsername(dto.getUsername());
            String encodePwd = MD5Util.getPwd(dto.getPassword());
            if(!encodePwd.equals(user.getPassword()))
            {
                throw new Exception("密码错误");
            }
            token = JwtUtil.generateToken(String.valueOf(user.getUsername()));
        } catch (Exception e) {
            log.warn("用户不存在or密码验证失败=======>{}", dto.getUsername());
        }
        return token;
    }

}
