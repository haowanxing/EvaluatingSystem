<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="java.sql.*,java.text.*,java.util.Date,function.*"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
//获取本页url(带参数)
String pageurl = request.getRequestURI()+"?";
if(request.getQueryString()!=null)
	pageurl += request.getQueryString();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
<title>通知信箱</title>
<link href="images/skin.css" rel="stylesheet" type="text/css" />
<script src="http://libs.useso.com/js/jquery/1.9.1/jquery.min.js" type="text/javascript"></script>
<script>
$(function(){
	$('#listtable tr').hover(function(){
	    $(this).addClass('trbg');
	},function(){
	    $(this).removeClass('trbg');
	});
})
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
		String usertable="manager_form";
		if(useraccess.equals("1001")){
			usertable="student";
		}
		String Page = request.getParameter("page");
		int start = 0;
		int shownum = 10;	//每页展示数量
		int p = 1;
		if(Page != null && !Page.equals("0")){
			p = Integer.parseInt(Page);
			start = (p-1)*10;
		}
		//开始驱动数据库
		Sql_op op = new Sql_op();
		ResultSet count = op.find("exam_msg as a,"+usertable+" as b", "count(*)", "a.uid=b.user_number and b.user_number="+session.getAttribute("nowusername"));
		count.next();
		int sum = Integer.parseInt(count.getString("count(*)"));
		int PageCount = (sum%shownum==0)?(sum/shownum):(sum/shownum+1);
		if(PageCount > 0 && Page != null && (Page.equals(PageCount+"") || Integer.parseInt(Page)>PageCount)){
			p = PageCount;
			start = (p-1)*10;
		}
		ResultSet rs = op.find("exam_msg as a,"+usertable+" as b","*","a.uid=b.user_number and b.user_number="+session.getAttribute("nowusername")+" order by a.id desc limit "+start+","+shownum);
		//获得数据结果集合
		ResultSetMetaData rmeta = rs.getMetaData();
		//确定数据集的列数，亦字段数
		int numColumns = rmeta.getColumnCount();
%>
<body style=" min-height:500px" bgcolor="#EEF2FB">
	<table class="table_container" width="90%" border="0" cellpadding="0" cellspacing="0">
		<tr>
			<td valign="top" id="top_name">
				<span class="autol"><span><b>通知信箱</b></span><i></i></span>
			</td>
		</tr>
		<tr>
			<td valign="top">
				<table cellspacing="0" width="100%" style="border:1px  solid #DCEAF7; border-bottom:0px; background:#E9F2FB">
        			<tr>
		              <td width="115px" class="bigtext">&nbsp;<img src="images/arrow.gif" width="16" height="22" align="absmiddle" />&nbsp;<a href="sendmsg.jsp" class="link3">[发送站内信]</a> </td>
		              <td width="125px" align="right" class="bigtext">共有 <b><%=sum %></b> 条记录&nbsp;&nbsp;</td>
		            </tr>
      			</table>
			     <form name="form2" method="get" action="" style="margin:0px; padding:0px">
			     <table id="listtable" border="1" cellpadding="0" cellspacing="0" bordercolor="#dddddd">
			     	<tr>
						<th width="3%"><input type="checkbox" onclick="checkAll(this,'ids[]')" /></th>
			            <th width="6%">状态</th>
			            <th width="115px">标题</th>
			            <th width="">内容</th>
						<th width="115px">发件人</th>
						<th width="140px">发送时间</th>
						<th width="115px">执行操作</th>
					</tr>
					<%
					while(rs.next()) {
						String see;
						switch(Integer.parseInt(rs.getString("see"))){
							case 0:see="images/msg0.gif";break;
							case 1:see="images/msg1.gif";break;
							default:see="error!";break;
						}
					%>
					<tr>
						<td><input type='checkbox' name='ids[]' value='<%=rs.getString("id") %>' id='content_<%=rs.getString("id") %>' /></td>
						<td><img src="<%=see %>" /></td>
						<td><%=rs.getString("title") %></td>
						<td style="padding:5px; text-align:left; line-height:15px"><%=rs.getString("content") %></td>
						<td><a href="javascript:void(0)" style=" text-decoration:underline"><%=rs.getString("senduser") %></a></td>
						<td><%=rs.getString("addtime") %></td>
						<td><a class="read" href="readmsg.jsp?id=<%=rs.getString("id") %>">阅读</a></td>
					</tr>
					<%} %>
				</table>                  
				<div style="position:relative; padding-bottom:10px">
					<input type="hidden" name="mod" value="msg" />
					<input type="hidden" name="act" value="del" />
					<div style="position:absolute; left:5px; top:5px">
						<input type="submit" value="删除" onclick='return confirm("确定要删除?")'/>
					</div>
					<div class="megas512" style=" margin-top:15px;">
						<%Page pagehtml=new Page(); %>
						<%=pagehtml.GetPage(sum,shownum,p,pageurl,"page") %>
					</div>
				</div>
				</form>
	</td>
		</tr>
	</table>
</body>
<%
op.close();
} %>
</html>