package com.Alice.testjedis;

import com.Alice.content.jedis.JedisClient;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Alice
 * @date 2018/8/12/012
 */
public class JedisTest {

    //测试单机版
    @Test
    public void testJedis(){
        //1.创建Jdesi对象，需要指定连接的端口和对象
        Jedis jedis = new Jedis("192.168.25.129",6379);
        //2.直接操作Jedis
        jedis.set("key1234","hello,redis");
        System.out.println(jedis.get("key1234"));
        //3.关闭Jedis
        jedis.close();
    }

    @Test
    public void testJdeisPool(){
        //1.创建jdeispool对象  指定端口和地址
        JedisPool pool = new JedisPool("192.168.25.129",6379);
        //2.获取jedis对象
        Jedis jedis = pool.getResource();
        //3.操作jedis
        jedis.set("keypool","valuepool");
        //4.关闭redis
        System.out.println(jedis.get("keypool"));
        jedis.close();
        //5.关闭连接池(一般在应用系统关闭时才关闭)
        pool.close();
    }


    //测试集群版
    @Test
    public void testJedisCluster(){
        Set<HostAndPort> nodes = new HashSet<>();
        nodes.add(new HostAndPort("192.168.25.129",7001));
        nodes.add(new HostAndPort("192.168.25.129",7002));
        nodes.add(new HostAndPort("192.168.25.129",7003));
        nodes.add(new HostAndPort("192.168.25.129",7004));
        nodes.add(new HostAndPort("192.168.25.129",7005));
        nodes.add(new HostAndPort("192.168.25.129",7006));
        //1.创建jediscluster对象
        JedisCluster cluster = new JedisCluster(nodes);
        //2.直接根据jediscluster操作redis集群
        cluster.set("clusterkey","clustervalue");
        //3.关闭jediscluster对象 封装了连接池
        System.out.println(cluster.get("clusterkey"));
        cluster.close();
    }
    

    @Test
    public void testdanji(){
        //1.初始化sping容器
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-service.xml");
        //2.获取实现类
        JedisClient client = context.getBean(JedisClient.class);
        //3.调用方法
        client.set("jedisclient","jedisclient");
        System.out.println(client.get("jedisclient"));
    }


}
