package com.chen.imagemanage.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chen.imagemanage.model.entity.PictureSet;
import com.chen.imagemanage.model.entity.Team;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeamMapper extends BaseMapper<Team> {
    //返回该用户名的所有团队信息
    List<Team> selectTeamsByMemberName(String memberName);

    //分页展示团队
    Page<Team> getTeamPage(@Param("page") Page<Team> page, @Param("memberName") String memberName);

    //修改团队头像信息
    boolean updateAvatar(String teamName,String avatar);

    //修改团队信息
    boolean updateTeamInformation(String teamName,String bio,String email);
}
