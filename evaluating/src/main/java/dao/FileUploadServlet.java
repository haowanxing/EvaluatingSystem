package dao;
//13092878367

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.FileUploadBase.SizeLimitExceededException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;


public class FileUploadServlet extends HttpServlet {

 /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

public FileUploadServlet() {
  super();
 }

 public void destroy() {
  super.destroy(); // Just puts "destroy" string in log
  // Put your code here
 }

 public void doGet(HttpServletRequest request, HttpServletResponse response)
   throws ServletException, IOException {
  doPost(request, response);
 }

 @SuppressWarnings({ "deprecation", "rawtypes", "unused", "static-access" })
public void doPost(HttpServletRequest request, HttpServletResponse response)
   throws ServletException, IOException {
	 
	 PrintWriter out = response.getWriter();
	    out.print("<!doctype html>");
	    out.print("<html lang='en'>");
	    out.print("<head>");
	    out.print("<meta charset='UTF-8'>");

	    out.print("<title>Document</title>");
	    out.print("</head>");
	    out.print("<body>");
	 
	 HttpSession session=request.getSession(true);
	  
	 String student=(String)session.getAttribute("nowusername");
	 String student2=(String)session.getAttribute("student_number");
	  if(student==null)
	  {
	  	out.println("<p>请重新登陆！3秒后自动跳转...</p>");
		response.setHeader("REFRESH","3;URL=Login.jsp");
	  }
	  else
	  {
	  	String flag=(String)session.getAttribute("access");//判断用户类型  学生 or 上级
	  		if(flag.equals("1001"))
	  		{
	  			Items temp=new Items();
			    temp.setTable("student");
			    temp.setFiled_name("user_number");
			    int fill_access = 5;
				try {
					fill_access = Integer.parseInt(temp.getFieldValue("Sfill_access",(String)session.getAttribute("nowusername")));
				} catch (NumberFormatException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			    if(fill_access==1)
			    {
			    	
				    out.println("<div id='wc'>"+2+"</div>");	
					   
				    out.print("</body>");
				    out.print("</html>");

			    	return ;
			    }
	  		}
	  		else
	  		{
	  			
	  			Items temp=new Items();
			    temp.setTable("manager_form");
			    temp.setFiled_name("user_number");
			    int fill_accessB = 5;
				try {
					fill_accessB = Integer.parseInt(temp.getFieldValue("role",(String)session.getAttribute("nowusername")));
				} catch (NumberFormatException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			    
			    temp.setTable("student");
			    temp.setFiled_name("user_number");
			    int fill_accessS = 5;
				try {
					fill_accessS = Integer.parseInt(temp.getFieldValue("now_role",(String)session.getAttribute("student_number")));
				} catch (NumberFormatException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			    
			    if(fill_accessB!=fill_accessS)
			    {
				    out.println("<div id='wc'>"+2+"</div>");	
					   
				    out.print("</body>");
				    out.print("</html>");
				  
				    return;
			    	
			    }
	  		}
	  }
	 
	    
		Items item=new Items();
		item.setTable("student_basic_news");
		item.setFiled_name("user_number");
  
	 
	 
	 
  response.setContentType("text/html");
  // 设置字符编码为UTF-8, 这样支持汉字显示
  response.setCharacterEncoding("UTF-8");  
  
  final long MAX_SIZE = 3 * 1024 * 1024;// 设置上传文件最大为 3M
  // 允许上传的文件格式的列表
  final String[] allowedExt = new String[] {"zip", "jpg", "jpeg", "gif", "txt",
    "doc", "docx", "mp3", "wma", "m4a","png" };
  response.setContentType("text/html");
  // 设置字符编码为UTF-8, 这样支持汉字显示
  response.setCharacterEncoding("UTF-8");
  request.setCharacterEncoding("utf-8");

  // 实例化一个硬盘文件工厂,用来配置上传组件ServletFileUpload
  DiskFileItemFactory dfif = new DiskFileItemFactory();
  dfif.setSizeThreshold(4096);// 设置上传文件时用于临时存放文件的内存大小,这里是4K.多于的部分将临时存在硬盘
  dfif.setRepository(new File(request.getSession().getServletContext().getRealPath("/")+"ImagesUploadTemp"));// 设置存放临时文件的目录,web根目录下的ImagesUploadTemp目录
  


  // 用以上工厂实例化上传组件
  ServletFileUpload sfu = new ServletFileUpload(dfif);
  // 设置最大上传尺寸
  sfu.setSizeMax(MAX_SIZE);

  
  // 从request得到 所有 上传域的列表
  
  List fileList = null;
  try 
  {
   fileList = sfu.parseRequest(request);//
  } 
  catch (FileUploadException e) 
  {// 处理文件尺寸过大异常
	  if (e instanceof SizeLimitExceededException) 
	  {
	    System.out.println("文件尺寸超过规定大小:" + MAX_SIZE + "字节<p />");
	   
	    out.println("<div id='wc'>"+1+"</div>");	
		   
	    out.print("</body>");
	    out.print("</html>");
	  
	    return;
   }
   e.printStackTrace();
  }
  // 没有文件上传
  if (fileList == null || fileList.size() == 0) 
  {
	  System.out.println("请选择上传文件<p />");
	 
	    out.println("<div id='wc'>"+5+"</div>");	
		   
	    out.print("</body>");
	    out.print("</html>");
	  
	  return;
  }
  // 得到所有上传的文件
  Iterator fileItr = fileList.iterator();
  // 循环处理所有文件
  while (fileItr.hasNext()) 
  {
   FileItem fileItem = null;
   String path = null;
   long size = 0;
   // 得到当前文件
   fileItem = (FileItem) fileItr.next();
   // 忽略简单form字段而不是上传域的文件域(<input type="text" />等)
   if (fileItem == null || fileItem.isFormField()) 
   {
    continue;
   }
   // 得到文件的完整路径
   path = fileItem.getName();
   // 得到文件的大小
   size = fileItem.getSize();
   if ("".equals(path) || size == 0) 
   {
	   System.out.println("请选择上传文件<p />");
	    out.println("<div id='wc'>"+5+"</div>");	
		   
	    out.print("</body>");
	    out.print("</html>");
    
	   return;
   }

   // 得到去除路径的文件名
   String t_name = path.substring(path.lastIndexOf("\\") + 1);
   // 得到文件的扩展名(无扩展名时将得到全名)
   String t_ext = t_name.substring(t_name.lastIndexOf(".") + 1);
   // 拒绝接受规定文件格式之外的文件类型
   int allowFlag = 0;
   int allowedExtCount = allowedExt.length;
//   for (; allowFlag < allowedExtCount; allowFlag++) 
//   {
//	    if (allowedExt[allowFlag].equals(t_ext))
//	    	break;
//   }
//   if (allowFlag == allowedExtCount) 
//   {
//	   out.println("请上传以下类型的文件<p />");
//	   for (allowFlag = 0; allowFlag < allowedExtCount; allowFlag++)
//		   out.println("*." + allowedExt[allowFlag]+ "&nbsp;&nbsp;&nbsp;");
//    
//    	out.println("<p /><a href=\"upload.html\" target=\"_top\">返回</a>");
//    return;
//   }

   long now = System.currentTimeMillis();
   // 根据系统时间生成上传后保存的文件名

   String name=(String)request.getParameter("fileName");
   String prefix =String.valueOf(now);
   
   String oldFilePath = null;
try {
	oldFilePath = item.getFieldValue(name, student);
} catch (SQLException e1) {
	// TODO Auto-generated catch block
	e1.printStackTrace();
}
   boolean flag=CreateFileUtil.deleteFile(request.getRealPath("/")+oldFilePath);
   System.out.println("删除文件状态："+flag+"\n");
   
   // 保存的最终文件完整路径,保存在web根目录下的ImagesUploaded目录下
   
   String QS=request.getSession().getServletContext().getRealPath("/");
   System.out.println("request.getSession().getServletContext().getRealPath('/')"+QS);;
   String u_name = QS+"ImagesUploaded/"+student+prefix + "." + t_ext;
   System.out.print("热了狗："+u_name);
  // String u_name = System.getenv("MOPAAS_FILESYSTEM24313_LOCAL_PATH")+"/"+System.getenv("MOPAAS_FILESYSTEM24313_NAME")+"/"+student+prefix + "." + t_ext;
   
	   try 
	   {
	    // 保存文件
		CreateFileUtil factorFile=new CreateFileUtil();
		factorFile.CreateFile(u_name);
	    fileItem.write(new File(u_name));
	    System.out.print(u_name);
	    System.out.println("文件上传成功. 已保存为: " + prefix + "." + t_ext+ " &nbsp;&nbsp;文件大小: " + size + "字节<p />");
	    
	    out.println("<div id='wc'>"+5+"</div>");	   
	    out.print("</body>");
	    out.print("</html>");
	    
	    //构造入口参数
	    
	    ArrayList<String> names=new ArrayList<String>();
	    ArrayList<String> values=new ArrayList<String>();
	    
	    names.add(name);
	    values.add("ImagesUploaded/"+ student+prefix + "." + t_ext);
	    
	    Submit_news_to_database sub_to_db=new Submit_news_to_database(); 
	    
	    	int rs=0;
	    	if(student2==null)
	    	{
	    		 rs=sub_to_db.write_news(student, names, values,"student_basic_news");
	    	}
	    	else
	    	{
	    		rs=sub_to_db.write_news(student2, names, values,"student_basic_news");
	    	}
	    
	    
	    
	   } catch (Exception e) 
	   {
	    e.printStackTrace();
	   }
	   
	   return ;

  }
  
  out.println("<div id='wc'>"+5+"</div>");	
  
  out.print("</body>");
  out.print("</html>");

 return;

 }

 public void init() throws ServletException {
  // Put your code here
 }

}