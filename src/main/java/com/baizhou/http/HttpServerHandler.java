package com.baizhou.http;

import com.baizhou.http.handler.RequestDispatcher;
import com.baizhou.http.handler.requesthandlers.protohandler.response.HttpResponseTool;
import com.baizhou.manager.WorkingDayManager;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

import java.net.InetAddress;

public class HttpServerHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
//        System.out.println("连接的客户端地址:" + ctx.channel().remoteAddress());
        ctx.writeAndFlush("客户端" + InetAddress.getLocalHost().getHostName() + "成功与服务端建立连接！ ");
        super.channelActive(ctx);
    }

    //收到消息时候, 返回
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        if(!WorkingDayManager.GetInstance().IsTodayWorkingDay()) return;

        if (!(msg instanceof FullHttpRequest)) {
            HttpResponseTool.Getinstance().SendError(ctx, "unknow request! not instance of fullHttpRequest");
            return;
        }

        FullHttpRequest httpRequest = (FullHttpRequest) msg;
        try {
            String path = httpRequest.getUri();//.uri();          //获取路径
            HttpMethod method = httpRequest.getMethod();//.method();//获取请求方法
//            System.out.println("接收到:" + method + " 请求");

            if (HttpMethod.OPTIONS.equals(method) ) {
//                System.out.println("处理OPTIONS" + ctx.channel().remoteAddress());
                FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, Unpooled.copiedBuffer("ok", CharsetUtil.UTF_8));
                response.headers().set(HttpHeaders.Names.CONTENT_TYPE, "text/plain; charset=UTF-8");
                HttpResponseTool.Getinstance().SetCORHeader(response);
                ctx.channel().writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
//                HttpResponseTool.Getinstance().SendString(ctx,"ok", HttpResponseStatus.OK);
                return;
            }

            boolean ret = RequestDispatcher.Getinstance().Dispatch(ctx, method, path, httpRequest.content(), httpRequest);
            if (!ret) {
                HttpResponseTool.Getinstance().SendError(ctx, "no handler for this path");
                return;
            }
        } catch (Exception e) {
            System.out.println("处理请求失败!");
            e.printStackTrace();
            HttpResponseTool.Getinstance().SendError(ctx, e.getMessage());
        } finally {
            //释放请求
            httpRequest.release();
        }
    }

    /**
     * 处理中发生异常, 通知客户端
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
        HttpResponseTool.Getinstance().SendError(ctx,"error during handling");
    }
}