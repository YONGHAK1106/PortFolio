package Infomation;

import java.sql.*;
import java.util.ArrayList;
import org.slf4j.*;
import java.io.*;

public class PublicUseClass { //���� Ŭ����
	final private Logger logger = LoggerFactory.getLogger(getClass());
	
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	//public static String fixPath = System.getProperty("user.dir");
	public static String fixPath = "C:\\Maintenance\\";
	//Preferences, BasicUi, SystemUi(fileSave)
	
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public static String reskey = "newgencninewgencni";
	public void setReskey(String reskey) {this.reskey = reskey;}
	public String getReskey() {return reskey;}
	
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	//config.txt [Expoint & DBMS & WAS info]

	public static String expointVersion;
	public static String dbms;
	public static String was;
	
	public void setDbms(String dbms) {this.dbms = dbms;}
	public String getDbms() {return dbms;}
	
	public void setWas(String was) {this.was = was;}
	public String getWas() {return was;}
	
	public void setExpointVersion(String expointVersion) {this.expointVersion = expointVersion;}
	public String getExpointVersion() {return expointVersion;}
	
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	//config.txt [Name info]
	
	public static String customername; //����
	public static String managername; //�����
	public static String engineername; //������
	public static String companyname; //���˾�ü
	
	public void setCustomername(String customername) {this.customername = customername;}
	public String getCustomername() {return customername;}
	
	public void setManagername(String managername) {this.managername = managername;}
	public String getManagername() {return managername;}
	
	public void setEngineername(String engineername) {this.engineername = engineername;}
	public String getEngineername() {return engineername;}
	
	public void setCompanyname(String companyname) {this.companyname = companyname;}
	public String getCompanyname() {return companyname;}
	
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	//config.txt [Check]
	
	public static String support; //�������
	public static String cycle; //�����ֱ�
	
	public void setSupport(String support) {this.support = support;}
	public String getSupport() {return support;}
	
	public void setCycle(String cycle) {this.cycle = cycle;}
	public String getCycle() {return cycle;}
	
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	//config.txt [Server and DB info]
	
	//������� dbms
	public static String defaulturl = "jdbc:sqlserver://";
	public static String ip; //���� ip
	public static String port; //���� port
	public static String dbname; //���չ��� db��
	public static String ID; //mssql ���� id
	public static String PW; //mssql ���� pw
	
	public void setIp(String ip) {this.ip = ip;}
	public String getIp() {return ip;}
	
	public void setPort(String port) {this.port = port;}
	public String getPort() {return port;}
	
	public void setDbname(String dbname) {this.dbname = dbname;}
	public String getDbname() {return dbname;}
	
	public void setId(String ID) {this.ID = ID;}
	public String getId() {return ID;}
	
	public void setPw(String PW) {this.PW = PW;}
	public String getPw() {return PW;}
	//������� dbms
	
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	//config.txt [Path]
	
	//������� file���
	public static String survicename; //WAS ���� ����(����) �� ���� ��
	public ArrayList<File> results1 = new ArrayList<File>(); //WAS �α� (�ֱ� ���� ����� ��¥)
	public ArrayList<File> results2 = new ArrayList<File>(); //WAS �α� (WAS �α� Ư�̻���)
	public ArrayList<File> results3 = new ArrayList<File>(); //���ε� ����
	
	public static String localset; //��ǰ�� �� ���� ��� (localset.properties)
	public static String catalinajar; //WAS �� (catalina.jar)
	public static String stdout; //WAS �α� ��� (stdout)
	public static String stderr; //WAS �α� ��� (stderr)
	public static String upload; //���ε� ���� ��� (upload ����)
	
	public void setSurvicename(String survicename) {this.survicename = survicename;}
	public String getSurvicename() {return survicename;}
	
	public void setLocalset(String localset) {this.localset = localset;}
	public String getLocalset() {return localset;}
	
	public void setCatalinajar(String catalinajar) {this.catalinajar = catalinajar;}
	public String getCatalinajar() {return catalinajar;}
	
	public void setStdout(String stdout) {this.stdout = stdout;}
	public String getStdout() {return stdout;}
	
	public void setStderr(String stderr) {this.stderr = stderr;}
	public String getStderr() {return stderr;}
	
	public void setUpload(String upload) {this.upload = upload;}
	public String getsetUpload() {return upload;}
	//������� file���
	
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	//config.txt [Risk]
	
	//������� ��ȣ����
	public static String serverPcRISKCase; //cpu&ram case
	public static String diskdanger; //disk ���� ����
	public static String fatalHowManyLine; //fatal line ���� �����ð���
	 
	public void setServerPcRISKCase(String serverPcRISKCase) {this.serverPcRISKCase = serverPcRISKCase;}
	public String getServerPcRISKCase() {return serverPcRISKCase;}
	
	public void setDiskdanger(String diskdanger) {this.diskdanger = diskdanger;}
	public String getDiskdanger() {return diskdanger;}
	
	public void setFatalHowManyLine(String fatalHowManyLine) {this.fatalHowManyLine = fatalHowManyLine;}
	public String getFatalHowManyLine() {return fatalHowManyLine;}
	//������� ��ȣ����

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	//������� cmd
	private StringBuffer buffer;
	private Process process;
	private BufferedReader bufferedReader;
	private StringBuffer readBuffer;

	public String inputCommand(String cmd) {
		buffer = new StringBuffer();
		
		buffer.append("cmd.exe ");
		buffer.append("/c ");
		buffer.append(cmd);
		
		return buffer.toString();
	}
	public String execCommand(String cmd) {
		try {
			process = Runtime.getRuntime().exec(cmd);
			bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
			
			String line = null;
			readBuffer = new StringBuffer();
			
			while((line = bufferedReader.readLine()) != null) {
				readBuffer.append(line);
				readBuffer.append("\n");
			}
			return readBuffer.toString();
		}catch(Exception e) {
			e.printStackTrace();
			logger.error("error ",e);
			System.exit(1);
		}
		return null;
	}
	//������� cmd
		
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}