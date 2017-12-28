package com.nrjh.lucene.fileSearcher.action;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.nrjh.lucene.constant.Constant;
import com.nrjh.lucene.fileSearcher.utils.FileIndexCreate;
import com.nrjh.lucene.fileSearcher.utils.FileIndexSearch;
/**
 * 文件检索servlet
 * @author huawei
 *
 */
@Controller
@RequestMapping("/fileSearcher")
public class FileSearcherController {
	@RequestMapping(value="/search.do")
	public ModelAndView search(HttpServletRequest req,HttpServletResponse rep){
		String[] fields = req.getParameterValues("fields");
		String keyword = req.getParameter("keyword");
		FileIndexSearch fileIndexSearch = new FileIndexSearch();
		Map rsltMap = fileIndexSearch.searchIndex(fields, keyword);
		req.setAttribute("rsltMap", rsltMap);
		return new ModelAndView(Constant.getURL("fileSearcherAction.fileSearcher"));
	}
	@RequestMapping("/detail.do")
	public ModelAndView detail(HttpServletRequest req,HttpServletResponse rep){
		String docId = req.getParameter("docId");
		FileIndexSearch fileIndexSearch = new FileIndexSearch();
		Map rsltMap = fileIndexSearch.getDetail(Integer.valueOf(docId));
		req.setAttribute("rsltMap", rsltMap);
		return new ModelAndView(Constant.getURL("fileSearcherAction.fileDetail"));
	}
	@RequestMapping("/manage.do")
	public ModelAndView manage(HttpServletRequest req,HttpServletResponse rep){
		
		return new ModelAndView(Constant.getURL("fileSearcherAction.indexManage"));
	}
	@RequestMapping("/reGenIndex.do")
	public void reGenIndex(HttpServletRequest req,HttpServletResponse rep){
		FileIndexCreate fileIndexCreate = new FileIndexCreate();
		fileIndexCreate.createIndex(Constant.getFileDirPath("dirPath"),true);
	}
	
	
}
