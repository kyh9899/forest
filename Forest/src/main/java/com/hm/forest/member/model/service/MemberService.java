package com.hm.forest.member.model.service;

import java.util.List;

import com.hm.forest.common.util.PageInfo;
import com.hm.forest.member.model.vo.Cart;
import com.hm.forest.member.model.vo.Member;

public interface MemberService {

	int save(Member member);
	
	boolean changePassword(int no, String newPassword, String pcode, String address1, String address2);

	Boolean isDuplicateId(String id);

	Member findMemberById(String id);

	int delete(int no);

	// 수정2
	
	// 검색값이 없을 때 회원 수 가져오기
	int selectmembercount();
	
	// 검색값이 없을 때 회원 목록 가져오기
	List<Member> getmemberlists(PageInfo pageInfo);
	
	// 검색값 있을 때 회원 수 가져오기
	int selectmembercountvalue(String searchType);
	
	// 검색값 있을 떄 회원 목록 가져오기
	List<Member> getmemberlistsvalue(String searchType, PageInfo pageInfo);
	
	// 사용계정 --> 휴면계정 변경
	int updatememberstatus(String status, int no);
	
	// 휴면계정 --> 사용계정으로 변경
	int activateMember(String status, int no);

	/* 장바구니 로직 */
	// 장바구니 제품 담기
	int save(Cart cart);

	// 장바구니 제품 목록 조회
	List<Cart> getCartListsByMemberNo(int memberNo);

	// 장바구니 제품 목록 삭제
	int delete(String cartNo);

	// 장바구니 제품 개수
	int selectCartItemsCount(int memberNo);

	/* 주문.결제 로직 */
	// 로그인멤버별 주문서 상품 목록 조회
	List<Cart> getCartListsByMemberNoAndCartNo(int memberNo, String cartNo);

	
	
	
	
//
//	int selectMemberCountByType(String status);
//	
//	List<Member> getMemberlistsBySearchValue(String status, PageInfo pageInfo, String searchType, String keyWord);
//	
//	int selectMemberCountBySearchValue(String status, String searchType, String keyWord);


}
