package dBassiant;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.sun.org.apache.xalan.internal.xsltc.compiler.Template;

import dao.Submit_news_to_database;
import function.*;

public class Mysql_operation {// 添加一个数据库 初始化
	
	//private ConnUtils db=new ConnUtils();
	private Connection conn = null;
	private PreparedStatement stmt=null;
	private ResultSet rs=null;
	private String table="";
	
	
	
	public String getTable() {
		return table;
	}



	public void setTable(String table) {
		this.table = table;
	}



	public ArrayList<Map<String, String>> getTable(String table) throws Exception//学号，表名
	{
		ArrayList<Map<String, String>> temp=new ArrayList<Map<String, String>>();
		DBHelper db=new DBHelper();
		Connection conn = null;
		PreparedStatement stmt=null;
		ResultSet rs=null;
		
		
		String sql="Select * From "+table+";";

		try {
			conn = db.getConnetion();
			stmt=conn.prepareStatement(sql);
			rs=stmt.executeQuery();
			
			
			
			while(rs.next())
			{
				Map<String, String> dataMap=new HashMap<String, String>();
				for(int b=1;b<=rs.getMetaData().getColumnCount();b++)//和数据库的字段比较，看是否存在
				{
					String name=rs.getMetaData().getColumnName(b);
					String value=rs.getString(name);	
					dataMap.put(name, value);
				}
				temp.add(dataMap);
			}
			return temp;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Map<String, String> dataMap = null;
			dataMap.put("错误", "00000");
			temp.add(dataMap);
			return temp;
		}		
	}
	
	
	
	public void destoryDB()
	{
		
			if(rs!=null)
			{
				try{
					rs.close();
					rs=null;
				}catch(Exception ex)
				{
					ex.printStackTrace();
				}
			}
			
			if(stmt!=null)
			{
				try{
					stmt.close();
					stmt=null;
				}catch(Exception ex)
				{
					ex.printStackTrace();
				}
			}
			
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
	}
	
	public int alter_value(String update_string,String limit)//return 1 表示修改成功  修改某一字段值
	{

		try {
				conn=ConnUtils.getConnection();
				
				String sql="update "+table+" set "+update_string+" "+limit+";";
				System.out.println("准备修改");
				System.out.println(sql);
				stmt=conn.prepareStatement(sql);
				stmt.executeUpdate(sql);
				System.out.println("修改成功！");

			} 
		catch (Exception e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("333333333333333333");
				return 0;
			}
			
		return 1;
				
	}
	
	public boolean sql_opWord(String sql)//直接输入sql语句 修改，删除，插入，更新 等操作 操作成功 返回ture
	{
		try {
			conn=ConnUtils.getConnection();
			
			System.out.println("准备修改");
			stmt=conn.prepareStatement(sql);
			stmt.executeUpdate(sql);
			System.out.println("修改成功！");

		} 
	catch (Exception e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("333333333333333333");
			
			return false;
		}
		
		return true;
	}
	
	public static void main(String args[])
	{
		ArrayList test=new ArrayList();
		Mysql_operation opt=new Mysql_operation();
		try {
			test=opt.getTable("student");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for(int a=0;a<test.size();a++)
		{
			Map temp1=(Map) test.get(a);
			System.out.print(temp1.get("student_number")+"\n");
		}
		
		opt.setTable("student");
		opt.alter_value("Sfill_access='1'", "where user_number='201321091074'");
		
		
	
	}

}
