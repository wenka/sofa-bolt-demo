package com.wk.study.protocol;

import com.alipay.remoting.CommandEncoder;
import com.alipay.remoting.rpc.ResponseCommand;
import com.alipay.remoting.rpc.RpcCommand;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

import java.io.Serializable;

/**
 * Created with IDEA
 * author:wenka wkwenka@gmail.com
 * Date:2019/10/16  上午 09:57
 * Description: 消息编码器
 */
public class IpoCommandEncoder implements CommandEncoder {
    /**
     * Encode object into bytes.
     *
     * @param ctx
     * @param msg
     * @param out
     * @throws Exception
     */
    @Override
    public void encode(ChannelHandlerContext ctx, Serializable msg, ByteBuf out) throws Exception {
        if (msg instanceof RpcCommand) {
            RpcCommand cmd = (RpcCommand) msg;
            out.writeBytes(cmd.getContent());
        }
    }
}
