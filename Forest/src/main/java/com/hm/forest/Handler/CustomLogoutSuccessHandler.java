package com.hm.forest.Handler;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CustomLogoutSuccessHandler implements LogoutSuccessHandler {

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        // 로그아웃 성공 시 수행할 동작을 여기에 추가합니다.
        // 예를 들어, 로그아웃 후 특정 페이지로 리다이렉트할 수 있습니다.
        response.sendRedirect("/");
    }
}