package com.wk.study.protocol;

import com.alipay.remoting.CommandDecoder;
import com.alipay.remoting.rpc.ResponseCommand;
import com.alipay.remoting.rpc.protocol.RpcCommandCode;
import com.alipay.remoting.rpc.protocol.RpcProtocol;
import com.alipay.remoting.rpc.protocol.RpcRequestCommand;
import com.alipay.remoting.rpc.protocol.RpcResponseCommand;
import com.wk.study.model.ipo.OrderData;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created with IDEA
 * author:wenka wkwenka@gmail.com
 * Date:2019/10/14  下午 03:01
 * Description: 消息解码器
 */
public class IpoCommandDecoder implements CommandDecoder {

    private static AtomicInteger MSG_COUNT = new AtomicInteger(0);


    /**
     * Decode bytes into object.
     *
     * @param ctx
     * @param in
     * @param out
     * @throws Exception
     */
    public void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        while (in.readableBytes() >= 197) {
//            System.out.println("第" + (MSG_COUNT.incrementAndGet()) + "条消息");
            in.resetReaderIndex();

            byte[] archHdrTBytes = new byte[32];
            in.readBytes(archHdrTBytes);

            byte[] reqRspHdrTBytes = new byte[40];
            in.readBytes(reqRspHdrTBytes);

            byte[] data = new byte[125];
            in.readBytes(data);

            byte[] heads = new byte[archHdrTBytes.length + reqRspHdrTBytes.length];
            System.arraycopy(archHdrTBytes, 0, heads, 0, archHdrTBytes.length);
            System.arraycopy(reqRspHdrTBytes, 0, heads, archHdrTBytes.length, reqRspHdrTBytes.length);

            RpcRequestCommand requestCommand = createRequestCommand();
            requestCommand.setHeader(heads);
            requestCommand.setContent(data);
            requestCommand.setClazz(OrderData.class.getName().getBytes());
            requestCommand.setRequestClass(OrderData.class.getName());
            out.add(requestCommand);
            in.markReaderIndex();
        }
    }

    private RpcRequestCommand createRequestCommand() {
        RpcRequestCommand command = new RpcRequestCommand();
        command.setArriveTime(System.currentTimeMillis());
        return command;
    }

    private ResponseCommand createResponseCommand(short cmdCode) {
        ResponseCommand command = new RpcResponseCommand();
        command.setCmdCode(RpcCommandCode.valueOf(cmdCode));
        return command;
    }
}
