package com.tt.service.tt.serviceImpl;

import com.tt.service.UploadImageService;
import com.tt.util.FtpUtil;
import com.tt.util.IDUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@Service
public class UploadImageServiceImpl implements UploadImageService {
    //这样spring会将配置文件中的值拿到，并且赋值给ftp_address
    @Value("${ftp_address}")
    private String ftp_address;

    @Value("${ftp_port}")
    private int ftp_port;

    @Value("${ftp_username}")
    private String ftp_username;

    @Value("${ftp_pwd}")
    private String ftp_pwd;

    @Value("${ftp_basepath}")
    private String ftp_basepath;

    @Value("${imageBaseUrl}")
    private String imageBaseUrl;


    @Override
    public Map uploadImages(MultipartFile multipartFile) {
        Map map = new HashMap();
        try {
            //获取图片的名称 生成新的文件名  需要一个工具类
            String oldName = multipartFile.getOriginalFilename();
            String newName = IDUtils.genImageName() + oldName.substring(oldName.lastIndexOf("."));
            //存放图片的路径
            String filepath = new DateTime().toString("yyyy/MM/dd");
            boolean result = FtpUtil.uploadFile(ftp_address, ftp_port, ftp_username, ftp_pwd, ftp_basepath, filepath, newName, multipartFile.getInputStream());
            if (!result) {
                map.put("error", 1);
                map.put("message", "文件上传失败");
                return map;
            }
            map.put("error",0);
            map.put("url",imageBaseUrl+filepath+"/"+newName);
            return map;
        } catch (Exception e) {
            map.put("error", 1);
            map.put("message", "文件上传发生异常");
            return map;
        }
    }
}
