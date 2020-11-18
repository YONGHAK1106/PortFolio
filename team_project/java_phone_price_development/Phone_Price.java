package telecom;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class teleom {
   public static void main(String[] args) {
      JFrame frame = new JFrame("�����"); // = frame.setTitle("�����");
      PhonePanel panel = new PhonePanel(); // �Ʒ� JPanel�� ��ӹ��� PhonePanel�� ���� ��ü ����

      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // close��ư�� ���� �׼�
      frame.getContentPane().add(panel, BorderLayout.SOUTH); 
      // �����ӿ� ����� ����Ʈ���� �˾Ƴ�, add�г��� �����̳ʿ� ��������, BorderLayout ��ġ������ 
      frame.pack(); // �����츦 ���÷����Ѵ�
      frame.setVisible(true); //ȭ�鿡 �������� �Լ�
   }
}
		////////////////////////////////////////////////////////////
class Orders extends JFrame { // JFrame �ڹ� ���ø����̼��� ���� �� ����ϴ� ������ Java.awt ��Ű������ ���ǵȴ�
	private JPanel contentPane; // contentPane �����̳� ��ü, Ʋ�� ����� ���� ����
      	public static void showUI(JCheckBox[] cb,JCheckBox[] cb1,JCheckBox[] cb2,
      		JCheckBox[] cb3,JCheckBox[] cb4) {
         
      		EventQueue.invokeLater(new Runnable() { // ���ο� �����尡 ����� �������̽��� ������ �� �ֵ���
      			public void run(){                  // EventQueue�� ���
      				try{  // ���ܻ�Ȳ ó��
      					Orders frame = new Orders(cb,cb1,cb2,cb3,cb4);
      					frame.setVisible(true); // setVisible(true) ȭ�鿡 �������� �Լ� ȣ��
      				} 
      				catch (Exception e){
      					e.printStackTrace(); // ���� �޼����� �߻� �ٿ����� ã�� �ܰ躰�� ���� ���
      				}
      			}
      		});
      	}
      	////////////////////////////////////////////////////////////
      	public Orders(JCheckBox[] cb,JCheckBox[] cb1,
      			JCheckBox[] cb2, JCheckBox[] cb3, JCheckBox[] cb4) {
         
      		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE); // ���� �� close�� ���� �׼�
      		setBounds(100, 100, 950, 300); // ���� �� â ũ��
      		contentPane = new JPanel(); // contentPane ��ü ����
      		contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));  // ��, ����, �Ʒ�, ������ , ���� �� â ��������
         	contentPane.setLayout(new BorderLayout(0, 0)); // ��, �Ʒ� ,����Ʈ�ҿ� BorderLayout ��ġ������ ����
         	setContentPane(contentPane);
  
         	StringBuffer sb = new StringBuffer(); // �ڹ� ���α׷� ������ ���ϴ� ���ڿ��� �ٷ� �� ����Ѵ�.
         	// �� �� ������ �Ŀ��� ����Ͽ� �����ϰ� �ִ� ���ڿ��� ������ ������ �� �ִ�. �׷��Ƿ�, 
         	// StringBuffer Ŭ������ �޼ҵ�� ���ڿ� ó�� ���� ����� ������ StringBuffer
         	// ��ü�� �ݿ��ϰ�, �޼ҵ� ���� Ÿ���� void �̴�.
         	for (int i = 0; i < cb.length; i++) // append : JCheckBox[] �����͸� ���� ���ڿ� ���� �߰�
         		if (cb[i].isSelected()){
         			sb.append("��Ż� : " + cb[i].getText() + " // ");
         		}
         	for (int i = 0; i < cb1.length; i++)
         		if (cb1[i].isSelected()){
         			sb.append("���� : " + cb1[i].getText() +" // ");
         		}
         	for (int i = 0; i < cb2.length; i++)
         		if (cb2[i].isSelected()){
            	  sb.append("�Һ� : " + cb2[i].getText() +" // ");    // �ֹ������� ���� ǥ�� !!
         		}
         	for (int i = 0; i < cb3.length; i++)
         		if (cb3[i].isSelected()){
         			sb.append("����� : " + cb3[i].getText() + " // ");
         		}
         	sb.append("�ΰ����� : ");
         	for (int i = 0; i < cb4.length; i++)
         		if (cb4[i].isSelected()){
         			sb.append(cb4[i].getText() + ", ");
         		}
         	sb.append(" // ");
         	//////////////////////////////
         	int selected = 1; // ��� ����� ���� selected=1 ����
         	int sum=0,sum1=0,sum2=0; // ��� ����� ���� sum ����
         	
         	if(cb[0].isSelected()){ // ��Ż� ������ ���� ����
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
         	if(cb2[0].isSelected()) // �Һ�
         		sum=sum/12;
         	else if(cb2[1].isSelected())
         		sum=sum/24;
         	else if(cb2[2].isSelected())
         		sum=sum/36;
         	else if(cb2[3].isSelected())
         		sum=sum/1;
         	//////////////////////////////
         	if(cb3[0].isSelected()) // �����
         		sum1=sum+35000;
         	else if(cb3[1].isSelected())
         		sum1=sum+45000;
         	else if(cb3[2].isSelected())
         		sum1=sum+55000;
         	else if(cb3[3].isSelected())
         		sum1=sum+65000;
         	//////////////////////////////
         	if(cb4[0].isSelected()){  // �ΰ�����(�ߺ����� üũ���� �ϰ� ����if�� �ۼ�)
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
         	sb.append("�� ���� �ݾ� : " + sum2 + "��"); // �Ѱ��� �ݾ��� sum2�� �־� ���ڿ����� �߰�
         	
         	
         	////////////////////////////////////////////////////////////
         	JLabel lblNewLabel = new JLabel(sb.toString()); // JLabel�� ���� ��Ʈ�������� �޴� ��ü ����
  
         	lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER); // ������Ʈ ����, �߾ӿ� ��ġ
         	contentPane.add(lblNewLabel, BorderLayout.CENTER);
  
         	////////////////////////////////////////////////////////////
         	JPanel panel = new JPanel(); // JPanel�� ���� ��ü ����
         	contentPane.add(panel, BorderLayout.SOUTH); // �Ʒ��ʿ� ��ġ

         	JButton btnNewButton = new JButton("��"); // ���� �� �� ������ â ������ ���
         	btnNewButton.addActionListener(new ActionListener() {
         	// ActionListener�� �������̽� �̴�. ActionListener�� ���� �� actionListener�� ����������Ѵ�.
         		public void actionPerformed(ActionEvent e) {
         			setVisible(false); // ȭ���� ���ִ� ����
         		}
         	});
         	panel.add(btnNewButton); // btnNewButton��ü�� panel�� �߰����شٴ� ��
         	
         	////////////////////////////////////////////////////////////
         	JButton btnNewButton_1 = new JButton("�ƴϿ�"); // ���� �� �ƴϿ� ������ â ������ ���
         	btnNewButton_1.addActionListener(new ActionListener() {
         	// ActionListener�� �������̽� �̴�.
         		public void actionPerformed(ActionEvent e) {
         			setVisible(false); // ȭ���� ���ִ� ����
         		}
         	});
         	panel.add(btnNewButton_1); // btnNewButton_1��ü�� panel�� �߰����شٴ� ��
         	
         	////////////////////////////////////////////////////////////
         	JLabel lblNewLabel_1 = new JLabel("������ ������ ������ �����ϴ�."); // ���� �� ���� �� ����
         	contentPane.add(lblNewLabel_1, BorderLayout.NORTH); // ���ʿ� ��ġ
      }      
}

///////////////////////////////////////////////////////////////////////////////////////////////////////////
class PhonePanel extends JPanel {
	private JButton button = new JButton("����"); // ��ư ��ü ����
	private JButton button1 = new JButton("�ݱ�"); // ��ư ��ü ����
	private JCheckBox[] checkBox, checkBox1, checkBox2, checkBox3, checkBox4; // üũ�ڽ� ��ü ����
	private JLabel label0 = new JLabel("����� �������!!"); // JLabel�� ���� ��ü ����
	private JLabel label = new JLabel("������ ��"); // JLabel�� ���� ��ü ����
   	private JLabel label1 = new JLabel("������ ��"); // JLabel�� ���� ��ü ����
   	private JLabel label2 = new JLabel("������ ��"); // JLabel�� ���� ��ü ����
   	private JLabel label3 = new JLabel("��Ż�"); // JLabel�� ���� ��ü ����
   	private JLabel label4 = new JLabel("����"); // JLabel�� ���� ��ü ����
   	private JLabel label5 = new JLabel("�Һΰ�������"); // JLabel�� ���� ��ü ����
   	private JLabel label6 = new JLabel("���������"); // JLabel�� ���� ��ü ����
   	private JLabel label7 = new JLabel("�ΰ�����(�ߺ�����)"); // JLabel�� ���� ��ü ����
   	private JLabel label8 = new JLabel("������ ��"); // JLabel�� ���� ��ü ����
   	private JLabel label9 = new JLabel("������ ��"); // JLabel�� ���� ��ü ����
   	private JTextField textField = new JTextField(10); // JTextField�� ���� ��ü ����, (10)�� ������ ���ڰ� �� ������ â�� ���̸� �����Ѵٴ� ��
   	private JTextField textField1 = new JTextField(10); // JTextField�� ���� ��ü ����
   	private JTextField textField2 = new JTextField(10); // JTextField�� ���� ��ü ����
   	private JTextField textField3 = new JTextField(10); // JTextField�� ���� ��ü ����
   	private JTextField textField4 = new JTextField(10); // JTextField�� ���� ��ü ����
   	private String[] PhoneText = { "SK", "KT", "LG", "��Ÿ" }; // String�迭
    	private String[] PhoneText1 = { "iPhone", "Galaxy", "G5", "�ؼ���" }; // String�迭
    	private String[] PhoneText2 = { "12����", "24����", "36����", "�Ͻú�" }; // String�迭
    	private String[] PhoneText3 = { "35", "45", "55", "������" }; // String�迭
    	private String[] PhoneText4 = { "���ڹ�����", "���� ��Ʈ����", "��ȭ������", "������ +5G" }; // String�迭
    	public PhonePanel() {
    	checkBox = new JCheckBox[PhoneText.length]; // JCheckBox�迭�� ����� checkBox��ü ����, ���̴� PhoneText�� �迭 ���� ��
    	checkBox1 = new JCheckBox[PhoneText1.length]; // JCheckBox�迭�� ����� checkBox1��ü ����, ���̴� PhoneText�� �迭 ���� ��
    	checkBox2 = new JCheckBox[PhoneText2.length]; // JCheckBox�迭�� ����� checkBox2��ü ����, ���̴� PhoneText�� �迭 ���� ��
    	checkBox3 = new JCheckBox[PhoneText3.length]; // JCheckBox�迭�� ����� checkBox3��ü ����, ���̴� PhoneText�� �迭 ���� ��
    	checkBox4 = new JCheckBox[PhoneText4.length]; // JCheckBox�迭�� ����� checkBox4��ü ����, ���̴� PhoneText�� �迭 ���� ��

    	PhoneListener listener = new PhoneListener(); // PhoneListener�� ���� ��ü ����

    	for (int i = 0; i < checkBox.length; i++) { // checkBox.length �� 4�̴�.
    		checkBox[i] = new JCheckBox(PhoneText[i]); // JCheckBox�� ���� ��ü ����
    		checkBox1[i] = new JCheckBox(PhoneText1[i]); // JCheckBox�� ���� ��ü ����
    		checkBox2[i] = new JCheckBox(PhoneText2[i]); // JCheckBox�� ���� ��ü ����
    		checkBox3[i] = new JCheckBox(PhoneText3[i]); // JCheckBox�� ���� ��ü ����
         	checkBox4[i] = new JCheckBox(PhoneText4[i]); // JCheckBox�� ���� ��ü ����
         	// JCheckBox�� ��������� ���̰� �ִ�. �����ڸ� ���ؼ� üũ�ڽ��� ǥ�õ� ���ڿ� ������ �����Ѵ�. 
         	setLayout(null); // for�� ���� �� ���� ��ġ�� �ʱ�ȭ
         	checkBox[i].setBounds(50, 100 + 50 * i, 80, 30); // ��ġũ�⼳��(������ġ,������ġ,���ΰ���,���ΰ���)
         	checkBox1[i].setBounds(200, 100 + 50 * i, 80, 30); // ��ġũ�⼳��(������ġ,������ġ,���ΰ���,���ΰ���)
         	checkBox2[i].setBounds(350, 100 + 50 * i, 80, 30); // ��ġũ�⼳��(������ġ,������ġ,���ΰ���,���ΰ���)
         	checkBox3[i].setBounds(500, 100 + 50 * i, 80, 30); // ��ġũ�⼳��(������ġ,������ġ,���ΰ���,���ΰ���)
         	checkBox4[i].setBounds(650, 100 + 50 * i, 110, 30); // ��ġũ�⼳��(������ġ,������ġ,���ΰ���,���ΰ���)
         	add(checkBox[i]); // �׸��� �߰����ִ� �Լ�
         	add(checkBox1[i]); // �׸��� �߰����ִ� �Լ�
         	add(checkBox2[i]); // �׸��� �߰����ִ� �Լ�
         	add(checkBox3[i]); // �׸��� �߰����ִ� �Լ�
         	add(checkBox4[i]); // �׸��� �߰����ִ� �Լ�
         	checkBox[i].addActionListener(listener); // checkBox[i]�� Action ������ ���
         	checkBox1[i].addActionListener(listener); // checkBox1[i]�� Action ������ ���
         	checkBox2[i].addActionListener(listener); // checkBox2[i]�� Action ������ ���
         	checkBox3[i].addActionListener(listener); // checkBox3[i]�� Action ������ ���
         	checkBox4[i].addActionListener(listener); // checkBox4[i]�� Action ������ ���
         	button.addActionListener(listener); // button�� Action ������ ���
         	button1.addActionListener(listener); // button1�� Action ������ ���
        	// ActionListener �� ���ϴ� �ؽ�Ʈ�ʵ忡 �ٿ��ִ� ���� addActionListener�̴�.
    	} 

    	button.setBounds(300, 380, 60, 30); // ���ù�ư ũ�� ��ġ ����(������ġ,������ġ,���ΰ���,���ΰ���)
    	button.addActionListener(new ActionListener() { //// ActionListener �������̽� 
   
    		public void actionPerformed(ActionEvent e) {
    			Orders.showUI(checkBox,checkBox1,checkBox2,checkBox3,checkBox4);
    		} // OrdersŬ������ showUI�� ���� üũ�ڽ� �迭�� �ѱ��.
    	});
    	button1.setBounds(380, 380, 60, 30); // �ݱ��ư ũ�� ��ġ ����(������ġ,������ġ,���ΰ���,���ΰ���)
    	button1.addActionListener(new ActionListener() { 

    		public void actionPerformed(ActionEvent e) {
    			System.exit(0); // �ݱ� ��ư�� ������ â�� ������ ���� ����
    		}	
    	});
  
    	label0.setBounds(340, 10, 200, 80); // ��ġũ�⼳��(������ġ,������ġ,���ΰ���,���ΰ���)
        label.setBounds(50, 270, 80, 80); // ��ġũ�⼳��(������ġ,������ġ,���ΰ���,���ΰ���)
        label1.setBounds(200, 270, 80, 80); // ��ġũ�⼳��(������ġ,������ġ,���ΰ���,���ΰ���)
        label2.setBounds(350, 270, 80, 80); // ��ġũ�⼳��(������ġ,������ġ,���ΰ���,���ΰ���)
        label3.setBounds(50, 45, 80, 80); // ��ġũ�⼳��(������ġ,������ġ,���ΰ���,���ΰ���)
        label4.setBounds(200, 45, 80, 80); // ��ġũ�⼳��(������ġ,������ġ,���ΰ���,���ΰ���)
        label5.setBounds(350, 45, 80, 80); // ��ġũ�⼳��(������ġ,������ġ,���ΰ���,���ΰ���)
        label6.setBounds(500, 45, 80, 80); // ��ġũ�⼳��(������ġ,������ġ,���ΰ���,���ΰ���)
        label7.setBounds(650, 45, 130, 80); // ��ġũ�⼳��(������ġ,������ġ,���ΰ���,���ΰ���)
        label8.setBounds(500, 270, 80, 80); // ��ġũ�⼳��(������ġ,������ġ,���ΰ���,���ΰ���)
        label9.setBounds(650, 270, 80, 80); // ��ġũ�⼳��(������ġ,������ġ,���ΰ���,���ΰ���)
 
        textField.setBounds(50, 330, 100, 30); // ��ġũ�⼳��(������ġ,������ġ,���ΰ���,���ΰ���)
        textField1.setBounds(200, 330, 100, 30); // ��ġũ�⼳��(������ġ,������ġ,���ΰ���,���ΰ���)
        textField2.setBounds(350, 330, 100, 30); // ��ġũ�⼳��(������ġ,������ġ,���ΰ���,���ΰ���)
        textField3.setBounds(500, 330, 100, 30); // ��ġũ�⼳��(������ġ,������ġ,���ΰ���,���ΰ���)
        textField4.setBounds(650, 330, 100, 30); // ��ġũ�⼳��(������ġ,������ġ,���ΰ���,���ΰ���)
 
        add(button); // �׸��� �߰����ִ� �Լ�
        add(button1); // �׸��� �߰����ִ� �Լ�
  
        add(label0); // �׸��� �߰����ִ� �Լ�
        add(label); // �׸��� �߰����ִ� �Լ�
        add(label1); // �׸��� �߰����ִ� �Լ�
        add(label2); // �׸��� �߰����ִ� �Լ�
        add(label3); // �׸��� �߰����ִ� �Լ�
        add(label4); // �׸��� �߰����ִ� �Լ�
        add(label5); // �׸��� �߰����ִ� �Լ�
        add(label6); // �׸��� �߰����ִ� �Լ�
        add(label7); // �׸��� �߰����ִ� �Լ�
        add(label8); // �׸��� �߰����ִ� �Լ�
        add(label9); // �׸��� �߰����ִ� �Լ�
  
        add(textField); // �׸��� �߰����ִ� �Լ�
        add(textField1); // �׸��� �߰����ִ� �Լ�
        add(textField2); // �׸��� �߰����ִ� �Լ�
        add(textField3); // �׸��� �߰����ִ� �Լ�
        add(textField4); // �׸��� �߰����ִ� �Լ�

        setPreferredSize(new Dimension(800, 500)); // â ũ�⼳��(���ΰ���,���ΰ���)
        setBackground(Color.white); // ��������

}

    private class PhoneListener implements ActionListener { // ActionListener�� ��ӹ޴� PhoneListenerŬ����
    	public void actionPerformed(ActionEvent event) { // ActionListener�� ��ӹ޾� actionPerformed�Լ� ����
    		StringBuffer Phone = new StringBuffer(); // StringBuffer�� ���� ��ü ����
    		StringBuffer Phone1 = new StringBuffer(); // StringBuffer�� ���� ��ü ����
    		StringBuffer Phone2 = new StringBuffer(); // StringBuffer�� ���� ��ü ����
    		StringBuffer Phone3 = new StringBuffer(); // StringBuffer�� ���� ��ü ����
    		StringBuffer Phone4 = new StringBuffer(); // StringBuffer�� ���� ��ü ����
    		
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
    		
    		textField.setText(Phone.toString()); // textField���� Phone�� append�� ���� ����
            	textField1.setText(Phone1.toString()); // textField1���� Phone1�� append�� ���� ����
            	textField2.setText(Phone2.toString()); // textField2���� Phone2�� append�� ���� ����
            	textField3.setText(Phone3.toString()); // textField3���� Phone3�� append�� ���� ����
            	textField4.setText(Phone4.toString()); // textField4���� Phone4�� append�� ���� ����
    	}
    }    
}