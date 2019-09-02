package com.wk.study.processor;

import com.alipay.remoting.BizContext;
import com.alipay.remoting.rpc.protocol.SyncMutiInterestUserProcessor;
import com.wk.study.model.MyRequest;
import com.wk.study.model.MyResponse;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IDEA
 * author:wenka wkwenka@gmail.com
 * Date:2019/09/02  上午 10:05
 * Description: 同步多 insterest 用户处理器
 */
public class MyMultiInsterestServerUserProcessor extends SyncMutiInterestUserProcessor {
    public Object handleRequest(BizContext bizContext, Object myRequest) throws Exception {
        MyResponse myResponse = null;
        if (myRequest instanceof MyRequest) {
            System.out.println("MyRequest 请求：" + myRequest);
            myResponse = new MyResponse("接收到 MyRequest 类型请求");
        }
        if (myRequest instanceof String) {
            System.out.println("String 请求：" + myRequest);
            myResponse = new MyResponse("接收到 String 类型请求");
        }
        return myResponse;
    }

    public List<String> multiInterest() {
        List<String> interestList = new ArrayList<String>(2);
        interestList.add(MyRequest.class.getName());
        interestList.add(String.class.getName());
        return interestList;
    }
}
