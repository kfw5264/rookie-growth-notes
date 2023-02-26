package com.masq.redis;

import com.masq.utils.JedisUtil;
import org.junit.Test;
import redis.clients.jedis.Jedis;

public class RedisListTest {

    @Test
    public void test01() {
        Jedis jedis = JedisUtil.getJedis();
        if (jedis == null) {
            return;
        }


        String key = "masq_list";

        // 先删除原来的重新添加
        jedis.del(key);

        jedis.rpush(key, "a", "b", "c");
        jedis.lpush(key, "1", "2", "3");
        System.out.println("LRANGE(0, -1) --> " + jedis.lrange(key, 0, -1));
        System.out.println("LRANGE(0, 4) --> " + jedis.lrange(key, 0, 4));

        jedis.lrem(key, 1, "a");
        System.out.println("after REM a LRANGE(0, -1) --> " + jedis.lrange(key, 0, -1));

        System.out.println("LPOP --> " + jedis.lpop(key));
        System.out.println("RPOP --> " + jedis.rpop(key));
        System.out.println("after LPOP and RPOP --> " + jedis.lrange(key, 0 , -1));

        jedis.rpoplpush(key, key);
        System.out.println("after RPOPLPUSH same key --> " + jedis.lrange(key, 0, -1));

        jedis.ltrim(key, 0, 1);
        System.out.println("after LTRIM --> " + jedis.lrange(key, 0,  -1));
    }

    @Test
    public void testBpop() {
        Jedis jedis = JedisUtil.getJedis();
        if (jedis == null) {
            return;
        }

        String key = "bpop_demo";
        for (int i = 0; i < 10; i++) {
            System.out.println(jedis.brpop(0, key));
        }
    }

    @Test
    public void testBpopSet() {
        Jedis jedis = JedisUtil.getJedis();
        if (jedis == null) {
            return;
        }

        String key = "bpop_demo";
        for (int i = 0; i < 10; i++) {
            jedis.rpush(key, i + "");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
