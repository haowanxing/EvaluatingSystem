package function;

public class Page {
	protected String html;
	public Page(){
		this.html = new String("");
	}
	
	public String GetPage(int datasum,int shownum,int p,String url,String tag){
		int PageCount = (datasum%shownum==0)?(datasum/shownum):(datasum/shownum+1);
		if(url.indexOf("?")==-1){
			url += "?";
		}
		url = url.replaceAll("("+tag+"=[^&]*)", "");
		if(url.indexOf("&") != -1 && url.charAt(url.length()-1)!='&')
			url+="&";
		this.html += "<span class='disabled'>"+((p-1)*shownum+1)+"-"+((p*shownum)>datasum?datasum:p*shownum)+"/"+datasum+" 记录</span><span class='disabled'>"+p+"/"+PageCount+"页</span>";
		if(PageCount != 0 && p != 1){
			html += "<a href='"+url+tag+"=1'>首页</a><a href='"+url+tag+"="+(p-1)+"'><</a>";
		}
		if(p-4 > 1){
			this.html += "<a href='"+url+tag+"=1'>1</a>...";
			for(int a=3;a>0;a--){
				this.html += "<a href='"+url+tag+"="+(p-a)+"'>"+(p-a)+"</a>";
			}
			this.html += "<span class='current'>"+p+"</span>";
		}else{
			for(int a=1;a<p;a++){
				this.html += "<a href='"+url+tag+"="+a+"'>"+a+"</a>";
			}
			this.html += "<span class='current'>"+p+"</span>";
		}
		if(p+4 < PageCount){
			for(int a=p+1;a<p+4;a++){
				this.html += "<a href='"+url+tag+"="+a+"'>"+a+"</a>";
			}
			this.html += "...<a href='"+url+tag+"="+PageCount+"'>"+PageCount+"</a>";
		}else{
			for(int a=p+1;a<=PageCount;a++){
				this.html += "<a href='"+url+tag+"="+a+"'>"+a+"</a>";
			}
		}
		if(PageCount != 0 && p!=PageCount){
			this.html += "<a href='"+url+tag+"="+(p+1)+"'>></a><a href='"+url+tag+"="+PageCount+"'>尾页</a>";
		}
		if(PageCount != 0 && PageCount != 1){
			this.html += "跳至<select name='topage' size='1' onchange='window.location=\""+url+tag+"=\"+this.value'>";
			if(p>10){
				for(int a=p-9;a<p;a++)
					this.html += "<option value='"+a+"'>"+a+"</option>";
				this.html += "<option value='"+p+"' selected>"+p+"</option>";
			}else{
				for(int a=1;a<p;a++)
					this.html += "<option value='"+a+"'>"+a+"</option>";
				this.html += "<option value='"+p+"' selected>"+p+"</option>";
			}
			if(p+9<PageCount){
				for(int a=p+1;a<p+9;a++)
					this.html += "<option value='"+a+"'>"+a+"</option>";
				if(p==1)
					this.html += "<option value='"+PageCount+"'>"+PageCount+"</option>";
			}else{
				for(int a=p+1;a<=PageCount;a++)
					this.html += "<option value='"+a+"'>"+a+"</option>";
			}
			this.html += "</select>页";
		}
		return this.html;
	}
	
	//测试函数
	public static void main(String[] ag){
		String url = "http://localhost:8080/Test_system1/msg.jsp?page=21&else=sa";
		System.out.println(url.indexOf("?"));
		System.out.println(url.replaceAll("(page=[^&]*)", ""));
		if(url.indexOf("?")==-1){
			url += "?";
		}
		url = url.replaceAll("(page=[^&]*)", "");
		if(url.charAt(url.length()-1)!='&')
			url+="&";
		System.out.println("<a href='"+url+"page=1'>1</a>");
	}
}
