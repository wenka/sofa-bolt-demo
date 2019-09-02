package com.wk.study.client;

import com.alipay.remoting.InvokeCallback;
import com.alipay.remoting.exception.RemotingException;
import com.alipay.remoting.rpc.RpcClient;
import com.alipay.remoting.rpc.RpcResponseFuture;
import com.wk.study.model.MyRequest;

import java.util.Scanner;
import java.util.concurrent.Executor;

/**
 * Created with IDEA
 * author:wenka wkwenka@gmail.com
 * Date:2019/08/30  下午 04:13
 * Description:
 * future 与 sync 只在“发出请求后是否阻塞等待”处不同
 */
public class MyClient {

    private static RpcClient rpcClient;

    public static void start() {
        rpcClient = new RpcClient();
        rpcClient.init();
    }

    public static void main(String[] args) throws RemotingException, InterruptedException {
        MyClient.start();

        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            String s = scanner.nextLine();
            if (s != null && s.trim().length() > 0) {
                MyRequest myRequest = new MyRequest();
                myRequest.setRequest(s);
                /**
                 * 1、获取或者创建连接（与netty服务端进行连接），Bolt连接的创建是延迟到第一次调用进行的
                 * 2、向服务端发起同步调用（四种调用方式中最常用的一种）
                 */
                Object o = rpcClient.invokeSync("127.0.0.1:8888", myRequest, 30 * 1000);

//                rpcClient.oneway("127.0.0.1:8888",myRequest); //单向通信

                /**
                 * Future异步通信
                 * 参数三：  io.netty.util.TimerTask 延迟启动时间
                 * 阻塞等待的超时时间： future.get( int timeoutMillis) 中进行设置的。
                 */
//                RpcResponseFuture rpcResponseFuture = rpcClient.invokeWithFuture("127.0.0.1:8888", myRequest, 30 * 1000);
//                Object o1 = rpcResponseFuture.get();

                /**
                 * Callback 异步通信
                 */
//                rpcClient.invokeWithCallback("127.0.0.1:8888", myRequest, new InvokeCallback() {
//                    public void onResponse(Object response) {
//                        System.out.println("回调成功：" + response);
//                    }
//
//                    public void onException(Throwable throwable) {
//                        System.out.println("发生异常...");
//                        throwable.printStackTrace();
//                    }
//
//                    public Executor getExecutor() {
//                        return null;
//                    }
//                }, 30 * 1000);

                System.out.println("服务器返回------> " + o);
            }
        }
    }
}
