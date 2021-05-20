package com.chen.imagemanage.controller;

import com.chen.imagemanage.common.api.ApiResult;
import com.chen.imagemanage.model.vo.RightVO;
import com.chen.imagemanage.service.team.TeamService;
import com.chen.imagemanage.service.teamMember.TeamMemberService;
import com.chen.imagemanage.service.user.UserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

import static com.chen.imagemanage.utils.JwtUtil.USER_NAME;

@RestController
@RequestMapping("/right")
public class RightController extends BaseController{
    @Resource
    private UserService userService;

    @Resource
    private TeamService teamService;

    @Resource
    private TeamMemberService teamMemberService;

    //获得成员对数据集操作的权限
    @GetMapping("/getRightInSet")
    public ApiResult<RightVO> getUserRightSet(
            @RequestParam(value = "setName")String setName,
            @RequestParam(value = "ownerName")String ownerName,
            @RequestHeader(value = USER_NAME) String userName){
        RightVO rightVO=null;
        Boolean isUser=userService.isUser(ownerName);
        Boolean isTeam=teamService.isTeam(ownerName);
        //表明是个人的数据集
        if(isUser){
            //如果团队拥有者就是该用户，赋于全部权限
            if(ownerName.equals(userName)){
                rightVO=RightVO.builder().ownerKind("个人").role("拥有者").ableAdd(1).ableDelete(1)
                        .ableCreateSet(1).ableDeleteSet(1).ableChange(1).build();
            }
            //如果团队拥有者不是是该用户，没有任何权限
            else{
                rightVO=RightVO.builder().ownerKind("个人").role("参观者").ableAdd(0).ableDelete(0)
                        .ableCreateSet(0).ableDeleteSet(0).ableChange(0).build();
            }
        }
        //表明不是个人的数据集
        else{
            //表明是团队
            if(isTeam){
                Boolean isTeamOwner=teamService.isTeamOwner(ownerName,userName);
                //该用户是团队的创建者
                if(isTeamOwner){
                    rightVO=RightVO.builder().ownerKind("团队").role("拥有者").ableAdd(1).ableDelete(1)
                            .ableCreateSet(1).ableDeleteSet(1).ableChange(1).build();
                }
                else{
                    //如果该用户在团队中，返回该用户的权限
                    if(teamMemberService.isInTeam(ownerName,userName)){
                        rightVO=teamMemberService.memberRight(ownerName,userName);
                    }
                    //该用户不在团队中
                    else{
                        rightVO=RightVO.builder().ownerKind("团队").role("参观者").ableAdd(0).ableDelete(0)
                                .ableCreateSet(0).ableDeleteSet(0).ableChange(0).build();
                    }
                }
            }
        }

        return ApiResult.success(rightVO);
    }

    //获得团队内成员在团队内的权限
    @GetMapping("/getRightInTeam")
    public ApiResult<RightVO> getRightInTeam(
            @RequestParam(value = "teamName")String teamName,
            @RequestHeader(value = USER_NAME) String memberName){
        RightVO rightVO=null;

        if(teamService.isTeamOwner(teamName,memberName)){
            rightVO=RightVO.builder().ownerKind("团队").role("拥有者").ableAdd(1).ableDelete(1)
                    .ableCreateSet(1).ableDeleteSet(1).ableChange(1).build();
        }
        else {
            rightVO = teamMemberService.memberRight(teamName, memberName);
        }
        return ApiResult.success(rightVO);
    }

}
