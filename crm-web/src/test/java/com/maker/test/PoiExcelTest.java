package com.maker.test;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PoiExcelTest {
    private static final String PATH_NAME="D:"+File.separator+"testExcel.xlsx";
    private static final Logger LOGGER= LoggerFactory.getLogger(PoiExcelTest.class);
    @Test
    public void createExcelTest() throws Exception{
        //创建Excel文件
        Workbook workbook= new HSSFWorkbook();
        //创建sheet
       Sheet sheet= workbook.createSheet("测试sheet");
      //创建行
        Row row=sheet.createRow(0);//参数代表行号，从0开始
        //在此行创建列
        Cell cell=row.createCell(0);//参数代表第几列，从0开始
        cell.setCellValue("姓名");
        //此处cell虽然指向了另一个单元格引用，但是在poi内部，之前创建的所有元素引用都有指向，一直是存在内存中的
        cell=row.createCell(1);
        cell.setCellValue("年龄");
        cell=row.createCell(2);
        cell.setCellValue("生日");
        for(int i=1;i<10;i++){
            row=sheet.createRow(i);
            cell=row.createCell(0);
            cell.setCellValue("Name"+i);
            cell=row.createCell(1);
            cell.setCellValue(20+i);//传什么类型的变量，就会生成什么类型的单元格格式
            cell=row.createCell(2);
            cell.setCellValue(new Date());

        }

       OutputStream outputStream=new FileOutputStream(PATH_NAME);
       //OutputStream outputStream= Files.newOutputStream(  );

        workbook.write(outputStream);

        outputStream.close();
        workbook.close();
        LOGGER.info("Create OK!");


    }
}
