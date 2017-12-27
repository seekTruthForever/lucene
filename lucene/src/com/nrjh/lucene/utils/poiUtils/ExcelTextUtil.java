package com.nrjh.lucene.utils.poiUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

import org.apache.poi.hssf.extractor.ExcelExtractor;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

public class ExcelTextUtil {
	  //直接读取Excel的全部内容
    public static String getExcelText(InputStream is)throws IOException{
        HSSFWorkbook wb=new HSSFWorkbook(new POIFSFileSystem(is));
        ExcelExtractor extractor=new ExcelExtractor(wb);
        extractor.setFormulasNotResults(false);
        extractor.setIncludeSheetNames(true);
        return extractor.getText();
    }

    //读取时细化到Sheet、行甚至单元格
    public static double getAvg(InputStream is)throws IOException{
        HSSFWorkbook wb=new HSSFWorkbook(new POIFSFileSystem(is));
        //获取第一张sheet
        HSSFSheet sheet=wb.getSheetAt(0);
        double molecule=0.0;
        double denominator=0.0;
        //按行遍历sheet
        Iterator riter=sheet.rowIterator();
        while(riter.hasNext()){
            HSSFRow row=(HSSFRow)riter.next();
            HSSFCell cell1=row.getCell(4);
            HSSFCell cell2=row.getCell(4);
            if(cell1.getCellType()!=HSSFCell.CELL_TYPE_NUMERIC){
                System.err.println("数字类型错误！");
                System.exit(-2);
            }
            if(cell2.getCellType()!=HSSFCell.CELL_TYPE_NUMERIC){
                System.err.println("数字类型错误！");
                System.exit(-2);
            }
            denominator+=Double.parseDouble(cell2.toString().trim());
            molecule+=Double.parseDouble(cell2.toString().trim())*Float.parseFloat(cell1.toString().trim());
        }
        return molecule/denominator;
    }

    public static void main(String[] args){
        File file = new File("F:/项目资料/mrs相关/现场检定调研201504/参考资料-201504/计量中心基础数据明细表.xls");
        try{
            FileInputStream fin=new FileInputStream(file);
            String cont=getExcelText(fin);
            System.out.println(cont);
            fin.close();
           // fin=new FileInputStream(file);
            //System.out.println("加权平均分"+getAvg(fin));
            //fin.close();
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
