package com.chen.imagemanage.service.billboard;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chen.imagemanage.mapper.BillboardMapper;
import com.chen.imagemanage.model.entity.Billboard;
import org.springframework.stereotype.Service;

@Service
public class BillboardServiceImpl extends ServiceImpl<BillboardMapper
        , Billboard> implements BillboardService {

}
