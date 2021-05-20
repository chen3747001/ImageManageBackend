package com.chen.imagemanage.service.teamMember;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.chen.imagemanage.model.entity.Team;
import com.chen.imagemanage.model.entity.TeamMember;
import com.chen.imagemanage.model.vo.RightVO;
import com.chen.imagemanage.model.vo.TeamMemberVO;

import java.util.List;

public interface TeamMemberService extends IService<TeamMember> {
    //检验该用户是否已在团队中
    boolean isInThisTeam(String teamName,String memberName);

    //添加一个团队成员
    boolean addMember(String teamName,String memberName,Integer ableDelete,Integer ableAdd,Integer ableCreateSet,Integer ableDeleteSet);

    //根据团队名分页展示团队成员信息(包括简介和头像)Page
    Page<TeamMemberVO> getTeamMemberPage(Page<Team> page, String teamName);

    //根据团队名返回成员的信息
    List<TeamMember> getTeamMemberList(String teamName);

    //修改团队中成员的权力
    boolean updatePower(String teamName,String memberName,Integer ableDelete,Integer ableAdd,Integer ableAddSet,Integer ableDeleteSet);

    //删除团队成员
    Integer deleteMember(String teamName,String memberName);

    //检测对应用户是否在对应团队中
    Boolean isInTeam(String teamName,String userName);

    //返回团队成员在团队中的权限
    RightVO memberRight(String teamName,String memberName);
}
