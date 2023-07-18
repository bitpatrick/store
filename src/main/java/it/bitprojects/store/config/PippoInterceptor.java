package it.bitprojects.store.config;

import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class PippoInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
		String pippo = request.getHeader("pippo");
		if (pippo != null) {

			System.out.println("Valore di pippo: " + pippo);
		}

		return true;
	}

}
