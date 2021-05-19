package com.chen.imagemanage.service.action;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.chen.imagemanage.model.entity.Action;
import com.chen.imagemanage.model.vo.PictureCardVO;

public interface ActionService  extends IService<Action> {
    //放回该用户的未接收的信息
    Action getNotReadAction(String name);

    //创建一个信息
    Action createAction(String sender,String receiver,String action,String object,String message);

    //修改一个信息
    Boolean updateAction(String id,String result);

    //分页返回该用户的信息（未处理的）
    Page<Action> getMyListNotSolved(Page<Action> page,String name);

    //分页返回该用户的信息（已处理的）
    Page<Action> getMyListSolved(Page<Action> page,String name);
}
