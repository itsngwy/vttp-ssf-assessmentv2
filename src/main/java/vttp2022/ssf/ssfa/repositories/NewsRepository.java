package vttp2022.ssf.ssfa.repositories;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

import jakarta.json.Json;

@Repository
public class NewsRepository {

    @Autowired
    @Qualifier("redislab")
    private RedisTemplate<String, String> redisTemplate;

    public void save(String id, String payload) {

        ValueOperations<String, String> valueOp= redisTemplate.opsForValue();
        valueOp.set(id, payload);
        System.out.printf("%s is saved\n", id);
    }

    public String get(String id) {

        ValueOperations<String, String> valueOp = redisTemplate.opsForValue();
        String value = valueOp.get(id);
        System.out.printf("%s retrieved successfully\n", id);
        return value;
    }
    
}
