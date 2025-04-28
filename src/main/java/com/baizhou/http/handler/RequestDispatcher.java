package com.baizhou.http.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpMethod;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

public class RequestDispatcher {

    private static RequestDispatcher _instance;

    public static RequestDispatcher Getinstance(){
        if( _instance == null){
            _instance = new RequestDispatcher();
        }
        return _instance;
    }

    private ConcurrentHashMap<String, BaseRequestHandler> handlerMap = new ConcurrentHashMap();
    /**
     *根据path分类处理
     * @param ctx
     * @param method
     * @param path
     * @param body
     * @return true成功, false失败
     */
    public boolean Dispatch(ChannelHandlerContext ctx, HttpMethod method, String path, ByteBuf body, FullHttpRequest request) throws InterruptedException, InvalidFormatException, IOException {
        int paramIndex = path.indexOf("?");
        if(paramIndex >= 0){
            path = path.substring(0, paramIndex);
        }

        BaseRequestHandler baseRequestHandler = this.handlerMap.get(path);
        if(baseRequestHandler == null){
            baseRequestHandler = RequestHandlerFactory.CreateHandler(path);
            if(baseRequestHandler == null) return false;
            this.handlerMap.put(path, baseRequestHandler);
        }

        baseRequestHandler.DoHandling(ctx, method, path, body, request );
        return true;
    }
}
