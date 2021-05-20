package com.chen.imagemanage.service.collect;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chen.imagemanage.mapper.ActionMapper;
import com.chen.imagemanage.mapper.CollectMapper;
import com.chen.imagemanage.model.entity.Action;
import com.chen.imagemanage.model.entity.Collect;
import com.chen.imagemanage.model.vo.PictureCardVO;
import com.chen.imagemanage.service.action.ActionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

@Slf4j
@Service
public class CollectServiceImpl extends ServiceImpl<CollectMapper
        , Collect> implements CollectService {
    @Resource
    private CollectMapper collectMapper;
    //查询该收藏是否存在
    @Override
    public boolean isExisted(String username,String setname){
        LambdaQueryWrapper<Collect> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Collect::getUsername, username).eq(Collect::getSetname,setname);
        Collect collect= baseMapper.selectOne(wrapper);
        return collect != null;
    }

    //新建一个收藏
    @Override
    public Collect createCollect(String username,String setname){
        Collect collect=Collect.builder()
                .username(username)
                .setname(setname)
                .date(new Date())
                .build();
        baseMapper.insert(collect);
        return collect;
    }

    //删除一个收藏
    @Override
    public Boolean deleteCollect(String username,String setname){
        LambdaQueryWrapper<Collect> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Collect::getUsername, username).eq(Collect::getSetname,setname);
        baseMapper.delete(wrapper);
        return true;
    }

    //分页展示用户收藏的数据集
    @Override
    public Page<PictureCardVO> getCollectPage(Page<PictureCardVO> page, String userName){
        System.out.println("page collect"+userName+page);
        Page<PictureCardVO> collectPage=collectMapper.showCollectSetInPage(page,userName);
        return collectPage;
    }
}
