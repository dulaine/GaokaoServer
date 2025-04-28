package com.baizhou.dbtool.parser;

import com.baizhou.dbtool.model.ExcelTableData;
import org.apache.poi.ss.usermodel.Sheet;

import java.io.IOException;

public interface ExcelParserInterface {
    public ExcelTableData DoParse(Sheet sheet, Integer startRow) throws IOException;
}
