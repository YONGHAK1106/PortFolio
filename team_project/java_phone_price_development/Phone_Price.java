package telecom;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class teleom {
   public static void main(String[] args) {
      JFrame frame = new JFrame("요금제"); // = frame.setTitle("요금제");
      PhonePanel panel = new PhonePanel(); // 아래 JPanel에 상속받은 PhonePanel에 대한 객체 생성

      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // close버튼에 따른 액션
      frame.getContentPane().add(panel, BorderLayout.SOUTH); 
      // 프레임에 연결된 컨텐트팬을 알아냄, add패널을 컨테이너에 각각상입, BorderLayout 배치관리자 
      frame.pack(); // 윈도우를 디스플레이한다
      frame.setVisible(true); //화면에 보여지는 함수
   }
}
		////////////////////////////////////////////////////////////
class Orders extends JFrame { // JFrame 자바 어플리케이션을 만들 때 사용하는 것으로 Java.awt 패키지에서 정의된다
	private JPanel contentPane; // contentPane 컨테이너 객체, 틀을 만드는 기초 공사
      	public static void showUI(JCheckBox[] cb,JCheckBox[] cb1,JCheckBox[] cb2,
      		JCheckBox[] cb3,JCheckBox[] cb4) {
         
      		EventQueue.invokeLater(new Runnable() { // 새로운 쓰레드가 사용자 인터페이스에 접촉할 수 있도록
      			public void run(){                  // EventQueue를 사용
      				try{  // 예외상황 처리
      					Orders frame = new Orders(cb,cb1,cb2,cb3,cb4);
      					frame.setVisible(true); // setVisible(true) 화면에 보여지는 함수 호출
      				} 
      				catch (Exception e){
      					e.printStackTrace(); // 에러 메세지의 발생 근원지를 찾아 단계별로 에러 출력
      				}
      			}
      		});
      	}
      	////////////////////////////////////////////////////////////
      	public Orders(JCheckBox[] cb,JCheckBox[] cb1,
      			JCheckBox[] cb2, JCheckBox[] cb3, JCheckBox[] cb4) {
         
      		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE); // 선택 후 close에 따른 액션
      		setBounds(100, 100, 950, 300); // 선택 후 창 크기
      		contentPane = new JPanel(); // contentPane 객체 선언
      		contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));  // 위, 왼쪽, 아래, 오른쪽 , 선택 후 창 간격조절
         	contentPane.setLayout(new BorderLayout(0, 0)); // 위, 아래 ,컨텐트팬에 BorderLayout 배치관리자 설정
         	setContentPane(contentPane);
  
         	StringBuffer sb = new StringBuffer(); // 자바 프로그램 내에서 변하는 문자열을 다룰 때 사용한다.
         	// 한 번 생성된 후에도 계속하여 저장하고 있는 문자열의 내용을 변경할 수 있다. 그러므로, 
         	// StringBuffer 클래스의 메소드는 문자열 처리 후의 결과를 원래의 StringBuffer
         	// 객체에 반영하고, 메소드 리턴 타입은 void 이다.
         	for (int i = 0; i < cb.length; i++) // append : JCheckBox[] 데이터를 현재 문자열 끝에 추가
         		if (cb[i].isSelected()){
         			sb.append("통신사 : " + cb[i].getText() + " // ");
         		}
         	for (int i = 0; i < cb1.length; i++)
         		if (cb1[i].isSelected()){
         			sb.append("기종 : " + cb1[i].getText() +" // ");
         		}
         	for (int i = 0; i < cb2.length; i++)
         		if (cb2[i].isSelected()){
            	  sb.append("할부 : " + cb2[i].getText() +" // ");    // 주문내역에 고른거 표시 !!
         		}
         	for (int i = 0; i < cb3.length; i++)
         		if (cb3[i].isSelected()){
         			sb.append("요금제 : " + cb3[i].getText() + " // ");
         		}
         	sb.append("부가서비스 : ");
         	for (int i = 0; i < cb4.length; i++)
         		if (cb4[i].isSelected()){
         			sb.append(cb4[i].getText() + ", ");
         		}
         	sb.append(" // ");
         	//////////////////////////////
         	int selected = 1; // 요금 계산을 위한 selected=1 선언
         	int sum=0,sum1=0,sum2=0; // 요금 계산을 위한 sum 선언
         	
         	if(cb[0].isSelected()){ // 통신사 선택후 기종 선택
         		if(cb1[0].isSelected())
         			sum=sum+selected*800000;
         		else if(cb1[1].isSelected())
         			sum=sum+selected*750000;
         		else if(cb1[2].isSelected())
         			sum=sum+selected*700000;
         		else if(cb1[3].isSelected())
         			sum=sum+selected*650000;
         	}
         	else if(cb[1].isSelected()){
         		if(cb1[0].isSelected())
         			sum=sum+selected*850000;
         		else if(cb1[1].isSelected())
         			sum=sum+selected*80000;
         		else if(cb1[2].isSelected())
         			sum=sum+selected*650000;
         		else if(cb1[3].isSelected())
         			sum=sum+selected*600000;
         	}
         	else if(cb[2].isSelected()){
         		if(cb1[0].isSelected())
         			sum=sum+selected*750000;
         		else if(cb1[1].isSelected())
         			sum=sum+selected*800000;
         		else if(cb1[2].isSelected())
         			sum=sum+selected*700000;
         		else if(cb1[3].isSelected())
         			sum=sum+selected*650000;
         	}
            	//////////////////////////////
         	if(cb2[0].isSelected()) // 할부
         		sum=sum/12;
         	else if(cb2[1].isSelected())
         		sum=sum/24;
         	else if(cb2[2].isSelected())
         		sum=sum/36;
         	else if(cb2[3].isSelected())
         		sum=sum/1;
         	//////////////////////////////
         	if(cb3[0].isSelected()) // 요금제
         		sum1=sum+35000;
         	else if(cb3[1].isSelected())
         		sum1=sum+45000;
         	else if(cb3[2].isSelected())
         		sum1=sum+55000;
         	else if(cb3[3].isSelected())
         		sum1=sum+65000;
         	//////////////////////////////
         	if(cb4[0].isSelected()){  // 부가서비스(중복까지 체크가능 하게 다중if문 작성)
         		sum2=sum1+selected*2000;
         		if(cb4[1].isSelected()){
         			sum2=sum2+3000;
         			if(cb4[2].isSelected()){
         				sum2=sum2+5000;
         				if(cb4[3].isSelected())
         					sum2=sum2+6000;
         			}
         			else if(cb4[3].isSelected())
         				sum2=sum2+6000;
         		}
         		else if(cb4[2].isSelected()){
         			sum2=sum2+5000;
         			if(cb4[3].isSelected())
         				sum2=sum2+6000;
         		}
         		else if(cb4[3].isSelected())
         			sum2=sum2+6000;
         	} //
         	else if(cb4[1].isSelected()){
         		sum2=sum1+selected*3000;
         		if(cb4[2].isSelected()){
         			sum2=sum2+5000;
         			if(cb4[3].isSelected())
             			sum2=sum2+6000;
         		}
         		else if(cb4[3].isSelected())
         			sum2=sum2+6000;
         	} //
         	else if(cb4[2].isSelected()){
         		sum2=sum1+selected*5000;
         		if(cb4[3].isSelected())
         			sum2=sum2+6000;
         	} //
         	else if(cb4[3].isSelected()){
         		sum2=sum1+selected*6000;
         	} //
         	sb.append("총 계산된 금액 : " + sum2 + "원"); // 총계산된 금액을 sum2에 넣어 문자열끝에 추가
         	
         	
         	////////////////////////////////////////////////////////////
         	JLabel lblNewLabel = new JLabel(sb.toString()); // JLabel에 대한 스트링값으로 받는 객체 생성
  
         	lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER); // 컴포넌트 정렬, 중앙에 배치
         	contentPane.add(lblNewLabel, BorderLayout.CENTER);
  
         	////////////////////////////////////////////////////////////
         	JPanel panel = new JPanel(); // JPanel에 대한 객체 생성
         	contentPane.add(panel, BorderLayout.SOUTH); // 아래쪽에 배치

         	JButton btnNewButton = new JButton("예"); // 선택 후 예 누르면 창 꺼지는 기능
         	btnNewButton.addActionListener(new ActionListener() {
         	// ActionListener는 인터페이스 이다. ActionListener를 쓰면 꼭 actionListener를 구현해줘야한다.
         		public void actionPerformed(ActionEvent e) {
         			setVisible(false); // 화면을 꺼주는 역할
         		}
         	});
         	panel.add(btnNewButton); // btnNewButton객체를 panel에 추가해준다는 뜻
         	
         	////////////////////////////////////////////////////////////
         	JButton btnNewButton_1 = new JButton("아니오"); // 선택 후 아니오 누르면 창 꺼지는 기능
         	btnNewButton_1.addActionListener(new ActionListener() {
         	// ActionListener는 인터페이스 이다.
         		public void actionPerformed(ActionEvent e) {
         			setVisible(false); // 화면을 꺼주는 역할
         		}
         	});
         	panel.add(btnNewButton_1); // btnNewButton_1객체를 panel에 추가해준다는 뜻
         	
         	////////////////////////////////////////////////////////////
         	JLabel lblNewLabel_1 = new JLabel("선택한 내용은 다음과 같습니다."); // 선택 후 북쪽 라벨 문구
         	contentPane.add(lblNewLabel_1, BorderLayout.NORTH); // 위쪽에 배치
      }      
}

///////////////////////////////////////////////////////////////////////////////////////////////////////////
class PhonePanel extends JPanel {
	private JButton button = new JButton("선택"); // 버튼 객체 생성
	private JButton button1 = new JButton("닫기"); // 버튼 객체 생성
	private JCheckBox[] checkBox, checkBox1, checkBox2, checkBox3, checkBox4; // 체크박스 객체 선언
	private JLabel label0 = new JLabel("요금제 계산하자!!"); // JLabel에 대한 객체 생성
	private JLabel label = new JLabel("선택한 것"); // JLabel에 대한 객체 생성
   	private JLabel label1 = new JLabel("선택한 것"); // JLabel에 대한 객체 생성
   	private JLabel label2 = new JLabel("선택한 것"); // JLabel에 대한 객체 생성
   	private JLabel label3 = new JLabel("통신사"); // JLabel에 대한 객체 생성
   	private JLabel label4 = new JLabel("기종"); // JLabel에 대한 객체 생성
   	private JLabel label5 = new JLabel("할부개월선택"); // JLabel에 대한 객체 생성
   	private JLabel label6 = new JLabel("요금제선택"); // JLabel에 대한 객체 생성
   	private JLabel label7 = new JLabel("부가서비스(중복가능)"); // JLabel에 대한 객체 생성
   	private JLabel label8 = new JLabel("선택한 것"); // JLabel에 대한 객체 생성
   	private JLabel label9 = new JLabel("선택한 것"); // JLabel에 대한 객체 생성
   	private JTextField textField = new JTextField(10); // JTextField에 대한 객체 생성, (10)은 열개의 글자가 들어갈 정도의 창의 길이를 성정한다는 뜻
   	private JTextField textField1 = new JTextField(10); // JTextField에 대한 객체 생성
   	private JTextField textField2 = new JTextField(10); // JTextField에 대한 객체 생성
   	private JTextField textField3 = new JTextField(10); // JTextField에 대한 객체 생성
   	private JTextField textField4 = new JTextField(10); // JTextField에 대한 객체 생성
   	private String[] PhoneText = { "SK", "KT", "LG", "기타" }; // String배열
    	private String[] PhoneText1 = { "iPhone", "Galaxy", "G5", "넥서스" }; // String배열
    	private String[] PhoneText2 = { "12개월", "24개월", "36개월", "일시불" }; // String배열
    	private String[] PhoneText3 = { "35", "45", "55", "무제한" }; // String배열
    	private String[] PhoneText4 = { "문자무제한", "음악 스트리밍", "통화무제한", "데이터 +5G" }; // String배열
    	public PhonePanel() {
    	checkBox = new JCheckBox[PhoneText.length]; // JCheckBox배열로 선언된 checkBox객체 생성, 길이는 PhoneText의 배열 길이 값
    	checkBox1 = new JCheckBox[PhoneText1.length]; // JCheckBox배열로 선언된 checkBox1객체 생성, 길이는 PhoneText의 배열 길이 값
    	checkBox2 = new JCheckBox[PhoneText2.length]; // JCheckBox배열로 선언된 checkBox2객체 생성, 길이는 PhoneText의 배열 길이 값
    	checkBox3 = new JCheckBox[PhoneText3.length]; // JCheckBox배열로 선언된 checkBox3객체 생성, 길이는 PhoneText의 배열 길이 값
    	checkBox4 = new JCheckBox[PhoneText4.length]; // JCheckBox배열로 선언된 checkBox4객체 생성, 길이는 PhoneText의 배열 길이 값

    	PhoneListener listener = new PhoneListener(); // PhoneListener에 대한 객체 생성

    	for (int i = 0; i < checkBox.length; i++) { // checkBox.length 는 4이다.
    		checkBox[i] = new JCheckBox(PhoneText[i]); // JCheckBox에 대한 객체 생성
    		checkBox1[i] = new JCheckBox(PhoneText1[i]); // JCheckBox에 대한 객체 생성
    		checkBox2[i] = new JCheckBox(PhoneText2[i]); // JCheckBox에 대한 객체 생성
    		checkBox3[i] = new JCheckBox(PhoneText3[i]); // JCheckBox에 대한 객체 생성
         	checkBox4[i] = new JCheckBox(PhoneText4[i]); // JCheckBox에 대한 객체 생성
         	// JCheckBox의 생성방법을 보이고 있다. 생성자를 통해서 체크박스에 표시될 문자열 정보를 전달한다. 
         	setLayout(null); // for문 돌릴 때 마다 위치값 초기화
         	checkBox[i].setBounds(50, 100 + 50 * i, 80, 30); // 위치크기설정(가로위치,세로위치,가로간격,세로간격)
         	checkBox1[i].setBounds(200, 100 + 50 * i, 80, 30); // 위치크기설정(가로위치,세로위치,가로간격,세로간격)
         	checkBox2[i].setBounds(350, 100 + 50 * i, 80, 30); // 위치크기설정(가로위치,세로위치,가로간격,세로간격)
         	checkBox3[i].setBounds(500, 100 + 50 * i, 80, 30); // 위치크기설정(가로위치,세로위치,가로간격,세로간격)
         	checkBox4[i].setBounds(650, 100 + 50 * i, 110, 30); // 위치크기설정(가로위치,세로위치,가로간격,세로간격)
         	add(checkBox[i]); // 항목을 추가해주는 함수
         	add(checkBox1[i]); // 항목을 추가해주는 함수
         	add(checkBox2[i]); // 항목을 추가해주는 함수
         	add(checkBox3[i]); // 항목을 추가해주는 함수
         	add(checkBox4[i]); // 항목을 추가해주는 함수
         	checkBox[i].addActionListener(listener); // checkBox[i]에 Action 리스너 등록
         	checkBox1[i].addActionListener(listener); // checkBox1[i]에 Action 리스너 등록
         	checkBox2[i].addActionListener(listener); // checkBox2[i]에 Action 리스너 등록
         	checkBox3[i].addActionListener(listener); // checkBox3[i]에 Action 리스너 등록
         	checkBox4[i].addActionListener(listener); // checkBox4[i]에 Action 리스너 등록
         	button.addActionListener(listener); // button에 Action 리스너 등록
         	button1.addActionListener(listener); // button1에 Action 리스너 등록
        	// ActionListener 를 원하는 텍스트필드에 붙여주는 것이 addActionListener이다.
    	} 

    	button.setBounds(300, 380, 60, 30); // 선택버튼 크기 위치 설정(가로위치,세로위치,가로간격,세로간격)
    	button.addActionListener(new ActionListener() { //// ActionListener 인터페이스 
   
    		public void actionPerformed(ActionEvent e) {
    			Orders.showUI(checkBox,checkBox1,checkBox2,checkBox3,checkBox4);
    		} // Orders클래스에 showUI를 통해 체크박스 배열을 넘긴다.
    	});
    	button1.setBounds(380, 380, 60, 30); // 닫기버튼 크기 위치 설정(가로위치,세로위치,가로간격,세로간격)
    	button1.addActionListener(new ActionListener() { 

    		public void actionPerformed(ActionEvent e) {
    			System.exit(0); // 닫기 버튼을 누르면 창이 꺼지는 역할 수행
    		}	
    	});
  
    	label0.setBounds(340, 10, 200, 80); // 위치크기설정(가로위치,세로위치,가로간격,세로간격)
        label.setBounds(50, 270, 80, 80); // 위치크기설정(가로위치,세로위치,가로간격,세로간격)
        label1.setBounds(200, 270, 80, 80); // 위치크기설정(가로위치,세로위치,가로간격,세로간격)
        label2.setBounds(350, 270, 80, 80); // 위치크기설정(가로위치,세로위치,가로간격,세로간격)
        label3.setBounds(50, 45, 80, 80); // 위치크기설정(가로위치,세로위치,가로간격,세로간격)
        label4.setBounds(200, 45, 80, 80); // 위치크기설정(가로위치,세로위치,가로간격,세로간격)
        label5.setBounds(350, 45, 80, 80); // 위치크기설정(가로위치,세로위치,가로간격,세로간격)
        label6.setBounds(500, 45, 80, 80); // 위치크기설정(가로위치,세로위치,가로간격,세로간격)
        label7.setBounds(650, 45, 130, 80); // 위치크기설정(가로위치,세로위치,가로간격,세로간격)
        label8.setBounds(500, 270, 80, 80); // 위치크기설정(가로위치,세로위치,가로간격,세로간격)
        label9.setBounds(650, 270, 80, 80); // 위치크기설정(가로위치,세로위치,가로간격,세로간격)
 
        textField.setBounds(50, 330, 100, 30); // 위치크기설정(가로위치,세로위치,가로간격,세로간격)
        textField1.setBounds(200, 330, 100, 30); // 위치크기설정(가로위치,세로위치,가로간격,세로간격)
        textField2.setBounds(350, 330, 100, 30); // 위치크기설정(가로위치,세로위치,가로간격,세로간격)
        textField3.setBounds(500, 330, 100, 30); // 위치크기설정(가로위치,세로위치,가로간격,세로간격)
        textField4.setBounds(650, 330, 100, 30); // 위치크기설정(가로위치,세로위치,가로간격,세로간격)
 
        add(button); // 항목을 추가해주는 함수
        add(button1); // 항목을 추가해주는 함수
  
        add(label0); // 항목을 추가해주는 함수
        add(label); // 항목을 추가해주는 함수
        add(label1); // 항목을 추가해주는 함수
        add(label2); // 항목을 추가해주는 함수
        add(label3); // 항목을 추가해주는 함수
        add(label4); // 항목을 추가해주는 함수
        add(label5); // 항목을 추가해주는 함수
        add(label6); // 항목을 추가해주는 함수
        add(label7); // 항목을 추가해주는 함수
        add(label8); // 항목을 추가해주는 함수
        add(label9); // 항목을 추가해주는 함수
  
        add(textField); // 항목을 추가해주는 함수
        add(textField1); // 항목을 추가해주는 함수
        add(textField2); // 항목을 추가해주는 함수
        add(textField3); // 항목을 추가해주는 함수
        add(textField4); // 항목을 추가해주는 함수

        setPreferredSize(new Dimension(800, 500)); // 창 크기설정(가로간격,세로간격)
        setBackground(Color.white); // 배경색설정

}

    private class PhoneListener implements ActionListener { // ActionListener에 상속받는 PhoneListener클래스
    	public void actionPerformed(ActionEvent event) { // ActionListener에 상속받아 actionPerformed함수 구현
    		StringBuffer Phone = new StringBuffer(); // StringBuffer에 대한 객체 생성
    		StringBuffer Phone1 = new StringBuffer(); // StringBuffer에 대한 객체 생성
    		StringBuffer Phone2 = new StringBuffer(); // StringBuffer에 대한 객체 생성
    		StringBuffer Phone3 = new StringBuffer(); // StringBuffer에 대한 객체 생성
    		StringBuffer Phone4 = new StringBuffer(); // StringBuffer에 대한 객체 생성
    		
    		for (int i = 0; i < checkBox.length; i++)
    			if (checkBox[i].isSelected()){
    				Phone.append(checkBox[i].getText() + " ");
    			}
    		for (int i = 0; i < checkBox1.length; i++)
    			if (checkBox1[i].isSelected()){
    				Phone1.append(checkBox1[i].getText() + " ");
    			}
    		for (int i = 0; i < checkBox2.length; i++)
    			if (checkBox2[i].isSelected()){
    				Phone2.append(checkBox2[i].getText() + " ");
    			}   
    		for (int i = 0; i < checkBox.length; i++)
    			if (checkBox3[i].isSelected()){
    				Phone3.append(checkBox3[i].getText() + " ");
    			}
    		for (int i = 0; i < checkBox.length; i++)
    			if (checkBox4[i].isSelected()){
    				Phone4.append(checkBox4[i].getText() + " ");
    			}	         
    		
    		textField.setText(Phone.toString()); // textField란에 Phone에 append된 값을 넣음
            	textField1.setText(Phone1.toString()); // textField1란에 Phone1에 append된 값을 넣음
            	textField2.setText(Phone2.toString()); // textField2란에 Phone2에 append된 값을 넣음
            	textField3.setText(Phone3.toString()); // textField3란에 Phone3에 append된 값을 넣음
            	textField4.setText(Phone4.toString()); // textField4란에 Phone4에 append된 값을 넣음
    	}
    }    
}