package com.hm.forest.member.model.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hm.forest.common.util.PageInfo;
import com.hm.forest.member.model.mapper.MemberMapper;
import com.hm.forest.member.model.vo.Cart;
import com.hm.forest.member.model.vo.Member;

@Service
public class MemberServiceImpl implements MemberService {
//	@Autowired
//	private MemberDao dao;
	
//	@Autowired
//	private SqlSession session;
	
	@Autowired
	private MemberMapper memberMapper;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Override
	public Member findMemberById(String id) {
		
		return memberMapper.selectMemberById(id);
	}

	@Override
	@Transactional
	public int save(Member member) {
		int result = 0;
		
		if (member.getNo() == 0) {
		
		// insert
			member.setPassword(passwordEncoder.encode(member.getPassword()));
			
			result = memberMapper.insertMember(member);
		}
		
		return result;
	}
	
	@Override
	public boolean changePassword(int no, String newPassword, String pcode, String address1, String address2) {
	    Map<String, Object> paramMap = new HashMap<>();

	    paramMap.put("no", no);
	    paramMap.put("newPassword", passwordEncoder.encode(newPassword));
	    paramMap.put("pcode", pcode);
	    paramMap.put("address1", address1);
	    paramMap.put("address2", address2);

	    int updatedRows = memberMapper.updatePassword(paramMap);

	    return updatedRows > 0;
	}

	@Override
	public Boolean isDuplicateId(String id) {
		
		return this.findMemberById(id) != null;
	}

	@Override
	@Transactional
	public int delete(int no) {

		return memberMapper.updateMemberStatus("N", no);
	}
	
	/* 장바구니 로직 */
	// 장바구니 상품 담기
	@Override
	@Transactional
	public int save(Cart cart) {
		return memberMapper.insertIntoCart(cart);
	}

	// 장바구니 제품 목록 조회
	@Override
	public List<Cart> getCartListsByMemberNo(int memberNo) {
		return memberMapper.selectCartLists(memberNo);
	}

	// 장바구니 제품 목록 삭제
	@Override
	@Transactional
	public int delete(String cartNo) {
		return memberMapper.deleteSelectedCartList(cartNo);
	}
	
	// 장바구니 제품 목록 개수
	@Override
	public int selectCartItemsCount(int memberNo) {
		return memberMapper.selectCartItemsCount(memberNo);
	}
	

	/* 주문.결제 로직 */
	// 로그인멤버별 주문서 상품 목록 조회
	@Override
	public List<Cart> getCartListsByMemberNoAndCartNo(int memberNo, String cartNo) {
		return memberMapper.selectCartOrderLists(memberNo, cartNo);
	}
	
	
//	@Override
//	public List<Member> getmemberlists(String searchType, PageInfo pageInfo) {
//	    return memberMapper.getmemberlists(searchType, pageInfo);
//	}
	
	// 검색값이 없을 때 회원 수 가져오기
		@Override
		public int selectmembercount() {
			
			return memberMapper.selectmembercount();
		}
	
	// 검색값이 없을 때 회원 목록 조회
	@Override
	public List<Member> getmemberlists(PageInfo pageInfo) {
		int limit = pageInfo.getListLimit();
		int offset = (pageInfo.getCurrentPage() - 1) * limit;
		
		RowBounds rowBounds = new RowBounds(offset, limit);
		
		return memberMapper.getmemberlists(rowBounds);
	}
	
	// 검색값 있을 때 회원 수 가져오기
	@Override
	public int selectmembercountvalue(String searchType) {

		return memberMapper.selectmembercountvalue(searchType);
	}

	// 검색값 있을 때 회원 목록 가져오기
	@Override
	public List<Member> getmemberlistsvalue(String searchType, PageInfo pageInfo) {
		int limit = pageInfo.getListLimit();
		int offset = (pageInfo.getCurrentPage() - 1) * limit;
		
		RowBounds rowBounds = new RowBounds(offset, limit);
	
		return memberMapper.getmemberlistsvalue(searchType, rowBounds);
	}
	
	

	// 사용계정 --> 휴면계정 변경
	@Override
	public int updatememberstatus(String status, int no) {

		return memberMapper.updatememberstatus("N", no);
	}
	
	// 휴면계정 --> 사용계정으로 변경
	@Override
	public int activateMember(String status, int no) {

		return memberMapper.updatememberstatus("Y", no);
	}


	
//	@Override
//	public int selectMemberCountByStatus(String status) {
//
//		return mapper.selectMemberCountByStatus(status);
//	}
//	
//	@Override
//	public List<Member> getMemberlists(String status, PageInfo pageInfo) {
//		int limit = pageInfo.getListLimit();
//		int offset = (pageInfo.getCurrentPage() - 1) * limit;
//		
//		RowBounds bounds = new RowBounds(offset, limit);
//		
//		
//		return mapper.selectMemberlistsByStatus(status,bounds);
//	}

}
