package com.baizhou.http.handler.requesthandlers.webMsgHandler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baizhou.common.WebJSONResult;
import com.baizhou.http.handler.BaseRequestHandler;
import com.baizhou.util.ByteBufferUtil;
import com.baizhou.util.OssFileUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpResponseStatus;
import sun.misc.BASE64Decoder;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Random;


public class userImgHandler extends BaseRequestHandler {
    @Override
    public void DoHandling(ChannelHandlerContext ctx, HttpMethod method, String path, ByteBuf body, FullHttpRequest request) {

        String errorMsg = "";
        String url = "";

        if (!method.equals(HttpMethod.POST)) {
            errorMsg = "只接受Post请求";
            Send(ctx, JSON.toJSONString(errorMsg.isEmpty() ? WebJSONResult.ok(url) : WebJSONResult.errorMsg(errorMsg)), HttpResponseStatus.OK);
            return;
        }

//        HttpPostRequestDecoder decoder = new HttpPostRequestDecoder(request);
//        decoder.offer(request);
//
//        // multipart/form-data  文件上传
//        List<InterfaceHttpData> parmList = decoder.getBodyHttpDatas();

        try {
            byte[] contentBytes = new byte[body.readableBytes()];
            body.readBytes(contentBytes, 0, contentBytes.length);
            String data = ByteBufferUtil.getString(contentBytes);
            JSONObject notifyModel = JSON.parseObject(data);
            String base64 = notifyModel.getString("data");
            int id = notifyModel.getInteger("id");
            System.out.println("user imsge id " + id + " base64: " + base64.length());

            if (base64.isEmpty()) {
                //超过保存上限
                errorMsg = "base64不能为空";
                Send(ctx, JSON.toJSONString(errorMsg.isEmpty() ? WebJSONResult.ok(url) : WebJSONResult.errorMsg(errorMsg)), HttpResponseStatus.OK);
                return;
            }

            //去除前缀
            int index = base64.indexOf("base64,");
            if (index >= 0) base64 = base64.substring(index + "base64,".length());

//            ModelExperimentInfoVO modelExperimentInfoVO = PlayerManager.GetInstance().GetModelVo(id, 1);
//            List<String> picList = modelExperimentInfoVO.GetSavedPicInfoList();
//            if (picList.size() < 5) {
                //保存图片
                String uid = "upload_" + new Random().nextLong() + "_" + id + "_" ;//+ picList.size();//      + CommonUtil.FormatFileDate(new Date()) + "_" + new Random().nextLong();
                String fileUrl = OssFileUtil.UploadImg(base64, "healthCommunity/" + uid + ".jpg");

//                String imgPath = GameDataManager.GetInstance().GetStringDefine(EnumStringDefine.IMG_PATH.getStateID());
//                FileUtils.mkdir(imgPath);
//                //组成文件名
//                String imgFileName = uid;//+ ".jpg";
//                String fileFullName = MessageFormat.format("{0}/{1}.jpg", imgPath, imgFileName);
//                boolean result = this.GenerateImage(base64, fileFullName);
//                if (result) {
//                    //保存成功, 记录图片路径
//                    String imgPathUrl = GameDataManager.GetInstance().GetStringDefine(EnumStringDefine.IMG_PATH_URL_PREFIX.getStateID());
//                    String imgUrl = MessageFormat.format("{0}/{1}.jpg", imgPathUrl, imgFileName);
//                    picList.add(imgUrl);
//                    modelExperimentInfoVO.SetSavedPicInfoList(picList);
//                    PlayerManager.GetInstance().UpdateModelInfo(modelExperimentInfoVO);
//                }
//                errorMsg = result ? "" : "保存图片失败";

                boolean result = !fileUrl.isEmpty();
                if (result) {
                    url = fileUrl;
                    //保存成功, 记录图片路径
//                    picList.add(fileUrl);
//                    modelExperimentInfoVO.SetSavedPicInfoList(picList);
//                    PlayerManager.GetInstance().UpdateModelInfo(modelExperimentInfoVO);
                }
                errorMsg = result ? "" : "保存图片失败";
                Send(ctx, JSON.toJSONString(errorMsg.isEmpty() ? WebJSONResult.ok(url) : WebJSONResult.errorMsg(errorMsg)), HttpResponseStatus.OK);
//            } else {
//                //超过保存上限
//                errorMsg = "超过保存的数量上限5张图";
//                Send(ctx, JSON.toJSONString(errorMsg.isEmpty() ? WebJSONResult.ok(url) : WebJSONResult.errorMsg(errorMsg)), HttpResponseStatus.OK);
//            }
        } catch (Exception e) {
            errorMsg = "保存异常" + e.getMessage();
            Send(ctx, JSON.toJSONString(errorMsg.isEmpty() ? WebJSONResult.ok(url) : WebJSONResult.errorMsg(errorMsg)), HttpResponseStatus.OK);
        }

//                Map<String, String> parmMap = new HashMap<>();
//        // 是GET请求
//        QueryStringDecoder decoder = new QueryStringDecoder(request.getUri());
//        decoder.parameters().entrySet().forEach(entry -> {
//            // entry.getValue()是一个List, 只取第一个元素
//            parmMap.put(entry.getKey().trim(), entry.getValue().get(0).trim());
//        });


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

    }

    //base64字符串转化成图片
    public boolean GenerateImage(String imgStr, String outputPath) {   //对字节数组字符串进行Base64解码并生成图片
        if (imgStr == null) //图像数据为空
            return false;
        BASE64Decoder decoder = new BASE64Decoder();
        try {
            //Base64解码
            byte[] b = decoder.decodeBuffer(imgStr);
            for (int i = 0; i < b.length; ++i) {
                if (b[i] < 0) {//调整异常数据
                    b[i] += 256;
                }
            }
            //生成jpeg图片
            String imgFilePath = outputPath;//"D:\\tupian\\new.jpg";//新生成的图片
            OutputStream out = new FileOutputStream(imgFilePath);
            out.write(b);
            out.flush();
            out.close();
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
}
