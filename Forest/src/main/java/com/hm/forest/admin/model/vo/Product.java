package com.hm.forest.admin.model.vo;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {
	
	private int no;
	
	private String category;
	
	private String name;
	
	private int price;
	
	private String content;
	
	private String company;
	
	private int discountrate;
	
	private String selling;
	
	private String status;
	
	private String image;
	
	private int wish;
	
	private List<Detail> Details;
	
 
    

}
