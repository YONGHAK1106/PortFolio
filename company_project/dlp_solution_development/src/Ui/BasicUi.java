package Ui;

import javax.swing.*;
import org.slf4j.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import Encrytion.AES256;
import Infomation.*;

 //환경설정
public class BasicUi extends JPanel implements ActionListener{
	PublicUseClass puc = new PublicUseClass();
	final private Logger logger = LoggerFactory.getLogger(getClass());
	File file = new File(puc.fixPath+"\\config.txt");
	
	protected static Font font = new Font("Serif", Font.PLAIN, 13);
	JTextArea text = new JTextArea("");
	JLabel jMessage;
	JButton bt_modify;
	JButton bt_next;
	Color color = new Color(255,0,0);
	static
	public JFrame frame;
	static public int nextC = 0;
	AES256 aes = new AES256(puc.reskey);
	
	String ippattern = "([0-9]{1,3})\\.([0-9]{1,3})\\.([0-9]{1,3})\\.([0-9]{1,3})"; //ip 정규식
	String portpattern = "^\\d{4}"; //port 숫자 4자리 정규식
	String pattern = "^[a-zA-Z0-9`~!@#$%^&*()-=_+\\[\\]{}:;',./<>?\\\\|]*$"; //영어,숫자,특수문자 정규식
	
	public BasicUi() {
		frame = new JFrame("ESF Maintenance Tool v1.0 - 환경설정");
		frame.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane(text);
		scrollPane.setBounds(10, 10, 670, 700);
		frame.add(scrollPane);
		
		jMessage = new JLabel("내용변경 시  Modify 버튼을 눌러주세요.");
		jMessage.setBounds(120,730,300,25);
		jMessage.setForeground(color);
		jMessage.setVisible(true);
		frame.add(jMessage);
		//11/22 Ui 수정한 부분
		bt_modify = new JButton("Modify"); //수정 
		bt_modify.setBounds(430, 730, 100, 25);
		frame.add(bt_modify);
		bt_modify.addActionListener(this);
		
		bt_next = new JButton("Next");
		bt_next.setBounds(540, 730, 100, 25);
		frame.add(bt_next);
		bt_next.addActionListener(this);
		frame.setLocationRelativeTo(null);// 화면 중앙
		frame.setSize(700,800);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		try {
			if(file.exists()) { 
				BufferedReader inFile = new BufferedReader(new FileReader(file));
				String line = null;
				String decryptLine = null;
				logger.debug("파일 복호화");
				while((line = inFile.readLine()) != null) { // 메모장 속 다음 문장이 없을때까지 반복
					//DB 암호화 정책
					decryptLine = aes.decrypt(line);
					text.append(decryptLine+"\n");  //TextArea에 복호화 된 문장 뿌림
				}	
				inFile.close();
			}
		}catch (Exception e) {
			logger.error("error 발견" ,e);
			e.printStackTrace();
		}
		Dimension frameSize = frame.getSize();
		// 내 모니터의 크기를 구합니다.
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		/*
		* 그래서 프레임의 위치를
		* (모니터화면 가로 - 프레임화면 가로) / 2,
		* (모니터화면 세로 - 프레임화면 세로) / 2 이렇게 설정한다.
		*/
		frame.setLocation((screenSize.width - frameSize.width)/2, (screenSize.height - frameSize.height)/2);
		//frame.setResizable(false); // 프레임 크기 조절을 할수 없게 만듭니다.
	}
	
	String button[] = {"예","아니오"};

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		FileWriter writer = null;
		
		File file1 = new File(puc.fixPath+"\\config.txt"); 
		FileReader fileReader = null;
		BufferedReader bufferedReader = null;
		FileWriter writer1 = null;
		String line = null;
		String replaceLine = ""; //기존에 있던 값에 대해 라인을 암호화하기 위한 스트링
		
		if(e.getSource().equals(bt_modify)) { //modify 버튼을 눌렀을때
			logger.debug("Modify 버튼 클릭");
			int result = JOptionPane.showOptionDialog(null, "수정하시겠습니까?", "알림창", 
					JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, button, "예");
			if(result == 0) { //"예" 눌렀을때
				try {
									
					writer = new FileWriter(file); 
					fileReader = new FileReader(file1);
					bufferedReader = new BufferedReader(fileReader);
					writer1 = new FileWriter(file1);
					//1.복호화 된 문장 뿌린 것을 다시 써주고
					writer.write(text.getText().replaceAll("\n", "\r\n")); //우선 환경설정에 있는 그 값을 그대로 파일에 저장. 후에 다시읽어 암호화
					//2.닫은다음
					writer.close();
					logger.debug("파일 암호화");
					
					//3.다시 암호화 한 문장을  써준다.
					while((line = bufferedReader.readLine()) != null) {
						replaceLine += (aes.encrypt(line) + "\r\n"); //모두 암호화
					}
					writer1.write(replaceLine); //다시 파일에 저장.
					logger.debug("수정 버튼 클릭 시 암호화");
					bufferedReader.close();
					writer1.close();
					JOptionPane.showMessageDialog(null, "수정되었습니다.");
					Alarm.checkBt=false;
				}
				catch(Exception e1) {
					e1.printStackTrace();
					logger.error("error 발견" , e);
				}
			}
			else {
				return;
			}
		}
		if(e.getSource().equals(bt_next)) {
			logger.debug("next 버튼 클릭");
			new AlertUi(1);
			frame.dispose();
		}
	}
}