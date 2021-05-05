package com.chen.imagemanage.service.pictureSet;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chen.imagemanage.mapper.PictureSetMapper;
import com.chen.imagemanage.model.entity.PictureSet;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class PictureSetServiceImpl extends ServiceImpl<PictureSetMapper, PictureSet> implements PictureSetService{
    @Resource
    private PictureSetMapper pictureSetMapper;

    @Override
    public PictureSet create(PictureSet pictureSet){
        //查询是否有相同名称的数据集
        LambdaQueryWrapper<PictureSet> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(PictureSet::getName, pictureSet.getName());
        PictureSet judge = baseMapper.selectOne(wrapper);

            PictureSet addSet = PictureSet.builder()
                .name(pictureSet.getName())
                .createTime(new Date()).useRange(pictureSet.getUseRange()).owner(pictureSet.getOwner()).avatar(pictureSet.getAvatar()).browse(0).amountPicture(0).size(0.0)
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

    @Override
    public List<PictureSet> showMySet(String owner){
        LambdaQueryWrapper<PictureSet> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(PictureSet::getOwner, owner);

        List<PictureSet> wrapperResult=baseMapper.selectList(wrapper);
        return wrapperResult;
    }

    @Override
    public Page<PictureSet> getMyList(Page<PictureSet> page, String tab,String ownerName){
        LambdaQueryWrapper<PictureSet> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(PictureSet::getOwner, ownerName);

        Page<PictureSet> mySet = pictureSetMapper.selectListAndPage(page, tab,ownerName);
        return mySet;
    }

    //获得对应名称的数据集的信息
    @Override
    public PictureSet getMessageByName(String name){
        //查询是否有相同名称的数据集
        LambdaQueryWrapper<PictureSet> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(PictureSet::getName,name);
        PictureSet data = baseMapper.selectOne(wrapper);
        return data;
    }

    //上传数据时修改对应数据集的信息
    @Override
    public boolean uploadPicture(String name, Date amendTime, Integer amountPicture, Double size){
        return pictureSetMapper.uploadPicture(name,amendTime,amountPicture,size);
    }

    //删除数据时修改对应数据集的信息
    @Override
    public boolean deletePicture(String name, Date amendTime, Integer amountPicture, Double size){
        return pictureSetMapper.uploadPicture(name,amendTime,amountPicture,size);
    }
}
