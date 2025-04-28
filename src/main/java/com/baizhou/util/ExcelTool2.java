package com.baizhou.util;

import com.monitorjbl.xlsx.StreamingReader;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class ExcelTool2 {

    public static Sheet ReadBigExcel(File file) {
        FileInputStream is = null;
        try {
            is = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Workbook workbook = StreamingReader.builder().rowCacheSize(200).bufferSize(1024).open(is);

        return workbook.getSheetAt(0);
    }

    public static String ReadCellValueByString(Row row ,int index){
        String value = "";
        if(  row.getCell(index) != null){
            value = row.getCell(index).getStringCellValue();
        }
        return  value;
    }

    public static String ReadCellValueByString(Row row ,int index, String defalutStr){
        String value = defalutStr;
        if(  row.getCell(index) != null){
            value = row.getCell(index).getStringCellValue();
        }
        return  value;
    }

    /**
     *  这里需要处理下有的数字列会带一个特殊符号，根据不同项目不同处理TODO
     * @param row
     * @param index
     * @return
     */
    public static Long ReadCellValueByNumber(Row row ,int index, Long defaultValue){
        String value = "0";
        if(  row.getCell(index) != null ){
            value = row.getCell(index).getStringCellValue();
            if(value == null || value.isEmpty() || value.equals("-") ||value.equals("一")) {
                //value = "0";
                return defaultValue;
            }
        }

        Long ret = Long.parseLong(value);
        return  ret;
    }

}
