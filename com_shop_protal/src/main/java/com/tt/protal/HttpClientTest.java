package com.tt.protal;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;


public class HttpClientTest {
    /**
     * 不带参数请求
     * @throws Exception
     */
    @Test
    public void doGet() throws Exception {
        //创建一个client对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        //创建一个get对象
        HttpGet get = new HttpGet("http://www.baidu.com");
        //执行请求
        CloseableHttpResponse response = httpClient.execute(get);
        //获取响应结果 200表示正常
        int code = response.getStatusLine().getStatusCode();
        System.out.println(code);
        HttpEntity entity = response.getEntity();
        System.out.println(EntityUtils.toString(entity,"utf-8"));
        //关闭
        response.close();
        httpClient.close();
    }
    /**
     * 带参数请求
     * @throws Exception
     */

    @Test
    public void doGetWithParams() throws Exception {
        //创建一个client对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        //创建一个uri对象
        URIBuilder uriBuilder=new URIBuilder("http://www.sogou.com/web");

        uriBuilder.addParameter("query","科比");
        HttpGet get = new HttpGet(uriBuilder.build());
        //执行请求
        CloseableHttpResponse response = httpClient.execute(get);
        //获取响应结果
        int code = response.getStatusLine().getStatusCode();
        System.out.println(code);
        HttpEntity entity = response.getEntity();
        System.out.println(EntityUtils.toString(entity,"utf-8"));
        //关闭
        response.close();
        httpClient.close();
    }
    /**
     * 不带doPost参数请求
     * @throws Exception
     */
    @Test
    public void doPost() throws Exception {
        //创建一个client对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        //创建一个post对象
        HttpPost post = new HttpPost("http://localhost:8082/httpclient/post.html");
        //执行请求
        CloseableHttpResponse response = httpClient.execute(post);
        //获取响应结果
        int code = response.getStatusLine().getStatusCode();
        System.out.println(code);
        HttpEntity entity = response.getEntity();
        System.out.println(EntityUtils.toString(entity,"utf-8"));
        //关闭
        response.close();
        httpClient.close();
    }

    /**
     * 带doPost参数请求
     * @throws Exception
     */
    @Test
    public void doPostWithParams() throws Exception {
        //创建一个client对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        //创建一个post对象
        HttpPost post = new HttpPost("http://localhost:8082/httpclient/post.html");
        //创建一个entity 模拟一个表单
        List<NameValuePair> kvlist=new ArrayList<>();
        kvlist.add(new BasicNameValuePair("username","usertest"));
        kvlist.add(new BasicNameValuePair("password","123456"));
        //包装为一个entity对象
        StringEntity string =new UrlEncodedFormEntity(kvlist);
        //设置请求的内容
        post.setEntity(string);
        //执行请求
        CloseableHttpResponse response = httpClient.execute(post);
        //获取响应结果
        int code = response.getStatusLine().getStatusCode();
        System.out.println(code);
        HttpEntity entity = response.getEntity();
        System.out.println(EntityUtils.toString(entity,"utf-8"));
        //关闭
        response.close();
        httpClient.close();
    }
}
