package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import mode.Item_student;

import dBassiant.DBHelper;
import dBassiant.Mysql_operation;

public class Manager_op {
	
//		***获取当前用户的能管理的学生条件限制***
//			table=用户所在表名 
//			student_flag=字段名
//			student=字段值
	public String getStudentLimt(String table,String student_flag,String student)
	{
		String result="";
		DBHelper db = new DBHelper();
		
		Connection conn=null;
		PreparedStatement stmt=null;
		ResultSet rs=null;
		String filed[]={"student_age_class","student_class","student_major"};
		
		try {
			conn=db.getConnetion();
			stmt=conn.prepareStatement("select * from "+table+" where "+student_flag+"=\""+student+"\" ;");
			rs=stmt.executeQuery();
			
			
			while(rs.next())
			{
				for(int a=0;a<filed.length;a++)
				{
					if(!rs.getString(filed[a]).equals("*"))
						{
							result+=" and "+filed[a]+"=\""+rs.getString(filed[a])+"\" ";
						};
				}
				
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
			
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		return result;
}

//获取user_number用户能够管理的学生列表
	public ArrayList<Item_student> getStudent(String user_number,String limit2)
	{
		ArrayList<Item_student>  items=new ArrayList<Item_student>();
		//getFieldValue("time",aStudent);
		//Integer.parseInt(getFieldValue("examine",aStudent));
		Manager_op mp=new Manager_op();
		String limit=mp.getStudentLimt("manager_form", "user_number", user_number);
		
		DBHelper db=new DBHelper();
		Connection conn = null;
		PreparedStatement stmt=null;
		ResultSet rs=null;
		
		String sql="select * from student as a,student_basic_news as b where a.user_number=b.user_number "+limit+limit2+" ;";
		////System.out.print(sql);

		try {
			conn = db.getConnetion();
			stmt=conn.prepareStatement(sql);
			rs=stmt.executeQuery();
			
			while(rs.next())
			{
				Item_student item=new Item_student();

				item.setAccess(rs.getInt("access"));
				item.setStudent_number(rs.getString("user_number"));
				item.setStudent_name(rs.getString("user_name"));
				item.setStudent_class(rs.getString("student_class"));
				item.setStudent_age_class(rs.getString("student_age_class"));
				DateFormat format2= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				
//				Date date=null;
//				String timeStamp=rs.getString("registration_time");
//				timeStamp=timeStamp.replace(".0", "");
				item.setTime(rs.getTimestamp("time"));
				item.setStudent_sum_mark(rs.getInt("student_sum_mark"));
				item.setExamin(rs.getInt("examine"));
				
				items.add(item);	
			}
						
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

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
			
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		return items;
		
	}

	
//	返回值为1，表示当前学生属于 当前角色管理
	public boolean judgeStudentExist(String user_number1,String user_number2)
	{
		
		Manager_op mp=new Manager_op();
		String limit=mp.getStudentLimt("manager_form", "user_number", user_number1);
		boolean result=false;
		
		DBHelper db=new DBHelper();
		Connection conn = null;
		PreparedStatement stmt=null;
		ResultSet rs=null;
		
		String sql="select count(*) from student where user_number="+user_number2+" "+limit+" ;";
		////System.out.print("sql");

		try {
			conn = db.getConnetion();
			stmt=conn.prepareStatement(sql);
			rs=stmt.executeQuery();
			
			
			
			while(rs.next())
			{
				if(rs.getInt(1)!=0)
					{
					result=true;
					};
			}
					
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

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
			
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		return result;
	}
		
	public double calculateSumMark(String table,String studentNumber)// 定制函数 算总分
	{
		double sum=0;
		DBHelper db = new DBHelper();
		
		Connection conn=null;
		PreparedStatement stmt=null;
		ResultSet rs=null;
		
		try {
			conn=db.getConnetion();
			stmt=conn.prepareStatement("select * from "+table+" where user_number=\""+studentNumber+"\" ;");
			rs=stmt.executeQuery();
			
			
			while(rs.next())
			{
				for(int b=1;b<=rs.getMetaData().getColumnCount();b++)//和数据库的字段比较，看是否存在
				{
					boolean flag=(boolean)(rs.getMetaData().getColumnName(b).equalsIgnoreCase("user_number"));
					if(!flag)
					{
						String temp=rs.getString(rs.getMetaData().getColumnName(b));
						if(temp!=null)
							sum+=Double.parseDouble(temp);
					}
								
				}
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
			
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		return sum;
		
	}
	
	public boolean creatUser(Map newUser)// 传入字段键值对，创建一条新的记录 插入数据库
	{

		String key="";
		String values="";
		int start=0;
		 for(Object obj : newUser.keySet())
		 {
			 Object value = newUser.get(obj );
			 if(start==0)
			 {
				 key+=(String)obj;
				 values+="\""+(String)value+"\"";	
				 start++;
			 }
			 else
			 {
				 key+=","+(String)obj;
				 values+=",\""+(String)value+"\"";
			 }
	    }
		 
		String sql="insert into manager_form ("+key+") values("+values+") ;";
		////System.out.println(sql);
		
		Mysql_operation mop= new Mysql_operation();
		
		if(mop.sql_opWord(sql))
			return true;
		else
			return false;
	}

	public boolean deletUser(String flag) // 删除 一个学生删除成功 返回 true
	{
		String sql="delete from manager_form where user_number = '"+flag+"'";
		
		Mysql_operation mop= new Mysql_operation();
		if(mop.sql_opWord(sql))
			return true;
		else
			return false;
		
	} 
	
	public ArrayList<String> getSelectValues(String manager,String fieled)
	{
		ArrayList<String> selectV= new ArrayList<String>();
		
		DBHelper db=new DBHelper();
		Connection conn = null;
		PreparedStatement stmt=null;
		ResultSet rs=null;
		
		String sql="select "+fieled+" from manager_form where user_number="+manager+";";
		
		////System.out.print("sql");

		try {
			conn = db.getConnetion();
			stmt=conn.prepareStatement(sql);
			rs=stmt.executeQuery();

			while(rs.next())
			{
				String temp=(String)rs.getString(fieled);
				if(temp.equals("*"))
				{
					Manager_op mp=new Manager_op();
					String limit=mp.getStudentLimt("manager_form", "user_number", manager);
					////System.out.println(limit);
					////System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx哟西："+limit);
					sql="select distinct "+fieled+" from student where id=id "+limit+";";
					////System.out.println(sql);
					stmt=conn.prepareStatement(sql);
					rs=stmt.executeQuery();
					while(rs.next())
					{
						selectV.add(rs.getString(fieled));
					}
				}
				else
				{
					selectV.add(temp);
				}
			}
					
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

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
			
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		
		return selectV;
	}
	
	public boolean deletUser(String flag[]) // 删除多个 删除成功 返回 true
	{
		String value="";
		int start=0;
		for(int a=0;a<flag.length;a++)
		{
			if(start==0)
			{
				value+="'"+flag[a]+"'";
				start++;
			}
			else
				value+=",'"+flag[a]+"'";
		}
		
		String sql="delete from manager_form where user_number in ("+value+") ;";
		//System.out.println(sql);
		
		
		Mysql_operation mop= new Mysql_operation();
		if(mop.sql_opWord(sql))
			return true;
		else
			return false;	
	}
	
	
	public boolean deletStu(String flag[]) // 删除多个学生  删除成功 返回 true
	{
		String value="";
		int start=0;
		for(int a=0;a<flag.length;a++)
		{
			if(start==0)
			{
				value+="'"+flag[a]+"'";
				start++;
			}
			else
				value+=",'"+flag[a]+"'";
		}
		
		String sql="delete from student where user_number in ("+value+") ;";
		String sql2="delete from student_basic_news where user_number in ("+value+") ;";
		String sql3="delete from student_marks where user_number in ("+value+") ;";
		//System.out.println(sql);
		
		boolean a;
		boolean b;
		boolean c;
		Mysql_operation mop= new Mysql_operation();
		
		if(mop.sql_opWord(sql))
			 a=true;
		else
			 a=false;
		if(mop.sql_opWord(sql))
			 a=true;
		else
			 a=false;	
		
		if(mop.sql_opWord(sql2))
			 b=true;
		else
			 b=false;
		
		if(mop.sql_opWord(sql3))
			 c=true;
		else
			 c=false;
		
		if(a&&b&&c)
			return true;
		else
			return false;
		
		
	}
	
	public static void main(String aggs[]){
		 Manager_op mop=new Manager_op();
		 mop.getStudentLimt("manager_form", "user_number", "123456");
		 ArrayList<String> a=null;
		 a=mop.getSelectValues("123456", "student_major");
		 
		 for(int b=0;b<a.size();b++)
		 {
			 //System.out.println(a.get(b));
		 }

	 }

}