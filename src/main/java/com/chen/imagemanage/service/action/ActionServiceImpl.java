package com.chen.imagemanage.service.action;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chen.imagemanage.mapper.ActionMapper;
import com.chen.imagemanage.model.entity.Action;
import com.chen.imagemanage.model.entity.Team;
import com.chen.imagemanage.model.vo.PictureCardVO;
import com.chen.imagemanage.service.teamMember.TeamMemberService;
import com.chen.imagemanage.service.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

@Slf4j
@Service
public class ActionServiceImpl extends ServiceImpl<ActionMapper
        , Action> implements ActionService {
    @Resource
    private ActionMapper actionMapper;
    @Resource
    private UserService userService;
    @Resource
    private TeamMemberService teamMemberService;
    //放回该用户的未接收的信息
    @Override
    public Action getNotReadAction(String name){
        LambdaQueryWrapper<Action> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Action::getReceiver, name).eq(Action::getState,0);
        Action action= baseMapper.selectOne(wrapper);
        if(action==null){
            LambdaQueryWrapper<Action> wrapper1 = new LambdaQueryWrapper<>();
            wrapper.eq(Action::getSender, name).eq(Action::getState,1);
            action=baseMapper.selectOne(wrapper1);
        }
        return action;
    }

    //创建一个信息
    @Override
    public Action createAction(String sender,String receiver,String action,String object,String message){
        if(userService.isExisted(receiver)){
            log.warn("该用户不存在");
            return null;
        }
        if(sender.equals(receiver)){
            log.warn("你很幽默，自己邀请自己");
            return null;
        }
        Action action1=Action.builder()
                .sender(sender)
                .receiver(receiver)
                .action(action)
                .object(object)
                .message(message)
                .date(new Date())
                .state("notSolved")
                .result("null")
                .build();
        baseMapper.insert(action1);
        return action1;
    }

    //修改一个信息
    @Override
    public Boolean updateAction(String id,String result){
        Date newdate=new Date();
        if(result.equals("同意")){
            Action action=baseMapper.selectById(id);
            String teamName=action.getObject();
            String memberName=action.getReceiver();
            boolean addOk=teamMemberService.addMember(teamName,memberName,0,0,0,0);
            if(!addOk){
                return false;
            }
        }
        return actionMapper.updateAction(id,result,newdate);
    }

    //分页返回该用户的信息（未处理的）
    @Override
    public Page<Action> getMyListNotSolved(Page<Action> page, String name){
        Page<Action> myActions = actionMapper.getNotSolvedActions(page,name);
        return myActions;
    }

    //分页返回该用户的信息（已处理的）
    @Override
    public Page<Action> getMyListSolved(Page<Action> page, String name){
        Page<Action> myActions = actionMapper.getSolvedActions(page,name);
        return myActions;
    }
}
