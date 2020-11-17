package Ui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import org.slf4j.*;

public class Alarm implements ActionListener {
	static public boolean checkBt = false; //�������� �ϳ� �����Ѵ�.
	final private Logger logger = LoggerFactory.getLogger(getClass());
	public static JFrame frame = new JFrame("���â");
	JButton bt = new JButton("Ȯ��");
	
	public Alarm() {
		JLabel label = new JLabel("DB ���� ��Ȯ�����ּ���.");
		
		frame.setLayout(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		label.setForeground(new Color(255,0,0));
		label.setFont(new Font("serif",Font.BOLD,18));
		label.setBounds(35,30,250,30);
		bt.setBounds(100, 60, 100, 25);
		
		frame.add(label);
		
		bt.addActionListener(this);
		frame.add(bt);

		frame.setLocationRelativeTo(null);
		frame.setSize(300,150);
		frame.setVisible(true);
		Dimension frameSize = frame.getSize();
		// �� ������� ũ�⸦ ����
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setLocation((screenSize.width - frameSize.width)/2, (screenSize.height - frameSize.height)/2);
	}
	
	public void actionPerformed(ActionEvent e) {
		/**
		 * ��ư Ŭ���� �������� checkBt�� true ��ȯ�Ѵ�
		 */
		if(e.getSource().equals(bt)) {
			logger.debug("alarm ��ư Ŭ��");
			checkBt = true; 
			frame.dispose();
			new BasicUi();
		}
	}
}
