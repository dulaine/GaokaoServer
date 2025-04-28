package com.baizhou;

import com.baizhou.data.constdefine.ConstDefine;
import com.baizhou.db.DBProxy;
import com.baizhou.http.HttpServer;
import com.baizhou.manager.*;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.gson.GsonAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.IOException;
import java.util.Date;

@SpringBootApplication(exclude = {GsonAutoConfiguration.class})
public class main_upload {
    public static void main_upload(String[] args) throws IOException, InvalidFormatException, InterruptedException {
////        Calendar cal = Calendar.getInstance();
////        int year = cal.get(Calendar.YEAR);
////        System.out.println("当前年份为：" + year);
//        String uniMajorIconUrl1 = System.getProperty("user.dir") + "/exceldata/9个图表的数据表.xlsx";
//        String uniMajorIconUrl2 = System.getProperty("user.dir") + "/exceldata/9个图表的数据表-New.xlsx";
////        DBDataTool.GetInstance().LoadFromUniExcel(uniMajorUrl,uniMajorIconUrl);
//        File iconexcel1 = new File(uniMajorIconUrl1);
//        File iconexcel2 = new File(uniMajorIconUrl2);
//        ExcelParser excelParser = new ExcelParser();
//        ExcelTableData iconTable1 = DBDataTool.GetInstance().Parse(iconexcel1, 0, excelParser, 2);
//
//        ExcelTableData iconTable2 = DBDataTool.GetInstance().Parse(iconexcel2, 0, excelParser, 2);
//
//        if (iconTable1.DataRows.size() != iconTable2.DataRows.size()) {
//            System.out.println("no equal len");
//            return;
//        }
//
//        for (int i = 0; i < iconTable1.DataRows.size(); i++) {
//            List<ExcelCellData> Cells1 = iconTable1.DataRows.get(i).Cells;
//            List<ExcelCellData> Cells2 = iconTable2.DataRows.get(i).Cells;
//
//            if (Cells1.size() != Cells2.size()) {
//                System.out.println("no equal cell  len" + Cells1.get(0).Data);
//                return;
//            }
//
//            for (int j = 0; j < Cells1.size(); j++) {
//
//                if (!Cells1.get(j).Data.trim().equals(Cells2.get(j).Data.trim())) {
//                    System.out.println("no equal cell" + Cells1.get(j).Data);
//                    break;
//                }
//
//            }
//        }
//
//
//        boolean a = true;
//        if (a) return;
//        ;

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

        Date time1 = new Date();
        ConfigManager.GetInstance().Init();
        GameDataManager.GetInstance().Init();
        WorkingDayManager.GetInstance().Init();

        ConfigurableApplicationContext applicationContext = SpringApplication.run(main_upload.class, args);
        DBProxy.Getinstance().SetApplicationContext(applicationContext);
        DBProxy.Getinstance().InitDBProxy();

        MajorClsManager.GetInstance().findMajorCls();
        PhysLimitationManager.GetInstance().InitDB();
        if(ConstDefine.NewVersion) UniMajorMgrInMem.GetInstance().InitDBOnStart();
        RankManager.GetInstance().Init();
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
//        String uniMajorIconUrl = System.getProperty("user.dir")+"/exceldata/9个图表的数据表.xlsx";
//        DBDataTool.GetInstance().LoadFromUniExcel(uniMajorUrl,uniMajorIconUrl);

//        String uniMajorUrl1 = System.getProperty("user.dir") + "/exceldata/major/1.xlsx";
//        String uniMajorUrl2 = System.getProperty("user.dir") + "/exceldata/major/2.xlsx";
//        String uniMajorUrl3 = System.getProperty("user.dir") + "/exceldata/major/3.xlsx";
//        DBDataTool.GetInstance().LoadFrom3YearMajorInfo(uniMajorUrl1, uniMajorUrl2, uniMajorUrl3);
        Date time2 = new Date();
        System.out.println("start up cost " + (time2.getTime() - time1.getTime())/1000);

        HttpServer httpServer = ((HttpServer) applicationContext.getBean("HttpServer"));
        httpServer.run();
//        WebsocketServer websocketServer = ((WebsocketServer) applicationContext.getBean(WebsocketServer.class));
//
//        Thread th1 = new Thread(httpServer);
//        Thread th2 = new Thread(websocketServer);
//        th1.start();
//        th2.start();

    }

}
