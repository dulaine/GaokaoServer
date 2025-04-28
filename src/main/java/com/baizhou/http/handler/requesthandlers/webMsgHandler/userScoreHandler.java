package com.baizhou.http.handler.requesthandlers.webMsgHandler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baizhou.common.WebJSONResult;
import com.baizhou.http.handler.BaseRequestHandler;
import com.baizhou.util.ByteBufferUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpResponseStatus;


public class userScoreHandler extends BaseRequestHandler {
    @Override
    public void DoHandling(ChannelHandlerContext ctx, HttpMethod method, String path, ByteBuf body, FullHttpRequest request) {

        String errorMsg = "";
        String url = "";

        byte[] contentBytes = new byte[body.readableBytes()];
        body.readBytes(contentBytes, 0, contentBytes.length);
        String data = ByteBufferUtil.getString(contentBytes);
        JSONObject notifyModel = JSON.parseObject(data);
        int score = notifyModel.getInteger("score");
        int id = notifyModel.getInteger("id");
        System.out.println("user score id "+ id +" score: "+ score);
//        //更新拼贴实验分数
//        UsersResVO vo = PlayerManager.GetInstance().GetUserTableInfo(id);
//        vo.setTaskName("");//更新拼图任务
//        vo.setExpSubmiteCount(vo.getExpSubmiteCount() + 1);//提交任务次数
//        //完成任务获取的分数
//        PlayerManager.GetInstance().UpdateModelInfoScore(vo, score, vo.getExprimentScore());

        Send(ctx, JSON.toJSONString(errorMsg.isEmpty() ? WebJSONResult.ok(url) : WebJSONResult.errorMsg(errorMsg)), HttpResponseStatus.OK);
    }
}
