<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>头部信息</title>
<script language="JavaScript">
function logout(){
	if (confirm("您确定要退出控制面板吗？"))
	top.location = "Login_out.jsp";
	return false;
}
</script>
<base target="main"/>
<link href="images/skin.css" rel="stylesheet" type="text/css"/>
</head>
<body leftmargin="0" topmargin="0">
<%
	//HttpSession session1=request.getSession(true);
	String userip = request.getHeader("x-forwarded-for"); ;
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
		if(userip == null || userip.length() == 0 || "unknown".equalsIgnoreCase(userip)) {
			userip = request.getHeader("Proxy-Client-IP");
		}
		if (userip == null || userip.length() == 0
				|| "unknown".equalsIgnoreCase(userip)) {
			userip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (userip == null || userip.length() == 0
				|| "unknown".equalsIgnoreCase(userip)) {
			userip = request.getRemoteAddr();
		}
		//HttpSession session1=request.getSession(true);
		session.getAttribute("nowusername");
%>
<table width="100%" height="64" border="0" cellpadding="0" cellspacing="0" class="admin_topbg">
  <tr>
    <td width="" height="64"></td>
    <td width="" valign="top">
    <table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-top: 15px;">
      <tr>
        <td width="" height="38" class="admin_txt" align="right"> <b><%=session.getAttribute("name") %></b> 您好！<span class="left_txt" style="color:#FFF">上次登录IP：<b style="color:#FFF">58.19.2.41</b> &nbsp;&nbsp;当前登录IP：<b style="color:#FFF"><%=userip %></b></span></td>
        <td width="4%">&nbsp;</td>
                <td width="90px"><a href="#"  class="top-bu-bg">使用教程</a></td>
                <td width="90px"><a href="#"  class="top-bu-bg">最新通知</a></td>
        <td width="90px"><a href="#" class="top-bu-bg">个人状态</a></td>
        <td width="90px"><a href="javascript:void(0);" target="_parent" onclick="logout();" class="top-bu-bg">安全退出</a></td>
        <td width="">&nbsp;</td>
      </tr>
    </table>
    </td>
  </tr>
</table>
<%
}
 %>
</body>
</html>
