package com.chen.imagemanage.model.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class CreatePictureSetDTO {
    @NotBlank(message = "拥有者名称不能为空")
    private String ownerName;

    @NotBlank(message = "数据集名称不能为空")
    private String name;

    @NotBlank(message = "存储方式不能为空")
    private String action;

    @NotBlank(message = "可见范围不能为空")
    private String useRange;

}
