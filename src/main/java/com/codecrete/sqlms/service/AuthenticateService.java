package com.codecrete.sqlms.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * TODO: Deprecate and use UserService as UserDetailsService
 */
@Service
public class AuthenticateService implements UserDetailsService {
    
    private static final Logger LOG = LogManager.getLogger(AuthenticateService.class);

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return this.userService.getUser(email);
    }
}
