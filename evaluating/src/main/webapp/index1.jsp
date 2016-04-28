 <%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
<head>
<title>用户中心</title>
<meta http-equiv=Content-Type content=text/html;charset=utf-8>

</head>
<%
	//HttpSession session1=request.getSession(true);
	if(session.getAttribute("nowusername")==null)
	{
		 out.println("<p>您还没有登陆，请重新登陆！3秒后自动跳转...</p>");
		response.setHeader("REFRESH","3;URL=Login.jsp"); 

	}else{
 %>
<frameset rows="65,*" id="father"  frameborder="NO" border="0" framespacing="0">
  <frame src="top.jsp" noresize="noresize" frameborder="no" name="topFrame" scrolling="no" marginwidth="0" marginheight="0" target="main" />
  <frameset cols="200,*"  id="frame">
    <frame src="left.jsp" name="leftFrame" noresize="noresize" marginwidth="0" marginheight="0" frameborder="no" scrolling="yes" target="main" />
    <frame src="notice.jsp" name="main" noresize="noresize" marginwidth="0" marginheight="0" frameborder="no" scrolling="yes" target="_self" />
  </frameset>
</frameset>
<noframes></noframes>
<%} %>
</html>