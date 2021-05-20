package com.chen.imagemanage.service.collect;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.chen.imagemanage.model.entity.Collect;
import com.chen.imagemanage.model.vo.PictureCardVO;

public interface CollectService extends IService<Collect> {
    //查询该收藏是否存在
    boolean isExisted(String username,String setname);

    //新建一个收藏
    Collect createCollect(String username,String setname);

    //删除一个收藏
    Boolean deleteCollect(String username,String setname);

    //分页展示用户收藏的数据集
    Page<PictureCardVO> getCollectPage(Page<PictureCardVO> page,String userName);
}
