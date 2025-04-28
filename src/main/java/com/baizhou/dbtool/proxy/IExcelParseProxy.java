package com.baizhou.dbtool.proxy;

import com.baizhou.dbtool.model.ExcelTableData;
import com.baizhou.dbtool.model.Param;
import org.apache.poi.ss.usermodel.Sheet;

import java.io.File;

public interface IExcelParseProxy {
    public void DoProxy(ExcelTableData table, File file, Param Param) throws InterruptedException;
}
