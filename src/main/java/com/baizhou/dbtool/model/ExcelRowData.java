package com.baizhou.dbtool.model;

import com.baizhou.dbtool.model.ExcelCellData;

import java.util.List;

public class ExcelRowData {
    public List<ExcelCellData> Cells;

    public ExcelRowData(List<ExcelCellData> cells){
        Cells = cells;
    }
}
