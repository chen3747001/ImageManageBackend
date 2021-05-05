package com.chen.imagemanage.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@Builder
@Accessors(chain = true)
@TableName("picturedata")
@NoArgsConstructor
@AllArgsConstructor
public class Picture {
    private static final long serialVersionUID = 1L;

    /**
     * 主键 图片的储存名称
     */
    @TableId(value = "uid")
    private String uid;

    /**
     * 用户输入的名称
     */
    @TableField("name")
    private String name;

    /**
     * 图片存储的地址
     */
    @TableField("source")
    private String source;

    /**
     * 图片大小
     * b为单位
     */
    @TableField(value = "size")
    private Integer size;

    /**
     * 存储的数据集名称
     */
    @TableField(value = "setName")
    private String setName;

}
