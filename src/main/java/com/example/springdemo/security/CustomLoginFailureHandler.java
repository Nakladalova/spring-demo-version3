package com.example.springdemo.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.example.springdemo.User;
//import com.example.springdemo.service.CustomUserDetailsService;
import com.example.springdemo.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

@Component
public class CustomLoginFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {
        String username = request.getParameter("username");
        User user = customUserDetailsService.getUserByUsername(username);

        if (user != null) {
            if (user.isEnabled() && user.isAccountNonLocked()) {
                if (user.getFailedAttempt() < CustomUserDetailsService.MAX_FAILED_ATTEMPTS - 1) {
                    customUserDetailsService.increaseFailedAttempts(user);
                } else {
                    customUserDetailsService.lock(user);
                    exception = new LockedException("Your account has been locked due to 3 failed attempts."
                            + " It will be unlocked after 24 hours.");
                }
            } else if (!user.isAccountNonLocked()) {
                if (customUserDetailsService.unlockWhenTimeExpired(user)) {
                    exception = new LockedException("Your account has been unlocked. Please try to login again.");
                }
            }

        }
        super.setDefaultFailureUrl("/login?error");
        super.onAuthenticationFailure(request, response, exception);
    }

}
