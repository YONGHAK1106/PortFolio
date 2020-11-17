package Ui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import org.slf4j.*;

public class Alarm implements ActionListener {
	static public boolean checkBt = false; //전역변수 하나 생성한다.
	final private Logger logger = LoggerFactory.getLogger(getClass());
	public static JFrame frame = new JFrame("경고창");
	JButton bt = new JButton("확인");
	
	public Alarm() {
		JLabel label = new JLabel("DB 정보 재확인해주세요.");
		
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
		// 내 모니터의 크기를 구함
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setLocation((screenSize.width - frameSize.width)/2, (screenSize.height - frameSize.height)/2);
	}
	
	public void actionPerformed(ActionEvent e) {
		/**
		 * 버튼 클릭시 전역변수 checkBt를 true 변환한다
		 */
		if(e.getSource().equals(bt)) {
			logger.debug("alarm 버튼 클릭");
			checkBt = true; 
			frame.dispose();
			new BasicUi();
		}
	}
}
