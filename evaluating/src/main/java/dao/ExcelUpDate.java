package dao;

import dBassiant.Mysql_operation;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadBase;
import org.apache.commons.fileupload.FileUploadBase.SizeLimitExceededException;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

public class ExcelUpDate extends HttpServlet
{
  public void destroy()
  {
    super.destroy();
  }

  public String upFileSingle(HttpServletRequest request, HttpServletResponse response, String fPath)
    throws IOException
  {
	  response.setCharacterEncoding("UTF-8");
    PrintWriter out = response.getWriter();
    String prefix = null;
    String t_ext = null;

    long MAX_SIZE = 5242880L;
    String[] allowedExt = { "xls" };

    DiskFileItemFactory dfif = new DiskFileItemFactory();

    dfif.setSizeThreshold(4096);

    dfif.setRepository(new File(fPath));

    ServletFileUpload sfu = new ServletFileUpload(dfif);

    sfu.setSizeMax(5242880L);

    List fileList = null;
    try
    {
      fileList = sfu.parseRequest(request);
    }
    catch (FileUploadException e)
    {
      if ((e instanceof FileUploadBase.SizeLimitExceededException))
      {
        System.out.println("文件尺寸超过规定大小:5242880字节<p />");
        return null;
      }
      e.printStackTrace();
    }

    if ((fileList == null) || (fileList.size() == 0))
    {
      System.out.println("请选择上传文件<p />");
      return null;
    }

    Iterator fileItr = fileList.iterator();

    while (fileItr.hasNext())
    {
      FileItem fileItem = null;
      String path = null;
      long size = 0L;

      fileItem = (FileItem)fileItr.next();

      if ((fileItem != null) && (!fileItem.isFormField()))
      {
        path = fileItem.getName();

        size = fileItem.getSize();

        if (("".equals(path)) || (size == 0L))
        {
          System.out.println("请选择上传文件<p />");
          return null;
        }

        String t_name = path.substring(path.lastIndexOf("\\") + 1);

        t_ext = t_name.substring(t_name.lastIndexOf(".") + 1);

        int allowFlag = 0;
        int allowedExtCount = allowedExt.length;

        for (; allowFlag < allowedExtCount; allowFlag++)
        {
          if (allowedExt[allowFlag].equals(t_ext))
          {
            break;
          }
        }
        if (allowFlag == allowedExtCount)
        {
          out.println("请上传以下类型的文件<p />");

          for (allowFlag = 0; allowFlag < allowedExtCount; allowFlag++) {
            out.println("*." + allowedExt[allowFlag] + "&nbsp;&nbsp;&nbsp;");
          }
          out.println("<p /><a href=\"upload.html\" target=\"_top\">返回</a>");
          return null;
        }

        long now = System.currentTimeMillis();

        prefix = "Excel" + String.valueOf(now);

        String u_name = fPath + "/" + prefix + "." + t_ext;
        try
        {
          CreateFileUtil factorFile = new CreateFileUtil();
          CreateFileUtil.CreateFile(u_name);

          fileItem.write(new File(u_name));

          System.out.print("asdsaaaaaaaaaaaaaaaaaaa");
          System.out.println("文件上传成功. 已保存为: " + prefix + "." + t_ext + " &nbsp;&nbsp;文件大小: " + size + "字节<p />");

          out.println("<div id='wc'>上传成功！！！</div>");
          out.print("</body>");
          out.print("</html>");
        }
        catch (Exception e)
        {
          e.printStackTrace();
          return null;
        }
      }
    }
    return prefix + "." + t_ext;
  }

  public int importExcelDataToTable(String filePath)
    throws IOException
  {
    Workbook wb = null;
    String sql = "";
    InputStream is = null;
    Mysql_operation mop = new Mysql_operation();
    int rightRows = 0;
    int erroRows = 0;
    try {
      is = new FileInputStream(filePath);
      wb = Workbook.getWorkbook(is);
      Sheet s = wb.getSheet(0);

      int rsRows = s.getRows();
      int rsColumns = s.getColumns();
      System.out.println("行数：" + rsRows);
      System.out.println("列数：" + rsColumns);

      String sql2 = "";

      String sql3 = "";

      for (int i = 1; i < rsRows; i++)
      {
        String name = "";
        String value = "";

        int counter = 0;

        for (int j = 0; j < rsColumns; j++)
        {
          if (!s.getCell(j, 0).getContents().isEmpty())
          {
            counter++;
            String cellText = s.getCell(j, i).getContents();
            if (counter == 1)
            {
              name = name + s.getCell(j, 0).getContents();
              value = value + "'" + cellText + "'";
            }
            else
            {
              name = name + "," + s.getCell(j, 0).getContents();
              value = value + ",'" + cellText + "'";
            }
          }
        }

        sql = "Replace into student (" + name + ") values(" + value + ");";
        if (mop.sql_opWord(sql))
        {
          rightRows++;
        }
        else
        {
          erroRows++;
        }

      }

      for (int i = 1; i < rsRows; i++)
      {
        String name = "";
        String value = "";

        int counter = 0;

        for (int j = 0; j < rsColumns; j++)
        {
          if (s.getCell(j, 0).getContents().equals("user_number"))
          {
            counter++;
            String cellText = s.getCell(j, i).getContents();
            if (counter == 1)
            {
              name = name + s.getCell(j, 0).getContents();
              value = value + "'" + cellText + "'";
            }
            else
            {
              name = name + "," + s.getCell(j, 0).getContents();
              value = value + ",'" + cellText + "'";
            }
          }

        }

        sql2 = "Replace into student_basic_news (" + name + ") values(" + value + ");";
        sql3 = "Replace into student_marks (" + name + ") values(" + value + ");";
        mop.sql_opWord(sql2);
        mop.sql_opWord(sql3);
        sql2 = "";
        sql3 = "";
      }

    }
    catch (FileNotFoundException e)
    {
      e.printStackTrace();
    }
    catch (BiffException e)
    {
      e.printStackTrace();
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
    finally
    {
      wb.close();
      is.close();
    }

    return rightRows;
  }

  public void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
  {
  }

  public void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
  {
    response.setContentType("text/html");
    PrintWriter out = response.getWriter();

    String ExcelPath = upFileSingle(request, response, request.getSession().getServletContext().getRealPath("/ExcelTemp/"));


    System.out.print(request.getSession().getServletContext().getRealPath("/ExcelTemp/")+"/" + ExcelPath);
    out.print("成功更新" + importExcelDataToTable(request.getSession().getServletContext().getRealPath("/")+"/ExcelTemp/"+ExcelPath) + "条记录");
    System.out.println("over");
  }

  public void init()
    throws ServletException
  {
  }
}