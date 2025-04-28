package com.baizhou.http.handler.requesthandlers.webMsgHandler;

import com.alibaba.fastjson.JSON;
import com.baizhou.common.WebJSONResult;
import com.baizhou.data.constdefine.ConstDefine;
import com.baizhou.data.enumdefine.EnumErrorMsg;
import com.baizhou.data.enumdefine.EnumStringDefine;
import com.baizhou.dbtool.DBDataTool;
import com.baizhou.http.handler.BaseRequestHandler;
import com.baizhou.manager.GameDataManager;
import com.baizhou.util.FileUtils;
import com.baizhou.util.HttpClientUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.multipart.FileUpload;
import io.netty.handler.codec.http.multipart.HttpPostRequestDecoder;
import io.netty.handler.codec.http.multipart.InterfaceHttpData;
import io.netty.handler.codec.http.multipart.MixedAttribute;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import java.io.File;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;


public class postExcelMultiFileHandler extends BaseRequestHandler {
    @Override
    public void DoHandling(ChannelHandlerContext ctx, HttpMethod method, String path, ByteBuf body, FullHttpRequest request) throws IOException, InvalidFormatException, InterruptedException {

        String errorMsg = "";
        String url = "";

        HttpPostRequestDecoder decoder = new HttpPostRequestDecoder(request);
        decoder.offer(request);


        Map<String, String> paramKey = new HashMap<>();
        // multipart/form-data  文件上传
        List<InterfaceHttpData> parmList = decoder.getBodyHttpDatas();
        for (InterfaceHttpData parm : parmList) {
            if(parm.getHttpDataType() == InterfaceHttpData.HttpDataType.Attribute){
                MixedAttribute parm1 = (MixedAttribute) parm;
//                InterfaceHttpData parm1 = parm;
                String name =  parm1.getName();
                String val =  parm1.getValue();

                paramKey.put(name, val);
            }
        }

        for (InterfaceHttpData parm : parmList) {
            if (parm.getHttpDataType() == InterfaceHttpData.HttpDataType.FileUpload) {
                FileUpload fileUpload = (FileUpload) parm;
                String fileName = fileUpload.getFilename();
                String keyname  = fileUpload.getName();
                if (fileUpload.isCompleted()) {
                    //                  //保存到磁盘
//                    StringBuffer fileNameBuf = new StringBuffer();
//                    fileNameBuf.append(DiskFileUpload.baseDirectory).append(fileName);
                    String uid = fileName;//"upload_" + new Random().nextLong() + "_" + fileName;//      + CommonUtil.FormatFileDate(new Date()) + "_" + new Random().nextLong();
                    String imgPath = GameDataManager.GetInstance().GetStringDefine(EnumStringDefine.TOOL_EXCEL_PATH.getStateID());
                    FileUtils.mkdir(imgPath);
                    //组成文件名
                    String imgFileName = uid ;//+ ".jpg";
                    String fileFullName = MessageFormat.format("{0}/{1}", imgPath, imgFileName);

                    try {
                            //备份原有的 excel
                            String uid2 = "upload_" + new Random().nextLong() + "_" + fileName;//      + CommonUtil.FormatFileDate(new Date()) + "_" + new Random().nextLong();
//                            String imgPath2 = GameDataManager.GetInstance().GetStringDefine(EnumStringDefine.TOOL_EXCEL_PATH.getStateID());
//                            FileUtils.mkdir(imgPath2);
                            //组成文件名
                            String fileFullName2 = MessageFormat.format("{0}/{1}", imgPath, uid2);
                            FileUtils.copyFile(fileFullName, fileFullName2);

                            //保存上传的Excel
                        fileUpload.renameTo(new File(fileFullName));//(fileNameBuf.toString()));

                        //
//                        String excelFile = paramKey.getOrDefault(ConstDefine.EXCEL_KEY, "");
//                        String iconFile = paramKey.getOrDefault(ConstDefine.ICONFILE_KEY, "");
//                        String updateExcelFile = paramKey.getOrDefault(ConstDefine.UpdateEXCEL_KEY, "");
//                        String backupExcelFile = paramKey.getOrDefault(ConstDefine.BackupEXCEL_KEY, "");

                        //记录Excel的保存路径
                        if(keyname.equals(ConstDefine.EXCEL_KEY)){
                            paramKey.put(ConstDefine.EXCEL_KEY, fileFullName);
                        }else if(keyname.equals(ConstDefine.ICONFILE_KEY)){
                            paramKey.put(ConstDefine.ICONFILE_KEY, fileFullName);
                        }else if(keyname.equals(ConstDefine.UpdateEXCEL_KEY)){
                            paramKey.put(ConstDefine.UpdateEXCEL_KEY, fileFullName);
                        }else if(keyname.equals(ConstDefine.BackupEXCEL_KEY)){
                            paramKey.put(ConstDefine.BackupEXCEL_KEY, fileFullName);
                        }

                        String imgUrlPrefix = GameDataManager.GetInstance().GetStringDefine(EnumStringDefine.TOOL_EXCEL_PATH_URL_PREFIX.getStateID());
                        url = MessageFormat.format("{0}/{1}", imgUrlPrefix, imgFileName);
                    } catch (IOException e) {
                        e.printStackTrace();
                        errorMsg = GameDataManager.GetInstance().GetErrorMsg(EnumErrorMsg.SaveImageFail);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }


        String year =  paramKey.get("year");
        String callbackUrl =  paramKey.get("callbackUrl");

        String mode =  paramKey.get(ConstDefine.MODE_KEY);

        try {
            if(mode.equals("1")){
                //全部更新
                //进行Excel保存
                DBDataTool.GetInstance().LoadFromUniExcelByGroup(
                        paramKey.get(ConstDefine.EXCEL_KEY), paramKey.get(ConstDefine.ICONFILE_KEY),
                        year , Integer.parseInt(paramKey.get("sheet")));


                //do get
//        HttpClientUtil.doGet("http://web.baizhoutj.com:8130/updateDB");
                HttpClientUtil.doGet(callbackUrl + "?year=" + year);

            }else  if(mode.equals("2")){
                //增量更新

                //全部更新
                //进行Excel保存
                DBDataTool.GetInstance().LoadFromUniExcelByGroup_IncreUpdate(
                        paramKey.get(ConstDefine.UpdateEXCEL_KEY), paramKey.get(ConstDefine.BackupEXCEL_KEY), paramKey.get(ConstDefine.ICONFILE_KEY),
//                    uniMajorUrl_update, uniMajorUrl_backup, uniMajorIconUrl,
                        year, Integer.parseInt(paramKey.get("sheet")));

                //do get
//        HttpClientUtil.doGet("http://web.baizhoutj.com:8130/updateDB");
                HttpClientUtil.doGet(callbackUrl + "?year=" + year);

            }

        }catch (IOException e){
            errorMsg = e.getMessage();
        }catch ( InvalidFormatException e){
            errorMsg = e.getMessage();
        }catch (InterruptedException e){
            errorMsg = e.getMessage();
        } catch (Exception e) {
            errorMsg = e.getMessage();
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
