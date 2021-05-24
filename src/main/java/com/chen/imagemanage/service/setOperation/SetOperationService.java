package com.chen.imagemanage.service.setOperation;

import com.baomidou.mybatisplus.extension.service.IService;
import com.chen.imagemanage.model.entity.SetOperation;

import java.util.List;

public interface SetOperationService extends IService<SetOperation> {
    //添加一个新的操作的信息
    SetOperation createOperation(String setName,String handler,String operation);

    //根据数据集名称返回所有操作的信息
    List<SetOperation> getOperationsBySetName(String setName);
}
