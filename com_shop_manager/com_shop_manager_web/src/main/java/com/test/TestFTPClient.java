package com.test;

import com.tt.util.FtpUtil;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class TestFTPClient {

    @Test
    public void testFTPCilent() throws IOException {
        //创建客户端
        FTPClient ftpClient = new FTPClient();
//连接的服务器地址和端口
        ftpClient.connect("192.168.1.151", 21);
//用户登录
        ftpClient.login("sjg", "admin");

        FileInputStream inputStream = new FileInputStream(new File("D:\\ddd\\sp2p\\public\\images\\app_start.png"));
//因为ftp默认上传的是文本类型,所以应该改为二进制格式
        ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
        //制定上传路径
        ftpClient.changeWorkingDirectory("/usr/local/nginx/html/images");
        //上传，需要一个文件流
        ftpClient.storeFile("test1.png", inputStream);

        inputStream.close();
        ftpClient.logout();

    }


    @Test
    public void testFtpUtil() throws Exception {

        FileInputStream inputStream = new FileInputStream(new File("D:\\ddd\\sp2p\\public\\images\\app_start.png"));
        FtpUtil.uploadFile("192.168.1.151",21,"sjg","admin","/usr/local/nginx/html/images","/2017/01/14","ss.png",inputStream);

    }
}
