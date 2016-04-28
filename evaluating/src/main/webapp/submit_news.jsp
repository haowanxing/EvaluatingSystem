<%@ page language="java" import="java.util.*" contentType="text/html; charset=Utf-8"%>
<%@ page import="java.io.*,dao.*,function.ComputeScore" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	request.setCharacterEncoding("UTF-8");
	response.setContentType("text/html;charset=UTF-8");

   Enumeration pNames=request.getParameterNames();
   String type=request.getParameter("type");
   if(type==null)
   {
   	type="man";
   }

  String student=(String)session.getAttribute("nowusername");
  String student2=(String)session.getAttribute("student_number");
  if(student==null)
  {
  	out.println("<p>请重新登陆！3秒后自动跳转...</p>");
  	%>
	<script>
	window.location.href="Login.jsp";
	</script>
<% 
  }
  else
  {
  	String flag=(String)session.getAttribute("access");//判断用户类型  学生 or 上级
  		if(flag.equals("1001")||type.equals("stu"))
  		{
  			Items temp=new Items();
		    temp.setTable("student");
		    temp.setFiled_name("user_number");
		    int fill_access=Integer.parseInt(temp.getFieldValue("Sfill_access",(String)session.getAttribute("nowusername")));
		    if(fill_access==1)
		    {
		    	out.print("2");//学生已经提交 美俄有修改权限 
		    	return ;
		    }
  		}
  		else
  		{
  			Items temp=new Items();
		    temp.setTable("manager_form");
		    temp.setFiled_name("user_number");
		    int fill_accessB=Integer.parseInt(temp.getFieldValue("role",(String)session.getAttribute("nowusername")));
		    
		    temp.setTable("student");
		    temp.setFiled_name("user_number");
		    int fill_accessS=Integer.parseInt(temp.getFieldValue("now_role",(String)session.getAttribute("student_number")));
		    
		    if(fill_accessB!=fill_accessS)
		    {
		    	out.print("3");// 上级没有 权限修改 一审核通过
		    	return ; 
		    }
  		}
  }
  
  ArrayList<String> names=new ArrayList<String>();
  ArrayList<String> values=new ArrayList<String>();
 
  while(pNames.hasMoreElements())
  {
      String name=(String)pNames.nextElement();
	//System.out.println("过滤："+name.equals("scoretitle"));
      if(!name.toLowerCase().equals("scoretitle"));
      {
      	String valueList[]=request.getParameterValues(name);
      	String value=valueList[0];
      	for(int a=1;a<valueList.length;a++)
      	{
      		value+="|"+valueList[a];
      			
      	}
      	
		name=name.replace("[]", "");
	    names.add(name);
	    values.add(value);
      }
      //System.out.print("name:"+name+"<br>value："+value);
        
  }

  
   //计算分数 初始化
  		ComputeScore com = new ComputeScore();
		com.SetXmlPath(request.getRealPath("WEB-INF//Xml//scorerule.xml"));
	//计算分数 初始化
	
		
  Submit_news_to_database sub_to_db=new Submit_news_to_database(); 
  String mode_name=request.getParameter("scoretitle");
  Manager_op mop=new Manager_op(); // 用于总分的计算
  
  	int rs=0;
  	if(student2==null)
  	{
  		
  		 com.setStudent(student);
  		
  		 rs=sub_to_db.write_news(student, names, values,"student_basic_news");
  		 if(rs==1)
  		 {
  		 	
  		 	double mark=com.docompute(mode_name); 
  		 	
  		    ArrayList<String> names2=new ArrayList<String>();
  			ArrayList<String> values2=new ArrayList<String>();
  		 	names2.add(mode_name);
  		 	values2.add(String.valueOf(mark));
  		 	
  		 	int rs2=sub_to_db.write_news(student, names2, values2,"student_basic_news");
  		 	System.out.println("总分："+values2.get(0));
  		 	int rs3=sub_to_db.write_news(student, names2, values2,"student_marks");//将模块分数 数据写入 student_marks
  		 	
  		 	if(rs3==1)
  		 	{
	  		 	double sum_mark=mop.calculateSumMark("student_marks", student);
	  		 	ArrayList<String> names3=new ArrayList<String>();
	  			ArrayList<String> values3=new ArrayList<String>();
	  			names3.add("student_sum_mark");
	  		 	values3.add(String.valueOf(sum_mark));
	  		 	sub_to_db.write_news(student, names3, values3,"student_basic_news");// 写如总分 */
	  		 	names3=null;
  		 		values3=null;
  		 	}
  		 	
			names2=null;
  		 	values2=null;
  		 }
  	
  	}
  	else
  	{
  		
  		com.setStudent(student2);
  		
  		rs=sub_to_db.write_news(student2, names, values,"student_basic_news");
  		
  		 if(rs==1)
  		 {
  		 	double mark=com.docompute(mode_name); 
  		 	
  		    ArrayList<String> names2=new ArrayList<String>();
  			ArrayList<String> values2=new ArrayList<String>();
  		 	names2.add(mode_name);
  		 	values2.add(String.valueOf(mark));
  		 	
  		 	int rs2=sub_to_db.write_news(student2, names2, values2,"student_basic_news");
  		 	
  		 	int rs3=sub_to_db.write_news(student2, names2, values2,"student_marks");//将模块分数 数据写入 student_marks
  		 	
  		 	if(rs3==1)
  		 	{
	  		 	double sum_mark=mop.calculateSumMark("student_marks", student2);
	  		 	ArrayList<String> names3=new ArrayList<String>();
	  			ArrayList<String> values3=new ArrayList<String>();
	  			names3.add("student_sum_mark");
	  		 	values3.add(String.valueOf(sum_mark));
	  		 	sub_to_db.write_news(student2, names3, values3,"student_basic_news");// 写如总分 */
	  		 	
	  		 	names3=null;
  		 		values3=null;
  		 	}
  		 	names2=null;
  		 	values2=null;
  		 	
  		 	
  		 }
  		
  	}
  	
  	names=null;
  	values=null;
  
    //计算分数 
  

		
  
  //计算分数 
  	
  
  	out.print(rs);//1 为成功提交 0 为提交失败
  	

%>

