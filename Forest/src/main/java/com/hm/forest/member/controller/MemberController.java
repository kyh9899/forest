package com.hm.forest.member.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.hm.forest.member.model.service.MemberService;
import com.hm.forest.member.model.vo.Member;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@RestController
// Model 객체에 loginMember라는 키값으로 Attribute가 추가되면 
// 해당 Attribute를 세션 영역에 추가하는 어노테이션이다.
public class MemberController {
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Autowired
	private MemberService memberService;
	
	@PostMapping("/enroll")
	public ModelAndView enroll(ModelAndView modelAndView, Member member) {
		
		int result = memberService.save(member);
		
		if (result > 0) {
			modelAndView.addObject("msg", "회원 가입 성공");
			modelAndView.addObject("location", "/");
		} else {
			modelAndView.addObject("msg", "회원 가입 실패");
			modelAndView.addObject("location", "/enroll");			
		}
		
		modelAndView.setViewName("page/common/msg");
		
		return modelAndView;
	}
	
	@GetMapping("/member/idCheck")
	public ResponseEntity<Map<String, Boolean>> idCheck(@RequestParam String userId) {
		Map<String, Boolean> map = new HashMap<>();
		
		map.put("duplicate", memberService.isDuplicateId(userId));
		
		return ResponseEntity.ok()
							 .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
							 .body(map);
	}
	
	@GetMapping("/member/passwordCheck")
	@ResponseBody
	public Map<String, Boolean> passwordCheck(@RequestParam String userPassword) {
	    Map<String, Boolean> response = new HashMap<>();
	    
	    log.info("비밀번호 확인 요청");

	    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    Member member = (Member) authentication.getPrincipal();

	    if (passwordEncoder.matches(userPassword, member.getPassword())) {
	        response.put("duplicate", true);
	    } else {
	        response.put("duplicate", false);
	    }

	    return response;
	}
	
	// 회원 정보 수정	
	@PostMapping("/myPage")
    public ModelAndView changePassword(ModelAndView modelAndView,
            @RequestParam("currentPassword") String currentPassword,
            @RequestParam("newPassword") String newPassword,
            @RequestParam("confirmPassword") String confirmPassword,
	        @RequestParam("pcode") String pcode,
	        @RequestParam("address1") String address1,
	        @RequestParam("address2") String address2) {
 
            log.info("비밀번호 변경 요청");
           
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            Member member = (Member) authentication.getPrincipal();
           
            if (!passwordEncoder.matches(currentPassword, member.getPassword())) {
            modelAndView.addObject("msg", "현재 비밀번호가 올바르지 않습니다.");
            modelAndView.addObject("location", "/myPage");
            modelAndView.setViewName("page/common/msg");
            return modelAndView;
            }
            
            if (!newPassword.equals(confirmPassword)) {
            modelAndView.addObject("msg", "새 비밀번호와 비밀번호 확인 값이 일치하지 않습니다.");
            modelAndView.addObject("location", "/myPage");
            modelAndView.setViewName("page/common/msg");
            return modelAndView;
            }
            
            boolean passwordChanged = memberService.changePassword(member.getNo(), newPassword, pcode, address1, address2);
            
            if (passwordChanged) {
            modelAndView.addObject("msg", "회원정보가 변경되었습니다.");
            modelAndView.addObject("location", "/");
            
            } else {
            modelAndView.addObject("msg", "회원정보 변경에 실패했습니다.");
            modelAndView.addObject("location", "/myPage");
            
           } 

modelAndView.setViewName("page/common/msg");

return modelAndView;
}

	
	// 회원 삭제
	@GetMapping("/delete")
	public ModelAndView delete(HttpServletRequest request, ModelAndView modelAndView) {
	    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

	    if (authentication != null && authentication.getPrincipal() instanceof Member) {
	        Member loginMember = (Member) authentication.getPrincipal();

	        int result = memberService.delete(loginMember.getNo());

	        if (result > 0) {
	            // 사용자를 로그아웃합니다.
	            new SecurityContextLogoutHandler().logout(request, null, null);
	            modelAndView.addObject("msg", "정상적으로 탈퇴되었습니다.");
	            modelAndView.addObject("location", "/");
	        } else {
	            modelAndView.addObject("msg", "탈퇴에 실패하였습니다.");
	            modelAndView.addObject("location", "/myPage");
	        }

	        modelAndView.setViewName("page/common/msg");
	    }

	    return modelAndView;
	}
	
}









