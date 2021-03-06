package com.wutao.netty.codec.decode;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * @author wutao
 * @email wutao56789@gmail.com
 * @date 2019/12/15 22:14
 */
public class ToIntegerDecoder extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        if(byteBuf.readableBytes() >= 4){
            list.add(byteBuf.readInt());
        }
    }
}
