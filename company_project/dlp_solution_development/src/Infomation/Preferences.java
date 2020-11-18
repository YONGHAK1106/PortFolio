package Infomation; 

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;

import javax.swing.JOptionPane;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import Encrytion.AES256;
import Ui.*;

public class Preferences extends ChangePassword { //환경설정 클래스
	final private Logger logger = LoggerFactory.getLogger(getClass());
	public static String p0 = "Expoint Version=";
	public static String p1 = "DBMS=";
	public static String p2 = "WAS=";
	public static String p3 = "CustomerName=";
	public static String p4 = "Managername=";
	public static String p5 = "CompanyName=";
	public static String p6 = "EngineerName=";
	public static String p7 = "Support=";
	public static String p8 = "Cycle=";
	public static String p9 = "IP=";
	public static String p10 = "Port=";
	public static String p11 = "DBname=";
	public static String p12 = "MSSQLID=";
	public static String p13 = "MSSQLPW=";
	public static String p14 = "WAS Service Name=";
	public static String p15 = "Localset Path=";
	public static String p16 = "catalina.jar Path=";
	public static String p17 = "stdout.txt Path=";
	public static String p18 = "stderr.txt Path=";
	public static String p19 = "upload folder Path=";
	public static String p20 = "ServerPc CPU&RAM RISK Case=";
	public static String p21 = "disk Danger=";
	public static String p22 = "Fatal Line=";
	
	PublicUseClass puc = new PublicUseClass();
	
	public Preferences() {
		
		AES256 aes = new AES256(puc.reskey); //key 값은 툴 패스워드와 동일
		String fileNamePath = puc.fixPath+"\\config.txt";
		
		File file = new File(fileNamePath);
		File file1 = new File(fileNamePath);
		File file2 = new File(fileNamePath);
		
		FileReader fileReader;
		BufferedReader bufferedReader;
		FileReader fileReader1;
		BufferedReader bufferedReader1;
		FileReader fileReader2;
		BufferedReader bufferedReader2;
		FileWriter writer;
		FileWriter writer1;
		
		String encryptLine = "";
		String decryptLine = "";
		String line = null;
		try {
			if(file.exists()) { //파일의 존재 유무 확인
				System.out.println("@@@@ 환경설정.txt 파일이 있습니다. @@@@" + "\n");
				
				fileReader = new FileReader(file);
				bufferedReader = new BufferedReader(fileReader);
				
				fileReader1 = new FileReader(file1);
				bufferedReader1 = new BufferedReader(fileReader1);
				
				fileReader2 = new FileReader(file2);
				bufferedReader2 = new BufferedReader(fileReader2);
				
				while((line = bufferedReader.readLine()) != null) { // 메모장 속 다음 문장이 없을때까지 반복
					decryptLine += (aes.decrypt(line)+"\r\n");
				}
				writer = new FileWriter(file);
				writer.write(decryptLine);
				writer.close();
				logger.debug("복호화 과정");
				line = null;
			
				while((line = bufferedReader1.readLine()) != null) {
					
					if(line.indexOf(p0) != -1) {
						puc.expointVersion = line.substring(16);
						System.out.println(p0 + puc.expointVersion);
					}
					
					else if(line.indexOf(p1) != -1) {
						puc.dbms = line.substring(5);
						System.out.println(p1 + puc.dbms);
					}
					
					else if(line.indexOf(p2) != -1) {
						puc.was = line.substring(4);
						System.out.println(p2 + puc.was);
					}
					
					else if(line.indexOf(p3) != -1) {
						puc.customername = line.substring(13);
						System.out.println(p3 + puc.customername);
					}
					
					else if(line.indexOf(p4) != -1) {
						puc.engineername = line.substring(12);
						System.out.println(p4 + puc.engineername);
					}
					
					else if(line.indexOf(p5) != -1) {
						puc.companyname = line.substring(12);
						System.out.println(p5 + puc.companyname);
					}
					
					else if(line.indexOf(p6) != -1) {
						puc.managername = line.substring(13);
						System.out.println(p6 + puc.managername);
					}
					
					else if(line.indexOf(p7) != -1) {
						puc.support = line.substring(8);
						System.out.println(p7 + puc.support);
					}
					
					else if(line.indexOf(p8) != -1) {
						puc.cycle = line.substring(6);
						System.out.println(p8 + puc.cycle);
					}
					
					else if(line.indexOf(p9) != -1) {
						puc.ip = line.substring(3);
						System.out.println(p9 + puc.ip);
					}
					
					else if(line.indexOf(p10) != -1) {
						puc.port = line.substring(5);
						System.out.println(p10 + puc.port);
					}
					
					else if(line.indexOf(p11) != -1) {
						puc.dbname = line.substring(7);
						System.out.println(p11 + puc.dbname);
					}
					
					else if(line.indexOf(p12) != -1) {
						puc.ID = line.substring(8);
						System.out.println(p12 + puc.ID);
					}
					
					else if(line.indexOf(p13) != -1) {
						puc.PW = line.substring(8);
						System.out.println(p13 + puc.PW);
					}
					
					else if(line.indexOf(p14) != -1) {
						puc.survicename = line.substring(17);
						if(puc.survicename.isEmpty()) {
							JOptionPane.showMessageDialog(null, "WasServiceName이 빈칸입니다");
							logger.error("survicename 빈칸");
						}
						System.out.println(p14 + puc.survicename);
					}
					
					else if(line.indexOf(p15) != -1) {
						puc.localset = line.substring(14);
						if(puc.localset.isEmpty()) {
							JOptionPane.showMessageDialog(null, "Localset Path가 빈칸입니다");
							logger.error("Localset Path 빈칸");
						}
						System.out.println(p15 + puc.localset);
					}
					
					else if(line.indexOf(p16) != -1) {
						puc.catalinajar = line.substring(18);
						if(puc.catalinajar.isEmpty()) {
							JOptionPane.showMessageDialog(null, "catalinajar 경로가 빈칸입니다");
							logger.error("Localset Path 빈칸");
						}
						System.out.println(p16 + puc.catalinajar);
					}
					
					else if(line.indexOf(p17) != -1) {
						puc.stdout = line.substring(16);
						if(puc.stdout.isEmpty()) {
							JOptionPane.showMessageDialog(null, "stdout이 빈칸입니다");
							logger.error("stdout Path 빈칸");
						}
						System.out.println(p17 + puc.stdout);
					}
					
					else if(line.indexOf(p18) != -1) {
						puc.stderr = line.substring(16);
						if(puc.stderr.isEmpty()) {
							JOptionPane.showMessageDialog(null, "stderr이 빈칸입니다");
							logger.error("stderr Path 빈칸");
						}
						System.out.println(p18 + puc.stderr);
					}
					
					else if(line.indexOf(p19) != -1) {
						puc.upload = line.substring(19);
						if(puc.upload.isEmpty()) {
							JOptionPane.showMessageDialog(null, "upload가 빈칸입니다");
							logger.error("upload Path 빈칸");
						}
						System.out.println(p19 + puc.upload);
					}
					
					else if(line.indexOf(p20) != -1) {
						puc.serverPcRISKCase = line.substring(27);
						if(puc.serverPcRISKCase.isEmpty()) {
							JOptionPane.showMessageDialog(null, "serverPcRISKCase가 빈칸입니다");
							logger.error("serverPcRISKCase 빈칸");
						}
						System.out.println(p20 + puc.serverPcRISKCase);
					}
					else if(line.indexOf(p21) != -1) {
						puc.diskdanger = line.substring(12);
						if(puc.diskdanger.isEmpty()) {
							JOptionPane.showMessageDialog(null, "diskdanger가 빈칸입니다");
							logger.error("diskdanger 빈칸");
						}
						System.out.println(p21 + puc.diskdanger);
					}
					else if(line.indexOf(p22) != -1) {
						puc.fatalHowManyLine = line.substring(11);
						if(puc.fatalHowManyLine.isEmpty()) {
							JOptionPane.showMessageDialog(null, "fatalHowManyLine가 빈칸입니다");
							logger.error("fatalHowManyLine 빈칸");
						}
						System.out.println(p22 + puc.fatalHowManyLine);
					}
				}
				
				line = null;
				while((line = bufferedReader2.readLine()) != null) { // 메모장 속 다음 문장이 없을때까지 반복
					encryptLine += (aes.encrypt(line)+"\r\n");
				}
				logger.debug("암호화 과정");
				writer1 = new FileWriter(file2);
				writer1.write(encryptLine);
				writer1.close();
				
				bufferedReader.close();
				fileReader.close();
				bufferedReader1.close();
				fileReader1.close();
				bufferedReader2.close();
				fileReader2.close();
			}
			else { 
				//System.out.println("@@@@ 환경설정.txt 파일이 없습니다. 다시 확인하십시오. @@@@"); 
			}
			//System.out.println("@@@@ 여기까지 환경설정 정보 @@@@" + "\n");
			
		}catch(FileNotFoundException e) {
			e.printStackTrace();
			logger.error("error message" , e);
		} 
		catch (IOException e) {
			e.printStackTrace();
			logger.error("error message" , e);
		}
		catch (NullPointerException e) {
			e.printStackTrace();
			logger.error("error message" , e);
		}
	}
}