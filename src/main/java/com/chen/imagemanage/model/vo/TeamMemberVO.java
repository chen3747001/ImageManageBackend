package com.chen.imagemanage.model.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class TeamMemberVO {
    private String id;

    /**
     * 团队名称
     */
    private String teamName;

    /**
     * 成员名称
     */
    private String memberName;

    /**
     * 删除图片的权力
     */
    private Integer ableDelete;

    /**
     * 添加图片的权力
     */
    private Integer ableAdd;

    /**
     * 新建数据集的权力
     */
    private Integer ableCreateSet;

    /**
     * 删除数据集的权力
     */
    private Integer ableDeleteSet;

    /**
     * 成员的简介
     */
    private String bio;

    /**
     * 成员的头像
     */
    private String  avatar;
}
