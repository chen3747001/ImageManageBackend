package com.chen.imagemanage.controller;

import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.chen.imagemanage.common.api.ApiResult;
import com.chen.imagemanage.model.dto.LoginDTO;
import com.chen.imagemanage.model.dto.RegisterDTO;
import com.chen.imagemanage.model.dto.UpdateUserDTO;
import com.chen.imagemanage.model.entity.User;
import com.chen.imagemanage.service.user.UserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

import static com.chen.imagemanage.utils.JwtUtil.USER_NAME;

@RestController
@RequestMapping("/user")
public class UserController extends BaseController {
    @Resource
    private UserService userService;

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ApiResult<Map<String, Object>> register(@Valid @RequestBody RegisterDTO dto) {
        User user = userService.executeRegister(dto);
        if (ObjectUtils.isEmpty(user)) {
            return ApiResult.failed("账号或邮箱已存在！");
        }
        Map<String, Object> map = new HashMap<>(16);
        map.put("user", user);
        return ApiResult.success(map);
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ApiResult<Map<String, String>> login(@Valid @RequestBody LoginDTO dto) {
        String token = userService.executeLogin(dto);
        if (ObjectUtils.isEmpty(token)) {
            return ApiResult.failed("账号密码错误");
        }
        Map<String, String> map = new HashMap<>(16);
        map.put("token", token);
        return ApiResult.success(map, "登u录成功");
    }

    @RequestMapping(value = "/info", method = RequestMethod.GET)
    public ApiResult<User> getUser(@RequestHeader(value = USER_NAME) String userName) {
        User user = userService.getUserByUsername(userName);
        return ApiResult.success(user);
    }

    @RequestMapping(value = "/information", method = RequestMethod.GET)
    public ApiResult<User> getUserByName(@RequestParam(value = "userName") String userName) {
        User user = userService.getUserByUsername(userName);
        return ApiResult.success(user);
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public ApiResult<Object> logOut() {
        return ApiResult.success(null, "注销成功");
    }

    //修改用户信息
    @PostMapping("/updateUserInformation")
    public ApiResult<Object> updateUserInformation(@RequestHeader(value = USER_NAME) String userName, @RequestBody UpdateUserDTO updateUserDTO){
        System.out.println(userName+updateUserDTO.getEmail());
        boolean updateOk=userService.updateUserInformation(userName,updateUserDTO.getBio(),updateUserDTO.getEmail(),updateUserDTO.getMobile(),updateUserDTO.getRole());
        if(updateOk){
            return ApiResult.success(null, "修改用户信息成功");
        }
        else{
            return ApiResult.failed("修改用户信息失败");
        }
    }
}