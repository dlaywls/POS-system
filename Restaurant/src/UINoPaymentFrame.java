import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class UINoPaymentFrame extends JFrame {

	private JPanel contentPane;

	
	/**
	 * Create the frame.
	 */
	public UINoPaymentFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("결제가 안 되었습니다.");
		lblNewLabel.setFont(new Font("굴림", Font.PLAIN, 24));
		lblNewLabel.setBounds(87, 96, 262, 36);
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
