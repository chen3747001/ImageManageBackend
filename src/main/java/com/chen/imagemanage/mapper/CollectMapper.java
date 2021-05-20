package com.chen.imagemanage.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chen.imagemanage.model.entity.Collect;
import com.chen.imagemanage.model.vo.PictureCardVO;
import org.apache.ibatis.annotations.Param;

public interface CollectMapper extends BaseMapper<Collect> {
    //分页显示该用户的收藏的数据集
    Page<PictureCardVO> showCollectSetInPage(@Param("page") Page<PictureCardVO> page,@Param("userName") String userName);

}
