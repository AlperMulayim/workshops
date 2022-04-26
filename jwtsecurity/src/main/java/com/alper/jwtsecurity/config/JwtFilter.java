package com.alper.jwtsecurity.config;

import com.alper.jwtsecurity.tokens.MyJwtService;
import com.alper.jwtsecurity.users.MyUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtFilter  extends OncePerRequestFilter {

    @Autowired
    private MyJwtService jwtService;

    @Autowired
    private MyUserDetails userDetails;


    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String authorization = request.getHeader("Authorization");

        if(authorization != null && authorization.startsWith("Bearer ")){
            String token = authorization.substring(7);
            String username = jwtService.extractUserName(token);
            UserDetails userDetail = userDetails.loadUserByUsername(username);

            if(jwtService.validateToken(token,userDetail)){
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken
                        = new UsernamePasswordAuthenticationToken(
                                userDetail,null,userDetail.getAuthorities()
                );

                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

            }

        }
        filterChain.doFilter(request,response);
    }
}
