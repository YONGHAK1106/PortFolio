package Ui;

import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.*;
import org.slf4j.*;
import Infomation.Preferences;

@SuppressWarnings("serial")
public class AlertUi {

	JOptionPane msg;
	final private Logger logger = LoggerFactory.getLogger(getClass());
	public static JFrame frame = new JFrame("알림 창");
	protected static Font font = new Font("Serif", Font.PLAIN, 13);
	
	JLabel a1 = new JLabel("시");
	JLabel b1 = new JLabel("시"); 
	
	JLabel a2 = new JLabel("스");
	JLabel b2 = new JLabel("스");
	
	JLabel a3 = new JLabel("템");
	JLabel b3 = new JLabel("템");
	
	JLabel a4 = new JLabel("점");
	JLabel b4 = new JLabel("점");
	
	JLabel a5 = new JLabel("검");
	JLabel b5 = new JLabel("검");
	
	JLabel a6 = new JLabel("중");
	JLabel b6 = new JLabel("중");
	
	JLabel a[] = {a1,a2,a3,a4,a5,a6};
	JLabel b[] = {b1,b2,b3,b4,b5,b6};
	
	JLabel jmsg = new JLabel("약 10초만 기다려주세요");
	
	public AlertUi(int i) {
			
			
		if(i==1) {
			
			frame.setLayout(null);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			Color color = new Color(0,0,255);
	
			a1.setBounds(30,30,20,20);
			a2.setBounds(50,30,20,20);
			a3.setBounds(70,30,20,20);
			a4.setBounds(90,30,20,20);
			a5.setBounds(110,30,20,20);
			a6.setBounds(130,30,20,20);
			
			b1.setBounds(30,27,20,20);
			b2.setBounds(50,27,20,20);
			b3.setBounds(70,27,20,20);
			b4.setBounds(90,27,20,20);
			b5.setBounds(110,27,20,20);
			b6.setBounds(130,27,20,20);		
			
			jmsg.setBounds(20,55,250,20);
			jmsg.setForeground(new Color(255,0,0));
			
			frame.add(jmsg);
			for(int i1 =0; i1<6; i1++) {
				
				frame.add(a[i1]);
				frame.add(b[i1]);
				a[1].setFont(font);
				b[i1].setFont(font);
				a[i1].setVisible(false);
				b[i1].setVisible(false);
				b[i1].setForeground(color);
			}

			
			frame.setLocationRelativeTo(null);
			frame.setSize(200,120);
			
			// 창 화면 중간에 띄우기
			// 프레임의 사이즈를 구함
			Dimension frameSize = frame.getSize();
			// 내 모니터의 크기를 구함
			Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
			
			/*
			* 그래서 프레임의 위치를
			* (모니터화면 가로 - 프레임화면 가로) / 2,
			* (모니터화면 세로 - 프레임화면 세로) / 2 이렇게 설정한다.
			*/
			frame.setLocation((screenSize.width - frameSize.width)/2, (screenSize.height - frameSize.height)/2);
			frame.setResizable(false); // 프레임 크기 조절을 할수 없게음
			frame.setVisible(true);
			Timer sysUiTimer = new Timer(true);
		
			TimerTask ta1 = new TimerTask() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					new SystemUi();
					frame.dispose();		
				}
				
			};
			sysUiTimer.schedule(ta1,100);
	
			
			Timer timer = new Timer(true);
			//정해진 시간 간격으로 특정한 일을 시킴.
			TimerTask ta = new TimerTask() {
				@Override
				public void run() {
					
					try {
						while(true) {							
							
							timer();
							Thread.sleep(200);
							timer1();
							Thread.sleep(200);
							timer2();
							Thread.sleep(200);
							timer3();
							Thread.sleep(200);
							timer4();
							Thread.sleep(200);
							timer5();
							Thread.sleep(200);
							timer6();
							Thread.sleep(1000);
							timer7();
							
							//현재 checkBt가 false 인지 true 인지 확인하기위해 씀
							System.out.println(Alarm.checkBt + " @@@@@@@@@@@###@@@@@@");
							
							//checkBt가 true면 반복문 빠져나가라
							if(Alarm.checkBt==true) {
								break;
							}
						}
						System.out.println(Alarm.checkBt + " @@@@@@@@@@@@@@@@@");
						frame.dispose();
					} 
					catch (Exception e) {
						logger.debug("AlertUi에 catch문 확인바람. ");
						
					}
				}
			};
			
			timer.schedule(ta,100); //0.5초
		}
		
	}	

	public void timer() {

		a[0].setVisible(false);
		b[0].setVisible(true);
		a[1].setVisible(true);
		a[2].setVisible(true);
		a[3].setVisible(true);
		a[4].setVisible(true);
		a[5].setVisible(true);
	}
	
	
	public void timer1() {
	
		a[0].setVisible(true);
		b[0].setVisible(false);
		b[1].setVisible(true);
		a[1].setVisible(false);
	}	
	
	public void timer2() {
	
		b[1].setVisible(false);
		a[1].setVisible(true);
		b[2].setVisible(true);
		a[2].setVisible(false);
	}
	
	public void timer3() {
		
		b[2].setVisible(false);
		a[2].setVisible(true);
		b[3].setVisible(true);
		a[3].setVisible(false);
	}	
	
	public void timer4() {

		b[3].setVisible(false);
		a[3].setVisible(true);
		b[4].setVisible(true);
		a[4].setVisible(false);
	}

	public void timer5() {
		
		b[4].setVisible(false);
		a[4].setVisible(true);
		b[5].setVisible(true);
		a[5].setVisible(false);
	}
	
	public void timer6() {
		
		b[5].setVisible(false);
		a[5].setVisible(true);
	}	
	public void timer7() {

		a[0].setVisible(false);
		a[1].setVisible(false);
		a[2].setVisible(false);
		a[3].setVisible(false);
		a[4].setVisible(false);
		a[5].setVisible(false);
	}
	

}

