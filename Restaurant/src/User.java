import java.io.*;
import java.util.*;
public class User implements Serializable{
	public static void main(String args[]){
		Scanner input = new Scanner(System.in);	// 스캐너 객체 호출
		Restaurant bab =new Restaurant();	// 식당 객체 생성하고 참조 변수가 가리키게 함.
		String firstOrNot="y";
		
		//데이터 복구
		try {
			FileInputStream fInputStream = new FileInputStream("restaurant.dat");
			ObjectInputStream  in = new ObjectInputStream (fInputStream);
			
			bab.loadDataSerialize(in);
			fInputStream.close();
			in.close();
				
		} catch(FileNotFoundException e) {
			System.out.println("파일이 없습니다. 파일을 새로 만드시겠습니까?[y/n]");
			firstOrNot=input.next();
			if(firstOrNot.equals("n")) {
				System.out.println("파일이 없습니다.");
			}
		}catch (Exception e) {
			System.out.println("파일 읽기 에러가 발생했습니다.");
		}  
		
		// while문을 통한 반복
		while(firstOrNot.equals("y")) {
			
			System.out.println("무엇을 하시겠습니까?(선택지 번호로 입력해주세요.)");
			System.out.println("1.주문 관리, 2.메뉴 관리, 3.테이블 관리, 4.종료, 5.정보 저장");
			int what = input.nextInt();
			if(what==1 && (bab.getTableLast() == 0 || bab.getMenuLast() == 0)) {
				System.out.println("메뉴와 테이블을 먼저 추가해주세요.");
				continue;
			}
			switch(what) {
			case 1:	//주문관리
				System.out.println("1. 주문 추가, 2. 주문 내역 확인, 3. 계산");
				int what1 = input.nextInt();
				if (what1==1) {
					System.out.println(bab.printTable());
					System.out.println("어느 테이블입니까?");
					int tableN = input.nextInt();
					bab.inTable(tableN);
					System.out.println(bab.printMenu());
					System.out.println("주문하실 메뉴를 선택해주세요.");
					int orderMenuN = input.nextInt();
					System.out.println("몇 개 주문하시겠습니까?");
					int orderN = input.nextInt();	
					bab.menuToOrder(orderMenuN,tableN,orderN);
				}
				else if (what1==2) {
					System.out.println(bab.printTable());
					System.out.println("어느 테이블입니까?");
					int tableN = input.nextInt();
					System.out.println(bab.printOrder(tableN));
					
				}
				else if (what1==3) {
					System.out.println(bab.printTable());
					System.out.println("어느 테이블입니까?");
					int table = input.nextInt();
					int paid = bab.pay(table);
					System.out.println(paid+"가 결제 되었습니다.");
				}
				break;
			case 2:	// 메뉴 관리
				System.out.println("1. 메뉴 추가, 2. 메뉴 삭제");
				int what2 = input.nextInt();
				if (what2==1) {
					input.nextLine();
					System.out.println("추가하실 메뉴의 이름을 입력하세요.");	
					String menuName = input.nextLine();	// 메뉴명 입력 받음
					System.out.println("추가하실 메뉴의 가격을 입력하세요.");
					int menuPrice = input.nextInt();// 메뉴 가격 입력 받음
					try {
						bab.addMenu(menuName, menuPrice);	// 메뉴 객체 생성 후 식당 메뉴에 추가
						System.out.println("\'"+menuName+"\'"+"(이/가) 추가되었습니다.");
						
					} catch (Exception e) {	
						System.out.println(e.getMessage());
					}
				}
				else if (what2==2) {
					input.nextLine();
					System.out.println("삭제하실 메뉴의 이름을 입력하세요.");	
					String menuName = input.nextLine();	// 메뉴명 입력 받음
					try {
						bab.deleteMenu(menuName);	// 코드와 이름을 부여받은 임의의 메뉴 객체를 생성하여 같은 메뉴 객체가 있다면 삭제
						System.out.println(menuName+"이 삭제되었습니다.");
					} catch (Exception e) {	
						System.out.println(e.getMessage());
					}
				}
				break;
			case 3:	// 테이블 관리
				System.out.println("1. 테이블 추가, 2. 테이블 삭제");
				int what3 = input.nextInt();
				if (what3==1) {
					input.nextLine();
					System.out.println("추가하실 테이블의 이름을 입력하세요.");	
					String tableName = input.nextLine();	// 테이블명 입력 받음
					System.out.println("추가하실 테이블의 수용 가능 인원을 입력하세요.");	
					int member = input.nextInt();	// 수용 가능 인원 입력 받음
					System.out.println("추가하실 테이블의 이용 가능 여부를 입력하세요.(true/false)");	
					boolean available = input.nextBoolean();
					try {
						bab.addTable(tableName, member, available);	// 테이블 객체 생성 후 식당 테이블에 추가
						System.out.println(tableName+" 테이블이 추가되었습니다.");
					} catch (Exception e) {	
						System.out.println(e.getMessage());
					}
				}
				else if (what3==2) {
					input.nextLine();
					System.out.println("삭제하실 테이블의 이름을 입력하세요.");	
					String tableName = input.nextLine();	// 테이블명 입력 받음
					try {
						bab.deleteTable(tableName);	// 이름을 부여받은 임의의 테이블 객체 생성 후 같은 이름의 테이블 객체 있다면 삭제
						System.out.println(tableName+" 테이블이 삭제되었습니다.");
					} catch (Exception e) {	
						System.out.println(e.getMessage());
					}
				}
				break;
			case 5:
				FileOutputStream fStream=null;
				try {
					fStream=new FileOutputStream("restaurant.dat");
					ObjectOutputStream  out=new ObjectOutputStream (fStream); //파일 오픈
					bab.saveDataSerialize(out);// 데이터 저장
					out.close();
					System.out.println("저장되었습니다");
				}catch(Exception e) {
					e.printStackTrace();
				}
				
				break;
				
			}
			// 종료 선택 시 while문 종료 및 프로그램 종료
			if(what==4) 
				//만약 매출량 같은 걸 파일에 저장하는 형식으로 한다면 여기에 추가하면 될 듯.
				break;
		}
		System.out.println("매출: "+ bab.getAmount());
		System.out.println("프로그램을 종료합니다.");
		input.close();
	}	
	
	
}
