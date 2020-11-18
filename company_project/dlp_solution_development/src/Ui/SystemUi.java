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
	JFrame frame = new JFrame("ESF Maintenance Tool v1.0 - ���˰��");
	private Font font3 = new Font("�������", Font.BOLD, 20);
	private Font font = new Font("�������",Font.PLAIN,17);
	 
	JLabel message = new JLabel("? ��ư�� ���� ��ư�Դϴ�.");
	
	JButton bt_exit;
	JButton bt_previous;
	JButton bt_save;
	
	//[0]�� ������ư���� product os db ....																
	String sbtn[] = {"HELP" ,"?" ,"?" ,"?" ,"?" ,
						"?" ,"?" ,"?","?" ,"?" ,
						"?" ,"?" ,"?" ,"?" ,"?" , 
						"?","?","?","?"};
	
	JButton[] btn = new JButton[sbtn.length ];
	
	SystemInfo sm = new SystemInfo();
	BasicInfo bm = new BasicInfo();
  	
  	//�⺻���� �� �޾ƿ���
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
 	
 	//�ý��� ���� �� �޾ƿ���
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
 		{"����",customer," �����",manager},
      	{"���˾�ü","(��)�������ؾ���","������",engineer},
      	{"��������",date,"���˽ð�",dataTime},
      	{"���˹��",support,"�����ֱ�",cycle},
      	{"��ǰ��/����",productName_version,"����ڼ�",user},
      	{"OS",os,"WAS ���񽺸�",wasName},
      	{"DBMS",dbmsName,"DB �̸� �� �뷮",dbFile_version}};
 	
    Object[][] data1 = { 
      	{"cpu",cpus,cpu},
      	{"�޸� ��뷮",rams,ram},
      	{"��ũ ��뷮",disks,disk},
      	{"WAS���� ����",wass,was},
      	{"WAS�α� Ư�̻���",wasFatals,wasFatal},
      	{"WAS �α�",wasLogs,wasLog},
      	{"DMBS ����",dbmss,dbms},
      	{"�λ翬�� ����",insas,insa},
      	{"���ε� ����",uploads,upload},
      	{"������ �α� ����",oldlogs,oldlog},
      	{"������ ÷������ ����",oldFiles,oldFile}
 		};
 
    Object[][] data2 = {{channels}}; 
 		
    Object[][] data3 = {{channel}}; 
 		
    Object[] column =  {" ", " ", " "," "}; 	
    Object[] column1 ={"�ý��� �̸�","�ý��� ����","���"};
    Object[] column2 =  {"����ä���׽�Ʈ"};
    Object[] column3 =  {"����ä�� ��뷮 ��ȸ"};
 		
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
		message.setFont(new Font("�������", Font.ITALIC, 13));
		message.setForeground(color);
		frame.add(message);
		message.setBounds(250,10,300,30);
		/**
		 * basicInfo ��ġ
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
 		 * table ����
 		 * table ->basic
 		 * table1 ->�ý���
 		 * table2 ->����ä������
 		 * table3 -> ����ä�� ���� ����
 		 */	
 		
		table.setRowHeight(30);
		table1.setRowHeight(53);
		table2.setRowHeight(250);
		table3.setRowHeight(500); // �� ��
		
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

 		sp.setBounds(60,50,630,240); //������ ���̺� ��ġ
 		sp1.setBounds(30,350,660,600); //�ý��� ���̺� ��ġ
 		sp2.setBounds(850,50,500,240); //����ä�� ���̺� ��ġ
 		sp3.setBounds(850,350,500,527); //����ä�� ���̺� ��ġ
 		
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
		//�⺻���� ���� ��ư
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
		
		//�ý��� ���� ���� ��ư
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
		// �� ������� ũ�⸦ ���մϴ�.
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		/*
		* �׷��� �������� ��ġ��
		* (�����ȭ�� ���� - ������ȭ�� ����) / 2,
		* (�����ȭ�� ���� - ������ȭ�� ����) / 2 �̷��� �����Ѵ�.
		*/
		frame.setLocation((screenSize.width - frameSize.width)/2, (screenSize.height - frameSize.height)/2);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Alarm.checkBt = true;
		}	
	
 		String[] buttons = {"��", "�ƴϿ�"}; //���� 0 , �ƴϿ��� 1 , ��ü ���� Ŭ�� �̺�Ʈ
 		public static void main(String args[]) {
 			 new SystemUi();
 		}
 			
	public void fileSave() {  // ���� ���� (�α׳����)	
		try {
			
			@SuppressWarnings("unused")
			Date today = new Date();
			String w;
			SimpleDateFormat date = new SimpleDateFormat("yyyy�� MM�� dd��");
			w =  date.format(today);

			File file = null;
			File fileNoexists = null;
			FileWriter fileWriter = null;
			
			String fileNm = puc.fixPath+"\\Save";
			file = new File(fileNm);
			
			String fileNmNoexists = fileNm + "\\����������������_"+w+".txt";
			
			if(file.exists()) {
				fileNoexists = new File(fileNmNoexists);
				fileWriter = new FileWriter(fileNoexists ,true);
				fileWriter.write("\r\n-----------------------------------------------------------\r\n");
				
				//�⺻ ���� 
				for(int i = 0 ; i<data.length ; i++) {
					for(int j =0; j<=3 ; j++) {
						if(j==1 || j==3) {
							fileWriter.write(" "+data[i][j]); // ������ ������..
							fileWriter.write("\r\n");
						}
						else {
							fileWriter.write(data[i][j] + " "); // ������ ������.
						}
					}
				}
				//System ����
				for(int i = 0 ; i<data1.length ; i++) {
					for(int j =0; j<3 ; j++) {
						if(j==2) {
							fileWriter.write(((String) data1[i][j]).replaceAll("\n", "\r\n")+""); // ������ ������..
							fileWriter.write("\r\n");
						}
					}
				}
				//����ä�� ����
				fileWriter.write(((String) data2[0][0]).replaceAll("\n", "\r\n")+""); // ������ ������..
				fileWriter.write("\r\n");
					
				//����ä�� ��뷮 ��ȸ
				fileWriter.write(((String) data3[0][0]).replaceAll("\n", "\r\n")+""); // ������ ������..
				fileWriter.write("\r\n");
			}
			
			else if(file.exists() == false) {
				file.mkdir();
				
				fileNoexists = new File(fileNmNoexists);
				fileWriter = new FileWriter(fileNoexists ,true);
				
				fileWriter.write("\r\n-----------------------------------------------------------\r\n");
				
				//�⺻ ���� 
				for(int i = 0 ; i<data.length ; i++) {
					for(int j =0; j<=3 ; j++) {
						if(j==1 || j==3) {
							fileWriter.write(" "+data[i][j]); // ������ ������..
							fileWriter.write("\r\n");
						}
						else {
							fileWriter.write(data[i][j] + " "); // ������ ������.
						}
					}
				}
				//System ����
				for(int i = 0 ; i<data1.length ; i++) {
					for(int j =0; j<3 ; j++) {
						if(j==2) {
							fileWriter.write(((String) data1[i][j]).replaceAll("\n", "\r\n")+""); // ������ ������..
							fileWriter.write("\r\n");
						}
					}
				}
				
				//����ä�� ����
				fileWriter.write(((String) data2[0][0]).replaceAll("\n", "\r\n")+""); // ������ ������..
				fileWriter.write("\r\n");
					
				//����ä�� ��뷮 ��ȸ
				fileWriter.write(((String) data3[0][0]).replaceAll("\n", "\r\n")+""); // ������ ������..
				fileWriter.write("\r\n");
			}
			fileWriter.close();
		}catch(Exception e1) {
			e1.printStackTrace();
			logger.error("message",e1);
		}
	}
	
	//������ ���� �ڵ尡 �ʹ� ������� ������ �ٲ���
	public void arr(int i) {
		// ~~~.html���� ~~~�̸��� ������.
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
			logger.error("save ��ư Ŭ��");
			int result = JOptionPane.showOptionDialog(null, 
					"�����Ͻðڽ��ϱ�?", "�˸�â", 
					JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, 
					null, buttons, "��");
			if(result==0) {
				fileSave();
				// ���� ���� (�α׳����)
				JOptionPane.showMessageDialog(null, "����Ǿ����ϴ�.");
			}
		}
		if(e.getSource().equals(bt_exit)) {
			logger.error("exit ��ư Ŭ��");
			System.exit(0);
		}
		
		String button[] = {"��","�ƴϿ�"};
		if(e.getSource().equals(bt_previous)) {
			int result = JOptionPane.showOptionDialog(null, 
					"�������� ���ðڽ��ϱ�?", "�˸�â", 
					JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, 
					null, button, "��");
				if(result == 0) {
					new BasicUi();
					frame.dispose();
				}
		}
		
		//��ư ���� ���� �迭�� ����. btn[0]~ btn[18] , arr �޼ҵ忡 ��ġ��Ŵ.
		for(int i =0; i<btn.length; i++) {
			if(e.getSource().equals(btn[i])) {
				logger.error("���� ��ư Ŭ��");
				arr(i);
			}
		}
	}
	//table1(�ý������� ����)�� s�� ������ �ܾ���� ��ȣ�� �Ķ��� �����ʿ�� �Ķ���
	public class ColorCellRenderer extends DefaultTableCellRenderer {
		public Component getTableCellRendererComponent(JTable table1, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
			super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
			setForeground(isSelected ? table.getSelectionForeground() : table.getForeground());
			if (value.equals("��ȣ")) {
				setForeground(Color.BLUE);
			} else {
				setForeground(Color.RED);
	        }
	        return this;
	    }
		ColorCellRenderer(){
		}
	}
	
	//Table�� TextArea �� scroll �ֱ� ->\n�� ���̺� ���� �ʴ´�.
	private class CustomCellRenderer extends DefaultTableCellRenderer {
		private JTextArea textArea;
		private JScrollPane scrollPane;

	    public CustomCellRenderer() {
	    	textArea = new JTextArea();
	        scrollPane = new JScrollPane(textArea);
	    }

	    public Component getTableCellRendererComponent(JTable table, Object value,
	            boolean isSelected, boolean hasFocus, int row, int column) {	
	        if(null != value) //�� �ȿ� ������ �ִٸ� �ؽ�Ʈ ������ �߰�
	            textArea.setText(value.toString());
	        return scrollPane;
	    }
	}
	
	//TextArea ������ �� �ְ� ������ִ� �ڵ�
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
