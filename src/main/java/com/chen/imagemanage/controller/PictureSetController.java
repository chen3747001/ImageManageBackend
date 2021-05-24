package com.chen.imagemanage.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chen.imagemanage.common.api.ApiResult;
import com.chen.imagemanage.model.dto.CreatePictureSetDTO;
import com.chen.imagemanage.model.entity.Picture;
import com.chen.imagemanage.model.entity.PictureSet;
import com.chen.imagemanage.model.vo.PictureCardVO;
import com.chen.imagemanage.service.picture.PictureService;
import com.chen.imagemanage.service.pictureSet.PictureSetService;
import com.chen.imagemanage.service.setOperation.SetOperationService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.*;
import java.net.URLEncoder;
import java.util.Base64;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import static com.chen.imagemanage.utils.JwtUtil.USER_NAME;
@Slf4j
@RestController
@RequestMapping("/pictureSet")
public class PictureSetController {
    @Resource
    private PictureSetService pictureSetService;
    @Resource
    private PictureService pictureService;
    @Resource
    private SetOperationService setOperationService;

    @GetMapping("/show")
    public ApiResult<List<PictureSet>> getPictureSet() {
        List<PictureSet> list = pictureSetService.list(new
                LambdaQueryWrapper<PictureSet>().eq(PictureSet::getUseRange, "公共"));
        return ApiResult.success(list);
    }

    //创建新的数据集
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ApiResult<PictureSet> createPictureSet(@Valid @RequestBody CreatePictureSetDTO dto) {
        PictureSet pictureSet = PictureSet.builder()
                .name(dto.getName())
                .owner(dto.getOwnerName())
                .useRange(dto.getUseRange())
                .avatar("add.png")
                .build();

        PictureSet result = pictureSetService.create(pictureSet);

        if (ObjectUtils.isEmpty(result)) {
            return ApiResult.failed("该数据集名称已存在！");
        }

        //保存操作的信息
        setOperationService.createOperation(dto.getName(),dto.getOwnerName(),"新建数据集");

        return ApiResult.success(pictureSet);
    }

    //展示属于我的数据集
    @RequestMapping(value = "/mySet", method = RequestMethod.GET)
    public ApiResult<List<PictureSet>> showMyPictureSet(@RequestHeader(value = USER_NAME) String userName) {
        List<PictureSet> result = pictureSetService.showMySet(userName);
        return ApiResult.success(result);
    }

    //展示属于我的数据集
    @RequestMapping(value = "/mySetTest", method = RequestMethod.GET)
    public ApiResult<Page<PictureCardVO>> showMyPictureSetTest(
            @RequestParam(value = "user") String userName,
            @RequestParam(value = "tab", defaultValue = "latest") String tab,
            @RequestParam(value = "pageNo", defaultValue = "1")  Integer pageNo,
            @RequestParam(value = "size", defaultValue = "10") Integer pageSize,
            @RequestParam(value = "scenario") String  scenario,
            @RequestParam(value = "dataKind") String  dataKind,
            @RequestParam(value = "searchName") String searchName) {
        System.out.println("开始查询"+scenario+"=="+dataKind+"=="+searchName);
        Page<PictureCardVO> result = pictureSetService.getMyList(new Page<>(pageNo, pageSize), tab,userName,scenario,dataKind,searchName);
        return ApiResult.success(result);
    }

    //展示公共的数据集
    @RequestMapping(value = "/showPublicSet", method = RequestMethod.GET)
    public ApiResult<Page<PictureCardVO>> showPublicPictureSetTest(
            @RequestParam(value = "tab", defaultValue = "latest") String tab,
            @RequestParam(value = "pageNo", defaultValue = "1")  Integer pageNo,
            @RequestParam(value = "size", defaultValue = "10") Integer pageSize,
            @RequestParam(value = "scenario") String  scenario,
            @RequestParam(value = "dataKind") String  dataKind,
            @RequestParam(value = "searchName") String searchName) {
        System.out.println("开始查询"+scenario+"=="+dataKind+"=="+searchName);
        Page<PictureCardVO> result = pictureSetService.getPublicList(new Page<>(pageNo, pageSize), tab,scenario,dataKind,searchName);
        return ApiResult.success(result);
    }

    //获得对应名称的数据集的信息
    @GetMapping("/getSetInformationByName")
    public ApiResult<PictureSet> getSetInformationByName(@RequestParam(value = "setName")String setName){
        System.out.println("查数据集信息 "+setName);
        PictureSet result=pictureSetService.getMessageByName(setName);
        System.out.println(result.getName()+" == "+result.getOwner()+" = "+result.getCreateTime());
        return ApiResult.success(result);
    }

    //下载对应数据集内容
    @PostMapping("/downloadSet")
    public void downloadSet(@RequestParam(value = "setName") String setName, HttpServletRequest request, HttpServletResponse response){
        System.out.println("download "+setName);

        //响应头的设置
        response.setCharacterEncoding("utf-8");
        response.setContentType("multipart/form-data");

        /**
         * 设置压缩包的名字
         * 解决不同浏览器压缩包名字含有中文时乱码的问题
         */
        String downloadName =setName+".zip";
        //用来获得 请求头中浏览器的信息
        String agent = request.getHeader("USER-AGENT");
        try {
            if (agent.contains("MSIE")||agent.contains("Trident")) {
                downloadName = URLEncoder.encode(downloadName, "UTF-8");
            } else {
                downloadName = new String(downloadName.getBytes("UTF-8"),"ISO-8859-1");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        /**
         * 获得下载的数据集的所有文件列表
         */
        List<Picture> pictureList=pictureService.getPicInformation(setName);
        int listLength=pictureList.size();
        int i;
        String[] pictureUrlList=new String[listLength];
        String[] pictureNameList=new String[listLength];
        for(i=0;i<listLength;i++){
            pictureUrlList[i]=pictureList.get(i).getUid();
            pictureNameList[i]=pictureList.get(i).getName();
        }
        System.out.println("下载的文件url是："+pictureUrlList[0]);

        //用来绑定下载的文件
        response.setHeader("Content-Disposition", "attachment;fileName=\"" + downloadName + "\"");

        /*
        * 设置压缩流：直接写入response，实现边压缩边下载
        */
        ZipOutputStream zipos = null;
        try {
            zipos = new ZipOutputStream(new BufferedOutputStream(response.getOutputStream()));
            zipos.setMethod(ZipOutputStream.DEFLATED); //设置压缩方法
        } catch (Exception e) {
            e.printStackTrace();
        }

        /*
         * 循环将文件写入压缩流
         */
        DataOutputStream os = null;
        InputStream is = null;
        String path="F:\\ImageManage\\data\\pictureData\\";
        for(i = 0; i < listLength; i++ ) {
            System.out.println("输出的文件名是：");
            try{
                //设置新的文件名，避免由于重名导致下载错误
                String fileName=pictureNameList[i].split("\\.")[0]+"-"+i;
                String fileKind=pictureNameList[i].split("\\.")[1];
                String fileOutPutName=fileName+"."+fileKind;
                System.out.println(fileOutPutName);
                //添加ZipEntry，并ZipEntry中写入文件流
                zipos.putNextEntry(new ZipEntry(fileOutPutName));

                os=new DataOutputStream(zipos);
                //获取本地文件
                String filePath=path+pictureUrlList[i];
                File file = new File(filePath);
                is = new FileInputStream(file);

                //输入流转换为输出流
//                IOUtils.copy(is, os);
                IOUtils.copy(is, os);
                zipos.closeEntry();
            }catch (IOException e){
                e.printStackTrace();
            }
        }

        //关闭流
        try {
            is.close();
            os.flush();
            os.close();
            zipos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    //修改数据集信息
    @PostMapping("/updateSetInformation")
    public ApiResult<Object> updateUserInformation(
            @RequestParam(value = "setName")String setName,
            @RequestParam(value = "bio")String bio,
            @RequestParam(value = "scenario")String scenario,
            @RequestParam(value = "dataKind")String dataKind,
            @RequestHeader(value = USER_NAME) String userName){
        boolean updateOk=pictureSetService.updateSetInformation(setName,bio,scenario,dataKind);
        if(updateOk){
            //保存操作的信息
            setOperationService.createOperation(setName,userName,"修改数据集介绍");

            return ApiResult.success(null, "修改用户信息成功");
        }
        else{
            return ApiResult.failed("修改用户信息失败");
        }
    }

    //新增一个访问人数
    @PostMapping("/addBrowse")
    public ApiResult<Object> addBrowse(@RequestParam(value = "setName") String setName){
        pictureSetService.addBrowse(setName);
        return ApiResult.success("添加浏览量成功");
    }

    //删除数据集
    @PostMapping("/deleteSet")
    public ApiResult<Object> deleteSet(@RequestParam(value = "setName")String setName){
        System.out.println("将要删除数据集");
        PictureSet pictureSet=pictureSetService.getMessageByName(setName);
        int i=0;
        /*
            获得头像的名称
        */
        String avatar=pictureSet.getAvatar();
        String avatarPath="F:\\ImageManage\\data\\avatar\\";
        File avatarFile=new File(avatarPath,avatar);
        System.out.println("删除数据集"+avatar);
        //如果头像不是系统初始化的头像，就删除
        if(!avatar.equals("add.png")){
            try {
                avatarFile.delete();
            } catch (Exception e) {
                log.warn("删除文件失败");
            }
        }

        /*
            获得该数据集的图片列表
        */
        List<Picture> pictureList=pictureService.getPicInformation(setName);
        String picPath="F:\\ImageManage\\data\\pictureData\\";
        //删除对应的图片文件
        for(i=0;i<pictureList.size();i++){
            File file=new File(picPath,pictureList.get(i).getUid());
            file.delete();
        }

        /*
            删除数据库中的记录
            1) setOperation
            2) pictureSet
            3)picture
        */
        boolean deleteOperation=setOperationService.deleteOperations(setName);
        boolean deleteSet=pictureSetService.deleteSet(setName);
        boolean deletePictures=pictureService.deletePicturesBySetName(setName);

        if(deleteOperation && deleteSet && deletePictures){
            return ApiResult.success("删除数据集成功");
        }
        else{
            return ApiResult.failed("删除失败");
        }

    }
}