package Ui;
import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import org.slf4j.*;

import Infomation.PublicUseClass;

public class LoginUi extends ChangePassword implements FocusListener,ActionListener, KeyListener{
	public static int LoginCheck=0;
	PublicUseClass puc = new PublicUseClass();
	final private Logger logger = LoggerFactory.getLogger(getClass());
	private JTextField idText; //id 텍스트필드
	private JPasswordField pwText; //password 텍스트필드
	private ImageIcon im; // 회사 이미지 아이콘
	private static String id = "newgencni"; // 아이디
	
	JButton bt_login = new JButton("\uB85C\uADF8\uC778");
	 
	JButton bt_changePw = new JButton("비밀번호변경");
	int count = 1; // 로그인 실패 반복 횟수
	static JFrame j ; //프레임 틀
	
	public LoginUi() {
		logger.debug("프로그램 실행");
		
		j =new JFrame("ESF Maintenance Tool v1.0");
		 
        j.setSize( 364, 531 );
        j.setVisible(true);
        j.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //프로세스에서도 끄기
        
        JLabel label_Title = new JLabel("로그인");
        label_Title.setHorizontalAlignment(SwingConstants.CENTER);
        label_Title.setForeground(Color.DARK_GRAY);
        label_Title.setFont(new Font("굴림", Font.BOLD, 25));
        
        JLabel label_ID = new JLabel("ID");
        label_ID.setFont(new Font("굴림", Font.BOLD, 12));        
        idText = new JTextField();
    
        pwText = new JPasswordField();
        JLabel label_PW = new JLabel("PASSWORD");
        label_PW.setFont(new Font("굴림", Font.BOLD, 12));

        im = new ImageIcon("new.png"); // UI 상 이미지 뉴젠씨앤아이
        j.setIconImage(im.getImage());
        JLabel label_deco;
        label_deco = new JLabel(im);
        
        GroupLayout groupLayout = new GroupLayout(j.getContentPane());
        groupLayout.setHorizontalGroup(
        	groupLayout.createParallelGroup(Alignment.LEADING)
        		.addGroup(groupLayout.createSequentialGroup()
        			.addGap(31)
        			.addComponent(label_Title, GroupLayout.PREFERRED_SIZE, 277, GroupLayout.PREFERRED_SIZE)
        			.addContainerGap(40, Short.MAX_VALUE))
        		.addGroup(groupLayout.createSequentialGroup()
        			.addGap(73)
        			.addComponent(label_deco, GroupLayout.PREFERRED_SIZE, 249, GroupLayout.PREFERRED_SIZE)
        			.addContainerGap(26, Short.MAX_VALUE))
        		.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
        			.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
        				.addGroup(Alignment.LEADING, groupLayout.createSequentialGroup()
        					.addGap(56)
        					.addComponent(label_ID, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE)
        					.addPreferredGap(ComponentPlacement.UNRELATED)
        					.addComponent(idText, GroupLayout.DEFAULT_SIZE, 196, Short.MAX_VALUE))
        				.addGroup(Alignment.LEADING, groupLayout.createSequentialGroup()
        					.addGap(18)
        					.addComponent(label_PW)
        					.addPreferredGap(ComponentPlacement.UNRELATED)
        					.addComponent(pwText, GroupLayout.DEFAULT_SIZE, 196, Short.MAX_VALUE))
        				.addGroup(groupLayout.createSequentialGroup()
        					.addContainerGap()
        					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
        						.addComponent(bt_login, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        						.addComponent(bt_changePw, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 146, Short.MAX_VALUE))))
        			.addGap(41))
        );
        groupLayout.setVerticalGroup(
        	groupLayout.createParallelGroup(Alignment.TRAILING)
        		.addGroup(groupLayout.createSequentialGroup()
        			.addGap(62)
        			.addComponent(label_Title, GroupLayout.DEFAULT_SIZE, 49, Short.MAX_VALUE)
        			.addGap(87)
        			.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
        				.addComponent(idText, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
        				.addComponent(label_ID, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE))
        			.addGap(63)
        			.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
        				.addComponent(pwText, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE)
        				.addComponent(label_PW, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE))
        			.addGap(34)
        			.addComponent(bt_login, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addComponent(bt_changePw, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        			.addGap(18)
        			.addComponent(label_deco, GroupLayout.PREFERRED_SIZE, 44, GroupLayout.PREFERRED_SIZE)
        			.addContainerGap())
        );
        
        j.getContentPane().setLayout(groupLayout);
        idText.requestFocus(true); //강제로 마우스 포커스주기
		 
        pwText.addKeyListener(this);
        bt_login.addActionListener(this);
        bt_changePw.addActionListener(this);

        // 창 화면 중간에 띄우기
		// 프레임의 사이즈를 구합니다.
		Dimension frameSize = j.getSize();
		// 내 모니터의 크기를 구합니다.
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		/*
		* 그래서 프레임의 위치를
		* (모니터화면 가로 - 프레임화면 가로) / 2,
		* (모니터화면 세로 - 프레임화면 세로) / 2 이렇게 설정한다.
		*/
		j.setLocation((screenSize.width - frameSize.width)/2, (screenSize.height - frameSize.height)/2);	       
	}
	boolean bLoginCheck = false; //버튼이 안눌리고 있다를 말함

	@SuppressWarnings("deprecation")
	public void isLoginCheck() { //유효성 검사
		try {
			//trim() 앞 뒤 공백없애줌 ㄹ
			//암호화된 문장끼리 비교
			if(id.equals(idText.getText().trim()) && pw.equals(sha.encryptSHA256(pwText.getText().trim()))) { // 
				logger.debug("password 암호화 비교");
				bLoginCheck = true;
				//isLogin()이 true라면 BasicUi로 넘어감
				if(isLogin()) { 
					LoginCheck=1;
					new BasicUi();
				}
				j.dispose();//로그인 창 종료
			}
			else {
				logger.error("아이디 또는 비밀번호 입력실패 ");
				JOptionPane.showMessageDialog(null, "아이디 또는 비밀번호가 틀렸습니다."
						+ count +"회 틀렸습니다. \n" 
						+"(5회 실패 시 초기 비밀번호로 변경됩니다.)");
				count++;
				if(count ==6) {
					JOptionPane.showMessageDialog(null, "로그인 창이 종료됩니다. 재 시작하세요.");
					
					File file = new File(puc.fixPath + "\\keys\\password.txt");
					FileWriter writer = new FileWriter(file);
					writer.write("Password:"+sha.encryptSHA256("newgenc&i88"));
					
					writer.close();
					System.exit(0);
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
			logger.error("error message",e);
		}
	}
	
	public void de(int count) {
		this.count = count;
	}
	public boolean isLogin() {
		return bLoginCheck;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource().equals(bt_login)) {
			logger.debug("로그인 버튼 클릭");
			isLoginCheck(); // 로그인 버튼 클릭 시 
		}
		//비밀번호 찾기 버튼 클릭 시
		if(e.getSource().equals(bt_changePw)) { 
			logger.debug("패스워드 변경 버튼 클릭");
			String[] buttons = {"예", "아니오"}; //예는 0 , 아니오는 1 , 전체 저장 클릭 이벤트
			int result = JOptionPane.showOptionDialog(null, "비밀번호 변경하시겠습니까?","알림창",
					JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, 
					null, buttons, "예");
			if(result == 0) {
				ChangePassword.main(null);
				j.dispose();
			}
		}
	}
	public void keyTyped(KeyEvent e) {}
	public void keyReleased(KeyEvent e) {
		
	}
	//엔터 키 입력시 버튼 클릭 이벤트
	public void keyPressed(KeyEvent e) {  // pwText에 포커스고 잇는 상태에서 엔터키 누르면 로그인 버튼 클릭하게하기
		int key = e.getKeyCode();
		if (key == KeyEvent.VK_ENTER) {
			Toolkit.getDefaultToolkit().beep(); 
			bt_login.doClick();
		}
	}
	public void focusGained(FocusEvent e) {
		  //idText 에 포커스를 두려고 할때 idText에 값이 없으면 idText로 포커스 이동
	} 
	public void focusLost(FocusEvent e) {
		//해당 컴퍼넌트idText를 벗어날 때 실행됨
	}
}