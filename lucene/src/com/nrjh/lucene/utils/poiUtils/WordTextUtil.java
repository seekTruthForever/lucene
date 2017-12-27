package com.nrjh.lucene.utils.poiUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.hwpf.usermodel.CharacterRun;
import org.apache.poi.hwpf.usermodel.Paragraph;
import org.apache.poi.hwpf.usermodel.Range;
import org.apache.poi.hwpf.usermodel.Section;

public class WordTextUtil {
	public static String getWordText(InputStream inputStream){
		WordExtractor extractor;
		String text = "";
		try {
			extractor = new WordExtractor(inputStream);
			text = extractor.getText();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return text;
		
	}
	
	  //分章节Section、段落Paragraph、字符串CharacterRun抽取
    public static String getWordText2(InputStream is) {
    	StringBuffer sb = new StringBuffer();
        HWPFDocument doc;
		try {
			doc = new HWPFDocument(is);
			 Range r=doc.getRange();
		        for(int x=0;x<r.numSections();x++){
		            Section s=r.getSection(x);
		            for(int y=0;y<s.numParagraphs();y++){
		                Paragraph p=s.getParagraph(y);
		                for(int z=0;z<p.numCharacterRuns();z++){
		                    CharacterRun run=p.getCharacterRun(z);
		                    String text=run.text();
		                    sb.append(text);
		                }
		            }
		        }
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
       
        return sb.toString();
    }
    
    public static void main(String[] args) {
        File file = new File("F:/PDF电子书/[www.java1234.com]java时间操作函数汇总.doc");
        try {
            FileInputStream fin = new FileInputStream(file);
            String cont = getWordText(fin);
            System.out.println(cont);
            fin.close();
            fin = new FileInputStream(file);
            //readDoc2(fin);
            fin.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
