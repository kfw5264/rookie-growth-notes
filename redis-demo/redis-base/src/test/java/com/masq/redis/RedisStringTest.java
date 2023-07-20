package com.masq.redis;

import com.masq.utils.JedisUtil;
import org.junit.Test;
import redis.clients.jedis.Jedis;

import java.util.List;

public class RedisStringTest {

    @Test
    public void testSetAndGet() {
        Jedis jedis = JedisUtil.getJedis();
        if (jedis == null) {
            return;
        }

        String key = "set_test";

        jedis.set(key, "This is a demo for set");
        long len = jedis.strlen(key);

        System.out.println("value is \"" + jedis.get(key) + "\", and length is " + len);

        jedis.append(key, ". append---");
        System.out.println(jedis.get(key));

        String str = jedis.getrange(key, 0, 11);
        System.out.println("0~11 is " + str);
    }

    @Test
    public void testMSetAndMGet() {
        Jedis jedis = JedisUtil.getJedis();
        if (jedis == null) {
            return;
        }

        String key1 = "mset_key_1";
        String value1 = "mset_value_1";
        String key2 = "mset_key_2";
        String value2 = "mset_value_2";
        String key3 = "mset_key_3";
        String value3 = "mset_value_3";

        jedis.mset(key1, value1, key2, value2, key3, value3);


        List<String> values = jedis.mget(key1, key2, key3);
        for (String value : values) {
            System.out.println("MGET获取到的value是：" + value);
        }

    }

    @Test
    public void testPsetex() {
        Jedis jedis = JedisUtil.getJedis();
        if (jedis == null) {
            return;
        }
        String key = "psetex_test";
        jedis.psetex(key, 10000, "This is a psetex demo");

        String str = jedis.get(key);
        long ttl = jedis.ttl(key);
        System.out.println("value is " + str + ", and ttl is " + ttl);


    }

    @Test
    public void testIncr() {
        Jedis jedis = JedisUtil.getJedis();
        if (jedis == null) {
            return;
        }

        String key = "masq:age";
        jedis.set(key, "31");

        jedis.incr(key);
        System.out.println("incr --> " + jedis.get(key));

        jedis.incrBy(key, 3);
        System.out.println("incrBy 3 --> " + jedis.get(key));

        jedis.incrByFloat(key, 2.5);
        System.out.println("incrByFloat 2.5 --> " + jedis.get(key));
        jedis.incrByFloat(key, 0.5);

        jedis.decr(key);
        System.out.println("decr --> " + jedis.get(key));

        jedis.decrBy(key, 6);
        System.out.println("decrBy 3 --> " + jedis.get(key));
    }


}
