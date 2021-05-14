package com.chen.imagemanage.service.teamMember;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.chen.imagemanage.model.entity.Team;
import com.chen.imagemanage.model.entity.TeamMember;
import com.chen.imagemanage.model.vo.TeamMemberVO;

public interface TeamMemberService extends IService<TeamMember> {
    //检验该用户是否已在团队中
    boolean isInThisTeam(String teamName,String memberName);

    //添加一个团队成员
    boolean addMember(String teamName,String memberName,Integer ableDelete,Integer ableAdd,Integer ableCreateSet,Integer ableDeleteSet);

    //根据团队名分页展示团队成员信息
    Page<TeamMemberVO> getTeamMemberPage(Page<Team> page, String teamName);
}
