package com.baizhou.dbtool.parser;

import com.baizhou.dbtool.model.ExcelCellData;
import com.baizhou.dbtool.model.ExcelRowData;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public abstract class BaseParser implements ExcelParserInterface {

    protected ExcelRowData parseTitleRow(Row row) throws IOException {
        //统计一行的合法格子, 除了备注
        List<ExcelCellData> cells = new ArrayList<>();
        int rowCount = row.getLastCellNum() -  row.getFirstCellNum() + 1;
        for (int i = 0; i < rowCount; i++) {
            String cellData = this.getCellVData(row.getCell(i));
            //遇到空格或备注停止
            if(cellData == null || cellData.equals("")) break;
            //添加cell
            ExcelCellData cell = new ExcelCellData(cellData, false);
            cells.add(cell);
        }
        ExcelRowData rowData = new ExcelRowData(cells);
        return rowData;
    }


    protected ExcelRowData parseDataRow(Row row, Integer columNum) throws IOException {
        //统计一行的合法格子, 除了备注
        List<ExcelCellData> cells = new ArrayList<>();
        for (int i = 0; i < columNum; i++) {
            String cellData = this.getCellVData(row.getCell(i));
            //添加cell
            ExcelCellData cell = new ExcelCellData(cellData, false);
            cells.add(cell);
        }
        ExcelRowData rowData = new ExcelRowData(cells);
        return rowData;
    }
    /**
     * 获取每个单元格的数据
     * @param cell 单元格对象
     * @return
     * @throws IOException
     */
    protected String getCellVData(Cell cell) throws IOException{

        // 空白或空
        if (cell == null || cell.getCellType()==Cell.CELL_TYPE_BLANK ) {
            return "";
        }

        // 0. 数字 类型
        if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
            if (HSSFDateUtil.isCellDateFormatted(cell)) {
                Date date = cell.getDateCellValue();
                DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                return df.format(date);
            }
            String val = cell.getNumericCellValue()+"";
            val = val.toUpperCase();
            if (val.contains("E")) {
                val = val.split("E")[0].replace(".", "");
            }
            return val;
        }

        // 1. String类型
        if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
            String val = cell.getStringCellValue();
            if (val == null || val.trim().length()==0) {
                return "";
            }
            return val.trim();
        }

        // 2. 公式 CELL_TYPE_FORMULA
        if (cell.getCellType() == Cell.CELL_TYPE_FORMULA) {
             return "";//cell.getStringCellValue();
        }

        // 4. 布尔值 CELL_TYPE_BOOLEAN
        if (cell.getCellType() == Cell.CELL_TYPE_BOOLEAN) {
            return cell.getBooleanCellValue()+"";
        }

        return "";
    }


}
