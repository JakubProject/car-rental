package pl.carrental.service;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class SecurityService {

    private AuthenticationManager authenticationManager;
    private UserDetailsService userDetailsService;

    public SecurityService(AuthenticationManager authenticationManager, UserDetailsService userDetailsService) {
        super();
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
    }

    public boolean isAuthenticated() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || AnonymousAuthenticationToken.class.isAssignableFrom(authentication.getClass())) {
            return false;
        }
        return authentication.isAuthenticated();
    }


}
