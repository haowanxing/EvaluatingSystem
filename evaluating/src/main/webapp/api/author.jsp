<%@ page language="java" import="java.util.*,java.sql.*,function.Sql_op" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
</head>
<body>
<%
//此页面为测评系统接收客户端认证结果的页面
/*
需要接收的参数包括（openid,openkey,userid,username,backurl）
参数说明：openid,客户授权ID | openkey,客户授权密钥 | userid,测评用户ID | username,测评用户名字 | backurl,客户登陆地址
*/
String Method = request.getMethod();
Map datas = null;
if("POST".equals(Method)){
	datas = request.getParameterMap();
}else{
	out.print("<p>invalid Method!</p>");
}
if(datas != null){
	Boolean checkedept = false;
	String[] OpenID = (String[]) datas.get("openid");
	String[] OpenKey = (String[]) datas.get("openkey");
	Sql_op sqlop = new Sql_op();
	ResultSet res = null;
	res = sqlop.find("exam_dept_info", "*", "openid='"+OpenID[0]+"' and openkey='"+OpenKey[0]+"'");
	while(res.next()){
		if(!res.getString("dname").equals("")){
			session.setAttribute("deptname",res.getString("dname"));
			checkedept = true;
		}
	}
	if(checkedept){
		String[] UserId = (String[]) datas.get("userid");
		String[] UserName = (String[]) datas.get("username");
		if(UserId!=null && !UserId[0].equals("")){
			session.setAttribute("authorid",UserId[0]);
			session.setAttribute("authorname",UserName[0]);
			out.print(UserId[0]+"-"+UserName[0]);
			System.out.print(UserId[0]);
		}else{
			out.print("<p>invalid!</p>");
			System.out.print("invalid!");
			//response.sendRedirect(((String[]) datas.get("backurl"))[0]);
		}
	}else{
		out.print("<p>Invalid OpenID OR OpenKey!</p>");
	}
}else{
	out.print("<p>invalid! No Datas!</p>");
}
%>
</body>
</html>