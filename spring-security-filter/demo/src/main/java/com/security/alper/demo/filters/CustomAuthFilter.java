package com.security.alper.demo.filters;

import com.security.alper.demo.authentication.CustomAuthentication;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.http.HttpResponse;

@Component
@AllArgsConstructor
public class CustomAuthFilter implements Filter {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String authorization = httpRequest.getHeader("Authorization");
        CustomAuthentication authentication = new CustomAuthentication(authorization,null);

        try {
            Authentication result =authenticationManager.authenticate(authentication);

            if(result.isAuthenticated()){
                SecurityContextHolder.getContext().setAuthentication(result);
                chain.doFilter(request,response);
            }else{
                httpResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
            }

        }
        catch (AuthenticationException e ){
            httpResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
            e.printStackTrace();
        }


    }
}
