<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="function.*,java.sql.*,java.text.*,java.util.Date"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
<title>站内信</title>
<link href="images/skin.css" rel="stylesheet" type="text/css" />
<script>

//全选/取消
function checkAll(o,checkBoxName){
	var oc = document.getElementsByName(checkBoxName);
	for(var i=0; i<oc.length; i++) {
		if(oc[i].disabled==false){
		    if(o.checked){
				oc[i].checked=true;	
			}else{
				oc[i].checked=false;	
			}
		}
	}
}
</script>
</head>
<%
	//HttpSession session1=request.getSession(true);
	if (session.getAttribute("nowusername") == null) {
		out.println("<p>请重新登陆！3秒后自动跳转...</p>");
	%>
		<script>
		window.location.href="Login.jsp";
		</script>
	<% 
	} else {
		String useraccess=session.getAttribute("access").toString();
		String usertable="student";
		if(useraccess.equals("1001")){
			usertable="manager_form";
		}
	 	request.setCharacterEncoding("utf-8");
	 	response.setCharacterEncoding("utf-8");
		int Prepage = 1;
		int Nextpage = 1;
		String SnowPage = request.getParameter("nowPage");
		int nowPage = 1;
		if(SnowPage != null){
			nowPage = Integer.parseInt(SnowPage);
		}
		//开始驱动数据库
		Sql_op op = new Sql_op();
		//检索确定id的记录
		ResultSet rs = op.find("exam_msg", "*", "id="+request.getParameter("id"));
		rs.next();
		//检索回复的记录
		ResultSet reply = op.find("exam_msg", "*", "sid="+rs.getString("id"));
		//获取回复对方的帐号
		String replyuser = "";
		ResultSet resuid = op.find(usertable, "*", "user_number="+rs.getString("senduser"));
		while(resuid.next())
			replyuser = resuid.getString("user_number");
		op.update("exam_msg", "see=1", "id="+request.getParameter("id"));
		op.close();
%>
<body style=" min-height:500px" bgcolor="#EEF2FB">
<table class="table_container" width="90%" border="0" cellpadding="0" cellspacing="0">
  <tr>
		<td valign="top" id="top_name" >
        <span class="autol"><span><b>发送站内信</b></span><i></i></span> 
    </td>
	</tr>
  <tr>
    <td valign="top">
    	<table cellspacing="0" width="100%" style="border:1px  solid #DCEAF7; border-bottom:0px; background:#E9F2FB">
			<tr>
				<td width="115px" class="bigtext">&nbsp;<img src="images/arrow.gif" width="16" height="22" align="absmiddle" />&nbsp;<a href="sendmsg.jsp" class="link3">[发送站内信]</a> </td>
			</tr>
		</table>
    	<form action="sendmsg.jsp" method="get" name="form2">
			<table id="addeditable" border="1" cellpadding="0" cellspacing="0" bordercolor="#dddddd">
  				<tr>
    				<td width="115px" align="right">发件人：</td>
    				<td>&nbsp;<%=rs.getString("senduser") %> </td>
				  </tr>
				  <tr>
				    <td align="right">发送时间：</td>
				    <td>&nbsp;<%=rs.getString("addtime") %> </td>
				  </tr>
				  <tr>
				    <td align="right">内容：</td>
				    <td>&nbsp;<%=rs.getString("content") %></td>
				  </tr>
				    <tr>
				    <td align="right">&nbsp;</td>
				    <td>&nbsp;<input type="hidden" name="name" value="<%=replyuser %>" /><input type="hidden" name="title" value="<%=rs.getString("title") %>" /><input type="hidden" name="sid" value="<%=rs.getString("id") %>" /><input type="submit" class="sub"  value=" 回 复 " /></td>
				  </tr>
				  <%while(reply.next()){ %>
				  <tr>
				    <td align="right">我的回复：</td>
				    <td>&nbsp;<%=reply.getString("content") %></td>
				  </tr>
				  <tr>
				    <td align="right">回复时间：</td>
				    <td>&nbsp;<%=reply.getString("addtime") %></td>
				  </tr>
				  <%} %>
			</table>
		</form>
		</td>
  </tr>
</table>
</body>
<%} %>
</html>