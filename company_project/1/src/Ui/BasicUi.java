package Ui;

import javax.swing.*;
import org.slf4j.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import Encrytion.AES256;
import Infomation.*;

 //ȯ�漳��
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
	
	String ippattern = "([0-9]{1,3})\\.([0-9]{1,3})\\.([0-9]{1,3})\\.([0-9]{1,3})"; //ip ���Խ�
	String portpattern = "^\\d{4}"; //port ���� 4�ڸ� ���Խ�
	String pattern = "^[a-zA-Z0-9`~!@#$%^&*()-=_+\\[\\]{}:;',./<>?\\\\|]*$"; //����,����,Ư������ ���Խ�
	
	public BasicUi() {
		frame = new JFrame("ESF Maintenance Tool v1.0 - ȯ�漳��");
		frame.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane(text);
		scrollPane.setBounds(10, 10, 670, 700);
		frame.add(scrollPane);
		
		jMessage = new JLabel("���뺯�� ��  Modify ��ư�� �����ּ���.");
		jMessage.setBounds(120,730,300,25);
		jMessage.setForeground(color);
		jMessage.setVisible(true);
		frame.add(jMessage);
		//11/22 Ui ������ �κ�
		bt_modify = new JButton("Modify"); //���� 
		bt_modify.setBounds(430, 730, 100, 25);
		frame.add(bt_modify);
		bt_modify.addActionListener(this);
		
		bt_next = new JButton("Next");
		bt_next.setBounds(540, 730, 100, 25);
		frame.add(bt_next);
		bt_next.addActionListener(this);
		frame.setLocationRelativeTo(null);// ȭ�� �߾�
		frame.setSize(700,800);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		try {
			if(file.exists()) { 
				BufferedReader inFile = new BufferedReader(new FileReader(file));
				String line = null;
				String decryptLine = null;
				logger.debug("���� ��ȣȭ");
				while((line = inFile.readLine()) != null) { // �޸��� �� ���� ������ ���������� �ݺ�
					//DB ��ȣȭ ��å
					decryptLine = aes.decrypt(line);
					text.append(decryptLine+"\n");  //TextArea�� ��ȣȭ �� ���� �Ѹ�
				}	
				inFile.close();
			}
		}catch (Exception e) {
			logger.error("error �߰�" ,e);
			e.printStackTrace();
		}
		Dimension frameSize = frame.getSize();
		// �� ������� ũ�⸦ ���մϴ�.
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		/*
		* �׷��� �������� ��ġ��
		* (�����ȭ�� ���� - ������ȭ�� ����) / 2,
		* (�����ȭ�� ���� - ������ȭ�� ����) / 2 �̷��� �����Ѵ�.
		*/
		frame.setLocation((screenSize.width - frameSize.width)/2, (screenSize.height - frameSize.height)/2);
		//frame.setResizable(false); // ������ ũ�� ������ �Ҽ� ���� ����ϴ�.
	}
	
	String button[] = {"��","�ƴϿ�"};

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		FileWriter writer = null;
		
		File file1 = new File(puc.fixPath+"\\config.txt"); 
		FileReader fileReader = null;
		BufferedReader bufferedReader = null;
		FileWriter writer1 = null;
		String line = null;
		String replaceLine = ""; //������ �ִ� ���� ���� ������ ��ȣȭ�ϱ� ���� ��Ʈ��
		
		if(e.getSource().equals(bt_modify)) { //modify ��ư�� ��������
			logger.debug("Modify ��ư Ŭ��");
			int result = JOptionPane.showOptionDialog(null, "�����Ͻðڽ��ϱ�?", "�˸�â", 
					JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, button, "��");
			if(result == 0) { //"��" ��������
				try {
									
					writer = new FileWriter(file); 
					fileReader = new FileReader(file1);
					bufferedReader = new BufferedReader(fileReader);
					writer1 = new FileWriter(file1);
					//1.��ȣȭ �� ���� �Ѹ� ���� �ٽ� ���ְ�
					writer.write(text.getText().replaceAll("\n", "\r\n")); //�켱 ȯ�漳���� �ִ� �� ���� �״�� ���Ͽ� ����. �Ŀ� �ٽ��о� ��ȣȭ
					//2.��������
					writer.close();
					logger.debug("���� ��ȣȭ");
					
					//3.�ٽ� ��ȣȭ �� ������  ���ش�.
					while((line = bufferedReader.readLine()) != null) {
						replaceLine += (aes.encrypt(line) + "\r\n"); //��� ��ȣȭ
					}
					writer1.write(replaceLine); //�ٽ� ���Ͽ� ����.
					logger.debug("���� ��ư Ŭ�� �� ��ȣȭ");
					bufferedReader.close();
					writer1.close();
					JOptionPane.showMessageDialog(null, "�����Ǿ����ϴ�.");
					Alarm.checkBt=false;
				}
				catch(Exception e1) {
					e1.printStackTrace();
					logger.error("error �߰�" , e);
				}
			}
			else {
				return;
			}
		}
		if(e.getSource().equals(bt_next)) {
			logger.debug("next ��ư Ŭ��");
			new AlertUi(1);
			frame.dispose();
		}
	}
}