package servletHL;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dBassiant.Mysql_operation;

/**
 * Servlet implementation class SaveXmlUrl
 */

public class SaveXmlUrl extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SaveXmlUrl() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session=request.getSession(true);
		String user_number=(String) session.getAttribute("nowusername");
		String tabkeName=request.getParameter("tableName");
		String xmlTable=request.getParameter("xmlTable");
		String xmlNews=request.getParameter("xmlNews");
		String xmlRules=request.getParameter("xmlRules");
		
		String sql="insert into manager_xmltable ( `user_number`, `table_name`, `tableXml`, `scorerule`, `newXml`, `usied`) "
				+ "VALUES ('"
				+ user_number
				+ "', '"
				+ tabkeName
				+ "', '"
				+ xmlTable
				+ "', '"
				+ xmlNews
				+ "', '"
				+ xmlRules
				+ "', '1');";
		System.out.println(sql);
		Mysql_operation optr=new Mysql_operation();
		optr.sql_opWord(sql);
		
	}

}



