package com.wutao.netty.rpc.provider;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

/**
 * @author wutao
 * @date 2019/12/11
 */
public class NettyServer {

    public static void startServer(String hostname,int port){
        startServer0(hostname,port);
    }

    private static void startServer0(String hostname,int port){
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup,workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {

                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            pipeline.addLast(new StringDecoder());
                            pipeline.addLast(new StringEncoder());
                            pipeline.addLast(new HelloServerHanler());
                        }
                    });
            serverBootstrap.bind(hostname,port).sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        finally {
//            bossGroup.shutdownGracefully();
//            workerGroup.shutdownGracefully();
//        }
    }



}
