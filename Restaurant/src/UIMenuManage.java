import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import javax.swing.JScrollBar;

public class UIMenuManage extends JPanel  {

	private Restaurant res;
	String deleteMenuName;
	
	/**
	 * Create the panel.
	 */
	public UIMenuManage(Restaurant res) {
		this.res=res;
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
        
        for (int i=0; i < menuSize; i++) {
        	
            JButton button = new JButton(menus.get(i).menuName);
            button.setPreferredSize(new Dimension(180, 130));
            button.setFont(new Font("굴림", Font.PLAIN, 20));
            panel_2.add(button, gbc);
            
            final int menuIndex=i;
            
            button.addActionListener(new ActionListener() {
            	
                @Override
                public void actionPerformed(ActionEvent e) {
                    
                    deleteMenuName=menus.get(menuIndex).menuName;
                    
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
		
		JButton btnNewButton_1 = new JButton("메뉴 추가");
		btnNewButton_1.setFont(new Font("굴림", Font.PLAIN, 20));
		btnNewButton_1.addActionListener( new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	UIAddMenu addMenu=new UIAddMenu(res);
            	showNext(addMenu);
            }
        });
		panel_1.add(btnNewButton_1);
		
		JButton btnNewButton_3 = new JButton("메뉴 삭제");
		btnNewButton_3.setFont(new Font("굴림", Font.PLAIN, 20));
		btnNewButton_3.addActionListener( new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	try {
					res.deleteMenu(deleteMenuName);
					UIMenuManage menuManager=new UIMenuManage(res);
                    showNext(menuManager);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
            }
        });
		panel_1.add(btnNewButton_3);
		
		JButton btnNewButton_2 = new JButton("정보 저장");
		btnNewButton_2.setFont(new Font("굴림", Font.PLAIN, 20));
		btnNewButton_2.addActionListener( new ActionListener() {
			@Override
            public void actionPerformed(ActionEvent e) {
              
				FileOutputStream fStream=null;
				try {
					fStream=new FileOutputStream("restaurant.dat");
					ObjectOutputStream  out=new ObjectOutputStream (fStream); //파일 오픈
					res.saveDataSerialize(out);// 데이터 저장
					out.close();
					System.out.println("저장되었습니다");
					JFrame saveDataFrame = new JFrame("saveData");
	                saveDataFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	                saveDataFrame.setSize(400, 200);

	                JPanel saveDataPanel = new JPanel();
	                JLabel lblNewLabel = new JLabel("정보가 저장되었습니다.");
	        		lblNewLabel.setFont(new Font("굴림", Font.PLAIN, 24));
	        		lblNewLabel.setBounds(92, 95, 272, 36);
	        		saveDataPanel.add(lblNewLabel);

	                saveDataFrame.add(saveDataPanel);

	                saveDataFrame.setVisible(true);
				}catch(Exception e1) {
					e1.printStackTrace();
				}
                
            }
        });
		panel_1.add(btnNewButton_2);
		
		JButton btnNewButton = new JButton("관리자 모드 화면");
		btnNewButton.setFont(new Font("굴림", Font.PLAIN, 20));
		btnNewButton.addActionListener( new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	UIManager manager=new UIManager(res);
            	showNext(manager);
            }
        });
		panel_1.add(btnNewButton);

	}
	private void showNext(Component ob) {
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
        frame.getContentPane().remove(this);
        frame.getContentPane().add(ob);
        frame.revalidate();
        frame.repaint();
    }
}
