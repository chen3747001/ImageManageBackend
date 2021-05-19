package com.chen.imagemanage.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chen.imagemanage.model.entity.Action;
import org.apache.ibatis.annotations.Param;

import javax.xml.crypto.Data;
import java.util.Date;
import java.util.List;

public interface ActionMapper  extends BaseMapper<Action> {
    //修改一个信息
    boolean updateAction(String id, String result, Date newDate);

    //搜索与我有关的信息(没处理的)
    Page<Action> getNotSolvedActions(@Param("page") Page<Action> page,@Param("name") String name);

    //搜索与我有关的信息(已经处理的)
    Page<Action> getSolvedActions(@Param("page") Page<Action> page,@Param("name") String name);
}
