package dao;

import java.sql.SQLException;

import mode.nowXmlTable;

public class GetTable {
	
	public nowXmlTable getTable()
	{
		Items itm=new Items();
		itm.setTable("manager_xmltable");
		itm.setFiled_name("usied");
		
		nowXmlTable table=new nowXmlTable();
		try {
			table.setTable_name(itm.getFieldValue("table_name", "1"));
			table.setTableXml(itm.getFieldValue("tableXml", "1"));
			table.setScorerule(itm.getFieldValue("scorerule", "1"));
			table.setNewXml(itm.getFieldValue("newXml", "1"));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return table;
		
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		GetTable a=new GetTable();
		System.out.println(a.getTable().getTableXml());

	}

}
