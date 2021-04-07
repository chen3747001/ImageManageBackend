package com.chen.imagemanage.service.pictureSet;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chen.imagemanage.mapper.PictureSetMapper;
import com.chen.imagemanage.model.dto.CreatePictureSetDTO;
import com.chen.imagemanage.model.entity.PictureSet;
import com.chen.imagemanage.model.entity.User;
import com.chen.imagemanage.utils.MD5Util;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class PictureSetServiceImpl extends ServiceImpl<PictureSetMapper, PictureSet> implements PictureSetService{
    @Override
    public PictureSet create(PictureSet pictureSet){
        //查询是否有相同名称的数据集
        LambdaQueryWrapper<PictureSet> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(PictureSet::getName, pictureSet.getName());
        PictureSet judge = baseMapper.selectOne(wrapper);

            PictureSet addSet = PictureSet.builder()
                .name(pictureSet.getName())
                .createTime(new Date()).useRange(pictureSet.getUseRange()).owner(pictureSet.getOwner())
                .build();

            if (!ObjectUtils.isEmpty(judge)) {
                log.info("账号或邮箱已存在！");
                PictureSet tempWrongSet=null;
                return  tempWrongSet;
            }
            else{
                baseMapper.insert(addSet);
                return addSet;
            }
    }
}
