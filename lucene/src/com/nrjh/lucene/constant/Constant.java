package com.nrjh.lucene.constant;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.nrjh.lucene.fileSearcher.utils.PropOptUtil;

/**
 * 公共常量类
 * @author huawei
 *
 */
public class Constant {
	/**
	 * 索引存放目录
	 */
	public static final String luceneIndexPath=((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest().getSession().getServletContext().getRealPath("/luceneIndex");
	/**
	 * 文件目录配置文件地址ַ
	 */
	public static final String FILE_DIR_CONF_PROP_PATH = "/com/nrjh/lucene/fileSearcher/conf/fileDirConf.properties";
	/**
	 * url列表配置文件地址ַ
	 */
	public static final String URL_LIST_PROP_PATH = "/com/nrjh/lucene/fileSearcher/conf/urlListConf.properties";
	/**
	 * 获取url
	 * @param urlFlag
	 * @return
	 */
	public static String getURL(String urlFlag){
		String url = PropOptUtil.getProperties(URL_LIST_PROP_PATH, urlFlag);
		if(url==null || "".equals(url)){
			url = PropOptUtil.getProperties(URL_LIST_PROP_PATH, "__default");
		}
		return url;
	}
	/**
	 * 获取文件目录路径ַ
	 * @param key
	 * @return
	 */
	public static String getFileDirPath(String key){
		String value = PropOptUtil.getProperties(FILE_DIR_CONF_PROP_PATH, key);
		if(value==null || "".equals(value)){
			value = "D:/新闻源/";
			PropOptUtil.createProperties(FILE_DIR_CONF_PROP_PATH, key, value);
		}
		return value;
	}
}
