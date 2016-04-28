 <%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>菜单列表</title>
<script src="js/jquery.js" type="text/javascript"></script>
<script src="js/prototype.lite.js" type="text/javascript"></script>
<script src="js/moo.fx.js" type="text/javascript"></script>
<script src="js/moo.fx.pack.js" type="text/javascript"></script>
<style>
body {
	font:12px Arial, Helvetica, sans-serif;
	color: #000;
	background-color: #EEF2FB;
	margin: 0px;
}
#container {
	width: 182px;
	margin-top:15px;
}
H1 {
	font-size: 12px;
	margin: 0px;
	width: 182px;
	cursor: pointer;
	height: 42px;
	line-height: 20px;	
}
H1 a {
	display: block;
	font-size:16px;
	font-family:"微软雅黑";
	width: 182px;
	color: #fff;
	height: 40px;
	text-decoration: none;
	moz-outline-style: none;
	/* background-image: url(images/menu_bgs.gif);
	background-repeat: no-repeat; */
	background-color:#4caedd;
	line-height: 45px;
	text-align: center;
	margin: 0px;
	padding: 0px;
}
.content{
	width: 182px;
	height: auto;
	overflow:hidden
	
}
.MM ul {
	list-style-type: none;
	margin: 0px;
	padding: 0px;
	display: block;
}
.MM li {
	font-family: Arial, Helvetica, sans-serif;
	font-size: 12px;
	line-height: 26px;
	color: #333333;
	list-style-type: none;
	display: block;
	text-decoration: none;
	height: 40px;
	width: 182px;
	padding-left: 0px;
	background-color:#f5f5f5;
}
.MM {
	width: 182px;
	margin: 0px;
	padding: 0px;
	left: 0px;
	top: 0px;
	right: 0px;
	bottom: 0px;
	clip: rect(0px,0px,0px,0px);
}
.MM a {
	font-family: Arial, Helvetica, sans-serif;
	font-size: 13px;
	line-height: 26px;
	color: #000;
	/* background-image: url(images/menu_bg1.gif);
	background-repeat: no-repeat; */
	height: 40px;
	width: 182px;
	display: block;
	text-align: center;
	margin: 0px;
	padding: 8px 0px 0px 0px;
	overflow: hidden;
	text-decoration: none;
}
.MM a.cur{color: #006600; background:url(images/menu_bg_c.png); font-weight:bold}
.MM a.hover{color: #006600; font-weight:bold;background-color:#e1e1e1;}

</style>
</head>

<body>
<%
	//HttpSession session=request.getSession(true);
	if(session.getAttribute("nowusername")==null)
	{
		out.println("<p>请重新登陆！3秒后自动跳转...</p>");
	%>
		<script>
		window.location.href="Login.jsp";
		</script>
	<% 
	}
	else
	{

	//HttpSession session=request.getSession(true);
		int access=Integer.parseInt((String)session.getAttribute("access"));
		System.out.println(access);
	
 %>
<table width="100%" border="0" cellpadding="0" cellspacing="0" bgcolor="#f5f5f5" style="margin-left:18px;">
  <tr>
    <td width="182" valign="top">
    <div id="container">
    <%
    	if(access==1001||access==1003)
    	{
    		
    	
     %>
          <h1 class="type"><a href="javascript:void(0)">学生功能</a></h1>
      <div class="content">
        <ul class="MM" style="background:#999">
                  <li><a class="cur hover" href="notice.jsp" target="main">注意事项</a></li>
                  <li><a href="table.jsp" target="main">查看/填写</a></li>
                  <li><a href="msg.jsp" target="main">通知信箱</a></li>
                </ul>
      </div>
      <%
      	}
      	if(access==1002||access==1003||access==1004)
      	{ 
      %>
          <h1 class="type"><a href="javascript:void(0)">领导功能</a></h1>
      <div class="content">
        <ul class="MM" style="background:#999">
                  <li><a href="center1.jsp?nowPage=1" target="main">审核信息列表</a></li>
                  <li><a href="msg.jsp" target="main">通知信箱</a></li>
                  <li><a href="notice.jsp" target="main">注意事项</a></li>
                </ul>
      </div>
      <%
      }
      
      	if(access==1004)
      	{
       %>
          <h1 class="type"><a href="javascript:void(0)">附加功能</a></h1>
      <div class="content">
        <div><img src="images/menu_topline.gif" width="182" height="5" /></div>
        <ul class="MM" style="background:#999">
                  <li><a href="manager_web/excelToSql.jsp" target="main">系统配置</a></li>
                  <li><a href="#" target="main">功能二</a></li>
                </ul>
      </div>
   <%
      	}
   %>
      </div>
    </td>
  </tr>
</table>
<%
	}// 防止 恶意的 网址进入
 %>
<script type="text/javascript">
var contents = document.getElementsByClassName('content');
var toggles = document.getElementsByClassName('type');
	
var myAccordion = new fx.Accordion(
	toggles, contents, {opacity: true, duration: 400}
);
myAccordion.showThisHideOpen(contents[0]);

jQuery.noConflict();
jQuery(function(){
    jQuery('.MM a').click(function(){
		jQuery('.MM a').removeClass('cur');
        jQuery(this).addClass('cur');
	});
	
	jQuery('.MM a').hover(function(){
	     jQuery(this).addClass('hover');
	},function(){
	     jQuery(this).removeClass('hover');
	});
})


</script>
</body>
</html>