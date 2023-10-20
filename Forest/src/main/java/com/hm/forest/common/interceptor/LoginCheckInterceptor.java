package com.hm.forest.common.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.hm.forest.member.model.service.MemberService;
import com.hm.forest.member.model.vo.Member;

import lombok.extern.slf4j.Slf4j;

/*
 * 인터셉터
 *   - 컨트롤러에 들어오는 요청과 응답을 가로채는 역할을 한다.
 *   - 인터셉터를 구현하기 위해서는 HandlerInterceptor 인터페이스를 구현해야 한다.
 *   
 * 필터와의 차이점
 *   - 필터는 서블릿 실행 전에 실행된다. (web.xml)
 *   - 인터셉터는 디스패처 서블릿 수행 후 컨트롤러에 요청을 넘기기 전에 실행된다. (servlet-context.xml)
 */
@Slf4j
public class LoginCheckInterceptor implements HandlerInterceptor {
	@Autowired
	private MemberService service;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// 컨트롤러가 실행되기 전에 필요한 작업을 할 수 있는 메소드
		// 반환값이 false일 경우 컨트롤러를 실행하지 않는다.
				
		log.info("preHandle() 호출");
		log.info(service.toString());
		
		Member loginMember = (Member) request.getSession().getAttribute("loginMember");
		
		if (loginMember == null) {
			request.setAttribute("msg", "로그인 후 이용이 가능합니다.");
			request.setAttribute("location", "/");
			request.getRequestDispatcher("page/common/msg")
				   .forward(request, response);
			
			return false;
		}
		
		return HandlerInterceptor.super.preHandle(request, response, handler);
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// 컨트롤러가 실행된 후에 필요한 작업을 할 수 있는 메소드
		
		log.info("postHandle() 호출");				
		
		HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// 컨트롤러의 처리가 끝나고 화면 처리까지 모두 끝나면 실행되는 메소드
		
		log.info("afterCompletion() 호출");				
		
		HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
	}
	
	
	
	
	
	
	
	
}
