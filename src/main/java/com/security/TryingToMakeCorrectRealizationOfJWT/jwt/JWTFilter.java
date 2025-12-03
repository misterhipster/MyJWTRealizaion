package com.security.TryingToMakeCorrectRealizationOfJWT.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import com.auth0.jwt.exceptions.JWTVerificationException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JWTFilter extends OncePerRequestFilter {
    private final CustomUserDetailService customUserDetailService;
    private final JWTService jwtService;


        @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && !authHeader.isBlank() && authHeader.startsWith("Bearer ")) {
            String jwt = authHeader.substring(7);
            if (jwt == null || jwt.isBlank()) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid JWT Token in Bearer Header");
            } else {
                try {
                    String email = jwtService.validateTokenAndRetrieveSubject(jwt);
                    UserDetails userDetails = customUserDetailService.loadUserByUsername(email);
                    UsernamePasswordAuthenticationToken authtoken = new UsernamePasswordAuthenticationToken(email, userDetails.getPassword(), userDetails.getAuthorities());

                    if (SecurityContextHolder.getContext().getAuthentication() == null) {
                        SecurityContextHolder.getContext().setAuthentication(authtoken);
                    }
                } catch (JWTVerificationException e) {
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid JWT Token");
                }
            }
        }

        filterChain.doFilter(request, response);
    }

//    @Override
//    protected void doFilterInternal(HttpServletRequest request,
//                                    HttpServletResponse response,
//                                    FilterChain filterChain)
//            throws ServletException, IOException {
//
//        String path = request.getServletPath();
//
//        System.out.println("THIS IS PATH% "+path);
//
//
//        System.out.println("=== JWT Filter Debug ===");
//        System.out.println("Path: " + path);
//        System.out.println("Method: " + request.getMethod());
//        System.out.println("Auth Header: " + request.getHeader("Authorization"));
//
//        // Явно пропускаем публичные endpoints
//        if (path.startsWith("/api/auth/") ||
//                path.startsWith("/api/debug/") ||
//                path.startsWith("/h2-console")) {
//            System.out.println("Skipping JWT check for public endpoint");
//            filterChain.doFilter(request, response);
//            return;
//        }
//
//        // Для остальных endpoints проверяем JWT
//        String authHeader = request.getHeader("Authorization");
//
//
//        if (authHeader != null && !authHeader.isBlank() && authHeader.startsWith("Bearer ")) {
//            String jwt = authHeader.substring(7);
//            if (jwt == null || jwt.isBlank()) {
//                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid JWT Token in Bearer Header");
//                return;
//            } else {
//                try {
//                    String email = jwtService.validateTokenAndRetrieveSubject(jwt);
//                    UserDetails userDetails = customUserDetailService.loadUserByUsername(email);
//                    UsernamePasswordAuthenticationToken authtoken =
//                            new UsernamePasswordAuthenticationToken(email, userDetails.getPassword(), userDetails.getAuthorities());
//
//                    if (SecurityContextHolder.getContext().getAuthentication() == null) {
//                        SecurityContextHolder.getContext().setAuthentication(authtoken);
//                    }
//                } catch (JWTVerificationException e) {
//                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid JWT Token");
//                    return;
//                }
//            }
//        }
//
//        // Если нет токена, но это публичный API - пропускаем
//
////        else if (path.startsWith("/api/")) {
////            filterChain.doFilter(request, response);
////            return;
////        }
//
//        // Если нет токена, но это публичный API - пропускаем
////        else if (path.startsWith("/api/user/") || path.startsWith("/api/auth/")) {
////            filterChain.doFilter(request, response);
////            return;
////        }
//        // Если нет токена, но это публичный API - пропускаем
////        else if (path.startsWith("/api/public/")) {
////            filterChain.doFilter(request, response);
////            return;
////        }
//        // Если нет токена и это защищенный endpoint - возвращаем 401
//        else {
//            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Missing JWT Token");
//            return;
//        }
//
//        filterChain.doFilter(request, response);
//    }
}
