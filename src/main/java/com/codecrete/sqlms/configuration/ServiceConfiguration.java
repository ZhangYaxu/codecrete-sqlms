package com.codecrete.sqlms.configuration;

import com.codecrete.sqlms.service.AuthenticateService;
import com.codecrete.sqlms.service.BoilerplateService;
import com.codecrete.sqlms.service.DashboardService;
import com.codecrete.sqlms.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * @author Eliot Morris
 */
public class ServiceConfiguration {
    
    /**
     * Log4j logger.
     */
    private static final Logger LOG = LogManager.getLogger(ServiceConfiguration.class);
    
    @Bean
    @Scope("prototype")
    public AuthenticateService authenticateService() {
        return new AuthenticateService();
    }
    
    @Bean
    @Scope("prototype")
    public BoilerplateService boilerplateService() {
        return new BoilerplateService();
    }
    
    @Bean
    @Scope("prototype")
    public DashboardService dashboardService() {
        return new DashboardService();
    }
    
    @Bean
    @Scope("prototype")
    public UserDetailsService userDetailsService() {
        return new AuthenticateService();
    }
    
    @Bean
    @Scope("prototype")
    public UserService userService() {
        return new UserService();
    }
}
