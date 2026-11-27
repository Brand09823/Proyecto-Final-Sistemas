package com.proyectofinal.sistemas.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * REQ-05: registra en consola cada petición HTTP (método, ruta, código de
 * respuesta y tiempo). Las respuestas 4xx se registran como WARN y las 5xx
 * como ERROR, para poder monitorear errores. Las consultas SQL se muestran
 * con spring.jpa.show-sql=true.
 */
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class RequestLoggingFilter extends OncePerRequestFilter {

    private static final Logger log = LoggerFactory.getLogger(RequestLoggingFilter.class);

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String uri = request.getRequestURI();
        // No llenar la consola con los recursos estáticos de Swagger y H2
        return uri.startsWith("/swagger-ui") || uri.startsWith("/v3/api-docs") || uri.startsWith("/h2-console");
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {

        long inicio = System.currentTimeMillis();
        try {
            filterChain.doFilter(request, response);
        } catch (ServletException | IOException | RuntimeException exception) {
            log.error("{} {} -> excepción no controlada: {}",
                    request.getMethod(), request.getRequestURI(), exception.getMessage());
            throw exception;
        }

        long duracion = System.currentTimeMillis() - inicio;
        int status = response.getStatus();
        if (status >= 500) {
            log.error("{} {} -> {} ({} ms)", request.getMethod(), request.getRequestURI(), status, duracion);
        } else if (status >= 400) {
            log.warn("{} {} -> {} ({} ms)", request.getMethod(), request.getRequestURI(), status, duracion);
        } else {
            log.info("{} {} -> {} ({} ms)", request.getMethod(), request.getRequestURI(), status, duracion);
        }
    }
}
