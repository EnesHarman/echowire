package com.echowire.article.security;

import com.echowire.article.core.threadlocal.ECThreadLocal;
import com.echowire.article.core.threadlocal.UserInfo;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class HeaderResolverFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        try {
            var roles = request.getHeader("X-User-Roles");
            var userId = request.getHeader("X-User-Id");
            var email = request.getHeader("X-User-Email");
            if(userId != null && roles != null ) {
                var auth = new UsernamePasswordAuthenticationToken(
                        userId, null,
                        Arrays.stream(roles.split(",")).toList().stream().map(role -> "ROLE_" + role).map(SimpleGrantedAuthority::new).toList()
                );
                auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(auth);
                ECThreadLocal.set(new UserInfo(userId, email));
            }
            chain.doFilter(request, response);
        } finally {
            ECThreadLocal.unset();
        }
    }
}
