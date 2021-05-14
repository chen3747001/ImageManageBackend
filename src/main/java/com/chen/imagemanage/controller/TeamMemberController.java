package com.chen.imagemanage.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chen.imagemanage.common.api.ApiResult;
import com.chen.imagemanage.model.entity.Team;
import com.chen.imagemanage.model.entity.TeamMember;
import com.chen.imagemanage.model.vo.TeamMemberVO;
import com.chen.imagemanage.service.teamMember.TeamMemberService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

import java.util.List;

import static com.chen.imagemanage.utils.JwtUtil.USER_NAME;

@RestController
@RequestMapping("/teamMember")
public class TeamMemberController {
    @Resource
    TeamMemberService teamMemberService;
    //返回该团队名的所有用户的信息（包括简介和头像）Page
    @RequestMapping(value = "/getTeamByMemberName", method = RequestMethod.GET)
    public ApiResult<Page<TeamMemberVO>> getTeamByMemberNamePage(
            @RequestParam(value = "pageNo", defaultValue = "1")  Integer pageNo,
            @RequestParam(value = "size", defaultValue = "10") Integer pageSize,
            @RequestParam(value = "teamName") String teamName) {
        System.out.println("开始显示成员信息");
        Page<TeamMemberVO> result = teamMemberService.getTeamMemberPage(new Page<>(pageNo, pageSize),teamName);
        return ApiResult.success(result);
    }

    //返回该团队名的所有用户的信息
    @RequestMapping(value = "/getTeamByMemberNameList", method = RequestMethod.GET)
    public ApiResult<List<TeamMember>> getTeamByMemberNameList(
            @RequestParam(value = "teamName") String teamName) {
        System.out.println("开始显示成员信息");
        List<TeamMember> result = teamMemberService.getTeamMemberList(teamName);
        return ApiResult.success(result);
    }

    //修改团队成员的信息
    @PostMapping("/updatePower")
    public ApiResult<Object> updatePower(@RequestBody TeamMember teamMember){
        boolean updateOk = teamMemberService.updatePower(teamMember.getTeamName(),teamMember.getMemberName(),teamMember.getAbleDelete(),
                teamMember.getAbleAdd(),teamMember.getAbleCreateSet(),teamMember.getAbleDeleteSet());
//        System.out.println("delete"+teamMember.getAbleDelete()+"add"+teamMember.getAbleAdd());
        if(updateOk){
            return ApiResult.success("修改成功");
        }
        else {
            return ApiResult.failed("修改失败");
        }
    }
}
