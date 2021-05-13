package com.chen.imagemanage.service.teamMember;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chen.imagemanage.mapper.TeamMemberMapper;
import com.chen.imagemanage.model.entity.TeamMember;
import com.chen.imagemanage.model.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class TeamMemberServiceImpl extends ServiceImpl<TeamMemberMapper, TeamMember> implements TeamMemberService {
    //检验该用户是否已在团队中
    @Override
    public boolean isInThisTeam(String teamName,String memberName){
        LambdaQueryWrapper<TeamMember> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(TeamMember::getTeamName, teamName).eq(TeamMember::getMemberName, memberName);
        TeamMember umsTeamMember = baseMapper.selectOne(wrapper);
//        System.out.println(umsTeamMember.getId()+umsTeamMember.toString());
//        System.out.println("判定结果是"+ObjectUtils.isEmpty(umsTeamMember));
        return ObjectUtils.isEmpty(umsTeamMember);
    }

    //添加一个团队成员
    @Override
    public boolean addMember(String teamName,String memberName,Integer ableDelete,Integer ableAdd,Integer ableCreateSet,Integer ableDeleteSet){
        if(this.isInThisTeam(teamName,memberName)) {
            TeamMember teamMember = TeamMember.builder()
                    .teamName(teamName)
                    .memberName(memberName)
                    .ableDelete(ableDelete)
                    .ableAdd(ableAdd)
                    .ableCreateSet(ableCreateSet)
                    .ableDeleteSet(ableDeleteSet)
                    .build();
            baseMapper.insert(teamMember);
            return true;
        }
        else{
            log.info("该用户"+memberName+"已在团队"+teamName+"中");
            return false;
        }
    }
}
