package com.wk.study.client;

import com.alipay.remoting.exception.RemotingException;
import com.alipay.remoting.rpc.RpcClient;
import com.wk.study.model.MyRequest;

import java.util.Scanner;

/**
 * Created with IDEA
 * author:wenka wkwenka@gmail.com
 * Date:2019/08/30  下午 04:13
 * Description:
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
                System.out.println("服务器返回------> " + o);
            }
        }
    }
}
