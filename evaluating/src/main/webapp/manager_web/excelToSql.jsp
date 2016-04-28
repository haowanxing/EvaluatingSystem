<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>excel导入数据</title>
<script type="text/javascript">
function openDoor(Id)
{
	
	window.open("manager_web/qwe.jsp?Ids="+Id,'newwindow','height=400,width=300,top=0,left=0,toolbar=no,menubar=no,scrollbars=no, resizable=no,location=no, status=no') ;
}
</script>

</head>
<body>
<table>
	<tr>
		<td>
			<form action="ExcelUpDate" method="post" enctype="multipart/form-data">
            	<table>
                <tr>
                    <td> 导入学生数据：</td><td class="cell_spacing"></td>
                  	<td><input type="file" name="StuInfoFile" value="选择文件"></td><td class="cell_spacing"></td>
                  	<td><input type="submit" value="提交数据"></td><td class="cell_spacing"></td>
			  	</tr>
             </table>
			</form>
		</td>
	</tr>
	<tr>
		<td>
		<form method="post" action="saveXml">
        <table>
        		<tr>
				<td colspan="3">测评表名：<input type="text"  name="tableName" /></td><td class="cell_spacing"></td>
                </tr>
                <tr>
                <td>newTable：<input id="xmlTable" name="xmlTable" type="text" /><input type="button" onclick="openDoor('xmlTable')" value="上传"/></td><td class="cell_spacing"></td>
				<td>xmlNews：<input id="xmlNews" name="xmlNews" type="text" /><input type="button" onclick="openDoor('xmlNews')" value="上传"/></td><td class="cell_spacing"></td>
				<td>xmlRules：<input id="xmlRules" name="xmlRules" type="text" /><input type="button" onclick="openDoor('xmlRules')" value="上传"/></td><td class="cell_spacing"></td>
                </tr>
                <tr>
                	<td><input type="submit" value="保存"/></td>
                </tr>
		</table>	
		</form>
		</td>
	</tr>
</table>


</body>
</html>