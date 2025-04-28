package com.baizhou.dbtool.proxy;

import com.baizhou.core.model.vo.ScoreToRankInfoVO;
import com.baizhou.data.constdefine.ConstDefine;
import com.baizhou.db.DBProxy;
import com.baizhou.dbtool.model.ExcelRowData;
import com.baizhou.dbtool.model.ExcelTableData;
import com.baizhou.dbtool.model.Param;
import org.apache.poi.ss.usermodel.Sheet;

import java.io.File;

public class YFYDExcelParserProxy implements IExcelParseProxy {
    @Override
    public void DoProxy(ExcelTableData table, File file, Param Param) {

        DBProxy.Getinstance().ScoreToRankInfoService.deleteAll();

        System.out.println("开始解析一分一段");

        ScoreToRankInfoVO vo = new ScoreToRankInfoVO();

        for (int i = 0; i < table.DataRows.size(); i++) {
            ExcelRowData row = table.DataRows.get(i);

            String scoreStr = row.Cells.get(0).Data.trim(); //分数
            String countStr = row.Cells.get(1).Data.trim(); //人数
            String accumStr = row.Cells.get(2).Data.trim(); //累计
            String rankStr = row.Cells.get(3).Data.trim(); //位次


            if (scoreStr.isEmpty()) continue; //空分数不记录
            Integer score = (int)Float.parseFloat(scoreStr);
            Integer count = countStr.isEmpty() ? 0 : (int)Float.parseFloat(countStr);
            Integer accumulation = accumStr.isEmpty() ? 0 : (int)Float.parseFloat(accumStr);
            Integer rank = rankStr.isEmpty() ? 0 : (int)Float.parseFloat(rankStr);

            vo.setScore(score);
            vo.setAccumulation(accumulation);
            vo.setCount(count);
            vo.setRank(rank);

            DBProxy.Getinstance().ScoreToRankInfoService.saveScoreToRankInfo(vo);
            System.out.println("保存"+scoreStr);
            try {
                Thread.sleep(ConstDefine.InsertDBInterval);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("完成解析一分一段");
    }
}
