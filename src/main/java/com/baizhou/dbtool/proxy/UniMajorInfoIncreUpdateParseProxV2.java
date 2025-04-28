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
import com.baizhou.manager.UniMajorMgrInMem;
import com.baizhou.util.BeanMapper;
import com.baizhou.util.GameUtil;
import com.msg.UniMajorInfo;

import java.io.File;
import java.util.*;

public class UniMajorInfoIncreUpdateParseProxV2 extends UniMajorInfoParseProx {
    enum EnumOpeType{
        New,
        Update,
        Delete
    }

    //对比2个sheet, 获取更新的row 信息
    public void Compare(ExcelTableData table, ExcelTableData table_backup, Integer sheetIdx,  ExcelTableData iconTable, String year,Integer latestVer) throws Exception {

        Date time1 = new Date();

        //按类型区分: 增删改
        HashMap<String, List<ExcelRowData>> dicByOpcode = FilterToDicByType(table);


        Date time2 = new Date();

        this.ResetMajorIconDic(iconTable);//保存图标数据

        EnumPici pici = GameUtil.GetPiciBySheetId(sheetIdx);// EnumPici.A;
//        System.out.println("增量解析院校池 批次 " + pici.name() + " start");

        Date time3 = new Date();

        List<UniMajorInfo> newDataList = OperateByType(EnumOpeType.New, dicByOpcode, pici, year, latestVer);
        List<UniMajorInfo> updateDataList =  OperateByType(EnumOpeType.Update, dicByOpcode, pici, year, latestVer);
        List<UniMajorInfo> delDataList =  OperateByType(EnumOpeType.Delete, dicByOpcode, pici, year, latestVer);


        SavePici(newDataList, updateDataList, delDataList, latestVer, pici, year);
        //todo  对比2个Table,
//        HashMap<String, HashMap<String, ExcelRowData>> UniMajorDic_ToBeUpdate = ExtractIncre(UniMajorDic_Update, UniMajorDic_Backup);

//
//        //转成proto 数据
//        List<UniMajorInfo> protoToNewList = ConvertToProtoList(UniMajorDic_New, pici.getStateID(), MajorIcon, year);
//
//        HashMap<String, List<UniMajorInfo>> groupDic = GetGroupInfo(protoList);
//        List<UniMajorInfo> reorderedList = ReorderProto(groupDic);
//
        Date time4 = new Date();
//
//        UpdatePici(reorderedList, 1, pici, year, groupDic);

        Date time5 = new Date();
//        System.out.println("UpdatePici cost: ms " + (time5.getTime() - time4.getTime())); //40ms
        System.out.println("UpdateAll cost: ms " + (time5.getTime() - time1.getTime())); //40ms

        System.out.println("增量解析院校池 批次 " + pici.name() + " end");
    }

    private List<UniMajorInfo>  OperateByType(EnumOpeType opeType, HashMap<String, List<ExcelRowData>> dicByOpcode, EnumPici pici, String year,Integer latestVer) throws InterruptedException {
        String key = "";
        switch (opeType){
            case New:
                key = ConstDefine.NewDataKEY;
                break;
            case Update:
                key = ConstDefine.UpdateKEY;
                break;
            case Delete:
                key = ConstDefine.DelDataKEY;
                break;
        }

        HashMap<String, HashMap<String, ExcelRowData>> UniMajorDic_ToOp =  ConvertListToDic(dicByOpcode.getOrDefault(key, null));
        FixShoolType(UniMajorDic_ToOp); //todo 先遍历所有数据, 在更新  "学校类型" 栏位


        //转成proto 数据
        List<UniMajorInfo> protoList = ConvertToProtoList(UniMajorDic_ToOp, pici.getStateID(), MajorIcon, year);


        HashMap<String, List<UniMajorInfo>> groupDic = GetGroupInfo(protoList);
        List<UniMajorInfo> reorderedList = ReorderProto(groupDic);

        Date time4 = new Date();


        return reorderedList;
//        UpdatePici(reorderedList, 1, pici, year, groupDic);

    }


    private HashMap<String, List<ExcelRowData>> FilterToDicByType(ExcelTableData table) {
        HashMap<String, List<ExcelRowData>> UniMajorDic = new HashMap<>();
        UniMajorDic.clear();


        List<ExcelRowData> newDataList = new ArrayList<>();
        List<ExcelRowData> delDataList = new ArrayList<>();
        List<ExcelRowData> updateDataList = new ArrayList<>();
        for (int i = 0; i < table.DataRows.size(); i++) {
            ExcelRowData row = table.DataRows.get(i);

            String opCode = row.Cells.get(55).Data.trim(); //操作

            if (opCode.isEmpty() || opCode.contains(ConstDefine.UnchnageKEY)) continue; //空分数不记录

            if(opCode.contains(ConstDefine.NewDataKEY)){
                //新增
                newDataList.add(row);
            }else if(opCode.contains(ConstDefine.DelDataKEY)){
                //删除
                delDataList.add(row);
            }else if(opCode.contains(ConstDefine.UpdateKEY)){
                //更新
                updateDataList.add(row);
            }
        }

        UniMajorDic.put(ConstDefine.NewDataKEY, newDataList);
        UniMajorDic.put(ConstDefine.DelDataKEY, delDataList);
        UniMajorDic.put(ConstDefine.UpdateKEY, updateDataList);

        return UniMajorDic;
    }

    //
    private HashMap<String, HashMap<String, ExcelRowData>>  ConvertListToDic(List<ExcelRowData> list) {
        HashMap<String, HashMap<String, ExcelRowData>> UniMajorDic = new HashMap<>();

        UniMajorDic.clear();

        for (int i = 0; i < list.size(); i++) {
            ExcelRowData row = list.get(i);

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


    private void SavePici(List<UniMajorInfo> protoToAddList,  List<UniMajorInfo> protoToUpdateList,List<UniMajorInfo> protoToDeleteList, Integer NewVersion,
                          EnumPici pici, String year) throws Exception {


        //删除MajorDB 和 GroupDB
//        DeleteUniMajorDB(pici, year);
//        if(true) return;
        switch (pici){
            case A:
            {

                UpdateDataUniMajorInfoA( protoToAddList,   protoToUpdateList, protoToDeleteList, pici, year, NewVersion);
                break;
            }
            case B:
                UpdateDataUniMajorInfoB( protoToAddList,   protoToUpdateList, protoToDeleteList, pici, year, NewVersion);
                break;
            case PreA:
                UpdateDataUniMajorInfoPreA( protoToAddList,   protoToUpdateList, protoToDeleteList, pici, year, NewVersion);
                break;
            case PreB:
                UpdateDataUniMajorInfoPreB( protoToAddList,   protoToUpdateList, protoToDeleteList, pici, year, NewVersion);
                break;
            case ZhuanKe:
                UpdateDataUniMajorInfoZhuanKe( protoToAddList,   protoToUpdateList, protoToDeleteList, pici, year, NewVersion);
                break;
            case ZXA:
                UpdateDataUniMajorInfoZXA( protoToAddList,   protoToUpdateList, protoToDeleteList, pici, year, NewVersion);
                break;
            case ZXB:
                UpdateDataUniMajorInfoZXB( protoToAddList,   protoToUpdateList, protoToDeleteList, pici, year, NewVersion);
                break;
        }



    }

    private void UpdateDataUniMajorInfoA(List<UniMajorInfo> protoToAddList,  List<UniMajorInfo> protoToUpdateList,List<UniMajorInfo> protoToDeleteList, EnumPici pici, String examYear, Integer NewVersion) throws Exception {
        Date time1 = new Date();

        HashMap<Long, UniMajorInfoAVO> checkHistDBID = new HashMap<>();
        //6 添加 更新 删除 //按id排序
        // 新增
        List<UniMajorInfoA> newEntityList = new ArrayList<>();
        List<UniMajorInfoALatest> newEntityLatestList = new ArrayList<>();
        for (int i = 0; i < protoToAddList.size(); i++) {
            UniMajorInfo proto = protoToAddList.get(i);
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

        Date time2 = new Date();



        //更新, 获取原始保存的专业
        List<Long> latestIdToUpdate = new ArrayList<>();
        for (int i = 0; i < protoToUpdateList.size(); i++) {
            UniMajorInfo proto = protoToUpdateList.get(i);
            Long uid = CommonUtil.GetMajorInfoId(examYear, proto.getUniMajorCode(), proto.getMajorCode());
            latestIdToUpdate.add(uid);

        }


        //1 获取 curlatest 数据
        List<UniMajorInfoALatestVO> latestDBs =  DBProxy.Getinstance().UniMajorInfoALatestService.findByIdIn(latestIdToUpdate);
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

        // 更新
        List<UniMajorInfoA> updateEntityList = new ArrayList<>();
        List<UniMajorInfoALatest> updateEntityLatestList = new ArrayList<>();
        for (int i = 0; i < protoToUpdateList.size(); i++) {
            UniMajorInfo proto = protoToUpdateList.get(i);

            Long uid = CommonUtil.GetMajorInfoId(examYear, proto.getUniMajorCode(), proto.getMajorCode());

            UniMajorInfoALatestVO updateLatestVO = CurLatestMajorByIdDic.getOrDefault(uid, null);
            UniMajorInfoAVO updateHistVO = CurHistMajorByIdDic.getOrDefault(uid, null);

            if(updateHistVO != null && updateLatestVO != null){
                //根据 已经存在 DB信息, 更新
                UniMajorInfoALatestVO latestDB = UniMajorInfoALatestVO.ConvertFromDTO(proto, updateLatestVO);
                latestDB.setDataVersion(NewVersion);
                UniMajorInfoAVO histDB_Update = UniMajorInfoAVO.ConvertFromDTO(proto, updateHistVO);
                histDB_Update.setDataVersion(NewVersion);

                updateEntityList.add(BeanMapper.map(histDB_Update, UniMajorInfoA.class));
                updateEntityLatestList.add(BeanMapper.map(latestDB, UniMajorInfoALatest.class));
            }else {

                System.out.println("数据无法更新, 因为在原来数据库中找不到 id " + uid +", unimajord :" + proto.getUniMajorCode() + ", " + proto.getMajorCode() + ", 专业" +  proto.getMajorName());

            }

        }

        Date time3 = new Date();

        //删除
        List<Long> idToDelete = new ArrayList<>();
        for (int i = 0; i < protoToDeleteList.size(); i++) {
            //删除 LatestVO
            Long uid = CommonUtil.GetMajorInfoId(examYear, protoToDeleteList.get(i).getUniMajorCode(), protoToDeleteList.get(i).getMajorCode());
            idToDelete.add(uid);
//            DBProxy.Getinstance().UniMajorInfoALatestService.deleteByKeyId(deleteMajorList.get(i).getId());
            //删除  HistVO
        }
        if(idToDelete.size() > 0){
            DBProxy.Getinstance().UniMajorInfoALatestService.deleteByIdIn(idToDelete); //不删除, 否则 志愿表的旧数据需要处理;  同时用latetest 和 Histdb 数据不对齐, 不能用latetest是否存在, 检测 histdb是否存在.
            DBProxy.Getinstance().UniMajorInfoAService.deleteByIdIn(idToDelete); //不删除, 否则 志愿表的旧数据需要处理;  同时用latetest 和 Histdb 数据不对齐, 不能用latetest是否存在, 检测 histdb是否存在.
        }


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

        System.out.println("update pici " + pici.name() + ",保存New Data 到Dic:" + (time2.getTime() - time1.getTime())
                + ",整理to update data:" + (time3.getTime() - time2.getTime())
                + ",delete:" + (time4.getTime() - time3.getTime())
                + ",batch insert/update num: " + newEntityLatestList.size() +"/"+ updateEntityLatestList.size() + ", cost time:" + (time5.getTime() - time4.getTime())
                + ",总共: " + (time5.getTime() - time1.getTime()));
    }

    private void UpdateDataUniMajorInfoB(List<UniMajorInfo> protoToAddList,  List<UniMajorInfo> protoToUpdateList,List<UniMajorInfo> protoToDeleteList, EnumPici pici, String examYear, Integer NewVersion) throws Exception {
        Date time1 = new Date();

        HashMap<Long, UniMajorInfoBVO> checkHistDBID = new HashMap<>();
        //6 添加 更新 删除 //按id排序
        // 新增
        List<UniMajorInfoB> newEntityList = new ArrayList<>();
        List<UniMajorInfoBLatest> newEntityLatestList = new ArrayList<>();
        for (int i = 0; i < protoToAddList.size(); i++) {
            UniMajorInfo proto = protoToAddList.get(i);
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

        Date time2 = new Date();



        //更新, 获取原始保存的专业
        List<Long> latestIdToUpdate = new ArrayList<>();
        for (int i = 0; i < protoToUpdateList.size(); i++) {
            UniMajorInfo proto = protoToUpdateList.get(i);
            Long uid = CommonUtil.GetMajorInfoId(examYear, proto.getUniMajorCode(), proto.getMajorCode());
            latestIdToUpdate.add(uid);

        }


        //1 获取 curlatest 数据
        List<UniMajorInfoBLatestVO> latestDBs =  DBProxy.Getinstance().UniMajorInfoBLatestService.findByIdIn(latestIdToUpdate);
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

        // 更新
        List<UniMajorInfoB> updateEntityList = new ArrayList<>();
        List<UniMajorInfoBLatest> updateEntityLatestList = new ArrayList<>();
        for (int i = 0; i < protoToUpdateList.size(); i++) {
            UniMajorInfo proto = protoToUpdateList.get(i);

            Long uid = CommonUtil.GetMajorInfoId(examYear, proto.getUniMajorCode(), proto.getMajorCode());

            UniMajorInfoBLatestVO updateLatestVO = CurLatestMajorByIdDic.getOrDefault(uid, null);
            UniMajorInfoBVO updateHistVO = CurHistMajorByIdDic.getOrDefault(uid, null);

            if(updateHistVO != null && updateLatestVO != null){
                //根据 已经存在 DB信息, 更新
                UniMajorInfoBLatestVO latestDB = UniMajorInfoBLatestVO.ConvertFromDTO(proto, updateLatestVO);
                latestDB.setDataVersion(NewVersion);
                UniMajorInfoBVO histDB_Update = UniMajorInfoBVO.ConvertFromDTO(proto, updateHistVO);
                histDB_Update.setDataVersion(NewVersion);

                updateEntityList.add(BeanMapper.map(histDB_Update, UniMajorInfoB.class));
                updateEntityLatestList.add(BeanMapper.map(latestDB, UniMajorInfoBLatest.class));
            }else {

                System.out.println("数据无法更新, 因为在原来数据库中找不到 id " + uid +", unimajord :" + proto.getUniMajorCode() + ", " + proto.getMajorCode() + ", 专业" +  proto.getMajorName());

            }

        }

        Date time3 = new Date();

        //删除
        List<Long> idToDelete = new ArrayList<>();
        for (int i = 0; i < protoToDeleteList.size(); i++) {
            //删除 LatestVO
            Long uid = CommonUtil.GetMajorInfoId(examYear, protoToDeleteList.get(i).getUniMajorCode(), protoToDeleteList.get(i).getMajorCode());
            idToDelete.add(uid);
//            DBProxy.Getinstance().UniMajorInfoBLatestService.deleteByKeyId(deleteMajorList.get(i).getId());
            //删除  HistVO
        }
        if(idToDelete.size() > 0){
            DBProxy.Getinstance().UniMajorInfoBLatestService.deleteByIdIn(idToDelete); //不删除, 否则 志愿表的旧数据需要处理;  同时用latetest 和 Histdb 数据不对齐, 不能用latetest是否存在, 检测 histdb是否存在.
            DBProxy.Getinstance().UniMajorInfoBService.deleteByIdIn(idToDelete); //不删除, 否则 志愿表的旧数据需要处理;  同时用latetest 和 Histdb 数据不对齐, 不能用latetest是否存在, 检测 histdb是否存在.
        }


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

        System.out.println("update pici " + pici.name() + ",保存New Data 到Dic:" + (time2.getTime() - time1.getTime())
                + ",整理to update data:" + (time3.getTime() - time2.getTime())
                + ",delete:" + (time4.getTime() - time3.getTime())
                + ",batch insert/update num: " + newEntityLatestList.size() +"/"+ updateEntityLatestList.size() + ", cost time:" + (time5.getTime() - time4.getTime())
                + ",总共: " + (time5.getTime() - time1.getTime()));
    }

    private void UpdateDataUniMajorInfoZhuanKe(List<UniMajorInfo> protoToAddList,  List<UniMajorInfo> protoToUpdateList,List<UniMajorInfo> protoToDeleteList, EnumPici pici, String examYear, Integer NewVersion) throws Exception {
        Date time1 = new Date();

        HashMap<Long, UniMajorInfoZhuanKeVO> checkHistDBID = new HashMap<>();
        //6 添加 更新 删除 //按id排序
        // 新增
        List<UniMajorInfoZhuanKe> newEntityList = new ArrayList<>();
        List<UniMajorInfoZhuanKeLatest> newEntityLatestList = new ArrayList<>();
        for (int i = 0; i < protoToAddList.size(); i++) {
            UniMajorInfo proto = protoToAddList.get(i);
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

        Date time2 = new Date();



        //更新, 获取原始保存的专业
        List<Long> latestIdToUpdate = new ArrayList<>();
        for (int i = 0; i < protoToUpdateList.size(); i++) {
            UniMajorInfo proto = protoToUpdateList.get(i);
            Long uid = CommonUtil.GetMajorInfoId(examYear, proto.getUniMajorCode(), proto.getMajorCode());
            latestIdToUpdate.add(uid);

        }


        //1 获取 curlatest 数据
        List<UniMajorInfoZhuanKeLatestVO> latestDBs =  DBProxy.Getinstance().UniMajorInfoZhuanKeLatestService.findByIdIn(latestIdToUpdate);
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

        // 更新
        List<UniMajorInfoZhuanKe> updateEntityList = new ArrayList<>();
        List<UniMajorInfoZhuanKeLatest> updateEntityLatestList = new ArrayList<>();
        for (int i = 0; i < protoToUpdateList.size(); i++) {
            UniMajorInfo proto = protoToUpdateList.get(i);

            Long uid = CommonUtil.GetMajorInfoId(examYear, proto.getUniMajorCode(), proto.getMajorCode());

            UniMajorInfoZhuanKeLatestVO updateLatestVO = CurLatestMajorByIdDic.getOrDefault(uid, null);
            UniMajorInfoZhuanKeVO updateHistVO = CurHistMajorByIdDic.getOrDefault(uid, null);

            if(updateHistVO != null && updateLatestVO != null){
                //根据 已经存在 DB信息, 更新
                UniMajorInfoZhuanKeLatestVO latestDB = UniMajorInfoZhuanKeLatestVO.ConvertFromDTO(proto, updateLatestVO);
                latestDB.setDataVersion(NewVersion);
                UniMajorInfoZhuanKeVO histDB_Update = UniMajorInfoZhuanKeVO.ConvertFromDTO(proto, updateHistVO);
                histDB_Update.setDataVersion(NewVersion);

                updateEntityList.add(BeanMapper.map(histDB_Update, UniMajorInfoZhuanKe.class));
                updateEntityLatestList.add(BeanMapper.map(latestDB, UniMajorInfoZhuanKeLatest.class));
            }else {

                System.out.println("数据无法更新, 因为在原来数据库中找不到 id " + uid +", unimajord :" + proto.getUniMajorCode() + ", " + proto.getMajorCode() + ", 专业" +  proto.getMajorName());

            }

        }

        Date time3 = new Date();

        //删除
        List<Long> idToDelete = new ArrayList<>();
        for (int i = 0; i < protoToDeleteList.size(); i++) {
            //删除 LatestVO
            Long uid = CommonUtil.GetMajorInfoId(examYear, protoToDeleteList.get(i).getUniMajorCode(), protoToDeleteList.get(i).getMajorCode());
            idToDelete.add(uid);
//            DBProxy.Getinstance().UniMajorInfoZhuanKeLatestService.deleteByKeyId(deleteMajorList.get(i).getId());
            //删除  HistVO
        }
        if(idToDelete.size() > 0){
            DBProxy.Getinstance().UniMajorInfoZhuanKeLatestService.deleteByIdIn(idToDelete); //不删除, 否则 志愿表的旧数据需要处理;  同时用latetest 和 Histdb 数据不对齐, 不能用latetest是否存在, 检测 histdb是否存在.
            DBProxy.Getinstance().UniMajorInfoZhuanKeService.deleteByIdIn(idToDelete); //不删除, 否则 志愿表的旧数据需要处理;  同时用latetest 和 Histdb 数据不对齐, 不能用latetest是否存在, 检测 histdb是否存在.
        }


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

        System.out.println("update pici " + pici.name() + ",保存New Data 到Dic:" + (time2.getTime() - time1.getTime())
                + ",整理to update data:" + (time3.getTime() - time2.getTime())
                + ",delete:" + (time4.getTime() - time3.getTime())
                + ",batch insert/update num: " + newEntityLatestList.size() +"/"+ updateEntityLatestList.size() + ", cost time:" + (time5.getTime() - time4.getTime())
                + ",总共: " + (time5.getTime() - time1.getTime()));
    }

    private void UpdateDataUniMajorInfoPreA(List<UniMajorInfo> protoToAddList,  List<UniMajorInfo> protoToUpdateList,List<UniMajorInfo> protoToDeleteList, EnumPici pici, String examYear, Integer NewVersion) throws Exception {
        Date time1 = new Date();

        HashMap<Long, UniMajorInfoPreAVO> checkHistDBID = new HashMap<>();
        //6 添加 更新 删除 //按id排序
        // 新增
        List<UniMajorInfoPreA> newEntityList = new ArrayList<>();
        List<UniMajorInfoPreALatest> newEntityLatestList = new ArrayList<>();
        for (int i = 0; i < protoToAddList.size(); i++) {
            UniMajorInfo proto = protoToAddList.get(i);
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

        Date time2 = new Date();



        //更新, 获取原始保存的专业
        List<Long> latestIdToUpdate = new ArrayList<>();
        for (int i = 0; i < protoToUpdateList.size(); i++) {
            UniMajorInfo proto = protoToUpdateList.get(i);
            Long uid = CommonUtil.GetMajorInfoId(examYear, proto.getUniMajorCode(), proto.getMajorCode());
            latestIdToUpdate.add(uid);

        }


        //1 获取 curlatest 数据
        List<UniMajorInfoPreALatestVO> latestDBs =  DBProxy.Getinstance().UniMajorInfoPreALatestService.findByIdIn(latestIdToUpdate);
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

        // 更新
        List<UniMajorInfoPreA> updateEntityList = new ArrayList<>();
        List<UniMajorInfoPreALatest> updateEntityLatestList = new ArrayList<>();
        for (int i = 0; i < protoToUpdateList.size(); i++) {
            UniMajorInfo proto = protoToUpdateList.get(i);

            Long uid = CommonUtil.GetMajorInfoId(examYear, proto.getUniMajorCode(), proto.getMajorCode());

            UniMajorInfoPreALatestVO updateLatestVO = CurLatestMajorByIdDic.getOrDefault(uid, null);
            UniMajorInfoPreAVO updateHistVO = CurHistMajorByIdDic.getOrDefault(uid, null);

            if(updateHistVO != null && updateLatestVO != null){
                //根据 已经存在 DB信息, 更新
                UniMajorInfoPreALatestVO latestDB = UniMajorInfoPreALatestVO.ConvertFromDTO(proto, updateLatestVO);
                latestDB.setDataVersion(NewVersion);
                UniMajorInfoPreAVO histDB_Update = UniMajorInfoPreAVO.ConvertFromDTO(proto, updateHistVO);
                histDB_Update.setDataVersion(NewVersion);

                updateEntityList.add(BeanMapper.map(histDB_Update, UniMajorInfoPreA.class));
                updateEntityLatestList.add(BeanMapper.map(latestDB, UniMajorInfoPreALatest.class));
            }else {

                System.out.println("数据无法更新, 因为在原来数据库中找不到 id " + uid +", unimajord :" + proto.getUniMajorCode() + ", " + proto.getMajorCode() + ", 专业" +  proto.getMajorName());

            }

        }

        Date time3 = new Date();

        //删除
        List<Long> idToDelete = new ArrayList<>();
        for (int i = 0; i < protoToDeleteList.size(); i++) {
            //删除 LatestVO
            Long uid = CommonUtil.GetMajorInfoId(examYear, protoToDeleteList.get(i).getUniMajorCode(), protoToDeleteList.get(i).getMajorCode());
            idToDelete.add(uid);
//            DBProxy.Getinstance().UniMajorInfoPreALatestService.deleteByKeyId(deleteMajorList.get(i).getId());
            //删除  HistVO
        }
        if(idToDelete.size() > 0){
            DBProxy.Getinstance().UniMajorInfoPreALatestService.deleteByIdIn(idToDelete); //不删除, 否则 志愿表的旧数据需要处理;  同时用latetest 和 Histdb 数据不对齐, 不能用latetest是否存在, 检测 histdb是否存在.
            DBProxy.Getinstance().UniMajorInfoPreAService.deleteByIdIn(idToDelete); //不删除, 否则 志愿表的旧数据需要处理;  同时用latetest 和 Histdb 数据不对齐, 不能用latetest是否存在, 检测 histdb是否存在.
        }


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

        System.out.println("update pici " + pici.name() + ",保存New Data 到Dic:" + (time2.getTime() - time1.getTime())
                + ",整理to update data:" + (time3.getTime() - time2.getTime())
                + ",delete:" + (time4.getTime() - time3.getTime())
                + ",batch insert/update num: " + newEntityLatestList.size() +"/"+ updateEntityLatestList.size() + ", cost time:" + (time5.getTime() - time4.getTime())
                + ",总共: " + (time5.getTime() - time1.getTime()));
    }

    private void UpdateDataUniMajorInfoPreB(List<UniMajorInfo> protoToAddList,  List<UniMajorInfo> protoToUpdateList,List<UniMajorInfo> protoToDeleteList, EnumPici pici, String examYear, Integer NewVersion) throws Exception {
        Date time1 = new Date();

        HashMap<Long, UniMajorInfoPreBVO> checkHistDBID = new HashMap<>();
        //6 添加 更新 删除 //按id排序
        // 新增
        List<UniMajorInfoPreB> newEntityList = new ArrayList<>();
        List<UniMajorInfoPreBLatest> newEntityLatestList = new ArrayList<>();
        for (int i = 0; i < protoToAddList.size(); i++) {
            UniMajorInfo proto = protoToAddList.get(i);
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

        Date time2 = new Date();



        //更新, 获取原始保存的专业
        List<Long> latestIdToUpdate = new ArrayList<>();
        for (int i = 0; i < protoToUpdateList.size(); i++) {
            UniMajorInfo proto = protoToUpdateList.get(i);
            Long uid = CommonUtil.GetMajorInfoId(examYear, proto.getUniMajorCode(), proto.getMajorCode());
            latestIdToUpdate.add(uid);

        }


        //1 获取 curlatest 数据
        List<UniMajorInfoPreBLatestVO> latestDBs =  DBProxy.Getinstance().UniMajorInfoPreBLatestService.findByIdIn(latestIdToUpdate);
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

        // 更新
        List<UniMajorInfoPreB> updateEntityList = new ArrayList<>();
        List<UniMajorInfoPreBLatest> updateEntityLatestList = new ArrayList<>();
        for (int i = 0; i < protoToUpdateList.size(); i++) {
            UniMajorInfo proto = protoToUpdateList.get(i);

            Long uid = CommonUtil.GetMajorInfoId(examYear, proto.getUniMajorCode(), proto.getMajorCode());

            UniMajorInfoPreBLatestVO updateLatestVO = CurLatestMajorByIdDic.getOrDefault(uid, null);
            UniMajorInfoPreBVO updateHistVO = CurHistMajorByIdDic.getOrDefault(uid, null);

            if(updateHistVO != null && updateLatestVO != null){
                //根据 已经存在 DB信息, 更新
                UniMajorInfoPreBLatestVO latestDB = UniMajorInfoPreBLatestVO.ConvertFromDTO(proto, updateLatestVO);
                latestDB.setDataVersion(NewVersion);
                UniMajorInfoPreBVO histDB_Update = UniMajorInfoPreBVO.ConvertFromDTO(proto, updateHistVO);
                histDB_Update.setDataVersion(NewVersion);

                updateEntityList.add(BeanMapper.map(histDB_Update, UniMajorInfoPreB.class));
                updateEntityLatestList.add(BeanMapper.map(latestDB, UniMajorInfoPreBLatest.class));
            }else {

                System.out.println("数据无法更新, 因为在原来数据库中找不到 id " + uid +", unimajord :" + proto.getUniMajorCode() + ", " + proto.getMajorCode() + ", 专业" +  proto.getMajorName());

            }

        }

        Date time3 = new Date();

        //删除
        List<Long> idToDelete = new ArrayList<>();
        for (int i = 0; i < protoToDeleteList.size(); i++) {
            //删除 LatestVO
            Long uid = CommonUtil.GetMajorInfoId(examYear, protoToDeleteList.get(i).getUniMajorCode(), protoToDeleteList.get(i).getMajorCode());
            idToDelete.add(uid);
//            DBProxy.Getinstance().UniMajorInfoPreBLatestService.deleteByKeyId(deleteMajorList.get(i).getId());
            //删除  HistVO
        }
        if(idToDelete.size() > 0){
            DBProxy.Getinstance().UniMajorInfoPreBLatestService.deleteByIdIn(idToDelete); //不删除, 否则 志愿表的旧数据需要处理;  同时用latetest 和 Histdb 数据不对齐, 不能用latetest是否存在, 检测 histdb是否存在.
            DBProxy.Getinstance().UniMajorInfoPreBService.deleteByIdIn(idToDelete); //不删除, 否则 志愿表的旧数据需要处理;  同时用latetest 和 Histdb 数据不对齐, 不能用latetest是否存在, 检测 histdb是否存在.
        }


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

        System.out.println("update pici " + pici.name() + ",保存New Data 到Dic:" + (time2.getTime() - time1.getTime())
                + ",整理to update data:" + (time3.getTime() - time2.getTime())
                + ",delete:" + (time4.getTime() - time3.getTime())
                + ",batch insert/update num: " + newEntityLatestList.size() +"/"+ updateEntityLatestList.size() + ", cost time:" + (time5.getTime() - time4.getTime())
                + ",总共: " + (time5.getTime() - time1.getTime()));
    }

    private void UpdateDataUniMajorInfoZXA(List<UniMajorInfo> protoToAddList,  List<UniMajorInfo> protoToUpdateList,List<UniMajorInfo> protoToDeleteList, EnumPici pici, String examYear, Integer NewVersion) throws Exception {
        Date time1 = new Date();

        HashMap<Long, UniMajorInfoZXAVO> checkHistDBID = new HashMap<>();
        //6 添加 更新 删除 //按id排序
        // 新增
        List<UniMajorInfoZXA> newEntityList = new ArrayList<>();
        List<UniMajorInfoZXALatest> newEntityLatestList = new ArrayList<>();
        for (int i = 0; i < protoToAddList.size(); i++) {
            UniMajorInfo proto = protoToAddList.get(i);
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

        Date time2 = new Date();



        //更新, 获取原始保存的专业
        List<Long> latestIdToUpdate = new ArrayList<>();
        for (int i = 0; i < protoToUpdateList.size(); i++) {
            UniMajorInfo proto = protoToUpdateList.get(i);
            Long uid = CommonUtil.GetMajorInfoId(examYear, proto.getUniMajorCode(), proto.getMajorCode());
            latestIdToUpdate.add(uid);

        }


        //1 获取 curlatest 数据
        List<UniMajorInfoZXALatestVO> latestDBs =  DBProxy.Getinstance().UniMajorInfoZXALatestService.findByIdIn(latestIdToUpdate);
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

        // 更新
        List<UniMajorInfoZXA> updateEntityList = new ArrayList<>();
        List<UniMajorInfoZXALatest> updateEntityLatestList = new ArrayList<>();
        for (int i = 0; i < protoToUpdateList.size(); i++) {
            UniMajorInfo proto = protoToUpdateList.get(i);

            Long uid = CommonUtil.GetMajorInfoId(examYear, proto.getUniMajorCode(), proto.getMajorCode());

            UniMajorInfoZXALatestVO updateLatestVO = CurLatestMajorByIdDic.getOrDefault(uid, null);
            UniMajorInfoZXAVO updateHistVO = CurHistMajorByIdDic.getOrDefault(uid, null);

            if(updateHistVO != null && updateLatestVO != null){
                //根据 已经存在 DB信息, 更新
                UniMajorInfoZXALatestVO latestDB = UniMajorInfoZXALatestVO.ConvertFromDTO(proto, updateLatestVO);
                latestDB.setDataVersion(NewVersion);
                UniMajorInfoZXAVO histDB_Update = UniMajorInfoZXAVO.ConvertFromDTO(proto, updateHistVO);
                histDB_Update.setDataVersion(NewVersion);

                updateEntityList.add(BeanMapper.map(histDB_Update, UniMajorInfoZXA.class));
                updateEntityLatestList.add(BeanMapper.map(latestDB, UniMajorInfoZXALatest.class));
            }else {

                System.out.println("数据无法更新, 因为在原来数据库中找不到 id " + uid +", unimajord :" + proto.getUniMajorCode() + ", " + proto.getMajorCode() + ", 专业" +  proto.getMajorName());

            }

        }

        Date time3 = new Date();

        //删除
        List<Long> idToDelete = new ArrayList<>();
        for (int i = 0; i < protoToDeleteList.size(); i++) {
            //删除 LatestVO
            Long uid = CommonUtil.GetMajorInfoId(examYear, protoToDeleteList.get(i).getUniMajorCode(), protoToDeleteList.get(i).getMajorCode());
            idToDelete.add(uid);
//            DBProxy.Getinstance().UniMajorInfoZXALatestService.deleteByKeyId(deleteMajorList.get(i).getId());
            //删除  HistVO
        }
        if(idToDelete.size() > 0){
            DBProxy.Getinstance().UniMajorInfoZXALatestService.deleteByIdIn(idToDelete); //不删除, 否则 志愿表的旧数据需要处理;  同时用latetest 和 Histdb 数据不对齐, 不能用latetest是否存在, 检测 histdb是否存在.
            DBProxy.Getinstance().UniMajorInfoZXAService.deleteByIdIn(idToDelete); //不删除, 否则 志愿表的旧数据需要处理;  同时用latetest 和 Histdb 数据不对齐, 不能用latetest是否存在, 检测 histdb是否存在.
        }


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

        System.out.println("update pici " + pici.name() + ",保存New Data 到Dic:" + (time2.getTime() - time1.getTime())
                + ",整理to update data:" + (time3.getTime() - time2.getTime())
                + ",delete:" + (time4.getTime() - time3.getTime())
                + ",batch insert/update num: " + newEntityLatestList.size() +"/"+ updateEntityLatestList.size() + ", cost time:" + (time5.getTime() - time4.getTime())
                + ",总共: " + (time5.getTime() - time1.getTime()));
    }

    private void UpdateDataUniMajorInfoZXB(List<UniMajorInfo> protoToAddList,  List<UniMajorInfo> protoToUpdateList,List<UniMajorInfo> protoToDeleteList, EnumPici pici, String examYear, Integer NewVersion) throws Exception {
        Date time1 = new Date();

        HashMap<Long, UniMajorInfoZXBVO> checkHistDBID = new HashMap<>();
        //6 添加 更新 删除 //按id排序
        // 新增
        List<UniMajorInfoZXB> newEntityList = new ArrayList<>();
        List<UniMajorInfoZXBLatest> newEntityLatestList = new ArrayList<>();
        for (int i = 0; i < protoToAddList.size(); i++) {
            UniMajorInfo proto = protoToAddList.get(i);
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

        Date time2 = new Date();



        //更新, 获取原始保存的专业
        List<Long> latestIdToUpdate = new ArrayList<>();
        for (int i = 0; i < protoToUpdateList.size(); i++) {
            UniMajorInfo proto = protoToUpdateList.get(i);
            Long uid = CommonUtil.GetMajorInfoId(examYear, proto.getUniMajorCode(), proto.getMajorCode());
            latestIdToUpdate.add(uid);

        }


        //1 获取 curlatest 数据
        List<UniMajorInfoZXBLatestVO> latestDBs =  DBProxy.Getinstance().UniMajorInfoZXBLatestService.findByIdIn(latestIdToUpdate);
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

        // 更新
        List<UniMajorInfoZXB> updateEntityList = new ArrayList<>();
        List<UniMajorInfoZXBLatest> updateEntityLatestList = new ArrayList<>();
        for (int i = 0; i < protoToUpdateList.size(); i++) {
            UniMajorInfo proto = protoToUpdateList.get(i);

            Long uid = CommonUtil.GetMajorInfoId(examYear, proto.getUniMajorCode(), proto.getMajorCode());

            UniMajorInfoZXBLatestVO updateLatestVO = CurLatestMajorByIdDic.getOrDefault(uid, null);
            UniMajorInfoZXBVO updateHistVO = CurHistMajorByIdDic.getOrDefault(uid, null);

            if(updateHistVO != null && updateLatestVO != null){
                //根据 已经存在 DB信息, 更新
                UniMajorInfoZXBLatestVO latestDB = UniMajorInfoZXBLatestVO.ConvertFromDTO(proto, updateLatestVO);
                latestDB.setDataVersion(NewVersion);
                UniMajorInfoZXBVO histDB_Update = UniMajorInfoZXBVO.ConvertFromDTO(proto, updateHistVO);
                histDB_Update.setDataVersion(NewVersion);

                updateEntityList.add(BeanMapper.map(histDB_Update, UniMajorInfoZXB.class));
                updateEntityLatestList.add(BeanMapper.map(latestDB, UniMajorInfoZXBLatest.class));
            }else {

                System.out.println("数据无法更新, 因为在原来数据库中找不到 id " + uid +", unimajord :" + proto.getUniMajorCode() + ", " + proto.getMajorCode() + ", 专业" +  proto.getMajorName());

            }

        }

        Date time3 = new Date();

        //删除
        List<Long> idToDelete = new ArrayList<>();
        for (int i = 0; i < protoToDeleteList.size(); i++) {
            //删除 LatestVO
            Long uid = CommonUtil.GetMajorInfoId(examYear, protoToDeleteList.get(i).getUniMajorCode(), protoToDeleteList.get(i).getMajorCode());
            idToDelete.add(uid);
//            DBProxy.Getinstance().UniMajorInfoZXBLatestService.deleteByKeyId(deleteMajorList.get(i).getId());
            //删除  HistVO
        }
        if(idToDelete.size() > 0){
            DBProxy.Getinstance().UniMajorInfoZXBLatestService.deleteByIdIn(idToDelete); //不删除, 否则 志愿表的旧数据需要处理;  同时用latetest 和 Histdb 数据不对齐, 不能用latetest是否存在, 检测 histdb是否存在.
            DBProxy.Getinstance().UniMajorInfoZXBService.deleteByIdIn(idToDelete); //不删除, 否则 志愿表的旧数据需要处理;  同时用latetest 和 Histdb 数据不对齐, 不能用latetest是否存在, 检测 histdb是否存在.
        }


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

        System.out.println("update pici " + pici.name() + ",保存New Data 到Dic:" + (time2.getTime() - time1.getTime())
                + ",整理to update data:" + (time3.getTime() - time2.getTime())
                + ",delete:" + (time4.getTime() - time3.getTime())
                + ",batch insert/update num: " + newEntityLatestList.size() +"/"+ updateEntityLatestList.size() + ", cost time:" + (time5.getTime() - time4.getTime())
                + ",总共: " + (time5.getTime() - time1.getTime()));
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
