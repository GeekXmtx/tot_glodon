package com.glodon.seckillweb.config;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisClusterTest{

    @Autowired
    private RedisTemplate<String,String> redisTemplate;


    @Test
    public void getValue(){
        ValueOperations<String,String> operations=redisTemplate.opsForValue();
        System.out.println(operations.get("test"));
    }
}
