package com.baizhou.http.handler.requesthandlers.webMsgHandler;

import com.alibaba.fastjson.JSON;
import com.baizhou.common.WebJSONResult;
import com.baizhou.http.handler.BaseRequestHandler;
import com.baizhou.manager.RankManager;
import com.baizhou.manager.UniMajorMgrInMem;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.QueryStringDecoder;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

public class getUpdateYFYDHandler  extends BaseRequestHandler {
    @Override
    public void DoHandling(ChannelHandlerContext ctx, HttpMethod method, String path, ByteBuf body, FullHttpRequest request) {

        Map<String, String> parmMap = new HashMap<>();
        // 是GET请求
        QueryStringDecoder decoder = new QueryStringDecoder(request.getUri());
        decoder.parameters().entrySet().forEach(entry -> {
            // entry.getValue()是一个List, 只取第一个元素
            parmMap.put(entry.getKey().trim(), entry.getValue().get(0).trim());
        });


        String errorMsg = "";
        String url = "";
        String examYear = parmMap.get("year");

        System.out.println(" update new YFYD" + examYear);

        if(!StringUtils.isEmpty(examYear)){
//            UniMajorMgrInMem.GetInstance().RefreshAfterDatatUpdate(examYear);
            RankManager.GetInstance().UpdateYearData(examYear);
        }



        Send(ctx, JSON.toJSONString(errorMsg.isEmpty() ? WebJSONResult.ok(url) : WebJSONResult.errorMsg(errorMsg)), HttpResponseStatus.OK);
    }
}
