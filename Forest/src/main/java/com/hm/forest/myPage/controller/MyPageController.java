package com.hm.forest.myPage.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.hm.forest.board.model.service.BoardService;
import com.hm.forest.board.model.vo.Board;
import com.hm.forest.common.util.PageInfo;
import com.hm.forest.member.model.service.MemberService;
import com.hm.forest.member.model.vo.Cart;
import com.hm.forest.member.model.vo.Member;
import com.hm.forest.payment.model.service.PaymentService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/myPage")
public class MyPageController {

	@Autowired
	private BoardService boardService;
	
	@Autowired
	private MemberService memberService;
	
	@Autowired
	private PaymentService paymentService;
	
	// myPage 이동
	@GetMapping("/myPage")
	public ModelAndView myPage (ModelAndView modelAndView, @AuthenticationPrincipal Member loginMember) {
		
		modelAndView.addObject("pageName", "myPage");
		modelAndView.addObject("loginMember", loginMember);
		modelAndView.setViewName("page/myPage/myPage");
		
		return modelAndView;
	}

	// 장바구니 페이지 요청
	@GetMapping("/cart")
	public ModelAndView cart (ModelAndView modelAndView, @AuthenticationPrincipal Member loginMember) {
		
		List<Cart> cartLists = null;
		int memberNo = loginMember.getNo();

		cartLists = memberService.getCartListsByMemberNo(memberNo);
		
		modelAndView.addObject("pageName", "cart");
		modelAndView.addObject("cartLists", cartLists);
		modelAndView.addObject("loginMember", loginMember);
		modelAndView.setViewName("page/myPage/cart");
		
		return modelAndView;
	}
	
	// 비동기 통신 응답
	// 장바구니 제품 목록 개수
	@GetMapping("/cart/count")
	public ResponseEntity<Map<String, Object>> selectCartItemsCount(@AuthenticationPrincipal Member loginMember) {
		Map<String, Object> map = new HashMap<>();
		int memberNo = loginMember.getNo();
		
		map.put("listCount", memberService.selectCartItemsCount(memberNo));

		return ResponseEntity.ok(map);
	}
		
	
	// 장바구니 상품 담기
	@PostMapping("/cart/add")	
	public ResponseEntity<Map<String, Object>> cart(@AuthenticationPrincipal Member loginMember, @RequestBody Cart cart)  {
		int result = 0;
		
		Map<String, Object> map = new HashMap<>();
		
		// 장바구니에 로그인멤버의 정보 설정
		cart.setMemberNo(loginMember.getNo());
		
		result = memberService.save(cart);
		 
		if (result > 0) {
	            map.put("message", "성공");
	     } else {
	            map.put("message", "실패");
	     }
		
		map.put("result", result);
		map.put("cart", cart);
		
		return ResponseEntity.ok(map);
	}
	
	// 장바구니 상품 삭제 
	@PostMapping("/cart/delete")
	public ResponseEntity<Map<String, Object>> cart(@AuthenticationPrincipal Member loginMember, @RequestBody String cartNo)  {
		int result = 0;
		
		Map<String, Object> map = new HashMap<>();
		
		log.info("cartNo : {}", cartNo);

		result = memberService.delete(cartNo);
		 
		if (result > 0) {
	            map.put("message", "성공");
	     } else {
	            map.put("message", "실패");
	     }
		
		map.put("result", result);
		
		return ResponseEntity.ok(map);
	}
	
	
	// 마이페이지 > 주문내역으로 이동
	@GetMapping("/orderList")
	public ModelAndView orderList (ModelAndView modelAndView, @AuthenticationPrincipal Member loginMember) {
		List<Cart> myPageOrderList = new ArrayList<>(); // 주문 내역 담을 객체 생성
		int memberNo = loginMember.getNo();
		
		// 마이페이지 > 주문 내역 조회
		myPageOrderList =  paymentService.getMyOrderListsByNo(memberNo);
		
		System.out.println("*****" + myPageOrderList);
		modelAndView.addObject("pageName", "orderList");
		modelAndView.addObject("loginMember", loginMember);
		modelAndView.addObject("myPageOrderList", myPageOrderList);
		modelAndView.setViewName("page/myPage/orderList");
		
		return modelAndView;
	}
	
	

	// 1:1문의페이지 이동. 게시물 전체 목록 조회(검색 기능 포함)
	 @GetMapping("/qna")
	 public ModelAndView FindAll(ModelAndView modelAndView, @RequestParam(defaultValue = "1") int page,  @AuthenticationPrincipal Member loginMember) {
		
		if (loginMember != null) {
			String type = "qna";
			int listCount = 0;
			PageInfo pageInfo = null;
			List<Board> boardLists = null;
			
			listCount = boardService.selectBoardCountByType(type);
			pageInfo = new PageInfo(page, 10, listCount, 10);
			boardLists = boardService.getBoardLists(type, pageInfo);
			
			modelAndView.addObject("pageName", "qna");
			modelAndView.addObject("pageInfo", pageInfo);
			modelAndView.addObject("boardLists", boardLists);
			modelAndView.addObject("loginMember", loginMember);
			
			modelAndView.setViewName("page/myPage/qna");
		} else {
			modelAndView.addObject("msg", "잘못된 접근 입니다.");
			modelAndView.addObject("location", "/");	
			
			modelAndView.setViewName("page/common/msg");
		}
		
		return modelAndView;
	}
	 
	// 특정 게시글 보기(& 조회수 업데이트 & 쿠키 정보로 조회수 중복 방지)
	@GetMapping("/view")
	public ModelAndView view (ModelAndView modelAndView, @RequestParam("no") int no,  @AuthenticationPrincipal Member loginMember, HttpSession session, HttpServletResponse response) {
		Board board = boardService.getBoardByNo(no);
		
		String sessionKey = no + ":cookie";
		
		// 세션이 세션키를 가지고 있는지 확인
		if (session.getAttribute(sessionKey) == null) {
			// 1. 세션 키가 없으면 조회수 업데이트(+1)
			int updateCount = boardService.updateReadCount(no);
			board.setReadCount(board.getReadCount() + 1);
			
			// 2. 세션에 세션 키 추가
			session.setAttribute(sessionKey, true);
			
			// 3. 조회수 중복 방지를 위해 쿠키 추가
			Cookie readCookie = new Cookie("read_" + no, "true");
		    readCookie.setMaxAge(24 * 60 * 60); // 쿠키 유효시간 설정(24시간)
			response.addCookie(readCookie);
		} 
		
		modelAndView.addObject("pageName", "qnaView");
		modelAndView.addObject("board", board);
		modelAndView.setViewName("page/myPage/view");
		modelAndView.addObject("loginMember", loginMember);
		
		return modelAndView;
	}
			

	// 게시글 작성 페이지 요청
	@GetMapping("/write")
	public ModelAndView write (ModelAndView modelAndView, @RequestParam("type") String type, @AuthenticationPrincipal Member loginMember) {
		modelAndView.addObject("pageName", "qnaWrite");
		modelAndView.addObject("type", type);
		modelAndView.addObject("loginMember", loginMember);
		modelAndView.setViewName("page/myPage/write");
		
		return modelAndView;
	}
	
	// 게시글 작성(등록)
	@PostMapping("/write")
	public ModelAndView save (ModelAndView modelAndView, @RequestParam("type") String type, @RequestParam("writerNo") int writerNo, Board board) {
		int result = 0;
		board.setType(type);
		board.setWriterNo(writerNo);
		
		result = boardService.save(board);
		
		if (result > 0) {
			modelAndView.addObject("msg", "게시글이 정상적으로 등록되었습니다.");
			modelAndView.addObject("board", board);
			modelAndView.addObject("location", "view?no=" + board.getNo());	
		} else {
			modelAndView.addObject("msg", "게시글 등록에 실패하였습니다.");
			modelAndView.addObject("location", "write?type=" + board.getType());				
		}
		modelAndView.setViewName("page/common/msg");

		return modelAndView;
	}
	
	// 게시글 수정 페이지 요청
	@GetMapping("/update")
	public ModelAndView update (ModelAndView modelAndView, @AuthenticationPrincipal Member loginMember, @RequestParam("no") int no) {
		Board board = boardService.getBoardByNo(no);
		
		modelAndView.addObject("pageName", "qnaUpdate");
		modelAndView.addObject("board", board);
		modelAndView.addObject("loginMember", loginMember);
		modelAndView.setViewName("page/myPage/update");
		
		return modelAndView;
	}
	
	// 게시글 수정
	@PostMapping("/update")
	public ModelAndView save (ModelAndView modelAndView, @RequestParam("no") int no,
					   		  @RequestParam("title") String title, @RequestParam("content") String content) {
		int result = 0;
		Board board = null;
		
		board = boardService.getBoardByNo(no);
		board.setTitle(title);
		board.setContent(content);
		
		result = boardService.save(board);
	
		if (result > 0) {
			modelAndView.addObject("msg", "게시글이 성공적으로 수정되었습니다.");
			modelAndView.addObject("location", "view?no=" + board.getNo());								
		} else {
			modelAndView.addObject("msg", "게시글 수정에 실패하였습니다.");
			modelAndView.addObject("location", "update?no=" + board.getNo());			
		}

		modelAndView.setViewName("page/common/msg");

		return modelAndView;
	}
	
	// 게시글 삭제(상태값 변경)
	@GetMapping("/delete")
	public ModelAndView delete(ModelAndView modelAndView, @RequestParam("no") int no) {
		int result = 0;
		Board board = null;
		
		board = boardService.getBoardByNo(no);
		
//		if (board != null && board.getWriterId().equals(loginMember.getId())) {
			result = boardService.delete(board.getNo());
			
			if (result > 0) {
				modelAndView.addObject("msg", "게시글이 정상적으로 삭제되었습니다.");
				modelAndView.addObject("location", board.getType());			
			} else {				
				modelAndView.addObject("msg", "게시글 삭제에 실패하였습니다.");
				modelAndView.addObject("location", "view?no=" + board.getNo());				
			}
//		} else {
//			modelAndView.addObject("msg", "잘못된 접근입니다.");
//			modelAndView.addObject("location", "/myPage/list");
//		}
		
		modelAndView.setViewName("page/common/msg");
		
		return modelAndView;
	}

	
	
	
	
	
}
