package com.cydeo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

@Configuration
public class AuthSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        Set <String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities()); //whenever authorization is done, Spring is capturing user's role and when logIn is done, Spring knows which role user is accessing program.
        if (roles.contains("Admin")) {
            response.sendRedirect("/user/create");
        }

        if (roles.contains("Manager")){
            response.sendRedirect("/task/create");
        }

        if (roles.contains("Employee")) {
            response.sendRedirect("/task/employee/pending-tasks");
        }

    }
}