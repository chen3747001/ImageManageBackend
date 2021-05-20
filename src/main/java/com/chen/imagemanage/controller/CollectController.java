package com.chen.imagemanage.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chen.imagemanage.common.api.ApiResult;
import com.chen.imagemanage.model.entity.Collect;
import com.chen.imagemanage.model.vo.PictureCardVO;
import com.chen.imagemanage.service.action.ActionService;
import com.chen.imagemanage.service.collect.CollectService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Collection;

@RestController
@RequestMapping("/collect")
public class CollectController extends BaseController{
    @Resource
    private CollectService collectService;

    //查询该收藏是否存在
    @PostMapping("/isExisted")
    public ApiResult<Object> isExisted(
            @RequestParam(value = "username")String username,
            @RequestParam(value = "setname")String setname){
        boolean isExisted=collectService.isExisted(username,setname);
        if(isExisted){
            return ApiResult.success("存在");
        }
        else {
            return ApiResult.success("不存在");
        }
    }

    //新建一个收藏
    @PostMapping("/createCollect")
    public ApiResult<Object> createCollect(
            @RequestParam(value = "username")String username,
            @RequestParam(value = "setname")String setname){
        Collect collect=collectService.createCollect(username,setname);
        return ApiResult.success("创建新收藏成功");
    }

    //删除一个收藏
    @PostMapping("/deleteCollect")
    public ApiResult<Object> deleteCollect(
            @RequestParam(value = "username")String username,
            @RequestParam(value = "setname")String setname){
        boolean isDelete=collectService.deleteCollect(username,setname);
        if(isDelete){
            return ApiResult.success("删除成功");
        }
        else {
            return ApiResult.success("删除失败");
        }
    }

    //分页显示用户收藏的数据集
    @RequestMapping(value = "/showCollects", method = RequestMethod.GET)
    public ApiResult<Page<PictureCardVO>> showCollectSetPage(
            @RequestParam(value = "user") String userName,
            @RequestParam(value = "pageNo", defaultValue = "1")  Integer pageNo,
            @RequestParam(value = "size", defaultValue = "10") Integer pageSize) {
        System.out.println("开始查找collect page"+userName);
        Page<PictureCardVO> result = collectService.getCollectPage(new Page<>(pageNo, pageSize),userName);
        return ApiResult.success(result);
    }
}
