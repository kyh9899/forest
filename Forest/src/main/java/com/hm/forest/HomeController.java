package com.hm.forest;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.hm.forest.member.model.vo.Member;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@RestController
public class HomeController {
	
	// home으로 이동
	@GetMapping("/")
	public ModelAndView home (ModelAndView modelAndView, @AuthenticationPrincipal Member loginMember) {
		modelAndView.addObject("pageName", "home");
		modelAndView.addObject("loginMember", loginMember);
		modelAndView.setViewName("page/home");
    
        return modelAndView;
	}
	
	// 캠페인 페이지_aesop으로 이동
	@GetMapping("/campaign/aesop")
	public ModelAndView aseop (ModelAndView modelAndView) {
		modelAndView.addObject("pageName", "aesop");
		modelAndView.setViewName("page/campaign/aesop");
    
        return modelAndView;
	}
	
	// 캠페인 페이지_biotherm으로 이동
	@GetMapping("/campaign/biotherm")
	public ModelAndView biotherm (ModelAndView modelAndView) {
		modelAndView.addObject("pageName", "biotherm");
		modelAndView.setViewName("page/campaign/biotherm");
		
		return modelAndView;
	}
	
	// 캠페인 페이지_brita으로 이동
	@GetMapping("/campaign/brita")
	public ModelAndView brita (ModelAndView modelAndView) {
		modelAndView.addObject("pageName", "brita");
		modelAndView.setViewName("page/campaign/brita");
		
		return modelAndView;
	}
	
	// 캠페인 페이지_melixir으로 이동
	@GetMapping("/campaign/melixir")
	public ModelAndView melixir (ModelAndView modelAndView) {
		modelAndView.addObject("pageName", "melixir");
		modelAndView.setViewName("page/campaign/melixir");
		
		return modelAndView;
	}
	
	// 캠페인 페이지_boonbastick으로 이동
	@GetMapping("/campaign/boonbastick")
	public ModelAndView boonbastick (ModelAndView modelAndView) {
		modelAndView.addObject("pageName", "boonbastick");
		modelAndView.setViewName("page/campaign/boonbastick");
		
		return modelAndView;
	}
	
	// 캠페인 페이지_mbci으로 이동
	@GetMapping("/campaign/mbci")
	public ModelAndView mbci (ModelAndView modelAndView) {
		modelAndView.addObject("pageName", "mbci");
		modelAndView.setViewName("page/campaign/mbci");
		
		return modelAndView;
	}
	
	// 이벤트 페이지_zeroweist으로 이동
	@GetMapping("/event/zeroweist")
	public ModelAndView zeroweist (ModelAndView modelAndView) {
		modelAndView.addObject("pageName", "zeroweist");
		modelAndView.setViewName("page/event/zeroweist");
    
        return modelAndView;
	}
	
	// 이벤트 페이지_voive으로 이동
	@GetMapping("/event/voive")
	public ModelAndView voive (ModelAndView modelAndView) {
		modelAndView.addObject("pageName", "voive");
		modelAndView.setViewName("page/event/voive");
		
		return modelAndView;
	}
	
	// 이벤트 페이지_vokit으로 이동
	@GetMapping("/event/bokit")
	public ModelAndView vokit (ModelAndView modelAndView) {
		modelAndView.addObject("pageName", "bokit");
		modelAndView.setViewName("page/event/bokit");
		
		return modelAndView;
	}
	
	// 이벤트 페이지_maysum으로 이동
	@GetMapping("/event/maysum")
	public ModelAndView maysum (ModelAndView modelAndView) {
		modelAndView.addObject("pageName", "maysum");
		modelAndView.setViewName("page/event/maysum");
		
		return modelAndView;
	}
	
	// about으로 이동
	@GetMapping("/about")
	public ModelAndView about (ModelAndView modelAndView,  @AuthenticationPrincipal Member loginMember) {
		
		modelAndView.addObject("pageName", "about");
		modelAndView.addObject("loginMember", loginMember);
		modelAndView.setViewName("page/about");
		
        return modelAndView;
	}
	
	// 참여하기로 이동
	@GetMapping("/program")
	public ModelAndView program (ModelAndView modelAndView, @AuthenticationPrincipal Member loginMember) {
		
		modelAndView.addObject("pageName", "program");
		modelAndView.addObject("loginMember", loginMember);
		modelAndView.setViewName("page/program");
		
		return modelAndView;
	}
	
	
	
	
	// 로그인 페이지로 이동 
	@GetMapping("/login")
	public ModelAndView login (ModelAndView modelAndView) {
		
		modelAndView.addObject("pageName", "login");
		modelAndView.setViewName("page/login");
		
		return modelAndView;
	}
	
	// 회원가입 페이지로 이동 
	@GetMapping("/enroll")
	public ModelAndView enroll (ModelAndView modelAndView) {
		
		modelAndView.addObject("pageName", "enroll");
		modelAndView.setViewName("page/enroll");
		
		return modelAndView;
	}

	

	
	@GetMapping("/myPage")
	public ModelAndView myPage (ModelAndView modelAndView, @AuthenticationPrincipal Member loginMember) {
		
		modelAndView.addObject("pageName", "myPage");
		modelAndView.addObject("loginMember", loginMember);
		modelAndView.setViewName("page/myPage/myPage");
		
		return modelAndView;
	}


	// 제품 페이지로 이동
	@GetMapping("/kitchen")
	public ModelAndView products (ModelAndView modelAndView, @AuthenticationPrincipal Member loginMember) {
		
		modelAndView.addObject("pageName", "kitchen");
		modelAndView.addObject("loginMember", loginMember);
		modelAndView.setViewName("page/products/kitchen");
		
		return modelAndView;
	}
	
	


}
