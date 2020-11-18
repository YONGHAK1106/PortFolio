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
	//�ʱ� ��й�ȣ
	static String pw = null;

	//�ʱ� ��й�ȣ ���
	File file = new File(puc.fixPath + "\\keys\\password.txt");
	
	JLabel l1 = new JLabel("���� ��й�ȣ");
	JLabel l2 = new JLabel("������ ��й�ȣ");
	JLabel l3 = new JLabel("��й�ȣ ���Է�");
	
	JPasswordField text1 = new JPasswordField();
	static JPasswordField text2 = new JPasswordField();
	JPasswordField text3 = new JPasswordField();
	
	//text2�� �Է¹��� ���� ��������
	protected static String changePW = null; 
	String changePW1 = null;

	JButton bt = new JButton("����");
	JButton bt1 = new JButton("���");
	static JFrame f; 
	
	public static void main(String args[]) {
		new ChangePassword();
		f.setVisible(true);
	}
	//�����ڷ� ���� ChangePassword�� �θ�Ŭ�������� �α���â�� ȣ��� �� ���� ȣ��� ->�޼ҵ�κ���
	public ChangePassword() {
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
			String line;
			while((line = br.readLine()) != null) {
				if(line.indexOf("Password:") != -1)
					pw  = line.substring(9);
			}
			/**
			 * �ؽ�Ʈ���Ͽ� ����� ��ȣȭ���� 64��Ʈ  ���̸� ���� ��ȣȭ���� 64��Ʈ�� ����Ǳ⶧���� 
			 * changeText������ ���ǹ� �޾��� ��ȣȭ�ѹ����� ���⼭ �ٽ� ��ȣȭ �ϱ⶧���� �װ��� ����
			 */
			if(pw.length()<64) {
		    	pw = sha.encryptSHA256(pw);
			} //- > ó������ ��й�ȣ�� ��ȣȭ �Ǿ��ִ»��¿�����
			
			br.close();
			
			System.out.println("pw : " + pw);
			FileWriter writer = new FileWriter(file);
			writer.write("Password:"+pw);
			writer.close();
		}catch(Exception e) {
			e.printStackTrace();
			logger.error("message", e);
		}
		f = new JFrame("��к�ȣ ����");
		f.setLayout(null);
		f.setSize(400,600);
		Font font = new Font("�������",Font.PLAIN,14);
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
		// �� ������� ũ�⸦ ���մϴ�.
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		/*
		* �׷��� �������� ��ġ��
		* (�����ȭ�� ���� - ������ȭ�� ����) / 2,
		* (�����ȭ�� ���� - ������ȭ�� ����) / 2 �̷��� �����Ѵ�.
		*/
		f.setLocation((screenSize.width - frameSize.width)/2, (screenSize.height - frameSize.height)/2); 
		f.setResizable(false); // ������ ũ�� ������ �Ҽ� ���� ����ϴ�.
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		changePW = text2.getText(); // �����ؽ�Ʈ 1
		changePW1 = text3.getText(); //�����ؽ�Ʈ 2
		
		if(e.getSource().equals(bt)) {
		 	try {
		 		if(pw.equals(sha.encryptSHA256(text1.getText().trim()))) {
		 			if(changePW.isEmpty()) { 
		 				JOptionPane.showMessageDialog(null, "�� ĭ�� ������ �ʽ��ϴ�");
		 			}
		 			else if(changePW1.isEmpty()) {
		 				JOptionPane.showMessageDialog(null, "�� ĭ�� ������ �ʽ��ϴ�");
		 			}
		 			// ������ ���ٸ�
		 			else if(changePW.contentEquals(changePW1) == false) { 
		 				JOptionPane.showMessageDialog(null, "������ ��й�ȣ�� ��ġ���� �ʽ��ϴ�.");
		 			}
		 			else if(changePW.contentEquals(changePW1)) {
		 				//������ ��й�ȣ ��ȣȭ �� pw�� ����
		 				pw = sha.encryptSHA256(changePW);
						
		 				FileWriter writer = new FileWriter(file);
		 				writer.write("Password:" + pw);
						writer.close();
						
						JOptionPane.showMessageDialog(null, "��й�ȣ ����Ϸ�");
						f.dispose();
						new LoginUi();
		 			}
		 		}
		 		else {
		 			JOptionPane.showMessageDialog(null, "���� ��й�ȣ�� Ʋ�Ƚ��ϴ�.");
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