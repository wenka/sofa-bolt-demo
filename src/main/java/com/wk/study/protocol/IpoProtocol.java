package com.wk.study.protocol;

import com.alipay.remoting.*;
import com.alipay.remoting.rpc.RpcCommandFactory;
import com.alipay.remoting.rpc.protocol.RpcCommandHandler;
import com.alipay.remoting.rpc.protocol.RpcHeartbeatTrigger;

/**
 * Created with IDEA
 * author:wenka wkwenka@gmail.com
 * Date:2019/10/14  下午 02:32
 * Description:
 */
public class IpoProtocol implements Protocol {
    private CommandEncoder encoder;
    private CommandDecoder decoder;
    private HeartbeatTrigger heartbeatTrigger;
    private CommandHandler commandHandler;
    private CommandFactory commandFactory;


    public IpoProtocol() {
        this.decoder = new IpoCommandDecoder();
        this.encoder = new IpoCommandEncoder();
        this.commandFactory = new RpcCommandFactory();
        this.heartbeatTrigger = new RpcHeartbeatTrigger(this.commandFactory);
        this.commandHandler = new RpcCommandHandler(this.commandFactory);
    }

    /**
     * Get the newEncoder for the protocol.
     *
     * @return
     */
    public CommandEncoder getEncoder() {
        return encoder;
    }

    /**
     * Get the decoder for the protocol.
     *
     * @return
     */
    public CommandDecoder getDecoder() {
        return decoder;
    }

    /**
     * Get the heartbeat trigger for the protocol.
     *
     * @return
     */
    public HeartbeatTrigger getHeartbeatTrigger() {
        return heartbeatTrigger;
    }

    /**
     * Get the command handler for the protocol.
     *
     * @return
     */
    public CommandHandler getCommandHandler() {
        return commandHandler;
    }

    /**
     * Get the command factory for the protocol.
     *
     * @return
     */
    public CommandFactory getCommandFactory() {
        return commandFactory;
    }
}
