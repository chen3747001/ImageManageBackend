package com.chen.imagemanage.service.pictureSet;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.chen.imagemanage.model.entity.PictureSet;

import java.util.Date;
import java.util.List;

public interface PictureSetService extends IService<PictureSet> {

    //创建一个新的数据集
    PictureSet create(PictureSet pictureSet);

    //展示我的数据集(返回数据类型为数据库表类型)
    List<PictureSet> showMySet(String owner);

    //分页展示我的数据集
    Page<PictureSet> getMyList(Page<PictureSet> page, String tab,String ownerName);

    //获得对应名称的数据集的信息
    PictureSet getMessageByName(String name);

    //上传数据时修改对应数据集的信息
    boolean uploadPicture(String name, Date amendTime, Integer amountPicture, Double size);

    //删除数据时修改对应数据集信息
    boolean deletePicture(String name, Date amendTime, Integer amountPicture, Double size);

}
