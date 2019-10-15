package com.wk.study.server;

import com.alipay.remoting.*;
import com.alipay.remoting.exception.RemotingException;
import com.alipay.remoting.rpc.RpcServer;
import com.alipay.remoting.rpc.protocol.SyncMutiInterestUserProcessor;
import com.wk.study.model.MyRequest;
import com.wk.study.model.MyResponse;
import com.wk.study.model.ipo.OrderData;
import com.wk.study.protocol.IpoProtocol;
import com.wk.study.serializer.MySerializer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IDEA
 * author:wenka wkwenka@gmail.com
 * Date:2019/10/14  上午 10:02
 * Description:
 */
public class IpoServer {

    public static void main(String[] args) throws InterruptedException, RemotingException {
        ProtocolManager.registerProtocol(new IpoProtocol(), (byte) -59);
        CustomSerializerManager.registerCustomSerializer(OrderData.class.getName(),new MySerializer());
        RpcServer rpcServer = new RpcServer(8888);

        rpcServer.registerUserProcessor(new IpoServerUserProcessor());

        IpoCONNECTEventProcessor ipoCONNECTEventProcessor = new IpoCONNECTEventProcessor();
        rpcServer.addConnectionEventProcessor(ConnectionEventType.CONNECT, ipoCONNECTEventProcessor);

        if (rpcServer.start()) {
            System.out.println("服务器端启动成功！");
            Thread.sleep(1000);
        } else {
            System.out.println("服务器启动失败！");
        }
    }
}

class IpoServerUserProcessor extends SyncMutiInterestUserProcessor {
    public Object handleRequest(BizContext bizContext, Object myRequest) throws Exception {
        MyResponse myResponse = null;
        if (myRequest instanceof MyRequest) {
            System.out.println("MyRequest 请求：" + myRequest);
            myResponse = new MyResponse("接收到 MyRequest 类型请求");
        }
        if (myRequest instanceof String) {
            System.out.println("String 请求：" + myRequest + "，长度：" + myRequest.toString().length());
            myResponse = new MyResponse("接收到 String 类型请求");
        }

        if (myRequest instanceof OrderData) {
            System.out.println("OrderData 请求：" + myRequest);
//            return "SUCCESS";
            return null;
        }
        return myResponse;
    }

    public List<String> multiInterest() {
        List<String> interestList = new ArrayList<String>(2);
        interestList.add(MyRequest.class.getName());
        interestList.add(String.class.getName());
        interestList.add(OrderData.class.getName());
        return interestList;
    }
}

/**
 * 连接事件处理器
 */
class IpoCONNECTEventProcessor implements ConnectionEventProcessor {
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