## 一些问题
1. 为什么生产环境不要使用keys来遍历redis中的key?
   > 由于redis是单线程的，当redis中包含大量的键，执行keys的时候可能会导致redis阻塞。如果真的有遍历键的需求，可以考虑下面集中方法 
   > 1. 在一个不对外提供服务的从节点上执行，这样不会阻塞到客户端的请求，但是会影响到主从复制。
   > 2. 如果确定总键数比较少，遍历不需要花费太多时间，可以执行。
   > 3. 使用scan命令渐进式遍历的方式来解决keys可能带来的阻塞。

2. Jedis直连以及连接池的优缺点 
    
  |  | 优点  | 缺点                                                                      | 
  | ----|-------------------------------------------------------------------------| ---- | 
  | 直连 | 简单方便，适用于少量长期连接的场景 | 1) 每次连接需要新建/关闭TCP连接   <br/>2) 资源无法控制，极端情况会出现连接泄露   <br/>3) Jedis对象线程不安全 |
  | 连接池 |1) 无需每次新建或者关闭连接，节约资源。 <br/>2) 使用连接池的形式保护和控制资源的使用 | 相对于直连使用相对麻烦，对于资源管理需要很多参数来保证，规划不合理的情况下容易出现问题 | 
 
3. Jedis无法从连接池获取到连接。
   ```
   redis.clients.jedis.exceptions.JedisConnectionException: Could not get a resource from the pool
   ```
    - 并发比较高的情况下连接池中jedis个数设置过小。
    - 没有正确使用连接池，比如使用完成之后没有释放连接。 
    - 存在慢查询操作，这些慢查询占用jedis无法归还
    - redis服务器因为一些原因造成阻塞，导致客户端的命令无法即使执行。
   
4. 客户端读写超时。
   ```
   redis.colents.jedis.exceptions.JedisConnectionException: Java.net.SocketTimeoutException: Read timed out.   
   ```
    - 读写超时时间设置过短。
    - 命令本身比较慢。
    - 客户端与服务器网络不正常。
    - Redis自身阻塞.

5. 客户端连接超时。
   ```
   redis.clients.jedis.exceptions.JedisConnectionExceptioni: java.net.SocketTimeoutException: connect timed out. 
   ```
    - 连接超时时间设置过短。
    - Redis发生阻塞，造成tcp-backlog已满，导致新的连接失败。
    - 客户端与服务器网络异常。

6. 客户端缓冲区异常
   ```
   redis.client.jedis.exceptions.JedisConnectionException: Unexpected end of stream
   ```
    - 输出缓冲区满
    - 长时间闲置连接被服务器断开
    - 不正常并发读写：Jedis对象同时被多个线程并发操作。

7. Lua脚本正在执行
   ```
   redis.clients.jedis.exceptions.JedisDataException: BUSY Redis  is busy running a script. You can only call SCRIPT KILL or SHUTDOWN NOSAVE.
   ```
   Redis执行lua脚本，并且超过了lua-time-limit，此时jedis调用Redis时会报错Lua脚本正在执行。

8. Redis正在加载持久化文件。
   ```
   redis.clients.jedis.exception.JedisDataException: LOADING Redis is loading the dataset in memory.
   ```
   Jedis调用Redis时， Redis正在从磁盘中加载持久化文件。 

9. Redis使用内存超过maxmemory配置
    ```
    redis.client.jedis.exceptions.JedisDataException: OOM command not allowd when used memory > "maxmemory".    
    ```
   Redis的使用内存大于配置的最大内存。 

10. 客户端连接数过大 
    ```
    redis.clients.jedis.exception.JedisDataException: ERR max number of client reached.
    ```

11. AOF为什么直接采用文本协议格式
    1. 文件协议具有很好的兼容性。
    2. 开启AOF后，所有写入命令都包含追加操作，直接采用协议格式，避免了二次处理开销。
    3. 文本协议具有可读性，方便直接修改和处理。 

12. AOF为什么把命令追加到aof_buf中？
    Redis使用单线程相应命令，如果每次写AOF都把命令写入到磁盘中，那么性能完全取决与当前硬盘负载 。先写入缓存区aof_buf中，还有另外一个好处，Redis可以提供多种缓冲区同步硬盘的策略 ，在性能和安全性方面做出平衡。


## Redis基础
1. Redis提供5种数据结构， 每种数据结构都有多种内部编码实现。
2. 纯内存存储、 IO多路复用技术、 单线程架构是造就Redis高性能的三个因素。
3. 由于Redis的单线程架构， 所以需要每个命令能被快速执行完， 否则会存在阻塞Redis的可能， 理解Redis单线程命令处理机制是开发和运维Redis的核心之一。
4. 批量操作（例如mget、 mset、 hmset等） 能够有效提高命令执行的效率， 但要注意每次批量操作的个数和字节数。
5. 了解每个命令的时间复杂度在开发中至关重要， 例如在使用keys、 hgetall、 smembers、 zrange等时间复杂度较高的命令时， 需要考虑数据规模 对于Redis的影响。
6. persist命令可以删除任意类型键的过期时间， 但是set命令也会删除字符串类型键的过期时间， 这在开发时容易被忽视。
7. move、 dump+restore、 migrate是Redis发展过程中三种迁移键的方式， 其中move命令基本废弃， migrate命令用原子性的方式实现了dump+restore， 并且支持批量操作， 是Redis Cluster实现水平扩容的重要工具。
8. scan命令可以解决keys命令可能带来的阻塞问题， 同时Redis还提供170了hscan、 sscan、 zscan渐进式地遍历hash、 set、 zset。

## Redis进阶
1. 慢查询中的两个重要参数slowlog-log-slower-than和slowlog-maxlen。
2. 慢查询不包含命令网络传输和排队时间。
3. 有必要将慢查询定期存放。
4. redis-cli一些重要的选项， 例如--latency、 –-bigkeys、 -i和-r组合。
5. redis-benchmark的使用方法和重要参数。
6. Pipeline可以有效减少RTT次数， 但每次Pipeline的命令数量不能无节制。
7. Redis可以使用Lua脚本创造出原子、 高效、 自定义命令组合。
8. Redis执行Lua脚本有两种方法： eval和evalsha。
9. Bitmaps可以用来做独立用户统计， 有效节省内存。
10. Bitmaps中setbit一个大的偏移量， 由于申请大量内存会导致阻塞。
11. HyperLogLog虽然在统计独立总量时存在一定的误差， 但是节省的内存量十分惊人。
12. Redis的发布订阅机制相比许多专业的消息队列系统功能较弱， 不具备堆积和回溯消息的能力， 但胜在足够简单。
13. Redis3.2提供了GEO功能， 用来实现基于地理位置信息的应用， 但底层实现是zset。

## 客户端通信协议(RESP)
- 参数格式    
  `set hello world`经客户端封装之后:

  ```
  *3       三个参数数量  
  SET    
  $5       key的字符数    
  hello    
  $5       value的字符数   
  world   
  ```      
  最终传输格式：  
  `*3\r\nSET\r\n$5\r\nhello\r\n$5\r\nworld\r\n`  
  返回结果： +OK

- 返回结果格式 

  - 状态回复：第一个字符"+"
  - 错误回复：第一个字符"-"
  - 整数回复：第一个字符":"
  - 字符串回复：第一个字符"$"
  - 多条字符串回复：第一个字符"*"

![Redis五种回复类型在RESP下的编码.png](../../redis-demo/note/images/Redis五种回复类型在RESP下的编码.png)  


### Jedis连接池的常用属性

|              参数名              |                                                    含义                                                     | 默认值                       |
|:-----------------------------:|:---------------------------------------------------------------------------------------------------------:|:--------------------------|
|           maxActive           |                                                 连接池最大连接数                                                  | 8                         | 
|            maxIdle            |                                                连接池最大空闲连接数                                                 | 8                         |
|            minIdle            |                                                连接池最小空闲连接数                                                 | 0                         |
|         maxWaitMillis         |                                   连接池资源耗尽时，调用者的最大等待时间(单位为毫秒)，一般不建议使用默认值                                   | -1：表示永远不超时，一直等待           |
|          jmxEnabled           | 是否开启jmx监控，如果应用开启了jmx端口并且jmxEnabled设置为true，就可以通过jconsole或者jvisualvm看到关于连接池的相关统计，有助于了解连接池的使用情况，并且可以针对其做监控统计 | true                      | 
|  minEvictableIdleTimeMillis   |                                          连接的最小空间时间，到达此值后空闲连接将被移除                                          | 1000L * 60L * 30ms = 30分钟 |
|    numTestsPerEvictionRun     |                                              做空闲连接检测时，每次的采样数                                              | 3                         | 
|         testOnBorrow          |                               向连接池借用连接时是否做连接有效性检测，无效连接会被移除，每次借用多执行一次ping命令                                | false                     | 
|         testOnReturn          |                               向连接池归还连接时是否做连接有效性检测，无效连接会被移除，每次归还多执行一次ping命令                                | false                     |
|         testWhileIdle         |                                       向连接池借用连接时是否做连接空闲检测，空闲超时连接会被移除                                       | false                     | 
| timeBetweenEvictionRunsMillis |                                             空闲连接的检测周期(单位为毫秒)                                              | -1 表示不做检测                 | 
|      blockWhenExhausted       |                  当连接池用尽后，调用者是否要等待，这个参数和maxWaitMillis对应的，只有当此参数为true时，maxWaitMillis才会生效 | true |


### Redis客户端管理
#### 客户端API
 1. `client list`

    | 命令  | 含义        |
    |-----------|----|
    | id  | 客户端连接ID   |
    | addr   | 客户端的IP和端口 |
    |fd | socket文件描述符 |
    | name | 客户端连接名称 |
    | age | 客户端连接存活周期 | 
    | idle | 客户端连接空闲周期| 
    | flags | 客户端类型标识 |
    |db | 当前客户端正在使用的数据库索引 | 
    | sub/psub | 当前客户端订阅的频道或者模式数 | 
    | multi | 当前事务中已执行的命令个数 | 
    | qbuf| 输入缓冲区总容量 |
    | qbuf_free | 输入缓冲区剩余容量 |
    | cbl | 固定缓冲区的长度 | 
    | oll | 动态缓冲区列表的长度  |
    | omem | 固定缓冲区和动态缓冲区使用的容量 | 
    | events | 文件描述事件r/w: r和w分别表示可读和可写 | 
    | cmd | 当前客户端最后一次执行的命令，不包含参数 | 

输入缓冲区使用不当会造成：
1. 一旦某个客户端输入缓存超过1G，客户端就会被关闭。
2. 输入缓冲区不受maxmemory限制，如果Redis设置了maxmeory为4G，已存储2G的情况下，如果输入缓冲区使用了3G，会产生数据丢失、键值淘汰、OOM等情况。


## Redis客户端 
1. RESP（Redis Serialization Protocol Redis） 保证客户端与服务端的正常通信， 是各种编程语言开发客户端的基础。
2. 要选择社区活跃客户端， 在实际项目中使用稳定版本的客户端。
3. 区分Jedis直连和连接池的区别， 在生产环境中， 应该使用连接池。
4. Jedis.close（） 在直连下是关闭连接， 在连接池则是归还连接。
5. Jedis客户端没有内置序列化， 需要自己选用。
6. 客户端输入缓冲区不能配置， 强制限制在1G之内， 但是不会受到maxmemory限制。
7. 客户端输出缓冲区支持普通客户端、 发布订阅客户端、 复制客户端配置， 同样会受到maxmemory限制。
8. Redis的timeout配置可以自动关闭闲置客户端， tcp-keepalive参数可以周期性检查关闭无效TCP连接
9. monitor命令虽然好用， 但是在大并发下存在输出缓冲区暴涨的可能性。
10. info clients帮助开发和运维人员找到客户端可能存在的问题。
11. 理解Redis通信原理和建立完善的监控系统对快速定位解决客户端常见问题非常有帮助


## 持久化
1. Redis提供了两种持久化方式： RDB和AOF。
2. RDB使用一次性生成内存快照的方式， 产生的文件紧凑压缩比更高， 因此读取RDB恢复速度更快。 由于每次生成RDB开销较大， 无法做到实时持久化， 一般用于数据冷备和复制传输。
3. save命令会阻塞主线程不建议使用， bgsave命令通过fork操作创建子进程生成RDB避免阻塞。
4. AOF通过追加写命令到文件实现持久化， 通过appendfsync参数可以控制实时/秒级持久化。 因为需要不断追加写命令， 所以AOF文件体积逐渐变大， 需要定期执行重写操作来降低文件体积。
5. AOF重写可以通过auto-aof-rewrite-min-size和auto-aof-rewritepercentage参数控制自动触发， 也可以使用bgrewriteaof命令手动触发。
6. 子进程执行期间使用copy-on-write机制与父进程共享内存， 避免内存消耗翻倍。 AOF重写期间还需要维护重写缓冲区， 保存新的写入命令避免数据丢失。
7. 持久化阻塞主线程场景有： fork阻塞和AOF追加阻塞。 fork阻塞时间跟内存量和系统有关， AOF追加阻塞说明硬盘资源紧张。
8. 单机下部署多个实例时， 为了防止出现多个子进程执行重写操作，建议做隔离控制， 避免CPU和IO资源竞争。

## 复制
1. Redis通过复制功能实现主节点的多个副本。 从节点可灵活地通过slaveof命令建立或断开复制流程。
2. 复制支持树状结构， 从节点可以复制另一个从节点， 实现一层层向下的复制流。 Redis2.8之后复制的流程分为： 全量复制和部分复制。 全量复制需要同步全部主节点的数据集，
大量消耗机器和网络资源。 而部分复制有效减少因网络异常等原因造成的不必要全量复制情况。 通过配置合理的复制积压缓冲区尽量避免全量复制。
3. 主从节点之间维护心跳和偏移量检查机制， 保证主从节点通信正常和数据一致。
4. Redis为了保证高性能复制过程是异步的， 写命令处理完后直接返回给客户端， 不等待从节点复制完成。 因此从节点数据集会有延迟情况。
5. 当使用从节点用于读写分离时会存在数据延迟、 过期数据、 从节点可用性等问题， 需要根据自身业务提前作出规避。
6. 在运维过程中， 主节点存在多个从节点或者一台机器上部署大量主节点的情况下， 会有复制风暴的风险。

## 阻塞
1. 客户端最先感知阻塞等Redis超时行为， 加入日志监控报警工具可快速定位阻塞问题， 同时需要对Redis进程和机器做全面监控。
2. 阻塞的内在原因： 确认主线程是否存在阻塞， 检查慢查询等信息，发现不合理使用API或数据结构的情况， 如keys、 sort、 hgetall等。 关注CPU使用率防止单核跑满。 当硬盘IO资源紧张时， AOF追加也会阻塞主线程。
3. 阻塞的外在原因： 从CPU竞争、 内存交换、 网络问题等方面入手排查是否因为系统层面问题引起阻塞
