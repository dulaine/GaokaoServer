package com.baizhou.http;

import com.baizhou.http.HttpServerHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;


public class HttpServerFilter extends ChannelInitializer<SocketChannel>{
    @Override
    protected void initChannel(SocketChannel socketChannel) {
        socketChannel.pipeline().addLast("encoder",new HttpResponseEncoder());
        socketChannel.pipeline().addLast("decoder",new HttpRequestDecoder());
        socketChannel.pipeline().addLast("aggregator", new HttpObjectAggregator(220*1024*1024));
        socketChannel.pipeline().addLast("handler", new HttpServerHandler());// 服务端业务逻辑
    }
}