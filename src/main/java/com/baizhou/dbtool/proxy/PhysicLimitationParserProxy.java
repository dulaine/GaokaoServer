package com.baizhou.dbtool.proxy;

import com.baizhou.core.model.vo.MajorClsInfoVO;
import com.baizhou.core.model.vo.PhysicLimitationInfoVO;
import com.baizhou.core.model.vo.ScoreToRankInfoVO;
import com.baizhou.data.constdefine.ConstDefine;
import com.baizhou.db.DBProxy;
import com.baizhou.dbtool.model.ExcelRowData;
import com.baizhou.dbtool.model.ExcelTableData;
import com.baizhou.dbtool.model.Param;

import java.io.File;

public class PhysicLimitationParserProxy implements IExcelParseProxy {
    @Override
    public void DoProxy(ExcelTableData table, File file, Param Param) {
        DBProxy.Getinstance().PhysicLimitationInfoService.deleteAll();
        System.out.println("开始解析 体检限报 目录");
        PhysicLimitationInfoVO vo = new PhysicLimitationInfoVO();

        for (int i = 0; i < table.DataRows.size(); i++) {
            ExcelRowData row = table.DataRows.get(i);


            String title1 = row.Cells.get(1).Data.trim(); // lvl_1 菜单
            String title2 = row.Cells.get(2).Data.trim(); // lvl_2 菜单
            String type = row.Cells.get(0).Data.trim(); // lvl_3类别
            String content = row.Cells.get(3).Data.trim(); // 详细内容


//            if (major1stClsStr.isEmpty()) continue; //空分数不记录
            vo.setTitle(title1);
            vo.setTitle2(title2);
            vo.setType(type);
            vo.setContent(content);

            DBProxy.Getinstance().PhysicLimitationInfoService.savePhysicLimitationInfo(vo);
            System.out.println("保存" + vo.getType());
            try {
                Thread.sleep(ConstDefine.InsertDBIntervalQuick);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("完成解析专业目录");
    }
}
