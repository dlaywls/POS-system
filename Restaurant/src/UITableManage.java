import javax.swing.JPanel;
import java.awt.CardLayout;
import java.awt.Color;
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
	String deleteTableName; //삭제할 테이블의 이름
	private JButton lastClickedButton;  //마지막으로 누른 버튼
	/**
	 * Create the panel.
	 */
	
	public UITableManage(Restaurant res) {
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
        
        //테이블의 개수만큼 반복해서 각 테이블의 이름과 수용가능 인원이 들어간 버튼 만들기
        for (int i=0; i < tableSize; i++) {
        	
            JButton button = new JButton("<html> <font size='5'>"+tables.get(i).getTableName()+"<br>"
            		+ "<font size='3'>수용 가능 인원:"+ tables.get(i).getMember()+"</p></html>");
            button.setPreferredSize(new Dimension(180, 140));
            button.setFont(new Font("굴림", Font.PLAIN,15));
            button.setBackground(Color.white);
            panel_2.add(button, gbc);
            
            final int tableIndex=i;
            
            //버튼 눌렀을 때
            button.addActionListener(new ActionListener() {
            	
                @Override
                public void actionPerformed(ActionEvent e) {
                	if(lastClickedButton!=null) {  //마지막으로 누른 버튼이 있을 경우 
                		lastClickedButton.setBackground(Color.white); 
                	}
                	lastClickedButton=button;
                	button.setBackground(new Color(0x8ECA97));  //버튼의 색 바꾸기
                	deleteTableName=tables.get(tableIndex).getTableName();  //삭제한 테이블의 이름 받아오기
                    
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
		
		//테이블 추가 버튼
		JButton btnNewButton_1 = new JButton("테이블 추가");
		btnNewButton_1.setFont(new Font("굴림", Font.PLAIN, 20));
		panel.add(btnNewButton_1);
		btnNewButton_1.addActionListener( new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	UIAddTable addTable=new UIAddTable(res);
            	showNext(addTable);
            }
        });
		
		//테이블 삭제 버튼
		JButton btnNewButton_3 = new JButton("테이블 삭제");
		btnNewButton_3.setFont(new Font("굴림", Font.PLAIN, 20));
		panel.add(btnNewButton_3);
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
		
		//정보 저장 버튼
		JButton btnNewButton_2 = new JButton("정보 저장");
		btnNewButton_2.setFont(new Font("굴림", Font.PLAIN, 20));
		panel.add(btnNewButton_2);
		btnNewButton_2.addActionListener( new ActionListener() {
			@Override
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
            }
        });
		
		//관리자 모드 화면 버튼
		JButton btnNewButton = new JButton("관리자 모드 화면");
		btnNewButton.setFont(new Font("굴림", Font.PLAIN, 20));
		btnNewButton.addActionListener( new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	UIManager manager=new UIManager(res);
            	showNext(manager);  //관리자 모드 패널로 이동
            }
        });
		panel.add(btnNewButton);

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
