package com.codecrete.sqlms.service;

import com.codecrete.domain.model.User;
import com.codecrete.sqlms.repository.UserRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

/**
 *
 */
@Service
public class UserService { //implements UserDetailsService {
    
    private static final Logger LOG = LogManager.getLogger(UserService.class);

    @Autowired
    private UserRepository userRepository;

//    @Override
//    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
//       return getUser(email);
//    }

    @PreAuthorize("hasRole('ROLE_MULE')")
    public User getUser(String email) {

        User user = this.userRepository.getByEmail(email);

        if (user == null) {
            LOG.error("Error loading User record");
            throw new RuntimeException("Error loading User record");
        }
        else LOG.trace(String.format("Loaded User record: %s", user.getEmail()));

        return user;
    }

    /**
     * Persist job to database
     *
     * @param user The @{code User} object to persist
     * @return The updated @{code User} object
     */
    @PreAuthorize("hasRole('ROLE_MULE')")
    public User saveUser(User user) {

        user = this.userRepository.save(user);

        if (user == null) {
            LOG.error("Error saving User record");
            throw new RuntimeException("Error saving User record");
        }
        else LOG.trace(String.format("Saved User record: %s", user.getEmail()));

        return user;
    }
}
