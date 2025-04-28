package com.baizhou.dbtool.proxy;

import com.baizhou.core.model.vo.MajorClsInfoVO;
import com.baizhou.core.model.vo.ScoreToRankInfoVO;
import com.baizhou.data.constdefine.ConstDefine;
import com.baizhou.db.DBProxy;
import com.baizhou.dbtool.model.ExcelRowData;
import com.baizhou.dbtool.model.ExcelTableData;
import com.baizhou.dbtool.model.Param;

import java.io.File;
import java.util.List;

public class MajorParserProxy implements IExcelParseProxy {
    @Override
    public void DoProxy(ExcelTableData table, File file, Param Param) {
//        DBProxy.Getinstance().MajorClsInfoService.deleteAll();   不删除旧数据, 否则原来的 数据有出错
        System.out.println("开始解析专业目录");
        MajorClsInfoVO vo = new MajorClsInfoVO();

        for (int i = 0; i < table.DataRows.size(); i++) {
            ExcelRowData row = table.DataRows.get(i);

            String major1stClsStr = row.Cells.get(0).Data.trim(); //大类
            String major2ndClsStr = row.Cells.get(1).Data.trim(); //2类
            String major3rdClsStr = row.Cells.get(3).Data.trim(); //3类


            int startIdxFor2 = 10;//二 开始列
            int colNum = 5;//
            String type3_name = "二" + ConstDefine.ItemSperator4;

            //记录 专业的限制
            String type3String = "";
            for (int j = 0; j < colNum; j++) {
                String temp = row.Cells.get(startIdxFor2 + j).Data.trim();
                if(!temp.isEmpty()){
                    if(!type3String.isEmpty()) type3String += ConstDefine.ItemSperator7;
                    type3String += type3_name + (j+1);
                }
            }



            if (major1stClsStr.isEmpty()) continue; //空分数不记录

            vo.setMajor1stCls(major1stClsStr);
            vo.setMajor2ndCls(major2ndClsStr);
            vo.setMajor3rdCls(major3rdClsStr);
            vo.setPhysicLimits(type3String);

//            先按cls3查询 是否已经存在, 存在就更新;  否则添加新数据
//            List<MajorClsInfoVO>  list = DBProxy.Getinstance().MajorClsInfoService.findByMajor1stClsAndMajor2ndClsAndMajor3rdCls(major1stClsStr, major2ndClsStr, major3rdClsStr);
            List<MajorClsInfoVO>  list = DBProxy.Getinstance().MajorClsInfoService.findByMajor3rdCls( major3rdClsStr);
            if(list.size() > 0){
                for (int j = 0; j < list.size(); j++) {
                    MajorClsInfoVO temp = list.get(j);
                    temp.setMajor1stCls(major1stClsStr);
                    temp.setMajor2ndCls(major2ndClsStr);
                    temp.setPhysicLimits(type3String);
                    DBProxy.Getinstance().MajorClsInfoService.saveMajorClsInfo(temp);
                    System.out.println("Update"+vo.getMajor3rdCls());
                }

            }else {
                DBProxy.Getinstance().MajorClsInfoService.saveMajorClsInfo(vo);
                System.out.println("保存"+vo.getMajor3rdCls());
            }


            try {
                Thread.sleep(ConstDefine.InsertDBIntervalQuick);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("完成解析专业目录");
    }
}
