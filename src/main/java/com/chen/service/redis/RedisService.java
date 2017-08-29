package com.chen.service.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@Service
public class RedisService {
    @Autowired
    private RedisTemplate<String, String> template;


    @Resource(name="redisTemplate")
    private ValueOperations<String,Object> valueOps;

    /**
     * 给redis添加键值对 30分钟过期
     */
    public void addKeyValueExpiredInThiryMinutes(String key, String value) {
        valueOps.set(key,value,30, TimeUnit.MINUTES);
    }

    public Object getByKey(String key){
        return valueOps.get(key);
    }
}
