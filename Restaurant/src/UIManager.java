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

public class UIManager extends JPanel implements Serializable{
	private Restaurant res;

	/**
	 * Create the panel.
	 */
	public UIManager(Restaurant res) {
		this.res = res;
		setLayout(null);
		
		JButton btnNewButton = new JButton("<html>메뉴<br> 관리</html>");
		btnNewButton.setFont(new Font("굴림", Font.PLAIN, 24));
		btnNewButton.setBounds(132, 144, 158, 326);
		btnNewButton.addActionListener( new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	UIMenuManage menuManage = new UIMenuManage(res);
            	showNext(menuManage);
            }
        });
		add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("<html>테이블 <br> \t\t 관리</html> ");
		btnNewButton_1.setFont(new Font("굴림", Font.PLAIN, 24));
		btnNewButton_1.setBounds(413, 144, 158, 326);
		btnNewButton_1.addActionListener( new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	UITableManage tableManege = new UITableManage(res);
            	showNext(tableManege);
            }
        });
		add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("첫 화면");
		btnNewButton_2.setFont(new Font("굴림", Font.PLAIN, 24));
		btnNewButton_2.setBounds(703, 144, 158, 326);
		btnNewButton_2.addActionListener( new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	UIHome home = new UIHome();
            	showNext(home);
            }
        });
		add(btnNewButton_2);

	}
	
	
	
	private void showNext(Component ob) {
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
        frame.getContentPane().remove(this);
        frame.getContentPane().add(ob);
        frame.revalidate();
        frame.repaint();
    }
}
