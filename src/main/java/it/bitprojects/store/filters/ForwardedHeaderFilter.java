package it.bitprojects.store.filters;

import java.io.IOException;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;

public class ForwardedHeaderFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Inizializzazione del filtro
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        if (request instanceof HttpServletRequest) {
            HttpServletRequest httpRequest = (HttpServletRequest) request;
            String forwardedHeader = httpRequest.getHeader("Forwarded");
            if (forwardedHeader != null) {
                // Estrai le informazioni desiderate dall'intestazione Forwarded
                // e sovrascrivi le informazioni di richiesta appropriate
            }
        }

        // Passa la richiesta al prossimo filtro o al servlet
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        // Pulizia delle risorse del filtro
    }
}

