package com.wk.study.client;

import com.alipay.remoting.BizContext;
import com.alipay.remoting.exception.RemotingException;
import com.alipay.remoting.rpc.RpcClient;
import com.alipay.remoting.rpc.protocol.SyncUserProcessor;
import com.wk.study.model.MyRequest;
import com.wk.study.model.MyResponse;

import java.util.Scanner;
import java.util.concurrent.CountDownLatch;

/**
 * Created with IDEA
 * author:wenka wkwenka@gmail.com
 * Date:2019/09/02  下午 02:13
 * Description:双工通信 客户端
 */
public class MyClient2 {

    private static CountDownLatch latch = new CountDownLatch(1);

    public static void main(String[] args) throws RemotingException, InterruptedException {
        RpcClient rpcClient = new RpcClient();
        rpcClient.registerUserProcessor(new MyServerUserProcessor2());
        rpcClient.init();

        MyRequest myRequest = new MyRequest();
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            String s = scanner.nextLine();
            if (s != null && s.trim().length() > 0) {
                myRequest.setRequest(s);
                MyResponse response = (MyResponse) rpcClient.invokeSync("127.0.0.1:8888", myRequest, 300 * 1000);
                System.out.println(response);
            }
        }
        latch.await();
    }

    static class MyServerUserProcessor2 extends SyncUserProcessor<MyRequest> {

        public Object handleRequest(BizContext bizContext, MyRequest myRequest) throws Exception {
            MyResponse myResponse = new MyResponse();
            if (myRequest != null) {
                System.out.println("接受到的请求：" + myRequest);
                myResponse.setResponse("我是客户端端，已收到请求。");
            }
            return myResponse;
        }

        public String interest() {
            return MyRequest.class.getName();
        }
    }
}
