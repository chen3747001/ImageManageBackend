package com.chen.imagemanage.service.picture;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chen.imagemanage.mapper.BillboardMapper;
import com.chen.imagemanage.mapper.PictureMapper;
import com.chen.imagemanage.mapper.PictureSetMapper;
import com.chen.imagemanage.model.entity.Billboard;
import com.chen.imagemanage.model.entity.Picture;
import com.chen.imagemanage.model.entity.PictureSet;
import com.chen.imagemanage.service.billboard.BillboardService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class PictureServiceImpl extends ServiceImpl<PictureMapper
        , Picture> implements PictureService {
    @Resource
    private PictureMapper pictureMapper;

    //上传数据时添加数据信息
    @Override
    public boolean insertPictureData(List<Picture> pictureList){
        return pictureMapper.insertPictureData(pictureList);
    }

    //上传图片数据
    @Override
    public boolean insertData(Picture data){
        baseMapper.insert(data);
        return true;
    }

    //获得对应数据集的所有图片的信息
    @Override
    public List<Picture> getPicInformation(String setName){
        LambdaQueryWrapper<Picture> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Picture::getSetName,setName);
        return baseMapper.selectList(wrapper);
    }

    //删除选中的图片
    public boolean deletePictureList(String[] pictureList){
        try{
            int i=0;
            for(i=0;i<pictureList.length;i++) {
                LambdaQueryWrapper<Picture> wrapper = new LambdaQueryWrapper<>();
                wrapper.eq(Picture::getUid,pictureList[i]);
                baseMapper.delete(wrapper);
            }
        }
        catch (Exception e){
            log.warn("文件不存在");
        }

        return true;
    }

    //删除对应数据集的所有图片记录
    @Override
    public boolean deletePicturesBySetName(String setName){
        LambdaQueryWrapper<Picture> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Picture::getSetName,setName);
        baseMapper.delete(wrapper);
        return true;
    }
}
