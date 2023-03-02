package com.maker.crm.commons.utils;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;

public class POIUtils {
    public static String getValue(Cell cell){
        System.err.println(cell);
        String value="";
        if(cell.getCellType()==CellType.BOOLEAN){
            value+=cell.getBooleanCellValue();
        } else if (cell.getCellType()==CellType.NUMERIC) {
            value+=cell.getNumericCellValue();
        } else if (cell.getCellType()==CellType.STRING) {
            value+=cell.getStringCellValue();
        }
        return value;

    }
}
