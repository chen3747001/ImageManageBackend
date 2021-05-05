package com.chen.imagemanage.service.picture;

import com.baomidou.mybatisplus.extension.service.IService;
import com.chen.imagemanage.model.entity.Picture;
import com.chen.imagemanage.model.entity.PictureSet;

import java.util.Date;
import java.util.List;

public interface PictureService extends IService<Picture> {
    //上传数据时添加数据信息
    boolean insertPictureData(List<Picture> pictureList);

    //上传图片数据
    boolean insertData(Picture data);

    //获得对应数据集的所有图片的信息
    List<Picture> getPicInformation(String setName);

    //删除选中的图片
    boolean deletePictureList(String[] pictureList);
}
