<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="function.*,java.sql.*,java.text.*,java.util.Date"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
<title>发送消息</title>
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
<body style=" min-height:500px" bgcolor="#EEF2FB">
<%
if (session.getAttribute("nowusername") == null) {
		out.println("<p>请重新登陆！3秒后自动跳转...</p>");
	%>
		<script>
		window.location.href="Login.jsp";
		</script>
	<% 
	}else{
		String useraccess=session.getAttribute("access").toString();
		String usertable="student";
		if(useraccess.equals("1001")){
			usertable="manager_form";
		}
	 	request.setCharacterEncoding("utf-8");
	 	response.setCharacterEncoding("utf-8");
		String name = request.getParameter("name");
		String sid = request.getParameter("sid");
		String title = request.getParameter("title");
		if(name==null)
			name = "";
		if(sid==null)
			sid = "0";
		if(title==null)
			title = "";
		System.out.println(name+"   "+sid);
		String username = request.getParameter("username");
		String content = request.getParameter("content");
		String senduser = "0";
		String uid = "0";
		if(content!=null&&username!=null){
			String[] users = username.split("\\|");
			for(int i=0;i<users.length;i++){
				Date dNow = new Date( );
		      	SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd hh:mm:ss");
		      	String nowtime = ft.format(dNow);
				Sql_op op = new Sql_op();
				ResultSet res = op.find(usertable, "*", null);
				while(res.next()){
					if(users[i].equals(res.getString("user_number"))){
						uid = res.getString("user_number");
						senduser = session.getAttribute("nowusername").toString();
					}
				}
				Map<String,String> data = new HashMap<String,String>();
				data.put("title",title);	//标题
				data.put("content",content);	//内容
				data.put("see","0");	//是否阅读
				data.put("uid",uid);	//发送给谁的用户id 对应student中的id 不是学号
				data.put("senduser",senduser);	//发送者的id
				data.put("sid",sid);	//应用于回复，回复哪条信息的id
				data.put("addtime",nowtime);	//当前时间
				System.out.println(data.size());
				op.insert("exam_msg", data);
				op.close();
			}
		}
 %>
<table class="table_container" width="90%" border="0" cellpadding="0" cellspacing="0">
  <tr>
	<td valign="top" id="top_name" >
        <span class="autol"><span><b>发送信息</b></span><i></i></span> 
	</td>
	</tr>
  <tr>
    <td valign="top" >
		<table cellspacing="0" width="100%" style="border:1px  solid #DCEAF7; border-bottom:0px; background:#E9F2FB">
        	<tr>
              <td width="115px" class="bigtext">&nbsp;<img src="images/arrow.gif" width="16" height="22" align="absmiddle" />&nbsp;<a href="msg.jsp" class="link3">[我的站内信]</a> </td>
              <td width="125px" align="right" class="bigtext"></td>
            </tr>
      	</table>
    	<form action="" method="POST" name="form1">
		<table id="addeditable" border=1 cellpadding=0 cellspacing=0 bordercolor="#dddddd">
			<tr>
				<td width="115px" align="right">发送给：</td>
				<td>&nbsp;<input name="username" type="text" style="width:400px" value="<%=name %>" /> <span class="zhushi">用“|”隔开会员名可群发。不填写将发送全站会员</span></td>
			</tr>
			<tr>
				<td width="115px" align="right">标题：</td>
				<td>&nbsp;<input name="title" type="text" style="width:400px" maxlength="100" value="<%=title %>" /> <span class="zhushi"></span></td>
			</tr>
			<tr>
				<td align="right">内容：</td>
				<td>&nbsp;<textarea style="width:400px; height:200px" name="content"></textarea></td>
			</tr>
			<tr>
				<td align="right">&nbsp;</td>
				<td>&nbsp;<input type="hidden" name="senduser" value="<%=session.getAttribute("nowusername")%>" /><input type="hidden" name="sid" value="0" /><input type="submit" class="sub" name="sub" value=" 发 送 " />
				</td>
			</tr>
		</table>
		</form>
	</td>
</table>
<%} %>
</body>
</html>