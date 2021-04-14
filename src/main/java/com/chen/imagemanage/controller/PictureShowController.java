package com.chen.imagemanage.controller;

import com.chen.imagemanage.common.api.ApiResult;
import com.chen.imagemanage.model.vo.PictureVO;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Base64;

@RestController
@RequestMapping("/picture")
public class PictureShowController {
    //测试用放回图片类
    @RequestMapping(value = "/getPicture/{path}", method = RequestMethod.GET)
    private ApiResult<PictureVO> getPic(@PathVariable String path) throws IOException{
        //图片的名称
        String location = path;
        //没有数据时使用的图片
        String location_temp="wolf.png";
        //如果该图片路径存在，就使用该路径，否则使用默认路径
        if(path==null || path.length()==0 || path.equals("none")){
            location=location_temp;
        }

        //获得文件的格式，方便前端根据格式显示
        String[] file_data=location.split("\\.");
        String picture_kind=file_data[1];
        byte[] byte_picture=null;

        File f = new File("F:\\ImageManage\\data\\avatar\\" + location);
        FileInputStream picInput = new FileInputStream(f);

        //检查图片数据是否存在，如果不存在就使用"wolf.png"图片
        if(picInput.available()==0){
            f=new File("F:\\ImageManage\\data\\avatar\\wolf.png");
            picInput=new FileInputStream(f);
            location=location_temp;
            picture_kind="png";
        }

        byte[] b = new byte[picInput.available()];
        picInput.read(b);

        /**
         * 重要：
         *  由于json无法传送byte[] 数据，所以使用base64 将byte[]转化为String 进行传输
         */
        String FileBuf = Base64.getEncoder().encodeToString(b);

        //写入传输的封装类
        PictureVO pictureVO=PictureVO.builder()
                .picture_name(location)
                .picture_detail(FileBuf)
                .picture_kind(picture_kind)
                .build();

        System.out.println("=   =   图片 "+location+"读取成功");
        return ApiResult.success(pictureVO);
    }
}
