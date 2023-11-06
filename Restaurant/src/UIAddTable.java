import javax.swing.JPanel;
import javax.swing.JLabel;

import java.awt.Component;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;

import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.ActionEvent;

public class UIAddTable extends JPanel {
	private JTextField name;
	private JTextField member;
	private Restaurant res;
	private boolean Availability;

	/**
	 * Create the panel.
	 */
	public UIAddTable(Restaurant res) {
		this.res=res;
		setLayout(null);
		
		JLabel lblNewLabel = new JLabel("이름: ");
		lblNewLabel.setFont(new Font("굴림", Font.PLAIN, 30));
		lblNewLabel.setBounds(211, 185, 81, 72);
		add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("수용 가능 인원:");
		lblNewLabel_1.setFont(new Font("굴림", Font.PLAIN, 30));
		lblNewLabel_1.setBounds(79, 279, 238, 72);
		add(lblNewLabel_1);
		
		JLabel lblNewLabel_1_1 = new JLabel("이용 가능 여부:");
		lblNewLabel_1_1.setFont(new Font("굴림", Font.PLAIN, 30));
		lblNewLabel_1_1.setBounds(80, 359, 212, 72);
		add(lblNewLabel_1_1);
		
		name = new JTextField();
		name.setColumns(10);
		name.setBounds(316, 206, 564, 42);
		add(name);
		
		member = new JTextField();
		member.setColumns(10);
		member.setBounds(316, 288, 564, 42);
		add(member);
		
		JRadioButton available = new JRadioButton("가능");
		available.setFont(new Font("굴림", Font.PLAIN, 30));
		available.setBounds(414, 377, 113, 36);
		add(available);
		
		JRadioButton unavailable = new JRadioButton("불가능");
		unavailable.setFont(new Font("굴림", Font.PLAIN, 30));
		unavailable.setBounds(643, 377, 138, 36);
		add(unavailable);
		
		ButtonGroup group=new ButtonGroup();
        group.add(available);
        group.add(unavailable);

        available.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                	Availability=true;
                }
            }
        });

        unavailable.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                	Availability=false;
                }
            }
        });
		
		JButton btnNewButton = new JButton("테이블 추가");
		btnNewButton.setFont(new Font("굴림", Font.PLAIN, 24));
		btnNewButton.setBounds(79, 487, 801, 49);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
					String tableName=name.getText();
					String memberTemp=member.getText();
					int tableMember=Integer.parseInt(memberTemp);
					res.addTable(tableName, tableMember,Availability);
					UITableManage tableManage=new UITableManage(res);
					showNext(tableManage);
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
	        		UIAddTable addTable = new UIAddTable(res);
	            	showNext(addTable);
				}  catch (Exception e1) {
					JFrame errorFrame = new JFrame("errorFrame");
					errorFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
					errorFrame.setSize(400, 200);

	                JPanel errorPanel = new JPanel();
	                JLabel lblNewLabel = new JLabel("이미 있는 테이블입니다.");
	        		lblNewLabel.setFont(new Font("굴림", Font.PLAIN, 24));
	        		lblNewLabel.setBounds(92, 95, 272, 36);
	        		errorPanel.add(lblNewLabel);

	        		errorFrame.add(errorPanel);

	        		errorFrame.setVisible(true);
	        		UIAddTable addTable = new UIAddTable(res);
	            	showNext(addTable);
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
