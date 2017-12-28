<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8"/>

<title>文件索引管理</title>
<link rel="stylesheet" style="text/css" href="skins/js/plupload/jquery.plupload.queue/css/jquery.plupload.queue.css"/>
<script type="text/javascript" src="skins/js/jquery.min.js"></script>
<script type="text/javascript" src="skins/js/plupload/plupload.full.min.js"></script>
<script type="text/javascript" src="skins/js/plupload/jquery.plupload.queue/jquery.plupload.queue.js"></script>
<script type="text/javascript" src="skins/js/plupload/i18n/zh_CN.js"></script>


</head>
<body style="font: 13px Verdana; background: #eee; color: #333">

<h1>上传文件</h1>

<p>请上传需要索引的文件：</p>

<div id="uploader">
 <p>Your browser doesn't have Flash, Silverlight or HTML5 support.</p>
</div>
<button id="toStop">暂停</button>
<button id="toStart">继续</button>
<button id="reGenIndex">重新生成索引</button>

<br />
<pre id="console"></pre>


<script type="text/javascript">
// Custom example logic

 $(function() {
 
  // Initialize the widget when the DOM is ready
 var uploader = $("#uploader").pluploadQueue({
   // General settings
   runtimes: 'html5,flash,silverlight,html4',
   url: "plupload/upload.do",
 
   // Maximum file size
   max_file_size: '10000mb',
 
   chunk_size: '1mb',
 
   // Resize images on clientside if we can
   resize: {
    width: 200,
    height: 200,
    quality: 90,
    crop: true // crop to exact dimensions
   },
 
   // Specify what files to browse for
   filters: {
   	max_file_size : '10mb',
		mime_types: [
			{title : "文本文件", extensions : "txt"}
		]
   },
 
   // Rename files by clicking on their titles
   rename: true,
 
   // Sort files
   sortable: true,
 
   // Enable ability to drag'n'drop files onto the widget (currently only HTML5 supports that)
   dragdrop: true,
 
   // Views to activate
   views: {
    list: true,
    thumbs: true, // Show thumbs
    active: 'thumbs'
   },
 
   // Flash settings
   flash_swf_url: 'skins/js/plupload/Moxie.swf',
 
   // Silverlight settings
   silverlight_xap_url: 'skins/js/plupload/Moxie.xap'
  });
   $("#toStop").on('click', function () {
   uploader.stop();
  });
 
  $("#toStart").on('click', function () {
   uploader.start();
  });
  $("#reGenIndex").on('click', function () {
	  var url = "fileSearcher.so?method=reGenIndex";
		  $.ajax({
			  type:"get",
			  url:url,
			  success:function(){
				  alert("成功");
			  }
		  });
	  });

 });


</script>
</body>
</html>
