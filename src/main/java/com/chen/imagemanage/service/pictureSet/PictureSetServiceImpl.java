package com.chen.imagemanage.service.pictureSet;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chen.imagemanage.mapper.PictureSetMapper;
import com.chen.imagemanage.model.entity.PictureSet;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

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

//    @Override
//    public List<PictureSetDetailVO> showMineSet(String owner) throws IOException {
//        LambdaQueryWrapper<PictureSet> wrapper = new LambdaQueryWrapper<>();
//        wrapper.eq(PictureSet::getOwner, owner);
//
//        List<PictureSet> wrapperResult=baseMapper.selectList(wrapper);
//        List<PictureSetDetailVO> result=new ArrayList<PictureSetDetailVO>();
//
//        for(PictureSet pictureSet : wrapperResult){
//
//            String avatarPath=pictureSet.getAvatar();
//            byte[] avatarData=null;
//            FileInputStream picInput=null;
//            File file=null;
//            Blob blob=null;
//            //备用的图片路径
//            String path="F:/ImageManage/data/avatar/temp.jpg";
//            //如果有图片路径
//            if(avatarPath!=null && avatarPath!=""){
//                avatarPath="F:\\ImageManage\\data\\avatar\\"+avatarPath;
//                file=new File(avatarPath);
//                picInput = new FileInputStream(file);
//
//                //有图片路径并且图片在本地有数据
//                if(picInput.available()!=0){
//                    avatarData=new byte[picInput.available()];
//                    picInput.read(avatarData);
//                }
//
//                //有图片路径但图片在本地没有数据
//                else{
//                    file=new File(path);
//                    picInput = new FileInputStream(file);
//                    avatarData=new byte[picInput.available()];
//                    picInput.read(avatarData);
//                }
//
//            }
//
//            //如果没有图片路径
//            else{
//                file=new File(path);
//                picInput = new FileInputStream(file);
//                avatarData=new byte[picInput.available()];
//                picInput.read(avatarData);
//            }
//
//
//            picInput = new FileInputStream(file);
//            avatarData=new byte[picInput.available()];
//            picInput.read(avatarData);
//
//
//
//            System.out.println(avatarData);
//
//            PictureSetDetailVO temp=PictureSetDetailVO.builder().name(pictureSet.getName())
//                    .owner(pictureSet.getOwner())
//                    .avatar_detail(avatarData)
//                    .avatar(pictureSet.getAvatar())
//                    .size(pictureSet.getSize())
//                    .amountPicture(pictureSet.getAmountPicture())
//                    .useRange(pictureSet.getUseRange())
//                    .id(pictureSet.getId())
//                    .createTime(pictureSet.getCreateTime())
//                    .amendTime(pictureSet.getAmendTime())
//                    .build();
//
//
//            System.out.println(temp);
//            result.add(temp);
//
//        }
//        System.out.println(result);
//        return result;
//    }

    @Override
    public List<PictureSet> showMySet(String owner){
        LambdaQueryWrapper<PictureSet> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(PictureSet::getOwner, owner);

        List<PictureSet> wrapperResult=baseMapper.selectList(wrapper);
        return wrapperResult;
    }
}
