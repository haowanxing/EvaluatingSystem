<%@page import="dBassiant.Mysql_operation"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="dao.*,mode.*" %>
<%
System.out.println("登录检测页！！");
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

  <%	
	request.setCharacterEncoding("utf-8");
  	String username=request.getParameter("username");
  	String userpassword=request.getParameter("userpassword");
  	String nowUser=(String)request.getAttribute("nowusername");
	
	if(nowUser!=null)
	{  
	  	if(nowUser.equals(username))
	  	{
	  		out.println("1");// 已存在session，已登录
	  		return ;
	  	}
  	}
  	
  	int b=0;
  	
  	ArrayList<Item_check> students=new ArrayList<Item_check>();
  	Items dao=new Items();
  	String tables[]={"manager_form","student"};
  	String table1="";
  	int resultFlag=0;
  	
  	Mysql_operation msqlOp=new Mysql_operation();
  	
  	for(int a=0;a<tables.length;a++)
  	{
  		resultFlag+=dao.getStudents_check("Select count(*) from "+tables[a]+" where user_number='"+username+"' and password='"+userpassword+"' ;");
  		if(resultFlag==1)
  		{
  			session.setAttribute("nowusername", username);
			session.setAttribute("table", tables[a]);
			table1=tables[a];	
						 
			dao.setTable(table1);
			dao.setFiled_name("user_number");
			//session.setAttribute("number", username);
			session.setAttribute("name",dao.getFieldValue("user_name",username));
							  	
			session.setAttribute("access", dao.getFieldValue("access",username));
			System.out.println("access:"+dao.getFieldValue("access",username));
				  		
				  		
			out.print("2");//登录成功1！！！ 不存在session 新建
			return ;
  		}
  	}
  	
	 
	 if(resultFlag==0)
	  	{
	  		//out.print("1");
	  		//response.sendRedirect("Login.jsp");
	  		out.print("4");//用户或密码错误
		
	  	}
	 
  
   %>
 
