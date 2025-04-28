package com.baizhou.dbtool.proxy;

import com.baizhou.common.CommonUtil;
import com.baizhou.core.model.entity.*;
import com.baizhou.core.model.vo.*;
import com.baizhou.data.constdefine.ConstDefine;
import com.baizhou.data.enumdefine.EnumPici;
import com.baizhou.db.DBProxy;
import com.baizhou.dbtool.model.ExcelRowData;
import com.baizhou.dbtool.model.ExcelTableData;
import com.baizhou.dbtool.model.Param;
import com.baizhou.dbtool.model.UniMajorParam;
import com.baizhou.gameutil.StringParser;
import com.baizhou.util.BeanMapper;
import com.baizhou.util.GameUtil;
import com.msg.UniMajorInfo;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.util.StringUtils;

import java.io.File;
import java.util.*;

public class UniMajorInfoIncreUpdateParseProx extends UniMajorInfoParseProx {


    //对比2个sheet, 获取更新的row 信息
    public void Compare(ExcelTableData table, ExcelTableData table_backup, Integer sheetIdx,  ExcelTableData iconTable, String year,Integer latestVer) throws InterruptedException {

        Date time1 = new Date();

//        Dic<专业组代码, Dic<专业代码, row>>
        HashMap<String, HashMap<String, ExcelRowData>> UniMajorDic_Update = ConvertExcelToDic(table);
        FixShoolType(UniMajorDic_Update);

        HashMap<String, HashMap<String, ExcelRowData>> UniMajorDic_Backup = ConvertExcelToDic(table_backup);
        FixShoolType(UniMajorDic_Backup);

        Date time2 = new Date();

        this.ResetMajorIconDic(iconTable);//保存图标数据

        EnumPici pici = GameUtil.GetPiciBySheetId(sheetIdx);// EnumPici.A;
//        System.out.println("增量解析院校池 批次 " + pici.name() + " start");

        Date time3 = new Date();
        //todo  对比2个Table,
        HashMap<String, HashMap<String, ExcelRowData>> UniMajorDic_ToBeUpdate = ExtractIncre(UniMajorDic_Update, UniMajorDic_Backup);


        //转成proto 数据
        List<UniMajorInfo> protoList = ConvertToProtoList(UniMajorDic_ToBeUpdate, pici.getStateID(), MajorIcon, year);

        HashMap<String, List<UniMajorInfo>> groupDic = GetGroupInfo(protoList);
        List<UniMajorInfo> reorderedList = ReorderProto(groupDic);

        Date time4 = new Date();

        UpdatePici(reorderedList, 1, pici, year, groupDic);

        Date time5 = new Date();
        System.out.println("UpdatePici cost: ms " + (time5.getTime() - time4.getTime())); //40ms
        System.out.println("UpdateAll cost: ms " + (time5.getTime() - time1.getTime())); //40ms

        System.out.println("增量解析院校池 批次 " + pici.name() + " end");
    }


//    HashMap<String, HashMap<String, ExcelRowData>> UniMajorDic = new HashMap<>(); //Dic<专业组代码, Dic<专业代码, row>>
   //获取ExcelTable的 字典
    private HashMap<String, HashMap<String, ExcelRowData>>  ConvertExcelToDic(ExcelTableData table) {
        HashMap<String, HashMap<String, ExcelRowData>> UniMajorDic = new HashMap<>();

        UniMajorDic.clear();

        for (int i = 0; i < table.DataRows.size(); i++) {
            ExcelRowData row = table.DataRows.get(i);

            String groupCode = row.Cells.get(0).Data.trim(); //专业组代码
            String majorCode = row.Cells.get(8).Data.trim(); //专业代码
            if (groupCode.isEmpty()) continue; //空分数不记录

            HashMap<String, ExcelRowData> majorDic = UniMajorDic.getOrDefault(groupCode, null);
            if (majorDic == null) {
                majorDic = new HashMap<>();
                UniMajorDic.put(groupCode, majorDic);
            }

            ExcelRowData uniMajor = majorDic.getOrDefault(majorCode, null);
            if (uniMajor == null) {
                majorDic.put(majorCode, row);
            } else {
//                System.out.println("专业重复 " + uniMajor.Cells.get(9));
            }

        }

        return UniMajorDic;
    }


    //提取 新增的 row
    private  HashMap<String, HashMap<String, ExcelRowData>> ExtractIncre( HashMap<String, HashMap<String, ExcelRowData>> UniMajorDic_Update,
                                                                          HashMap<String, HashMap<String, ExcelRowData>> UniMajorDic_backup){


        HashMap<String, HashMap<String, ExcelRowData>> temp = new HashMap<>();

        //Dic<专业组代码, Dic<专业代码, row>>
        for (Map.Entry<String, HashMap<String, ExcelRowData>> uniMajors : UniMajorDic_Update.entrySet()) {
            String majorGroupCode = uniMajors.getKey();  //专业组信息

            HashMap<String, ExcelRowData> majorDic = uniMajors.getValue();

            //旧数据中的 对应MajorGroup
            HashMap<String, ExcelRowData> majorDic_Backup = UniMajorDic_backup.getOrDefault(majorGroupCode, null);
            if(majorDic_Backup == null) continue; //如果旧数据没有, 所有的数据 都用于更新


            List<String> unchangedMajorCode = new ArrayList<>();// 没有变化的 Row

            //遍历专业组中的专业
            for (Map.Entry<String, ExcelRowData> major : majorDic.entrySet()) {
                String majorCode = major.getKey();

                ExcelRowData row = major.getValue();

                //旧数据行
                ExcelRowData row_backup =  majorDic_Backup.getOrDefault(majorCode, null);
                if(row_backup == null)  continue;

                //对比row 是否需要update
                boolean needUpdate = CompareRow(row, row_backup);
                if(!needUpdate) unchangedMajorCode.add(majorCode);

//                String schooleNames = (row.Cells.get(1).Data.trim());//学校
//                String schooleTypeofExcel = row.Cells.get(50).Data.trim();//类型
            }


            //删除不需要的row
            for (int i = 0; i < unchangedMajorCode.size(); i++) {
                majorDic.remove(unchangedMajorCode.get(i));
            }

        }


        return UniMajorDic_Update;//temp;
    }
    // 对比行
    private boolean CompareRow(ExcelRowData rowUpdate, ExcelRowData row_backup){
        boolean needUpdate = false;

        if(row_backup.Cells.size() == rowUpdate.Cells.size()){
            for (int i = 0; i < rowUpdate.Cells.size(); i++) {
                if(!rowUpdate.Cells.get(i).Data.trim().equals(row_backup.Cells.get(i).Data.trim())){
                    needUpdate = true;
                    break;
                }
            }
        }else {
            needUpdate = true; //列数量不同
        }


        return needUpdate;
    }

    private void UpdatePici(List<UniMajorInfo> protoList, Integer NewVersion, EnumPici pici, String year,HashMap<String, List<UniMajorInfo>> groupDic) throws InterruptedException {


//        //删除MajorDB 和 GroupDB
//        DeleteUniMajorDB(pici, year);
//
////        Long majorMaxId = 0L;
//        //lateest Major和Group 直接用固定id;   HistDB用自动生成
//        Long majorLatestStartId = 1L;
//        Long groupLatestStartId = 1L;
        System.out.println("更新 " + protoList.size() + "条");

        Long interval = ConstDefine.InsertDBInterval;;// 500L;//100L;//ConstDefine.InsertDBInterval;

        switch (pici){

            case A:
                UpdateUniMajorInfoA(protoList, pici, year);
                break;
            case B:
                UpdateUniMajorInfoB(protoList, pici, year);
                break;
            case PreA:
                UpdateUniMajorInfoPreA(protoList, pici, year);
                break;
            case PreB:
                UpdateUniMajorInfoPreB(protoList, pici, year);
                break;
            case ZhuanKe:
                UpdateUniMajorInfoZhuanKe(protoList, pici, year);
                break;
            case ZXA:
                UpdateUniMajorInfoZXA(protoList, pici, year);
                break;
            case ZXB:
                UpdateUniMajorInfoZXB(protoList, pici, year);
                break;
        }


    }


    //更新批次
    private void UpdatePici_V1(List<UniMajorInfo> protoList, Integer NewVersion, EnumPici pici, String year,HashMap<String, List<UniMajorInfo>> groupDic) throws InterruptedException {


//        //删除MajorDB 和 GroupDB
//        DeleteUniMajorDB(pici, year);
//
////        Long majorMaxId = 0L;
//        //lateest Major和Group 直接用固定id;   HistDB用自动生成
//        Long majorLatestStartId = 1L;
//        Long groupLatestStartId = 1L;

        Long interval = ConstDefine.InsertDBInterval;;// 500L;//100L;//ConstDefine.InsertDBInterval;

        List<String> nonExistDBUniMajorCodes = new ArrayList<>();
        List<String> nonExistDBMajorCodes = new ArrayList<>();

        String lastSaveGroupCode = "";// 最近保存的GroupCode
        for (int i = 0; i < protoList.size(); i++) {
            UniMajorInfo proto = protoList.get(i);


            switch (pici){
                case A:
                {

                    //检测是否有需要更新的数据
                    List<UniMajorInfoALatestVO> latestDBs = DBProxy.Getinstance().UniMajorInfoALatestService.findByUniMajorCodeAndMajorCodeAndExamYear(proto.getUniMajorCode(), proto.getMajorCode(), proto.getExamYear());
                    if(latestDBs.size() == 0){
                        System.out.println("latest DB 不存在 需要更新的数据   " + ":" +  proto.getUniMajorCode() + ":" +  proto.getMajorCode());
                        nonExistDBUniMajorCodes.add(proto.getUniMajorCode());
                        nonExistDBMajorCodes.add(proto.getMajorCode());

                    }else if(latestDBs.size() > 1){
                        System.out.println("latest DB 重复多条数据: " + latestDBs.get(i).getId() + ":" +  proto.getUniMajorCode() + ":" +  proto.getMajorCode());
                    }else {

                        Long idInHist = latestDBs.get(0).getIdInHistDB();

                        //更新latetst数据
                        UniMajorInfoALatestVO latestDB = UniMajorInfoALatestVO.ConvertFromDTO(proto, latestDBs.get(0));
                        DBProxy.Getinstance().UniMajorInfoALatestService.saveUniMajorInfoALatest(latestDB);

                       //更新历史数据
                        UniMajorInfoAVO histDB =  DBProxy.Getinstance().UniMajorInfoAService.findOneById(idInHist);
                        UniMajorInfoAVO histDB_Update = UniMajorInfoAVO.ConvertFromDTO(proto, histDB);
                        DBProxy.Getinstance().UniMajorInfoAService.saveUniMajorInfoA(histDB_Update);

                        System.out.println("更新" + histDB_Update.getMajorName());
                        Thread.sleep(ConstDefine.InsertDBIntervalQuick);
                    }

//
//                    //保存历史库
//                    UniMajorInfoAVO histDB = UniMajorInfoAVO.ConvertFromDTO(proto);
//                    histDB.setId(null);
//                    histDB.setDataVersion(NewVersion);
//                    UniMajorInfoA uniMajorInfoA = DBProxy.Getinstance().UniMajorInfoAService.saveUniMajorInfoA(histDB);
//
//                    //保存最新数据库
//                    UniMajorInfoALatestVO latestDB = UniMajorInfoALatestVO.ConvertFromDTO(proto);
//                    latestDB.setId(null);//(null);
//                    latestDB.setDataVersion(NewVersion);
//                    latestDB.setIdInHistDB(uniMajorInfoA.getId());
//                    DBProxy.Getinstance().UniMajorInfoALatestService.saveUniMajorInfoALatest(latestDB);
//
//
//                    Thread.sleep(ConstDefine.InsertDBIntervalQuick);


                    break;
                }
                case B:
                {
                    //检测是否有需要更新的数据
                    List<UniMajorInfoBLatestVO> latestDBs = DBProxy.Getinstance().UniMajorInfoBLatestService.findByUniMajorCodeAndMajorCodeAndExamYear(proto.getUniMajorCode(), proto.getMajorCode(), proto.getExamYear());
                    if(latestDBs.size() == 0){
                        System.out.println("latest DB 不存在 需要更新的数据   " + ":" +  proto.getUniMajorCode() + ":" +  proto.getMajorCode());
                        nonExistDBUniMajorCodes.add(proto.getUniMajorCode());
                        nonExistDBMajorCodes.add(proto.getMajorCode());

                    }else if(latestDBs.size() > 1){
                        System.out.println("latest DB 重复多条数据: " + latestDBs.get(i).getId() + ":" +  proto.getUniMajorCode() + ":" +  proto.getMajorCode());
                    }else {

                        Long idInHist = latestDBs.get(0).getIdInHistDB();

                        //更新latetst数据
                        UniMajorInfoBLatestVO latestDB = UniMajorInfoBLatestVO.ConvertFromDTO(proto, latestDBs.get(0));
                        DBProxy.Getinstance().UniMajorInfoBLatestService.saveUniMajorInfoBLatest(latestDB);

                        //更新历史数据
                        UniMajorInfoBVO histDB =  DBProxy.Getinstance().UniMajorInfoBService.findOneById(idInHist);
                        UniMajorInfoBVO histDB_Update = UniMajorInfoBVO.ConvertFromDTO(proto, histDB);
                        DBProxy.Getinstance().UniMajorInfoBService.saveUniMajorInfoB(histDB_Update);

                        System.out.println("更新" + histDB_Update.getMajorName());
                        Thread.sleep(ConstDefine.InsertDBIntervalQuick);
                    }


                    break;
                }
                case PreA:
                {
                    //检测是否有需要更新的数据
                    List<UniMajorInfoPreALatestVO> latestDBs = DBProxy.Getinstance().UniMajorInfoPreALatestService.findByUniMajorCodeAndMajorCodeAndExamYear(proto.getUniMajorCode(), proto.getMajorCode(), proto.getExamYear());
                    if(latestDBs.size() == 0){
                        System.out.println("latest DB 不存在 需要更新的数据   " + ":" +  proto.getUniMajorCode() + ":" +  proto.getMajorCode());
                        nonExistDBUniMajorCodes.add(proto.getUniMajorCode());
                        nonExistDBMajorCodes.add(proto.getMajorCode());

                    }else if(latestDBs.size() > 1){
                        System.out.println("latest DB 重复多条数据: " + latestDBs.get(i).getId() + ":" +  proto.getUniMajorCode() + ":" +  proto.getMajorCode());
                    }else {

                        Long idInHist = latestDBs.get(0).getIdInHistDB();

                        //更新latetst数据
                        UniMajorInfoPreALatestVO latestDB = UniMajorInfoPreALatestVO.ConvertFromDTO(proto, latestDBs.get(0));
                        DBProxy.Getinstance().UniMajorInfoPreALatestService.saveUniMajorInfoPreALatest(latestDB);

                        //更新历史数据
                        UniMajorInfoPreAVO histDB =  DBProxy.Getinstance().UniMajorInfoPreAService.findOneById(idInHist);
                        UniMajorInfoPreAVO histDB_Update = UniMajorInfoPreAVO.ConvertFromDTO(proto, histDB);
                        DBProxy.Getinstance().UniMajorInfoPreAService.saveUniMajorInfoPreA(histDB_Update);

                        System.out.println("更新" + histDB_Update.getMajorName());
                        Thread.sleep(ConstDefine.InsertDBIntervalQuick);
                    }


                    break;
                }
                case PreB:
                {
                    //检测是否有需要更新的数据
                    List<UniMajorInfoPreBLatestVO> latestDBs = DBProxy.Getinstance().UniMajorInfoPreBLatestService.findByUniMajorCodeAndMajorCodeAndExamYear(proto.getUniMajorCode(), proto.getMajorCode(), proto.getExamYear());
                    if(latestDBs.size() == 0){
                        System.out.println("latest DB 不存在 需要更新的数据   " + ":" +  proto.getUniMajorCode() + ":" +  proto.getMajorCode());
                        nonExistDBUniMajorCodes.add(proto.getUniMajorCode());
                        nonExistDBMajorCodes.add(proto.getMajorCode());

                    }else if(latestDBs.size() > 1){
                        System.out.println("latest DB 重复多条数据: " + latestDBs.get(i).getId() + ":" +  proto.getUniMajorCode() + ":" +  proto.getMajorCode());
                    }else {

                        Long idInHist = latestDBs.get(0).getIdInHistDB();

                        //更新latetst数据
                        UniMajorInfoPreBLatestVO latestDB = UniMajorInfoPreBLatestVO.ConvertFromDTO(proto, latestDBs.get(0));
                        DBProxy.Getinstance().UniMajorInfoPreBLatestService.saveUniMajorInfoPreBLatest(latestDB);

                        //更新历史数据
                        UniMajorInfoPreBVO histDB =  DBProxy.Getinstance().UniMajorInfoPreBService.findOneById(idInHist);
                        UniMajorInfoPreBVO histDB_Update = UniMajorInfoPreBVO.ConvertFromDTO(proto, histDB);
                        DBProxy.Getinstance().UniMajorInfoPreBService.saveUniMajorInfoPreB(histDB_Update);

                        System.out.println("更新" + histDB_Update.getMajorName());
                        Thread.sleep(ConstDefine.InsertDBIntervalQuick);
                    }

                    break;
                }
                case ZhuanKe:
                {

                    //检测是否有需要更新的数据
                    List<UniMajorInfoZhuanKeLatestVO> latestDBs = DBProxy.Getinstance().UniMajorInfoZhuanKeLatestService.findByUniMajorCodeAndMajorCodeAndExamYear(proto.getUniMajorCode(), proto.getMajorCode(), proto.getExamYear());
                    if(latestDBs.size() == 0){
                        System.out.println("latest DB 不存在 需要更新的数据   " + ":" +  proto.getUniMajorCode() + ":" +  proto.getMajorCode());
                        nonExistDBUniMajorCodes.add(proto.getUniMajorCode());
                        nonExistDBMajorCodes.add(proto.getMajorCode());

                    }else if(latestDBs.size() > 1){
                        System.out.println("latest DB 重复多条数据: " + latestDBs.get(i).getId() + ":" +  proto.getUniMajorCode() + ":" +  proto.getMajorCode());
                    }else {

                        Long idInHist = latestDBs.get(0).getIdInHistDB();

                        //更新latetst数据
                        UniMajorInfoZhuanKeLatestVO latestDB = UniMajorInfoZhuanKeLatestVO.ConvertFromDTO(proto, latestDBs.get(0));
                        DBProxy.Getinstance().UniMajorInfoZhuanKeLatestService.saveUniMajorInfoZhuanKeLatest(latestDB);

                        //更新历史数据
                        UniMajorInfoZhuanKeVO histDB =  DBProxy.Getinstance().UniMajorInfoZhuanKeService.findOneById(idInHist);
                        UniMajorInfoZhuanKeVO histDB_Update = UniMajorInfoZhuanKeVO.ConvertFromDTO(proto, histDB);
                        DBProxy.Getinstance().UniMajorInfoZhuanKeService.saveUniMajorInfoZhuanKe(histDB_Update);

                        System.out.println("更新" + histDB_Update.getMajorName());
                        Thread.sleep(ConstDefine.InsertDBIntervalQuick);
                    }



                    break;
                }
                case ZXA:
                {

                    //检测是否有需要更新的数据
                    List<UniMajorInfoZXALatestVO> latestDBs = DBProxy.Getinstance().UniMajorInfoZXALatestService.findByUniMajorCodeAndMajorCodeAndExamYear(proto.getUniMajorCode(), proto.getMajorCode(), proto.getExamYear());
                    if(latestDBs.size() == 0){
                        System.out.println("latest DB 不存在 需要更新的数据   " + ":" +  proto.getUniMajorCode() + ":" +  proto.getMajorCode());
                        nonExistDBUniMajorCodes.add(proto.getUniMajorCode());
                        nonExistDBMajorCodes.add(proto.getMajorCode());

                    }else if(latestDBs.size() > 1){
                        System.out.println("latest DB 重复多条数据: " + latestDBs.get(i).getId() + ":" +  proto.getUniMajorCode() + ":" +  proto.getMajorCode());
                    }else {

                        Long idInHist = latestDBs.get(0).getIdInHistDB();

                        //更新latetst数据
                        UniMajorInfoZXALatestVO latestDB = UniMajorInfoZXALatestVO.ConvertFromDTO(proto, latestDBs.get(0));
                        DBProxy.Getinstance().UniMajorInfoZXALatestService.saveUniMajorInfoZXALatest(latestDB);

                        //更新历史数据
                        UniMajorInfoZXAVO histDB =  DBProxy.Getinstance().UniMajorInfoZXAService.findOneById(idInHist);
                        UniMajorInfoZXAVO histDB_Update = UniMajorInfoZXAVO.ConvertFromDTO(proto, histDB);
                        DBProxy.Getinstance().UniMajorInfoZXAService.saveUniMajorInfoZXA(histDB_Update);

                        System.out.println("更新" + histDB_Update.getMajorName());
                        Thread.sleep(ConstDefine.InsertDBIntervalQuick);
                    }


                    break;
                }
                case ZXB:
                {
                    //检测是否有需要更新的数据
                    List<UniMajorInfoZXBLatestVO> latestDBs = DBProxy.Getinstance().UniMajorInfoZXBLatestService.findByUniMajorCodeAndMajorCodeAndExamYear(proto.getUniMajorCode(), proto.getMajorCode(), proto.getExamYear());
                    if(latestDBs.size() == 0){
                        System.out.println("latest DB 不存在 需要更新的数据   " + ":" +  proto.getUniMajorCode() + ":" +  proto.getMajorCode());
                        nonExistDBUniMajorCodes.add(proto.getUniMajorCode());
                        nonExistDBMajorCodes.add(proto.getMajorCode());

                    }else if(latestDBs.size() > 1){
                        System.out.println("latest DB 重复多条数据: " + latestDBs.get(i).getId() + ":" +  proto.getUniMajorCode() + ":" +  proto.getMajorCode());
                    }else {

                        Long idInHist = latestDBs.get(0).getIdInHistDB();

                        //更新latetst数据
                        UniMajorInfoZXBLatestVO latestDB = UniMajorInfoZXBLatestVO.ConvertFromDTO(proto, latestDBs.get(0));
                        DBProxy.Getinstance().UniMajorInfoZXBLatestService.saveUniMajorInfoZXBLatest(latestDB);

                        //更新历史数据
                        UniMajorInfoZXBVO histDB =  DBProxy.Getinstance().UniMajorInfoZXBService.findOneById(idInHist);
                        UniMajorInfoZXBVO histDB_Update = UniMajorInfoZXBVO.ConvertFromDTO(proto, histDB);
                        DBProxy.Getinstance().UniMajorInfoZXBService.saveUniMajorInfoZXB(histDB_Update);

                        System.out.println("更新" + histDB_Update.getMajorName());
                        Thread.sleep(ConstDefine.InsertDBIntervalQuick);
                    }

                    break;
                }
            }


        }



//        List<String> nonExistDBUniMajorCodes = new ArrayList<>();
//        List<String> nonExistDBMajorCodes = new ArrayList<>();

        if(nonExistDBUniMajorCodes.size() > 0){
            System.out.println("存在无法更新的数据!!!");

            for (int i = 0; i < nonExistDBMajorCodes.size(); i++) {
                System.out.println("存在无法更新的数据!!!"  + "UniMajorCode: " + nonExistDBUniMajorCodes.get(i) + ", MajorCode: " + nonExistDBMajorCodes.get(i));
            }
        }


    }


    private void UpdateUniMajorInfoA(List<UniMajorInfo> protoList,EnumPici pici, String year){
        List<String> nonExistDBUniMajorCodes = new ArrayList<>();
        List<String> nonExistDBMajorCodes = new ArrayList<>();


        Set<String> unicodes = new HashSet<>(); //记录需要更新的 unicode
        HashMap<String, HashMap<String, UniMajorInfo>>  MajorToUpdateDic = new HashMap<>();
        for (int i = 0; i < protoList.size(); i++) {
            UniMajorInfo proto = protoList.get(i);
//            //保存历史库
//            UniMajorInfoAVO histDB = UniMajorInfoAVO.ConvertFromDTO(proto);
//            histDB.setId(null);
//            histDB.setDataVersion(NewVersion);
//            entityList.add(BeanMapper.map(histDB, UniMajorInfoA.class));

            String uniMajorCode = proto.getUniMajorCode();
            String majorCode = proto.getMajorCode();

            //保存unicode
            unicodes.add(uniMajorCode);

            //保存到字典
            HashMap<String, UniMajorInfo> majorDic = MajorToUpdateDic.getOrDefault(uniMajorCode, null);
            if(majorDic == null){
                majorDic = new HashMap<>();
                MajorToUpdateDic.put(uniMajorCode, majorDic);
            }

            majorDic.put(majorCode, proto);

        }

        //获取 latest 数据
        List<UniMajorInfoALatestVO> latestDBs =  DBProxy.Getinstance().UniMajorInfoALatestService.findByUniMajorCodeInAndExamYear(new ArrayList<>(unicodes), year);
        HashMap<String, HashMap<String, UniMajorInfoALatestVO>>  LatestMajorExistDic = new HashMap<>();

        for (int i = 0; i < latestDBs.size(); i++) {
            UniMajorInfoALatestVO latestVO = latestDBs.get(i);

            String uniMajorCode = latestVO.getUniMajorCode();
            String majorCode = latestVO.getMajorCode();

//            //保存unicode
//            unicodes.add(uniMajorCode);

            //保存到字典
            HashMap<String, UniMajorInfoALatestVO> majorDic = LatestMajorExistDic.getOrDefault(uniMajorCode, null);
            if(majorDic == null){
                majorDic = new HashMap<>();
                LatestMajorExistDic.put(uniMajorCode, majorDic);
            }
            majorDic.put(majorCode, latestVO);
        }

        //获取历史数据
        List<Long> histId = new ArrayList<>();
        for (int i = 0; i < latestDBs.size(); i++) {
            UniMajorInfoALatestVO temp = latestDBs.get(i);
            histId.add(temp.getIdInHistDB());
        }
        List<UniMajorInfoAVO> histInfos =  DBProxy.Getinstance().UniMajorInfoAService.findByIdIn(histId);

        //保存到字典
        HashMap<Long, UniMajorInfoAVO>  HistMajorByIdDic = new HashMap<>();
        for (int i = 0; i < histInfos.size(); i++) {
            HistMajorByIdDic.put(histInfos.get(i).getId(),  histInfos.get(i));
        }


        List<UniMajorInfoA> entityList = new ArrayList<>();
        List<UniMajorInfoALatest> entityLatestList = new ArrayList<>();
        //遍历Update
        for (Map.Entry<String, HashMap<String, UniMajorInfo>>  entry: MajorToUpdateDic.entrySet()) {
            String uniCode = entry.getKey();
            HashMap<String, UniMajorInfo> UniCodeDic = entry.getValue();

            for(Map.Entry<String, UniMajorInfo> majorInfoEntry: UniCodeDic.entrySet()){
                String majorCode = majorInfoEntry.getKey();
                UniMajorInfo  uniMajorInfo = majorInfoEntry.getValue();


                //找到最新的
                HashMap<String, UniMajorInfoALatestVO> tempLatestDic =  LatestMajorExistDic.getOrDefault(uniCode, null);
                if(tempLatestDic == null){
                    System.out.println("latest DB 不存在 需要更新的数据   " + ":" +  uniCode + ":" +  majorCode);
                    nonExistDBUniMajorCodes.add(uniCode);
                    nonExistDBMajorCodes.add(majorCode);
                }else {
                    UniMajorInfoALatestVO  latestTempVO =  tempLatestDic.getOrDefault(majorCode, null);
                    if(latestTempVO == null){
                        System.out.println("latest DB 不存在 需要更新的数据   " + ":" +  uniCode + ":" +  majorCode);
                        nonExistDBUniMajorCodes.add(uniCode);
                        nonExistDBMajorCodes.add(majorCode);
                    }else {
                        //更新latetst数据
                        UniMajorInfoALatestVO latestDB = UniMajorInfoALatestVO.ConvertFromDTO(uniMajorInfo, latestTempVO);

                        //更新历史数据
                        UniMajorInfoAVO histDB = HistMajorByIdDic.getOrDefault(latestDB.getIdInHistDB(), null);
                        UniMajorInfoAVO histDB_Update = UniMajorInfoAVO.ConvertFromDTO(uniMajorInfo, histDB);


                        entityList.add(BeanMapper.map(histDB_Update, UniMajorInfoA.class));
                        entityLatestList.add(BeanMapper.map(latestDB, UniMajorInfoALatest.class));
                    }
                }

            }

        }


        DBProxy.Getinstance().UniMajorInfoAService.batchUpdate(entityList);
        DBProxy.Getinstance().UniMajorInfoALatestService.batchUpdate(entityLatestList);


        if(nonExistDBUniMajorCodes.size() > 0){
            System.out.println("存在无法更新的数据!!!");

            for (int i = 0; i < nonExistDBMajorCodes.size(); i++) {
                System.out.println("存在无法更新的数据!!!"  + "UniMajorCode: " + nonExistDBUniMajorCodes.get(i) + ", MajorCode: " + nonExistDBMajorCodes.get(i));
            }
        }
    }

    private void UpdateUniMajorInfoB(List<UniMajorInfo> protoList,EnumPici pici, String year){
        List<String> nonExistDBUniMajorCodes = new ArrayList<>();
        List<String> nonExistDBMajorCodes = new ArrayList<>();


        Set<String> unicodes = new HashSet<>(); //记录需要更新的 unicode
        HashMap<String, HashMap<String, UniMajorInfo>>  MajorToUpdateDic = new HashMap<>();
        for (int i = 0; i < protoList.size(); i++) {
            UniMajorInfo proto = protoList.get(i);
//            //保存历史库
//            UniMajorInfoBVO histDB = UniMajorInfoBVO.ConvertFromDTO(proto);
//            histDB.setId(null);
//            histDB.setDataVersion(NewVersion);
//            entityList.add(BeanMapper.map(histDB, UniMajorInfoB.class));

            String uniMajorCode = proto.getUniMajorCode();
            String majorCode = proto.getMajorCode();

            //保存unicode
            unicodes.add(uniMajorCode);

            //保存到字典
            HashMap<String, UniMajorInfo> majorDic = MajorToUpdateDic.getOrDefault(uniMajorCode, null);
            if(majorDic == null){
                majorDic = new HashMap<>();
                MajorToUpdateDic.put(uniMajorCode, majorDic);
            }

            majorDic.put(majorCode, proto);

        }

        //获取 latest 数据
        List<UniMajorInfoBLatestVO> latestDBs =  DBProxy.Getinstance().UniMajorInfoBLatestService.findByUniMajorCodeInAndExamYear(new ArrayList<>(unicodes), year);
        HashMap<String, HashMap<String, UniMajorInfoBLatestVO>>  LatestMajorExistDic = new HashMap<>();

        for (int i = 0; i < latestDBs.size(); i++) {
            UniMajorInfoBLatestVO latestVO = latestDBs.get(i);

            String uniMajorCode = latestVO.getUniMajorCode();
            String majorCode = latestVO.getMajorCode();

//            //保存unicode
//            unicodes.add(uniMajorCode);

            //保存到字典
            HashMap<String, UniMajorInfoBLatestVO> majorDic = LatestMajorExistDic.getOrDefault(uniMajorCode, null);
            if(majorDic == null){
                majorDic = new HashMap<>();
                LatestMajorExistDic.put(uniMajorCode, majorDic);
            }
            majorDic.put(majorCode, latestVO);
        }

        //获取历史数据
        List<Long> histId = new ArrayList<>();
        for (int i = 0; i < latestDBs.size(); i++) {
            UniMajorInfoBLatestVO temp = latestDBs.get(i);
            histId.add(temp.getIdInHistDB());
        }
        List<UniMajorInfoBVO> histInfos =  DBProxy.Getinstance().UniMajorInfoBService.findByIdIn(histId);

        //保存到字典
        HashMap<Long, UniMajorInfoBVO>  HistMajorByIdDic = new HashMap<>();
        for (int i = 0; i < histInfos.size(); i++) {
            HistMajorByIdDic.put(histInfos.get(i).getId(),  histInfos.get(i));
        }


        List<UniMajorInfoB> entityList = new ArrayList<>();
        List<UniMajorInfoBLatest> entityLatestList = new ArrayList<>();
        //遍历Update
        for (Map.Entry<String, HashMap<String, UniMajorInfo>>  entry: MajorToUpdateDic.entrySet()) {
            String uniCode = entry.getKey();
            HashMap<String, UniMajorInfo> UniCodeDic = entry.getValue();

            for(Map.Entry<String, UniMajorInfo> majorInfoEntry: UniCodeDic.entrySet()){
                String majorCode = majorInfoEntry.getKey();
                UniMajorInfo  uniMajorInfo = majorInfoEntry.getValue();


                //找到最新的
                HashMap<String, UniMajorInfoBLatestVO> tempLatestDic =  LatestMajorExistDic.getOrDefault(uniCode, null);
                if(tempLatestDic == null){
                    System.out.println("latest DB 不存在 需要更新的数据   " + ":" +  uniCode + ":" +  majorCode);
                    nonExistDBUniMajorCodes.add(uniCode);
                    nonExistDBMajorCodes.add(majorCode);
                }else {
                    UniMajorInfoBLatestVO  latestTempVO =  tempLatestDic.getOrDefault(majorCode, null);
                    if(latestTempVO == null){
                        System.out.println("latest DB 不存在 需要更新的数据   " + ":" +  uniCode + ":" +  majorCode);
                        nonExistDBUniMajorCodes.add(uniCode);
                        nonExistDBMajorCodes.add(majorCode);
                    }else {
                        //更新latetst数据
                        UniMajorInfoBLatestVO latestDB = UniMajorInfoBLatestVO.ConvertFromDTO(uniMajorInfo, latestTempVO);

                        //更新历史数据
                        UniMajorInfoBVO histDB = HistMajorByIdDic.getOrDefault(latestDB.getIdInHistDB(), null);
                        UniMajorInfoBVO histDB_Update = UniMajorInfoBVO.ConvertFromDTO(uniMajorInfo, histDB);


                        entityList.add(BeanMapper.map(histDB_Update, UniMajorInfoB.class));
                        entityLatestList.add(BeanMapper.map(latestDB, UniMajorInfoBLatest.class));
                    }
                }

            }

        }


        DBProxy.Getinstance().UniMajorInfoBService.batchUpdate(entityList);
        DBProxy.Getinstance().UniMajorInfoBLatestService.batchUpdate(entityLatestList);


        if(nonExistDBUniMajorCodes.size() > 0){
            System.out.println("存在无法更新的数据!!!");

            for (int i = 0; i < nonExistDBMajorCodes.size(); i++) {
                System.out.println("存在无法更新的数据!!!"  + "UniMajorCode: " + nonExistDBUniMajorCodes.get(i) + ", MajorCode: " + nonExistDBMajorCodes.get(i));
            }
        }
    }


    private void UpdateUniMajorInfoPreA(List<UniMajorInfo> protoList,EnumPici pici, String year){
        List<String> nonExistDBUniMajorCodes = new ArrayList<>();
        List<String> nonExistDBMajorCodes = new ArrayList<>();


        Set<String> unicodes = new HashSet<>(); //记录需要更新的 unicode
        HashMap<String, HashMap<String, UniMajorInfo>>  MajorToUpdateDic = new HashMap<>();
        for (int i = 0; i < protoList.size(); i++) {
            UniMajorInfo proto = protoList.get(i);
//            //保存历史库
//            UniMajorInfoPreAVO histDB = UniMajorInfoPreAVO.ConvertFromDTO(proto);
//            histDB.setId(null);
//            histDB.setDataVersion(NewVersion);
//            entityList.add(BeanMapper.map(histDB, UniMajorInfoPreA.class));

            String uniMajorCode = proto.getUniMajorCode();
            String majorCode = proto.getMajorCode();

            //保存unicode
            unicodes.add(uniMajorCode);

            //保存到字典
            HashMap<String, UniMajorInfo> majorDic = MajorToUpdateDic.getOrDefault(uniMajorCode, null);
            if(majorDic == null){
                majorDic = new HashMap<>();
                MajorToUpdateDic.put(uniMajorCode, majorDic);
            }

            majorDic.put(majorCode, proto);

        }

        //获取 latest 数据
        List<UniMajorInfoPreALatestVO> latestDBs =  DBProxy.Getinstance().UniMajorInfoPreALatestService.findByUniMajorCodeInAndExamYear(new ArrayList<>(unicodes), year);
        HashMap<String, HashMap<String, UniMajorInfoPreALatestVO>>  LatestMajorExistDic = new HashMap<>();

        for (int i = 0; i < latestDBs.size(); i++) {
            UniMajorInfoPreALatestVO latestVO = latestDBs.get(i);

            String uniMajorCode = latestVO.getUniMajorCode();
            String majorCode = latestVO.getMajorCode();

//            //保存unicode
//            unicodes.add(uniMajorCode);

            //保存到字典
            HashMap<String, UniMajorInfoPreALatestVO> majorDic = LatestMajorExistDic.getOrDefault(uniMajorCode, null);
            if(majorDic == null){
                majorDic = new HashMap<>();
                LatestMajorExistDic.put(uniMajorCode, majorDic);
            }
            majorDic.put(majorCode, latestVO);
        }

        //获取历史数据
        List<Long> histId = new ArrayList<>();
        for (int i = 0; i < latestDBs.size(); i++) {
            UniMajorInfoPreALatestVO temp = latestDBs.get(i);
            histId.add(temp.getIdInHistDB());
        }
        List<UniMajorInfoPreAVO> histInfos =  DBProxy.Getinstance().UniMajorInfoPreAService.findByIdIn(histId);

        //保存到字典
        HashMap<Long, UniMajorInfoPreAVO>  HistMajorByIdDic = new HashMap<>();
        for (int i = 0; i < histInfos.size(); i++) {
            HistMajorByIdDic.put(histInfos.get(i).getId(),  histInfos.get(i));
        }


        List<UniMajorInfoPreA> entityList = new ArrayList<>();
        List<UniMajorInfoPreALatest> entityLatestList = new ArrayList<>();
        //遍历Update
        for (Map.Entry<String, HashMap<String, UniMajorInfo>>  entry: MajorToUpdateDic.entrySet()) {
            String uniCode = entry.getKey();
            HashMap<String, UniMajorInfo> UniCodeDic = entry.getValue();

            for(Map.Entry<String, UniMajorInfo> majorInfoEntry: UniCodeDic.entrySet()){
                String majorCode = majorInfoEntry.getKey();
                UniMajorInfo  uniMajorInfo = majorInfoEntry.getValue();


                //找到最新的
                HashMap<String, UniMajorInfoPreALatestVO> tempLatestDic =  LatestMajorExistDic.getOrDefault(uniCode, null);
                if(tempLatestDic == null){
                    System.out.println("latest DB 不存在 需要更新的数据   " + ":" +  uniCode + ":" +  majorCode);
                    nonExistDBUniMajorCodes.add(uniCode);
                    nonExistDBMajorCodes.add(majorCode);
                }else {
                    UniMajorInfoPreALatestVO  latestTempVO =  tempLatestDic.getOrDefault(majorCode, null);
                    if(latestTempVO == null){
                        System.out.println("latest DB 不存在 需要更新的数据   " + ":" +  uniCode + ":" +  majorCode);
                        nonExistDBUniMajorCodes.add(uniCode);
                        nonExistDBMajorCodes.add(majorCode);
                    }else {
                        //更新latetst数据
                        UniMajorInfoPreALatestVO latestDB = UniMajorInfoPreALatestVO.ConvertFromDTO(uniMajorInfo, latestTempVO);

                        //更新历史数据
                        UniMajorInfoPreAVO histDB = HistMajorByIdDic.getOrDefault(latestDB.getIdInHistDB(), null);
                        UniMajorInfoPreAVO histDB_Update = UniMajorInfoPreAVO.ConvertFromDTO(uniMajorInfo, histDB);


                        entityList.add(BeanMapper.map(histDB_Update, UniMajorInfoPreA.class));
                        entityLatestList.add(BeanMapper.map(latestDB, UniMajorInfoPreALatest.class));
                    }
                }

            }

        }


        DBProxy.Getinstance().UniMajorInfoPreAService.batchUpdate(entityList);
        DBProxy.Getinstance().UniMajorInfoPreALatestService.batchUpdate(entityLatestList);


        if(nonExistDBUniMajorCodes.size() > 0){
            System.out.println("存在无法更新的数据!!!");

            for (int i = 0; i < nonExistDBMajorCodes.size(); i++) {
                System.out.println("存在无法更新的数据!!!"  + "UniMajorCode: " + nonExistDBUniMajorCodes.get(i) + ", MajorCode: " + nonExistDBMajorCodes.get(i));
            }
        }
    }


    private void UpdateUniMajorInfoPreB(List<UniMajorInfo> protoList,EnumPici pici, String year){
        List<String> nonExistDBUniMajorCodes = new ArrayList<>();
        List<String> nonExistDBMajorCodes = new ArrayList<>();


        Set<String> unicodes = new HashSet<>(); //记录需要更新的 unicode
        HashMap<String, HashMap<String, UniMajorInfo>>  MajorToUpdateDic = new HashMap<>();
        for (int i = 0; i < protoList.size(); i++) {
            UniMajorInfo proto = protoList.get(i);
//            //保存历史库
//            UniMajorInfoPreBVO histDB = UniMajorInfoPreBVO.ConvertFromDTO(proto);
//            histDB.setId(null);
//            histDB.setDataVersion(NewVersion);
//            entityList.add(BeanMapper.map(histDB, UniMajorInfoPreB.class));

            String uniMajorCode = proto.getUniMajorCode();
            String majorCode = proto.getMajorCode();

            //保存unicode
            unicodes.add(uniMajorCode);

            //保存到字典
            HashMap<String, UniMajorInfo> majorDic = MajorToUpdateDic.getOrDefault(uniMajorCode, null);
            if(majorDic == null){
                majorDic = new HashMap<>();
                MajorToUpdateDic.put(uniMajorCode, majorDic);
            }

            majorDic.put(majorCode, proto);

        }

        //获取 latest 数据
        List<UniMajorInfoPreBLatestVO> latestDBs =  DBProxy.Getinstance().UniMajorInfoPreBLatestService.findByUniMajorCodeInAndExamYear(new ArrayList<>(unicodes), year);
        HashMap<String, HashMap<String, UniMajorInfoPreBLatestVO>>  LatestMajorExistDic = new HashMap<>();

        for (int i = 0; i < latestDBs.size(); i++) {
            UniMajorInfoPreBLatestVO latestVO = latestDBs.get(i);

            String uniMajorCode = latestVO.getUniMajorCode();
            String majorCode = latestVO.getMajorCode();

//            //保存unicode
//            unicodes.add(uniMajorCode);

            //保存到字典
            HashMap<String, UniMajorInfoPreBLatestVO> majorDic = LatestMajorExistDic.getOrDefault(uniMajorCode, null);
            if(majorDic == null){
                majorDic = new HashMap<>();
                LatestMajorExistDic.put(uniMajorCode, majorDic);
            }
            majorDic.put(majorCode, latestVO);
        }

        //获取历史数据
        List<Long> histId = new ArrayList<>();
        for (int i = 0; i < latestDBs.size(); i++) {
            UniMajorInfoPreBLatestVO temp = latestDBs.get(i);
            histId.add(temp.getIdInHistDB());
        }
        List<UniMajorInfoPreBVO> histInfos =  DBProxy.Getinstance().UniMajorInfoPreBService.findByIdIn(histId);

        //保存到字典
        HashMap<Long, UniMajorInfoPreBVO>  HistMajorByIdDic = new HashMap<>();
        for (int i = 0; i < histInfos.size(); i++) {
            HistMajorByIdDic.put(histInfos.get(i).getId(),  histInfos.get(i));
        }


        List<UniMajorInfoPreB> entityList = new ArrayList<>();
        List<UniMajorInfoPreBLatest> entityLatestList = new ArrayList<>();
        //遍历Update
        for (Map.Entry<String, HashMap<String, UniMajorInfo>>  entry: MajorToUpdateDic.entrySet()) {
            String uniCode = entry.getKey();
            HashMap<String, UniMajorInfo> UniCodeDic = entry.getValue();

            for(Map.Entry<String, UniMajorInfo> majorInfoEntry: UniCodeDic.entrySet()){
                String majorCode = majorInfoEntry.getKey();
                UniMajorInfo  uniMajorInfo = majorInfoEntry.getValue();


                //找到最新的
                HashMap<String, UniMajorInfoPreBLatestVO> tempLatestDic =  LatestMajorExistDic.getOrDefault(uniCode, null);
                if(tempLatestDic == null){
                    System.out.println("latest DB 不存在 需要更新的数据   " + ":" +  uniCode + ":" +  majorCode);
                    nonExistDBUniMajorCodes.add(uniCode);
                    nonExistDBMajorCodes.add(majorCode);
                }else {
                    UniMajorInfoPreBLatestVO  latestTempVO =  tempLatestDic.getOrDefault(majorCode, null);
                    if(latestTempVO == null){
                        System.out.println("latest DB 不存在 需要更新的数据   " + ":" +  uniCode + ":" +  majorCode);
                        nonExistDBUniMajorCodes.add(uniCode);
                        nonExistDBMajorCodes.add(majorCode);
                    }else {
                        //更新latetst数据
                        UniMajorInfoPreBLatestVO latestDB = UniMajorInfoPreBLatestVO.ConvertFromDTO(uniMajorInfo, latestTempVO);

                        //更新历史数据
                        UniMajorInfoPreBVO histDB = HistMajorByIdDic.getOrDefault(latestDB.getIdInHistDB(), null);
                        UniMajorInfoPreBVO histDB_Update = UniMajorInfoPreBVO.ConvertFromDTO(uniMajorInfo, histDB);


                        entityList.add(BeanMapper.map(histDB_Update, UniMajorInfoPreB.class));
                        entityLatestList.add(BeanMapper.map(latestDB, UniMajorInfoPreBLatest.class));
                    }
                }

            }

        }


        DBProxy.Getinstance().UniMajorInfoPreBService.batchUpdate(entityList);
        DBProxy.Getinstance().UniMajorInfoPreBLatestService.batchUpdate(entityLatestList);


        if(nonExistDBUniMajorCodes.size() > 0){
            System.out.println("存在无法更新的数据!!!");

            for (int i = 0; i < nonExistDBMajorCodes.size(); i++) {
                System.out.println("存在无法更新的数据!!!"  + "UniMajorCode: " + nonExistDBUniMajorCodes.get(i) + ", MajorCode: " + nonExistDBMajorCodes.get(i));
            }
        }
    }


    private void UpdateUniMajorInfoZhuanKe(List<UniMajorInfo> protoList,EnumPici pici, String year){
        List<String> nonExistDBUniMajorCodes = new ArrayList<>();
        List<String> nonExistDBMajorCodes = new ArrayList<>();


        Set<String> unicodes = new HashSet<>(); //记录需要更新的 unicode
        HashMap<String, HashMap<String, UniMajorInfo>>  MajorToUpdateDic = new HashMap<>();
        for (int i = 0; i < protoList.size(); i++) {
            UniMajorInfo proto = protoList.get(i);
//            //保存历史库
//            UniMajorInfoZhuanKeVO histDB = UniMajorInfoZhuanKeVO.ConvertFromDTO(proto);
//            histDB.setId(null);
//            histDB.setDataVersion(NewVersion);
//            entityList.add(BeanMapper.map(histDB, UniMajorInfoZhuanKe.class));

            String uniMajorCode = proto.getUniMajorCode();
            String majorCode = proto.getMajorCode();

            //保存unicode
            unicodes.add(uniMajorCode);

            //保存到字典
            HashMap<String, UniMajorInfo> majorDic = MajorToUpdateDic.getOrDefault(uniMajorCode, null);
            if(majorDic == null){
                majorDic = new HashMap<>();
                MajorToUpdateDic.put(uniMajorCode, majorDic);
            }

            majorDic.put(majorCode, proto);

        }

        //获取 latest 数据
        List<UniMajorInfoZhuanKeLatestVO> latestDBs =  DBProxy.Getinstance().UniMajorInfoZhuanKeLatestService.findByUniMajorCodeInAndExamYear(new ArrayList<>(unicodes), year);
        HashMap<String, HashMap<String, UniMajorInfoZhuanKeLatestVO>>  LatestMajorExistDic = new HashMap<>();

        for (int i = 0; i < latestDBs.size(); i++) {
            UniMajorInfoZhuanKeLatestVO latestVO = latestDBs.get(i);

            String uniMajorCode = latestVO.getUniMajorCode();
            String majorCode = latestVO.getMajorCode();

//            //保存unicode
//            unicodes.add(uniMajorCode);

            //保存到字典
            HashMap<String, UniMajorInfoZhuanKeLatestVO> majorDic = LatestMajorExistDic.getOrDefault(uniMajorCode, null);
            if(majorDic == null){
                majorDic = new HashMap<>();
                LatestMajorExistDic.put(uniMajorCode, majorDic);
            }
            majorDic.put(majorCode, latestVO);
        }

        //获取历史数据
        List<Long> histId = new ArrayList<>();
        for (int i = 0; i < latestDBs.size(); i++) {
            UniMajorInfoZhuanKeLatestVO temp = latestDBs.get(i);
            histId.add(temp.getIdInHistDB());
        }
        List<UniMajorInfoZhuanKeVO> histInfos =  DBProxy.Getinstance().UniMajorInfoZhuanKeService.findByIdIn(histId);

        //保存到字典
        HashMap<Long, UniMajorInfoZhuanKeVO>  HistMajorByIdDic = new HashMap<>();
        for (int i = 0; i < histInfos.size(); i++) {
            HistMajorByIdDic.put(histInfos.get(i).getId(),  histInfos.get(i));
        }


        List<UniMajorInfoZhuanKe> entityList = new ArrayList<>();
        List<UniMajorInfoZhuanKeLatest> entityLatestList = new ArrayList<>();
        //遍历Update
        for (Map.Entry<String, HashMap<String, UniMajorInfo>>  entry: MajorToUpdateDic.entrySet()) {
            String uniCode = entry.getKey();
            HashMap<String, UniMajorInfo> UniCodeDic = entry.getValue();

            for(Map.Entry<String, UniMajorInfo> majorInfoEntry: UniCodeDic.entrySet()){
                String majorCode = majorInfoEntry.getKey();
                UniMajorInfo  uniMajorInfo = majorInfoEntry.getValue();


                //找到最新的
                HashMap<String, UniMajorInfoZhuanKeLatestVO> tempLatestDic =  LatestMajorExistDic.getOrDefault(uniCode, null);
                if(tempLatestDic == null){
                    System.out.println("latest DB 不存在 需要更新的数据   " + ":" +  uniCode + ":" +  majorCode);
                    nonExistDBUniMajorCodes.add(uniCode);
                    nonExistDBMajorCodes.add(majorCode);
                }else {
                    UniMajorInfoZhuanKeLatestVO  latestTempVO =  tempLatestDic.getOrDefault(majorCode, null);
                    if(latestTempVO == null){
                        System.out.println("latest DB 不存在 需要更新的数据   " + ":" +  uniCode + ":" +  majorCode);
                        nonExistDBUniMajorCodes.add(uniCode);
                        nonExistDBMajorCodes.add(majorCode);
                    }else {
                        //更新latetst数据
                        UniMajorInfoZhuanKeLatestVO latestDB = UniMajorInfoZhuanKeLatestVO.ConvertFromDTO(uniMajorInfo, latestTempVO);

                        //更新历史数据
                        UniMajorInfoZhuanKeVO histDB = HistMajorByIdDic.getOrDefault(latestDB.getIdInHistDB(), null);
                        UniMajorInfoZhuanKeVO histDB_Update = UniMajorInfoZhuanKeVO.ConvertFromDTO(uniMajorInfo, histDB);


                        entityList.add(BeanMapper.map(histDB_Update, UniMajorInfoZhuanKe.class));
                        entityLatestList.add(BeanMapper.map(latestDB, UniMajorInfoZhuanKeLatest.class));
                    }
                }

            }

        }


        DBProxy.Getinstance().UniMajorInfoZhuanKeService.batchUpdate(entityList);
        DBProxy.Getinstance().UniMajorInfoZhuanKeLatestService.batchUpdate(entityLatestList);


        if(nonExistDBUniMajorCodes.size() > 0){
            System.out.println("存在无法更新的数据!!!");

            for (int i = 0; i < nonExistDBMajorCodes.size(); i++) {
                System.out.println("存在无法更新的数据!!!"  + "UniMajorCode: " + nonExistDBUniMajorCodes.get(i) + ", MajorCode: " + nonExistDBMajorCodes.get(i));
            }
        }
    }


    private void UpdateUniMajorInfoZXA(List<UniMajorInfo> protoList,EnumPici pici, String year){
        List<String> nonExistDBUniMajorCodes = new ArrayList<>();
        List<String> nonExistDBMajorCodes = new ArrayList<>();


        Set<String> unicodes = new HashSet<>(); //记录需要更新的 unicode
        HashMap<String, HashMap<String, UniMajorInfo>>  MajorToUpdateDic = new HashMap<>();
        for (int i = 0; i < protoList.size(); i++) {
            UniMajorInfo proto = protoList.get(i);
//            //保存历史库
//            UniMajorInfoZXAVO histDB = UniMajorInfoZXAVO.ConvertFromDTO(proto);
//            histDB.setId(null);
//            histDB.setDataVersion(NewVersion);
//            entityList.add(BeanMapper.map(histDB, UniMajorInfoZXA.class));

            String uniMajorCode = proto.getUniMajorCode();
            String majorCode = proto.getMajorCode();

            //保存unicode
            unicodes.add(uniMajorCode);

            //保存到字典
            HashMap<String, UniMajorInfo> majorDic = MajorToUpdateDic.getOrDefault(uniMajorCode, null);
            if(majorDic == null){
                majorDic = new HashMap<>();
                MajorToUpdateDic.put(uniMajorCode, majorDic);
            }

            majorDic.put(majorCode, proto);

        }

        //获取 latest 数据
        List<UniMajorInfoZXALatestVO> latestDBs =  DBProxy.Getinstance().UniMajorInfoZXALatestService.findByUniMajorCodeInAndExamYear(new ArrayList<>(unicodes), year);
        HashMap<String, HashMap<String, UniMajorInfoZXALatestVO>>  LatestMajorExistDic = new HashMap<>();

        for (int i = 0; i < latestDBs.size(); i++) {
            UniMajorInfoZXALatestVO latestVO = latestDBs.get(i);

            String uniMajorCode = latestVO.getUniMajorCode();
            String majorCode = latestVO.getMajorCode();

//            //保存unicode
//            unicodes.add(uniMajorCode);

            //保存到字典
            HashMap<String, UniMajorInfoZXALatestVO> majorDic = LatestMajorExistDic.getOrDefault(uniMajorCode, null);
            if(majorDic == null){
                majorDic = new HashMap<>();
                LatestMajorExistDic.put(uniMajorCode, majorDic);
            }
            majorDic.put(majorCode, latestVO);
        }

        //获取历史数据
        List<Long> histId = new ArrayList<>();
        for (int i = 0; i < latestDBs.size(); i++) {
            UniMajorInfoZXALatestVO temp = latestDBs.get(i);
            histId.add(temp.getIdInHistDB());
        }
        List<UniMajorInfoZXAVO> histInfos =  DBProxy.Getinstance().UniMajorInfoZXAService.findByIdIn(histId);

        //保存到字典
        HashMap<Long, UniMajorInfoZXAVO>  HistMajorByIdDic = new HashMap<>();
        for (int i = 0; i < histInfos.size(); i++) {
            HistMajorByIdDic.put(histInfos.get(i).getId(),  histInfos.get(i));
        }


        List<UniMajorInfoZXA> entityList = new ArrayList<>();
        List<UniMajorInfoZXALatest> entityLatestList = new ArrayList<>();
        //遍历Update
        for (Map.Entry<String, HashMap<String, UniMajorInfo>>  entry: MajorToUpdateDic.entrySet()) {
            String uniCode = entry.getKey();
            HashMap<String, UniMajorInfo> UniCodeDic = entry.getValue();

            for(Map.Entry<String, UniMajorInfo> majorInfoEntry: UniCodeDic.entrySet()){
                String majorCode = majorInfoEntry.getKey();
                UniMajorInfo  uniMajorInfo = majorInfoEntry.getValue();


                //找到最新的
                HashMap<String, UniMajorInfoZXALatestVO> tempLatestDic =  LatestMajorExistDic.getOrDefault(uniCode, null);
                if(tempLatestDic == null){
                    System.out.println("latest DB 不存在 需要更新的数据   " + ":" +  uniCode + ":" +  majorCode);
                    nonExistDBUniMajorCodes.add(uniCode);
                    nonExistDBMajorCodes.add(majorCode);
                }else {
                    UniMajorInfoZXALatestVO  latestTempVO =  tempLatestDic.getOrDefault(majorCode, null);
                    if(latestTempVO == null){
                        System.out.println("latest DB 不存在 需要更新的数据   " + ":" +  uniCode + ":" +  majorCode);
                        nonExistDBUniMajorCodes.add(uniCode);
                        nonExistDBMajorCodes.add(majorCode);
                    }else {
                        //更新latetst数据
                        UniMajorInfoZXALatestVO latestDB = UniMajorInfoZXALatestVO.ConvertFromDTO(uniMajorInfo, latestTempVO);

                        //更新历史数据
                        UniMajorInfoZXAVO histDB = HistMajorByIdDic.getOrDefault(latestDB.getIdInHistDB(), null);
                        UniMajorInfoZXAVO histDB_Update = UniMajorInfoZXAVO.ConvertFromDTO(uniMajorInfo, histDB);


                        entityList.add(BeanMapper.map(histDB_Update, UniMajorInfoZXA.class));
                        entityLatestList.add(BeanMapper.map(latestDB, UniMajorInfoZXALatest.class));
                    }
                }

            }

        }


        DBProxy.Getinstance().UniMajorInfoZXAService.batchUpdate(entityList);
        DBProxy.Getinstance().UniMajorInfoZXALatestService.batchUpdate(entityLatestList);


        if(nonExistDBUniMajorCodes.size() > 0){
            System.out.println("存在无法更新的数据!!!");

            for (int i = 0; i < nonExistDBMajorCodes.size(); i++) {
                System.out.println("存在无法更新的数据!!!"  + "UniMajorCode: " + nonExistDBUniMajorCodes.get(i) + ", MajorCode: " + nonExistDBMajorCodes.get(i));
            }
        }
    }


    private void UpdateUniMajorInfoZXB(List<UniMajorInfo> protoList,EnumPici pici, String year){
        List<String> nonExistDBUniMajorCodes = new ArrayList<>();
        List<String> nonExistDBMajorCodes = new ArrayList<>();


        Set<String> unicodes = new HashSet<>(); //记录需要更新的 unicode
        HashMap<String, HashMap<String, UniMajorInfo>>  MajorToUpdateDic = new HashMap<>();
        for (int i = 0; i < protoList.size(); i++) {
            UniMajorInfo proto = protoList.get(i);
//            //保存历史库
//            UniMajorInfoZXBVO histDB = UniMajorInfoZXBVO.ConvertFromDTO(proto);
//            histDB.setId(null);
//            histDB.setDataVersion(NewVersion);
//            entityList.add(BeanMapper.map(histDB, UniMajorInfoZXB.class));

            String uniMajorCode = proto.getUniMajorCode();
            String majorCode = proto.getMajorCode();

            //保存unicode
            unicodes.add(uniMajorCode);

            //保存到字典
            HashMap<String, UniMajorInfo> majorDic = MajorToUpdateDic.getOrDefault(uniMajorCode, null);
            if(majorDic == null){
                majorDic = new HashMap<>();
                MajorToUpdateDic.put(uniMajorCode, majorDic);
            }

            majorDic.put(majorCode, proto);

        }

        //获取 latest 数据
        List<UniMajorInfoZXBLatestVO> latestDBs =  DBProxy.Getinstance().UniMajorInfoZXBLatestService.findByUniMajorCodeInAndExamYear(new ArrayList<>(unicodes), year);
        HashMap<String, HashMap<String, UniMajorInfoZXBLatestVO>>  LatestMajorExistDic = new HashMap<>();

        for (int i = 0; i < latestDBs.size(); i++) {
            UniMajorInfoZXBLatestVO latestVO = latestDBs.get(i);

            String uniMajorCode = latestVO.getUniMajorCode();
            String majorCode = latestVO.getMajorCode();

//            //保存unicode
//            unicodes.add(uniMajorCode);

            //保存到字典
            HashMap<String, UniMajorInfoZXBLatestVO> majorDic = LatestMajorExistDic.getOrDefault(uniMajorCode, null);
            if(majorDic == null){
                majorDic = new HashMap<>();
                LatestMajorExistDic.put(uniMajorCode, majorDic);
            }
            majorDic.put(majorCode, latestVO);
        }

        //获取历史数据
        List<Long> histId = new ArrayList<>();
        for (int i = 0; i < latestDBs.size(); i++) {
            UniMajorInfoZXBLatestVO temp = latestDBs.get(i);
            histId.add(temp.getIdInHistDB());
        }
        List<UniMajorInfoZXBVO> histInfos =  DBProxy.Getinstance().UniMajorInfoZXBService.findByIdIn(histId);

        //保存到字典
        HashMap<Long, UniMajorInfoZXBVO>  HistMajorByIdDic = new HashMap<>();
        for (int i = 0; i < histInfos.size(); i++) {
            HistMajorByIdDic.put(histInfos.get(i).getId(),  histInfos.get(i));
        }


        List<UniMajorInfoZXB> entityList = new ArrayList<>();
        List<UniMajorInfoZXBLatest> entityLatestList = new ArrayList<>();
        //遍历Update
        for (Map.Entry<String, HashMap<String, UniMajorInfo>>  entry: MajorToUpdateDic.entrySet()) {
            String uniCode = entry.getKey();
            HashMap<String, UniMajorInfo> UniCodeDic = entry.getValue();

            for(Map.Entry<String, UniMajorInfo> majorInfoEntry: UniCodeDic.entrySet()){
                String majorCode = majorInfoEntry.getKey();
                UniMajorInfo  uniMajorInfo = majorInfoEntry.getValue();


                //找到最新的
                HashMap<String, UniMajorInfoZXBLatestVO> tempLatestDic =  LatestMajorExistDic.getOrDefault(uniCode, null);
                if(tempLatestDic == null){
                    System.out.println("latest DB 不存在 需要更新的数据   " + ":" +  uniCode + ":" +  majorCode);
                    nonExistDBUniMajorCodes.add(uniCode);
                    nonExistDBMajorCodes.add(majorCode);
                }else {
                    UniMajorInfoZXBLatestVO  latestTempVO =  tempLatestDic.getOrDefault(majorCode, null);
                    if(latestTempVO == null){
                        System.out.println("latest DB 不存在 需要更新的数据   " + ":" +  uniCode + ":" +  majorCode);
                        nonExistDBUniMajorCodes.add(uniCode);
                        nonExistDBMajorCodes.add(majorCode);
                    }else {
                        //更新latetst数据
                        UniMajorInfoZXBLatestVO latestDB = UniMajorInfoZXBLatestVO.ConvertFromDTO(uniMajorInfo, latestTempVO);

                        //更新历史数据
                        UniMajorInfoZXBVO histDB = HistMajorByIdDic.getOrDefault(latestDB.getIdInHistDB(), null);
                        UniMajorInfoZXBVO histDB_Update = UniMajorInfoZXBVO.ConvertFromDTO(uniMajorInfo, histDB);


                        entityList.add(BeanMapper.map(histDB_Update, UniMajorInfoZXB.class));
                        entityLatestList.add(BeanMapper.map(latestDB, UniMajorInfoZXBLatest.class));
                    }
                }

            }

        }


        DBProxy.Getinstance().UniMajorInfoZXBService.batchUpdate(entityList);
        DBProxy.Getinstance().UniMajorInfoZXBLatestService.batchUpdate(entityLatestList);


        if(nonExistDBUniMajorCodes.size() > 0){
            System.out.println("存在无法更新的数据!!!");

            for (int i = 0; i < nonExistDBMajorCodes.size(); i++) {
                System.out.println("存在无法更新的数据!!!"  + "UniMajorCode: " + nonExistDBUniMajorCodes.get(i) + ", MajorCode: " + nonExistDBMajorCodes.get(i));
            }
        }
    }


    @Override
    public void DoProxy(ExcelTableData table, File file, Param Param) throws InterruptedException {

//        //保存excel数据
//        this.ResetDic(table);
//        //重置
//        this.FixShoolType(UniMajorDic);
//
//        UniMajorParam param = (UniMajorParam) Param;
//        int sheetIdx = param.SheetIdx;
//
//        ExcelTableData iconTable = param.iconTable; //图标数据
//        this.ResetMajorIconDic(iconTable);//保存图标数据
//
//        EnumPici pici = GameUtil.GetPiciBySheetId(sheetIdx);// EnumPici.A;
//
//        //转成proto 数据
//        List<UniMajorInfo> protoList = ConvertToProtoList(UniMajorDic, pici.getStateID(), MajorIcon, param.Year);
//
//        HashMap<String, List<UniMajorInfo>> groupDic = GetGroupInfo(protoList);
//        List<UniMajorInfo> reorderedList = ReorderProto(groupDic);
//
//        SavePici(reorderedList, param.NewVersion, pici, param.Year, groupDic);
//
//
//        System.out.println("完成解析院校池");
    }


}
