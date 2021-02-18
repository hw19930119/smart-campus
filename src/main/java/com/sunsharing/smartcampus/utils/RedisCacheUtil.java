/*
 * @(#) RedisUtil
 * 版权声明 厦门畅享信息技术有限公司, 版权所有 违者必究
 *
 * <br> Copyright:  Copyright (c) 2019
 * <br> Company:厦门畅享信息技术有限公司
 * <br> @author Administrator
 * <br> 2019-08-19 09:50:06
 */

package com.sunsharing.smartcampus.utils;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class RedisCacheUtil {
    public static StringRedisTemplate redisTemplate;
    public static String useRedis;
    @Autowired(required = false)
    public void setRedisTemplate(StringRedisTemplate redisTemplate) {
        RedisCacheUtil.redisTemplate = redisTemplate;
    }
    public static Object getCache(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    public static void saveCache(String key, String value) {
        redisTemplate.opsForValue().set(key,value);

    }

    public static void saveCacheByPriod(String key, String value, String priod) {
        redisTemplate.opsForValue().set(key, value, Long.parseLong(priod), TimeUnit.SECONDS);
    }
    public static void expireByKeyAndSecond(String key,int second) {
        if(getCache(key)!=null){
            redisTemplate.expire(key,second,TimeUnit.SECONDS);
        }
    }
    public static void expireByKey(String key) {
        if(getCache(key)!=null){
            redisTemplate.expire(key,1,TimeUnit.MILLISECONDS);
        }
    }
}
