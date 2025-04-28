package com.baizhou.dbtool.model;

public class ExcelCellData {
    public String Data;
    public Boolean NeedColored;

    public ExcelCellData(String data, Boolean needColored){
        this.Data = data;
        this.NeedColored = needColored;
    }
}
