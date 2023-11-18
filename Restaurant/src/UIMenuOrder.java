import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.JTable;

public class UIMenuOrder extends JPanel {
	
	private DefaultTableModel tableModel;
	private JTable orderTable;
	private JButton lastClickedButton; //마지막으로 누른 버튼 
	private int orderMenuN;   //주문한 메뉴의 번호
	private int orderMenuCount;   //주문한 메뉴의 개수
	/**
	 * Create the panel.
	 */
	public UIMenuOrder(Restaurant res , int tableN) {
		
		ArrayList<Table>tables=res.getTables();
		Table selectedTable=tables.get(tableN);  //현재 선택된 테이블
		ArrayList<Menu>menus=res.getMenus();

		JPanel panel = new JPanel();
		add(panel);
		panel.setLayout(null);   //패널 만들기
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		
		String[] columns = {"NO.", "이름", "수량", "가격"};   //테이블의 열
        tableModel = new DefaultTableModel(columns, 0);   
        
		orderTable = new JTable(tableModel); 
		orderTable.setDefaultRenderer(Object.class, new CustomRenderer());
		JScrollPane scrollPane = new JScrollPane(orderTable);
        scrollPane.setBounds(15, 10, 300, 310);
        panel.add(scrollPane);     //테이블 만들기
       
        //주문한 메뉴 테이블에 담기
        ArrayList<Order> orderedMenus=selectedTable.getOrders();
        for(int i=0; i<selectedTable.getOrderLast();i++) {
        	Order orderedMenu=orderedMenus.get(i);
        	Object[] rowData = {tableModel.getRowCount(), orderedMenu.menuName, orderedMenu.orderCount, orderedMenu.price*orderedMenu.orderCount};
            tableModel.addRow(rowData);
        }
        
		
		//새로 추가할 메뉴의 수량
		JLabel lblNewLabel_6_2 = new JLabel("수량:");
		lblNewLabel_6_2.setBounds(15, 422, 51, 24);
		panel.add(lblNewLabel_6_2);
		lblNewLabel_6_2.setFont(new Font("굴림", Font.PLAIN, 20));
		
		JSpinner spinner = new JSpinner(new SpinnerNumberModel(1, 1, Integer.MAX_VALUE, 1));
		spinner.setBounds(78, 420, 51, 34);
		panel.add(spinner);
		orderMenuCount = (int) spinner.getValue();
		
		spinner.addChangeListener(e -> {
			orderMenuCount = (int) spinner.getValue();  // 값 변경 시 변수에 저장
			// 수량 , 가격 값 업데이트
		    int rowCount = tableModel.getRowCount();
		    if (rowCount > 0) {
		        int columnQuantity = 2; // '수량' column index
		        int columnTotal=3;  // "가격" column index
		        int price=menus.get(orderMenuN).getPrice();
		        tableModel.setValueAt(orderMenuCount, rowCount - 1, columnQuantity);
		        tableModel.setValueAt(orderMenuCount*price, rowCount - 1, columnTotal);
		    }
           
		});
		
		
		//주문 버튼
		JButton btnNewButton_7 = new JButton("주문");
		btnNewButton_7.setBounds(151, 404, 159, 60);
		panel.add(btnNewButton_7);
		btnNewButton_7.setFont(new Font("굴림", Font.PLAIN, 20));
		btnNewButton_7.addActionListener( new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	res.menuToOrder(orderMenuN,tableN, orderMenuCount);
            	UIMenuOrder menuOrder =new UIMenuOrder(res,tableN);
        		showNext(menuOrder);
            }
        });
		
		//결제 버튼
		JButton btnNewButton_7_1_2 = new JButton("결제");
		btnNewButton_7_1_2.setBounds(15, 474, 300, 74);
		panel.add(btnNewButton_7_1_2);
		btnNewButton_7_1_2.setFont(new Font("굴림", Font.PLAIN, 20));
		btnNewButton_7_1_2.addActionListener( new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	UIPaymentConfirm frame = new UIPaymentConfirm(tables.get(tableN).getTotal());
				frame.setVisible(true);
            	res.pay(tableN);
            	
            	UISales sales=new UISales(res);
            	showNext(sales);  //'판매 모드' 패널로 이동
            }
        });
		
		//이전 화면 버튼
		JButton btnNewButton_7_1_1 = new JButton("이전 화면");
		btnNewButton_7_1_1.setFont(new Font("굴림", Font.PLAIN, 20));
		btnNewButton_7_1_1.setBounds(15, 558, 145, 33);
		panel.add(btnNewButton_7_1_1);
		btnNewButton_7_1_1.addActionListener( new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	UISales sales=new UISales(res);
            	showNext(sales);  //'판매 모드' 패널로 이동
            }
        });
		
		JLabel lblNewLabel_6_1 = new JLabel(""+selectedTable.getTotal());
		lblNewLabel_6_1.setBounds(141, 370, 178, 24);
		panel.add(lblNewLabel_6_1);
		lblNewLabel_6_1.setFont(new Font("굴림", Font.PLAIN, 20));
		
		JLabel lblNewLabel_6 = new JLabel("합계: ");
		lblNewLabel_6.setBounds(15, 370, 51, 24);
		panel.add(lblNewLabel_6);
		lblNewLabel_6.setFont(new Font("굴림", Font.PLAIN, 20));
		
		JButton btnNewButton_7_1_1_1 = new JButton("체크 아웃");
		btnNewButton_7_1_1_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(selectedTable.getOrderLast()==0) {
					selectedTable.setAvailable(true);
					UISales sales=new UISales(res);
	            	showNext(sales);  //'판매 모드' 패널로 이동
				}else {
					UINoPaymentFrame noPayment =new UINoPaymentFrame();
					noPayment.setVisible(true);
				}
			}
		});
		btnNewButton_7_1_1_1.setFont(new Font("굴림", Font.PLAIN, 20));
		btnNewButton_7_1_1_1.setBounds(170, 558, 145, 33);
		panel.add(btnNewButton_7_1_1_1);
		
		
		
		
		//메뉴 버튼 테이블
		JScrollPane scrollPane_1 = new JScrollPane();
		add(scrollPane_1);
		
		JPanel panel_2 = new JPanel();

		scrollPane_1.setViewportView(panel_2);
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

        int menuSize=res.getMenuLast();
        
        //메뉴의 개수만큼 반복해서 각 메뉴의 이름이 들어간 버튼 만들기
        for (int i=0; i < menuSize; i++) {
        	
            JButton button = new JButton("<html> <font size='5'>"+menus.get(i).menuName+"<br>"
            		+ "<font size='3'>가격:"+ menus.get(i).price+"</html>");
            button.setPreferredSize(new Dimension(110, 90));
            button.setFont(new Font("굴림", Font.PLAIN, 16));
            button.setBackground(Color.white);
            
            panel_2.add(button, gbc);
            
            final int menuIndex=i;
            
            //버튼 눌렀을 때
            button.addActionListener(new ActionListener() {
            	
                @Override
                public void actionPerformed(ActionEvent e) {
                	if(lastClickedButton!=null) {  //마지막으로 누른 버튼이 있을 경우 
                		lastClickedButton.setBackground(Color.white);  //마지막으로 누른 버튼의 색을 원래 색으로 바꿈
                		int rowCount = tableModel.getRowCount();
                        if (rowCount > 0) {
                            tableModel.removeRow(rowCount - 1);  //이전에 추가된 행은 삭제됨.
                        }
                        
                        //한 번 더 누르면 초기화
                        if(lastClickedButton==button) {
                        	UIMenuOrder menuOrder =new UIMenuOrder(res,tableN);
                    		showNext(menuOrder);
                        }
                	}
                	
                	//스피너 값 초기화
                	spinner.setValue(1);
                	lastClickedButton=button; 
                	button.setBackground(new Color(0x8ECA97));  //버튼 색 바꾸기 
                	orderMenuN=menuIndex;
                	Menu orderedMenu = menus.get(menuIndex);
                    Object[] lastSelectMenu = {tableModel.getRowCount(), orderedMenu.menuName, orderMenuCount, orderedMenu.price * orderMenuCount};
                    tableModel.addRow(lastSelectMenu);                    
                }
            });

            gbc.gridx++;
            if (gbc.gridx > 2) {
                gbc.gridx = 0;
                gbc.gridy++;
            }
            
        }
	}
	private class CustomRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            // Get the default rendering component
            Component renderer = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

            // 클릭 된 버튼이 있을 경우 테이블 마지막 행의 색을 바꿈
            int lastRow = table.getRowCount() - 1;
            if (row == lastRow && lastClickedButton!=null) {
                
                renderer.setBackground(Color.YELLOW);
            } else {
                
                renderer.setBackground(table.getBackground());
            }

            return renderer;
        }
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
