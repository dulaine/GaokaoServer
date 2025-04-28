package com.baizhou.dbtool;

import com.baizhou.config.MergToolConfig;
import com.baizhou.data.enumdefine.EnumPici;
import com.baizhou.dbtool.model.ExcelTableData;
import com.baizhou.dbtool.model.UniMajorParam;
import com.baizhou.dbtool.parser.ExcelParser;
import com.baizhou.dbtool.proxy.UniMajorInfoParseProx;
import com.baizhou.util.GameUtil;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.util.ZipSecureFile;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.File;
import java.io.IOException;

public class MergTool {

    private static MergTool _instance;
    ExcelParser excelParser = new ExcelParser();

    public static MergTool GetInstance() {
        if (_instance == null) {

            _instance = new MergTool();
//            _instance.init();
        }
        return _instance;
    }

    //院校池 excel信息 ==> 按Group保存
    public  void Build(MergToolConfig fileConfig) throws IOException, InvalidFormatException, InterruptedException {
        System.out.println("开始 合并数据,  年份:" + fileConfig.Year);
//        File excel = new File(url);
//        ZipSecureFile.setMinInflateRatio(-1.0d);
//        Workbook workbook = DBDataTool.ParseWorkbook(excel);


    }


    //院校池 excel信息 ==> 按Group保存
    public  void Build(String url, String uniMajorIconUrl, String year, Integer sheetNum) throws IOException, InvalidFormatException, InterruptedException {
        System.out.println("开始 报考系统,  年份:" + year);
        File excel = new File(url);
        ZipSecureFile.setMinInflateRatio(-1.0d);
        Workbook workbook = DBDataTool.ParseWorkbook(excel);

//        Date time1 = new Date();
//        //获取版本信息
//        Integer latestVer = 1;
//        List<UniGroupInfoALatestVO> groupInfo = DBProxy.Getinstance().UniGroupInfoALatestService.findByExamYear(year);
//        if(groupInfo.size() == 0){
//            //之前 没有添加过  Groupinfo; 直接用UniMajorInfoALates中的
//            List<UniMajorInfoALatestVO> list = DBProxy.Getinstance().UniMajorInfoALatestService.getAllUniMajorInfoALatests();
//            if (list.size() > 0) {
//                latestVer = list.get(0).getDataVersion() + 1;
//            }
//        }else {
//            //之前 有添加过GroupInfo
//            latestVer = groupInfo.get(0).getDataVersion() + 1;
//        }
//
//        Date time2 = new Date();
//        System.out.println("get last version cost " + (time2.getTime() - time1.getTime()));

        //获取icon数据
        File iconexcel = new File(uniMajorIconUrl);
        ExcelTableData iconTable = DBDataTool.Parse(iconexcel, 0, excelParser, 2);
//        LoadFromIconMajorInfo(iconTable); //更新Icon Major


        for (int i = 0; i < 8; i++) {
            if(i == 4){
                System.out.println("专科提前批次continue");
                continue;
            }
            if(sheetNum > 0 &&  i != sheetNum - 1) continue;
            EnumPici enumPici = GameUtil.GetPiciBySheetId(i);
            //        //获取版本信息
            Integer latestVer =  1;// GetLatestVersion(year, enumPici);
            System.out.println("get latest vertion " + latestVer);




            ExcelTableData table = DBDataTool.Parse(workbook, excel, i, excelParser, 1);
            UniMajorInfoParseProx proxy = new UniMajorInfoParseProx();
            proxy.DoProxy(table, excel, new UniMajorParam(i, latestVer, iconTable, year));
        }

    }

}
