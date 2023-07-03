# RabbitMQ学习笔记
## AMQP(Advanced Message Queuing Protocol)
### 1. 什么是AMQP
进程之间传递异步消息的网络协议。

### 2. AMQP工作过程

### 3. 队列

## RabbitMQ
### 简介
使用Erlang语言编写的基于AMQP的消息中间件。消息中间件作为分布式系统中的重要组件之一，作用应用解耦、异步消息以及流量削峰等问题。 

### 使用场景
1. 排队算法
2. 秒杀活动
3. 消息分发
4. 异步处理
5. 数据同步
6. 处理耗时任务
7. 流量削峰

### RabbitMQ原理
1. Message
2. Publisher
3. Consumer
4. Exchange
5. Binding
6. Queue
7. Routing-key
8. Conection
9. Channel
10. Virtual Host
11. Broker

### RabbitMQ安装
1. 安装Erlang

2. 安装RabbitMQ
3. 安装


yum install -y make gcc gcc-c++ m4 openssl openssl-devel ncurses-devel unixODBC unixODBC-devel java java-devel