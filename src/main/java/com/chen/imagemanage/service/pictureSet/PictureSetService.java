package com.chen.imagemanage.service.pictureSet;

import com.baomidou.mybatisplus.extension.service.IService;
import com.chen.imagemanage.model.entity.PictureSet;

import java.util.List;

public interface PictureSetService extends IService<PictureSet> {

    //创建一个新的数据集
    PictureSet create(PictureSet pictureSet);

    //展示我的数据集(返回数据类型为数据库表类型)
    List<PictureSet> showMySet(String owner);
}
