package com.chen.imagemanage.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chen.imagemanage.model.entity.PictureSet;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface PictureSetMapper extends BaseMapper<PictureSet> {
    //分页展示数据集
    Page<PictureSet> selectListAndPage(@Param("page") Page<PictureSet> page, @Param("tab") String tab,@Param("ownerName") String ownerName);

    //上传数据时修改数据集信息
    boolean uploadPicture(@Param("name") String name, @Param("amendTime") Date amendTime, @Param("amountPicture") Integer amountPicture,@Param("size") Double size);
}
