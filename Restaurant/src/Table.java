import java.io.*;
import java.util.*;

// Table 클래스
public class Table {
	private String tableName;	// 테이블 번호 변수
	private int member=0;	// 수용 가능 인원 변수
	private boolean available = true;	// 테이블 이용 가능 여부 변수	
	private ArrayList<Order> orders=new ArrayList<Order>();// 주문 내역 리스트 변수
	
	//테이블 데이터 복구 생성자
	public Table (DataInputStream dInputStream) throws IOException {
		this.tableName=dInputStream.readUTF();
		this.member=dInputStream.readInt();
		this.available=dInputStream.readBoolean();
		int orderLast=dInputStream.readInt();
		//주문 메뉴 정보 읽기
		for(int i=0;i<orderLast;i++) {
			Order order=new Order();
			order.readOrder(dInputStream);
			orders.add(order);
		}
	}
	
	// 테이블 생성자
	Table(String tableName){
		this.tableName = tableName;
	}
		
	// 테이블 생성자
	Table(String tableName, int member, boolean available){
		this.tableName = tableName;
		this.member = member;
		this.available = available;
	}
			
	// 테이블명 접근자
	String getTableName() {
		return tableName;
	}
	
	// 수용 가능 인원 접근자
	int getMember() {
		return member;
	}
	
	// 이용 가능 여부 접근자
	boolean getAvailable() {
		return available;
	}
	
	// orderLast 접근자
	int getOrderLast() {
		return orders.size();
	}
		
	// 테이블명 설정자
	void setName(String tableName) {
		this.tableName = tableName;
	}
	
	// 수용 가능 인원 설정자
	void setMember(int member) {
		this.member = member;
	}
	
	// 이용 가능 여부 설정자
	void setAvailable(boolean tf) {
		this.available = tf;
	}
			
	// 주문 추가 함수
	void addOrder(Order order, int n){
		
		int search = searchOrder(order);
		// 이전에 주문했던 메뉴가 아닐 경우
		if(search == -1) {
			orders.add(order);
			search=orders.size()-1;// 새로운 주문을 추가합니다.	
		}
		orders.get(search).addOrderCount(n);	// 주문한 해당 메뉴의 주문량을 n 증가합니다.
	}
	
	// 메뉴 객체 탐색
	public int searchOrder(Order target) {
		if(orders.isEmpty()) return -1; //처음 주문일 경우
		
	    for (int i = 0; i < orders.size(); i++) {
	    	// i번째 인덱스의 객체와 찾으려는 order가 같은 경우
	        if (orders.get(i).equals(target)) {	
	            return i;  // 해당 인덱스 반환
	        }
	    }
	    return -1;  // 찾으려는 order 없는 경우
	}
	
	// 테이블에 손님이 들어온 경우
	int inTable() {
		if (available == true) {
			available = false;	// 이용 불가능으로 변경
			return 0;
		}
		else {
			return -1;
		}
	}
	
	// 손님이 계산을 마치고 나갈 경우
	int outTable() {
		int paid = getTotal();	// paid 변수에 total 값을 대입(total은 0으로 초기화할 것이기 때문에)
		available = true;	// 이용 가능으로 변경	 
		orders.clear();	// 주문내역 삭제
		return paid;
	}
	
	// 주문 내역을 보여주는 함수
	ArrayList<Order> getOrders() {
		return orders;
	}
	
	// total 접근자
	int getTotal() {
		int total = 0;
		for (int i=0; i < orders.size(); i++) {
			total += orders.get(i).getOrderPay();
		}
		return total;
	}
	
	// equals() 함수 재정의
    public boolean equals(Table t) {
    	if (this.tableName.equals(t.getTableName())) {
    		return true;
    	}
    	else {
    		return false;
    	}
    }
    
	// toString() 재정의
	public String toString() {
		return tableName+"("+member+"): " + available;
	}
	
	public String printOrder() {
		String order = "----------주문내역----------\n";
		//반복문을 사용해 식당의 메뉴를 table 변수에 추가
		for(int i=0; i<orders.size(); i++) {
			if (orders.get(i) != null) {
				order += i +". "+ orders.get(i)+"개";
				}
			else {
				break;
			}
			order += "\n";
		}
		order += "------------------------\n";
		order += "주문금액: " + getTotal();
		return order;
	}
	
	
	//테이블 저장
	public void saveTables(DataOutputStream dStream) {
		try {
			dStream.writeUTF(this.tableName);
			dStream.writeInt(this.member);
			dStream.writeBoolean(this.available);
			dStream.writeInt(orders.size());
			//주문 내역 리스트 저장 
			for(int i=0;i<orders.size();i++) {
				orders.get(i).saveOrder(dStream);
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}
