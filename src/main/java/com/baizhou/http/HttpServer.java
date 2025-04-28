package com.baizhou.http;

import com.baizhou.data.enumdefine.EnumStringDefine;
import com.baizhou.manager.GameDataManager;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.springframework.stereotype.Component;

@Component("HttpServer")
public class HttpServer  implements Runnable {
    public void run(){

        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workGroup = new NioEventLoopGroup();

        try {

            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workGroup);
            b.channel(NioServerSocketChannel.class);
            b.childHandler(new HttpServerFilter());

            int port = Integer.parseInt(GameDataManager.GetInstance().GetStringDefine(EnumStringDefine.NettyServerPort.getStateID()));
            System.out.println("服务端开启等待客户端连接... ..." + port);

            Channel ch = b.bind(port).sync().channel();

            ch.closeFuture().sync();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }
    }
}
