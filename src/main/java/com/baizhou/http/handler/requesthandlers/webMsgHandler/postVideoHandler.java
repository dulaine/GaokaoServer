package com.baizhou.http.handler.requesthandlers.webMsgHandler;

import com.alibaba.fastjson.JSON;
import com.baizhou.common.WebJSONResult;
import com.baizhou.data.enumdefine.EnumErrorMsg;
import com.baizhou.data.enumdefine.EnumStringDefine;
import com.baizhou.http.handler.BaseRequestHandler;
import com.baizhou.manager.GameDataManager;
import com.baizhou.manager.PlayerManager;
import com.baizhou.util.FileUtils;
import com.baizhou.util.OssFileUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.multipart.FileUpload;
import io.netty.handler.codec.http.multipart.HttpPostRequestDecoder;
import io.netty.handler.codec.http.multipart.InterfaceHttpData;

import java.io.File;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.List;
import java.util.Random;

public class postVideoHandler extends BaseRequestHandler {
    @Override
    public void DoHandling(ChannelHandlerContext ctx, HttpMethod method, String path, ByteBuf body, FullHttpRequest request) {

        String errorMsg = "";
        String url = "";

        HttpPostRequestDecoder decoder = new HttpPostRequestDecoder(request);
        decoder.offer(request);

        // multipart/form-data  文件上传
        List<InterfaceHttpData> parmList = decoder.getBodyHttpDatas();
        for (InterfaceHttpData parm : parmList) {
            if (parm.getHttpDataType() == InterfaceHttpData.HttpDataType.FileUpload) {
                FileUpload fileUpload = (FileUpload) parm;
                String fileName = fileUpload.getFilename();
                if (fileUpload.isCompleted()) {
                    //                  //保存到磁盘
//                    StringBuffer fileNameBuf = new StringBuffer();
//                    fileNameBuf.append(DiskFileUpload.baseDirectory).append(fileName);
                    String uid = "upload_" + new Random().nextLong() + "_" + fileName;//      + CommonUtil.FormatFileDate(new Date()) + "_" + new Random().nextLong();
                    String imgPath = GameDataManager.GetInstance().GetStringDefine(EnumStringDefine.VIDEO_PATH.getStateID());
                    FileUtils.mkdir(imgPath);
                    //组成文件名
                    String imgFileName = uid;//+ ".jpg";
                    String fileFullName = MessageFormat.format("{0}/{1}", imgPath, imgFileName);

                    boolean local = false;
                    if (local) {
                        try {
                            fileUpload.renameTo(new File(fileFullName));//(fileNameBuf.toString()));
                            String imgUrlPrefix = GameDataManager.GetInstance().GetStringDefine(EnumStringDefine.VIDEO_PATH_URL_PREFIX.getStateID());
                            url = MessageFormat.format("{0}/{1}", imgUrlPrefix, imgFileName);
//                            PlayerManager.GetInstance().UpdateVideoUrl(url);
                        } catch (IOException e) {
                            e.printStackTrace();
                            errorMsg = GameDataManager.GetInstance().GetErrorMsg(EnumErrorMsg.SaveImageFail);
                        }
                    } else {
                        url = OssFileUtil.UploadIFile(fileUpload, "citydesignv2/video/" + imgFileName);
//                        PlayerManager.GetInstance().UpdateVideoUrl(url);
                    }

                }
            }
        }

//        byte[] contentBytes = new byte[body.readableBytes()];
//        body.readBytes(contentBytes, 0, contentBytes.length);

//        String savedContentImgName = CommonUtil.SaveNewsImg(contentBytes, "postImg_");
//        if (!savedContentImgName.isEmpty()) {
//            String imgUrlPrefix = GameDataManager.GetInstance().GetStringDefine(EnumStringDefine.IMG_PATH_URL_PREFIX.getStateID());
//            url = MessageFormat.format("{0}/{1}", imgUrlPrefix, savedContentImgName);
////            Send(ctx, JSON.toJSONString(WebJSONResult.ok(url)), HttpResponseStatus.OK);
//        } else {
//            //msg = EnumErrorMsg.SaveNewsImageFail;
//            errorMsg = GameDataManager.GetInstance().GetErrorMsg(EnumErrorMsg.SaveNewsImageFail);
//        }

        Send(ctx, JSON.toJSONString(errorMsg.isEmpty() ? WebJSONResult.ok(url) : WebJSONResult.errorMsg(errorMsg)), HttpResponseStatus.OK);
    }
}
