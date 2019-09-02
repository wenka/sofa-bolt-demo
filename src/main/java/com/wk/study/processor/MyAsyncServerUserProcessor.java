package com.wk.study.processor;

import com.alipay.remoting.AsyncContext;
import com.alipay.remoting.BizContext;
import com.alipay.remoting.rpc.protocol.AsyncUserProcessor;
import com.wk.study.model.MyRequest;
import com.wk.study.model.MyResponse;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created with IDEA
 * author:wenka wkwenka@gmail.com
 * Date:2019/09/02  上午 09:58
 * Description: 异步自定义业务处理器:使用多线程(线程池)调用
 */
public class MyAsyncServerUserProcessor extends AsyncUserProcessor<MyRequest> {

    private final static Executor executor = Executors.newCachedThreadPool();

    public void handleRequest(BizContext bizContext, AsyncContext asyncContext, MyRequest myRequest) {
        executor.execute(new AsyncTask(bizContext, asyncContext, myRequest));
    }

    public String interest() {
        return MyRequest.class.getName();
    }

    class AsyncTask implements Runnable {

        private BizContext bizContext;

        private AsyncContext asyncContext;

        private MyRequest myRequest;

        public AsyncTask(BizContext bizContext, AsyncContext asyncContext, MyRequest myRequest) {
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
                System.out.println(bizContext.getRemoteAddress() + bizContext.getRemotePort());
                myResponse.setResponse("from server --> " + myRequest.getRequest());
            }
            asyncContext.sendResponse(myResponse);
        }
    }
}
