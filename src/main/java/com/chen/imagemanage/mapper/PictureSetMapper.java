package com.chen.imagemanage.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chen.imagemanage.model.entity.PictureSet;
import com.chen.imagemanage.model.vo.PictureCardVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface PictureSetMapper extends BaseMapper<PictureSet> {
    //分页展示我的数据集
    Page<PictureCardVO> selectListAndPage(@Param("page") Page<PictureCardVO> page, @Param("tab") String tab, @Param("ownerName") String ownerName,@Param("scenario") String scenario,@Param("dataKind") String dataKind,@Param("searchName") String searchName);

    //分页展示公共数据集
    Page<PictureCardVO> selectPublicListAndPage(@Param("page") Page<PictureCardVO> page, @Param("tab") String tab,@Param("scenario") String scenario,@Param("dataKind") String dataKind,@Param("searchName") String searchName);

    //上传数据时修改数据集信息
    boolean uploadPicture(@Param("name") String name, @Param("amendTime") Date amendTime, @Param("amountPicture") Integer amountPicture,@Param("size") Double size);

    //修改团队头像信息
    boolean updateAvatar(String setName,String avatar);

    //修改团队信息
    boolean updateSetInformation(String setName,String bio,String scenario,String dataKind);
}
