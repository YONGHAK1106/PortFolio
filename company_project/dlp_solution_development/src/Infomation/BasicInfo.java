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
	
	//고객사
	public String BasicCustomer() {
		return customername;
	}
	
	//담당자
	public String BasicManagerName() { 
		return managername;
	}
	
	//점검자
	public String BasicEngineerName() { //종이에 수기로
		return engineername;
	}
	
	//점검업체
	public String BasicCompany() { //종이에 수기로
		return companyname;
	}
	
	//점검일자
	public String BasicDate() {
		Date today = new Date();
		SimpleDateFormat date = new SimpleDateFormat("yyyy년 MM월 dd일");
	    return date.format(today);
	}
	
	//점검시간
	public String BasicTime() {
		Date today = new Date();
	    SimpleDateFormat time = new SimpleDateFormat("a hh시 mm분 ss초");
		return time.format(today);
	    
	}
	
	//지원방법
	public String BasicSupport() { 
		if(support.equals("1")) {
			support = "방문";
		}
		else if(support.equals("2")) {
			support = "원격";
		}
		else {
			logger.error("#환경설정에서 Support 값 재확인");
			support = "환경설정에서 Support 값을 잘못 입력하였습니다.";
		}
		//System.out.println("지원방법 : " + support);
		return support;
	}
	
	//점검주기
	public String BasicCycle() { 
		if(cycle.equals("1")) {
			cycle = "매월";
		}
		else if(cycle.equals("2")) {
			cycle = "격월";
		}
		else if(cycle.equals("3")) {
			cycle = "분기";
		}
		else {
			logger.error("#환경설정에서 Cycle 값 재확인");
			cycle = "환경설정에서 Cycle 값을 잘못 입력하였습니다.";
		}
		//System.out.println("점검주기 : " + cycle);
		return cycle;
	}
	
	//제품명 및 버전	
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
					if(line.indexOf(word) != -1) { //<title>이 시작하는 문장 가져오기
						//System.out.println(line); //"<title>CrossGuard Expoint V4.0 Install</title>" 출력
						array = line.split(startline);
						for(int i=1; i<array.length; i++) {
							//System.out.println("제품명 및 버전 : "+ array[i].substring(0));
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
			return "#수동으로 확인하시오.";
		}
		else if(expointVersion.equals("3")){
			return "#수동으로 확인하시오.";
		}
		else {
			logger.error("#환경설정에서 Expoint Version 값 재확인");
			return "#환경설정에서  Expoint Version 값을 다시 확인하시오.";
		}
	}
	


	//사용자수
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
					conn = DriverManager.getConnection(url, ID, PW); //(url, 서버아이디, 서버패스워드)
					stmt = conn.createStatement();
					rs = stmt.executeQuery("SELECT COUNT(UR_ID) "
										+ "FROM dbo.INSA_USER_INFO" +" WHERE USE_YN = 'Y'"); //얻고자하는 내용에 대한 쿼리문
						
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
				return "#수동으로 확인하시오.";
			}
			else {
				return "#수동으로 확인하시오.";
			}
		}
		else if(expointVersion.equals("2")) {
			return "#수동으로 확인하시오.";
		}
		else if(expointVersion.equals("3")){
			return "#수동으로 확인하시오.";
		}
		else {
			logger.error("#환경설정에서 Expoint Version 값 재확인");
			return "#환경설정에서  Expoint Version 값을 다시 확인하시오.";
		}
	}
	
	//OS
	public String BasicOSName() {
		Runtime runtime = Runtime.getRuntime();
		StringBuilder sb = new StringBuilder();
        //System.out.println("OS : " + System.getProperty("os.name"));
		return System.getProperty("os.name");
	}
	
	//WAS 명
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
				FindVERSION = "#catalina.jar 경로 재확인 바람";
			}
			else if(result.substring(16, 36).startsWith("Apache") == false) {
				FindVERSION = "#catalina.jar 경로 재확인 바람";
			}
			else if(result.substring(16, 36).contains("Apache") == false) {
				FindVERSION = "#catalina.jar 경로 재확인 바람";
			}
			else if(result.substring(16, 36).startsWith("Apache") == true){
				FindVERSION = result.substring(16, 36);
			}
			else {
				FindVERSION = "#catalina.jar 경로 재확인 바람";
			}
			
			return FindVERSION;

		}
		else if(was.equals("2")) {
			return "#수동으로 확인하시오.";
		}
		else if(was.equals("3")){
			return "#수동으로 확인하시오.";
		}
		else {
			logger.error("#환경설정에서 WAS 값 재확인");
			return "#환경설정에서  WAS값을 다시 확인하시오."; 
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
				con = DriverManager.getConnection(url, ID, PW); //(url, 서버아이디, 서버패스워드)
				stmt = con.createStatement();
				rs = stmt.executeQuery("EXEC sp_server_info"); //얻고자하는 내용에 대한 쿼리문
				
				while( rs.next() ) {
					String field1 = rs.getString("attribute_name"); //필드 이름
					String field2 = rs.getString("attribute_value"); //필드이름
					if(field1.equals("DBMS_VER")) {//테이블에 attribute_name 에서 두번째 DBMS_VER 을 찾는거.
			               //System.out.println("DBMS : " + field2);
						for(int i=2000; i<2030; i++) //중간에 버전 년도가 들어가 있는 스트링을 찾아서 버전 출력
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
			return "#수동으로 확인하시오.";
		}
		else if(dbms.equals("3")){
			return "#수동으로 확인하시오.";
		}
		else {
			logger.error("#환경설정에서 DBMS 값 재확인");
			return "#환경설정에서  DBMS 값을 다시 확인하시오.";
		}
	}
	
	//DB파일 및 용량
	public String BasicDBFile()  {
		
		if(dbms.equals("1")) {
			Connection conn = null;
			Statement stmt = null;
			ResultSet rs = null;
			String a = null;
			final String url = defaulturl+ip+":"+port+";DatabaseName="+dbname;
			
			try {
				
				Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver"); //sqljdbc
				conn = DriverManager.getConnection(url, ID, PW); //(url, 서버아이디, 서버패스워드)
				stmt = conn.createStatement();
				rs = stmt.executeQuery("exec sp_helpdb" + " '"+ dbname + "'"); //얻고자하는 내용에 대한 쿼리문
				
				while( rs.next() ) {
					String field1 = rs.getString("name"); //필드 이름
					String field2 = rs.getString("db_size"); //필드이름
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
			return "#수동으로 확인하시오.";
		}
		else if(dbms.equals("3")){
			return "#수동으로 확인하시오.";
		}
		else {
			logger.error("#환경설정에서 DBMS 값 재확인");
			return "#환경설정에서  DBMS 값을 다시 확인하시오.";
		}
	}
}