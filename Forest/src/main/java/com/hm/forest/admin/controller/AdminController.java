package com.hm.forest.admin.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.hm.forest.admin.model.service.AdminService;
import com.hm.forest.admin.model.vo.Detail;
import com.hm.forest.admin.model.vo.Product;
import com.hm.forest.board.model.service.BoardService;
import com.hm.forest.board.model.vo.Board;
import com.hm.forest.common.util.MultipartFileUtil;
import com.hm.forest.common.util.PageInfo;
import com.hm.forest.member.model.service.MemberService;
import com.hm.forest.member.model.vo.Member;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Slf4j
//@RestController
@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	private AdminService adminService;
	
	@Autowired
	private MemberService memberService;
	
	@Autowired
	private BoardService boardService;
	
	// 이미지 업로드 
	private final ResourceLoader resourceLoader;
	
	
	// 관리자페이지_매출관리로 이동
		@GetMapping("/salesMgmt")
		@ResponseBody
		public ModelAndView salesMgmt (ModelAndView modlAndView) {
			

			modlAndView.addObject("pageName", "salesMgmt");
			modlAndView.setViewName("page/admin/salesMgmt");
			
			return modlAndView;
		}
		
		// 관리자페이지_제품관리로 이동
		@GetMapping("/productMgmt")
		@ResponseBody
		public ModelAndView AdminMgmt (ModelAndView modelAndView) {
			
			modelAndView.addObject("pageName", "productMgmt");
			modelAndView.setViewName("page/admin/productMgmt");
			
			return modelAndView;
		}
		


				

		// 관리자_옵션 등록 페이지로 이동 및 제품 리스트 가져오기
		@GetMapping("/productMgmtDetail")
		@ResponseBody
		public ModelAndView getProductListForSelect(ModelAndView modelAndView) {
		    List<Product> productlists = null;

		    productlists = adminService.getProductDetailBoardList();

			modelAndView.addObject("pageName", "productMgmtDetail");
			modelAndView.addObject("productlists", productlists);
			modelAndView.setViewName("page/admin/productMgmtDetail");

		    return modelAndView;

		}
		

		// 관리자_옵션 등록
		@PostMapping("/productDetailInsert")
		public ResponseEntity<Map<String, Object>> detailInsert(@RequestBody Detail detail) {
			int result = 0;
			Map<String, Object> map = new HashMap<>();
			
			result = adminService.save(detail);
			
			System.out.println(detail);		    
			
		    if (result > 0) {
		        // Insert 성공
		    	map.put("msg", "등록에 성공했습니다.");
		    } else {
		        // Insert 실패
		    	map.put("msg", "등록에 실패하였습니다.");
		    }
				
		    return ResponseEntity.ok(map);
		}
		

		
		  // 관리자페이지_제품등록
		@PostMapping("/productMgmt/insert")
		public ModelAndView insert(ModelAndView modelAndView,
								   Product product,
								   @RequestParam("upfile") MultipartFile upfile) {
			
		    int result = 0;
		    Map<String, Object> map = new HashMap<>();
		    
		    if (upfile != null && !upfile.isEmpty()) {
				String location = null;
				String renamedFileName = null;
				
				try {
					location = resourceLoader.getResource("/static/upload/product").getFile().getPath();

					renamedFileName = MultipartFileUtil.save(upfile, location);
					
					if (renamedFileName != null) {
						product.setImage(renamedFileName);
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
				

				System.out.println(location + "★★★★★★★");
			}
		    
		    result = adminService.save(product);

		    map.put("resultCode", result);
		    map.put("product", product);


		    if (result > 0) {
		        // Insert 성공
		    	modelAndView.addObject("msg", "제품이 등록되었습니다.");
		    } else {
		        // Insert 실패
		    	modelAndView.addObject("msg", "등록에 실패하였습니다.");
		    }

		    System.out.println(map);
		    
		    modelAndView.setViewName("redirect:/admin/productMgmtList");
		    return modelAndView;
		}
		

		// 관리자 페이지_제품목록 리스트
		@ResponseBody
		@GetMapping("/productMgmtList")
		public ModelAndView list(ModelAndView modelAndView, 
								 @RequestParam(defaultValue =  "1") int page) {
			
			int listCount = 0;
			PageInfo pageInfo = null;
			List<Product> productlists = null; 
			
			listCount = adminService.getProductBoardCount();
			pageInfo = new PageInfo(page, 10, listCount, 10);
			productlists = adminService.getProductBoardList(pageInfo);
			
			log.info("Page : {}", page);
			log.info("ListCount : {}", listCount);

			modelAndView.addObject("pageInfo", pageInfo);
			modelAndView.addObject("productlists", productlists);
			
			System.out.println();
			
			modelAndView.setViewName("page/admin/productMgmtList");
			
			return modelAndView;
		}
		
		
		@GetMapping("/productMgmtView")
		@ResponseBody
		public ModelAndView view(ModelAndView modelAndView,
								 @RequestParam("no") int no) {
			
			log.info("view() 호출 - {}", no);

			Product product = null;
			
			product =adminService.getProductAdminBoardByNo(no);
			
			modelAndView.addObject("pageName", "productMgmtView");
			modelAndView.addObject("product", product);
			modelAndView.setViewName("page/admin/ProductMgmtView");
			
			return modelAndView;	
		}
		
		// 관리자페이지_제품 상세 페이지에서 수정버튼 요청
		@GetMapping("/productMgmtUpdate")
		@ResponseBody
		public ModelAndView update (ModelAndView modelAndView, @RequestParam("no") int no) {
			Product product = adminService.getProductAdminBoardByNo(no);
			
			modelAndView.addObject("pageName", "productMgmtUpdate");
			modelAndView.addObject("product", product);
			modelAndView.setViewName("page/admin/productMgmtUpdate");
			
			return modelAndView;
		}
		
		
		
		 // 관리자페이지_제품 수정
		 @PostMapping("/productMgmtUpdate")
		 @ResponseBody
		 public ModelAndView update (ModelAndView modelAndView, 
				 					 @RequestParam("upfile") MultipartFile upfile,
				 					 @RequestParam("no") int no,
				 					 @RequestParam("name") String name,
				 					 @RequestParam("price") int price,
				 					 @RequestParam("content") String content ) {

			 int result = 0;
			 Product product = null;

	 
			 product = adminService.getProductAdminBoardByNo(no);
	 
			 
			 if (upfile != null && !upfile.isEmpty()) {
					 String location = null;
					 String renamedFileName = null;
					 
			
					 try {
						location = resourceLoader.getResource("/static/upload/product/")
						 						  .getFile()
						 						  .getPath();
						
						// 이전에 업로드된 첨부파일 삭제
						if (product.getImage() != null) {
							MultipartFileUtil.delete(location + "/" + product.getImage());
							log.info(location + "★삭제된 후 location★");
							
						}
						location = resourceLoader.getResource("/static/upload/product/")
		 						  .getFile()
		 						  .getPath();
							
						// 수정된 첨부파일을 저장한다.
						renamedFileName = MultipartFileUtil.save(upfile, location);
						
						System.out.println(upfile + "★upfile★");
						System.out.println(location + "★첨부파일 저장 후 location★");
						
						if (renamedFileName != null) {
						
							product.setImage(renamedFileName);
						}
					} catch (IOException e) {
						e.printStackTrace();
					}
				 }


			 product.setName(name);
			 product.setPrice(price);
			 product.setContent(content);
			 

			 log.info("★ 보드 : {}", product);
				 
		     result = adminService.save(product);

		 
				 if ( result > 0 ) {
					 modelAndView.addObject("msg", "게시글 수정 성공");
					 modelAndView.addObject("location", "/admin/productMgmtView?no=" + product.getNo()); 
				 } else {
					 modelAndView.addObject("msg", "게시글 수정 실패");
					 modelAndView.addObject("location", "/admin/productMgmtUpdate?no=" + product.getNo()); 		 
				 }
				 
				 modelAndView.setViewName("page/common/msg");

				return modelAndView;
		 }
		 
		 
		 // 관리자_제품삭제
		 @GetMapping("/productMgmtDelete")
		 @ResponseBody
		 public ModelAndView delete(ModelAndView modelAndView,
				 					@RequestParam int no) {
			 int result = 0;
			 Product product = null;
			 
			 product = adminService.getProductAdminBoardByNo(no);
			 result = adminService.delete(product.getNo());
			 
			 if (result > 0) {
					modelAndView.addObject("msg", "게시글이 정상적으로 삭제되었습니다.");
					modelAndView.addObject("location", "/admin/productMgmtList");			
				} else {				
					modelAndView.addObject("msg", "게시글 삭제에 실패하였습니다.");
					modelAndView.addObject("location", "/admin/productMgmtView?no=" + product.getNo());				
				}
			 
			 modelAndView.setViewName("page/common/msg");
				
				return modelAndView;
		 }
		 

		
	      
//	       // 관리자_옵션 상세보기
//	       @GetMapping("/productMgmtDetailView")
//	       @ResponseBody
//	         public ModelAndView getProductDetailView(ModelAndView modelAndView, @RequestParam("no") int no) {
//	             Product product = null;
//
//	             product = adminService.getProductDetailViewByNO(no);
//	             
//	             System.out.println("product값 담기는지 확인 : " + product);
//
//	            modelAndView.addObject("pageName", "productMgmtDetailView");
//	            modelAndView.addObject("product", product);
//	            modelAndView.setViewName("page/admin/productMgmtDetailView");
//
//	             return modelAndView;
//
//	         }
	       
//		 @GetMapping("/productMgmtDetailView")
//		 public ResponseEntity<Product> getProductDetailView(@RequestParam("no") int no) {
//		     Product product = adminService.getProductDetailViewByNO(no);
//		     
//		     System.out.println("product값 : " + product);
//
//		     if (product != null) {
//		         return new ResponseEntity<>(product, HttpStatus.OK);
//		     } else {
//		         return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//		     }
//		 }
		 
		 @GetMapping("/productMgmtDetailView")
		 public ModelAndView getProductDetailView(ModelAndView modelAndView,@RequestParam(value="no", required=false) Integer no) {
		     Product product = adminService.getProductDetailViewByNO(no);
		     
		     System.out.println("product값★ : " + product);

		     
		     
	        modelAndView.addObject("pageName", "productMgmtDetailView");
            modelAndView.addObject("product", product);
            modelAndView.setViewName("page/admin/productMgmtDetailView");

            return modelAndView;
		     
		 }
	    






		
		
		
		// 회원 목록 가져오기
		@GetMapping("/memberMgmt")
		@ResponseBody
		public ModelAndView memberlist (ModelAndView modelAndView, @RequestParam(defaultValue = "1") int page, 
										@RequestParam(required = false) String searchType) {
			
			int listcount = 0;
			PageInfo pageInfo = null;
			List<Member> memberlists = null;
			
			log.info("@@@@ 검색 값: {}", searchType);
			log.info("Page : {}", page);
			log.info("ListCount : {}", listcount);
			
			// 검색값이 있는 경우
			 if (searchType != null) {
				 listcount = memberService.selectmembercountvalue(searchType);
				 pageInfo = new PageInfo(page, 10, listcount, 10);
				 memberlists = memberService.getmemberlistsvalue(searchType, pageInfo);
				 
				 modelAndView.addObject("pageName", "memberMgmt");
				 modelAndView.addObject("pageInfo", pageInfo);
				 modelAndView.addObject("memberlists", memberlists);
				 modelAndView.addObject("searchType", searchType); // 페이징 처리를 위해 searchType값을 넘겨준다. 
			
			// 검색값이 없는 경우
			} else {
				listcount = memberService.selectmembercount();
				pageInfo = new PageInfo(page, 10, listcount, 10);
				memberlists = memberService.getmemberlists(pageInfo);
				
				modelAndView.addObject("searchType", searchType);
				modelAndView.addObject("pageName", "memberMgmt");
				modelAndView.addObject("pageInfo", pageInfo);
				modelAndView.addObject("memberlists", memberlists);
			
			}
			 
			 log.info("ListCount : {}", listcount);
			 log.info("MemberLists : {}", memberlists);
			 
			 modelAndView.setViewName("page/admin/memberMgmt");
			 
			 return modelAndView;
		}


		// 사용계정 --> 휴면계정으로 바꾸기
		
		 @PostMapping("/updatememberstatus")
		 public String updateMemberStatus(@RequestParam("no") int no) {
			    int result = memberService.updatememberstatus("N", no);

			    if (result > 0) {
			        
			        return "redirect:/admin/memberMgmt";
			    } else {
			        
			        return "redirect:/admin/memberMgmt?error=사용자%20상태%20변경에%20실패했습니다.";
			    }
			}
		 
		// 휴면계정 --> 사용계정으로 바꾸기
		 
		 @PostMapping("/activateMember")
		 public String activateMember(@RequestParam("no") int no) {
		     int result = memberService.activateMember("Y", no);

		     if (result > 0) {
		    	 
		         return "redirect:/admin/memberMgmt";
		     } else {
		    	 
		         return "redirect:/admin/boardMgmt?error=사용자%20상태%20변경에%20실패했습니다."; 
		     }
		 }
		 

		 
		// 게시물 목록 조회
				 @GetMapping("/boardMgmt")
				 @ResponseBody
				 public ModelAndView boardMgmtlist(ModelAndView modelAndView, @RequestParam(defaultValue = "1") int page,
						 				 	 @RequestParam(required = false) String searchType, @RequestParam(required = false) String keyWord) {
					 
					 int listCount = 0;
					 PageInfo pageInfo = null;
					 List<Board> boardLists = null;
					 
			
					 // 검색값이 있는 경우
					 if (searchType != null ) {
						 listCount = boardService.selectboardcountsearch(searchType, keyWord);
						 pageInfo = new PageInfo(page, 10, listCount, 10);
						 boardLists = boardService.getboardlistsearch(pageInfo, searchType, keyWord);
						 
						 modelAndView.addObject("pageName", "boardMgmt");
						 modelAndView.addObject("pageInfo", pageInfo);
						 modelAndView.addObject("searchType", searchType); 
						 modelAndView.addObject("keyWord", keyWord);
						 modelAndView.addObject("boardLists", boardLists);
						 
						log.info("@@@@ 검색 값: {}", searchType);
						log.info("Page : {}", page);
						log.info("ListCount : {}", boardLists);
					
					// 검색값이 없는 경우
					} else {
						listCount = boardService.selectboardcount();
						pageInfo = new PageInfo(page, 10, listCount, 10);
						boardLists = boardService.getboardlist(pageInfo);
						
						modelAndView.addObject("pageName", "boardMgmt");
						modelAndView.addObject("pageInfo", pageInfo);
						modelAndView.addObject("boardLists", boardLists);
						modelAndView.addObject("searchType", searchType);
						
						log.info("@@@@ 검색 값: {}", searchType);
						log.info("Page : {}", page);
						log.info("ListCount : {}", boardLists);
						
					
					}
					 modelAndView.setViewName("page/admin/boardMgmt");
					 
					 return modelAndView;
				}
		 
		 // 삭제
		 @PostMapping("/updatboardstatus")
		 public String updatboardstatus(@RequestParam("no") int no) {
			 int result = boardService.updatboardstatus("N", no);
			 
			 if (result > 0) {
				
				 return "redirect:/admin/boardMgmt";
			}else {
				return "redirect:/admin/boardMgmt?error=사용자%20상태%20변경에%20실패했습니다.";
			}
		 }
		 
		 // 계시
		 @PostMapping("activateboard")
		 public String activateboard(@RequestParam("no") int no) {
			 int result = boardService.activateboard("Y", no);
			 
			 if (result > 0) {
				return "redirect:/admin/boardMgmt";
			}else {
				return "redirect:/admin/boardMgmt?error=사용자%20상태%20변경에%20실패했습니다.";
			}	 
		 }
		 
		 // 댓글 관리자 페이지
		 @GetMapping("replyMgmt")
		 @ResponseBody
		 public ModelAndView repluMgmt (ModelAndView modelAndView,@RequestParam(defaultValue = "1")int page, 
				 						@RequestParam(required = false)String searchType) {
			 
			 int listcount = 0;
			 PageInfo pageInfo = null;
			 List<Board> replyLists = null;
			 
			log.info("@@@@ 검색 값: {}", searchType);
			log.info("Page : {}", page);
			log.info("ListCount : {}", listcount);	
			
			// 검색값 O
			if (searchType != null) {
				listcount = boardService.selectreplycountsearch(searchType);
				pageInfo = new PageInfo(page, 10, listcount, 10);
				replyLists = boardService.getreplylistsearch(searchType, pageInfo);
				
				 modelAndView.addObject("pageName", "replyMgmt");
				 modelAndView.addObject("pageInfo", pageInfo);
				 modelAndView.addObject("replyLists", replyLists);
				 modelAndView.addObject("searchType", searchType);
			}else {
				listcount = boardService.selectreplycount();
				pageInfo = new PageInfo(page, 10, listcount, 10);
				replyLists = boardService.getreplylists(pageInfo);
				
				modelAndView.addObject("searchType", searchType);
				modelAndView.addObject("pageName", "replyMgmt");
				modelAndView.addObject("pageInfo", pageInfo);
				modelAndView.addObject("replyLists", replyLists);
			}
			 log.info("ListCount : {}", listcount);
			 log.info("MemberLists : {}", replyLists);
			 
			 modelAndView.setViewName("page/admin/replyMgmt");
			 
			 return modelAndView;
		 }
		 
		 


		 
		
		 

				

		 

//			// 관리자페이지_프로그램관리로 이동
//			@GetMapping("/replyMgmt")
//			@ResponseBody
//			public ModelAndView replyMgmt (ModelAndView modlAndView) {
//				
//				modlAndView.addObject("pageName", "replyMgmt");
//				modlAndView.setViewName("page/admin/replyMgmt");
//				
//				return modlAndView;
//			}


}
