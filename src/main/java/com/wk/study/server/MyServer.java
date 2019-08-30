package com.wk.study.server;

import com.alipay.remoting.rpc.RpcServer;
import com.wk.study.processor.MyServerUserProcessor;

/**
 * Created with IDEA
 * author:wenka wkwenka@gmail.com
 * Date:2019/08/30  下午 04:05
 * Description: 服务端
 */
public class MyServer {

    private final static int PORT = 8888;

    public static boolean start() {

        /**
         * 创建 RpcServer 实例，指定监听端口
         */
        RpcServer rpcServer = new RpcServer(PORT);

        /**
         * 注册用户请求处理器
         */
        rpcServer.registerUserProcessor(new MyServerUserProcessor());

        // 启动
        return rpcServer.start();
    }

    public static void main(String[] args) {
        boolean start = MyServer.start();
        if (start) {
            System.out.println("服务启动成功！");
        } else {
            System.err.println("服务启动失败！");
        }
    }
}
