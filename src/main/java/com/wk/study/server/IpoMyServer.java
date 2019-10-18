package com.wk.study.server;

import com.alipay.remoting.*;
import com.alipay.remoting.rpc.RpcServer;
import com.alipay.remoting.rpc.protocol.AsyncUserProcessor;
import com.alipay.remoting.rpc.protocol.SyncUserProcessor;
import com.wk.study.model.MyRequest;
import com.wk.study.model.MyResponse;
import com.wk.study.model.ipo.OrderData;
import com.wk.study.protocol.IpoProtocol;
import com.wk.study.serializer.MySerializer;
import com.wk.study.util.RabbitmqUtil;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created with IDEA
 * author:wenka wkwenka@gmail.com
 * Date:2019/10/14  上午 10:02
 * Description:
 */
public class IpoMyServer {

    public static void main(String[] args) throws InterruptedException {
        ProtocolManager.registerProtocol(new IpoProtocol(), (byte) -59);
        CustomSerializerManager.registerCustomSerializer(OrderData.class.getName(), new MySerializer());
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

/**
 * 异步业务处理器
 */
class IpoServerUserProcessor extends AsyncUserProcessor<Object> {
    private static AtomicInteger MSG_COUNT = new AtomicInteger(0);
    private static volatile long time = 0;
    private final static Executor executor = Executors.newCachedThreadPool();

    public void handleRequest(BizContext bizContext, AsyncContext asyncContext, Object myRequest) {
        executor.execute(new IpoServerUserProcessor.AsyncTask(bizContext, asyncContext, myRequest));
    }

    public String interest() {
        return OrderData.class.getName();
    }

    class AsyncTask implements Runnable {

        private BizContext bizContext;

        private AsyncContext asyncContext;

        private Object myRequest;

        public AsyncTask(BizContext bizContext, AsyncContext asyncContext, Object myRequest) {
            this.bizContext = bizContext;
            this.asyncContext = asyncContext;
            this.myRequest = myRequest;
        }

        /**
         * @see Thread#run()
         */
        public void run() {
            MyResponse myResponse = new MyResponse();
            if (myRequest != null) {
                if (MSG_COUNT.get() == 0) {
                    time = System.currentTimeMillis();
                }
                if (MSG_COUNT.incrementAndGet() % 50000 == 0) {
                    long l = System.currentTimeMillis();
                    System.out.println("OrderData 请求[" + (MSG_COUNT.get()) + "] 耗时：" + (l - time));
                    time = l;
                }
                RabbitmqUtil.sendMessage(myRequest);
                myResponse.setResponse("SUCCESS");
            }
            asyncContext.sendResponse(myResponse);
        }
    }
}

/**
 * 同步业务处理器
 */
class IpoSycnServerUserProcessor extends SyncUserProcessor<Object> {
    private static AtomicInteger MSG_COUNT = new AtomicInteger(0);
    private static volatile long time = 0;

    @Override
    public BizContext preHandleRequest(RemotingContext remotingCtx, Object request) {
        return super.preHandleRequest(remotingCtx, request);
    }

    public Object handleRequest(BizContext bizContext, Object myRequest) throws Exception {
        MyResponse myResponse = new MyResponse();
        if (myRequest != null) {
            if (MSG_COUNT.get() == 0) {
                time = System.currentTimeMillis();
            }
            if (MSG_COUNT.incrementAndGet() % 100000 == 0) {
                long l = System.currentTimeMillis();
                System.out.println("OrderData 请求[" + (MSG_COUNT.get()) + "] 耗时：" + (l - time));
                time = l;
            }
            RabbitmqUtil.sendMessage(myRequest);
            myResponse.setResponse("SUCCESS");
        }
        return myResponse;
    }

    /**
     * 指定感兴趣的请求数据类型，该 UserProcessor 只对感兴趣的请求类型的数据进行处理；
     *
     * @return
     */
    public String interest() {
        return OrderData.class.getName();
    }

    /**
     * 默认情况下，我们使用最佳实践的线程模型来处理请求，即尽可能少的占用 IO 线程，
     * 但有一些场景，比如计算过程非常简单，希望减少线程切换，尽可能大的增加 IO 吞吐量的场景。
     * 此时我们提供了一个开关，来让业务处理也在 IO 线程执行。
     *
     * @return
     */
    @Override
    public boolean processInIOThread() {
        return true;
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