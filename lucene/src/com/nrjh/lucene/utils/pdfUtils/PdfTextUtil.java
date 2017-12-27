package com.nrjh.lucene.utils.pdfUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
/**
 * pdf文字工具类，获取文字内容
 * @author huawei
 * 依赖pdfbox和fontbox两个jar包
 */
public class PdfTextUtil {
	/**
	 * 获取pdf文档中的文字
	 * @param filePath 文件路径
	 * @return
	 */
	public static String getText(String filePath){
		PDDocument pdDocument;
		PDFTextStripper pdfTextStripper;
		String text = "";
		try {
			pdDocument = PDDocument.load(new File(filePath));
			int pageCount = pdDocument.getNumberOfPages();
			pdfTextStripper = new PDFTextStripper();
			pdfTextStripper.setStartPage(0);
			pdfTextStripper.setEndPage(pageCount);
			text = pdfTextStripper.getText(pdDocument);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return text;
		
	}
	/**
	 * 获取pdf中的文字
	 * @param inputStream 文件输入流
	 * @return
	 */
	public static String getText(InputStream inputStream){
		PDDocument pdDocument;
		PDFTextStripper pdfTextStripper;
		String text = "";
		try {
			pdDocument = PDDocument.load(inputStream);
			int pageCount = pdDocument.getNumberOfPages();
			pdfTextStripper = new PDFTextStripper();
			pdfTextStripper.setStartPage(0);
			pdfTextStripper.setEndPage(pageCount);
			text = pdfTextStripper.getText(pdDocument);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return text;
	}
	/**
	 * 将pdf文件中的文字写入另一个文件中
	 * @param inputStream pdf文件输入流
	 * @param outputStream 文件输出流
	 */
	public static void write2TextFile(InputStream inputStream,OutputStream outputStream){
		PDDocument pdDocument;
		PDFTextStripper pdfTextStripper;
		Writer writer = null;
		try {
			pdDocument = PDDocument.load(inputStream);
			int pageCount = pdDocument.getNumberOfPages();
			pdfTextStripper = new PDFTextStripper();
			pdfTextStripper.setStartPage(0);
			pdfTextStripper.setEndPage(pageCount);
			writer = new OutputStreamWriter(outputStream);
			pdfTextStripper.writeText(pdDocument, writer);
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
				try {
					if(writer != null){
						writer.flush();
						writer.close();
					}
				
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
	}
	/**
	 * 将pdf中的文字写入另一个文件中
	 * @param inFilePath pdf输入路径
	 * @param outFilePath 输出文件的路径
	 */
	public static void write2TextFile(String inFilePath,String outFilePath){
		PDDocument pdDocument;
		PDFTextStripper pdfTextStripper;
		Writer writer = null;
		OutputStream outputStream = null;
		try {
			pdDocument = PDDocument.load(new File(inFilePath));
			int pageCount = pdDocument.getNumberOfPages();
			pdfTextStripper = new PDFTextStripper();
			pdfTextStripper.setStartPage(0);
			pdfTextStripper.setEndPage(pageCount);
			outputStream = new FileOutputStream(new File(outFilePath));
			writer = new OutputStreamWriter(outputStream);
			pdfTextStripper.writeText(pdDocument, writer);
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
				try {
					if(writer != null){
						writer.flush();
						writer.close();
					}
					if(outputStream != null){
						outputStream.close();
					}
					
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
	}
}
