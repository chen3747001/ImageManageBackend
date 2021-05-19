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
@TableName("action")
@NoArgsConstructor
@AllArgsConstructor
public class Action implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键,type = IdType.AUTO id自增长
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String  id;

    /**
     * 请求发送者
     */
    @TableField("sender")
    private String sender;

    /**
     * 请求接收者
     */
    @TableField("receiver")
    private String receiver;

    /**
     * 请求的动作（邀请加入等）
     */
    @TableField("action")
    private String action;

    /**
     * 请求的对象（哪个团队等）
     */
    @TableField("object")
    private String object;

    /**
     * 请求的显示内容
     */
    @TableField("message")
    private String message;

    /**
     * 请求发出时的日期
     */
    @TableField("date")
    private Date date;

    /**
     * 请求的状态 solved,notSolved
     */
    @TableField("state")
    private String state;

    /**
     * 请求的结果 同意 不同意
     */
    @TableField("result")
    private String  result;
}
