package com.baizhou.http.handler.requesthandlers.webMsgHandler;

import com.baizhou.http.handler.BaseRequestHandler;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpMethod;

public class postExcelHandler extends BaseRequestHandler {
    private static int parentSKU = 0;//Parent-SKU
    private static int kindId = 1;//类目ID
    private static int title = 2;//标题
    private static int productDesc = 3;//描述
    private static int productSpecification = 4;//产品规格属性
    private static int weight = 5;//重量（kg）
    private static int length = 6;//长(cm)
    private static int width = 7;//宽(cm)
    private static int height = 8;//高(cm)
    private static int stock = 9;//库存数量
    private static int price = 10;//原价（$）
    private static int promotionPrice = 11;//促销价（$）
    private static int color = 12;//color
    private static int size = 13;//size
    private static int customSpecification = 14;//自定义规格（变种）
    private static int SKU = 15;//SKU
    private static int variantPic = 16;//变种图
    private static int mainPic = 17;//产品主图
    private static int attachPic1 = 18;//附图1
    private static int attachPic2 = 19;//附图2
    private static int attachPic3 = 20;//附图3
    private static int attachPic4 = 21;//附图4
    private static int attachPic5 = 22;//附图5
    private static int productSourceUrl = 23;//来源URL
    private static int isTranslated = 24;//是否翻译

    private long savedExcelCount = 0;//保存的excel计数

    @Override
    public void DoHandling(ChannelHandlerContext ctx, HttpMethod method, String path, ByteBuf body, FullHttpRequest request) {

//        Map<String, String> parmMap = new HashMap<>();
//        // 是GET请求
//        QueryStringDecoder decoder = new QueryStringDecoder(request.getUri());
//        decoder.parameters().entrySet().forEach(entry -> {
//            // entry.getValue()是一个List, 只取第一个元素
//            parmMap.put(entry.getKey().trim(), entry.getValue().get(0).trim());
//        });
//
//
//        //检测是否有用户id和商店名称
//        String userId = parmMap.get(ConstDefine.UserId);
//        String shopName = parmMap.get(ConstDefine.ShopName);
//        String platformIdString = parmMap.get(ConstDefine.PlatformId);
//        if (shopName == null || shopName.isEmpty()) {
//            Send(ctx, JSON.toJSONString(WebJSONResult.errorMsg("no shopName name")), HttpResponseStatus.OK);
//            return;
//        }
//        if (userId == null || userId.isEmpty()) {
//            Send(ctx, JSON.toJSONString(WebJSONResult.errorMsg("no user id")), HttpResponseStatus.OK);
//            return;
//        }
//        if (platformIdString == null || platformIdString.isEmpty()) {
//            Send(ctx, JSON.toJSONString(WebJSONResult.errorMsg("no platform id")), HttpResponseStatus.OK);
//            return;
//        }
//        Integer platformId = null;
//        try {
//            platformId = Integer.parseInt(platformIdString);
//        }catch (NumberFormatException e){
//            Send(ctx, JSON.toJSONString(WebJSONResult.errorMsg("no platform id")), HttpResponseStatus.OK);
//            return;
//        }
//
//
//        byte[] contentBytes = new byte[body.readableBytes()];
//        body.readBytes(contentBytes, 0, contentBytes.length);
//        String errorMsg = "";
//
//        InputStream input = new ByteArrayInputStream(contentBytes); //is = new FileInputStream(file);
//        Workbook wb = StreamingReader.builder().rowCacheSize(200).bufferSize(1024).open(input);
//        Sheet sheet = wb.getSheetAt(0);
//
//        try {
//
//            //创建一个xlsx文件
//            XSSFWorkbook workbook = new XSSFWorkbook(); //如果是xls: workbook = new HSSFWorkbook();
//            Sheet sheet1 = workbook.createSheet();
//            int rowIndex = 0;
//
//            int maxRow = JsonConfigManager.GetInstance().GetGameConfig().getExcelReadLimit();
//            //插入数据库
//            for (Row row : sheet) {
//                if(rowIndex > maxRow) break; //最大插入数据不超过配置
//                if (!errorMsg.isEmpty()) break;
//
//                //为保存xlsx备份准备
//                XSSFRow xssfRow = (XSSFRow) sheet1.createRow(rowIndex);
//                int cellCount = row.getLastCellNum();
//                for (int i = 0; i <= cellCount; i++) {
//                    Cell cell = xssfRow.createCell(i);
//                    //不为空, 就设置一个String类型的值
//                    if (row.getCell(i) != null) {
//                        cell.setCellValue(row.getCell(i).getStringCellValue());
//                    }
//                }
//                rowIndex++;
//
//
//                if (row.getRowNum() != 0) {
////                String id = ExcelTool.ReadCellValueByString(row, idIndex, "");
////                if(id.isEmpty()) continue;
////                //检测id 是否已经存在
////                ProductPublicationInfoVO vo = DBProxy.Getinstance().ProductPublicationInfoService.findOneById(id);
////                if(vo != null) {
////                    errorMsg = "id : "+id +" already exist";
////                    //continue;
////                    break;
////                }
//
//                    //读取excel
//                    String temp_parentSKU = ExcelTool.ReadCellValueByString(row, parentSKU, "");
//                    String temp_kindId = ExcelTool.ReadCellValueByString(row, kindId, "");
//                    String temp_title = ExcelTool.ReadCellValueByString(row, title, "");
//                    String temp_productDesc = ExcelTool.ReadCellValueByString(row, productDesc, "");
//                    String temp_productSpecification = ExcelTool.ReadCellValueByString(row, productSpecification, "");
//                    float temp_weight = ExcelTool.ReadCellValueByNumber(row, weight, 0f);
//                    float temp_length = ExcelTool.ReadCellValueByNumber(row, length, 0f);
//                    float temp_width = ExcelTool.ReadCellValueByNumber(row, width, 0f);
//                    float temp_height = ExcelTool.ReadCellValueByNumber(row, height, 0f);
//                    int temp_stock = ExcelTool.ReadCellValueByNumber(row, stock, 0);
//                    float temp_price = ExcelTool.ReadCellValueByNumber(row, price, 0f);
//                    float temp_promotionPrice = ExcelTool.ReadCellValueByNumber(row, promotionPrice, 0f);
//                    String temp_color = ExcelTool.ReadCellValueByString(row, color, "");
//                    String temp_size = ExcelTool.ReadCellValueByString(row, size, "");
//                    String temp_customSpecification = ExcelTool.ReadCellValueByString(row, customSpecification, "");
//                    String temp_SKU = ExcelTool.ReadCellValueByString(row, SKU, "");
//                    String temp_variantPic = ExcelTool.ReadCellValueByString(row, variantPic, "");
//                    String temp_mainPic = ExcelTool.ReadCellValueByString(row, mainPic, "");
//                    String temp_attachPic1 = ExcelTool.ReadCellValueByString(row, attachPic1, "");
//                    String temp_attachPic2 = ExcelTool.ReadCellValueByString(row, attachPic2, "");
//                    String temp_attachPic3 = ExcelTool.ReadCellValueByString(row, attachPic3, "");
//                    String temp_attachPic4 = ExcelTool.ReadCellValueByString(row, attachPic4, "");
//                    String temp_attachPic5 = ExcelTool.ReadCellValueByString(row, attachPic5, "");
//                    String temp_productSourceUrl = ExcelTool.ReadCellValueByString(row, productSourceUrl, "");
//                    int temp_isTranslated = ExcelTool.ReadCellValueByNumber(row, isTranslated, 1);
////
////                String imgUrl = ExcelTool.ReadCellValueByString(row, parentSKU, "");
////                String imgHDUrl = ExcelTool.ReadCellValueByString(row, imgHDUrlIndex, "");
////                String productSource = "";
////                String title = ExcelTool.ReadCellValueByString(row, titleIndex, "");
////                String productId = ExcelTool.ReadCellValueByString(row, productIdIndex, "");
////                String shopName = ExcelTool.ReadCellValueByString(row, shopNameIndex, "");
////                String productGroup = ExcelTool.ReadCellValueByString(row, productGroupIndex, "");
////                float price = ExcelTool.ReadCellValueByNumber(row, priceIndex, 0f);
////                int stock = ExcelTool.ReadCellValueByNumber(row, stockIndex, 0);
////                String createTime = ExcelTool.ReadCellValueByString(row, createTimeIndex, "");
////                String updateTime = ExcelTool.ReadCellValueByString(row, updateTimeIndex, "");
//                    ProductPublicationInfoVO vo = new ProductPublicationInfoVO();
////                vo.setId(id);
//                    vo.setUserId(userId);
//                    vo.setProductGroup("");
//                    vo.setShopName(shopName);
//                    vo.setPlatformId(platformId);
//                    String date = CommonUtil.FormatDate(new Date());
//                    vo.setCreateTime(date);
//                    vo.setUpdateTime(date);
//                    vo.setState(EnumPublishState.Unpublished.GetId());
//                    //excel数据赋值
//                    vo.setParentSKU(temp_parentSKU);
//                    vo.setKindId(temp_kindId);
//                    vo.setTitle(temp_title);
//                    vo.setProductDesc(temp_productDesc);
//                    vo.setProductSpecification(temp_productSpecification);
//                    vo.setWeight(temp_weight);
//                    vo.setLength(temp_length);
//                    vo.setWidth(temp_width);
//                    vo.setHeight(temp_height);
//                    vo.setStock(temp_stock);
//                    vo.setPrice(temp_price);
//                    vo.setPromotionPrice(temp_promotionPrice);
//                    vo.setColor(temp_color);
//                    vo.setSize(temp_size);
//                    vo.setCustomSpecification(temp_customSpecification);
//                    vo.setSKU(temp_SKU);
//                    vo.setVariantPic(temp_variantPic);
//                    vo.setMainPic(temp_mainPic);
//                    vo.setAttachPic1(temp_attachPic1);
//                    vo.setAttachPic2(temp_attachPic2);
//                    vo.setAttachPic3(temp_attachPic3);
//                    vo.setAttachPic4(temp_attachPic4);
//                    vo.setAttachPic5(temp_attachPic5);
//                    vo.setProductSourceUrl(temp_productSourceUrl);
//                    vo.setIsTranslated(temp_isTranslated);
//
//                    //保存记录
//                    DBProxy.Getinstance().ProductPublicationInfoService.saveProductPublicationInfo(vo);
//                }
//            }
//
//
//            //保存excel, 读取excel内容, 创建一个新xlsx文件. 直接保存二进制,无法用excel打开, 需要先保存成XSSFWorkbook对象.
//            String uid = CommonUtil.FormatFileDate(new Date());
//            FileUtils.mkdir(JsonConfigManager.GetInstance().GetGameConfig().getExcelSaveRelativePath());
//            savedExcelCount++;
//            String fileName = MessageFormat.format("{0}/{1}.xlsx", JsonConfigManager.GetInstance().GetGameConfig().getExcelSaveRelativePath(), uid + "_" + userId+ "_" + platformId + "_" + shopName  + "_" + savedExcelCount);
//            File filePath = new File(fileName);
//            if (!filePath.exists()) {
//                filePath.createNewFile();
//            } else {
//                FileUtils.deleteFile(fileName);
//            }
//            FileOutputStream fos = new FileOutputStream(fileName);
//            workbook.write(fos);
//            fos.close();
//
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//            errorMsg = "save server failed " + e.getMessage();
//        } catch (IOException e) {
//            e.printStackTrace();
//            errorMsg = "save server failed " + e.getMessage();
//        }
//
//
//        if (errorMsg.isEmpty()) {
//            Send(ctx, JSON.toJSONString(WebJSONResult.ok()), HttpResponseStatus.OK);
//        } else {
//            Send(ctx, JSON.toJSONString(WebJSONResult.errorMsg(errorMsg)), HttpResponseStatus.OK);
//        }


    }
}