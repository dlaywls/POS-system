import java.io.*;

public class Order extends Menu{
	int orderCount;
	
	Order(){
		super(null, 0);
		orderCount=0;
	}
	Order(String menuName, int price) {
		super(menuName, price);
		orderCount =0;
	}
	
	// 메뉴 주문량 접근자
	int getOrderCount() {
		return orderCount;
	}
	
	// 메뉴 주문량 설정자
	void setOrderCount(int orderCount) {
		this.orderCount = orderCount;
	}
	
	// 주문량 추가 함수
	void addOrderCount(int n) {
		orderCount += n;
	}
		
	// 주문량 감소 함수
	void minusOrderCount(int n) {
		if (orderCount > n) {
			orderCount -= n;
		}
	}
	
	int getOrderPay() {
		return price * orderCount;
	}

    // equals() 함수 재정의
    public boolean equals(Order ord) {
    	if (this.menuName.equals(ord.getMenuName())) {
    		return true;
    	}
    	else {
    		return false;
    	}
    }
    
	// toString() 함수 재정의
	public String toString() {
		return menuName + " : " + price + " / " +orderCount;
	}	
	
	public void readOrder(DataInputStream dInputStream) throws IOException{
		this.menuName=dInputStream.readUTF();
		this.price=dInputStream.readInt();
		this.orderCount=dInputStream.readInt();
	}
}
