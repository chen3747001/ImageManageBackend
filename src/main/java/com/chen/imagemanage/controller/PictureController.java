package com.chen.imagemanage.controller;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chen.imagemanage.common.api.ApiResult;
import com.chen.imagemanage.mapper.PictureSetMapper;
import com.chen.imagemanage.model.dto.DeletePictureDTO;
import com.chen.imagemanage.model.dto.RegisterDTO;
import com.chen.imagemanage.model.dto.TempTestDTO;
import com.chen.imagemanage.model.dto.TestDTO;
import com.chen.imagemanage.model.entity.Picture;
import com.chen.imagemanage.model.entity.PictureSet;
import com.chen.imagemanage.model.vo.PictureVO;
import com.chen.imagemanage.service.picture.PictureService;
import com.chen.imagemanage.service.pictureSet.PictureSetService;
import com.chen.imagemanage.service.setOperation.SetOperationService;
import com.chen.imagemanage.service.team.TeamService;
import com.chen.imagemanage.service.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.w3c.dom.events.EventException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.chen.imagemanage.utils.JwtUtil.USER_NAME;

@Slf4j
@RestController
@RequestMapping("/picture")
public class PictureController {
    @Resource
    private PictureSetService pictureSetService;
    @Resource
    private PictureService pictureService;
    @Resource
    private UserService userService;
    @Resource
    private TeamService teamService;
    @Resource
    private SetOperationService setOperationService;

    //获得对应数据集的所有图片的信息
    @RequestMapping(value = "/getPictureInformation/{name}", method = RequestMethod.GET)
    private ApiResult<List<Picture>> getPictureInformation(@PathVariable String name){
//        System.out.println("qing qiu");
        return ApiResult.success(pictureService.getPicInformation(name));
    }

    //删除选中的图片
    @RequestMapping(value="/deletePictureList",method = RequestMethod.POST)
    public ApiResult<Object> importData(
            @Valid @RequestBody DeletePictureDTO deletePictureDTO,
            @RequestHeader(value = USER_NAME) String userName){
//        pictureService.deletePictureList(fileList);
        //分类获得数据
        String setName=deletePictureDTO.getSetName();
        String[] fileList=deletePictureDTO.getPictureList();
        Integer[] size=deletePictureDTO.getSize();
        Integer total_size=0;

        //获得对应数据的数据并进行修改
        PictureSet setData=pictureSetService.getMessageByName(setName);
        Integer amount_picture=setData.getAmountPicture()-fileList.length;
        Double set_size=setData.getSize();
        Date time=new Date();

        System.out.println(time+" 开始删除数据"+setName);
        //删除操作
        String path="F:\\ImageManage\\data\\pictureData\\";
        int i=0;
        for(i=0;i<fileList.length;i++){
            System.out.println("== "+fileList[i]+" == "+size[i]);
            total_size+=size[i];
            File file=new File(path,fileList[i]);
            if(file.isFile() && file.exists()){
                try{
                    file.delete();
                }
                catch(Exception e){
                    log.warn("删除文件失败");
                }
            }
            else{
                return ApiResult.failed("删除文件失败 文件："+fileList[i]+" 不存在");
            }
        }
        //最后数据集中实际的数据大小
        Double final_size=set_size-total_size;
        //在数据库中写入修改后数据集的信息
        boolean updateOk=pictureSetService.uploadPicture(setName,time,amount_picture,final_size);
        //在数据库中修改删除后图片的信息
        boolean deleteOk=pictureService.deletePictureList(fileList);
        System.out.println("删除的所有内容大小是"+total_size);

        //保存操作的信息
        setOperationService.createOperation(setName,userName,"删除了 "+fileList.length+" 个文件");

        return ApiResult.success(null, "删除成功");
    }


        //显示头像
    @RequestMapping(value = "/getAvatar/{path}", method = RequestMethod.GET)
    private ApiResult<PictureVO> getAvatar(@PathVariable String path) throws IOException{
        //图片的名称
        String location = path;
        //没有数据时使用的图片
        String location_temp="add.png";
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

        picInput.close();
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

    //显示图片
    @RequestMapping(value = "/getPicture/{path}", method = RequestMethod.GET)
    private ApiResult<PictureVO> getPicture(@PathVariable String path) throws IOException{
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

        File f = new File("F:\\ImageManage\\data\\pictureData\\" + location);
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

        picInput.close();
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
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //上传图片
    @PostMapping("/upload")
    public ApiResult<Object> importData(
            @RequestPart("files") MultipartFile[] files,
            @RequestPart("setName") String setName,
            @RequestHeader(value = USER_NAME) String userName) throws IOException {
        System.out.println("开始传输数据");
        //判断文件是否为空
        if(files==null || files.length==0){
            return ApiResult.failed("传输文件为空");
        }

        //获得日期格式化数据
        SimpleDateFormat sdf = new SimpleDateFormat("-yyyy-MM-dd-hh-mm-ss");
        Date time=new Date();
        String format = sdf.format(time);
        System.out.println("开始上传，日期："+format);

        //获得文件输出路径
        String path="F:\\ImageManage\\data\\pictureData\\";
        System.out.println("setName is"+setName);
        PictureSet setData=pictureSetService.getMessageByName(setName);

        int count=0;
        String oldName="";
        String newName="";
        //数据集中数据大小
        Double size=setData.getSize();

        //数据集中数据数量(这次执行后)
        System.out.println("文件数量"+files.length);
        Integer amount_picture=setData.getAmountPicture()+files.length;
        Path realpath=null;


        //保存文件
        for(MultipartFile file:files) {

            oldName=file.getOriginalFilename();
            newName=setName+format+"-"+(count++)+ oldName.substring(oldName.lastIndexOf("."));
            realpath=Paths.get(path+newName);
            size+=file.getSize();

            Picture picData=Picture.builder()
                    .uid(newName)
                    .name(oldName)
                    .size((int) file.getSize())
                    .source(realpath.toString())
                    .setName(setName)
                    .build();
            System.out.println("每个文件详细数据 "+picData.getUid()+"="+picData.getName()+"="+picData.getSize()+"="+picData.getSource()+"="+picData.getSetName());
            //在数据库中写入添加的图片的信息
            pictureService.insertData(picData);
            //保存文件
            file.transferTo(new File(path,newName));
        }

        System.out.println("amount_picture is:"+amount_picture);
        //在数据库中写入修改后的信息
        boolean updateOk=pictureSetService.uploadPicture(setName,time,amount_picture,size);

        //保存操作的信息
        setOperationService.createOperation(setName,userName,"上传了 "+files.length+" 个文件");

        return ApiResult.success(null, "上传成功");
    }

    //修改头像
    @PostMapping("/updateAvatar")
    public ApiResult<Object> updateAvatar(
            @RequestPart("files") MultipartFile files,
            @RequestPart("name") String name,
            @RequestPart("kind") String kind,
            @RequestPart("oldAvatar") String oldAvatar,
            @RequestHeader(value = USER_NAME) String userName) throws IOException {
        System.out.println("修改头像开始，上传图片"+name+kind+"====="+files.getName()+files.getSize()+files.getOriginalFilename()+" oldAvatar is "+oldAvatar);

        //判断文件是否为空
        if(files.getSize()==0){
            return ApiResult.failed("传输文件为空");
        }
        //获得日期格式化数据
        SimpleDateFormat sdf = new SimpleDateFormat("-yyyy-MM-dd-hh-mm-ss");
        Date time=new Date();
        String format = sdf.format(time);

        String path="F:\\ImageManage\\data\\avatar\\";
        String oldName=files.getOriginalFilename();
        String newName=name+format+oldName.substring(oldName.lastIndexOf("."));
        /*
        *下载文件
        * */
        files.transferTo(new File(path,newName));

       /*
       * 修改数据库中的信息
       */
        if(kind.equals("user")){
            userService.setAvatar(name,newName);
        }
        else if(kind.equals("team")){
            teamService.updateAvatar(name,newName);
        }
        else if(kind.equals("set")){
            pictureSetService.updateAvatar(name,newName);
            //保存操作的信息
            setOperationService.createOperation(name,userName,"修改了头像");
        }


        /*
        * 如果图片不是add.png
        * 那就删除原头像
        * */
        File deleteFile=new File(path,oldAvatar);
        if(!oldAvatar.equals("add.png")){
            if(deleteFile.isFile() && deleteFile.exists()) {
                try {
                    deleteFile.delete();
                } catch (Exception e) {
                    log.warn("删除文件失败");
                }
            }
            else{
                return ApiResult.failed("删除原头像失败 文件："+oldAvatar+" 不存在");
            }
        }

        return ApiResult.success(null, "修改头像成功");
    }
}
