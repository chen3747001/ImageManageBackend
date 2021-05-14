package com.chen.imagemanage.service.teamMember;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chen.imagemanage.mapper.TeamMemberMapper;
import com.chen.imagemanage.model.entity.Team;
import com.chen.imagemanage.model.entity.TeamMember;
import com.chen.imagemanage.model.entity.User;
import com.chen.imagemanage.model.vo.TeamMemberVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class TeamMemberServiceImpl extends ServiceImpl<TeamMemberMapper, TeamMember> implements TeamMemberService {
    @Resource
    private TeamMemberMapper teamMemberMapper;

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

    //根据团队名分页展示团队成员信息(包括简介和头像)Page
    public Page<TeamMemberVO> getTeamMemberPage(Page<Team> page, String teamName){
        return teamMemberMapper.getTeamMemberPage(page,teamName);
    }

    //根据团队名返回成员的信息
    public List<TeamMember> getTeamMemberList(String teamName){
        LambdaQueryWrapper<TeamMember> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(TeamMember::getTeamName, teamName);
        return baseMapper.selectList(wrapper);
    }

    //修改团队中成员的权力
    public boolean updatePower(String teamName,String memberName,Integer ableDelete,Integer ableAdd,Integer ableAddSet,Integer ableDeleteSet){

        return teamMemberMapper.updatePower(teamName,memberName,ableDelete,ableAdd,ableAddSet,ableDeleteSet);
    }
}
