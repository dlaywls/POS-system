import java.io.*;
import java.util.*;

// Restaurant 클래스(super)
public class Restaurant{
	// Menu 객체들로 이루어진 리스트
	protected ArrayList<Menu> menus;
	// Table 객체들로 이루어진 리스트
	protected ArrayList<Table> tables;
	// 매출액 변수
	protected int amount=0;
	
	// Restaurant 기본 생성자 함수
	public Restaurant() {
		 tables=new ArrayList<Table>();	// 테이블 배열 생성
		 menus=new ArrayList<Menu>();		// 메뉴 배열 생성
	}
		
	// menus 접근자
	ArrayList<Menu> getMenus() {
		return menus;
	}
	// tables 접근자
	ArrayList<Table> getTables() {
		return tables;
	}
	// menuLast 접근자
	int getMenuLast() {
		return menus.size();
	}
	// tableLast 접근자
	int getTableLast() {
		return tables.size();
	}
	// amount 접근자
	int getAmount() {
		return amount;
	}
	
	// 메뉴 객체 탐색
	private int searchMenu(Menu target) {
	    for (int i = 0; i < menus.size(); i++) {
	    	// i번째 인덱스의 객체와 찾으려는 menu가 같은 경우
	        if (menus.get(i).equals(target)) {	
	            return i;  // 해당 인덱스 반환
	        }
	    }
	    return -1;  // 찾으려는 menu 없는 경우
	} 
		
	// 테이블 객체 탐색
	private int searchTable(Table target) {
	    for (int i = 0; i < tables.size(); i++) {
	    	// i번째 인덱스의 객체와 찾으려는 table이 같은 경우
	        if (tables.get(i).equals(target)) {	
	            return i;  // 해당 인덱스 반환
	        }
	    }
	    return -1;	// 찾으려는 table 없는 경우
	}
	
	// 메뉴 객체 추가
	public void addMenu(Menu m) throws Exception {
		// 추가하려는 메뉴가 원래 없던 메뉴일 경우
		if (searchMenu(m)==-1) {
			menus.add( m);	// 메뉴 추가
		}
		// 추가하려는 메뉴가 원래 있던 메뉴일 경우
		else {
			throw new Exception("The menu already exists");
		}
	}
	
	// 테이블 객체 추가
	public void addTable(Table t) throws Exception {
		// 추가하려는 테이블이 원래 없던 테이블일 경우
		if (searchTable(t)==-1) {
			tables.add(t);	// tables에서 비어있는 첫번째 인덱스에 메뉴 추가
		}
		// 추가하려는 테이블이 원래 있던 테이블일 경우
		else {
			throw new Exception("The table already exists");
		}
	}
	
	public void addAmount(int paid) {
		amount += paid;
	}
 	
	// 메뉴 객체 삭제
	public int deleteMenu(Menu m) throws Exception{
		int j = searchMenu(m);	// 삭제하려는 메뉴가 있는지 확인
		// 삭제하려는 메뉴가 있는 경우
		if (j != -1) {
			menus.remove(j);
			return 0;
		}
		// 삭제하려는 메뉴가 없는 경우
		else {
			throw new Exception("The menu does not exist");	// 삭제하려는 메뉴가 없는 경우 예외 발생
		}
	}

	// 테이블 객체 삭제
	public int deleteTable(Table t) throws Exception {
		int j = searchTable(t);	// 삭제하려는 테이블이 있는지 확인
		// 삭제하려는 메뉴가 있는 경우
		if (j != -1) {
			// for 반복문을 통해 삭제된 인덱스부터 뒤의 인덱스에 있는 객체를 앞당깁니다
			tables.remove(j);

			return 0;
		}
		// 삭제하려는 메뉴가 없는 경우
		else {
			throw new Exception("The table does not exist");	// 삭제하려는 메뉴가 없는 경우 예외 발생
		}
	}
	
	public Order menuToOrder(Menu m) {
		Order ord;
		ord = new Order(m.menuName, m.price);
		return ord;
	}
	
	// 계산
	public int pay(Table t) {
		int paid = t.outTable();
		addAmount(paid);
		return paid;
	}
	
	//데이터 저장
	public void saveData(DataOutputStream dStream) {
		try {
			//메뉴 데이터 저장
			dStream.writeInt(menus.size());
			for(int i=0;i<menus.size();i++) {
				menus.get(i).saveMenus(dStream);
			}
			
			//테이블 데이터 저장
			dStream.writeInt(tables.size());
			for(int i=0; i<tables.size(); i++) {
				tables.get(i).saveTables(dStream);
			}
			
			dStream.writeInt(amount);
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	//데이터 복구
	public void loadData(DataInputStream dInputStream) throws Exception {
		int menuLast = 0;
		int tableLast = 0;
	    try {
	    	//메뉴 정보 복구
	    	menuLast=dInputStream.readInt();
	    	for(int i=0;i<menuLast;i++) {
	    		Menu menu=new Menu(dInputStream);
	    		menus.add(menu);
	    	}
	    	
	    	//테이블 정보 복구
	    	tableLast=dInputStream.readInt();
	    	for(int i=0;i<tableLast;i++) {
	    		Table table=new Table(dInputStream);
	    		tables.add(table);
	    	}
	    	
	    	amount=dInputStream.readInt();
	        
	    }catch(EOFException e) {
	    	System.out.println("끝");
	    }catch(IOException e) {
	    	e.printStackTrace();
	    }
	}
}
	






	
	
	

       
       
	
	
	

