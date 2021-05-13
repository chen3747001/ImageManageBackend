package com.chen.imagemanage.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Builder
@Accessors(chain = true)
@TableName("team")
@NoArgsConstructor
@AllArgsConstructor
public class Team implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 名称
     */
    @TableField("name")
    private String name;

    /**
     * 创造者姓名
     */
    @TableField("owner")
    private String owner;

    /**
     * 简介
     */
    @TableField("bio")
    private String bio;
    /**
     * 联系邮箱
     */
    @TableField("email")
    private String email;
    /**
     * 团队头像
     */
    @TableField("avatar")
    private String avatar;
    /**
     * 成员人数
     */
    @TableField("peopleCount")
    private String peopleCount;
    /**
     * 数据集数量
     */
    @TableField("setCount")
    private String setCount;
}
