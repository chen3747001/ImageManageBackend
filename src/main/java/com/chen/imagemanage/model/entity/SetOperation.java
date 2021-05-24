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
@TableName("setOperation")
@NoArgsConstructor
@AllArgsConstructor
public class SetOperation implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 主键,type = IdType.AUTO id自增长
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String  id;

    /**
     * 操作的日期
     */
    @TableField("date")
    private Date date;

    /**
     * 数据集名称
     */
    @TableField("setName")
    private String setName;

    /**
     * 操作者名称
     */
    @TableField("handler")
    private String handler;

    /**
     * 具体操作
     */
    @TableField("operation")
    private String operation;
}
