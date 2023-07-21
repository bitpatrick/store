package it.bitprojects.store.config;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class IPLoggingInterceptorHandler implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String ipAddress = request.getRemoteAddr();
		System.out.println("Sto chiamando l'interceptor IPLoggingInterceptorHandler");
		System.out.println(ipAddress);
		return true;
	}

}
