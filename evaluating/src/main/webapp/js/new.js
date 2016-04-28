// JavaScript Document
//
//function getXml(methed,name) //获取xml文档Dom
//{
//
//if (window.XMLHttpRequest)
//  {// code for IE7+, Firefox, Chrome, Opera, Safari
//  xmlhttp=new XMLHttpRequest();
//  }
//else
//  {// code for IE6, IE5
//  xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
//  }
//
//xmlhttp.open(methed,name,false);
//xmlhttp.send();
//xmlDoc=xmlhttp.responseXML;
//
//return xmlDoc;
//}

//弹出层函数
function showalert(mAlert, mLink, mclose, inner, biao, num) {
    var myAlert = document.getElementById(mAlert);
    var reg = document.getElementById(mLink).getElementsByTagName("a")[0];
    var mClose = document.getElementById(mclose);
    var mClose_c = document.getElementById(mclose+"_c");
    
        myAlert.style.display = "block";
        myAlert.style.position = "absolute";
        myAlert.style.top = "20%";
        myAlert.style.left = "20%";
//        myAlert.style.marginTop = "-170px";
//        myAlert.style.marginLeft = "-260px";

        mybg = document.createElement("div");
        mybg.setAttribute("id", "mybg");
        mybg.style.background = "#000";
        mybg.style.width = "100%";
        mybg.style.height = "100%";
        mybg.style.position = "absolute";
        mybg.style.top = "0";
        mybg.style.left = "0";
        mybg.style.zIndex = "500";
        mybg.style.opacity = "0.7";
        mybg.style.filter = "Alpha(opacity=30)";
        document.body.appendChild(mybg);

        document.body.style.overflow = "hidden";
    

        mClose.onclick = function() {
        	CheckAllCheckbox();
            myAlert.style.display = "none";
            mybg.style.display = "none";
        }
        mClose_c.onclick = function() {
            myAlert.style.display = "none";
            mybg.style.display = "none";
        }
}
function CheckAllCheckbox()
{
	var inputs = document.getElementsByTagName("input");
    for(var i=0;i<inputs.length;i++)
    {
        if(inputs[i].type=='checkbox')   
        { 
        	if(inputs[i].checked){
        		inputs[i].value = "yes";
        	}else{
        		inputs[i].value = "no";
        		inputs[i].checked = "true";
        	}
        	//document.getElementById(document.all[i].id).checked=document.getElementById("chkAll").checked;
        }
    }
}