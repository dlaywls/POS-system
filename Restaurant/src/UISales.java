import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.JLabel;
import java.awt.BorderLayout;

public class UISales extends JPanel {
	/**
	 * Create the panel.
	 */
	public UISales(Restaurant res) {
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
        gbc.weightx = 1.0; 
        gbc.weighty = 1.0;

        int tableSize=res.getTableLast();
        ArrayList<Table>tables=res.getTables();
        
        //테이블의 개수만큼 반복해서 각 테이블의 이름과 수용가능 인원이 들어간 버튼 만들기
        for (int i=0; i < tableSize; i++) {
        	String buttonText;
        	String orders=tables.get(i).printOrder();  //주문 메뉴 정보들
            final int tableIndex=i;
            boolean isAvaiable=tables.get(tableIndex).getAvailable();  //사용 가능 여부
            
            if(!isAvaiable) {   //사용불가
            	buttonText="<html> <font size='5'>"+tables.get(i).getTableName()+"<br>"
                		+ "<font size='3'>수용 가능 인원:"+ tables.get(i).getMember()+"<br>"
                				+"<font size='3'>이용 가능 여부:"+ tables.get(i).getAvailable()+"<br>"
                				+ "<font size='3'>"+ orders+"</html>";
            }else {    //사용 가능
            	buttonText="<html> <font size='5'>"+tables.get(i).getTableName()+"<br>"
                		+ "<font size='3'>수용 가능 인원:"+ tables.get(i).getMember()+"<br>"
        				+ "<font size='3'>이용 가능 여부:"+ tables.get(i).getAvailable()+"</html>";
            }
            
            JButton button=new JButton(buttonText);
            button.setPreferredSize(new Dimension(180, 250));
            button.setFont(new Font("굴림", Font.PLAIN,15));
            button.setBackground(Color.white);
            
            if(!isAvaiable) {   //사용 불가능하다면
            	button.setBackground(new Color(0x8ECA97));  //버튼의 색 바꾸기
            }
            panel_2.add(button, gbc);
            
            //버튼 눌렀을 때
            button.addActionListener(new ActionListener() {
            	
                @Override
                public void actionPerformed(ActionEvent e) {
                	  
                	if(isAvaiable) {  
                		res.inTable(tableIndex);  //테이블 지정
                		button.setBackground(new Color(0x8ECA97));  //버튼의 색 바꾸기
                		UISales sales =new UISales(res);  //업데이트된 데이터 보여주기
                		showNext(sales);
                		
                	}
                	
                	if(!isAvaiable) {  //테이블 주문하기
                		UIMenuOrder menuOrder =new UIMenuOrder(res,tableIndex);
                		showNext(menuOrder);
                	}
                	
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
		panel.setLayout(new GridLayout(0, 1, 50, 50));
		
		JPanel panel_1 = new JPanel();
		panel.add(panel_1);
		
		int amount=res.amount;
		panel_1.setLayout(new BorderLayout(0, 0));
		JLabel lblNewLabel = new JLabel("매출: "+amount);
		lblNewLabel.setFont(new Font("굴림", Font.PLAIN, 24));
		panel_1.add(lblNewLabel);
		
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
		
		// 첫 화면 버튼
		JButton btnNewButton = new JButton("첫 화면");
		btnNewButton.setFont(new Font("굴림", Font.PLAIN, 20));
		panel.add(btnNewButton);
		btnNewButton.addActionListener( new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	UISaveDataConfirm saveDataConfirm=new UISaveDataConfirm(res);
            	saveDataConfirm.setVisible(true);
            	UIHome home = new UIHome();
            	showNext(home);
            }
        });
		
		
		

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
