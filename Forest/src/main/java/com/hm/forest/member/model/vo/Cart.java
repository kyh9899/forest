package com.hm.forest.member.model.vo;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cart {
	
	private int no;
	
	private int memberNo;
	
	private int productNo;

	private int detailNo;	

	// Detail
	private String color;
	
	private String size;
	
	private int stock;

	// Product
	private String category;	
	
	private String name;
	
	private int price;

	private int discountrate;
	
	private String image;
	
	private String status;
	
	// 선택된 수량
	private int quantity;
	
	private int totalPrice;
	
	private Date paymentDate;

	
}
