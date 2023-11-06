import javax.swing.JPanel;
import javax.swing.JLabel;

import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.JButton;
import javax.swing.JFrame;

public class UIAddMenu extends JPanel {
	private JTextField price;
	private JTextField name;
	private Restaurant res;

	/**
	 * Create the panel.
	 */
	public UIAddMenu(Restaurant res) {
		this.res=res;
		setLayout(null);
		
		JLabel lblNewLabel = new JLabel("가격:");
		lblNewLabel.setFont(new Font("굴림", Font.PLAIN, 30));
		lblNewLabel.setBounds(196, 170, 74, 76);
		add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("이름:");
		lblNewLabel_1.setFont(new Font("굴림", Font.PLAIN, 30));
		lblNewLabel_1.setBounds(196, 285, 74, 76);
		add(lblNewLabel_1);
		
		price = new JTextField();
		price.setColumns(10);
		price.setBounds(282, 194, 503, 39);
		add(price);
		
		name = new JTextField();
		name.setColumns(10);
		name.setBounds(282, 299, 503, 39);
		add(name);
		
		JButton btnNewButton = new JButton("메뉴 추가");
		btnNewButton.setFont(new Font("굴림", Font.PLAIN, 30));
		btnNewButton.setBounds(199, 437, 624, 54);
		btnNewButton.addActionListener( new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	
            	try {
            		String menuName=name.getText();
                	String priceTemp = price.getText();
                    int menuPrice = Integer.parseInt(priceTemp);
					res.addMenu(menuName, menuPrice);
					UIMenuManage menuManage = new UIMenuManage(res);
	            	showNext(menuManage);
				
				}catch(NumberFormatException e1) {
					JFrame errorFrame = new JFrame("errorFrame");
					errorFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
					errorFrame.setSize(400, 200);

	                JPanel errorPanel = new JPanel();
	                JLabel lblNewLabel = new JLabel("<html>잘못 입력했습니다.<br> 다시 입력해주세요</html>");
	        		lblNewLabel.setFont(new Font("굴림", Font.PLAIN, 24));
	        		lblNewLabel.setBounds(92, 95, 272, 36);
	        		errorPanel.add(lblNewLabel);

	        		errorFrame.add(errorPanel);

	        		errorFrame.setVisible(true);
	        		UIAddMenu addMenu = new UIAddMenu(res);
	            	showNext(addMenu);
				} catch (Exception e1) {
					JFrame errorFrame = new JFrame("errorFrame");
					errorFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
					errorFrame.setSize(400, 200);

	                JPanel errorPanel = new JPanel();
	                JLabel lblNewLabel = new JLabel("이미 있는 메뉴입니다.");
	        		lblNewLabel.setFont(new Font("굴림", Font.PLAIN, 24));
	        		lblNewLabel.setBounds(92, 95, 272, 36);
	        		errorPanel.add(lblNewLabel);

	        		errorFrame.add(errorPanel);

	        		errorFrame.setVisible(true);
	        		UIAddMenu addMenu = new UIAddMenu(res);
	            	showNext(addMenu);
				}
            	
            }
        });
		add(btnNewButton);

	}
	
	private void showNext(Component ob) {
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
        frame.getContentPane().remove(this);
        frame.getContentPane().add(ob);
        frame.revalidate();
        frame.repaint();
    }
}
