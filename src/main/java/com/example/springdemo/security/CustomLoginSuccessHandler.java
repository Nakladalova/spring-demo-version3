package com.example.springdemo.security;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.example.springdemo.service.CustomUserDetails;
import com.example.springdemo.model.User;
import com.example.springdemo.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component
public class CustomLoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        CustomUserDetails customUserDetails =  (CustomUserDetails) authentication.getPrincipal();
        User user = customUserDetails.getUser();

        if (user.getFailedAttempt() > 0) {
            customUserDetailsService.resetFailedAttempts(user.getUsername());
        }
        super.onAuthenticationSuccess(request, response, authentication);
    }

}
