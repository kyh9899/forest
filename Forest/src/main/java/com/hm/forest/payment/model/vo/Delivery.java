package com.hm.forest.payment.model.vo;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Delivery {
	private int no;

	private int paymentNo;
	
	private String recipient; // 수령인
	
	private String phone;
	
	private String pcode;

	private String address1;
	
	private String address2;
	
	private String memo;
	
	private String status;
	
	private Date deliveryDate;
	
	private Date returnDate;

}
