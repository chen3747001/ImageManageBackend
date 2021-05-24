package com.chen.imagemanage.service.pictureSet;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.chen.imagemanage.model.entity.PictureSet;
import com.chen.imagemanage.model.vo.PictureCardVO;

import java.util.Date;
import java.util.List;

public interface PictureSetService extends IService<PictureSet> {

    //创建一个新的数据集
    PictureSet create(PictureSet pictureSet);

    //展示我的数据集(返回数据类型为数据库表类型)
    List<PictureSet> showMySet(String owner);

    //分页展示我的数据集
    Page<PictureCardVO> getMyList(Page<PictureCardVO> page, String tab, String ownerName,String scenario,String dataKind,String searchName);

    //分页展示公共的数据集
    Page<PictureCardVO> getPublicList(Page<PictureCardVO> page, String tab,String scenario,String dataKind,String searchName);

    //获得对应名称的数据集的信息
    PictureSet getMessageByName(String name);

    //上传数据时修改对应数据集的信息
    boolean uploadPicture(String name, Date amendTime, Integer amountPicture, Double size);

    //删除数据时修改对应数据集信息
    boolean deletePicture(String name, Date amendTime, Integer amountPicture, Double size);

    //修改头像信息
    boolean updateAvatar(String setName,String avatar);

    //修改数据集信息
    Boolean updateSetInformation(String setName,String bio,String scenario,String dataKind);

    //新增一个浏览人数
    Boolean addBrowse(String setName);

    //删除对应数据集的信息
    Boolean deleteSet(String setName);
}
