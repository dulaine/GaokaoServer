package com.baizhou.dbtool.parser;

import com.baizhou.dbtool.model.ExcelRowData;
import com.baizhou.dbtool.model.ExcelTableData;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ExcelParser extends BaseParser {
    @Override
    public ExcelTableData DoParse(Sheet sheet, Integer startRow) throws IOException {
        int firstRowIndex = sheet.getFirstRowNum();
        int lastRowIndex = sheet.getLastRowNum();
//        this.SheetName = sheet.getSheetName();

        //获取第一行
        Row title = sheet.getRow(0);
        ExcelRowData titleRow = this.parseTitleRow(title);
        int validColumNum = titleRow.Cells.size();

        List<ExcelRowData> dataList = new ArrayList<>();
//        for (int i = firstRowIndex + 1; i <= lastRowIndex; i++) {
        for (int i = startRow; i <= lastRowIndex; i++) {
            Row dataRow = sheet.getRow(i);
            if (dataRow == null) continue;//空行
            ExcelRowData data = parseDataRow(dataRow, validColumNum);
//            if(data.Cells.get(0).Data.equals("")) continue;//行首为空,不是数据行
            dataList.add(data);
        }

        //返回数据
        ExcelTableData table = new ExcelTableData();
        table.ColumNum = validColumNum;
        table.DataRows = dataList;
        table.VariableNameRow = titleRow;
//        table.VarialeTypeRow = typeRow;

//        this.table = table;
//
//
//        //遍历, 找到 "专业名" 列
//        for (int i = 0; i < titleRow.Cells.size(); i++) {
//            if (titleRow.Cells.get(i).Data.equals(MajorName)) {
//                this.MajorRowIndex = i;
//                break;
//            }
//        }
        //保存所有专业名

        return table;
    }
}
