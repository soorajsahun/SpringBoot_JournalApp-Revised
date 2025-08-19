package net.engineeringdigest.journalApp.service;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class RedisService {

    @Autowired
    private RedisTemplate redisTemplate;

    public <T> T get(String key, Class<T> weatherResponseClass) {
        try {
            Object o = redisTemplate.opsForValue().get(key);

            //to convert in our desired class object
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(o.toString(), weatherResponseClass);
        } catch (Exception e) {
            log.error("Exception", e);
            return null;
        }
    }

    public void set(String key, Object o, Long expiry) {
        try {
            ObjectMapper objectMapper=new ObjectMapper();
            String s = objectMapper.writeValueAsString(o);
            redisTemplate.opsForValue().set(key, s, expiry, TimeUnit.SECONDS);

        } catch (Exception e) {
            log.error("Exception", e);
        }
    }
}