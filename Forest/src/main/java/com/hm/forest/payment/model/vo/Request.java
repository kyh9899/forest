package com.hm.forest.payment.model.vo;

import java.util.List;

import com.hm.forest.member.model.vo.Cart;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Request {
	private int cartNo;
	
	private int totalPrice;
	
	private List<Cart> cartLists;

	private List<Order> orderLists;
	
	private Delivery delivery;
}
