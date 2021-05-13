package com.chen.imagemanage.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chen.imagemanage.common.api.ApiResult;
import com.chen.imagemanage.model.dto.UpdateUserDTO;
import com.chen.imagemanage.model.entity.PictureSet;
import com.chen.imagemanage.model.entity.Team;
import com.chen.imagemanage.service.team.TeamService;
import com.chen.imagemanage.service.teamMember.TeamMemberService;
import com.chen.imagemanage.service.user.UserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

import static com.chen.imagemanage.utils.JwtUtil.USER_NAME;

@RestController
@RequestMapping("/team")
public class TeamController {
    @Resource
    TeamService teamService;
    @Resource
    TeamMemberService teamMemberService;
    //新建团队
    @PostMapping("/createTeam")
    public ApiResult<Object> createTeam(@RequestBody Team team){
        System.out.println("新建团队 "+team.getName());
        boolean createOk=teamService.createTeam(team);
        if(createOk){
            //由于创建者是最高权限，赋予所有权限
            teamMemberService.addMember(team.getName(),team.getOwner(),1,1,1,1);
            return ApiResult.success("新建团队成功");
        }
        else{
            return ApiResult.failed("用户名或邮箱已被使用");
        }
    }

    //返回该用户名的所有团队
    @GetMapping("/getTeamByMemberName")
    public ApiResult<List<Team>> getTeamByMemberName(@RequestHeader(value = USER_NAME) String userName){
        System.out.println("开始获得团队信息");
        List<Team> result=teamService.selectTeamsByMemberName(userName);
        System.out.println("数据拉取完成");
        for(int i=0;i<result.size();i++){
            System.out.println("团队名称"+result.get(i).getName());
        }
        return ApiResult.success(result);
    }

    //返回该用户名的所有团队 Page
    @RequestMapping(value = "/getTeamByMemberNamePage", method = RequestMethod.GET)
    public ApiResult<Page<Team>> getTeamByMemberNamePage(
            @RequestParam(value = "pageNo", defaultValue = "1")  Integer pageNo,
            @RequestParam(value = "size", defaultValue = "10") Integer pageSize,
            @RequestHeader(value = USER_NAME) String userName) {
        Page<Team> result = teamService.getTeamPage(new Page<>(pageNo, pageSize),userName);
        return ApiResult.success(result);
    }

    //根据团队名返回团队的信息
    @GetMapping("/getTeamByTeamName")
    public ApiResult<Team> getTeamByTeamName(@RequestParam(value="teamName") String teamName){
        return ApiResult.success(teamService.getTeamByTeamName(teamName));
    }

    //修改用户信息
    @PostMapping("/updateInformation")
    public ApiResult<Object> updateUserInformation(@RequestParam(value = "teamName")String teamName,
                                                   @RequestParam(value = "bio")String bio,
                                                   @RequestParam(value = "email")String email){
        boolean updateOk=teamService.updateTeamInformation(teamName,bio,email);
        if(updateOk){
            return ApiResult.success(null, "修改用户信息成功");
        }
        else{
            return ApiResult.failed("修改用户信息失败");
        }
    }
}
