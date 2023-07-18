package it.bitprojects.store.handlers;

import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class PippoInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
		// Verifica se l'interceptor è stato già eseguito per questa richiesta
//		if (request.getAttribute("PippoInterceptor") == null) {
//			String pippo = request.getHeader("pippo");
//			if (pippo != null) {
//				System.out.println("Valore di pippo: " + pippo);
//			}
//			// Imposta l'attributo per segnalare che l'interceptor è stato eseguito
//			request.setAttribute("PippoInterceptor", true);
//		}
		String pippo = request.getHeader("pippo");
		if (pippo != null) {

			System.out.println("Valore di pippo: " + pippo);
		}

		return true;
	}

//	@Override
//	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
//			throws Exception {
//		String pippo = request.getHeader("pippo");
//		if (pippo != null) {
//			System.out.println("Valore di pippo: " + pippo);
//		}
//		HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
//	}

}
