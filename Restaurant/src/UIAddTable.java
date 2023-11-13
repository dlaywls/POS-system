import javax.swing.JPanel;
import javax.swing.JLabel;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;

import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.ActionEvent;

public class UIAddTable extends JPanel {
	private JTextField name;  //테이블 이름 텍스트 필드
	private JTextField member;  //테이블 수용가능 인원 텍스트 필드
	private boolean Availability;  //테이블 이용 가능 여부

	public UIAddTable(Restaurant res) {
		setLayout(null);
		
		JLabel lblNewLabel = new JLabel("이름: ");
		lblNewLabel.setFont(new Font("굴림", Font.PLAIN, 30));
		lblNewLabel.setBounds(211, 185, 81, 72);
		add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("수용 가능 인원:");
		lblNewLabel_1.setFont(new Font("굴림", Font.PLAIN, 30));
		lblNewLabel_1.setBounds(79, 279, 238, 72);
		add(lblNewLabel_1);
		
		JLabel lblNewLabel_1_1 = new JLabel("이용 가능 여부:");
		lblNewLabel_1_1.setFont(new Font("굴림", Font.PLAIN, 30));
		lblNewLabel_1_1.setBounds(80, 359, 212, 72);
		add(lblNewLabel_1_1);
		
		//이름 담는 텍스틑 필드
		name = new JTextField();
		name.setColumns(10);
		name.setBounds(316, 206, 564, 42);
		add(name);
		
		//수용 가능 인원 담는 텍스트 필드
		member = new JTextField();
		member.setColumns(10);
		member.setBounds(316, 288, 564, 42);
		add(member);
		
		//이용가능 여부 '가능' 라디오 버튼
		JRadioButton available = new JRadioButton("가능");
		available.setFont(new Font("굴림", Font.PLAIN, 30));
		available.setBounds(414, 377, 113, 36);
		add(available);
		
		//이용 가능 여부 '불가능' 라디오 버튼
		JRadioButton unavailable = new JRadioButton("불가능");
		unavailable.setFont(new Font("굴림", Font.PLAIN, 30));
		unavailable.setBounds(643, 377, 138, 36);
		add(unavailable);
		
		//라디오 버튼 그룹으로 하기
		ButtonGroup group=new ButtonGroup();
        group.add(available);
        group.add(unavailable);

        //가능이 체크됐을 때 
        available.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                	Availability=true;   //이용 가능 여부 true
                }
            }
        });

        unavailable.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                	Availability=false;    //이용 가능 여부 false
                }
            }
        });
		
        //테이블 추가 버튼
		JButton btnNewButton = new JButton("테이블 추가");
		btnNewButton.setFont(new Font("굴림", Font.PLAIN, 24));
		btnNewButton.setBounds(79, 487, 801, 49);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
					String tableName=name.getText();   //텍스트 필드에서 텍스트 받아오기
					String memberTemp=member.getText();   //텍스트 필드에서 텍스트 받아오기
					int tableMember=Integer.parseInt(memberTemp);  //수용 가능 인원 int타입으로 바꾸기
					res.addTable(tableName, tableMember,Availability);
					UITableManage tableManage=new UITableManage(res);
					showNext(tableManage);   //테이블 관리 패널로 이동
					
				}catch(NumberFormatException e1) {   //수용 가능 인원에 텍스트를 입력했을 경우
					JFrame errorFrame = new JFrame("errorFrame");
					errorFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
					errorFrame.setSize(400, 300);

					JPanel errorPanel = new JPanel();
	                errorPanel.setLayout(null);
	                JLabel lblNewLabel = new JLabel("<html>잘못 입력했습니다.<br> <tr> 다시 입력해주세요</html>");
	                lblNewLabel.setBounds(70, 60, 256, 60);
	        		lblNewLabel.setFont(new Font("굴림", Font.PLAIN, 24));
	        		
	        		JButton btnNewButton = new JButton("확인");
	        		btnNewButton.setBounds(260, 200, 91, 23);
	        		btnNewButton.addActionListener(new ActionListener() {
	        			public void actionPerformed(ActionEvent e) {
	        		        // '확인' 버튼을 눌렀을 때 현재 프레임을 닫고, UIAddMenu를 표시
	        		        errorFrame.dispose();
	        		        
	        		    }
	                });
	        		errorPanel.add(lblNewLabel);
	        		errorPanel.add(btnNewButton);
	        		errorFrame.add(errorPanel);
	        		errorFrame.setVisible(true);
	        		UIAddTable addTable = new UIAddTable(res);
	            	showNext(addTable);
	            	
				}  catch (Exception e1) {  //이미 있는 테이블을 추가하려는 경우 예외 처리
					JFrame errorFrame = new JFrame("errorFrame");
					errorFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
					errorFrame.setSize(400, 300);

	                JPanel errorPanel = new JPanel();
	                errorPanel.setLayout(null);
	                JLabel lblNewLabel = new JLabel("이미 있는 테이블입니다.");
	                lblNewLabel.setBounds(50, 90, 270, 28);
	                lblNewLabel.setFont(new Font("굴림", Font.PLAIN, 24));
	        		
	        		
	        		JButton btnNewButton = new JButton("확인");
	        		btnNewButton.setBounds(260, 200, 91, 23);
	        		btnNewButton.addActionListener(new ActionListener() {
	        			public void actionPerformed(ActionEvent e) {
	        		        // '확인' 버튼을 눌렀을 때 현재 프레임을 닫고, UIAddMenu를 표시
	        		        errorFrame.dispose();
	        		    }
	                });
	        		errorPanel.add(lblNewLabel);
	                errorPanel.add(btnNewButton);
	        		errorFrame.add(errorPanel);
	        		errorFrame.setVisible(true);
	        		UIAddTable addTable = new UIAddTable(res);
	            	showNext(addTable);
				}
				
			}
		});
		add(btnNewButton);
		
		//메뉴관리로 가는 버튼
		JButton btnNewButton_1 = new JButton("< 테이블 관리 ");
		btnNewButton_1.setFont(new Font("돋움", Font.PLAIN, 16));
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				UITableManage tableManage=new UITableManage(res);
				showNext(tableManage);
			}
		});
		btnNewButton_1.setBounds(50, 50, 151, 47);
		btnNewButton_1.setBackground(Color.white);
		add(btnNewButton_1);
		

	}
	
	//다음 패널로 이동하는 함수
	private void showNext(Component ob) {
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
        frame.getContentPane().remove(this);
        frame.getContentPane().add(ob);
        frame.revalidate();
        frame.repaint();
    }
}
