package com.example.gestionproduit.filter;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.gestionproduit.service.CustomUserDetailsService;
import com.example.gestionproduit.util.JwtUtil;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtRequestFilter extends OncePerRequestFilter {

    private JwtUtil jwtUtil;
    private CustomUserDetailsService userDetailsService;

    public JwtRequestFilter(JwtUtil jwtUtil, CustomUserDetailsService userDetailsService) {
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        final String authorizationHeader = request.getHeader("Authorization");

        String email = null;
        String jwt = null;

        // Extract JWT and email from the Authorization header
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwt = authorizationHeader.substring(7);
            email = jwtUtil.extractEmail(jwt);
        }

        // Authenticate the user if email is present and not already authenticated
        if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
        	try {
        		UserDetails userDetails = this.userDetailsService.loadUserByUsername(email);
        		if (jwtUtil.validateToken(jwt, userDetails)) {
                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());
                    usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                }
        	} catch(UsernameNotFoundException e) {
    			sendErrorResponse(response, HttpServletResponse.SC_NOT_FOUND, e.getMessage());
    			return;
        	}
        }

        // Check if the email is allowed to access product endpoints
		if (request.getRequestURI().startsWith("/products") && !"admin@admin.com".equals(email)) {
			sendErrorResponse(response, HttpServletResponse.SC_FORBIDDEN, "Access Denied");
			return;
		}

        chain.doFilter(request, response);
    }
    


    private void sendErrorResponse(HttpServletResponse response, int status, String message) throws IOException {
    	response.setStatus(status);
    	response.getWriter().write("{\"error\": \"" + message + "\"}");
    	response.setContentType("application/json");
    	response.setCharacterEncoding("UTF-8");
    	response.getWriter().flush();
    }

}