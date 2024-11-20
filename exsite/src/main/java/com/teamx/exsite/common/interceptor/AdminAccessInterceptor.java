package com.teamx.exsite.common.interceptor;

import java.net.URLEncoder;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.teamx.exsite.model.dto.user.UserDTO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Component
public class AdminAccessInterceptor implements HandlerInterceptor{
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		HttpSession session = request.getSession();
		UserDTO loginUser = (UserDTO)session.getAttribute("loginUser");
		String contextPath = request.getContextPath();
		if(loginUser == null) {
			String url = request.getRequestURI();
			String query = request.getQueryString();
			String encodedUrl = null;
			
			if(query == null) {
				encodedUrl=URLEncoder.encode(url, "utf-8");
			} else {
				encodedUrl=URLEncoder.encode(url+"?"+query, "utf-8");
			}
			
			
			response.sendRedirect(contextPath+"/login?url="+encodedUrl);
			return false;
		} else if(loginUser.getGrade() != 'A') {
			response.sendRedirect(contextPath+"/");
			return false;
		}
		
		return true;
	}

}