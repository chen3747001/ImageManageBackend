package com.chen.imagemanage.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chen.imagemanage.common.api.ApiResult;
import com.chen.imagemanage.model.entity.Action;
import com.chen.imagemanage.service.action.ActionService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/action")
public class ActionController extends BaseController{
    @Resource
    private ActionService actionService;

    //邀请用户加入团队
    @PostMapping("/inviteJoin")
    public ApiResult<Object> inviteToJoin(
            @RequestParam(value = "sender")String sender,
            @RequestParam(value = "receiver")String receiver,
            @RequestParam(value = "object")String object){
        String action="邀请加入";
        String message=sender+" 邀请你加入团队（"+object+")";
        Action action1=actionService.createAction(sender,receiver,action,object,message);
        if(action1!=null){
            return ApiResult.success("已发送邀请");
        }
        else{
            return ApiResult.failed("发送邀请失败，该用户不存在或者是自己");
        }
    }

    //展示我的信息
    @GetMapping("/showAction")
    public Action showAction(
            @RequestParam(value = "name")String name){
        return actionService.getNotReadAction(name);
    }

    //分页展示我的信息（未处理）
    @GetMapping("/showNotSolvedPageAction")
    public ApiResult<Page<Action>> showNotSolvedPageAction(
            @RequestParam(value = "pageNo", defaultValue = "1")  Integer pageNo,
            @RequestParam(value = "size", defaultValue = "10") Integer pageSize,
            @RequestParam(value = "name")String name){
        return ApiResult.success(actionService.getMyListNotSolved(new Page<>(pageNo,pageSize),name));
    }

    //分页展示我的信息（已处理）
    @GetMapping("/showSolvedPageAction")
    public ApiResult<Page<Action>> showSolvedPageAction(
            @RequestParam(value = "pageNo", defaultValue = "1")  Integer pageNo,
            @RequestParam(value = "size", defaultValue = "10") Integer pageSize,
            @RequestParam(value = "name")String name){
        return ApiResult.success(actionService.getMyListSolved(new Page<>(pageNo,pageSize),name));
    }

    //处理请求
    @PostMapping("/dealAction")
    public ApiResult<Object> dealAction(
            @RequestParam(value = "id") String id,
            @RequestParam(value = "result")String result){
        boolean dealOk=actionService.updateAction(id,result);
        if(dealOk){
            return ApiResult.success("处理成功");
        }
        else {
            return ApiResult.failed("处理失败，请稍后再试");
        }
    }
}