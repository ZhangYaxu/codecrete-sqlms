package com.codecrete.sqlms.configuration;

import com.codecrete.domain.model.Company;
import com.codecrete.domain.model.Role;
import com.codecrete.domain.model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Eliot Morris
 */
@TestConfiguration
public class MockBeanConfiguration {
    
    /**
     * Log4j logger.
     */
    private static final Logger LOG = LogManager.getLogger(MockBeanConfiguration.class);
    
    @Bean
    @Scope("prototype")
    public Role mockActuatorRole() {
        
        Role role = new Role();
        role.setId(4);
        role.setName("ROLE_ACTUATOR");
        role.setDescription("Actuator role");
        role.setCreated(mockCreated());
        role.setModified(mockModified());
        
        return role;
    }
    
    @Bean
    @Scope("prototype")
    public Role mockAdminRole() {
        
        Role role = new Role();
        role.setId(3);
        role.setName("ROLE_ADMIN");
        role.setDescription("Admin role");
        role.setCreated(mockCreated());
        role.setModified(mockModified());
        
        return role;
    }
    
    @Bean
    public Company mockCompany() {
        
        Company company = new Company();
        company.setId(1);
        company.setCreated(mockCreated());
        company.setModified(mockModified());
        company.setName("Codecrete");
        
        return company;
    }
    
    @Bean
    public LocalDateTime mockCreated() {
        return LocalDateTime.of(1979, 9, 28, 11, 34, 00, 000);
    }
    
    @Bean
    public LocalDateTime mockModified() {
        return LocalDateTime.of(2014, 12, 14, 11, 34, 00, 000);
    }
    
    @Bean
    @Scope("prototype")
    public Role mockMuleRole() {
        
        Role role = new Role();
        role.setId(2);
        role.setName("ROLE_MULE");
        role.setDescription("Mule role");
        role.setCreated(mockCreated());
        role.setModified(mockModified());
        
        return role;
    }
    
    @Bean
    @Lazy
    @Scope("prototype")
    public User mockTim() {
        
        String email = "tim.santaniello@codecrete.org";
        String forename = "Tim";
        String surname = "Santaniello";
        
        return mockUser(email, forename, surname);
    }
    
    @Bean
    @Lazy
    @Scope("prototype")
    public User mockUser(String email, String forename, String surname) {
        
        Set<Role> roles = new HashSet<>();
        roles.add(mockActuatorRole());
        roles.add(mockAdminRole());
        roles.add(mockMuleRole());
        roles.add(mockUserRole());
        
        User user = new User();
        user.setCompany(mockCompany());
        user.setCreated(mockCreated());
        user.setEmail(email);
        user.setForename(forename);
        user.setModified(mockModified());
        user.setSurname(surname);
        user.setPassword("password");
        user.setRoles(roles);
        
        return user;
    }
    
    @Bean
    @Scope("prototype")
    public Role mockUserRole() {
        
        Role role = new Role();
        role.setId(1);
        role.setName("ROLE_USER");
        role.setDescription("User role");
        role.setCreated(mockCreated());
        role.setModified(mockModified());
        
        return role;
    }
}
