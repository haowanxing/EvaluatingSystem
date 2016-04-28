package function;

import java.sql.*;
import java.util.Map;

import org.json.*;

public class Sql_op {
	private static Connection connection = null;
	private Statement stmt = null;
	private ResultSet rs = null;
	public Sql_op(){
		
	}
	public ResultSet Query(String sql) {	//适用于检索数据的调用
		System.out.println("调用Query函数...");
		System.out.println("进入try语句...");
		try {
			System.out.println("开始获取Connection...");
			connection = ConnUtils.getConnection();
			System.out.println("Connection获取完毕！");
			stmt = connection.createStatement();
			rs = stmt.executeQuery(sql);
			return rs;
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		} finally {

		}
	}
	public int Query2(String sql){	//适用于增、删、改的操作调用
		stmt = null;
		rs = null;
		try {
			connection = ConnUtils.getConnection();
			stmt = connection.createStatement();
			int result = stmt.executeUpdate(sql);
			return result;
		} catch (Exception ex) {
			ex.printStackTrace();
			return -1;
		} finally {
			
		}
	}
	
	public int insert(String table,Map<String,String> data){	//插入数据
		String key="",value="";
		for(Map.Entry<String, String> entry:data.entrySet()){
			key += entry.getKey()+",";
			value+=entry.getValue()+"','";
		     System.out.println(entry.getKey()+"--->"+entry.getValue());   
		}
		key = key.substring(0, key.length()-1);
		value = value.substring(0, value.length()-3);
		String sql = "INSERT INTO "+table+" ("+key+") VALUES ('"+value+"')";
		return Query2(sql);
	}
	public int delete(String table,String where){	//删除数据
		String sql = "DELETE FROM "+table+" WHERE "+where;
		return Query2(sql);
	}

	public int update(String table,String str,String where){	//更新数据
		String sql = "UPDATE "+table+" SET "+str+" WHERE "+where;
		return Query2(sql);
	}
	public ResultSet find(String table,String filed,String where) throws SQLException{	//检索数据
		String sql;
		if(where==null || where == ""){
			sql = "select "+filed+" from "+table;
		}else{
			sql = "select "+filed+" from "+table+" where "+where;
		}
		System.out.println("Sql_op.find: "+sql+"  接着执行程序...");
		ResultSet result = Query(sql);
		System.out.println("find函数执行完毕！");
		return result;
	}
	public String findToJson(String table,String filed,String where) throws SQLException, JSONException{	//检索数据
		String sql;
		if(where==null || where == ""){
			sql = "select "+filed+" from "+table;
		}else{
			sql = "select "+filed+" from "+table+" where "+where;
		}
		ResultSet result = Query(sql);
		return resultSetToJson(result);
	}
	public String resultSetToJson(ResultSet rs) throws SQLException,JSONException  
	{  
	   // json数组  
	   JSONArray array = new JSONArray();  
	    
	   // 获取列数  
	   ResultSetMetaData metaData = rs.getMetaData();  
	   int columnCount = metaData.getColumnCount();  
	    
	   // 遍历ResultSet中的每条数据  
	    while (rs.next()) {  
	        JSONObject jsonObj = new JSONObject();  
	         
	        // 遍历每一列  
	        for (int i = 1; i <= columnCount; i++) {  
	            String columnName =metaData.getColumnLabel(i);  
	            String value = rs.getString(columnName);  
	            jsonObj.put(columnName, value);  
	        }   
	        array.put(jsonObj);   
	    }  
	    
	   return array.toString();  
	}
	public void close(){
		if (rs != null) {
			try {
				rs.close();
				rs = null;
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}

		if (stmt != null) {
			try {
				stmt.close();
				stmt = null;
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}
}
