package com.codecrete.sqlms.controller;

import com.codecrete.sqlms.configuration.IntegrationTestConfiguration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

/**
 *
 */
@WebMvcTest
@RunWith(SpringRunner.class)
@ContextConfiguration(classes=IntegrationTestConfiguration.class)
public class AuthenticateIT {
    
    private static final Logger LOG = LogManager.getLogger(AuthenticateIT.class);

    @Autowired
    private MockMvc mvc;

    // FIX: (AuthenticationCredentialsNotFoundException) Database out of sync
    @Test
    public void testAuthenticate() throws Exception {

        // Check credentials against database
//        this.mvc.perform(formLogin("/login").user("tim.santaniello@codecrete.com").password("password"))
//                .andExpect(authenticated().withRoles("USER","MULE","ADMIN","ACTUATOR"));
    }
}
