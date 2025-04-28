package com.baizhou.dbtool.model;

public class UniMajorParam extends Param {
    public Integer SheetIdx;
    public Integer NewVersion;
    public ExcelTableData iconTable;
    public String Year;

    public  UniMajorParam(Integer idx,  Integer vers,ExcelTableData iconTable, String year){
        this.SheetIdx = idx;
        this.NewVersion = vers;
        this.iconTable = iconTable;
        this.Year = year;
    }
}
