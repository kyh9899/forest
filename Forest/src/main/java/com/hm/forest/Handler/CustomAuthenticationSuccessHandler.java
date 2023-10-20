package com.hm.forest.Handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {
        // 로그인 성공 시 처리할 내용을 여기에 구현합니다.
        // 예를 들어, 로그인한 사용자를 홈 페이지로 리다이렉트하거나 특정 동작을 수행할 수 있습니다.
        response.sendRedirect("/");
    }
}
