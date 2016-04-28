<%@ page language="java" import="java.util.*,function.*,java.sql.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
System.out.println("登录页！！");
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
  
    <title>thinks 测评系统登录</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<link href="css/login-box.css" rel="stylesheet" type="text/css" />
	<script src="js/new.js"language="javascript"></script>
	<script type="text/javascript" src="js/jquery.js"></script>
	<script type="text/javascript">
	
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
	//alert(o);
    return o;
	}
	
	
    //将form转为AJAX提交
	function ajaxSubmit(frm) {
		var dataPara = getFormJson(frm);
		//alert("Bengdan");
		$.ajax({
			url: "examine_user.jsp",
			type: frm.method,
			data: dataPara,
			dataType:"text",
			success: function(mess){
                    var flag=parseInt(mess);
                    	//alert(mess);
                    var message;
                    switch (flag)
                    {
                        case 1 : ;
                        case 2 : ;
                        case 3 : message='登录成功！！';
                        		
                        		setTimeout(function()
                       			 {
									window.location.href="index1.jsp"; 

                       			 },1000);	
                        		break;
                        case 4 : message='用户名或密码错误！！';break;
                        case 5 : message='内部错误，请稍候再试！！';break;
                    }

                   document.getElementById("message").innerHTML=message;
                }
		});
	}


	//调用
        $(document).ready(function(){
            $('form').bind('submit', function(){
                ajaxSubmit(this);
                return false;
            });


        });
    </script>
  </head>
  
  <body>
  <%
	if(session.getAttribute("nowusername")!=null)
	{
		out.println("<p>您已经登陆，请稍后！</p>");
		response.setHeader("REFRESH","0;URL=index1.jsp");
	}else{
	 %>
	<div id="login" style="margin:100px auto 0px; width:475px">
    <div id="login-box">
        <H1 align="center">综合素质测评系统</H1><br />
        <span id="login-tips">请使用您的学号或者工号登录本系统.</span>
        <br />
        <br />
		<form name="form1" method="post" action="">
        <div id="login-box-name" style="margin-top:20px;">学号/工号:</div>
        <div id="login-box-field" style="margin-top:20px;">
        <input name="username" class="form-login" id="Username" value="" size="30" maxlength="12" />
        </div>
      <div id="login-box-name">口令:</div>
        <div id="login-box-field">
        <input name="userpassword" type="password" class="form-login" title="Password" value="" size="30" maxlength="1024" />
        </div>
        <div id='message' style="float:right; margin: 0px; padding:0px; color:red;"></div>
        <!--<a href="#"><img src="images/login-btn.png" width="120" height="45" style="margin-left:90px;" /></a>-->
        <input id="enter" name="SuBmit" type="image" value="登录" src="images/login_bot.png" width="120" height="45" style="margin: 10px 0px 0px 220px;" />
    	</form>
    </div>
	<div id="login-bottom">
		<p align="center" style="font-size: 13px;">技术工作组：创明软件工作室－Thinks团队</p>
	</div>
</div>
<script type="text/javascript">
window.onload = function() {
    document.getElementById("Username").focus();
}
</script>
  <%} %>
  </body>
</html>