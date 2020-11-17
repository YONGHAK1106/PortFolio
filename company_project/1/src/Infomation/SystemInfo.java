package Infomation;

import java.util.Scanner;
import java.util.regex.Pattern;

import javax.swing.JOptionPane;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.io.*;
import java.text.*;
import java.lang.Object;
import java.lang.management.*;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.sql.*;
import com.sun.management.OperatingSystemMXBean;

import Ui.AlertUi;

public class SystemInfo extends PublicUseClass {
	final private Logger logger = LoggerFactory.getLogger(getClass());

	static int cpui=1000, cpucal=0;
	static double cpuload, cpuj=0.0 , cpusum=0, CPUsage=0;
	static String cpuresult = null;
	//CPU ��뷮
	public String SystemCPU() {
		String result = null;
		final OperatingSystemMXBean osBean = (com.sun.management.OperatingSystemMXBean)ManagementFactory
	    		.getOperatingSystemMXBean();
	    
	    while(cpui<=3000){ //�� 3��. 3�� ����. (�� ó���� �����Ǵ� ���� ���ܽ�ų����)
	    	cpuload = osBean.getSystemCpuLoad();

	    	if(cpuload < 0.0)
	    		continue;
	    	
	    	CPUsage=cpuload*100;
	    	//System.out.println("CPU Usage : "+Math.round(CPUsage)+"%");

	    	if(cpui>1999) //ó���� �����Ǵ� cpu���� �����ϴ� ���ǹ�
	    		cpusum += CPUsage;
	    	try {
	    		Thread.sleep(cpui);
	    	} catch (InterruptedException e) {
	    		e.printStackTrace();
	    	}
	    	cpui=cpui+1000; //1�� ������Ű�� ������. 1000 == 1��
	    	cpuj++;
	    }
	    cpucal = (int) Math.round(cpusum/(cpuj-1)); //��հ� ���
	    
	    if(serverPcRISKCase.equals("1")) { //serverPcRISKCase �� ȯ�漳��(Preferences)���� �������°���.
	    	if(cpucal < 50) {
	    		cpuresult = "��ȣ";
	    		result = "CPU : " + Integer.toString(cpucal) + "%";
	    	}
	    	else if(cpucal < 80) {
	    		cpuresult = "�����ʿ�";
	    		result = "CPU : " + Integer.toString(cpucal) + "%\n" + "(����ڿ� �����Ͽ� ���ʿ��� ���μ��� ���� �ȳ�)";
	    	}
	    	else {
	    		cpuresult = "�����ʿ�";
	    		result = "CPU : " + Integer.toString(cpucal) + "%\n" + "(����ڿ� �����Ͽ� ���ʿ��� ���μ��� ���� �ȳ�)";
	    	}
	    }
	    else if(serverPcRISKCase.equals("2")) {
	    	if(cpucal < 80) {
	    		cpuresult = "��ȣ";
	    		result = "CPU : " + Integer.toString(cpucal) + "%";
	    	}
	    	else {
	    		cpuresult = "��ȣ";
	    		result = "CPU : " + Integer.toString(cpucal) + "%\n" + "(80%�� ������ case2 �̹Ƿ� ��ȣ.)";
	    	}
	    }
	    else {
	    	cpuresult = "�����ʿ�";
	    	result = "#ȯ�漳������ serverPcRISKCase ���� �ٽ� Ȯ���Ͻÿ�.";
	    	logger.error("#ȯ�漳������ serverPcRISKCas �� ��Ȯ��");
	    }
		return result;
	}
	//CPU ��뷮 ��ȣ���� ���
	public String SystemCPUStatus() {
		return cpuresult;
	}
	
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	static int ramtotal=0; //�� �޸�
	static int ramfree=0; //��밡�� �޸�
	static int ramusage=0; //��뷮
	static int ramfreespace=0;
	static String ramresult = null; //�޸� ���˻��¸� �˱����� ����
	//�޸� ��뷮
	public String SystemRAM() {
		String result = null;
		OperatingSystemMXBean osBean = (com.sun.management.OperatingSystemMXBean)ManagementFactory.getOperatingSystemMXBean();
		
		ramtotal = (int) Math.round( osBean.getTotalPhysicalMemorySize() / (1024*1024) / 1000.0);
		ramfree = (int) Math.round( osBean.getFreePhysicalMemorySize() / (1024*1024) / 1000.0);
		ramusage = (int) Math.round((Double.valueOf(osBean.getTotalPhysicalMemorySize() - osBean.getFreePhysicalMemorySize())) / Double.valueOf( osBean.getTotalPhysicalMemorySize() ) * 100);
		ramfreespace = (int) Math.round(Double.valueOf(osBean.getFreePhysicalMemorySize()) / Double.valueOf(osBean.getTotalPhysicalMemorySize()) * 100);
		
		if(serverPcRISKCase.equals("1")) { //serverPcRISKCase �� ȯ�漳��(Preferences)���� �������°���.
	    	if(ramusage < 50) {
	    		ramresult = "��ȣ";
	    		result = "RAM : " + ramtotal + "GB /" + ramfree + "GB ��밡��";
	    	}
	    	else if(ramusage < 80) {
	    		ramresult = "�����ʿ�";
	    		result = "RAM : " + ramtotal + "GB /" + ramfree + "GB ��밡�� \n" + "(����ڿ� ���� �� ���ʿ��� ���μ��� ���� �ȳ�.)";
	    	}
	    	else {
	    		ramresult = "�����ʿ�";
	    		result = "RAM : " + ramtotal + "GB /" + ramfree + "GB ��밡�� \n" + "(����ڿ� ���� �� ���ʿ��� ���μ��� ���� �ȳ�.)";
	    	}
	    }
	    else if(serverPcRISKCase.equals("2")) {
	    	if(ramusage < 80) {
	    		ramresult = "��ȣ";
	    		result = "RAM : " + ramtotal + "GB /" + ramfree + "GB ��밡��";
	    	}
	    	else {
	    		ramresult ="��ȣ";
	    		result = "RAM : " + ramtotal + "GB /" + ramfree + "GB ��밡�� \n" + "(80%�� ������ case2 �̹Ƿ� ��ȣ.)";
	    	}
	    }
	    else {
	    	ramresult = "�����ʿ�";
	    	result = "#ȯ�漳������  serverPcRISKCase ���� �ٽ� Ȯ���Ͻÿ�.";
	    	logger.error("#ȯ�漳������ serverPcRISKCas �� ��Ȯ��");
	    }
		return result;
	}
	//�޸� ��뷮 ��ȣ���� ���
	public String SystemRAMStatus() {
		return ramresult;
	}
	
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	static double disktotal=0.0; //��ũ ��ü�뷮
	static double diskuse=0.0; //��ũ ������� �뷮
	static double diskcal=0.0; //��ũ ����
	static int disknum=0; //��ũ ���˻��¸� �˱����� ����
	//��ũ ��뷮
	public String SystemDISK() {
		Runtime runtime = Runtime.getRuntime();
	    File[] roots = File.listRoots();
	    StringBuilder sb = new StringBuilder();
	    
    	for (File root : roots) {
    		if(root.getAbsolutePath().startsWith("A")) {
    			continue;
    		}
            disktotal = (double) (root.getTotalSpace()/(1024*1024*1024));
            diskuse = (double) (root.getFreeSpace()/(1024*1024*1024));
            diskcal = (disktotal-diskuse)/disktotal*100.0; //��ũ ����� ���ϱ�. ( (��ü�뷮-��밡���ѿ뷮)/��ü�뷮*100)
    		
            sb.append("����̺�� " + root.getAbsolutePath() + " : ");
            sb.append((int)disktotal + "GB / "); //����Ʈ�� 1024�� 3��(1073741824) �Ѱɷ� ������ GB�� ���´�.
            sb.append((int)diskuse + "GB ��밡��");
            
            if( (Math.round(diskcal)) > Integer.parseInt(diskdanger)) { //��ũ ��뷮�� ��ü���� 80% �̻� ����
            	sb.append("\n***" + root.getAbsolutePath() + "����̺�� �����ڿ� �����Ͽ� ��ũ ���� �ȳ� �� ��ȣ ����.\n");
    			sb.append("***������ �α����� : �α� ���� ���� ���̵� �Ϸ� \n");
            	sb.append("***������ ÷������ ���� : ÷������ ���� ���� ���� ���̵� �Ϸ�)");
            	disknum++; //80%�� �ʰ��ƴ��� �˱����� int�� ����
            }
            sb.append("\n");
    	}
    	roots.clone();
        //System.out.println(sb.toString());
        return sb.toString();
	}
	//��ũ ��뷮 ��ȣ���� ���
	public String SystemDISKStatus() {
		if(disknum > 0) { //��ũ�� 1�̻��̸� 80�۰� �Ѿ��ٴ� ���̹Ƿ� �����ʿ�
			return "�����ʿ�";
		}
		else {
			return "��ȣ";
		}
	}
	
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	static String FindRUNNING = "RUNNING";
	static String FindSTOPPED = "STOPPED";
	static String wasresult = null; //was ���ۻ��¸� �˱����� ����
	//WAS ���� ����(����)
	public String SystemWasService() { //���� ���ۻ��� Ȯ��
		if(was.equals("1")) {
			SystemInfo cmd = new SystemInfo();
			
			String command = cmd.inputCommand("sc query " + survicename); //��Ĺ�� ���ۻ��¸� �˷��ִ� cmd ��ɾ�. survicename�� ȯ�漳��(Preferences)���� �������°���.
			String result = cmd.execCommand(command);
			
			if(result.indexOf(FindRUNNING) != -1) { //result���� "RUNNING" �� ���ԵǾ� �ִ��� ã�°�.
				wasresult = "�����۵�Ȯ��";
			}
			else if(result.indexOf(FindSTOPPED) != -1){ //result���� "STOPPED" �� ���ԵǾ� �ִ��� ã�°�.
				wasresult = "������������";
			}
			else {
				wasresult = "��Ĺ ���񽺸��� �ٽ� Ȯ���Ͻÿ�";
			}
			
			String totalresult = "���μ��� �� : " + survicename + "\n" + "���� ���� Ȯ�� : " + wasresult;
			
			if(wasresult.equals("�����۵�Ȯ��")) {
				return totalresult;
			}
			else if(wasresult.equals("������������")) {
				return totalresult + "\n" + "(���� ��������.. ����ڿ� ���� �� ��ȣ ����)";
			}
			else {
				return "��Ĺ ���񽺸��� �ٽ� Ȯ���Ͻÿ�";
			}
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
	//WAS ���� ����(����) ��ȣ���� ���
	public String SystemWasServiceStatus() {
		if(wasresult == null) {
			return "�����ʿ�";
		}
		else if(wasresult.equals("�����۵�Ȯ��")){
			return "��ȣ";
		}
		else {
			return "�����ʿ�";
		}
	}
	
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	static int Logtotal = 0; //fatal �� �ִ��� ������ �˱����� ����
	static String fatalstate = "��ȣ";
	static String FindFatal = "fatal";
	//WAS �α� (WAS �α� Ư�̻���)
	public String SystemWasLogFatal() {
		
		if(expointVersion.equals("1")) {
			Logtotal=1;
			
			File f = new File(stderr);
			File[] files = f.listFiles();
			StringBuilder sb = new StringBuilder();
			
			FileReader fileReader = null;
			BufferedReader bufferedReader = null;
			String stderrFile = null;
			String line = null;
			
			int linecount = 1; //txt���� �� ������ �������� �˱� ���� ����
			int a=0; //fatal �� ���° �ٿ� �ִ��� �˱� ���� ����
			ArrayList<String> fatalFileContents =null; //fatal �߰� �� ������ ��������ִ� arraylist
			ArrayList<String> fatalFolderName =null; //fatal �߰� �� �� �ش� ���� �̸��� ��������ִ� arraylist
			
			try {
				if(f.exists()) {
					survicename = survicename.toLowerCase(); //�ҹ��ڷ� �ٲ��ִ� ����
					for (File file : files) {
						if (file.getName().startsWith(survicename + "-stderr.")) { //"���񽺸�-stderr." �� �����ϴ� ���� ���.	
							results2.add(file); //results2 �� ���
						}
					}
					
					if(results2.size() == 0) { //stderr ������ �ϳ��� ���� ��
						
						//fileReader = new FileReader(stderrFile);
						//bufferedReader = new BufferedReader(fileReader);
						
						sb.append("WAS �α� Ư�̻��� : Ư�̻��� ����\n");
						sb.append("(" + stderr + " ��ο� stderr ������ �����ϴ�.)\n");
					}
					else if(results2.size() < 15) { //stderr ������ 15�� �̸��϶�
						for(int i=0; i<results2.size(); i++) {
							stderrFile = results2.get(results2.size()-(i+1)).toString();
							fileReader = new FileReader(stderrFile);
							bufferedReader = new BufferedReader(fileReader);
							
							fatalFileContents = new ArrayList<String>();
							fatalFolderName = new ArrayList<String>();
							linecount=1;
							Logtotal=1;
							while((line = bufferedReader.readLine()) != null) {
								fatalFileContents.add(line);
								if(line.indexOf(FindFatal) != -1) {
									a=linecount;
									sb.append(stderrFile + " " + a + "��° �ٿ��� "+FindFatal + "�߰�\n");
									
									fatalFolderName.add(stderrFile.substring(stderrFile.toString().length()-21, stderrFile.toString().length()-4));
									Logtotal++;
									fatalstate = "�����ʿ�";
								}
								linecount++;
							}
							if(Logtotal > 1)
								SystemWasLogFatalFile(fatalFolderName, fatalFileContents);
							/*
							if(Logtotal > 0) { //Logtotal�� 1�̻��̸� fatal�� �߰��ߴٴ� �ǹ� �̹Ƿ� SystemWasLogFatalFile�޼ҵ� ȣ�� �Ͽ� ���� ����
								if(Integer.parseInt(fatalHowManyLine) == 0) {
									break;
								}
								else {
									SystemWasLogFatalFile(fatalFolderName, fatalFileContents);
								}
							}
							*/
						}
						if(fatalstate.equals("��ȣ"))
							sb.append("WAS �α� Ư�̻��� : Ư�̻��� ����\n");
						else {
							sb.append("(UJIBOSU ���� �� "+ "WASLogFatalFolder ������\n"
									+ "fatal �߰� ���� ���� --> ���� ���� ��û �� ��ȣ ����)");
						}
					}
					else if(results2.size() >= 15){ //stderr ������ 15�� �̻��϶�
						for(int i=0; i<15; i++) {
							stderrFile = results2.get(results2.size()-(i+1)).toString();
							fileReader = new FileReader(stderrFile);
							bufferedReader = new BufferedReader(fileReader);
							
							fatalFileContents = new ArrayList<String>();
							fatalFolderName = new ArrayList<String>();
							linecount=1;
							Logtotal=1;
							while((line = bufferedReader.readLine()) != null) {
								fatalFileContents.add(line);
								if(line.indexOf(FindFatal) != -1) {
									a=linecount;
									sb.append(stderrFile + " " + a + "��° �ٿ��� "+FindFatal + "�߰�\n");
									
									fatalFolderName.add(stderrFile.substring(stderrFile.toString().length()-21, stderrFile.toString().length()-4));
									Logtotal++;
									fatalstate = "�����ʿ�";
								}
								linecount++;
							}
							if(Logtotal > 1)
								SystemWasLogFatalFile(fatalFolderName, fatalFileContents);
							/*
							if(Logtotal > 0) { //Logtotal�� 1�̻��̸� fatal�� �߰��ߴٴ� �ǹ� �̹Ƿ� SystemWasLogFatalFile�޼ҵ� ȣ�� �Ͽ� ���� ����
								if(Integer.parseInt(fatalHowManyLine) == 0) {
									break;
								}
								else {
									SystemWasLogFatalFile(fatalFolderName, fatalFileContents);
								}
							}
							*/
						}
						if(fatalstate.equals("��ȣ"))
							sb.append("WAS �α� Ư�̻��� : Ư�̻��� ����\n");
						else {
							sb.append("(UJIBOSU ���� �� "+ "WASLogFatalFolder ������\n"
									+ "fatal �߰� ���� ���� --> ���� ���� ��û �� ��ȣ ����)");
						}
					}
					
				} else sb.append("waslog ��� or ȯ�漳��.txt ��Ȯ�� �ٶ�.\n");
				bufferedReader.close();
				fileReader.close();
			}
			catch(FileNotFoundException e) {
				e.printStackTrace();
				logger.error("error message" , e);
			}catch(IOException e) {
				e.printStackTrace();
				logger.error("error message" , e);
			}catch(NullPointerException e) {
				logger.error("error message" , e);
				//e.printStackTrace();
			}
			//System.out.println(sb.toString());
			return sb.toString();
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
	//WAS �α� (WAS �α� Ư�̻���) ��ȣ���� ���
	public String SystemWasLogFatalStatus() {
		if(fatalstate.equals("��ȣ"))
			return "��ȣ";
		else
			return "�����ʿ�";
	}
	
	//WAS �α� (WAS �α� Ư�̻���) fatal �߽߰� ���Ʒ� n���� ���� ���Ϸ� �����ϱ� ���� �޼ҵ�
	public void SystemWasLogFatalFile(ArrayList<String> fatalFolderName , ArrayList<String> fatalFileContents) {
		File filefd = null;
		File filesn = null;
		FileWriter writer = null;
		String filefolderPath = fixPath+"\\WASLogFatalFolder";
		
		filefd = new File(filefolderPath);
		try {
			int linecount; //txt���� �� ������ �������� �˱� ���� ����
			int fatalLine; //fatal �� ���° �ٿ� �ִ��� �˱� ���� ����
			int fatalPath=0; //fatal �� ���� �̸��� �����Ҷ� ���� ���� ����
			Iterator it = null;
			
			if(filefd.exists()) { //WASLogFatalFolder ������ ���� ���
				linecount=1;
				fatalLine=0;
				it = fatalFileContents.iterator();
				filesn = new File(filefolderPath+"\\"+fatalFolderName.get(fatalPath++)+".txt");
				writer = new FileWriter(filesn, true);
				while(it.hasNext()) {
					if(it.next().equals(FindFatal)) {
						fatalLine=linecount;
						if(fatalLine < Integer.parseInt(fatalHowManyLine)) //fatal �߰� ��ġ�� ������ 50�� �̸��϶�
							for(int m=0; m<fatalLine; m++) //ex) m=0; m<10; m++
								writer.write(fatalFileContents.get(m) + "\r\n"); //ex) get(0) -> get(1) .... -> get(9)
						
						else if(fatalLine >= Integer.parseInt(fatalHowManyLine)) //fatal �߰� ��ġ�� ������ 50�� �̻��϶� ex)63 >= 50
							for(int m=0; m<Integer.parseInt(fatalHowManyLine); m++)
								writer.write(fatalFileContents.get((fatalLine-Integer.parseInt(fatalHowManyLine))+m) + "\r\n"); //ex) get((63-50)+0) -> get((63-50)+1) .... -> get((63-50)+49)

						if((fatalFileContents.size()-fatalLine) < Integer.parseInt(fatalHowManyLine)) //fatal �߰� ��ġ�� �Ʒ����� 50�� �̸��϶� ex) 63-30 < 50
							for(int m=0; m<(fatalFileContents.size()-fatalLine); m++) //ex) m=0; m<33; m++
								writer.write(fatalFileContents.get(m+fatalLine) + "\r\n"); //ex) get(0+30) -> get(1+33) .... -> get(32+30)
								
						else if((fatalFileContents.size()-fatalLine) > Integer.parseInt(fatalHowManyLine)) //fatal �߰� ��ġ�� �Ʒ����� 50�� �̻��϶� ex) 63-10 > 50
							for(int m=0; m<Integer.parseInt(fatalHowManyLine); m++) 
								writer.write(fatalFileContents.get(m+fatalLine) + "\r\n"); //ex) get(0+10) -> get(1+10) .... -> get(49+10)
						
						writer.write("\r\n" + "---------------------------------------------���뼱---------------------------------------------" + "\r\n" + "\r\n");
						writer.flush();
					}
					linecount++;
				}
			}
			else if(filefd.exists() == false){ //WASLogFatalFolder ������ ���� ���
				filefd.mkdir(); //WASLogFatalFolder ���� ����
				
				linecount=1;
				fatalLine=0;
				it = fatalFileContents.iterator();
				filesn = new File(filefolderPath+"\\"+fatalFolderName.get(fatalPath++)+".txt");
				writer = new FileWriter(filesn, true);
				while(it.hasNext()) {
					if(it.next().equals(FindFatal)) {
						fatalLine=linecount;
						if(fatalLine < Integer.parseInt(fatalHowManyLine))
							for(int m=0; m<fatalLine; m++)
								writer.write(fatalFileContents.get(m) + "\r\n");
						
						else if(fatalLine >= Integer.parseInt(fatalHowManyLine))
							for(int m=0; m<Integer.parseInt(fatalHowManyLine); m++)
								writer.write(fatalFileContents.get((fatalLine-Integer.parseInt(fatalHowManyLine))+m) + "\r\n");

						if((fatalFileContents.size()-fatalLine) < Integer.parseInt(fatalHowManyLine))
							for(int m=0; m<(fatalFileContents.size()-fatalLine); m++)
								writer.write(fatalFileContents.get(m+fatalLine) + "\r\n");
								
						else if((fatalFileContents.size()-fatalLine) > Integer.parseInt(fatalHowManyLine))
							for(int m=0; m<Integer.parseInt(fatalHowManyLine); m++)
								writer.write(fatalFileContents.get(m+fatalLine) + "\r\n");
						
						writer.write("\r\n" + "---------------------------------------------���뼱---------------------------------------------" + "\r\n" + "\r\n");
						writer.flush();
					}
					linecount++;
				}
			}
			writer.close();
		}catch(FileNotFoundException e) {
			logger.error("error message" , e);
			e.printStackTrace();
		}catch(IOException e) {
			logger.error("error message" , e);
			e.printStackTrace();
		}
	}
	
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	String logdaydate = null;
	//WAS �α� (�ֱ� ���� ����� ��¥)
	public String SystemWasLogDay() {
		if(expointVersion.equals("1")) {
			File f = new File(stdout); //stdout �� ȯ�漳��(Preferences)���� �������°���.
			File[] files = f.listFiles();	
			String result = null;
			try {
				if(f.exists()) {
					survicename = survicename.toLowerCase(); //���񽺸� �ҹ��ڷ� ��ȯ
					for (File file : files) {
						if (file.getName().startsWith(survicename + "-stdout.")) {  //"esf-stdout." �� �����ϴ� ���� ���.
							results1.add(file);
						}
					}
					if(results1.size() == 0) { //stdout ������ �ϳ��� ���� ��
						logdaydate = "��ȣ";
						result = "stdout ������ �����ϴ�.";
					}
					else { //stdout ������ ���� ��
						File FindLOGOUT = results1.get(results1.size()-1); //�������� ���� �����ؼ� �ֱ�.
						logdaydate = FindLOGOUT.toString().substring(FindLOGOUT.toString().length()-14, FindLOGOUT.toString().length()-4);
						result = "�ֱ� ���� ����� ��¥ : " + FindLOGOUT.toString().substring(FindLOGOUT.toString().length()-14, FindLOGOUT.toString().length()-4);
					}
				}
				else {
					logdaydate = "�����ʿ�";
					result = "waslog ��� or ȯ�漳��.txt�� ��Ȯ�� �ٶ�.";
				}
				files.clone();
			}
			catch(Exception e) {
				e.printStackTrace();
				logger.error("error message" , e);
			}
			return result;
		}
		else if(expointVersion.equals("2")) {
			logdaydate = "�����ʿ�";
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
	//WAS �α� (�ֱ� ���� ����� ��¥) ��ȣ���� ���
	public String SystemWasLogDayStatus() {
		String pattern = "^\\d{4}-\\d{2}-\\d{2}$"; //yyyy-mm-dd �� ���Խ�
		
		if(logdaydate.equals("��ȣ")) {
			return "��ȣ";
		}
		else if(logdaydate.equals("�����ʿ�")) {
			return "�����ʿ�";
		}
		if(logdaydate.matches(pattern) != true) {
			return "�����ʿ�";
		}
		else if(logdaydate.matches(pattern)) {
			return "��ȣ";
		}
		else {
			return "�����ʿ�";
		}
		//return "��¥ ������� �Ǿ����� ��ȣ ����";
	}
	
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	static String dbmsaction = null;
	//DBMS ����
	public String SystemDBMS() {
		if(expointVersion.equals("1")) {
			if(dbms.equals("1")) {
				Connection conn = null;
				Statement stmt = null;
				ResultSet rs = null;
				String dbmsresult = null;
				final String url = defaulturl+ip+":"+port+";"
						+ "DatabaseName="+dbname;
				
				try {
					Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver"); //sqljdbc
					try {
						conn = DriverManager.getConnection(url, ID, PW); //(url, �������̵�, �����н�����)
						logger.debug("��� ���� ����!");
						
					}catch(SQLException error) {
						logger.error("��� ���� ���� ip,port,id,pw�� ��Ȯ�ϰ� �Է��Ͽ����� Ȯ�ιٶ�.");
					}
					
					stmt = conn.createStatement();
					rs = stmt.executeQuery("SELECT CH_NM, COUNT(*) AS " +"\'�� ��û�Ǽ�\'" + " FROM dbo.APPR_LIST " + "GROUP BY CH_NM"); //������ϴ� ���뿡 ���� ������
					
					String field1 = null;
					String field2 = null;
					
					while( rs.next() ) {
						field1 = rs.getString("CH_NM"); //�ʵ� �̸�
						field2 = rs.getString("�� ��û�Ǽ�");
						//System.out.println(field1 + " -> " + field2 ); //ä�κ� ��û�Ǽ� ��ȸ
					}
					if(field1.isEmpty() == true && field2.isEmpty() == true) {
						dbmsaction = "�����ʿ�";
						//System.out.println("��û�Ǽ� ��ȸ �� ���� Ȯ�� : ��ȸ �Ұ�.");
						dbmsresult = "��û�Ǽ� ��ȸ �� ���� Ȯ�� : ��ȸ �Ұ�.";
					}
					else {
						dbmsaction = "��ȣ";
						//System.out.println("��û�Ǽ� ��ȸ �� ���� Ȯ�� : ��ȸ �� ���� ��ȣ");
						dbmsresult = "��û�Ǽ� ��ȸ �� ���� Ȯ�� : ��ȸ �� ���� ��ȣ.";
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
				return dbmsresult;
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
		else if(expointVersion.equals("2")) {
			return "#�������� Ȯ���Ͻÿ�.";
		}
		else if(expointVersion.equals("3")){
			return "#�������� Ȯ���Ͻÿ�.";
		}
		else {
			logger.error("#ȯ�漳������ Expoint Version �� ��Ȯ��");
			JOptionPane.showMessageDialog(null, "db ���� ������ �ùٸ��� �ʽ��ϴ�");
			
			return "#ȯ�漳������  Expoint Version ���� �ٽ� Ȯ���Ͻÿ�.";
		}
	}
	
	//DBMS ���� ��ȣ���� ���
	public String SystemDBMSStatus() {
		//System.out.println(dbms);
		if(dbmsaction == null) {
			return "�����ʿ�";
		}
		else if(dbmsaction.equals("��ȣ")){
			return "��ȣ";
		}
		else {
			return "�����ʿ�";
		}
	}
	
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	static int insatotal = 0; //���� ���а�찡 ������ ��ȣ���� �Ǵ��ϴ� int ����
	static String insaresult = null; //���˻��¸� ����ϱ� ���� ����
	//�λ翬�� ����
	public String SystemBatchService() {
		if(expointVersion.equals("1")) {
			if(dbms.equals("1")) {
				StringBuilder sb = new StringBuilder();
				Connection conn = null;
				Statement stmt = null;
				ResultSet rs = null;
				final String url = defaulturl+ip+":"+port+";DatabaseName="+dbname;
				
				ArrayList<String> insaContents = new ArrayList<String>(); //�λ翬�� ��ġ���� ���г����� �ֱ����� ArrayList
				
				try {
					Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver"); //sqljdbc
					conn = DriverManager.getConnection(url, ID, PW); //(url, �������̵�, �����н�����)
					stmt = conn.createStatement();
					
					Calendar cal = Calendar.getInstance ( ); //��¥ ��� �Լ�
					cal.add(cal.DATE, -1); //1����
					String date1 = cal.get(cal.YEAR) + " -" + (cal.get(cal.MONTH)+1) + " -" + cal.get(cal.DATE);
					//System.out.println(date1);
					
					cal.add(cal.DATE, -14); //1���� + 14����
					String date2 = cal.get(cal.YEAR) + " -" + (cal.get(cal.MONTH)+1) + " -" + cal.get(cal.DATE);
					//System.out.println(date2);
					
					String[] select = {"CHART_TYPE", "CHART_YEAR", "CHART_MONTH", "CHART_DAY", "CHART_TITLE", "OPT1"};
					
					rs = stmt.executeQuery("SELECT " + select[0] +", "+ select[1] +", "+ select[2] +", "+ select[3] +", "+ select[4] +", "+ select[5] +" "
							+ "FROM dbo.BATCH_CHART_LOG "
							+ "WHERE CHART_TYPE='Batch_Run_Log' "
							+ "AND CHART_TITLE='InsaSyncJob' "
							+ "AND RG_DT BETWEEN CONVERT(datetime, '"+ date2 +" 00:00:01') AND CONVERT(datetime,'"+ date1 +" 23:59:59')");

					String field[] = new String[select.length];
					
					//////////////////////////////////////////////////////
					//���� while ���� String �迭�� �� ���
					if( rs.next() ) {
						insatotal = insatotal + 1;
						while( rs.next() ) {
							for(int i=0; i<select.length; i++) {
								field[i] = rs.getString(select[i]);
							}
							if(field[5].equals("F")) { //���� ���а� ������ �۵�
								insatotal++; //���а� ������ insatotal�� �������� ���� �Ǵ�
								insaContents.add("(" + field[2] + "�� " + field[3] + "�Ͽ� ���� �߰�. DB�� ���չ��� Ȩ���������� Ȯ���Ͻÿ�.)\n"); //������ ��¥�� insaContents�� �߰�
							}
						}
					}
					else { //���� �˻��Ǵ� ������ ���� ��
						sb.append("(DB�� ������ ������ �λ翬�� ��ġ���� ������ ������ ����.\n" + "DB�� ���չ��� Ȩ���������� �ѹ� Ȯ���Ͻÿ�.)\n");
					}
					
					Iterator it = insaContents.iterator();
					if(insatotal == 0) { //���� 0�� ��� ��ȸ�Ǵ� ������ ��� �����ʿ�
						insaresult = "�����ʿ�";
					}
					else if(insatotal == 1) { //���� 1�� ��� ��ȸ�ǰ� ��� �����̿��� ��ȣ
						insaresult = "��ȣ";
						if(insaContents.isEmpty() == true) { //��� �����̸� insaContents�� ������ ����ߵ�
							sb.append("��ġ���� ���� Ȯ�� : ������ Ȯ�� \n");
						}
						else {
							//??
						}
					}
					else { //���� 2�̻��� ��� ��ȸ�ǰ� ���а� �־ �����ʿ�
						insaresult = "�����ʿ�";
						if(insaContents.isEmpty() == false) { //���а� ������ insaContents�� ������ �־�ߵ�
							sb.append("��ġ���� ���� Ȯ�� : �λ翬�� ��ġ���� ���� �߰� \n");
							while(it.hasNext()) {
								sb.append(it.next());
							}
						}
						else {
							//??
						}
					}
					
					///////////////////////////////////////////////////////
					/*//���� �ּ��� String �迭�� �Ƚ��� ���
					String field1 = null;
					String field2 = null;
					String field3 = null;
					String field4 = null;
					String field5 = null;
					String field6 = null;
					while( rs.next() ) {
						field1 = rs.getString("CHART_TYPE"); //�ʵ� �̸�
						field2 = rs.getString("CHART_YEAR");
						field3 = rs.getString("CHART_MONTH");
						field4 = rs.getString("CHART_DAY");
						field5 = rs.getString("CHART_TITLE");
						field6 = rs.getString("OPT1");
						System.out.println(field1 + " " + field2 + " " + field3 + " " + field4 + " "+ field5 + " "+ field6);
						
						if(field6.equals("F")) { //���� ���а� ������ total�� 1���� ���� �Ʒ� if������ ��ȣ �Ǵ�.
							sb.append(field3 + "�� " + field4 + "�Ͽ� ���� �߰�. DB�� ���չ��� Ȩ���������� Ȯ���Ͻÿ�. \n");
							total++;
						}
						else if(field6.isEmpty()) //���� �˻��Ǵ� ������ ���� ��
							sb.append("�˻��Ǵ� ������ ����. DB�� ���չ��� Ȩ���������� Ȯ���Ͻÿ�. \n");
					}
					*/
					
					//System.out.println(sb.toString());
					rs.close();
					stmt.close();
					conn.close();
				}
				catch(SQLException e) {
					e.printStackTrace();
					logger.error("error message" , e);
				}
				catch( ClassNotFoundException e) {
					e.printStackTrace();
					logger.error("error message" , e);
				}
				return sb.toString();
			}
			else if(dbms.equals("2")) {
				insaresult = "�����ʿ�";
				return "#�������� Ȯ���Ͻÿ�.";
			}
			else if(dbms.equals("3")){
				insaresult = "�����ʿ�";
				return "#�������� Ȯ���Ͻÿ�.";
			}
			else {
				logger.error("#ȯ�漳������ DBMS �� ��Ȯ��");
				return "#ȯ�漳������  DBMS ���� �ٽ� Ȯ���Ͻÿ�.";
			}
			
		}
		else if(expointVersion.equals("2")) {
			insaresult = "�����ʿ�";
			return "#�������� Ȯ���Ͻÿ�.";
		}
		else if(expointVersion.equals("3")){
			insaresult = "�����ʿ�";
			return "#�������� Ȯ���Ͻÿ�.";
		}
		else {
			logger.error("#ȯ�漳������ Expoint Version �� ��Ȯ��");
			return "#ȯ�漳������  Expoint Version ���� �ٽ� Ȯ���Ͻÿ�.";
		}
	}
	//�λ翬�� ���� ��ȣ���� ���
	public String SystemBatchServiceStatus() {
		return insaresult;
	}
	
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//���ε� ����
	public String SystemUpload() {
		if(expointVersion.equals("1")) {
			long sum=0, DirectorySize=0;
			String uploadsize = null;
			String result = null;
			
			File f = new File(upload); //upload �� ȯ�漳��(Preferences)���� �����°���.
			File[] files = f.listFiles();
			
			try {
				if(f.exists()) {
					for (File file : files) {
						if (file.getName().startsWith("20"))   //"20"���� �����ϴ� ���� ���.
							results3.add(file); //20���� �����ϴ� ���� results3�� ��� ���
					}
					if(results3.size() == 0) { //�ֱ� 3�����ε� ���� ������ �Ѱ��� ������
						DirectorySize=0;
						sum=0;
					}
					else if(results3.size() < 3) { //�ֱ� 3�����ε� ���� ������ 3�� �̸��϶�
						for(int i=0; i<results3.size(); i++) {
							uploadsize = results3.get(results3.size()-(i+1)).toString(); //�� �ڿ������� ���� ���.
							//System.out.println(uploadsize);
							File FindDirectory = new File(uploadsize);
							DirectorySize = getSize(FindDirectory); //����Ʈ
							sum += DirectorySize;
						}
					}
					else {
						for(int i=0; i<3; i++) { //�ֱ� 3�����̹Ƿ� i<3���� ����
							uploadsize = results3.get(results3.size()-(i+1)).toString(); //�� �ڿ������� 3�� ���� ���.
							//System.out.println(uploadsize);
							File FindDirectory = new File(uploadsize);
							DirectorySize = getSize(FindDirectory); //����Ʈ
							sum += DirectorySize;
						}
					}
					
					if(sum >= 1073741824) { //���� 1,000,000,000����Ʈ�� ������ �Ⱑ ������ ���
						sum = sum/(1024*1024*1024);
						result = upload + " (�ֱ� 3����  " + sum + "GB)\n"
								+ "(" + upload.substring(0, 2) + "����̺� �뷮�� ������ �� ����ڿ� �����ϰ� ���� �ȳ�.)";
					}
					else { //���� 1,000,000,000����Ʈ�� �ȳ����� �ް� ������ ���
						sum = sum/(1024*1024);
						result = upload + " (�ֱ� 3����  " + sum + "MB)\n"
								+ "(" + upload.substring(0, 2) + "����̺� �뷮�� ������ �� ����ڿ� �����ϰ� ���� �ȳ�.)";
					}
				} else result = "���ε� ���� ��� or ���� ���� or ȯ�漳��.txt ��Ȯ�� �ٶ�.";
			}
			catch(Exception e) {
				logger.error("error message" , e);
				e.printStackTrace();
			}
			return result;
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
	public static long getSize(File f) { //���ε� ���� ������ ��� �޼ҵ�
		long size = 0; // Store the total size of all files
		if (f.isDirectory()) { //������ ��
			File[] files = f.listFiles(); // All files and subdirectories
			for (int i = 0; i < files.length; i++) {
				size += getSize(files[i]); // Recursive call
			}
		}else size += f.length(); // Base case ������ ��
		return size;
	}
	//���ε� ���� ��ȣ���� ���
	public String SystemuploadStatus() {
		if(expointVersion.equals("1")) {
			return "��ȣ";
		}
		else if(expointVersion.equals("2")) {
			return "�����ʿ�";
		}
		else if(expointVersion.equals("3")){
			return "�����ʿ�";
		}
		else {
			return "�����ʿ�";
		}
	}
	
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//������ �α� ���̵�
	public String SystemOldLog() {
	    StringBuilder sb = new StringBuilder();
	    
	    sb.append("@@������ �α� ���� ���̵�@@ \n");
	    sb.append(stdout + " ��η� �̵� \n");
	    sb.append("����ڿ� �����Ͽ� ������ �α�(stdout����)�� ����ÿ�.");
	    
        //System.out.println(sb.toString());
        return sb.toString();
	}
	//������ �α� ���̵� ��ȣ���
	public String SystemOldLogStatus() {
		return "��ȣ";
	}
	
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//������ ÷������ ���̵�
	public String SystemOldFile() {
		StringBuilder sb = new StringBuilder();
	    
	    sb.append("������ �α� ÷�������� ��ġ����\n");
	    sb.append("��ġ ���鼭 �ڵ����� ���� ��.");
		
        //System.out.println(sb.toString());
        return sb.toString();
	}
	//������ ÷������ ��ȣ���
	public String SystemOldFileStatus() {
		return "��ȣ";
	}
	
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	static String[] channelname = new String[40]; //ä�� �̸��� ������� ��Ʈ���迭
	static String[] channecode = new String[40]; //ä�κ� �ڵ带 ������� ��Ʈ���迭
	//����ä�� ����
	public String SystemChannel() {
		if(expointVersion.equals("1")) {
			if(dbms.equals("1")) {
				StringBuilder sb = new StringBuilder();
				Connection conn = null;
				Statement stmt = null;
				ResultSet rs1 = null; //ä�� �̸��� �ڵ带 �˾Ƴ�
				ResultSet rs2 = null; //�� ��û�Ǽ�
				ResultSet rs3 = null; //�� �����Ǽ�
				ResultSet rs4 = null; //�� �ݷ��Ǽ�
				final String url = defaulturl+ip+":"+port+";DatabaseName="+dbname;

				int i=0;
				String field1 = null; //ä���̸�
				String field2 = null; //ä���ڵ�
				String field3 = null; //ä���̸�
				String field4 = null; //�� ��û �Ǽ�
				String field5 = null; //�� ���� �Ǽ�
				String field6 = null; //�� �ݷ� �Ǽ�
				
				try {
					Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver"); //sqljdbc
					conn = DriverManager.getConnection(url, ID, PW); //(url, �������̵�, �����н�����)
					stmt = conn.createStatement();
					
					Calendar cal = Calendar.getInstance ( ); //��¥ ��� �Լ�
					cal.add(cal.DATE, -1); //1����
					String date1 = cal.get(cal.YEAR) + " -" + (cal.get(cal.MONTH)+1) + " -" + cal.get(cal.DATE);
					
					cal.add(cal.DATE, -30); //30����
					String date2 = cal.get(cal.YEAR) + " -" + (cal.get(cal.MONTH)+1) + " -" + cal.get(cal.DATE);
					
					rs1 = stmt.executeQuery("SELECT CH_NM AS 'ä�θ�', CH_CODE AS 'ä���ڵ�' " 
					      + "FROM dbo.APPR_LIST GROUP BY CH_NM, CH_CODE");
					
					while( rs1.next() ) {
						field1 = rs1.getString("ä�θ�"); //�ʵ� �̸�
						field2 = rs1.getString("ä���ڵ�");
						channelname[i] = field1; //ä���̸��� ����
						channecode[i] = field2; //ä���ڵ带 ����
						i++;	
					}
					i=0;
					while(channelname[i] != null && channecode[i] != null) {
						//�� ��û�Ǽ� ��ȸ
						rs2 = stmt.executeQuery("SELECT CH_NM AS 'ä�θ�', CH_CODE, COUNT(*) AS 'ä�κ� ��û�Ǽ� ��ȸ' " 
								+ "FROM dbo.APPR_LIST "
								+ "where CH_CODE = " + channecode[i] + " AND ACK_RESULT != 'X' AND PROCESS_STEP = '5' "
								+ "AND REQ_DT BETWEEN CONVERT(datetime, '"+ date2 +" 00:00:01') AND CONVERT(datetime,'"+ date1 +" 23:59:59') "
								+ "GROUP BY CH_NM, CH_CODE "
								+ "ORDER BY CH_CODE ASC;");
						if(rs2.next()) { //���� ���� ���� ���
							field3 = rs2.getString("ä�θ�");
							field4 = rs2.getString("ä�κ� ��û�Ǽ� ��ȸ");
							sb.append("ä�θ� : " + field3 + "\n");
							sb.append("�� ��û �Ǽ� : (" + field4 + ")��" + "\n");
						}else { //���� ���� ���
							sb.append("ä�θ� : " + channelname[i] + "\n");
							sb.append("�� ��û �Ǽ� : (0)��\n");
						}
						
						//�� ����Ǽ� ��ȸ
						rs3 = stmt.executeQuery("SELECT CH_NM AS 'ä�θ�', CH_CODE, COUNT(*) AS 'ä�κ� ����Ǽ� ��ȸ' " 
								+ "FROM dbo.APPR_LIST "
								+ "where CH_CODE = " + channecode[i] + " AND ACK_RESULT != 'Y' AND PROCESS_STEP = '5' "
								+ "AND REQ_DT BETWEEN CONVERT(datetime, '"+ date2 +" 00:00:01') AND CONVERT(datetime,'"+ date1 +" 23:59:59') "
								+ "GROUP BY CH_NM, CH_CODE "
								+ "ORDER BY CH_CODE ASC;");
						if(rs3.next()) {
							field5 = rs3.getString("ä�κ� ����Ǽ� ��ȸ");
							sb.append("�� ���� �Ǽ� : (" + field5 + ")��" + "\n");
						}else {
							sb.append("�� ���� �Ǽ� : (0)��\n");
						}
						
						//�� �ݷ��Ǽ� ��ȸ
						rs4 = stmt.executeQuery("SELECT CH_NM AS 'ä�θ�', CH_CODE, COUNT(*) AS 'ä�κ� �ݷ��Ǽ� ��ȸ' " 
								+ "FROM dbo.APPR_LIST "
								+ "where CH_CODE = " + channecode[i] + " AND ACK_RESULT != 'N' AND PROCESS_STEP = '5' "
								+ "AND REQ_DT BETWEEN CONVERT(datetime, '"+ date2 +" 00:00:01') AND CONVERT(datetime,'"+ date1 +" 23:59:59') "
								+ "GROUP BY CH_NM, CH_CODE "
								+ "ORDER BY CH_CODE ASC;");
						if(rs4.next()) {
							field6 = rs4.getString("ä�κ� �ݷ��Ǽ� ��ȸ");
							sb.append("�� �ݷ� �Ǽ� : (" + field6 + ")��" + "\n");
						}else {
							sb.append("�� �ݷ� �Ǽ� : (0)��\n");
						}
						i++;
						sb.append("--------------------------------------------------------------\n");
					}
					
					//System.out.println(sb.toString());
					rs1.close();
					rs2.close();
					rs3.close();
					rs4.close();
					stmt.close();
					conn.close();
				}
				catch(SQLException e) {

					e.printStackTrace();
					logger.error("error message" , e);
				}
				catch( ClassNotFoundException e) {
					e.printStackTrace();
					logger.error("error message" , e);
				}
				return sb.toString();
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
	//����ä�� ����
	public String SystemChannelStatus() {
		StringBuilder sb = new StringBuilder();
		int i=0;
		if(expointVersion.equals("1")) {
			if(dbms.equals("1")) {
				while(channelname[i] != null && channecode[i] != null) {
					sb.append(channelname[i] + "(" + channecode[i] + ") => ��ȣ\n");
					i++;
				}
				//System.out.println(sb.toString());
				return sb.toString();
			}
			else if(dbms.equals("2")) {
				return "#�������� Ȯ���Ͻÿ�.";
			}
			else if(dbms.equals("3")){
				return "#�������� Ȯ���Ͻÿ�.";
			}
			else {
				return "#ȯ�漳������  DBMS ���� �ٽ� Ȯ���Ͻÿ�.";
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
}