package com.baizhou.dbtool.proxy;

import com.baizhou.common.CommonUtil;
import com.baizhou.core.model.entity.*;
import com.baizhou.core.model.vo.*;
import com.baizhou.data.constdefine.ConstDefine;
import com.baizhou.data.enumdefine.EnumDeleteStatus;
import com.baizhou.data.enumdefine.EnumHasGenderScore;
import com.baizhou.data.enumdefine.EnumPici;
import com.baizhou.db.DBProxy;
import com.baizhou.dbtool.DBDataTool;
import com.baizhou.dbtool.model.ExcelRowData;
import com.baizhou.dbtool.model.ExcelTableData;
import com.baizhou.dbtool.model.Param;
import com.baizhou.dbtool.model.UniMajorParam;
import com.baizhou.gameutil.StringParser;
import com.baizhou.manager.UniMajorMgrInMem;
import com.baizhou.util.BeanMapper;
import com.baizhou.util.GameUtil;
import com.msg.FormInfo;
import com.msg.UniMajorInfo;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.util.StringUtils;

import java.io.File;
import java.util.*;

public class UniMajorInfoParseProx implements IExcelParseProxy {
    @Override
    public void DoProxy(ExcelTableData table, File file, Param Param) throws InterruptedException {

        //保存excel数据
        this.ResetDic(table);
        //重置
        this.FixShoolType(UniMajorDic);

        UniMajorParam param = (UniMajorParam) Param;
        int sheetIdx = param.SheetIdx;

        ExcelTableData iconTable = param.iconTable; //图标数据
        this.ResetMajorIconDic(iconTable);//保存图标数据

        EnumPici pici = GameUtil.GetPiciBySheetId(sheetIdx);// EnumPici.A;
//        if(sheetIdx == 0){
//            System.out.println("开始解析提前批A");
//            pici = EnumPici.PreA;
//        }else  if(sheetIdx == 1){
//            System.out.println("开始解析提前批B");
//            pici = EnumPici.PreB;
//        }else  if(sheetIdx == 2){
//            System.out.println("开始解析本A");
//            pici = EnumPici.A;
//        }else  if(sheetIdx == 3){
//            System.out.println("开始解析本B");
//            pici = EnumPici.B;
//         }else  if(sheetIdx == 4){
//
//        }else  if(sheetIdx == 5){
//            System.out.println("开始解析专科");
//            pici = EnumPici.ZhuanKe;
//        }else  if(sheetIdx == 6){
//            System.out.println("开始解析征询本A批次");
//            pici = EnumPici.ZXA;
//        }else  if(sheetIdx == 7){
//            System.out.println("开始解析征询本B批次");
//            pici = EnumPici.ZXB;
//        }


        //转成proto 数据
        List<UniMajorInfo> protoList = ConvertToProtoList(UniMajorDic, pici.getStateID(), MajorIcon, param.Year);

        HashMap<String, List<UniMajorInfo>> groupDic = GetGroupInfo(protoList);
        List<UniMajorInfo> reorderedList = ReorderProto(groupDic);

        try {

            SavePici(reorderedList, param.NewVersion, pici, param.Year, groupDic);

        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("完成解析院校池");
    }


    protected  HashMap<String, List<UniMajorInfo>> GetGroupInfo(List<UniMajorInfo> protoList ){

        HashMap<String, List<UniMajorInfo>> groupDic = new HashMap<>();

        //每个group用  majorList的第一个  getScoreFor22 从高到底
        //group中的major 用 getMajor22LowScore  从第到高排序
        for (int i = 0; i < protoList.size(); i++) {
            UniMajorInfo temp = protoList.get(i);

            String uniMajorCode = temp.getUniMajorCode();
            List<UniMajorInfo> tempList = groupDic.getOrDefault(uniMajorCode, null);
            if (tempList == null) {
                tempList = new ArrayList<>();
                groupDic.put(uniMajorCode, tempList);
            }

            tempList.add(temp);
        }

        return groupDic;
    }

    //每个group用  majorList的第一个  getScoreFor22 从高到底
    //group中的major 用 getMajor22LowScore  从第到高排序
    protected  List<UniMajorInfo> ReorderProto(  HashMap<String, List<UniMajorInfo>> groupDic){

//        HashMap<String, List<UniMajorInfo>> groupDic = new HashMap<>();
//
//        //每个group用  majorList的第一个  getScoreFor22 从高到底
//        //group中的major 用 getMajor22LowScore  从第到高排序
//        for (int i = 0; i < protoList.size(); i++) {
//            UniMajorInfo temp = protoList.get(i);
//
//            String uniMajorCode = temp.getUniMajorCode();
//            List<UniMajorInfo> tempList = groupDic.getOrDefault(uniMajorCode, null);
//            if (tempList == null) {
//                tempList = new ArrayList<>();
//                groupDic.put(uniMajorCode, tempList);
//            }
//
//            tempList.add(temp);
//        }




        List<List<UniMajorInfo>> groupInfos = new ArrayList<>();
        for (Map.Entry<String, List<UniMajorInfo>> groupEntry : groupDic.entrySet()) {
//            String groupCode = groupEntry.getKey();

            List<UniMajorInfo> majorInfos = groupEntry.getValue();
            majorInfos.sort(new Comparator<UniMajorInfo>() {
                @Override
                public int compare(UniMajorInfo o1, UniMajorInfo o2) {
                    int one = (int) Float.parseFloat(o1.getMajor22LowScore());
                    int two = (int) Float.parseFloat(o2.getMajor22LowScore());
                    if (one == two) return 0;
                    return one - two > 0 ? -1 : 1;
                }
            });


//            //初始化专业组
//            com.msg.UniMajorGroupInfo.Builder groupProto = com.msg.UniMajorGroupInfo.newBuilder();
//            groupProto.addAllMajors(majorInfos);

            //添加专业组
            groupInfos.add(majorInfos);
        }


        groupInfos.sort(new Comparator<List<UniMajorInfo>>() {
            @Override
            public int compare(List<UniMajorInfo> o1, List<UniMajorInfo> o2) {
//                System.out.println("" + o1.getMajorsList().get(0).getScoreFor22() + "," + o2.getMajorsList().get(0).getScoreFor22() + ", " + o1.getMajorsList().get(0).getId() + "," + o1.getMajorsList().get(0).getMajorName()+ ", " + o2.getMajorsList().get(0).getId() + "," + o2.getMajorsList().get(0).getMajorName());
                int one =(int) Float.parseFloat(o1.get(0).getScoreFor22());
                int two = (int) Float.parseFloat(o2.get(0).getScoreFor22());
                if (one == two) return 0;
                return one - two > 0 ? -1 : 1;
            }
        });



        List<UniMajorInfo> reorderedProtoList = new ArrayList<>();
        for (int i = groupInfos.size() - 1; i >=0; i--) {
            List<UniMajorInfo>  majorInfoALatestVOList = groupInfos.get(i);

            for (int j = majorInfoALatestVOList.size() - 1; j >=0 ; j--) {

                UniMajorInfo majorInfoALatestVO = majorInfoALatestVOList.get(j);

//                majorInfoALatestVO.setId(uid);
//                uid++;
//                Thread.sleep(ConstDefine.InsertDBInterval);
//                DBProxy.Getinstance().UniMajorInfoALatestService.saveUniMajorInfoALatest(majorInfoALatestVO);
                reorderedProtoList.add(majorInfoALatestVO);

            }

        }

        return reorderedProtoList;

    }

    private void DeleteUniMajorDB(EnumPici pici, String year) throws InterruptedException {
        switch (pici){
            case A:
            {
                DBProxy.Getinstance().UniMajorInfoALatestService.deleteByExamYear(year);//.deleteAll();
                DBProxy.Getinstance().UniGroupInfoALatestService.deleteByExamYear(year);//deleteAll();

                break;
            }
            case B:
            {
                DBProxy.Getinstance().UniMajorInfoBLatestService.deleteByExamYear(year);//deleteAll();
                DBProxy.Getinstance().UniGroupInfoBLatestService.deleteByExamYear(year);//deleteAll();

                break;
            }
            case PreA:
            {
                DBProxy.Getinstance().UniMajorInfoPreALatestService.deleteByExamYear(year);//deleteAll();
                DBProxy.Getinstance().UniGroupInfoPreALatestService.deleteByExamYear(year);//deleteAll();

                break;
            }
            case PreB:
            {
                DBProxy.Getinstance().UniMajorInfoPreBLatestService.deleteByExamYear(year);//deleteAll();
                DBProxy.Getinstance().UniGroupInfoPreBLatestService.deleteByExamYear(year);//deleteAll();

                break;
            }
            case ZhuanKe:
            {
                DBProxy.Getinstance().UniMajorInfoZhuanKeLatestService.deleteByExamYear(year);//deleteAll();
                DBProxy.Getinstance().UniGroupInfoZhuanKeLatestService.deleteByExamYear(year);//deleteAll();

                break;
            }
            case ZXA:
            {
                DBProxy.Getinstance().UniMajorInfoZXALatestService.deleteByExamYear(year);//deleteAll();
                DBProxy.Getinstance().UniGroupInfoZXALatestService.deleteByExamYear(year);//deleteAll();

                break;
            }
            case ZXB:
            {
                DBProxy.Getinstance().UniMajorInfoZXBLatestService.deleteByExamYear(year);//deleteAll();
                DBProxy.Getinstance().UniGroupInfoZXBLatestService.deleteByExamYear(year);//deleteAll();

                break;
            }
        }

        Thread.sleep(ConstDefine.DeleteDBInterval);
    }



    private void SavePici(List<UniMajorInfo> protoList, Integer NewVersion, EnumPici pici, String year,HashMap<String, List<UniMajorInfo>> groupDic) throws Exception {


        //删除MajorDB 和 GroupDB
//        DeleteUniMajorDB(pici, year);
//        if(true) return;
        switch (pici){
            case A:
            {

                SaveUniMajorInfoA( protoList, NewVersion, pici, year);
                break;
            }
            case B:
                SaveUniMajorInfoB( protoList, NewVersion, pici, year);
                break;
            case PreA:
                SaveUniMajorInfoPreA( protoList, NewVersion, pici, year);
                break;
            case PreB:
                SaveUniMajorInfoPreB( protoList, NewVersion, pici, year);
                break;
            case ZhuanKe:
                SaveUniMajorInfoZhuanKe( protoList, NewVersion, pici, year);
                break;
            case ZXA:
                SaveUniMajorInfoZXA( protoList, NewVersion, pici, year);
                break;
            case ZXB:
                SaveUniMajorInfoZXB( protoList, NewVersion, pici, year);
                break;
        }



    }

    //批量上传更新
    private void SavePici_V2(List<UniMajorInfo> protoList, Integer NewVersion, EnumPici pici, String year,HashMap<String, List<UniMajorInfo>> groupDic) throws InterruptedException {


        //删除MajorDB 和 GroupDB
        DeleteUniMajorDB(pici, year);

        switch (pici){
            case A:
            {

                SavePiciA( protoList, NewVersion, pici, year);
                break;
            }
            case B:
                SavePiciB( protoList, NewVersion, pici, year);
                break;
            case PreA:
                SavePiciPreA( protoList, NewVersion, pici, year);
                break;
            case PreB:
                SavePiciPreB( protoList, NewVersion, pici, year);
                break;
            case ZhuanKe:
                SavePiciZhuanKe( protoList, NewVersion, pici, year);
                break;
            case ZXA:
                SavePiciZXA( protoList, NewVersion, pici, year);
                break;
            case ZXB:
                SavePiciZXB( protoList, NewVersion, pici, year);
                break;
        }

        //保存最新版本
        UniMajorMgrInMem.GetInstance().SaveLatestVersion(pici, year, NewVersion);

    }

    private void SavePici_V1(List<UniMajorInfo> protoList, Integer NewVersion, EnumPici pici, String year,HashMap<String, List<UniMajorInfo>> groupDic) throws InterruptedException {


        //删除MajorDB 和 GroupDB
        DeleteUniMajorDB(pici, year);

//        Long majorMaxId = 0L;
        //lateest Major和Group 直接用固定id;   HistDB用自动生成
        Long majorLatestStartId = 1L;
        Long groupLatestStartId = 1L;

        Long interval = ConstDefine.InsertDBInterval;;// 500L;//100L;//ConstDefine.InsertDBInterval;

        String lastSaveGroupCode = "";// 最近保存的GroupCode
        for (int i = 0; i < protoList.size(); i++) {
            UniMajorInfo proto = protoList.get(i);


            switch (pici){
                case A:
                {
                    //保存历史库
                    UniMajorInfoAVO histDB = UniMajorInfoAVO.ConvertFromDTO(proto);
                    histDB.setId(null);
                    histDB.setDataVersion(NewVersion);
                    UniMajorInfoA uniMajorInfoA = DBProxy.Getinstance().UniMajorInfoAService.saveUniMajorInfoA(histDB);

                    Long latestId = uniMajorInfoA.getId();
                    //保存最新数据库
                    UniMajorInfoALatestVO latestDB = UniMajorInfoALatestVO.ConvertFromDTO(proto);
                    latestDB.setId(null);//(null);
                    latestDB.setDataVersion(NewVersion);
                    latestDB.setIdInHistDB(latestId);
                    DBProxy.Getinstance().UniMajorInfoALatestService.saveUniMajorInfoALatest(latestDB);

                    //更新历史库 把相同专业更新 idInLatestDB 栏位;
                    List<UniMajorInfoAVO> oldHistDB = DBProxy.Getinstance().UniMajorInfoAService.findByUniMajorCodeAndMajorCodeAndExamYear(histDB.getUniMajorCode(), histDB.getMajorCode(), histDB.getExamYear());
                    for (int j = 0; j < oldHistDB.size(); j++) {
                        UniMajorInfoAVO tempOldHistDB = oldHistDB.get(j);
                        tempOldHistDB.setIdInLatestDB(latestId);
                        if(latestId == null){
                            System.out.println("latest == null");
                        }
                        DBProxy.Getinstance().UniMajorInfoAService.saveUniMajorInfoA(tempOldHistDB);
                    }

                    //每个uni group 保存Group信息
                    if(!lastSaveGroupCode.equals(proto.getUniMajorCode())){
                        UniGroupInfoALatestVO groupDB = UniGroupInfoALatestVO.ConvertFromDTO(proto);

                        groupDB.setId(null);///(null);
                        groupDB.setDataVersion(NewVersion);
                        groupDB.setMajorNames(GameUtil.GetAllMajorInGroup(groupDB.getUniMajorCode(),groupDic));
                        groupDB.setMajor22LowRank(GameUtil.GetLowestMajorRankInGroup(groupDB.getUniMajorCode(),groupDic)); //设置 专业的 最低分数
                        groupDB.setRankFor22(GameUtil.GetLowestGroupRankInGroup(groupDB.getUniMajorCode(),groupDic)); //设置 专业组的 最低分数
                        DBProxy.Getinstance().UniGroupInfoALatestService.saveUniGroupInfoALatest(groupDB);

                        lastSaveGroupCode = groupDB.getUniMajorCode();
                        System.out.println("保存" + histDB.getMajorName());
                        Thread.sleep(interval);
                    }else {
                        Thread.sleep(ConstDefine.InsertDBIntervalQuick);
                    }



                    break;
                }
                case B:
                {
                    //保存历史库
                    UniMajorInfoBVO histDB = UniMajorInfoBVO.ConvertFromDTO(proto);
                    histDB.setId(null);
                    histDB.setDataVersion(NewVersion);
                    UniMajorInfoB uniMajorInfoA = DBProxy.Getinstance().UniMajorInfoBService.saveUniMajorInfoB(histDB);

                    //保存最新数据库
                    UniMajorInfoBLatestVO latestDB = UniMajorInfoBLatestVO.ConvertFromDTO(proto);
                    latestDB.setId(null);
                    latestDB.setDataVersion(NewVersion);
                    latestDB.setIdInHistDB(uniMajorInfoA.getId());
                    DBProxy.Getinstance().UniMajorInfoBLatestService.saveUniMajorInfoBLatest(latestDB);

                    //更新历史库 把相同专业更新 idInLatestDB 栏位;
                    List<UniMajorInfoBVO> oldHistDB = DBProxy.Getinstance().UniMajorInfoBService.findByUniMajorCodeAndMajorCodeAndExamYear(histDB.getUniMajorCode(), histDB.getMajorCode(), histDB.getExamYear());
                    for (int j = 0; j < oldHistDB.size(); j++) {
                        UniMajorInfoBVO tempOldHistDB = oldHistDB.get(j);
                        tempOldHistDB.setIdInLatestDB(uniMajorInfoA.getId());
                        DBProxy.Getinstance().UniMajorInfoBService.saveUniMajorInfoB(tempOldHistDB);
                    }

                    //每个uni group 保存Group信息
                    if(!lastSaveGroupCode.equals(proto.getUniMajorCode())){
                        UniGroupInfoBLatestVO groupDB = UniGroupInfoBLatestVO.ConvertFromDTO(proto);
                        groupDB.setId(null);
                        groupDB.setDataVersion(NewVersion);
                        groupDB.setMajorNames(GameUtil.GetAllMajorInGroup(groupDB.getUniMajorCode(),groupDic));
                        groupDB.setMajor22LowRank(GameUtil.GetLowestMajorRankInGroup(groupDB.getUniMajorCode(),groupDic)); //设置 专业的 最低分数
                        groupDB.setRankFor22(GameUtil.GetLowestGroupRankInGroup(groupDB.getUniMajorCode(),groupDic)); //设置 专业组的 最低分数
                        DBProxy.Getinstance().UniGroupInfoBLatestService.saveUniGroupInfoBLatest(groupDB);

                        lastSaveGroupCode = groupDB.getUniMajorCode();

                        System.out.println("保存" + histDB.getMajorName());
                        Thread.sleep(interval);
                    }else {
                        Thread.sleep(ConstDefine.InsertDBIntervalQuick);
                    }


//                    System.out.println("保存" + histDB.getMajorName());
//                    Thread.sleep(interval);
                    break;
                }
                case PreA:
                {
                    //保存历史库
                    UniMajorInfoPreAVO histDB = UniMajorInfoPreAVO.ConvertFromDTO(proto);
                    histDB.setId(null);
                    histDB.setDataVersion(NewVersion);
                    UniMajorInfoPreA uniMajorInfoA = DBProxy.Getinstance().UniMajorInfoPreAService.saveUniMajorInfoPreA(histDB);

                    //保存最新数据库
                    UniMajorInfoPreALatestVO latestDB = UniMajorInfoPreALatestVO.ConvertFromDTO(proto);
                    latestDB.setId(null);
                    latestDB.setDataVersion(NewVersion);
                    latestDB.setIdInHistDB(uniMajorInfoA.getId());
                    DBProxy.Getinstance().UniMajorInfoPreALatestService.saveUniMajorInfoPreALatest(latestDB);

                    //更新历史库 把相同专业更新 idInLatestDB 栏位;
                    List<UniMajorInfoPreAVO> oldHistDB = DBProxy.Getinstance().UniMajorInfoPreAService.findByUniMajorCodeAndMajorCodeAndExamYear(histDB.getUniMajorCode(), histDB.getMajorCode(), histDB.getExamYear());
                    for (int j = 0; j < oldHistDB.size(); j++) {
                        UniMajorInfoPreAVO tempOldHistDB = oldHistDB.get(j);
                        tempOldHistDB.setIdInLatestDB(uniMajorInfoA.getId());
                        DBProxy.Getinstance().UniMajorInfoPreAService.saveUniMajorInfoPreA(tempOldHistDB);
                    }

                    //每个uni group 保存Group信息
                    if(!lastSaveGroupCode.equals(proto.getUniMajorCode())){
                        UniGroupInfoPreALatestVO groupDB = UniGroupInfoPreALatestVO.ConvertFromDTO(proto);
                        groupDB.setId(null);
                        groupDB.setDataVersion(NewVersion);
                        groupDB.setMajorNames(GameUtil.GetAllMajorInGroup(groupDB.getUniMajorCode(),groupDic));
                        groupDB.setMajor22LowRank(GameUtil.GetLowestMajorRankInGroup(groupDB.getUniMajorCode(),groupDic)); //设置 专业的 最低分数
                        groupDB.setRankFor22(GameUtil.GetLowestGroupRankInGroup(groupDB.getUniMajorCode(),groupDic)); //设置 专业组的 最低分数
                        DBProxy.Getinstance().UniGroupInfoPreALatestService.saveUniGroupInfoPreALatest(groupDB);

                        lastSaveGroupCode = groupDB.getUniMajorCode();
                        System.out.println("保存" + histDB.getMajorName());
                        Thread.sleep(interval);

                    }else {
                        Thread.sleep(ConstDefine.InsertDBIntervalQuick);
                    }


//                    System.out.println("保存" + histDB.getMajorName());
//                    Thread.sleep(interval);

                    break;
                }
                case PreB:
                {
                    //保存历史库
                    UniMajorInfoPreBVO histDB = UniMajorInfoPreBVO.ConvertFromDTO(proto);
                    histDB.setId(null);
                    histDB.setDataVersion(NewVersion);
                    UniMajorInfoPreB uniMajorInfoA = DBProxy.Getinstance().UniMajorInfoPreBService.saveUniMajorInfoPreB(histDB);

                    //保存最新数据库
                    UniMajorInfoPreBLatestVO latestDB = UniMajorInfoPreBLatestVO.ConvertFromDTO(proto);
                    latestDB.setId(null);
                    latestDB.setDataVersion(NewVersion);
                    latestDB.setIdInHistDB(uniMajorInfoA.getId());
                    DBProxy.Getinstance().UniMajorInfoPreBLatestService.saveUniMajorInfoPreBLatest(latestDB);

                    //更新历史库 把相同专业更新 idInLatestDB 栏位;
                    List<UniMajorInfoPreBVO> oldHistDB = DBProxy.Getinstance().UniMajorInfoPreBService.findByUniMajorCodeAndMajorCodeAndExamYear(histDB.getUniMajorCode(), histDB.getMajorCode(), histDB.getExamYear());
                    for (int j = 0; j < oldHistDB.size(); j++) {
                        UniMajorInfoPreBVO tempOldHistDB = oldHistDB.get(j);
                        tempOldHistDB.setIdInLatestDB(uniMajorInfoA.getId());
                        DBProxy.Getinstance().UniMajorInfoPreBService.saveUniMajorInfoPreB(tempOldHistDB);
                    }

                    //每个uni group 保存Group信息
                    if(!lastSaveGroupCode.equals(proto.getUniMajorCode())){
                        UniGroupInfoPreBLatestVO groupDB = UniGroupInfoPreBLatestVO.ConvertFromDTO(proto);
                        groupDB.setId(null);
                        groupDB.setDataVersion(NewVersion);
                        groupDB.setMajorNames(GameUtil.GetAllMajorInGroup(groupDB.getUniMajorCode(),groupDic));
                        groupDB.setMajor22LowRank(GameUtil.GetLowestMajorRankInGroup(groupDB.getUniMajorCode(),groupDic)); //设置 专业的 最低分数
                        groupDB.setRankFor22(GameUtil.GetLowestGroupRankInGroup(groupDB.getUniMajorCode(),groupDic)); //设置 专业组的 最低分数
                        DBProxy.Getinstance().UniGroupInfoPreBLatestService.saveUniGroupInfoPreBLatest(groupDB);

                        lastSaveGroupCode = groupDB.getUniMajorCode();

                        System.out.println("保存" + histDB.getMajorName());
                        Thread.sleep(interval);
                    }else {
                        Thread.sleep(ConstDefine.InsertDBIntervalQuick);
                    }


//                    System.out.println("保存" + histDB.getMajorName());
//                    Thread.sleep(interval);
                    break;
                }
                case ZhuanKe:
                {
                    //保存历史库
                    UniMajorInfoZhuanKeVO histDB = UniMajorInfoZhuanKeVO.ConvertFromDTO(proto);
                    histDB.setId(null);
                    histDB.setDataVersion(NewVersion);
                    UniMajorInfoZhuanKe uniMajorInfoA = DBProxy.Getinstance().UniMajorInfoZhuanKeService.saveUniMajorInfoZhuanKe(histDB);

                    //保存最新数据库
                    UniMajorInfoZhuanKeLatestVO latestDB = UniMajorInfoZhuanKeLatestVO.ConvertFromDTO(proto);
                    latestDB.setId(null);
                    latestDB.setDataVersion(NewVersion);
                    latestDB.setIdInHistDB(uniMajorInfoA.getId());
                    DBProxy.Getinstance().UniMajorInfoZhuanKeLatestService.saveUniMajorInfoZhuanKeLatest(latestDB);

                    //更新历史库 把相同专业更新 idInLatestDB 栏位;
                    List<UniMajorInfoZhuanKeVO> oldHistDB = DBProxy.Getinstance().UniMajorInfoZhuanKeService.findByUniMajorCodeAndMajorCodeAndExamYear(histDB.getUniMajorCode(), histDB.getMajorCode(), histDB.getExamYear());
                    for (int j = 0; j < oldHistDB.size(); j++) {
                        UniMajorInfoZhuanKeVO tempOldHistDB = oldHistDB.get(j);
                        tempOldHistDB.setIdInLatestDB(uniMajorInfoA.getId());
                        DBProxy.Getinstance().UniMajorInfoZhuanKeService.saveUniMajorInfoZhuanKe(tempOldHistDB);
                    }

                    //每个uni group 保存Group信息
                    if(!lastSaveGroupCode.equals(proto.getUniMajorCode())){
                        UniGroupInfoZhuanKeLatestVO groupDB = UniGroupInfoZhuanKeLatestVO.ConvertFromDTO(proto);
                        groupDB.setId(null);
                        groupDB.setDataVersion(NewVersion);
                        groupDB.setMajorNames(GameUtil.GetAllMajorInGroup(groupDB.getUniMajorCode(),groupDic));
                        groupDB.setMajor22LowRank(GameUtil.GetLowestMajorRankInGroup(groupDB.getUniMajorCode(),groupDic)); //设置 专业的 最低分数
                        groupDB.setRankFor22(GameUtil.GetLowestGroupRankInGroup(groupDB.getUniMajorCode(),groupDic)); //设置 专业组的 最低分数
                        DBProxy.Getinstance().UniGroupInfoZhuanKeLatestService.saveUniGroupInfoZhuanKeLatest(groupDB);

                        lastSaveGroupCode = groupDB.getUniMajorCode();

                        System.out.println("保存" + histDB.getMajorName());
                        Thread.sleep(interval);
                    }else {
                        Thread.sleep(ConstDefine.InsertDBIntervalQuick);
                    }

//
//                    System.out.println("保存" + histDB.getMajorName());
//                    Thread.sleep(interval);

                    break;
                }
                case ZXA:
                {
                    //保存历史库
                    UniMajorInfoZXAVO histDB = UniMajorInfoZXAVO.ConvertFromDTO(proto);
                    histDB.setId(null);
                    histDB.setDataVersion(NewVersion);
                    UniMajorInfoZXA uniMajorInfoA = DBProxy.Getinstance().UniMajorInfoZXAService.saveUniMajorInfoZXA(histDB);

                    //保存最新数据库
                    UniMajorInfoZXALatestVO latestDB = UniMajorInfoZXALatestVO.ConvertFromDTO(proto);
                    latestDB.setId(null);
                    latestDB.setDataVersion(NewVersion);
                    latestDB.setIdInHistDB(uniMajorInfoA.getId());
                    DBProxy.Getinstance().UniMajorInfoZXALatestService.saveUniMajorInfoZXALatest(latestDB);

                    //更新历史库 把相同专业更新 idInLatestDB 栏位;
                    List<UniMajorInfoZXAVO> oldHistDB = DBProxy.Getinstance().UniMajorInfoZXAService.findByUniMajorCodeAndMajorCodeAndExamYear(histDB.getUniMajorCode(), histDB.getMajorCode(), histDB.getExamYear());
                    for (int j = 0; j < oldHistDB.size(); j++) {
                        UniMajorInfoZXAVO tempOldHistDB = oldHistDB.get(j);
                        tempOldHistDB.setIdInLatestDB(uniMajorInfoA.getId());
                        DBProxy.Getinstance().UniMajorInfoZXAService.saveUniMajorInfoZXA(tempOldHistDB);
                    }

                    //每个uni group 保存Group信息
                    if(!lastSaveGroupCode.equals(proto.getUniMajorCode())){
                        UniGroupInfoZXALatestVO groupDB = UniGroupInfoZXALatestVO.ConvertFromDTO(proto);
                        groupDB.setId(null);
                        groupDB.setDataVersion(NewVersion);
                        groupDB.setMajorNames(GameUtil.GetAllMajorInGroup(groupDB.getUniMajorCode(),groupDic));
                        groupDB.setMajor22LowRank(GameUtil.GetLowestMajorRankInGroup(groupDB.getUniMajorCode(),groupDic)); //设置 专业的 最低分数
                        groupDB.setRankFor22(GameUtil.GetLowestGroupRankInGroup(groupDB.getUniMajorCode(),groupDic)); //设置 专业组的 最低分数
                        DBProxy.Getinstance().UniGroupInfoZXALatestService.saveUniGroupInfoZXALatest(groupDB);

                        lastSaveGroupCode = groupDB.getUniMajorCode();
                        System.out.println("保存" + histDB.getMajorName());
                        Thread.sleep(interval);
                    }else {
                        Thread.sleep(ConstDefine.InsertDBIntervalQuick);
                    }


//                    System.out.println("保存" + histDB.getMajorName());
//                    Thread.sleep(interval);

                    break;
                }
                case ZXB:
                {
                    //保存历史库
                    UniMajorInfoZXBVO histDB = UniMajorInfoZXBVO.ConvertFromDTO(proto);
                    histDB.setId(null);
                    histDB.setDataVersion(NewVersion);
                    UniMajorInfoZXB uniMajorInfoA = DBProxy.Getinstance().UniMajorInfoZXBService.saveUniMajorInfoZXB(histDB);

                    //保存最新数据库
                    UniMajorInfoZXBLatestVO latestDB = UniMajorInfoZXBLatestVO.ConvertFromDTO(proto);
                    latestDB.setId(null);
                    latestDB.setDataVersion(NewVersion);
                    latestDB.setIdInHistDB(uniMajorInfoA.getId());
                    DBProxy.Getinstance().UniMajorInfoZXBLatestService.saveUniMajorInfoZXBLatest(latestDB);

                    //更新历史库 把相同专业更新 idInLatestDB 栏位;
                    List<UniMajorInfoZXBVO> oldHistDB = DBProxy.Getinstance().UniMajorInfoZXBService.findByUniMajorCodeAndMajorCodeAndExamYear(histDB.getUniMajorCode(), histDB.getMajorCode(), histDB.getExamYear());
                    for (int j = 0; j < oldHistDB.size(); j++) {
                        UniMajorInfoZXBVO tempOldHistDB = oldHistDB.get(j);
                        tempOldHistDB.setIdInLatestDB(uniMajorInfoA.getId());
                        DBProxy.Getinstance().UniMajorInfoZXBService.saveUniMajorInfoZXB(tempOldHistDB);
                    }

                    //每个uni group 保存Group信息
                    if(!lastSaveGroupCode.equals(proto.getUniMajorCode())){
                        UniGroupInfoZXBLatestVO groupDB = UniGroupInfoZXBLatestVO.ConvertFromDTO(proto);
                        groupDB.setId(null);
                        groupDB.setDataVersion(NewVersion);
                        groupDB.setMajorNames(GameUtil.GetAllMajorInGroup(groupDB.getUniMajorCode(),groupDic));
                        groupDB.setMajor22LowRank(GameUtil.GetLowestMajorRankInGroup(groupDB.getUniMajorCode(),groupDic)); //设置 专业的 最低分数
                        groupDB.setRankFor22(GameUtil.GetLowestGroupRankInGroup(groupDB.getUniMajorCode(),groupDic)); //设置 专业组的 最低分数
                        DBProxy.Getinstance().UniGroupInfoZXBLatestService.saveUniGroupInfoZXBLatest(groupDB);

                        lastSaveGroupCode = groupDB.getUniMajorCode();
                        System.out.println("保存" + histDB.getMajorName());
                        Thread.sleep(interval);
                    }else {
                        Thread.sleep(ConstDefine.InsertDBIntervalQuick);
                    }


//                    System.out.println("保存" + histDB.getMajorName());
//                    Thread.sleep(interval);

                    break;
                }
            }


        }


//
//        switch (sheetIdx) {
//            case 0: {
//
//
//
//                break;
//            }
//            case 1: {
//                //提前批B
//                System.out.println("开始解析提前批B");
//
//                pici = 4;
//
//                //转成proto 数据
//                List<UniMajorInfo> protoList = ConvertToProtoList(UniMajorDic, pici, MajorIcon);
//
//
//                DBProxy.Getinstance().UniMajorInfoPreBLatestService.deleteAll();
//                Thread.sleep(ConstDefine.InsertDBInterval);
//
//                for (int i = 0; i < protoList.size(); i++) {
//                    UniMajorInfo proto = protoList.get(i);
//
//                    //保存历史库
//                    UniMajorInfoPreBVO histDB = UniMajorInfoPreBVO.ConvertFromDTO(proto);
//                    histDB.setId(null);
//                    histDB.setDataVersion(param.NewVersion);
//                    UniMajorInfoPreB uniMajorInfoA = DBProxy.Getinstance().UniMajorInfoPreBService.saveUniMajorInfoPreB(histDB);
//
//                    //保存最新数据库
//                    UniMajorInfoPreBLatestVO latestDB = UniMajorInfoPreBLatestVO.ConvertFromDTO(proto);
//                    latestDB.setId(null);
//                    latestDB.setDataVersion(param.NewVersion);
//                    latestDB.setIdInHistDB(uniMajorInfoA.getId());
//                    DBProxy.Getinstance().UniMajorInfoPreBLatestService.saveUniMajorInfoPreBLatest(latestDB);
//                    System.out.println("保存" + histDB.getMajorName());
//                    Thread.sleep(ConstDefine.InsertDBInterval);
//                }
//
//
//                break;
//            }
//            case 2: {
//                //本A
//                System.out.println("开始解析本A");
//
//                pici = 1;
//
//                //转成proto 数据
//                List<UniMajorInfo> protoList = ConvertToProtoList(UniMajorDic, pici, MajorIcon);
//
//
//                DBProxy.Getinstance().UniMajorInfoALatestService.deleteAll();
//                Thread.sleep(ConstDefine.InsertDBInterval);
//
//                for (int i = 0; i < protoList.size(); i++) {
//                    UniMajorInfo proto = protoList.get(i);
////                    boolean a = (proto.getUniMajorCode().trim().equals("02351")  || proto.getUniMajorCode().trim().equals("04111"));
////                    if (!a) continue;
//
////                    System.out.println("保存pre1 " + proto.getUniMajorCode());
////                    System.out.println("保存pre2 " + proto.getMajorClsIds());
//                    //保存历史库
//                    UniMajorInfoAVO histDB = UniMajorInfoAVO.ConvertFromDTO(proto);
//                    histDB.setId(null);
//                    histDB.setDataVersion(param.NewVersion);
//                    UniMajorInfoA uniMajorInfoA = DBProxy.Getinstance().UniMajorInfoAService.saveUniMajorInfoA(histDB);
//
//                    //保存最新数据库
//                    UniMajorInfoALatestVO latestDB = UniMajorInfoALatestVO.ConvertFromDTO(proto);
//                    latestDB.setId(null);
//                    latestDB.setDataVersion(param.NewVersion);
//                    latestDB.setIdInHistDB(uniMajorInfoA.getId());
//                    DBProxy.Getinstance().UniMajorInfoALatestService.saveUniMajorInfoALatest(latestDB);
//                    System.out.println("保存" + histDB.getMajorName());
//                    Thread.sleep(ConstDefine.InsertDBInterval);
//                }
//
//
//                break;
//            }
//            case 3: {
//                //本B
//                System.out.println("开始解析本B");
//
//                pici = 2;
//
//                //转成proto 数据
//                List<UniMajorInfo> protoList = ConvertToProtoList(UniMajorDic, pici, MajorIcon);
//
//
//                DBProxy.Getinstance().UniMajorInfoBLatestService.deleteAll();
//                Thread.sleep(ConstDefine.InsertDBInterval);
//
//                for (int i = 0; i < protoList.size(); i++) {
//                    UniMajorInfo proto = protoList.get(i);
//
//                    //保存历史库
//                    UniMajorInfoBVO histDB = UniMajorInfoBVO.ConvertFromDTO(proto);
//                    histDB.setId(null);
//                    histDB.setDataVersion(param.NewVersion);
//                    UniMajorInfoB uniMajorInfoA = DBProxy.Getinstance().UniMajorInfoBService.saveUniMajorInfoB(histDB);
//
//                    //保存最新数据库
//                    UniMajorInfoBLatestVO latestDB = UniMajorInfoBLatestVO.ConvertFromDTO(proto);
//                    latestDB.setId(null);
//                    latestDB.setDataVersion(param.NewVersion);
//                    latestDB.setIdInHistDB(uniMajorInfoA.getId());
//                    DBProxy.Getinstance().UniMajorInfoBLatestService.saveUniMajorInfoBLatest(latestDB);
//                    System.out.println("保存" + histDB.getMajorName());
//                    Thread.sleep(ConstDefine.InsertDBInterval);
//                }
//
//                break;
//            }
//        }
    }

    HashMap<String, HashMap<String, ExcelRowData>> UniMajorDic = new HashMap<>(); //Dic<专业组代码, Dic<专业代码, row>>

    private void ResetDic(ExcelTableData table) {
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
                System.out.println("专业重复 " + uniMajor.Cells.get(9));
            }

        }
    }


    protected List<UniMajorInfo> ConvertToProtoList(HashMap<String, HashMap<String, ExcelRowData>> UniMajorDic, int Pici,
                                                  HashMap<String, HashMap<String, ExcelRowData>> MajorIconDic, String year) {

        List<UniMajorInfo> uniMajorInfos = new ArrayList<>();


        for (Map.Entry<String, HashMap<String, ExcelRowData>> uniMajors : UniMajorDic.entrySet()) {
            String majorGroupCode = uniMajors.getKey();  //专业组信息
//            if(!majorGroupCode.equals("09582")) continue;
            HashMap<String, ExcelRowData> majorDic = uniMajors.getValue();

            int plan_Group23 = 0; //23计划数 = 组内求和

            int plan_Group22 = 0; //22计划数 = 组内求和
            int admin_Group22 = 0;//            22录取数 = 组内求和
//            int score_Group22 = 0;//            22组分数
//            int rank_Group22 = 0;//            22组位次

            int plan_Group21 = 0; //21计划数 = 组内求和
            int admin_Group21 = 0;//            21录取数 = 组内求和
//            int score_Group21 = 0;//            21组分数
//            int rank_Group21 = 0;//            21组位次

            int plan_Group20 = 0; //20计划数 = 组内求和
            int admin_Group20 = 0;//            20录取数 = 组内求和
//            int score_Group20 = 0;//            20组分数
//            int rank_Group20 = 0;//            20组位次


            //遍历专业组中的专业
            for (Map.Entry<String, ExcelRowData> major : majorDic.entrySet()) {
                //统计组合信息
                ExcelRowData row = major.getValue();

                String plan23 = row.Cells.get(14).Data.trim(); //23计划数

                String plan22 = row.Cells.get(18).Data.trim(); //22计划数
                String admin22 = row.Cells.get(19).Data.trim();//22录取数
//                String score22 = row.Cells.get(20).Data.trim(); //  22组分数
//                String rank22 = row.Cells.get(21).Data.trim(); //   22组位次

                String plan21 = row.Cells.get(28).Data.trim(); //21计划数
                String admin21 = row.Cells.get(29).Data.trim();//21录取数
//                String score21 = row.Cells.get(30).Data.trim(); //  21组分数
//                String rank21 = row.Cells.get(31).Data.trim(); //   21组位次

                String plan20 = row.Cells.get(38).Data.trim(); //20计划数
                String admin20 = row.Cells.get(39).Data.trim();//20录取数
//                String score20 = row.Cells.get(40).Data.trim(); //  20组分数
//                String rank20 = row.Cells.get(41).Data.trim(); //   20组位次

                plan_Group23 += plan23.isEmpty() || !CommonUtil.isNumeric(plan23) ? 0 : (int) Float.parseFloat(plan23);

                plan_Group22 += plan22.isEmpty() || !CommonUtil.isNumeric(plan22) ? 0 : (int) Float.parseFloat(plan22);
                admin_Group22 += admin22.isEmpty() || !CommonUtil.isNumeric(admin22) ? 0 : (int) Float.parseFloat(admin22);
//                score_Group22 += Integer.parseInt(score22);
//                rank_Group22 += Integer.parseInt(rank22);


                plan_Group21 += plan21.isEmpty() || !CommonUtil.isNumeric(plan21) ? 0 : (int) Float.parseFloat(plan21);
                admin_Group21 += admin21.isEmpty() || !CommonUtil.isNumeric(admin21) ? 0 : (int) Float.parseFloat(admin21);
//                score_Group21 += Integer.parseInt(score21);
//                rank_Group21 += Integer.parseInt(rank21);

                plan_Group20 += plan20.isEmpty() || !CommonUtil.isNumeric(plan20) ? 0 : (int) Float.parseFloat(plan20);
                admin_Group20 += admin20.isEmpty() || !CommonUtil.isNumeric(admin20) ? 0 : (int) Float.parseFloat(admin20);
//                score_Group20 += Integer.parseInt(score20);
//                rank_Group20 += Integer.parseInt(rank20);
            }


            HashMap<String, ExcelRowData> majoriconDic = MajorIconDic.getOrDefault(majorGroupCode, null);//icon 数据
            List<UniMajorInfo.Builder> uniMajorGroupInfos = new ArrayList<>();
            for (Map.Entry<String, ExcelRowData> major : majorDic.entrySet()) {
                String majorCode = major.getKey();
                //记录icon图片信息
                List<String> IconList = new ArrayList<>();
                ExcelRowData iconRow = majoriconDic == null ? null : majoriconDic.getOrDefault(majorCode, null);
                if (iconRow != null) {
                    for (int i = 1; i <= 8; i++) {
                        String cellData = iconRow.Cells.get(3 + i).Data.trim();
                        if (!cellData.isEmpty()) {
                            if (i == 1) {
                                //第四轮学科
                                cellData = GameUtil.ReplaceSpecialChar(cellData);
                                for (int j = 0; j < ConstDefine.ClassLvl.length; j++) {
                                    if(cellData.equals(ConstDefine.ClassLvl[j])){
                                        IconList.add((i + 8 + j) + "");
                                        break;
                                    }
                                }
//                                switch (cellData) {
//                                    case "A-": {
//                                        IconList.add((i + 8 + 0) + "");
//                                        break;
//                                    }
//                                    case "A": {
//                                        IconList.add((i + 8 + 1) + "");
//                                        break;
//                                    }
//                                    case "A+": {
//                                        IconList.add((i + 8 + 2) + "");
//                                        break;
//                                    }
//                                    case "B-": {
//                                        IconList.add((i + 8 + 3) + "");
//                                        break;
//                                    }
//                                    case "B": {
//                                        IconList.add((i + 8 + 4) + "");
//                                        break;
//                                    }
//                                    case "B+": {
//                                        IconList.add((i + 8 + 5) + "");
//                                        break;
//                                    }
//                                    case "C-": {
//                                        IconList.add((i + 8 + 6) + "");
//                                        break;
//                                    }
//                                    case "C": {
//                                        IconList.add((i + 8 + 7) + "");
//                                        break;
//                                    }
//                                    case "C+": {
//                                        IconList.add((i + 8 + 8) + "");
//                                        break;
//                                    }
//                                }


                            } else {
                                IconList.add(i + "");
                            }

                        }
                    }
                }

                //统计组合信息
                ExcelRowData row = major.getValue();

                com.msg.UniMajorInfo.Builder builder = Convert(row, Pici);

                //添加组内求和
                builder.setStudentNum22Plan(plan_Group22 + "");
                builder.setStudentNum22Admit(admin_Group22 + "");

                builder.setStudentNum21Plan(plan_Group21 + "");
                builder.setStudentNum21Admit(admin_Group21 + "");

                builder.setStudentNum20Plan(plan_Group20 + "");
                builder.setStudentNum20Admit(admin_Group20 + "");

                builder.setStudentNum23Plan(plan_Group23 + "");

                builder.addAllMajorIcon(IconList);

                builder.setExamYear(year == null ? "2023" : year);
                //uniMajorInfos.add(builder.build());
                uniMajorGroupInfos.add(builder);
            }

            //保存专业组clsid 和 majroName
            Set<Long> groupClsIds = new HashSet<>();
            Set<String> groupMajorNames = new HashSet<>();
            for (int i = 0; i < uniMajorGroupInfos.size(); i++) {
                com.msg.UniMajorInfo.Builder builder = uniMajorGroupInfos.get(i);
                groupClsIds.addAll(builder.getMajorClsIdsList())  ;
                groupMajorNames.addAll(builder.getMajorNameDetailsList());
            }
            for (int i = 0; i < uniMajorGroupInfos.size(); i++) {
                com.msg.UniMajorInfo.Builder builder = uniMajorGroupInfos.get(i);
                builder.addAllMajorGroupClsIds(new ArrayList<>(groupClsIds));
                builder.addAllMajorGroupMajorName(new ArrayList<>(groupMajorNames));
            }

            //保存专业组信息
            for (int i = 0; i < uniMajorGroupInfos.size(); i++) {
                uniMajorInfos.add(uniMajorGroupInfos.get(i).build());
            }

        }

        return uniMajorInfos;

//
//        com.msg.UniMajorInfo.Builder info = com.msg.UniMajorInfo.newBuilder();
//
//        for (int i = 0; i < table.DataRows.size(); i++) {
//            ExcelRowData row = table.DataRows.get(i);
//
//            String scoreStr = row.Cells.get(0).Data.trim(); //专业组
//            String countStr = row.Cells.get(1).Data.trim(); //人数
//            String accumStr = row.Cells.get(2).Data.trim(); //累计
//            String rankStr = row.Cells.get(3).Data.trim(); //位次
//
//
//            if (scoreStr.isEmpty()) continue; //空分数不记录
//            Integer score = Integer.parseInt(scoreStr);
//            Integer count = countStr.isEmpty() ? 0 : Integer.parseInt(countStr);
//            Integer accumulation = accumStr.isEmpty() ? 0 : Integer.parseInt(accumStr);
//            Integer rank = rankStr.isEmpty() ? 0 : Integer.parseInt(rankStr);
//
//            vo.setScore(score);
//            vo.setAccumulation(accumulation);
//            vo.setCount(count);
//            vo.setRank(rank);
//
//            DBProxy.Getinstance().MajorClsInfoService.saveMajorClsInfo(vo);
//
//            try {
//                Thread.sleep(ConstDefine.InsertDBInterval);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
    }

    private com.msg.UniMajorInfo.Builder Convert(ExcelRowData row, int pici) {

        com.msg.UniMajorInfo.Builder info = com.msg.UniMajorInfo.newBuilder();

        info.setId(0);
        info.setUniMajorCode(CommonUtil.RemoveDot(row.Cells.get(0).Data.trim()));
        info.setPici(pici);
        info.setSchoolName(row.Cells.get(1).Data.trim());
        info.setSchoolLvl1(row.Cells.get(2).Data.trim().isEmpty() ? 0 : 1);
        info.setSchoolLvl2(row.Cells.get(3).Data.trim().isEmpty() ? 0 : 1);
        info.setSchoolLvl3(row.Cells.get(4).Data.trim().isEmpty() ? 0 : 1);
        info.setSchoolType(row.Cells.get(50).Data.trim());
        info.setProvince(row.Cells.get(5).Data.trim());
        info.setCity(row.Cells.get(6).Data.trim());
        info.setBelongDep(row.Cells.get(7).Data.trim());

        String adminRule = row.Cells.get(48).Data.trim();
        info.setAdmissionRule((adminRule.isEmpty() || !CommonUtil.isNumeric(adminRule) ? 0 : (int) Float.parseFloat(adminRule)) + "");
        info.setSchoolRank(row.Cells.get(49).Data.trim());
//        info.setStudentNum22Plan(row.Cells.get(0).Data.trim());
        info.setSubjectRequirement(row.Cells.get(10).Data.trim());
//        info.setStudentNum22Admit(row.Cells.get(0).Data.trim());
        String scoreFor22 = row.Cells.get(20).Data.trim(); //22组分数
//        info.setScoreFor22((scoreFor22.isEmpty() || !CommonUtil.isNumeric(scoreFor22) ? 0 : (int) Float.parseFloat(scoreFor22)) + "");
        String rankFor22 = row.Cells.get(21).Data.trim();  //22组位次
//        info.setRankFor22((rankFor22.isEmpty() || !CommonUtil.isNumeric(rankFor22) ? 0 : (int) Float.parseFloat(rankFor22)) + "");
        List<Integer> scoreFor22List = GameUtil.SplitBoyAndGirlData(scoreFor22);
        List<Integer> rankFor22List = GameUtil.SplitBoyAndGirlData(rankFor22);
        if(scoreFor22List.size() > 1){
            info.setScoreFor22(scoreFor22List.get(0) +"");
            info.setRankFor22((rankFor22List.size() > 0 ? rankFor22List.get(0)  : 0) + "");
            info.setScoreFor22Nv(scoreFor22List.get(1) +"");
            info.setRankFor22Nv((rankFor22List.size() > 1 ? rankFor22List.get(1)  : 0) + "");
            info.setHasGenderScore(EnumHasGenderScore.Yes.getStateID());
        }else {
            info.setScoreFor22((scoreFor22.isEmpty() || !CommonUtil.isNumeric(scoreFor22) ? 0 : (int) Float.parseFloat(scoreFor22)) + "");
            info.setRankFor22((rankFor22.isEmpty() || !CommonUtil.isNumeric(rankFor22) ? 0 : (int) Float.parseFloat(rankFor22)) + "");
            info.setScoreFor22Nv(info.getScoreFor22());
            info.setRankFor22Nv(info.getRankFor22());
            info.setHasGenderScore(EnumHasGenderScore.No.getStateID());
        }


//        info.setStudentNum21Plan(row.Cells.get(0).Data.trim());
//        info.setStudentNum21Admit(row.Cells.get(0).Data.trim());
        String scoreFor21 = row.Cells.get(30).Data.trim();
        String rankFor21 = row.Cells.get(31).Data.trim();
        info.setScoreFor21((scoreFor21.isEmpty() || !CommonUtil.isNumeric(scoreFor21) ? 0 : (int) Float.parseFloat(scoreFor21)) + "");
        info.setRankFor21((rankFor21.isEmpty() || !CommonUtil.isNumeric(rankFor21) ? 0 : (int) Float.parseFloat(rankFor21)) + "");

//        info.setStudentNum20Plan(row.Cells.get(0).Data.trim());
//        info.setStudentNum20Admit(row.Cells.get(0).Data.trim());
        String scoreFor20 = row.Cells.get(40).Data.trim();
        String rankFor20 = row.Cells.get(41).Data.trim();
        info.setScoreFor20((scoreFor20.isEmpty() || !CommonUtil.isNumeric(scoreFor20) ? 0 : (int) Float.parseFloat(scoreFor20)) + "");
        info.setRankFor20((rankFor20.isEmpty() || !CommonUtil.isNumeric(rankFor20) ? 0 : (int) Float.parseFloat(rankFor20)) + "");

        info.setMajorCode(CommonUtil.RemoveDot(row.Cells.get(8).Data.trim()) );
        info.setMajorName(row.Cells.get(9).Data.trim());
        info.setMajorComments(row.Cells.get(13).Data.trim());
        info.setMajorOtherInfo("");

        info.setRequireLang(row.Cells.get(11).Data.trim());
        info.setRequireInterview(row.Cells.get(12).Data.trim());
        info.setMajorDuration(row.Cells.get(16).Data.trim());
        info.setMajorTuition(row.Cells.get(17).Data.trim());

        String major22P = row.Cells.get(18).Data.trim();
        String major22A = row.Cells.get(19).Data.trim();
        String major22LS = row.Cells.get(24).Data.trim();//22 低分数
        String major22LR = row.Cells.get(25).Data.trim();//22 低位次
        info.setMajor22Plan((major22P.isEmpty() || !CommonUtil.isNumeric(major22P) ? 0 : (int) Float.parseFloat(major22P)) + "");
        info.setMajor22Admin((major22A.isEmpty() || !CommonUtil.isNumeric(major22A) ? 0 : (int) Float.parseFloat(major22A)) + "");
//        info.setMajor22LowScore((major22LS.isEmpty() || !CommonUtil.isNumeric(major22LS) ? 0 : (int) Float.parseFloat(major22LS)) + "");
//        info.setMajor22LowRank((major22LR.isEmpty() || !CommonUtil.isNumeric(major22LR) ? 0 : (int) Float.parseFloat(major22LR)) + "");

        List<Integer> major22LSList = GameUtil.SplitBoyAndGirlData(major22LS);
        List<Integer> major22LRList = GameUtil.SplitBoyAndGirlData(major22LR);
        if(major22LSList.size() > 1){
            info.setMajor22LowScore(major22LSList.get(0) +"");
            info.setMajor22LowRank((major22LRList.size() > 0 ? major22LRList.get(0)  : 0) + "");
            info.setMajor22LowScoreNv(major22LSList.get(1) +"");
            info.setMajor22LowRankNv((major22LRList.size() > 1 ? major22LRList.get(1)  : 0) + "");
//            info.setHasGenderScore(EnumHasGenderScore.Yes.getStateID());
        }else {
            info.setMajor22LowScore((major22LS.isEmpty() || !CommonUtil.isNumeric(major22LS) ? 0 : (int) Float.parseFloat(major22LS)) + "");
            info.setMajor22LowRank((major22LR.isEmpty() || !CommonUtil.isNumeric(major22LR) ? 0 : (int) Float.parseFloat(major22LR)) + "");
            info.setMajor22LowScoreNv(info.getMajor22LowScore());
            info.setMajor22LowRankNv(info.getMajor22LowRank());
//            info.setHasGenderScore(EnumHasGenderScore.No.getStateID());
        }



        String major22HS = row.Cells.get(22).Data.trim();
        String major22HRank = row.Cells.get(23).Data.trim();
        String major22AvgS = row.Cells.get(26).Data.trim();
        String major22AvgRank = row.Cells.get(27).Data.trim();
        info.setMajor22HScore((major22HS.isEmpty() || !CommonUtil.isNumeric(major22HS) ? 0 : (int) Float.parseFloat(major22HS)) + "");
        info.setMajor22HRank((major22HRank.isEmpty() || !CommonUtil.isNumeric(major22HRank) ? 0 : (int) Float.parseFloat(major22HRank)) + "");
        info.setMajor22AvgScore((major22AvgS.isEmpty() || !CommonUtil.isNumeric(major22AvgS) ? 0 : (int) Float.parseFloat(major22AvgS)) + "");
        info.setMajor22AvgRank((major22AvgRank.isEmpty() || !CommonUtil.isNumeric(major22AvgRank) ? 0 : (int) Float.parseFloat(major22AvgRank)) + "");


        String major21P = row.Cells.get(28).Data.trim();
        String major21A = row.Cells.get(29).Data.trim();
        String major21LS = row.Cells.get(34).Data.trim();
        String major21LR = row.Cells.get(35).Data.trim();
        info.setMajor21Plan((major21P.isEmpty() || !CommonUtil.isNumeric(major21P) ? 0 : (int) Float.parseFloat(major21P)) + "");
        info.setMajor21Admin((major21A.isEmpty() || !CommonUtil.isNumeric(major21A) ? 0 : (int) Float.parseFloat(major21A)) + "");
        info.setMajor21LowScore((major21LS.isEmpty() || !CommonUtil.isNumeric(major21LS) ? 0 : (int) Float.parseFloat(major21LS)) + "");
        info.setMajor21LowRank((major21LR.isEmpty() || !CommonUtil.isNumeric(major21LR) ? 0 : (int) Float.parseFloat(major21LR)) + "");


        String major21HS = row.Cells.get(32).Data.trim();
        String major21HRank = row.Cells.get(33).Data.trim();
        String major21AvgS = row.Cells.get(36).Data.trim();
        String major21AvgRank = row.Cells.get(37).Data.trim();
        info.setMajor21HScore((major21HS.isEmpty() || !CommonUtil.isNumeric(major21HS) ? 0 : (int) Float.parseFloat(major21HS)) + "");
        info.setMajor21HRank((major21HRank.isEmpty() || !CommonUtil.isNumeric(major21HRank) ? 0 : (int) Float.parseFloat(major21HRank)) + "");
        info.setMajor21AvgScore((major21AvgS.isEmpty() || !CommonUtil.isNumeric(major21AvgS) ? 0 : (int) Float.parseFloat(major21AvgS)) + "");
        info.setMajor21AvgRank((major21AvgRank.isEmpty() || !CommonUtil.isNumeric(major21AvgRank) ? 0 : (int) Float.parseFloat(major21AvgRank)) + "");


        String major20P = row.Cells.get(38).Data.trim();
        String major20A = row.Cells.get(39).Data.trim();
        String major20LS = row.Cells.get(44).Data.trim();
        String major20LR = row.Cells.get(45).Data.trim();
        info.setMajor20Plan((major20P.isEmpty() || !CommonUtil.isNumeric(major20P) ? 0 : (int) Float.parseFloat(major20P)) + "");
        info.setMajor20Admin((major20A.isEmpty() || !CommonUtil.isNumeric(major20A) ? 0 : (int) Float.parseFloat(major20A)) + "");
        info.setMajor20LowScore((major20LS.isEmpty() || !CommonUtil.isNumeric(major20LS) ? 0 : (int) Float.parseFloat(major20LS)) + "");
        info.setMajor20LowRank((major20LR.isEmpty() || !CommonUtil.isNumeric(major20LR) ? 0 : (int) Float.parseFloat(major20LR)) + "");

        String major20HS = row.Cells.get(42).Data.trim();
        String major20HRank = row.Cells.get(43).Data.trim();
        String major20AvgS = row.Cells.get(46).Data.trim();
        String major20AvgRank = row.Cells.get(47).Data.trim();
        info.setMajor20HScore((major20HS.isEmpty() || !CommonUtil.isNumeric(major20HS) ? 0 : (int) Float.parseFloat(major20HS)) + "");
        info.setMajor20HRank((major20HRank.isEmpty() || !CommonUtil.isNumeric(major20HRank) ? 0 : (int) Float.parseFloat(major20HRank)) + "");
        info.setMajor20AvgScore((major20AvgS.isEmpty() || !CommonUtil.isNumeric(major20AvgS) ? 0 : (int) Float.parseFloat(major20AvgS)) + "");
        info.setMajor20AvgRank((major20AvgRank.isEmpty() || !CommonUtil.isNumeric(major20AvgRank) ? 0 : (int) Float.parseFloat(major20AvgRank)) + "");


        String major23Plan = row.Cells.get(14).Data.trim();//23计划数
        info.setMajor23Plan((major23Plan.isEmpty() || !CommonUtil.isNumeric(major23Plan) ? 0 : (int) Float.parseFloat(major23Plan)) + "");


        String EnglishScore = row.Cells.get(51).Data.trim();//英语成绩限制
        info.setEnglishScore((EnglishScore.isEmpty() || !CommonUtil.isNumeric(EnglishScore) ? 0 : (int) Float.parseFloat(EnglishScore)) );

        String MathScore = row.Cells.get(52).Data.trim();//数学成绩限制
        info.setMathScore((MathScore.isEmpty() || !CommonUtil.isNumeric(MathScore) ? 0 : (int) Float.parseFloat(MathScore)));

        String heightLimit = row.Cells.get(53).Data.trim();//身高限制
        info.setHeightLimit((heightLimit.isEmpty() || !CommonUtil.isNumeric(heightLimit) ? "" : (int) Float.parseFloat(heightLimit)) + "" );

        //设置中外合作
        info.setIsBenSuo(DBDataTool.IsBenSuo(info.getMajorName(), info.getMajorDuration()) ? 1 : 0);
        //设置本硕
        info.setIsZhongWai(DBDataTool.IsZhongWaiHeZuo(info.getMajorName()) ? 1 : 0);

        //设置 保研率 和  升学率 todo
        String baoYanLv = "";//row.Cells.get(53).Data.trim();//保研率
        String shengXueLv = "";//row.Cells.get(53).Data.trim();//升学率
        info.setBaoYanLv(baoYanLv);
        info.setShengXueLv(shengXueLv);

        //保存所有专业id
//        info.setMajorClsIds("");
        String ids = "";
        List<String> majorNameDetails = new ArrayList<>();//专业名 细节
        //查询数据库,
        String majors = row.Cells.get(54).Data.trim();
        if (!majors.trim().isEmpty()) {
            majors = majors.replace("、", ConstDefine.ItemSperator7);
            majors = majors.replace("，", ConstDefine.ItemSperator7);

//            majors = majors.replace("、", ConstDefine.ItemSperator7);

            String[] majs = majors.split(ConstDefine.ItemSperator7);
            for (int i = 0; i < majs.length; i++) {
                String m = majs[i];
                if (m.isEmpty()) continue;

                //查询数据库, 获取major id
                List<MajorClsInfoVO> list = DBProxy.Getinstance().MajorClsInfoService.findByMajor3rdCls(m);
                if (list.size() == 0) {
//                    System.out.println("no find major name " + info.getUniMajorCode() + "," + m);
                    continue; //没有查询到专业
                }

                if (!ids.isEmpty()) {
                    ids += ConstDefine.ItemSperator7;
                }
                ids += list.get(0).getId();
                majorNameDetails.add(list.get(0).getMajor3rdCls());
            }

            info.addAllMajorClsIds(StringParser.SplitLongNumber(ids, ConstDefine.ItemSperator7));
            info.addAllMajorNameDetails(majorNameDetails);
        }


        return info;
    }

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    HashMap<String, HashMap<String, ExcelRowData>> MajorIcon = new HashMap<>();

    protected void ResetMajorIconDic(ExcelTableData table) {
        MajorIcon.clear();

        for (int i = 0; i < table.DataRows.size(); i++) {
            ExcelRowData row = table.DataRows.get(i);

            String groupCode = row.Cells.get(0).Data.trim(); //专业组代码
            String majorCode = row.Cells.get(2).Data.trim(); //专业代码

            if (groupCode.isEmpty()) continue; //空分数不记录

            HashMap<String, ExcelRowData> majorDic = MajorIcon.getOrDefault(groupCode, null);
            if (majorDic == null) {
                majorDic = new HashMap<>();
                MajorIcon.put(groupCode, majorDic);
            }

            ExcelRowData uniMajor = majorDic.getOrDefault(majorCode, null);
            if (uniMajor == null) {
                majorDic.put(majorCode, row);
            } else {
//                uniMajor.Cells
                //合并2个row
                for (int j = 4; j < row.Cells.size(); j++) {
                    String contentNew = row.Cells.get(j).Data.trim();
                    contentNew = GameUtil.ReplaceSpecialChar(contentNew);
                    String contentCur = uniMajor.Cells.get(j).Data.trim();
                    if(contentNew.isEmpty()) continue; //新内容 空, 跳过
                    if(!contentCur.isEmpty()) continue; //非空的内容直接跳过

                    if(j == 4){
                        boolean hasContent = false;
                        for (int k = 0; k < ConstDefine.ClassLvl.length; k++) {
                            if(contentNew.equals(ConstDefine.ClassLvl[k])){
                                hasContent = true;
                                break;
                            }
                        }

                        if(hasContent){
                            uniMajor.Cells.get(j).Data = contentNew;
                        }

                    }else {
                        uniMajor.Cells.get(j).Data = contentNew;
                    }
                }

//                System.out.println("major icon 专业重复 " + uniMajor.Cells.get(3));
            }

        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

//更新每个  学校的类型
    protected void FixShoolType( HashMap<String, HashMap<String, ExcelRowData>> UniMajorDic) {
//        UniMajorDic.clear();s
        HashMap<String, String> SchoolNameAndTypeDic = new HashMap<>();

        //记录学校和类型
        for (Map.Entry<String, HashMap<String, ExcelRowData>> uniMajors : UniMajorDic.entrySet()) {
            String majorGroupCode = uniMajors.getKey();  //专业组信息

            HashMap<String, ExcelRowData> majorDic = uniMajors.getValue();

            //遍历专业组中的专业
            for (Map.Entry<String, ExcelRowData> major : majorDic.entrySet()) {
                ExcelRowData row = major.getValue();
                String schooleNames = (row.Cells.get(1).Data.trim());//学校
                String schooleTypeofExcel = row.Cells.get(50).Data.trim();//类型
                 //跳过 空
                if(StringUtils.isEmpty(schooleTypeofExcel)) continue;

                //保存类型
                String schoolType =  SchoolNameAndTypeDic.getOrDefault(schooleNames, null);
                if(schoolType == null){
                    SchoolNameAndTypeDic.put(schooleNames, schooleTypeofExcel);
                }else {
                    //保存最长的类型
                    if(schoolType.length() < schooleTypeofExcel.length()){
                        SchoolNameAndTypeDic.put(schooleNames, schooleTypeofExcel);
                    }
                }
            }

        }


        //重置类型
        for (Map.Entry<String, HashMap<String, ExcelRowData>> uniMajors : UniMajorDic.entrySet()) {
            String majorGroupCode = uniMajors.getKey();  //专业组信息

            HashMap<String, ExcelRowData> majorDic = uniMajors.getValue();

            //遍历专业组中的专业
            for (Map.Entry<String, ExcelRowData> major : majorDic.entrySet()) {
                ExcelRowData row = major.getValue();
                String schooleNames = (row.Cells.get(1).Data.trim());//学校
                String schooleTypeofExcel = row.Cells.get(50).Data.trim();//类型

                //保存类型
                String schoolType =  SchoolNameAndTypeDic.getOrDefault(schooleNames, null);
                if(schoolType != null){
                    row.Cells.get(50).Data = schoolType;
                }
            }
        }

    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    private void SavePiciA(List<UniMajorInfo> protoList, Integer NewVersion, EnumPici pici, String examYear){
        Date time1 = new Date();

        //保存历史库
        List<UniMajorInfoA> entityList = new ArrayList<>();
        for (int i = 0; i < protoList.size(); i++) {
            UniMajorInfo proto = protoList.get(i);
            //保存历史库
            UniMajorInfoAVO histDB = UniMajorInfoAVO.ConvertFromDTO(proto);
            histDB.setId(null);
            histDB.setDataVersion(NewVersion);
            entityList.add(BeanMapper.map(histDB, UniMajorInfoA.class));
        }
        DBProxy.Getinstance().UniMajorInfoAService.batchSave(entityList);

        Date time2 = new Date();
        //保存最新数据库
        List<UniMajorInfoALatest> entityLatestList = new ArrayList<>();
        for (int i = 0; i < protoList.size(); i++) {
            UniMajorInfo proto = protoList.get(i);

            Long latestId = entityList.get(i).getId();
            //保存最新数据库
            UniMajorInfoALatestVO latestDB = UniMajorInfoALatestVO.ConvertFromDTO(proto);
            latestDB.setId(null);//(null);
            latestDB.setDataVersion(NewVersion);
            latestDB.setIdInHistDB(latestId);

            entityLatestList.add(BeanMapper.map(latestDB, UniMajorInfoALatest.class));
//            DBProxy.Getinstance().UniMajorInfoALatestService.saveUniMajorInfoALatest(latestDB);
        }

        DBProxy.Getinstance().UniMajorInfoALatestService.batchSave(entityLatestList);


        Date time3 = new Date();
        HashMap<String, Long> latestIdDic = new HashMap<>(); //key unicode+majorCode;  val: latestId
        //保存LatestID
        for (int i = 0; i < entityList.size(); i++) {
            Long latestId = entityList.get(i).getId();
            entityList.get(i).setIdInLatestDB(latestId);
            latestIdDic.put( entityList.get(i).getUniMajorCode() +"_"+entityList.get(i).getMajorCode(), latestId );
        }
        DBProxy.Getinstance().UniMajorInfoAService.batchUpdate(entityList);


        Date time4 = new Date();
        //更新
        List<UniMajorInfoA> oldEntityList = new ArrayList<>();

        List<Long> majorid = GetTemplateAndFormMajorList(pici, examYear);
        List<UniMajorInfoAVO> templateMajorInfoList = DBProxy.Getinstance().UniMajorInfoAService.findByIdIn(majorid);
        for (int i = 0; i < templateMajorInfoList.size(); i++) {
            UniMajorInfoAVO oldMajorInfo = templateMajorInfoList.get(i);

            Long latestId = latestIdDic.getOrDefault(oldMajorInfo.getUniMajorCode() + "_" + oldMajorInfo.getMajorCode(), null);
            if(latestId != null){
                oldMajorInfo.setIdInLatestDB(latestId);
            }else {
                ////System.out.println("");
            }

            oldEntityList.add(BeanMapper.map(oldMajorInfo, UniMajorInfoA.class));
        }
        DBProxy.Getinstance().UniMajorInfoAService.batchUpdate(oldEntityList);

        Date time5 = new Date();

        System.out.println("update pici " + pici.name() + ",保存Hist:" + (time2.getTime() - time1.getTime())
                + ",保存Latest:" + (time3.getTime() - time2.getTime())
                + ",更新HistDB的 latestId:" + (time4.getTime() - time3.getTime())
                + ",更新formId: " + (time5.getTime() - time4.getTime())
                + ",总共: " + (time5.getTime() - time1.getTime()));
    }

    private void SavePiciB(List<UniMajorInfo> protoList, Integer NewVersion, EnumPici pici, String examYear){
        Date time1 = new Date();

        //保存历史库
        List<UniMajorInfoB> entityList = new ArrayList<>();
        for (int i = 0; i < protoList.size(); i++) {
            UniMajorInfo proto = protoList.get(i);
            //保存历史库
            UniMajorInfoBVO histDB = UniMajorInfoBVO.ConvertFromDTO(proto);
            histDB.setId(null);
            histDB.setDataVersion(NewVersion);
            entityList.add(BeanMapper.map(histDB, UniMajorInfoB.class));
        }
        DBProxy.Getinstance().UniMajorInfoBService.batchSave(entityList);

        Date time2 = new Date();
        //保存最新数据库
        List<UniMajorInfoBLatest> entityLatestList = new ArrayList<>();
        for (int i = 0; i < protoList.size(); i++) {
            UniMajorInfo proto = protoList.get(i);

            Long latestId = entityList.get(i).getId();
            //保存最新数据库
            UniMajorInfoBLatestVO latestDB = UniMajorInfoBLatestVO.ConvertFromDTO(proto);
            latestDB.setId(null);//(null);
            latestDB.setDataVersion(NewVersion);
            latestDB.setIdInHistDB(latestId);

            entityLatestList.add(BeanMapper.map(latestDB, UniMajorInfoBLatest.class));
//            DBProxy.Getinstance().UniMajorInfoBLatestService.saveUniMajorInfoBLatest(latestDB);
        }

        DBProxy.Getinstance().UniMajorInfoBLatestService.batchSave(entityLatestList);


        Date time3 = new Date();
        HashMap<String, Long> latestIdDic = new HashMap<>(); //key unicode+majorCode;  val: latestId
        //保存LatestID
        for (int i = 0; i < entityList.size(); i++) {
            Long latestId = entityList.get(i).getId();
            entityList.get(i).setIdInLatestDB(latestId);
            latestIdDic.put( entityList.get(i).getUniMajorCode() +"_"+entityList.get(i).getMajorCode(), latestId );
        }
        DBProxy.Getinstance().UniMajorInfoBService.batchUpdate(entityList);


        Date time4 = new Date();
        //更新
        List<UniMajorInfoB> oldEntityList = new ArrayList<>();

        List<Long> majorid = GetTemplateAndFormMajorList(pici, examYear);
        List<UniMajorInfoBVO> templateMajorInfoList = DBProxy.Getinstance().UniMajorInfoBService.findByIdIn(majorid);
        for (int i = 0; i < templateMajorInfoList.size(); i++) {
            UniMajorInfoBVO oldMajorInfo = templateMajorInfoList.get(i);

            Long latestId = latestIdDic.getOrDefault(oldMajorInfo.getUniMajorCode() + "_" + oldMajorInfo.getMajorCode(), null);
            if(latestId != null){
                oldMajorInfo.setIdInLatestDB(latestId);
            }else {
                ////System.out.println("");
            }

            oldEntityList.add(BeanMapper.map(oldMajorInfo, UniMajorInfoB.class));
        }
        DBProxy.Getinstance().UniMajorInfoBService.batchUpdate(oldEntityList);

        Date time5 = new Date();

        System.out.println("update pici " + pici.name() + "保存Hist" + (time2.getTime() - time1.getTime())
                + "保存Latest" + (time3.getTime() - time2.getTime())
                + "更新HistDB的 latestId" + (time4.getTime() - time3.getTime())
                + "更新formId " + (time5.getTime() - time4.getTime())
                + "总共 " + (time5.getTime() - time1.getTime()));
    }

    private void SavePiciPreA(List<UniMajorInfo> protoList, Integer NewVersion, EnumPici pici, String examYear){
        Date time1 = new Date();

        //保存历史库
        List<UniMajorInfoPreA> entityList = new ArrayList<>();
        for (int i = 0; i < protoList.size(); i++) {
            UniMajorInfo proto = protoList.get(i);
            //保存历史库
            UniMajorInfoPreAVO histDB = UniMajorInfoPreAVO.ConvertFromDTO(proto);
            histDB.setId(null);
            histDB.setDataVersion(NewVersion);
            entityList.add(BeanMapper.map(histDB, UniMajorInfoPreA.class));
        }
        DBProxy.Getinstance().UniMajorInfoPreAService.batchSave(entityList);

        Date time2 = new Date();
        //保存最新数据库
        List<UniMajorInfoPreALatest> entityLatestList = new ArrayList<>();
        for (int i = 0; i < protoList.size(); i++) {
            UniMajorInfo proto = protoList.get(i);

            Long latestId = entityList.get(i).getId();
            //保存最新数据库
            UniMajorInfoPreALatestVO latestDB = UniMajorInfoPreALatestVO.ConvertFromDTO(proto);
            latestDB.setId(null);//(null);
            latestDB.setDataVersion(NewVersion);
            latestDB.setIdInHistDB(latestId);

            entityLatestList.add(BeanMapper.map(latestDB, UniMajorInfoPreALatest.class));
//            DBProxy.Getinstance().UniMajorInfoPreALatestService.saveUniMajorInfoPreALatest(latestDB);
        }

        DBProxy.Getinstance().UniMajorInfoPreALatestService.batchSave(entityLatestList);


        Date time3 = new Date();
        HashMap<String, Long> latestIdDic = new HashMap<>(); //key unicode+majorCode;  val: latestId
        //保存LatestID
        for (int i = 0; i < entityList.size(); i++) {
            Long latestId = entityList.get(i).getId();
            entityList.get(i).setIdInLatestDB(latestId);
            latestIdDic.put( entityList.get(i).getUniMajorCode() +"_"+entityList.get(i).getMajorCode(), latestId );
        }
        DBProxy.Getinstance().UniMajorInfoPreAService.batchUpdate(entityList);


        Date time4 = new Date();
        //更新
        List<UniMajorInfoPreA> oldEntityList = new ArrayList<>();

        List<Long> majorid = GetTemplateAndFormMajorList(pici, examYear);
        List<UniMajorInfoPreAVO> templateMajorInfoList = DBProxy.Getinstance().UniMajorInfoPreAService.findByIdIn(majorid);
        for (int i = 0; i < templateMajorInfoList.size(); i++) {
            UniMajorInfoPreAVO oldMajorInfo = templateMajorInfoList.get(i);

            Long latestId = latestIdDic.getOrDefault(oldMajorInfo.getUniMajorCode() + "_" + oldMajorInfo.getMajorCode(), null);
            if(latestId != null){
                oldMajorInfo.setIdInLatestDB(latestId);
            }else {
                ////System.out.println("");
            }

            oldEntityList.add(BeanMapper.map(oldMajorInfo, UniMajorInfoPreA.class));
        }
        DBProxy.Getinstance().UniMajorInfoPreAService.batchUpdate(oldEntityList);

        Date time5 = new Date();

        System.out.println("update pici " + pici.name() + "保存Hist" + (time2.getTime() - time1.getTime())
                + "保存Latest" + (time3.getTime() - time2.getTime())
                + "更新HistDB的 latestId" + (time4.getTime() - time3.getTime())
                + "更新formId " + (time5.getTime() - time4.getTime())
                + "总共 " + (time5.getTime() - time1.getTime()));
    }

    private void SavePiciPreB(List<UniMajorInfo> protoList, Integer NewVersion, EnumPici pici, String examYear){
        Date time1 = new Date();

        //保存历史库
        List<UniMajorInfoPreB> entityList = new ArrayList<>();
        for (int i = 0; i < protoList.size(); i++) {
            UniMajorInfo proto = protoList.get(i);
            //保存历史库
            UniMajorInfoPreBVO histDB = UniMajorInfoPreBVO.ConvertFromDTO(proto);
            histDB.setId(null);
            histDB.setDataVersion(NewVersion);
            entityList.add(BeanMapper.map(histDB, UniMajorInfoPreB.class));
        }
        DBProxy.Getinstance().UniMajorInfoPreBService.batchSave(entityList);

        Date time2 = new Date();
        //保存最新数据库
        List<UniMajorInfoPreBLatest> entityLatestList = new ArrayList<>();
        for (int i = 0; i < protoList.size(); i++) {
            UniMajorInfo proto = protoList.get(i);

            Long latestId = entityList.get(i).getId();
            //保存最新数据库
            UniMajorInfoPreBLatestVO latestDB = UniMajorInfoPreBLatestVO.ConvertFromDTO(proto);
            latestDB.setId(null);//(null);
            latestDB.setDataVersion(NewVersion);
            latestDB.setIdInHistDB(latestId);

            entityLatestList.add(BeanMapper.map(latestDB, UniMajorInfoPreBLatest.class));
//            DBProxy.Getinstance().UniMajorInfoPreBLatestService.saveUniMajorInfoPreBLatest(latestDB);
        }

        DBProxy.Getinstance().UniMajorInfoPreBLatestService.batchSave(entityLatestList);


        Date time3 = new Date();
        HashMap<String, Long> latestIdDic = new HashMap<>(); //key unicode+majorCode;  val: latestId
        //保存LatestID
        for (int i = 0; i < entityList.size(); i++) {
            Long latestId = entityList.get(i).getId();
            entityList.get(i).setIdInLatestDB(latestId);
            latestIdDic.put( entityList.get(i).getUniMajorCode() +"_"+entityList.get(i).getMajorCode(), latestId );
        }
        DBProxy.Getinstance().UniMajorInfoPreBService.batchUpdate(entityList);


        Date time4 = new Date();
        //更新
        List<UniMajorInfoPreB> oldEntityList = new ArrayList<>();

        List<Long> majorid = GetTemplateAndFormMajorList(pici, examYear);
        List<UniMajorInfoPreBVO> templateMajorInfoList = DBProxy.Getinstance().UniMajorInfoPreBService.findByIdIn(majorid);
        for (int i = 0; i < templateMajorInfoList.size(); i++) {
            UniMajorInfoPreBVO oldMajorInfo = templateMajorInfoList.get(i);

            Long latestId = latestIdDic.getOrDefault(oldMajorInfo.getUniMajorCode() + "_" + oldMajorInfo.getMajorCode(), null);
            if(latestId != null){
                oldMajorInfo.setIdInLatestDB(latestId);
            }else {
                ////System.out.println("");
            }

            oldEntityList.add(BeanMapper.map(oldMajorInfo, UniMajorInfoPreB.class));
        }
        DBProxy.Getinstance().UniMajorInfoPreBService.batchUpdate(oldEntityList);

        Date time5 = new Date();

        System.out.println("update pici " + pici.name() + "保存Hist" + (time2.getTime() - time1.getTime())
                + "保存Latest" + (time3.getTime() - time2.getTime())
                + "更新HistDB的 latestId" + (time4.getTime() - time3.getTime())
                + "更新formId " + (time5.getTime() - time4.getTime())
                + "总共 " + (time5.getTime() - time1.getTime()));
    }

    private void SavePiciZhuanKe(List<UniMajorInfo> protoList, Integer NewVersion, EnumPici pici, String examYear){
        Date time1 = new Date();

        //保存历史库
        List<UniMajorInfoZhuanKe> entityList = new ArrayList<>();
        for (int i = 0; i < protoList.size(); i++) {
            UniMajorInfo proto = protoList.get(i);
            //保存历史库
            UniMajorInfoZhuanKeVO histDB = UniMajorInfoZhuanKeVO.ConvertFromDTO(proto);
            histDB.setId(null);
            histDB.setDataVersion(NewVersion);
            entityList.add(BeanMapper.map(histDB, UniMajorInfoZhuanKe.class));
        }
        DBProxy.Getinstance().UniMajorInfoZhuanKeService.batchSave(entityList);

        Date time2 = new Date();
        //保存最新数据库
        List<UniMajorInfoZhuanKeLatest> entityLatestList = new ArrayList<>();
        for (int i = 0; i < protoList.size(); i++) {
            UniMajorInfo proto = protoList.get(i);

            Long latestId = entityList.get(i).getId();
            //保存最新数据库
            UniMajorInfoZhuanKeLatestVO latestDB = UniMajorInfoZhuanKeLatestVO.ConvertFromDTO(proto);
            latestDB.setId(null);//(null);
            latestDB.setDataVersion(NewVersion);
            latestDB.setIdInHistDB(latestId);

            entityLatestList.add(BeanMapper.map(latestDB, UniMajorInfoZhuanKeLatest.class));
//            DBProxy.Getinstance().UniMajorInfoZhuanKeLatestService.saveUniMajorInfoZhuanKeLatest(latestDB);
        }

        DBProxy.Getinstance().UniMajorInfoZhuanKeLatestService.batchSave(entityLatestList);


        Date time3 = new Date();
        HashMap<String, Long> latestIdDic = new HashMap<>(); //key unicode+majorCode;  val: latestId
        //保存LatestID
        for (int i = 0; i < entityList.size(); i++) {
            Long latestId = entityList.get(i).getId();
            entityList.get(i).setIdInLatestDB(latestId);
            latestIdDic.put( entityList.get(i).getUniMajorCode() +"_"+entityList.get(i).getMajorCode(), latestId );
        }
        DBProxy.Getinstance().UniMajorInfoZhuanKeService.batchUpdate(entityList);


        Date time4 = new Date();
        //更新
        List<UniMajorInfoZhuanKe> oldEntityList = new ArrayList<>();

        List<Long> majorid = GetTemplateAndFormMajorList(pici, examYear);
        List<UniMajorInfoZhuanKeVO> templateMajorInfoList = DBProxy.Getinstance().UniMajorInfoZhuanKeService.findByIdIn(majorid);
        for (int i = 0; i < templateMajorInfoList.size(); i++) {
            UniMajorInfoZhuanKeVO oldMajorInfo = templateMajorInfoList.get(i);

            Long latestId = latestIdDic.getOrDefault(oldMajorInfo.getUniMajorCode() + "_" + oldMajorInfo.getMajorCode(), null);
            if(latestId != null){
                oldMajorInfo.setIdInLatestDB(latestId);
            }else {
                ////System.out.println("");
            }

            oldEntityList.add(BeanMapper.map(oldMajorInfo, UniMajorInfoZhuanKe.class));
        }
        DBProxy.Getinstance().UniMajorInfoZhuanKeService.batchUpdate(oldEntityList);

        Date time5 = new Date();

        System.out.println("update pici " + pici.name() + "保存Hist" + (time2.getTime() - time1.getTime())
                + "保存Latest" + (time3.getTime() - time2.getTime())
                + "更新HistDB的 latestId" + (time4.getTime() - time3.getTime())
                + "更新formId " + (time5.getTime() - time4.getTime())
                + "总共 " + (time5.getTime() - time1.getTime()));
    }

    private void SavePiciZXA(List<UniMajorInfo> protoList, Integer NewVersion, EnumPici pici, String examYear){
        Date time1 = new Date();

        //保存历史库
        List<UniMajorInfoZXA> entityList = new ArrayList<>();
        for (int i = 0; i < protoList.size(); i++) {
            UniMajorInfo proto = protoList.get(i);
            //保存历史库
            UniMajorInfoZXAVO histDB = UniMajorInfoZXAVO.ConvertFromDTO(proto);
            histDB.setId(null);
            histDB.setDataVersion(NewVersion);
            entityList.add(BeanMapper.map(histDB, UniMajorInfoZXA.class));
        }
        DBProxy.Getinstance().UniMajorInfoZXAService.batchSave(entityList);

        Date time2 = new Date();
        //保存最新数据库
        List<UniMajorInfoZXALatest> entityLatestList = new ArrayList<>();
        for (int i = 0; i < protoList.size(); i++) {
            UniMajorInfo proto = protoList.get(i);

            Long latestId = entityList.get(i).getId();
            //保存最新数据库
            UniMajorInfoZXALatestVO latestDB = UniMajorInfoZXALatestVO.ConvertFromDTO(proto);
            latestDB.setId(null);//(null);
            latestDB.setDataVersion(NewVersion);
            latestDB.setIdInHistDB(latestId);

            entityLatestList.add(BeanMapper.map(latestDB, UniMajorInfoZXALatest.class));
//            DBProxy.Getinstance().UniMajorInfoZXALatestService.saveUniMajorInfoZXALatest(latestDB);
        }

        DBProxy.Getinstance().UniMajorInfoZXALatestService.batchSave(entityLatestList);


        Date time3 = new Date();
        HashMap<String, Long> latestIdDic = new HashMap<>(); //key unicode+majorCode;  val: latestId
        //保存LatestID
        for (int i = 0; i < entityList.size(); i++) {
            Long latestId = entityList.get(i).getId();
            entityList.get(i).setIdInLatestDB(latestId);
            latestIdDic.put( entityList.get(i).getUniMajorCode() +"_"+entityList.get(i).getMajorCode(), latestId );
        }
        DBProxy.Getinstance().UniMajorInfoZXAService.batchUpdate(entityList);


        Date time4 = new Date();
        //更新
        List<UniMajorInfoZXA> oldEntityList = new ArrayList<>();

        List<Long> majorid = GetTemplateAndFormMajorList(pici, examYear);
        List<UniMajorInfoZXAVO> templateMajorInfoList = DBProxy.Getinstance().UniMajorInfoZXAService.findByIdIn(majorid);
        for (int i = 0; i < templateMajorInfoList.size(); i++) {
            UniMajorInfoZXAVO oldMajorInfo = templateMajorInfoList.get(i);

            Long latestId = latestIdDic.getOrDefault(oldMajorInfo.getUniMajorCode() + "_" + oldMajorInfo.getMajorCode(), null);
            if(latestId != null){
                oldMajorInfo.setIdInLatestDB(latestId);
            }else {
                ////System.out.println("");
            }

            oldEntityList.add(BeanMapper.map(oldMajorInfo, UniMajorInfoZXA.class));
        }
        DBProxy.Getinstance().UniMajorInfoZXAService.batchUpdate(oldEntityList);

        Date time5 = new Date();

        System.out.println("update pici " + pici.name() + "保存Hist" + (time2.getTime() - time1.getTime())
                + "保存Latest" + (time3.getTime() - time2.getTime())
                + "更新HistDB的 latestId" + (time4.getTime() - time3.getTime())
                + "更新formId " + (time5.getTime() - time4.getTime())
                + "总共 " + (time5.getTime() - time1.getTime()));
    }

    private void SavePiciZXB(List<UniMajorInfo> protoList, Integer NewVersion, EnumPici pici, String examYear){
        Date time1 = new Date();

        //保存历史库
        List<UniMajorInfoZXB> entityList = new ArrayList<>();
        for (int i = 0; i < protoList.size(); i++) {
            UniMajorInfo proto = protoList.get(i);
            //保存历史库
            UniMajorInfoZXBVO histDB = UniMajorInfoZXBVO.ConvertFromDTO(proto);
            histDB.setId(null);
            histDB.setDataVersion(NewVersion);
            entityList.add(BeanMapper.map(histDB, UniMajorInfoZXB.class));
        }
        DBProxy.Getinstance().UniMajorInfoZXBService.batchSave(entityList);

        Date time2 = new Date();
        //保存最新数据库
        List<UniMajorInfoZXBLatest> entityLatestList = new ArrayList<>();
        for (int i = 0; i < protoList.size(); i++) {
            UniMajorInfo proto = protoList.get(i);

            Long latestId = entityList.get(i).getId();
            //保存最新数据库
            UniMajorInfoZXBLatestVO latestDB = UniMajorInfoZXBLatestVO.ConvertFromDTO(proto);
            latestDB.setId(null);//(null);
            latestDB.setDataVersion(NewVersion);
            latestDB.setIdInHistDB(latestId);

            entityLatestList.add(BeanMapper.map(latestDB, UniMajorInfoZXBLatest.class));
//            DBProxy.Getinstance().UniMajorInfoZXBLatestService.saveUniMajorInfoZXBLatest(latestDB);
        }

        DBProxy.Getinstance().UniMajorInfoZXBLatestService.batchSave(entityLatestList);


        Date time3 = new Date();
        HashMap<String, Long> latestIdDic = new HashMap<>(); //key unicode+majorCode;  val: latestId
        //保存LatestID
        for (int i = 0; i < entityList.size(); i++) {
            Long latestId = entityList.get(i).getId();
            entityList.get(i).setIdInLatestDB(latestId);
            latestIdDic.put( entityList.get(i).getUniMajorCode() +"_"+entityList.get(i).getMajorCode(), latestId );
        }
        DBProxy.Getinstance().UniMajorInfoZXBService.batchUpdate(entityList);


        Date time4 = new Date();
        //更新
        List<UniMajorInfoZXB> oldEntityList = new ArrayList<>();

        List<Long> majorid = GetTemplateAndFormMajorList(pici, examYear);
        List<UniMajorInfoZXBVO> templateMajorInfoList = DBProxy.Getinstance().UniMajorInfoZXBService.findByIdIn(majorid);
        for (int i = 0; i < templateMajorInfoList.size(); i++) {
            UniMajorInfoZXBVO oldMajorInfo = templateMajorInfoList.get(i);

            Long latestId = latestIdDic.getOrDefault(oldMajorInfo.getUniMajorCode() + "_" + oldMajorInfo.getMajorCode(), null);
            if(latestId != null){
                oldMajorInfo.setIdInLatestDB(latestId);
            }else {
                ////System.out.println("");
            }

            oldEntityList.add(BeanMapper.map(oldMajorInfo, UniMajorInfoZXB.class));
        }
        DBProxy.Getinstance().UniMajorInfoZXBService.batchUpdate(oldEntityList);

        Date time5 = new Date();

        System.out.println("update pici " + pici.name() + "保存Hist" + (time2.getTime() - time1.getTime())
                + "保存Latest" + (time3.getTime() - time2.getTime())
                + "更新HistDB的 latestId" + (time4.getTime() - time3.getTime())
                + "更新formId " + (time5.getTime() - time4.getTime())
                + "总共 " + (time5.getTime() - time1.getTime()));
    }

    private List<Long> GetTemplateAndFormMajorList(EnumPici pici, String examYear){
        //获取模板中的专业id
        List<TemplateInfoVO> templist = DBProxy.Getinstance().TemplateInfoService.findByExamYearAndPici(examYear, pici.getStateID());
        Set<Long> majorIdToUpdate = new HashSet<>();
        for (int i = 0; i < templist.size(); i++) {
            majorIdToUpdate.addAll(GameUtil.GetMajorIDsFromDetail(templist.get(i).getFormDetail()));
        }

//        DBProxy.Getinstance().FormInfoService.findByExamYear()// 按年和批次 查询;  更新
        //获取订单中的专业id
        List<FormInfoVO> formInfoVOS = DBProxy.Getinstance().FormInfoService.findByExamYearAndPici(examYear, pici.getStateID());
        for (int i = 0; i < formInfoVOS.size(); i++) {
            majorIdToUpdate.addAll(GameUtil.GetMajorIDsFromDetail(formInfoVOS.get(i).getFormDetail()));
        }

        return new ArrayList<>(majorIdToUpdate);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    private void SaveUniMajorInfoA(List<UniMajorInfo> protoList, Integer NewVersion, EnumPici pici, String examYear) throws Exception {
        Date time1 = new Date();

//        1.  查询 所有 当前 pici + year的 信息
//        1.1  保存   unicode + majorcode 字典  curDic/

        //1 获取 curlatest 数据
        List<UniMajorInfoALatestVO> latestDBs =  DBProxy.Getinstance().UniMajorInfoALatestService.findByPiciAndExamYear(pici.getStateID(), examYear);
        HashMap<String, HashMap<String, UniMajorInfoALatestVO>>  CurLatestMajorDic = new HashMap<>();
        HashMap<Long, UniMajorInfoALatestVO>  CurLatestMajorByIdDic = new HashMap<>();
        for (int i = 0; i < latestDBs.size(); i++) {
            UniMajorInfoALatestVO latestVO = latestDBs.get(i);
            String uniMajorCode = latestVO.getUniMajorCode();
            String majorCode = latestVO.getMajorCode();

            //1.1保存到字典
            HashMap<String, UniMajorInfoALatestVO> majorDic = CurLatestMajorDic.getOrDefault(uniMajorCode, null);
            if(majorDic == null){
                majorDic = new HashMap<>();
                CurLatestMajorDic.put(uniMajorCode, majorDic);
            }
            majorDic.put(majorCode, latestVO);

            //1.2 按id 保存
            Long uid = CommonUtil.GetMajorInfoId(examYear, latestVO.getUniMajorCode(), latestVO.getMajorCode());
//            CurLatestMajorByIdDic.put(latestVO.getId(), latestVO);
            CurLatestMajorByIdDic.put(uid, latestVO);
        }


        //2 获取cur历史数据
        List<Long> histId = new ArrayList<>();
        for (int i = 0; i < latestDBs.size(); i++) {
            UniMajorInfoALatestVO temp = latestDBs.get(i);
            histId.add(temp.getIdInHistDB());
        }
        List<UniMajorInfoAVO> histInfos =  DBProxy.Getinstance().UniMajorInfoAService.findByIdIn(histId);
        //2.1 保存到字典
        HashMap<Long, UniMajorInfoAVO>  CurHistMajorByIdDic = new HashMap<>();
        for (int i = 0; i < histInfos.size(); i++) {
            Long uid = CommonUtil.GetMajorInfoId(examYear, histInfos.get(i).getUniMajorCode(), histInfos.get(i).getMajorCode());
//            CurHistMajorByIdDic.put(histInfos.get(i).getId(),  histInfos.get(i));
            CurHistMajorByIdDic.put(uid,  histInfos.get(i));
        }

        Date time2 = new Date();

//        2. 所有当前要 更新的 数据
//        2.1  保存   unicode + majorcode 字典 toUpdateDic


        //3  tobe update Major
        HashMap<String, HashMap<String, UniMajorInfo>>  MajorToUpdateDic = new HashMap<>();
        for (int i = 0; i < protoList.size(); i++) {
            UniMajorInfo proto = protoList.get(i);
            String uniMajorCode = proto.getUniMajorCode();
            String majorCode = proto.getMajorCode();
            //保存到字典
            HashMap<String, UniMajorInfo> majorDic = MajorToUpdateDic.getOrDefault(uniMajorCode, null);
            if(majorDic == null){
                majorDic = new HashMap<>();
                MajorToUpdateDic.put(uniMajorCode, majorDic);
            }
            majorDic.put(majorCode, proto);
        }


//        2.2  遍历 toUpdateDic ;
//        2.2.1    在curDic 是否 存在  =》 存在： 删除curDic 对应entry， update；  不存在： 新增insert
//        2.2.2 最后 curDic中剩下的 就是需要被删除的 entry
//      ==》 warn一下就可以；  协商： 【删减某条目时，会保留专业组代码和专业代码，其余信息清空，这种情况判断为需要删除院校池、志愿表、模板中对应的专业组和专】

        List<UniMajorInfo> newMajorList = new ArrayList<>();
        List<UniMajorInfo> updateMajorList = new ArrayList<>();
        List<UniMajorInfoALatestVO> deleteMajorList = new ArrayList<>();

        List<Long> oldLatestIdToDelete = new ArrayList<>(); //旧id格式的数据, latest需要删除
        List<UniMajorInfo> newMajorListForOldIdFormat = new ArrayList<>(); //删除的数据需要新增

        //4. 遍历TobeUpdate Dic
        for (Map.Entry<String, HashMap<String, UniMajorInfo>>  entry: MajorToUpdateDic.entrySet()) {
            String uniCode = entry.getKey();
            HashMap<String, UniMajorInfo> UniCodeDic = entry.getValue();


            for(Map.Entry<String, UniMajorInfo> majorInfoEntry: UniCodeDic.entrySet()) {
                String majorCode = majorInfoEntry.getKey();
                UniMajorInfo uniMajorInfo = majorInfoEntry.getValue();


                //4.1  TobeUpdate 数据, 是否在 CurLatest 存在
                HashMap<String, UniMajorInfoALatestVO> tempLatestDic =  CurLatestMajorDic.getOrDefault(uniCode, null);
                if(tempLatestDic == null){
                    //4.2  不存在,  新增
                    newMajorList.add(uniMajorInfo);
                }else {
                    UniMajorInfoALatestVO  latestTempVO =  tempLatestDic.getOrDefault(majorCode, null);
                    if(latestTempVO == null){
                        newMajorList.add(uniMajorInfo);
                    }else {
                        Long uid = CommonUtil.GetMajorInfoId(examYear, latestTempVO.getUniMajorCode(), latestTempVO.getMajorCode());
                        if(!latestTempVO.getId().equals(uid)){
                           //如果id 和规则 不同, 就是 旧数据, 需要删除
                            oldLatestIdToDelete.add(latestTempVO.getId());
                            //新增一个数据
//                            newMajorListForOldIdFormat.add(uniMajorInfo);
                            newMajorList.add(uniMajorInfo);
                        }else {
                            //4.3  已经存在, 用于更新
                            updateMajorList.add(uniMajorInfo);
                        }

                        //4.4 删除 tempLatestDic 中对应 majorcode
                        tempLatestDic.remove(majorCode);

                    }
                }

            }


        }

        //5 最后 遍历 CurLatestMajorDic, 只剩下 需要删除的
        for (Map.Entry<String, HashMap<String, UniMajorInfoALatestVO>>  entry: CurLatestMajorDic.entrySet()) {
            String uniCode = entry.getKey();
            HashMap<String, UniMajorInfoALatestVO> UniCodeDic = entry.getValue();

            for (Map.Entry<String, UniMajorInfoALatestVO> majorInfoEntry : UniCodeDic.entrySet()) {
                String majorCode = majorInfoEntry.getKey();
                UniMajorInfoALatestVO uniMajorInfo = majorInfoEntry.getValue();


                deleteMajorList.add(uniMajorInfo);
            }

        }


        HashMap<Long, UniMajorInfoAVO> checkHistDBID = new HashMap<>();
        //6 添加 更新 删除 //按id排序
        // 新增
        List<UniMajorInfoA> newEntityList = new ArrayList<>();
        List<UniMajorInfoALatest> newEntityLatestList = new ArrayList<>();
        for (int i = 0; i < newMajorList.size(); i++) {
            UniMajorInfo proto = newMajorList.get(i);
            //保存历史库
            UniMajorInfoAVO histDB = UniMajorInfoAVO.ConvertFromDTO(proto);
            Long uid = CommonUtil.GetMajorInfoId(examYear, proto.getUniMajorCode(), proto.getMajorCode());
            histDB.setId(uid);
            histDB.setDataVersion(NewVersion);
            histDB.setIdInLatestDB(uid);
            newEntityList.add(BeanMapper.map(histDB, UniMajorInfoA.class));

            UniMajorInfoAVO existHist = checkHistDBID.getOrDefault(histDB.getId(), null);
            if(existHist != null){
                System.out.println("dup");
            }else {
                checkHistDBID.put(histDB.getId(), histDB);
            }


            //保存最新数据库
            UniMajorInfoALatestVO latestDB = UniMajorInfoALatestVO.ConvertFromDTO(proto);
            latestDB.setId(uid);//(null);
            latestDB.setDataVersion(NewVersion);
            latestDB.setIdInHistDB(uid);

            newEntityLatestList.add(BeanMapper.map(latestDB, UniMajorInfoALatest.class));
        }

        // 更新
        List<UniMajorInfoA> updateEntityList = new ArrayList<>();
        List<UniMajorInfoALatest> updateEntityLatestList = new ArrayList<>();
        for (int i = 0; i < updateMajorList.size(); i++) {
            UniMajorInfo proto = updateMajorList.get(i);

            Long uid = CommonUtil.GetMajorInfoId(examYear, proto.getUniMajorCode(), proto.getMajorCode());

            UniMajorInfoALatestVO updateLatestVO = CurLatestMajorByIdDic.getOrDefault(uid, null);
            UniMajorInfoAVO updateHistVO = CurHistMajorByIdDic.getOrDefault(uid, null);

            //根据 已经存在 DB信息, 更新
            UniMajorInfoALatestVO latestDB = UniMajorInfoALatestVO.ConvertFromDTO(proto, updateLatestVO);
            latestDB.setDataVersion(NewVersion);

            UniMajorInfoAVO histDB_Update = UniMajorInfoAVO.ConvertFromDTO(proto, updateHistVO);
            histDB_Update.setDataVersion(NewVersion);

            updateEntityList.add(BeanMapper.map(histDB_Update, UniMajorInfoA.class));
            updateEntityLatestList.add(BeanMapper.map(latestDB, UniMajorInfoALatest.class));
        }

        Date time3 = new Date();

        //删除
        List<Long> idToDelete = new ArrayList<>();
        for (int i = 0; i < deleteMajorList.size(); i++) {
            //删除 LatestVO
            idToDelete.add(deleteMajorList.get(i).getId());
//            DBProxy.Getinstance().UniMajorInfoALatestService.deleteByKeyId(deleteMajorList.get(i).getId());
            //删除  HistVO
        }
        if(idToDelete.size() > 0){
            DBProxy.Getinstance().UniMajorInfoALatestService.deleteByIdIn(idToDelete); //不删除, 否则 志愿表的旧数据需要处理;  同时用latetest 和 Histdb 数据不对齐, 不能用latetest是否存在, 检测 histdb是否存在.
            DBProxy.Getinstance().UniMajorInfoAService.deleteByIdIn(idToDelete); //不删除, 否则 志愿表的旧数据需要处理;  同时用latetest 和 Histdb 数据不对齐, 不能用latetest是否存在, 检测 histdb是否存在.
        }
        //删除 旧latest数据
        if(oldLatestIdToDelete.size() > 0){
            DBProxy.Getinstance().UniMajorInfoALatestService.deleteByIdIn(oldLatestIdToDelete);
        }

//        for (int i = 0; i < newEntityList.size(); i++) {
//            if(newEntityList.get(i).getId() == 20241431104L){
//                System.out.println("");
//            }
//        }

        Date time4 = new Date();
        //批量 保存
        DBProxy.Getinstance().UniMajorInfoAService.batchSave(newEntityList);
        DBProxy.Getinstance().UniMajorInfoALatestService.batchSave(newEntityLatestList);
        //批量 更新
        DBProxy.Getinstance().UniMajorInfoAService.batchUpdate(updateEntityList);
        DBProxy.Getinstance().UniMajorInfoALatestService.batchUpdate(updateEntityLatestList);



        Date time5 = new Date();

        //保存最新版本
        UniMajorMgrInMem.GetInstance().SaveLatestVersion(pici, examYear, NewVersion);

        System.out.println("update pici " + pici.name() + ",保存CurLatest&CurHist 到Dic:" + (time2.getTime() - time1.getTime())
                + ",整理entity:" + (time3.getTime() - time2.getTime())
                + ",delete:" + (time4.getTime() - time3.getTime())
                + ",batch insert/update num: " + newEntityLatestList.size() +"/"+ updateEntityLatestList.size() + ", cost time:" + (time5.getTime() - time4.getTime())
                + ",总共: " + (time5.getTime() - time1.getTime()));
    }

    private void SaveUniMajorInfoB(List<UniMajorInfo> protoList, Integer NewVersion, EnumPici pici, String examYear) throws Exception {
        Date time1 = new Date();

//        1.  查询 所有 当前 pici + year的 信息
//        1.1  保存   unicode + majorcode 字典  curDic/

        //1 获取 curlatest 数据
        List<UniMajorInfoBLatestVO> latestDBs =  DBProxy.Getinstance().UniMajorInfoBLatestService.findByPiciAndExamYear(pici.getStateID(), examYear);
        HashMap<String, HashMap<String, UniMajorInfoBLatestVO>>  CurLatestMajorDic = new HashMap<>();
        HashMap<Long, UniMajorInfoBLatestVO>  CurLatestMajorByIdDic = new HashMap<>();
        for (int i = 0; i < latestDBs.size(); i++) {
            UniMajorInfoBLatestVO latestVO = latestDBs.get(i);
            String uniMajorCode = latestVO.getUniMajorCode();
            String majorCode = latestVO.getMajorCode();

            //1.1保存到字典
            HashMap<String, UniMajorInfoBLatestVO> majorDic = CurLatestMajorDic.getOrDefault(uniMajorCode, null);
            if(majorDic == null){
                majorDic = new HashMap<>();
                CurLatestMajorDic.put(uniMajorCode, majorDic);
            }
            majorDic.put(majorCode, latestVO);

            //1.2 按id 保存
            Long uid = CommonUtil.GetMajorInfoId(examYear, latestVO.getUniMajorCode(), latestVO.getMajorCode());
//            CurLatestMajorByIdDic.put(latestVO.getId(), latestVO);
            CurLatestMajorByIdDic.put(uid, latestVO);
        }


        //2 获取cur历史数据
        List<Long> histId = new ArrayList<>();
        for (int i = 0; i < latestDBs.size(); i++) {
            UniMajorInfoBLatestVO temp = latestDBs.get(i);
            histId.add(temp.getIdInHistDB());
        }
        List<UniMajorInfoBVO> histInfos =  DBProxy.Getinstance().UniMajorInfoBService.findByIdIn(histId);
        //2.1 保存到字典
        HashMap<Long, UniMajorInfoBVO>  CurHistMajorByIdDic = new HashMap<>();
        for (int i = 0; i < histInfos.size(); i++) {
            Long uid = CommonUtil.GetMajorInfoId(examYear, histInfos.get(i).getUniMajorCode(), histInfos.get(i).getMajorCode());
//            CurHistMajorByIdDic.put(histInfos.get(i).getId(),  histInfos.get(i));
            CurHistMajorByIdDic.put(uid,  histInfos.get(i));
        }

        Date time2 = new Date();

//        2. 所有当前要 更新的 数据
//        2.1  保存   unicode + majorcode 字典 toUpdateDic


        //3  tobe update Major
        HashMap<String, HashMap<String, UniMajorInfo>>  MajorToUpdateDic = new HashMap<>();
        for (int i = 0; i < protoList.size(); i++) {
            UniMajorInfo proto = protoList.get(i);
            String uniMajorCode = proto.getUniMajorCode();
            String majorCode = proto.getMajorCode();
            //保存到字典
            HashMap<String, UniMajorInfo> majorDic = MajorToUpdateDic.getOrDefault(uniMajorCode, null);
            if(majorDic == null){
                majorDic = new HashMap<>();
                MajorToUpdateDic.put(uniMajorCode, majorDic);
            }
            majorDic.put(majorCode, proto);
        }


//        2.2  遍历 toUpdateDic ;
//        2.2.1    在curDic 是否 存在  =》 存在： 删除curDic 对应entry， update；  不存在： 新增insert
//        2.2.2 最后 curDic中剩下的 就是需要被删除的 entry
//      ==》 warn一下就可以；  协商： 【删减某条目时，会保留专业组代码和专业代码，其余信息清空，这种情况判断为需要删除院校池、志愿表、模板中对应的专业组和专】

        List<UniMajorInfo> newMajorList = new ArrayList<>();
        List<UniMajorInfo> updateMajorList = new ArrayList<>();
        List<UniMajorInfoBLatestVO> deleteMajorList = new ArrayList<>();

        List<Long> oldLatestIdToDelete = new ArrayList<>(); //旧id格式的数据, latest需要删除
        List<UniMajorInfo> newMajorListForOldIdFormat = new ArrayList<>(); //删除的数据需要新增

        //4. 遍历TobeUpdate Dic
        for (Map.Entry<String, HashMap<String, UniMajorInfo>>  entry: MajorToUpdateDic.entrySet()) {
            String uniCode = entry.getKey();
            HashMap<String, UniMajorInfo> UniCodeDic = entry.getValue();


            for(Map.Entry<String, UniMajorInfo> majorInfoEntry: UniCodeDic.entrySet()) {
                String majorCode = majorInfoEntry.getKey();
                UniMajorInfo uniMajorInfo = majorInfoEntry.getValue();


                //4.1  TobeUpdate 数据, 是否在 CurLatest 存在
                HashMap<String, UniMajorInfoBLatestVO> tempLatestDic =  CurLatestMajorDic.getOrDefault(uniCode, null);
                if(tempLatestDic == null){
                    //4.2  不存在,  新增
                    newMajorList.add(uniMajorInfo);
                }else {
                    UniMajorInfoBLatestVO  latestTempVO =  tempLatestDic.getOrDefault(majorCode, null);
                    if(latestTempVO == null){
                        newMajorList.add(uniMajorInfo);
                    }else {
                        Long uid = CommonUtil.GetMajorInfoId(examYear, latestTempVO.getUniMajorCode(), latestTempVO.getMajorCode());
                        if(!latestTempVO.getId().equals(uid)){
                            //如果id 和规则 不同, 就是 旧数据, 需要删除
                            oldLatestIdToDelete.add(latestTempVO.getId());
                            //新增一个数据
//                            newMajorListForOldIdFormat.add(uniMajorInfo);
                            newMajorList.add(uniMajorInfo);
                        }else {
                            //4.3  已经存在, 用于更新
                            updateMajorList.add(uniMajorInfo);
                        }

                        //4.4 删除 tempLatestDic 中对应 majorcode
                        tempLatestDic.remove(majorCode);

                    }
                }

            }


        }

        //5 最后 遍历 CurLatestMajorDic, 只剩下 需要删除的
        for (Map.Entry<String, HashMap<String, UniMajorInfoBLatestVO>>  entry: CurLatestMajorDic.entrySet()) {
            String uniCode = entry.getKey();
            HashMap<String, UniMajorInfoBLatestVO> UniCodeDic = entry.getValue();

            for (Map.Entry<String, UniMajorInfoBLatestVO> majorInfoEntry : UniCodeDic.entrySet()) {
                String majorCode = majorInfoEntry.getKey();
                UniMajorInfoBLatestVO uniMajorInfo = majorInfoEntry.getValue();


                deleteMajorList.add(uniMajorInfo);
            }

        }


        HashMap<Long, UniMajorInfoBVO> checkHistDBID = new HashMap<>();
        //6 添加 更新 删除 //按id排序
        // 新增
        List<UniMajorInfoB> newEntityList = new ArrayList<>();
        List<UniMajorInfoBLatest> newEntityLatestList = new ArrayList<>();
        for (int i = 0; i < newMajorList.size(); i++) {
            UniMajorInfo proto = newMajorList.get(i);
            //保存历史库
            UniMajorInfoBVO histDB = UniMajorInfoBVO.ConvertFromDTO(proto);
            Long uid = CommonUtil.GetMajorInfoId(examYear, proto.getUniMajorCode(), proto.getMajorCode());
            histDB.setId(uid);
            histDB.setDataVersion(NewVersion);
            histDB.setIdInLatestDB(uid);
            newEntityList.add(BeanMapper.map(histDB, UniMajorInfoB.class));

            UniMajorInfoBVO existHist = checkHistDBID.getOrDefault(histDB.getId(), null);
            if(existHist != null){
                System.out.println("dup");
            }else {
                checkHistDBID.put(histDB.getId(), histDB);
            }


            //保存最新数据库
            UniMajorInfoBLatestVO latestDB = UniMajorInfoBLatestVO.ConvertFromDTO(proto);
            latestDB.setId(uid);//(null);
            latestDB.setDataVersion(NewVersion);
            latestDB.setIdInHistDB(uid);

            newEntityLatestList.add(BeanMapper.map(latestDB, UniMajorInfoBLatest.class));
        }

        // 更新
        List<UniMajorInfoB> updateEntityList = new ArrayList<>();
        List<UniMajorInfoBLatest> updateEntityLatestList = new ArrayList<>();
        for (int i = 0; i < updateMajorList.size(); i++) {
            UniMajorInfo proto = updateMajorList.get(i);

            Long uid = CommonUtil.GetMajorInfoId(examYear, proto.getUniMajorCode(), proto.getMajorCode());

            UniMajorInfoBLatestVO updateLatestVO = CurLatestMajorByIdDic.getOrDefault(uid, null);
            UniMajorInfoBVO updateHistVO = CurHistMajorByIdDic.getOrDefault(uid, null);

            //根据 已经存在 DB信息, 更新
            UniMajorInfoBLatestVO latestDB = UniMajorInfoBLatestVO.ConvertFromDTO(proto, updateLatestVO);
            latestDB.setDataVersion(NewVersion);

            UniMajorInfoBVO histDB_Update = UniMajorInfoBVO.ConvertFromDTO(proto, updateHistVO);
            histDB_Update.setDataVersion(NewVersion);

            updateEntityList.add(BeanMapper.map(histDB_Update, UniMajorInfoB.class));
            updateEntityLatestList.add(BeanMapper.map(latestDB, UniMajorInfoBLatest.class));
        }

        Date time3 = new Date();

        //删除
        List<Long> idToDelete = new ArrayList<>();
        for (int i = 0; i < deleteMajorList.size(); i++) {
            //删除 LatestVO
            idToDelete.add(deleteMajorList.get(i).getId());
//            DBProxy.Getinstance().UniMajorInfoBLatestService.deleteByKeyId(deleteMajorList.get(i).getId());
            //删除  HistVO
        }
        if(idToDelete.size() > 0){
            DBProxy.Getinstance().UniMajorInfoBLatestService.deleteByIdIn(idToDelete); //不删除, 否则 志愿表的旧数据需要处理;  同时用latetest 和 Histdb 数据不对齐, 不能用latetest是否存在, 检测 histdb是否存在.
            DBProxy.Getinstance().UniMajorInfoBService.deleteByIdIn(idToDelete); //不删除, 否则 志愿表的旧数据需要处理;  同时用latetest 和 Histdb 数据不对齐, 不能用latetest是否存在, 检测 histdb是否存在.
        }
        //删除 旧latest数据
        if(oldLatestIdToDelete.size() > 0){
            DBProxy.Getinstance().UniMajorInfoBLatestService.deleteByIdIn(oldLatestIdToDelete);
        }

//        for (int i = 0; i < newEntityList.size(); i++) {
//            if(newEntityList.get(i).getId() == 20241431104L){
//                System.out.println("");
//            }
//        }

        Date time4 = new Date();
        //批量 保存
        DBProxy.Getinstance().UniMajorInfoBService.batchSave(newEntityList);
        DBProxy.Getinstance().UniMajorInfoBLatestService.batchSave(newEntityLatestList);
        //批量 更新
        DBProxy.Getinstance().UniMajorInfoBService.batchUpdate(updateEntityList);
        DBProxy.Getinstance().UniMajorInfoBLatestService.batchUpdate(updateEntityLatestList);



        Date time5 = new Date();

        //保存最新版本
        UniMajorMgrInMem.GetInstance().SaveLatestVersion(pici, examYear, NewVersion);

        System.out.println("update pici " + pici.name() + ",保存CurLatest&CurHist 到Dic:" + (time2.getTime() - time1.getTime())
                + ",整理entity:" + (time3.getTime() - time2.getTime())
                + ",delete:" + (time4.getTime() - time3.getTime())
                + ",batch insert/update num: " + newEntityLatestList.size() +"/"+ updateEntityLatestList.size() + ", cost time:" + (time5.getTime() - time4.getTime())
                + ",总共: " + (time5.getTime() - time1.getTime()));
    }

    private void SaveUniMajorInfoPreA(List<UniMajorInfo> protoList, Integer NewVersion, EnumPici pici, String examYear) throws Exception {
        Date time1 = new Date();

//        1.  查询 所有 当前 pici + year的 信息
//        1.1  保存   unicode + majorcode 字典  curDic/

        //1 获取 curlatest 数据
        List<UniMajorInfoPreALatestVO> latestDBs =  DBProxy.Getinstance().UniMajorInfoPreALatestService.findByPiciAndExamYear(pici.getStateID(), examYear);
        HashMap<String, HashMap<String, UniMajorInfoPreALatestVO>>  CurLatestMajorDic = new HashMap<>();
        HashMap<Long, UniMajorInfoPreALatestVO>  CurLatestMajorByIdDic = new HashMap<>();
        for (int i = 0; i < latestDBs.size(); i++) {
            UniMajorInfoPreALatestVO latestVO = latestDBs.get(i);
            String uniMajorCode = latestVO.getUniMajorCode();
            String majorCode = latestVO.getMajorCode();

            //1.1保存到字典
            HashMap<String, UniMajorInfoPreALatestVO> majorDic = CurLatestMajorDic.getOrDefault(uniMajorCode, null);
            if(majorDic == null){
                majorDic = new HashMap<>();
                CurLatestMajorDic.put(uniMajorCode, majorDic);
            }
            majorDic.put(majorCode, latestVO);

            //1.2 按id 保存
            Long uid = CommonUtil.GetMajorInfoId(examYear, latestVO.getUniMajorCode(), latestVO.getMajorCode());
//            CurLatestMajorByIdDic.put(latestVO.getId(), latestVO);
            CurLatestMajorByIdDic.put(uid, latestVO);
        }


        //2 获取cur历史数据
        List<Long> histId = new ArrayList<>();
        for (int i = 0; i < latestDBs.size(); i++) {
            UniMajorInfoPreALatestVO temp = latestDBs.get(i);
            histId.add(temp.getIdInHistDB());
        }
        List<UniMajorInfoPreAVO> histInfos =  DBProxy.Getinstance().UniMajorInfoPreAService.findByIdIn(histId);
        //2.1 保存到字典
        HashMap<Long, UniMajorInfoPreAVO>  CurHistMajorByIdDic = new HashMap<>();
        for (int i = 0; i < histInfos.size(); i++) {
            Long uid = CommonUtil.GetMajorInfoId(examYear, histInfos.get(i).getUniMajorCode(), histInfos.get(i).getMajorCode());
//            CurHistMajorByIdDic.put(histInfos.get(i).getId(),  histInfos.get(i));
            CurHistMajorByIdDic.put(uid,  histInfos.get(i));
        }

        Date time2 = new Date();

//        2. 所有当前要 更新的 数据
//        2.1  保存   unicode + majorcode 字典 toUpdateDic


        //3  tobe update Major
        HashMap<String, HashMap<String, UniMajorInfo>>  MajorToUpdateDic = new HashMap<>();
        for (int i = 0; i < protoList.size(); i++) {
            UniMajorInfo proto = protoList.get(i);
            String uniMajorCode = proto.getUniMajorCode();
            String majorCode = proto.getMajorCode();
            //保存到字典
            HashMap<String, UniMajorInfo> majorDic = MajorToUpdateDic.getOrDefault(uniMajorCode, null);
            if(majorDic == null){
                majorDic = new HashMap<>();
                MajorToUpdateDic.put(uniMajorCode, majorDic);
            }
            majorDic.put(majorCode, proto);
        }


//        2.2  遍历 toUpdateDic ;
//        2.2.1    在curDic 是否 存在  =》 存在： 删除curDic 对应entry， update；  不存在： 新增insert
//        2.2.2 最后 curDic中剩下的 就是需要被删除的 entry
//      ==》 warn一下就可以；  协商： 【删减某条目时，会保留专业组代码和专业代码，其余信息清空，这种情况判断为需要删除院校池、志愿表、模板中对应的专业组和专】

        List<UniMajorInfo> newMajorList = new ArrayList<>();
        List<UniMajorInfo> updateMajorList = new ArrayList<>();
        List<UniMajorInfoPreALatestVO> deleteMajorList = new ArrayList<>();

        List<Long> oldLatestIdToDelete = new ArrayList<>(); //旧id格式的数据, latest需要删除
        List<UniMajorInfo> newMajorListForOldIdFormat = new ArrayList<>(); //删除的数据需要新增

        //4. 遍历TobeUpdate Dic
        for (Map.Entry<String, HashMap<String, UniMajorInfo>>  entry: MajorToUpdateDic.entrySet()) {
            String uniCode = entry.getKey();
            HashMap<String, UniMajorInfo> UniCodeDic = entry.getValue();


            for(Map.Entry<String, UniMajorInfo> majorInfoEntry: UniCodeDic.entrySet()) {
                String majorCode = majorInfoEntry.getKey();
                UniMajorInfo uniMajorInfo = majorInfoEntry.getValue();


                //4.1  TobeUpdate 数据, 是否在 CurLatest 存在
                HashMap<String, UniMajorInfoPreALatestVO> tempLatestDic =  CurLatestMajorDic.getOrDefault(uniCode, null);
                if(tempLatestDic == null){
                    //4.2  不存在,  新增
                    newMajorList.add(uniMajorInfo);
                }else {
                    UniMajorInfoPreALatestVO  latestTempVO =  tempLatestDic.getOrDefault(majorCode, null);
                    if(latestTempVO == null){
                        newMajorList.add(uniMajorInfo);
                    }else {
                        Long uid = CommonUtil.GetMajorInfoId(examYear, latestTempVO.getUniMajorCode(), latestTempVO.getMajorCode());
                        if(!latestTempVO.getId().equals(uid)){
                            //如果id 和规则 不同, 就是 旧数据, 需要删除
                            oldLatestIdToDelete.add(latestTempVO.getId());
                            //新增一个数据
//                            newMajorListForOldIdFormat.add(uniMajorInfo);
                            newMajorList.add(uniMajorInfo);
                        }else {
                            //4.3  已经存在, 用于更新
                            updateMajorList.add(uniMajorInfo);
                        }

                        //4.4 删除 tempLatestDic 中对应 majorcode
                        tempLatestDic.remove(majorCode);

                    }
                }

            }


        }

        //5 最后 遍历 CurLatestMajorDic, 只剩下 需要删除的
        for (Map.Entry<String, HashMap<String, UniMajorInfoPreALatestVO>>  entry: CurLatestMajorDic.entrySet()) {
            String uniCode = entry.getKey();
            HashMap<String, UniMajorInfoPreALatestVO> UniCodeDic = entry.getValue();

            for (Map.Entry<String, UniMajorInfoPreALatestVO> majorInfoEntry : UniCodeDic.entrySet()) {
                String majorCode = majorInfoEntry.getKey();
                UniMajorInfoPreALatestVO uniMajorInfo = majorInfoEntry.getValue();


                deleteMajorList.add(uniMajorInfo);
            }

        }


        HashMap<Long, UniMajorInfoPreAVO> checkHistDBID = new HashMap<>();
        //6 添加 更新 删除 //按id排序
        // 新增
        List<UniMajorInfoPreA> newEntityList = new ArrayList<>();
        List<UniMajorInfoPreALatest> newEntityLatestList = new ArrayList<>();
        for (int i = 0; i < newMajorList.size(); i++) {
            UniMajorInfo proto = newMajorList.get(i);
            //保存历史库
            UniMajorInfoPreAVO histDB = UniMajorInfoPreAVO.ConvertFromDTO(proto);
            Long uid = CommonUtil.GetMajorInfoId(examYear, proto.getUniMajorCode(), proto.getMajorCode());
            histDB.setId(uid);
            histDB.setDataVersion(NewVersion);
            histDB.setIdInLatestDB(uid);
            newEntityList.add(BeanMapper.map(histDB, UniMajorInfoPreA.class));

            UniMajorInfoPreAVO existHist = checkHistDBID.getOrDefault(histDB.getId(), null);
            if(existHist != null){
                System.out.println("dup");
            }else {
                checkHistDBID.put(histDB.getId(), histDB);
            }


            //保存最新数据库
            UniMajorInfoPreALatestVO latestDB = UniMajorInfoPreALatestVO.ConvertFromDTO(proto);
            latestDB.setId(uid);//(null);
            latestDB.setDataVersion(NewVersion);
            latestDB.setIdInHistDB(uid);

            newEntityLatestList.add(BeanMapper.map(latestDB, UniMajorInfoPreALatest.class));
        }

        // 更新
        List<UniMajorInfoPreA> updateEntityList = new ArrayList<>();
        List<UniMajorInfoPreALatest> updateEntityLatestList = new ArrayList<>();
        for (int i = 0; i < updateMajorList.size(); i++) {
            UniMajorInfo proto = updateMajorList.get(i);

            Long uid = CommonUtil.GetMajorInfoId(examYear, proto.getUniMajorCode(), proto.getMajorCode());

            UniMajorInfoPreALatestVO updateLatestVO = CurLatestMajorByIdDic.getOrDefault(uid, null);
            UniMajorInfoPreAVO updateHistVO = CurHistMajorByIdDic.getOrDefault(uid, null);

            //根据 已经存在 DB信息, 更新
            UniMajorInfoPreALatestVO latestDB = UniMajorInfoPreALatestVO.ConvertFromDTO(proto, updateLatestVO);
            latestDB.setDataVersion(NewVersion);

            UniMajorInfoPreAVO histDB_Update = UniMajorInfoPreAVO.ConvertFromDTO(proto, updateHistVO);
            histDB_Update.setDataVersion(NewVersion);

            updateEntityList.add(BeanMapper.map(histDB_Update, UniMajorInfoPreA.class));
            updateEntityLatestList.add(BeanMapper.map(latestDB, UniMajorInfoPreALatest.class));
        }

        Date time3 = new Date();

        //删除
        List<Long> idToDelete = new ArrayList<>();
        for (int i = 0; i < deleteMajorList.size(); i++) {
            //删除 LatestVO
            idToDelete.add(deleteMajorList.get(i).getId());
//            DBProxy.Getinstance().UniMajorInfoPreALatestService.deleteByKeyId(deleteMajorList.get(i).getId());
            //删除  HistVO
        }
        if(idToDelete.size() > 0){
            DBProxy.Getinstance().UniMajorInfoPreALatestService.deleteByIdIn(idToDelete); //不删除, 否则 志愿表的旧数据需要处理;  同时用latetest 和 Histdb 数据不对齐, 不能用latetest是否存在, 检测 histdb是否存在.
            DBProxy.Getinstance().UniMajorInfoPreAService.deleteByIdIn(idToDelete); //不删除, 否则 志愿表的旧数据需要处理;  同时用latetest 和 Histdb 数据不对齐, 不能用latetest是否存在, 检测 histdb是否存在.
        }
        //删除 旧latest数据
        if(oldLatestIdToDelete.size() > 0){
            DBProxy.Getinstance().UniMajorInfoPreALatestService.deleteByIdIn(oldLatestIdToDelete);
        }

//        for (int i = 0; i < newEntityList.size(); i++) {
//            if(newEntityList.get(i).getId() == 20241431104L){
//                System.out.println("");
//            }
//        }

        Date time4 = new Date();
        //批量 保存
        DBProxy.Getinstance().UniMajorInfoPreAService.batchSave(newEntityList);
        DBProxy.Getinstance().UniMajorInfoPreALatestService.batchSave(newEntityLatestList);
        //批量 更新
        DBProxy.Getinstance().UniMajorInfoPreAService.batchUpdate(updateEntityList);
        DBProxy.Getinstance().UniMajorInfoPreALatestService.batchUpdate(updateEntityLatestList);



        Date time5 = new Date();

        //保存最新版本
        UniMajorMgrInMem.GetInstance().SaveLatestVersion(pici, examYear, NewVersion);

        System.out.println("update pici " + pici.name() + ",保存CurLatest&CurHist 到Dic:" + (time2.getTime() - time1.getTime())
                + ",整理entity:" + (time3.getTime() - time2.getTime())
                + ",delete:" + (time4.getTime() - time3.getTime())
                + ",batch insert/update num: " + newEntityLatestList.size() +"/"+ updateEntityLatestList.size() + ", cost time:" + (time5.getTime() - time4.getTime())
                + ",总共: " + (time5.getTime() - time1.getTime()));
    }

    private void SaveUniMajorInfoPreB(List<UniMajorInfo> protoList, Integer NewVersion, EnumPici pici, String examYear) throws Exception {
        Date time1 = new Date();

//        1.  查询 所有 当前 pici + year的 信息
//        1.1  保存   unicode + majorcode 字典  curDic/

        //1 获取 curlatest 数据
        List<UniMajorInfoPreBLatestVO> latestDBs =  DBProxy.Getinstance().UniMajorInfoPreBLatestService.findByPiciAndExamYear(pici.getStateID(), examYear);
        HashMap<String, HashMap<String, UniMajorInfoPreBLatestVO>>  CurLatestMajorDic = new HashMap<>();
        HashMap<Long, UniMajorInfoPreBLatestVO>  CurLatestMajorByIdDic = new HashMap<>();
        for (int i = 0; i < latestDBs.size(); i++) {
            UniMajorInfoPreBLatestVO latestVO = latestDBs.get(i);
            String uniMajorCode = latestVO.getUniMajorCode();
            String majorCode = latestVO.getMajorCode();

            //1.1保存到字典
            HashMap<String, UniMajorInfoPreBLatestVO> majorDic = CurLatestMajorDic.getOrDefault(uniMajorCode, null);
            if(majorDic == null){
                majorDic = new HashMap<>();
                CurLatestMajorDic.put(uniMajorCode, majorDic);
            }
            majorDic.put(majorCode, latestVO);

            //1.2 按id 保存
            Long uid = CommonUtil.GetMajorInfoId(examYear, latestVO.getUniMajorCode(), latestVO.getMajorCode());
//            CurLatestMajorByIdDic.put(latestVO.getId(), latestVO);
            CurLatestMajorByIdDic.put(uid, latestVO);
        }


        //2 获取cur历史数据
        List<Long> histId = new ArrayList<>();
        for (int i = 0; i < latestDBs.size(); i++) {
            UniMajorInfoPreBLatestVO temp = latestDBs.get(i);
            histId.add(temp.getIdInHistDB());
        }
        List<UniMajorInfoPreBVO> histInfos =  DBProxy.Getinstance().UniMajorInfoPreBService.findByIdIn(histId);
        //2.1 保存到字典
        HashMap<Long, UniMajorInfoPreBVO>  CurHistMajorByIdDic = new HashMap<>();
        for (int i = 0; i < histInfos.size(); i++) {
            Long uid = CommonUtil.GetMajorInfoId(examYear, histInfos.get(i).getUniMajorCode(), histInfos.get(i).getMajorCode());
//            CurHistMajorByIdDic.put(histInfos.get(i).getId(),  histInfos.get(i));
            CurHistMajorByIdDic.put(uid,  histInfos.get(i));
        }

        Date time2 = new Date();

//        2. 所有当前要 更新的 数据
//        2.1  保存   unicode + majorcode 字典 toUpdateDic


        //3  tobe update Major
        HashMap<String, HashMap<String, UniMajorInfo>>  MajorToUpdateDic = new HashMap<>();
        for (int i = 0; i < protoList.size(); i++) {
            UniMajorInfo proto = protoList.get(i);
            String uniMajorCode = proto.getUniMajorCode();
            String majorCode = proto.getMajorCode();
            //保存到字典
            HashMap<String, UniMajorInfo> majorDic = MajorToUpdateDic.getOrDefault(uniMajorCode, null);
            if(majorDic == null){
                majorDic = new HashMap<>();
                MajorToUpdateDic.put(uniMajorCode, majorDic);
            }
            majorDic.put(majorCode, proto);
        }


//        2.2  遍历 toUpdateDic ;
//        2.2.1    在curDic 是否 存在  =》 存在： 删除curDic 对应entry， update；  不存在： 新增insert
//        2.2.2 最后 curDic中剩下的 就是需要被删除的 entry
//      ==》 warn一下就可以；  协商： 【删减某条目时，会保留专业组代码和专业代码，其余信息清空，这种情况判断为需要删除院校池、志愿表、模板中对应的专业组和专】

        List<UniMajorInfo> newMajorList = new ArrayList<>();
        List<UniMajorInfo> updateMajorList = new ArrayList<>();
        List<UniMajorInfoPreBLatestVO> deleteMajorList = new ArrayList<>();

        List<Long> oldLatestIdToDelete = new ArrayList<>(); //旧id格式的数据, latest需要删除
        List<UniMajorInfo> newMajorListForOldIdFormat = new ArrayList<>(); //删除的数据需要新增

        //4. 遍历TobeUpdate Dic
        for (Map.Entry<String, HashMap<String, UniMajorInfo>>  entry: MajorToUpdateDic.entrySet()) {
            String uniCode = entry.getKey();
            HashMap<String, UniMajorInfo> UniCodeDic = entry.getValue();


            for(Map.Entry<String, UniMajorInfo> majorInfoEntry: UniCodeDic.entrySet()) {
                String majorCode = majorInfoEntry.getKey();
                UniMajorInfo uniMajorInfo = majorInfoEntry.getValue();


                //4.1  TobeUpdate 数据, 是否在 CurLatest 存在
                HashMap<String, UniMajorInfoPreBLatestVO> tempLatestDic =  CurLatestMajorDic.getOrDefault(uniCode, null);
                if(tempLatestDic == null){
                    //4.2  不存在,  新增
                    newMajorList.add(uniMajorInfo);
                }else {
                    UniMajorInfoPreBLatestVO  latestTempVO =  tempLatestDic.getOrDefault(majorCode, null);
                    if(latestTempVO == null){
                        newMajorList.add(uniMajorInfo);
                    }else {
                        Long uid = CommonUtil.GetMajorInfoId(examYear, latestTempVO.getUniMajorCode(), latestTempVO.getMajorCode());
                        if(!latestTempVO.getId().equals(uid)){
                            //如果id 和规则 不同, 就是 旧数据, 需要删除
                            oldLatestIdToDelete.add(latestTempVO.getId());
                            //新增一个数据
//                            newMajorListForOldIdFormat.add(uniMajorInfo);
                            newMajorList.add(uniMajorInfo);
                        }else {
                            //4.3  已经存在, 用于更新
                            updateMajorList.add(uniMajorInfo);
                        }

                        //4.4 删除 tempLatestDic 中对应 majorcode
                        tempLatestDic.remove(majorCode);

                    }
                }

            }


        }

        //5 最后 遍历 CurLatestMajorDic, 只剩下 需要删除的
        for (Map.Entry<String, HashMap<String, UniMajorInfoPreBLatestVO>>  entry: CurLatestMajorDic.entrySet()) {
            String uniCode = entry.getKey();
            HashMap<String, UniMajorInfoPreBLatestVO> UniCodeDic = entry.getValue();

            for (Map.Entry<String, UniMajorInfoPreBLatestVO> majorInfoEntry : UniCodeDic.entrySet()) {
                String majorCode = majorInfoEntry.getKey();
                UniMajorInfoPreBLatestVO uniMajorInfo = majorInfoEntry.getValue();


                deleteMajorList.add(uniMajorInfo);
            }

        }


        HashMap<Long, UniMajorInfoPreBVO> checkHistDBID = new HashMap<>();
        //6 添加 更新 删除 //按id排序
        // 新增
        List<UniMajorInfoPreB> newEntityList = new ArrayList<>();
        List<UniMajorInfoPreBLatest> newEntityLatestList = new ArrayList<>();
        for (int i = 0; i < newMajorList.size(); i++) {
            UniMajorInfo proto = newMajorList.get(i);
            //保存历史库
            UniMajorInfoPreBVO histDB = UniMajorInfoPreBVO.ConvertFromDTO(proto);
            Long uid = CommonUtil.GetMajorInfoId(examYear, proto.getUniMajorCode(), proto.getMajorCode());
            histDB.setId(uid);
            histDB.setDataVersion(NewVersion);
            histDB.setIdInLatestDB(uid);
            newEntityList.add(BeanMapper.map(histDB, UniMajorInfoPreB.class));

            UniMajorInfoPreBVO existHist = checkHistDBID.getOrDefault(histDB.getId(), null);
            if(existHist != null){
                System.out.println("dup");
            }else {
                checkHistDBID.put(histDB.getId(), histDB);
            }


            //保存最新数据库
            UniMajorInfoPreBLatestVO latestDB = UniMajorInfoPreBLatestVO.ConvertFromDTO(proto);
            latestDB.setId(uid);//(null);
            latestDB.setDataVersion(NewVersion);
            latestDB.setIdInHistDB(uid);

            newEntityLatestList.add(BeanMapper.map(latestDB, UniMajorInfoPreBLatest.class));
        }

        // 更新
        List<UniMajorInfoPreB> updateEntityList = new ArrayList<>();
        List<UniMajorInfoPreBLatest> updateEntityLatestList = new ArrayList<>();
        for (int i = 0; i < updateMajorList.size(); i++) {
            UniMajorInfo proto = updateMajorList.get(i);

            Long uid = CommonUtil.GetMajorInfoId(examYear, proto.getUniMajorCode(), proto.getMajorCode());

            UniMajorInfoPreBLatestVO updateLatestVO = CurLatestMajorByIdDic.getOrDefault(uid, null);
            UniMajorInfoPreBVO updateHistVO = CurHistMajorByIdDic.getOrDefault(uid, null);

            //根据 已经存在 DB信息, 更新
            UniMajorInfoPreBLatestVO latestDB = UniMajorInfoPreBLatestVO.ConvertFromDTO(proto, updateLatestVO);
            latestDB.setDataVersion(NewVersion);

            UniMajorInfoPreBVO histDB_Update = UniMajorInfoPreBVO.ConvertFromDTO(proto, updateHistVO);
            histDB_Update.setDataVersion(NewVersion);

            updateEntityList.add(BeanMapper.map(histDB_Update, UniMajorInfoPreB.class));
            updateEntityLatestList.add(BeanMapper.map(latestDB, UniMajorInfoPreBLatest.class));
        }

        Date time3 = new Date();

        //删除
        List<Long> idToDelete = new ArrayList<>();
        for (int i = 0; i < deleteMajorList.size(); i++) {
            //删除 LatestVO
            idToDelete.add(deleteMajorList.get(i).getId());
//            DBProxy.Getinstance().UniMajorInfoPreBLatestService.deleteByKeyId(deleteMajorList.get(i).getId());
            //删除  HistVO
        }
        if(idToDelete.size() > 0){
            DBProxy.Getinstance().UniMajorInfoPreBLatestService.deleteByIdIn(idToDelete); //不删除, 否则 志愿表的旧数据需要处理;  同时用latetest 和 Histdb 数据不对齐, 不能用latetest是否存在, 检测 histdb是否存在.
            DBProxy.Getinstance().UniMajorInfoPreBService.deleteByIdIn(idToDelete); //不删除, 否则 志愿表的旧数据需要处理;  同时用latetest 和 Histdb 数据不对齐, 不能用latetest是否存在, 检测 histdb是否存在.
        }
        //删除 旧latest数据
        if(oldLatestIdToDelete.size() > 0){
            DBProxy.Getinstance().UniMajorInfoPreBLatestService.deleteByIdIn(oldLatestIdToDelete);
        }

//        for (int i = 0; i < newEntityList.size(); i++) {
//            if(newEntityList.get(i).getId() == 20241431104L){
//                System.out.println("");
//            }
//        }

        Date time4 = new Date();
        //批量 保存
        DBProxy.Getinstance().UniMajorInfoPreBService.batchSave(newEntityList);
        DBProxy.Getinstance().UniMajorInfoPreBLatestService.batchSave(newEntityLatestList);
        //批量 更新
        DBProxy.Getinstance().UniMajorInfoPreBService.batchUpdate(updateEntityList);
        DBProxy.Getinstance().UniMajorInfoPreBLatestService.batchUpdate(updateEntityLatestList);



        Date time5 = new Date();

        //保存最新版本
        UniMajorMgrInMem.GetInstance().SaveLatestVersion(pici, examYear, NewVersion);

        System.out.println("update pici " + pici.name() + ",保存CurLatest&CurHist 到Dic:" + (time2.getTime() - time1.getTime())
                + ",整理entity:" + (time3.getTime() - time2.getTime())
                + ",delete:" + (time4.getTime() - time3.getTime())
                + ",batch insert/update num: " + newEntityLatestList.size() +"/"+ updateEntityLatestList.size() + ", cost time:" + (time5.getTime() - time4.getTime())
                + ",总共: " + (time5.getTime() - time1.getTime()));
    }

    private void SaveUniMajorInfoZhuanKe(List<UniMajorInfo> protoList, Integer NewVersion, EnumPici pici, String examYear) throws Exception {
        Date time1 = new Date();

//        1.  查询 所有 当前 pici + year的 信息
//        1.1  保存   unicode + majorcode 字典  curDic/

        //1 获取 curlatest 数据
        List<UniMajorInfoZhuanKeLatestVO> latestDBs =  DBProxy.Getinstance().UniMajorInfoZhuanKeLatestService.findByPiciAndExamYear(pici.getStateID(), examYear);
        HashMap<String, HashMap<String, UniMajorInfoZhuanKeLatestVO>>  CurLatestMajorDic = new HashMap<>();
        HashMap<Long, UniMajorInfoZhuanKeLatestVO>  CurLatestMajorByIdDic = new HashMap<>();
        for (int i = 0; i < latestDBs.size(); i++) {
            UniMajorInfoZhuanKeLatestVO latestVO = latestDBs.get(i);
            String uniMajorCode = latestVO.getUniMajorCode();
            String majorCode = latestVO.getMajorCode();

            //1.1保存到字典
            HashMap<String, UniMajorInfoZhuanKeLatestVO> majorDic = CurLatestMajorDic.getOrDefault(uniMajorCode, null);
            if(majorDic == null){
                majorDic = new HashMap<>();
                CurLatestMajorDic.put(uniMajorCode, majorDic);
            }
            majorDic.put(majorCode, latestVO);

            //1.2 按id 保存
            Long uid = CommonUtil.GetMajorInfoId(examYear, latestVO.getUniMajorCode(), latestVO.getMajorCode());
//            CurLatestMajorByIdDic.put(latestVO.getId(), latestVO);
            CurLatestMajorByIdDic.put(uid, latestVO);
        }


        //2 获取cur历史数据
        List<Long> histId = new ArrayList<>();
        for (int i = 0; i < latestDBs.size(); i++) {
            UniMajorInfoZhuanKeLatestVO temp = latestDBs.get(i);
            histId.add(temp.getIdInHistDB());
        }
        List<UniMajorInfoZhuanKeVO> histInfos =  DBProxy.Getinstance().UniMajorInfoZhuanKeService.findByIdIn(histId);
        //2.1 保存到字典
        HashMap<Long, UniMajorInfoZhuanKeVO>  CurHistMajorByIdDic = new HashMap<>();
        for (int i = 0; i < histInfos.size(); i++) {
            Long uid = CommonUtil.GetMajorInfoId(examYear, histInfos.get(i).getUniMajorCode(), histInfos.get(i).getMajorCode());
//            CurHistMajorByIdDic.put(histInfos.get(i).getId(),  histInfos.get(i));
            CurHistMajorByIdDic.put(uid,  histInfos.get(i));
        }

        Date time2 = new Date();

//        2. 所有当前要 更新的 数据
//        2.1  保存   unicode + majorcode 字典 toUpdateDic


        //3  tobe update Major
        HashMap<String, HashMap<String, UniMajorInfo>>  MajorToUpdateDic = new HashMap<>();
        for (int i = 0; i < protoList.size(); i++) {
            UniMajorInfo proto = protoList.get(i);
            String uniMajorCode = proto.getUniMajorCode();
            String majorCode = proto.getMajorCode();
            //保存到字典
            HashMap<String, UniMajorInfo> majorDic = MajorToUpdateDic.getOrDefault(uniMajorCode, null);
            if(majorDic == null){
                majorDic = new HashMap<>();
                MajorToUpdateDic.put(uniMajorCode, majorDic);
            }
            majorDic.put(majorCode, proto);
        }


//        2.2  遍历 toUpdateDic ;
//        2.2.1    在curDic 是否 存在  =》 存在： 删除curDic 对应entry， update；  不存在： 新增insert
//        2.2.2 最后 curDic中剩下的 就是需要被删除的 entry
//      ==》 warn一下就可以；  协商： 【删减某条目时，会保留专业组代码和专业代码，其余信息清空，这种情况判断为需要删除院校池、志愿表、模板中对应的专业组和专】

        List<UniMajorInfo> newMajorList = new ArrayList<>();
        List<UniMajorInfo> updateMajorList = new ArrayList<>();
        List<UniMajorInfoZhuanKeLatestVO> deleteMajorList = new ArrayList<>();

        List<Long> oldLatestIdToDelete = new ArrayList<>(); //旧id格式的数据, latest需要删除
        List<UniMajorInfo> newMajorListForOldIdFormat = new ArrayList<>(); //删除的数据需要新增

        //4. 遍历TobeUpdate Dic
        for (Map.Entry<String, HashMap<String, UniMajorInfo>>  entry: MajorToUpdateDic.entrySet()) {
            String uniCode = entry.getKey();
            HashMap<String, UniMajorInfo> UniCodeDic = entry.getValue();


            for(Map.Entry<String, UniMajorInfo> majorInfoEntry: UniCodeDic.entrySet()) {
                String majorCode = majorInfoEntry.getKey();
                UniMajorInfo uniMajorInfo = majorInfoEntry.getValue();


                //4.1  TobeUpdate 数据, 是否在 CurLatest 存在
                HashMap<String, UniMajorInfoZhuanKeLatestVO> tempLatestDic =  CurLatestMajorDic.getOrDefault(uniCode, null);
                if(tempLatestDic == null){
                    //4.2  不存在,  新增
                    newMajorList.add(uniMajorInfo);
                }else {
                    UniMajorInfoZhuanKeLatestVO  latestTempVO =  tempLatestDic.getOrDefault(majorCode, null);
                    if(latestTempVO == null){
                        newMajorList.add(uniMajorInfo);
                    }else {
                        Long uid = CommonUtil.GetMajorInfoId(examYear, latestTempVO.getUniMajorCode(), latestTempVO.getMajorCode());
                        if(!latestTempVO.getId().equals(uid)){
                            //如果id 和规则 不同, 就是 旧数据, 需要删除
                            oldLatestIdToDelete.add(latestTempVO.getId());
                            //新增一个数据
//                            newMajorListForOldIdFormat.add(uniMajorInfo);
                            newMajorList.add(uniMajorInfo);
                        }else {
                            //4.3  已经存在, 用于更新
                            updateMajorList.add(uniMajorInfo);
                        }

                        //4.4 删除 tempLatestDic 中对应 majorcode
                        tempLatestDic.remove(majorCode);

                    }
                }

            }


        }

        //5 最后 遍历 CurLatestMajorDic, 只剩下 需要删除的
        for (Map.Entry<String, HashMap<String, UniMajorInfoZhuanKeLatestVO>>  entry: CurLatestMajorDic.entrySet()) {
            String uniCode = entry.getKey();
            HashMap<String, UniMajorInfoZhuanKeLatestVO> UniCodeDic = entry.getValue();

            for (Map.Entry<String, UniMajorInfoZhuanKeLatestVO> majorInfoEntry : UniCodeDic.entrySet()) {
                String majorCode = majorInfoEntry.getKey();
                UniMajorInfoZhuanKeLatestVO uniMajorInfo = majorInfoEntry.getValue();


                deleteMajorList.add(uniMajorInfo);
            }

        }


        HashMap<Long, UniMajorInfoZhuanKeVO> checkHistDBID = new HashMap<>();
        //6 添加 更新 删除 //按id排序
        // 新增
        List<UniMajorInfoZhuanKe> newEntityList = new ArrayList<>();
        List<UniMajorInfoZhuanKeLatest> newEntityLatestList = new ArrayList<>();
        for (int i = 0; i < newMajorList.size(); i++) {
            UniMajorInfo proto = newMajorList.get(i);
            //保存历史库
            UniMajorInfoZhuanKeVO histDB = UniMajorInfoZhuanKeVO.ConvertFromDTO(proto);
            Long uid = CommonUtil.GetMajorInfoId(examYear, proto.getUniMajorCode(), proto.getMajorCode());
            histDB.setId(uid);
            histDB.setDataVersion(NewVersion);
            histDB.setIdInLatestDB(uid);
            newEntityList.add(BeanMapper.map(histDB, UniMajorInfoZhuanKe.class));

            UniMajorInfoZhuanKeVO existHist = checkHistDBID.getOrDefault(histDB.getId(), null);
            if(existHist != null){
                System.out.println("dup");
            }else {
                checkHistDBID.put(histDB.getId(), histDB);
            }


            //保存最新数据库
            UniMajorInfoZhuanKeLatestVO latestDB = UniMajorInfoZhuanKeLatestVO.ConvertFromDTO(proto);
            latestDB.setId(uid);//(null);
            latestDB.setDataVersion(NewVersion);
            latestDB.setIdInHistDB(uid);

            newEntityLatestList.add(BeanMapper.map(latestDB, UniMajorInfoZhuanKeLatest.class));
        }

        // 更新
        List<UniMajorInfoZhuanKe> updateEntityList = new ArrayList<>();
        List<UniMajorInfoZhuanKeLatest> updateEntityLatestList = new ArrayList<>();
        for (int i = 0; i < updateMajorList.size(); i++) {
            UniMajorInfo proto = updateMajorList.get(i);

            Long uid = CommonUtil.GetMajorInfoId(examYear, proto.getUniMajorCode(), proto.getMajorCode());

            UniMajorInfoZhuanKeLatestVO updateLatestVO = CurLatestMajorByIdDic.getOrDefault(uid, null);
            UniMajorInfoZhuanKeVO updateHistVO = CurHistMajorByIdDic.getOrDefault(uid, null);

            //根据 已经存在 DB信息, 更新
            UniMajorInfoZhuanKeLatestVO latestDB = UniMajorInfoZhuanKeLatestVO.ConvertFromDTO(proto, updateLatestVO);
            latestDB.setDataVersion(NewVersion);

            UniMajorInfoZhuanKeVO histDB_Update = UniMajorInfoZhuanKeVO.ConvertFromDTO(proto, updateHistVO);
            histDB_Update.setDataVersion(NewVersion);

            updateEntityList.add(BeanMapper.map(histDB_Update, UniMajorInfoZhuanKe.class));
            updateEntityLatestList.add(BeanMapper.map(latestDB, UniMajorInfoZhuanKeLatest.class));
        }

        Date time3 = new Date();

        //删除
        List<Long> idToDelete = new ArrayList<>();
        for (int i = 0; i < deleteMajorList.size(); i++) {
            //删除 LatestVO
            idToDelete.add(deleteMajorList.get(i).getId());
//            DBProxy.Getinstance().UniMajorInfoZhuanKeLatestService.deleteByKeyId(deleteMajorList.get(i).getId());
            //删除  HistVO
        }
        if(idToDelete.size() > 0){
            DBProxy.Getinstance().UniMajorInfoZhuanKeLatestService.deleteByIdIn(idToDelete); //不删除, 否则 志愿表的旧数据需要处理;  同时用latetest 和 Histdb 数据不对齐, 不能用latetest是否存在, 检测 histdb是否存在.
            DBProxy.Getinstance().UniMajorInfoZhuanKeService.deleteByIdIn(idToDelete); //不删除, 否则 志愿表的旧数据需要处理;  同时用latetest 和 Histdb 数据不对齐, 不能用latetest是否存在, 检测 histdb是否存在.
        }
        //删除 旧latest数据
        if(oldLatestIdToDelete.size() > 0){
            DBProxy.Getinstance().UniMajorInfoZhuanKeLatestService.deleteByIdIn(oldLatestIdToDelete);
        }

//        for (int i = 0; i < newEntityList.size(); i++) {
//            if(newEntityList.get(i).getId() == 20241431104L){
//                System.out.println("");
//            }
//        }

        Date time4 = new Date();
        //批量 保存
        DBProxy.Getinstance().UniMajorInfoZhuanKeService.batchSave(newEntityList);
        DBProxy.Getinstance().UniMajorInfoZhuanKeLatestService.batchSave(newEntityLatestList);
        //批量 更新
        DBProxy.Getinstance().UniMajorInfoZhuanKeService.batchUpdate(updateEntityList);
        DBProxy.Getinstance().UniMajorInfoZhuanKeLatestService.batchUpdate(updateEntityLatestList);



        Date time5 = new Date();

        //保存最新版本
        UniMajorMgrInMem.GetInstance().SaveLatestVersion(pici, examYear, NewVersion);

        System.out.println("update pici " + pici.name() + ",保存CurLatest&CurHist 到Dic:" + (time2.getTime() - time1.getTime())
                + ",整理entity:" + (time3.getTime() - time2.getTime())
                + ",delete:" + (time4.getTime() - time3.getTime())
                + ",batch insert/update num: " + newEntityLatestList.size() +"/"+ updateEntityLatestList.size() + ", cost time:" + (time5.getTime() - time4.getTime())
                + ",总共: " + (time5.getTime() - time1.getTime()));
    }

    private void SaveUniMajorInfoZXA(List<UniMajorInfo> protoList, Integer NewVersion, EnumPici pici, String examYear) throws Exception {
        Date time1 = new Date();

//        1.  查询 所有 当前 pici + year的 信息
//        1.1  保存   unicode + majorcode 字典  curDic/

        //1 获取 curlatest 数据
        List<UniMajorInfoZXALatestVO> latestDBs =  DBProxy.Getinstance().UniMajorInfoZXALatestService.findByPiciAndExamYear(pici.getStateID(), examYear);
        HashMap<String, HashMap<String, UniMajorInfoZXALatestVO>>  CurLatestMajorDic = new HashMap<>();
        HashMap<Long, UniMajorInfoZXALatestVO>  CurLatestMajorByIdDic = new HashMap<>();
        for (int i = 0; i < latestDBs.size(); i++) {
            UniMajorInfoZXALatestVO latestVO = latestDBs.get(i);
            String uniMajorCode = latestVO.getUniMajorCode();
            String majorCode = latestVO.getMajorCode();

            //1.1保存到字典
            HashMap<String, UniMajorInfoZXALatestVO> majorDic = CurLatestMajorDic.getOrDefault(uniMajorCode, null);
            if(majorDic == null){
                majorDic = new HashMap<>();
                CurLatestMajorDic.put(uniMajorCode, majorDic);
            }
            majorDic.put(majorCode, latestVO);

            //1.2 按id 保存
            Long uid = CommonUtil.GetMajorInfoId(examYear, latestVO.getUniMajorCode(), latestVO.getMajorCode());
//            CurLatestMajorByIdDic.put(latestVO.getId(), latestVO);
            CurLatestMajorByIdDic.put(uid, latestVO);
        }


        //2 获取cur历史数据
        List<Long> histId = new ArrayList<>();
        for (int i = 0; i < latestDBs.size(); i++) {
            UniMajorInfoZXALatestVO temp = latestDBs.get(i);
            histId.add(temp.getIdInHistDB());
        }
        List<UniMajorInfoZXAVO> histInfos =  DBProxy.Getinstance().UniMajorInfoZXAService.findByIdIn(histId);
        //2.1 保存到字典
        HashMap<Long, UniMajorInfoZXAVO>  CurHistMajorByIdDic = new HashMap<>();
        for (int i = 0; i < histInfos.size(); i++) {
            Long uid = CommonUtil.GetMajorInfoId(examYear, histInfos.get(i).getUniMajorCode(), histInfos.get(i).getMajorCode());
//            CurHistMajorByIdDic.put(histInfos.get(i).getId(),  histInfos.get(i));
            CurHistMajorByIdDic.put(uid,  histInfos.get(i));
        }

        Date time2 = new Date();

//        2. 所有当前要 更新的 数据
//        2.1  保存   unicode + majorcode 字典 toUpdateDic


        //3  tobe update Major
        HashMap<String, HashMap<String, UniMajorInfo>>  MajorToUpdateDic = new HashMap<>();
        for (int i = 0; i < protoList.size(); i++) {
            UniMajorInfo proto = protoList.get(i);
            String uniMajorCode = proto.getUniMajorCode();
            String majorCode = proto.getMajorCode();
            //保存到字典
            HashMap<String, UniMajorInfo> majorDic = MajorToUpdateDic.getOrDefault(uniMajorCode, null);
            if(majorDic == null){
                majorDic = new HashMap<>();
                MajorToUpdateDic.put(uniMajorCode, majorDic);
            }
            majorDic.put(majorCode, proto);
        }


//        2.2  遍历 toUpdateDic ;
//        2.2.1    在curDic 是否 存在  =》 存在： 删除curDic 对应entry， update；  不存在： 新增insert
//        2.2.2 最后 curDic中剩下的 就是需要被删除的 entry
//      ==》 warn一下就可以；  协商： 【删减某条目时，会保留专业组代码和专业代码，其余信息清空，这种情况判断为需要删除院校池、志愿表、模板中对应的专业组和专】

        List<UniMajorInfo> newMajorList = new ArrayList<>();
        List<UniMajorInfo> updateMajorList = new ArrayList<>();
        List<UniMajorInfoZXALatestVO> deleteMajorList = new ArrayList<>();

        List<Long> oldLatestIdToDelete = new ArrayList<>(); //旧id格式的数据, latest需要删除
        List<UniMajorInfo> newMajorListForOldIdFormat = new ArrayList<>(); //删除的数据需要新增

        //4. 遍历TobeUpdate Dic
        for (Map.Entry<String, HashMap<String, UniMajorInfo>>  entry: MajorToUpdateDic.entrySet()) {
            String uniCode = entry.getKey();
            HashMap<String, UniMajorInfo> UniCodeDic = entry.getValue();


            for(Map.Entry<String, UniMajorInfo> majorInfoEntry: UniCodeDic.entrySet()) {
                String majorCode = majorInfoEntry.getKey();
                UniMajorInfo uniMajorInfo = majorInfoEntry.getValue();


                //4.1  TobeUpdate 数据, 是否在 CurLatest 存在
                HashMap<String, UniMajorInfoZXALatestVO> tempLatestDic =  CurLatestMajorDic.getOrDefault(uniCode, null);
                if(tempLatestDic == null){
                    //4.2  不存在,  新增
                    newMajorList.add(uniMajorInfo);
                }else {
                    UniMajorInfoZXALatestVO  latestTempVO =  tempLatestDic.getOrDefault(majorCode, null);
                    if(latestTempVO == null){
                        newMajorList.add(uniMajorInfo);
                    }else {
                        Long uid = CommonUtil.GetMajorInfoId(examYear, latestTempVO.getUniMajorCode(), latestTempVO.getMajorCode());
                        if(!latestTempVO.getId().equals(uid)){
                            //如果id 和规则 不同, 就是 旧数据, 需要删除
                            oldLatestIdToDelete.add(latestTempVO.getId());
                            //新增一个数据
//                            newMajorListForOldIdFormat.add(uniMajorInfo);
                            newMajorList.add(uniMajorInfo);
                        }else {
                            //4.3  已经存在, 用于更新
                            updateMajorList.add(uniMajorInfo);
                        }

                        //4.4 删除 tempLatestDic 中对应 majorcode
                        tempLatestDic.remove(majorCode);

                    }
                }

            }


        }

        //5 最后 遍历 CurLatestMajorDic, 只剩下 需要删除的
        for (Map.Entry<String, HashMap<String, UniMajorInfoZXALatestVO>>  entry: CurLatestMajorDic.entrySet()) {
            String uniCode = entry.getKey();
            HashMap<String, UniMajorInfoZXALatestVO> UniCodeDic = entry.getValue();

            for (Map.Entry<String, UniMajorInfoZXALatestVO> majorInfoEntry : UniCodeDic.entrySet()) {
                String majorCode = majorInfoEntry.getKey();
                UniMajorInfoZXALatestVO uniMajorInfo = majorInfoEntry.getValue();


                deleteMajorList.add(uniMajorInfo);
            }

        }


        HashMap<Long, UniMajorInfoZXAVO> checkHistDBID = new HashMap<>();
        //6 添加 更新 删除 //按id排序
        // 新增
        List<UniMajorInfoZXA> newEntityList = new ArrayList<>();
        List<UniMajorInfoZXALatest> newEntityLatestList = new ArrayList<>();
        for (int i = 0; i < newMajorList.size(); i++) {
            UniMajorInfo proto = newMajorList.get(i);
            //保存历史库
            UniMajorInfoZXAVO histDB = UniMajorInfoZXAVO.ConvertFromDTO(proto);
            Long uid = CommonUtil.GetMajorInfoId(examYear, proto.getUniMajorCode(), proto.getMajorCode());
            histDB.setId(uid);
            histDB.setDataVersion(NewVersion);
            histDB.setIdInLatestDB(uid);
            newEntityList.add(BeanMapper.map(histDB, UniMajorInfoZXA.class));

            UniMajorInfoZXAVO existHist = checkHistDBID.getOrDefault(histDB.getId(), null);
            if(existHist != null){
                System.out.println("dup");
            }else {
                checkHistDBID.put(histDB.getId(), histDB);
            }


            //保存最新数据库
            UniMajorInfoZXALatestVO latestDB = UniMajorInfoZXALatestVO.ConvertFromDTO(proto);
            latestDB.setId(uid);//(null);
            latestDB.setDataVersion(NewVersion);
            latestDB.setIdInHistDB(uid);

            newEntityLatestList.add(BeanMapper.map(latestDB, UniMajorInfoZXALatest.class));
        }

        // 更新
        List<UniMajorInfoZXA> updateEntityList = new ArrayList<>();
        List<UniMajorInfoZXALatest> updateEntityLatestList = new ArrayList<>();
        for (int i = 0; i < updateMajorList.size(); i++) {
            UniMajorInfo proto = updateMajorList.get(i);

            Long uid = CommonUtil.GetMajorInfoId(examYear, proto.getUniMajorCode(), proto.getMajorCode());

            UniMajorInfoZXALatestVO updateLatestVO = CurLatestMajorByIdDic.getOrDefault(uid, null);
            UniMajorInfoZXAVO updateHistVO = CurHistMajorByIdDic.getOrDefault(uid, null);

            //根据 已经存在 DB信息, 更新
            UniMajorInfoZXALatestVO latestDB = UniMajorInfoZXALatestVO.ConvertFromDTO(proto, updateLatestVO);
            latestDB.setDataVersion(NewVersion);

            UniMajorInfoZXAVO histDB_Update = UniMajorInfoZXAVO.ConvertFromDTO(proto, updateHistVO);
            histDB_Update.setDataVersion(NewVersion);

            updateEntityList.add(BeanMapper.map(histDB_Update, UniMajorInfoZXA.class));
            updateEntityLatestList.add(BeanMapper.map(latestDB, UniMajorInfoZXALatest.class));
        }

        Date time3 = new Date();

        //删除
        List<Long> idToDelete = new ArrayList<>();
        for (int i = 0; i < deleteMajorList.size(); i++) {
            //删除 LatestVO
            idToDelete.add(deleteMajorList.get(i).getId());
//            DBProxy.Getinstance().UniMajorInfoZXALatestService.deleteByKeyId(deleteMajorList.get(i).getId());
            //删除  HistVO
        }
        if(idToDelete.size() > 0){
            DBProxy.Getinstance().UniMajorInfoZXALatestService.deleteByIdIn(idToDelete); //不删除, 否则 志愿表的旧数据需要处理;  同时用latetest 和 Histdb 数据不对齐, 不能用latetest是否存在, 检测 histdb是否存在.
            DBProxy.Getinstance().UniMajorInfoZXAService.deleteByIdIn(idToDelete); //不删除, 否则 志愿表的旧数据需要处理;  同时用latetest 和 Histdb 数据不对齐, 不能用latetest是否存在, 检测 histdb是否存在.
        }
        //删除 旧latest数据
        if(oldLatestIdToDelete.size() > 0){
            DBProxy.Getinstance().UniMajorInfoZXALatestService.deleteByIdIn(oldLatestIdToDelete);
        }

//        for (int i = 0; i < newEntityList.size(); i++) {
//            if(newEntityList.get(i).getId() == 20241431104L){
//                System.out.println("");
//            }
//        }

        Date time4 = new Date();
        //批量 保存
        DBProxy.Getinstance().UniMajorInfoZXAService.batchSave(newEntityList);
        DBProxy.Getinstance().UniMajorInfoZXALatestService.batchSave(newEntityLatestList);
        //批量 更新
        DBProxy.Getinstance().UniMajorInfoZXAService.batchUpdate(updateEntityList);
        DBProxy.Getinstance().UniMajorInfoZXALatestService.batchUpdate(updateEntityLatestList);



        Date time5 = new Date();

        //保存最新版本
        UniMajorMgrInMem.GetInstance().SaveLatestVersion(pici, examYear, NewVersion);

        System.out.println("update pici " + pici.name() + ",保存CurLatest&CurHist 到Dic:" + (time2.getTime() - time1.getTime())
                + ",整理entity:" + (time3.getTime() - time2.getTime())
                + ",delete:" + (time4.getTime() - time3.getTime())
                + ",batch insert/update num: " + newEntityLatestList.size() +"/"+ updateEntityLatestList.size() + ", cost time:" + (time5.getTime() - time4.getTime())
                + ",总共: " + (time5.getTime() - time1.getTime()));
    }

    private void SaveUniMajorInfoZXB(List<UniMajorInfo> protoList, Integer NewVersion, EnumPici pici, String examYear) throws Exception {
        Date time1 = new Date();

//        1.  查询 所有 当前 pici + year的 信息
//        1.1  保存   unicode + majorcode 字典  curDic/

        //1 获取 curlatest 数据
        List<UniMajorInfoZXBLatestVO> latestDBs =  DBProxy.Getinstance().UniMajorInfoZXBLatestService.findByPiciAndExamYear(pici.getStateID(), examYear);
        HashMap<String, HashMap<String, UniMajorInfoZXBLatestVO>>  CurLatestMajorDic = new HashMap<>();
        HashMap<Long, UniMajorInfoZXBLatestVO>  CurLatestMajorByIdDic = new HashMap<>();
        for (int i = 0; i < latestDBs.size(); i++) {
            UniMajorInfoZXBLatestVO latestVO = latestDBs.get(i);
            String uniMajorCode = latestVO.getUniMajorCode();
            String majorCode = latestVO.getMajorCode();

            //1.1保存到字典
            HashMap<String, UniMajorInfoZXBLatestVO> majorDic = CurLatestMajorDic.getOrDefault(uniMajorCode, null);
            if(majorDic == null){
                majorDic = new HashMap<>();
                CurLatestMajorDic.put(uniMajorCode, majorDic);
            }
            majorDic.put(majorCode, latestVO);

            //1.2 按id 保存
            Long uid = CommonUtil.GetMajorInfoId(examYear, latestVO.getUniMajorCode(), latestVO.getMajorCode());
//            CurLatestMajorByIdDic.put(latestVO.getId(), latestVO);
            CurLatestMajorByIdDic.put(uid, latestVO);
        }


        //2 获取cur历史数据
        List<Long> histId = new ArrayList<>();
        for (int i = 0; i < latestDBs.size(); i++) {
            UniMajorInfoZXBLatestVO temp = latestDBs.get(i);
            histId.add(temp.getIdInHistDB());
        }
        List<UniMajorInfoZXBVO> histInfos =  DBProxy.Getinstance().UniMajorInfoZXBService.findByIdIn(histId);
        //2.1 保存到字典
        HashMap<Long, UniMajorInfoZXBVO>  CurHistMajorByIdDic = new HashMap<>();
        for (int i = 0; i < histInfos.size(); i++) {
            Long uid = CommonUtil.GetMajorInfoId(examYear, histInfos.get(i).getUniMajorCode(), histInfos.get(i).getMajorCode());
//            CurHistMajorByIdDic.put(histInfos.get(i).getId(),  histInfos.get(i));
            CurHistMajorByIdDic.put(uid,  histInfos.get(i));
        }

        Date time2 = new Date();

//        2. 所有当前要 更新的 数据
//        2.1  保存   unicode + majorcode 字典 toUpdateDic


        //3  tobe update Major
        HashMap<String, HashMap<String, UniMajorInfo>>  MajorToUpdateDic = new HashMap<>();
        for (int i = 0; i < protoList.size(); i++) {
            UniMajorInfo proto = protoList.get(i);
            String uniMajorCode = proto.getUniMajorCode();
            String majorCode = proto.getMajorCode();
            //保存到字典
            HashMap<String, UniMajorInfo> majorDic = MajorToUpdateDic.getOrDefault(uniMajorCode, null);
            if(majorDic == null){
                majorDic = new HashMap<>();
                MajorToUpdateDic.put(uniMajorCode, majorDic);
            }
            majorDic.put(majorCode, proto);
        }


//        2.2  遍历 toUpdateDic ;
//        2.2.1    在curDic 是否 存在  =》 存在： 删除curDic 对应entry， update；  不存在： 新增insert
//        2.2.2 最后 curDic中剩下的 就是需要被删除的 entry
//      ==》 warn一下就可以；  协商： 【删减某条目时，会保留专业组代码和专业代码，其余信息清空，这种情况判断为需要删除院校池、志愿表、模板中对应的专业组和专】

        List<UniMajorInfo> newMajorList = new ArrayList<>();
        List<UniMajorInfo> updateMajorList = new ArrayList<>();
        List<UniMajorInfoZXBLatestVO> deleteMajorList = new ArrayList<>();

        List<Long> oldLatestIdToDelete = new ArrayList<>(); //旧id格式的数据, latest需要删除
        List<UniMajorInfo> newMajorListForOldIdFormat = new ArrayList<>(); //删除的数据需要新增

        //4. 遍历TobeUpdate Dic
        for (Map.Entry<String, HashMap<String, UniMajorInfo>>  entry: MajorToUpdateDic.entrySet()) {
            String uniCode = entry.getKey();
            HashMap<String, UniMajorInfo> UniCodeDic = entry.getValue();


            for(Map.Entry<String, UniMajorInfo> majorInfoEntry: UniCodeDic.entrySet()) {
                String majorCode = majorInfoEntry.getKey();
                UniMajorInfo uniMajorInfo = majorInfoEntry.getValue();


                //4.1  TobeUpdate 数据, 是否在 CurLatest 存在
                HashMap<String, UniMajorInfoZXBLatestVO> tempLatestDic =  CurLatestMajorDic.getOrDefault(uniCode, null);
                if(tempLatestDic == null){
                    //4.2  不存在,  新增
                    newMajorList.add(uniMajorInfo);
                }else {
                    UniMajorInfoZXBLatestVO  latestTempVO =  tempLatestDic.getOrDefault(majorCode, null);
                    if(latestTempVO == null){
                        newMajorList.add(uniMajorInfo);
                    }else {
                        Long uid = CommonUtil.GetMajorInfoId(examYear, latestTempVO.getUniMajorCode(), latestTempVO.getMajorCode());
                        if(!latestTempVO.getId().equals(uid)){
                            //如果id 和规则 不同, 就是 旧数据, 需要删除
                            oldLatestIdToDelete.add(latestTempVO.getId());
                            //新增一个数据
//                            newMajorListForOldIdFormat.add(uniMajorInfo);
                            newMajorList.add(uniMajorInfo);
                        }else {
                            //4.3  已经存在, 用于更新
                            updateMajorList.add(uniMajorInfo);
                        }

                        //4.4 删除 tempLatestDic 中对应 majorcode
                        tempLatestDic.remove(majorCode);

                    }
                }

            }


        }

        //5 最后 遍历 CurLatestMajorDic, 只剩下 需要删除的
        for (Map.Entry<String, HashMap<String, UniMajorInfoZXBLatestVO>>  entry: CurLatestMajorDic.entrySet()) {
            String uniCode = entry.getKey();
            HashMap<String, UniMajorInfoZXBLatestVO> UniCodeDic = entry.getValue();

            for (Map.Entry<String, UniMajorInfoZXBLatestVO> majorInfoEntry : UniCodeDic.entrySet()) {
                String majorCode = majorInfoEntry.getKey();
                UniMajorInfoZXBLatestVO uniMajorInfo = majorInfoEntry.getValue();


                deleteMajorList.add(uniMajorInfo);
            }

        }


        HashMap<Long, UniMajorInfoZXBVO> checkHistDBID = new HashMap<>();
        //6 添加 更新 删除 //按id排序
        // 新增
        List<UniMajorInfoZXB> newEntityList = new ArrayList<>();
        List<UniMajorInfoZXBLatest> newEntityLatestList = new ArrayList<>();
        for (int i = 0; i < newMajorList.size(); i++) {
            UniMajorInfo proto = newMajorList.get(i);
            //保存历史库
            UniMajorInfoZXBVO histDB = UniMajorInfoZXBVO.ConvertFromDTO(proto);
            Long uid = CommonUtil.GetMajorInfoId(examYear, proto.getUniMajorCode(), proto.getMajorCode());
            histDB.setId(uid);
            histDB.setDataVersion(NewVersion);
            histDB.setIdInLatestDB(uid);
            newEntityList.add(BeanMapper.map(histDB, UniMajorInfoZXB.class));

            UniMajorInfoZXBVO existHist = checkHistDBID.getOrDefault(histDB.getId(), null);
            if(existHist != null){
                System.out.println("dup");
            }else {
                checkHistDBID.put(histDB.getId(), histDB);
            }


            //保存最新数据库
            UniMajorInfoZXBLatestVO latestDB = UniMajorInfoZXBLatestVO.ConvertFromDTO(proto);
            latestDB.setId(uid);//(null);
            latestDB.setDataVersion(NewVersion);
            latestDB.setIdInHistDB(uid);

            newEntityLatestList.add(BeanMapper.map(latestDB, UniMajorInfoZXBLatest.class));
        }

        // 更新
        List<UniMajorInfoZXB> updateEntityList = new ArrayList<>();
        List<UniMajorInfoZXBLatest> updateEntityLatestList = new ArrayList<>();
        for (int i = 0; i < updateMajorList.size(); i++) {
            UniMajorInfo proto = updateMajorList.get(i);

            Long uid = CommonUtil.GetMajorInfoId(examYear, proto.getUniMajorCode(), proto.getMajorCode());

            UniMajorInfoZXBLatestVO updateLatestVO = CurLatestMajorByIdDic.getOrDefault(uid, null);
            UniMajorInfoZXBVO updateHistVO = CurHistMajorByIdDic.getOrDefault(uid, null);

            //根据 已经存在 DB信息, 更新
            UniMajorInfoZXBLatestVO latestDB = UniMajorInfoZXBLatestVO.ConvertFromDTO(proto, updateLatestVO);
            latestDB.setDataVersion(NewVersion);

            UniMajorInfoZXBVO histDB_Update = UniMajorInfoZXBVO.ConvertFromDTO(proto, updateHistVO);
            histDB_Update.setDataVersion(NewVersion);

            updateEntityList.add(BeanMapper.map(histDB_Update, UniMajorInfoZXB.class));
            updateEntityLatestList.add(BeanMapper.map(latestDB, UniMajorInfoZXBLatest.class));
        }

        Date time3 = new Date();

        //删除
        List<Long> idToDelete = new ArrayList<>();
        for (int i = 0; i < deleteMajorList.size(); i++) {
            //删除 LatestVO
            idToDelete.add(deleteMajorList.get(i).getId());
//            DBProxy.Getinstance().UniMajorInfoZXBLatestService.deleteByKeyId(deleteMajorList.get(i).getId());
            //删除  HistVO
        }
        if(idToDelete.size() > 0){
            DBProxy.Getinstance().UniMajorInfoZXBLatestService.deleteByIdIn(idToDelete); //不删除, 否则 志愿表的旧数据需要处理;  同时用latetest 和 Histdb 数据不对齐, 不能用latetest是否存在, 检测 histdb是否存在.
            DBProxy.Getinstance().UniMajorInfoZXBService.deleteByIdIn(idToDelete); //不删除, 否则 志愿表的旧数据需要处理;  同时用latetest 和 Histdb 数据不对齐, 不能用latetest是否存在, 检测 histdb是否存在.
        }
        //删除 旧latest数据
        if(oldLatestIdToDelete.size() > 0){
            DBProxy.Getinstance().UniMajorInfoZXBLatestService.deleteByIdIn(oldLatestIdToDelete);
        }

//        for (int i = 0; i < newEntityList.size(); i++) {
//            if(newEntityList.get(i).getId() == 20241431104L){
//                System.out.println("");
//            }
//        }

        Date time4 = new Date();
        //批量 保存
        DBProxy.Getinstance().UniMajorInfoZXBService.batchSave(newEntityList);
        DBProxy.Getinstance().UniMajorInfoZXBLatestService.batchSave(newEntityLatestList);
        //批量 更新
        DBProxy.Getinstance().UniMajorInfoZXBService.batchUpdate(updateEntityList);
        DBProxy.Getinstance().UniMajorInfoZXBLatestService.batchUpdate(updateEntityLatestList);



        Date time5 = new Date();

        //保存最新版本
        UniMajorMgrInMem.GetInstance().SaveLatestVersion(pici, examYear, NewVersion);

        System.out.println("update pici " + pici.name() + ",保存CurLatest&CurHist 到Dic:" + (time2.getTime() - time1.getTime())
                + ",整理entity:" + (time3.getTime() - time2.getTime())
                + ",delete:" + (time4.getTime() - time3.getTime())
                + ",batch insert/update num: " + newEntityLatestList.size() +"/"+ updateEntityLatestList.size() + ", cost time:" + (time5.getTime() - time4.getTime())
                + ",总共: " + (time5.getTime() - time1.getTime()));
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


}
