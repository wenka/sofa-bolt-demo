package com.wk.study.server;

import com.alipay.remoting.BizContext;
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
 * Date:2019/09/02  下午 02:06
 * Description: 双工通信服务器端
 */
public class MyServer2 {

    public static void main(String[] args) throws InterruptedException, RemotingException {
        RpcServer rpcServer = new RpcServer(8888);
        MyServerUserProcessor2 myServerUserProcessor2 = new MyServerUserProcessor2();
        rpcServer.registerUserProcessor(myServerUserProcessor2);

        // 开启服务器端连接管理功能
        rpcServer.switches().turnOn(GlobalSwitch.SERVER_MANAGE_CONNECTION_SWITCH);

        if (rpcServer.start()) {
            System.out.println("服务器端启动成功！");
            Thread.sleep(1000);
            MyRequest myRequest = new MyRequest();
            Scanner scanner = new Scanner(System.in);
            while (scanner.hasNextLine()) {
                String s = scanner.nextLine();
                if (s != null && s.trim().length() > 0) {
                    myRequest.setRequest(s);
                    Object o = rpcServer.invokeSync(myServerUserProcessor2.getRemoteAddr(), myRequest, 10 * 1000);
                    System.out.println(o);
                }
            }
        } else {
            System.out.println("服务器启动失败！");
        }

    }

    static class MyServerUserProcessor2 extends SyncUserProcessor<MyRequest> {

        private String remoteAddr;

        public Object handleRequest(BizContext bizContext, MyRequest myRequest) throws Exception {
            this.remoteAddr = bizContext.getRemoteAddress();
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

        public String getRemoteAddr() {
            return remoteAddr;
        }
    }
}
