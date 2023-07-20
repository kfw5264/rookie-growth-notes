package com.masq.redis;

import com.masq.utils.JedisUtil;
import org.junit.Test;
import redis.clients.jedis.Jedis;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class RedisBaseTest {

    @Test
    public void testKeysAndDBSize() {
        Jedis jedis = JedisUtil.getJedis();
        Set<String> keys = jedis.keys("*");
        System.out.println("通过keys*查出的key共有" + keys.size());
        keys.forEach(System.out::println);

        long count = jedis.dbSize();
        System.out.println("通过dbsize获取的key共有" + count);
    }

    @Test
    public void testDelKey() {
        Jedis jedis = JedisUtil.getJedis();
        long count = jedis.del("age2", "age3");
        System.out.println(count);
    }

    @Test
    public void testExists() {
        Jedis jedis = JedisUtil.getJedis();
        boolean flag = jedis.exists("age2");
        System.out.println(flag);
    }

    @Test
    public void testExpireAndTTL() {
        Jedis jedis = JedisUtil.getJedis();
        String key = "testExpireAndTTL";
        jedis.set(key, "test");

        long ttl = jedis.ttl(key);
        System.out.println("没有设置过期时间的TTL ---> " + ttl);

        jedis.expire(key, 2000);
        ttl = jedis.ttl(key);
        System.out.println("添加完2000秒过期之后的TTL --> " + ttl);

        try {
            Thread.sleep(10 * 1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        ttl = jedis.ttl(key);
        System.out.println("10秒后的TTL ---> " + ttl);

        long persist = jedis.persist(key);
        ttl = jedis.ttl(key);
        System.out.println("移除过期时间之后的TTL ---> " + ttl );

        jedis.expire(key, 2);

        try {
            Thread.sleep(3 * 1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        ttl = jedis.ttl(key);
        System.out.println("过期后的TTL ---> " + ttl);
    }

    @Test
    public void testRenameAndType() {
        Jedis jedis = JedisUtil.getJedis();
        String key = "test01";
        jedis.set(key, "abc");
        System.out.println(key + "的类型是" + jedis.type(key));

        key = "test02";
        Map<String, String> map = new HashMap<>();
        map.put("name", "masq");
        map.put("age", "12");
        jedis.hset(key, map);
        System.out.println(key + "的类型是" + jedis.type(key));

        key = "test03";
        jedis.rpush(key, "a", "b");
        System.out.println(key + "的类型是" + jedis.type(key));

        key = "test04";
        jedis.sadd(key, "a", "b");
        System.out.println(key + "的类型是" + jedis.type(key));

        key = "test05";
        jedis.zadd(key, 52, "zhangsan");
        System.out.println(key + "的类型是" + jedis.type(key));

        String oldKey = "a";
        String newKey = "b";

        // 先删掉原来redis中存储的内容之后重新设置
        jedis.del(oldKey, newKey);

        jedis.set(oldKey, "a");
        System.out.println("RENAME前：" + oldKey + " --> " + jedis.get(oldKey));
        System.out.println("RENAME前：" + newKey + " --> " + jedis.get(newKey));
        jedis.rename(oldKey, newKey);
        System.out.println("RENAME后：" + oldKey + " --> " + jedis.get(oldKey));
        System.out.println("RENAME后" + newKey + " --> " + jedis.get(newKey));

    }




}
