package com.baizhou.dbtool.proxy;

import com.baizhou.common.CommonUtil;
import com.baizhou.core.model.entity.*;
import com.baizhou.core.model.vo.*;
import com.baizhou.data.constdefine.ConstDefine;
import com.baizhou.db.DBProxy;
import com.baizhou.dbtool.model.ExcelRowData;
import com.baizhou.dbtool.model.ExcelTableData;
import com.baizhou.dbtool.model.Param;
import com.baizhou.dbtool.model.UniMajorParam;
import com.baizhou.gameutil.StringParser;
import com.msg.MajorInfoYear;
import com.msg.UniMajorInfo;
import org.apache.poi.ss.usermodel.Sheet;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ThreeYearMajorInfoParseProxy implements IExcelParseProxy {
    @Override
    public void DoProxy(ExcelTableData table, File file, Param Param) throws InterruptedException {

        //保存excel数据
        this.ResetDic(table);


        UniMajorParam param = (UniMajorParam) Param;
        int sheetIdx = param.SheetIdx;
        int year = param.NewVersion; // 0: 最近第一个年   1: 2年  2:3年
        int pici = 0;

        switch (sheetIdx) {
            case 0: {
                System.out.println("开始解析提前批A");
                //提前批A
                pici = 3;
                break;
            }
            case 1: {
                //提前批B
                System.out.println("开始解析提前批B");
                pici = 4;
                break;
            }
            case 2: {
                //本A
                System.out.println("开始解析本A");
                pici = 1;
                break;
            }
            case 3: {
                //本B
                System.out.println("开始解析本B");
                pici = 2;
                break;
            }
        }

        //转成proto 数据
        List<MajorInfoYear> protoList = ConvertToProtoList(UniMajorDic, pici);

        switch (year) {
            case 0: {
                System.out.println("开始解析最近1年 专业  ");

                //转成proto 数据
//                List<MajorInfoYear> protoList = ConvertToProtoList(UniMajorDic, pici);

//                DBProxy.Getinstance().UniMajorInfoPreALatestService.deleteAll();
                Thread.sleep(ConstDefine.InsertDBInterval);

                for (int i = 0; i < protoList.size(); i++) {
                    MajorInfoYear proto = protoList.get(i);
                    //保存
                    MajorInfoY1VO majorInfoVO = MajorInfoY1VO.ConvertFromDTO(proto);
                    majorInfoVO.setId(null);
                    DBProxy.Getinstance().MajorInfoY1Service.saveMajorInfoY1(majorInfoVO);

                    System.out.println("保存 最近1年" + majorInfoVO.getMajorName());
                    Thread.sleep(ConstDefine.InsertDBIntervalQuick);
                }


                break;
            }

            case 1: {
                System.out.println("开始解析最近2年 专业");
                Thread.sleep(ConstDefine.InsertDBInterval);

                for (int i = 0; i < protoList.size(); i++) {
                    MajorInfoYear proto = protoList.get(i);
                    //保存
                    MajorInfoY2VO majorInfoVO = MajorInfoY2VO.ConvertFromDTO(proto);
                    majorInfoVO.setId(null);
                    DBProxy.Getinstance().MajorInfoY2Service.saveMajorInfoY2(majorInfoVO);

                    System.out.println("保存 最近2年" + majorInfoVO.getMajorName());
                    Thread.sleep(ConstDefine.InsertDBIntervalQuick);
                }


                break;
            }

            case 2: {
                System.out.println("开始解析最近3年 专业");
                Thread.sleep(ConstDefine.InsertDBInterval);

                for (int i = 0; i < protoList.size(); i++) {
                    MajorInfoYear proto = protoList.get(i);
                    //保存
                    MajorInfoY3VO majorInfoVO = MajorInfoY3VO.ConvertFromDTO(proto);
                    majorInfoVO.setId(null);
                    DBProxy.Getinstance().MajorInfoY3Service.saveMajorInfoY3(majorInfoVO);

                    System.out.println("保存 最近3年" + majorInfoVO.getMajorName());
                    Thread.sleep(ConstDefine.InsertDBIntervalQuick);
                }


                break;
            }
        }


        System.out.println("完成解析专业信息");
    }


    HashMap<String, HashMap<String, ExcelRowData>> UniMajorDic = new HashMap<>();

    private void ResetDic(ExcelTableData table) {
        UniMajorDic.clear();

        for (int i = 0; i < table.DataRows.size(); i++) {
            ExcelRowData row = table.DataRows.get(i);

            String groupCode = row.Cells.get(0).Data.trim(); //专业组代码
            String majorCode = row.Cells.get(2).Data.trim(); //专业代码
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


    private List<MajorInfoYear> ConvertToProtoList(HashMap<String, HashMap<String, ExcelRowData>> UniMajorDic,
                                                   int Pici) {

        List<MajorInfoYear> uniMajorInfos = new ArrayList<>();


        for (Map.Entry<String, HashMap<String, ExcelRowData>> uniMajors : UniMajorDic.entrySet()) {
            String majorGroupCode = uniMajors.getKey();  //专业组信息
            HashMap<String, ExcelRowData> majorDic = uniMajors.getValue();


            for (Map.Entry<String, ExcelRowData> major : majorDic.entrySet()) {
                //统计组合信息
                ExcelRowData row = major.getValue();

                com.msg.MajorInfoYear.Builder builder = Convert(row, Pici);

                uniMajorInfos.add(builder.build());
            }
        }

        return uniMajorInfos;
    }

    private com.msg.MajorInfoYear.Builder Convert(ExcelRowData row, int pici) {

        com.msg.MajorInfoYear.Builder info = com.msg.MajorInfoYear.newBuilder();

        info.setId(0);
        info.setUniMajorCode(row.Cells.get(0).Data.trim());
        info.setPici(pici);
        info.setSchoolName(row.Cells.get(1).Data.trim());
        info.setMajorCode(row.Cells.get(2).Data.trim());
        info.setMajorName(row.Cells.get(3).Data.trim());
        info.setSubjectRequirement(row.Cells.get(4).Data.trim());
        info.setRequireLang(row.Cells.get(5).Data.trim());
        info.setRequireInterview(row.Cells.get(6).Data.trim());
        info.setMajorComments(row.Cells.get(7).Data.trim());
        info.setMajorDuration(row.Cells.get(8).Data.trim());
        info.setMajorTuition(row.Cells.get(9).Data.trim());

        String major22P = row.Cells.get(10).Data.trim();
        String major22A = row.Cells.get(11).Data.trim();
        info.setMajor22Plan((major22P.isEmpty() || !CommonUtil.isNumeric(major22P) ? 0 : (int) Float.parseFloat(major22P)) + "");
        info.setMajor22Admin((major22A.isEmpty() || !CommonUtil.isNumeric(major22A) ? 0 : (int) Float.parseFloat(major22A)) + "");

//        info.setStudentNum22Admit(row.Cells.get(0).Data.trim());
        String scoreFor22 = row.Cells.get(12).Data.trim();
        info.setScoreFor22((scoreFor22.isEmpty() || !CommonUtil.isNumeric(scoreFor22) ? 0 : (int) Float.parseFloat(scoreFor22)) + "");
        String rankFor22 = row.Cells.get(13).Data.trim();
        info.setRankFor22((rankFor22.isEmpty() || !CommonUtil.isNumeric(rankFor22) ? 0 : (int) Float.parseFloat(rankFor22)) + "");

        String major22HS = row.Cells.get(14).Data.trim();
        String major22HR = row.Cells.get(15).Data.trim();
        info.setMajor22HScore((major22HS.isEmpty() || !CommonUtil.isNumeric(major22HS) ? 0 : (int) Float.parseFloat(major22HS)) + "");
        info.setMajor22HRank((major22HR.isEmpty() || !CommonUtil.isNumeric(major22HR) ? 0 : (int) Float.parseFloat(major22HR)) + "");

        String major22LS = row.Cells.get(16).Data.trim();
        String major22LR = row.Cells.get(17).Data.trim();
        info.setMajor22LowScore((major22LS.isEmpty() || !CommonUtil.isNumeric(major22LS) ? 0 : (int) Float.parseFloat(major22LS)) + "");
        info.setMajor22LowRank((major22LR.isEmpty() || !CommonUtil.isNumeric(major22LR) ? 0 : (int) Float.parseFloat(major22LR)) + "");


        String major22AvgS = row.Cells.get(18).Data.trim();
        String major22AvgR = row.Cells.get(19).Data.trim();
        info.setMajor22AvgScore((major22AvgS.isEmpty() || !CommonUtil.isNumeric(major22AvgS) ? 0 : (int) Float.parseFloat(major22AvgS)) + "");
        info.setMajor22AvgRank((major22AvgR.isEmpty() || !CommonUtil.isNumeric(major22AvgR) ? 0 : (int) Float.parseFloat(major22AvgR)) + "");

        info.setPiciName(row.Cells.get(20).Data.trim());


        return info;
    }


}

