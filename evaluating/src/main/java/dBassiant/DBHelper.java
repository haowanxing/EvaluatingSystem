package dBassiant;

import java.sql.Connection;

import function.*;


public class DBHelper {
	private Connection con=null;
	
	public DBHelper()
	{
		
	}
	
	
	public Connection getConnetion() throws Exception
	{
		if(con==null)
		{
			con=ConnUtils.getConnection();
			return con;
		}
		return con;
	}
}
