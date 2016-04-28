package dao;
import java.io.*;
import java.util.*;
import org.w3c.dom.*;
import javax.xml.parsers.*;
import dao.Submit_news_to_database;

public class Xml{
	protected int tdn;
	protected String text;
	protected String tablehtml;
	protected String StudentNumber;
	protected Document doc;
	//protected Map<String, String> map;
	protected Map<String,String> massages;
	public enum Type{	//枚举以下类型供switch使用
		text,select,checkbox,textarea,txt,radio,file;
		public static Type getType(String type){
			return valueOf(type.toLowerCase());
		}
	}
	public static void main(String[] args){
		Xml x=new Xml();
		x.SetXmlPath("/Users/anthony/Workspaces/MyEclipse 9/Test_system1/WebRoot/WEB-INF/Xml/new.xml");
		x.GetInputName();
	}
	public Xml(){
		System.out.println("此处调用了xml的无参构造函数");
		tdn = 0;
	}
	public Xml(String xmlpath){	//构造函数 参数为xml绝对路径
		try{
			File f=new File(xmlpath);
			DocumentBuilderFactory factory=DocumentBuilderFactory.newInstance();
			DocumentBuilder builder=factory.newDocumentBuilder();
			doc = builder.parse(f);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	public void SetXmlPath(String xmlpath){	//构造函数 参数为xml绝对路径
		try{
			File f=new File(xmlpath);
			DocumentBuilderFactory factory=DocumentBuilderFactory.newInstance();
			DocumentBuilder builder=factory.newDocumentBuilder();
			doc = builder.parse(f);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	public void SetStudentNumber(String number){
		this.StudentNumber = number;
	}
	
	public ArrayList<String> GetInputName(){
		ArrayList<String> tname = new ArrayList<String>();
		NodeList nl = doc.getElementsByTagName("socia");
		for(int i=0;i<nl.getLength();i++){
			NodeList nlc = nl.item(i).getChildNodes();
			for(int j=0;j<nlc.getLength();j++){
				if(nlc.item(j).getNodeType() == Node.ELEMENT_NODE){
					Element elemt = (Element) nlc.item(j);
					if(!elemt.getAttribute("name").equals("")){
						tname.add(elemt.getAttribute("name"));
					}
				}
			}
		}
		return tname;
	}
	public String WriteTable(){		//返回Table表格的html代码
		tablehtml="";
		NodeList nl = doc.getElementsByTagName("Ceping");
		addrowspan(nl.item(0));
		tablehtml += "<table class='tb_user' border='0'>\n<tr>\n";
		creattable(nl.item(0));
		tablehtml += "</table>";
		return tablehtml;
	}
	private Node creattable(Node nl){	//根据xml生成Table中<tr><td>的Html代码
		Submit_news_to_database dataobj = new Submit_news_to_database();
		try {
			massages = dataobj.getAllKeys(StudentNumber, "student_basic_news");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		tdn++;
		NodeList obj = nl.getChildNodes();
		for(int i=0;i<obj.getLength();i++){
			if(obj.item(i).getNodeType() == Node.ELEMENT_NODE){
				Element child = (Element) obj.item(i);
				tablehtml += "\t<td class='td_"+tdn+"' width='' rowspan='"+child.getAttribute("rowspan")+"' >";
				if(child.getAttribute("sid").equals("") || child.getAttribute("item").equals("false")){
					tablehtml += child.getAttribute("title");
					if(!child.getAttribute("name").equals("") && !child.getAttribute("value").equals("")){
						//tablehtml += "("+child.getAttribute("value")+"分)";
						tablehtml += "</td><td class='td_os'>("+child.getAttribute("value")+"分)";
					}
				}else{
					//tablehtml += "<div id='al_"+child.getAttribute("sid")+"' class='content'><a href='javascript:void(0);' onClick='showalert(\""+child.getAttribute("sid")+"\",\"al_"+child.getAttribute("sid")+"\",\"close_"+child.getAttribute("sid")+"\")'>"+child.getAttribute("title")+"</a></div>";
					String score = massages.get(child.getAttribute("sid")) != null?massages.get(child.getAttribute("sid")):"0";
					tablehtml += ""+child.getAttribute("title")+"</td><td class='td_hvs'><div id='al_"+child.getAttribute("sid")+"' class='content'><a href='javascript:void(0);' onClick='showalert(\""+child.getAttribute("sid")+"\",\"al_"+child.getAttribute("sid")+"\",\"close_"+child.getAttribute("sid")+"\")'><span id='s_"+child.getAttribute("sid")+"'>"+score+"</span>分</a></div>";
				}
				tablehtml += "</td>\n";
				if(child.getAttribute("item").equals("true")){
					tablehtml += "</tr>\n<tr>\n";
				}else{
					creattable(child);
				}
			}
			if(i+1==obj.getLength()){
				tdn--;
			}
		}
		return nl;
	}
	private Node addrowspan(Node nl){	//为Table的xml添加跨行数
		NodeList xmlobj = nl.getChildNodes();
		for(int a=0;a<xmlobj.getLength();a++){
			if(xmlobj.item(a).getNodeType() == Node.ELEMENT_NODE){
				Element echild = (Element) xmlobj.item(a);
				if(!echild.getAttribute("item").equals("true")){
					addrowspan(xmlobj.item(a));
					int num=0;
					for(int b=0;b<xmlobj.item(a).getChildNodes().getLength();b++){
						if(xmlobj.item(a).getChildNodes().item(b).getNodeType() == Node.ELEMENT_NODE){
							Element echild2 = (Element) xmlobj.item(a).getChildNodes().item(b);
							num += Integer.parseInt(echild2.getAttribute("rowspan"));
						}
					}
					echild.setAttribute("rowspan", num+"");
				}else{
					echild.setAttribute("rowspan", "1");
				}
			}
		}
		return nl;
	}
	public String writealert(){		//返回弹出框的Html代码
		Submit_news_to_database dataobj = new Submit_news_to_database();
		try {
			massages = dataobj.getAllKeys(StudentNumber, "student_basic_news");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		text = "<div class='box'>";
		NodeList nl = doc.getElementsByTagName("socia");
		for (int i=0;i<nl.getLength();i++){
			Element socia = (Element) nl.item(i);
			int col = Integer.parseInt(socia.getAttribute("maxcol"));
			text+="<form id='formid"+i+"' name='formid"+i+"' method='post' target='hidden1' action='FileUploadServlet?fileName=zigefile' Enctype=multipart/form-data >";
			//text += "<form>";
			text += "<input type='hidden' name='scoretitle' value='"+socia.getAttribute("title")+"' />";
			text +="<div id='"+socia.getAttribute("title")+"' class='alert'><h4><span>"+socia.getAttribute("name")+"</span></h4><table border='0px' cellpadding='0px' cellspacing='10px'><tr>";
			NodeList nlc = nl.item(i).getChildNodes();
			int rel = 1;
			int num = 0;
			boolean isfirst = true;
			int starttag = 0;
			Element ParentLdoc = null;
			for(int j=0;j<nlc.getLength();j++){
				if(nlc.item(j).getNodeType() == Node.ELEMENT_NODE){
					//Element enlc = (Element) nlc;
					Element Ldoc = (Element) nlc.item(j);
					Ldoc.setAttribute("nownum", num+"");
					if(isfirst){
						isfirst = false;
						starttag = j;
					}
					if(Integer.parseInt(Ldoc.getAttribute("relevance")) != rel){
						num++;
						if(ParentLdoc.getAttribute("more").equals("true")){
							text += "<a onclick='delIndex(this)'><div class='btn_remove'></div></a><a onclick='add(this,\"-1\")'><div class='btn_add'></div></a>";
						}
						text+="</td></tr>";
						if(!ParentLdoc.getAttribute("name").equals("msg") && massages.get(ParentLdoc.getAttribute("name")) != null && massages.get(ParentLdoc.getAttribute("name")).split("\\|").length>num){
							j = starttag-1;
							col = Integer.parseInt(socia.getAttribute("maxcol"));
							continue;
						}else{
							//isfirst = true;
							starttag = j;
							num = 0;
							Ldoc.setAttribute("nownum", num+"");
						}
						text+="</table><table border='0px' cellpadding='0px' cellspacing='10px'><tr>";
						col = Integer.parseInt(socia.getAttribute("maxcol"));
					}
					if(col == 0){
						text+="</td></tr><tr>";
						col = Integer.parseInt(socia.getAttribute("maxcol"));
					}
					String type=Ldoc.getAttribute("type");
					switch(Type.getType(type))
					{
						case text: text+="<td>"+write1(Ldoc)+"";
							break;
						case select: text+="<td>"+write2(Ldoc)+"";
							break;
						case radio: text+="<td>"+write3(Ldoc)+"";
							break;
						case file: text+="<td>"+write4(Ldoc)+"";
							break;
						case checkbox: text+="<td>"+write5(Ldoc)+"";
							break;
						case textarea: text+="<td>"+write6(Ldoc)+"";
							break;
						case txt: text+="<td align='center'>"+write7(Ldoc)+"";
							break;
						default:
							break;
					}
					col--;
					rel = Integer.parseInt(Ldoc.getAttribute("relevance"));
					ParentLdoc = (Element) nlc.item(j);
				}
			}
			text+="</tr></table>";
			text+="<p><input id='close_"+socia.getAttribute("title")+"' type='submit' value='提交' class='sub' /><input id='close_"+socia.getAttribute("title")+"_c' type='button' value='取消' class='sub' /></p><br>";
			text+="</form>";
			text+="</div>";
		}
		text += "</div>";
		return text;
		
	}
	public String write1(Element Ldoc){	//文本框
		String text = "";
		if(massages.get(Ldoc.getAttribute("name")) == null) {
			text = Ldoc.getTextContent()+": "+"<input class='text' type='text' name='"+Ldoc.getAttribute("name")+"' value='"+Ldoc.getAttribute("default")+"' placeholder='"+Ldoc.getAttribute("tips")+"' maxlength='"+Ldoc.getAttribute("maxlen")+"' size='"+Ldoc.getAttribute("size")+"' />"+Ldoc.getAttribute("unit");
		}else{
			String[] every = massages.get(Ldoc.getAttribute("name")).split("\\|");//System.out.println("Every:"+every.length);
			int nownum = Integer.parseInt(Ldoc.getAttribute("nownum"));//System.out.println("NowNum:"+nownum);
			text = Ldoc.getTextContent()+": "+"<input class='text' type='text' name='"+Ldoc.getAttribute("name")+"' value='"+every[nownum]+"' placeholder='"+Ldoc.getAttribute("tips")+"' maxlength='"+Ldoc.getAttribute("maxlen")+"' size='"+Ldoc.getAttribute("size")+"' />"+Ldoc.getAttribute("unit");
		}
		return text;
	}
	public String write2(Element Ldoc){	//选择框
		String[] values = Ldoc.getAttribute("default").split("\\|");
		String text = Ldoc.getTextContent()+": ";
		text += "<select class='select' name='"+Ldoc.getAttribute("name")+"' style='width:"+Ldoc.getAttribute("size")+"px;' >";
		int nownum = Integer.parseInt(Ldoc.getAttribute("nownum"));
		for(int i=0;i<values.length;i++){
			if(massages.get(Ldoc.getAttribute("name")) != null){
				String[] every = massages.get(Ldoc.getAttribute("name")).split("\\|");
				if(every[nownum] != null && every[nownum].equals(values[i])) {
					text += "<option selected value='"+values[i]+"'>"+values[i]+"</option>";
				}else{
					text += "<option value='"+values[i]+"'>"+values[i]+"</option>";
				}
			}else{
				text += "<option value='"+values[i]+"'>"+values[i]+"</option>";
			}
		}
		text += "</select>";
		return text;
	}
	public String write3(Element Ldoc){	//单选框
		String chek = "";
		String[] values = Ldoc.getAttribute("default").split("\\|");
		String text = Ldoc.getTextContent()+": ";
		for(int i=0;i<values.length;i++){
			if(massages.get(Ldoc.getAttribute("name")) != null && massages.get(Ldoc.getAttribute("name")).equals(values[i])) {
				chek = "<input type='radio' checked name='"+Ldoc.getAttribute("name")+"' value='"+values[i]+"'>"+values[i];				
			}else{
				chek = i!=0?"<input type='radio' name='"+Ldoc.getAttribute("name")+"' value='"+values[i]+"'>"+values[i]:"<input type='radio' checked name='"+Ldoc.getAttribute("name")+"' value='"+values[i]+"'>"+values[i];
				//chek = "<input type='radio' name='"+Ldoc.getAttribute("name")+"' value='"+values[i]+"'>"+values[i];
			}
			text += chek;
		}
		return text;
	}
	public String write4(Element Ldoc){	//附件框
		String text = Ldoc.getTextContent()+": "+"<input type='file' style='width: 170px;' name='"+Ldoc.getAttribute("name")+"' value='"+Ldoc.getAttribute("default")+"' />";
		if(massages.get(Ldoc.getAttribute("name")) != null){
			text += "<a href='"+massages.get(Ldoc.getAttribute("name"))+"' target='_blank' style='font-size: 15px;'>查看已上传</a>";
		}
		return text;
	}
	public String write5(Element Ldoc){	//checkbox
		String text = "";
		if(massages.get(Ldoc.getAttribute("name")) == null) {
			text = Ldoc.getTextContent()+": "+"<input type='checkbox' name='"+Ldoc.getAttribute("name")+"' />";
		}else if(massages.get(Ldoc.getAttribute("name")).equals("yes")){
			text = Ldoc.getTextContent()+": "+"<input type='checkbox' checked name='"+Ldoc.getAttribute("name")+"' />";
		}else{
			text = Ldoc.getTextContent()+": "+"<input type='checkbox' name='"+Ldoc.getAttribute("name")+"' />";
		}
		return text;
	}
	public String write6(Element Ldoc){	//TextArea
		String text = "";
		if(massages.get(Ldoc.getAttribute("name")) == null) {
			text = Ldoc.getTextContent()+"<textarea rows='"+Ldoc.getAttribute("rows")+"' cols='"+Ldoc.getAttribute("cols")+"' name='"+Ldoc.getAttribute("name")+"' placeholder='"+Ldoc.getAttribute("default")+"'></textarea>";
		}else{
			text = Ldoc.getTextContent()+"<textarea rows='"+Ldoc.getAttribute("rows")+"' cols='"+Ldoc.getAttribute("cols")+"' name='"+Ldoc.getAttribute("name")+"' placeholder='"+Ldoc.getAttribute("default")+"'>"+massages.get(Ldoc.getAttribute("name"))+"</textarea>";
		}
		return text;
	}
	public String write7(Element Ldoc){	//提示分隔
		String text = "<span style='"+Ldoc.getAttribute("style")+"'>"+Ldoc.getTextContent()+"</span>";
		return text;
	}
} 