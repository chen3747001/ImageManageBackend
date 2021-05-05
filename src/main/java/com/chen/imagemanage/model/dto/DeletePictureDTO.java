package com.chen.imagemanage.model.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class DeletePictureDTO {
    //数据集名称
    private String setName;

    //文件保存名称数组
    private String[] pictureList;

    //文件大小数组
    private Integer[] size;
}
