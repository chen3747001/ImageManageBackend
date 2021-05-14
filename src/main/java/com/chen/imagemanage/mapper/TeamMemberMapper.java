package com.chen.imagemanage.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chen.imagemanage.model.entity.Team;
import com.chen.imagemanage.model.entity.TeamMember;
import com.chen.imagemanage.model.vo.TeamMemberVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TeamMemberMapper extends BaseMapper<TeamMember> {
    //根据团队名分页展示团队成员
    Page<TeamMemberVO> getTeamMemberPage(@Param("page") Page<Team> page, @Param("teamName") String teamName);

    //修改团队中成员的权力
    boolean updatePower(String teamName,String memberName,Integer ableDelete,Integer ableAdd,Integer ableAddSet,Integer ableDeleteSet);
}
