package com.nrjh.lucene.fileSearcher.action;

import java.io.IOException;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.nrjh.lucene.constant.Constant;
import com.nrjh.lucene.fileSearcher.utils.FileIndexCreate;
import com.nrjh.lucene.fileSearcher.utils.FileIndexSearch;
/**
 * 文件检索servlet
 * @author huawei
 *
 */
public class FileSearcherAction extends HttpServlet {
	/**
	 * 处理get请求
	 */
	protected void doGet(HttpServletRequest req,HttpServletResponse rep) throws ServletException, IOException{
		String uri = req.getRequestURI();
		uri=uri==null?"":uri;
		String method = req.getParameter("method");
		if(method==null || method.isEmpty()) {
			method="search";
		}
		if("search".equals(method)){
			String[] fields = req.getParameterValues("fields");
			String keyword = req.getParameter("keyword");
			FileIndexSearch fileIndexSearch = new FileIndexSearch();
			Map rsltMap = fileIndexSearch.searchIndex(fields, keyword);
			req.setAttribute("rsltMap", rsltMap);
			RequestDispatcher dispatcher = req.getRequestDispatcher(Constant.getURL("fileSearcherAction.fileSearcher"));
			dispatcher.forward(req, rep);
		}else if("detail".equals(method)){
			String docId = req.getParameter("docId");
			FileIndexSearch fileIndexSearch = new FileIndexSearch();
			Map rsltMap = fileIndexSearch.getDetail(Integer.valueOf(docId));
			req.setAttribute("rsltMap", rsltMap);
			RequestDispatcher dispatcher = req.getRequestDispatcher(Constant.getURL("fileSearcherAction.fileDetail"));
			dispatcher.forward(req, rep);
		}else if("manage".equals(method)){
			RequestDispatcher dispatcher = req.getRequestDispatcher(Constant.getURL("fileSearcherAction.indexManage"));
			dispatcher.forward(req, rep);
		}else if("reGenIndex".equals(method)){
			FileIndexCreate fileIndexCreate = new FileIndexCreate();
			fileIndexCreate.createIndex(Constant.getFileDirPath("dirPath"),true);
		}
	}
	/**
	 * 处理post请求
	 */
	protected void doPost(HttpServletRequest req,HttpServletResponse rep) throws ServletException, IOException{
		req.setCharacterEncoding("UTF-8");
		String uri = req.getRequestURI();
		uri=uri==null?"":uri;
		String method = req.getParameter("method");
		if("search".equals(method)){
			String[] fields = req.getParameterValues("fields");
			String keyword = req.getParameter("keyword");
			FileIndexSearch fileIndexSearch = new FileIndexSearch();
			Map rsltMap = fileIndexSearch.searchIndex(fields, keyword);
			req.setAttribute("rsltMap", rsltMap);
			RequestDispatcher dispatcher = req.getRequestDispatcher(Constant.getURL("fileSearcherAction.fileSearcher"));
			dispatcher.forward(req, rep);
		}
	}
	
}
