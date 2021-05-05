package com.chen.imagemanage.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.chen.imagemanage.model.entity.Picture;

import java.util.List;

public interface PictureMapper extends BaseMapper<Picture> {
    //添加图片时插入数据
    boolean insertPictureData(List<Picture> pictureList);
}
