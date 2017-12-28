package com.nrjh.lucene.fileSearcher.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.TextField;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

import com.nrjh.lucene.constant.Constant;
import com.nrjh.lucene.utils.common.HtmlUtils;
import com.nrjh.lucene.utils.ikext.IKAnalyzer5x;
import com.nrjh.lucene.utils.unicode.UnicodeReader;
/**
 * 创建文件索引
 * @author huawei
 *
 */
public class FileIndexCreate {
	
    private Directory directory ;
    private Analyzer analyzer ;
    private static String indexPath=Constant.luceneIndexPath;
    /**
     * 创建文件索引类的构造函数
     * @param indexFilePath
     */
    public FileIndexCreate(String indexFilePath){
        try {
        	indexPath = indexFilePath;
            directory = FSDirectory.open(new File(indexFilePath).toPath());
            analyzer = new IKAnalyzer5x(true);
            //analyzer = new StandardAnalyzer();
            analyzer.setVersion(Version.LATEST);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * 文件索引创建类的构造函数
     */
    public FileIndexCreate(){
        this(indexPath);
    }
    /**
     * 生成文件索引
     * @param dirPath 文件目录
     * @param initFlag 是否重新初始化索引
     */
	public void createIndex(String dirPath,boolean initFlag){
		IndexWriter indexWriter = null;
		try {
			// 文件索引写入配置
			IndexWriterConfig indexWriterConfig = new IndexWriterConfig(analyzer);
			indexWriterConfig.setOpenMode(initFlag?OpenMode.CREATE:OpenMode.CREATE_OR_APPEND);//OpenMode.CREATE_OR_APPEND
			// 文件索引写入
			indexWriter = new IndexWriter(directory,indexWriterConfig);
			List<File> files = getFileList(dirPath, new ArrayList<File>());
			
			for(int i=0;i<files.size();i++){
				Document document = new Document();
				File file = files.get(i);
				InputStream fis = new FileInputStream(file);
				BufferedReader br = new BufferedReader(new UnicodeReader(fis, Charset.defaultCharset().name()));
				StringBuffer rsltStr = new StringBuffer();
				String subStr = null;
				String title = "";
				int j=0;
				   while ((subStr=br.readLine())!=null){
					   if(StringUtils.isNotBlank(subStr)){
						   subStr = HtmlUtils.htmlEncode(subStr);
						   if(j==0){
							   title = subStr;
						   }
						   j++;
						   rsltStr.append(subStr).append("<br>");
					   }
				   }
	            String content = rsltStr.toString();
				   document.add(new TextField("title",title.substring(0, title.length()>30?30:title.length()),Store.YES));
				   document.add(new TextField("content",content,Store.YES));
				   document.add(new TextField("path",file.getPath(),Store.YES));
				   indexWriter.addDocument(document);
				   br.close();
				   fis.close();
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			if(indexWriter != null){
				try {
					indexWriter.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	/**
	 * 获取文件列表
	 * @param strPath
	 * @param filelist
	 * @return
	 */
	public static List<File> getFileList(String strPath,List<File> filelist) {
        File dir = new File(strPath);
        File[] files = dir.listFiles(); // 获取目录下的所有文件
        if (files != null) {
            for (int i = 0; i < files.length; i++) {
                String fileName = files[i].getName();
                if (files[i].isDirectory()) { // 如果文件是目录，继续迭代
                    getFileList(files[i].getAbsolutePath(),filelist); 
                } else if (fileName.endsWith("txt")) { //处理txt格式的文件
                    filelist.add(files[i]);
                }
            }

        }
        return filelist;
    }
	
}
