package com.chen.imagemanage.service.pictureSet;

import com.baomidou.mybatisplus.extension.service.IService;
import com.chen.imagemanage.model.dto.CreatePictureSetDTO;
import com.chen.imagemanage.model.entity.PictureSet;

public interface PictureSetService extends IService<PictureSet> {

    //创建一个新的数据集
    PictureSet create(PictureSet pictureSet);
}
