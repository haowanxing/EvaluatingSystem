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
	<link href="images/skin.css" rel="stylesheet" type="text/css" />
	<script src="http://libs.useso.com/js/jquery/1.9.1/jquery.min.js" type="text/javascript"></script>
	<script type="text/javascript" src="js/new.js"></script>
	<script type="text/javascript" src="js/jquery.js"></script>
	<script type="text/javascript" src="js/Table.js"></script>
	<script type="text/javascript">
    //将form转为AJAX提交
	function ajaxSubmit(frm, fn) {

		var message_box=document.getElementById("message1");
     		message_box.style.display="block";
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
/*             	alert(data);
            	 var aatr_data=data.split("|");
                  data=aatr_data[0];// 处理结果标志
                  alert(data);
                 var mark=aatr_data[1];//分数
                
                 alert(mark);
                 var look_mark=data[2];//分数id
                 alert(look_mark);
                  */
                 
                clearInterval(time2);
                var hidden = document.getElementById("hidden_frame").contentWindow.document.getElementById("wc");
                var return_flag=parseInt(hidden.innerHTML);
               
                if(return_flag==5&&data==1)
                {
               		message_box.innerHTML="<p>提交成功！</p>";

               	}
                else
                {         
                	message_box.innerHTML="<p>提交失败！</p>";	
                }
                                             
            }
        },80);
			
    }
  
    function closeButtonM()
    {
    	var content=document.getElementById("message1_content");
		content.style.display="none";
		window.location.href="table.jsp?student_number=<%=request.getParameter("student_number")%>";
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
  <%//HttpSession session1=request.getSession(true);
	if(session.getAttribute("nowusername")==null)
	{
	%>
		<script>
		window.location.href="Login.jsp";
		</script>
	<% 
	}else{
		String username = session.getAttribute("nowusername").toString();
	 %>
	<div id='message1_content'>
		<div id="message1" ></div>
		<div class="buttonM"><input type="button" onclick="closeButtonM()" value="确定"></div>
	</div>
	
	<table class="table_container" width="90%" border="0" cellpadding="0" cellspacing="0">
  	<tr>
		<td valign="top" id="top_name" >
        <span class="autol"><span><b>填写表格</b></span><i></i></span> 
        </td>
	</tr>
  <tr>
    <td valign="top">
<center>
    <%
    	Xml obj = new Xml();
        	obj.SetStudentNumber(username);
        	obj.SetXmlPath(request.getRealPath("WEB-INF//Xml//new.xml"));
    %>
    <%=obj.writealert() %>
    <%	obj.SetXmlPath(request.getRealPath("WEB-INF//Xml//table.xml")); %>
    <%=obj.WriteTable() %>
    <a class="tb_sub" href="Contrl/submit_flag.jsp?submit=1&type=stu&ids[]=<%=session.getAttribute("nowusername") %>">提交</a>
</center></td>
  </tr>
</table>
<iframe name="hidden" style="display: none;"></iframe>
<iframe name='hidden1' style="display: none;" id='hidden_frame' src=''></iframe>
	<script>
		jQuery(document).ready(function() {
			$('h4').mousedown(function(event) {
				var parent = $(this).parent();
				var pid = parent.attr("id");
				var isMove = true;
				var abs_x = event.pageX - $('div#'+pid).offset().left;
				var abs_y = event.pageY - $('div#'+pid).offset().top;
				$(document).mousemove(function(event) {
					if (isMove) {
						var obj = $('div.alert');
						obj.css({
							'left' : event.pageX - abs_x,
							'top' : event.pageY - abs_y
						});
					}
				}).mouseup(function() {
					isMove = false;
				});
			});
		});
	</script>
<%} %>
    </body>
</html>