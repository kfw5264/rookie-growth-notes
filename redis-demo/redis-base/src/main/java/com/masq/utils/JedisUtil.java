package com.masq.utils;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class JedisUtil {

    private static final String REDIS_HOST = "192.168.154.128";
    private static final int REDIS_PORT = 6379;
    private static final int TIMEOUT = 2000;
    private static final String REDIS_AUTH = "123456";

    public static Jedis getJedis() {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxIdle(8);
        config.setMaxTotal(18);
        try (JedisPool pool = new JedisPool(config, REDIS_HOST, REDIS_PORT, TIMEOUT, REDIS_AUTH)) {
            return pool.getResource();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
