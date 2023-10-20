package com.hm.forest.payment.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {
	private int no;

	private int paymentNo;

	private int cartNo;
	
	private int productNo;
	
	private int detailNo;
	
	private int quantity;
	
	private String status; // 주문 리스트 내의 제품 건별 상태값 
}
