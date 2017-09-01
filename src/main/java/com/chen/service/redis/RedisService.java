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
    @SuppressWarnings("SpringAutowiredFieldsWarningInspection")
    @Autowired
    private RedisTemplate<String, String> template;


    @Resource(name="redisTemplate")
    private ValueOperations<String,Object> valueOps;

    /**
     * 给redis添加键值对 30分钟过期
     */
    public void addKeyValueExpiredInThiryMinutes(String key, Object value) {
        valueOps.set(key,value,30, TimeUnit.MINUTES);
    }

    /**
     * 根据键从redis中获取值 并且延长值的生命周期
     */
    public Object getByKey(String key){

        Object value=valueOps.get(key);
        if(value!=null) {
            valueOps.set(key, value, 30, TimeUnit.MINUTES);
        }
        return valueOps.get(key);
    }
}
