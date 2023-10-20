package com.hm.forest.member.model.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import com.hm.forest.member.model.vo.Cart;
import com.hm.forest.member.model.vo.Member;

@Mapper
public interface MemberMapper {
	
	int selectCount();

	Member selectMemberById(@Param("id") String id);
	
	int insertMember(Member member);
	
	int updateMember(Member member);
	
	int updateMemberStatus(@Param("status") String status, @Param("no") int no);
	
	int updatePassword(Map<String, Object> paramMap);
	
	int selectMemberCountByStatus(@Param("status") String status);
	
	List<Member> selectMemberlistsByStatus(@Param("status") String status, RowBounds bounds);

	// 관리자_멤버 (민지)
	
	// 검색값 없을때 회원 수 조회
	int selectmembercount();
	
	// 검색값 없을 떄 회원 목록 조회
	List<Member> getmemberlists (RowBounds rowBounds);
	
	// 검색값 있을 때 회원 수 조회
	int selectmembercountvalue(@Param("searchType") String searchType);
		
	// 검색값 있을 때 회원 목록 조회
	List<Member> getmemberlistsvalue (@Param("searchType") String searchType, RowBounds rowBounds);
	
	// 사용계정 --> 휴면계정 변경
	int updatememberstatus(@Param("status") String status,@Param("no") int no);
	
	// 휴면계정 --> 사용계정으로 변경
	int activateMember(@Param("status") String status, @Param("no") int no);
	
	/* 장바구니 로직 */
	// 장바구니 상품 담기
	int insertIntoCart(Cart cart);

	// 장바구니 제품 목록 조회
	List<Cart> selectCartLists(@Param("memberNo") int memberNo);

	// 장바구니 제품 목록 삭제
	int deleteSelectedCartList(@Param("cartNo") String cartNo);

	// 장바구니 제품 목록 개수
	int selectCartItemsCount(@Param("memberNo") int memberNo);

	/* 주문.결제 로직 */
	// 로그인멤버별 주문서 상품 목록 조회
	List<Cart> selectCartOrderLists(@Param("memberNo") int memberNo, @Param("cartNo") String cartNo);


}
