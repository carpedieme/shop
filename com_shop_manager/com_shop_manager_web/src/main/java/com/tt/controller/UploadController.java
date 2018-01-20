package com.tt.controller;

import com.tt.service.UploadImageService;
import com.tt.util.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@Controller
public class UploadController {

    @Autowired
    private UploadImageService uploadImageService;

    /**
     * 上传图片
     * @param uploadFile
     * @return
     */
    @RequestMapping("/pic/upload")
    @ResponseBody
    public String uploadImage(MultipartFile uploadFile){
        //为了保证兼容性需要把返回数据转换成json格式的字符串
        return JsonUtils.objectToJson(uploadImageService.uploadImages(uploadFile));
    }

}
