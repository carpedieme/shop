package com.test;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tt.mapper.TbItemMapper;
import com.tt.pojo.TbItem;
import com.tt.pojo.TbItemExample;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.testng.annotations.Test;

import java.util.List;

/**
 * 测试分页
 */
public class TestPageHelper {
    @Test
    public  void  testPageHelper(){
        //创建一个spring容器
        ApplicationContext applicationContext=new ClassPathXmlApplicationContext("classpath:spring/applicationContext-*.xml");
        //从spring中获得mapper的代理对象
        TbItemMapper tbItemMapper=applicationContext.getBean(TbItemMapper.class);
        //执行查询并进行分页
        TbItemExample tbItemExample=new TbItemExample();
        //分页处理
        PageHelper.startPage(1,10);
        List<TbItem> list=tbItemMapper.selectByExample(tbItemExample);
        //获取商品列表
        for (TbItem t :
                list) {
            System.out.println(t.getTitle());
        }
        //获取分页信息
        PageInfo<TbItem> p=new PageInfo<TbItem>(list);
        long total=p.getTotal();
        System.out.println("共有商品+++"+total);
    }
}
