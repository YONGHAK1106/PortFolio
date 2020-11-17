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
	private JTextField idText; //id �ؽ�Ʈ�ʵ�
	private JPasswordField pwText; //password �ؽ�Ʈ�ʵ�
	private ImageIcon im; // ȸ�� �̹��� ������
	private static String id = "newgencni"; // ���̵�
	
	JButton bt_login = new JButton("\uB85C\uADF8\uC778");
	 
	JButton bt_changePw = new JButton("��й�ȣ����");
	int count = 1; // �α��� ���� �ݺ� Ƚ��
	static JFrame j ; //������ Ʋ
	
	public LoginUi() {
		logger.debug("���α׷� ����");
		
		j =new JFrame("ESF Maintenance Tool v1.0");
		 
        j.setSize( 364, 531 );
        j.setVisible(true);
        j.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //���μ��������� ����
        
        JLabel label_Title = new JLabel("�α���");
        label_Title.setHorizontalAlignment(SwingConstants.CENTER);
        label_Title.setForeground(Color.DARK_GRAY);
        label_Title.setFont(new Font("����", Font.BOLD, 25));
        
        JLabel label_ID = new JLabel("ID");
        label_ID.setFont(new Font("����", Font.BOLD, 12));        
        idText = new JTextField();
    
        pwText = new JPasswordField();
        JLabel label_PW = new JLabel("PASSWORD");
        label_PW.setFont(new Font("����", Font.BOLD, 12));

        im = new ImageIcon("new.png"); // UI �� �̹��� �������ؾ���
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
        idText.requestFocus(true); //������ ���콺 ��Ŀ���ֱ�
		 
        pwText.addKeyListener(this);
        bt_login.addActionListener(this);
        bt_changePw.addActionListener(this);

        // â ȭ�� �߰��� ����
		// �������� ����� ���մϴ�.
		Dimension frameSize = j.getSize();
		// �� ������� ũ�⸦ ���մϴ�.
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		/*
		* �׷��� �������� ��ġ��
		* (�����ȭ�� ���� - ������ȭ�� ����) / 2,
		* (�����ȭ�� ���� - ������ȭ�� ����) / 2 �̷��� �����Ѵ�.
		*/
		j.setLocation((screenSize.width - frameSize.width)/2, (screenSize.height - frameSize.height)/2);	       
	}
	boolean bLoginCheck = false; //��ư�� �ȴ����� �ִٸ� ����

	@SuppressWarnings("deprecation")
	public void isLoginCheck() { //��ȿ�� �˻�
		try {
			//trim() �� �� ��������� ��
			//��ȣȭ�� ���峢�� ��
			if(id.equals(idText.getText().trim()) && pw.equals(sha.encryptSHA256(pwText.getText().trim()))) { // 
				logger.debug("password ��ȣȭ ��");
				bLoginCheck = true;
				//isLogin()�� true��� BasicUi�� �Ѿ
				if(isLogin()) { 
					LoginCheck=1;
					new BasicUi();
				}
				j.dispose();//�α��� â ����
			}
			else {
				logger.error("���̵� �Ǵ� ��й�ȣ �Է½��� ");
				JOptionPane.showMessageDialog(null, "���̵� �Ǵ� ��й�ȣ�� Ʋ�Ƚ��ϴ�."
						+ count +"ȸ Ʋ�Ƚ��ϴ�. \n" 
						+"(5ȸ ���� �� �ʱ� ��й�ȣ�� ����˴ϴ�.)");
				count++;
				if(count ==6) {
					JOptionPane.showMessageDialog(null, "�α��� â�� ����˴ϴ�. �� �����ϼ���.");
					
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
			logger.debug("�α��� ��ư Ŭ��");
			isLoginCheck(); // �α��� ��ư Ŭ�� �� 
		}
		//��й�ȣ ã�� ��ư Ŭ�� ��
		if(e.getSource().equals(bt_changePw)) { 
			logger.debug("�н����� ���� ��ư Ŭ��");
			String[] buttons = {"��", "�ƴϿ�"}; //���� 0 , �ƴϿ��� 1 , ��ü ���� Ŭ�� �̺�Ʈ
			int result = JOptionPane.showOptionDialog(null, "��й�ȣ �����Ͻðڽ��ϱ�?","�˸�â",
					JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, 
					null, buttons, "��");
			if(result == 0) {
				ChangePassword.main(null);
				j.dispose();
			}
		}
	}
	public void keyTyped(KeyEvent e) {}
	public void keyReleased(KeyEvent e) {
		
	}
	//���� Ű �Է½� ��ư Ŭ�� �̺�Ʈ
	public void keyPressed(KeyEvent e) {  // pwText�� ��Ŀ���� �մ� ���¿��� ����Ű ������ �α��� ��ư Ŭ���ϰ��ϱ�
		int key = e.getKeyCode();
		if (key == KeyEvent.VK_ENTER) {
			Toolkit.getDefaultToolkit().beep(); 
			bt_login.doClick();
		}
	}
	public void focusGained(FocusEvent e) {
		  //idText �� ��Ŀ���� �η��� �Ҷ� idText�� ���� ������ idText�� ��Ŀ�� �̵�
	} 
	public void focusLost(FocusEvent e) {
		//�ش� ���۳�ƮidText�� ��� �� �����
	}
}