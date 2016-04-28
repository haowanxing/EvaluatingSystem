package dBassiant;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class FileSystem
 */

public class FileSystem extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FileSystem() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter writer=response.getWriter();
		writer.println("hello word!!");
		writer.println("<br/>");
		writer.println(System.getenv("MOPAAS_FILESYSTEM24313_NAME"));
		
		writer.println("<br/>");
		writer.println(System.getenv("MOPAAS_FILESYSTEM24313_HOST"));
		writer.println("<br/>");
		
		writer.println(System.getProperty("java.version"));
		writer.println("<br/>");
		
		String file=System.getenv("MOPAAS_FILESYSTEM24313_LOCAL_PATH")+"/"+System.getenv("MOPAAS_FILESYSTEM24313_NAME");
		
		writer.println(file);
		
		File f=new File(file);
		writer.println("<br/>");
		
		if(f.exists())
		{
			writer.println("FileSystem is efective");
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
