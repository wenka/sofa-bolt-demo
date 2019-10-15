package com.wk.study.serializer;

import com.alipay.remoting.DefaultCustomSerializer;
import com.alipay.remoting.InvokeContext;
import com.alipay.remoting.exception.DeserializationException;
import com.alipay.remoting.exception.SerializationException;
import com.alipay.remoting.rpc.RequestCommand;
import com.alipay.remoting.rpc.ResponseCommand;
import com.alipay.remoting.rpc.protocol.RpcRequestCommand;
import com.wk.study.model.ipo.OrderData;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;

/**
 * Created with IDEA
 * author:wenka wkwenka@gmail.com
 * Date:2019/10/14  上午 09:15
 * Description:
 */
public class MySerializer extends DefaultCustomSerializer {


    /**
     * Serialize the header of RequestCommand.
     *
     * @param request
     * @param invokeContext
     * @return
     * @throws SerializationException
     */
    public <T extends RequestCommand> boolean serializeHeader(T request, InvokeContext invokeContext) throws SerializationException {
        return false;
    }

    /**
     * Serialize the header of ResponseCommand.
     *
     * @param response
     * @return
     * @throws SerializationException
     */
    public <T extends ResponseCommand> boolean serializeHeader(T response) throws SerializationException {
        return false;
    }

    /**
     * Deserialize the header of RequestCommand.
     *
     * @param request
     * @return
     * @throws SerializationException
     */
    public <T extends RequestCommand> boolean deserializeHeader(T request) throws DeserializationException {
        return false;
    }

    /**
     * Deserialize the header of ResponseCommand.
     *
     * @param response
     * @param invokeContext
     * @return
     * @throws SerializationException
     */
    public <T extends ResponseCommand> boolean deserializeHeader(T response, InvokeContext invokeContext) throws DeserializationException {
        return false;
    }

    /**
     * Serialize the content of RequestCommand.
     *
     * @param request
     * @param invokeContext
     * @return
     * @throws SerializationException
     */
    public <T extends RequestCommand> boolean serializeContent(T request, InvokeContext invokeContext) throws SerializationException {
        return false;
    }

    /**
     * Serialize the content of ResponseCommand.
     *
     * @param response
     * @return
     * @throws SerializationException
     */
    public <T extends ResponseCommand> boolean serializeContent(T response) throws SerializationException {
        return false;
    }

    /**
     * Deserialize the content of RequestCommand.
     *
     * @param request
     * @return
     * @throws SerializationException
     */
    public <T extends RequestCommand> boolean deserializeContent(T request) throws DeserializationException {
        RpcRequestCommand rpcReq = (RpcRequestCommand) request;
        byte[] content = rpcReq.getContent();

        ByteBuf byteBuf = Unpooled.wrappedBuffer(content);
        CharSequence nonTrdTypCod = byteBuf.readCharSequence(2, Charset.defaultCharset());
        CharSequence invtAcctID = byteBuf.readCharSequence(10, Charset.defaultCharset());
        CharSequence membExcIdCod = byteBuf.readCharSequence(5, Charset.defaultCharset());
        CharSequence ordrNo = byteBuf.readCharSequence(9, Charset.defaultCharset());
        CharSequence dateLstUpdDat = byteBuf.readCharSequence(8, Charset.defaultCharset());
        CharSequence ordrQty = byteBuf.readCharSequence(9, Charset.defaultCharset());
        CharSequence ordrPrc = byteBuf.readCharSequence(8, Charset.defaultCharset());
        CharSequence ordrEntTim = byteBuf.readCharSequence(9, Charset.defaultCharset());
        CharSequence amount = byteBuf.readCharSequence(17, Charset.defaultCharset());
        CharSequence partSubGrpIdCod = byteBuf.readCharSequence(3, Charset.defaultCharset());
        CharSequence partNoTxt = byteBuf.readCharSequence(3, Charset.defaultCharset());
        CharSequence partOsSubGrpIdCod = byteBuf.readCharSequence(3, Charset.defaultCharset());
        CharSequence partOsNoTxt = byteBuf.readCharSequence(3, Charset.defaultCharset());
        CharSequence acctTypCod = byteBuf.readCharSequence(1, Charset.defaultCharset());
        CharSequence brnId = byteBuf.readCharSequence(5, Charset.defaultCharset());
        CharSequence userOrdNum = byteBuf.readCharSequence(16, Charset.defaultCharset());
        CharSequence text = byteBuf.readCharSequence(12, Charset.defaultCharset());
        CharSequence deletedFlag = byteBuf.readCharSequence(1, Charset.defaultCharset());
        CharSequence statusFlag = byteBuf.readCharSequence(1, Charset.defaultCharset());

        OrderData orderData = new OrderData().setNonTrdTypCod(nonTrdTypCod.toString())
                .setInvtAcctID(invtAcctID.toString())
                .setMembExcIdCod(membExcIdCod.toString())
                .setOrdrNo(ordrNo.toString())
                .setDateLstUpdDat(dateLstUpdDat.toString())
                .setOrdrQty(ordrQty.toString())
                .setOrdrPrc(ordrPrc.toString())
                .setOrdrEntTim(ordrEntTim.toString())
                .setAmount(amount.toString())
                .setPartSubGrpIdCod(partSubGrpIdCod.toString())
                .setPartNoTxt(partNoTxt.toString())
                .setPartOsSubGrpIdCod(partOsSubGrpIdCod.toString())
                .setPartOsNoTxt(partOsNoTxt.toString())
                .setAcctTypCod(acctTypCod.toString())
                .setBrnId(brnId.toString())
                .setUserOrdNum(userOrdNum.toString())
                .setText(text.toString())
                .setDeletedFlag(deletedFlag.toString())
                .setStatusFlag(statusFlag.toString());
        rpcReq.setRequestObject(orderData);

        return true;
    }

    /**
     * Deserialize the content of ResponseCommand.
     *
     * @param response
     * @param invokeContext
     * @return
     * @throws SerializationException
     */
    public <T extends ResponseCommand> boolean deserializeContent(T response, InvokeContext invokeContext) throws DeserializationException {
        return false;
    }
}
