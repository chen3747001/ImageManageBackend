package com.chen.imagemanage.model.entity;

import com.baomidou.mybatisplus.annotation.*;
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
@TableName("pictureset")
@NoArgsConstructor
@AllArgsConstructor
public class PictureSet implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键,type = IdType.AUTO id自增长
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

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
     * 创建时间 FieldFill.INSERT 自动填充时间
     */
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private Date createTime;

    /**
     * 修改公告时间
     */
    @TableField(value = "amend_time")
    private Date amendTime;

    /**
     * 图片数量
     */
    @TableField(value = "amount_picture")
    private Integer amountPicture;

    /**
     * 数据总大小
     * b为单位
     */
    @TableField(value = "size")
    private Double size;

    /**
     * 数据集的封面
     */
    @TableField(value = "avatar")
    private String avatar;

    /**
     * 范围 公共 私有
     */
    @TableField(value = "use_range")
    private String useRange;

    /**
     * 浏览量
     */
    @TableField(value = "browse")
    private Integer browse;

}