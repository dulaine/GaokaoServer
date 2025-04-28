package com.baizhou.dbtool;

import com.baizhou.core.model.entity.*;
import com.baizhou.core.model.vo.*;
import com.baizhou.data.constdefine.ConstDefine;
import com.baizhou.data.enumdefine.EnumPici;
import com.baizhou.db.DBProxy;
import com.baizhou.dbtool.model.ExcelTableData;
import com.baizhou.dbtool.model.UniMajorParam;
import com.baizhou.dbtool.parser.ExcelParser;
import com.baizhou.dbtool.parser.ExcelParserInterface;
import com.baizhou.dbtool.proxy.*;
import com.baizhou.manager.OrderManager;
import com.baizhou.util.*;
import com.msg.UniMajorInfo;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.util.ZipSecureFile;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.Year;
import java.util.*;

public class DBDataTool {
    private static DBDataTool _instance;
    ExcelParser excelParser = new ExcelParser();

    public static DBDataTool GetInstance() {
        if (_instance == null) {

            _instance = new DBDataTool();
            _instance.init();
        }
        return _instance;
    }

    private void init() {

//        try {
//            String url = System.getProperty("user.dir")+"/dataConfigJson/FileConfig.json";
//            String json = FileUtils.readFile(url);
//            //System.out.println("jaon:" + json);
//            JSONObject object = JSON.parseObject(json);
//            config = object.toJavaObject(FileConfig.class);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }


    //院校池 excel信息
    public void LoadFromUniExcel(String url, String uniMajorIconUrl) throws IOException, InvalidFormatException, InterruptedException {
        System.out.println("开始 报考系统");
        File excel = new File(url);

        Integer latestVer = 1;
        List<UniMajorInfoALatestVO> list = DBProxy.Getinstance().UniMajorInfoALatestService.getAllUniMajorInfoALatests();
        if (list.size() > 0) {
            latestVer = list.get(0).getDataVersion() + 1;
        }

        //获取icon数据
        File iconexcel = new File(uniMajorIconUrl);
        ExcelTableData iconTable = Parse(iconexcel, 0, excelParser, 2);
        LoadFromIconMajorInfo(iconTable); //更新Icon Major

        for (int i = 0; i < 4; i++) {
            ExcelTableData table = Parse(excel, i, excelParser, 1);
            UniMajorInfoParseProx proxy = new UniMajorInfoParseProx();
            proxy.DoProxy(table, excel, new UniMajorParam(i, latestVer, iconTable, "2023"));
        }

    }

    //专业目录 excel信息
    public void LoadFromMajorExcel(String url) throws IOException, InvalidFormatException {
        System.out.println("开始 本科专业目录");
        File excel = new File(url);
        ExcelTableData table = Parse(excel, 0, excelParser, 1);

        MajorParserProxy proxy = new MajorParserProxy();
        proxy.DoProxy(table, excel, new UniMajorParam(0, 0, null,null));
    }


    //一分一段 excel信息
    public void LoadFromScoreRankExcel(String url, String examYear) throws IOException, InvalidFormatException, InterruptedException {

        System.out.println("开始 一分一段数据部分 " + examYear);
        File excel = new File(url);
        ExcelTableData table = Parse(excel, 0, excelParser, 2);

//        DBProxy.Getinstance().yfydService.deleteAll();
//        try {
//            Thread.sleep(ConstDefine.InsertDBInterval);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

//        this.parExcelHandler(System.getProperty("user.dir")+config.YFYDDir,
//                new YFYDExcelParseProxy(),"理");
//        this.parExcelHandler(dir, new YFYDExcelParserProxy(),excelName);


        IExcelParseProxy proxy = new YFYDExcelParserProxy();
        proxy.DoProxy(table, excel, new UniMajorParam(0, 0, null,null));
    }

    public void LoadFromScoreRankExcelByYear(String url, String examYear) throws IOException, InvalidFormatException, InterruptedException {

        System.out.println("开始 一分一段数据部分 " + examYear);
        File excel = new File(url);
        ExcelTableData table = Parse(excel, 0, excelParser, 2);

        IExcelParseProxy proxy = new YFYDByYearExcelParserProxy();
        proxy.DoProxy(table, excel, new UniMajorParam(0, 0, null,examYear));
    }

    //
    //3年专业信息  excel信息
    public void LoadFrom3YearMajorInfo(String firstY, String secY, String thirdY) throws IOException, InvalidFormatException, InterruptedException {
        System.out.println("开始 /3年专业信息");
        File excelY1 = new File(firstY);
        //解析第一个年专业
        DBProxy.Getinstance().MajorInfoY1Service.deleteAll();
        for (int i = 0; i < 4; i++) {
            ExcelTableData table = Parse(excelY1, i, excelParser, 1);
            ThreeYearMajorInfoParseProxy proxy = new ThreeYearMajorInfoParseProxy();
            proxy.DoProxy(table, excelY1, new UniMajorParam(i, 0, null,null));
        }

        File excelY2 = new File(secY);
        DBProxy.Getinstance().MajorInfoY2Service.deleteAll();
        //解析第2个年专业
        for (int i = 0; i < 4; i++) {
            ExcelTableData table = Parse(excelY2, i, excelParser, 1);
            ThreeYearMajorInfoParseProxy proxy = new ThreeYearMajorInfoParseProxy();
            proxy.DoProxy(table, excelY2, new UniMajorParam(i, 1, null,null));
        }

        File excelY3 = new File(thirdY);
        DBProxy.Getinstance().MajorInfoY3Service.deleteAll();
        //解析第3个年专业
        for (int i = 0; i < 4; i++) {
            ExcelTableData table = Parse(excelY3, i, excelParser, 1);
            ThreeYearMajorInfoParseProxy proxy = new ThreeYearMajorInfoParseProxy();
            proxy.DoProxy(table, excelY3, new UniMajorParam(i, 2, null,null));
        }

    }

    //3年专业信息  excel信息, 一次一年
    public void LoadFrom3YearMajorInfoByOne(String firstY, String year) throws IOException, InvalidFormatException, InterruptedException {
        System.out.println("开始 3年专业信息, 年份: " + year);
        File excelY1 = new File(firstY);
        //解析第一个年专业
        DBProxy.Getinstance().MajorInfoYAllService.deleteByYear(year);
        for (int i = 0; i < 4; i++) {
            ExcelTableData table = Parse(excelY1, i, excelParser, 1);
            ThreeYearMajorInfoByYearParseProxy proxy = new ThreeYearMajorInfoByYearParseProxy();
            proxy.DoProxy(table, excelY1, new UniMajorParam(i, 0, null,year));
        }
        System.out.println("全部完成!");
    }


    //
    //icon major专业信息  excel信息
    public void LoadFromIconMajorInfo(ExcelTableData iconTable) throws IOException, InvalidFormatException, InterruptedException {
        System.out.println("开始 icon专业信息");
//        File excelY1 = new File(firstY);
        //解析第一个年专业
        DBProxy.Getinstance().IconMajorInfoService.deleteAll();
//        for (int i = 0; i < 4; i++) {
//            ExcelTableData table = Parse(excelY1, i, excelParser, 1);
        IconMajorInfoParseProxy proxy = new IconMajorInfoParseProxy();
        proxy.DoProxy(iconTable, null, new UniMajorParam(0, 0, null, null));
//        }

    }


    public static ExcelTableData Parse(File excelFile, int sheetIndex, ExcelParserInterface parser, int startRowIdx) throws IOException, InvalidFormatException {
        Workbook book;
        Sheet sheet;
        //根据文件类型读取
        String extension = FileUtils.GetExtension(excelFile);
        if (extension.equals("xls")) {
            POIFSFileSystem poifsFileSystem = new POIFSFileSystem(new FileInputStream(excelFile));
            book = new HSSFWorkbook(poifsFileSystem);
            sheet = book.getSheetAt(sheetIndex);
        } else if (extension.equals("xlsx")) {
            book = new XSSFWorkbook(excelFile);
            sheet = book.getSheetAt(sheetIndex);
        } else {
            System.out.println("文件不是excel类型 " + excelFile.getName());
            return null;
        }

        //解析excel
//        ExcelTableData data = parseExcel(sheet, book);
        ExcelTableData data = parser.DoParse(sheet, startRowIdx);
        data.TableName = FileUtils.GetNameWithoutExtension(excelFile);
        return data;
    }

    public static Workbook ParseWorkbook(File excelFile) throws IOException, InvalidFormatException {
        Workbook book;
        Sheet sheet;
        //根据文件类型读取
        String extension = FileUtils.GetExtension(excelFile);
        if (extension.equals("xls")) {
            POIFSFileSystem poifsFileSystem = new POIFSFileSystem(new FileInputStream(excelFile));
            book = new HSSFWorkbook(poifsFileSystem);
//            sheet = book.getSheetAt(sheetIndex);
        } else if (extension.equals("xlsx")) {
            book = new XSSFWorkbook(excelFile);
//            sheet = book.getSheetAt(sheetIndex);
        } else {
            System.out.println("文件不是excel类型 " + excelFile.getName());
            return null;
        }

//        //解析excel
////        ExcelTableData data = parseExcel(sheet, book);
//        ExcelTableData data = parser.DoParse(sheet, startRowIdx);
//        data.TableName = FileUtils.GetNameWithoutExtension(excelFile);
        return book;
    }


    public static ExcelTableData Parse(Workbook book, File excelFile,int sheetIndex, ExcelParserInterface parser, int startRowIdx) throws IOException, InvalidFormatException {
//        Workbook book;
//        Sheet sheet;
//        //根据文件类型读取
//        String extension = FileUtils.GetExtension(excelFile);
//        if (extension.equals("xls")) {
//            POIFSFileSystem poifsFileSystem = new POIFSFileSystem(new FileInputStream(excelFile));
//            book = new HSSFWorkbook(poifsFileSystem);
//            sheet = book.getSheetAt(sheetIndex);
//        } else if (extension.equals("xlsx")) {
//            book = new XSSFWorkbook(excelFile);
//            sheet = book.getSheetAt(sheetIndex);
//        } else {
//            System.out.println("文件不是excel类型 " + excelFile.getName());
//            return null;
//        }
        Sheet sheet = book.getSheetAt(sheetIndex);

        //解析excel
//        ExcelTableData data = parseExcel(sheet, book);
        ExcelTableData data = parser.DoParse(sheet, startRowIdx);
        data.TableName = FileUtils.GetNameWithoutExtension(excelFile);
        return data;
    }



    //院校池 excel信息 ==> 按Group保存
    public void LoadFromUniExcelByGroup(String url, String uniMajorIconUrl, String year, Integer sheetNum) throws IOException, InvalidFormatException, InterruptedException {
        System.out.println("开始 报考系统,  年份:" + year);
        File excel = new File(url);
        ZipSecureFile.setMinInflateRatio(-1.0d);
        Workbook workbook = ParseWorkbook(excel);

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
        ExcelTableData iconTable = Parse(iconexcel, 0, excelParser, 2);
//        LoadFromIconMajorInfo(iconTable); //更新Icon Major

        Date start = new Date();
        for (int i = 0; i < 8; i++) {
            Date time1 = new Date();
            if(i == 4){
                System.out.println("专科提前批次continue");
                continue;
            }
            if(sheetNum > 0 &&  i != sheetNum - 1) continue;
            EnumPici enumPici = GameUtil.GetPiciBySheetId(i);
            System.out.println("开始解析批次 " + enumPici.name());
            //        //获取版本信息
            Integer latestVer = GetLatestVersion(year, enumPici);
            System.out.println("get latest vertion " + latestVer);

            ExcelTableData table = Parse(workbook, excel, i, excelParser, 1);
            UniMajorInfoParseProx proxy = new UniMajorInfoParseProx();
            proxy.DoProxy(table, excel, new UniMajorParam(i, latestVer, iconTable, year));


            Date time2 = new Date();
            System.out.println("update pici " + enumPici.name() + " cost " + (time2.getTime() - time1.getTime()));
        }
        Date end = new Date();
        System.out.println("总时间 pici cost " + (end.getTime() - start.getTime()));
    }

    //院校池 excel信息 ==> 按Group保存
    public void LoadFromUniExcelByGroup_IncreUpdate(String url, String backupUrl, String uniMajorIconUrl, String year, Integer sheetNum) throws Exception {
        System.out.println("开始 增量更新 报考系统,  年份:" + year);
        File excel = new File(url);
        ZipSecureFile.setMinInflateRatio(-1.0d);
        Workbook workbook = ParseWorkbook(excel);


        //对比 备份的 Excel
        File backupExcel = new File(backupUrl);
        Workbook workbook_backup = ParseWorkbook(backupExcel);


        //获取icon数据
        File iconexcel = new File(uniMajorIconUrl);
        ExcelTableData iconTable = Parse(iconexcel, 0, excelParser, 2);
//        LoadFromIconMajorInfo(iconTable); //更新Icon Major

        Date start = new Date();
        for (int i = 0; i < 8; i++) {
            Date time1 = new Date();
            if(i == 4){
//                System.out.println("专科提前批次continue");
                continue;
            }
            if(sheetNum > 0 &&  i != sheetNum - 1) continue;
            EnumPici enumPici = GameUtil.GetPiciBySheetId(i);
            System.out.println("增量解析院校池 批次 " + enumPici.name() + " start");
            //获取版本信息
            Integer latestVer = GetLatestVersion(year, enumPici);
            System.out.println("get latest vertion " + latestVer);

            ExcelTableData table = Parse(workbook, excel, i, excelParser, 1);
            ExcelTableData table_backup = Parse(workbook_backup, excel, i, excelParser, 1);

//            UniMajorInfoIncreUpdateParseProx proxy = new UniMajorInfoIncreUpdateParseProx();
//            proxy.Compare(table, table_backup, i, iconTable, year, latestVer);

            UniMajorInfoIncreUpdateParseProxV2 proxy = new UniMajorInfoIncreUpdateParseProxV2();
            proxy.Compare(table, table_backup, i, iconTable, year, latestVer);


            Date time2 = new Date();
            System.out.println("update pici " + enumPici.name() + " cost " + (time2.getTime() - time1.getTime()));
//            proxy.DoProxy(table, excel, new UniMajorParam(i, latestVer, iconTable, year));
        }

        Date end = new Date();
        System.out.println("总时间 pici cost " + (end.getTime() - start.getTime()));
        System.out.println("完成增量解析院校池  ");

    }


    public Integer GetLatestVersion(String year, EnumPici enumPici){
//        Date time1 = new Date();
        //获取版本信息
        Integer latestVer = 1;
        if(enumPici == EnumPici.A){
//            List<UniGroupInfoALatestVO> groupInfo = DBProxy.Getinstance().UniGroupInfoALatestService.findByExamYear(year);
//            if(groupInfo.size() == 0){
//                //之前 没有添加过  Groupinfo; 直接用UniMajorInfoALates中的
//                List<UniMajorInfoALatestVO> list = DBProxy.Getinstance().UniMajorInfoALatestService.getAllUniMajorInfoALatests();
//                if (list.size() > 0) {
//                    latestVer = list.get(0).getDataVersion() + 1;
//                }
//            }else {
//                //之前 有添加过GroupInfo
//                latestVer = groupInfo.get(0).getDataVersion() + 1;
//            }

            latestVer = DBProxy.Getinstance().UniMajorInfoALatestService.findMaxVersion(year) + 1;
        }else if(enumPici == EnumPici.B){
            latestVer = DBProxy.Getinstance().UniMajorInfoBLatestService.findMaxVersion(year) + 1;
        }else if(enumPici == EnumPici.PreA){
            latestVer = DBProxy.Getinstance().UniMajorInfoPreALatestService.findMaxVersion(year) + 1;
        }else if(enumPici == EnumPici.PreB){

            latestVer = DBProxy.Getinstance().UniMajorInfoPreBLatestService.findMaxVersion(year) + 1;
        }else if(enumPici == EnumPici.ZhuanKe){


            latestVer = DBProxy.Getinstance().UniMajorInfoZhuanKeLatestService.findMaxVersion(year) + 1;
        }else if(enumPici == EnumPici.ZXA){

            latestVer = DBProxy.Getinstance().UniMajorInfoZXALatestService.findMaxVersion(year) + 1;
        }else if(enumPici == EnumPici.ZXB){

            latestVer = DBProxy.Getinstance().UniMajorInfoZXBLatestService.findMaxVersion(year) + 1;
        }

//        Date time2 = new Date();
//        System.out.println("get last version cost " + (time2.getTime() - time1.getTime()));

        return latestVer;
    }




    //记录 体检限报
    public void LoadFromPhysicLimiationExcel(String url) throws IOException, InvalidFormatException {
        System.out.println("开始 体检限报Excel");
        File excel = new File(url);
        ExcelTableData table = Parse(excel, 0, excelParser, 1);

        PhysicLimitationParserProxy proxy = new PhysicLimitationParserProxy();
        proxy.DoProxy(table, excel, new UniMajorParam(0, 0, null,null));
    }


    //利用LatestDB,   对HistDB 重新进行 设置LatestID
    public static  void InitCurDB(EnumPici pici){

        Date time = new Date();
        switch (pici){

            case A:
            {
//                List<UniMajorInfoALatestVO>  list = DBProxy.Getinstance().UniMajorInfoALatestService.getAllUniMajorInfoALatests();

                HashMap<Long, UniMajorInfoALatestVO> latestDic = new HashMap<>();
                List<UniMajorInfoALatestVO> latestList = new ArrayList<>();

                Integer page = 0;
                Integer limit = 100;
                Pageable pageable = PageRequest.of(0, limit);
                Map<String, Object> map =  DBProxy.Getinstance().UniMajorInfoALatestService.listbyPage(pageable);
//                    List<UniMajorInfoALatestVO> list = (List<UniMajorInfoALatestVO>) map.get("items");
                page = (int)map.get("totalPage");


                Date time1 = new Date();
                for (int i = 0; i < page; i++) {
                    Date time1_1 = new Date();

                    pageable = PageRequest.of(i, limit);
                    map =  DBProxy.Getinstance().UniMajorInfoALatestService.listbyPage(pageable);
                    List<UniMajorInfoALatestVO>   list = (List<UniMajorInfoALatestVO>) map.get("items");


                    for (int j = 0; j < list.size(); j++) {
                        latestDic.put(list.get(j).getId(), list.get(j));
                        latestList.add(list.get(j));
                    }

                    Date time1_2 = new Date();
//                        System.out.println("find by page 100 cost: ms " + (time1_2.getTime() - time1_1.getTime())); //40ms

                }
                Date time2 = new Date();
                System.out.println("find latest total cost: ms " + (time2.getTime() - time1.getTime()));



                //year,  unicode, majorCode
                HashMap<String,    HashMap<String,     HashMap<String,   List<UniMajorInfoAVO>> >>  oldeMajorDic = new HashMap<>();

                page = 0;
                limit = 100;
                pageable = PageRequest.of(0, limit);
                Map<String, Object> map2 = DBProxy.Getinstance().UniMajorInfoAService.listbyPage(pageable);
//                    List<UniMajorInfo> list2 = (List<com.msg.UniMajorInfo>) map.get("items");
                page = (int)map2.get("totalPage");

                for (int i = 0; i < page; i++) {
                    Date time2_1 = new Date();

                    pageable = PageRequest.of(i, limit);
                    map2 =  DBProxy.Getinstance().UniMajorInfoAService.listbyPage(pageable);
                    List<UniMajorInfoAVO> list2 = (List<UniMajorInfoAVO>) map2.get("items");


                    //记录 数据
                    for (int j = 0; j < list2.size(); j++) {
                        UniMajorInfoAVO tempMajor = list2.get(j);
                        String year = tempMajor.getExamYear();
                        String uniCode = tempMajor.getUniMajorCode();
                        String majorCode = tempMajor.getMajorCode();

                        HashMap<String,     HashMap<String,   List<UniMajorInfoAVO>> > yearDci = oldeMajorDic.getOrDefault(year, null);
                        if(yearDci == null){
                            yearDci = new HashMap<>();
                            oldeMajorDic.put(year, yearDci);
                        }

                        HashMap<String,   List<UniMajorInfoAVO>>  uniCodeDic = yearDci.getOrDefault(uniCode, null);
                        if(uniCodeDic == null){
                            uniCodeDic = new HashMap<>();
                            yearDci.put(uniCode, uniCodeDic);
                        }


                        List<UniMajorInfoAVO> tempList =   uniCodeDic.getOrDefault(majorCode, null);
                        if(tempList == null){
                            tempList = new ArrayList<>();
                            uniCodeDic.put(majorCode, tempList);
                        }
                        tempList.add(tempMajor);

                    }

                    Date time2_2 = new Date();
//                        System.out.println("find old page 100 cost: ms " + (time2_2.getTime() - time2_1.getTime()));  //160ms

                }

                Date time3 = new Date();
                System.out.println("find old total cost: ms " + (time3.getTime() - time2.getTime()));


                //保存数据
                for (int i = 0; i < latestList.size(); i++) {
                    UniMajorInfoALatestVO tempMajor = latestList.get(i);

                    String year = tempMajor.getExamYear();
                    String uniCode = tempMajor.getUniMajorCode();
                    String majorCode = tempMajor.getMajorCode();

                    HashMap<String,     HashMap<String,   List<UniMajorInfoAVO>> > yearDci =      oldeMajorDic.getOrDefault(year, null);

                    HashMap<String,   List<UniMajorInfoAVO>> uniCodeDic =  yearDci.getOrDefault(uniCode, null);

                    List<UniMajorInfoAVO>  tempList  = uniCodeDic.getOrDefault(majorCode, null);


                    Date time3_1 = new Date();
                    for (int j = 0; j < tempList.size(); j++) {
                        UniMajorInfoAVO tempOldHistDB = tempList.get(j);
//                                UniMajorInfoALatestVO uniMajorInfoALatestVO =  DBProxy.Getinstance().UniMajorInfoALatestService.findOneById(temp.getId());
//                                UniMajorInfoAVO uniMajorInfoALatestVO =  DBProxy.Getinstance().UniMajorInfoAService.findOneById(temp.getId());
                        tempOldHistDB.setIdInLatestDB(tempMajor.getIdInHistDB());
                        DBProxy.Getinstance().UniMajorInfoAService.saveUniMajorInfoA(tempOldHistDB);
                    }

                    Date time3_2 = new Date();
//                        System.out.println("find save one latest cost: ms " + (time3_2.getTime() - time3_1.getTime()));   //3ms

                }

                Date time4= new Date();
                System.out.println("save total cost: ms " + (time4.getTime() - time3.getTime()));

            }
            break;
            case B:
            {

//                List<UniMajorInfoBLatestVO>  list = DBProxy.Getinstance().UniMajorInfoBLatestService.getAllUniMajorInfoBLatests();
//                for (int i = 0; i < list.size(); i++) {
//                    UniMajorInfoBLatestVO temp = list.get(i);
//
//                    //更新历史库 把相同专业更新 idInLatestDB 栏位;
//                    List<UniMajorInfoBVO> oldHistDB = DBProxy.Getinstance().UniMajorInfoBService.findByUniMajorCodeAndMajorCodeAndExamYear(temp.getUniMajorCode(), temp.getMajorCode(), temp.getExamYear());
//                    for (int j = 0; j < oldHistDB.size(); j++) {
//                        UniMajorInfoBVO tempOldHistDB = oldHistDB.get(j);
//                        tempOldHistDB.setIdInLatestDB(temp.getIdInHistDB());
//                        DBProxy.Getinstance().UniMajorInfoBService.saveUniMajorInfoB(tempOldHistDB);
//                    }
//                }
                //                List<UniMajorInfoALatestVO>  list = DBProxy.Getinstance().UniMajorInfoALatestService.getAllUniMajorInfoALatests();


                HashMap<Long, UniMajorInfoBLatestVO>  latestDic = new HashMap<>();
                List<UniMajorInfoBLatestVO> latestList = new ArrayList<>();

                Integer page = 0;
                Integer limit = 100;
                Pageable pageable = PageRequest.of(0, limit);
                Map<String, Object> map =  DBProxy.Getinstance().UniMajorInfoBLatestService.listbyPage(pageable);
//                    List<UniMajorInfoBLatestVO> list = (List<UniMajorInfoBLatestVO>) map.get("items");
                page = (int)map.get("totalPage");


                Date time1 = new Date();
                for (int i = 0; i < page; i++) {
                    Date time1_1 = new Date();

                    pageable = PageRequest.of(i, limit);
                    map =  DBProxy.Getinstance().UniMajorInfoBLatestService.listbyPage(pageable);
                    List<UniMajorInfoBLatestVO>   list = (List<UniMajorInfoBLatestVO>) map.get("items");


                    for (int j = 0; j < list.size(); j++) {
                        latestDic.put(list.get(j).getId(), list.get(j));
                        latestList.add(list.get(j));
                    }

                    Date time1_2 = new Date();
//                        System.out.println("find by page 100 cost: ms " + (time1_2.getTime() - time1_1.getTime())); //40ms

                }
                Date time2 = new Date();
                System.out.println("find latest total cost: ms " + (time2.getTime() - time1.getTime()));



                //year,  unicode, majorCode
                HashMap<String,    HashMap<String,     HashMap<String,   List<UniMajorInfoBVO>> >>  oldeMajorDic = new HashMap<>();

                page = 0;
                limit = 100;
                pageable = PageRequest.of(0, limit);
                Map<String, Object> map2 = DBProxy.Getinstance().UniMajorInfoBService.listbyPage(pageable);
//                    List<UniMajorInfo> list2 = (List<com.msg.UniMajorInfo>) map.get("items");
                page = (int)map2.get("totalPage");

                for (int i = 0; i < page; i++) {
                    Date time2_1 = new Date();

                    pageable = PageRequest.of(i, limit);
                    map2 =  DBProxy.Getinstance().UniMajorInfoBService.listbyPage(pageable);
                    List<UniMajorInfoBVO> list2 = (List<UniMajorInfoBVO>) map2.get("items");


                    //记录 数据
                    for (int j = 0; j < list2.size(); j++) {
                        UniMajorInfoBVO tempMajor = list2.get(j);
                        String year = tempMajor.getExamYear();
                        String uniCode = tempMajor.getUniMajorCode();
                        String majorCode = tempMajor.getMajorCode();

                        HashMap<String,     HashMap<String,   List<UniMajorInfoBVO>> > yearDci = oldeMajorDic.getOrDefault(year, null);
                        if(yearDci == null){
                            yearDci = new HashMap<>();
                            oldeMajorDic.put(year, yearDci);
                        }

                        HashMap<String,   List<UniMajorInfoBVO>>  uniCodeDic = yearDci.getOrDefault(uniCode, null);
                        if(uniCodeDic == null){
                            uniCodeDic = new HashMap<>();
                            yearDci.put(uniCode, uniCodeDic);
                        }


                        List<UniMajorInfoBVO> tempList =   uniCodeDic.getOrDefault(majorCode, null);
                        if(tempList == null){
                            tempList = new ArrayList<>();
                            uniCodeDic.put(majorCode, tempList);
                        }
                        tempList.add(tempMajor);

                    }

                    Date time2_2 = new Date();
//                        System.out.println("find old page 100 cost: ms " + (time2_2.getTime() - time2_1.getTime()));  //160ms

                }

                Date time3 = new Date();
                System.out.println("find old total cost: ms " + (time3.getTime() - time2.getTime()));


                //保存数据
                for (int i = 0; i < latestList.size(); i++) {
                    UniMajorInfoBLatestVO tempMajor = latestList.get(i);

                    String year = tempMajor.getExamYear();
                    String uniCode = tempMajor.getUniMajorCode();
                    String majorCode = tempMajor.getMajorCode();

                    HashMap<String,     HashMap<String,   List<UniMajorInfoBVO>> > yearDci =      oldeMajorDic.getOrDefault(year, null);

                    HashMap<String,   List<UniMajorInfoBVO>> uniCodeDic =  yearDci.getOrDefault(uniCode, null);

                    List<UniMajorInfoBVO>  tempList  = uniCodeDic.getOrDefault(majorCode, null);


                    Date time3_1 = new Date();
                    for (int j = 0; j < tempList.size(); j++) {
                        UniMajorInfoBVO tempOldHistDB = tempList.get(j);
//                                UniMajorInfoBLatestVO UniMajorInfoBLatestVO =  DBProxy.Getinstance().UniMajorInfoBLatestService.findOneById(temp.getId());
//                                UniMajorInfoBVO UniMajorInfoBLatestVO =  DBProxy.Getinstance().UniMajorInfoBService.findOneById(temp.getId());
                        tempOldHistDB.setIdInLatestDB(tempMajor.getIdInHistDB());
                        DBProxy.Getinstance().UniMajorInfoBService.saveUniMajorInfoB(tempOldHistDB);
                    }

                    Date time3_2 = new Date();
//                        System.out.println("find save one latest cost: ms " + (time3_2.getTime() - time3_1.getTime()));   //3ms

                }

                Date time4= new Date();
                System.out.println("save total cost: ms " + (time4.getTime() - time3.getTime()));

            }
            break;
            case PreA:
            {
//                List<UniMajorInfoPreALatestVO>  list = DBProxy.Getinstance().UniMajorInfoPreALatestService.getAllUniMajorInfoPreALatests();
//                for (int i = 0; i < list.size(); i++) {
//                    UniMajorInfoPreALatestVO temp = list.get(i);
//
//                    //更新历史库 把相同专业更新 idInLatestDB 栏位;
//                    List<UniMajorInfoPreAVO> oldHistDB = DBProxy.Getinstance().UniMajorInfoPreAService.findByUniMajorCodeAndMajorCodeAndExamYear(temp.getUniMajorCode(), temp.getMajorCode(), temp.getExamYear());
//                    for (int j = 0; j < oldHistDB.size(); j++) {
//                        UniMajorInfoPreAVO tempOldHistDB = oldHistDB.get(j);
//                        tempOldHistDB.setIdInLatestDB(temp.getIdInHistDB());
//                        DBProxy.Getinstance().UniMajorInfoPreAService.saveUniMajorInfoPreA(tempOldHistDB);
//                    }
//                }

                //                List<UniMajorInfoALatestVO>  list = DBProxy.Getinstance().UniMajorInfoALatestService.getAllUniMajorInfoALatests();


                HashMap<Long, UniMajorInfoPreALatestVO>  latestDic = new HashMap<>();
                List<UniMajorInfoPreALatestVO> latestList = new ArrayList<>();

                Integer page = 0;
                Integer limit = 100;
                Pageable pageable = PageRequest.of(0, limit);
                Map<String, Object> map =  DBProxy.Getinstance().UniMajorInfoPreALatestService.listbyPage(pageable);
//                    List<UniMajorInfoPreALatestVO> list = (List<UniMajorInfoPreALatestVO>) map.get("items");
                page = (int)map.get("totalPage");


                Date time1 = new Date();
                for (int i = 0; i < page; i++) {
                    Date time1_1 = new Date();

                    pageable = PageRequest.of(i, limit);
                    map =  DBProxy.Getinstance().UniMajorInfoPreALatestService.listbyPage(pageable);
                    List<UniMajorInfoPreALatestVO>   list = (List<UniMajorInfoPreALatestVO>) map.get("items");


                    for (int j = 0; j < list.size(); j++) {
                        latestDic.put(list.get(j).getId(), list.get(j));
                        latestList.add(list.get(j));
                    }

                    Date time1_2 = new Date();
//                        System.out.println("find by page 100 cost: ms " + (time1_2.getTime() - time1_1.getTime())); //40ms

                }
                Date time2 = new Date();
                System.out.println("find latest total cost: ms " + (time2.getTime() - time1.getTime()));



                //year,  unicode, majorCode
                HashMap<String,    HashMap<String,     HashMap<String,   List<UniMajorInfoPreAVO>> >>  oldeMajorDic = new HashMap<>();

                page = 0;
                limit = 100;
                pageable = PageRequest.of(0, limit);
                Map<String, Object> map2 = DBProxy.Getinstance().UniMajorInfoPreAService.listbyPage(pageable);
//                    List<UniMajorInfo> list2 = (List<com.msg.UniMajorInfo>) map.get("items");
                page = (int)map2.get("totalPage");

                for (int i = 0; i < page; i++) {
                    Date time2_1 = new Date();

                    pageable = PageRequest.of(i, limit);
                    map2 =  DBProxy.Getinstance().UniMajorInfoPreAService.listbyPage(pageable);
                    List<UniMajorInfoPreAVO> list2 = (List<UniMajorInfoPreAVO>) map2.get("items");


                    //记录 数据
                    for (int j = 0; j < list2.size(); j++) {
                        UniMajorInfoPreAVO tempMajor = list2.get(j);
                        String year = tempMajor.getExamYear();
                        String uniCode = tempMajor.getUniMajorCode();
                        String majorCode = tempMajor.getMajorCode();

                        HashMap<String,     HashMap<String,   List<UniMajorInfoPreAVO>> > yearDci = oldeMajorDic.getOrDefault(year, null);
                        if(yearDci == null){
                            yearDci = new HashMap<>();
                            oldeMajorDic.put(year, yearDci);
                        }

                        HashMap<String,   List<UniMajorInfoPreAVO>>  uniCodeDic = yearDci.getOrDefault(uniCode, null);
                        if(uniCodeDic == null){
                            uniCodeDic = new HashMap<>();
                            yearDci.put(uniCode, uniCodeDic);
                        }


                        List<UniMajorInfoPreAVO> tempList =   uniCodeDic.getOrDefault(majorCode, null);
                        if(tempList == null){
                            tempList = new ArrayList<>();
                            uniCodeDic.put(majorCode, tempList);
                        }
                        tempList.add(tempMajor);

                    }

                    Date time2_2 = new Date();
//                        System.out.println("find old page 100 cost: ms " + (time2_2.getTime() - time2_1.getTime()));  //160ms

                }

                Date time3 = new Date();
                System.out.println("find old total cost: ms " + (time3.getTime() - time2.getTime()));


                //保存数据
                for (int i = 0; i < latestList.size(); i++) {
                    UniMajorInfoPreALatestVO tempMajor = latestList.get(i);

                    String year = tempMajor.getExamYear();
                    String uniCode = tempMajor.getUniMajorCode();
                    String majorCode = tempMajor.getMajorCode();

                    HashMap<String,     HashMap<String,   List<UniMajorInfoPreAVO>> > yearDci =      oldeMajorDic.getOrDefault(year, null);

                    HashMap<String,   List<UniMajorInfoPreAVO>> uniCodeDic =  yearDci.getOrDefault(uniCode, null);

                    List<UniMajorInfoPreAVO>  tempList  = uniCodeDic.getOrDefault(majorCode, null);


                    Date time3_1 = new Date();
                    for (int j = 0; j < tempList.size(); j++) {
                        UniMajorInfoPreAVO tempOldHistDB = tempList.get(j);
//                                UniMajorInfoPreALatestVO UniMajorInfoPreALatestVO =  DBProxy.Getinstance().UniMajorInfoPreALatestService.findOneById(temp.getId());
//                                UniMajorInfoPreAVO UniMajorInfoPreALatestVO =  DBProxy.Getinstance().UniMajorInfoPreAService.findOneById(temp.getId());
                        tempOldHistDB.setIdInLatestDB(tempMajor.getIdInHistDB());
                        DBProxy.Getinstance().UniMajorInfoPreAService.saveUniMajorInfoPreA(tempOldHistDB);
                    }

                    Date time3_2 = new Date();
//                        System.out.println("find save one latest cost: ms " + (time3_2.getTime() - time3_1.getTime()));   //3ms

                }

                Date time4= new Date();
                System.out.println("save total cost: ms " + (time4.getTime() - time3.getTime()));

            }
            break;
            case PreB:
            {
//                List<UniMajorInfoPreBLatestVO>  list = DBProxy.Getinstance().UniMajorInfoPreBLatestService.getAllUniMajorInfoPreBLatests();
//                for (int i = 0; i < list.size(); i++) {
//                    UniMajorInfoPreBLatestVO temp = list.get(i);
//
//                    //更新历史库 把相同专业更新 idInLatestDB 栏位;
//                    List<UniMajorInfoPreBVO> oldHistDB = DBProxy.Getinstance().UniMajorInfoPreBService.findByUniMajorCodeAndMajorCodeAndExamYear(temp.getUniMajorCode(), temp.getMajorCode(), temp.getExamYear());
//                    for (int j = 0; j < oldHistDB.size(); j++) {
//                        UniMajorInfoPreBVO tempOldHistDB = oldHistDB.get(j);
//                        tempOldHistDB.setIdInLatestDB(temp.getIdInHistDB());
//                        DBProxy.Getinstance().UniMajorInfoPreBService.saveUniMajorInfoPreB(tempOldHistDB);
//                    }
//                }

                //                List<UniMajorInfoALatestVO>  list = DBProxy.Getinstance().UniMajorInfoALatestService.getAllUniMajorInfoALatests();


                HashMap<Long, UniMajorInfoPreBLatestVO>  latestDic = new HashMap<>();
                List<UniMajorInfoPreBLatestVO> latestList = new ArrayList<>();

                Integer page = 0;
                Integer limit = 100;
                Pageable pageable = PageRequest.of(0, limit);
                Map<String, Object> map =  DBProxy.Getinstance().UniMajorInfoPreBLatestService.listbyPage(pageable);
//                    List<UniMajorInfoPreBLatestVO> list = (List<UniMajorInfoPreBLatestVO>) map.get("items");
                page = (int)map.get("totalPage");


                Date time1 = new Date();
                for (int i = 0; i < page; i++) {
                    Date time1_1 = new Date();

                    pageable = PageRequest.of(i, limit);
                    map =  DBProxy.Getinstance().UniMajorInfoPreBLatestService.listbyPage(pageable);
                    List<UniMajorInfoPreBLatestVO>   list = (List<UniMajorInfoPreBLatestVO>) map.get("items");


                    for (int j = 0; j < list.size(); j++) {
                        latestDic.put(list.get(j).getId(), list.get(j));
                        latestList.add(list.get(j));
                    }

                    Date time1_2 = new Date();
//                        System.out.println("find by page 100 cost: ms " + (time1_2.getTime() - time1_1.getTime())); //40ms

                }
                Date time2 = new Date();
                System.out.println("find latest total cost: ms " + (time2.getTime() - time1.getTime()));



                //year,  unicode, majorCode
                HashMap<String,    HashMap<String,     HashMap<String,   List<UniMajorInfoPreBVO>> >>  oldeMajorDic = new HashMap<>();

                page = 0;
                limit = 100;
                pageable = PageRequest.of(0, limit);
                Map<String, Object> map2 = DBProxy.Getinstance().UniMajorInfoPreBService.listbyPage(pageable);
//                    List<UniMajorInfo> list2 = (List<com.msg.UniMajorInfo>) map.get("items");
                page = (int)map2.get("totalPage");

                for (int i = 0; i < page; i++) {
                    Date time2_1 = new Date();

                    pageable = PageRequest.of(i, limit);
                    map2 =  DBProxy.Getinstance().UniMajorInfoPreBService.listbyPage(pageable);
                    List<UniMajorInfoPreBVO> list2 = (List<UniMajorInfoPreBVO>) map2.get("items");


                    //记录 数据
                    for (int j = 0; j < list2.size(); j++) {
                        UniMajorInfoPreBVO tempMajor = list2.get(j);
                        String year = tempMajor.getExamYear();
                        String uniCode = tempMajor.getUniMajorCode();
                        String majorCode = tempMajor.getMajorCode();

                        HashMap<String,     HashMap<String,   List<UniMajorInfoPreBVO>> > yearDci = oldeMajorDic.getOrDefault(year, null);
                        if(yearDci == null){
                            yearDci = new HashMap<>();
                            oldeMajorDic.put(year, yearDci);
                        }

                        HashMap<String,   List<UniMajorInfoPreBVO>>  uniCodeDic = yearDci.getOrDefault(uniCode, null);
                        if(uniCodeDic == null){
                            uniCodeDic = new HashMap<>();
                            yearDci.put(uniCode, uniCodeDic);
                        }


                        List<UniMajorInfoPreBVO> tempList =   uniCodeDic.getOrDefault(majorCode, null);
                        if(tempList == null){
                            tempList = new ArrayList<>();
                            uniCodeDic.put(majorCode, tempList);
                        }
                        tempList.add(tempMajor);

                    }

                    Date time2_2 = new Date();
//                        System.out.println("find old page 100 cost: ms " + (time2_2.getTime() - time2_1.getTime()));  //160ms

                }

                Date time3 = new Date();
                System.out.println("find old total cost: ms " + (time3.getTime() - time2.getTime()));


                //保存数据
                for (int i = 0; i < latestList.size(); i++) {
                    UniMajorInfoPreBLatestVO tempMajor = latestList.get(i);

                    String year = tempMajor.getExamYear();
                    String uniCode = tempMajor.getUniMajorCode();
                    String majorCode = tempMajor.getMajorCode();

                    HashMap<String,     HashMap<String,   List<UniMajorInfoPreBVO>> > yearDci =      oldeMajorDic.getOrDefault(year, null);

                    HashMap<String,   List<UniMajorInfoPreBVO>> uniCodeDic =  yearDci.getOrDefault(uniCode, null);

                    List<UniMajorInfoPreBVO>  tempList  = uniCodeDic.getOrDefault(majorCode, null);


                    Date time3_1 = new Date();
                    for (int j = 0; j < tempList.size(); j++) {
                        UniMajorInfoPreBVO tempOldHistDB = tempList.get(j);
//                                UniMajorInfoPreBLatestVO UniMajorInfoPreBLatestVO =  DBProxy.Getinstance().UniMajorInfoPreBLatestService.findOneById(temp.getId());
//                                UniMajorInfoPreBVO UniMajorInfoPreBLatestVO =  DBProxy.Getinstance().UniMajorInfoPreBService.findOneById(temp.getId());
                        tempOldHistDB.setIdInLatestDB(tempMajor.getIdInHistDB());
                        DBProxy.Getinstance().UniMajorInfoPreBService.saveUniMajorInfoPreB(tempOldHistDB);
                    }

                    Date time3_2 = new Date();
//                        System.out.println("find save one latest cost: ms " + (time3_2.getTime() - time3_1.getTime()));   //3ms

                }

                Date time4= new Date();
                System.out.println("save total cost: ms " + (time4.getTime() - time3.getTime()));

            }
            break;
            case ZhuanKe:
            {
//                List<UniMajorInfoZhuanKeLatestVO>  list = DBProxy.Getinstance().UniMajorInfoZhuanKeLatestService.getAllUniMajorInfoZhuanKeLatests();
//                for (int i = 0; i < list.size(); i++) {
//                    UniMajorInfoZhuanKeLatestVO temp = list.get(i);
//
//                    //更新历史库 把相同专业更新 idInLatestDB 栏位;
//                    List<UniMajorInfoZhuanKeVO> oldHistDB = DBProxy.Getinstance().UniMajorInfoZhuanKeService.findByUniMajorCodeAndMajorCodeAndExamYear(temp.getUniMajorCode(), temp.getMajorCode(), temp.getExamYear());
//                    for (int j = 0; j < oldHistDB.size(); j++) {
//                        UniMajorInfoZhuanKeVO tempOldHistDB = oldHistDB.get(j);
//                        tempOldHistDB.setIdInLatestDB(temp.getIdInHistDB());
//                        DBProxy.Getinstance().UniMajorInfoZhuanKeService.saveUniMajorInfoZhuanKe(tempOldHistDB);
//                    }
//                }

                //                List<UniMajorInfoALatestVO>  list = DBProxy.Getinstance().UniMajorInfoALatestService.getAllUniMajorInfoALatests();

                HashMap<Long, UniMajorInfoZhuanKeLatestVO>  latestDic = new HashMap<>();
                List<UniMajorInfoZhuanKeLatestVO> latestList = new ArrayList<>();

                Integer page = 0;
                Integer limit = 100;
                Pageable pageable = PageRequest.of(0, limit);
                Map<String, Object> map =  DBProxy.Getinstance().UniMajorInfoZhuanKeLatestService.listbyPage(pageable);
//                    List<UniMajorInfoZhuanKeLatestVO> list = (List<UniMajorInfoZhuanKeLatestVO>) map.get("items");
                page = (int)map.get("totalPage");


                Date time1 = new Date();
                for (int i = 0; i < page; i++) {
                    Date time1_1 = new Date();

                    pageable = PageRequest.of(i, limit);
                    map =  DBProxy.Getinstance().UniMajorInfoZhuanKeLatestService.listbyPage(pageable);
                    List<UniMajorInfoZhuanKeLatestVO>   list = (List<UniMajorInfoZhuanKeLatestVO>) map.get("items");


                    for (int j = 0; j < list.size(); j++) {
                        latestDic.put(list.get(j).getId(), list.get(j));
                        latestList.add(list.get(j));
                    }

                    Date time1_2 = new Date();
//                        System.out.println("find by page 100 cost: ms " + (time1_2.getTime() - time1_1.getTime())); //40ms

                }
                Date time2 = new Date();
                System.out.println("find latest total cost: ms " + (time2.getTime() - time1.getTime()));



                //year,  unicode, majorCode
                HashMap<String,    HashMap<String,     HashMap<String,   List<UniMajorInfoZhuanKeVO>> >>  oldeMajorDic = new HashMap<>();

                page = 0;
                limit = 100;
                pageable = PageRequest.of(0, limit);
                Map<String, Object> map2 = DBProxy.Getinstance().UniMajorInfoZhuanKeService.listbyPage(pageable);
//                    List<UniMajorInfo> list2 = (List<com.msg.UniMajorInfo>) map.get("items");
                page = (int)map2.get("totalPage");

                for (int i = 0; i < page; i++) {
                    Date time2_1 = new Date();

                    pageable = PageRequest.of(i, limit);
                    map2 =  DBProxy.Getinstance().UniMajorInfoZhuanKeService.listbyPage(pageable);
                    List<UniMajorInfoZhuanKeVO> list2 = (List<UniMajorInfoZhuanKeVO>) map2.get("items");


                    //记录 数据
                    for (int j = 0; j < list2.size(); j++) {
                        UniMajorInfoZhuanKeVO tempMajor = list2.get(j);
                        String year = tempMajor.getExamYear();
                        String uniCode = tempMajor.getUniMajorCode();
                        String majorCode = tempMajor.getMajorCode();

                        HashMap<String,     HashMap<String,   List<UniMajorInfoZhuanKeVO>> > yearDci = oldeMajorDic.getOrDefault(year, null);
                        if(yearDci == null){
                            yearDci = new HashMap<>();
                            oldeMajorDic.put(year, yearDci);
                        }

                        HashMap<String,   List<UniMajorInfoZhuanKeVO>>  uniCodeDic = yearDci.getOrDefault(uniCode, null);
                        if(uniCodeDic == null){
                            uniCodeDic = new HashMap<>();
                            yearDci.put(uniCode, uniCodeDic);
                        }


                        List<UniMajorInfoZhuanKeVO> tempList =   uniCodeDic.getOrDefault(majorCode, null);
                        if(tempList == null){
                            tempList = new ArrayList<>();
                            uniCodeDic.put(majorCode, tempList);
                        }
                        tempList.add(tempMajor);

                    }

                    Date time2_2 = new Date();
//                        System.out.println("find old page 100 cost: ms " + (time2_2.getTime() - time2_1.getTime()));  //160ms

                }

                Date time3 = new Date();
                System.out.println("find old total cost: ms " + (time3.getTime() - time2.getTime()));


                //保存数据
                for (int i = 0; i < latestList.size(); i++) {
                    UniMajorInfoZhuanKeLatestVO tempMajor = latestList.get(i);

                    String year = tempMajor.getExamYear();
                    String uniCode = tempMajor.getUniMajorCode();
                    String majorCode = tempMajor.getMajorCode();

                    HashMap<String,     HashMap<String,   List<UniMajorInfoZhuanKeVO>> > yearDci =      oldeMajorDic.getOrDefault(year, null);

                    HashMap<String,   List<UniMajorInfoZhuanKeVO>> uniCodeDic =  yearDci.getOrDefault(uniCode, null);

                    List<UniMajorInfoZhuanKeVO>  tempList  = uniCodeDic.getOrDefault(majorCode, null);


                    Date time3_1 = new Date();
                    for (int j = 0; j < tempList.size(); j++) {
                        UniMajorInfoZhuanKeVO tempOldHistDB = tempList.get(j);
//                                UniMajorInfoZhuanKeLatestVO UniMajorInfoZhuanKeLatestVO =  DBProxy.Getinstance().UniMajorInfoZhuanKeLatestService.findOneById(temp.getId());
//                                UniMajorInfoZhuanKeVO UniMajorInfoZhuanKeLatestVO =  DBProxy.Getinstance().UniMajorInfoZhuanKeService.findOneById(temp.getId());
                        tempOldHistDB.setIdInLatestDB(tempMajor.getIdInHistDB());
                        DBProxy.Getinstance().UniMajorInfoZhuanKeService.saveUniMajorInfoZhuanKe(tempOldHistDB);
                    }

                    Date time3_2 = new Date();
//                        System.out.println("find save one latest cost: ms " + (time3_2.getTime() - time3_1.getTime()));   //3ms

                }

                Date time4= new Date();
                System.out.println("save total cost: ms " + (time4.getTime() - time3.getTime()));

            }
            break;
            case ZXA:
            {
//                List<UniMajorInfoZXALatestVO>  list = DBProxy.Getinstance().UniMajorInfoZXALatestService.getAllUniMajorInfoZXALatests();
//                for (int i = 0; i < list.size(); i++) {
//                    UniMajorInfoZXALatestVO temp = list.get(i);
//
//                    //更新历史库 把相同专业更新 idInLatestDB 栏位;
//                    List<UniMajorInfoZXAVO> oldHistDB = DBProxy.Getinstance().UniMajorInfoZXAService.findByUniMajorCodeAndMajorCodeAndExamYear(temp.getUniMajorCode(), temp.getMajorCode(), temp.getExamYear());
//                    for (int j = 0; j < oldHistDB.size(); j++) {
//                        UniMajorInfoZXAVO tempOldHistDB = oldHistDB.get(j);
//                        tempOldHistDB.setIdInLatestDB(temp.getIdInHistDB());
//                        DBProxy.Getinstance().UniMajorInfoZXAService.saveUniMajorInfoZXA(tempOldHistDB);
//                    }
//                }

                HashMap<Long, UniMajorInfoZXALatestVO>  latestDic = new HashMap<>();
                List<UniMajorInfoZXALatestVO> latestList = new ArrayList<>();

                Integer page = 0;
                Integer limit = 100;
                Pageable pageable = PageRequest.of(0, limit);
                Map<String, Object> map =  DBProxy.Getinstance().UniMajorInfoZXALatestService.listbyPage(pageable);
//                    List<UniMajorInfoZXALatestVO> list = (List<UniMajorInfoZXALatestVO>) map.get("items");
                page = (int)map.get("totalPage");


                Date time1 = new Date();
                for (int i = 0; i < page; i++) {
                    Date time1_1 = new Date();

                    pageable = PageRequest.of(i, limit);
                    map =  DBProxy.Getinstance().UniMajorInfoZXALatestService.listbyPage(pageable);
                    List<UniMajorInfoZXALatestVO>   list = (List<UniMajorInfoZXALatestVO>) map.get("items");


                    for (int j = 0; j < list.size(); j++) {
                        latestDic.put(list.get(j).getId(), list.get(j));
                        latestList.add(list.get(j));
                    }

                    Date time1_2 = new Date();
//                        System.out.println("find by page 100 cost: ms " + (time1_2.getTime() - time1_1.getTime())); //40ms

                }
                Date time2 = new Date();
                System.out.println("find latest total cost: ms " + (time2.getTime() - time1.getTime()));



                //year,  unicode, majorCode
                HashMap<String,    HashMap<String,     HashMap<String,   List<UniMajorInfoZXAVO>> >>  oldeMajorDic = new HashMap<>();

                page = 0;
                limit = 100;
                pageable = PageRequest.of(0, limit);
                Map<String, Object> map2 = DBProxy.Getinstance().UniMajorInfoZXAService.listbyPage(pageable);
//                    List<UniMajorInfo> list2 = (List<com.msg.UniMajorInfo>) map.get("items");
                page = (int)map2.get("totalPage");

                for (int i = 0; i < page; i++) {
                    Date time2_1 = new Date();

                    pageable = PageRequest.of(i, limit);
                    map2 =  DBProxy.Getinstance().UniMajorInfoZXAService.listbyPage(pageable);
                    List<UniMajorInfoZXAVO> list2 = (List<UniMajorInfoZXAVO>) map2.get("items");


                    //记录 数据
                    for (int j = 0; j < list2.size(); j++) {
                        UniMajorInfoZXAVO tempMajor = list2.get(j);
                        String year = tempMajor.getExamYear();
                        String uniCode = tempMajor.getUniMajorCode();
                        String majorCode = tempMajor.getMajorCode();

                        HashMap<String,     HashMap<String,   List<UniMajorInfoZXAVO>> > yearDci = oldeMajorDic.getOrDefault(year, null);
                        if(yearDci == null){
                            yearDci = new HashMap<>();
                            oldeMajorDic.put(year, yearDci);
                        }

                        HashMap<String,   List<UniMajorInfoZXAVO>>  uniCodeDic = yearDci.getOrDefault(uniCode, null);
                        if(uniCodeDic == null){
                            uniCodeDic = new HashMap<>();
                            yearDci.put(uniCode, uniCodeDic);
                        }


                        List<UniMajorInfoZXAVO> tempList =   uniCodeDic.getOrDefault(majorCode, null);
                        if(tempList == null){
                            tempList = new ArrayList<>();
                            uniCodeDic.put(majorCode, tempList);
                        }
                        tempList.add(tempMajor);

                    }

                    Date time2_2 = new Date();
//                        System.out.println("find old page 100 cost: ms " + (time2_2.getTime() - time2_1.getTime()));  //160ms

                }

                Date time3 = new Date();
                System.out.println("find old total cost: ms " + (time3.getTime() - time2.getTime()));


                //保存数据
                for (int i = 0; i < latestList.size(); i++) {
                    UniMajorInfoZXALatestVO tempMajor = latestList.get(i);

                    String year = tempMajor.getExamYear();
                    String uniCode = tempMajor.getUniMajorCode();
                    String majorCode = tempMajor.getMajorCode();

                    HashMap<String,     HashMap<String,   List<UniMajorInfoZXAVO>> > yearDci =      oldeMajorDic.getOrDefault(year, null);

                    HashMap<String,   List<UniMajorInfoZXAVO>> uniCodeDic =  yearDci.getOrDefault(uniCode, null);

                    List<UniMajorInfoZXAVO>  tempList  = uniCodeDic.getOrDefault(majorCode, null);


                    Date time3_1 = new Date();
                    for (int j = 0; j < tempList.size(); j++) {
                        UniMajorInfoZXAVO tempOldHistDB = tempList.get(j);
//                                UniMajorInfoZXALatestVO UniMajorInfoZXALatestVO =  DBProxy.Getinstance().UniMajorInfoZXALatestService.findOneById(temp.getId());
//                                UniMajorInfoZXAVO UniMajorInfoZXALatestVO =  DBProxy.Getinstance().UniMajorInfoZXAService.findOneById(temp.getId());
                        tempOldHistDB.setIdInLatestDB(tempMajor.getIdInHistDB());
                        DBProxy.Getinstance().UniMajorInfoZXAService.saveUniMajorInfoZXA(tempOldHistDB);
                    }

                    Date time3_2 = new Date();
//                        System.out.println("find save one latest cost: ms " + (time3_2.getTime() - time3_1.getTime()));   //3ms

                }

                Date time4= new Date();
                System.out.println("save total cost: ms " + (time4.getTime() - time3.getTime()));


            }
            break;
            case ZXB:
            {
//                List<UniMajorInfoZXBLatestVO>  list = DBProxy.Getinstance().UniMajorInfoZXBLatestService.getAllUniMajorInfoZXBLatests();
//                for (int i = 0; i < list.size(); i++) {
//                    UniMajorInfoZXBLatestVO temp = list.get(i);
//
//                    //更新历史库 把相同专业更新 idInLatestDB 栏位;
//                    List<UniMajorInfoZXBVO> oldHistDB = DBProxy.Getinstance().UniMajorInfoZXBService.findByUniMajorCodeAndMajorCodeAndExamYear(temp.getUniMajorCode(), temp.getMajorCode(), temp.getExamYear());
//                    for (int j = 0; j < oldHistDB.size(); j++) {
//                        UniMajorInfoZXBVO tempOldHistDB = oldHistDB.get(j);
//                        tempOldHistDB.setIdInLatestDB(temp.getIdInHistDB());
//                        DBProxy.Getinstance().UniMajorInfoZXBService.saveUniMajorInfoZXB(tempOldHistDB);
//                    }
//                }


                HashMap<Long, UniMajorInfoZXBLatestVO>  latestDic = new HashMap<>();
                List<UniMajorInfoZXBLatestVO> latestList = new ArrayList<>();

                Integer page = 0;
                Integer limit = 100;
                Pageable pageable = PageRequest.of(0, limit);
                Map<String, Object> map =  DBProxy.Getinstance().UniMajorInfoZXBLatestService.listbyPage(pageable);
//                    List<UniMajorInfoZXBLatestVO> list = (List<UniMajorInfoZXBLatestVO>) map.get("items");
                page = (int)map.get("totalPage");


                Date time1 = new Date();
                for (int i = 0; i < page; i++) {
                    Date time1_1 = new Date();

                    pageable = PageRequest.of(i, limit);
                    map =  DBProxy.Getinstance().UniMajorInfoZXBLatestService.listbyPage(pageable);
                    List<UniMajorInfoZXBLatestVO>   list = (List<UniMajorInfoZXBLatestVO>) map.get("items");


                    for (int j = 0; j < list.size(); j++) {
                        latestDic.put(list.get(j).getId(), list.get(j));
                        latestList.add(list.get(j));
                    }

                    Date time1_2 = new Date();
//                        System.out.println("find by page 100 cost: ms " + (time1_2.getTime() - time1_1.getTime())); //40ms

                }
                Date time2 = new Date();
                System.out.println("find latest total cost: ms " + (time2.getTime() - time1.getTime()));



                //year,  unicode, majorCode
                HashMap<String,    HashMap<String,     HashMap<String,   List<UniMajorInfoZXBVO>> >>  oldeMajorDic = new HashMap<>();

                page = 0;
                limit = 100;
                pageable = PageRequest.of(0, limit);
                Map<String, Object> map2 = DBProxy.Getinstance().UniMajorInfoZXBService.listbyPage(pageable);
//                    List<UniMajorInfo> list2 = (List<com.msg.UniMajorInfo>) map.get("items");
                page = (int)map2.get("totalPage");

                for (int i = 0; i < page; i++) {
                    Date time2_1 = new Date();

                    pageable = PageRequest.of(i, limit);
                    map2 =  DBProxy.Getinstance().UniMajorInfoZXBService.listbyPage(pageable);
                    List<UniMajorInfoZXBVO> list2 = (List<UniMajorInfoZXBVO>) map2.get("items");


                    //记录 数据
                    for (int j = 0; j < list2.size(); j++) {
                        UniMajorInfoZXBVO tempMajor = list2.get(j);
                        String year = tempMajor.getExamYear();
                        String uniCode = tempMajor.getUniMajorCode();
                        String majorCode = tempMajor.getMajorCode();

                        HashMap<String,     HashMap<String,   List<UniMajorInfoZXBVO>> > yearDci = oldeMajorDic.getOrDefault(year, null);
                        if(yearDci == null){
                            yearDci = new HashMap<>();
                            oldeMajorDic.put(year, yearDci);
                        }

                        HashMap<String,   List<UniMajorInfoZXBVO>>  uniCodeDic = yearDci.getOrDefault(uniCode, null);
                        if(uniCodeDic == null){
                            uniCodeDic = new HashMap<>();
                            yearDci.put(uniCode, uniCodeDic);
                        }


                        List<UniMajorInfoZXBVO> tempList =   uniCodeDic.getOrDefault(majorCode, null);
                        if(tempList == null){
                            tempList = new ArrayList<>();
                            uniCodeDic.put(majorCode, tempList);
                        }
                        tempList.add(tempMajor);

                    }

                    Date time2_2 = new Date();
//                        System.out.println("find old page 100 cost: ms " + (time2_2.getTime() - time2_1.getTime()));  //160ms

                }

                Date time3 = new Date();
                System.out.println("find old total cost: ms " + (time3.getTime() - time2.getTime()));


                //保存数据
                for (int i = 0; i < latestList.size(); i++) {
                    UniMajorInfoZXBLatestVO tempMajor = latestList.get(i);

                    String year = tempMajor.getExamYear();
                    String uniCode = tempMajor.getUniMajorCode();
                    String majorCode = tempMajor.getMajorCode();

                    HashMap<String,     HashMap<String,   List<UniMajorInfoZXBVO>> > yearDci =      oldeMajorDic.getOrDefault(year, null);

                    HashMap<String,   List<UniMajorInfoZXBVO>> uniCodeDic =  yearDci.getOrDefault(uniCode, null);

                    List<UniMajorInfoZXBVO>  tempList  = uniCodeDic.getOrDefault(majorCode, null);


                    Date time3_1 = new Date();
                    for (int j = 0; j < tempList.size(); j++) {
                        UniMajorInfoZXBVO tempOldHistDB = tempList.get(j);
//                                UniMajorInfoZXBLatestVO UniMajorInfoZXBLatestVO =  DBProxy.Getinstance().UniMajorInfoZXBLatestService.findOneById(temp.getId());
//                                UniMajorInfoZXBVO UniMajorInfoZXBLatestVO =  DBProxy.Getinstance().UniMajorInfoZXBService.findOneById(temp.getId());
                        tempOldHistDB.setIdInLatestDB(tempMajor.getIdInHistDB());
                        DBProxy.Getinstance().UniMajorInfoZXBService.saveUniMajorInfoZXB(tempOldHistDB);
                    }

                    Date time3_2 = new Date();
//                        System.out.println("find save one latest cost: ms " + (time3_2.getTime() - time3_1.getTime()));   //3ms

                }

                Date time4= new Date();
                System.out.println("save total cost: ms " + (time4.getTime() - time3.getTime()));

            }
            break;
        }


        Date time2 = new Date();

        System.out.println("init pici " + pici.name() + ", time : "  + ( time2.getTime() - time.getTime()));

    }

    //更新订单管理的  体检限报栏位
    public static void UpdateOrderForPhysicInfo(){
        List<OrderInfoVO>  list = DBProxy.Getinstance().OrderInfoService.getAllOrderInfos();

        for (int i = 0; i < list.size(); i++) {
            OrderInfoVO temp = list.get(i);

            ClientInfoVO clientInfoVO = OrderManager.GetInstance().GetClientByTel(temp.getTel());
            if(clientInfoVO != null){
                System.out.println("client info " + clientInfoVO.getName());
                if(clientInfoVO.getName().equals("郑钧开")){
                    System.out.println("");
                }
                temp.setHasSelPhysicLimit( clientInfoVO.ConvertToDTO().getHasSelectPhysicLimitation());
            }

            DBProxy.Getinstance().OrderInfoService.saveOrderInfo(temp);

        }

    }


    //是否中外合作  中外合作：筛选专业名称列中含有“中外合作”字样的条目
    public static boolean IsZhongWaiHeZuo(String majorInfo){
        return  majorInfo.contains("中外合作");
    }

    //2.本硕：筛选专业名称列中含有“5+3”“8年”“八年”“本硕”字样的条目，以及学制列 大于 五年 的条目
    public static boolean IsBenSuo(String majorInfo, String Duration){
        Integer num = ExtracIntFromWord(Duration);
        return  majorInfo.contains("5+3") ||
                majorInfo.contains("8年") ||
                majorInfo.contains("八年") ||
                majorInfo.contains("本硕") ||
                num > 5 ;
    }

    private static Integer ExtracIntFromWord(String Duration){
        String tempStr = Duration.trim();
        if(StringUtils.isEmpty(tempStr)) return 0;

        Integer num = 0;
        for (int i = 0; i < tempStr.length(); i++) {
//            System.out.println(str.charAt(i));
            String temp = tempStr.substring(i, i+1);
//            char temp = tempStr.charAt(i);

            Integer tempNum = 0;
            if(temp.equals("一" )||temp.equals("1" )){
                tempNum = 1;
            }else if(temp.equals("二" )||temp.equals("2" )){
                tempNum = 2;
            }else if(temp.equals("三" )||temp.equals("3" )){
                tempNum = 3;
            }else if(temp.equals("四" )||temp.equals("4" )){
                tempNum = 4;
            }else if(temp.equals("五" )||temp.equals("5" )){
                tempNum = 5;
            }else if(temp.equals("六" )||temp.equals("6" )){
                tempNum = 6;
            }else if(temp.equals("七" )||temp.equals("7" )){
                tempNum = 7;
            }else if(temp.equals("八" )||temp.equals("8" )){
                tempNum = 8;
            }else if(temp.equals("九" )||temp.equals("9" )){
                tempNum = 9;
            }else if(temp.equals("十" )||temp.equals("10" )){
                tempNum = 10;
            }else {
//                tempNum = 11;
            }

            if(tempNum > num) num = tempNum;
        }

        return num;
    }



    //上传Excel
    public static String UploadExcel(String httpurl, String url, String uniMajorIconUrl, String year, Integer sheetNum, String callbackUrl){

        System.out.println("开始 上传报考系统,  年份:" + year);
        File uniMajorExcel = new File(url);
        File uniMajorIconExcel = new File(uniMajorIconUrl);



        List<String> params = new ArrayList<>();
        params.add(ConstDefine.EXCEL_KEY);
        params.add(ConstDefine.ICONFILE_KEY);

        List<File> files = new ArrayList<>();
        files.add(uniMajorExcel);
        files.add(uniMajorIconExcel);


        Map<String, String> paramKey = new HashMap<>();
        paramKey.put("year", year);
        paramKey.put("sheet", sheetNum + "");
        paramKey.put("callbackUrl", callbackUrl);
        paramKey.put(ConstDefine.MODE_KEY, "1"); //全部更新模式
        paramKey.put(ConstDefine.EXCEL_KEY, uniMajorExcel.getName());
        paramKey.put(ConstDefine.ICONFILE_KEY, uniMajorIconExcel.getName());

        System.out.println("上传中......" );
        String succeed =  HttpClientUtil.doPostFiles(httpurl, params, files,paramKey );
        return succeed;
    }

    //院校池 excel信息 ==> 按Group保存
    public String IncreUpdate(String httpurl,String url, String backupUrl, String uniMajorIconUrl, String year, Integer sheetNum, String callbackUrl) throws IOException, InvalidFormatException, InterruptedException {
        System.out.println("开始 增量更新 报考系统,  年份:" + year);

        File uniMajorExcel = new File(url);
        File uniMajorIconExcel = new File(uniMajorIconUrl);

        //对比 备份的 Excel
        File backupExcel = new File(backupUrl);


        List<String> params = new ArrayList<>();
        params.add(ConstDefine.UpdateEXCEL_KEY);
        params.add(ConstDefine.ICONFILE_KEY);
        params.add(ConstDefine.BackupEXCEL_KEY);

        List<File> files = new ArrayList<>();
        files.add(uniMajorExcel);
        files.add(uniMajorIconExcel);
        files.add(backupExcel);



        Map<String, String> paramKey = new HashMap<>();
        paramKey.put("year", year);
        paramKey.put("sheet", sheetNum + "");
        paramKey.put("callbackUrl", callbackUrl);
        paramKey.put(ConstDefine.MODE_KEY, "2"); //全部更新模式
        paramKey.put(ConstDefine.UpdateEXCEL_KEY, uniMajorExcel.getName());
        paramKey.put(ConstDefine.ICONFILE_KEY, uniMajorIconExcel.getName());
        paramKey.put(ConstDefine.BackupEXCEL_KEY, backupExcel.getName());

        System.out.println("上传中......" );
        String succeed = HttpClientUtil.doPostFiles(httpurl, params, files,paramKey );

        return succeed;
    }

    public static  void UpdateUniMajorLatest_GroupMajorName(EnumPici pici){

        Date time = new Date();
        switch (pici){

            case A:
            {

                Date time1 = new Date();

                Integer limit = Integer.MAX_VALUE;
                Pageable pageable = PageRequest.of(0, limit);
                Map<String, Object> map =  DBProxy.Getinstance().UniMajorInfoALatestService.listbyPage(pageable);
                List<UniMajorInfoALatestVO> list = (List<UniMajorInfoALatestVO>) map.get("items");
//                page = (int)map.get("totalPage");


                List<UniMajorInfoALatest> updateEntityLatestList = new ArrayList<>();
                //按unicode 保存
                HashMap<String, List<UniMajorInfoALatestVO>> tempDic = new HashMap<>();
                for (int i = 0; i < list.size(); i++) {
                    UniMajorInfoALatestVO temp = list.get(i);
                    String uniMajorCode = temp.getUniMajorCode();
                    List<UniMajorInfoALatestVO> uniMajorCodeList =  tempDic.getOrDefault(uniMajorCode, null);
                    if(uniMajorCodeList == null){
                        uniMajorCodeList = new ArrayList<>();
                        tempDic.put(uniMajorCode, uniMajorCodeList);
                    }
                    uniMajorCodeList.add(temp);

//                    updateEntityLatestList.add(BeanMapper.map(list.get(i), UniMajorInfoALatest.class));
                }
                //更新majorName 和 clss id
                for (Map.Entry<String, List<UniMajorInfoALatestVO>> entry: tempDic.entrySet()) {
                    List<UniMajorInfoALatestVO> uniMajorCodeList = entry.getValue();

                    String groupMajorName = "";
                    String groupMajorClsId = "";
                    for (int i = 0; i < uniMajorCodeList.size(); i++) {
                        UniMajorInfoALatestVO tempVO = uniMajorCodeList.get(i);
                        if(!StringUtils.isEmpty(groupMajorName)) groupMajorName += ConstDefine.ItemSperator7;
                        groupMajorName += tempVO.getMajorName();
                        if(!StringUtils.isEmpty(groupMajorClsId)) groupMajorClsId += ConstDefine.ItemSperator7;
                        groupMajorClsId += tempVO.getMajorClsIds();
                    }

                    for (int i = 0; i < uniMajorCodeList.size(); i++) {
                        UniMajorInfoALatestVO tempVO = uniMajorCodeList.get(i);
                        tempVO.setMajorGroupMajorName(groupMajorName);
                        tempVO.setMajorGroupClsIds(groupMajorClsId);
                    }

                }

                for (int i = 0; i < list.size(); i++) {
                    updateEntityLatestList.add(BeanMapper.map(list.get(i), UniMajorInfoALatest.class));
                }

                DBProxy.Getinstance().UniMajorInfoALatestService.batchUpdate(updateEntityLatestList);

                Date time2 = new Date();
                System.out.println("find latest total cost: ms " + (time2.getTime() - time1.getTime()));

            }
            break;
            case B:
            {
                Date time1 = new Date();

                Integer limit = Integer.MAX_VALUE;
                Pageable pageable = PageRequest.of(0, limit);
                Map<String, Object> map =  DBProxy.Getinstance().UniMajorInfoBLatestService.listbyPage(pageable);
                List<UniMajorInfoBLatestVO> list = (List<UniMajorInfoBLatestVO>) map.get("items");
//                page = (int)map.get("totalPage");


                List<UniMajorInfoBLatest> updateEntityLatestList = new ArrayList<>();
                //按unicode 保存
                HashMap<String, List<UniMajorInfoBLatestVO>> tempDic = new HashMap<>();
                for (int i = 0; i < list.size(); i++) {
                    UniMajorInfoBLatestVO temp = list.get(i);
                    String uniMajorCode = temp.getUniMajorCode();
                    List<UniMajorInfoBLatestVO> uniMajorCodeList =  tempDic.getOrDefault(uniMajorCode, null);
                    if(uniMajorCodeList == null){
                        uniMajorCodeList = new ArrayList<>();
                        tempDic.put(uniMajorCode, uniMajorCodeList);
                    }
                    uniMajorCodeList.add(temp);

//                    updateEntityLatestList.add(BeanMapper.map(list.get(i), UniMajorInfoBLatest.class));
                }
                //更新majorName 和 clss id
                for (Map.Entry<String, List<UniMajorInfoBLatestVO>> entry: tempDic.entrySet()) {
                    List<UniMajorInfoBLatestVO> uniMajorCodeList = entry.getValue();

                    String groupMajorName = "";
                    String groupMajorClsId = "";
                    for (int i = 0; i < uniMajorCodeList.size(); i++) {
                        UniMajorInfoBLatestVO tempVO = uniMajorCodeList.get(i);
                        if(!StringUtils.isEmpty(groupMajorName)) groupMajorName += ConstDefine.ItemSperator7;
                        groupMajorName += tempVO.getMajorName();
                        if(!StringUtils.isEmpty(groupMajorClsId)) groupMajorClsId += ConstDefine.ItemSperator7;
                        groupMajorClsId += tempVO.getMajorClsIds();
                    }

                    for (int i = 0; i < uniMajorCodeList.size(); i++) {
                        UniMajorInfoBLatestVO tempVO = uniMajorCodeList.get(i);
                        tempVO.setMajorGroupMajorName(groupMajorName);
                        tempVO.setMajorGroupClsIds(groupMajorClsId);
                    }
                }

                for (int i = 0; i < list.size(); i++) {
                    updateEntityLatestList.add(BeanMapper.map(list.get(i), UniMajorInfoBLatest.class));
                }
                DBProxy.Getinstance().UniMajorInfoBLatestService.batchUpdate(updateEntityLatestList);

                Date time2 = new Date();
                System.out.println("find latest total cost: ms " + (time2.getTime() - time1.getTime()));
            }
            break;
            case PreA:
            {
                Date time1 = new Date();

                Integer limit = Integer.MAX_VALUE;
                Pageable pageable = PageRequest.of(0, limit);
                Map<String, Object> map =  DBProxy.Getinstance().UniMajorInfoPreALatestService.listbyPage(pageable);
                List<UniMajorInfoPreALatestVO> list = (List<UniMajorInfoPreALatestVO>) map.get("items");
//                page = (int)map.get("totalPage");


                List<UniMajorInfoPreALatest> updateEntityLatestList = new ArrayList<>();
                //按unicode 保存
                HashMap<String, List<UniMajorInfoPreALatestVO>> tempDic = new HashMap<>();
                for (int i = 0; i < list.size(); i++) {
                    UniMajorInfoPreALatestVO temp = list.get(i);
                    String uniMajorCode = temp.getUniMajorCode();
                    List<UniMajorInfoPreALatestVO> uniMajorCodeList =  tempDic.getOrDefault(uniMajorCode, null);
                    if(uniMajorCodeList == null){
                        uniMajorCodeList = new ArrayList<>();
                        tempDic.put(uniMajorCode, uniMajorCodeList);
                    }
                    uniMajorCodeList.add(temp);

//                    updateEntityLatestList.add(BeanMapper.map(list.get(i), UniMajorInfoPreALatest.class));
                }
                //更新majorName 和 clss id
                for (Map.Entry<String, List<UniMajorInfoPreALatestVO>> entry: tempDic.entrySet()) {
                    List<UniMajorInfoPreALatestVO> uniMajorCodeList = entry.getValue();

                    String groupMajorName = "";
                    String groupMajorClsId = "";
                    for (int i = 0; i < uniMajorCodeList.size(); i++) {
                        UniMajorInfoPreALatestVO tempVO = uniMajorCodeList.get(i);
                        if(!StringUtils.isEmpty(groupMajorName)) groupMajorName += ConstDefine.ItemSperator7;
                        groupMajorName += tempVO.getMajorName();
                        if(!StringUtils.isEmpty(groupMajorClsId)) groupMajorClsId += ConstDefine.ItemSperator7;
                        groupMajorClsId += tempVO.getMajorClsIds();
                    }

                    for (int i = 0; i < uniMajorCodeList.size(); i++) {
                        UniMajorInfoPreALatestVO tempVO = uniMajorCodeList.get(i);
                        tempVO.setMajorGroupMajorName(groupMajorName);
                        tempVO.setMajorGroupClsIds(groupMajorClsId);
                    }
                }

                for (int i = 0; i < list.size(); i++) {
                    updateEntityLatestList.add(BeanMapper.map(list.get(i), UniMajorInfoPreALatest.class));
                }
                DBProxy.Getinstance().UniMajorInfoPreALatestService.batchUpdate(updateEntityLatestList);

                Date time2 = new Date();
                System.out.println("find latest total cost: ms " + (time2.getTime() - time1.getTime()));

            }
            break;
            case PreB:
            {

                Date time1 = new Date();

                Integer limit = Integer.MAX_VALUE;
                Pageable pageable = PageRequest.of(0, limit);
                Map<String, Object> map =  DBProxy.Getinstance().UniMajorInfoPreBLatestService.listbyPage(pageable);
                List<UniMajorInfoPreBLatestVO> list = (List<UniMajorInfoPreBLatestVO>) map.get("items");
//                page = (int)map.get("totalPage");


                List<UniMajorInfoPreBLatest> updateEntityLatestList = new ArrayList<>();
                //按unicode 保存
                HashMap<String, List<UniMajorInfoPreBLatestVO>> tempDic = new HashMap<>();
                for (int i = 0; i < list.size(); i++) {
                    UniMajorInfoPreBLatestVO temp = list.get(i);
                    String uniMajorCode = temp.getUniMajorCode();
                    List<UniMajorInfoPreBLatestVO> uniMajorCodeList =  tempDic.getOrDefault(uniMajorCode, null);
                    if(uniMajorCodeList == null){
                        uniMajorCodeList = new ArrayList<>();
                        tempDic.put(uniMajorCode, uniMajorCodeList);
                    }
                    uniMajorCodeList.add(temp);

//                    updateEntityLatestList.add(BeanMapper.map(list.get(i), UniMajorInfoPreBLatest.class));
                }
                //更新majorName 和 clss id
                for (Map.Entry<String, List<UniMajorInfoPreBLatestVO>> entry: tempDic.entrySet()) {
                    List<UniMajorInfoPreBLatestVO> uniMajorCodeList = entry.getValue();

                    String groupMajorName = "";
                    String groupMajorClsId = "";
                    for (int i = 0; i < uniMajorCodeList.size(); i++) {
                        UniMajorInfoPreBLatestVO tempVO = uniMajorCodeList.get(i);
                        if(!StringUtils.isEmpty(groupMajorName)) groupMajorName += ConstDefine.ItemSperator7;
                        groupMajorName += tempVO.getMajorName();
                        if(!StringUtils.isEmpty(groupMajorClsId)) groupMajorClsId += ConstDefine.ItemSperator7;
                        groupMajorClsId += tempVO.getMajorClsIds();
                    }

                    for (int i = 0; i < uniMajorCodeList.size(); i++) {
                        UniMajorInfoPreBLatestVO tempVO = uniMajorCodeList.get(i);
                        tempVO.setMajorGroupMajorName(groupMajorName);
                        tempVO.setMajorGroupClsIds(groupMajorClsId);
                    }
                }

                for (int i = 0; i < list.size(); i++) {
                    updateEntityLatestList.add(BeanMapper.map(list.get(i), UniMajorInfoPreBLatest.class));
                }
                DBProxy.Getinstance().UniMajorInfoPreBLatestService.batchUpdate(updateEntityLatestList);

                Date time2 = new Date();
                System.out.println("find latest total cost: ms " + (time2.getTime() - time1.getTime()));

            }
            break;
            case ZhuanKe:
            {
                Date time1 = new Date();

                Integer limit = Integer.MAX_VALUE;
                Pageable pageable = PageRequest.of(0, limit);
                Map<String, Object> map =  DBProxy.Getinstance().UniMajorInfoZhuanKeLatestService.listbyPage(pageable);
                List<UniMajorInfoZhuanKeLatestVO> list = (List<UniMajorInfoZhuanKeLatestVO>) map.get("items");
//                page = (int)map.get("totalPage");


                List<UniMajorInfoZhuanKeLatest> updateEntityLatestList = new ArrayList<>();
                //按unicode 保存
                HashMap<String, List<UniMajorInfoZhuanKeLatestVO>> tempDic = new HashMap<>();
                for (int i = 0; i < list.size(); i++) {
                    UniMajorInfoZhuanKeLatestVO temp = list.get(i);
                    String uniMajorCode = temp.getUniMajorCode();
                    List<UniMajorInfoZhuanKeLatestVO> uniMajorCodeList =  tempDic.getOrDefault(uniMajorCode, null);
                    if(uniMajorCodeList == null){
                        uniMajorCodeList = new ArrayList<>();
                        tempDic.put(uniMajorCode, uniMajorCodeList);
                    }
                    uniMajorCodeList.add(temp);

//                    updateEntityLatestList.add(BeanMapper.map(list.get(i), UniMajorInfoZhuanKeLatest.class));
                }
                //更新majorName 和 clss id
                for (Map.Entry<String, List<UniMajorInfoZhuanKeLatestVO>> entry: tempDic.entrySet()) {
                    List<UniMajorInfoZhuanKeLatestVO> uniMajorCodeList = entry.getValue();

                    String groupMajorName = "";
                    String groupMajorClsId = "";
                    for (int i = 0; i < uniMajorCodeList.size(); i++) {
                        UniMajorInfoZhuanKeLatestVO tempVO = uniMajorCodeList.get(i);
                        if(!StringUtils.isEmpty(groupMajorName)) groupMajorName += ConstDefine.ItemSperator7;
                        groupMajorName += tempVO.getMajorName();
                        if(!StringUtils.isEmpty(groupMajorClsId)) groupMajorClsId += ConstDefine.ItemSperator7;
                        groupMajorClsId += tempVO.getMajorClsIds();
                    }

                    for (int i = 0; i < uniMajorCodeList.size(); i++) {
                        UniMajorInfoZhuanKeLatestVO tempVO = uniMajorCodeList.get(i);
                        tempVO.setMajorGroupMajorName(groupMajorName);
                        tempVO.setMajorGroupClsIds(groupMajorClsId);
                    }
                }


                for (int i = 0; i < list.size(); i++) {
                    updateEntityLatestList.add(BeanMapper.map(list.get(i), UniMajorInfoZhuanKeLatest.class));
                }
                DBProxy.Getinstance().UniMajorInfoZhuanKeLatestService.batchUpdate(updateEntityLatestList);

                Date time2 = new Date();
                System.out.println("find latest total cost: ms " + (time2.getTime() - time1.getTime()));

            }
            break;
            case ZXA:
            {
                Date time1 = new Date();

                Integer limit = Integer.MAX_VALUE;
                Pageable pageable = PageRequest.of(0, limit);
                Map<String, Object> map =  DBProxy.Getinstance().UniMajorInfoZXALatestService.listbyPage(pageable);
                List<UniMajorInfoZXALatestVO> list = (List<UniMajorInfoZXALatestVO>) map.get("items");
//                page = (int)map.get("totalPage");


                List<UniMajorInfoZXALatest> updateEntityLatestList = new ArrayList<>();
                //按unicode 保存
                HashMap<String, List<UniMajorInfoZXALatestVO>> tempDic = new HashMap<>();
                for (int i = 0; i < list.size(); i++) {
                    UniMajorInfoZXALatestVO temp = list.get(i);
                    String uniMajorCode = temp.getUniMajorCode();
                    List<UniMajorInfoZXALatestVO> uniMajorCodeList =  tempDic.getOrDefault(uniMajorCode, null);
                    if(uniMajorCodeList == null){
                        uniMajorCodeList = new ArrayList<>();
                        tempDic.put(uniMajorCode, uniMajorCodeList);
                    }
                    uniMajorCodeList.add(temp);

//                    updateEntityLatestList.add(BeanMapper.map(list.get(i), UniMajorInfoZXALatest.class));
                }
                //更新majorName 和 clss id
                for (Map.Entry<String, List<UniMajorInfoZXALatestVO>> entry: tempDic.entrySet()) {
                    List<UniMajorInfoZXALatestVO> uniMajorCodeList = entry.getValue();

                    String groupMajorName = "";
                    String groupMajorClsId = "";
                    for (int i = 0; i < uniMajorCodeList.size(); i++) {
                        UniMajorInfoZXALatestVO tempVO = uniMajorCodeList.get(i);
                        if(!StringUtils.isEmpty(groupMajorName)) groupMajorName += ConstDefine.ItemSperator7;
                        groupMajorName += tempVO.getMajorName();
                        if(!StringUtils.isEmpty(groupMajorClsId)) groupMajorClsId += ConstDefine.ItemSperator7;
                        groupMajorClsId += tempVO.getMajorClsIds();
                    }

                    for (int i = 0; i < uniMajorCodeList.size(); i++) {
                        UniMajorInfoZXALatestVO tempVO = uniMajorCodeList.get(i);
                        tempVO.setMajorGroupMajorName(groupMajorName);
                        tempVO.setMajorGroupClsIds(groupMajorClsId);
                    }
                }


                for (int i = 0; i < list.size(); i++) {
                    updateEntityLatestList.add(BeanMapper.map(list.get(i), UniMajorInfoZXALatest.class));
                }
                DBProxy.Getinstance().UniMajorInfoZXALatestService.batchUpdate(updateEntityLatestList);

                Date time2 = new Date();
                System.out.println("find latest total cost: ms " + (time2.getTime() - time1.getTime()));
            }
            break;
            case ZXB:
            {
                Date time1 = new Date();

                Integer limit = Integer.MAX_VALUE;
                Pageable pageable = PageRequest.of(0, limit);
                Map<String, Object> map =  DBProxy.Getinstance().UniMajorInfoZXBLatestService.listbyPage(pageable);
                List<UniMajorInfoZXBLatestVO> list = (List<UniMajorInfoZXBLatestVO>) map.get("items");
//                page = (int)map.get("totalPage");


                List<UniMajorInfoZXBLatest> updateEntityLatestList = new ArrayList<>();
                //按unicode 保存
                HashMap<String, List<UniMajorInfoZXBLatestVO>> tempDic = new HashMap<>();
                for (int i = 0; i < list.size(); i++) {
                    UniMajorInfoZXBLatestVO temp = list.get(i);
                    String uniMajorCode = temp.getUniMajorCode();
                    List<UniMajorInfoZXBLatestVO> uniMajorCodeList =  tempDic.getOrDefault(uniMajorCode, null);
                    if(uniMajorCodeList == null){
                        uniMajorCodeList = new ArrayList<>();
                        tempDic.put(uniMajorCode, uniMajorCodeList);
                    }
                    uniMajorCodeList.add(temp);

//                    updateEntityLatestList.add(BeanMapper.map(list.get(i), UniMajorInfoZXBLatest.class));
                }
                //更新majorName 和 clss id
                for (Map.Entry<String, List<UniMajorInfoZXBLatestVO>> entry: tempDic.entrySet()) {
                    List<UniMajorInfoZXBLatestVO> uniMajorCodeList = entry.getValue();

                    String groupMajorName = "";
                    String groupMajorClsId = "";
                    for (int i = 0; i < uniMajorCodeList.size(); i++) {
                        UniMajorInfoZXBLatestVO tempVO = uniMajorCodeList.get(i);
                        if(!StringUtils.isEmpty(groupMajorName)) groupMajorName += ConstDefine.ItemSperator7;
                        groupMajorName += tempVO.getMajorName();
                        if(!StringUtils.isEmpty(groupMajorClsId)) groupMajorClsId += ConstDefine.ItemSperator7;
                        groupMajorClsId += tempVO.getMajorClsIds();
                    }

                    for (int i = 0; i < uniMajorCodeList.size(); i++) {
                        UniMajorInfoZXBLatestVO tempVO = uniMajorCodeList.get(i);
                        tempVO.setMajorGroupMajorName(groupMajorName);
                        tempVO.setMajorGroupClsIds(groupMajorClsId);
                    }
                }

                for (int i = 0; i < list.size(); i++) {
                    updateEntityLatestList.add(BeanMapper.map(list.get(i), UniMajorInfoZXBLatest.class));
                }
                DBProxy.Getinstance().UniMajorInfoZXBLatestService.batchUpdate(updateEntityLatestList);

                Date time2 = new Date();
                System.out.println("find latest total cost: ms " + (time2.getTime() - time1.getTime()));
            }
            break;
        }


        Date time2 = new Date();

        System.out.println("init pici " + pici.name() + ", time : "  + ( time2.getTime() - time.getTime()));

    }
}
