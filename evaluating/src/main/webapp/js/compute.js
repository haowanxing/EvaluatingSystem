function getXml(methed,name) //获取xml文档Dom
{

if (window.XMLHttpRequest)
  {// code for IE7+, Firefox, Chrome, Opera, Safari
  xmlhttp=new XMLHttpRequest();
  }
else
  {// code for IE6, IE5
  xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
  }

xmlhttp.open(methed,name,false);
xmlhttp.send();
xmlDoc=xmlhttp.responseXML;

return xmlDoc;
}

function docompute(alxml,dataxml){
	alrxml = getXml("GET",alxml);
	data = getXml("GET",dataxml);
	dom1 = alrxml.getElementsByTagName("socia");
	dom2 = data.getElementsByTagName("Item");
	for(var i=0;i<dom1.length;i++){
		//alert(dom1[i].childNodes[1].attributes['name'].value);
		var sid = dom1[i].attributes['title'].value;
		var rel = 1;
		var tname = null;
		var score = 0;
		item = dom1[i].getElementsByTagName("item");
		for(var a=0;a<item.length;a++){
			if(rel != item[a].attributes['relevance'].value){
				
			}
			tname = item[a].attributes['name'].value;
			var input = document.getElementsByTagName("input");
			for(var inp=0;inp<input.length;inp++){
				if(input[inp].name == tname){
					tvalue = input[inp].value;	//获取用户填写的值
					for(var b=0;b<dom2.length;b++){
						alert(dom2[b].attributes['sid'].value);
						if(sid == dom2[b].attributes['sid'].value){	//通过sid对应数据
							var scoredom = dom2[b].getElementByTagName('score');	//获取分数节点
							alert("21sss");
							score += getscore(scoredom,tvalue);
							var classdom = dom2[b].getElementByTagName('Class');
							for(var bb=0;bb<classdom.length;bb++){
								scoredom = classdom[bb].getElementByTagName('score');	//获取分数节点
								score += getscore(scoredom,tvalue);
							}
						}
					}
				}
			}
			rel = item[a].attributes['relevance'].value;
		}
		document.getElementById("s_"+sid).innerHTML = score;
		score = 0;
	}
}
function getscore(scoredom,tvalue){
	var score = 0;
	for(var ba=0;ba<scoredom.length;bb++){
		if(tvalue == scoredom[ba].attributes['name'].value){
			score +=scoredom[ba].value;
			alert(scoredom[ba].attributes['name'].value+score);
		}
	}
	return score;
}