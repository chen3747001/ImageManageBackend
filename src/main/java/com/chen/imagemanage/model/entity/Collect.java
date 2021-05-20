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
import java.util.Date;

@Data
@Builder
@Accessors(chain = true)
@TableName("collect")
@NoArgsConstructor
@AllArgsConstructor
public class Collect implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 主键,type = IdType.AUTO id自增长
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String  id;

    /**
     * 用户名
     */
    @TableField("username")
    private String username;

    /**
     * 数据集名称
     */
    @TableField("setname")
    private String setname;

    /**
     * 数据集名称
     */
    @TableField("date")
    private Date date;
}
