package com.masq.redis;

import com.masq.utils.JedisUtil;
import org.junit.Test;
import redis.clients.jedis.Jedis;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RedisHashTest {

    @Test
    public void testHset() {
        Jedis jedis = JedisUtil.getJedis();
        if (jedis == null) {
            return;
        }

        String key = "masq:user:1";

        Map<String, String> map = new HashMap<>();
        map.put("name", "masq");
        map.put("age", "31");
        map.put("gender", "true");

        jedis.hset(key, map);
        jedis.hset(key, "work", "programmer");

        List<String> values = jedis.hmget(key, "name", "age", "gender", "work");
        System.out.println("[name, age, gender, work] --> " + values);

        jedis.hdel(key, "work");
        System.out.println( "HGET work --> " + jedis.hget(key, "work"));

        jedis.hincrBy(key, "age", 3);
        System.out.println(jedis.hget(key, "age"));
        jedis.hincrByFloat(key, "age", 2.5);
        System.out.println(jedis.hget(key, "age"));

        System.out.println("HGETALL --> " + jedis.hgetAll(key));

        System.out.println("HKEYS --> " + jedis.hkeys(key));

        System.out.println("HVALUES --> " + jedis.hvals(key));

        System.out.println("HLEN --> " + jedis.hlen(key));
    }
}
