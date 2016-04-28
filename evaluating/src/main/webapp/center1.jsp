<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="dao.Items,mode.Item_student,java.text.*"%>
<%@ page import="dao.Manager_op"%>
<%
System.out.println("管理者管理页！！");
	String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";


	HttpSession session1=request.getSession(true);
	
	if(session.getAttribute("nowusername")==null)
	{
		/* RequestDispatcher rd = request.getRequestDispatcher("Login.jsp");   
		rd.forward(request,response); */
		out.println("<p>您还没有登陆，请重新登陆！3秒后自动跳转...</p>");
	%>
		<script>
		window.location.href="Login.jsp";
		</script>
	<% 
	}
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
<title>用户列表</title>
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
    function showTable()
    {
        if(document.getElementById("selsecId").style.display=="block")
            document.getElementById("selsecId").style.display="none";
        else
            document.getElementById("selsecId").style.display="block";
    }
	//全选/取消
	function checkAll(o, checkBoxName) {
		var oc = document.getElementsByName(checkBoxName);
		for ( var i = 0; i < oc.length; i++) {
			if (oc[i].disabled == false) {
				if (o.checked) {
					oc[i].checked = true;
				} else {
					oc[i].checked = false;
				}
			}
		}
	}

	function submitFun(act) {
		document.getElementById("ways").value = act;
		document.getElementById("proccess").submit();
	}

	function submitFun1(act, student) {
		document.getElementById("ways").value = act;
		document.getElementById("proccess").attributes["action"].value = document
				.getElementById("proccess").attributes["action"].value
				+ "&ids[]=" + student;
		//  alert(document.getElementById("proccess").attributes["action"].value);
		document.getElementById("proccess").submit();
	}
</script>
</head>
<%
	int Prepage = 1;
	int Nextpage = 1;
	Items item = new Items();
	ArrayList<Item_student> students = new ArrayList<Item_student>();
	String user_name = (String) session.getAttribute("nowusername");

	String major = request.getParameter("student_major");
	String S_class = request.getParameter("student_class");
	String S_age = request.getParameter("student_age_class");
	String S_student = request.getParameter("user_number");
	String limit2 = "";
	
	System.out.println(major+S_student);

	if (!(major == null || S_age == null || S_class == null))//都不为空 才执行
	{
		if (!major.equals("*"))// 如果为全部 则没有 限制
			limit2 += " and major=\"" + major + "\"";

		if (!S_class.equals("*"))
			limit2 += " and student_class=\"" + S_class + "\"";

		if (!S_age.equals("*"))
			limit2 += " and student_age_class=\"" + S_age + "\"";
		if (!S_student.isEmpty())
			limit2 += " and a.user_number=\"" + S_student + "\"";
		
		System.out.println(limit2);
	} 
	else 
	{
		major = "";
		S_age = "";
		S_class = "";
		S_student = "";

	}

	

	Manager_op item2 = new Manager_op();

	students = item2.getStudent(user_name, limit2);
	String SnowPage = request.getParameter("nowPage");

	//System.out.println("当前页数："+SnowPage);
	session.setAttribute("nowPage", SnowPage);
	if (SnowPage == null) {
		SnowPage = "1";
	}
	int nowPage = Integer.parseInt(SnowPage);
%>

<body style=" min-height:500px" bgcolor="#EEF2FB">
	<table class="table_container" width="90%" border="0" cellpadding="0" cellspacing="0">
		<tr>
			<td valign="top" id="top_name">
				<span class="autol"><span><b>用户列表</b></span></span>
			</td>
		</tr>
		<tr>
			<td valign="top">
				<form name="form1" action="center1.jsp" method="post">
				    <div id="classify"><a href="javascript:showTable()">分类筛选</a><div style="margin:0px; padding: 0px; float:right; margin-right: 10px"> 未提交：<span class="red">22</span> | 已审核：11 | 未审核：45</div></div>
				    <table id="selsecId"  style="display:none; margin-left: 20px;" cellspacing="0" width="100%" style="border:1px  solid #DCEAF7; border-bottom:0px; background:#E9F2FB;">
				        <tr>
				            <td width="250px" align="left">
				                专业：<select name='student_major' onchange='window.location="#"+this.value'>
				                <option value='*' selected>全部</option>
<%
	ArrayList<String> selectM=item2.getSelectValues(user_name, "student_major");
	
	for(int zx=0;zx<selectM.size();zx++)
	{
		String tempM=selectM.get(zx);
 %>				                

				                <option value='<%=tempM %>' class="zy"><%=tempM %></option>
<%
	}
 %>
				            </select>
				            </td>
				            <td width="250px" align="left">
				                年级：<select name='student_age_class' onchange='window.location="#"+this.value'>
				                    <option value='*' selected>全部</option>
<%
	ArrayList<String> selectA=item2.getSelectValues(user_name, "student_age_class");
	
	for(int zx=0;zx<selectA.size();zx++)
	{
		String tempM=selectA.get(zx);
 %>				                

				                <option value='<%=tempM %>' class="zy"><%=tempM %></option>
<%
	}
 %>
				                </select>
				            </td>
				            <td width="250px" align="left">
				                班级：<select name='student_class' onchange='window.location="#"+this.value'>
				                    <option value='*' selected>全部</option>
<%
	ArrayList<String> selectC=item2.getSelectValues(user_name, "student_class");
	
	for(int zx=0;zx<selectC.size();zx++)
	{
		String tempM=selectC.get(zx);
 %>				                
				                <option value='<%=tempM %>' class="zy"><%=tempM %></option>
<%
	}
 %>
				                </select>
				            </td>
				
				
				            <td width="" align="right">学号：<input type="text" name="user_number" value="" />&nbsp;&nbsp;&nbsp;<input type="submit" value="搜索" /></td>
				
				        </tr>
				    </table>
				    <input type="hidden" name="mod" value="link"/>
				    <input type="hidden" name="act" value="list"/>
				</form>
			</td>
		</tr>
		
		<tr>
			<td valign="top">
<%--///////////////////////////////// --%>
				<form name="form2" id='proccess' method="post" action="process.jsp?nowPage=<%=nowPage%>" style="margin:0px; padding:0px" >
					<div id="rnews" style="margin:0px; vertical-align:top; padding:0px;" >
						<table id="listtable" border="1" cellpadding="0" cellspacing="0" bordercolor="#dddddd">
							<tr>
								<th width="3%"><input type="checkbox" onclick="checkAll(this,'ids[]')" /></th>
								<th width="115px">姓名</th>
								<th width="">学号</th>
								<th width="8%">总分</th>
								<th width="115px">状态</th>
								<th width="150px">提交时间</th>
								<th width="120px">操作</th>
							</tr>
							<%--读取学生记录，并显示//////////////////////////////////// --%>
							<%
								int endStudent = nowPage * 10;

								if (endStudent > students.size())
									endStudent = students.size();
								//确定当前页面的 最后一个人/// 	

								for (int i = ((nowPage - 1) * 10); i < endStudent; i++) {
									Item_student student = students.get(i);
							%>
							<tr>
								<td><input type='checkbox' name='ids[]' value='<%=student.getStudent_number()%>' id='content_10' /></td>
								<td><%=student.getStudent_name()%></td>
								<td><%=student.getStudent_number()%></td>
								<td><%=student.getStudent_sum_mark()%></td>
								<%
									/* 判断学生表的 当前状态   */
										String examine = "";
										int boss = 0;
										int staff = 0;

										Items temp = new Items();
										temp.setTable("student");
										temp.setFiled_name("user_number");
										//System.out.println("测试："+temp.getFieldValue("now_role","201321091074")+"over");
										staff = Integer.parseInt(temp.getFieldValue("now_role",
												student.getStudent_number()));

										temp.setTable("manager_form");
										temp.setFiled_name("user_number");
										boss = Integer.parseInt(temp.getFieldValue("role",
												(String) session.getAttribute("nowusername")));

										if (boss == staff) {

											switch (student.getExamin()) {
											case 0:
												examine = "未填写";
												break;
											case 1:
												examine = "已提交";
												break;
											case 2:
												examine = "退审";
												break;
											}

										} else {
											if (boss > staff)
												examine = "未提交";
											else
												examine = "审核通过";
										}

										DateFormat format2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
										String timea = format2.format(student.getTime());
										/* 判断学生表的 当前状态   */
								%>
								<td class="showstate" style="color:green;"><%=examine%></td>
								<td><%=timea%></td>
								<td>
									<!-- 单条记录操作 --> 
									<a href="manager_table.jsp?student_number=<%=student.getStudent_number()%>" class="link4">审阅</a>&nbsp;&nbsp; 
									<a href="#10" onclick="submitFun1('1','<%=student.getStudent_number()%>')" class="link4">通过</a>&nbsp;&nbsp; 
									<a href="#10" onclick="submitFun1('2','<%=student.getStudent_number()%>')" class="link4">退回</a>
								</td>
							</tr>
							<%
								}
							%>

							<%--读取学生记录，并显示///////////////////////////////////// --%>

						</table>
					</div>
					<div style="position:relative; padding-bottom:10px" >
						<input type="hidden" name="mod" value="link" />
						<input type="hidden" name="act" value="del" />
						<div style="position:absolute; left:5px; top:5px">
							<input type="hidden" name="nowPage" value="<%=nowPage%>" /> <input
								type="hidden" name="student_major" value="<%=major%>" /> <input
								type="hidden" name="student_class" value="<%=S_class%>" /> <input
								type="hidden" name="student_age_class" value="<%=S_age%>" /> <input
								type="hidden" name="user_number" value="<%=S_student%>" />
							<%--提交部分 --%>
							<input id="ways" type="hidden" name="action" value="edit"/>
							<input type="button" name="editBtn" value="审核通过" onclick="submitFun('1');"/> 
							<input type="button" name="delBtn" value="退审" onclick="submitFun('2');"/>
						</div>

						<%--/////////////////////////// --%>

						<%--生成页码/////////////////////////////////////////////////////////////////////////// --%>
						<div class="megas512" style=" margin-top:15px;">
							<%
								double sum_page1 = (double) students.size() / 10;
								int sum_page = (int) Math.ceil(sum_page1);
								if (sum_page == 0) {
									sum_page = 1;
								}
							%>
							<span class='disabled'>1-10/<%=sum_page%> 记录</span><span
								class='disabled'><%=nowPage%>/<%=sum_page%> 页</span>
							<%
								if (nowPage == 1) {
									Prepage = nowPage;
									if (sum_page == nowPage)
										Nextpage = nowPage;
									else
										Nextpage = nowPage + 1;
								} else if (nowPage == sum_page) {
									Nextpage = nowPage;
									if (nowPage == 1) {
										Prepage = nowPage;
									} else {
										Prepage = nowPage - 1;
									}

								} else {
									Nextpage = nowPage + 1;
									Prepage = nowPage - 1;
								}
							%>
							<a href='center1.jsp?nowPage=1'>首页</a> <a
								href='center1.jsp?nowPage=<%=Prepage%>'><</a>
							<%
								if (nowPage > sum_page - 3) {
									if ((sum_page - 5) <= 0) {

										for (int psum_page = 1; psum_page <= sum_page; psum_page++) {
											if (psum_page == nowPage) {
							%>
							<span class="current"><%=nowPage%></span>
							<%
								} else {
							%>
							<a href='center1.jsp?nowPage=<%=psum_page%>'><%=psum_page%></a>
							<%
								}
										}
									} else {
										int psum_page = sum_page - 5;
										for (int i = 0; i < 5; i++) {
											psum_page++;
											String Aclass = "";
											if (psum_page == nowPage) {
							%>
							<span class="current"><%=nowPage%></span>
							<%
								} else {
							%>
							<a href='center1.jsp?nowPage=<%=psum_page%>'><%=psum_page%></a>
							<%
								}
										}
									}
								} else {
									if (nowPage < 3) {
										for (int i = 1; i < 6; i++) {
											if (i == nowPage) {
							%>
							<span class="current"><%=nowPage%></span>
							<%
								} else {
							%>
							<a href='center1.jsp?nowPage=<%=i%>'><%=i%></a>
							<%
								}
										}
									} else {
										for (int i = nowPage - 2; i <= (nowPage + 2); i++) {
											if (i == nowPage) {
							%>
							<span class="current"><%=nowPage%></span>
							<%
								} else {
							%>
							<a href='center1.jsp?nowPage=<%=i%>'><%=i%></a>
							<%
								}
										}
									}
								}
							%>
							<a href='center1.jsp?nowPage=<%=Nextpage%>'>></a> <a
								href='center1.jsp?nowPage=<%=sum_page%>'>尾页</a> 跳至 <select
								name='topage' size='1'
								onchange='window.location="center1.jsp?nowPage="+this.value'>
								<option value='1' selected>1</option>
								<%
									for (int a = 2; a <= sum_page; a++) {
								%>
								<option value='<%=a%>'><%=a%></option>
								<%
									}
								%>
							</select>页
						</div>
					</div>
				</form>
			</td>
		</tr>
		<tr>
	</table>
	<%
		//防止通过网址 恶意进入
	%>
</body>
</html>