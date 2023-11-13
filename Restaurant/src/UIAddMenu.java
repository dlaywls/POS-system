import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UIAddMenu extends JPanel {
	private JTextField price;
	private JTextField name;
	
	public UIAddMenu(Restaurant res) {
		setLayout(null);
		
		JLabel lblNewLabel = new JLabel("가격:");
		lblNewLabel.setFont(new Font("굴림", Font.PLAIN, 30));
		lblNewLabel.setBounds(196, 170, 74, 76);
		add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("이름:");
		lblNewLabel_1.setFont(new Font("굴림", Font.PLAIN, 30));
		lblNewLabel_1.setBounds(196, 285, 74, 76);
		add(lblNewLabel_1);
		
		//가격 정보 담는 텍스트 필드
		price = new JTextField();
		price.setColumns(10);
		price.setBounds(282, 194, 503, 39);
		add(price);
		
		//이름 정보 담는 텍스트 필드
		name = new JTextField();
		name.setColumns(10);
		name.setBounds(282, 299, 503, 39);
		add(name);
		
		//메뉴 추가 버튼
		JButton btnNewButton = new JButton("메뉴 추가");
		btnNewButton.setFont(new Font("굴림", Font.PLAIN, 30));
		btnNewButton.setBounds(199, 437, 624, 54);
		btnNewButton.addActionListener( new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	
            	try {
            		String menuName=name.getText();   //텍스트 필드에서 텍스트 받아오기
                	String priceTemp = price.getText();  //텍스트 필드에서 텍스트 받아오기
                    int menuPrice = Integer.parseInt(priceTemp);  //가격 텍스트 필드를 int 타입으로 바꾸기
					res.addMenu(menuName, menuPrice);
					UIMenuManage menuManage = new UIMenuManage(res);
	            	showNext(menuManage);  //메뉴 관리 패널로 이동
				
				}catch(NumberFormatException e1) {     //가격 텍스트 필드에 텍스트가 들어왔을 때 예외처리
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
	        		errorFrame.getContentPane().add(errorPanel);
	        		errorFrame.setVisible(true);
	        		UIAddMenu addMenu = new UIAddMenu(res);
	            	showNext(addMenu);
	            	
				} catch (Exception e1) {     //이미 있는 메뉴를 추가하려고 할때 예외 처리
					JFrame errorFrame = new JFrame("errorFrame");
					errorFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
					errorFrame.setSize(400, 300);

	                JPanel errorPanel = new JPanel();
	                errorPanel.setLayout(null);
	                JLabel lblNewLabel = new JLabel("이미 있는 메뉴입니다.");
	                lblNewLabel.setBounds(70, 90, 256, 28);
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
	        		errorFrame.getContentPane().add(errorPanel);
	        		errorFrame.setVisible(true);
	        		UIAddMenu addMenu = new UIAddMenu(res);
	            	showNext(addMenu);
				}
            	
            }
        });
		add(btnNewButton);
		
		//메뉴관리로 가는 버튼
		JButton btnNewButton_1 = new JButton("< 메뉴 관리 ");
		btnNewButton_1.setFont(new Font("돋움", Font.PLAIN, 16));
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				UIMenuManage menuManage = new UIMenuManage(res);
            	showNext(menuManage);
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
