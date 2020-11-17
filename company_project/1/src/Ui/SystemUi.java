package Ui;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.swing.*;
import javax.swing.event.CellEditorListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellEditor;
import org.slf4j.*;
import Infomation.*;

@SuppressWarnings("serial")
public class SystemUi extends JPanel implements ActionListener{
	
	final private Logger logger = LoggerFactory.getLogger(getClass());
	Preferences pre = new Preferences();
	PublicUseClass puc = new PublicUseClass();
	String fileUrl = puc.fixPath + "\\Manual\\passiveTool\\";
	JFrame frame = new JFrame("ESF Maintenance Tool v1.0 - 점검결과");
	private Font font3 = new Font("맑은고딕", Font.BOLD, 20);
	private Font font = new Font("맑은고딕",Font.PLAIN,17);
	 
	JLabel message = new JLabel("? 버튼은 도움말 버튼입니다.");
	
	JButton bt_exit;
	JButton bt_previous;
	JButton bt_save;
	
	//[0]은 헬프버튼부터 product os db ....																
	String sbtn[] = {"HELP" ,"?" ,"?" ,"?" ,"?" ,
						"?" ,"?" ,"?","?" ,"?" ,
						"?" ,"?" ,"?" ,"?" ,"?" , 
						"?","?","?","?"};
	
	JButton[] btn = new JButton[sbtn.length ];
	
	SystemInfo sm = new SystemInfo();
	BasicInfo bm = new BasicInfo();
  	
  	//기본정보 값 받아오기
	String customer = bm.BasicCustomer();
 	String manager = bm.BasicManagerName();
 	String customerCompany = bm.BasicCompany();
 	String engineer = bm.BasicEngineerName();
 	String date = bm.BasicDate();
 	String dataTime = bm.BasicTime();
 	String support = bm.BasicSupport();
 	String cycle = bm.BasicCycle();
 	String productName_version = bm.BasicProductName();
 	String user =bm.BasicUser();
 	String os = System.getProperty("os.name");
 	String wasName = bm.BasicWasName();
 	String dbmsName = bm.BasicDBMSName();
 	String dbFile_version = bm.BasicDBFile();
 	
 	//시스템 정보 값 받아오기
 	String cpu =sm.SystemCPU();
 	String cpus = sm.SystemCPUStatus();

 	String ram = sm.SystemRAM();
 	String rams = sm.SystemRAMStatus();
 	
 	String disk = sm.SystemDISK();
 	String disks = sm.SystemDISKStatus();
 	
 	String was = sm.SystemWasService();
 	String wass = sm.SystemWasServiceStatus();
 	
 	String wasFatal = sm.SystemWasLogFatal();
 	String wasFatals = sm.SystemWasLogFatalStatus();
 	
 	String wasLog = sm.SystemWasLogDay();
 	String wasLogs = sm.SystemWasLogDayStatus();
 	
 	String dbms = sm.SystemDBMS();
 	String dbmss = sm.SystemDBMSStatus();
 	
 	String insa = sm.SystemBatchService();
 	String insas = sm.SystemBatchServiceStatus();
 	
 	String upload = sm.SystemUpload();
 	String uploads = sm.SystemuploadStatus();
 	
 	String oldlog = sm.SystemOldLog();
 	String oldlogs = sm.SystemOldLogStatus();
 	
 	String oldFile = sm.SystemOldFile();
 	String oldFiles = sm.SystemOldFileStatus();
 	
 	String channel = sm.SystemChannel();
 	String channels = sm.SystemChannelStatus();
 
 	Object[][] data = {
 		{"고객사",customer," 담당자",manager},
      	{"접검업체","(주)뉴젠씨앤아이","점검자",engineer},
      	{"점검일자",date,"점검시간",dataTime},
      	{"점검방법",support,"점검주기",cycle},
      	{"제품명/버전",productName_version,"사용자수",user},
      	{"OS",os,"WAS 서비스명",wasName},
      	{"DBMS",dbmsName,"DB 이름 및 용량",dbFile_version}};
 	
    Object[][] data1 = { 
      	{"cpu",cpus,cpu},
      	{"메모리 사용량",rams,ram},
      	{"디스크 사용량",disks,disk},
      	{"WAS동작 상태",wass,was},
      	{"WAS로그 특이사항",wasFatals,wasFatal},
      	{"WAS 로그",wasLogs,wasLog},
      	{"DMBS 동작",dbmss,dbms},
      	{"인사연동 동작",insas,insa},
      	{"업로드 폴더",uploads,upload},
      	{"오래된 로그 정리",oldlogs,oldlog},
      	{"오래된 첨부파일 정리",oldFiles,oldFile}
 		};
 
    Object[][] data2 = {{channels}}; 
 		
    Object[][] data3 = {{channel}}; 
 		
    Object[] column =  {" ", " ", " "," "}; 	
    Object[] column1 ={"시스템 이름","시스템 상태","비고"};
    Object[] column2 =  {"연동채널테스트"};
    Object[] column3 =  {"연동채널 사용량 조회"};
 		
	JTable table = new JTable(data,column);
	JTable table1 = new JTable(data1,column1);
	JTable table2 = new JTable(data2,column2);
	JTable table3 = new JTable(data3,column3);
	
	public SystemUi(){
		Color color = new Color(255,0,0);
		frame.setLayout(null);
		JLabel lb1 = new JLabel("Basic Infomation");
		JLabel lb2 = new JLabel("System Infomation");
		JLabel lb3 = new JLabel("Channel Infomaiton");
		JLabel lb4 = new JLabel("Channel Usage Infomation");
		
		lb1.setFont(font3);
		lb2.setFont(font3);
		lb3.setFont(font3);
		lb4.setFont(font3);
		message.setFont(new Font("맑은고딕", Font.ITALIC, 13));
		message.setForeground(color);
		frame.add(message);
		message.setBounds(250,10,300,30);
		/**
		 * basicInfo 위치
		 * ...
		 */
		frame.add(lb1);
 		lb1.setBounds(30,10,200,35); 
 		frame.add(lb2);
 		lb2.setBounds(30,310,200,35);
 		frame.add(lb3);
 		lb3.setBounds(850,10,250,35);
		frame.add(lb4);
 		lb4.setBounds(850,300,250,35);
 		
 		/**
 		 * table 정의
 		 * table ->basic
 		 * table1 ->시스템
 		 * table2 ->연동채널인포
 		 * table3 -> 연동채널 상태 인포
 		 */	
 		
		table.setRowHeight(30);
		table1.setRowHeight(53);
		table2.setRowHeight(250);
		table3.setRowHeight(500); // 열 폭
		
		table.getColumnModel().getColumn(0).setPreferredWidth(20);

		table1.getColumnModel().getColumn(1).setCellRenderer(new ColorCellRenderer());
        table1.getColumnModel().getColumn(2).setCellRenderer(new CustomCellRenderer());
        table1.getColumnModel().getColumn(2).setCellEditor(new CustomEditor());
        table1.getColumnModel().getColumn(0).setPreferredWidth(150);
        table1.getColumnModel().getColumn(1).setPreferredWidth(100);    
        table1.getColumnModel().getColumn(2).setPreferredWidth(550);
        
        table2.getColumnModel().getColumn(0).setCellRenderer(new CustomCellRenderer());
        table2.getColumnModel().getColumn(0).setCellEditor(new CustomEditor());
        
        table3.getColumnModel().getColumn(0).setCellRenderer(new CustomCellRenderer());
        table3.getColumnModel().getColumn(0).setCellEditor(new CustomEditor());

        JScrollPane sp = new JScrollPane(table);
 		JScrollPane sp1 = new JScrollPane(table1);
 		JScrollPane sp2 = new JScrollPane(table2);
 		JScrollPane sp3 = new JScrollPane(table3);
 		sp.setFont(font);
 		sp1.setFont(font);
 		sp2.setFont(font);
 		sp3.setFont(font);
		
 		frame.add(sp);
 		frame.add(sp1);
 		frame.add(sp2);
 		frame.add(sp3);

 		sp.setBounds(60,50,630,240); //베이직 테이블 위치
 		sp1.setBounds(30,350,660,600); //시스템 테이블 위치
 		sp2.setBounds(850,50,500,240); //연동채널 테이블 위치
 		sp3.setBounds(850,350,500,527); //연동채널 테이블 위치
 		
 		setBackground(Color.white);

		bt_previous = new JButton("Previous");
		bt_previous.setFont(font);
		bt_previous.setBounds(900,920,115,30);
		bt_previous.addActionListener(this);
		frame.add(bt_previous);
		
		bt_save = new JButton("Save");
		bt_save.setFont(font);
		bt_save.setBounds(1040,920,115,30);
		bt_save.addActionListener(this);
		frame.add(bt_save);
		
		bt_exit = new JButton("Exit");
		bt_exit.setFont(font);
		bt_exit.setBounds(1180,920,115,30);
		bt_exit.addActionListener(this);
		frame.add(bt_exit);

		//Help
		btn[0] = new JButton(sbtn[0]);
		
		for(int i =0 ; i<btn.length; i++) {
			btn[i] = new JButton(sbtn[i]);
		}
	
		btn[0].setBounds(1235,15,115,20);
		//기본정보 도움말 버튼
		//bt_product.setBounds(10,197,45,20);
		btn[1].setBounds(10,197,45,20);
		//bt_os.setBounds(10,227,45,20);
		btn[2].setBounds(10,227,45,20);
		//bt_dbName.setBounds(10,258,45,20); 
		btn[3].setBounds(10,258,45,20);
		//bt_user.setBounds(695,197,45,20);
		btn[4].setBounds(695,197,45,20);			
		//bt_wasName.setBounds(695,227,45,20);
		btn[5].setBounds(695,227,45,20);
		//bt_dbFile.setBounds(695,258,45,20);
		btn[6].setBounds(695,258,45,20);
		
		//시스템 정보 도움말 버튼
		//bt_cpu.setBounds(695,390,45,20);
		btn[7].setBounds(695,390,45,20);
		//bt_memory.setBounds(695,440,45,20);
		btn[8].setBounds(695,440,45,20);			
		//bt_disk.setBounds(695,490,45,20);
		btn[9].setBounds(695,490,45,20);	
		//bt_wasState.setBounds(695,545,45,20);
		btn[10].setBounds(695,545,45,20);
		//bt_wasUniqueness.setBounds(695,595,45,20);
		btn[11].setBounds(695,595,45,20);	
		//bt_wasLog.setBounds(695,655,45,20);
		btn[12].setBounds(695,655,45,20);		
		//bt_dbmsState.setBounds(695,705,45,20);
		btn[13].setBounds(695,705,45,20);
		//bt_insa.setBounds(695,760,45,20);
		btn[14].setBounds(695,760,45,20);	
		//bt_upload.setBounds(695,810,45,20);
		btn[15].setBounds(695,810,45,20);
		//bt_clearLog.setBounds(695,863,45,20);
		btn[16].setBounds(695,863,45,20);
		//bt_clearFile.setBounds(695,912,45,20);
		btn[17].setBounds(695,912,45,20);	
		//bt_channelInfo.setBounds(1040,18,45,20);
		btn[18].setBounds(1040,18,45,20);
		
		for(int i =0; i< sbtn.length; i++) {
		
			frame.add(btn[i]);
			btn[i].addActionListener(this);
		}
		
		frame.setSize(1370,1000);
		Dimension frameSize = frame.getSize();
		// 내 모니터의 크기를 구합니다.
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		/*
		* 그래서 프레임의 위치를
		* (모니터화면 가로 - 프레임화면 가로) / 2,
		* (모니터화면 세로 - 프레임화면 세로) / 2 이렇게 설정한다.
		*/
		frame.setLocation((screenSize.width - frameSize.width)/2, (screenSize.height - frameSize.height)/2);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Alarm.checkBt = true;
		}	
	
 		String[] buttons = {"예", "아니오"}; //예는 0 , 아니오는 1 , 전체 저장 클릭 이벤트
 		public static void main(String args[]) {
 			 new SystemUi();
 		}
 			
	public void fileSave() {  // 파일 저장 (로그남기기)	
		try {
			
			@SuppressWarnings("unused")
			Date today = new Date();
			String w;
			SimpleDateFormat date = new SimpleDateFormat("yyyy년 MM월 dd일");
			w =  date.format(today);

			File file = null;
			File fileNoexists = null;
			FileWriter fileWriter = null;
			
			String fileNm = puc.fixPath+"\\Save";
			file = new File(fileNm);
			
			String fileNmNoexists = fileNm + "\\유지보수정기점검_"+w+".txt";
			
			if(file.exists()) {
				fileNoexists = new File(fileNmNoexists);
				fileWriter = new FileWriter(fileNoexists ,true);
				fileWriter.write("\r\n-----------------------------------------------------------\r\n");
				
				//기본 정보 
				for(int i = 0 ; i<data.length ; i++) {
					for(int j =0; j<=3 ; j++) {
						if(j==1 || j==3) {
							fileWriter.write(" "+data[i][j]); // 저장할 정보들..
							fileWriter.write("\r\n");
						}
						else {
							fileWriter.write(data[i][j] + " "); // 저장할 정보들.
						}
					}
				}
				//System 정보
				for(int i = 0 ; i<data1.length ; i++) {
					for(int j =0; j<3 ; j++) {
						if(j==2) {
							fileWriter.write(((String) data1[i][j]).replaceAll("\n", "\r\n")+""); // 저장할 정보들..
							fileWriter.write("\r\n");
						}
					}
				}
				//연동채널 상태
				fileWriter.write(((String) data2[0][0]).replaceAll("\n", "\r\n")+""); // 저장할 정보들..
				fileWriter.write("\r\n");
					
				//연동채널 사용량 조회
				fileWriter.write(((String) data3[0][0]).replaceAll("\n", "\r\n")+""); // 저장할 정보들..
				fileWriter.write("\r\n");
			}
			
			else if(file.exists() == false) {
				file.mkdir();
				
				fileNoexists = new File(fileNmNoexists);
				fileWriter = new FileWriter(fileNoexists ,true);
				
				fileWriter.write("\r\n-----------------------------------------------------------\r\n");
				
				//기본 정보 
				for(int i = 0 ; i<data.length ; i++) {
					for(int j =0; j<=3 ; j++) {
						if(j==1 || j==3) {
							fileWriter.write(" "+data[i][j]); // 저장할 정보들..
							fileWriter.write("\r\n");
						}
						else {
							fileWriter.write(data[i][j] + " "); // 저장할 정보들.
						}
					}
				}
				//System 정보
				for(int i = 0 ; i<data1.length ; i++) {
					for(int j =0; j<3 ; j++) {
						if(j==2) {
							fileWriter.write(((String) data1[i][j]).replaceAll("\n", "\r\n")+""); // 저장할 정보들..
							fileWriter.write("\r\n");
						}
					}
				}
				
				//연동채널 상태
				fileWriter.write(((String) data2[0][0]).replaceAll("\n", "\r\n")+""); // 저장할 정보들..
				fileWriter.write("\r\n");
					
				//연동채널 사용량 조회
				fileWriter.write(((String) data3[0][0]).replaceAll("\n", "\r\n")+""); // 저장할 정보들..
				fileWriter.write("\r\n");
			}
			fileWriter.close();
		}catch(Exception e1) {
			e1.printStackTrace();
			logger.error("message",e1);
		}
	}
	
	//일일히 쓰면 코드가 너무 길어지기 때문에 바꿔줌
	public void arr(int i) {
		// ~~~.html에서 ~~~이름을 가져옴.
		String arr[] = {
				"PassiveToolGuide","productName","os","DBMS","user","wasName","dbFileName","CPU Guide",
				"Memory","Disk Guide","wasState","wasLogUniqueness","wasLog","DBState","InsaState","uploadFolder",
				"logClear","oldFileClear","chanelInfo"
		};
		if (Desktop.isDesktopSupported()) {
            // Desktop desktop = Desktop.getDesktop();
             try {
             	Desktop.getDesktop().open(new File((fileUrl+arr[i]+"."+"html")));
             } catch (Exception ex) {
            	 logger.error("message",ex);// do nothing
             }
		}	 
	}
	
	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(bt_save)) {	
			logger.error("save 버튼 클릭");
			int result = JOptionPane.showOptionDialog(null, 
					"저장하시겠습니까?", "알림창", 
					JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, 
					null, buttons, "예");
			if(result==0) {
				fileSave();
				// 파일 저장 (로그남기기)
				JOptionPane.showMessageDialog(null, "저장되었습니다.");
			}
		}
		if(e.getSource().equals(bt_exit)) {
			logger.error("exit 버튼 클릭");
			System.exit(0);
		}
		
		String button[] = {"예","아니오"};
		if(e.getSource().equals(bt_previous)) {
			int result = JOptionPane.showOptionDialog(null, 
					"이전으로 가시겠습니까?", "알림창", 
					JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, 
					null, button, "예");
				if(result == 0) {
					new BasicUi();
					frame.dispose();
				}
		}
		
		//버튼 들이 많아 배열로 만듬. btn[0]~ btn[18] , arr 메소드에 일치시킴.
		for(int i =0; i<btn.length; i++) {
			if(e.getSource().equals(btn[i])) {
				logger.error("도움말 버튼 클릭");
				arr(i);
			}
		}
	}
	//table1(시스템정보 상태)에 s로 끝나는 단어들을 양호면 파란색 점검필요면 파란색
	public class ColorCellRenderer extends DefaultTableCellRenderer {
		public Component getTableCellRendererComponent(JTable table1, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
			super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
			setForeground(isSelected ? table.getSelectionForeground() : table.getForeground());
			if (value.equals("양호")) {
				setForeground(Color.BLUE);
			} else {
				setForeground(Color.RED);
	        }
	        return this;
	    }
		ColorCellRenderer(){
		}
	}
	
	//Table에 TextArea 및 scroll 넣기 ->\n는 테이블에 먹지 않는다.
	private class CustomCellRenderer extends DefaultTableCellRenderer {
		private JTextArea textArea;
		private JScrollPane scrollPane;

	    public CustomCellRenderer() {
	    	textArea = new JTextArea();
	        scrollPane = new JScrollPane(textArea);
	    }

	    public Component getTableCellRendererComponent(JTable table, Object value,
	            boolean isSelected, boolean hasFocus, int row, int column) {	
	        if(null != value) //셀 안에 내용이 있다면 텍스트 에리어 추가
	            textArea.setText(value.toString());
	        return scrollPane;
	    }
	}
	
	//TextArea 편집할 수 있게 만들어주는 코드
	class CustomEditor implements TableCellEditor {
	    private JTextArea textArea;
	    private JScrollPane scrollPane;

	    public CustomEditor() {
	        textArea = new JTextArea();
	        scrollPane = new JScrollPane(textArea);
	    }

	    @Override
	    public Component getTableCellEditorComponent(JTable table, Object value,
	            boolean isSelected, int row, int column) {
	        if(null != value)
	            textArea.setText(value.toString());
	        return scrollPane;
	    }

	    @Override
	    public void addCellEditorListener(CellEditorListener arg0) {
	        // TODO Auto-generated method stub

	    }
	    @Override
	    public void cancelCellEditing() {
	        // TODO Auto-generated method stub

	    }
	    @Override
	    public Object getCellEditorValue() {
	        // TODO Auto-generated method stub
	        return textArea.getText();
	    }
	    @Override
	    public boolean isCellEditable(EventObject arg0) {
	    	// TODO Auto-generated method stub
	    	return true;
	    }
	    @Override
	    public void removeCellEditorListener(CellEditorListener arg0) {
	        // TODO Auto-generated method stub

	    }
	    @Override
	    public boolean shouldSelectCell(EventObject arg0) {
	        // TODO Auto-generated method stub
	        return true;
	    }
	    @Override
	    public boolean stopCellEditing() {
	        // TODO Auto-generated method stub
	        return true;
	    }
	}
}
