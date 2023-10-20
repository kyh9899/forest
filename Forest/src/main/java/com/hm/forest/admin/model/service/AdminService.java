package com.hm.forest.admin.model.service;

import java.util.List;

import com.hm.forest.admin.model.vo.Detail;
import com.hm.forest.admin.model.vo.Product;
import com.hm.forest.board.model.vo.Reply;
import com.hm.forest.common.util.PageInfo;
import com.hm.forest.member.model.vo.Cart;

public interface AdminService {
	
	// 관리자_제품등록,제품수정
	int save(Product product);

	// 관리자_제품삭제
	int delete(int no);
	
	// 관리자_특정 제품 보기 
	Product getProductAdminBoardByNo(int no);

	// 관리자_제품목록 리스트 
	int getProductBoardCount();

	// 관리자_제품 전체 목록 조회
	List<Product> getProductBoardList(PageInfo pageInfo);

	// 메인_(제품보기)특정 제품 조회
	Product getProductBoardByNo(int no);
	
	// 메인_(제품보기) 제품 리스트 카테고리별로 전체 목록 조회
	List<Product> getProductBoardList(String category, PageInfo pageInfo);
	
	// 메인_(제품보기) 제품 목록 카테고리별 리스트 전체 개수
	int getProductBoardCountByCategory(String category);


	// 제품 등록 후 비동기로 제품 정보 보여주기
//	List<Product> getDetailsByProductNo();


	// 단품 주문시 제품 정보 조회
	Product getItemListsByProductNoAndDetailNo(int productNo, int detailNo);

    // 관리자_옵션 등록시 제품 선택
	List<Product> getProductDetailBoardList();

	// 관리자_옵션등록
	int save(Detail detail);

	// 관리자_옵셔 상세보기
	Product getProductDetailViewByNO(int no);





}
