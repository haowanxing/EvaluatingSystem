<%@page import="dBassiant.Mysql_operation"%>
<%@page import="dBassiant.DBHelper"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="dao.*"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";


/* 
  
  Enumeration pNames=request.getParameterNames();
  while(pNames.hasMoreElements())
  {
      String name=(String)pNames.nextElement();
      String value=request.getParameter(name);
      System.out.println("name:"+name+"<br>value："+value);
        
  }
*/

	if(session.getAttribute("nowusername")==null)
	{
		response.sendRedirect("Login.jsp");
	}
	

	String students[]=request.getParameterValues("ids[]");
	
	String contents="";
	//确定 当前页码
	String nowpage=request.getParameter("nowPage");// 从center1.jsp 获取 页码
		
	if(nowpage==null)
	{
		nowpage=(String)session.getAttribute("nowaPgae");
		
		if(nowpage==null)
			nowpage="1";
	}
		
	Mysql_operation mysq_op=new Mysql_operation ();
	
	Items temp=new Items();
    temp.setTable("manager_form");
    temp.setFiled_name("user_number");
    int boss=Integer.parseInt(temp.getFieldValue("role",(String)session.getAttribute("nowusername")));//获取当前角色等级
    int Stu_role=boss+1;//如通过 学生 所属的上级等级


	int b=3;
	
	if(students!=null)
	{
		if(request.getParameter("action").equalsIgnoreCase("2"))
		{
			System.out.print("退审");
			
			for(int za=0;za<students.length;za++)
			{
				
	 			Items tempQ=new Items();
			    tempQ.setTable("manager_form");
			    tempQ.setFiled_name("user_number");
			    int fill_accessB=Integer.parseInt(tempQ.getFieldValue("role",(String)session.getAttribute("nowusername")));
			    // 获取当前用户的角色等级
			    tempQ.setTable("student");
			    tempQ.setFiled_name("user_number");
			    int fill_accessS=Integer.parseInt(tempQ.getFieldValue("now_role",students[za]));
			    //获取学生所属上级的角色等级
			    if(fill_accessB==fill_accessS)//判断 当前用户是否有 修改学生信息的权利
			    {
			   		// 上级有 权限修改 一
			   		tempQ.setTable("student_basic_news");
			    	tempQ.setFiled_name("user_number");
			    	if(!tempQ.getFieldValue("examine",students[za]).equals("0"))//判断用户是否填写
			    	{
				   		mysq_op.setTable("student_basic_news");
						mysq_op.alter_value("examine='2'", "where user_number='"+students[za]+"'");//修改 填写表的状态
						
				    	mysq_op.setTable("student");
						mysq_op.alter_value("Sfill_access='0'", "where user_number='"+students[za]+"'");
						
				    	mysq_op.alter_value("now_role='1'", "where user_number='"+students[za]+"'");//所有的退审都需要将 当前角色等级降到最低
				    	
					}	
			    }	
			}

		}
		else
		{
			System.out.println("审核通过");
			for(int za=0;za<students.length;za++)
			{
				Items tempQ=new Items();
			    tempQ.setTable("manager_form");
			    tempQ.setFiled_name("user_number");
			    int fill_accessB=Integer.parseInt(tempQ.getFieldValue("role",(String)session.getAttribute("nowusername")));
			    
			    tempQ.setTable("student");
			    tempQ.setFiled_name("user_number");
			    int fill_accessS=Integer.parseInt(tempQ.getFieldValue("now_role",students[za]));
			    
			    if(fill_accessB==fill_accessS)
			    {
			   		// 上级有 权限修改 一审核通过
			   		tempQ.setTable("student_basic_news");
			    	tempQ.setFiled_name("user_number");
			    	if(!tempQ.getFieldValue("examine",students[za]).equals("0"))//判断用户是否填写
			    	{
				   		mysq_op.setTable("student_basic_news");
						mysq_op.alter_value("examine='1'", "where user_number='"+students[za]+"'");//修改 填写表的状态
						
						mysq_op.setTable("student");
				    	mysq_op.alter_value("now_role='"+Stu_role+"'", "where user_number='"+students[za]+"'");//审核通过的 将当前角色等级改为上一级的角色等级
				    	
					}	
			    }			
			}
		}
		

	}
	response.sendRedirect("center1.jsp?nowPage="+nowpage);


 %>  
 
 


