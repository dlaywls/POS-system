import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class UIProgramExit extends JPanel {

	/**
	 * Create the panel.
	 */
	public UIProgramExit() {
		setLayout(null);
		
		JLabel lblNewLabel_1 = new JLabel("프로그램을 종료합니다.");
		lblNewLabel_1.setBounds(102, 109, 256, 28);
		lblNewLabel_1.setFont(new Font("굴림", Font.PLAIN, 24));
		add(lblNewLabel_1);
		
		JButton btnNewButton = new JButton("확인");
		btnNewButton.setBounds(280, 200, 91, 23);
		btnNewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	System.exit(0);
            }
        });
		add(btnNewButton);

	}

}
