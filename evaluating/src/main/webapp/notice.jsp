<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
<title>注意事项</title>
<link href="images/skin.css" rel="stylesheet" type="text/css" />
<script>

//全选/取消
function checkAll(o,checkBoxName){
	var oc = document.getElementsByName(checkBoxName);
	for(var i=0; i<oc.length; i++) {
		if(oc[i].disabled==false){
		    if(o.checked){
				oc[i].checked=true;	
			}else{
				oc[i].checked=false;	
			}
		}
	}
}
</script>
<style type="text/css">
<!--
* {margin:0 auto;padding:0;}
.lis {
	font-size: medium;
	font-weight: bold;
}
.line {font-size: medium}
.title {
	font-size: x-large;
	font-weight: bold;
}
-->
</style>
</head>
<body style=" min-height:500px" bgcolor="#EEF2FB">
<table class="table_container" width="90%" border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td valign="top" id="top_name">
        	<span class="autol"><span><b>注意事项</b></span><i></i></span> 
    	</td>
	</tr>
	<tr>
    	<td valign="top" class="td_content"><div align="center" class="title">总&nbsp;&nbsp;则 </div>
	      	<p><span class="lis">&nbsp;&nbsp;&nbsp;&nbsp;第一条</span>&nbsp;&nbsp;<span class="line">学生综合素质测评分为基本素质测评和发展素质测评两个方面。基本素质测评由品德测评与课程学习成绩测评两部分组成，各占总评成绩的30%、70%；发展素质测评由能力测评与创新测评两部分组成，直接计入总成绩中。学生素质综合测评成绩按百分制计分。</span> </p>
	     	<p><span class="lis">&nbsp;&nbsp;&nbsp;&nbsp;第二条</span>&nbsp;&nbsp;<span class="line">学生综合素质测评的评定由学院测评领导小组、班级测评小组具体组织实施。学院测评领导小组成员由学院领导各年级辅导员组成；班级测评小组成员由班主任、班长、团支书、学习委员、叁名民主推选的学生代表组成。</span> </p>
	       	<p><span class="lis"><strong>&nbsp;&nbsp;&nbsp;&nbsp;第三条</strong></span>&nbsp;&nbsp;<span class="line">对学生进行综合素质测评，必须在掌握学生大量具体真实测评材料的基础上，本着定性评价和定量测评相结合的原则，采用民主测评和组织测评相结合的方式进行。 </span></p>
    		<p><span class="lis">&nbsp;&nbsp;&nbsp;&nbsp;第四条</span>&nbsp;&nbsp;<span class="line">学生素质综合测评按学年度进行计算、评定（即上一年9月底至当年9月底）。基本素质及发展素质的测评先由学生本人进行自评，经班级测评小组评议，核查后报年级测评小组校对，最终经学院测评领导小组确定审核。学生素质综合测评结果作为评奖评优的基本依据。本办法适用于我院全体本科学生，中途因各种原因休学或停学的学生不参加当年度的综合素质测评。 </span></p>
    		<div align="center"><img src="images/list-example.png" /></div>
    	</td>
	</tr>
</table>
</body>
</html>