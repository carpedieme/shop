package com.tt.test;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.testng.annotations.Test;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;

import java.util.HashSet;
import java.util.Set;

public class Testjedis {

    @Test
    public void testjedis() {

        //创建jedis对象
        Jedis jedis = new Jedis("192.168.78.130", 6379);
        //调用jedis的对象方法
        jedis.set("testtt", "1234567");
        System.out.println(jedis.get("testtt"));
        //关闭jedis
        jedis.close();
    }

    /**
     * 浪费性能
     * 使用连接池
     */
    @Test
    public void testjedisByConnectionPopl() {
        //创建连接池
        JedisPool jp = new JedisPool("192.168.78.130", 6379);
        //获取对象
        Jedis jedis = jp.getResource();
        jedis.set("testJedisPool", "pool");
        System.out.println(jedis.get("testJedisPool"));
        jedis.close();
        jp.close();
    }

    /**
     * 连接redis集群版
     */
    @Test
    public void testjedisClustor() {
        HashSet<HostAndPort> nodes = new HashSet<>();
        nodes.add(new HostAndPort("192.168.78.130", 7001));
        nodes.add(new HostAndPort("192.168.78.130", 7002));
        nodes.add(new HostAndPort("192.168.78.130", 7003));
        nodes.add(new HostAndPort("192.168.78.130", 7004));
        nodes.add(new HostAndPort("192.168.78.130", 7005));
        nodes.add(new HostAndPort("192.168.78.130", 7006));
        //JedisCluster不需要连接池  他是自带的连接池
        JedisCluster jc = new JedisCluster(nodes);
        jc.set("testJedisCluster", "weeeeeeee");
        System.out.println(jc.get("testJedisCluster"));
        jc.close();
    }

    /**
     * 测试Springredis单机版
     */
    @Test
    public void testjedisbySpring() {
        ApplicationContext act = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-*.xml");
        JedisPool pool = (JedisPool) act.getBean("resdisClient");
        Jedis is = pool.getResource();
        System.out.println(is.get("testJedisPool"));
        is.close();
        pool.close();
    }

    /**
     * 测试springredis集群版
     */
    @Test
    public void testJedisClusterBySpring() {
        ApplicationContext act = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-*.xml");

        JedisCluster jc = (JedisCluster) act.getBean("resdisClient");
        System.out.println(jc.get("b"));
        jc.close();
    }

}
