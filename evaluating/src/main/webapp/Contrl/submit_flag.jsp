<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="dao.*,dBassiant.*" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

	String student=request.getParameter("ids[]");
		
	Mysql_operation mysq_op=new Mysql_operation ();
	String flag=(String)request.getParameter("submit");
	System.out.println("submit_flag_jsp"+flag);
	// 用于学生 是否能填写表的  控制 
	if(flag!=null)
	{
		mysq_op.setTable("student");
		mysq_op.alter_value("Sfill_access='"+flag+"'", "where user_number='"+student+"'");
		mysq_op.setTable("student_basic_news");
		mysq_op.alter_value("examine='"+flag+"'", "where user_number='"+student+"'");
		response.sendRedirect("../table.jsp");
		//mysq_op.destoryDB();
	}
%>

