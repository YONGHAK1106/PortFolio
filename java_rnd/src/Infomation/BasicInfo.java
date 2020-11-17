package Infomation;

import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import Ui.Alarm;
import java.text.SimpleDateFormat;
import java.io.*;
import java.sql.*;

public class BasicInfo extends PublicUseClass {
	final private Logger logger = LoggerFactory.getLogger(getClass());
	
	//����
	public String BasicCustomer() {
		return customername;
	}
	
	//�����
	public String BasicManagerName() { 
		return managername;
	}
	
	//������
	public String BasicEngineerName() { //���̿� �����
		return engineername;
	}
	
	//���˾�ü
	public String BasicCompany() { //���̿� �����
		return companyname;
	}
	
	//��������
	public String BasicDate() {
		Date today = new Date();
		SimpleDateFormat date = new SimpleDateFormat("yyyy�� MM�� dd��");
	    return date.format(today);
	}
	
	//���˽ð�
	public String BasicTime() {
		Date today = new Date();
	    SimpleDateFormat time = new SimpleDateFormat("a hh�� mm�� ss��");
		return time.format(today);
	    
	}
	
	//�������
	public String BasicSupport() { 
		if(support.equals("1")) {
			support = "�湮";
		}
		else if(support.equals("2")) {
			support = "����";
		}
		else {
			logger.error("#ȯ�漳������ Support �� ��Ȯ��");
			support = "ȯ�漳������ Support ���� �߸� �Է��Ͽ����ϴ�.";
		}
		//System.out.println("������� : " + support);
		return support;
	}
	
	//�����ֱ�
	public String BasicCycle() { 
		if(cycle.equals("1")) {
			cycle = "�ſ�";
		}
		else if(cycle.equals("2")) {
			cycle = "�ݿ�";
		}
		else if(cycle.equals("3")) {
			cycle = "�б�";
		}
		else {
			logger.error("#ȯ�漳������ Cycle �� ��Ȯ��");
			cycle = "ȯ�漳������ Cycle ���� �߸� �Է��Ͽ����ϴ�.";
		}
		//System.out.println("�����ֱ� : " + cycle);
		return cycle;
	}
	
	//��ǰ�� �� ����	
	public String BasicProductName() {
		if(expointVersion.equals("1")) {

			String word = "envs.productname=";
			String startline = "envs.productname=";
			String[] array;
			String product = null;
			
			try {
				FileReader fileReader = new FileReader(localset);
				BufferedReader bufferedReader = new BufferedReader(fileReader);
				
				String line;
				while((line = bufferedReader.readLine()) != null) {
					if(line.indexOf(word) != -1) { //<title>�� �����ϴ� ���� ��������
						//System.out.println(line); //"<title>CrossGuard Expoint V4.0 Install</title>" ���
						array = line.split(startline);
						for(int i=1; i<array.length; i++) {
							//System.out.println("��ǰ�� �� ���� : "+ array[i].substring(0));
							product = array[i].substring(0);
						}
					}
				} fileReader.close();
			}catch(FileNotFoundException e) {
				e.printStackTrace();
				logger.error("error message" , e);
			}catch(IOException e) {
				e.printStackTrace();
				logger.error("error message" , e);
			}
			return product;
		}
		else if(expointVersion.equals("2")) {
			return "#�������� Ȯ���Ͻÿ�.";
		}
		else if(expointVersion.equals("3")){
			return "#�������� Ȯ���Ͻÿ�.";
		}
		else {
			logger.error("#ȯ�漳������ Expoint Version �� ��Ȯ��");
			return "#ȯ�漳������  Expoint Version ���� �ٽ� Ȯ���Ͻÿ�.";
		}
	}
	


	//����ڼ�
	public String BasicUser()  {
		if(expointVersion.equals("1")) {
			if(dbms.equals("1")) {
				
				Connection conn = null;
				Statement stmt = null;
				ResultSet rs = null;
				int rowcount = 0;
				final String url = defaulturl+ip+":"+port+";DatabaseName="+dbname;
				
				try {
					Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver"); //sqljdbc
					conn = DriverManager.getConnection(url, ID, PW); //(url, �������̵�, �����н�����)
					stmt = conn.createStatement();
					rs = stmt.executeQuery("SELECT COUNT(UR_ID) "
										+ "FROM dbo.INSA_USER_INFO" +" WHERE USE_YN = 'Y'"); //������ϴ� ���뿡 ���� ������
						
					if(rs.next()) 
						rowcount = rs.getInt(1);
					rs.close();
					stmt.close();
					conn.close();
				
				}
				catch(SQLException e) {
					e.printStackTrace();
					new Alarm();
					logger.error("error message" , e);
				}
				catch( ClassNotFoundException e) {
					e.printStackTrace();
					logger.error("error message" , e);
				}
				return String.valueOf(rowcount);
			}
			else if(dbms.equals("2")) {
				return "#�������� Ȯ���Ͻÿ�.";
			}
			else {
				return "#�������� Ȯ���Ͻÿ�.";
			}
		}
		else if(expointVersion.equals("2")) {
			return "#�������� Ȯ���Ͻÿ�.";
		}
		else if(expointVersion.equals("3")){
			return "#�������� Ȯ���Ͻÿ�.";
		}
		else {
			logger.error("#ȯ�漳������ Expoint Version �� ��Ȯ��");
			return "#ȯ�漳������  Expoint Version ���� �ٽ� Ȯ���Ͻÿ�.";
		}
	}
	
	//OS
	public String BasicOSName() {
		Runtime runtime = Runtime.getRuntime();
		StringBuilder sb = new StringBuilder();
        //System.out.println("OS : " + System.getProperty("os.name"));
		return System.getProperty("os.name");
	}
	
	//WAS ��
	public String BasicWasName() {
		System.out.println("1");
		try {
			
		}catch(StringIndexOutOfBoundsException e) {
			e.printStackTrace();
			logger.error(""+e);
		}
		if(was.equals("1")) {
			BasicInfo cmd = new BasicInfo();
			String FindVERSION = null;			
			String command = cmd.inputCommand("cd " + catalinajar + " && java -cp catalina.jar org.apache.catalina.util.ServerInfo");
			String result = cmd.execCommand(command);
			if(result.length() == 0 ||result.isEmpty() || result.equals("null") || result == null ||
					result.substring(16, 36).isEmpty() ||
					result.substring(16, 36).equals("null") ||
					result.substring(16, 36) == null) {
				FindVERSION = "#catalina.jar ��� ��Ȯ�� �ٶ�";
			}
			else if(result.substring(16, 36).startsWith("Apache") == false) {
				FindVERSION = "#catalina.jar ��� ��Ȯ�� �ٶ�";
			}
			else if(result.substring(16, 36).contains("Apache") == false) {
				FindVERSION = "#catalina.jar ��� ��Ȯ�� �ٶ�";
			}
			else if(result.substring(16, 36).startsWith("Apache") == true){
				FindVERSION = result.substring(16, 36);
			}
			else {
				FindVERSION = "#catalina.jar ��� ��Ȯ�� �ٶ�";
			}
			
			return FindVERSION;

		}
		else if(was.equals("2")) {
			return "#�������� Ȯ���Ͻÿ�.";
		}
		else if(was.equals("3")){
			return "#�������� Ȯ���Ͻÿ�.";
		}
		else {
			logger.error("#ȯ�漳������ WAS �� ��Ȯ��");
			return "#ȯ�漳������  WAS���� �ٽ� Ȯ���Ͻÿ�."; 
		}
	}
	
	//DBMS
	static Connection con = null;
	public String BasicDBMSName() {
		if(dbms.equals("1")) {
		
			Statement stmt = null;
			ResultSet rs = null;
			String a = null;
			final String url = defaulturl+ip+":"+port+";DatabaseName="+dbname;
			
			try {
				Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver"); //sqljdbc
				con = DriverManager.getConnection(url, ID, PW); //(url, �������̵�, �����н�����)
				stmt = con.createStatement();
				rs = stmt.executeQuery("EXEC sp_server_info"); //������ϴ� ���뿡 ���� ������
				
				while( rs.next() ) {
					String field1 = rs.getString("attribute_name"); //�ʵ� �̸�
					String field2 = rs.getString("attribute_value"); //�ʵ��̸�
					if(field1.equals("DBMS_VER")) {//���̺� attribute_name ���� �ι�° DBMS_VER �� ã�°�.
			               //System.out.println("DBMS : " + field2);
						for(int i=2000; i<2030; i++) //�߰��� ���� �⵵�� �� �ִ� ��Ʈ���� ã�Ƽ� ���� ���
							if(field2.contains(String.valueOf(i)))
								a = "Microsoft SQL Server " + i;
			        }
				}
				rs.close();
				stmt.close();
				con.close();
				
			}
			catch(SQLException e) {
				e.printStackTrace();
				logger.error("error message" , e);
			}catch(ClassNotFoundException e) {
				e.printStackTrace();
				logger.error("error message" , e);
			}catch(NullPointerException e) {
				e.printStackTrace();
				logger.error("error message" , e);
			}
			return a;
		}
		else if(dbms.equals("2")) {
			return "#�������� Ȯ���Ͻÿ�.";
		}
		else if(dbms.equals("3")){
			return "#�������� Ȯ���Ͻÿ�.";
		}
		else {
			logger.error("#ȯ�漳������ DBMS �� ��Ȯ��");
			return "#ȯ�漳������  DBMS ���� �ٽ� Ȯ���Ͻÿ�.";
		}
	}
	
	//DB���� �� �뷮
	public String BasicDBFile()  {
		
		if(dbms.equals("1")) {
			Connection conn = null;
			Statement stmt = null;
			ResultSet rs = null;
			String a = null;
			final String url = defaulturl+ip+":"+port+";DatabaseName="+dbname;
			
			try {
				
				Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver"); //sqljdbc
				conn = DriverManager.getConnection(url, ID, PW); //(url, �������̵�, �����н�����)
				stmt = conn.createStatement();
				rs = stmt.executeQuery("exec sp_helpdb" + " '"+ dbname + "'"); //������ϴ� ���뿡 ���� ������
				
				while( rs.next() ) {
					String field1 = rs.getString("name"); //�ʵ� �̸�
					String field2 = rs.getString("db_size"); //�ʵ��̸�
					a = field1 + " " + field2;
				}
				rs.close();
				stmt.close();
				conn.close();
			}
			catch(SQLException e) {
				e.printStackTrace();
				logger.error("error message" , e);
			}
			
			catch(ClassNotFoundException e) {
				e.printStackTrace();
				logger.error("error message" , e);
			}
			return a;
		}
		else if(dbms.equals("2")) {
			return "#�������� Ȯ���Ͻÿ�.";
		}
		else if(dbms.equals("3")){
			return "#�������� Ȯ���Ͻÿ�.";
		}
		else {
			logger.error("#ȯ�漳������ DBMS �� ��Ȯ��");
			return "#ȯ�漳������  DBMS ���� �ٽ� Ȯ���Ͻÿ�.";
		}
	}
}