package org.msa.userservice.global.config.redis;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
@RequiredArgsConstructor
public class RedisUtil {

    private final RedisTemplate<String, Object> redisTemplate;

    public void set(String key, Object object, Duration duration) {
        redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<>(object.getClass()));
        if(duration == null) {
            redisTemplate.opsForValue().set(key, object);
            return;
        }
        redisTemplate.opsForValue().set(key, object, duration);
    }

    public Object get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    public void delete(String key) {
        redisTemplate.delete(key);
    }
}
