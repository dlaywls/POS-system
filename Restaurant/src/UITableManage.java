import javax.swing.JPanel;
import java.awt.CardLayout;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.BoxLayout;

public class UITableManage extends JPanel {
	private Restaurant res;
	String deleteTableName;
	/**
	 * Create the panel.
	 */
	public UITableManage(Restaurant res) {
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

        int tableSize=res.getTableLast();
        ArrayList<Table>tables=res.getTables();
        
        for (int i=0; i < tableSize; i++) {
        	
            JButton button = new JButton(tables.get(i).getTableName());
            button.setPreferredSize(new Dimension(180, 140));
            button.setFont(new Font("굴림", Font.PLAIN, 20));
            panel_2.add(button, gbc);
            
            final int menuIndex=i;
            
            button.addActionListener(new ActionListener() {
            	
                @Override
                public void actionPerformed(ActionEvent e) {
                    
                	deleteTableName=tables.get(menuIndex).getTableName();
                    
                }
            });

            gbc.gridx++;
            if (gbc.gridx > 3) {
                gbc.gridx = 0;
                gbc.gridy++;
            }
            
        }
		
		
		

		JPanel panel = new JPanel();
		add(panel);
		panel.setLayout(new GridLayout(0, 1, 5, 5));
		
		JButton btnNewButton_1 = new JButton("테이블 추가");
		btnNewButton_1.setFont(new Font("굴림", Font.PLAIN, 20));
		btnNewButton_1.addActionListener( new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	UIAddTable addTable=new UIAddTable(res);
            	showNext(addTable);
            }
        });
		panel.add(btnNewButton_1);
		
		JButton btnNewButton_3 = new JButton("테이블 삭제");
		btnNewButton_3.setFont(new Font("굴림", Font.PLAIN, 20));
		btnNewButton_3.addActionListener( new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	try {
					res.deleteTable(deleteTableName);
					UITableManage tableManage=new UITableManage(res);
                    showNext(tableManage);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
            }
        });
		panel.add(btnNewButton_3);
		
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
				}catch(Exception e1) {
					e1.printStackTrace();
				}
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
            }
        });
		panel.add(btnNewButton_2);
		
		JButton btnNewButton = new JButton("관리자 모드 화면");
		btnNewButton.setFont(new Font("굴림", Font.PLAIN, 20));
		btnNewButton.addActionListener( new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	UIManager manager=new UIManager(res);
            	showNext(manager);
            }
        });
		panel.add(btnNewButton);

	}
	
	private void showNext(Component ob) {
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
        frame.getContentPane().remove(this);
        frame.getContentPane().add(ob);
        frame.revalidate();
        frame.repaint();
    }

}
