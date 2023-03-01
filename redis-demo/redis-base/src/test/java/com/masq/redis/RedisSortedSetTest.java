package com.masq.redis;

import com.masq.utils.JedisUtil;
import org.junit.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.params.ZParams;
import redis.clients.jedis.params.ZRangeParams;
import redis.clients.jedis.resps.Tuple;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class RedisSortedSetTest {

    @Test
    public void testSortedSet() {
        Jedis jedis = JedisUtil.getJedis();
        if (jedis == null) {
            return;
        }

        // 语文成绩
        String key1 = "masq:score:chinese";
        // 数学成绩
        String key2 = "masq:score:math";

        // 先删除redis中已经存在的数据
        jedis.del(key1, key2);

        // 语文成绩登记
        Map<String, Double> chineseScore = new HashMap<>();
        chineseScore.put("s-1", 72.5);
        chineseScore.put("s-2", 82.0);
        chineseScore.put("s-3", 64.0);
        chineseScore.put("s-4", 97.5);
        chineseScore.put("s-5", 78.5);
        chineseScore.put("s-7", 69.5);
        jedis.zadd(key1, chineseScore);

        // 数学成绩登记
        Map<String, Double> mathScore = new HashMap<>();
        mathScore.put("s-1", 92.5);
        mathScore.put("s-2", 78.5);
        mathScore.put("s-3", 67.5);
        mathScore.put("s-4", 82.5);
        mathScore.put("s-5", 78.5);
        mathScore.put("s-6", 82.5);
        jedis.zadd(key2, mathScore);

        // 两门考试都参加的同学平均分(使用zset求交集的方式)
        ZParams params = new ZParams();
        params.weights(0.5, 0.5);
        Set<Tuple> tuples = jedis.zinterWithScores(params, key1, key2);
        System.out.println("参加全部两门课程的同学平均分及排名(从低到高) --> " + tuples);

        // 所有同学的平均分
        tuples = jedis.zunionWithScores(params, key1, key2);
        System.out.println("所有同学的平均分及排名(从低到高) --> " + tuples);

        // 两门课程都参加的同学的总分
        params.weights(1, 1);
        tuples = jedis.zinterWithScores(params, key1, key2);
        System.out.println("参加全部两门课程的同学的总分及排名(从低到高) --> " + tuples);

        // 所有同学的总分
        tuples = jedis.zunionWithScores(params, key1, key2);
        System.out.println("所有同学的总分及排名(从低到高) --> " + tuples);

        System.out.println( "只参加语文考试的同学 -->" + jedis.zdiffWithScores(key1, key2));
        System.out.println( "只参加数学考试的同学 -->" + jedis.zdiffWithScores(key2, key1));

        // 语文考试学生个数
        System.out.println("语文考试学生个数 --> " + jedis.zcard(key1));

        // s-1语文成绩排名
        Long rank1 = jedis.zrevrank(key1, "s-1");
        Long rank2 = jedis.zrank(key1, "s-1");
        System.out.println("s-1的语文成绩排名为" + (rank1 + 1) + ", 倒数" + (rank2 + 1));

        // s-1的语文成绩增加5分
        jedis.zincrby(key1, 5.0, "s-1");
        // 查看s-1现在的语文成绩
        Double score = jedis.zscore(key1, "s-1");
        System.out.println("s-1的语文成绩是" + score);

        // 语文成绩排名
        List<String> range = jedis.zrange(key1, 0, -1);
        System.out.println("语文成绩排名（从低到高） --> " + range);
        List<String> revRange = jedis.zrevrange(key1, 0, -1);
        System.out.println("语文成绩排名（从高到低） --> " + revRange);

        // 语文成绩80分以上的人数
        long count = jedis.zcount(key1, 80, 150);
        System.out.println("语文成绩80分以上的人数有" + count );

        // 删除语文成绩倒数两位
        jedis.zremrangeByRank(key1, 0, 1);
        revRange = jedis.zrevrange(key1, 0, -1);
        System.out.println("语文成绩排名（从高到低） --> " + revRange);

        // 删除数学成绩60-82之间的人
        jedis.zremrangeByScore(key2, 60, 80);
        revRange = jedis.zrevrange(key2, 0, -1);
        System.out.println("数学成绩排名（从高到低） --> " + revRange);

    }
}
