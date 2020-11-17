package Infomation; 

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;

import javax.swing.JOptionPane;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import Encrytion.AES256;
import Ui.*;

public class Preferences extends ChangePassword { //ȯ�漳�� Ŭ����
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
		
		AES256 aes = new AES256(puc.reskey); //key ���� �� �н������ ����
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
			if(file.exists()) { //������ ���� ���� Ȯ��
				System.out.println("@@@@ ȯ�漳��.txt ������ �ֽ��ϴ�. @@@@" + "\n");
				
				fileReader = new FileReader(file);
				bufferedReader = new BufferedReader(fileReader);
				
				fileReader1 = new FileReader(file1);
				bufferedReader1 = new BufferedReader(fileReader1);
				
				fileReader2 = new FileReader(file2);
				bufferedReader2 = new BufferedReader(fileReader2);
				
				while((line = bufferedReader.readLine()) != null) { // �޸��� �� ���� ������ ���������� �ݺ�
					decryptLine += (aes.decrypt(line)+"\r\n");
				}
				writer = new FileWriter(file);
				writer.write(decryptLine);
				writer.close();
				logger.debug("��ȣȭ ����");
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
							JOptionPane.showMessageDialog(null, "WasServiceName�� ��ĭ�Դϴ�");
							logger.error("survicename ��ĭ");
						}
						System.out.println(p14 + puc.survicename);
					}
					
					else if(line.indexOf(p15) != -1) {
						puc.localset = line.substring(14);
						if(puc.localset.isEmpty()) {
							JOptionPane.showMessageDialog(null, "Localset Path�� ��ĭ�Դϴ�");
							logger.error("Localset Path ��ĭ");
						}
						System.out.println(p15 + puc.localset);
					}
					
					else if(line.indexOf(p16) != -1) {
						puc.catalinajar = line.substring(18);
						if(puc.catalinajar.isEmpty()) {
							JOptionPane.showMessageDialog(null, "catalinajar ��ΰ� ��ĭ�Դϴ�");
							logger.error("Localset Path ��ĭ");
						}
						System.out.println(p16 + puc.catalinajar);
					}
					
					else if(line.indexOf(p17) != -1) {
						puc.stdout = line.substring(16);
						if(puc.stdout.isEmpty()) {
							JOptionPane.showMessageDialog(null, "stdout�� ��ĭ�Դϴ�");
							logger.error("stdout Path ��ĭ");
						}
						System.out.println(p17 + puc.stdout);
					}
					
					else if(line.indexOf(p18) != -1) {
						puc.stderr = line.substring(16);
						if(puc.stderr.isEmpty()) {
							JOptionPane.showMessageDialog(null, "stderr�� ��ĭ�Դϴ�");
							logger.error("stderr Path ��ĭ");
						}
						System.out.println(p18 + puc.stderr);
					}
					
					else if(line.indexOf(p19) != -1) {
						puc.upload = line.substring(19);
						if(puc.upload.isEmpty()) {
							JOptionPane.showMessageDialog(null, "upload�� ��ĭ�Դϴ�");
							logger.error("upload Path ��ĭ");
						}
						System.out.println(p19 + puc.upload);
					}
					
					else if(line.indexOf(p20) != -1) {
						puc.serverPcRISKCase = line.substring(27);
						if(puc.serverPcRISKCase.isEmpty()) {
							JOptionPane.showMessageDialog(null, "serverPcRISKCase�� ��ĭ�Դϴ�");
							logger.error("serverPcRISKCase ��ĭ");
						}
						System.out.println(p20 + puc.serverPcRISKCase);
					}
					else if(line.indexOf(p21) != -1) {
						puc.diskdanger = line.substring(12);
						if(puc.diskdanger.isEmpty()) {
							JOptionPane.showMessageDialog(null, "diskdanger�� ��ĭ�Դϴ�");
							logger.error("diskdanger ��ĭ");
						}
						System.out.println(p21 + puc.diskdanger);
					}
					else if(line.indexOf(p22) != -1) {
						puc.fatalHowManyLine = line.substring(11);
						if(puc.fatalHowManyLine.isEmpty()) {
							JOptionPane.showMessageDialog(null, "fatalHowManyLine�� ��ĭ�Դϴ�");
							logger.error("fatalHowManyLine ��ĭ");
						}
						System.out.println(p22 + puc.fatalHowManyLine);
					}
				}
				
				line = null;
				while((line = bufferedReader2.readLine()) != null) { // �޸��� �� ���� ������ ���������� �ݺ�
					encryptLine += (aes.encrypt(line)+"\r\n");
				}
				logger.debug("��ȣȭ ����");
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
				//System.out.println("@@@@ ȯ�漳��.txt ������ �����ϴ�. �ٽ� Ȯ���Ͻʽÿ�. @@@@"); 
			}
			//System.out.println("@@@@ ������� ȯ�漳�� ���� @@@@" + "\n");
			
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