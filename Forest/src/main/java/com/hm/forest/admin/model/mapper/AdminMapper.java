package com.hm.forest.admin.model.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import com.hm.forest.admin.model.vo.Detail;
import com.hm.forest.admin.model.vo.Product;
import com.hm.forest.board.model.vo.Board;
import com.hm.forest.member.model.vo.Cart;


@Mapper
public interface AdminMapper {
	
	int selectProductBoardCount();

	int insertProduct(Product product);

	int updateProduct(Product product);

	List<Product> selectAll(RowBounds bounds);
	
	// 관리자_특정 제품 조회(view)
	Product selectProductAdminBoardByNo(@Param("no") int no);
	
	// 관리자_제품 삭제
	int updateProductStatus(@Param("no") int no, @Param("status") String status);

	
	
	// 메인_(제품보기) 카테고리 별 제품 전체 목록 조회
	List<Product> selectProductListByCategory(@Param("category") String category, RowBounds bounds);

	// 메인_(제품보기) 카테고리 별 제품 목록 전체 개수
	int selectProductCountByCategory(String category);
	
	// 메인_(제품보기) 특정 제품 조회(view)
	Product selectProductBoardByNo(@Param("no") int no);

	// 단품 주문시 상품 정보 조회
	Product selectItemLists(int productNo, int detailNo);


	// 관리자_옵션 등록 하기 전에 제품 보여주기
	List<Product> selectAllForDetail();

	// 관리자_옵션 등록
	int insertDetail(Detail detail);
	
	// 관리자_옵션 수정
	int updateDetail(Detail detail);

	// 관리자_옵션 상세보기(view)
	Product selectProductDetailBoardByNo(@Param("no") int no);




}
