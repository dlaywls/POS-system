import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JDialog;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;


public class UIHome extends JPanel implements Serializable{

	/**
	 * Create the panel.
	 */
	
	
	private Restaurant res;
	
	public static void main(String[] args) {
		
        JFrame frame = new JFrame("Restaurant");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000,650);
        frame.getContentPane().add(new UIHome());
        frame.setVisible(true);
       
    }

	public UIHome() {
		res=new Restaurant();
		//데이터 복구
		try {
			FileInputStream fInputStream = new FileInputStream("restaurant.dat");
			ObjectInputStream  in = new ObjectInputStream (fInputStream);
					
			res.loadDataSerialize(in);
			fInputStream.close();
			in.close();
						
		} catch(FileNotFoundException e) {
			//파일 에러 프레임 
			JFrame fileErrorFrame = new JFrame("File error frame");
	        fileErrorFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        fileErrorFrame.setSize(500, 400);
	        fileErrorFrame.setLayout(new FlowLayout());
	        
	        //파일 에러 프레임에 파일 에러 다이얼로그 넣기
	        JDialog modalDialog = new JDialog(fileErrorFrame, "File error Dialog", true); 
            modalDialog.setLayout(new BorderLayout());

            JLabel label = new JLabel("<html> 파일이 없습니다.<br> 파일을 새로 만드시겠습니까?</html>");
            label.setFont(new Font("굴림", Font.PLAIN, 24));
            label.setHorizontalAlignment(JLabel.CENTER); 
            modalDialog.add(label, BorderLayout.CENTER);

            JPanel buttonPanel = new JPanel();
            buttonPanel.setLayout(new FlowLayout());
            
            JButton yesButton = new JButton("예");
            JButton noButton = new JButton("아니오");
            buttonPanel.add(yesButton);
            buttonPanel.add(noButton);

            modalDialog.add(buttonPanel, BorderLayout.SOUTH);
            
            
           //'yesButton'버튼을 눌렀을 경우
            yesButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    modalDialog.setVisible(false);
                }
            });

            //'noButton'버튼을 눌렀을 경우
            noButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                	JDialog modalDialog = new JDialog(fileErrorFrame, "program exit", true); 
                    modalDialog.setLayout(new BorderLayout());
                	
                    UIProgramExit programExit = new UIProgramExit();
                    modalDialog.add(programExit.contentPane);
                    
                    modalDialog.setSize(500, 380);
                    modalDialog.setVisible(true);
                }
            });
            
            //'x'버튼을 눌렀을 경우
            modalDialog.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    System.exit(0); // 프로그램 종료
                }
            });
            
            modalDialog.setSize(500, 380);
            modalDialog.setVisible(true);
		}catch (Exception e) {
			System.out.println("파일 읽기 에러가 발생했습니다.");
		}
		
		
		setLayout(null);
		
		//'관리자 모드' 버튼
		JButton btnNewButton = new JButton("관리자 모드");
		btnNewButton.setFont(new Font("굴림", Font.PLAIN, 20));
		btnNewButton.setBounds(137, 88, 303, 323);
		add(btnNewButton);
		btnNewButton.addActionListener( new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	UIManager manager=new UIManager(res);
            	showNext(manager);  //'관리자 모드' 패널로 이동
            }
        });
		
		//'판매 모드' 버튼
		JButton btnNewButton_1 = new JButton("판매모드");
		btnNewButton_1.setFont(new Font("굴림", Font.PLAIN, 20));
		btnNewButton_1.setBounds(552, 88, 303, 323);
		add(btnNewButton_1);
		btnNewButton_1.addActionListener( new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	UISales sales=new UISales(res);
            	showNext(sales);  //'판매 모드' 패널로 이동
            }
        });
		
		//'종료' 버튼
		JButton btnNewButton_2 = new JButton("종료");
		btnNewButton_2.setFont(new Font("굴림", Font.PLAIN, 20));
		btnNewButton_2.setBounds(137, 488, 303, 76);
		add(btnNewButton_2);
		btnNewButton_2.addActionListener( new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	UIProgramExit programExit=new UIProgramExit();
            	programExit.setVisible(true); //'UIProgrmaExit'프레임 보여줌.
            }
        });
		
		//'정보 저장' 버튼
		JButton btnNewButton_2_1 = new JButton("정보 저장");
		btnNewButton_2_1.setFont(new Font("굴림", Font.PLAIN, 20));
		btnNewButton_2_1.setBounds(552, 488, 303, 76);
		add(btnNewButton_2_1);
		btnNewButton_2_1.addActionListener( new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	FileOutputStream fStream=null;
				try {
					fStream=new FileOutputStream("restaurant.dat");
					ObjectOutputStream  out=new ObjectOutputStream (fStream); //파일 오픈
					res.saveDataSerialize(out);// 데이터 저장
					out.close();
					
					UISaveData saveData=new UISaveData();
					saveData.setVisible(true); //정보 저장 확인 프레임 보여주기.
				}catch(Exception e1) {
					e1.printStackTrace();
				}
                
            }
        });
		}
	
	//다음 패널로 이동하는 함수
	private void showNext(Component ob) {
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
        frame.getContentPane().remove(this);
        frame.getContentPane().add(ob);
        frame.revalidate();
        frame.repaint();
    }  
}
