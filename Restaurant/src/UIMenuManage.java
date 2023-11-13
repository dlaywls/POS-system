import javax.swing.*;


import java.awt.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;



public class UIMenuManage extends JPanel  {

	String deleteMenuName; //삭제할 메뉴의 이름
	private JButton lastClickedButton; //마지막으로 누른 버튼 
	
	/**
	 * Create the panel.
	 */
	public UIMenuManage(Restaurant res) {
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		
		JScrollPane scrollPane = new JScrollPane();
		add(scrollPane);
		
		JPanel panel_2 = new JPanel();

        scrollPane.setViewportView(panel_2);
        panel_2.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.anchor = GridBagConstraints.CENTER;

        int menuSize=res.getMenuLast();
        ArrayList<Menu>menus=res.getMenus();
        
        //메뉴의 개수만큼 반복해서 각 메뉴의 이름이 들어간 버튼 만들기
        for (int i=0; i < menuSize; i++) {
        	
            JButton button = new JButton(menus.get(i).menuName);
            button.setPreferredSize(new Dimension(180, 130));
            button.setFont(new Font("굴림", Font.PLAIN, 20));
            button.setBackground(Color.white);
            
            panel_2.add(button, gbc);
            
            final int menuIndex=i;
            
            //버튼 눌렀을 때
            button.addActionListener(new ActionListener() {
            	
                @Override
                public void actionPerformed(ActionEvent e) {
                	if(lastClickedButton!=null) {  //마지막으로 누른 버튼이 있을 경우 마지막으로 누른 버튼의 색을 원래 색으로 바꿈
                		lastClickedButton.setBackground(Color.white); 
                	}
                	lastClickedButton=button; 
                	button.setBackground(new Color(0x8ECA97));  //버튼 색 바꾸기
                    deleteMenuName=menus.get(menuIndex).menuName;  //삭제할 메뉴의 이름 가져오기
                }
            });

            gbc.gridx++;
            if (gbc.gridx > 3) {
                gbc.gridx = 0;
                gbc.gridy++;
            }
            
        }

		
		JPanel panel_1 = new JPanel();
		add(panel_1);
		panel_1.setLayout(new GridLayout(0, 1, 5, 5));
		
		//'메뉴 추가' 버튼
		JButton btnNewButton_1 = new JButton("메뉴 추가");
		btnNewButton_1.setFont(new Font("굴림", Font.PLAIN, 20));
		panel_1.add(btnNewButton_1);
		btnNewButton_1.addActionListener( new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	UIAddMenu addMenu=new UIAddMenu(res);
            	showNext(addMenu);  //메뉴 추가 패널로 이동
            }
        });
		
		//'메뉴 삭제'버튼
		JButton btnNewButton_3 = new JButton("메뉴 삭제");
		btnNewButton_3.setFont(new Font("굴림", Font.PLAIN, 20));
		panel_1.add(btnNewButton_3);
		btnNewButton_3.addActionListener( new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	try {
					res.deleteMenu(deleteMenuName); //삭제할 메뉴의 이름으로 메뉴 삭제
					UIMenuManage menuManager=new UIMenuManage(res); 
                    showNext(menuManager);  //다시 메뉴 관리 화면 로드
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
            }
        });
		
		//정보 저장 버튼
		JButton btnNewButton_2 = new JButton("정보 저장");
		btnNewButton_2.setFont(new Font("굴림", Font.PLAIN, 20));
		panel_1.add(btnNewButton_2);
		btnNewButton_2.addActionListener( new ActionListener() {
			@Override
            public void actionPerformed(ActionEvent e) {
              
				FileOutputStream fStream=null;
				try {
					fStream=new FileOutputStream("restaurant.dat");
					ObjectOutputStream  out=new ObjectOutputStream (fStream); //파일 오픈
					res.saveDataSerialize(out);// 데이터 저장
					out.close();
					UISaveData saveData=new UISaveData();
					saveData.setVisible(true); //데이터 저장 확인 프레임 보여줌
				}catch(Exception e1) {
					e1.printStackTrace();
				}
            }
        });
		
		//관리자 모드 화면 버튼
		JButton btnNewButton = new JButton("관리자 모드 화면");
		btnNewButton.setFont(new Font("굴림", Font.PLAIN, 20));
		panel_1.add(btnNewButton);
		btnNewButton.addActionListener( new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	UIManager manager=new UIManager(res);
            	showNext(manager); //관리자 모드 패널로 이동
            }
        });
	}
	
	//다음 화면으로 이동하는 함수
	private void showNext(Component ob) {
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
        frame.getContentPane().remove(this);
        frame.getContentPane().add(ob);
        frame.revalidate();
        frame.repaint();
    }
}
