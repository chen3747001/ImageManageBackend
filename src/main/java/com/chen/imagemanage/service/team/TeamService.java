package com.chen.imagemanage.service.team;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.chen.imagemanage.model.entity.PictureSet;
import com.chen.imagemanage.model.entity.Team;

import java.util.List;

public interface TeamService extends IService<Team> {
    //检验该用户名或者邮箱是否已被使用
    boolean isUsed(String name,String email);
    //新建团队
    boolean createTeam(Team team);

    //返回该用户名的所有团队信息
    List<Team> selectTeamsByMemberName(String memberName);

    //分页展示团队信息
    Page<Team> getTeamPage(Page<Team> page, String memberName);

    //根据团队名返回团队的信息
    Team getTeamByTeamName(String teamName);

    //修改头像信息
    boolean updateAvatar(String teamName,String avatar);

    //修改团队信息
    Boolean updateTeamInformation(String teamName,String bio,String email);
}
