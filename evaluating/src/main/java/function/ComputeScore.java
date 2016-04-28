package function;
import java.io.File;
import java.sql.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

//import org.json.*;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
public class ComputeScore {
	private Sql_op op = new Sql_op();
	private static String ScoreTable = "exam_table_score";
	private static String UserTable = "student_basic_news";
	private static String UserIdField = "user_number";
	private static String ScoreVlaue = "scorevalue";
	private String Student;
	protected Document doc;
	private double Score = 0;
	/**
	 * @param args
	 * @throws SQLException 
	 */
	public static void main(String[] args) throws SQLException {
		// TODO Auto-generated method stub
		ComputeScore com = new ComputeScore();
//		com.SetXmlPath("/Users/anthony/Workspaces/MyEclipse 9/Test_system1/WebRoot/WEB-INF/Xml/scorerule.xml");
//		com.setStudent("201321092028");
		System.out.println(com.docompute("pkoufen"));
	}

	public void SetXmlPath(String xmlpath){	//构造函数 参数为xml绝对路径
		try{
			File f=new File(xmlpath);
			DocumentBuilderFactory factory=DocumentBuilderFactory.newInstance();
			DocumentBuilder builder=factory.newDocumentBuilder();
			doc = builder.parse(f);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	public double docompute(String sid) throws SQLException{
		NodeList nl = doc.getElementsByTagName("socia");
		for(int i=0;i<nl.getLength();i++){	//遍历socia标签
			if(((Element) nl.item(i)).getAttribute("title").equals(sid)){
				NodeList nlc = nl.item(i).getChildNodes();
				for(int j=0;j<nlc.getLength();j++){	//遍历rule便签
					if(nlc.item(j).getNodeType() == Node.ELEMENT_NODE){
						Element elemt = (Element) nlc.item(j);
						if(elemt.getAttribute("type").equals("single")){
							String key = elemt.getTextContent();
							ResultSet rs = op.find(ScoreTable, "scorevalue", key+"='"+key+"'");
							rs.next();
							ResultSet var = op.find(UserTable, "*", UserIdField+"='"+Student+"'");
							var.next();
							String[] every = var.getString(key).split("\\|");
							for(int e=0;e<every.length;e++){
								Score += Double.parseDouble(rs.getString(ScoreVlaue))*Double.parseDouble(every[e]);
							}
							//System.out.println(Score);
						}else if(elemt.getAttribute("type").equals("rela")){
							String[] keys = elemt.getTextContent().split("~");	//	rule规则中的字段匹配
							ResultSet var = op.find(UserTable, "*", UserIdField+"='"+Student+"'");
							var.next();
							int num = var.getString(keys[0]).split("\\|").length;	//项数
							for(int n=0;n<num;n++){
								int tag = 0;
								for(int kk=0;kk<keys.length;kk++){	//如果其中一项是'无'，则不加分!
									String[] every = var.getString(keys[kk]).split("\\|");
									if(every[n].equals("无"))
										tag=1;
								}
								if(tag==1)
									continue;
								for(int kk=0;kk<keys.length;kk++){
									String[] every = var.getString(keys[kk]).split("\\|");
									String where = keys[0]+"='"+every[n]+"'";
									for(int k=1;k<keys.length;k++){
										where += " and "+keys[k]+"='"+var.getString(keys[k]).split("\\|")[n]+"'";
									}
									ResultSet rs = op.find(ScoreTable, "count(*),scorevalue", where);
									System.out.println();
									rs.next();
									if(rs.getString("count(*)") != null){
										Score += Double.parseDouble(rs.getString(ScoreVlaue));
									}
								}
							}
							
						}else if(elemt.getAttribute("type").equals("relb")){
							ResultSet var = op.find(UserTable, "*", UserIdField+"='"+Student+"'");
							var.next();
							String[] keys = elemt.getTextContent().split("\\*");	//	rule规则中的字段匹配
							String[] every = var.getString(keys[0]).split("\\|");
							for(int e=0;e<every.length;e++){
								String where = keys[0]+"='"+every[e]+"'";
								ResultSet rs = op.find(ScoreTable, "count(*),scorevalue", where);
								rs.next();
								if(rs.getString("count(*)") != null){
									Score += Double.parseDouble(rs.getString(ScoreVlaue))*Double.parseDouble(var.getString(keys[1]));
								}
							}
						}
						op.close();
					}
				}
				if(Score >Double.parseDouble(((Element) nl.item(i)).getAttribute("maxscore"))){
					Score = Double.parseDouble(((Element) nl.item(i)).getAttribute("maxscore"));
				}
				//System.out.println(Score);
				//Score = 0;
			}
		}
		return Score;
	}

	public void setStudent(String student) {
		Student = student;
	}

	public String getStudent() {
		return Student;
	}
}
