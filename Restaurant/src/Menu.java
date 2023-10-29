import java.io.*;
import java.util.ArrayList;

// Menu 클래스
public class Menu implements Serializable{
	protected String menuName = "";	// 메뉴명 변수
	protected int price = 0;	// 메뉴 가격 변수
	
	//메뉴 생성자
	public Menu(DataInputStream dInputStream) throws IOException{		
		this.price=dInputStream.readInt();
		this.menuName=dInputStream.readUTF();		
	}
	
	//메뉴 생성자: 이름만 지정
	Menu(String menuName){
		this.menuName = menuName;
	}
	
	//메뉴 생성자: 이름과 가격을 지정하여 생성함
	Menu(String menuName, int price) {
		this.menuName = menuName;
		this.price = price;
	}
		
	// 메뉴명 접근자
	String getMenuName() {
		return menuName;	// 메뉴명 반환
	}
		
	// 메뉴 가격 접근자
	int getPrice() {
		return price;	//메뉴 가격 반환
	}
			
	// 메뉴명 설정자
	void setMenuName(String menuName) {
		this.menuName = menuName;
	}
		
	// 메뉴 가격 설정자
	void setPrice(int price) {
		this.price = price;
	}
	
	
	// equals() 함수 재정의
    public boolean equals(Menu m) {
    	if (this.menuName.equals(m.getMenuName())) {
    		return true;
    	}
    	else {
    		return false;
    	}
    }
    
	// toString() 함수 재정의
	public String toString() {
		return menuName + " : " + price ;
	}
	
	//메뉴 저장
	public void saveMenus(DataOutputStream dStream) {
		try {			
			dStream.writeInt(this.price);
			dStream.writeUTF(this.menuName);
			
		}catch(Exception e) {
			e.printStackTrace();
		}				
	}	
}
