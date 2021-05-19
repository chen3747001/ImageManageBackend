package com.chen.imagemanage.service.user;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chen.imagemanage.mapper.TeamMapper;
import com.chen.imagemanage.mapper.UserMapper;
import com.chen.imagemanage.model.dto.LoginDTO;
import com.chen.imagemanage.model.dto.RegisterDTO;
import com.chen.imagemanage.model.entity.Team;
import com.chen.imagemanage.model.entity.User;
import com.chen.imagemanage.service.team.TeamService;
import com.chen.imagemanage.utils.JwtUtil;
import com.chen.imagemanage.utils.MD5Util;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;

@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Resource
    UserMapper userMapper;
    @Resource
    TeamService teamService;

    //检验该用户是否存在
    @Override
    public boolean isExisted(String name){
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUsername, name);
        User umsUser = baseMapper.selectOne(wrapper);
        return ObjectUtils.isEmpty(umsUser);
    }

    //检验该用户名或者邮箱是否已被使用
    @Override
    public boolean isUsed(String name,String email){
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
//        wrapper.eq(User::getUsername, name).eq(User::getEmail, email);
        wrapper.eq(User::getUsername, name).or().eq(User::getEmail, email);
        User umsUser = baseMapper.selectOne(wrapper);
        return ObjectUtils.isEmpty(umsUser);
    }

    //用户注册
    @Override
    public User executeRegister(RegisterDTO dto) {
        //查询是否有相同用户名的用户
//        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
//        wrapper.eq(User::getUsername, dto.getName()).or().eq(User::getEmail, dto.getEmail());
//        User umsUser = baseMapper.selectOne(wrapper);

        User addUser = User.builder()
                .username(dto.getName())
                .alias(dto.getName())
                .password(MD5Util.getPwd(dto.getPass()))
                .email(dto.getEmail())
                .createTime(new Date())
                .avatar("add.png")
                .score(0)
                .build();

        if(!this.isUsed(dto.getName(),dto.getEmail()) || !teamService.isUsed(dto.getName(),dto.getEmail())){
//        if (!ObjectUtils.isEmpty(umsUser)) {
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

    //修改头像信息
    @Override
    public Boolean setAvatar(String userName,String avatar){
        return userMapper.updateAvatar(userName,avatar);
    }

    //修改用户信息
    public Boolean updateUserInformation(String userName,String bio,String email,String mobile,String role){
        return userMapper.updateUserInformation(userName,bio,email,mobile,role);
    }

}
