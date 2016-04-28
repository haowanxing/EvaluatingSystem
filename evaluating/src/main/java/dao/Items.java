package dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import mode.*;
import dBassiant.DBHelper;

public class Items{
	
	private String table;
	
	private String filed_name;
	
	public String getFiled_name() {
		return filed_name;
	}


	public void setFiled_name(String filed_name) {
		this.filed_name = filed_name;
	}

	
	
	public Item_student getStudent(String aStudent) throws NumberFormatException, SQLException//获取基本信息，不包括 填写信息 //定制表 不能 共用
	{
		Item_student item=new Item_student();
		//getFieldValue("time",aStudent);
		//Integer.parseInt(getFieldValue("examine",aStudent));
		
		setTable("student");
		item.setAccess(Integer.parseInt(getFieldValue("access",aStudent)));
		item.setStudent_number(getFieldValue("user_number",aStudent));
		item.setStudent_name(getFieldValue("user_name",aStudent));
		item.setStudent_class(getFieldValue("student_class",aStudent));
		item.setStudent_age_class(getFieldValue("student_age_class",aStudent));
		DateFormat format2= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date=null;
		String timeStamp=getFieldValue("registration_time",aStudent);
		timeStamp=timeStamp.replace(".0", "");
		
		
		//DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
		setTable("student_basic_news");
		
		item.setStudent_sum_mark(Integer.parseInt(getFieldValue("student_sum_mark",aStudent)));
		item.setExamin(Integer.parseInt(getFieldValue("examine",aStudent)));
		
		
		////System.out.print(timeStamp);
		
		try {
			date=format2.parse(timeStamp);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		item.setTime(date);
		
		return item;
	}
	
	
	public String getTable() {
		return table;
	}


	public void setTable(String table) {
		this.table = table;
	}


	public ArrayList<Item_student> getStudents()//传入 sql 语句 返回 对应的学生 数据 list  //获取基本信息，不包括 填写信息 定制 的 只适合本系统
	{
		String sql1="Select "+filed_name+" From "+table+" ;";
		
		ArrayList<Item_student> mode=new ArrayList<Item_student>();
		DBHelper db = new DBHelper();
		
		Connection conn=null;
		PreparedStatement stmt=null;
		ResultSet rs=null;
	
		try
			{
				conn=db.getConnetion();	
				stmt=conn.prepareStatement(sql1);
				rs=stmt.executeQuery();
				while(rs.next())
				{
					Item_student item=new Item_student();
					String aStudent=rs.getString(filed_name);
					item=getStudent(aStudent);
				
					
					mode.add(item);
				}
				///////////////////////////////////////////////////////第二部分
				
				
		
				return mode;
			}
		catch(Exception ex)
			{
				ex.printStackTrace();
				return null;
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
	
		
	}
	
	public int alter_examine(String students_number,int examine)//return 1 表示修改成功  修改某一字段值
	{
		DBHelper db=new DBHelper();
		PreparedStatement stmt=null;
		Connection conn=null;
		ResultSet rs=null;
		try {
				conn=db.getConnetion();
				String sql="Select * from "+table+" where user_number='"+students_number+"';";
				stmt=conn.prepareStatement(sql);
				rs=stmt.executeQuery(sql);	
				
				while(rs.next())
				{
					int za=rs.getInt("examine");
					
					if(za!=0)
					{
						sql="update "+table+" set examine='"+examine+"' where user_number='"+students_number+"';";
						//System.out.println("准备修改");
						stmt=conn.prepareStatement(sql);
						stmt.executeUpdate(sql);
						//System.out.println("修改成功！");
					}
					
				}		
			
		} 
		catch (Exception e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			//System.out.println("333333333333333333");
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
			
			try 
			{
				conn.close();
			} 
			catch (SQLException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		}
		
		return 1;
				
	}

	public int getStudents_check(String sql)//匹配 字段是否存在 在在返回  记录条数
	{
		DBHelper db=new DBHelper();
		PreparedStatement stmt=null;
		Connection conn=null;
		ResultSet rs=null;
		int reslut = 0;
		
		try {
			conn=db.getConnetion();
			
			//System.out.println("准备查询");
			stmt=conn.prepareStatement(sql);
			rs=stmt.executeQuery(sql);
			//System.out.println("查询成功！");
			while(rs.next())
			{
				reslut = rs.getInt(1);
			}
		} 
	catch (Exception e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			//System.out.println("333333333333333333");
			
			return 0;
		}
		return reslut;
	}

	public String getFieldValue(String word,String filed_value) throws SQLException //获取某一字段值 word是目标字段 ，filed_value是标志字段
	{
		String sql="Select "+word+" From "+table+" where "+filed_name+"=\""+filed_value+"\" ;";
		System.out.println("sql:"+sql);
		DBHelper db = new DBHelper();
		
		Connection conn=null;
		PreparedStatement stmt=null;
		ResultSet rs=null;
		String result="";
		
		try {
			conn=db.getConnetion();
			stmt=conn.prepareStatement(sql);
			rs=stmt.executeQuery();
			
			while(rs.next())
			{
				result=rs.getString(word);
				
			}
			
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
			
			conn.close();
			
		}
		return result;
	}
	
	
	
	
	
	
	public static void main(String []args) throws SQLException
	{
//		Connection coon = null;
//		DBHelper db=new DBHelper();
//		try {
//			coon=db.getConnetion();
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		if(coon!=null)
//		{
//			//System.out.println("succes");
//		}
//		
//		Items items=new Items();
//		//System.out.print(items.getStudents_check("Select * From Student_basic_news").size());
		
		////////////////////////////////////////////////////////////////////////////////
		DBHelper db = new DBHelper();
		
		
		Connection conn=null;
		PreparedStatement stmt=null;
		ResultSet rs=null;
		
		try
			{
				conn=db.getConnetion();
		
				String sql="Select * From student_basic_news Where user_number=201321091074 ;";
				
				
				stmt=conn.prepareStatement(sql);
				rs=stmt.executeQuery();
				
				Item_student item=new Item_student();
				//for(int a=1;a<=rs.getMetaData().getColumnCount();a++)
					////System.out.println(rs.getMetaData().getColumnName(a));
				
	
				
			}
		catch(Exception ex)
			{
				ex.printStackTrace();
				//return null;
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
		
		//////////////////////////////////////////////////////////////
		
		Items items=new Items();
		items.setTable("student_basic_news");
		items.setFiled_name("user_number");
		////System.out.print(items.getFieldValue("time","201321091074")+"\n");
		DateFormat format2= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		//System.out.println(format2.format(items.getStudent("201321091074").getTime())+"\n");
		//System.out.print(items.alter_examine("201321091074", 2));
		
		
		
	}

}

