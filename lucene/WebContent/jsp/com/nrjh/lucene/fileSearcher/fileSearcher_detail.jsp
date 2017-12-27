<%@ page contentType="text/html;charset=UTF-8" %>
<%@page import="java.util.Map"%>
<html>
<%
	Map rsltMap = (Map)request.getAttribute("rsltMap");
if(rsltMap==null) return;
String docId = rsltMap.get("docId").toString();
String title = rsltMap.get("title").toString();
String content = rsltMap.get("content").toString();
String path = rsltMap.get("path").toString();
%>
<head>
<title>文件内容</title>
<style type="text/css">
	div.doc_div{margin:0 auto;width:90%;}
	div.title_div{font-size:14;font-weight:bold; text-align:center;}
	div.content_div{font-size:14;margin-top:5px;}
</style>

</head>

<body>
	<div class="doc_div">
		<input type="hidden" name="docId" value='<%=docId %>'/>
		<input type="hidden" name="path" value='<%=path %>'/>
		<div class="title_div"><%=title %></div>
		<div class="content_div"><%=content %></div>
	</div>
</body>
</html>