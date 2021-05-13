package com.chen.imagemanage.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Builder
@Accessors(chain = true)
@TableName("teammember")
@NoArgsConstructor
@AllArgsConstructor
public class TeamMember implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键,type = IdType.AUTO id自增长
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    /**
     * 团队名称
     */
    @TableField("teamName")
    private String teamName;

    /**
     * 成员名称
     */
    @TableField("memberName")
    private String memberName;

    /**
     * 删除图片的权力
     */
    @TableField("ableDelete")
    private Integer ableDelete;

    /**
     * 添加图片的权力
     */
    @TableField("ableAdd")
    private Integer ableAdd;

    /**
     * 新建数据集的权力
     */
    @TableField("ableCreateSet")
    private Integer ableCreateSet;

    /**
     * 删除数据集的权力
     */
    @TableField("ableDeleteSet")
    private Integer ableDeleteSet;
}
