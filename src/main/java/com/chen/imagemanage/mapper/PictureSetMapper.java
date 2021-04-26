package com.chen.imagemanage.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chen.imagemanage.model.entity.PictureSet;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PictureSetMapper extends BaseMapper<PictureSet> {
    Page<PictureSet> selectListAndPage(@Param("page") Page<PictureSet> page, @Param("tab") String tab,@Param("ownerName") String ownerName);
}
