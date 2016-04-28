<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@ page language="java" import="dao.*" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title>My JSP 'index.jsp' starting page</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" type="text/css" href="css/tables.css">
	<link href=" images/skin.css" rel="stylesheet" type="text/css" />
	<script src="js/new.js"language="javascript"></script>
	<script type="text/javascript" src="js/jquery.js"></script>
	<script type="text/javascript" src="js/Table.js"></script>
	<script type="text/javascript">
    //将form转为AJAX提交
	function ajaxSubmit(frm, fn) {
		var dataPara = getFormJson(frm);
		//alert("Bengdan");
		$.ajax({
			url: "submit_news.jsp",
			type: frm.method,
			data: dataPara,
			success: fn
		});
	}

	//将form中的值转换为键值对。
	function getFormJson(frm) {
    var o = {};
    var a = $(frm).serializeArray();
    $.each(a, function () {
        if (o[this.name] !== undefined) {
            if (!o[this.name].push) {
                o[this.name] = [o[this.name]];
            }
            o[this.name].push(this.value || '');
        } else {
            o[this.name] = this.value || '';
        }
    });
	
    return o;
	}

	//调用
   $(document).ready(function(){
		$('form').bind('submit', function(){
			ajaxSubmit(this, function(data){
				alert_message(data);	
			});//ajaxSubmit
			//return false;
		}); 
    });
    
    function alert_message(data)
    {
    	var content=document.getElementById("message1_content");
    		content.style.display="block";
    	var message_box=document.getElementById("message1");
    		message_box.style.display="block";
    		message_box.innerHTML="<p>提交中，请稍后。。。</p>";
        var finish_flag=0;
        
        
        
        var time1=setInterval(function(){
        	var hidden = document.getElementById("hidden_frame").contentWindow.document.getElementById("wc");
        
            if(hidden!=null)
            {
                clearInterval(time1);
                finish_flag=1;
            }
        },50);
        var time2=setInterval(function(){
            if(finish_flag==1)
            {
                clearInterval(time2);
                var hidden = document.getElementById("hidden_frame").contentWindow.document.getElementById("wc");
                var return_flag=parseInt(hidden.innerHTML);
               
                if(return_flag==5&&data==1)
               		message_box.innerHTML="<p>提交成功！</p>";
                else
                {         
                	message_box.innerHTML="<p>提交失败！</p>";	
                }
                                    
            }
        },80);
			
    }
 //操作类型提交  
function submitFun1(act,student)
{
    document.getElementById("ways").value=act;
    document.getElementById("proccess").attributes["action"].value=document.getElementById("proccess").attributes["action"].value+"&ids[]="+student;
  //  alert(document.getElementById("proccess").attributes["action"].value);
	document.getElementById("proccess").submit();
}
 
 
function closeButtonM()
{
	var content=document.getElementById("message1_content");
	content.style.display="none";
	window.location.href="manager_table.jsp?student_number=<%=request.getParameter("student_number")%>";
} 
</script>	   

<style>
.buttonM{
	height: 30px;
    width: 300px;
    margin: 0 auto;
    background-color: rgb(63, 71, 65);
    padding-top: 10px;
}

.buttonM input{
    width: 50px;
    margin: 0 auto;
    display: block;
    background-color: white;
    border-style: none;
    height: 20px;
    border-radius: 5px;

}
	</style>
  </head>
  
  <body bgcolor="#EEF2FB">
  <%
  	int reforw=0;
	if(session.getAttribute("nowusername")==null)
	{
		reforw=1;
	}
	else
	{
		String username = (String)session.getAttribute("nowusername");
		
		String student_number=request.getParameter("student_number");
		session.setAttribute("student_number",student_number );
		//如果传入student_number参数为空，返回

		
			Manager_op mop=new Manager_op();
			
			if(!mop.judgeStudentExist(username, student_number))
			{	
				reforw=1;
			};
		
		
	 %>
	<div id='message1_content'>
		<div id="message1" ></div>
		<div class="buttonM"><input type="button" onclick="closeButtonM()" value="确定"></div>	
	</div>
	<table class="table_container" border="0" cellpadding="0" cellspacing="0">
  <tr>
  	<td valign="top" id="top_name">
        <span class="autol"><span><b>填写表格</b></span><i></i></span> 
        </td>
	</tr>
  <tr>
    <td valign="top">
<center>
<!--                              学生个人信息展示                                                                                                     -->
  <div class="flag" style="color: white;height: 30px;width: 100%;background-color: rgb(10, 9, 9); ">
  	<table style=" color: white; ">
		<tbody>
			<tr>
				<td>姓名：</td><td>何雷</td>
				<td class="ceelSpace" style="width: 30px; ">&nbsp;</td>       
				<td>学号:</td>
				<td>201321091074</td>   
				<td class="ceelSpace" style="width: 30px;">&nbsp;</td>   
				<td>专业：</td>       
				<td>计科</td>   
				<td class="ceelSpace" style=" width: 30px; ">&nbsp;</td>       
				<td>班级：</td>       
				<td>1302</td>
				<td class="ceelSpace" style="width: 30px; ">&nbsp;</td>
				<td>状态：</td>
				<td>已通过</td>     
			</tr>   
		</tbody>
	</table> 
</div>
<!--                              学生个人信息展示                                                                       end                              -->

    <%
    	Xml obj = new Xml();
        	obj.SetStudentNumber(student_number);
        	obj.SetXmlPath(request.getRealPath("WEB-INF//Xml//new.xml"));
    %>
    <%=obj.writealert() %>
    <%	obj.SetXmlPath(request.getRealPath("WEB-INF//Xml//table.xml")); %>
    <%=obj.WriteTable() %>
</center></td>
  </tr>
  <tr>
  <td align="center">
     <form name="form2" id='proccess' method="post" action="process.jsp?nowPage=<%=session.getAttribute("nowPage") %>" style="margin:0px; padding:0px">
  	<table><tr>
  		<td><a class="tb_sub" href="center1.jsp?nowPage=<%=session.getAttribute("nowPage") %>">返回</a></td>
  		<td><a class="tb_sub" href="#10" onclick="submitFun1('1','<%=student_number %>')" class=link4>通过</a></td>
  		<td><a class="tb_sub" href="#10" onclick="submitFun1('2','<%=student_number %>')"  class=link4>退回</a></td>
  	</tr></table>
  		<input id="ways" type="hidden" name="action" value="edit">
  	</form>
  </td>
  </tr>
</table>
	<iframe name="hidden" style="display: none;"></iframe>
	<iframe name='hidden1' style="display: none;" id='hidden_frame' src=''></iframe>
	<%} 

	%>
    </body>
</html>