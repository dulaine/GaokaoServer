package com.baizhou;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baizhou.config.FileConfig;
import com.baizhou.config.MajorConfig;
import com.baizhou.config.MergToolConfig;
import com.baizhou.data.constdefine.ConstDefine;
import com.baizhou.data.enumdefine.EnumErrorMsg;
import com.baizhou.data.enumdefine.EnumPici;
import com.baizhou.db.DBProxy;
import com.baizhou.dbtool.DBDataTool;
import com.baizhou.dbtool.MergTool;
import com.baizhou.http.HttpServer;
import com.baizhou.manager.*;
import com.baizhou.util.FileUtils;
import com.baizhou.util.GameUtil;
import com.baizhou.util.HttpClientUtil;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.beans.factory.support.ManagedMap;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.gson.GsonAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.util.StringUtils;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

@SpringBootApplication(exclude = {GsonAutoConfiguration.class})
public class main1 {
    public static void main1(String[] args) throws Exception {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(main.class, args);
        DBProxy.Getinstance().SetApplicationContext(applicationContext);
        DBProxy.Getinstance().InitDBProxy();

        /***********************************************/
        //上传数据
//        String url = "http://192.168.1.32:8131/updateDB";
//        String url = "http://49.232.219.244:8131/updateDB";
        String url = "http://121.37.94.90:8131/updateDB";
//        App2(url);
        App2_1(url);
        /***********************************************/

        /***********************************************/
//        //增量更新 上传数据
////        String url = "http://192.168.1.32:8131/updateDB";
////        String url = "http://49.232.219.244:8131/updateDB";
//        String url = "http://121.37.94.90:8131/updateDB";
////        App2(url);
//        App7_IncreUpdate(url);
        /***********************************************/

        /***********************************************/
        //上传数据 服务器
////        String url = "http://192.168.1.32:8132/tool/postExcel";
////        String callbackUrl = "http://192.168.1.32:8131/updateDB";
//        String url = "http://121.37.94.90:8132/tool/postExcel";
//        String callbackUrl = "http://121.37.94.90:8131/updateDB";
//        App9_UploadExcel(url, callbackUrl);
        /***********************************************/

        /***********************************************/
//        //增量更新 上传数据 服务器
////        String url = "http://192.168.1.32:8132/tool/postExcel";
////        String callbackUrl = "http://192.168.1.32:8131/updateDB";
//        String url = "http://121.37.94.90:8132/tool/postExcel";
//        String callbackUrl = "http://121.37.94.90:8131/updateDB";
//        App10_IncreUpdateExcel(url, callbackUrl);
        /***********************************************/


        /***********************************************/
        //更新 订单的体检限报栏位
//        MajorClsManager.GetInstance().findMajorCls();
//        PhysLimitationManager.GetInstance().InitDB();
//        DBDataTool.UpdateOrderForPhysicInfo();
        /***********************************************/


        /***********************************************/
//        //最近3年成绩, 一次一个
//        APP5_1_Add3YearMajor();
        /***********************************************/


        /**************************************************************/
//        //更新旧数据 中的  latestID
//        DBDataTool.InitCurDB(EnumPici.A);
//        DBDataTool.InitCurDB(EnumPici.B);
//        DBDataTool.InitCurDB(EnumPici.PreA);
//        DBDataTool.InitCurDB(EnumPici.PreB);
//        DBDataTool.InitCurDB(EnumPici.ZhuanKe);
//        DBDataTool.InitCurDB(EnumPici.ZXA);
//        DBDataTool.InitCurDB(EnumPici.ZXB);
        /************************************************************/
//        App3_PhysicLimitation();

//        APP4_Major();

        /**************************************************************/
//        //添加1分一段你工具
//        //        String url = "http://192.168.1.32:8131/updateYFYDDB";
//        String url = "http://121.37.94.90:8131/updateYFYDDB";
//        App6_AddYFYD(url);
        /**************************************************************/

//        /**************************************************************/
//        //更新 latest , group majorName, group majorName
//        DBDataTool.UpdateUniMajorLatest_GroupMajorName(EnumPici.A);
//        DBDataTool.UpdateUniMajorLatest_GroupMajorName(EnumPici.B);
//        DBDataTool.UpdateUniMajorLatest_GroupMajorName(EnumPici.PreA);
//        DBDataTool.UpdateUniMajorLatest_GroupMajorName(EnumPici.PreB);
//        DBDataTool.UpdateUniMajorLatest_GroupMajorName(EnumPici.ZhuanKe);
//        DBDataTool.UpdateUniMajorLatest_GroupMajorName(EnumPici.ZXA);
//        DBDataTool.UpdateUniMajorLatest_GroupMajorName(EnumPici.ZXB);
//
//        /**************************************************************/

        /**************************************************************/
//        //更新最新版本
//        List<String> list = DBProxy.Getinstance().UniMajorInfoALatestService.selectDistinctYear();
//        for (int i = 0; i < list.size(); i++) {
//            String year = list.get(i);
//            if(StringUtils.isEmpty(year)) continue;
//            for (EnumPici pici: EnumPici.values()) {
//                Integer latestPici = DBDataTool.GetInstance().GetLatestVersion(year, pici);
//                UniMajorMgrInMem.GetInstance().SaveLatestVersion(pici, year, latestPici - 1);
//            }
//        }

        /**************************************************************/

    }

    //解析专业信息
    public static void APP4_Major() throws IOException, InvalidFormatException {
         String majorExcelUrl = System.getProperty("user.dir")+"/exceldata/2023本科专业目录.xlsx";
        DBDataTool.GetInstance().LoadFromMajorExcel(majorExcelUrl);

    }

    public static  void App3_PhysicLimitation() throws IOException, InvalidFormatException {

        String uniMajorIconUrl = System.getProperty("user.dir") + "/exceldata/体检限报列表.xlsx";
        DBDataTool.GetInstance().LoadFromPhysicLimiationExcel(uniMajorIconUrl);

    }


    public static void App2(String url) throws InterruptedException, InvalidFormatException, IOException {
//        FileConfig fileConfig = JsonParser.FileConfigParse("./Config/FileConfig.json");


        String json = FileUtils.readFile("./DBConfig.json");
        JSONObject object = JSON.parseObject(json);
        FileConfig fileConfig = object.toJavaObject(FileConfig.class);


        String year = fileConfig.Year.trim();

        String uniMajorUrl = System.getProperty("user.dir") + "/exceldata/NewMajorExcel.xlsx";
        String uniMajorIconUrl = System.getProperty("user.dir") + "/exceldata/9个图表的数据表.xlsx";
        DBDataTool.GetInstance().LoadFromUniExcelByGroup(uniMajorUrl, uniMajorIconUrl, year, -1);


        //do get
//        HttpClientUtil.doGet("http://web.baizhoutj.com:8130/updateDB");
        HttpClientUtil.doGet(url + "?year=" + year);


    }


    public void App1() throws InterruptedException, InvalidFormatException, IOException {
        //        Calendar cal = Calendar.getInstance();
//        int year = cal.get(Calendar.YEAR);
//        System.out.println("当前年份为：" + year);
//        boolean a = true;
//        if(a) return;;

//
//        String orgid = "1";
//        String user="1";
//        String acces="eyJraWQiOiJyc2ExIiwiYWxnIjoiUlMyNTYifQ.eyJzdWIiOiJjcmVjZyZjcmNjMTJ8OTI0MzgyIiwic3lzdGVtSWQiOiIxNTM1MDY3Njg2NDk2MTI0OTI4IiwiYXpwIjoienFqZyIsImlzcyI6Imh0dHA6XC9cL2dnZncuY3IxMmcuY29tLmNuXC9uZXBvY2gtb2lkY1wvIiwiZXhwIjoxNjYwNzI4NDQ0LCJ1c2VyTmFtZSI6IuWNr-WLh-mRqyIsImlhdCI6MTY2MDcyNDg0NCwidXNlcklkIjo5MjQzODIsImp0aSI6IjBiNmMxNGQzLWU3NjYtNGU3Ni1iNDQ1LTM1NDMwM2IwZDZjYyIsInVzZXJDb2RlIjoiY3JjYzEyfDkyNDM4MiJ9.jAYJVwtZY_m_pLhzCrJgouScSLa6gFgl5tF4PirsMQgJeXq-A-v_eLzArG18JIR5THPL7HVrC1VhMzBqYDKfZ0u_T7-kmdHgPsTty9D3FshmWamP6414O_yZweRmN_KMk9XQYY3eu--viqyZ6Cyjibi7EdyG6MdC4eLkTmduXLEwoPG0FOuq3MvbhsIRUtKxMtr51Dc52Sbuk4ErbA3QBAjFzQ3rFM7zCHZSBSmWfxYriYrkde_DgfDSZd2r_aPNNxBpYdZn_u2wwmBKafSVrSaYwjio9EXLvRVHNd6rw6TvgzQ4B0SYapin-zndmmu7-RQBO6qkCULJW1JLAyBvtA";
////        Map<String, String> param = new ManagedMap<>();
////
////        param.put("orgId", orgid);
////        param.put("user", user+ "");
////        Map<String, String> header = new ManagedMap<>();
////        param.put("Authorization", "Bearer " +acces);
////        JSONObject returnRoot = HttpClientUtil.doGetJson(ConstDefine.OAUTH_URL + "pub/orgs/" + orgid + "/next", param, header);
//
//        Map<String, String> param = new ManagedMap<>();
//        param.put("orgDim", "2");
//        Map<String, String> header = new ManagedMap<>();
//        param.put("Authorization", "Bearer " + acces);
//        JSONObject returnRoot = HttpClientUtil.doGetJson(ConstDefine.OAUTH_URL + "pub/orgs/root", param, header);
//
//        boolean ok = returnRoot.getBoolean("ok");
//        if (ok) {
//            JSONArray jsonArray = returnRoot.getJSONArray("data");
//             GameUtil.ConvertToOrgUserInfoDTO(jsonArray);
//        } else {
//            //返回错误
////            errorMsg = EnumErrorMsg.GetWxOpenIdError;
//            String msg = returnRoot.getString("msg");
//            System.out.println("get root error:" + msg);
//        }
//
//        boolean a = true;
//        if(a) return;

//        ConfigManager.GetInstance().Init();
//        GameDataManager.GetInstance().Init();
//        WorkingDayManager.GetInstance().Init();

//        ConfigurableApplicationContext applicationContext = SpringApplication.run(main.class, args);
//        DBProxy.Getinstance().SetApplicationContext(applicationContext);
//        DBProxy.Getinstance().InitDBProxy();

//        boolean useHttp = true;
//        if (useHttp) {
//            ((HttpServer) applicationContext.getBean("HttpServer")).run();
//        } else {
//            ((WebsocketServer) applicationContext.getBean(WebsocketServer.class)).run();
//        }
//        ((HttpProxyServer) applicationContext.getBean(HttpProxyServer.class)).run();


//        String yfydUrl = System.getProperty("user.dir")+"/exceldata/22、21、20年一分一段表对比版(1).xlsx";
//        DBDataTool.GetInstance().LoadFromScoreRankExcel(yfydUrl);
//
//        String majorExcelUrl = System.getProperty("user.dir")+"/exceldata/2023本科专业目录（792条）.xlsx";
//        DBDataTool.GetInstance().LoadFromMajorExcel(majorExcelUrl);


//
////        String uniMajorUrl = System.getProperty("user.dir")+"/exceldata/过滤后数据V8.xlsx";
//        String uniMajorUrl = System.getProperty("user.dir")+"/exceldata/报考版基础数据表V3.xlsx";
        String uniMajorUrl = System.getProperty("user.dir") + "/exceldata/NewMajorExcel.xlsx";
        String uniMajorIconUrl = System.getProperty("user.dir") + "/exceldata/9个图表的数据表.xlsx";
        DBDataTool.GetInstance().LoadFromUniExcel(uniMajorUrl, uniMajorIconUrl);

//        String uniMajorUrl1 = System.getProperty("user.dir") + "/exceldata/major/1.xlsx";
//        String uniMajorUrl2 = System.getProperty("user.dir") + "/exceldata/major/2.xlsx";
//        String uniMajorUrl3 = System.getProperty("user.dir") + "/exceldata/major/3.xlsx";
//        DBDataTool.GetInstance().LoadFrom3YearMajorInfo(uniMajorUrl1, uniMajorUrl2, uniMajorUrl3);

//        HttpServer httpServer = ((HttpServer) applicationContext.getBean("HttpServer"));
//        httpServer.run();
//        WebsocketServer websocketServer = ((WebsocketServer) applicationContext.getBean(WebsocketServer.class));
//
//        Thread th1 = new Thread(httpServer);
//        Thread th2 = new Thread(websocketServer);
//        th1.start();
//        th2.start();


    }

    //最近3年的专业分数
    public static void APP5_Add3YearMajor() throws InterruptedException, InvalidFormatException, IOException {
        String uniMajorUrl1 = System.getProperty("user.dir") + "/exceldata/major/1.xlsx";
        String uniMajorUrl2 = System.getProperty("user.dir") + "/exceldata/major/2.xlsx";
        String uniMajorUrl3 = System.getProperty("user.dir") + "/exceldata/major/3.xlsx";
        DBDataTool.GetInstance().LoadFrom3YearMajorInfo(uniMajorUrl1, uniMajorUrl2, uniMajorUrl3);
    }

    //最近3年的专业分数
    public static void APP5_1_Add3YearMajor() throws InterruptedException, InvalidFormatException, IOException {
        String json = FileUtils.readFile("./MajorConfig.json");
        JSONObject object = JSON.parseObject(json);
        MajorConfig fileConfig = object.toJavaObject(MajorConfig.class);


        String year = fileConfig.Year.trim();
        String path = fileConfig.Path.trim();

//        String uniMajorUrl1 = System.getProperty("user.dir") + "/exceldata/major/1.xlsx";
//        String uniMajorUrl2 = System.getProperty("user.dir") + "/exceldata/major/2.xlsx";
//        String uniMajorUrl3 = System.getProperty("user.dir") + "/exceldata/major/3.xlsx";
        DBDataTool.GetInstance().LoadFrom3YearMajorInfoByOne(path, year);

    }

    public static void App2_1(String url) throws Exception {
//        FileConfig fileConfig = JsonParser.FileConfigParse("./Config/FileConfig.json");


        String json = FileUtils.readFile("./DBConfig.json");
        JSONObject object = JSON.parseObject(json);
        FileConfig fileConfig = object.toJavaObject(FileConfig.class);


        String year = fileConfig.Year.trim();
        Integer sheet = fileConfig.Sheet;

        String uniMajorUrl = fileConfig.NewMajorExcelPath;// System.getProperty("user.dir") + "/NewMajorExcel.xlsx";
        String uniMajorIconUrl = System.getProperty("user.dir") + "/9个图表的数据表.xlsx";
        DBDataTool.GetInstance().LoadFromUniExcelByGroup(uniMajorUrl, uniMajorIconUrl, year, sheet);


        //do get
//        HttpClientUtil.doGet("http://web.baizhoutj.com:8130/updateDB");
        HttpClientUtil.doGet(url + "?year=" + year);


        //利用NewMajorExcel.xlsx ==> 自动生成 back.xlsx  和  updat.xlsx 用与更新
        FileUtils.deleteFile(fileConfig.BackupExcelPath);
        FileUtils.deleteFile(fileConfig.UpdateMajorExcelPath);

        FileUtils.copyFile(uniMajorUrl, fileConfig.BackupExcelPath);
        FileUtils.copyFile(uniMajorUrl, fileConfig.UpdateMajorExcelPath);



    }

    public static void App6_AddYFYD(String url) throws InterruptedException, InvalidFormatException, IOException {
        String json = FileUtils.readFile("./DBConfig.json");
        JSONObject object = JSON.parseObject(json);
        FileConfig fileConfig = object.toJavaObject(FileConfig.class);

        String year = fileConfig.Year.trim();
        String yfydUrl = System.getProperty("user.dir")+"/exceldata/YFYD.xlsx";
        DBDataTool.GetInstance().LoadFromScoreRankExcelByYear(yfydUrl,year);

        HttpClientUtil.doGet(url + "?year=" + year);
    }

    public static void App7_IncreUpdate(String url) throws Exception {
//        FileConfig fileConfig = JsonParser.FileConfigParse("./Config/FileConfig.json");


        String json = FileUtils.readFile("./DBConfig.json");
        JSONObject object = JSON.parseObject(json);
        FileConfig fileConfig = object.toJavaObject(FileConfig.class);


        String year = fileConfig.Year.trim();
        Integer sheet = fileConfig.Sheet;

//        String uniMajorUrl = fileConfig.NewMajorExcelPath;//
        String uniMajorUrl_update = fileConfig.UpdateMajorExcelPath;// System.getProperty("user.dir") + "/NewMajorExcel.xlsx";
        String uniMajorUrl_backup = fileConfig.BackupExcelPath;// System.getProperty("user.dir") + "/NewMajorExcel.xlsx";
        String uniMajorIconUrl = System.getProperty("user.dir") + "/9个图表的数据表.xlsx";
        DBDataTool.GetInstance().LoadFromUniExcelByGroup_IncreUpdate(uniMajorUrl_update, uniMajorUrl_backup, uniMajorIconUrl, year, sheet);


        //do get
//        HttpClientUtil.doGet("http://web.baizhoutj.com:8130/updateDB");
        HttpClientUtil.doGet(url + "?year=" + year);


        //利用 UpdateMajorExcelPath.xlsx ==> 自动生成 back.xlsx
        FileUtils.deleteFile(fileConfig.BackupExcelPath);
//        FileUtils.deleteFile(fileConfig.UpdateMajorExcelPath);
        FileUtils.copyFile(uniMajorUrl_update, fileConfig.BackupExcelPath);
//        FileUtils.copyFile(uniMajorUrl, fileConfig.UpdateMajorExcelPath);



    }


    public static void App8_MergTool(String url) throws InterruptedException, InvalidFormatException, IOException {
//        FileConfig fileConfig = JsonParser.FileConfigParse("./Config/FileConfig.json");


        String json = FileUtils.readFile("./MergToolConfig.json");
        JSONObject object = JSON.parseObject(json);
        MergToolConfig fileConfig = object.toJavaObject(MergToolConfig.class);


        String year = fileConfig.Year.trim();

//        String uniMajorUrl = System.getProperty("user.dir") + "/exceldata/NewMajorExcel.xlsx";
//        String uniMajorIconUrl = System.getProperty("user.dir") + "/exceldata/9个图表的数据表.xlsx";
        MergTool.GetInstance().Build(fileConfig);


//        //do get
////        HttpClientUtil.doGet("http://web.baizhoutj.com:8130/updateDB");
//        HttpClientUtil.doGet(url + "?year=" + year);


    }

    public static void App9_UploadExcel(String url, String callbackUrl) throws Exception {
//        FileConfig fileConfig = JsonParser.FileConfigParse("./Config/FileConfig.json");


        String json = FileUtils.readFile("./DBConfig.json");
        JSONObject object = JSON.parseObject(json);
        FileConfig fileConfig = object.toJavaObject(FileConfig.class);


        String year = fileConfig.Year.trim();
        Integer sheet = fileConfig.Sheet;

        String uniMajorUrl = fileConfig.NewMajorExcelPath;// System.getProperty("user.dir") + "/NewMajorExcel.xlsx";
        String uniMajorIconUrl = System.getProperty("user.dir") + "/9个图表的数据表.xlsx";
        String succeed = DBDataTool.GetInstance().UploadExcel(url, uniMajorUrl, uniMajorIconUrl, year, sheet, callbackUrl);

        boolean isSucceed = false;
        if(succeed.equals("error")){
            isSucceed = false;
        }else {
            JSONObject jsonObject =  JSON.parseObject(succeed);
            Integer status =  jsonObject.getInteger("status");
            String errorMsg =  jsonObject.getString("msg");

            isSucceed = status == 200;
            if(!isSucceed){
                System.out.println(errorMsg);
            }
        }

//        //do get
////        HttpClientUtil.doGet("http://web.baizhoutj.com:8130/updateDB");
//        HttpClientUtil.doGet(callbackUrl + "?year=" + year);

        if(isSucceed){
            //利用NewMajorExcel.xlsx ==> 自动生成 back.xlsx  和  updat.xlsx 用与更新
            FileUtils.deleteFile(fileConfig.BackupExcelPath);
            FileUtils.deleteFile(fileConfig.UpdateMajorExcelPath);

            FileUtils.copyFile(uniMajorUrl, fileConfig.BackupExcelPath);
            FileUtils.copyFile(uniMajorUrl, fileConfig.UpdateMajorExcelPath);
            System.out.println("数据上传完成!!" );
        }else {
            System.out.println("数据上传失败!!" );
        }






    }



    public static void App10_IncreUpdateExcel(String url, String callbackUrl) throws Exception {


        String json = FileUtils.readFile("./DBConfig.json");
        JSONObject object = JSON.parseObject(json);
        FileConfig fileConfig = object.toJavaObject(FileConfig.class);


        String year = fileConfig.Year.trim();
        Integer sheet = fileConfig.Sheet;

//        String uniMajorUrl = fileConfig.NewMajorExcelPath;//
        String uniMajorUrl_update = fileConfig.UpdateMajorExcelPath;// System.getProperty("user.dir") + "/NewMajorExcel.xlsx";
        String uniMajorUrl_backup = fileConfig.BackupExcelPath;// System.getProperty("user.dir") + "/NewMajorExcel.xlsx";
        String uniMajorIconUrl = System.getProperty("user.dir") + "/9个图表的数据表.xlsx";
        String succeed =   DBDataTool.GetInstance().IncreUpdate(url, uniMajorUrl_update, uniMajorUrl_backup, uniMajorIconUrl, year, sheet,callbackUrl);

        boolean isSucceed = false;
        if(succeed.equals("error")){
            isSucceed = false;
        }else {
            JSONObject jsonObject =  JSON.parseObject(succeed);
            Integer status =  jsonObject.getInteger("status");
            String errorMsg =  jsonObject.getString("msg");

            isSucceed = status == 200;
            if(!isSucceed){
                System.out.println(errorMsg);
            }
        }

//        //do get
////        HttpClientUtil.doGet("http://web.baizhoutj.com:8130/updateDB");
//        HttpClientUtil.doGet(url + "?year=" + year);


        if(isSucceed){
            //利用 UpdateMajorExcelPath.xlsx ==> 自动生成 back.xlsx
            FileUtils.deleteFile(fileConfig.BackupExcelPath);
//        FileUtils.deleteFile(fileConfig.UpdateMajorExcelPath);
            FileUtils.copyFile(uniMajorUrl_update, fileConfig.BackupExcelPath);
//        FileUtils.copyFile(uniMajorUrl, fileConfig.UpdateMajorExcelPath);
            System.out.println("数据上传完成!!" );
        }else {
            System.out.println("数据上传失败!!" );
        }






    }
}
