import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

public class UISaveDataConfirm extends JFrame {

	private JPanel contentPane;

	/**
	 * Create the frame.
	 */
	public UISaveDataConfirm(Restaurant res) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("정보를 저장하시겠습니까?");
		lblNewLabel.setFont(new Font("굴림", Font.PLAIN, 24));
		lblNewLabel.setBounds(70, 101, 305, 36);
		contentPane.add(lblNewLabel);
		
		//아니오 버튼
		JButton btnNewButton = new JButton("아니오");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// '확인' 버튼을 눌렀을 때 현재 프레임을 닫음
		        dispose();
			}
		});
		btnNewButton.setBounds(244, 189, 91, 23);
		contentPane.add(btnNewButton);
		
		//"예" 버튼
		JButton btnNewButton1 = new JButton("예");
		btnNewButton1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				FileOutputStream fStream=null;
				try {
					fStream=new FileOutputStream("restaurant.dat");
					ObjectOutputStream  out=new ObjectOutputStream (fStream); //파일 오픈
					res.saveDataSerialize(out);// 데이터 저장
					out.close();
					UISaveData saveData=new UISaveData();
					saveData.setVisible(true);   //데이터 저장 확인 프레임 보여줌
				}catch(Exception e1) {
					e1.printStackTrace();
				}
            
                
		        dispose();
			}
		});
		btnNewButton1.setBounds(100, 189, 91, 23);
		contentPane.add(btnNewButton1);
		
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
