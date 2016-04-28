<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Hello</title>

</head>

<body>
			<form action="manager_web/HL?Ids=<%=request.getParameter("Ids")%>" method="post" enctype="multipart/form-data">
			  <label>导入学生数据：
			  <input type="file" name="StuInfoFile" value="选择文件">
			  <input type="submit" value="提交数据">
			  </label>
			  <p><%=request.getParameter("Ids")%></p>
			</form>
				
</body>
</html>
