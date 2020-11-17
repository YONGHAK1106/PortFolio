package Ui;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import javax.swing.*;
import org.slf4j.*;
import Encrytion.SHA256;
import Infomation.PublicUseClass;

public class ChangePassword implements ActionListener {
	PublicUseClass puc  = new PublicUseClass();
	final private Logger logger = LoggerFactory.getLogger(getClass());
	SHA256 sha = new SHA256();
	static String line;
	//초기 비밀번호
	static String pw = null;

	//초기 비밀번호 경로
	File file = new File(puc.fixPath + "\\keys\\password.txt");
	
	JLabel l1 = new JLabel("현재 비밀번호");
	JLabel l2 = new JLabel("변경할 비밀번호");
	JLabel l3 = new JLabel("비밀번호 재입력");
	
	JPasswordField text1 = new JPasswordField();
	static JPasswordField text2 = new JPasswordField();
	JPasswordField text3 = new JPasswordField();
	
	//text2에 입력받은 값을 리턴해줌
	protected static String changePW = null; 
	String changePW1 = null;

	JButton bt = new JButton("변경");
	JButton bt1 = new JButton("취소");
	static JFrame f; 
	
	public static void main(String args[]) {
		new ChangePassword();
		f.setVisible(true);
	}
	//생성자로 쓰면 ChangePassword가 부모클래스여서 로그인창이 호출될 때 같이 호출됨 ->메소드로변경
	public ChangePassword() {
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
			String line;
			while((line = br.readLine()) != null) {
				if(line.indexOf("Password:") != -1)
					pw  = line.substring(9);
			}
			/**
			 * 텍스트파일에 저장된 암호화문이 64비트  밑이면 실행 암호화문은 64비트로 저장되기때문에 
			 * changeText때문에 조건문 달아줌 암호화한문장을 여기서 다시 암호화 하기때문에 그것을 방지
			 */
			if(pw.length()<64) {
		    	pw = sha.encryptSHA256(pw);
			} //- > 처음에는 비밀번호가 암호화 되어있는상태여야함
			
			br.close();
			
			System.out.println("pw : " + pw);
			FileWriter writer = new FileWriter(file);
			writer.write("Password:"+pw);
			writer.close();
		}catch(Exception e) {
			e.printStackTrace();
			logger.error("message", e);
		}
		f = new JFrame("비밀변호 변경");
		f.setLayout(null);
		f.setSize(400,600);
		Font font = new Font("맑은고딕",Font.PLAIN,14);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		f.setLocation((dim.width/2) - (f.getWidth()/2), (dim.height/2) - (f.getHeight()/2));
		
		l1.setFont(font);
		l2.setFont(font);
		l3.setFont(font);

		l1.setBounds(30,100,120,40);
		l2.setBounds(30,150,120,40);
		l3.setBounds(30,200,120,40);
		
		text1.setBounds(160,100,160,30);
		text2.setBounds(160,150,160,30);
		text3.setBounds(160,200,160,30);

		bt.setBounds(200,250,115,25);
		bt1.setBounds(200,290,115,25);
		
		bt.addActionListener(this);
		bt1.addActionListener(this);
		
		f.add(l1);
		f.add(text1);
		f.add(l2);
		f.add(text2);
		f.add(l3);
		f.add(text3);
		f.add(bt);
		f.add(bt1);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Dimension frameSize = f.getSize();
		// 내 모니터의 크기를 구합니다.
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		/*
		* 그래서 프레임의 위치를
		* (모니터화면 가로 - 프레임화면 가로) / 2,
		* (모니터화면 세로 - 프레임화면 세로) / 2 이렇게 설정한다.
		*/
		f.setLocation((screenSize.width - frameSize.width)/2, (screenSize.height - frameSize.height)/2); 
		f.setResizable(false); // 프레임 크기 조절을 할수 없게 만듭니다.
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		changePW = text2.getText(); // 변경텍스트 1
		changePW1 = text3.getText(); //변경텍스트 2
		
		if(e.getSource().equals(bt)) {
		 	try {
		 		if(pw.equals(sha.encryptSHA256(text1.getText().trim()))) {
		 			if(changePW.isEmpty()) { 
		 				JOptionPane.showMessageDialog(null, "빈 칸응 허용되지 않습니다");
		 			}
		 			else if(changePW1.isEmpty()) {
		 				JOptionPane.showMessageDialog(null, "빈 칸응 허용되지 않습니다");
		 			}
		 			// 내용이 같다면
		 			else if(changePW.contentEquals(changePW1) == false) { 
		 				JOptionPane.showMessageDialog(null, "변경할 비밀번호가 일치하지 않습니다.");
		 			}
		 			else if(changePW.contentEquals(changePW1)) {
		 				//변경할 비밀번호 암호화 후 pw에 저장
		 				pw = sha.encryptSHA256(changePW);
						
		 				FileWriter writer = new FileWriter(file);
		 				writer.write("Password:" + pw);
						writer.close();
						
						JOptionPane.showMessageDialog(null, "비밀번호 변경완료");
						f.dispose();
						new LoginUi();
		 			}
		 		}
		 		else {
		 			JOptionPane.showMessageDialog(null, "현재 비밀번호가 틀렸습니다.");
		 		}			
		 	}catch(Exception x) {
		 		x.printStackTrace();
		 		logger.error("error message",x);
		 	}
		}
		if(e.getSource().equals(bt1)) {
			System.exit(0);
		}
	}
}