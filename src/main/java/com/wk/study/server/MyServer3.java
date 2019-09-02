package com.wk.study.server;

import com.alipay.remoting.BizContext;
import com.alipay.remoting.Connection;
import com.alipay.remoting.ConnectionEventProcessor;
import com.alipay.remoting.ConnectionEventType;
import com.alipay.remoting.config.switches.GlobalSwitch;
import com.alipay.remoting.exception.RemotingException;
import com.alipay.remoting.rpc.RpcServer;
import com.alipay.remoting.rpc.protocol.SyncUserProcessor;
import com.wk.study.model.MyRequest;
import com.wk.study.model.MyResponse;

import java.util.Scanner;

/**
 * Created with IDEA
 * author:wenka wkwenka@gmail.com
 * Date:2019/09/02  下午 02:27
 * Description: 双工通信服务器端 基于 connection 链路模式
 */
public class MyServer3 {

    public static void main(String[] args) throws InterruptedException, RemotingException {
        RpcServer rpcServer = new RpcServer(8888);
        MyServerUserProcessor myServerUserProcessor = new MyServerUserProcessor();
        rpcServer.registerUserProcessor(myServerUserProcessor);

        // 创建并注册 ConnectionEventType.CONNECT 连接事件处理器
        MyCONNECTEventProcessor myCONNECTEventProcessor = new MyCONNECTEventProcessor();
        rpcServer.addConnectionEventProcessor(ConnectionEventType.CONNECT, myCONNECTEventProcessor);

        if (rpcServer.start()) {
            System.out.println("服务器端启动成功！");
            Thread.sleep(1000);
            MyRequest myRequest = new MyRequest();
            Scanner scanner = new Scanner(System.in);
            while (scanner.hasNextLine()) {
                String s = scanner.nextLine();
                if (s != null && s.trim().length() > 0) {
                    myRequest.setRequest(s);
                    Object o = rpcServer.invokeSync(myCONNECTEventProcessor.getConnection(), myRequest, 10 * 1000);
                    System.out.println(o);
                }
            }
        } else {
            System.out.println("服务器启动失败！");
        }

    }

    static class MyServerUserProcessor extends SyncUserProcessor<MyRequest> {

        public Object handleRequest(BizContext bizContext, MyRequest myRequest) throws Exception {
            MyResponse myResponse = new MyResponse();
            if (myRequest != null) {
                System.out.println("接受到的请求：" + myRequest);
                myResponse.setResponse("我是服务器端，已收到请求。");
            }
            return myResponse;
        }

        public String interest() {
            return MyRequest.class.getName();
        }
    }

    /**
     * 连接事件处理器
     */
    static class MyCONNECTEventProcessor implements ConnectionEventProcessor {
        // 存储连接，用于服务端向客户端发起远程通信
        private Connection connection;

        public void onEvent(String remoteAddr, Connection connection) {
            System.out.println("有新连接加入：" + remoteAddr);
            this.connection = connection;
        }

        public Connection getConnection() {
            return connection;
        }
    }
}
