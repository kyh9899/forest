package com.hm.forest.payment.model.vo;

import java.util.Date;
import java.util.List;

import com.hm.forest.member.model.vo.Cart;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Payment {
	private int no;

	private int memberNo;

	private int totalPrice;
	
	private String paymentMethod;

	private Date paymentDate;

	private Date cancelDate;
	
	private String status;
	
	private List<Order> orderLists; // 주문 제품 정보 리스트
	
	private Delivery delivery; // 배송 정보 객체
	
}
