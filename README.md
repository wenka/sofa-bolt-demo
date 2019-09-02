> SOFABolt 是蚂蚁金融服务集团开发的一套基于 Netty 实现的网络通信框架。
- 为了让 Java 程序员能将更多的精力放在基于网络通信的业务逻辑实现上，而不是过多的纠结于网络底层 NIO 的实现以及处理难以调试的网络问题，Netty 应运而生。
- 为了让中间件开发者能将更多的精力放在产品功能特性实现上，而不是重复地一遍遍制造通信框架的轮子，SOFABolt 应运而生。
> 基于 **SOFABolt** 的学习实例

#

#### 功能简介
- 基础通信功能 ( remoting-core )
    - 基于 Netty 高效的网络 IO 与线程模型运用
    - 连接管理 (无锁建连，定时断链，自动重连)
    - 基础通信模型 ( oneway，sync，future，callback )
    - 超时控制
    - 批量解包与批量提交处理器
    - 心跳与 IDLE 事件处理
- 协议框架 ( protocol-skeleton )
    - 命令与命令处理器
    - 编解码处理器
    - 心跳触发器
- 私有协议定制实现 - RPC 通信协议 ( protocol-implementation )
    - RPC 通信协议的设计
    - 灵活的反序列化时机控制
    - 请求处理超时 FailFast 机制
    - 用户请求处理器 ( UserProcessor )
    - 双工通信