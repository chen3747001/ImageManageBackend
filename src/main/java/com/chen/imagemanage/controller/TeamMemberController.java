package com.chen.imagemanage.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chen.imagemanage.common.api.ApiResult;
import com.chen.imagemanage.model.entity.Team;
import com.chen.imagemanage.model.entity.TeamMember;
import com.chen.imagemanage.model.vo.TeamMemberVO;
import com.chen.imagemanage.service.teamMember.TeamMemberService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

import static com.chen.imagemanage.utils.JwtUtil.USER_NAME;

@RestController
@RequestMapping("/teamMember")
public class TeamMemberController {
    @Resource
    TeamMemberService teamMemberService;
    //返回该用户名的所有团队 Page
    @RequestMapping(value = "/getTeamByMemberName", method = RequestMethod.GET)
    public ApiResult<Page<TeamMemberVO>> getTeamByMemberNamePage(
            @RequestParam(value = "pageNo", defaultValue = "1")  Integer pageNo,
            @RequestParam(value = "size", defaultValue = "10") Integer pageSize,
            @RequestParam(value = "teamName") String teamName) {
        System.out.println("开始显示成员信息");
        Page<TeamMemberVO> result = teamMemberService.getTeamMemberPage(new Page<>(pageNo, pageSize),teamName);
        return ApiResult.success(result);
    }
}
