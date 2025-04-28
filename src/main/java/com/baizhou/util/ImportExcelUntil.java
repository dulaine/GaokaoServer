package com.baizhou.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;



public class ImportExcelUntil {

    public static List<List<String>> readExcel(File excelFile) {
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
        List<List<String>> cells = new ArrayList<List<String>>();

        try {
            //同时支持Excel 2003、2007
            FileInputStream is = new FileInputStream(excelFile); //文件流
            Workbook workbook = WorkbookFactory.create(is); //这种方式 Excel 2003/2007/2010 都是可以处理的
            int sheetCount = workbook.getNumberOfSheets();  //Sheet的数量

                Sheet sheet = workbook.getSheetAt(0);
                int rowCount = sheet.getPhysicalNumberOfRows(); //获取总行数
                //遍历每一行 跳过第一行
                for (int r = 1; r < rowCount; r++) {
                    Row row = sheet.getRow(r);
                    int cellCount = row.getPhysicalNumberOfCells(); //获取总列数
                    //遍历每一列
                    List<String> cellList = new ArrayList<String>();
                    for (int c = 0; c < cellCount; c++) {
                        Cell cell = row.getCell(c);

                        String cellValue = "";
                        if( cell != null){
                            CellType cellType = cell.getCellTypeEnum();
                            switch (cellType) {
                                case STRING: //文本
                                    cellValue = cell.getStringCellValue();
                                    break;
                                case NUMERIC: //数字、日期
                                    if (DateUtil.isCellDateFormatted(cell)) {
                                        cellValue = fmt.format(cell.getDateCellValue()); //日期型
                                    } else {
                                        cellValue = String.valueOf(cell.getNumericCellValue()); //数字
                                    }
                                    break;
                                case BOOLEAN: //布尔型
                                    cellValue = String.valueOf(cell.getBooleanCellValue());
                                    break;
                                case _NONE: //空白
                                    cellValue = cell.getStringCellValue();
                                    break;
                            }
                        }
                        cellList.add(cellValue);
                    }
                    cells.add(cellList);
                }
               System.out.println("cells count is "  + cells.size());

        } catch (InvalidFormatException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return cells;
    }
    }