// JavaScript Document
var mark_error = 0;

function delIndex(obj) {
	var rowIndex = obj.parentNode.parentNode.rowIndex; // 获得行下标
	// alert(rowIndex);
	var tb = obj.parentNode.parentNode.parentNode;
	if (tb.rows.length < 2) {
	  //alert('亲,删了就没有了哦！');
	  return false;
	}
	tb.deleteRow(rowIndex); // 删除当前行
	// add(rowIndex);//在当前行插入一行
}
//新增行

function add(obj,rowIndex) {
	var tb = obj.parentNode.parentNode.parentNode;
	var tr = obj.parentNode.parentNode;
	if (rowIndex == '-1') {
		rowIndex = tb.rows.length; //默认在末尾插入一行
	}
	var row = tb.insertRow(rowIndex); //在表格的指定插入一行
	row.innerHTML = tr.innerHTML;
}
function preview(fileid) {
	var x = document.getElementById(fileid);
	if (!x || !x.value)
		return;
	var patn = /\.jpg$|\.png$|\.jpeg$|\.gif$/i;
	if (patn.test(x.value)) {
		return;
	} else {
		alert("您选择的似乎不是图像文件。");
	}
}

function alert_message()
{
		var message_box=document.getElementById("message1");
    var finish_flag=0;
    var time1=setInterval(function(){
    var hidden = document.getElementById("hidden_frame").contentWindow.document.getElementById("wc");
    
        if(hidden!=null)
        {
            clearInterval(time1);
            finish_flag=1;
        }
    })
    var time2=setInterval(function(){
        if(finish_flag==1)
        {
            clearInterval(time2);
            var hidden = document.getElementById("hidden_frame").contentWindow.document.getElementById("wc");
            var return_flag=parseInt(hidden.innerHTML);
           // alert(return_flag);
            if(return_flag==5)
           	message_box.innerHTML="<p>文件上传成功！</p>";
            else
            message_box.innerHTML="<p>文件上传失败！</p>";
            
            setTimeout(function(){
				message_box.style.display="hidden";
				window.location.href="table.jsp";
        		},1000);
   
           
        }
    });
		
}