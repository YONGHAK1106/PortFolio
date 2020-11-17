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
	//CPU 사용량
	public String SystemCPU() {
		String result = null;
		final OperatingSystemMXBean osBean = (com.sun.management.OperatingSystemMXBean)ManagementFactory
	    		.getOperatingSystemMXBean();
	    
	    while(cpui<=3000){ //총 3초. 3번 측정. (맨 처음에 측정되는 값은 제외시킬거임)
	    	cpuload = osBean.getSystemCpuLoad();

	    	if(cpuload < 0.0)
	    		continue;
	    	
	    	CPUsage=cpuload*100;
	    	//System.out.println("CPU Usage : "+Math.round(CPUsage)+"%");

	    	if(cpui>1999) //처음에 측정되는 cpu값은 제외하는 조건문
	    		cpusum += CPUsage;
	    	try {
	    		Thread.sleep(cpui);
	    	} catch (InterruptedException e) {
	    		e.printStackTrace();
	    	}
	    	cpui=cpui+1000; //1초 증가시키는 증감식. 1000 == 1초
	    	cpuj++;
	    }
	    cpucal = (int) Math.round(cpusum/(cpuj-1)); //평균값 계산
	    
	    if(serverPcRISKCase.equals("1")) { //serverPcRISKCase 는 환경설정(Preferences)에서 가져오는거임.
	    	if(cpucal < 50) {
	    		cpuresult = "양호";
	    		result = "CPU : " + Integer.toString(cpucal) + "%";
	    	}
	    	else if(cpucal < 80) {
	    		cpuresult = "점검필요";
	    		result = "CPU : " + Integer.toString(cpucal) + "%\n" + "(담당자와 상의하여 불필요한 프로세스 중지 안내)";
	    	}
	    	else {
	    		cpuresult = "점검필요";
	    		result = "CPU : " + Integer.toString(cpucal) + "%\n" + "(담당자와 상의하여 불필요한 프로세스 중지 안내)";
	    	}
	    }
	    else if(serverPcRISKCase.equals("2")) {
	    	if(cpucal < 80) {
	    		cpuresult = "양호";
	    		result = "CPU : " + Integer.toString(cpucal) + "%";
	    	}
	    	else {
	    		cpuresult = "양호";
	    		result = "CPU : " + Integer.toString(cpucal) + "%\n" + "(80%가 넘지만 case2 이므로 양호.)";
	    	}
	    }
	    else {
	    	cpuresult = "점검필요";
	    	result = "#환경설정에서 serverPcRISKCase 값을 다시 확인하시오.";
	    	logger.error("#환경설정에서 serverPcRISKCas 값 재확인");
	    }
		return result;
	}
	//CPU 사용량 양호기준 출력
	public String SystemCPUStatus() {
		return cpuresult;
	}
	
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	static int ramtotal=0; //총 메모리
	static int ramfree=0; //사용가능 메모리
	static int ramusage=0; //사용량
	static int ramfreespace=0;
	static String ramresult = null; //메모리 점검상태를 알기위한 변수
	//메모리 사용량
	public String SystemRAM() {
		String result = null;
		OperatingSystemMXBean osBean = (com.sun.management.OperatingSystemMXBean)ManagementFactory.getOperatingSystemMXBean();
		
		ramtotal = (int) Math.round( osBean.getTotalPhysicalMemorySize() / (1024*1024) / 1000.0);
		ramfree = (int) Math.round( osBean.getFreePhysicalMemorySize() / (1024*1024) / 1000.0);
		ramusage = (int) Math.round((Double.valueOf(osBean.getTotalPhysicalMemorySize() - osBean.getFreePhysicalMemorySize())) / Double.valueOf( osBean.getTotalPhysicalMemorySize() ) * 100);
		ramfreespace = (int) Math.round(Double.valueOf(osBean.getFreePhysicalMemorySize()) / Double.valueOf(osBean.getTotalPhysicalMemorySize()) * 100);
		
		if(serverPcRISKCase.equals("1")) { //serverPcRISKCase 는 환경설정(Preferences)에서 가져오는거임.
	    	if(ramusage < 50) {
	    		ramresult = "양호";
	    		result = "RAM : " + ramtotal + "GB /" + ramfree + "GB 사용가능";
	    	}
	    	else if(ramusage < 80) {
	    		ramresult = "점검필요";
	    		result = "RAM : " + ramtotal + "GB /" + ramfree + "GB 사용가능 \n" + "(담당자와 상의 후 불필요한 프로세스 중지 안내.)";
	    	}
	    	else {
	    		ramresult = "점검필요";
	    		result = "RAM : " + ramtotal + "GB /" + ramfree + "GB 사용가능 \n" + "(담당자와 상의 후 불필요한 프로세스 중지 안내.)";
	    	}
	    }
	    else if(serverPcRISKCase.equals("2")) {
	    	if(ramusage < 80) {
	    		ramresult = "양호";
	    		result = "RAM : " + ramtotal + "GB /" + ramfree + "GB 사용가능";
	    	}
	    	else {
	    		ramresult ="양호";
	    		result = "RAM : " + ramtotal + "GB /" + ramfree + "GB 사용가능 \n" + "(80%가 넘지만 case2 이므로 양호.)";
	    	}
	    }
	    else {
	    	ramresult = "점검필요";
	    	result = "#환경설정에서  serverPcRISKCase 값을 다시 확인하시오.";
	    	logger.error("#환경설정에서 serverPcRISKCas 값 재확인");
	    }
		return result;
	}
	//메모리 사용량 양호기준 출력
	public String SystemRAMStatus() {
		return ramresult;
	}
	
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	static double disktotal=0.0; //디스크 전체용량
	static double diskuse=0.0; //디스크 사용중인 용량
	static double diskcal=0.0; //디스크 사용률
	static int disknum=0; //디스크 점검상태를 알기위한 변수
	//디스크 사용량
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
            diskcal = (disktotal-diskuse)/disktotal*100.0; //디스크 사용율 구하기. ( (전체용량-사용가능한용량)/전체용량*100)
    		
            sb.append("드라이브명 " + root.getAbsolutePath() + " : ");
            sb.append((int)disktotal + "GB / "); //바이트를 1024에 3승(1073741824) 한걸로 나누면 GB로 나온다.
            sb.append((int)diskuse + "GB 사용가능");
            
            if( (Math.round(diskcal)) > Integer.parseInt(diskdanger)) { //디스크 사용량이 전체에서 80% 이상 사용시
            	sb.append("\n***" + root.getAbsolutePath() + "드라이브는 담장자와 상의하여 디스크 정리 안내 후 양호 기입.\n");
    			sb.append("***오래된 로그정리 : 로그 관련 고객사 가이드 완료 \n");
            	sb.append("***오래된 첨부파일 정리 : 첨부파일 정리 관련 고객사 가이드 완료)");
            	disknum++; //80%가 초가됐는지 알기위한 int형 변수
            }
            sb.append("\n");
    	}
    	roots.clone();
        //System.out.println(sb.toString());
        return sb.toString();
	}
	//디스크 사용량 양호기준 출력
	public String SystemDISKStatus() {
		if(disknum > 0) { //디스크가 1이상이면 80퍼가 넘었다는 뜻이므로 점검필요
			return "점검필요";
		}
		else {
			return "양호";
		}
	}
	
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	static String FindRUNNING = "RUNNING";
	static String FindSTOPPED = "STOPPED";
	static String wasresult = null; //was 동작상태를 알기위한 변수
	//WAS 동작 상태(서비스)
	public String SystemWasService() { //서비스 동작상태 확인
		if(was.equals("1")) {
			SystemInfo cmd = new SystemInfo();
			
			String command = cmd.inputCommand("sc query " + survicename); //톰캣의 동작상태를 알려주는 cmd 명령어. survicename은 환경설정(Preferences)에서 가져오는거임.
			String result = cmd.execCommand(command);
			
			if(result.indexOf(FindRUNNING) != -1) { //result값에 "RUNNING" 이 포함되어 있는지 찾는거.
				wasresult = "정상작동확인";
			}
			else if(result.indexOf(FindSTOPPED) != -1){ //result값에 "STOPPED" 이 포함되어 있는지 찾는거.
				wasresult = "현재중지상태";
			}
			else {
				wasresult = "톰캣 서비스명을 다시 확인하시오";
			}
			
			String totalresult = "프로세스 명 : " + survicename + "\n" + "서비스 동작 확인 : " + wasresult;
			
			if(wasresult.equals("정상작동확인")) {
				return totalresult;
			}
			else if(wasresult.equals("현재중지상태")) {
				return totalresult + "\n" + "(현재 중지상태.. 담당자와 상의 후 양호 기입)";
			}
			else {
				return "톰캣 서비스명을 다시 확인하시오";
			}
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
	//WAS 동작 상태(서비스) 양호기준 출력
	public String SystemWasServiceStatus() {
		if(wasresult == null) {
			return "점검필요";
		}
		else if(wasresult.equals("정상작동확인")){
			return "양호";
		}
		else {
			return "점검필요";
		}
	}
	
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	static int Logtotal = 0; //fatal 이 있는지 없는지 알기위한 변수
	static String fatalstate = "양호";
	static String FindFatal = "fatal";
	//WAS 로그 (WAS 로그 특이사항)
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
			
			int linecount = 1; //txt파일 총 라인이 몇줄인지 알기 위한 변수
			int a=0; //fatal 이 몇번째 줄에 있는지 알기 위한 변수
			ArrayList<String> fatalFileContents =null; //fatal 발견 시 문장을 저장시켜주는 arraylist
			ArrayList<String> fatalFolderName =null; //fatal 발견 시 그 해당 폴더 이름을 저장시켜주는 arraylist
			
			try {
				if(f.exists()) {
					survicename = survicename.toLowerCase(); //소문자로 바꿔주는 문장
					for (File file : files) {
						if (file.getName().startsWith(survicename + "-stderr.")) { //"서비스명-stderr." 로 시작하는 폴더 담기.	
							results2.add(file); //results2 에 담김
						}
					}
					
					if(results2.size() == 0) { //stderr 파일이 하나도 없을 시
						
						//fileReader = new FileReader(stderrFile);
						//bufferedReader = new BufferedReader(fileReader);
						
						sb.append("WAS 로그 특이사항 : 특이사항 없음\n");
						sb.append("(" + stderr + " 경로에 stderr 파일이 없습니다.)\n");
					}
					else if(results2.size() < 15) { //stderr 파일이 15개 미만일때
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
									sb.append(stderrFile + " " + a + "번째 줄에서 "+FindFatal + "발견\n");
									
									fatalFolderName.add(stderrFile.substring(stderrFile.toString().length()-21, stderrFile.toString().length()-4));
									Logtotal++;
									fatalstate = "점검필요";
								}
								linecount++;
							}
							if(Logtotal > 1)
								SystemWasLogFatalFile(fatalFolderName, fatalFileContents);
							/*
							if(Logtotal > 0) { //Logtotal이 1이상이면 fatal을 발견했다는 의미 이므로 SystemWasLogFatalFile메소드 호출 하여 파일 저장
								if(Integer.parseInt(fatalHowManyLine) == 0) {
									break;
								}
								else {
									SystemWasLogFatalFile(fatalFolderName, fatalFileContents);
								}
							}
							*/
						}
						if(fatalstate.equals("양호"))
							sb.append("WAS 로그 특이사항 : 특이사항 없음\n");
						else {
							sb.append("(UJIBOSU 폴더 내 "+ "WASLogFatalFolder 폴더에\n"
									+ "fatal 발견 파일 저장 --> 파일 반출 요청 후 양호 기입)");
						}
					}
					else if(results2.size() >= 15){ //stderr 파일이 15개 이상일때
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
									sb.append(stderrFile + " " + a + "번째 줄에서 "+FindFatal + "발견\n");
									
									fatalFolderName.add(stderrFile.substring(stderrFile.toString().length()-21, stderrFile.toString().length()-4));
									Logtotal++;
									fatalstate = "점검필요";
								}
								linecount++;
							}
							if(Logtotal > 1)
								SystemWasLogFatalFile(fatalFolderName, fatalFileContents);
							/*
							if(Logtotal > 0) { //Logtotal이 1이상이면 fatal을 발견했다는 의미 이므로 SystemWasLogFatalFile메소드 호출 하여 파일 저장
								if(Integer.parseInt(fatalHowManyLine) == 0) {
									break;
								}
								else {
									SystemWasLogFatalFile(fatalFolderName, fatalFileContents);
								}
							}
							*/
						}
						if(fatalstate.equals("양호"))
							sb.append("WAS 로그 특이사항 : 특이사항 없음\n");
						else {
							sb.append("(UJIBOSU 폴더 내 "+ "WASLogFatalFolder 폴더에\n"
									+ "fatal 발견 파일 저장 --> 파일 반출 요청 후 양호 기입)");
						}
					}
					
				} else sb.append("waslog 경로 or 환경설정.txt 재확인 바람.\n");
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
	//WAS 로그 (WAS 로그 특이사항) 양호기준 출력
	public String SystemWasLogFatalStatus() {
		if(fatalstate.equals("양호"))
			return "양호";
		else
			return "점검필요";
	}
	
	//WAS 로그 (WAS 로그 특이사항) fatal 발견시 위아래 n줄을 따와 파일로 저장하기 위한 메소드
	public void SystemWasLogFatalFile(ArrayList<String> fatalFolderName , ArrayList<String> fatalFileContents) {
		File filefd = null;
		File filesn = null;
		FileWriter writer = null;
		String filefolderPath = fixPath+"\\WASLogFatalFolder";
		
		filefd = new File(filefolderPath);
		try {
			int linecount; //txt파일 총 라인이 몇줄인지 알기 위한 변수
			int fatalLine; //fatal 이 몇번째 줄에 있는지 알기 위한 변수
			int fatalPath=0; //fatal 의 폴더 이름을 저장할때 쓰기 위한 변수
			Iterator it = null;
			
			if(filefd.exists()) { //WASLogFatalFolder 폴더가 있을 경우
				linecount=1;
				fatalLine=0;
				it = fatalFileContents.iterator();
				filesn = new File(filefolderPath+"\\"+fatalFolderName.get(fatalPath++)+".txt");
				writer = new FileWriter(filesn, true);
				while(it.hasNext()) {
					if(it.next().equals(FindFatal)) {
						fatalLine=linecount;
						if(fatalLine < Integer.parseInt(fatalHowManyLine)) //fatal 발견 위치의 윗줄이 50줄 미만일때
							for(int m=0; m<fatalLine; m++) //ex) m=0; m<10; m++
								writer.write(fatalFileContents.get(m) + "\r\n"); //ex) get(0) -> get(1) .... -> get(9)
						
						else if(fatalLine >= Integer.parseInt(fatalHowManyLine)) //fatal 발견 위치의 윗줄이 50줄 이상일때 ex)63 >= 50
							for(int m=0; m<Integer.parseInt(fatalHowManyLine); m++)
								writer.write(fatalFileContents.get((fatalLine-Integer.parseInt(fatalHowManyLine))+m) + "\r\n"); //ex) get((63-50)+0) -> get((63-50)+1) .... -> get((63-50)+49)

						if((fatalFileContents.size()-fatalLine) < Integer.parseInt(fatalHowManyLine)) //fatal 발견 위치의 아랫줄이 50줄 미만일때 ex) 63-30 < 50
							for(int m=0; m<(fatalFileContents.size()-fatalLine); m++) //ex) m=0; m<33; m++
								writer.write(fatalFileContents.get(m+fatalLine) + "\r\n"); //ex) get(0+30) -> get(1+33) .... -> get(32+30)
								
						else if((fatalFileContents.size()-fatalLine) > Integer.parseInt(fatalHowManyLine)) //fatal 발견 위치의 아랫줄이 50줄 이상일때 ex) 63-10 > 50
							for(int m=0; m<Integer.parseInt(fatalHowManyLine); m++) 
								writer.write(fatalFileContents.get(m+fatalLine) + "\r\n"); //ex) get(0+10) -> get(1+10) .... -> get(49+10)
						
						writer.write("\r\n" + "---------------------------------------------절취선---------------------------------------------" + "\r\n" + "\r\n");
						writer.flush();
					}
					linecount++;
				}
			}
			else if(filefd.exists() == false){ //WASLogFatalFolder 폴더가 없을 경우
				filefd.mkdir(); //WASLogFatalFolder 폴더 생성
				
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
						
						writer.write("\r\n" + "---------------------------------------------절취선---------------------------------------------" + "\r\n" + "\r\n");
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
	//WAS 로그 (최근 서비스 재시작 날짜)
	public String SystemWasLogDay() {
		if(expointVersion.equals("1")) {
			File f = new File(stdout); //stdout 는 환경설정(Preferences)에서 가져오는거임.
			File[] files = f.listFiles();	
			String result = null;
			try {
				if(f.exists()) {
					survicename = survicename.toLowerCase(); //서비스명 소문자로 변환
					for (File file : files) {
						if (file.getName().startsWith(survicename + "-stdout.")) {  //"esf-stdout." 로 시작하는 폴더 담기.
							results1.add(file);
						}
					}
					if(results1.size() == 0) { //stdout 파일이 하나도 없을 시
						logdaydate = "양호";
						result = "stdout 파일이 없습니다.";
					}
					else { //stdout 파일이 있을 시
						File FindLOGOUT = results1.get(results1.size()-1); //마지막에 담긴거 추출해서 넣기.
						logdaydate = FindLOGOUT.toString().substring(FindLOGOUT.toString().length()-14, FindLOGOUT.toString().length()-4);
						result = "최근 서비스 재시작 날짜 : " + FindLOGOUT.toString().substring(FindLOGOUT.toString().length()-14, FindLOGOUT.toString().length()-4);
					}
				}
				else {
					logdaydate = "점검필요";
					result = "waslog 경로 or 환경설정.txt를 재확인 바람.";
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
			logdaydate = "점검필요";
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
	//WAS 로그 (최근 서비스 재시작 날짜) 양호기준 출력
	public String SystemWasLogDayStatus() {
		String pattern = "^\\d{4}-\\d{2}-\\d{2}$"; //yyyy-mm-dd 의 정규식
		
		if(logdaydate.equals("양호")) {
			return "양호";
		}
		else if(logdaydate.equals("점검필요")) {
			return "점검필요";
		}
		if(logdaydate.matches(pattern) != true) {
			return "점검필요";
		}
		else if(logdaydate.matches(pattern)) {
			return "양호";
		}
		else {
			return "점검필요";
		}
		//return "날짜 정상출력 되었으면 양호 기입";
	}
	
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	static String dbmsaction = null;
	//DBMS 동작
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
						conn = DriverManager.getConnection(url, ID, PW); //(url, 서버아이디, 서버패스워드)
						logger.debug("디비 연결 성공!");
						
					}catch(SQLException error) {
						logger.error("디비 연결 실패 ip,port,id,pw를 정확하게 입력하였는지 확인바람.");
					}
					
					stmt = conn.createStatement();
					rs = stmt.executeQuery("SELECT CH_NM, COUNT(*) AS " +"\'총 신청건수\'" + " FROM dbo.APPR_LIST " + "GROUP BY CH_NM"); //얻고자하는 내용에 대한 쿼리문
					
					String field1 = null;
					String field2 = null;
					
					while( rs.next() ) {
						field1 = rs.getString("CH_NM"); //필드 이름
						field2 = rs.getString("총 신청건수");
						//System.out.println(field1 + " -> " + field2 ); //채널별 신청건수 조회
					}
					if(field1.isEmpty() == true && field2.isEmpty() == true) {
						dbmsaction = "점검필요";
						//System.out.println("신청건수 조회 및 접속 확인 : 조회 불가.");
						dbmsresult = "신청건수 조회 및 접속 확인 : 조회 불가.";
					}
					else {
						dbmsaction = "양호";
						//System.out.println("신청건수 조회 및 접속 확인 : 조회 및 접속 양호");
						dbmsresult = "신청건수 조회 및 접속 확인 : 조회 및 접속 양호.";
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
		else if(expointVersion.equals("2")) {
			return "#수동으로 확인하시오.";
		}
		else if(expointVersion.equals("3")){
			return "#수동으로 확인하시오.";
		}
		else {
			logger.error("#환경설정에서 Expoint Version 값 재확인");
			JOptionPane.showMessageDialog(null, "db 관련 정보가 올바르지 않습니다");
			
			return "#환경설정에서  Expoint Version 값을 다시 확인하시오.";
		}
	}
	
	//DBMS 동작 양호기준 출력
	public String SystemDBMSStatus() {
		//System.out.println(dbms);
		if(dbmsaction == null) {
			return "점검필요";
		}
		else if(dbmsaction.equals("양호")){
			return "양호";
		}
		else {
			return "점검필요";
		}
	}
	
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	static int insatotal = 0; //만약 실패경우가 있을시 양호기준 판단하는 int 변수
	static String insaresult = null; //점검상태를 출력하기 위한 변수
	//인사연동 동작
	public String SystemBatchService() {
		if(expointVersion.equals("1")) {
			if(dbms.equals("1")) {
				StringBuilder sb = new StringBuilder();
				Connection conn = null;
				Statement stmt = null;
				ResultSet rs = null;
				final String url = defaulturl+ip+":"+port+";DatabaseName="+dbname;
				
				ArrayList<String> insaContents = new ArrayList<String>(); //인사연동 배치서비스 실패내용을 넣기위한 ArrayList
				
				try {
					Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver"); //sqljdbc
					conn = DriverManager.getConnection(url, ID, PW); //(url, 서버아이디, 서버패스워드)
					stmt = conn.createStatement();
					
					Calendar cal = Calendar.getInstance ( ); //날짜 계산 함수
					cal.add(cal.DATE, -1); //1일전
					String date1 = cal.get(cal.YEAR) + " -" + (cal.get(cal.MONTH)+1) + " -" + cal.get(cal.DATE);
					//System.out.println(date1);
					
					cal.add(cal.DATE, -14); //1일전 + 14일전
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
					//여기 while 문은 String 배열을 쓴 경우
					if( rs.next() ) {
						insatotal = insatotal + 1;
						while( rs.next() ) {
							for(int i=0; i<select.length; i++) {
								field[i] = rs.getString(select[i]);
							}
							if(field[5].equals("F")) { //만약 실패가 있을시 작동
								insatotal++; //실패가 있으면 insatotal을 증가시켜 상태 판단
								insaContents.add("(" + field[2] + "월 " + field[3] + "일에 실패 발견. DB나 통합반출 홈페이지에서 확인하시오.)\n"); //실패한 날짜를 insaContents에 추가
							}
						}
					}
					else { //만약 검색되는 내용이 없을 시
						sb.append("(DB는 정상동작 하지만 인사연동 배치서비스 실행한 정보가 없음.\n" + "DB나 통합반출 홈페이지에서 한번 확인하시오.)\n");
					}
					
					Iterator it = insaContents.iterator();
					if(insatotal == 0) { //만약 0일 경우 조회되는 내용이 없어서 점검필요
						insaresult = "점검필요";
					}
					else if(insatotal == 1) { //만약 1일 경우 조회되고 모두 성공이여서 양호
						insaresult = "양호";
						if(insaContents.isEmpty() == true) { //모두 성공이면 insaContents에 내용이 없어야됨
							sb.append("배치서비스 동작 확인 : 정상동작 확인 \n");
						}
						else {
							//??
						}
					}
					else { //만약 2이상일 경우 조회되고 실패가 있어서 점검필요
						insaresult = "점검필요";
						if(insaContents.isEmpty() == false) { //실패가 있으면 insaContents에 내용이 있어야됨
							sb.append("배치서비스 동작 확인 : 인사연동 배치서비스 실패 발견 \n");
							while(it.hasNext()) {
								sb.append(it.next());
							}
						}
						else {
							//??
						}
					}
					
					///////////////////////////////////////////////////////
					/*//여기 주석은 String 배열을 안썼을 경우
					String field1 = null;
					String field2 = null;
					String field3 = null;
					String field4 = null;
					String field5 = null;
					String field6 = null;
					while( rs.next() ) {
						field1 = rs.getString("CHART_TYPE"); //필드 이름
						field2 = rs.getString("CHART_YEAR");
						field3 = rs.getString("CHART_MONTH");
						field4 = rs.getString("CHART_DAY");
						field5 = rs.getString("CHART_TITLE");
						field6 = rs.getString("OPT1");
						System.out.println(field1 + " " + field2 + " " + field3 + " " + field4 + " "+ field5 + " "+ field6);
						
						if(field6.equals("F")) { //만약 실패가 있으면 total을 1증가 시켜 아래 if문에서 양호 판단.
							sb.append(field3 + "월 " + field4 + "일에 실패 발견. DB나 통합반출 홈페이지에서 확인하시오. \n");
							total++;
						}
						else if(field6.isEmpty()) //만약 검색되는 내용이 없을 시
							sb.append("검색되는 내용이 없음. DB나 통합반출 홈페이지에서 확인하시오. \n");
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
				insaresult = "점검필요";
				return "#수동으로 확인하시오.";
			}
			else if(dbms.equals("3")){
				insaresult = "점검필요";
				return "#수동으로 확인하시오.";
			}
			else {
				logger.error("#환경설정에서 DBMS 값 재확인");
				return "#환경설정에서  DBMS 값을 다시 확인하시오.";
			}
			
		}
		else if(expointVersion.equals("2")) {
			insaresult = "점검필요";
			return "#수동으로 확인하시오.";
		}
		else if(expointVersion.equals("3")){
			insaresult = "점검필요";
			return "#수동으로 확인하시오.";
		}
		else {
			logger.error("#환경설정에서 Expoint Version 값 재확인");
			return "#환경설정에서  Expoint Version 값을 다시 확인하시오.";
		}
	}
	//인사연동 동작 양호기준 출력
	public String SystemBatchServiceStatus() {
		return insaresult;
	}
	
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//업로드 폴더
	public String SystemUpload() {
		if(expointVersion.equals("1")) {
			long sum=0, DirectorySize=0;
			String uploadsize = null;
			String result = null;
			
			File f = new File(upload); //upload 는 환경설정(Preferences)에서 가져온거임.
			File[] files = f.listFiles();
			
			try {
				if(f.exists()) {
					for (File file : files) {
						if (file.getName().startsWith("20"))   //"20"으로 시작하는 폴더 담기.
							results3.add(file); //20으로 시작하는 폴더 results3에 모두 담기
					}
					if(results3.size() == 0) { //최근 3개월인데 월별 폴더가 한개도 없을때
						DirectorySize=0;
						sum=0;
					}
					else if(results3.size() < 3) { //최근 3개월인데 월별 폴더가 3개 미만일때
						for(int i=0; i<results3.size(); i++) {
							uploadsize = results3.get(results3.size()-(i+1)).toString(); //맨 뒤에꺼부터 폴더 계산.
							//System.out.println(uploadsize);
							File FindDirectory = new File(uploadsize);
							DirectorySize = getSize(FindDirectory); //바이트
							sum += DirectorySize;
						}
					}
					else {
						for(int i=0; i<3; i++) { //최근 3개월이므로 i<3으로 지정
							uploadsize = results3.get(results3.size()-(i+1)).toString(); //맨 뒤에꺼부터 3개 폴더 계산.
							//System.out.println(uploadsize);
							File FindDirectory = new File(uploadsize);
							DirectorySize = getSize(FindDirectory); //바이트
							sum += DirectorySize;
						}
					}
					
					if(sum >= 1073741824) { //만약 1,000,000,000바이트가 넘을시 기가 단위로 계산
						sum = sum/(1024*1024*1024);
						result = upload + " (최근 3개월  " + sum + "GB)\n"
								+ "(" + upload.substring(0, 2) + "드라이브 용량이 부족할 시 담당자와 상의하고 정리 안내.)";
					}
					else { //만약 1,000,000,000바이트가 안넘으면 메가 단위로 계산
						sum = sum/(1024*1024);
						result = upload + " (최근 3개월  " + sum + "MB)\n"
								+ "(" + upload.substring(0, 2) + "드라이브 용량이 부족할 시 담당자와 상의하고 정리 안내.)";
					}
				} else result = "업로드 폴더 경로 or 월별 폴더 or 환경설정.txt 재확인 바람.";
			}
			catch(Exception e) {
				logger.error("error message" , e);
				e.printStackTrace();
			}
			return result;
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
	public static long getSize(File f) { //업로드 폴더 사이즈 계산 메소드
		long size = 0; // Store the total size of all files
		if (f.isDirectory()) { //폴더일 때
			File[] files = f.listFiles(); // All files and subdirectories
			for (int i = 0; i < files.length; i++) {
				size += getSize(files[i]); // Recursive call
			}
		}else size += f.length(); // Base case 파일일 때
		return size;
	}
	//업로드 폴더 양호기준 출력
	public String SystemuploadStatus() {
		if(expointVersion.equals("1")) {
			return "양호";
		}
		else if(expointVersion.equals("2")) {
			return "점검필요";
		}
		else if(expointVersion.equals("3")){
			return "점검필요";
		}
		else {
			return "점검필요";
		}
	}
	
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//오래된 로그 가이드
	public String SystemOldLog() {
	    StringBuilder sb = new StringBuilder();
	    
	    sb.append("@@오래된 로그 정리 가이드@@ \n");
	    sb.append(stdout + " 경로로 이동 \n");
	    sb.append("담당자와 상의하여 오래된 로그(stdout파일)를 지우시오.");
	    
        //System.out.println(sb.toString());
        return sb.toString();
	}
	//오래된 로그 가이드 양호출력
	public String SystemOldLogStatus() {
		return "양호";
	}
	
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//오래된 첨부파일 가이드
	public String SystemOldFile() {
		StringBuilder sb = new StringBuilder();
	    
	    sb.append("오래된 로그 첨부파일은 배치서비스\n");
	    sb.append("배치 돌면서 자동으로 정리 됨.");
		
        //System.out.println(sb.toString());
        return sb.toString();
	}
	//오래된 첨부파일 양호출력
	public String SystemOldFileStatus() {
		return "양호";
	}
	
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	static String[] channelname = new String[40]; //채널 이름을 담기위한 스트링배열
	static String[] channecode = new String[40]; //채널별 코드를 담기위한 스트링배열
	//연동채널 점검
	public String SystemChannel() {
		if(expointVersion.equals("1")) {
			if(dbms.equals("1")) {
				StringBuilder sb = new StringBuilder();
				Connection conn = null;
				Statement stmt = null;
				ResultSet rs1 = null; //채널 이름과 코드를 알아냄
				ResultSet rs2 = null; //월 신청건수
				ResultSet rs3 = null; //월 결제건수
				ResultSet rs4 = null; //월 반려건수
				final String url = defaulturl+ip+":"+port+";DatabaseName="+dbname;

				int i=0;
				String field1 = null; //채널이름
				String field2 = null; //채널코드
				String field3 = null; //채널이름
				String field4 = null; //월 신청 건수
				String field5 = null; //월 결재 건수
				String field6 = null; //월 반려 건수
				
				try {
					Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver"); //sqljdbc
					conn = DriverManager.getConnection(url, ID, PW); //(url, 서버아이디, 서버패스워드)
					stmt = conn.createStatement();
					
					Calendar cal = Calendar.getInstance ( ); //날짜 계산 함수
					cal.add(cal.DATE, -1); //1일전
					String date1 = cal.get(cal.YEAR) + " -" + (cal.get(cal.MONTH)+1) + " -" + cal.get(cal.DATE);
					
					cal.add(cal.DATE, -30); //30일전
					String date2 = cal.get(cal.YEAR) + " -" + (cal.get(cal.MONTH)+1) + " -" + cal.get(cal.DATE);
					
					rs1 = stmt.executeQuery("SELECT CH_NM AS '채널명', CH_CODE AS '채널코드' " 
					      + "FROM dbo.APPR_LIST GROUP BY CH_NM, CH_CODE");
					
					while( rs1.next() ) {
						field1 = rs1.getString("채널명"); //필드 이름
						field2 = rs1.getString("채널코드");
						channelname[i] = field1; //채널이름을 저장
						channecode[i] = field2; //채널코드를 저장
						i++;	
					}
					i=0;
					while(channelname[i] != null && channecode[i] != null) {
						//월 신청건수 조회
						rs2 = stmt.executeQuery("SELECT CH_NM AS '채널명', CH_CODE, COUNT(*) AS '채널별 신청건수 조회' " 
								+ "FROM dbo.APPR_LIST "
								+ "where CH_CODE = " + channecode[i] + " AND ACK_RESULT != 'X' AND PROCESS_STEP = '5' "
								+ "AND REQ_DT BETWEEN CONVERT(datetime, '"+ date2 +" 00:00:01') AND CONVERT(datetime,'"+ date1 +" 23:59:59') "
								+ "GROUP BY CH_NM, CH_CODE "
								+ "ORDER BY CH_CODE ASC;");
						if(rs2.next()) { //만약 열이 있을 경우
							field3 = rs2.getString("채널명");
							field4 = rs2.getString("채널별 신청건수 조회");
							sb.append("채널명 : " + field3 + "\n");
							sb.append("월 신청 건수 : (" + field4 + ")건" + "\n");
						}else { //열이 없을 경우
							sb.append("채널명 : " + channelname[i] + "\n");
							sb.append("월 신청 건수 : (0)건\n");
						}
						
						//월 결재건수 조회
						rs3 = stmt.executeQuery("SELECT CH_NM AS '채널명', CH_CODE, COUNT(*) AS '채널별 결재건수 조회' " 
								+ "FROM dbo.APPR_LIST "
								+ "where CH_CODE = " + channecode[i] + " AND ACK_RESULT != 'Y' AND PROCESS_STEP = '5' "
								+ "AND REQ_DT BETWEEN CONVERT(datetime, '"+ date2 +" 00:00:01') AND CONVERT(datetime,'"+ date1 +" 23:59:59') "
								+ "GROUP BY CH_NM, CH_CODE "
								+ "ORDER BY CH_CODE ASC;");
						if(rs3.next()) {
							field5 = rs3.getString("채널별 결재건수 조회");
							sb.append("월 결재 건수 : (" + field5 + ")건" + "\n");
						}else {
							sb.append("월 결재 건수 : (0)건\n");
						}
						
						//월 반려건수 조회
						rs4 = stmt.executeQuery("SELECT CH_NM AS '채널명', CH_CODE, COUNT(*) AS '채널별 반려건수 조회' " 
								+ "FROM dbo.APPR_LIST "
								+ "where CH_CODE = " + channecode[i] + " AND ACK_RESULT != 'N' AND PROCESS_STEP = '5' "
								+ "AND REQ_DT BETWEEN CONVERT(datetime, '"+ date2 +" 00:00:01') AND CONVERT(datetime,'"+ date1 +" 23:59:59') "
								+ "GROUP BY CH_NM, CH_CODE "
								+ "ORDER BY CH_CODE ASC;");
						if(rs4.next()) {
							field6 = rs4.getString("채널별 반려건수 조회");
							sb.append("월 반려 건수 : (" + field6 + ")건" + "\n");
						}else {
							sb.append("월 반려 건수 : (0)건\n");
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
	//연동채널 점검
	public String SystemChannelStatus() {
		StringBuilder sb = new StringBuilder();
		int i=0;
		if(expointVersion.equals("1")) {
			if(dbms.equals("1")) {
				while(channelname[i] != null && channecode[i] != null) {
					sb.append(channelname[i] + "(" + channecode[i] + ") => 양호\n");
					i++;
				}
				//System.out.println(sb.toString());
				return sb.toString();
			}
			else if(dbms.equals("2")) {
				return "#수동으로 확인하시오.";
			}
			else if(dbms.equals("3")){
				return "#수동으로 확인하시오.";
			}
			else {
				return "#환경설정에서  DBMS 값을 다시 확인하시오.";
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
}