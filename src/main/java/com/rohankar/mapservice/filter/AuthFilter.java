package com.rohankar.mapservice.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import com.rohankar.mapservice.exception.AuthorizationException;
import com.rohankar.mapservice.service.AuthService;

/**
 * @author Sagar Rohankar
 */
@Component
public class AuthFilter extends GenericFilterBean {

    @Autowired
    private AuthService authService;

    @Override
    public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain chain)
        throws IOException, ServletException {
        if (((HttpServletRequest)request).getMethod().equalsIgnoreCase("DELETE")) {

            final String authToken = ((HttpServletRequest)request).getHeader("Authorization");
            if (authToken == null) {
                throw new AuthorizationException("'Authorzation' header missing");
            }

            authService.doAuthorization(authToken);
        }
        chain.doFilter(request, response);
    }

}
