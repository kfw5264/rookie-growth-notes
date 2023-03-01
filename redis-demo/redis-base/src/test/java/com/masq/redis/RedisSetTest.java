package com.masq.redis;

import com.masq.utils.JedisUtil;
import org.junit.Test;
import redis.clients.jedis.Jedis;

import java.util.Set;

public class RedisSetTest {

    @Test
    public void testSet() {
        Jedis jedis = JedisUtil.getJedis();
        if (jedis == null) {
            return;
        }

        String key1 = "masq:user:1:tags";
        String key2 = "masq:user:2:tags";

        // 测试功能，执行前先删除原来保存到redis中的数据
        jedis.del(key1, key2);

        // ID为1的用户的标签
        jedis.sadd(key1, "football", "music", "food", "swimming");
        // ID为2的用户的标签
        jedis.sadd(key2, "football", "basketball", "reading", "food");

        // 查看ID为1的用户的所有标签
        Set<String> user1Tags = jedis.smembers(key1);
        System.out.println("ID为1的用户的标签：" + user1Tags);

        // 删除ID为1的music标签
        jedis.srem(key1, "music");
        // 查看ID为1的用户的所有标签
        user1Tags = jedis.smembers(key1);
        System.out.println("ID为1的用户的标签：" + user1Tags);

        // 判断用户2有没有swimming跟food标签
        System.out.println("用户2是否存在swimming标签 --> " + jedis.sismember(key2, "swimming"));
        System.out.println("用户2是否存在food标签 --> " + jedis.sismember(key2, "food"));

        // 计算两个用户的标签个数
        System.out.println("用户1的标签数 --> " + jedis.scard(key1));
        System.out.println("用户2的标签数 --> " + jedis.scard(key2));

        // 随机取出用户2的两个标签
        System.out.println("随机取出用户2的两个标签 --> " + jedis.srandmember(key2, 2));

        // 用户1和用户2的共同标签
        Set<String> sameTags = jedis.sinter(key1, key2);
        System.out.println("用户1跟用户2的共同标签 --> " + sameTags);

        // 用户1和用户2的所有标签
        System.out.println("两个用户的所有标签 --> " + jedis.sunion(key1, key2));

        // 用户1不同于用户2的标签
        System.out.println("用户1不同于用户2的标签 --> " + jedis.sdiff(key1, key2));

        // 用户2不同于用户1的标签
        System.out.println("用户2不同于用户1的标签 --> " + jedis.sdiff(key2, key1));

        // 随机弹出用户1的一个标签
        System.out.println("随机弹出用户1的一个标签 --> " + jedis.spop(key1));
        // 查看ID为1的用户的所有标签
        user1Tags = jedis.smembers(key1);
        System.out.println("ID为1的用户的标签：" + user1Tags);
    }
}
