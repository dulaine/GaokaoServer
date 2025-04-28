package com.baizhou.http.handler.requesthandlers.webMsgHandler;

import com.alibaba.fastjson.JSON;
import com.baizhou.common.WebJSONResult;
import com.baizhou.http.handler.BaseRequestHandler;
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

public class getUpdateNewDBHandler  extends BaseRequestHandler {
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

        System.out.println(" update new DB" + examYear);

        if(!StringUtils.isEmpty(examYear)){
            UniMajorMgrInMem.GetInstance().RefreshAfterDatatUpdate(examYear);
        }


//
//        byte[] contentBytes = new byte[body.readableBytes()];
//        body.readBytes(contentBytes, 0, contentBytes.length);
//        String data = ByteBufferUtil.getString(contentBytes);
//        JSONObject notifyModel = JSON.parseObject(data);
//        int score = notifyModel.getInteger("score");
//        int id = notifyModel.getInteger("id");
//        System.out.println("user score id "+ id +" score: "+ score);
//        //更新拼贴实验分数
//        UsersResVO vo = PlayerManager.GetInstance().GetUserTableInfo(id);
//        vo.setTaskName("");//更新拼图任务
//        vo.setExpSubmiteCount(vo.getExpSubmiteCount() + 1);//提交任务次数
//        //完成任务获取的分数
//        PlayerManager.GetInstance().UpdateModelInfoScore(vo, score, vo.getExprimentScore());

        Send(ctx, JSON.toJSONString(errorMsg.isEmpty() ? WebJSONResult.ok(url) : WebJSONResult.errorMsg(errorMsg)), HttpResponseStatus.OK);
    }
}
