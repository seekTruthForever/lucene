package com.nrjh.lucene.utils.poiUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.poi.hslf.extractor.PowerPointExtractor;
import org.apache.poi.hslf.usermodel.HSLFSlideShow;
import org.apache.poi.sl.usermodel.Slide;
import org.apache.poi.sl.usermodel.SlideShow;

import com.sun.javafx.text.TextRun;

public class PPTTextUtil {
	  //直接抽取幻灯片的全部内容
    public static String readDoc1(InputStream is) throws IOException{
        PowerPointExtractor extractor=new PowerPointExtractor(is);
        return extractor.getText();
    }

//    //一张幻灯片一张幻灯片地读取
//    public static void readDoc2(InputStream is) throws IOException{
//        SlideShow ss=new HSLFSlideShow(is);
//        List<Slide> slides=ss.getSlides();
//        for(int i=0;i<slides.size();i++){
//            //读取一张幻灯片的标题
//            String title=slides.get(i).getTitle();
//            System.out.println("标题:"+title);
//            //读取一张幻灯片的内容(包括标题)
//            TextRun[] runs=slides.get(i)..getTextRuns();
//            for(int j=0;j
//                System.out.println(runs[j].getText());
//            }
//        }
//    }

    public static void main(String[] args){
        File file = new File("/home/orisun/2.ppt");
        try{
            FileInputStream fin=new FileInputStream(file);
            String cont=readDoc1(fin);
            System.out.println(cont);
            fin.close();
           // fin=new FileInputStream(file);
           // readDoc2(fin);
           // fin.close();
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
