package com.chen.imagemanage.model.vo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.util.Date;

@Data
public class PictureCardVO {
    /**
     * 名称
     */
    private String name;

    /**
     * 创造者姓名
     */
    private String owner;

    /**
     * 创建时间 FieldFill.INSERT 自动填充时间
     */
    private Date createTime;

    /**
     * 修改公告时间
     */
    private Date amendTime;

    /**
     * 图片数量
     */
    private Integer amountPicture;

    /**
     * 数据总大小
     * b为单位
     */
    private Double size;

    /**
     * 数据集的封面
     */
    private String avatar;

    /**
     * 范围 公共 私有
     */
    private String useRange;

    /**
     * 浏览量
     */
    private Integer browse;

    /*
     * 简介
     * */
    private String bio;

    /*
     * 应用场景
     * */
    private String scenario;

    /*
     * 数据的类型
     * */
    private String dataKind;

    /*
     * 创造者的头像
     * */
    private String ownerAvatar;
}
