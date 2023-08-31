package com.fudian.project.system.security.utils;

/**
 * @version 1.0
 * @author： WangQiMin
 * @date： 2021-09-24 11:27
 */

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fudian.api.system.pojo.LoginUser;
import com.fudian.api.system.pojo.Students;
import com.fudian.common.utils.ServletUtils;
import com.fudian.project.system.security.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;

import javax.validation.constraints.NotNull;
import java.lang.reflect.Method;
import java.time.Duration;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Cache缓存自定义key
 */
@Configuration
public class MyCacheConfig {
    @Autowired
    TokenService tokenService;


    @Bean
    public KeyGenerator setKeyGenerator() {
        return new KeyGenerator() {
            @NotNull
            public Object generate(@NotNull Object target, @NotNull Method method, @NotNull Object... params) {
                //通过Token获取当前用户查
                Students student = tokenService.getLoginUser(ServletUtils.getRequest()).getStudent();
                return  method.getName()+"+"+student.getStudentId();
            }
        };
    }
    @Bean
    public KeyGenerator delKeyGenerator() {
        return new KeyGenerator() {
            @NotNull
            public Object generate(@NotNull Object target, @NotNull Method method, @NotNull Object... params) {
                //通过Token获取当前用户查
                Students student = tokenService.getLoginUser(ServletUtils.getRequest()).getStudent();
                return  method.getName()+"+"+student.getStudentId();
            }
        };
    }
    @Bean
    public KeyGenerator delAllKeyGenerator() {
        return new KeyGenerator() {
            @NotNull
            public Object generate(@NotNull Object target, @NotNull Method method, @NotNull Object... params) {
                //通过Token获取当前用户查
                Students student = tokenService.getLoginUser(ServletUtils.getRequest()).getStudent();
                return  method.getName()+"+*";
            }
        };
    }
   /* *//*—————————————————————————————————设置key的缓存过期时间———————————————————————————————————————————————*//*
    @Bean
    public CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
        return new RedisCacheManager(
                RedisCacheWriter.nonLockingRedisCacheWriter(redisConnectionFactory),
                this.getRedisCacheConfigurationWithTtl(600), // 默认策略，未配置的 key 会使用这个
                this.getRedisCacheConfigurationMap() // 指定 key 策略
        );
    }

    private Map<String, RedisCacheConfiguration> getRedisCacheConfigurationMap() {
        Map<String, RedisCacheConfiguration> redisCacheConfigurationMap = new HashMap<>();
        //设置过期时间
        redisCacheConfigurationMap.put("TrainingServiceImpl", this.getRedisCacheConfigurationWithTtl(1800));
        return redisCacheConfigurationMap;
    }

    private RedisCacheConfiguration getRedisCacheConfigurationWithTtl(Integer seconds) {
        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(Object.class);
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(om);
        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig();
        redisCacheConfiguration = redisCacheConfiguration.serializeValuesWith(
                RedisSerializationContext
                        .SerializationPair
                        .fromSerializer(jackson2JsonRedisSerializer)
        ).entryTtl(Duration.ofSeconds(seconds));
        return redisCacheConfiguration;
    }*/

}
