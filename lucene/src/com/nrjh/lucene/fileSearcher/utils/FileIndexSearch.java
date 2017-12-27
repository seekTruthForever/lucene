package com.nrjh.lucene.fileSearcher.utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.highlight.Formatter;
import org.apache.lucene.search.highlight.Fragmenter;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.Scorer;
import org.apache.lucene.search.highlight.SimpleFragmenter;
import org.apache.lucene.search.highlight.SimpleHTMLFormatter;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

import com.nrjh.lucene.utils.ikext.IKAnalyzer5x;
/**
 * 检索文件内容
 * @author huawei
 *
 */
public class FileIndexSearch {
	
    private Directory directory ;
    private Analyzer analyzer ;
    private static String indexPath=FileIndexSearch.class.getResource("/luceneIndex").getPath();
    /**
     * 带参数构造,参数用来指定索引文件目录
     * @param indexFilePath
     */
    public FileIndexSearch(String indexFilePath){
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
     * 默认构造,使用系统默认的路径作为索引
     */
    public FileIndexSearch(){
        this(indexPath);
    }
	
	/**
	 * 检索
	 * @param fields
	 * @param keyword
	 * @return
	 */
	public Map searchIndex(String[] fields,String keyword){
		Map rsltMap = new HashMap();
		try {
			if(!isExistIndexFile(indexPath)){
				FileIndexCreate fileIndexCreate = new FileIndexCreate(indexPath);
				fileIndexCreate.createIndex(Constant.getFileDirPath("dirPath"),true);
			}
			IndexReader indexReader = DirectoryReader.open(directory);
			IndexSearcher indexSearcher = new IndexSearcher(indexReader);
			//Query query = new TermQuery(new Term("content","教师"));
			 MultiFieldQueryParser queryParser =new MultiFieldQueryParser(fields,analyzer);
	         Query query = queryParser.parse(keyword);
			TopDocs topDocs = indexSearcher.search(query, 10);
			int count = topDocs.totalHits;
			//System.out.println("共检索出 "+count+" 条记录");
			rsltMap.put("totalHits", count);
			 //高亮显示
            /*  
                  创建高亮器,使搜索的结果高亮显示
                SimpleHTMLFormatter：用来控制你要加亮的关键字的高亮方式
                此类有2个构造方法
                1：SimpleHTMLFormatter()默认的构造方法.加亮方式：<B>关键字</B>
                2：SimpleHTMLFormatter(String preTag, String postTag).加亮方式：preTag关键字postTag
             */
            Formatter formatter = new SimpleHTMLFormatter("<font color='red'>","</font>");    
            /*
                 QueryScorer
                QueryScorer 是内置的计分器。计分器的工作首先是将片段排序。QueryScorer使用的项是从用户输入的查询中得到的；
                它会从原始输入的单词、词组和布尔查询中提取项，并且基于相应的加权因子（boost factor）给它们加权。
                为了便于QueryScoere使用，还必须对查询的原始形式进行重写。
                比如，带通配符查询、模糊查询、前缀查询以及范围查询 等，都被重写为BoolenaQuery中所使用的项。
                在将Query实例传递到QueryScorer之前，可以调用Query.rewrite (IndexReader)方法来重写Query对象 
             */
            Scorer fragmentScorer = new QueryScorer(query);
            Highlighter highlighter = new Highlighter(formatter,fragmentScorer);
            Fragmenter fragmenter = new SimpleFragmenter(100);
            /*    
                Highlighter利用Fragmenter将原始文本分割成多个片段。
                      内置的SimpleFragmenter将原始文本分割成相同大小的片段，片段默认的大小为100个字符。这个大小是可控制的。
             */
            highlighter.setTextFragmenter(fragmenter);
			ScoreDoc[] scoreDocs = topDocs.scoreDocs;
			List<Map> rsltList = new ArrayList<Map>();
			Document document = null;
			for(ScoreDoc scoreDoc : scoreDocs){
				int docId = scoreDoc.doc;
				document = indexSearcher.doc(docId);
				String title = document.get("title");
				String path = document.get("path");
				String content = document.get("content");
				String lighterTitle=null,lighterContent=null;
				if(null != fields && fields.length>0){
					//lighterPath = highlighter.getBestFragment(analyzer, "path", path);
					lighterTitle = highlighter.getBestFragment(analyzer, "title", title);
					lighterContent = highlighter.getBestFragment(analyzer, "content", content);
//					if(Arrays.binarySearch(fields, "path")>-1){
//		                	
//					}
//					if(Arrays.binarySearch(fields, "title")>-1){
//		                
//					}
//					if(Arrays.binarySearch(fields, "content")>-1){
//		                
//					}
				}
				
				if(null==lighterTitle){
                	lighterTitle = title;
                }
				if(null==lighterContent){
                	lighterContent = content;
                }
				Map map = new HashMap();
				map.put("docId", docId);
				map.put("title", lighterTitle);
				map.put("path", path);
				map.put("content", lighterContent+"...");
				rsltList.add(map);
				//System.out.println("docId:"+docId+", 标题:"+lighterTitle+"， 地址:"+lighterPath+"， 内容:"+lighterContent);
				
			}
			rsltMap.put("data", rsltList);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			return rsltMap;
		}
		
		
	}
	
	public Map getDetail(int docId){
		Map rsltMap = new HashMap();
		try {
			if(!isExistIndexFile(indexPath)){
				FileIndexCreate fileIndexCreate = new FileIndexCreate(indexPath);
				fileIndexCreate.createIndex(Constant.getFileDirPath("dirPath"),true);
			}
			IndexReader indexReader = DirectoryReader.open(directory);
			IndexSearcher indexSearcher = new IndexSearcher(indexReader);
			Document document = indexSearcher.doc(docId);
			String title = document.get("title");
			String path = document.get("path");
			String content = document.get("content");
			
			rsltMap.put("docId", docId);
			rsltMap.put("title", title);
			rsltMap.put("path", path);
			rsltMap.put("content", content);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			return rsltMap;
		}
		
		
	}
	
	/**
     * 判断是否已经存在索引文件
     * @param indexPath
     * @return
     */
    private  boolean isExistIndexFile(String indexPath) throws Exception{
        File file = new File(indexPath);
        if (!file.exists()) {
            file.mkdirs();
        }
        String indexSufix="/write.lock";
         //根据索引文件segments.gen是否存在判断是否是第一次创建索引   
        File indexFile=new File(indexPath+indexSufix);
        return indexFile.exists();
    }
	
}
