# Kafka
## 消息队列
- 优点
    1. 异步任务
    2. 解耦
    3. 削峰填谷     

- 工作模式
    1. 至多一次：服务不能重复消费同一条消息，消息消费完成之后立即删除。
    2. 没有限制：不同服务消费同一条消息


## Kafka
> - Kafka是一个分布式流处理平台，流处理平台特性：   
>   1. 发布和订阅流式消息，类似消息队列。
>   2. 储存流式记录且具有较好的容错性。
>   3. 流式记录产生时就处理。 
> - Kafka使用场景: 
>   1. 实时流数据管道，系统和应用之间可靠的传递消息。（Message Queue） 
>   2. 构建实时流式应用程序，对这些数据进行转换或者影响。    

> Kafka提供了Kafka Streaming插件包实现了实时的在线流处理。运行在应用端，简单、入门要求低、部署方便。

### 基本概念
- Topic
- Borker
- Partition
- ConsumerGroup



- Topic分区数越大，写入能力越强，同时消费者消费能力越强。

#### 高性能
- 高吞吐率     
    - 顺序写入、MMFile(Memory Mapped File)
    - Zero Copy零拷贝
        1. 常规IO: 磁盘--> 内存 --> 应用 --> Socket Buffer --> 网络
        2. 零拷贝：磁盘 --> 内存 --> Socket Buffer --> 网络


#### 安装(CentOS)


#### Java API
- Topic操作
- 生产者与消费者
- 自定义分区机制
- 自定义序列化
- 拦截器
- offset自动控制
- acks和retries
    > 生产者写入数据之后，Kafka需要给生产者一个确认信息，如果超过等待时间生产者没有收到确认信息，就会重试n次。   
    > `request.timeout.ms=30000`，生产者接受确认消息的时间，默认30s    
    > `retries=2147383647`, 生产者重试次数         
- 幂等写  
    > Kafka写入生产者发送的消息之后给生产发送确认消息，如果发送确认消息的过程超时，生产者就会进行重试，这样会导致Kafka中出现重复数据，幂等可以保证生产者上发送的消息不会丢失，也不会重复。    
    > 生产者每次请求都会有一个唯一标识，并且Kafka会记录所有已处理过的请求，当收到新的请求时，就会和已处理过的请求对比，如果处理记录中有相同的标识，说明是重聚记录，拒绝掉。    
    > Kafka会给生产者生成一个唯一的PID，每个生产者会给每条消息一个序号，PID和序列号与消息捆绑到一块，然后发送给Broker。   
    > 开启幂等: `enable.idemportence=true`。要开启幂等必须先开启重试机制。    
    > 重试可能会导致顺序不一致，此时需要设置`max.in.flight.requests.per.connection`小于或者等于5，如果设置为1者意味着数据严格有序，只要有一个发送不成功则等待，直到发送成功为止。   
- Kafka事务   
    > - 生产者Only：
    > - 消费者&生产者事务：

#### 架构进阶
1. Kafka数据同步机制
    - Kafka 0.11之前使用高水位线截取机制。可能会导致数据丢失以及数据不一致的情况。 
        - 高水位：所有高水位之前的数据默认已经同步了
        - LEO:Log End Offset。日志最后的偏移量。
        - ISR: In-Sync-Replicas. 处在同步的副本集
    - Leader Epoch机制。

#### kafka-eagle

#### Kafka-Flume集成

#### Kafka集成SpringBoot
- `@KafkaListeners`
- `@SendTo`
- `KafkaTemplate`
    - 事务下： `KafkaTemplate#executeInTransaction()`


  