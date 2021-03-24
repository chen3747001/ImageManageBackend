package com.chen.imagemanage.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;
/**
 * @Data自动生成 set get方法
 * @NoArgsConstructor 生成无参构造方法
 * @AllArgsConstructor生成全部参数构造方法
 * @Builder @Accessors(chain = true) 支持链式操作
 */
@Data
@Builder
@Accessors(chain = true)
@TableName("billboard")
@NoArgsConstructor
@AllArgsConstructor
public class Billboard implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键,type = IdType.AUTO id自增长
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 公告牌
     */
    @TableField("content")
    private String content;

    /**
     * 公告时间 FieldFill.INSERT 自动填充时间
     */
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private Date createTime;

    /**
     * 1：展示中，0：过期
     */
    @Builder.Default
    @TableField("shownot")
    private boolean shownot = false;

}