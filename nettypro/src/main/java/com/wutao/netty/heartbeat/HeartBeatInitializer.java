package com.wutao.netty.heartbeat;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

/**
 * @author wutao
 * @date 2019/12/9
 */
public class HeartBeatInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast("decoder",new StringDecoder());
        pipeline.addLast("encoder",new StringEncoder());
        //添加心跳检测handler,参数依次是读空闲时间阈值,写空闲时间阈值,读写空闲时间阈值
        pipeline.addLast(new IdleStateHandler(2,2,2, TimeUnit.SECONDS));
        pipeline.addLast(new HeartBeatHandler());
    }
}
