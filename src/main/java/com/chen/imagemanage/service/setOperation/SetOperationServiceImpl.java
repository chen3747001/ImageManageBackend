package com.chen.imagemanage.service.setOperation;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chen.imagemanage.mapper.ActionMapper;
import com.chen.imagemanage.mapper.SetOperationMapper;
import com.chen.imagemanage.model.entity.Action;
import com.chen.imagemanage.model.entity.SetOperation;
import com.chen.imagemanage.service.action.ActionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class SetOperationServiceImpl extends ServiceImpl<SetOperationMapper
        , SetOperation> implements SetOperationService {

    //添加一个新的操作的信息
    @Override
    public SetOperation createOperation(String setName,String handler,String operation){
        SetOperation setOperation=SetOperation.builder()
                .date(new Date())
                .setName(setName)
                .handler(handler)
                .operation(operation).build();
        baseMapper.insert(setOperation);
        return setOperation;
    }

    //根据数据集名称返回所有操作的信息
    @Override
    public List<SetOperation> getOperationsBySetName(String setName){
        LambdaQueryWrapper<SetOperation> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SetOperation::getSetName,setName);

        return baseMapper.selectList(wrapper);
    }

    //删除对应数据集的所有操作记录
    @Override
    public Boolean deleteOperations(String setName){
        LambdaQueryWrapper<SetOperation> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SetOperation::getSetName,setName);
        baseMapper.delete(wrapper);
        return true;
    }
}
