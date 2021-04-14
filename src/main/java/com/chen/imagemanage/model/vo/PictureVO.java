package com.chen.imagemanage.model.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Builder
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class PictureVO {
    /**
     * 图片名称
     */
    private String picture_name;

    /**
     * 图片详细数据
     */
    private String picture_detail;

    /**
     * 图片类型
     */
    private String picture_kind;


}
