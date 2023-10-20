package com.hm.forest.board.model.vo;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Board {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int no;
	
	private int rowNum;
	
	private String type;
	
	private String category;
	
	private int writerNo;
	
	private String writerId;
	
	private String title;
	
	private String content;
	
	private String content2;
	
	private String originalFilename; 
	
	private String renamedFilename;
	
	private int readCount;
	
	private int likeCount;
	
	private int reportCount;
	
	private String reportDetail;
	
	private String status;
	
	private List<Reply> replies;
	
	private Date createDate;
	
	private Date modifyDate;
	
}

