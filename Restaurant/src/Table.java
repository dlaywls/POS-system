import java.io.*;

// Table 클래스
public class Table {
	private String tableName;	// 테이블 번호 변수
	private int member=0;	// 수용 가능 인원 변수
	private boolean available = true;	// 테이블 이용 가능 여부 변수
	private Order[] orders = new Order[100];	// 주문 내역 리스트 변수
	private int orderLast = 0;	// orders 리스트에서 주문이 채워진 마지막 인덱스의 다음 인덱스를 확인하기 위한 변수
	
	//테이블 데이터 복구 생성자
	public Table (DataInputStream dInputStream) throws IOException {
		this.tableName=dInputStream.readUTF();
		this.member=dInputStream.readInt();
		this.available=dInputStream.readBoolean();
		this.orderLast=dInputStream.readInt();
		//주문 메뉴 정보 읽기
		for(int i=0;i<orderLast;i++) {
			String menuName=dInputStream.readUTF();
			int price=dInputStream.readInt();
			int orderCount=dInputStream.readInt();
			Order order=new Order(menuName,price);
			order.setOrderCount(orderCount);
			orders[i]=order;
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
		return orderLast;
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
			orders[orderLast] = order;	// orderLast 인덱스에 새로운 주문을 추가합니다.
			search = orderLast;	// search 값을 order 객체가 들어간 인덱스 값으로 바꿉니다.
			orderLast++;	// orderLast에 1을 더하여 마지막으로 채워진 인덱스의 다음 인덱스를 가리키도록 합니다.
		}
		orders[search].addOrderCount(n);	// 주문한 해당 메뉴의 주문량을 n 증가합니다.
	}
	
	// 메뉴 객체 탐색
	public int searchOrder(Order target) {
	    for (int i = 0; i < orderLast; i++) {
	    	// i번째 인덱스의 객체와 찾으려는 order가 같은 경우
	        if (orders[i].equals(target)) {	
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
		orderLast = 0;	// orderLast를 0으로 변경
		return paid;
	}
	
	// 주문 내역을 보여주는 함수
	Order[] getOrders() {
		return orders;
	}
	
	// total 접근자
	int getTotal() {
		int total = 0;
		for (int i=0; i < orderLast; i++) {
			total += orders[i].getOrderPay();
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
	
	
	//테이블 저장
	public void saveTables(DataOutputStream dStream) {
		try {
			dStream.writeUTF(this.tableName);
			dStream.writeInt(this.member);
			dStream.writeBoolean(this.available);
			dStream.writeInt(this.orderLast);
			//주문 내역 리스트 저장 
			for(int i=0;i<orderLast;i++) {
				dStream.writeUTF(orders[i].menuName);
				dStream.writeInt(orders[i].price);
				dStream.writeInt(orders[i].orderCount);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}
