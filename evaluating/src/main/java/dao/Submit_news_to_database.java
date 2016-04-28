package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import dBassiant.*;

public class Submit_news_to_database {
	public int write_news(String aStudent,ArrayList<String> names,ArrayList<String> values,String table) throws SQLException
	{
		DBHelper db=new DBHelper();
		Connection conn = null;
		PreparedStatement stmt=null;
		ResultSet rs=null;
		try {
			conn = db.getConnetion();
			String sql="Select * From "+table+" Where user_number="+aStudent+" ;";

			stmt=conn.prepareStatement(sql);
			rs=stmt.executeQuery();
			
			System.out.print("aaaaaaaaaaaaaaaaaaaaaa"+rs.getMetaData().getColumnCount()+"aaaaaaaaaaa");
			for(int a=0;a<names.size();a++)//遍历 传入的 键值对
			{
				String temp=names.get(a).toLowerCase();
				int c=0;
				//System.out.print("\n"+"键值对位置："+a+"          ");
				for(int b=1;b<=rs.getMetaData().getColumnCount();b++)//和数据库的字段比较，看是否存在
				{
					boolean flag=(boolean)(rs.getMetaData().getColumnName(b).equalsIgnoreCase(temp));
					if(flag)
					{
						//System.out.print(rs.getMetaData().getColumnName(b)+":"+values.get(a));
						sql="update "+table+" set "+temp+"='"+values.get(a)+"' where user_number="+aStudent+" ;";
						stmt=conn.prepareStatement(sql);
						stmt.executeUpdate(sql);
						
						c++;
						break;
					}
								
				}
				
				System.out.print("\n");
				if(c==0)//不存在时
				{
					sql="alter table "+table+" add "+temp+" varchar(250)";
					stmt=conn.prepareStatement(sql);
					stmt.executeUpdate(sql);
					System.out.print("ssssssssssssssssssssssssssssssss");
					sql="update student_basic_news set "+temp+"=\""+values.get(a)+"\" where user_number="+aStudent+" ;";
					stmt=conn.prepareStatement(sql);
					stmt.executeUpdate(sql);
					System.out.print("修改成功2！！");
				}
			}
			
			return 1;
		} 
		catch (Exception e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
		finally
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
		
	}
	public Map<String, String> getAllKeys(String aStudent,String table) throws Exception//学号，表名
	{

		DBHelper db=new DBHelper();
		Connection conn = null;
		PreparedStatement stmt=null;
		ResultSet rs=null;
		Map<String, String> dataMap=new HashMap<String, String>();
		
		
		String sql="Select * From "+table+" Where user_number="+aStudent+" ;";

		try {
			conn = db.getConnetion();
			stmt=conn.prepareStatement(sql);
			rs=stmt.executeQuery();
			
			
			
			while(rs.next())
			{
				for(int b=1;b<=rs.getMetaData().getColumnCount();b++)
				{
					String name=rs.getMetaData().getColumnName(b);
					String value=rs.getString(name);	
					dataMap.put(name, value);
				}
			}
			
			
		} 
		catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			dataMap.put("错误", "00000");
			return dataMap;
		}
		finally
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
			
		}
		
		return dataMap;
	}
	
	public static void main(String args[])
	{
		Submit_news_to_database a=new Submit_news_to_database();
		try {
			@SuppressWarnings("rawtypes")
			Map b=a.getAllKeys("201321091074", "student_basic_news");
			System.out.print(b.get("zhiwei"));
			System.out.print(b.get("tiyulv"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
