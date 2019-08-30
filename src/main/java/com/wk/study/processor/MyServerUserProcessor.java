package com.wk.study.processor;

import com.alipay.remoting.BizContext;
import com.alipay.remoting.rpc.protocol.SyncUserProcessor;
import com.wk.study.model.MyRequest;
import com.wk.study.model.MyResponse;

/**
 * Created with IDEA
 * author:wenka wkwenka@gmail.com
 * Date:2019/08/30  下午 03:57
 * Description: 自定义业务处理器 (实现用户请求处理器)
 * <p>
 * SyncUserProcessor ：需要在当前处理线程以return返回值的形式返回处理结果
 * AsyncUserProcessor ：有一个 AsyncContext 存根，可以在当前线程，也可以在异步线程，调用 sendResponse 方法返回处理结果
 */
public class MyServerUserProcessor extends SyncUserProcessor<MyRequest> {

    public Object handleRequest(BizContext bizContext, MyRequest myRequest) throws Exception {
        MyResponse myResponse = new MyResponse();
        if (myRequest != null) {
            System.out.println(myRequest);
            myResponse.setResponse("from server --> " + myRequest.getRequest());
        }
        return myResponse;
    }

    /**
     * 指定感兴趣的请求数据类型，该 UserProcessor 只对感兴趣的请求类型的数据进行处理；
     *
     * @return
     */
    public String interest() {
        return MyRequest.class.getName();
    }
}
