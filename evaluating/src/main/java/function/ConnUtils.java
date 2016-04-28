package function;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnUtils {
	private static Connection conn = null;
	private static String driver = null;
	private static String dbName = null;
	private static String userName = null;
	private static String userPasswd = null;
	private static String host = null;
	private static String port = null;
	private static String url = null;

	static {
		 System.out.println("开始读取数据库配置文件");
		 PropertiesUtils.loadFile("dbconfig.properties");
		 driver = PropertiesUtils.getPropertyValue("DRIVER");
		 host = PropertiesUtils.getPropertyValue("DBSERVER");
		 dbName = PropertiesUtils.getPropertyValue("DBNAME");
		 userName = PropertiesUtils.getPropertyValue("USER");
		 userPasswd = PropertiesUtils.getPropertyValue("PWD");
		 port = PropertiesUtils.getPropertyValue("PORT");
		 url = "jdbc:mysql://"+host+":"+port+"/"+dbName+"?characterEncoding=utf-8";
		 System.out.println("读取数据库文件完毕!");
		 try {
		 Class.forName(driver);
		 } catch (ClassNotFoundException e) {
		 e.printStackTrace();
		 }
	}

	/**
	 * 取得连接的工具方法
	 * 
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public static Connection getConnection() throws
			SQLException {
		//System.out.println("开始调用Connection静态方法!");
		conn = DriverManager.getConnection(url, userName, userPasswd);
		return conn;
	}

}
