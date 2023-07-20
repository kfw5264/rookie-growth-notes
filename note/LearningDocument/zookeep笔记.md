## 概念
- Apache Hadoop下子项目，树形目录服务。
- 分布式的、开源的分布式应用程序的协调服务(管理)
- 功能：
    - 配置管理
    - 分布式锁
    - 集群管理

## 数据结构
树形目录，类似Unix文件系统目录树，层次化结构   
每个节点ZNode   
节点类型：
    - PERSISTENT 持久化节点
    - EPHEMERAL  临时节点  -e 
    - PERSISTENT_SEQUENTIAL 持久化顺序节点  -s
    - EPHEMERAL_SEQUENTIAL  临时顺序节点    -es


## 命令
### 服务端
```bash
./zkServer.sh start       启动
./zkServer.sh status      状态
./zkServer.sh stop        停止
./zkServer.sh restart     重启
```
### 客户端
```bash
./zkCli.sh -server host:port
> ls   
> ls2 已被ls -s替代    
> create -e/s/es [NODE_NAME] [DATA]    
> get    
> set    
> delete    
> deleteall    
> quit    
```
#### Java客户端Curator
1. 建立连接
2. 创建节点
3. 删除节点
4. 修改节点
5. 查询节点
6. Watch事件监听
7. 分布式锁实现
    - 临时顺序节点：获取到锁的节点宕机之后自动删除
    - 最小子节点顺序：

### 集群
- 奇数个节点
- myid  zxid

echo 1 > /usr/local/zookeeper-cluster/zookeeper-1/dataDir/myid
echo 2 > /usr/local/zookeeper-cluster/zookeeper-2/dataDir/myid
echo 3 > /usr/local/zookeeper-cluster/zookeeper-3/dataDir/myid

server.1=192.168.154.129:2881:3881
server.2=192.168.154.129:2882:3882
server.3=192.168.154.129:2883:3883

#### 集群中角色
1. Leader
    - 处理事务请求：如增删改
    - 集群内部各服务间的调度者
2. Follower
    - 处理客户端非事务请求，转发事务请求给Leader
    - 参与Leader选举投票
3. Observer
    - 处理客户端非事务请求，转发事务请求给Leadercls

    
