import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class UIPaymentConfirm extends JFrame {
	
	JPanel contentPane;

	
	public UIPaymentConfirm(int total) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		System.out.print(total);
		JLabel lblNewLabel = new JLabel("<html>"+total+"원이<br> 결제되었습니다.</html>");
		lblNewLabel.setFont(new Font("굴림", Font.PLAIN, 24));
		lblNewLabel.setBounds(99, 82, 262, 65);
		contentPane.add(lblNewLabel);
		
		//확인 버튼
		JButton btnNewButton = new JButton("확인");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// '확인' 버튼을 눌렀을 때 현재 프레임을 닫음
		        dispose();
			}
		});
		btnNewButton.setBounds(292, 191, 91, 23);
		contentPane.add(btnNewButton);
	}

}
