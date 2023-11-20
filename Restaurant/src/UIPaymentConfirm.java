import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTable;
import javax.swing.SwingUtilities;

public class UIPaymentConfirm extends JFrame {
	
	JPanel contentPane;
	private JTable table;
	private DefaultTableModel tableModel;
	private UIMenuOrder menuOrderPanel;

	
	public UIPaymentConfirm(Table selectedTable,Restaurant res , int tableN, UIMenuOrder menuOrderPanel) {
		this.menuOrderPanel = menuOrderPanel;
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 343, 491);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		String[] columns = {"NO.", "이름", "수량", "가격"};   //테이블의 열
        tableModel = new DefaultTableModel(columns, 0); 
        
		table = new JTable(tableModel);
		table.setBounds(43, 361, 493, -324);
		JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(15, 10, 300, 246);
        contentPane.add(scrollPane);  
        
        JLabel lblNewLabel = new JLabel("<html>"+selectedTable.getTotal()+"원을 <br>결제하시겠습니까?</html>");
        lblNewLabel.setFont(new Font("굴림", Font.PLAIN, 24));
        lblNewLabel.setBounds(49, 300, 226, 66);
        contentPane.add(lblNewLabel);
		
        ArrayList<Order> orderedMenus=selectedTable.getOrders();
        for(int i=0; i<selectedTable.getOrderLast();i++) {
        	Order orderedMenu=orderedMenus.get(i);
        	Object[] rowData = {tableModel.getRowCount(), orderedMenu.menuName, orderedMenu.orderCount, orderedMenu.price*orderedMenu.orderCount};
            tableModel.addRow(rowData);
        }
        
        JLabel lblNewLabel_6_1 = new JLabel(""+selectedTable.getTotal());
		lblNewLabel_6_1.setBounds(137, 266, 178, 24);
		contentPane.add(lblNewLabel_6_1);
		lblNewLabel_6_1.setFont(new Font("굴림", Font.PLAIN, 20));
		
		JLabel lblNewLabel_6 = new JLabel("합계: ");
		lblNewLabel_6.setBounds(15, 266, 51, 24);
		contentPane.add(lblNewLabel_6);
		lblNewLabel_6.setFont(new Font("굴림", Font.PLAIN, 20));
		
		JButton btnNewButton = new JButton("아니오");
		btnNewButton.setBounds(224, 407, 91, 23);
		contentPane.add(btnNewButton);
		btnNewButton.addActionListener( new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	dispose();
            }
        });
		
		JButton btnNewButton_1 = new JButton("예");
		btnNewButton_1.setBounds(15, 407, 91, 23);
		contentPane.add(btnNewButton_1);
		btnNewButton_1.addActionListener( new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	res.pay(tableN);
                // Switch to the UISales panel
                menuOrderPanel.showNext(new UISales(res));
                dispose(); 
           }
        });
	}
}
