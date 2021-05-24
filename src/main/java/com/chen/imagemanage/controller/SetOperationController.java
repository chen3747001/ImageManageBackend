package com.chen.imagemanage.controller;

import com.chen.imagemanage.common.api.ApiResult;
import com.chen.imagemanage.model.entity.SetOperation;
import com.chen.imagemanage.service.action.ActionService;
import com.chen.imagemanage.service.setOperation.SetOperationService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/setOperation")
public class SetOperationController extends BaseController{
    @Resource
    private SetOperationService setOperationService;

    @GetMapping("/getOperationsBySetName")
    public ApiResult<List<SetOperation>> getOperations(
            @RequestParam(value = "setName")String setName){
        List<SetOperation> setOperations=setOperationService.getOperationsBySetName(setName);
        return ApiResult.success(setOperations);
    }
}
