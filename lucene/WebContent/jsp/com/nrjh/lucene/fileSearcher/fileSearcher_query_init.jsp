<%@ page contentType="text/html;charset=UTF-8" %>
<%@page import="java.util.Map"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<html>
<%
	Map rsltMap = (Map)request.getAttribute("rsltMap");
if(rsltMap==null) return;
	String totalHits = rsltMap.get("totalHits")==null?"0":rsltMap.get("totalHits").toString();
	List<Map> dataList = (List<Map>)(rsltMap.get("data")==null?new ArrayList():rsltMap.get("data"));
	String keyword = request.getParameter("keyword");
	keyword = keyword==null?"":keyword;
	
%>
<head>
<title>文件内容检索</title>
<style type="text/css">
	div.searchTool_div{margin:0 auto;width:60%;}
	div.all_news_div{margin:0 auto;width:60%;}
	div.totalHits_div{color:#333;font-size:12;margin-top:5px;margin-bottom:5px;}
	div.all_info_div{height:500px;overflow-y:auto;overflow-x:hidden;}
	div.info_div{margin-top:10px;width:100%;}
	div.info_div:nth-child(even){background:#FFFFFF;}
	div.info_div:nth-child(odd){background:#F0F0F0;}
	div.title_div{font-size:14px;font-weight:bold;}
	div.content_div{margin-top:5px;font-size:14px;line-height:20px;}
	.keyword_input{width:100%;}
	div.tools_div{width:100%;text-align:right;}
</style>
<script type="text/javascript">
	//搜索
	function searchIt(){
		document.forms[0].action="fileSearcher.so?method=search";
		document.forms[0].submit();
	}
	//明细
	function getDetail(docId){
		var url = "fileSearcher.so?method=detail&docId="+docId;
		window.showModelessDialog(url,'stockerThread','dialogWidth:800px;dialogHeight:555px;center:yes;help:no;titlebar:no');
	}
	//管理
	function manageIt(){
		var url = "fileSearcher.so?method=manage";
		window.showModalDialog(url,'stockerThread','dialogWidth:800px;dialogHeight:555px;center:yes;help:no;titlebar:no');
	}
</script>
</head>

<body>
<div class="tools_div">
<input type="button" name="manageBtn" value="管理索引" onclick="manageIt()"/>
</div>
<div class="searchTool_div">
<form action="fileSearcher.so?method=search" method="post">
<table width="100%">
<tr>
<td>
<input type="text" name="keyword" class="keyword_input" value='<%=keyword %>'/>
</td>
<td width="150" style="display:none;">
<input type="checkbox" name="fields" value="title" checked/><span>标题</span>
<input type="checkbox" name="fields" value="content" checked/><span>内容</span>
</td>
<td>
<input type="button" name="searchBtn" value="搜索一下" onclick="searchIt()"/>
</td>
</tr>
</table>
</form>
</div>
<div class="all_news_div">
<div class="totalHits_div"><span>共检索出 <%=totalHits %> 条记录</span></div>
<div class="all_info_div">
<%
	for(int i=0;i<dataList.size();i++){
		Map dataMap = dataList.get(i);
		String docId = dataMap.get("docId").toString();
		String title = dataMap.get("title").toString();
		String content = dataMap.get("content").toString();
		String path = dataMap.get("path").toString();
%>
	<div class="info_div">
		<input type="hidden" name="docId" value='<%=docId %>'/>
		<input type="hidden" name="path" value='<%=path %>'/>
		<div class="title_div"><a href="javascript:void(0);" onclick="getDetail(<%=docId %>)"><%=title %></a></div>
		<div class="content_div"><%=content %></div>
	</div>
<%
	}
%>
</div>

</div>
</body>
</html>