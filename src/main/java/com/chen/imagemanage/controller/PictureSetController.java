package com.chen.imagemanage.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.chen.imagemanage.common.api.ApiResult;
import com.chen.imagemanage.model.dto.CreatePictureSetDTO;
import com.chen.imagemanage.model.entity.PictureSet;
import com.chen.imagemanage.model.vo.PictureVO;
import com.chen.imagemanage.service.pictureSet.PictureSetService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.List;

import static com.chen.imagemanage.utils.JwtUtil.USER_NAME;

@RestController
@RequestMapping("/pictureSet")
public class PictureSetController {
    @Resource
    private PictureSetService pictureSetService;

    @GetMapping("/show")
    public ApiResult<List<PictureSet>> getPictureSet() {
        List<PictureSet> list = pictureSetService.list(new
                LambdaQueryWrapper<PictureSet>().eq(PictureSet::getUseRange, "公共"));
        return ApiResult.success(list);
    }

    //创建新的数据集
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ApiResult<PictureSet> createPictureSet(@RequestHeader(value = USER_NAME) String userName, @Valid @RequestBody CreatePictureSetDTO dto) {
        PictureSet pictureSet = PictureSet.builder()
                .name(dto.getName())
                .owner(userName)
                .useRange(dto.getUseRange())
                .build();

        PictureSet result = pictureSetService.create(pictureSet);

        if (ObjectUtils.isEmpty(result)) {
            return ApiResult.failed("该数据集名称已存在！");
        }

        return ApiResult.success(pictureSet);
    }

    //展示属于我的数据集
    @RequestMapping(value = "/mySet", method = RequestMethod.GET)
    public ApiResult<List<PictureSet>> showMyPictureSet(@RequestHeader(value = USER_NAME) String userName) {
        List<PictureSet> result = pictureSetService.showMySet(userName);
        return ApiResult.success(result);
    }

}