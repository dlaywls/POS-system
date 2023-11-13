import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JFrame;

import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;
import javax.swing.JLabel;

public class UIManager extends JPanel implements Serializable{
	
	public UIManager(Restaurant res) {
		setLayout(null);
		
		//'메뉴 관리' 버튼
		JButton btnNewButton = new JButton("<html>메뉴<br> 관리</html>");
		btnNewButton.setFont(new Font("굴림", Font.PLAIN, 24));
		btnNewButton.setBounds(132, 144, 158, 326);
		add(btnNewButton);
		btnNewButton.addActionListener( new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	UIMenuManage menuManage = new UIMenuManage(res);
            	showNext(menuManage); //메뉴 관리 패널로 이동
            }
        });
		
		//'테이블 관리' 버튼
		JButton btnNewButton_1 = new JButton("<html>테이블 <br> \t\t 관리</html> ");
		btnNewButton_1.setFont(new Font("굴림", Font.PLAIN, 24));
		btnNewButton_1.setBounds(413, 144, 158, 326);
		add(btnNewButton_1);
		btnNewButton_1.addActionListener( new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	UITableManage tableManege = new UITableManage(res);
            	showNext(tableManege); //테이블 관리 패널로 이동
            }
        });
		
		//'첫 화면'버튼
		JButton btnNewButton_2 = new JButton("첫 화면");
		btnNewButton_2.setFont(new Font("굴림", Font.PLAIN, 24));
		btnNewButton_2.setBounds(703, 144, 158, 326);
		add(btnNewButton_2);
		
		JLabel lblNewLabel = new JLabel("관리자 모드");
		lblNewLabel.setFont(new Font("굴림", Font.PLAIN, 24));
		lblNewLabel.setBounds(425, 58, 128, 44);
		add(lblNewLabel);
		btnNewButton_2.addActionListener( new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	UIHome home = new UIHome();
            	showNext(home); //첫 화면 패널로 이동
            }
        });
	}
	
	
	
	private void showNext(Component ob) {
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
        frame.getContentPane().remove(this);
        frame.getContentPane().add(ob);
        frame.revalidate();
        frame.repaint();
    }
}
