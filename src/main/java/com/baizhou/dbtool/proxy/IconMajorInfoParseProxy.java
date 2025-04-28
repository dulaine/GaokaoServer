package com.baizhou.dbtool.proxy;

import com.baizhou.common.CommonUtil;
import com.baizhou.core.model.vo.IconMajorInfoVO;
import com.baizhou.core.model.vo.MajorInfoY1VO;
import com.baizhou.data.constdefine.ConstDefine;
import com.baizhou.db.DBProxy;
import com.baizhou.dbtool.model.ExcelRowData;
import com.baizhou.dbtool.model.ExcelTableData;
import com.baizhou.dbtool.model.Param;
import com.baizhou.util.GameUtil;
import com.msg.IconMajorInfo;
import com.msg.MajorInfoYear;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IconMajorInfoParseProxy implements IExcelParseProxy {
    @Override
    public void DoProxy(ExcelTableData table, File file, Param Param) throws InterruptedException {

        List<IconMajorInfo> protoList = ConvertToProtoList(table);

        Thread.sleep(ConstDefine.InsertDBInterval);

        for (int i = 0; i < protoList.size(); i++) {
            IconMajorInfo proto = protoList.get(i);

            //保存
            IconMajorInfoVO majorInfoVO = IconMajorInfoVO.ConvertFromDTO(proto);
            majorInfoVO.setId(null);
            DBProxy.Getinstance().IconMajorInfoService.saveIconMajorInfo(majorInfoVO);

            System.out.println("保存 图标数据" + majorInfoVO.getMajorName());
            Thread.sleep(ConstDefine.InsertDBIntervalQuick);
        }
    }

    private List<IconMajorInfo> ConvertToProtoList(ExcelTableData table) {

        List<IconMajorInfo> iconMajorInfos = new ArrayList<>();

        for (int i = 0; i < table.DataRows.size(); i++) {
            ExcelRowData row = table.DataRows.get(i);

            com.msg.IconMajorInfo.Builder builder = Convert(row);
            iconMajorInfos.add(builder.build());
        }


        return iconMajorInfos;
    }

    private com.msg.IconMajorInfo.Builder Convert(ExcelRowData row) {

        com.msg.IconMajorInfo.Builder info = com.msg.IconMajorInfo.newBuilder();

        info.setId(0);
        info.setUniMajorCode(row.Cells.get(0).Data.trim());
        info.setSchoolName(row.Cells.get(1).Data.trim());
        info.setMajorCode(row.Cells.get(2).Data.trim());
        info.setMajorName(row.Cells.get(3).Data.trim());


        String cls1 = row.Cells.get(4).Data.trim();
        cls1 = GameUtil.ReplaceSpecialChar(cls1);
        boolean hasValid = false;
        for (int j = 0; j < ConstDefine.ClassLvl.length; j++) {
            if (cls1.equals(ConstDefine.ClassLvl[j])) {
                hasValid = true;
                break;
            }
        }
        info.setCls1(hasValid ? cls1 : "");


        info.setCls2(row.Cells.get(5).Data.trim());
        info.setCls3(row.Cells.get(6).Data.trim());
        info.setCls4(row.Cells.get(7).Data.trim());
        info.setCls5(row.Cells.get(8).Data.trim());
        info.setCls6(row.Cells.get(9).Data.trim());
        info.setCls7(row.Cells.get(10).Data.trim());
        info.setCls8(row.Cells.get(11).Data.trim());

        return info;
    }

}
