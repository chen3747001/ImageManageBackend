package com.chen.imagemanage.service.team;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chen.imagemanage.mapper.PictureSetMapper;
import com.chen.imagemanage.mapper.TeamMapper;
import com.chen.imagemanage.model.entity.PictureSet;
import com.chen.imagemanage.model.entity.Team;
import com.chen.imagemanage.model.entity.TeamMember;
import com.chen.imagemanage.model.entity.User;
import com.chen.imagemanage.service.pictureSet.PictureSetService;
import com.chen.imagemanage.service.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class TeamServiceImpl extends ServiceImpl<TeamMapper, Team> implements TeamService {
    @Resource
    private UserService userService;
    @Resource
    private TeamMapper teamMapper;

    //检验该用户名或者邮箱是否已被使用
    @Override
    public boolean isUsed(String name,String email){
        LambdaQueryWrapper<Team> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Team::getName, name).or().eq(Team::getEmail, email);
        Team umsTeam = baseMapper.selectOne(wrapper);
        return ObjectUtils.isEmpty(umsTeam);
    }

    //新建团队
    @Override
    public boolean createTeam(Team team){
        if(!this.isUsed(team.getName(),team.getEmail()) || !userService.isUsed(team.getName(),team.getEmail())){
            log.info("账号或邮箱已存在！");
            return false;
        }
        else{
            baseMapper.insert(team);
            return true;
        }
    }

    //返回该用户名的所有团队信息
    @Override
    public List<Team> selectTeamsByMemberName(String memberName){
        return teamMapper.selectTeamsByMemberName(memberName);
    }

    //分页展示团队信息
    @Override
    public Page<Team> getTeamPage(Page<Team> page, String memberName){
        Page<Team> mySet = teamMapper.getTeamPage(page,memberName);
        return mySet;
    }

    //根据团队名返回团队的信息
    @Override
    public Team getTeamByTeamName(String teamName){
        LambdaQueryWrapper<Team> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Team::getName, teamName);
        return baseMapper.selectOne(wrapper);
    }

    //修改头像信息
    @Override
    public boolean updateAvatar(String teamName,String avatar){
        return teamMapper.updateAvatar(teamName,avatar);
    }

    //修改团队信息
    @Override
    public Boolean updateTeamInformation(String teamName,String bio,String email){
        return teamMapper.updateTeamInformation(teamName,bio,email);
    }


}
